package com.ht.biz.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.citytree.Citytree;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
public interface CityTreeMapper extends BaseMapper<Citytree> {

    List<Citytree> findlistPage();
    int childnodecount(String id);
    Citytree  selectbyId(String  id);
    void updateOne(PageData pd);
}
