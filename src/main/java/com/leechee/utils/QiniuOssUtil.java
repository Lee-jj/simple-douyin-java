package com.leechee.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QiniuOssUtil {
    
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String domain;

    /**
     * 文件上传
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {

        // 文件的外链地址
        StringBuffer fileUrl = new StringBuffer("http://");
        fileUrl.append(domain);

        // 配置地区，自动选择
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;

        // 生成上传凭证
        UploadManager uploadManager = new UploadManager(cfg);

        // 指定上传文件的名称
        String key = objectName;

        // 创建凭证
        Auth auth = Auth.create(accessKey, secretKey);
        // 上传凭证
        String upToken = auth.uploadToken(bucketName);

        try {
            Response response = uploadManager.put(bytes, key, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            fileUrl.append('/');
            fileUrl.append(putRet.key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }

        // log.info("文件上传到：{}", fileUrl.toString());
        return fileUrl.toString();
    }
}
