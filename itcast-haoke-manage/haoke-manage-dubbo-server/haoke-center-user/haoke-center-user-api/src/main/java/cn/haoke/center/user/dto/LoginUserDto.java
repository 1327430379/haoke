package cn.haoke.center.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto implements Serializable {

    private String token;

    private Long userId;

    private String name;

    private String role;

    private String mobile;

    private String avatar;
}
