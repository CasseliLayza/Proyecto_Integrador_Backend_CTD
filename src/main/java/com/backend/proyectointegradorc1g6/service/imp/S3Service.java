package com.backend.proyectointegradorc1g6.service.imp;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.backend.proyectointegradorc1g6.entity.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {
    private final static String BUCKET = "casse4sevenmediaresource";

    @Autowired
    //private AmazonS3Client amazonS3Client;
    private final AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();

    public String putObject(MultipartFile multipartFile) {
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String contentType = multipartFile.getContentType();
        if (!isImageContentType(contentType) || !isValidImageExtension(extension)) {
            throw new IllegalArgumentException("Formato de archivo no permitido. Solo se permiten JPG, JPEG y PNG.");
        }
        String key = String.format("%s.%s", UUID.randomUUID(), extension);


        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        //objectMetadata.setContentLength(multipartFile.getSize()); //
        System.out.println("Content-Type: " + multipartFile.getContentType());


        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, key, multipartFile.getInputStream(), objectMetadata);
            amazonS3Client.putObject(putObjectRequest);
            //return key;
            return amazonS3Client.getUrl(BUCKET, key).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isImageContentType(String contentType) {
        return "image/jpeg".equals(contentType) || "image/png".equals(contentType);
    }

    private boolean isValidImageExtension(String extension) {
        return "jpg".equalsIgnoreCase(extension) ||
                "jpeg".equalsIgnoreCase(extension) ||
                "png".equalsIgnoreCase(extension);
    }

    public Asset getObject(String key) {
        S3Object s3Object = amazonS3Client.getObject(BUCKET, key);
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
        amazonS3Client.deleteObject(BUCKET, key);
    }

    public String getObjectUrl(String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", BUCKET, key);
    }


}
