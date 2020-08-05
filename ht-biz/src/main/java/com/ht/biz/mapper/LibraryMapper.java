package com.ht.biz.mapper;



import java.util.List;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ht.commons.support.geelink.entity.Librarygelink;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.library.Library;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
public interface LibraryMapper extends BaseMapper<Library> {
      PageData findlibrarybyid(String id);
      int updatereview(PageData pd);
      List<Library> findall();
      List<PageData> findByPage(MyPage page);
      List<PageData> readlistPage(MyPage mypage);
      int  maxsort();
      int getdownfilehoney(List<String> id);

      //后台查询
      List<PageData> sysfindByPage(MyPage page);

      List<PageData> getlistByids(MyPage page);

      List<PageData> promotelibrary(MyPage page);

      List<Library> findbytitle(PageData pd);

    List<PageData> sysfindByPagenopass(MyPage page);
	Library findbyId(String id);

    List<PageData> myload(MyPage page);

    Integer countcole(String userId);

    List<Librarygelink> geelinkfindall();
}
