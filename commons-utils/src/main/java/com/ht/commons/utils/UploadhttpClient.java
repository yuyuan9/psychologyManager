package com.ht.commons.utils;




import com.ht.commons.constants.Const;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import ytx.org.apache.http.protocol.HTTP;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


public class UploadhttpClient {
    public String httpClientUploadFile(MultipartFile file, String dfs) {
        final String remote_url = Const.uploadpath;// 第三方服务器请求地址
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            String fileName = file.getOriginalFilename();
            HttpPost httpPost = new HttpPost( remote_url );
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody( "file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName );// 文件流
            builder.addTextBody( "newfilename", dfs );// 类似浏览器表单提交，对应input的name和value
            HttpEntity entity = builder.build();
            httpPost.setEntity( entity );
            HttpResponse response = httpClient.execute( httpPost );// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString( responseEntity, Charset.forName( "UTF-8" ) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

//
    public static String httpClientUploadFile(MultipartFile file, String dfs,String watermark) {
        final String remote_url = Const.uploadpath;// 第三方服务器请求地址
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            String fileName = file.getOriginalFilename();
            HttpPost httpPost = new HttpPost( remote_url );
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
            builder.addBinaryBody( "file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName );// 文件流
            builder.addTextBody( "newfilename", dfs );// 类似浏览器表单提交，对应input的name和value
            builder.addTextBody( "watermark", watermark,contentType);// 类似浏览器表单提交，对应input的name和value
            HttpEntity entity = builder.build();
            httpPost.setEntity( entity );
            HttpResponse response = httpClient.execute( httpPost );// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString( responseEntity, Charset.forName( "UTF-8" ) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void downfile(String filePath, HttpServletResponse response) throws IOException {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        //File f = new File(localFilePath);
        try {
            String HTTp_URL = Const.filepaht + filePath;
            response.setContentType( "application/force-download" );
            response.setHeader( "Content-Disposition", "attachment;fileName=" + HTTp_URL.substring( (HTTp_URL.lastIndexOf( "/" ) + 1) ) );
            urlfile = new URL( HTTp_URL );
            httpUrl = (HttpURLConnection) urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream( httpUrl.getInputStream() );
            bos = new BufferedOutputStream( response.getOutputStream() );
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read( b )) != -1) {
                bos.write( b, 0, len );
            }
            System.out.println( "下载成功" );
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String zipfile(String dfs) {
        final String remote_url = Const.zipfile;// 第三方服务器请求地址
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            HttpPost httpPost = new HttpPost( remote_url );
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody( "pathurl", dfs );// 类似浏览器表单提交，对应input的name和value
            HttpEntity entity = builder.build();
            httpPost.setEntity( entity );
            HttpResponse response = httpClient.execute( httpPost );// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString( responseEntity, Charset.forName( "UTF-8" ) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }



    public static String HttpPostWithJson(String url, String json) {
        String returnValue = "这是默认返回值，接口调用失败";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);
            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json,"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);
            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return returnValue;
    }

//    public static void main(String[] arg){
//        String data="{" +
//                "\"collection\":\"wenku\",\"queryParam\":{\"query\":\"广州\"},\"highLightParam\":{\"startTag\":\"<em>\",\"endTag\":\"</em>\",\"field\":[\"supportobj\"]},\"returnParam\":{\"field\":[\"year\",\"title\"],\"rows\":1,\"start\":0}}";
//        HttpPostWithJson("http://112.126.96.51:9091/v2/api/search/",data);
//
//
//    }
    //创建数据集
    //http://127.0.0.0:9090/v2/api/collection/

    public static  void savaCollection(String url,String clientId,String name,String  maxShardsPerNode,String numShards,String replicationFactor,String [] types){
        if(StringUtils.isBlank(maxShardsPerNode  )){
            maxShardsPerNode="3";
        }
        if(StringUtils.isBlank(numShards)){
            numShards="3";
        }
        if(StringUtils.isBlank(replicationFactor)){
            numShards="2";
        }
        StringBuilder  jsonstring= new StringBuilder(  );
        String str ="{" +
            "\"clientId\":" +clientId+ ",\"params\":{\"name\":" +name+ "" +
            ",\"maxShardsPerNode\":"+maxShardsPerNode+",\"numShards\":"+numShards+",\"replicationFactor\":" +replicationFactor+
            "},\"types\":[";
        String str2="";
        for(int i= 0;i<types.length;i++){
            if(i==0){
                str2 =types[i];
            }else{
                str2 = str2+","+types[i];
            }
        }
        String str3= "]}";
        jsonstring.append( str );
        jsonstring.append( str2 );
        jsonstring.append(str3  );
        HttpPostWithJson(url,jsonstring.toString());

    }
    //添加字段
    public static  void savafield(String url,String clientId,String collection,String name,String  type,Boolean  indexed,
                                    boolean stored,boolean  docValues,boolean required,boolean multiValued,boolean sort,
                                    boolean copyfield,boolean searchable,boolean recommendable ){
        String  str = "  {\"clientId\":" +clientId+ ",\"collection\":" +collection+ ",\"umeFieldList\":[{\"name\":" +name+
                ",\"type\":" +type+ ",\"indexed\":" +indexed+ ",\"stored\":" +stored+ ",\"docValues\":" +docValues+
                ",\"required\":" +required+ ",\"multiValued\":" +multiValued+",\"sort\":" +sort+",\"copyfield\":" +copyfield+"," +
                "\"searchable\":" +searchable+",\"recommendable\":" +recommendable+ "}]}";
        HttpPostWithJson(url,str);

    }

    //  #######  数据管理
      //添加索引
    public static  void  savaIndexes(String url,String clientId,String collection,String data,String  type){
            String  str = "{\"clientId\":" +clientId+
                    ",\"collection\":" +collection+ ",\"properties\":{\"data\":" +data+
                    "},\"type\":" +type+ "}";
        HttpPostWithJson(url,str);
    }
    //修改索引  action:ADD:对字段值进行新增 UPDATE：对字段值进行更新 CLEAR：清除字段值  data{"",""},{"",""}
    public static void updateIndexes(String url,String collection,String action,String data ){
        String str ="{\"action\":" +action+
               ",\"collection\":" +collection+ ",\"data\":["+data +"]}";
        HttpPostWithJson(url,str);
    }
    //查询数据
    public static void selectdata(String url,String collection,String query ,String startTag,String endTag,String[] field,String[] returnParam){
        String str2 ="";
        for(int i= 0;i<field.length;i++){
            if(i==0){
                str2 =field[i];
            }else{
                str2 = str2+","+field[i];
            }
        }
        String str3 ="";
        for(int i= 0;i<returnParam.length;i++){
            if(i==0){
                str3 =returnParam[i];
            }else{
                str3 = str3+","+returnParam[i];
            }
        }
        String str ="{\"collection\":" +collection+ ",\"queryParam\":{\"query\":" +query +"},\"highLightParam\":{\"startTag\":" +startTag+
                ",\"endTag\":" +endTag+ ",\"field\":["+ str2+"]},\"returnParam\":{\"field\":["+str3+"]}}";
        HttpPostWithJson(url,str);
    }


    //###搜索工呢 #######
    //高亮搜索
    public static void hselect(String url,String collection,String query,String[] filed,String startTag,String  endTag,int rows,int start,String [] Param){
        String str2 ="";
        for(int i= 0;i<filed.length;i++){
            if(i==0){
                str2 =filed[i];
            }else{
                str2 = str2+","+filed[i];
            }
        }
        String str3 ="";
        for(int i= 0;i<Param.length;i++){
            if(i==0){
                str3 =Param[i];
            }else{
                str3 = str3+","+Param[i];
            }
        }
        String str ="{\"collection\":" +collection+ ",\"queryParam\":{\"query\":" +query+ "},\"highLightParam\":{\"startTag\":" +startTag+
                ",\"endTag\":" +endTag+ ",\"field\":["+str2+"]},\"returnParam\":{\"field\":["+ str3+"],\"rows\":" +rows+
                ",\"start\":" +start+ "}}";
        HttpPostWithJson(url,str);
    }
    //排序搜索
    public static void hselect(String url,String collection,String query,String[] filedsort,int rows,int start,String [] Param){
        String str2 ="";
        for(int i= 0;i<filedsort.length;i++){
            if(i==0){
                str2 =filedsort[i];
            }else{
                str2 = str2+","+filedsort[i];
            }
        }
        String str3 ="";
        for(int i= 0;i<Param.length;i++){
            if(i==0){
                str3 =Param[i];
            }else{
                str3 = str3+","+Param[i];
            }
        }
        String str ="{\"collection\":"+collection+",\"queryParam\":{\"query\":"+ query+",\"sortField\":["+str2+"]}," +
                "\"returnParam\":{\"field\":["+str3+"],\"rows\":"+ rows+",\"start\":"+start+"}}";
        HttpPostWithJson(url,str);
    }
        //精准/模糊搜索 precise: true 精准匹配 false 模糊匹配

    public static void likeselect(String url,String collection,String query,Boolean precise,String[] filed,int rows,int start){
        String str2 ="";
        for(int i= 0;i<filed.length;i++){
            if(i==0){
                str2 =filed[i];
            }else{
                str2 = str2+","+filed[i];
            }
        }

        String str ="{\"collection\":"+collection+",\"queryParam\":{\"query\":"+query+",\"precise\":"+precise+"}," +
                "\"returnParam\":{\"field\":["+str2+"],\"rows\":"+rows+",\"start\":"+start +"}}";
        HttpPostWithJson(url,str);
    }
    //条件搜索  filterQuery==> "filterQuery":["<in/title>乌拉特","<in/summary>呼伦贝尔"] //条件搜索 1 "filterQuery":["乌拉特","呼伦贝尔"] //条件搜索 2
    public static void paramselect(String url,String collection,String query,Boolean gqlSearch,String filterQuery ,int rows,int start,String[] filed){
        String str3 ="";
        for(int i= 0;i<filed.length;i++){
            if(i==0){
                str3 =filed[i];
            }else{
                str3 = str3+","+filed[i];
            }
        }
        String str ="{\"collection\":"+collection+",\"queryParam\":{\"gqlSearch\":"+gqlSearch+",\"query\":"+query+","+filterQuery+"}," +
                "\"returnParam\":{\"field\":["+str3+"],\"rows\":"+rows+",\"start\":"+start+"}}";
        HttpPostWithJson(url,str);
    }
    //gql 搜索
    public static void gqlselect(String url,String collection,String query,Boolean gqlSearch,String filterQuery ,int rows,int start,String[] filed){
        String str3 ="";
        for(int i= 0;i<filed.length;i++){
            if(i==0){
                str3 =filed[i];
            }else{
                str3 = str3+","+filed[i];
            }
        }
        String str ="{\"collection\":"+collection+",\"queryParam\":{\"gqlSearch\":"+gqlSearch+",\"query\":"+query+"},\"returnParam\":{\"field\":["+filed+"],\"rows\":"+rows+",\"start\":"+start+"}}";
        HttpPostWithJson(url,str);
    }
    //主题（topic）搜索
    public static void topicselect(String url,String collection,String query,Boolean gqlSearch, String topicAlias, String filterQuery ,int rows,int start,String[] filed){
        String str3 ="";
        for(int i= 0;i<filed.length;i++){
            if(i==0){
                str3 =filed[i];
            }else{
                str3 = str3+","+filed[i];
            }
        }
        String str ="{\"collection\":\"demo\",//数据集名称\"queryParam\":{//查询参数\"gqlParam\":{//资源参数\"topicAlias\":\"demo\"//主题（topic）资源别名},\"gqlSearch\":true,//gql搜索\"query\":\"{教育}\"//查询的主题，用大括号{}包起来},\"returnParam\":{//返回参数\"field\":[//返回的字段\"year\",\"issue\"],\"rows\":1,//返回的条数\"start\":0//从第几条开始返回，0代表从第一条开始}}";
        HttpPostWithJson(url,str);
    }
    public static void main(String[] arg){
        String[]str = new String[]{"DATA","JOBS"};
        savaCollection("","test","name","3","3","2",str);

    }
}

