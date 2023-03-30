package cn.tedu.camper.portal.Vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Accessors(chain = true)
@Data
public class OrderVo {
    @NotBlank(message = "姓名不得為空")
    @Pattern(regexp = "^.{2,20}$",message = "姓名需要2~20個字,數字")
    private String nickname;
   @Pattern(regexp = "09\\d{8}")
    private String phone;
    @NotBlank(message = "地址不得為空")
    private String address;
    private Integer type;
}
