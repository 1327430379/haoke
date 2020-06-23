package cn.haoke.mgmt.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class AppLoginDto implements Serializable {

    private String username;
    private String password;

}
