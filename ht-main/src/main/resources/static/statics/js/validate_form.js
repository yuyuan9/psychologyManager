function emailmsg(obj,msg){
	var reg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
	if (reg.test(obj.val())) {
	   return true;
	}else{
	   if(msg!=null){
		   layer.msg(msg);
	   }else{
		   layer.msg("邮箱错误");
	   }
	   obj.focus();
	   return false;
	}
}

function email(obj){
	emailmsg(obj,null);
}

function phonemsg(obj,msg){
	var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;
	if ($.trim(obj.val()) != '' || $.trim(obj.val()) != null || $.trim(obj.val()) != undefined ) {
		   return true;
		}
	if (reg.test($.trim(obj.val())) || $.trim(obj.val()) != '' ) {
	   return true;
	}else{
	  if(msg!=null){
	    layer.msg(msg);
	  }else{
		layer.msg('手机号码错误');
	  }
	   obj.focus();
	   return false;
	};
	
}

function phone(obj){
	phonemsg(obj,null);
}

function isNotNull(obj,msg){
	if($.trim(obj.val())=='' || 
		$.trim(obj.val())==null || 
		$.trim(obj.val())=='undefined'){
		layer.msg(msg);
		obj.focus();
		return false;
	}
	return true;
}


function date0GTdate1(strDate0,strDate1,day,msg)
{
    var date0 = new Date(strDate0.val().replace(/\-/g, "\/"));
    var date1 = new Date(strDate1.val().replace(/\-/g, "\/"));
   // alert(date0);
    //  alert(date1);
	if(date0-date1<=day){
		layer.msg(msg);
		//strDate1.click();
		return false;
	 }
	
	return true;
}


function isNotExist(url,obj,msg){
	
		//alert(url);
		//alert(contractCode);
		//假设存在
		var flag=false;
		
	     $.ajax({
	     		type :"POST",
	     		url :url,
	     		dataType:"json",
	     		data:{"pro":$.trim(obj.val())},
	     		async : false,
				beforeSend:function(){
				
					
					},
	     		success : function(result) {
	     			//alert(result.status);
	     			if(result.status==1){
	     				
	     				//alert(msg);
	     				
	     				flag=true;

	     			}else{
	     				
	     				layer.msg(msg);
	     			}
	     		},
	     		error : function(result,error) {
	     			
	     	

	     		}
	     	});	
	     
	    // alert(flag);
	     return flag;

}

/**
* 实时动态强制更改用户录入
* arg1 inputObject
**/
function amount(th){
    var regStrs = [
        ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
        ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点
        ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点
        ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上
    ];
    for(i=0; i<regStrs.length; i++){
        var reg = new RegExp(regStrs[i][0]);
        th.value = th.value.replace(reg, regStrs[i][1]);
    }
}
 
/**
* 录入完成后，输入模式失去焦点后对录入进行判断并强制更改，并对小数点进行0补全
* arg1 inputObject
* 这个函数写得很傻，是我很早以前写的了，没有进行优化，但功能十分齐全，你尝试着使用
* 其实有一种可以更快速的JavaScript内置函数可以提取杂乱数据中的数字：
* parseFloat('10');
**/
function overFormat(th){
    var v = th.value;
    if(v === ''){
        v = '0.00';
    }else if(v === '0'){
        v = '0.00';
    }else if(v === '0.'){
        v = '0.00';
    }else if(/^0+\d+\.?\d*.*$/.test(v)){
        v = v.replace(/^0+(\d+\.?\d*).*$/, '$1');
        v = inp.getRightPriceFormat(v).val;
    }else if(/^0\.\d$/.test(v)){
        v = v + '0';
    }else if(!/^\d+\.\d{2}$/.test(v)){
        if(/^\d+\.\d{2}.+/.test(v)){
            v = v.replace(/^(\d+\.\d{2}).*$/, '$1');
        }else if(/^\d+$/.test(v)){
            v = v + '.00';
        }else if(/^\d+\.$/.test(v)){
            v = v + '00';
        }else if(/^\d+\.\d$/.test(v)){
            v = v + '0';
        }else if(/^[^\d]+\d+\.?\d*$/.test(v)){
            v = v.replace(/^[^\d]+(\d+\.?\d*)$/, '$1');
        }else if(/\d+/.test(v)){
            v = v.replace(/^[^\d]*(\d+\.?\d*).*$/, '$1');
            ty = false;
        }else if(/^0+\d+\.?\d*$/.test(v)){
            v = v.replace(/^0+(\d+\.?\d*)$/, '$1');
            ty = false;
        }else{
            v = '0.00';
        }
    }
    th.value = v; 
}

function validateNumber(e, pnumber){ 
	if (!/^\d+$/.test(pnumber)){ 
	   e.value = /^\d+/.exec(e.value);
	} 
	return false; 
} 


//验证银行卡
function formatBankNo (BankNo){
    if (BankNo.value == "") return;
    var account = new String (BankNo.value);
    account = account.substring(0,22); /*帐号的总数, 包括空格在内 */
    if (account.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
        /* 对照格式 */
        if (account.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" +
        ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
            var accountNumeric = accountChar = "", i;
            for (i=0;i<account.length;i++){
                accountChar = account.substr (i,1);
                if (!isNaN (accountChar) && (accountChar != " ")) accountNumeric = accountNumeric + accountChar;
            }
            account = "";
            for (i=0;i<accountNumeric.length;i++){    /* 可将以下空格改为-,效果也不错 */
                if (i == 4) account = account + " "; /* 帐号第四位数后加空格 */
                if (i == 8) account = account + " "; /* 帐号第八位数后加空格 */
                if (i == 12) account = account + " ";/* 帐号第十二位后数后加空格 */
                account = account + accountNumeric.substr (i,1)
            }
        }
    }
    else
    {
        account = " " + account.substring (1,5) + " " + account.substring (6,10) + " " + account.substring (14,18) + "-" + account.substring(18,25);
    }
    if (account != BankNo.value) BankNo.value = account;
}

//验证身份证号
function validateCardNo(card){
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	if(reg.test(card.val()) === false)  
	{  
	    layer.msg("身份证输入不合法");
		obj.focus();
	    return false;
	}else{
	    return true;
	}
}























