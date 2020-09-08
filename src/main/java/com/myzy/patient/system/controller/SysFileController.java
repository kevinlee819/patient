package com.myzy.patient.system.controller;

import com.myzy.patient.system.service.SysFileService;
import com.myzy.patient.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author leekejin
 * @since 2020-07-13 09:10:14
 */
//@RestController
//@RequestMapping("sysFile")
//@Api(tags = "文件管理", hidden = true)
public class SysFileController {

    @Resource
    private SysFileService sysFileService;

    @ApiOperation("上传附件")
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public List<String> upload(@RequestParam("files") MultipartFile[] files) {
        return sysFileService.upload(files);
    }

    @ApiOperation("下载模板文件")
    @GetMapping("/downTemplate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateName", value = "模板文件名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "导出文件名", required = true, dataType = "String", paramType = "query"),
    })
    public void downTemplate(String templateName, String fileName, HttpServletResponse response) {
        FileUtil.exportFile(templateName, fileName, response);
    }

}