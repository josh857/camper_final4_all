package cn.tedu.camper.portal.service.impl;

import cn.tedu.camper.portal.mapper.ImagesMapper;
import cn.tedu.camper.portal.model.Images;
import cn.tedu.camper.portal.service.IImagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ImagesServiceImpl extends ServiceImpl<ImagesMapper, Images> implements IImagesService {
    @Autowired
    ImagesMapper imagesMapper;

    @Override
    public String saveImage(String imageName) {
        Images images = new Images();
        images.setName(imageName);
        int rows=imagesMapper.insert(images);
        if(rows!=1){
            log.debug("存取失敗");
            return "數據庫存取失敗";
        }
        log.debug("存取成功");
        return "數據庫保存成功";
    }
}
