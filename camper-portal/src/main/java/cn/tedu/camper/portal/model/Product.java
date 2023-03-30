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
@TableName("product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    /**
     * 使用人數
     */
    @TableField("person")
    private String person;

    /**
     * 功能
     */
    @TableField("function")
    private String function;

    /**
     * 內容
     */
    @TableField("content")
    private String content;

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
    @TableField("buy_quantity")
    private Integer buyQuantity;

    /**
     * 商品內容示圖 
     */
    @TableField("img")
    private String img;

    @TableField(exist = false)
    private Integer quantity;

    @TableField(exist = false)
    private String[] image;
    @TableField(exist = false)
    private Integer total;

}
