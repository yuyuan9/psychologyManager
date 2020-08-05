package com.ht.shiro.controller.phonecode;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.commons.utils.ValidataUtil;
import com.ht.entity.shiro.SendCode;
import com.ht.entity.shiro.SysUser;
import com.ht.shiro.service.SmsService;
import com.ht.shiro.service.SysUserService;
import com.ht.utils.SendCodeUtils;
import com.ht.vo.shiro.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/ht-shiro/sendcode")
@Api(value="RegionCompanyController",description = "手机发送验证码")

public class PhoneSendCodeController  extends BaseController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @ApiOperation(value="手机发送验证码",notes="")
    @RequestMapping(value = "/sendPhoneCode", method = RequestMethod.GET)
    public Respon sendPhoneCode(UserVO uservo, HttpServletRequest request) {
        Respon respon = this.getRespon();
        try {
            //数字验证码不能为空
            if(null!=uservo && StringUtils.isBlank( uservo.getVerificacode() )){
              return   respon.error("验证码不能为空");
            }
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            Serializable sid = session.getId();
            String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
            if(!StringUtils.isBlank( sessionCode ) && !sessionCode.equalsIgnoreCase(uservo.getVerificacode() )) {
               return respon.error("验证不正确");
            }

            if (!new ValidataUtil().isMobileNO(uservo.getPhone())) {
               return  respon.error("手机号码不正确");
            }
            List<SendCode> sendCode = (List<SendCode>) ((SmsService)SpringContextUtil.getBean("smsService")).findThirtymit( uservo.getPhone() );
            /*  SysUser user =sysUserService.findByPhone( uservo.getPhone() );*/
              if(sendCode.size()>0){
                  return  respon.error("短信发送频繁");
              }
            boolean send = SendCodeUtils.sendMessage(uservo,SmsService.class.getName());
            if (send) {
                respon.success("验证码发送成功");
                return respon;
            } else {
                respon.error("短信发送频繁");
                return respon;
            }

        } catch (Exception e) {
            e.printStackTrace();
            respon.error("发送短信失败");
            return respon;
        }

    }
    @ApiOperation(value="手机发送验证码",notes="")
    @RequestMapping(value = "/wxPhoneCode", method = RequestMethod.GET)
    public Respon wxPhoneCode(UserVO uservo, HttpServletRequest request) {
        Respon respon = this.getRespon();
        try {
            //数字验证码不能为空
            if(null!=uservo && StringUtils.isBlank( uservo.getVerificacode() )){
                return   respon.error("验证码不能为空");
            }

            String sessionCode = redisTemplate.opsForValue().get("wxapp");	//获取session中的验证码
            redisTemplate.delete( "wxapp" );
            if(StringUtils.isBlank( sessionCode ) && !sessionCode.equalsIgnoreCase(uservo.getVerificacode() )) {
                return respon.error("验证不正确");
            }

            if (!new ValidataUtil().isMobileNO(uservo.getPhone())) {
                return  respon.error("手机号码不正确");
            }
           // SendCode sendCode = (SendCode) ((SmsService)SpringContextUtil.getBean("smsService")).findThirtymit( uservo.getPhone() );
           SysUser user =sysUserService.findByPhone( uservo.getPhone() );
            if(null!=user){
                return  respon.error("用户已注册");
            }
            boolean send = SendCodeUtils.sendMessage(uservo,SmsService.class.getName());
            if (send) {
                respon.success(null);
                return respon;
            } else {
                respon.error("短信发送频繁");
                return respon;
            }

        } catch (Exception e) {
            e.printStackTrace();
            respon.error("发送短信失败");
            return respon;
        }

    }
}
