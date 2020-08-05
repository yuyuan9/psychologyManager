package com.ht.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.collection.CollectionDown;
import com.ht.entity.biz.library.Library;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
public interface CollectionDownMapper extends BaseMapper<CollectionDown> {

    List<PageData> findcolletionbyUserId(MyPage page);
}
