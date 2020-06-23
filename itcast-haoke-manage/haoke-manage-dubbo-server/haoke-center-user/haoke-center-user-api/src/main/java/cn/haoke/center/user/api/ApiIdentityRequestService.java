package cn.haoke.center.user.api;

import cn.haoke.center.user.pojo.IdentityRequestEo;
import com.github.pagehelper.PageInfo;

public interface ApiIdentityRequestService {

    Long addIdentityRequest(IdentityRequestEo eo);

     PageInfo<IdentityRequestEo> queryByPage(Integer pageNum, Integer pageSize, IdentityRequestEo eo);

     void updateStatus(Long id,Integer identity);

     IdentityRequestEo queryById(Long id);



}
