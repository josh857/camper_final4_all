package cn.tedu.camper.portal.service.impl;

import cn.tedu.camper.portal.Vo.ChangeVo;
import cn.tedu.camper.portal.Vo.RegisterVo;
import cn.tedu.camper.portal.Vo.UserVo;
import cn.tedu.camper.portal.model.User;
import cn.tedu.camper.portal.mapper.UserMapper;
import cn.tedu.camper.portal.service.IUserService;
import cn.tedu.camper.portal.service.ServiceException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    IUserService userService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails getUserDetails(String username) {
        User user = userMapper.finduserByUsername(username);
        UserDetails u = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities("/null")
                .build();
        return u;
    }

    @Transactional
    @Override
    public void register(RegisterVo registervo) {
        if (registervo == null) {
            throw new ServiceException("請輸入註冊資料");
        }
        if (!registervo.getPassword().equals(registervo.getConfirm())) {
            throw new ServiceException("確認密碼不匹配");
        }

        log.debug("驗證帳號是否使用");
        User user = userMapper.finduserByUsername(registervo.getUsername());
        if (user != null) {
            throw new ServiceException("帳號重複");
        }
        user = new User();
        user.setUsername(registervo.getUsername());
        user.setNickname(registervo.getNickname());
        String password = passwordEncoder.encode(registervo.getPassword());
        user.setPassword("{bcrypt}" + password);
        user.setAddress(registervo.getAddress());
        user.setPhone(registervo.getPhone());
        user.setCreatetime(LocalDateTime.now());
        userMapper.insert(user);
    }


    @Override
    public UserVo getcurrentUserVo() {
        String username = currentUsername();
        UserVo userVo = userMapper.getuserVoByUsername(username);
        return userVo;
    }

    @Transactional
    @Override
    public String currentUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            return username;
        }
        throw ServiceException.notFound("當前用戶找不到");
    }
    @Transactional
    @Override
    public void change(ChangeVo changevo, Integer id) {
        //拿到當前用戶信息
        User user = userService.getById(id);
        //加密收到的新密碼
        String newpassword = passwordEncoder.encode(changevo.getPassword());
        newpassword ="{bcrypt}"+ newpassword;
        log.debug(newpassword);
        //加密的密碼與現有密碼比對
        if(user.getPassword().equals(newpassword)){
           throw new ServiceException("密碼不得一樣");
        }
        if(!changevo.getPassword().equals(changevo.getConfirm())){
            throw new ServiceException("確認密碼不一致");
        }
        userMapper.updatePassword(newpassword,id);
    }

    public void update(String address,String nickname,Integer phone,Integer id){
        userMapper.update(address,nickname,phone,id);
    }
}