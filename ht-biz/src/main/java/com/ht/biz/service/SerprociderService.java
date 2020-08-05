package com.ht.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.library.Library;
import com.ht.entity.shiro.usertype.ServiceProvider;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jied
 */

public interface SerprociderService extends IService<ServiceProvider> {


    PageData findById(PageData pd);

    List<PageData> findByPage(MyPage page);

    List<PageData> sysfindByPage(MyPage page);
    
    void updateIsblond(PageData pd);
}

