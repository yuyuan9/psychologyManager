package com.ht.biz.service.impl;

import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ht.commons.support.geelink.entity.GeelinkPolicyDig;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.PolicyDigMapper;
import com.ht.biz.service.PolicyDigService;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.RequestUtils;
import com.ht.entity.policydig.PolicyDig;
import com.ht.utils.GaodeRegionUtil;
@Service
public class PolicyDigServiceImpl extends ServiceImpl<PolicyDigMapper, PolicyDig> implements PolicyDigService {

	private static final String speProvince="北京市天津市上海市重庆市深圳市";
	private static final String or=" or ";
	
	@Override
	public PolicyDig findById(String id) {
		// TODO Auto-generated method stub
		return baseMapper.selectById(id);
	}

	@Override
	public List<PolicyDig> findlistPage(MyPage<PolicyDig> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.findlistPage(mypage);
	}

	@Override
	public void edit(PolicyDig policyDig) {
		// TODO Auto-generated method stub
		baseMapper.updateById(policyDig);
	}

	@Override
	public void deleted(PolicyDig policyDig) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(policyDig.getId());
	}
	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(id);
	}

	@Override
	public void insert(PolicyDig policyDig) {
		// TODO Auto-generated method stub
		baseMapper.insert(policyDig);
	}

	@Override
	public List<PolicyDig> findV3listPage(MyPage<PolicyDig> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.findV3listPage(mypage);
	}

	@Override
	public void updateStatus(PageData pd) {
		// TODO Auto-generated method stub
		baseMapper.updateStatus(pd);
	}

	@Override
	public void updateTop(PolicyDig policyDig) {
		// TODO Auto-generated method stub
		baseMapper.updateTop(policyDig);
	}

	@Override
	public List<PolicyDig> findlist(MyPage<PolicyDig> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.findlist(mypage);
	}

	@Override
	public List<PolicyDig> subscribeList(MyPage<PolicyDig> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.subscribeList(mypage);
	}

	@Override
	public void getCookies(PageData pd, HttpServletRequest request, String memory, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			if(!StringUtils.isBlank(memory)&&StringUtils.equals(memory, "1")){
				Cookie[] cs=request.getCookies();
				if(cs!=null){
					for(int i=0;i<cs.length;i++){
						if(cs[i].getName().equals("province")){
							String string=URLDecoder.decode(cs[i].getValue(),"UTF-8");
							if(StringUtils.contains(string, "国家")){
								pd.add("province","国家");
							}else{
								pd.add("province", URLDecoder.decode(cs[i].getValue(),"UTF-8"));
							}
				        }else if(cs[i].getName().equals("city")){
				        	pd.add("city", URLDecoder.decode(cs[i].getValue(),"UTF-8"));
				        }else if(cs[i].getName().equals("area")){
				        	pd.add("area", URLDecoder.decode(cs[i].getValue(),"UTF-8"));
				        }
					}
				}
				//cooike没有取定位
				if(pd.get("province")==null||StringUtils.equals("", pd.getString("province"))){
					Map<String, String> map=GaodeRegionUtil.getAddrName(GaodeRegionUtil.getPublicIP());
					pd.add("province", map.get("province"));
					pd.add("city", map.get("city"));
					pd.add("area", "");
				}
			}else if(!StringUtils.isBlank(memory)&&StringUtils.equals(memory, "2")){
				Cookie province=new Cookie("province",URLEncoder.encode(StringUtils.isBlank(pd.getString("province"))?"":pd.getString("province"),"UTF-8"));
				Cookie city=new Cookie("city",URLEncoder.encode(StringUtils.isBlank(pd.getString("city"))?"":pd.getString("city"),"UTF-8"));
				Cookie area=new Cookie("area",URLEncoder.encode(StringUtils.isBlank(pd.getString("area"))?"":pd.getString("area"),"UTF-8"));
				province.setMaxAge(60*60*24*30);city.setMaxAge(60*60*24*30);area.setMaxAge(60*60*24*30);
				province.setPath("/");city.setPath("/");area.setPath("/");
				response.addCookie(province);response.addCookie(city);response.addCookie(area);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<PolicyDig> searchlist(MyPage<PolicyDig> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.searchlist(mypage);
	}

	@Override
	public List<GeelinkPolicyDig> geelinkfindall() {
		return baseMapper.geelinkfindall();
	}
	//省市区复选框选择
	public String getRegionSql(String province,String city,String area,String countrys,String provinces,String citys,String areas){
		StringBuffer subsql=new StringBuffer();
		String ss="select * from policydigs t where 1=1 and ";
		if(StringUtils.isNotBlank(countrys)){
			subsql.append("(t.province LIKE CONCAT(CONCAT('%', '国家'),'%')) or ");
		}
		if(StringUtils.isNotBlank(provinces)){
			if(speProvince.contains(province)){
				subsql.append(String.format("(t.city LIKE CONCAT(CONCAT('%s', '%s'),'%s') and ifnull(t.area,'')='') or ", "%",province,"%"));
			}else{
				subsql.append(String.format("(t.province LIKE CONCAT(CONCAT('%s', '%s'),'%s') and ifnull(t.city,'')='' and ifnull(t.area,'')='') or ", "%",province,"%"));
			}
		}
		if(StringUtils.isNotBlank(citys)){
			if(speProvince.contains(province)){
				subsql.append(String.format("(t.city LIKE CONCAT(CONCAT('%s', '%s'),'%s') and t.area like CONCAT(CONCAT('%s', '%s'),'%s')) or ","%",province,"%", "%",city,"%"));
			}else{
				subsql.append(String.format("(t.province LIKE CONCAT(CONCAT('%s', '%s'),'%s') and t.city like CONCAT(CONCAT('%s', '%s'),'%s') and ifnull(t.area,'')='') or ","%",province,"%", "%",city,"%"));
			}
		}
		if(StringUtils.isNotBlank(areas)){
			subsql.append(String.format("(t.province LIKE CONCAT(CONCAT('%s', '%s'),'%s') and t.city like CONCAT(CONCAT('%s', '%s'),'%s') and t.area like CONCAT(CONCAT('%s', '%s'),'%s')) or ","%",province,"%", "%",city,"%", "%",area,"%"));
		}
		if(StringUtils.isNotBlank(subsql.toString())){
			ss="("+ss+"("+StringUtils.removeEnd(subsql.toString()," or ")+"))tt";
		}else{
			ss=StringUtils.removeEnd(ss, "and");
		}
		return ss;
	}
	//正常省市区选择
	public String getSql(String province,String city,String area){
		String p="(t.province LIKE CONCAT(CONCAT('%s', '%s'),'%s') and ifnull(t.city,'')='' and ifnull(t.area,'')='')";
		String pc="(t.province LIKE CONCAT(CONCAT('%s', '%s'),'%s') and t.city like CONCAT(CONCAT('%s', '%s'),'%s') and ifnull(t.area,'')='')";
		String pca="(t.province LIKE CONCAT(CONCAT('%s', '%s'),'%s') and t.city like CONCAT(CONCAT('%s', '%s'),'%s') and t.area like CONCAT(CONCAT('%s', '%s'),'%s'))";
		String c="(t.city LIKE CONCAT(CONCAT('%s', '%s'),'%s') and ifnull(t.area,'')='')";
		String ca="(t.city LIKE CONCAT(CONCAT('%s', '%s'),'%s') and t.area like CONCAT(CONCAT('%s', '%s'),'%s'))";
		StringBuffer subsql=new StringBuffer();
		//subsql.append("(");
		//字符串中包含有%,String.format会报异常
		if(!StringUtils.isBlank(province)){
			subsql.append("select * from policydigs t where 1=1 and t.province LIKE CONCAT(CONCAT('%', '国家'),'%') or ");
		}
		if(!StringUtils.isBlank(province)&&!StringUtils.equals(province, "国家")){
			if(speProvince.contains(province)){
				subsql.append(String.format(c,"%",province,"%")).append(or);
			}else{
				subsql.append(String.format(p,"%",province,"%")).append(or);
			}
		}
		if(!StringUtils.isBlank(province) && !StringUtils.isBlank(city)){
			if(speProvince.contains(province)){
				subsql.append(String.format(ca,"%",province,"%","%",city,"%")).append(or);
			}else{
				subsql.append(String.format(pc,"%",province,"%","%",city,"%")).append(or);
			}
		}
		if(!StringUtils.isBlank(province) && !StringUtils.isBlank(city) && !StringUtils.isBlank(area)){
			subsql.append(String.format(pca,"%",province,"%","%",city,"%","%",area,"%")).append(or);
		}
		//subsql.append(")tt");
		if(StringUtils.isNotBlank(subsql.toString())){
			return "("+StringUtils.removeEnd(subsql.toString(), or)+")tt";
		}else{
			return "policydigs";
		}
		
	}
	public static void main(String[] args) {
		PolicyDigServiceImpl p=new PolicyDigServiceImpl();
		System.out.println(p.getRegionSql("国家", null, null, "1", null, null, null));
		System.out.println(p.getRegionSql("广东省", null, null, "1", "1", null, null));
		System.out.println(p.getRegionSql("广东省", "广州市", null, "1", "1", "1", null));
		System.out.println(p.getRegionSql("广东省", "广州市", "天河区", "1", "1", "1", "1"));
		System.out.println(p.getRegionSql("北京市", "朝阳区", null, "1", "1", null, null));
		System.out.println(p.getRegionSql("北京市", "朝阳区", null, "1", "1", "1", null));
		System.out.println(p.getRegionSql("深圳市", "昌平区", null, "1", "1", null, null));
		System.out.println(p.getRegionSql("深圳市", "昌平区", null, "1", "1", "1", null));
	}
	//pc端
	@Override
	public String getPcStr(String regions,String province, String city, String area, String countrys, String provinces, String citys,
			String areas) {
		// TODO Auto-generated method stub
		if(StringUtils.equals(regions, "0")){
			return getRegionSql(province,city,area,countrys,provinces,citys,areas);
		}else{
			return getSql(province,city,area);
		}
	}
	//小程序
	@Override
	public String getSpStr(String province, String city, String area) {
		// TODO Auto-generated method stub
		return getSql(province,city,area);
	}
	//手机端
	@Override
	public String getphoneStr(String province, String city, String area) {
		// TODO Auto-generated method stub
		return getSql(province,city,area);
	}

	@Override
	public List<PolicyDig> centerlist(MyPage<PolicyDig> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.centerlist(mypage);
	}

	@Override
	public List<PolicyDig> appnewlist(MyPage<PolicyDig> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.appnewlist(mypage);
	}
	
}
