package cn.tedu.camper.portal.service;

import cn.tedu.camper.portal.Vo.ProductVo;
import cn.tedu.camper.portal.model.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IProductService extends IService<Product> {
    /**
     * @param pagenum  當前頁號
     * @param pagesize 當前頁的產品數量
     * @return 當前所有產品
     */
    PageInfo<Product> getproducts(Integer pagenum, Integer pagesize);


    PageInfo<Product> getproductsType_1(Integer pagenum, Integer pagsize);

    PageInfo<Product> getproductsType_2(Integer pagenum, Integer pagsize);

    PageInfo<Product> getproductsType_3(Integer pagenum, Integer pagsize);

    PageInfo<Product> getsearch(Integer pagenum, Integer pagesize, String key);

    List<Product> getHotProduct();

    Product getdetail(Integer id);

    String getfileimage(MultipartFile multipartFile);

//    String  saveProducts(List<ProductVo> pvs);

    void saveProduct(Product product);

    String uploadFiles(List<MultipartFile> files);

}
