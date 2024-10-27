package com.backend.proyectointegradorc1g6.service;

import com.backend.proyectointegradorc1g6.entity.Asset;
import com.backend.proyectointegradorc1g6.exception.IssuePutObjectException;
import org.springframework.web.multipart.MultipartFile;

public interface IS3Service {
    String putObject(MultipartFile multipartFile) throws IssuePutObjectException;
    Asset getObject(String key);
    void deleteObject(String key);
    String getObjectUrl(String key);
}
