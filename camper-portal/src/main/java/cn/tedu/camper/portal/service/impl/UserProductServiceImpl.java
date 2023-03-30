package cn.tedu.camper.portal.service.impl;

import cn.tedu.camper.portal.model.UserProduct;
import cn.tedu.camper.portal.mapper.UserProductMapper;
import cn.tedu.camper.portal.service.IUserProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserProductServiceImpl extends ServiceImpl<UserProductMapper, UserProduct> implements IUserProductService {

}
