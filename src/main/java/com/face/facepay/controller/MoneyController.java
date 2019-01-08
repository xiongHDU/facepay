package com.face.facepay.controller;

import com.face.facepay.pojo.PostResponse;
import com.face.facepay.pojo.User;
import com.face.facepay.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;


@RestController
@RequestMapping("facepay/money")
public class MoneyController {
    @Autowired
    private MoneyService moneyService;

    @RequestMapping("put")
    public PostResponse putMoney(@RequestBody User user, HttpServletRequest request){
        return moneyService.putMoney(user.getUsername(),user.getBalance(),request);
    }

    @RequestMapping("reduce")
    public PostResponse buyCommodityReduceMoney(@RequestParam("commodityCodeList") String commodityCodeList,
                                                @RequestParam(value = "face",required = false) MultipartFile file) throws ParseException, UnsupportedEncodingException {
        return moneyService.reduceMoney(commodityCodeList,file);
    }
}
