package cn.tedu.camper.portal.controller;


import cn.tedu.camper.portal.Vo.R;
import cn.tedu.camper.portal.Vo.UserVo;
import cn.tedu.camper.portal.service.IUserService;
import cn.tedu.camper.portal.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/me")
    public R<UserVo> getcurrentUserVo(){
        UserVo uservo= userService.getcurrentUserVo();
        if(uservo==null){
            return R.failed(ServiceException.notFound("加載失敗"));
        }
        return R.ok(uservo);
    }

}
