package cn.tedu.camper.portal.Vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserVo {
    private Integer Id;
    private String nickname;

}
