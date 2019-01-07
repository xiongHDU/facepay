package com.face.facepay.utils;

import com.face.facepay.pojo.User;
import com.qcloud.image.ImageClient;

import com.qcloud.image.request.FaceGetGroupIdsRequest;
import com.qcloud.image.request.FaceIdentifyRequest;
import com.qcloud.image.request.FaceNewPersonRequest;
import com.qcloud.image.request.FaceVerifyRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class FaceRecognitionUtils {
    private final static String appId = "1256541087";
    private final static String secretId = "AKIDofOwKXOZO5DE4jkWhrtmU1QehZHo7hst";
    private final static String secretKey = "zy9FAxb8cw68yc9WinkYBdv2yjJiw55v";
    private final static String bucketName = "test01";
    private static ImageClient imageClient = new ImageClient(appId, secretId, secretKey,
            ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com);

    /**
     * 查询所有group,测试连接使用
     *
     */
    public static void faceGetGroupId() {
        String ret;
        System.out.println("====================================================");
        FaceGetGroupIdsRequest getGroupReq = new FaceGetGroupIdsRequest(bucketName);
        ret = imageClient.faceGetGroupIds(getGroupReq);
        System.out.println("face get group ids  ret:" + ret);
    }

    /**
     * 验证人脸，正确返回人脸ID,错误返回null;
     * @param faceImageFile
     * @return
     */
    public  static String faceFaceIdentify(File faceImageFile) {
        String ret;
        String groupId = "group1";
        FaceIdentifyRequest faceIdentifyReq2 = new FaceIdentifyRequest(bucketName, groupId, faceImageFile);
        ret = imageClient.faceIdentify(faceIdentifyReq2);
        System.out.println(ret);
        JSONObject myJson = new JSONObject(ret);
        //System.out.println(myJson.get("data"));
        JSONObject  data = new JSONObject(myJson.get("data").toString());
        //System.out.println(data);
        JSONArray candidates=new JSONArray(data.get("candidates").toString());

        String faceVerifyPersonId = candidates.getJSONObject(0).getString("person_id");
        //System.out.println("====================================================");
       // System.out.println(faceVerifyPersonId);
        String faceVerifyName = faceImageFile.getName();
        File faceVerifyImage = faceImageFile;
        FaceVerifyRequest faceVerifyReq = new FaceVerifyRequest(bucketName, faceVerifyPersonId, faceVerifyName, faceVerifyImage);
        ret = imageClient.faceVerify(faceVerifyReq);
        JSONObject myJson1 = new JSONObject(ret);
        //System.out.println(myJson.get("data"));
        JSONObject  data1 = new JSONObject(myJson1.get("data").toString());
        //System.out.println(data);
        if(data1.getBoolean("ismatch"))
            return faceVerifyPersonId;
        else return null;

    }

    /**
     * 新增人脸
     * @param user
     * @param personNewImage
     * @return
     */
    public static boolean faceNewPerson(User user,File personNewImage ) {
        String ret;
        FaceNewPersonRequest personNewReq;
        String[] groupIds = new String[2];
        groupIds[0] = "group0";
        groupIds[1] = "group1";
        String personName = user.getUsername();
        String personId = user.getUsername();
        String personTag = "I'm " + user.getUsername();
        //System.out.println("====================================================");
        personNewReq = new FaceNewPersonRequest(bucketName, personId, groupIds, personNewImage, personName, personTag);
        ret = imageClient.faceNewPerson(personNewReq);
        System.out.println("person new ret:" + ret);
        JSONObject myJson1 = new JSONObject(ret);
        int code = myJson1.getInt("code");
        if (code != 0)
            return false;
        else return true;
    }

}
