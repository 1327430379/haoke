package cn.haoke.center.house.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteVo implements Serializable {

    private Long id;
    /***
     * 房源Id
     */
    private String houseId;

    /**
     * 用户id
     */
    private Long userId;

    /***
     * 房源标题
     */
    private String houseTitle;

    /***
     * 房源图片
     */
    private String houseImage;

    /***
     * 房源租金
     */
    private String houseRent;
}
