package cn.tedu.camper.portal.controller;


import cn.tedu.camper.portal.Vo.R;
import cn.tedu.camper.portal.Vo.OrderVo;
import cn.tedu.camper.portal.Vo.UserVo;
import cn.tedu.camper.portal.mapper.OrdersMapper;
import cn.tedu.camper.portal.mapper.UserMapper;
import cn.tedu.camper.portal.model.*;
import cn.tedu.camper.portal.paypal.CaptureIntentExamples.CreateOrder;
import cn.tedu.camper.portal.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.paypal.http.HttpResponse;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Slf4j
@RestController
@RequestMapping("/v1/orders")
public class OrdersController {
@Autowired
IUserService userService;
@Autowired
    UserMapper userMapper;
@Autowired
IOrdersService ordersService;
@Autowired
    OrdersMapper ordersMapper;
@Autowired
IShopcarService shopcarService;
@Autowired
IProductService productService;
@Autowired
ICityService cityService;


    @GetMapping("/getuser")
    public R<User> getuser(){
        String username= userService.currentUsername();
        User user = userMapper.finduserByUsername(username);
        return R.ok(user);
    }
    @PostMapping("/insert")
    public R insert(@Validated OrderVo orderVo , BindingResult result){
        if(result.hasErrors()){
            String error = result.getFieldError().getDefaultMessage();
            log.info("表單驗證錯誤{}",error);
            return R.unproecsableEntity(error);
        }
        ordersService.insert(orderVo);
        log.debug("存取所有商品資料成功");
        return R.ok("存取成功");
    }


    @GetMapping("/paypal")
    public R pay() throws IOException {
        //先獲得當前user
        String username = userService.currentUsername();
        User u = userMapper.finduserByUsername(username);
        //獲得其中一張訂單資料
//        QueryWrapper<Orders> query = new QueryWrapper<>();
//        query.eq("user_id",u.getId());
//        List<Orders> order = ordersMapper.selectList(query);
//        //獲得其中一項商品訂單的收件人地址
//        String address = order.get(0).getAddress();
//        log.debug("{}",address);
        String address = u.getAddress();
        List<Shopcar> list = shopcarService.getUserProduct();
        //放進creatorder裡創建
        CreateOrder createOrder = new CreateOrder();
        HttpResponse<Order> orders= createOrder.createOrder(true,list,u,address);
        //先取得創建的orderId 之後 capture 使用
        String orderId=orders.result().id();
        LinkDescription link =orders.result().links().get(1);
        String url = link.href();
        log.debug(url);
        return R.ok(url);
    }

    @GetMapping("/getlist")
    public R<List<Product>> getlist(){
        List<Product> products = new ArrayList<>();
        UserVo user = userService.getcurrentUserVo();
        QueryWrapper<Orders> query = new QueryWrapper<>();
        query.eq("user_id",user.getId());
        List<Orders> list = ordersMapper.selectList(query);
        log.debug("{}",list);
        //用orderid 拿到所有product資料返回list
    for(Orders orders :list){
        Product product =productService.getById(orders.getProductId());
        product.setQuantity(orders.getQuantity());
        Integer total = orders.getPrice()*orders.getQuantity();
        product.setTotal(total);
        products.add(product);
    }
    log.debug("{}",products);
    return R.ok(products);
    }
    @GetMapping("/getrange/{type}")
    public R<List<String>> getrange(@PathVariable String type){
        City c = ordersService.getcitytype(type);
        List<Cityrange> list = new CopyOnWriteArrayList<>();
        list = ordersService.getrange(c.getType());
        List<String> list1 = new CopyOnWriteArrayList<>();
        for(Cityrange range:list){
            list1.add(range.getName());
        }
        return R.ok(list1);
    }

    @GetMapping("getcity")
    public R<List<String>> getcity(){
       List<City> list = ordersService.getcity();
       List<String> list2=new CopyOnWriteArrayList<>();
       for(City c:list){
           list2.add(c.getName());
       }
       return R.ok(list2);

    }

}
