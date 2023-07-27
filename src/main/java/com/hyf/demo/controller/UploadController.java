package com.hyf.demo.controller;

import com.hyf.demo.constant.CommonConstant;
import com.hyf.demo.service.UploadService;
import com.hyf.demo.util.Result;
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
    private UploadService uploadService;

    @PostMapping("images")
    @ApiOperation("图片上传")
    public Result upload(@RequestBody MultipartFile file){
        return Result.success(CommonConstant.OPERATE_SUCCESS,uploadService.uploadImages(file));
    }

}
