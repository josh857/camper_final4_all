package cn.tedu.camper.portal.Vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ChangeVo {
    private String oldpassword;
    private String password;
    private String confirm;
}
