package com.ht.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.keywork.Keywork;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
public interface KeyworkMapper extends BaseMapper<Keywork> {

    MyPage<PageData> syslistall(MyPage page);

    List<Keywork> findList();


    PageData getfindbyId(String id);
}
