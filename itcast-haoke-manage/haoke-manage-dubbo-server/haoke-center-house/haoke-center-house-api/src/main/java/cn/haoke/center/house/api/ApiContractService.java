package cn.haoke.center.house.api;

import cn.haoke.center.house.dto.ContractReqDto;
import cn.haoke.center.house.pojo.ContractEo;
import cn.haoke.common.vo.RestResponse;
import com.github.pagehelper.PageInfo;

public interface ApiContractService {

    /***
     * 分页查询
     * @param dto
     * @return
     */
    PageInfo<ContractEo> listByPage(ContractReqDto dto);
}
