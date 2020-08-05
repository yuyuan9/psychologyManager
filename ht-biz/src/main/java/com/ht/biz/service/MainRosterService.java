package com.ht.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.MainRoster;

import java.util.List;


public interface MainRosterService extends IService<MainRoster> {


    List<PageData> sysfindallmainroster(MyPage page);
}
