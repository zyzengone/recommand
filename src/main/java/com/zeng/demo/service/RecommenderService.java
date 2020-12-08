package com.zeng.demo.service;

import net.librec.conf.Configuration;
import net.librec.data.DataModel;
import net.librec.data.model.TextDataModel;
import net.librec.eval.RecommenderEvaluator;
import net.librec.eval.rating.MAEEvaluator;
import net.librec.eval.rating.RMSEEvaluator;
import net.librec.filter.GenericRecommendedFilter;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.cf.ItemKNNRecommender;
import net.librec.recommender.cf.UserKNNRecommender;
import net.librec.recommender.item.RecommendedItem;
import net.librec.similarity.PCCSimilarity;
import net.librec.similarity.RecommenderSimilarity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RecommenderService
 * @Description: TODO
 * @author: yourname
 * @date: 2020年12月08日 下午6:57
 */
@Service
public class RecommenderService {
    /**根据配置文件读取数据
     * @param userId 用户id
     * @param itemId 商品id
     * @return
     * @throws Exception
     */
    public static List<RecommendedItem> getItemListFromResource(String userId, String itemId) throws Exception {

        // recommender configuration
        Configuration conf = new Configuration();
        Configuration.Resource resource = new Configuration.Resource("rec/cf/userknn-test.properties");
        conf.addResource(resource);

        // build data model
        DataModel dataModel = new TextDataModel(conf);
        dataModel.buildDataModel();

        // set recommendation context
        RecommenderContext context = new RecommenderContext(conf, dataModel);
        RecommenderSimilarity similarity = new PCCSimilarity();
        similarity.buildSimilarityMatrix(dataModel);
        context.setSimilarity(similarity);

        // training
        Recommender recommender = new UserKNNRecommender();
        recommender.recommend(context);

        // evaluation
        RecommenderEvaluator evaluator = new MAEEvaluator();
        recommender.evaluate(evaluator);

        // recommendation results
        List<RecommendedItem> recommendedItemList = recommender.getRecommendedList();
        GenericRecommendedFilter filter = new GenericRecommendedFilter();

        if(userId!=null){
            List<String> userIdList = new ArrayList<>();
            userIdList.add(userId);
            filter.setUserIdList(userIdList);
        }
        if(itemId != null){
            List<String> itemIdList = new ArrayList<>();
            itemIdList.add(itemId);
            filter.setItemIdList(itemIdList);
        }

        recommendedItemList = filter.filter(recommendedItemList);
        return recommendedItemList;
    }

    /**从文件查询推荐列表
     * @param userId 用户id
     * @param itemId 商品id
     * @return
     * @throws Exception
     */
    public static List<RecommendedItem> getItemListFromText(String userId,String itemId) throws Exception {

        // build data model
        Configuration conf = new Configuration();
        conf.set("dfs.data.dir", "C:\\Users\\zyzengone\\project\\springboot-librec-master\\data");
//          conf.set("data.input.path", "movielens/ml-100k");
        TextDataModel dataModel = new TextDataModel(conf);
        dataModel.buildDataModel();

        // build recommender context
        RecommenderContext context = new RecommenderContext(conf, dataModel);

        // build similarity
        conf.set("rec.recommender.similarity.key" ,"item");
        RecommenderSimilarity similarity = new PCCSimilarity();
        similarity.buildSimilarityMatrix(dataModel);
        context.setSimilarity(similarity);

        // build recommender
        conf.set("rec.neighbors.knn.number", "5");
        Recommender recommender = new ItemKNNRecommender();
        recommender.setContext(context);

        // run recommender algorithm
        recommender.recommend(context);

        // evaluate the recommended result
        RecommenderEvaluator evaluator = new RMSEEvaluator();
        System.out.println("RMSE:" + recommender.evaluate(evaluator));

        // filter the recommended result
        List<RecommendedItem> recommendedItemList = recommender.getRecommendedList();
        GenericRecommendedFilter filter = new GenericRecommendedFilter();

        if(userId!=null){
            List<String> userIdList = new ArrayList<>();
            userIdList.add(userId);
            filter.setUserIdList(userIdList);
        }
        if(itemId != null){
            List<String> itemIdList = new ArrayList<>();
            itemIdList.add(itemId);
            filter.setItemIdList(itemIdList);
        }
        recommendedItemList = filter.filter(recommendedItemList);

        return recommendedItemList;
    }
}
