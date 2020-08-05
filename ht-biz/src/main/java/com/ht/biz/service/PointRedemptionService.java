package com.ht.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.UserWaterReward;

public interface PointRedemptionService  extends IService<UserWaterReward> {
    Double getWaterTotal(String userId);
    List<PageData> findList(MyPage page);
    Integer findUserIdByDayCount(PageData pd);
    public boolean findByUserId(String userId,String rewardRuleId)throws Exception;
    public boolean findCondition(String userId,String rewardRuleId,String starttime,String endtime)throws Exception;
    public List<PageData> selectRewrd(MyPage page);
}
