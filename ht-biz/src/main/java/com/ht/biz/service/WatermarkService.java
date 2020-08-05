package com.ht.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.Watermark;


import java.util.List;

public interface WatermarkService extends IService<Watermark> {
    List<PageData> sysfindByPage(MyPage page);
}
