package com.ht.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.usertype.BserviceProvider;
import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jied
 */

public interface BserprociderService extends IService<BserviceProvider> {

    PageData findById(PageData pd);

    List<PageData> sysfindByPage(MyPage page);
}

