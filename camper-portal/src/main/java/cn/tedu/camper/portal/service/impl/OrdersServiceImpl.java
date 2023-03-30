package cn.tedu.camper.portal.service.impl;

import cn.tedu.camper.portal.Vo.OrderVo;

import cn.tedu.camper.portal.Vo.UserVo;
import cn.tedu.camper.portal.mapper.CityMapper;
import cn.tedu.camper.portal.mapper.CityrangeMapper;
import cn.tedu.camper.portal.mapper.ShopcarMapper;
import cn.tedu.camper.portal.model.City;
import cn.tedu.camper.portal.model.Cityrange;
import cn.tedu.camper.portal.model.Orders;
import cn.tedu.camper.portal.mapper.OrdersMapper;
import cn.tedu.camper.portal.model.Shopcar;
import cn.tedu.camper.portal.service.IOrdersService;
import cn.tedu.camper.portal.service.IShopcarService;
import cn.tedu.camper.portal.service.IUserService;
import cn.tedu.camper.portal.service.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    @Autowired
    IUserService userService;
    @Autowired
    IShopcarService shopcarService;
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    ShopcarMapper shopcarMapper;
    @Autowired
    CityrangeMapper cityrangeMapper;
    @Autowired
    CityMapper cityMapper;
    @Override
    public void insert(OrderVo orderVo) {
        if(orderVo==null){
            throw new ServiceException("請下單後再執行此作業");
        }
        //當前參數更改當前user表中的地址欄
        UserVo userVo = userService.getcurrentUserVo();
        userService.update(orderVo.getAddress(),orderVo.getNickname(),Integer.parseInt(orderVo.getPhone()),userVo.getId());
//        Orders orders = new Orders();
        //獲取當前用戶 購物車裡的商品資訊list
//        List<Shopcar> list = shopcarService.getUserProduct();
//        for(Shopcar shopCar: list) {
//           orders.setUsername(orderVo.getNickname());
//            orders.setPhone(orderVo.getPhone());
//            orders.setAddress(orderVo.getAddress());
//            orders.setPayed(orderVo.getType());
//           orders.setProductId(shopCar.getProductid());
//            orders.setUserId(shopCar.getUserid());
//           Integer price= shopCar.getPrice()*shopCar.getQuantity();
//           orders.setPrice(price);
//            orders.setQuantity(shopCar.getQuantity());
//            orders.setCreatetime(LocalDateTime.now());
//           orders.setUpdatetime(LocalDateTime.now());
//           ordersMapper.insert(orders);
//           log.debug("存取成功");
//       }
    }
    public void delete (){
        QueryWrapper<Shopcar> query = new QueryWrapper<>();
        Integer id = userService.getcurrentUserVo().getId();
        query.eq("userId",id);
        int row=shopcarMapper.delete(query);
        if(row>1){
            log.debug("刪除成功");
        }
    }

    @Override
    public List<Cityrange> getrange(Integer type) {
        QueryWrapper<Cityrange> query=new QueryWrapper<>();
        query.eq("type",type);
        List<Cityrange> list=cityrangeMapper.selectList(query);
        return list;

    }

    @Override
    public List<City> getcity() {
        List<City> list = cityMapper.getcitys();
        return list;
    }

    @Override
    public City getcitytype(String name) {
        QueryWrapper query = new QueryWrapper();
        query.eq("name",name);
        City c=cityMapper.selectOne(query);
        return c;
    }
}
