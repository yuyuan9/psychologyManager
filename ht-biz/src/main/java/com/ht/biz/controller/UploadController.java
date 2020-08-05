package com.ht.biz.controller;


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
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Controller

@RestController
@RequestMapping(value = "/uploadController")
@Api(value = "UploadController", description = "文件管理controller")
public class UploadController extends BaseController {
    @Autowired
    private UploadFileService uploadFileService;


//    @Autowired
//    private UploadUtil uploadUtil;
    @ApiOperation(value = "文件上传", notes = " ")
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Respon upload(@ApiParam(name = "file", value = "文件", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse resopnse,String classname) throws Exception {

        Respon respon = new Respon();
        try {
            if(null==file) {
                return  respon.error( "请选择上传文件" );
            }
            long size =file.getSize();
          if(size > Const.maxipload) {
                respon.error( "上传文件必须小于" + 10 + "MB" );
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
            String reslut = http.httpClientUploadFile( file, newFileName );
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
                            return respon.error("用户信息失效请重新登录");
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


// //ftp上传文件
// @RequestMapping(value = "/ftpupload")
// @ApiOperation(value = "本地文件上传",notes ="本地文件上传" )
// public Respon uploadfunction(HttpServletRequest request, HttpServletResponse response,MultipartFile file){
//     //创建文件对象并获取请求中的文件对象
//   /*  MultipartFile file = null;*/
//     Respon resultData = new Respon();
//
//     try{
//         MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
//        /* file = mRequest.getFile("fileNames");*/
//
//         //判断上传非空
//         if(null == file) {
//             System.out.println( file.getOriginalFilename() );
//             resultData.error( "上传文件失败" );
//             return resultData;
//         }
//         //上传需要导入数据的文件
//         //用来检测程序运行时间
//         long  startTime=System.currentTimeMillis();
//         System.out.println("上传的文件名为："+file.getOriginalFilename());
//         String fileName = file.getOriginalFilename();
//         InputStream inputStream = file.getInputStream();
//         String hostName = uploadUtil.getHostname();
//         String username = uploadUtil.getUsername();
//         String password = uploadUtil.getPassword();
//         String targetPath = uploadUtil.getTargetPath();
//     /*    String suffix = cmsArticleService.getSuffix(fileName);*/
//         String suffix = new UploadImageUtil().getExtension( fileName ); //后缀名
//         if ("exe".equals( suffix ) || "java".equals( suffix ) || "sh".equals( suffix )) {
//             resultData.error( "上传文件格式不能是" + suffix + "!" );
//             return resultData;
//         }
//         fileName = upload(hostName,username,password,targetPath,suffix,inputStream);
//         //计算上传时间
//         long  endTime=System.currentTimeMillis();
//         String uploadTime = String.valueOf(endTime-startTime);
//         System.out.println("上传所用时间："+uploadTime+"ms");
//         resultData.success( fileName );
////         resultData.put("code",200);
////         resultData.put("msg","上传文件成功");
////         resultData.put("filename",fileName);
//         return resultData;
//
//     } catch (Exception e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//     }
//     return null;
// }
    //FTP文件上传
    public static String upload(String hostname,String username,String password,
                                String targetPath,String suffix,InputStream inputStream) throws SocketException, IOException {
        //实例化ftpClient
        FTPClient ftpClient = new FTPClient();
        //设置登陆超时时间,默认是20s
        ftpClient.setDataTimeout(120000);
        //1.连接服务器
        ftpClient.connect(hostname,21);
        //2.登录（指定用户名和密码）
        boolean b = ftpClient.login(username,password);
        if(!b) {
            System.out.println("登陸超時");
            if (ftpClient.isConnected()) {
                // 断开连接
                ftpClient.disconnect();
            }
        }
        // 设置字符编码
        ftpClient.setControlEncoding("UTF-8");
        //基本路径，一定存在
        String basePath="/";
        String[] pathArray = targetPath.split("/");
        for(String path:pathArray){
            basePath+=path+"/";
            //3.指定目录 返回布尔类型 true表示该目录存在
            boolean dirExsists = ftpClient.changeWorkingDirectory(basePath);
            //4.如果指定的目录不存在，则创建目录
            if(!dirExsists){
                //此方式，每次，只能创建一级目录
                boolean flag=ftpClient.makeDirectory(basePath);
                if (flag){
                    System.out.println("创建成功！");
                }
            }
        }
        //重新指定上传文件的路径
        ftpClient.changeWorkingDirectory(targetPath);
        //5.设置上传文件的方式
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //使用uuid，保存文件名唯一性
        String uuid= UUID.randomUUID().toString();
        /**
          * 6.执行上传
          * remote 上传服务后，文件的名称
          * local 文件输入流
          * 上传文件时，如果已经存在同名文件，会被覆盖
          */
        ftpClient.enterLocalPassiveMode();
        boolean uploadFlag = ftpClient.storeFile(uuid+suffix,inputStream);
        if(uploadFlag)
            System.out.println("上传成功！");
        return uuid+suffix;
    }

//    @RequestMapping(value = "/test")
//    @ApiOperation(value = "本地文件上传",notes ="本地文件上传" )
//    public Respon uploadfunction() {
//        //创建文件对象并获取请求中的文件对象
//       File file = new  File("C:\\Users\\lhy\\Desktop\\大话设计模式(1).pdf");
//        uploadToFtp(file);
//        return  null ;
//    }
//    private boolean uploadToFtp(File file){
//        long startTime = System.currentTimeMillis();
//        FTPClient ftpClient = new FTPClient();
//        try {
//            //连接ftp服务器 参数填服务器的ip
//            ftpClient.connect(uploadUtil.getHostname());
//            //进行登录 参数分别为账号 密码
//            ftpClient.login(uploadUtil.getUsername(),uploadUtil.getPassword());
//            //改变工作目录（按自己需要是否改变）
//            //只能选择local_root下已存在的目录
//        //    ftpClient.changeWorkingDirectory("images");
//            //设置文件类型为二进制文件
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//            //开启被动模式（按自己如何配置的ftp服务器来决定是否开启）
//            //ftpClient.enterLocalPassiveMode();
//            //上传文件 参数：上传后的文件名，输入流
//            ftpClient.storeFile(file.getName(), new FileInputStream(file));
//            ftpClient.disconnect();
//            System.out.println(file.getName());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//        long entTime = System.currentTimeMillis();
//        System.out.println( entTime-startTime );
//        return true;
//    }

}

