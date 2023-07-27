package com.hyf.demo.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpStatus;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import com.hyf.demo.annotation.OperationType;
import com.hyf.demo.entity.OSSProperties;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Resource
    private OSSProperties ossProperties;


    @OperationType(action = "文件上传操作")
    @Override
    public String uploadImages(MultipartFile file) {
        log.info("文件名，{}", file.getOriginalFilename());
        log.info("文件大小，{}", file.getSize());


        String filename = file.getOriginalFilename();

        String suffix = filename.substring(filename.lastIndexOf("."));

        String newName = LocalDateTime
                .now()
                .format(DateTimeFormatter.ISO_DATE)
                + "/"
                + UUID.randomUUID()
                + suffix;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder()
                .build(ossProperties.getEndpoint(),
                        ossProperties.getKeyid(),
                        ossProperties.getKeysecret());


        try {

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            ObjectMetadata metadata = new ObjectMetadata();
            //注意，此处设置了格式，但是不生效就离谱。依旧无法通过链接预览，只能访问即下载
            metadata.setContentType("image/jpg");
            metadata.setContentDisposition("inline");


            //上传文件。
            ossClient.putObject(
                    ossProperties.getBucketname()
                    , newName
                    , file.getInputStream()
                    , metadata);

            //得到上传之后的文件地址
            //https://bucketname.endpoint/objectnane  https://upload-demo-liu.oss-cn-hangzhou.aliyuncs.com
            String fileUrl = "https://" + ossProperties.getBucketname()
                    + "." + ossProperties.getEndpoint()
                    + "/" + newName;

            System.out.println("上传之后的文件路径" + fileUrl);

            return fileUrl;

        } catch (Exception e) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR, "上传失败");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

    }
}
