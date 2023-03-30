package cn.tedu.camper.portal.service.impl;

import cn.tedu.camper.portal.Vo.CarVo;
import cn.tedu.camper.portal.Vo.UserVo;
import cn.tedu.camper.portal.mapper.CarMapper;
import cn.tedu.camper.portal.mapper.ProductMapper;
import cn.tedu.camper.portal.mapper.ShopcarMapper;
import cn.tedu.camper.portal.model.Car;
import cn.tedu.camper.portal.model.Product;
import cn.tedu.camper.portal.model.Shopcar;
import cn.tedu.camper.portal.service.ICarService;
import cn.tedu.camper.portal.service.IUserService;
import cn.tedu.camper.portal.service.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService {
    @Autowired
    IUserService userService;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CarMapper carMapper;
    @Autowired
    ShopcarMapper shopcarMapper;

    //點擊加入購物車後存取至car表中
    @Override
    public String gotocar(Integer id) {

        if(id ==null){
            throw new ServiceException("需要商品id");
        }
        String username = userService.currentUsername();
        //拿到當前user
        UserVo u  =userService.getcurrentUserVo();
        //拿到選取的product
        Product p = productMapper.selectById(id);
        //判斷是否重複點擊加入購物車不重複存取商品資料
        Shopcar oldcar =shopcarMapper.getproduct(u.getId(),p.getId());
        if(oldcar!=null){
            return "已重複加入";
        }
        Shopcar shopcar = new Shopcar();
        shopcar.setUserid(u.getId());
        shopcar.setProductid(p.getId());
        shopcar.setPrice(p.getPrice());
        shopcar.setTitle(p.getTitle());
        shopcar.setType(p.getType());
        shopcar.setQuantity(p.getBuyQuantity());
        shopcar.setImages(p.getImages());
        Integer total = p.getPrice()*p.getBuyQuantity();
        shopcar.setTotal(total);
        int rows= shopcarMapper.insert(shopcar);
        if(rows!=1) {
            return ServiceException.busy().getLocalizedMessage();
        }
        //設置Car 對象
        Car car = new Car();
        car.setUserid(u.getId());
        car.setProductid(p.getId());
        car.setPrice(p.getPrice());
        car.setCreatetime(LocalDateTime.now());
        car.setUpdatetime(LocalDateTime.now());
        car.setQuantity(1);
        //存取商品資料
         int row=carMapper.insert(car);
        if(row!=1) {
            return ServiceException.busy().getLocalizedMessage();
        }
        return "存取成功";
    }

    //顯示購物車內商品數量
    @Override
    public CarVo getrows() {
        String username= userService.currentUsername();
        UserVo u = userService.getcurrentUserVo();
        QueryWrapper<Shopcar> query = new QueryWrapper<>();
        query.eq("userid",u.getId());
       int row= shopcarMapper.selectCount(query);
        CarVo carVo=new CarVo();
        carVo.setRow(row);
       return carVo;
    }



    //顯示用戶的購物車商品資訊
    public List<Shopcar> getuserproduct(){
     //拿到當前用戶
     String username= userService.currentUsername();
     UserVo u = userService.getcurrentUserVo();
     //用當前用戶id找尋購物車資訊list
     QueryWrapper<Shopcar> query= new QueryWrapper<>();
     query.eq("userid",u.getId());
     List<Shopcar> cars = shopcarMapper.selectList(query);
     return cars;
    }

}
