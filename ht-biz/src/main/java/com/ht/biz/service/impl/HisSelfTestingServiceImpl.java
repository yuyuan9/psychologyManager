package com.ht.biz.service.impl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.HisSelfTestingMapper;
import com.ht.biz.service.HisSelfTestingService;
import com.ht.commons.support.pdf.PdfService;
import com.ht.commons.support.pdf.PdfServiceImpl;
import com.ht.commons.utils.MyPage;
import com.ht.entity.biz.freeassess.HisSelfTesting;
@Service
public class HisSelfTestingServiceImpl extends ServiceImpl<HisSelfTestingMapper,HisSelfTesting>implements HisSelfTestingService {
	
	private PdfService pdfService=new PdfServiceImpl();
	
	@Override
	public List<HisSelfTesting> findList(MyPage<HisSelfTesting> page) {
		// TODO Auto-generated method stub
		return baseMapper.findList(page);
	}
	/*
	 * 导出pdf
	 */
	@Override
	public void exportpdfs(Collection<Map<String, String>> forms, OutputStream outputStream, String path,int size) {
		// TODO Auto-generated method stub
		try {
			File template = new File(path);
			pdfService.batchBuildAndMerge(template, forms, outputStream,size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String getHisSelfTesting(Map<String,Object> table1,HisSelfTesting testing) {
		// TODO Auto-generated method stub
		String s=null;
		if(StringUtils.isNotBlank(testing.getYearToString())){
			s=testing.getYearToString().replace("<s class='sign wrong'></s>", "").replace("<s class='sign right'></s>", "");
			table1.put("yearToString", s);
		}
		if(StringUtils.isNotBlank(testing.getNologyToString())){
			s=testing.getNologyToString().replace("<s class='sign wrong'></s>", "").replace("<s class='sign right'></s>", "");
			table1.put("nologyToString", s);
		}
		if(StringUtils.isNotBlank(testing.getTechToString())){
			s=testing.getTechToString().replace("<s class='sign wrong'></s>", "").replace("<s class='sign right'></s>", "");
			table1.put("techToString", s);
		}
		if(StringUtils.isNotBlank(testing.getPersonToString())){
			s=testing.getPersonToString().replace("<s class='sign wrong'></s>", "").replace("<s class='sign right'></s>", "");
			table1.put("personToString", s);
		}
		if(StringUtils.isNotBlank(testing.getCostToString())){
			s=testing.getCostToString().replace("<s class='sign wrong'></s>", "").replace("<s class='sign right'></s>", "");
			table1.put("costToString", s);
		}
		if(StringUtils.isNotBlank(testing.getIncomeToString())){
			s=testing.getIncomeToString().replace("<s class='sign wrong'></s>", "").replace("<s class='sign right'></s>", "");
			table1.put("incomeToString", s);
		}
		if(StringUtils.isNotBlank(testing.getCompopToString())){
			s=testing.getCompopToString().replace("<s class='sign wrong'></s>", "").replace("<s class='sign right'></s>", "");
			table1.put("compopToString", s);
		}
		s=null;
		if(StringUtils.isNotBlank(testing.getCity())){
			if(StringUtils.equals("淮安市", testing.getCity())||StringUtils.equals("广州市", testing.getCity())||StringUtils.equals("苏州市", testing.getCity())){
				if("A".equals(testing.getSaleyfrate())){
					table1.put("saleyfrate", "销售收入≤5000万，≥5%");
				}else if("B".equals(testing.getSaleyfrate())){
					table1.put("saleyfrate", "5000万＜销售收入≤2亿，≥4%");
				}else if("C".equals(testing.getSaleyfrate())){
					table1.put("saleyfrate", "销售收入＞2亿，≥3%");	
				}else if("D".equals(testing.getSaleyfrate())){
					table1.put("saleyfrate", "近2年研发费用＜3%");
				}else{
					table1.put("saleyfrate", "近2年研发费用，＜3%");
				}
				if("A".equals(testing.getJnyfrate())){
					table1.put("jnyfrate", "≥60%");
				}else if("B".equals(testing.getJnyfrate())){
					table1.put("jnyfrate", "＜60%");
				}
				if("A".equals(testing.getProdrate())){
					table1.put("prodrate", "≥60%");
				}else if("B".equals(testing.getProdrate())){
					table1.put("prodrate", "50%≤R＜60%");
				}else if("C".equals(testing.getProdrate())){
					table1.put("prodrate", "＜50%");	
				}
			}else if(StringUtils.equals("金华市", testing.getCity())){
				if("A".equals(testing.getSaleyfrate())){
					table1.put("saleyfrate", "销售收入≤5000万，≥5%");
				}else if("B".equals(testing.getSaleyfrate())){
					table1.put("saleyfrate", "5000万＜销售收入≤2亿，≥4%");
				}else if("C".equals(testing.getSaleyfrate())){
					table1.put("saleyfrate", "销售收入＞2亿，≥3%");	
				}else if("D".equals(testing.getSaleyfrate())){
					table1.put("saleyfrate", "近1年研发费用＜3%");
				}else{
					table1.put("saleyfrate", "近1年研发费用，＜3%");
				}
				if("A".equals(testing.getJnyfrate())){
					table1.put("jnyfrate", "≥60%");
				}else if("B".equals(testing.getJnyfrate())){
					table1.put("jnyfrate", "＜60%");
				}
				if("A".equals(testing.getProdrate())){
					table1.put("prodrate", "≥60%");
				}else if("B".equals(testing.getProdrate())){
					table1.put("prodrate", "50%≤R＜60%");
				}else if("C".equals(testing.getProdrate())){
					table1.put("prodrate", "＜50%");	
				}
			}else{
				if("A".equals(testing.getSaleyfrate())){
					table1.put("saleyfrate", "销售收入≤5000万，≥5%");
				}else if("B".equals(testing.getSaleyfrate())){
					table1.put("saleyfrate", "5000万＜销售收入≤2亿，≥4%");
				}else if("C".equals(testing.getSaleyfrate())){
					table1.put("saleyfrate", "销售收入＞2亿，≥3%");	
				}
				if("A".equals(testing.getJnyfrate())){
					table1.put("jnyfrate", "≥60%");
				}else if("B".equals(testing.getJnyfrate())){
					table1.put("jnyfrate", "＜60%");
				}
				if("A".equals(testing.getProdrate())){
					table1.put("prodrate", "≥60%");
				}else if("B".equals(testing.getProdrate())){
					table1.put("prodrate", "＜60%");
				}
			}
			if(StringUtils.equals("淮安市", testing.getCity())){
				s="huaian";
			}else if(StringUtils.equals("广州市", testing.getCity())){
				s="guangzhou";
			}else if(StringUtils.equals("金华市", testing.getCity())){
				s="jinhua";
			}else if(StringUtils.equals("苏州市", testing.getCity())){
				s="suzhou";
			}else{
				s="quanguo";
			}
		}
		return s;
	}

}
