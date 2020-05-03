package cn.haoke.center.house.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 房源表
 * </p>
 *
 * @author itcast
 */
@Data
@Accessors(chain = true)
@TableName("hk_house_resources")
public class HouseResources implements Serializable {

    /***
     * 房源标题
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /***
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    private Date createTime;

    /***
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    /***
     * 地址
     */
    private String houseAddress;

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

    /***
     * 审核状态（0：未审核，1：审核通过，2：审核未通过）
     */
    private Integer checkStatus;

    /**
     * 租赁状态(0:未出租，1:已出租，2：到期)
     */
    private Integer rentStatus;

    /**
     * 装修，1-精装，2-简装，3-毛坯
     */
    private Integer decoration;

    /***
     * 配套设施
     */
    private String facilities;


}
