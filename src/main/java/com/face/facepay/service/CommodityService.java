package com.face.facepay.service;

import com.face.facepay.dao.CommodityDao;
import com.face.facepay.dao.UserDao;
import com.face.facepay.pojo.Commodity;
import com.face.facepay.pojo.GetResponse;
import com.face.facepay.pojo.PostResponse;
import com.face.facepay.pojo.User;
import com.face.facepay.utils.OrderCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Service
public class CommodityService {
    @Autowired
    private CommodityDao commodityDao;
    @Autowired
    private UserDao userDao;

    /**
     * 获取商品列表
     * @return
     */
    public GetResponse<List<Commodity>> getCommodityList(){
        GetResponse<List<Commodity>> response = new GetResponse<>();
        List<Commodity> commodityList = commodityDao.queryCommodityList();
        if(commodityList == null||commodityList.size()==0){
            return response.error(-300);
        }
        response.sucess(commodityList);
        return response;
    }

    /**
     * 添加商品
     * 但出现商品名重复时，只添加数量
     * @param commodityList
     * @return
     */
    @Transactional
    public PostResponse addCommodity(List<Commodity> commodityList, HttpServletRequest request){
        PostResponse response = new PostResponse();
        HttpSession session = request.getSession();
        String useranme = (String) session.getAttribute("username");
        if(useranme == null){
            return response.error(-800);
        }
        User user = userDao.getUser(useranme);
        if(user==null)
            return response.error(-101);
        if(!user.getRoleName().equals("店员")&&!user.getRoleName().equals("店长")){
            return response.error(-103);
        }
        for(Commodity commodity:commodityList){
            Long id= commodityDao.getCommodityIdByCommodityName(commodity.getCommodityName());
            if(id!=null){
                commodityDao.addCommodityCount(id,commodity.getCommodityCount());
                commodityList.remove(commodity);
            }
            else {
                try {
                    commodity.setCommodityCode(OrderCodeUtils.getOrderCode("commodity",commodity.getCommodityName()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return response.error(-403);

                }
            }
        }
        if(!commodityDao.addCommodity(commodityList))
            return response.error(-400);
        return response;

    }
}
