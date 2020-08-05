package com.ht.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.EvaluateMapper;
import com.ht.biz.service.EvaluateService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.comment.EvaluateWeb;
import com.ht.entity.biz.comment.EvaluateWebVO;
import com.ht.entity.tree.Tree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EvaluateServiceImpl  extends ServiceImpl<EvaluateMapper, EvaluateWebVO> implements EvaluateService {
    @Override
    public List<EvaluateWeb> findEvaluatByTargetId(MyPage page) throws Exception {
       return  (List<EvaluateWeb>) baseMapper.findEvaluatByTargetId(page);
    }

    @Override
    public List<EvaluateWeb> sysfindall(MyPage page) {
        return baseMapper.sysfindall(page);
    }

    @Override
    public List<PageData> getgroup() {
        return baseMapper.getgroup();
    }

    @Override
    public boolean updatebyId(PageData pd) {
        return baseMapper.updatebyId(pd);
    }

}
