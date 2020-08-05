package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.commons.support.geelink.entity.Librarygelink;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.library.Library;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.LibraryMapper;
import com.ht.biz.service.LibraryService;


import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jied
 */
@Service
public class LibraryServiceImpl extends ServiceImpl<LibraryMapper, Library> implements LibraryService {

    @Override
    public boolean updatereview(PageData pd) {
         boolean flag =false;
         int i = baseMapper.updatereview(pd);
         if(i>0){
             flag =true;
         }
       return flag;
    }

    @Override
    public PageData findlibrarybyid(String id) {
        return baseMapper.findlibrarybyid(id);
    }

    @Override
    public List<Library> findall() {
        return baseMapper.findall();
    }
    @Override
    public List<PageData> findByPage(MyPage page) {
        return baseMapper.findByPage(page);
    }

    @Override
    public Integer maxsort() {
        return baseMapper.maxsort();
    }

    @Override
    public int getdownfilehoney(List<String> id) {
        return baseMapper.getdownfilehoney(id);
    }

    public List<Library> findbytitle(PageData pd) {
        return  baseMapper.findbytitle(pd);
    }


    //后台查询
    @Override
    public List<PageData> sysfindByPage(MyPage page) {
        return baseMapper.sysfindByPage(page);
    }

    @Override
    public List<PageData> getlistByids(MyPage page) {
        return baseMapper.getlistByids(page);
    }

    @Override
    public List<PageData> sysfindByPagenopass(MyPage page) {
        return baseMapper.sysfindByPagenopass(page);
    }
/*    @Override
    public IPage<PageData> findByPage(Long current, Long size,PageData pd) throws Exception {
        //分页器对象
        IPage<PageData> page = new Page<PageData>(current, size);
        //条件构造器对象
        QueryWrapper<PageData> queryWrapper = new QueryWrapper<PageData>();
        //区域查询标题模糊查询
        if (StringUtils.isNotBlank(pd.getString( "keyword2" ))){
            queryWrapper.like("lib.region", pd.getString( "keyword2" ))
                    .or().like("lib.title", pd.getString( "keyword2" ));
        }
        //省
        if(StringUtils.isNotBlank(pd.getString( "province" ))){
            queryWrapper.like("lib.province", pd.getString( "province" ));
        }
        //市
        if(StringUtils.isNotBlank(pd.getString( "city" ))){
            queryWrapper.like("lib.city", pd.getString( "city" ));
        }
        //区
        if(StringUtils.isNotBlank(pd.getString( "area" ))){
            queryWrapper.like("lib.area", pd.getString( "area" ));
        }
        if (StringUtils.isNotBlank(pd.getString( "keywords" ))){
            queryWrapper.like("lib.keywords", pd.getString( "keywords" ));
        }
        //政策类型
        if (StringUtils.isNotBlank(pd.getString( "libtype" ))){
            queryWrapper.eq("lib.libtype",pd.getString( "libtype" ));
        }
        //项目类型
        if (StringUtils.isNotBlank(pd.getString( "type" ))){
            queryWrapper.like("lib.type",pd.getString( "type" ));
        }
        //上传类型
        if (StringUtils.isNotBlank(pd.getString( "person" ))){
            queryWrapper.eq("lib.person",pd.getString( "person" ));
        }
        //待审核
        if (StringUtils.isNotBlank(pd.getString( "status" ))){
            queryWrapper.ne( "lib.status",1 );
        }else{ //已审核
            queryWrapper.eq( "lib.status",1 );
        }
        //排序
        queryWrapper.orderBy( true,false,"createdate");
        return   baseMapper.findByPage(page, queryWrapper);
    }*/


    @Override
    public List<PageData> promotelibrary(MyPage page) {
//        PageData pd = new PageData(  );
//        if(StringUtils.isNoneBlank( library.getProvince() )){
//            pd.put( "province",library.getProvince() );
//        } if(StringUtils.isNoneBlank(  library.getCity())){
//            pd.put( "city",library.getCity() );
//        }
//        if(StringUtils.isNoneBlank(  library.getArea())){
//            pd.put( "area",library.getArea() );
//        }
//        if(StringUtils.isNoneBlank(  library.getType())){
//            pd.put( "type",library.getType() );
//        }
//        page.setPd( pd );
        return (List<PageData>)baseMapper.promotelibrary(page);

    }

    @Override
    public List<PageData> readlistPage(MyPage mypage) {
        return baseMapper.readlistPage(mypage);
    }

	@Override
	public Library findbyId(String id) {
		// TODO Auto-generated method stub
		return baseMapper.findbyId(id);
	}

    @Override
    public List<PageData> myload(MyPage page) {
        return baseMapper.myload(page);
    }

    @Override
    public Integer countcole(String userId) {
        return baseMapper.countcole(userId);
    }

    @Override
    public List<Librarygelink> geelinkfindall() {
        return baseMapper.geelinkfindall();
    }


}
