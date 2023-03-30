package cn.tedu.camper.portal.mapper;

import cn.tedu.camper.portal.Vo.UserVo;
import cn.tedu.camper.portal.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


    @Repository
    public interface UserMapper extends BaseMapper<User> {
        //用戶名找尋用戶資料
        @Select("select * from user where username=#{username}")
        User finduserByUsername(String username);

        @Select("select Id , nickname from user where username=#{username}")
        UserVo getuserVoByUsername (String username);

        @Update("update user set password=#{password} where id=#{id}")
        Integer updatePassword (String password , Integer id);

        @Update("update user set address=#{address},nickname=#{nickname},phone=#{phone} where id=#{id}")
        Integer update (String address ,String nickname,Integer phone, Integer id);
    }
