package cn.tedu.camper.portal.service;

import cn.tedu.camper.portal.model.Images;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IImagesService extends IService<Images> {
         String saveImage(String imageName);

}
