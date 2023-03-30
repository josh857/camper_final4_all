package cn.tedu.camper.portal.controller;


import cn.tedu.camper.portal.Vo.R;
import cn.tedu.camper.portal.model.Product;
import cn.tedu.camper.portal.service.IProductService;
import cn.tedu.camper.portal.service.ServiceException;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/v1/product")
public class ProductController {
    @Autowired
    IProductService productService;

    @GetMapping("/search")
    R<PageInfo<Product>> getsearch(Integer pagenum, String key) {
        if (pagenum == null) {
            pagenum = 1;
        }
        Integer pagesize = 9;
        try {
            PageInfo<Product> pageInfo = productService.getsearch(pagenum, pagesize, key);
            return R.ok(pageInfo);
        } catch (ServiceException e) {
            log.error("失敗的加載", e);
            return R.failed(e);
        }
    }

    //獲得所有產品資訊list
    @GetMapping("")
    R<PageInfo<Product>> getlist(Integer pagenum) {
        if (pagenum == null) {
            pagenum = 1;
        }
        Integer pagesize = 9;
        try {
            PageInfo<Product> pageInfo = productService.getproducts(pagenum, pagesize);
            return R.ok(pageInfo);
        } catch (ServiceException e) {
            log.error("失敗的加載", e);
            return R.failed(e);
        }
    }

    @GetMapping("/type_1")
    R<PageInfo<Product>> getlist_type1(Integer pagenum) {
        if (pagenum == null) {
            pagenum = 1;
        }
        Integer pagesize = 9;
        try {
            PageInfo<Product> pageInfo = productService.getproductsType_1(pagenum, pagesize);
            return R.ok(pageInfo);
        } catch (ServiceException e) {
            log.error("失敗的加載", e);
            return R.failed(e);
        }
    }

    @GetMapping("/type_2")
    R<PageInfo<Product>> getlist_type2(Integer pagenum) {
        if (pagenum == null) {
            pagenum = 1;
        }
        Integer pagesize = 9;
        try {
            PageInfo<Product> pageInfo = productService.getproductsType_2(pagenum, pagesize);
            return R.ok(pageInfo);
        } catch (ServiceException e) {
            log.error("失敗的加載", e);
            return R.failed(e);
        }
    }

    @GetMapping("/type_3")
    R<PageInfo<Product>> getlist_type3(Integer pagenum) {
        if (pagenum == null) {
            pagenum = 1;
        }
        Integer pagesize = 9;
        try {
            PageInfo<Product> pageInfo = productService.getproductsType_3(pagenum, pagesize);
            return R.ok(pageInfo);
        } catch (ServiceException e) {
            log.error("失敗的加載", e);
            return R.failed(e);
        }
    }

    @GetMapping("/hot")
    R<List<Product>> getHotProduct() {
        List<Product> list = productService.getHotProduct();
        return R.ok(list);
    }

    @GetMapping("/detail/{id}")
    R<Product> getdetail(@PathVariable Integer id) {
        Product p = productService.getdetail(id);
        return R.ok(p);
    }

    @PostMapping("/getfile")
    R getfile(MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        String basestr = productService.getfileimage(file);
        System.out.println(basestr);
        return R.ok(basestr);
    }

//    @PostMapping ("/save")
//    R saveProduct (@RequestParam List<ProductVo> lists){
//        System.out.println("調用saveProduct controller 開始");
//        if(message==null){
//            return R.notFound("儲存失敗");
//        }
//        return R.ok(message);
//    }


    @PostMapping("/upload")
    @ResponseBody
    void uploadFile(@RequestParam("files") List<MultipartFile> files) {
        System.out.println("傳入後端的file:" + files);
        productService.uploadFiles(files);
    }

}
