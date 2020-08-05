package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honeymanager.UserWaterReward;

public interface UserWaterRewardMapper  extends BaseMapper<UserWaterReward> {
    Double getWaterTotal(String userId);
    List<PageData> findList(MyPage page);
    Integer findUserIdByDayCount(PageData pd);
    public List<PageData> selectRewrd(MyPage page);
}
