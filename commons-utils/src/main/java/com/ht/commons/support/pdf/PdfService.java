package com.ht.commons.support.pdf;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;


/**
 * pdf生成器
 *
 */
public interface PdfService
{
	/**
	 * 根据模板填充数据后生成新的PDF
	 * @param input
	 * @param map
	 * @param output
	 */
	void build (InputStream input, Map<String,String> map, OutputStream output);
	
	
	
	/**
	 * 合并多个PDF
	 * @param inputStreams 要合并的 PDF 数组
	 * @param outputStream 合并后的 PDF
	 */
	void merge (InputStream[] inputStreams, OutputStream outputStream);
	
	/**
	 * 批量生成并合并PDF文件
	 * @param directory
	 * @param forms
	 * @param outputStream
	 */
	void batchBuildAndMerge (File directory, Collection<Map<String, String>> forms, OutputStream outputStream,int size);
	
	
	

}
