package cn.haoke.im.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionEo implements Serializable {

    @Id
    private String id;

    /***
     * 用户id
     */
    private Long userId;

    /***
     * 用户姓名
     */

    private String userName;

    /***
     * 对方id
     */
    private Long targetId;

    /***
     * 对方姓名
     */
    private String targetName;

    /***
     * 会话创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /***
     * 会话开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /***
     * 对方头像
     */
    private String avatar;

    /***
     * 对方手机号码
     */
    private String mobile;

    /***
     * 是否关闭：0 未关闭，1已关闭
     */
    private Integer isClose;

    /**
     * 最近 沟通时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastMessageTime;

    /***
     * 会话结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /***
     * 最后一条消息内容
     */
    private String lastMessage;


}
