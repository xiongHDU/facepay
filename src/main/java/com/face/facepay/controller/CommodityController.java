package com.face.facepay.controller;

import com.face.facepay.pojo.Commodity;
import com.face.facepay.pojo.GetResponse;
import com.face.facepay.pojo.PostResponse;
import com.face.facepay.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("facepay/commodity")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @GetMapping("list")
    public GetResponse<List<Commodity>> getCommodityList(){
        return commodityService.getCommodityList();
    }

    @RequestMapping("add")
    public PostResponse addCommodityList(@RequestBody List<Commodity> commodityList, HttpServletRequest request){
        return commodityService.addCommodity(commodityList,request);
    }
}
