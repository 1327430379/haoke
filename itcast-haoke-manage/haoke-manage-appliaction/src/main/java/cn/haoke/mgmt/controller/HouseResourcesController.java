package cn.haoke.mgmt.controller;

import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.HouseResourcesService;
import cn.haoke.mgmt.service.SearchService;
import cn.haoke.mgmt.vo.HouseData;
import cn.haoke.mgmt.vo.TableResult;
import cn.haoke.center.house.pojo.HouseResources;
import org.apache.ibatis.annotations.Update;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("house/resources")
public class HouseResourcesController extends AbstractBaseController {

    @Autowired
    private HouseResourcesService houseResourcesService;
    @Autowired
    private SearchService searchService;

    /**
     * 新增房源
     *
     * @param houseResources json数据
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody HouseResources houseResources) {
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
     * @param houseResources
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    public ResponseEntity<TableResult> list(HouseResources houseResources,
                                            @RequestParam(name = "currentPage",
                                                    defaultValue = "1") Integer currentPage,
                                            @RequestParam(name = "pageSize",
                                                    defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(this.houseResourcesService.queryList(houseResources, currentPage,
                pageSize));
    }

    /**
     * 修改房源
     * @param houseResources
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody HouseResources houseResources) {
        try {
            boolean bool = this.houseResourcesService.update(houseResources);
            if(bool){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {

        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @GetMapping("/{id}")
    public RestResponse<HouseData> queryById(@PathVariable String id){
        return searchService.queryById(id);
    }

}