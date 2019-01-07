package com.face.facepay.controller;

import com.face.facepay.dto.OrderDto;
import com.face.facepay.pojo.GetResponse;
import com.face.facepay.pojo.PostResponse;
import com.face.facepay.pojo.User;
import com.face.facepay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 登录、权限认证简单用session和cookie实现，未用框架
 * 如果想进一步，可以考虑shiro或spring security
 */
@RestController
@RequestMapping("facepay/auth")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("regist")
    public PostResponse regist(@RequestParam(value = "face",required = false) MultipartFile imageFile ,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password){
        return userService.regist(username,password,imageFile);
    }

    @RequestMapping("login")
    public PostResponse login(@RequestBody User user, HttpServletRequest request)  {
        return userService.login(user.getUsername(),user.getPassword(),request);
    }

    @GetMapping(value = "getmsg/{username}")
    public GetResponse<User> getUserMsg(@PathVariable String username,HttpServletRequest request){
        return  userService.getUserMsg(username,request);
    }

    @RequestMapping("getOrder/{username}")
    public GetResponse<List<OrderDto>> getUserOrder(@PathVariable String username,HttpServletRequest request){
        return userService.getUserOrder(username,request);
    }

    @GetMapping("getUsers")
    public GetResponse<List<User>> getUsers(HttpServletRequest request){
        return userService.queryUsers(request);
    }

    @RequestMapping("updateUserRole")
    public PostResponse updateUserRole(@RequestBody User user,HttpServletRequest request){
        return userService.updateUserRole(user,request);
    }


}
