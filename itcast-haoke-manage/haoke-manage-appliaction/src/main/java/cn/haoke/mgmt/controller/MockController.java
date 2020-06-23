package cn.haoke.mgmt.controller;

import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.config.MockConfig;
import cn.haoke.mgmt.vo.WebResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mock")
@CrossOrigin
public class MockController extends AbstractBaseController {


    private final String HK_MK_FAQ = "HK_MK_FAQ";
    private final String HK_MK_MENU = "HK_MK_MENU";
    private final String HK_MK_AD = "HK_MK_AD";
    private final String HK_MK_INFO = "HK_MK_INFO";
    private final String HK_MK_HOUSE = "HK_MK_HOUSE";
    @Autowired
    private MockConfig mockConfig;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     *   * 菜单
     *   *
     *   * @return
     *   
     */
    @GetMapping("index/menu")
    public WebResult indexMenu() throws IOException {
        String json = redisTemplate.opsForValue().get(HK_MK_MENU);
        return handleData(json);
    }

    /**
     *   * 首页资讯
     *   * @return
     *   
     */
    @GetMapping("index/info")
    public WebResult indexInfo() {
        String json = mockConfig.getIndexInfo();
        return handleData(json);
    }

    /**
     *   * 首页问答
     *   * @return
     *   
     */
    @GetMapping("index/faq")
    public WebResult indexFaq() {
        String json = mockConfig.getIndexFaq();
        return handleData(json);
    }

    /**
     *   * 首页房源信息
     *   * @return
     *   
     */

    @GetMapping("index/house")
    public WebResult indexHouse() {
        String json = mockConfig.getIndexHouse();
        return handleData(json);
    }

    /**
     *   * 查询资讯
     *   *
     *   * @param type
     *   * @return
     *   
     */
    @GetMapping("infos/list")
    public WebResult infosList(@RequestParam("type") Integer type) {
        String json = null;
        switch (type) {
            case 1:
                json= this.mockConfig.getInfosList1();break;
            case 2:
                json= this.mockConfig.getInfosList2();break;
            case 3:
                json= this.mockConfig.getInfosList3();break;
        }
        return handleData2(json);
    }

    /**
     *   * 我的中心
     *   * @return
     *   
     */
    @GetMapping("my/info")
    public WebResult myInfo() {
        String json = mockConfig.getMy();
        return handleData(json);
    }

    private WebResult handleData(String json) {
        Map map = null;
        try {
            map = objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map dataMap = (Map) map.get("data");
        Map metaMap = (Map) map.get("meta");
        WebResult webResult = WebResult.ok((List<?>) dataMap.get("list"));
        webResult.setMeta(metaMap);
        return webResult;
    }
    private WebResult handleData2(String json){
        Map map = null;
        try {
            map = objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map dataMap = (Map) map.get("data");
        Map metaMap = (Map) map.get("meta");
        Map listMap = (Map) dataMap.get("list");
        WebResult webResult = WebResult.ok((List<?>) listMap.get("data"));
        webResult.setMeta(metaMap);
        return webResult;
    }
}
