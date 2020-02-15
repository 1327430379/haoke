package cn.itcast.haoke.dubbo.server.pojo;

import lombok.Data;

import java.util.Date;

@Data
public abstract class BasePojo implements java.io.Serializable {

    private Date created;
    private Date updated;

}