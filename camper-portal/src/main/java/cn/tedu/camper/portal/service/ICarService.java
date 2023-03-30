package cn.tedu.camper.portal.service;

import cn.tedu.camper.portal.Vo.CarVo;
import cn.tedu.camper.portal.model.Car;
import cn.tedu.camper.portal.model.Shopcar;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ICarService extends IService<Car> {
    String gotocar(Integer id);

    CarVo getrows();

     List<Shopcar> getuserproduct();

}
