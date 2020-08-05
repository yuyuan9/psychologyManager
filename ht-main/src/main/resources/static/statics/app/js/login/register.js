var regbox = new Vue({
    el: '#r-register',
    data: {
        //tab切换
        isTab: false,
        phone: '',
        imgCode: '',
        code: '',
        password: '',
        repassword: '',
    },
    created: function () {
    	this.initPage();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
            this.changeCode();
        },
        changeCode:function(){
        	$("#regcodeimg").attr("src", "/ht-biz/sendcode/codeinfo?t="+ new Date().getTime());
        },
        login:function(){
        	location.href="/ht-biz/app/login/login.html";
        },
        register:function(){
        	location.href="/ht-biz/app/login/register.html";
        },
        getcode: function () {
            //var imgcode = $("#imgCode").val();
            var step = 59;
            var _res = "";
            var mobilePhone = $.trim($("#phone").val());
            var imgCode = $.trim($("#imgCode").val());
            if (mobilePhone == "") {
                layer.msg("请输入正确的手机号码");
                return false;
            }
            if (imgCode == '') {
                layer.msg("请输入图形验证码");
                return false;
            }
            var length = mobilePhone.length;
            var mobile = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;

            if (length == 11 && mobile.test(mobilePhone)) {
                $.ajax({
                    type: "GET",
                    url: '/ht-shiro/sendcode/sendPhoneCode',
                    timeout: 60000,
                    data: {
                        phone: mobilePhone,
                        verificacode: imgCode,
                    },
                    dataType: "json",
                    beforeSend: function () {
                        _res = setInterval(function () {
                            $("#sendCode").attr("disabled", true);//设置disabled属性
                            $('#sendCode').html("重新发送(" + step + ")");
                            step -= 1;
                            if (step <= 0) {
                                $("#sendCode").removeAttr("disabled"); //移除disabled属性
                                $('#sendCode').html('获取验证码');
                                clearInterval(_res);   //清除setInterval
                                step = 59;
                            }
                        }, 1000);
                    },
                    success: function (msg) {
                        if (msg.code == 10000) {
                            layer.msg("验证码发送成功");
                        } else {
                            layer.msg(msg.msg);
                            clearInterval(_res);
                        }
                    },
                    error: function (msg, error) {
                        layer.msg(msg.msg);
                        regbox.changeCode();
                        clearInterval(_res);
                    }
                });
            } else {
                layer.msg('手机号码格式错误！');
                regbox.changeCode();
                return false;
            }
           
        },
        // 个人注册提交
        submitReg: function () {
            var v = this.ordinaryValidata();
            if (v) {
                htajax.postFormDone("/ht-shiro/loginuser/register", {
                        username: this.phone,
                        password: this.password,
                        code: this.code,
                    },
                    function(data){
                        layer.load(1, {
                            shade: [0.5,'#fff'] //0.1透明度的白色背景
                        });
                    },
                    function (data) {
                        if (data.code == 10000) {
                            $(".loading_button").css("background", "none");
                            $(".loading_button").css("backgroundColor", "#dbdbdb");
                            layer.closeAll();
                            layer.msg('注册成功');
                            setTimeout(function () {
                                location.href = "/ht-biz/app/login/login.html";
                            }, 500);
                        } else {
                        	regbox.changeCode();
                            layer.msg(data.msg);
                            setTimeout(function () {layer.closeAll();}, 1000);
                        }
                    },
                    function (data) {
                        setTimeout(function () {layer.closeAll();}, 1000);
                        regbox.changeCode();
                        //$(".submitInfo").fadeIn("500");
                        //$("#overlay").fadeIn("300");
                        //layer.alert(data.msg);
                    });
            }
        },
        ordinaryValidata: function () { //普通注册验证
            var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;
            var regBox = {
                regMobile: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/, //手机
            }
            var mflag = regBox.regMobile.test($.trim(this.phone));
            var regpsd =/^(\w){6,12}$/;
            if (!(mflag)) {
                layer.msg("请输入正确的手机号码");
                return false;
            }
            if ($.trim(this.phone) == '') {
                layer.msg("请输入正确的手机号码");
                return false;
            } else if ($.trim(this.imgCode) == '') {
                layer.msg("请输入图形验证码");
                return false;
            } else if ($.trim(this.code) == '') {
                layer.msg("请输入短信验证码");
                return false;
            } else if(!regpsd.test($.trim(this.password))){
                layer.msg("请输入6-12位数字或字母组合密码");
                return false;
            } else if ($.trim(this.password) == '') {
                layer.msg("请输入密码");
                return false;
            } else if ($.trim(this.password) != $.trim(this.repassword)) {
                layer.msg("两次密码不一致");
                return false;
            } else {
                return true;
            }
        }, 
        valistdata: function () {
            var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;
            var regBox = {
                regMobile: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/, //手机
            }
            var mflag = regBox.regMobile.test($.trim(this.phone));
            if (!(mflag)) {
                layer.msg("请输入正确的手机号码");
                return false;
            }
        },

       
    	
    },
  
});