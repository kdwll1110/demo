package com.hyf.demo.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpStatus;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.hyf.demo.annotation.OperationType;
import com.hyf.demo.entity.OSSProperties;
import com.hyf.demo.entity.SysUser;
import com.hyf.demo.enums.OperationTypeEnum;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.ISysUserService;
import com.hyf.demo.service.IUploadService;
import com.hyf.demo.util.JwtUtil;
import com.hyf.demo.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Slf4j
public class UploadServiceImpl implements IUploadService {

    @Resource
    private OSSProperties ossProperties;

    @Resource
    private ISysUserService iSysUserService;

    @OperationType(action = OperationTypeEnum.UPLOADING)
    @Override
    public String uploadImagesToOSS(MultipartFile file) {
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

    @Override
    public Result uploadImagesToLocal(MultipartFile file,String token) {
        System.out.println("token = " + token);
        try {
            //解析token获取手机号
            Claims claims = JwtUtil.parseJWT(token);
            String subject = claims.getSubject();
            System.out.println("subject = " + subject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());

        File folder = new File("D:\\server\\upload\\",format);
        //File folder = new File("/opt/upload/"+type,format);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));

        System.out.println("newName = " + newName);

        try {
            file.transferTo(new File(folder,newName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String realPath = "/static/"+format+"/"+newName;

        SysUser user = SecurityUtil.getSysUserDetail().getSysUser();
        //上传到数据库
        iSysUserService.lambdaUpdate().set(SysUser::getAvatar,realPath).eq(SysUser::getId,user.getId()).update();

        //提交事务
        return Result.success("上传成功！",realPath);
    }
}
