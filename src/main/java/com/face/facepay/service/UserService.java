package com.face.facepay.service;

import com.face.facepay.dao.OrderDao;
import com.face.facepay.dao.UserDao;
import com.face.facepay.dto.OrderDto;
import com.face.facepay.pojo.*;
import com.face.facepay.utils.FaceRecognitionUtils;
import com.face.facepay.utils.MD5Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import static com.face.facepay.utils.InputStreamToFIleUtils.inputStreamToFile;


@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    private final String key = "facepay001";
    /**
     * 获取用户订单列表
     * @param username
     * @return
     */
    public GetResponse<List<OrderDto>> getUserOrder(String username,HttpServletRequest request){
        GetResponse<List<OrderDto>> response = new GetResponse<>();
        User user = userDao.getUser(username);
        if(user == null){
            response.error(-101);
            return response;
        }
        HttpSession session = request.getSession();
        if(!user.getUsername().equals(session.getAttribute("username"))){
            return response.error(-800);
        }
        List<OrderDto> orderDtos = new ArrayList<>();
        List<Order> orderList =  orderDao.queryOrderByUsername(username);
        if(orderList==null||orderList.size()==0){
            response.error(-200);
            return response;
        }
        for(Order order: orderList){
            OrderDto orderDto = new OrderDto();
            String commoditylist = new String(order.getCommodityList());
            orderDto.setCommodityList(new Gson().fromJson(commoditylist,new TypeToken<List<Commodity>>(){}.getType()));
            orderDto.setOrderCode(order.getOrderCode());
            orderDto.setOrderId(order.getOrderId());
            orderDto.setUserId(order.getUserId());
            orderDto.setTime(order.getTime());
            orderDto.setTotalPrice(order.getTotalPrice());
            orderDtos.add(orderDto);
        }
        response.setData(orderDtos);
        response.sucess(orderDtos);
        return response;
    }

    /**
     * 查询个人用户信息
     * @param username
     * @return
     */
    public GetResponse<User> getUserMsg(String username,HttpServletRequest request){
        GetResponse<User> response = new GetResponse<>();
        User user = userDao.getUser(username);
        if(user == null){
            response.error(-101);
            return response;
        }
        HttpSession session = request.getSession();
        if(!user.getUsername().equals(session.getAttribute("username"))){
            return response.error(-800);
        }
        response.sucess(user);
        return response;
    }

    /**
     * 登录验证
     * @param username
     * @param password
     * @return
     */
    public PostResponse login(String username, String password, HttpServletRequest request) {
        PostResponse response = new PostResponse();
        String pwd = userDao.getPassword(username);
        if(pwd == null){
            response.error(-101);
            return response;
        }
        if(!MD5Utils.verify(password,key,pwd)){
            response.error(-100);
            return response;
        }
        HttpSession session =  request.getSession();
        session.setAttribute("username",username);
        return response;
    }

    /**
     * 注册
     * @param username
     * @param password
     * @param imageFile
     * @return
     */
    public PostResponse regist(String username, String password, MultipartFile imageFile) {
        PostResponse response = new PostResponse();
        User testUser = userDao.getUser(username);
        if(testUser!=null){
            return response.error(-101);
        }
        User user = new User();
        user.setBalance(0);
        user.setUsername(username);
        password = MD5Utils.md5(password,key);
        user.setPassword(password);
        user.setRoleName("顾客");
        File f = null;
        if(imageFile.equals("")||imageFile.getSize()<=0){
            return response.error(-600);
        }else{
            InputStream ins = null;
            try {
                ins = imageFile.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return response.error(-401);
            }
            f=new File(imageFile.getOriginalFilename());
            inputStreamToFile(ins, f);
        }
        if(FaceRecognitionUtils.faceNewPerson(user,f)){
            userDao.insertUser(user);
            File f2 = new File(f.getAbsolutePath());
            f2.delete();
            return response;
        }
        else {
            File f2 = new File(f.getAbsolutePath());
            f2.delete();
            return response.error(-402);
        }
    }

    /**
     * 获取用户列表 未分页
     * @return
     */
    public GetResponse<List<User>> queryUsers(HttpServletRequest request) {
        GetResponse<List<User>> response = new GetResponse<>();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if(username == null){
            return response.error(-800);
        }
        User user = userDao.getUser(username);
        if(user==null)
            return response.error(-101);

        if(!user.getRoleName().equals("店长")){
            return response.error(-103);
        }
        List<User> userList = userDao.queryUser();
        if(userList ==null||userList.size() == 0){
            return response.error(-104);
        }
        return response.sucess(userList);
    }

    public PostResponse updateUserRole(User user, HttpServletRequest request) {
        PostResponse response =new PostResponse();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if(username == null){
            return response.error(-800);
        }
        User admin = userDao.getUser(username);
        if(admin==null)
            return response.error(-101);

        if(!admin.getRoleName().equals("店长")){
            return response.error(-103);
        }
        userDao.updateUserRoleByUsername(user);
        return response;
    }
}
