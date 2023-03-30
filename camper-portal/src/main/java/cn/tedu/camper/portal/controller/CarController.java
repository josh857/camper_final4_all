package cn.tedu.camper.portal.controller;


import cn.tedu.camper.portal.Vo.CarVo;
import cn.tedu.camper.portal.Vo.R;
import cn.tedu.camper.portal.model.Car;
import cn.tedu.camper.portal.model.Product;
import cn.tedu.camper.portal.model.Shopcar;
import cn.tedu.camper.portal.service.ICarService;
import cn.tedu.camper.portal.service.impl.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/car")
public class CarController {
@Autowired
ICarService carService;



@PostMapping("/incar/{id}")
    R gotocar(@PathVariable Integer id){
    String message=carService.gotocar(id);
    return R.created(message);
}



@GetMapping("/getrows")
    R<CarVo> getrow(){
    CarVo carVo = carService.getrows();
    return R.ok(carVo);
}



@GetMapping("/getproducts")
    R<List<Shopcar>> getuserproducts(){
    List<Shopcar> list=carService.getuserproduct();
    return R.ok(list);
}

}
