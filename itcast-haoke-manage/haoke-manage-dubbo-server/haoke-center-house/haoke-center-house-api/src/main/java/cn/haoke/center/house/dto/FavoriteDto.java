package cn.haoke.center.house.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FavoriteDto implements Serializable {

    private Long id;
    private String houseId;
    private String houseName;
    private Long userId;
    private Integer pageNum;
    private Integer pageSize;


}
