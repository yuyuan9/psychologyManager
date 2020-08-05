package com.ht.commons.utils;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * 下载文件 创建人：FH 创建时间：2014年12月23日
 * 
 * @version
 */
public class FileDownload {

	/**
	 * @param response
	 * @param filePath
	 *            //文件完整路径(包括文件名和扩展名)
	 * @param fileName
	 *            //下载后看到的文件名
	 * @return 文件名
	 */
	public static void fileDownload(final HttpServletResponse response, String filePath, String fileName)
			throws Exception {

		byte[] data = new FileUtil().toByteArray2(filePath);
		//fileName = URLEncoder.encode(fileName, "UTF-8");
		
		response.reset();
		setFileDownloadHeader(response,fileName);
		//response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
		response.flushBuffer();

	}

	/**
	 * <pre>
	* 浏览器下载文件时需要在服务端给出下载的文件名，当文件名是ASCII字符时没有问题
	* 当文件名有非ASCII字符时就有可能出现乱码
	*
	* 这里的实现方式参考这篇文章
	* http://blog.robotshell.org/2012/deal-with-http-header-encoding-for-file-download/
	*
	* 最终设置的response header是这样:
	*
	* Content-Disposition: attachment;
	*                      filename="encoded_text";
	*                      filename*=utf-8''encoded_text
	*
	* 其中encoded_text是经过RFC 3986的“百分号URL编码”规则处理过的文件名
	 * </pre>
	 * 
	 * @param response
	 * @param filename
	 * @return
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String filename) {
		String headerValue = "attachment;";
		headerValue += " filename=\"" + encodeURIComponent(filename) + "\";";
		headerValue += " filename*=utf-8''" + encodeURIComponent(filename);
		response.setHeader("Content-Disposition", headerValue);
	}

	/**
	 * <pre>
	* 符合 RFC 3986 标准的“百分号URL编码”
	* 在这个方法里，空格会被编码成%20，而不是+
	* 和浏览器的encodeURIComponent行为一致
	 * </pre>
	 * 
	 * @param value
	 * @return
	 */
	public static String encodeURIComponent(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
