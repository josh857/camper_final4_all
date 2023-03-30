package cn.tedu.camper.portal.mapper;

import cn.tedu.camper.portal.model.Car;
import cn.tedu.camper.portal.model.Shopcar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


    @Repository
    public interface ShopcarMapper extends BaseMapper<Shopcar> {
    @Select("update shopcar set quantity=#{quan},total=#{total} where userid=#{userid} AND id=#{id}")
    void updatequantity(Integer quan, Integer total,Integer userid, Integer id);

    @Select("select * from shopcar where userid=#{userid} AND productid=#{productid}")
    Shopcar getproduct(Integer userid, Integer productid);
    }
