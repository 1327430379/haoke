package cn.haoke.mgmt.vo;

import cn.haoke.center.house.pojo.HouseResources;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseResourcesVo  {

    /***
     * 房源id
     */
    private String id;

    /***
     * 创建时间
     */
    private Date createTime;

    /***
     * 更新时间
     */
    private Date updateTime;


    /**
     * 房源标题
     */
    private String title;


    /**
     * 租金
     */
    private Integer rent;

    /**
     * 租赁方式，1-整租，2-合租
     */
    private Integer rentMethod;

    /**
     * 户型，如：2室1厅1卫
     */
    private String houseType;
    /**
     * 使用面积
     */
    private String useArea;

    /**
     * 楼层，如：8/26
     */
    private String floor;

    /**
     * 朝向：东、南、西、北
     */
    private Integer orientation;


    /**
     * 图片，最多5张
     */
    private String image;

    /**
     * 描述
     */
    private String houseDesc;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 看房时间，1-上午，2-中午，3-下午，4-晚上，5-全天
     */
    private Integer time;


    /***
     * 房东id
     */
    private Long houseOwnerId;

    /***
     * 房东姓名
     */
    private String houseOwnerName;

    /**
     * 房东头像
     */
    private String houseOwnerAvatar;

    /***
     * 是否收藏
     */
    private Boolean isFavorite;
}
