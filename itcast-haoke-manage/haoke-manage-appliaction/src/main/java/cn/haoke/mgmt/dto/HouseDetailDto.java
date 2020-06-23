package cn.haoke.mgmt.dto;

import cn.haoke.mgmt.vo.HouseData;
import lombok.Data;

/***
 * 房源详情dto
 */
@Data
public class HouseDetailDto extends HouseData {

    /***
     * 是否收藏
     */
    private Boolean isFavorite;

}
