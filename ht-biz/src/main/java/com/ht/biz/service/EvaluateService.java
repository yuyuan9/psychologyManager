package com.ht.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.comment.EvaluateWeb;
import com.ht.entity.biz.comment.EvaluateWebVO;
import com.ht.entity.biz.file.TUploadFile;

import java.util.List;


public interface EvaluateService extends IService<EvaluateWebVO> {
    List<EvaluateWeb> findEvaluatByTargetId(MyPage page) throws Exception;

    List<EvaluateWeb> sysfindall(MyPage page);
    List<PageData> getgroup();

    boolean updatebyId(PageData pd);
}
