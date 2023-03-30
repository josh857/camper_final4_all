package cn.tedu.camper.portal.mapper;

import cn.tedu.camper.portal.model.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


    @Repository
    public interface ProductMapper extends BaseMapper<Product> {
            @Select("select * from product order by type")
            List<Product> getproducts();


            @Select("select * from product order by buy_quantity asc limit 0,3")
            List<Product> getHotproduct();

            @Select("select * from product where title like '%${key}%'")
            List<Product> getsearchproduct(String key);

    }

