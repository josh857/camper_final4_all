package cn.tedu.camper.portal.service.impl;

import cn.tedu.camper.portal.mapper.CityMapper;
import cn.tedu.camper.portal.model.City;
import cn.tedu.camper.portal.service.ICityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

}
