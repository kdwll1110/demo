package com.hyf.demo.service;

import com.hyf.demo.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
    String uploadImagesToOSS(MultipartFile file);

    Result uploadImagesToLocal(MultipartFile file,String token);
}
