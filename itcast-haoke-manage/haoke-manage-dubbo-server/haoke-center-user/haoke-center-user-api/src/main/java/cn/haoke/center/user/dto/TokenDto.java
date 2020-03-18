package cn.haoke.center.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {


    private Long userId;
    //用户id_token的形式
    private String token;



}
