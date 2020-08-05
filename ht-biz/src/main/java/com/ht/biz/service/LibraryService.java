package com.ht.biz.service;




import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import com.ht.commons.support.geelink.entity.Librarygelink;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.library.Library;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jied
 */

public interface LibraryService extends IService<Library> {
    //修改审核状态

    boolean updatereview(PageData pd);
    PageData findlibrarybyid(String id);
    List<Library> findall();

    List<PageData> promotelibrary(MyPage page);
    List<PageData> readlistPage(MyPage mypage);

    List<PageData> findByPage(MyPage page);

    Integer maxsort();

    int getdownfilehoney(List<String> id);

    List<Library> findbytitle(PageData pd);
    //后台查询
    List<PageData> sysfindByPage(MyPage page);


    List<PageData> getlistByids(MyPage page);

    List<PageData> sysfindByPagenopass(MyPage page);
	Library findbyId(String id);

    List<PageData> myload(MyPage page);

    Integer countcole(String userId);

    List<Librarygelink> geelinkfindall();

}

