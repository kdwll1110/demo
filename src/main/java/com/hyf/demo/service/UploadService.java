package com.hyf.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String uploadImages(MultipartFile file);
}
