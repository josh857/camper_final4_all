package cn.tedu.camper.portal.controller;


import cn.tedu.camper.portal.Vo.R;
import cn.tedu.camper.portal.service.IShopcarService;
import cn.tedu.camper.portal.service.impl.ShopcarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/shopcar")
public class ShopcarController {
    @Autowired
    IShopcarService shopcarService;

@PostMapping("/update/{id}")
    public R updatequantity(Integer quan, @PathVariable Integer id ){
    shopcarService.updatequantity(quan,id);
    return R.ok("成功更新數據");
}


@PostMapping("/delete/{id}")
    public R deletecarproduct(@PathVariable Integer id){
    shopcarService.deletecarproduct(id);
    return R.ok("成功刪除數據");
}
}
