package com.zxj.mall.thirdparty;


import com.aliyun.oss.OSSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallThirdPartyApplicationTests {

    @Autowired
    private OSSClient ossClient;

    @Test
    public void testUpload() throws Exception {
        String bucketName = "zxj-bucket-mall";
        String objectName = "Irelia.jpg";
        InputStream inputStream = Files.newInputStream(Paths.get("D:\\Code\\Mall\\mall-third-party\\src\\main\\resources\\static\\Irelia.jpg"));
        ossClient.putObject(bucketName, objectName, inputStream);
        ossClient.shutdown();
        System.out.println("upload success");

    }

}
