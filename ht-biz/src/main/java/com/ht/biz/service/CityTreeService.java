package com.ht.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.citytree.Citytree;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jied
 */
public interface CityTreeService extends IService<Citytree> {

    List<Citytree> findlistPage() throws Exception;

    void deleteCityById(String id) throws  Exception;
    boolean ischildnode(String id)throws Exception;
    Citytree  selectbyId(String  id);
    void updateOne(PageData pd);
}
