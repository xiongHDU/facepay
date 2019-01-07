package com.face.facepay;

import com.face.facepay.pojo.User;
import com.face.facepay.utils.FaceRecognitionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacepayApplicationTests {

    @Test
    public void contextLoads() {
    }
    @Test
    public void test01(){
        FaceRecognitionUtils.faceGetGroupId();
    }
    @Test
    public void test02(){
        User user = new User();
        user.setUsername("xxx");
        //user.setPassword(MD5Utils.md5("123456","facepay001"));
        File f = new File("G:\\ljx.jpg");
        System.out.println(FaceRecognitionUtils.faceNewPerson(user,f));
    }
    @Test
    public void test03(){
//        User user = new User();
//        user.setUsername("xxx");
        //user.setPassword(MD5Utils.md5("123456","facepay001"));
        File f = new File("G:\\ljx.jpg");
        System.out.println(FaceRecognitionUtils.faceFaceIdentify(f));
    }

}

