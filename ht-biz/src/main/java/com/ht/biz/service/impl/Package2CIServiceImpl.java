package com.ht.biz.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.Package2CIMapper;
import com.ht.biz.service.Package2CIService;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.Package2CI;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class Package2CIServiceImpl extends ServiceImpl<Package2CIMapper, Package2CI> implements Package2CIService {

	@Override
	public List<PageData> findByPackageId(String id) {
		// TODO Auto-generated method stub
		return baseMapper.findByPackageId(id);
	}

	@Override
	public void insert(Package2CI package2ci) {
		// TODO Auto-generated method stub
		baseMapper.insert(package2ci);
	}

	@Override
	public void deleted(String id) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(id);
	}

	@Override
	public void deleteByPackageId(String packageId) {
		// TODO Auto-generated method stub
		baseMapper.deleteByPackageId(packageId);
	}

	@Override
	public Package2CI findByIdAndPackageId(Package2CI package2ci) {
		// TODO Auto-generated method stub
		return baseMapper.findByIdAndPackageId(package2ci);
	}

	@Override
	public Object getshortUrl(String baseUrl) {
		// TODO Auto-generated method stub
		Object object=null;
		//ObjectMapper mapper = new ObjectMapper();
		URL connect;
        StringBuffer data = new StringBuffer();
		try {
			connect = new URL(baseUrl);
			HttpURLConnection connection = (HttpURLConnection) connect.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(50000);
			//connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("contentType", "utf-8");
			OutputStreamWriter paramout = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			paramout.write(new JSONObject().toString());
			paramout.flush();
			InputStream inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = reader.readLine()) != null) {
			    data.append(line);
			}
			paramout.close();
			reader.close();
			//object=JSONObject.fromObject(data);
			JSONArray array=JSONArray.fromObject(data.toString());
			Iterator<Object> it=array.iterator();
	    	while(it.hasNext()){
	    		JSONObject btn = (JSONObject) it.next();
	    		object=btn.get("url_short");
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	
}
