package com.ht.biz.controller;

import com.ht.commons.constants.Const;
import com.ht.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;


@Api(value = "SecCodeController", description = "发送验证码")
@Controller
@RequestMapping("/ht-biz/sendcode")
public class SecCodeController {

	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	@GetMapping("/codeinfo")
	@ApiOperation(value="获取验证码",notes="")
	public void generate(HttpServletResponse response,String wxapp){
		ByteArrayOutputStream output = new ByteArrayOutputStream(); 
		String code = drawImg(output);
		if(StringUtils.isNotBlank(wxapp)){
			redisTemplate.opsForValue().set("wxapp",code);
		}else{
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			session.setAttribute(Const.SESSION_SECURITY_CODE, code);
		}
		try {
			ServletOutputStream out = response.getOutputStream();
			output.writeTo(out);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String drawImg(ByteArrayOutputStream output){
		String code = "";
		for(int i=0; i<5; i++){
			code += randomChar();
		}
		int width = 150;
		int height = 40;
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		Font font = new Font("Times New Roman",Font.PLAIN,20);
		Graphics2D g = bi.createGraphics();
		g.setFont(font);
		Color color = new Color(66,2,82);
		g.setColor(color);
		g.setBackground(new Color(226,226,240));
		g.clearRect(0, 0, width, height);
		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(code, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		g.drawString(code, (int)x, (int)baseY);
		g.dispose();
		try {
			ImageIO.write(bi, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	private char randomChar(){
		Random r = new Random();
		String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
		return s.charAt(r.nextInt(s.length()));
	}
}
