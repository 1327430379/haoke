package cn.itcast.haoke.dubbo.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    private Integer totalPage;
    private List<HouseData> list;
    private Set<String> hotWord;

}