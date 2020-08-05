package com.ht.commons.support.excel;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.ht.commons.constants.Const.Code;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.ReflectHelper;

//excel处理类
public class Excel {
	//全局变量（声明一个excel表）
	Workbook workbook = null ;
	
	/*
	 * 导入
	 * number为导入开始行
	 * 记录错误行
	 */
	public static Respon excelImport(MultipartFile file,Object object,Integer number,Map map) throws Exception{
		Respon respon =new Respon();
		//用来存放导入的数据
		List list=new ArrayList();
		//统计所有的出错的行
		List<Integer> listint=new ArrayList<Integer>();
		//拼接错误的行数
		StringBuffer sub=new StringBuffer();
		//  1、用HSSFWorkbook打开或者创建“Excel文件对象”
		//
		//  2、用HSSFWorkbook对象返回或者创建Sheet对象
		//
		//  3、用Sheet对象返回行对象，用行对象得到Cell对象
		//
		//  4、对Cell对象读写。
		//获得文件名 
		Workbook workbook = getWorkbook(file) ;
		if(workbook!=null){
			Sheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
			Class clazz=object.getClass();
			Field[] fields=clazz.getDeclaredFields();
			Field.setAccessible(fields,true); 
			int cols=sheet.getRow(0).getPhysicalNumberOfCells();
			for (int i = number; i <= rows; i++) {
				Row row = sheet.getRow(i);
				try {
					getObject(fields, cols, object, row);
				} catch (Exception e) {
					listint.add(i);
				}
				list.add(object);
				if(list.size()>2000){
					break;
				}
				object=object.getClass().newInstance();
			}
		}
		respon=getRespon(respon,listint,list,sub);
		return respon;
	}
	/*
	 * 导出
	 * list为空时下载模板
	 * object为导出对象
	 * suffix为文件后缀.xls或者.xlsx
	 * strings为导出具体名单（类的字段）
	 * fileName为导出文件名
	 * workName为导出文件第一个工作表的名称（默认为work0）
	 */
	@SuppressWarnings("resource")
	public static void excelExport(HttpServletResponse response,List list,Object object,String suffix,String[] strings,String fileName,String workName) throws Exception{
		//创建HSSFWorkbook对象(excel的文档对象)  
		Workbook workbook = null ;
		if(!StringUtils.isBlank(suffix)&&StringUtils.equals(suffix, ".xlsx")){ 
		    //2007 
		    workbook = new XSSFWorkbook(); 
		}else{
			//2003
			suffix=".xls";
			workbook = new HSSFWorkbook(); 
		}
		//建立新的sheet对象（excel的表单） 
		Sheet sheet = workbook.createSheet(StringUtils.isBlank(workName)?"work0":workName);
		//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		Row row = sheet.createRow(0);
		//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
		Cell cell=row.createCell(0);
		//设置单元格内容
		cell.setCellValue(StringUtils.isBlank(workName)?"work0":workName);
		CellStyle cellStyle =workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cell.setCellStyle(cellStyle);
		//通过反射获取列数
		Field[] fields=object.getClass().getDeclaredFields();
		Field.setAccessible(fields,true); 
		//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,strings.length-1));
		//创建第二行
		Row row2 = sheet.createRow(1);
		for(int i=0;i<strings.length;i++){
			row2.createCell(i).setCellValue(strings[i]);
		}
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Row rows = sheet.createRow(i+2);
				object=list.get(i);
				for(int k=0;k<strings.length;k++){
					Field field=fields[k];
					if(StringUtils.equals(field.getType().getName(), "java.lang.String")){
						rows.createCell(k).setCellValue(field.get(object)==null?"":String.valueOf(field.get(object)));
					}else if(StringUtils.equals(field.getType().getName(), "java.util.Date")){
						Date date=field.get(object)==null?null:(Date)field.get(object);
						if(date==null){
							rows.createCell(k).setCellValue("");
						}else{
							rows.createCell(k).setCellValue(DateUtil.dateToStr(date, 11));
						}
					}else if(StringUtils.equals(field.getType().getName(), "java.lang.Boolean")||StringUtils.equals(field.getType().getName(), "boolean")){
						rows.createCell(k).setCellValue(field.get(object)==null?"":String.valueOf(field.get(object)));
					}else{
						rows.createCell(k).setCellValue(field.get(object)==null?"0":String.valueOf(field.get(object)));
					}
					
				}
			}
		}
		OutputStream output=response.getOutputStream();  
		response.reset();  
		if(StringUtils.isBlank(fileName)){
			fileName=DateUtil.dateToStr(new Date(),5);
		}
		response.setHeader("Content-disposition", "attachment; filename="+URLEncoder.encode(fileName, "utf-8")+suffix);  
		response.setContentType("application/msexcel");          
		workbook.write(output);  
		output.close();
	}
	/*
	 * 导出指定数据（非全部）
	 * list为空时下载模板
	 * object为导出对象
	 * suffix为文件后缀.xls或者.xlsx
	 * fileName为导出文件名
	 * workName为导出文件第一个工作表的名称（默认为work0）
	 * Columns 导出对应数据
	 * titles要带出的列名集合
	 */
	@SuppressWarnings("resource")
	public static void excelAppoint(HttpServletResponse response,List list,Object object,String suffix,String fileName,String workName,String[] Columns,String[] titles) throws Exception{
		//创建HSSFWorkbook对象(excel的文档对象)  
				Workbook workbook = null ;
				if(!StringUtils.isBlank(suffix)&&StringUtils.equals(suffix, ".xlsx")){ 
				    //2007 
				    workbook = new XSSFWorkbook(); 
				}else{
					//2003
					suffix=".xls";
					workbook = new HSSFWorkbook(); 
				}
				//建立新的sheet对象（excel的表单） 
				Sheet sheet = workbook.createSheet(StringUtils.isBlank(workName)?"work0":workName);
				//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
				Row row = sheet.createRow(0);
				//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
				Cell cell=row.createCell(0);
				//设置单元格内容
				cell.setCellValue(StringUtils.isBlank(workName)?"work0":workName);
				CellStyle cellStyle =workbook.createCellStyle();
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				//设置边框
				cellStyle.setBorderBottom(BorderStyle.THIN);; //下边框  
				cellStyle.setBorderLeft(BorderStyle.THIN);//左边框  
				cellStyle.setBorderTop(BorderStyle.THIN);//上边框  
				cellStyle.setBorderRight(BorderStyle.THIN);//右边框 
				cell.setCellStyle(cellStyle);
				//设置单元格格式
				CellStyle cells =workbook.createCellStyle();
				cells.setAlignment(HorizontalAlignment.LEFT);
				cells.setVerticalAlignment(VerticalAlignment.TOP);
				//设置自动换行
				cells.setWrapText(true);
				//设置边框
				cells.setBorderBottom(BorderStyle.THIN);; //下边框  
				cells.setBorderLeft(BorderStyle.THIN);//左边框  
				cells.setBorderTop(BorderStyle.THIN);//上边框  
				cells.setBorderRight(BorderStyle.THIN);//右边框 
				//通过反射获取列数
				Field[] fields=ReflectHelper.getAllField(object.getClass());
				Field.setAccessible(fields,true); 
				//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
				sheet.addMergedRegion(new CellRangeAddress(0,0,0,titles.length));
				Row row2 = sheet.createRow(1);
				Cell cell2=row2.createCell(0);
				cell2.setCellValue("序号");
				cell2.setCellStyle(cellStyle);
				for(int i=0;i<titles.length;i++){
					sheet.setColumnWidth(i+1, 9100);
					Cell cell3=row2.createCell(i+1);
					cell3.setCellValue(titles[i]);
					cell3.setCellStyle(cellStyle);
				}
				if(list!=null){
					for(int i=0;i<list.size();i++){
						Row rows = sheet.createRow(i+2);
						//rows.setHeight((short)(200*10));
						object=list.get(i);
						Cell cell0=rows.createCell(0);
						cell0.setCellValue(i+1);
						cell0.setCellStyle(cells);
						//rows.createCell(0).setCellValue(i+1);
						for(int k=0;k<fields.length;k++){
							Field field=fields[k];
							for(int m=1;m<=Columns.length;m++){
								
								if(StringUtils.equals(field.getName(), Columns[m-1])){
									if(StringUtils.equals(field.getType().getName(), "java.lang.String")){
										Cell cell4=rows.createCell(m);
										cell4.setCellValue(field.get(object)==null?"":String.valueOf(field.get(object)));
										cell4.setCellStyle(cells);
									}else if(StringUtils.equals(field.getType().getName(), "java.util.Date")){
										Date date=field.get(object)==null?null:(Date)field.get(object);
										if(date==null){
											Cell cell4=rows.createCell(m);
											cell4.setCellValue("");
											cell4.setCellStyle(cells);
										}else{
											Cell cell4=rows.createCell(m);
											cell4.setCellValue(DateUtil.dateToStr(date, 11));
											cell4.setCellStyle(cells);
										}
									}else if(StringUtils.equals(field.getType().getName(), "java.lang.Boolean")||StringUtils.equals(field.getType().getName(), "boolean")){
										Cell cell4=rows.createCell(m);
										cell4.setCellValue(field.get(object)==null?"":String.valueOf(field.get(object)));
										cell4.setCellStyle(cells);
									}else{
										Cell cell4=rows.createCell(m);
										cell4.setCellValue(field.get(object)==null?"0":String.valueOf(field.get(object)));
										cell4.setCellStyle(cells);
									}
								}
							}
						}
					}
				}
				OutputStream output=response.getOutputStream();  
				response.reset();  
				if(StringUtils.isBlank(fileName)){
					fileName=DateUtil.dateToStr(new Date(),5);
				}
				response.setHeader("Content-disposition", "attachment; filename="+URLEncoder.encode(fileName, "utf-8")+suffix);  
				response.setContentType("application/msexcel");          
				workbook.write(output);  
				output.close();
	}
	public static Workbook getWorkbook(MultipartFile file){
		Workbook workbook = null ;
		try {
			String fileName=file.getOriginalFilename(); 
			if(fileName.endsWith("xls")){ 
				//2003 
			    workbook = new HSSFWorkbook(file.getInputStream()); 
			}else if(fileName.endsWith("xlsx")){ 
			    //2007 
			    workbook = new XSSFWorkbook(file.getInputStream()); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workbook;
	}
	public static Object getObject(Field[] fields,int cols,Object object,Row row) throws Exception{
		for(int k=0;k<cols;k++){
			if(StringUtils.equals(fields[k].getType().getName(), "java.lang.String")){
				if(row.getCell(k)!=null&&!row.getCell(k).toString().equals("")){
					fields[k].set(object, row.getCell(k).toString());
				}else{
					fields[k].set(object, null);
				}
			}else if(StringUtils.equals(fields[k].getType().getName(), "java.util.Date")){
				Date date = null;
				if(row.getCell(k)!=null&&!row.getCell(k).toString().equals("")){
					double d = Double.valueOf(row.getCell(k).getNumericCellValue());
					date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(d);
				}
				fields[k].set(object, date);
			}else if(StringUtils.equals(fields[k].getType().getName(), "java.lang.Integer")||StringUtils.equals(fields[k].getType().getName(), "int")){
				if(row.getCell(k)!=null&&!row.getCell(k).toString().equals("")){
					fields[k].set(object, (int)row.getCell(k).getNumericCellValue());
				}else{
					fields[k].set(object, (int)0);
				}
			}else if(StringUtils.equals(fields[k].getType().getName(), "java.lang.Boolean")||StringUtils.equals(fields[k].getType().getName(), "boolean")){
				if(row.getCell(k)!=null&&!row.getCell(k).toString().equals("")){
					fields[k].set(object, row.getCell(k).getBooleanCellValue());
				}else{
					fields[k].set(object, false);
				}
			}else if(StringUtils.equals(fields[k].getType().getName(), "java.lang.Double")||StringUtils.equals(fields[k].getType().getName(), "double")){
				if(row.getCell(k)!=null&&!row.getCell(k).toString().equals("")){
					fields[k].set(object, Double.valueOf(row.getCell(k).getNumericCellValue()));
				}else{
					fields[k].set(object, 0.0);
				}
			}else if(StringUtils.equals(fields[k].getType().getName(), "java.lang.Long")||StringUtils.equals(fields[k].getType().getName(), "long")){
				if(row.getCell(k)!=null&&!row.getCell(k).toString().equals("")){
					fields[k].set(object, (long)row.getCell(k).getNumericCellValue());
				}else{
					fields[k].set(object, (long)0);
				}
			}else if(StringUtils.equals(fields[k].getType().getName(), "java.lang.Float")||StringUtils.equals(fields[k].getType().getName(), "float")){
				if(row.getCell(k)!=null&&!row.getCell(k).toString().equals("")){
					fields[k].set(object, (float)row.getCell(k).getNumericCellValue());
				}else{
					fields[k].set(object, (float)0);
				}
			}else if(StringUtils.equals(fields[k].getType().getName(), "java.lang.Short")||StringUtils.equals(fields[k].getType().getName(), "short")){
				if(row.getCell(k)!=null&&!row.getCell(k).toString().equals("")){
					fields[k].set(object, (short)row.getCell(k).getNumericCellValue());
				}else{
					fields[k].set(object, (short)0);
				}
			}
	}
		return object;
	}
	public static Respon getRespon(Respon respon,List<Integer> listint,List list,StringBuffer sub){
		if(listint.size()>0){
			sub.append("第");
			for(int k=0;k<listint.size();k++){
				if(k==listint.size()-1){
					sub.append(listint.get(k)+1);
				}else{
					sub.append((listint.get(k)+1)+"、");
				}
			}
			sub.append("行数据错误，导入失败");
			respon.setCode(Code.C10110.getKey());
			respon.setMsg(sub.toString());
			respon.setData(null);
		}else if(list.size()>2000){
			respon.setCode(Code.C10110.getKey());
			respon.setMsg("本次导入大于2000行拒绝导入");
			respon.setData(null);
		}else{
			respon.setCode(Code.C10000.getKey());
			respon.setData(list);
		}
		return respon;
	}
}
