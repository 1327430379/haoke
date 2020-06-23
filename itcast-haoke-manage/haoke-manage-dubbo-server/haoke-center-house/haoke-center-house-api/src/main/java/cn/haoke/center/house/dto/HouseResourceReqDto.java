package cn.haoke.center.house.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
 * 房源搜索dto
 */
@Data
public class HouseResourceReqDto implements Serializable {



    /**
     * 房源标题
     */
    private String title;


    /**
     *租金范围1
     */
    private Integer rent1;

    /**
     * 租金范围2
     */
    private Integer rent2;

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
     * 联系人
     */
    private String contact;

    /**
     * 手机号
     */
    private String mobile;



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


}
