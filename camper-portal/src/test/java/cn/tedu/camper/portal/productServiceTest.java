package cn.tedu.camper.portal;

import cn.tedu.camper.portal.model.Product;
import cn.tedu.camper.portal.service.IProductService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class productServiceTest {
    @Resource
    IProductService productService;
    @Test
    public void saveProduct(){
        Product product=new Product();
        product.setTitle("伊人帳篷")
                .setPrice(3000)
                .setFunction("3")
                .setContent("帳篷好玩又實在")
                .setBuyQuantity(1)
                .setPerson("3")
                .setType(2)
                .setImg("123")
                .setImages("123")
                ;

        productService.saveProduct(product);

    }


}
