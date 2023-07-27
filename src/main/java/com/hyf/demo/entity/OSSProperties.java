package com.hyf.demo.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
public class OSSProperties {
    @Value(value = "${aliyun.oss.file.bucketname}")
    private String bucketname;
    @Value(value = "${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value(value = "${aliyun.oss.file.keyid}")
    private String keyid;
    @Value(value = "${aliyun.oss.file.keysecret}")
    private String keysecret;



}
