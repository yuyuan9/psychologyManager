package com.ht.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.comment.EvaluateWeb;
import com.ht.entity.biz.comment.EvaluateWebVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
public interface EvaluateMapper extends BaseMapper<EvaluateWebVO> {

    List<EvaluateWeb> findEvaluatByTargetId(MyPage page);

    List<EvaluateWeb> findByPIds(List<String> pids);

    List<EvaluateWeb> sysfindall(MyPage page);

    List<PageData> getgroup();

    boolean updatebyId(PageData pd);
}
