package cn.haoke.mgmt.vo;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableResult<T> {

    private List<T> list;
    private Pagination pagination;

}