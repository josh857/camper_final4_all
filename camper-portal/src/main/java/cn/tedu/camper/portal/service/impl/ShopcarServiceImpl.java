package cn.tedu.camper.portal.service.impl;

import cn.tedu.camper.portal.Vo.UserVo;
import cn.tedu.camper.portal.mapper.CarMapper;
import cn.tedu.camper.portal.mapper.ProductMapper;
import cn.tedu.camper.portal.model.Shopcar;
import cn.tedu.camper.portal.mapper.ShopcarMapper;
import cn.tedu.camper.portal.service.IShopcarService;
import cn.tedu.camper.portal.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShopcarServiceImpl extends ServiceImpl<ShopcarMapper, Shopcar> implements IShopcarService {
    @Autowired
    ShopcarMapper shopcarMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    IUserService userService;
    @Autowired
    CarMapper carMapper;
    @Override
    public void updatequantity(Integer quan,Integer id ) {
        String username= userService.currentUsername();
        UserVo u = userService.getcurrentUserVo();
        Shopcar shopcar = shopcarMapper.selectById(id);
        Integer total = shopcar.getPrice();
        total*=quan;
        shopcarMapper.updatequantity(quan,total,u.getId(),id);


    }

    @Override
    public void deletecarproduct(Integer id) {
        String username= userService.currentUsername();
        UserVo u = userService.getcurrentUserVo();
        QueryWrapper<Shopcar> query= new QueryWrapper<>();
        query.eq("userid",u.getId());
        query.eq("id",id);
        int rows=shopcarMapper.delete(query);
        if(rows==1){
            log.debug("刪除資料成功");
        }
    }
    //獲得當前用戶的產品資料
    @Override
    public List<Shopcar> getUserProduct() {
        String username= userService.currentUsername();
        UserVo u = userService.getcurrentUserVo();
        QueryWrapper<Shopcar> query = new QueryWrapper<>();
        query.eq("userid",u.getId());
        List<Shopcar> shopcars= shopcarMapper.selectList(query);
        return shopcars;
    }
}
