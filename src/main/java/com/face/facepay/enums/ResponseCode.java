package com.face.facepay.enums;

public enum ResponseCode {
    SUCESS(0,"sucess"),
    LOGINFAIL(-100,"用户名或密码错误"),
    NULLUSER(-101,"查无该用户"),
    NOTENOUGHMONEY(-102,"您的余额不足"),
    AUTHORIZEFAIL(-103,"权限认证失败，您没有该权限"),
    NULLUSERS(-104,"用户列表为空"),
    NULLORDER(-200,"您的订单暂时为空"),
    NULLCOMMODITY(-300,"暂时没有商品"),
    NOTENOUGHCOMMODITY(-301,"商品数量不足"),
    ADDERROE(-400,"系统错误，添加失败"),
    FILEREADFAIL(-401,"系统错误，文件转换失败"),
    ADDFACEFAIL(-402,"系统错误，人脸添加失败"),
    CODEFAIL(-403,"系统错误，编码生成失败"),
    MD5ERROR(-500,"MD5加密时出现异常"),
    FILEUPLOADFAIL(-600,"图片上传失败"),
    FACEIDENTIFY(-700,"人脸验证失败"),
    NOTLOGIN(-800,"用户未登录，请先登录")
    ;
    private final String message;
    private final int code;

    ResponseCode( int code,String message) {
        this.message = message;
        this.code = code;
    }
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
    public static ResponseCode valueOf(Integer value){
        if (value == null) {
            return null;
        }
        for (ResponseCode type : ResponseCode.values()) {
            if (value.equals(type.getCode())) {
                return type;
            }
            continue;
        }
        return null;//错误的value
    }



}
