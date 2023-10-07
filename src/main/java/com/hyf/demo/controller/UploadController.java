package com.hyf.demo.controller;

import com.hyf.demo.constant.CommonConstant;
import com.hyf.demo.service.IUploadService;
import com.hyf.demo.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/upload")
@Slf4j
@Api(value = "文件接口",tags = "提供了操作文件相关的API")
public class UploadController {

    @Resource
    private IUploadService IUploadService;

    @PostMapping("images")
    @ApiOperation("图片上传OSS")
    public Result uploadImagesToOSS(@RequestBody MultipartFile file){
        return Result.success(CommonConstant.OPERATE_SUCCESS, IUploadService.uploadImagesToOSS(file));
    }

    @PostMapping("uploadImagesToLocal")
    @ApiOperation("图片上传到本地")
    public Result uploadImagesToLocal(@RequestBody MultipartFile file, @RequestHeader("authorization") String token){
        return Result.success(CommonConstant.OPERATE_SUCCESS, IUploadService.uploadImagesToLocal(file,token));
    }


}
