package cn.tedu.camper.portal.service;

import cn.tedu.camper.portal.model.Shopcar;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IShopcarService extends IService<Shopcar> {
     void updatequantity(Integer data,Integer id);

     void deletecarproduct(Integer id);

     List<Shopcar> getUserProduct();
}
