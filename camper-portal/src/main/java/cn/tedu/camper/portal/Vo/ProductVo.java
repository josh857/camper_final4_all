package cn.tedu.camper.portal.Vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Accessors(chain = true)
public class ProductVo {
    @NotBlank(message="名稱不得為空")
    private String productName ;
    @NotBlank(message = "人數不得為空")
    private String person;
    @NotBlank(message="功能不得為空")
    private String functional;
    @NotBlank(message = "內容不得為空")
    private String content;
    @NotBlank(message="價格不得為空")
    private String price;
    @NotBlank(message = "類型不得為空")
    private String type;

    private String file;

    private String fileName;
}
