package com.face.facepay.dao;

import com.face.facepay.pojo.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


import java.util.List;
@Component
public interface CommodityDao {
    /**
     * 查询全部商品
     * @return
     */
    List<Commodity> queryCommodityList();

    /**
     * 添加商品
     * @param commodityList
     */
    Boolean addCommodity(List<Commodity> commodityList);

    /**
     * 更新商品数量
     * @param commodityCode
     * @param count
     * @return
     */
    Boolean updateCommodityCount(@Param("commodityCode") String commodityCode, @Param("count") int count);

    /**
     * 获取商品数量
     * @param commodityCode
     * @return
     */
    Integer getCommodityCount(String commodityCode);

    /**
     * 增加商品数量，一切为了性能考虑
     * @param commodityId
     * @param count
     * @return
     */
    Boolean addCommodityCount(@Param("commodityId") long commodityId, @Param("count") int count);

    /**
     * 根据商品名找到商品ID,商品名数据里唯一
     * @param commodityName
     * @return
     */
    Long getCommodityIdByCommodityName(String commodityName);

    /**
     * 根据商品编码查找商品列表
     * @param commodityCodeList
     * @return
     */
    List<Commodity> queryCommodityListByCommodityCode(List<String> commodityCodeList);




}
