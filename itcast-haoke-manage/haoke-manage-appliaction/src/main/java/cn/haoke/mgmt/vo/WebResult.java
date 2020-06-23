package cn.haoke.mgmt.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebResult {
    //对象序列化的时候忽略此字段
    @JsonIgnore
    private Integer status;
    @JsonIgnore
    private String msg;
    @JsonIgnore
    private List<?> list;

    @JsonIgnore
    public static WebResult ok(List<?> list){
        return new WebResult(200,"成功",list);
    }
    @JsonIgnore
    public static WebResult ok(List<?> list,String msg){
        return new WebResult(200,msg,list);
    }

    public Map<String,Object> getData(){
        Map<String,Object> map = new HashMap<>();
        map.put("list",this.list);
        return map;
    }
    public Map<String,Object> getMeta(){
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("msg",msg);
        return map;
    }
    public void setMeta(Map map){
        this.msg = (String) map.get("msg");
        this.status = (Integer) map.get("status");
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
