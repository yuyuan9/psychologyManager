package com.ht.biz.system.controller;


//import com.ht.biz.base.TreeBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ht.biz.service.CityTreeService;


import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.tree.TreeBuilder;
import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.commons.utils.PageData;

import com.ht.entity.biz.citytree.Citytree;
import io.swagger.annotations.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping(value = "/sys/ht-biz/syscitytree")
@Api(value = "CtiyTreeController", description = "省份树形列表")
@RestController
public class CtiyTreeController extends BaseController {

    @Autowired
    private CityTreeService cityTreeService;



    private LoadingCache<Object, String> citycache = CacheBuilder.newBuilder()
            .recordStats()
            .maximumSize(1000)
            .expireAfterAccess(10, TimeUnit.DAYS)
            .build(
                    new CacheLoader<Object, String>() {
                        @Override
                        public String  load(Object o) throws Exception {
                            List<Citytree> list = cityTreeService.findlistPage( );
                            TreeBuilder treeBuilder = new TreeBuilder(new ArrayList<TreeEntity>(list));
                            String city = treeBuilder.buildJsonTree();
                            return city;
                        }
                    }
            );


    @ApiOperation(value = "树形列表", notes = " ")
    @RequestMapping(value = "treelist", method = RequestMethod.GET)
    @Cacheable(value = "user")
    public Respon citytreelist() throws Exception {
       /* Object  ss= getCache(1);*/
        Respon respon = new Respon();
        if(StringUtils.isNoneBlank( citycache.get(1  ) )){ //从缓存从查询
            respon.setData(citycache.get(1  )  );
        }else{
            List<Citytree> list = cityTreeService.findlistPage( );
            TreeBuilder treeBuilder = new TreeBuilder(new ArrayList<TreeEntity>(list));
            String city = treeBuilder.buildJsonTree();
            respon.setData(city  );
        }
        return respon;
    }





    /*@RequestMapping(value="/selectbyId", method=RequestMethod.GET)
    public Respon selectbyId( String  id) throws Exception {
        Respon respon = new Respon();
        Citytree s = cityTreeService.selectbyId(id  );
        return respon;
    }*/
    @ApiOperation(value = "修改启动禁用状态", notes = " ")
    @RequestMapping(value="/updatedisbale", method=RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "选择要修改节点的id，多个时用逗号隔开", dataType = "String", example = "1,2,3"),
        @ApiImplicitParam(name="disables",value="0:启动 1：禁用",dataType="int", example = "1")
    })
    public Respon update( ) throws Exception {
        Respon respon = new Respon();
         PageData pd = this.getPageData();

        try {
            cityTreeService.updateOne(pd);
            respon.success( "修改成功" );
        }catch (Exception e){
            respon.error( "修改失败" );
        }
        return respon;
    }


    @ApiOperation(value = "删除", notes = " ")
    @ApiImplicitParam(name = "id", value = "cityID", required = true, dataType = "String")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public Respon delectcity( String  id) throws Exception {
        Respon respon = new Respon();
        if(cityTreeService.ischildnode(id)){ //判断是有子节点
            try {
                cityTreeService.deleteCityById( id );
            }catch (Exception e){
                respon.error("删除失败");
            }
        }else{
            respon.error("请先删除其子节点");
        }
        return respon;
    }


    @ApiOperation(value = "查询列表", notes = " ")
    @GetMapping(value="/findall")
    public Respon findall() throws Exception {
        Respon respon = this.getRespon();
        List<Citytree>  list   =   cityTreeService.findlistPage(  );
        respon.success( list );
        return respon;
    }
    @ApiOperation(value = "根据id查询", notes = " ")
    @GetMapping(value="findbyId")
    public Respon findbyId(String id) throws Exception {
        Respon respon = this.getRespon();
        Citytree citytree =  cityTreeService.getById( id );
        getRespon().success(citytree);

        return respon;
    }
//    @Override
//    protected Object loadData(Object o) {
//        List<Citytree> list = null;
//        try {
//            list = cityTreeService.findlistPage( );
//            TreeBuilder treeBuilder = new TreeBuilder(new ArrayList<TreeEntity>(list));
//            String city = treeBuilder.buildJsonTree();
//            return city;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
