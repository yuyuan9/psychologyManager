package com.ht.commons.support.pdf;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BadPdfFormatException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;


public class PdfServiceImpl implements PdfService
{
	class PdfBuildWorker extends Thread
	{
		private File _templateFile;
		private Map<String, String> _form;
		private ByteArrayOutputStream _outputStream;

		public PdfBuildWorker(File templateFile, Map<String, String> form)
		{
			_templateFile = templateFile;
			_form = form;
		}

		public ByteArrayOutputStream getOutputStream()
		{
			return _outputStream;
		}

		@Override
		public void run()
		{
			_outputStream = new ByteArrayOutputStream ();
			FileInputStream templateIS = null;

			try
			{
				templateIS = new FileInputStream (_templateFile);

				build(templateIS, _form, _outputStream);

				_outputStream.flush();
			}
			catch (Exception ex)
			{
				throw new RuntimeException (ex);
			}
			finally
			{
				IOUtils.closeQuietly(templateIS);
				IOUtils.closeQuietly(_outputStream);
			}
		}
	}
	
	

	/**
	 * 填冲PDF模板数据，输出最终PDF
	 * @param template PDF模板
	 * @param map 表单数据
	 * @param outputStream 填冲数据后的 PDF 输出流
	 */
	public void build(InputStream template, Map<String, String> map, OutputStream outputStream)
	{
		try
		{
			// 读取 PDF 模板
			PdfReader reader = new PdfReader (template);

			// 修改 PDF 的操作类
			PdfStamper stamper = new PdfStamper (reader, outputStream);

			// 使最终的 PDF 去除表单，因为表单PDF不能进行合并
			stamper.setFormFlattening(true);

			// 填冲表单数据
			for (Entry<String, String> pair : map.entrySet())
			{
				stamper.getAcroFields().setField(pair.getKey(), ObjectUtils.toString(pair.getValue()));
			}

			// 关闭 PDF 资源
			reader.close();
			stamper.close();
		}
		catch (IOException ex)
		{
			throw new RuntimeException (ex);
		}
		catch (DocumentException ex)
		{
			throw new RuntimeException (ex);
		}
		finally
		{
			// 释放相关资源
			IOUtils.closeQuietly(template);
			IOUtils.closeQuietly(outputStream);
		}
	}
	
	
	/**
	 * 填冲PDF模板数据，输出最终PDF
	 * @param template PDF模板
	 * @param map 表单数据
	 * @param outputStream 填冲数据后的 PDF 输出流
	 */
	public void buildMapObject(InputStream template, Map<String, Object> map, OutputStream outputStream)
	{
		try
		{
			// 读取 PDF 模板
			PdfReader reader = new PdfReader (template);

			// 修改 PDF 的操作类
			PdfStamper stamper = new PdfStamper (reader, outputStream);

			// 使最终的 PDF 去除表单，因为表单PDF不能进行合并
			stamper.setFormFlattening(true);

			// 填冲表单数据
			for (Entry<String, Object> pair : map.entrySet())
			{
				stamper.getAcroFields().setField(pair.getKey(), ObjectUtils.toString(pair.getValue()));
			}

			// 关闭 PDF 资源
			reader.close();
			stamper.close();
		}
		catch (IOException ex)
		{
			throw new RuntimeException (ex);
		}
		catch (DocumentException ex)
		{
			throw new RuntimeException (ex);
		}
		finally
		{
			// 释放相关资源
			IOUtils.closeQuietly(template);
			IOUtils.closeQuietly(outputStream);
		}
	}

	/**
	 * 合并PDF文件
	 * @param pages PDF 输入流
	 * @param outputStream 合并后的 PDF 输出流
	 */
	public void merge(InputStream[] pages, OutputStream outputStream)
	{
		try
		{
			// 建立PDF文档对象
			Document document = new Document ();

			// 建立PDF复制操作对象
			PdfCopy copy = new PdfCopy (document, outputStream);

			// 在操作PDF之类，请先打开文档
			document.open();

			// 循环需要合并的PDF流
			for (InputStream inputStream : pages)
			{
				// 读取PDF
				PdfReader reader = new PdfReader (inputStream);

				// 把PDF中的每一页都合并进去
				for (int index = 1; index <= reader.getNumberOfPages(); index++)
				{
					// 添加PDF页
					copy.addPage(copy.getImportedPage(reader, index));
				}
			}

			// 在完成PDF操作之后关闭文档
			document.close();
		}
		catch (BadPdfFormatException ex)
		{
			throw new RuntimeException (ex);
		}
		catch (DocumentException ex)
		{
			throw new RuntimeException (ex);
		}
		catch (IOException ex)
		{
			throw new RuntimeException (ex);
		}
		finally
		{
			// 释放相关资源
			for (InputStream inputStream : pages)
			{
				IOUtils.closeQuietly(inputStream);
			}

			IOUtils.closeQuietly(outputStream);
		}
	}
	
	public void reverse(InputStream in, OutputStream out)
	{
		try
		{
			Document document = new Document ();

			PdfCopy copy = new PdfCopy (document, out);

			document.open();

			PdfReader reader = new PdfReader(in);
			
			for (int index = reader.getNumberOfPages(); index > 0; index--)
			{
				copy.addPage(copy.getImportedPage(reader, index));
			}

			document.close();
		}
		catch (BadPdfFormatException ex)
		{
			throw new RuntimeException (ex);
		}
		catch (DocumentException ex)
		{
			throw new RuntimeException (ex);
		}
		catch (IOException ex)
		{
			throw new RuntimeException (ex);
		}
		finally
		{
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}
	
	
	public void batchBuildAndMerge(File directory, final Collection<Map<String, String>> forms, OutputStream outputStream,int size)
	{
		final Queue<PdfBuildWorker> workerQueue = new LinkedList<PdfBuildWorker> ();
		int index = 0;

		try
		{
			while (index < size)
			{
				final File templateFile = new File (directory, String.format("%s.pdf", index + 1));

				PdfBuildWorker worker = new PdfBuildWorker (templateFile, (Map<String, String>)CollectionUtils.get(forms, 0));

				workerQueue.add(worker);

				worker.start();

				index++;
			}

			for (PdfBuildWorker worker : workerQueue)
			{
				try
				{
					worker.join();
				}
				catch (InterruptedException ex)
				{
					throw new RuntimeException (ex);
				}
			}

			ArrayList<ByteArrayOutputStream> osList = new ArrayList<ByteArrayOutputStream> ();
			for (PdfBuildWorker worker : workerQueue)
			{
				osList.add (worker.getOutputStream());
			}

			merge(this.transform(osList).toArray(new InputStream[osList.size()]), outputStream);

			outputStream.flush();
		}
		catch (IOException ex)
		{
			throw new RuntimeException (ex);
		}
		finally
		{
			IOUtils.closeQuietly(outputStream);
		}
	}
	
	public List<InputStream> transform(List<ByteArrayOutputStream> input)
	{
		List<InputStream> result = new ArrayList<InputStream> ();
		for (ByteArrayOutputStream item : input)
		{
			result.add(new ByteArrayInputStream (item.toByteArray()));
		}

		return result;
	}
	
	
}