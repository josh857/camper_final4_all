package cn.tedu.camper.portal.service;

import cn.tedu.camper.portal.Vo.ChangeVo;
import cn.tedu.camper.portal.Vo.RegisterVo;
import cn.tedu.camper.portal.Vo.UserVo;
import cn.tedu.camper.portal.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;


public interface IUserService extends IService<User> {

    UserDetails getUserDetails(String username);



    void register(RegisterVo registervo);

    UserVo getcurrentUserVo();


     String currentUsername();

     void change(ChangeVo changevo, Integer id);

    void update(String address,String nickname,Integer phone,Integer id);

}
