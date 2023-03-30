package cn.tedu.camper.portal.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shopcar")
public class Shopcar implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    /**
     * 價格
     */
    @TableField("price")
    private Integer price;

    /**
     * 1.帳篷2.天幕3.氣墊床
     */
    @TableField("type")
    private Integer type;

    /**
     * 商品示圖
     */
    @TableField("images")
    private String images;

    /**
     * 購買次數
     */
    @TableField("quantity")
    private Integer quantity;

    @TableField("userid")
    private Integer userid;

    @TableField("productid")
    private Integer productid;
    
    @TableField("total")
    private Integer total;

}
