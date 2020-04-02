package cn.haoke.mgmt.controller;

import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.dto.Menu;
import cn.haoke.mgmt.util.JsonUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("menu")
public class MenuController extends AbstractBaseController {


    @GetMapping("/getMenuData")
    public RestResponse getMenuData() throws IOException {
        Object json = JsonUtils.readJsonFromClassPath("menu.json", Map.class);
        return new RestResponse(json);
    }

    public static void main(String[] args) {
        String msg = "13323";
        String[] strings = msg.split(",");
        System.out.println(strings.length);
    }
}
