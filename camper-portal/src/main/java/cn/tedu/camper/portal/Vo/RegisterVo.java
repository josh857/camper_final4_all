package cn.tedu.camper.portal.Vo;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Accessors(chain = true)
@Data
public class RegisterVo {
    @NotBlank(message = "姓名不得為空")
    @Pattern(regexp = "^.{2,20}$",message = "姓名需要2~20個字,數字")
    private  String nickname;
    @NotBlank(message = "帳號不得為空")
    @Pattern(regexp = "^.{2,20}$",message = "帳號需要2~20個字,數字")
    private String username;
    @NotBlank(message = "密碼不得為空")
    @Pattern(regexp = "^\\w{6,20}$",message = "密碼6~20個字母,數字")
    private String password;
    @NotBlank(message="確認密碼不得為空")
    private String confirm;
    @NotBlank(message = "手機號不得為空")
    @Pattern(regexp = "^.{10}$",message = "手機格式錯誤")
    private String phone;
    @NotBlank(message = "地址不得為空")
    private String address;

}
