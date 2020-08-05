package com.ht.biz.service.impl.hisself.xml.impl;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ht.biz.service.impl.hisself.ReadXml;
import com.ht.biz.service.impl.hisself.xml.entity.Model;
import com.ht.biz.service.impl.hisself.xml.entity.ModelValue;
import com.ht.commons.utils.ReflectHelper;


public class ReadXmlImpl implements ReadXml {

	private final static String DEFAULT = "finance.xml";

	private final static String DEFAULT_PATH = String.format("%s", DEFAULT);

	private static List<Model> models = new ArrayList<Model>();

	public static List<Model> getModels() {
		if (models.isEmpty()) {
			try {
				synchronized (models) {
					new ReadXmlImpl().read();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return models;
	}


	public void read() throws Exception {
		try {

			SAXReader reader = new SAXReader();
			InputStream is = getInputStream();
			Document document = reader.read(is);

			Element root = document.getRootElement();

			List<Element> list = root.elements();
			Model model = null;
			for (Element e : list) {
				model = readModel(e);
				List<Element> childs = e.elements();
				if (childs != null && !childs.isEmpty()) {
					ModelValue mv = null;
					for (Element child : childs) {
						mv = readModelValue(child);
						model.getModelValueList().add(mv);
					}
				}
				models.add(model);
			}

			if (is != null) {
				is.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 读取模型
	 */
	public Model readModel(Element e) throws Exception {
		Model model = new Model();
		Field[] fields = Model.class.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] as = field.getAnnotations();
			if (as != null && as.length>0) {
				ReflectHelper.setValueByFieldName(model, field.getName(), e.attributeValue(field.getName()));
			}
		}
		return model;
	}

	public ModelValue readModelValue(Element e) throws Exception {
		ModelValue mv = new ModelValue();
		Field[] fields = ModelValue.class.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] as = field.getAnnotations();
			if (as != null && as.length>0) {
				Object value=null;
				if(StringUtils.equals(field.getType().getName(), double.class.getName())){
					value=Double.parseDouble(e.attributeValue(field.getName()));
				}
				ReflectHelper.setValueByFieldName(mv, field.getName(), value);
			}
		}
		return mv;
	}

	public InputStream getInputStream() {
		return getInputStream(DEFAULT_PATH);
	}

	public InputStream getInputStream(String filePath) {
		InputStream is = null;
		if (StringUtils.isBlank(filePath)) {
			is = ReadXml.class.getResourceAsStream(DEFAULT_PATH);
		} else {
			is = ReadXml.class.getResourceAsStream(filePath);
		}

		return is;
	}
	
	public static void main(String[] args) {
		List<Model> models=ReadXmlImpl.getModels();
		for(Model m:models){
			System.out.println(m);
		}
	}

}
