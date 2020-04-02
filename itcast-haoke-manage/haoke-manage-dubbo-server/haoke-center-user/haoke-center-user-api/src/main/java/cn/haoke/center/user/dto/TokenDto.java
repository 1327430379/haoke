package cn.haoke.center.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto implements Serializable {


    private Long userId;
    //用户id_token的形式
    private String token;



}
