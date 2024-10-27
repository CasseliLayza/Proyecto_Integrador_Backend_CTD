package com.backend.proyectointegradorc1g6.service.imp;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.backend.proyectointegradorc1g6.entity.Asset;
import com.backend.proyectointegradorc1g6.exception.IssuePutObjectException;
import com.backend.proyectointegradorc1g6.service.IS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service implements IS3Service {
    private final String BUCKET;
    private final AmazonS3 amazonS3;


    @Autowired
    public S3Service(@Value("${aws.s3.bucket-name}") String bucket, AmazonS3 amazonS3) {
        this.BUCKET = bucket;
        this.amazonS3 = amazonS3;
    }


    public String putObject(MultipartFile multipartFile) throws IssuePutObjectException {
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String contentType = multipartFile.getContentType();
        if (!isImageContentType(contentType) || !isValidImageExtension(extension)) {
            throw new IllegalArgumentException("Formato de archivo no permitido. Solo se permiten JPG, JPEG y PNG.");
        }
        String key = String.format("%s.%s", UUID.randomUUID(), extension);


        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        //System.out.println("Content-Type: " + multipartFile.getContentType());


        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, key, multipartFile.getInputStream(), objectMetadata);
            amazonS3.putObject(putObjectRequest);
            //return key;
            return amazonS3.getUrl(BUCKET, key).toString();
        } catch (Exception e) {
            throw new IssuePutObjectException("Error al subir imagen");
        }
    }

    private boolean isImageContentType(String contentType) {
        return "image/jpeg".equals(contentType) || "image/png".equals(contentType) || "image/jpg".equals(contentType);
    }

    private boolean isValidImageExtension(String extension) {
        return "jpg".equalsIgnoreCase(extension) ||
                "jpeg".equalsIgnoreCase(extension) ||
                "png".equalsIgnoreCase(extension);
    }

    public Asset getObject(String key) {
        S3Object s3Object = amazonS3.getObject(BUCKET, key);
        ObjectMetadata objectMetadata = s3Object.getObjectMetadata();

        try {
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return new Asset(bytes, objectMetadata.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteObject(String key) {
        assert false;
        amazonS3.deleteObject(BUCKET, key);
    }

    public String getObjectUrl(String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", BUCKET, key);
    }


}
