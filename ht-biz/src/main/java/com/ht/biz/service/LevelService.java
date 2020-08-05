package com.ht.biz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.sys.Level;



public interface LevelService  extends IService<Level> {

    Page<Level> findAllList(MyPage page);

    Level findByName(String name);
}
