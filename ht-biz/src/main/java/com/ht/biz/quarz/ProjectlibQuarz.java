package com.ht.biz.quarz;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.ImportRecordService;
import com.ht.biz.service.ProjectlibCopyService;
import com.ht.biz.service.ProjectlibService;
import com.ht.commons.support.mysql.DBConnection;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.ReflectHelper;
import com.ht.entity.biz.solr.projectlib.Projectlib;
import com.ht.entity.biz.solr.projectlib.jdbc.BakDBConnection;
import com.ht.entity.biz.solr.recore.ImportRecord;

@Component
@EnableScheduling
public class ProjectlibQuarz {
	
	@Autowired
	private ProjectlibService projectlibService;
	
//	@Autowired
//	private ProjectlibCopyService projectlibCopyService;
	
	@Autowired
	private ImportRecordService importRecordService;
	
	@Value("${solr.dataupload}")
    private boolean dataupload;
	
	public void task(){
		QueryWrapper<ImportRecord> qw=new QueryWrapper<ImportRecord>();
		qw.like("createdate", DateUtil.dateToStr(DateUtil.addDay(new Date(), -1), 11));
		qw.like("tableName", "t_project_lib");
		List<ImportRecord> list=importRecordService.list(qw);
		BakDBConnection bakdbconn = new BakDBConnection();
		StringBuffer subsql=new StringBuffer();
		PageData pd=new PageData();
		for(ImportRecord im:list){
			QueryWrapper<Projectlib> qws=new QueryWrapper<Projectlib>();
			qws.eq("hisImportId", im.getId());
			try {
				bakdbconn.getConnection();
				List<Projectlib> lists=projectlibService.list(qws);
				int k=0;
				for(Projectlib p:lists){
					k++;
					StringBuffer sub=new StringBuffer();
					if(p.getCompanyName()==null){
						sub.append(" and t.companyName is null");
					}else{
						sub.append(" and t.companyName='"+p.getCompanyName()+"'");
					}
					if(p.getName()==null){
						sub.append(" and t.name is null");
					}else{
						sub.append(" and t.name='"+p.getName()+"'");
					}
					if(p.getType()==null){
						sub.append(" and t.type is null");
					}else{
						sub.append(" and t.type='"+p.getType()+"'");
					}
					if(p.getSpecial()==null){
						sub.append(" and t.special is null");
					}else{
						sub.append(" and t.special='"+p.getSpecial()+"'");
					}
					bakdbconn.executeQuerySql("select t.id from t_project_lib t where 1=1"+sub.toString());
					if(bakdbconn.getRs()!=null&&bakdbconn.getRs().next()){
						//重复数据
						//projectlibCopyService.save(p.getProjectlibCopy(p));
						subsql=getSub(p,subsql);
					}else{
						pd.add("sql", sub.toString());
						Integer counts=projectlibService.dealReset(pd);
						if(counts!=null&&counts>1){
							//重复数据
						//	projectlibCopyService.save(p.getProjectlibCopy(p));
							subsql=getSub(p,subsql);
						}
					}
					sub.delete(0, sub.toString().length());
					if(k%100==0){
						bakdbconn.close();
						bakdbconn.getConnection();
					}
				}
				if(!StringUtils.isBlank(subsql.toString())){
					Field[] fs=ReflectHelper.getAllField(Projectlib.class);
					StringBuffer sbf=new StringBuffer();
					for(int i=0;i<fs.length;i++){
						sbf.append(fs[i].getName());
						if(i!=fs.length-1){
							sbf.append(",");
						}
					}
					DBConnection dbconn = new DBConnection(dataupload);
					dbconn.getConnection();
					dbconn.executeSql("insert into t_project_lib_copy ("+sbf.toString()+") values "+subsql.toString().substring(0,subsql.toString().length()-1));
					subsql.delete(0, subsql.toString().length());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				if(bakdbconn!=null){
					bakdbconn.close();
				}
				e.printStackTrace();
			}finally {
				if(bakdbconn!=null){
					bakdbconn.close();
				}
			}	
		}
//		System.out.println("任务执行....");
//		System.out.println("任务执行完毕");
	}
	
	public StringBuffer getSub(Projectlib p,StringBuffer sub) throws Exception{
		sub.append("(");
		Field[] fs=ReflectHelper.getAllField(p.getClass());
		for(int i=0;i<fs.length;i++){
			Object object=ReflectHelper.getValueByFieldName(p, fs[i].getName());
			if(StringUtils.equals(fs[i].getType().getName(), "java.lang.String")){
				object=object==null?"":object;
				sub.append("'"+object+"'");
			}else if(StringUtils.equals(fs[i].getType().getName(), "java.util.Date")){
				object=object==null?null:"'"+DateUtil.dateToStr(DateUtil.StrTodate(String.valueOf(object), "EEE MMM dd HH:mm:ss z yyyy"),12)+"'";
				sub.append(object);
			}else{
				object=object==null?0:object;
				sub.append("'"+object+"'");
			}
			if(i!=fs.length-1){
				sub.append(",");
			}
		}
		sub.append("),");
		return sub;
	}
}
