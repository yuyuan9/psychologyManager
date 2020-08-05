package com.ht.biz.system.controller;



import com.ht.biz.service.UploadFileService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.UploadImageUtil;
import com.ht.commons.utils.UploadhttpClient;

import com.ht.commons.utils.UuidUtil;
import com.ht.entity.biz.file.TUploadFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller

@RestController
@RequestMapping(value = "/sys/sysuploadController")
@Api(value = "UploadController", description = "后台文件管理controller")
public class SysUploadController extends BaseController {
    @Autowired
    private UploadFileService uploadFileService;


    @ApiOperation(value = "文件上传", notes = " ")
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Respon upload(@ApiParam(name = "file", value = "文件", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse resopnse,String classname,String watermark) throws Exception {

        Respon respon = new Respon();
        try {
            if(null==file) {
                return  respon.error( "请选择上传文件" );
            }
            long size =file.getSize();
            if(size > Const.sysmaxipload) {
                respon.error( "上传文件必须小于" + 80 + "MB" );
                return respon;
            }

            String picture = file.getOriginalFilename();
            String subfix = new UploadImageUtil().getExtension( picture ); //后缀名
            if ("exe".equals( subfix ) || "java".equals( subfix ) || "sh".equals( subfix )) {
                respon.error( "上传文件格式不能是" + subfix + "!" );
                return respon;
            }
            SimpleDateFormat df = new SimpleDateFormat( "yyyyMMddHHmmss" );
            String dfs = df.format( new Date() ) + "_" + new Random().nextInt( 1000 );
            String newFileName = dfs + "." + subfix;
            UploadhttpClient http = new UploadhttpClient();
            String reslut ="";
            if(StringUtils.isNotBlank( watermark )){
                reslut = http.httpClientUploadFile( file, newFileName,watermark);
            }else{
                 reslut = http.httpClientUploadFile( file, newFileName);
            }
            if (!StringUtils.isBlank( reslut ) && !reslut.equals( "false" )) {
                if(!org.apache.commons.lang.StringUtils.isBlank(reslut) && !reslut.equals("false") ){
                    try {
                        TUploadFile upload= new TUploadFile();
                        upload.setId( UuidUtil.get32UUID() );
                        upload.setCreatedate(new Date());
                        upload.setClazzName( classname );
                        if(null!=getLoginInfo()){
                            upload.setCreateid( getLoginInfo().get( "userId" ).toString() );
                        }else{
                            return respon.loginerror("用户信息失效请重新登录");
                        }
                        String[] paths  = reslut.split( "," );
                        //upload.setDeleted(0);
                        upload.setExt(subfix);
                        upload.setFileName(picture);
                        upload.setOriginalName(dfs);
                        upload.setPath(paths[0]);
                        upload.setSize(file.getSize());
                        upload.setPdfpath(paths[1]);
                        upload.setOsspath(paths[2]);
                        uploadFileService.save(upload);
                        respon.success(upload );
                    }catch (Exception e){
                        e.printStackTrace();
                        respon.error("上传图片失败");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            respon.error( "上传图片失败" );
        }
        return respon;
    }


    @ApiOperation(value = "文件下载", notes = " ")
    @RequestMapping(value = "/downfile", method = RequestMethod.GET)
    public void createFolw(@ApiParam(name = "path", value = "文件路径", required = true) String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UploadhttpClient uploadhttpClient = new UploadhttpClient();
        uploadhttpClient.downfile(path ,response );
    }


}

