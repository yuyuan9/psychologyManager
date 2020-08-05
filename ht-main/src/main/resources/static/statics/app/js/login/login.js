var applogin = new Vue({
    el: '#l-login',
    data: {
        //tab切换
        isTab: true,
        phone:'',
        password: '',
        codeImg: '',
        passwordType: 'password',
    },
    created: function () {
    	this.initPage();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
            this.changeCode();
            //this.changeCode("regcodeimg");
        },
        changeCode:function(){
        	$("#logincodeimg").attr("src", "/ht-biz/sendcode/codeinfo?t="+ new Date().getTime());
        },
        login:function(){
        	location.href="/ht-biz/app/login/login.html";
        },
        register:function(){
        	location.href="/ht-biz/app/login/register.html";
        },
        loginSubmit:function(){
            if (applogin.validateForm()) {
            	layer.load(1, {shade: [0.5,'#fff'] }); //0代表加载的风格，支持0-2
                var loginname = $.trim($("input[name='phone']").val());
                var password = $("input[name='password']").val();
                var codevalue = $.trim($('input[name="code"]').val());
                //var rememberMe = $("input[type='checkbox']").is(':checked');
                htajax.getForm("/ht-shiro/loginuser/ajaxLogin",  {
                    username: loginname,
                    password: password,
                    code:codevalue,
                },
                function (data) {
                    if(data.code == 10000){
                        layer.closeAll("loading");
                        //layer.msg("登录成功，正在为您跳转首页！")
                        var timer=setTimeout(function(){
                        	//applogin.changeCode();
                            window.location.href='/ht-biz/app/index/index.html';
                        },500);
                    }else{
                        layer.closeAll("loading");
                        layer.msg(data.msg);
                        applogin.changeCode();
                    }
                }, function (data) {
                    //错误回调
                    layer.msg("系统繁忙，请稍候重试！");
                    box.changeCode();
                    layer.closeAll("loading");
                });
    
            }
        },
        validateForm:function(){
            if ($.trim(this.phone) == '') {
                layer.msg("请填写手机号/社会统一信用代码")
                return false;
            } else if ($.trim(this.password) == '') {
                layer.msg("请填写密码")
                return false;
            } else if ($.trim(this.codeImg) == '') {
                layer.msg("请填写验证码")
                return false;
            }  else {
                return true;
            }
        },
        showPwd() {
            if (this.passwordType === 'password') {
              this.passwordType = ''
            } else {
              this.passwordType = 'password'
            }
        }
    	
    },
  
});