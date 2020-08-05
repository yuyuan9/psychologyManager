package com.ht.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.collection.CollectionDown;
import com.ht.entity.shiro.usertype.ServiceProvider;

import java.util.List;


/**
 * <p>
 * 收藏下载
 * </p>
 *
 * @author jied
 */

public interface CollectionDownService extends IService<CollectionDown> {

    /**
     * 查询是否收藏
     * @param userId
     * @param targetId
     * @return
     */
    CollectionDown getColletionByCreateIdAndTagerId(String userId, String targetId,int type);

    /**
     * 我的收藏
     * @param page
     * @return
     */
    List<PageData> findcolletionbyUserId(MyPage page);

    /**
     * 我的下载
     * @param page
     * @return
     */
    List<PageData> findMydownByUserId(MyPage page);
}

