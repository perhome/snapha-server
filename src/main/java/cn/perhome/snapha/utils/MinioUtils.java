package cn.perhome.snapha.utils;

import cn.perhome.snapha.dto.ResponseFileUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import cn.perhome.snapha.config.MinioConfig;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioUtils {

    private final MinioConfig minioConfig;
    private final MinioClient minioClient;

    public void createBucket(String bucketName) throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    public ResponseFileUploadDto uploadFile(MultipartFile file, String bucketName) throws Exception {
        return  uploadFile(file, bucketName, null);
    }

    /**
     * 上传文件
     */
    public ResponseFileUploadDto uploadFile(MultipartFile file, String bucketName, Map tags) throws Exception {
        //判断文件是否为空
        if (null == file || 0 == file.getSize()) {
            return null;
        }
        Date today = new Date();
        //判断存储桶是否存在  不存在则创建
        createBucket(bucketName);
        //文件名
        String originalFilename = file.getOriginalFilename();
        //新的文件名 = 存储桶文件名_时间戳.后缀名
        assert originalFilename != null;
        SimpleDateFormat formatDay;
        if (!StringUtils.hasLength(bucketName)) {
           bucketName = "test";
        }
        if (bucketName.endsWith("daily")) {
            formatDay = new SimpleDateFormat("yyyy/MM/dd/");
        }
        else if (bucketName.endsWith("yearly")) {
            formatDay = new SimpleDateFormat("yyyy/");
        }
        else {
            formatDay = new SimpleDateFormat("yyyy/MM/");

        }
        String fileName = formatDay.format(today) + originalFilename.replace(" ", "_");
        //开始上传
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                                file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());

        if (tags != null) {
            minioClient.setObjectTags(
                    SetObjectTagsArgs.builder().bucket(bucketName).object(fileName).tags(tags).build());
        }
        String urlHost = minioConfig.getEndpoint() + "/" + bucketName + "/" + fileName;
        String urlHttp = minioConfig.getHost() + "/" + bucketName + "/" + fileName;
//        log.info("上传文件成功url, urlHttp ：[{}]", urlHttp);
        return new ResponseFileUploadDto(urlHost, urlHttp);
    }

    public ResponseFileUploadDto putFile(String bucketName, String originFileName
            , InputStream stream, long streamSize, String contentType) throws Exception {

        Date today = new Date();
        //判断存储桶是否存在  不存在则创建
        createBucket(bucketName);

        SimpleDateFormat formatDay;
        if (!StringUtils.hasLength(bucketName)) {
            bucketName = "test";
        }
        if (bucketName.endsWith("daily")) {
            formatDay = new SimpleDateFormat("yyyy/MM/dd/");
        }
        else if (bucketName.endsWith("yearly")) {
            formatDay = new SimpleDateFormat("yyyy/");
        }
        else {
            formatDay = new SimpleDateFormat("yyyy/MM/");
        }
        String fileName = formatDay.format(today) + originFileName;
        //开始上传
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                                stream, streamSize, -1)
                        .contentType(contentType)
                        .build());

        String urlHost = minioConfig.getEndpoint() + "/" + bucketName + "/" + fileName;
        String urlHttp = minioConfig.getHost() + "/" + bucketName + "/" + fileName;
        return new ResponseFileUploadDto(urlHost, urlHttp);
    }

    /**
     * 获取全部bucket
     *
     * @return
     */
    public List<Bucket> getAllBuckets() throws Exception {
        return minioClient.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    public Optional<Bucket> getBucket(String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, InternalException, ErrorResponseException, ServerException, XmlParserException {
        return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    public void removeBucket(String bucketName) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取⽂件外链
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    public String getObjectURL(String bucketName, String objectName, Integer expires) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).expiry(expires).build());
    }

    /**
     * 获取⽂件
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @return ⼆进制流
     */
    public InputStream getObject(String bucketName, String objectName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 上传⽂件
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @param stream     ⽂件流
     * @throws Exception https://docs.minio.io/cn/java-minioClient-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws
            Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, stream.available(), -1).contentType(objectName.substring(objectName.lastIndexOf("."))).build());
    }

    /**
     * 上传⽂件
     *
     * @param bucketName  bucket名称
     * @param objectName  ⽂件名称
     * @param stream      ⽂件流
     * @param size        ⼤⼩
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-minioClient-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream, long
            size, String contextType) throws Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, size, -1).contentType(contextType).build());
    }

    /**
     * 获取⽂件信息
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @throws Exception https://docs.minio.io/cn/java-minioClient-api-reference.html#statObject
     */
    public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
        return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 删除⽂件
     *
     * @param bucketName bucket名称
     * @param objectName ⽂件名称
     * @throws Exception https://docs.minio.io/cn/java-minioClient-apireference.html#removeObject
     */
    public void removeObject(String bucketName, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }
}