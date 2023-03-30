package cn.tedu.camper.portal.service.impl;

import cn.tedu.camper.portal.Vo.ProductVo;
import cn.tedu.camper.portal.model.Product;
import cn.tedu.camper.portal.mapper.ProductMapper;
import cn.tedu.camper.portal.service.IProductService;
import cn.tedu.camper.portal.service.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    ProductMapper productMapper;


    //查詢所有商品品項
    @Override
    public PageInfo<Product> getproducts(Integer pagenum, Integer pagesize) {
        if (pagenum == null || pagesize == null) {
            throw ServiceException.unprocessableEntry("參數不得為空");
        }
        PageHelper.startPage(pagenum, pagesize);
        List<Product> list = productMapper.getproducts();
        log.debug("找到所有產品列表");
        return new PageInfo<>(list);
    }


    //查詢TYPE為1的商品品項
    @Override
    public PageInfo<Product> getproductsType_1(Integer pagenum, Integer pagesize) {
        if (pagenum == null || pagesize == null) {
            throw ServiceException.unprocessableEntry("參數不得為空");
        }
        PageHelper.startPage(pagenum, pagesize);
        QueryWrapper query = new QueryWrapper();
        query.eq("type", 1);
        List<Product> list = productMapper.selectList(query);
        return new PageInfo<>(list);
    }


    //查詢TYPE為2的商品品項
    @Override
    public PageInfo<Product> getproductsType_2(Integer pagenum, Integer pagesize) {
        if (pagenum == null || pagesize == null) {
            throw ServiceException.unprocessableEntry("參數不得為空");
        }
        PageHelper.startPage(pagenum, pagesize);
        QueryWrapper query = new QueryWrapper();
        query.eq("type", 2);
        List<Product> list = productMapper.selectList(query);
        return new PageInfo<>(list);
    }


    //查詢TYPE為3的商品品項
    @Override
    public PageInfo<Product> getproductsType_3(Integer pagenum, Integer pagesize) {
        if (pagenum == null || pagesize == null) {
            throw ServiceException.unprocessableEntry("參數不得為空");
        }
        PageHelper.startPage(pagenum, pagesize);
        QueryWrapper query = new QueryWrapper();
        query.eq("type", 3);
        List<Product> list = productMapper.selectList(query);
        return new PageInfo<>(list);
    }

    //查詢熱門商品
    @Override
    public List<Product> getHotProduct() {
        List<Product> list = productMapper.getHotproduct();
        list.forEach(product -> System.out.println(product));
        return list;
    }

    //搜尋找到的商品
    @Override
    public PageInfo<Product> getsearch(Integer pagenum, Integer pagesize, String key) {
        PageHelper.startPage(pagenum, pagesize);
        List<Product> list = productMapper.getsearchproduct(key);
        return new PageInfo<>(list);
    }

    //利用前端給予的商品ID 查詢該商品資訊
    @Override
    public Product getdetail(Integer id) {
        if (id == null) {
            throw new ServiceException("id不得為空");
        }
        Product product = productMapper.selectById(id);
        String img = product.getImg();
        String[] arr = img.split(",");
        product.setImage(arr);
        if (product == null) {
            throw ServiceException.busy();
        }
        return product;
    }

    public static String tobase64(String filename) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(filename);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(data);
    }


    public String getfileimage(MultipartFile file) {
        //  設置路徑
        String path = "C:/resource/";
        //創建路徑的文件夾
        File folder = new File(path);
        folder.mkdirs();
        //取得上傳的黨名
        String fileName = file.getOriginalFilename();
        try {
            File files = new File(folder, fileName);
            file.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileName);
        String uri = path + fileName;
        String basestr = tobase64(uri);
        return basestr;
    }


    //內部儲存文件 地址為C:/resource/....
    public static String saveFile(MultipartFile file) {
        //  設置路徑
        String path = "C:/resource/";
        //創建路徑的文件夾
        File folder = new File(path);
        folder.mkdirs();
        String fileName = file.getOriginalFilename();
        try {
            File files = new File(folder, fileName);
            file.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = path + fileName;
        return url;
    }

    //接收前端 MutipartFile 的 list 並存取檔案 讀取內容分割用   ProductVo 封裝 並加入list中
    @Override
    public String uploadFiles(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            //使用 saveFile方法儲存到硬碟位置
            //並將
            String url = saveFile(file);
            File f = new File(url);
            String content = null;
            try (FileInputStream in = new FileInputStream(f);
                 InputStreamReader isr = new InputStreamReader(in, "utf-8");
                 BufferedReader redear = new BufferedReader(isr)) {
                while ((content = redear.readLine()) != null) {
                    String[] arr = content.split(",");
                    //new 一個Product 類 封裝 vo 參數
                    Product p = new Product();
                    p.setTitle(arr[0])
                            .setPerson(arr[1])
                            .setContent(arr[2])
                            .setFunction(arr[3])
                            .setPrice(Integer.parseInt(arr[4]))
                            .setType(Integer.parseInt(arr[5]))
                            .setImg("123")
                            .setBuyQuantity(1);
                    saveProduct(p);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "成功";
    }

    //存取product
    @Transactional
    @Override
    public void saveProduct(Product product) {
        productMapper.insert(product);

    }

//    //建立關閉 schedurler 方法
//    public void closeScheduler() throws SchedulerException {
//        Scheduler scheduler=getTimmer();
//        scheduler.shutdown();
//    }
//
//    //建立 開啟job 方法
//    public Scheduler getTimmer() throws SchedulerException {
//        JobDetail job = JobBuilder.newJob(MyQuartz.class).build();
//        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5)).build();
//        Scheduler scheduler =StdSchedulerFactory.getDefaultScheduler();
//        scheduler.scheduleJob(job,trigger);
//        scheduler.start();
//        return scheduler;
//    }
}

