package com.ht.utils;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.constants.Const;
import com.ht.commons.support.sms.SMSConfig;
import com.ht.commons.support.sms.SMSHelp;
import com.ht.commons.support.sms.SMSHelpImpl;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.commons.utils.UuidUtil;
import com.ht.entity.policydig.PolicyPackage;
import com.ht.entity.policydig.PolicyPackageSend;
import com.ht.entity.shiro.SendCode;
import com.ht.vo.shiro.UserVO;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class SendCodeUtils {

    private static Logger logger = LoggerFactory.getLogger(SendCodeUtils.class);
    /*
     * 发送短信
     */
    public static boolean sendMessage(UserVO uservo,String beanName) throws Exception {
        System.out.println(uservo.toString());
        String captcha = RandomStringUtils.randomNumeric(6);
        logger.info("发送短信接收", "验证码=" + captcha);
        // 发送验证码
         Boolean sendSuccess =  new SMSHelpImpl().send( uservo.getPhone(),new String[] { captcha, "30" }, SMSConfig.MSG_REGISTER_TEMPLATE );
        if (sendSuccess) {
            SendCode savePd = new SendCode();
            savePd.setCreatedate( new Date() );
            savePd.setDeleted( 0 );
            savePd.setContent(captcha  );
            savePd.setIsavailable( 0 );
            savePd.setPhone( uservo.getPhone() );
            savePd.setReceiver( uservo.getPhone() );
            savePd.setSmstype( 0 );
            savePd.setSmssendimmediately( 1 );
            savePd.setSmssendsuccess(1 );
            savePd.setSmssendsuccess( 1 );
            savePd.setSmstemplate( SMSConfig.MSG_REGISTER_TEMPLATE );
            ((IService)SpringContextUtil.getBean("smsService")).save(savePd);
            //smsService.save(savePd);
            return true;
        } else {
            return false;
        }

    }
    /*
     * 发送短信(政策推送)
     */
  //boolean sendSuccess = sendSmsUtil.send(pd.getString("phone"), template, new String[] { pd.getString("province")+pd.getString("city")+pd.getString("area")+",", DateUtil.formatDatess(policyPackage.getSendTime()),pd.getString("ciIdUrl") }, false,SendSmsUtil.system);
    public static boolean sendMsg(PolicyPackage policyPackage,PageData pd,String beanName) throws Exception {
    	Boolean sendSuccess =  new SMSHelpImpl().send( pd.getString("phone"),new String[] {pd.getString("province")+pd.getString("city")+pd.getString("area")+",", DateUtil.dateToStr(policyPackage.getSendTime(),13),pd.getString("ciIdUrl") }, SMSConfig.MSG_POLICY_PUSH_TEMPLATE );
    	//Boolean sendSuccess=true;
    	if (sendSuccess) {
            SendCode savePd = new SendCode();
            savePd.setCreatedate( new Date() );
            savePd.setDeleted( 0 );
            savePd.setContent(pd.getString("ciIdUrl")  );
            savePd.setIsavailable( 0 );
            savePd.setPhone( pd.getString("phone") );
            savePd.setReceiver( pd.getString("phone") );
            savePd.setSmstype( 0 );
            savePd.setSmssendimmediately( 1 );
            savePd.setSmssendsuccess(1 );
            savePd.setSmssendsuccess( 1 );
            savePd.setSmstemplate( SMSConfig.MSG_POLICY_PUSH_TEMPLATE );
            ((IService)SpringContextUtil.getBean("smsService")).save(savePd);
            //smsService.save(savePd);
            return true;
        } else {
            return false;
        }
    }
}
