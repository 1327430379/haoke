package cn.haoke.mgmt.controller;

import cn.haoke.center.house.dto.HouseResourceReqDto;
import cn.haoke.common.constants.CommonConstant;
import cn.haoke.common.constants.enums.ExceptionCode;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.HouseResourcesService;
import cn.haoke.mgmt.service.SearchService;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import cn.haoke.mgmt.vo.HouseData;
import cn.haoke.mgmt.vo.HouseResourcesVo;
import cn.haoke.mgmt.vo.Pagination;
import cn.haoke.mgmt.vo.TableResult;
import cn.haoke.center.house.pojo.HouseResources;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("house/resources")
public class HouseResourcesController extends AbstractBaseController {

    @Autowired
    private HouseResourcesService houseResourcesService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 新增房源
     *
     * @param houseResources json数据
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody HouseResources houseResources) {
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getCurrentLoginInfo();
        if(loginInfo==null){
            houseResources.setHouseOwnerId(CommonConstant.DEFAULT_USER_ID);
        }else{
            houseResources.setHouseOwnerId(loginInfo.getUserId());
        }

        try {
            boolean bool = this.houseResourcesService.save(houseResources);
            if (bool) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 查询房源列表
     *
     * @param dto
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    public RestResponse<TableResult> list(HouseResourceReqDto dto,
                                          @RequestParam(name = "currentPage",required = false,
                                                    defaultValue = "1") Integer currentPage,
                                          @RequestParam(name = "pageSize",required = false,
                                                    defaultValue = "10") Integer pageSize) {
        if(dto==null){
            dto = new HouseResourceReqDto();
        }
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if(null ==loginInfo){
            //throw new BusinessException(CommonConstant.LOGIN_SESSION_DISABLES);
            dto.setHouseOwnerId(CommonConstant.DEFAULT_USER_ID);
        }else{
            dto.setHouseOwnerId(loginInfo.getUserId());
        }
       // houseResources.setHouseOwnerId(loginInfo.getUserId());
        PageInfo<HouseResources> pageInfo = houseResourcesService.queryList(dto, currentPage, pageSize);
        Pagination pagination = new Pagination(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal());
        return new RestResponse<>(new TableResult<>(pageInfo.getList(),pagination));
    }

    /**
     * 修改房源
     * @param houseResources
     * @return
     */
    @PutMapping
    public RestResponse<Void> update(@RequestBody HouseResources houseResources) {

        return houseResourcesService.update(houseResources);
    }

    @GetMapping("/{id}")
    public RestResponse<HouseResourcesVo> queryById(@PathVariable String id) {
        return new RestResponse<>(houseResourcesService.queryHouseResourcesById(id));
    }

    @GetMapping("/queryAll")
    public RestResponse<List<HouseData>> queryAll() {
        QueryBuilder queryBuilder = new MatchAllQueryBuilder();
        SearchQuery searchQuery = new NativeSearchQuery(queryBuilder);
        PageRequest pageRequest = new PageRequest(0, 3000);

        searchQuery.setPageable(pageRequest);
        List<HouseData> list = elasticsearchTemplate.queryForList(searchQuery, HouseData.class);
        Map<String,HouseData> map = new HashMap<>();
        long startTime = System.currentTimeMillis();
        for (HouseData houseData : list) {
            houseData.setId(houseData.getId().toLowerCase());
            if(map.containsKey(houseData.getId()))continue;
            HouseResources resources = new HouseResources();
            resources.setId(houseData.getId());
            resources.setTitle(houseData.getTitle());
            resources.setRent(Integer.parseInt(houseData.getRent()));
            resources.setHouseType(houseData.getHouseType());
            String image = houseData.getImage();
            image = image + ",SH2155780015805956096.jpg";
            resources.setImage(image);

            resources.setOrientation(Integer.parseInt(houseData.getOrientation().substring(0,houseData.getOrientation().length()-1)));
            resources.setFloor(houseData.getFloor());
            resources.setRentMethod(1);
            resources.setHouseOwnerId(10987769291357639L);
            resources.setHouseOwnerName("凌瓒房东");
            resources.setCreateTime(new Date());
            resources.setUpdateTime(new Date());
            resources.setContact("凌瓒");
            resources.setMobile("17398798769");
            resources.setHouseDesc("这是一套精装修的房子");
            resources.setTime(5);
            houseResourcesService.save(resources);
            map.put(houseData.getId(),houseData);

        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);

        return new RestResponse<>(list);
    }

    @GetMapping("/query/rentStatus")
    public RestResponse<Map> queryByRentStatus(){
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if(loginInfo == null){
//            super.error(ExceptionCode.LOGIN_INFO_INVALID.getCode(),ExceptionCode.LOGIN_INFO_INVALID.getMsg());
            throw new BusinessException(ExceptionCode.LOGIN_INFO_INVALID.getMsg());
        }
        HouseResources resources = new HouseResources();
        resources.setHouseOwnerId(loginInfo.getUserId());
        return new RestResponse<>(houseResourcesService.queryByRentStatus(resources));
    }
    @DeleteMapping("/{id}")
    public RestResponse<Void> deleteById(@PathVariable String id){
        return houseResourcesService.deleteById(id);
    }
}
