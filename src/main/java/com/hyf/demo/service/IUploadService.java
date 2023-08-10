package com.hyf.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
    String uploadImages(MultipartFile file);
}
