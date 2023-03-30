package cn.tedu.camper.portal.controller;

import cn.tedu.camper.portal.Vo.R;
import cn.tedu.camper.portal.Vo.ChangeVo;
import cn.tedu.camper.portal.Vo.RegisterVo;
import cn.tedu.camper.portal.mapper.*;
import cn.tedu.camper.portal.model.Orders;
import cn.tedu.camper.portal.model.Shopcar;
import cn.tedu.camper.portal.model.User;
import cn.tedu.camper.portal.service.IImagesService;
import cn.tedu.camper.portal.service.IShopcarService;
import cn.tedu.camper.portal.service.IUserService;
import cn.tedu.camper.portal.service.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class SysController {
@Autowired
    IUserService userService;
@Autowired
    CarMapper carMapper;
@Autowired
    UserMapper userMapper;
@Autowired
    ShopcarMapper shopcarMapper;
@Autowired
    IShopcarService shopcarService;
@Autowired
    OrdersMapper ordersMapper;
@Autowired
    IImagesService imagesService;
BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @GetMapping("/login.html")
    ModelAndView login(){
        return new ModelAndView("login");
    }

    @GetMapping("/imageUpload.html")
    ModelAndView imageUpload(){
        return new ModelAndView("imageUpload");
    }


    @GetMapping("/register.html")
    ModelAndView register(){
        return new ModelAndView("register");
    }

    @GetMapping("/index.html")
    ModelAndView index(){
        return new ModelAndView("index");
    }

    @GetMapping("/index_type1.html")
    ModelAndView index_type1(){
        return new ModelAndView("index_type1");
    }

    @GetMapping("/index_type2.html")
    ModelAndView index_type2(){
        return new ModelAndView("index_type2");
    }

    @GetMapping("/index_type3.html")
    ModelAndView index_type3(){
        return new ModelAndView("index_type3");
    }

    @GetMapping("/changePassword.html")
    ModelAndView change(){
        return new ModelAndView("changePassword");
    }
    @GetMapping("/shopList.html")
    ModelAndView shopList(){
        return new ModelAndView("shopList");
    }
    @GetMapping("/update.html")
    ModelAndView update(){
        return new ModelAndView("update");
    }
    @GetMapping("/uploadFiles.html")
    ModelAndView uploadFile(){
        return new ModelAndView("uploadFiles");

    }
   //串接結帳動作
    @GetMapping("/payedshopList.html")
    ModelAndView payed_shopList(){

        String username = userService.currentUsername();
        User user = userMapper.finduserByUsername(username);

        Orders orders = new Orders();
        List<Shopcar> list = shopcarService.getUserProduct();
        for(Shopcar shopCar: list) {
            orders.setUsername(user.getNickname());
            orders.setUserId(user.getId());
            orders.setUpdatetime(LocalDateTime.now());
            orders.setCreatetime(LocalDateTime.now());
            orders.setAddress(user.getAddress());
            orders.setProductId(shopCar.getProductid());
            Integer price= shopCar.getPrice()*shopCar.getQuantity();
            orders.setPrice(price);
            orders.setPhone(Integer.parseInt(user.getPhone()));
            orders.setQuantity(shopCar.getQuantity());
            ordersMapper.insert(orders);
            log.debug("存取成功");
        }
        QueryWrapper<Shopcar> query = new QueryWrapper<>();
        query.eq("userid",user.getId());
        shopcarMapper.delete(query);
        log.debug("支付完畢!刪除購物車資料");
        return new ModelAndView("payedshopList");

    }



    @GetMapping("/product.html")
    ModelAndView detail(){
        return new ModelAndView("product");
    }
    @GetMapping("/car.html")
    ModelAndView car(){
        return new ModelAndView("car");
    }



    //顯示購物車內商品訊息
    @GetMapping("/orders.html")
    ModelAndView orders(){
        String username=userService.currentUsername();
        User user = userMapper.finduserByUsername(username);
        QueryWrapper<Shopcar> query = new QueryWrapper<>();
        query.eq("userid" ,user.getId());
        List<Shopcar> list = shopcarMapper.selectList(query);
        if(list.isEmpty()){
            throw  new ServiceException("請先加入商品至購物車");
        }
        return new ModelAndView("order");
    }


    //註冊功能
    @PostMapping("/register")
    public R register (@Validated RegisterVo registerVo, BindingResult result){
        if(result.hasErrors()){
            String error = result.getFieldError().getDefaultMessage();
            log.info("表單驗證錯誤{}",error);
            return R.unproecsableEntity(error);
        }
        if(!registerVo.getPassword().equals(registerVo.getConfirm())){
            log.info("確認密碼不一致{}");
            return R.unproecsableEntity("確認密碼不一致");

        }
       try {
           userService.register(registerVo);
           return R.created("註冊成功");
       }catch (ServiceException e){
           log.error("註冊失敗",e);
           return R.failed(e);
       }
    }


    //更改密碼功能
    @PostMapping("/change/{id}")
    public R change(ChangeVo changevo, @PathVariable Integer id){
        String username = userService.currentUsername();
        User user = userMapper.finduserByUsername(username);
        if(id==null){
            return R.notFound("請給id");
        }
        if (changevo==null){
            return R.unproecsableEntity("請輸入完整參數");
        }
        log.debug("原密碼:{}",user.getPassword());
        if(changevo.getOldpassword().equals(user.getPassword())){
            return R.unproecsableEntity("原密碼輸入錯誤");
        }
        userService.change(changevo,id);
        return R.ok("更改完成");
    }



    //轉base64
    public static String tobase64(String path){
        InputStream in = null;
        byte[] data=null;
        try {
           in = new FileInputStream(path);
           data = new byte[in.available()];
           in.read(data);
           in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BASE64Encoder base64=new BASE64Encoder();
        return base64.encode(data);
    }

    @PostMapping("/upload")
    public R uploadImage (MultipartFile imageFile) throws IOException {
        //創建目標存儲目錄
        String path = "C:/resource";
        File folder = new File(path);
        folder.mkdirs();
        log.debug("存儲文件夾");

        //獲取擴展名
        String filename = imageFile.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf("."));

        log.debug("擴展名{}",ext);
        //生成隨機文件名
        String name = UUID.randomUUID().toString()+ext;
        File file = new File(folder,name);
        log.debug("保存到:{}",file.getAbsolutePath());

        //保存文件
        imageFile.transferTo(file);
        //文件的顯示url
        String url =path+"/"+name;
        //轉為base64
        String str=tobase64(url);
        log.debug(str);
        //存取數據庫
        String message=imagesService.saveImage(str);
        log.debug("{}",url);
        return R.ok(str);
    }



}
