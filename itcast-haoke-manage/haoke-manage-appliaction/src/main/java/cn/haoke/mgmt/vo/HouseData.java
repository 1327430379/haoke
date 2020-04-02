package cn.haoke.mgmt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "haoke", type = "house", createIndex = false)
public class HouseData implements Serializable {
    @Id
    private String id;
    /***
     * 标题
     */
    private String title;
    /***
     * 租金
     */
    private String rent;
    /***
     * 楼层
     */
    private String floor;

    /***
     * 图片
     */
    private String image;

    /***
     *房间大小
     */
    private String orientation;

    /***
     *房屋类型
     */
    private String houseType;

    /***
     * 租赁方式
     */
    private String rentMethod;

    /***
     * 看房时间
     */
    private String time;
}