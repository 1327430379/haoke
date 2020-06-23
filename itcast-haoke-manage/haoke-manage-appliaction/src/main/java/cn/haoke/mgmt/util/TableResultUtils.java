package cn.haoke.mgmt.util;

import cn.haoke.center.house.pojo.HouseResources;
import cn.haoke.mgmt.vo.Pagination;
import cn.haoke.mgmt.vo.TableResult;
import com.github.pagehelper.PageInfo;
import org.springframework.util.CollectionUtils;

public class TableResultUtils {

    public static TableResult pageInfo2TableResult(PageInfo pageInfo){
        if(CollectionUtils.isEmpty(pageInfo.getList())){
            return new TableResult();
        }
        Pagination pagination = new Pagination(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal());
        return new TableResult(pageInfo.getList(),pagination);
    }
}
