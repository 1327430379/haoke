package cn.haoke.center.user.constants;

public class UserConstant {

    //登录用户存入缓存key的前缀
    public static final String LOGIN_USER_CACHE_PRE = "login_cache_user_";

    public static final String USER_TOKEN_PRE = "user_token_";

    public static final Integer USER_TOKEN_EXPIRE_TIME = 30*60;


    /***
     * 启用与禁用
     */
    public static final Integer ENABLED = 1;
    public static final Integer DISABLED = 0;
}
