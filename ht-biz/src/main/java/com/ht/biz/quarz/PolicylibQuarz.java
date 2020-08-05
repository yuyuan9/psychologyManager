package com.ht.biz.quarz;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.ImportRecordService;
import com.ht.biz.service.PolicylibService;
import com.ht.commons.support.mysql.DBConnection;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.solr.policylib.Policylib;
import com.ht.entity.biz.solr.recore.ImportRecord;

@Component
@EnableScheduling
public class PolicylibQuarz {

	@Autowired
	private PolicylibService policylibService;
	
	@Autowired
	private ImportRecordService importRecordService;
	
	@Value("${solr.dataupload}")
    private boolean dataupload;
	
	public static final String strings="year,fileno,projecname,specialmum,managerway,"
			                   + "supportobj,applyterm,fundfacilitie,endtime,region,"
			                   + "province,city,area,chargedept,linkman,remark,"
			                   + "technology,industry,hisImportId,source,"
			                   + "id,createdate,createid,lastmodified,"
			                   + "regionid";
	
	public void task(){
		QueryWrapper<ImportRecord> qw=new QueryWrapper<ImportRecord>();
		qw.like("createdate", DateUtil.dateToStr(DateUtil.addDay(new Date(), -1), 11));
		qw.like("tableName", "t_policy_lib");
		try {
			List<ImportRecord> list=importRecordService.list(qw);
			StringBuffer subsql=new StringBuffer();
			StringBuffer sbf=new StringBuffer(strings);
			PageData pd=new PageData();
			for(ImportRecord im:list){
				QueryWrapper<Policylib> qws=new QueryWrapper<Policylib>();
				qws.eq("hisImportId", im.getId());
				List<Policylib> lists=policylibService.list(qws);
				for(Policylib p:lists){
					StringBuffer sub=new StringBuffer();
					if(p.getProjecname()==null){
						sub.append(" and t.projecname is null");
					}else{
						sub.append(" and t.projecname='"+p.getProjecname()+"'");
					}
					if(p.getSpecialmum()==null){
						sub.append(" and t.specialmum is null");
					}else{
						sub.append(" and t.specialmum='"+p.getSpecialmum()+"'");
					}
					if(p.getManagerway()==null){
						sub.append(" and t.managerway is null");
					}else{
						sub.append(" and t.managerway='"+p.getManagerway()+"'");
					}
					if(p.getSupportobj()==null){
						sub.append(" and t.supportobj is null");
					}else{
						sub.append(" and t.supportobj='"+p.getSupportobj()+"'");
					}
					pd.add("sql", sub.toString());
					Integer counts=policylibService.dealReset(pd);
					if(counts!=null&&counts>1){
						subsql=getSub(p,subsql);
					}
					sub.delete(0, sub.toString().length());
				}
				if(!StringUtils.isBlank(subsql.toString())){
					DBConnection dbconn = new DBConnection(dataupload);
					dbconn.getConnection();
					dbconn.executeSql("insert into t_policy_lib_copy ("+sbf.toString()+") values "+subsql.toString().substring(0,subsql.toString().length()-1));
					subsql.delete(0, subsql.toString().length());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public StringBuffer getSub(Policylib p,StringBuffer sub) throws Exception{
		sub.append("(");
		sub.append(p.getYear()==null?null:"'"+p.getYear()+"'"+",");
		sub.append(p.getFileno()==null?null:"'"+p.getFileno()+"'"+",");
		sub.append(p.getProjecname()==null?null:"'"+p.getProjecname()+"'"+",");
		sub.append(p.getSpecialmum()==null?null:"'"+p.getSpecialmum()+"'"+",");
		sub.append(p.getManagerway()==null?null:"'"+p.getManagerway()+"'"+",");
		sub.append(p.getSupportobj()==null?null:"'"+p.getSupportobj()+"'"+",");
		sub.append(p.getApplyterm()==null?null:"'"+p.getApplyterm()+"'"+",");
		sub.append(p.getFundfacilitie()==null?null:"'"+p.getFundfacilitie()+"'"+",");
		sub.append(p.getEndtime()==null?null:"'"+p.getEndtime()+"'"+",");
		sub.append(p.getRegion()==null?null:"'"+p.getRegion()+"'"+",");
		sub.append(p.getProvince()==null?null:"'"+p.getProvince()+"'"+",");
		sub.append(p.getCity()==null?null:"'"+p.getCity()+"'"+",");
		sub.append(p.getArea()==null?null:"'"+p.getArea()+"'"+",");
		sub.append(p.getChargedept()==null?null:"'"+p.getChargedept()+"'"+",");
		sub.append(p.getLinkman()==null?null:"'"+p.getLinkman()+"'"+",");
		sub.append(p.getRemark()==null?null:"'"+p.getRemark()+"'"+",");
		sub.append(p.getTechnology()==null?null:"'"+p.getTechnology()+"'"+",");
		sub.append(p.getIndustry()==null?null:"'"+p.getIndustry()+"'"+",");
		sub.append(p.getHisImportId()==null?null:"'"+p.getHisImportId()+"'"+",");
		sub.append(p.getSource()==null?null:"'"+p.getSource()+"'"+",");
		sub.append(p.getId()==null?null:"'"+p.getId()+"'"+",");
		sub.append((p.getCreatedate()==null?null:"'"+DateUtil.dateToStr(p.getCreatedate(), 12)+"'")+",");
		sub.append(p.getCreateid()==null?null:"'"+p.getCreateid()+"'"+",");
		sub.append((p.getLastmodified()==null?null:"'"+DateUtil.dateToStr(p.getLastmodified(), 12)+"'")+",");
		sub.append(p.getRegionid()==null?null:"'"+p.getRegionid()+"'"+"");
		sub.append("),");
		return sub;
	}
}
