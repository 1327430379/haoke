package cn.haoke.mgmt.controller;

import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.dto.UploadFileDto;
import cn.haoke.mgmt.service.PicUploadFileSystemService;
import cn.haoke.mgmt.vo.PicUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("pic/upload")
@Controller
public class PicUploadController extends AbstractBaseController {

    @Autowired
    private PicUploadFileSystemService picUploadService;
    //private PicUploadService picUploadService;

//    @Autowired
//    private PicUploadFileSystemService picUploadService;

    @PostMapping
    @ResponseBody
    public PicUploadResult upload(@RequestParam("file") MultipartFile multipartFile) {
        return this.picUploadService.upload(multipartFile);
    }
    @PostMapping("/batch")
    @ResponseBody
    public PicUploadResult upload(@RequestParam("uploadFiles") List<UploadFileDto> fileList){

        System.out.println("批量上传文件！");
        System.out.println(fileList.size());
        return null;
    }
}
