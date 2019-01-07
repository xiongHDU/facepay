package com.face.facepay.service;

import com.face.facepay.dao.CommodityDao;
import com.face.facepay.dao.OrderDao;
import com.face.facepay.dao.UserDao;
import com.face.facepay.pojo.Commodity;
import com.face.facepay.pojo.Order;
import com.face.facepay.pojo.PostResponse;
import com.face.facepay.pojo.User;
import com.face.facepay.utils.DateUtils;
import com.face.facepay.utils.FaceRecognitionUtils;
import com.face.facepay.utils.OrderCodeUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

import static com.face.facepay.utils.InputStreamToFIleUtils.inputStreamToFile;

@Service
public class MoneyService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private OrderDao orderDao;

    /**
     * 充钱
     * @param username
     * @param money
     * @return
     */
    @Transactional
    public PostResponse putMoney(String username, double money, HttpServletRequest request){
        PostResponse response = new PostResponse();
        User user = userDao.getUser(username);
        if(user == null){
            response.error(-101);
            return response;
        }
        HttpSession session = request.getSession();
        if(!user.getUsername().equals(session.getAttribute("username"))){
            return response.error(-800);
        }
        if(!userDao.addUserMoneyByUsername(username,money)){
            return response.error(-400);
        }
        return response;
    }

    /**
     * 用户花钱买商品，并生成订单。
     * 传入的commodityCodes是Json格式的字符串
     * @param commodityCodes
     * @param file
     * @return
     * @throws ParseException
     */
    @Transactional
    public PostResponse reduceMoney(String commodityCodes, MultipartFile file) throws ParseException {
        PostResponse response = new PostResponse();
        File f = null;
        if(file.equals("")||file.getSize()<=0){
            return response.error(-600);
        }else{
            InputStream ins = null;
            try {
                ins = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return response.error(-401);
            }
            f=new File(file.getOriginalFilename());
            inputStreamToFile(ins, f);
        }
        //根据图片文件上传到腾讯云，找到用户
        String personId = FaceRecognitionUtils.faceFaceIdentify(f);
        if(personId==null)
            return response.error(-700);
        User user = userDao.getUser(personId);
        //将commodityCodeList去重，转为数量增加
        Map<String,Integer> map = new HashMap<>();
        String[] commodityCodeList = commodityCodes.split("\\|");
        for(String commodityCode:commodityCodeList){
            if(map.containsKey(commodityCode)){
                Integer code = map.get(commodityCode);
                map.put(commodityCode,code+1);
            }
            else map.put(commodityCode,1);
        }
        List<String> commodities = new ArrayList<>();
        for(Map.Entry entry:map.entrySet()){
            commodities.add((String)entry.getKey());
        }
        double totalPrice = 0;
        List<Commodity> commodityList = commodityDao.queryCommodityListByCommodityCode(commodities);
        for(Commodity commodity:commodityList){
            totalPrice += commodity.getCommodityPrice()*map.get(commodity.getCommodityCode());
        }
        //商品总价大于用户余额
        if(totalPrice>user.getBalance())
            return response.error(-102);
        //出现商品不足的情况
        for(Commodity commodity:commodityList){
            if(commodity.getCommodityCount()<map.get(commodity.getCommodityCode())){
                return  response.error(-301);
            }
        }
        for(Commodity commodity:commodityList){
            commodity.setCommodityCount(commodity.getCommodityCount()-map.get(commodity.getCommodityCode()));
            commodityDao.updateCommodityCount(commodity.getCommodityCode(),commodity.getCommodityCount());
            commodity.setCommodityCount(map.get(commodity.getCommodityCode()));
        }
        //创建订单
        String commoditylist = new Gson().toJson(commodityList);
        //将字符串加上转义
        commoditylist = commoditylist.replaceAll("\"","\\\"");
        System.out.println(commoditylist);
        Order order = new Order();
        order.setCommodityList(commoditylist);
        order.setUserId(user.getUserId());
        order.setTime(DateUtils.dateToString(new Date()));
        order.setOrderCode(OrderCodeUtils.getOrderCode(commoditylist,user.getUsername()));
        order.setTotalPrice(totalPrice);
        orderDao.insertOrder(order);
        //修改用户余额
        userDao.updateUserMoneyByUsername(user.getUsername(),user.getBalance()-totalPrice);
        return response;
    }
}
