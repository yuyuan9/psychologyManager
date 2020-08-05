package com.ht.commons.support.geelink.jsonquery;


import com.ht.commons.support.geelink.entity.GeelinkPolicyDig;
import com.ht.commons.support.geelink.entity.Librarygelink;
import com.ht.commons.support.geelink.entity.Policy;
import com.ht.commons.support.geelink.http.GeeLinkHttpClient;

import org.apache.commons.lang.StringUtils;


import java.io.IOException;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Geelinkdatacreate {
    private Class clazz;

    public Geelinkdatacreate(Class clazz) {
        this.clazz=clazz;
    }
    public Geelinkdatacreate() {

    }
    public String payload(List<Object> query) {
        String fmt="%s";
        return String.format(fmt,getpayload(query));
    }
    public String queryStr(List<Object> query) {
        String fmt="%s";
        return String.format(fmt,getproperties(query));
    }

    public String queryStrupdata(List<Object> query) {
        String fmt="{%s,%s}";
        return String.format(fmt, getCollection(),getdata(query));
    }
    private String getCollection() {
        StringBuffer str= new StringBuffer();
        str.append("\"collection\":");
        if(this.clazz!=null) {
            if(this.clazz.getSimpleName().equals(GeelinkPolicyDig.class.getSimpleName())) {
                str.append("\"policyExpress\"");
            }else if(this.clazz.getSimpleName().equals(Librarygelink.class.getSimpleName())) {
                str.append("\"library\"");
            }else if(this.clazz.getSimpleName().equals(Policy.class.getSimpleName())) {
                str.append("\"policy\"");
            }
        }
        return str.toString();
    }

    private String getpayload(List<Object> query) {
        StringBuffer str = new StringBuffer();
        str.append("\"payload\": [");
        str.append(getField(query));
        str.append("]");
        return str.toString();
    }

    private String getproperties(List<Object> query) {
        StringBuffer str = new StringBuffer();
        str.append("\"data\":[");
        str.append(getField(query));
        str.append("]}");
        return str.toString();
    }

    private String getdata(List<Object> query) {
        StringBuffer str = new StringBuffer();
        str.append("\"data\": [");
        str.append(getField(query));
        str.append("]");
        return str.toString();
    }

    private String getField(List<Object> entity) {
        StringBuffer fieldstr = new StringBuffer();
        for(Object entitys :entity){
            fieldstr.append( "{" );
            Field[] fields = entitys.getClass().getDeclaredFields();
            for(int i = 0 ; i < fields.length ; i++) {
                //设置是否允许访问，不是修改原来的访问权限修饰词。
                fields[i].setAccessible(true);
                try {
                    if(null!=fields[i].get(entitys)&&StringUtils.isNotBlank( fields[i].get(entitys).toString() )){
                        if(fields[i].getName().toString().equals( "content" )){
                            fieldstr.append("\"").append(""+fields[i].getName()).append( "\":" ).append("\"")
                                    .append((fields[i].get(entitys))==null? "" :stripHtml(fields[i].get(entitys).toString() )).append( "\","  );
                        }else{
                            fieldstr.append("\"").append(""+fields[i].getName()).append( "\":" ).append("\"")
                                    .append((fields[i].get(entitys))==null? "" :fields[i].get(entitys).toString() ).append( "\","  );
                        }

                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            fieldstr.deleteCharAt(fieldstr.length() - 1);
            fieldstr.append( "}," );
        }

        return  StringUtils.removeEnd(fieldstr.toString(), ",");
    }

    public String stripHtml(String content) {
        content = content.replaceAll("\\<.*?>", "");
        content.replaceAll("\n", "");
        // 去掉空格
        content = content.replaceAll(" ", "");
        return content;
    }
    /**
     * json文件上传数据
     * @param clientId
     * @param collection
     * @param jsonfilepath
     * @return
     */
    public String jsonfileupload(String clientId ,String collection,String jsonfilepath){
        String url="http://120.79.238.238:9090/v2/api/index/";
        StringBuffer json = new StringBuffer();
        json.append("{\"clientId\":"+clientId+",\"collection\":"+collection+",\"properties\":{\"data\":"+jsonfilepath+"},\"type\":\"FILE\"}");
        System.out.println(json.toString());
        String retvalue= null;
        try {
            retvalue = GeeLinkHttpClient.sendUTF8(url, json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(retvalue);
        return retvalue;
    }
    /**
     * json数据上传数据
     * @param entity
     * @return
     * @throws IOException
     */
    public String jsondataPolicyDig(List<Object> entity) throws IOException {
      /*  String url="http://120.24.87.208:9090/admin/v2/api/index/";*/
        String url="http://120.24.87.208:9090/admin/v2/api/profiler/increment.tagging";
        Geelinkdatacreate cd = new Geelinkdatacreate(entity.getClass());
        StringBuffer json = new StringBuffer(  );
        json.append(  "{ \"collection\":\"library\",\"maxTagNum\":2,\"otherTagName\":\"分类/其他\", ");
        json.append( cd.payload(entity) );
        json.append(",\"resourceId\":[\"b9c48f52-6ce9-44f4-b217-d362c334b5a0\",\"cb88f91a-a811-42f6-b48e-c3c026f7eaea\"],\"thread\":5}"  );
        String retvalue = GeeLinkHttpClient.sendUTF8(url, json.toString());
        return retvalue ;
    }

    public String jsondataPolicyDigtow(List<Object> entity) throws IOException {
        /*  String url="http://120.24.87.208:9090/admin/v2/api/index/";*/
        String url="http://120.24.87.208:9090/admin/v2/api/profiler/increment.tagging";
        Geelinkdatacreate cd = new Geelinkdatacreate(entity.getClass());
        StringBuffer json = new StringBuffer(  );
        json.append(  "{ \"collection\":\"policyExpress\",\"maxTagNum\":2,\"otherTagName\":\"分类/其他\", ");
        json.append( cd.payload(entity) );
        json.append(",\"resourceId\":[\"817a932c-3bb9-4b56-9af9-7c30cf510ef5\",\"85c3928b-3638-45c9-bce7-8cfca1625e29\",\"87d65a93-b615-4c61-8478-4a4f5f86cd2f\",\"fd333846-42ab-4273-8b04-5769a2cae7af\",\"4aa0b5ed-2c1e-4b88-981c-46fc11371b3b\"],\"thread\":5}"  );
        String retvalue = GeeLinkHttpClient.sendUTF8(url, json.toString());
        return retvalue ;
    }

    /**
     * 修改
     * @param action  ADD:对字段值进行新增 UPDATE：对字段值进行更新 CLEAR：清除字段值
     * @param query
     * @return
     */
    public String updatedata(String action,String mode, List<Object> query){
        //put
        String url = "http://120.24.87.208:9090/admin/v2/api/index";
        StringBuffer json = new StringBuffer();
        json.append( "{\"action\":\""+action+"\",\"collection\": \""+mode+"\"," );
        Geelinkdatacreate cd = new Geelinkdatacreate(query.getClass());
        json.append(cd.queryStr(query)  ) ;
        System.out.println( json.toString() );
        try {
            String result =   GeeLinkHttpClient.sendUTF8put(url,json.toString());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //测试修改
//    public static void main(String[] arg){
//        PolicyDig  policyDig = new PolicyDig();
//        policyDig.setId( "1111111" );
//        policyDig.setArea( "111111" );
//        PolicyDig  policyDig2 = new PolicyDig();
//        policyDig2.setId( "2222" );
//        Geelinkdatacreate cd = new Geelinkdatacreate();
//        List<Object> objects = new ArrayList<>(  );
//        objects.add( policyDig );
//        objects.add( policyDig2 );
//        System.out.println( cd.updatedata("UPDATE","policy ",objects) );
//    }
    //批量删除
    public static String deletedata(String[] idlist){
      //  HTTP DELETE： http://IP:PORT/admin/v2/api/index/{collectionName}/{id}
     //   String url = "HTTP DELETE： http://127.0.0.1:9090/admin/v2/api/index/demo/00001";
       // HTTP DELETE： http://IP:PORT/admin/v2/api/index/{collectionName}/idList
        String url ="HTTP DELETE： http://127.0.0.1:9090/admin/v2/api/index/demo/idList";
        StringBuffer json = new StringBuffer(); //"D00001","D00002","D00003"]}
        json.append( "{\"idList\":[" );
        if(idlist.length>0){
            for(String id : idlist){
                json.append("\"").append(""+id).append( "\"," );
            }
            json.deleteCharAt(json.length() - 1);
        }
        json.append( "] }" );

        return json.toString() ;
    }

    //通过json文件上传数据
//    public static void main(String[] arg){
//     /* *//*  PolicyDig  policyDig = new PolicyDig();
//        policyDig.setId( "testss" );
//        policyDig.setArea( "test" );
//        PolicyDig  policyDig2 = new PolicyDig();
//        policyDig2.setId( "testss" );
//        policyDig2.setArea( "test" );
//        Geelinkdatacreate cd = new Geelinkdatacreate(PolicyDig.class);
//        List<Object> objects = new ArrayList<>(  );
//        objects.add( policyDig );
//        objects.add( policyDig2 );
//        System.out.println( cd.queryStrupdata(objects) );*//*
//        String[] array3 = new String[3];
//        array3[0]="1";
//        array3[1]="1";
//        array3[2]="1";*/
//    /*    System.out.println(  deletedata(array3));*/
//        querylibrary("广州市",1,15,"广东省","广州市","天河区","分类/申报通知","分类/科研立项");
//    }

    //文库查询查询


    public static  String querylibrary(String  query,int current,int size,String province,String city,String area,String type,String libtype){
        StringBuffer json = new StringBuffer();
        if(StringUtils.isBlank( query)){
            query="";
        }
        json.append( "{ \"collection\": \"library\",  \"queryParam\": {  \"gqlSearch\":\"true\", \"query\": \""+query+"\"" );
        if(StringUtils.isBlank( query )){
            json.append( ",\"sortField\": [ \"createdate desc\"]" );
        }
        if(StringUtils.isNotBlank(  query)){
            json.append( ",\"gqlParam\":{\"qcAlias\":\"sortSearch\"}" );
            json.append( " ,\"sortField\": [\"score desc\"]" );
        }

        if(StringUtils.isNotBlank( province )){
            json.append(",\"gqlFilterQuery\":[");
        }

        if(StringUtils.isNotBlank( province )){
            json.append("\"<and>(<in/region>"+province);
        }
        if(StringUtils.isNotBlank( city )){
            json.append("<and>"+city);
        }
        if(StringUtils.isNotBlank( area )){
            json.append("<and>"+area);
        }
        if(StringUtils.isNotBlank( province )){
            json.append(")\"]");
        }
        if(StringUtils.isNotBlank( type )||StringUtils.isNotBlank( libtype )){
            json.append(",\"filterFacet\":{");
        }
        if(StringUtils.isNotBlank( type )){
            json.append("\"_gl_dp_taxonomy_projectOneTax\":[\"分类/"+type+"\"]");
        }
        if(StringUtils.isNotBlank( libtype )){
            if(StringUtils.isNotBlank( type )){
                json.append(",\"_gl_dp_taxonomy_projectOneTax\":[\"分类/"+type+"\"]");
            }else{
                json.append("\"_gl_dp_taxonomy_policyTypeTax\":[\""+libtype+"\"]");
            }
        }
        if(StringUtils.isNotBlank( type )||StringUtils.isNotBlank( libtype )){
            json.append("}");
        }
      /*  json.append( ",\"sortField\": [\"createdate desc\"]" );*/
        json.append( "},\"returnParam\":{\"field\":[\"browsecount\",\"jbrowsecount\",\"uploadusername\",\"istop\",\"createdate\",\"technology\",\"_gl_dp_taxonomy_policyTypeTax\",\"_gl_dp_taxonomy_projectOneTax\",\"title\",\"name\",\"url\",\"region\",\"provice\",\"city\",\"area\",\"libtype\"],\"rows\":"+size +",\"start\":"+((current-1))*size+"}}" );
     /*   System.out.println( json.toString() );*/
        try {
         String result =   GeeLinkHttpClient.sendUTF8("http://120.79.238.238:9091/v2/api/search",json.toString());
 /*        System.out.println( result );*/
         return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //政策速递
    public static  String queryPolicyDig(String  query,long current,long size,String province,String city,String area,String type,String libtype,String datetime1,String datetime2,Integer apply){
        StringBuffer json = new StringBuffer();
        if(StringUtils.isBlank( query)){
            query="";
        }
        json.append( "{ \"collection\": \"policyExpress\",  \"queryParam\": {  \"gqlSearch\":\"true\", \"query\": \""+query+"\"" );
        if(StringUtils.isBlank( query )){
            json.append( ",\"sortField\": [ \"createdate desc\"]" );
        }
        if(StringUtils.isNotBlank(  query)){
            json.append( ",\"gqlParam\":{\"qcAlias\":\"sortSearch\"}" );
            json.append( " ,\"sortField\": [\"datetime desc\"]" );
        }

        if(StringUtils.isNotBlank( province )){
            json.append(",\"gqlFilterQuery\":[");
        }

        if(StringUtils.isNotBlank( province )){
            json.append("\"<in/province_s>"+province+"");
        }
        if(StringUtils.isNotBlank( city )){
            json.append("\",\"<in/city_s>"+city+"");
        }
        if(StringUtils.isNotBlank( area )){
            json.append("\",\"<in/area_s>"+area+"");
        }
        if(StringUtils.isNotBlank( province )){
            json.append("\"]");
        }

        if(StringUtils.isNotBlank( datetime1 )||StringUtils.isNotBlank( datetime2 )||null!=apply){
            json.append(", \"filterQuery\":[");
        }


        if(StringUtils.isNotBlank( datetime1 )){
            json.append(" \"datetime:["+datetime1+" TO *]\"");
        }

        if(StringUtils.isNotBlank( datetime2 )){
            if(StringUtils.isNotBlank( datetime1 )){
                json.append(", \"datetime:[* TO "+datetime2+"]\"");
            }else{
                json.append("\"datetime:[* TO "+datetime2+"]\"");
            }
        }
        if(null!=apply){
            if(StringUtils.isNotBlank( datetime1 )||StringUtils.isNotBlank( datetime2)){
                json.append(",");
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            //申报中
            if( apply==1){
                json.append("\"beginDate:[* TO  "+df.format(new Date())+"]\",");
                json.append("\"endDate:["+df.format(new Date())+"   TO * ]\"");
            }
            //申报结束
            if( apply==0){
                json.append("\"endDate:[* TO  "+df.format(new Date()) +" ]\"");
            }
        }

        if(StringUtils.isNotBlank( datetime1 )||StringUtils.isNotBlank( datetime2 )||null!=apply){
            json.append("]");
        }
        if(StringUtils.isNotBlank( type )||StringUtils.isNotBlank( libtype )){
            json.append(",\"filterFacet\":{");
        }
        if(StringUtils.isNotBlank( type )){
            json.append("\"_gl_dp_taxonomy_policyTypeTax\":[\""+type+"\"]");
        }
        if(StringUtils.isNotBlank( libtype )){
            if(StringUtils.isNotBlank( type )){
                json.append(",\"_gl_dp_taxonomy_projectOneTax\":[\""+libtype+"\"]");
            }else{
                json.append("\"_gl_dp_taxonomy_projectOneTax\":[\""+libtype+"\"]");
            }
        }
        if(StringUtils.isNotBlank( type )||StringUtils.isNotBlank( libtype )){
            json.append("}");
        }

        json.append( "},\"returnParam\":{\"field\":[\"name\",\"seoword\",\"title\",\"date\",\"datetime\",\"province\",\"city\",\"area\",\"areaGrade\",\"companyProvince\",\"companyCity\",\"companyArea\",\"source\",\"url\",\"shortUrl\",\"sign\",\"productType\",\"issueCompany\",\"nature\",\"status\",\"top\",\"onShow\",\"beginDate\",\"endDate\",\"browsecount\",\"field\",\"applyStatus\",\"originalDate\",\"forward\",\"collection\",\"industry\",\"industryFirst\",\"tecnology\",\"tecnologyFirst\",\"product\",\"productFirst\",\"sort\",\"_gl_dp_taxonomy_policyTypeTax\",\"_gl_dp_taxonomy_projectOneTax\"]," +
                "\"rows\":"+size +",\"start\":"+((current-1))*size+"}}" );

        try {
            String result =   GeeLinkHttpClient.sendUTF8("http://120.79.238.238:9091/v2/api/search",json.toString());
            /*        System.out.println( result );*/
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] arg){
//        querypoliglib("广州",0,0,"","","",10,1);
//    }
    public static  String querypoliglib(String  query,Integer year1,Integer year2,String province,String city,String area,Integer current ,Integer size){
        StringBuffer json = new StringBuffer();
        if(StringUtils.isBlank( query)){
            query="";
        }
        json.append( "{ \"collection\": \"policy\",\"queryParam\": {\"query\": \""+query+"\"" );
        json.append( ",\"filterQuery\": [\"year:[");
        if(null==year1){
            year1=2008;
        }
        if(null==year2){
            year2 =2020;
        }
        json.append( year1+" TO "+year2 +"]\"");
        if(StringUtils.isNotBlank( province )){
            json.append( ",\"province:\\\""+province+"\\\"\"" );
        }
        if(StringUtils.isNotBlank( city )){
            json.append( ",\"city:\\\""+city+"\\\"\"" );
        }
        if(StringUtils.isNotBlank( area )){
            json.append( ",\"area:\\\""+area+"\\\"\"" );
        }
        if(null!=year1||null!=year2||StringUtils.isNotBlank( province )){
            json.append( "]");
        }
        json.append( "}," );
        json.append("\"returnParam\":{\"field\":[\"id\",\"createdate\",\"year\",\"fileno\",\"projecname\",\"specialmum\",\"managerway\",\"supportobj\",\"\",\"applyterm\",\"fundfacilitie\",\"endtime\",\"region\",\"province\",\"city\",\"area\",\"chargedept\",\"remark\",\"technology\",\"industry\",\"hisImportId\",\"str\",\"fundfacilitie\",\"_gl_dp_taxonomy_enterpriseTypeTax\",\"_gl_dp_taxonomy_technicalDomainTax\",\"_gl_dp_taxonomy_industryDomainTax\",\"_gl_dp_taxonomy_competentOrgTax\",\"_gl_dp_taxonomy_projectOneTax\",\"_gl_dp_taxonomy_projectTwoTax\",\"_gl_dp_taxonomy_enterpriseScaleTax \"],\"rows\":"+size+",\"start\":"+((current-1))*size+"}}");
       try {
            String result =   GeeLinkHttpClient.sendUTF8("http://120.79.238.238:9091/v2/api/search",json.toString());
            System.out.println( result );
            return result;
       } catch (IOException e) {
           e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] arg){
//        addfilelibry("library","uploadusername","String");
//    }

//  type  string； pint；pdouble；plong； pdate；pfloat；text_geelink(字 段能进行 gql 搜索时使用此类型)
    public static String  addfilelibry(String molde,String name,String type){
        StringBuffer json = new StringBuffer();
        json.append( "{\"collection\":\""+molde+"\",\"umeFieldList\":[{\"name\":\""+name+"\",\"type\":\""+type+"\",\"indexed\":true,\"stored\":true,\"docValues\":false,\"required\":false,\"multiValued\":false}]}" );
        System.out.println( json.toString() );
       /* try {
            String result =   GeeLinkHttpClient.sendUTF8("http://120.24.87.208:9090/admin/v2/api/schema/field",json.toString());
            return result ;
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return null;
    }



}
