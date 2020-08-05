package com.ht.biz.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.KeyworkService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.tree.TreeBuilder;
import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.UuidUtil;
import com.ht.entity.biz.sys.keywork.Keywork;
import com.ht.entity.shiro.SysPermission;
import com.ht.utils.LoginUserUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RestController
@RequestMapping(value = "/sys/ht-biz/keyword")
@Api(value = "KeywordController", description = "后台词库管理")
public class KeywordController extends BaseController {

    @Autowired
    KeyworkService keyworkService;

    @PostMapping("sysfindall")
    @ApiOperation(value="查询列表",notes="查询列表")
    public Respon sysparent(@RequestBody PageData pd){
        Respon respon = new Respon(  );
        MyPage page = this.getMyPage( pd );
        MyPage<PageData> list =  keyworkService.syslistall(page);
        return respon.success(list,page);
    }

    @PostMapping("sysparenlist")
    @ApiOperation(value="查询父类",notes="查询父类")
    public Respon sysparenlist(){
        Respon respon = new Respon(  );
        QueryWrapper que = new QueryWrapper();
        que.eq("pid", 0);
        List<Keywork>  list = keyworkService.list(que);
     /*   MyPage<PageData> list =  keyworkService.sysparent();*/
        return respon.success(list);
    }
    //导出词库
    @GetMapping("sysexport")
    @ApiOperation(value="导出词库",notes="导出词库")
    public void sysexport(HttpServletResponse response) throws FileNotFoundException {
        String path =	ResourceUtils.getURL("classpath:").getPath();
        File file = new File(path+Const.ciku);
        file.delete();
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            // write
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            QueryWrapper que = new QueryWrapper();
            que.ne("pid", 0);
            List<Keywork>  list = keyworkService.list(que);
            String  buffer="";
            for(int i= 0;i<list.size();i++){
                if(i==0){
                    buffer =list.get( 0 ).getKeywordname();
                }else{
                    buffer  =buffer+System.getProperty("line.separator")+list.get( i ).getKeywordname() ;
                }
            }
            bw.write(buffer);
            bw.flush();
            bw.close();
            fw.close();
            // read
            exportFile(response,path+Const.ciku,"词库.dic");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportFile(HttpServletResponse response,
                                  String csvFilePath, String fileName) throws IOException {
        FileInputStream in = null;
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        response.setContentType("text/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(fileName, "UTF-8"));
        response.setCharacterEncoding("UTF-8");
        try {
            in = new FileInputStream(csvFilePath);
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    out.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    @ApiOperation(value="关键词获取树形列表", notes="关键词获取树形列表")
    @GetMapping("/treelist")
    public Respon treelist()throws Exception{
        Respon respon = this.getRespon();
        try {
            List<Keywork> list= keyworkService.findList();
            List<TreeEntity> source = new ArrayList<TreeEntity>();
            source.addAll(list);
            List<TreeEntity> treelist = new TreeBuilder(source).buildTree();
            respon.success(treelist);
        }catch(Exception e) {
            respon.error(e);
        }
        return respon;
    }

    //sava yes
    @PostMapping("/save")
    @ApiOperation(value="添加Keywork",notes="添加Keywork")
    public Respon savakeyword(@ApiParam(name="关键词对象",value="传入json格式",required=true) @RequestBody Keywork keywork){
        Respon respon = new Respon(  );
        QueryWrapper query = new QueryWrapper();
        query.eq( "keywordname" ,keywork.getKeywordname());
        Keywork key  = keyworkService.getOne(  query);
        if(null!=key){
            return respon.error( "词库名称不能重复" );
        }else{
            keywork.setCreateid( LoginUserUtils.getUserId() );
            keywork.setCreatedate( new Date(  ) );
            keyworkService.save( keywork );
            respon.success("添加成功");
        }
        return respon ;
    }

    //修改yes
    @PostMapping("upatakeywork")
    @ApiOperation(value="修改Keywork",notes="修改Keywork")
    public Respon updatakeywork(@ApiParam(name="keywork对象",value="传入json格式",required=true)@RequestBody  Keywork keywork){
        Respon respon = new Respon();
        if(null!=keywork){
            Keywork key = keyworkService.getById(keywork.getId()  );
            if(key.getKeywordname().equals(keywork.getKeywordname()  )){
                if(keyworkService.updateById(keywork  )){
                    respon.success( "成功" );
                }else{
                    respon.error( "失败" );
                }
            }else{
                QueryWrapper query = new QueryWrapper();
                query.eq( "keywordname" ,keywork.getKeywordname());
                Keywork cre  = keyworkService.getOne(  query);
                if(cre!=null){
                    return respon.error( "词库名称不能重复" );
                }else{
                     if(keyworkService.updateById(keywork  )){
                         respon.success( "成功" );
                     }else{
                         respon.error( "失败" );
                     }
                }
            }


        }
        return respon;
    }
    //yes
    @GetMapping("findKeyworkById")
    @ApiOperation(value="根据id查询keyword",notes="修改Keywork")
    public Respon findKeyworkById(@ApiParam(name="keywork对象",value="传入json格式",required=true) String id){
        Respon respon = new Respon(  );
        PageData keywork = keyworkService.getfindbyId( id );
        if(null!=keywork){
         return    respon.success( keywork );
        }
        return respon.error("查询失败");
    }
    //批量删除 yes
    @GetMapping("batchdelete")
    @ApiOperation(value="根据id批量删除",notes="")
    public Respon batchdelete(@ApiParam(name="id集合",value="多个id用逗号隔开（1,2,3）",required=true) String ids){
        Respon respon = new Respon(  );
        if(StringUtils.isNoneBlank( ids )){
            List<String> idso = new ArrayList<>(  );
            String[] idss = ids.split( "," );
            for(String dil:idss){
                Keywork keywork = keyworkService.getById( dil );
                if(keywork.getPid()==0) {
                    QueryWrapper que = new QueryWrapper();
                    que.eq( "pid", dil );
                    List<Keywork> list = keyworkService.list( que );
                    if (list.size() > 0) {
                        return respon.error( "请先删除子节点数据" );
                    }
                }
                    idso.add( dil );
            }
            boolean  flag = keyworkService.removeByIds( idso );
            if(flag){
                return respon.success( "删除成功" );
            }
        }
        return respon.error("删除失败");
    }
    //yes
//    @GetMapping("sava")
//    @ApiOperation(value="根据id查询keyword",notes="修改Keywork")
//    public Respon save( String name,int pid){
//        Respon respon = new Respon(  );
//        String[]  s = name.split("、"  );
//        for(String s1:s){
//            QueryWrapper query = new QueryWrapper();
//            query.eq( "keywordname" ,s1);
//            Keywork key  = keyworkService.getOne(  query);
//            if(null!=key){
//               System.out.println( s1 );
//            }else{
//                Keywork keywork =new Keywork();
//                keywork.setKeywordname( s1 );
//                keywork.setCreateid( "1");
//                keywork.setCreatedate( new Date(  ) );
//                keywork.setPid( pid);
//                keyworkService.save( keywork );
//                System.out.println("添加成功");
//            }
//        }
//return null;
//    }

}
