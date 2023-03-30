package cn.tedu.camper.portal.mapper;

import cn.tedu.camper.portal.model.City;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


    @Repository
    public interface CityMapper extends BaseMapper<City> {
        @Select("select * from city")
        List<City> getcitys();
    }
