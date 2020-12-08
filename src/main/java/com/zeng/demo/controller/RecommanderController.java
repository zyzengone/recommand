package com.zeng.demo.controller;

import com.zeng.demo.pojo.Result;
import com.zeng.demo.service.RecommenderService;
import com.zeng.demo.util.ResultUtil;
import net.librec.recommender.item.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName: RecommanderController
 * @Description: TODO
 * @author: yourname
 * @date: 2020年12月08日 下午6:58
 */
@RestController
@RequestMapping("/recommender")
public class RecommanderController {
    /**根据用户推荐感兴趣的商品
     * @param response
     * @param userId
     */
    @Autowired
    RecommenderService recommenderService;
    @RequestMapping(value = "/user")
    public Result userBasedRecommender(@Nullable @RequestParam("userId") String userId) {
        List<RecommendedItem> recommendedItemList1 = null;

        try {
            recommendedItemList1 = recommenderService.getItemListFromText(userId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(recommendedItemList1);
    }
}
