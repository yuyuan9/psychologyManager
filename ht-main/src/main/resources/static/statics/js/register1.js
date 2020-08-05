/**
 * Created by dsy on 2019/5/17
 */
var box = new Vue({
    el: '#box',
    data: {
        isChoose: 1,
        eIsChoose:1,
        isActive: 0,
        phone: '',
        imgCode: '',
        code: '',
        password: '',
        repassword: '',
        isProtocol: false,
        eIsProtocol:false,

        eCreditCode: '',
        ePhone: '',
        eImgCode: '',
        eCode: '',
        companyPasswordData:'',
        companyRepasswordData:'',
        companyNameData:'',
        loginType : 'userinfo',
    },
    created: function () {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.changeCode();
            this.eChangeCode();
        },
        change: function () {
            if (this.loginType == 'userinfo') {
                this.loginType = 'userpassword'
            } else {
                this.loginType = 'userinfo'
            }
        },
        close:function(){
            $(".submitInfo").hide();
            $("#overlay").hide();
        },
        //提示信息
        login_info_error1: function () {
            $(".login_info1>i").attr("class", "login_icon_error");
            $(".login_info1>s").html('请输入正确的手机号码')
            $(".login_info1").css({
                "display": "block"
            });
        },
        login_info_error2: function () {
            $(".login_info2>i").attr("class", "login_icon_error");
            $(".login_info2>s").html('请输图形验证码')
            $(".login_info2").css({
                "display": "block"
            });
        },
        login_info_error3: function () {
            $(".login_info3>i").attr("class", "login_icon_error");
            $(".login_info3>s").html('短信验证码错误')
            $(".login_info3").css({
                "display": "block"
            });
        },
        login_info_error4: function () {
            $(".login_info4>i").attr("class", "login_icon_error");
            $(".login_info4>s").html('请输入密码')
            $(".login_info4").css({
                "display": "block"
            });
        },
        login_info_error5: function () {
            $(".login_info5>i").attr("class", "login_icon_error");
            $(".login_info5>s").html('两次密码不一致')
            $(".login_info5").css({
                "display": "block"
            });
        },
        login_info_error6: function () {
            $(".login_info6>i").attr("class", "login_icon_error");
            $(".login_info6>s").html('请输入正确的代码位数')
            $(".login_info6").css({
                "display": "block"
            });
        },
        login_info_error7: function () {
            $(".login_info7>i").attr("class", "login_icon_error");
            $(".login_info7>s").html('请输入正确的手机号码')
            $(".login_info7").css({
                "display": "block"
            });
        },
        login_info_error8: function () {
            $(".login_info8>i").attr("class", "login_icon_error");
            $(".login_info8>s").html('图形验证码错误')
            $(".login_info8").css({
                "display": "block"
            });
        },
        login_info_error9: function () {
            $(".login_info9>i").attr("class", "login_icon_error");
            $(".login_info9>s").html('短信验证码错误')
            $(".login_info9").css({
                "display": "block"
            });
        },
        login_info_error10: function () {
            $(".login_info10>i").attr("class", "login_icon_error");
            $(".login_info10>s").html('请设置6-12位数字或字母组合')
            $(".login_info10").css({
                "display": "block"
            });
        },
        login_info_error11: function () {
            $(".login_info11>i").attr("class", "login_icon_error");
            $(".login_info11>s").html('请再次确认密码')
            $(".login_info11").css({
                "display": "block"
            });
        },
        login_info_error12: function () {
            $(".login_info12>i").attr("class", "login_icon_error");
            $(".login_info12>s").html('请输入您的公司名称')
            $(".login_info12").css({
                "display": "block"
            });
        },

        login_info_true1: function () {
            $(".login_info1>i").attr("class", "login_icon_true");
            $(".login_info1>s").html('')
            $(".login_info1").css({
                "display": "block"
            });
        },
        login_info_true2: function () {
            $(".login_info2>i").attr("class", "login_icon_true");
            $(".login_info2>s").html('')
            $(".login_info2").css({
                "display": "block"
            });
        },
        login_info_true3: function () {
            $(".login_info3>i").attr("class", "login_icon_true");
            $(".login_info3>s").html('')
            $(".login_info3").css({
                "display": "block"
            });
        },
        login_info_true4: function () {
            $(".login_info4>i").attr("class", "login_icon_true");
            $(".login_info4>s").html('')
            $(".login_info4").css({
                "display": "block"
            });
        },
        login_info_true5: function () {
            $(".login_info5>i").attr("class", "login_icon_true");
            $(".login_info5>s").html('')
            $(".login_info5").css({
                "display": "block"
            });
        },
        login_info_true6: function () {
            $(".login_info6>i").attr("class", "login_icon_true");
            $(".login_info6>s").html('')
            $(".login_info6").css({
                "display": "block"
            });
        },
        login_info_true7: function () {
            $(".login_info7>i").attr("class", "login_icon_true");
            $(".login_info7>s").html('')
            $(".login_info7").css({
                "display": "block"
            });
        },
        login_info_true8: function () {
            $(".login_info8>i").attr("class", "login_icon_true");
            $(".login_info8>s").html('')
            $(".login_info8").css({
                "display": "block"
            });
        },
        login_info_true9: function () {
            $(".login_info9>i").attr("class", "login_icon_true");
            $(".login_info9>s").html('')
            $(".login_info9").css({
                "display": "block"
            });
        },
        login_info_true10: function () {
            $(".login_info10>i").attr("class", "login_icon_true");
            $(".login_info10>s").html('')
            $(".login_info10").css({
                "display": "block"
            });
        },
        login_info_true11: function () {
            $(".login_info11>i").attr("class", "login_icon_true");
            $(".login_info11>s").html('')
            $(".login_info11").css({
                "display": "block"
            });
        },
        login_info_true12: function () {
            $(".login_info12>i").attr("class", "login_icon_true");
            $(".login_info12>s").html('')
            $(".login_info12").css({
                "display": "block"
            });
        },
        valistdata: function () {
            var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;
            var regBox = {
                regMobile: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/, //手机
            }
            var mflag = regBox.regMobile.test($.trim(this.phone));
            if (!(mflag)) {
                this.login_info_error1();
                return false;
            } else {
                this.login_info_true1();
                return true;
            }
        },
        imgCodeData: function () {
            if ($.trim(this.imgCode) == '') {
                this.login_info_error2();
                return false;
            } else {
                this.login_info_true2();
                return true;
            }
        },
        codeData: function () {
            if ($.trim(this.code) == '') {
                this.login_info_error3();
                return false;
            } else {
                this.login_info_true3();
                return true;
            }
        },
        changeCode: function () {
            $("#codeImg").attr("src", "/ht-biz/sendcode/codeinfo?t=" + new Date().getTime());
        },

        getcode: function () {
            var imgcode = $("#imgCode").val();
            var step = 59;
            var _res = "";
            var mobilePhone = $.trim($("#phone").val());
            var imgCode = $.trim($("#imgCode").val());
            if (mobilePhone == "") {
                this.login_info_error1();
                return false;
            }
            if (imgCode == '') {
                this.login_info_error2();
                return false;
            }
            var length = mobilePhone.length;
            var mobile = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;
            if (length == 11 && mobile.test(mobilePhone)) {
                $.ajax({
                    type: "POST",
                    url: '/beetl/reg/sendPhoneCode.do',
                    timeout: 60000,
                    data: {
                        "phone": mobilePhone,
                        tel_code: imgCode
                    },
                    dataType: "json",
                    beforeSend: function () {
                        _res = setInterval(function () {
                            $("#sendCode").attr("disabled", true); //设置disabled属性
                            $('#sendCode').html("重新发送(" + step + ")");
                            step -= 1;
                            if (step <= 0) {
                                $("#sendCode").removeAttr("disabled"); //移除disabled属性
                                $('#sendCode').html('获取验证码');
                                clearInterval(_res); //清除setInterval
                                step = 59;
                            }
                        }, 1000);
                    },
                    success: function (msg) {
                        if (msg.code != 0) {
                            layer.msg(msg.msg);
                            box.changeCode();
                            clearInterval(_res);
                            $("#sendCode").attr("disabled", false);
                            $('#sendCode').html("重新发送");
                        } else {
                            layer.msg("发送短信成功，请注意查收");
                        }
                    },
                    error: function (msg, error) {
                        layer.msg(msg);
                        box.changeCode();
                        clearInterval(_res);
                    }
                });
            } else {
                layer.msg('手机号码格式错误！');
                box.changeCode();
                return false;
            }
        },
        valpassword: function () {
            var reg = /^(\w){6,12}$/;
            var password = $.trim(this.password);
            if ($.trim(this.password) == '') {
                this.login_info_error4();
                return false;
            } else if (!reg.test(password)) {
                this.login_info_error4();
                $(".login_info4>s").html('密码必须由6-12位数字或者字母组成')
                return false;
            } else {
                this.login_info_true4();
                return true
            }
        },
        reValpassword: function () {
            if ($.trim(this.password) != '' && $.trim(this.password) != $.trim(this.repassword)) {
                this.login_info_error5();
                $(".login_info5>s").html('两次密码不一致')
                return false;
            } else {
                this.login_info_true5();
                return true
            }
        },
        ordinaryValidata: function () { //普通注册验证
            if ($.trim(this.phone) == '') {
                this.login_info_error1();
                return false;
            } else if ($.trim(this.imgCode) == '') {
                this.login_info_error2();
                return false;
            } else if ($.trim(this.code) == '') {
                this.login_info_error3();
                return false;
            } else if ($.trim(this.password) == '') {
                this.login_info_error4();
                return false;
            } else if ($.trim(this.password) != $.trim(this.repassword)) {
                this.login_info_error5();
                return false;
            } else {
                return true;
            }
        },
        // 个人注册提交
        submitReg: function () {
            var v = this.ordinaryValidata();
            if (v) {
                htajax.postForm("/ht-shiro/loginuser/register", {
                        phone: this.phone,
                        password: this.password,
                        repassword: this.repassword,
                        imgCode: this.imgCode,
                        code: this.code,
                    },
                    function (data) {
                        if (data.code == 10000) {

                            $(".loading_button").css("background", "none");
                            $(".loading_button").css("backgroundColor", "#dbdbdb");
                            layer.load();
                            layer.msg('您已成功注册，现为你跳转登录！');
                            setInterval(function () {
                                if (!box.getUrlData('returnUrl')) {
                                    location.href = "/beetl/login/toLogin.html";
                                } else {
                                    location.href = "/beetl/login/toLogin.html?returnUrl=" + box.returnurl;
                                }
                            }, 3000);

                        } else {
                            box.changeCode();
                            layer.msg(data.msg);
                        }
                    },
                    function (data) {
                        box.changeCode();
                        $(".submitInfo").fadeIn("500");
                        $("#overlay").fadeIn("300");
                        //layer.alert(data.msg);
                    });

            }
        },
        protocol: function () { //协议解除注册按钮
            if (this.isProtocol) {
                $("#submitReg").attr("disabled", true);
            } else {
                $("#submitReg").removeAttr("disabled");
            }

            if (this.isChoose == 0) {
                this.isChoose = 1;
            } else {
                this.isChoose = 0;
            }
        },

        //企业
        //信用代码
        eCreditCodetData: function () {
            var str = /^[A-Za-z0-9]{18}/;
            var creditCode = $.trim(this.eCreditCode);
            if (!(str.test(creditCode))) {
                this.login_info_error6();

                return false;
            } else {
                this.login_info_true6();

                return true;
            }
        },
        //手机号码
        eValistdata: function () {
            var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;
            var regBox = {
                regMobile: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/, //手机
            }
            var mflag = regBox.regMobile.test($.trim(this.ePhone));
            if (!(mflag)) {
                this.login_info_error7();
                return false;
            } else {
                this.login_info_true7();
                return true;
            }
        },
        //切换图形验证码
        eChangeCode: function () {
            $("#eCodeImg").attr("src", "/ht-biz/sendcode/codeinfo?t="+ new Date().getTime());
        },
        //图形验证
        eimgCodeData: function () {
            if ($.trim(this.eImgCode) == '') {
                this.login_info_error8();
                return false;
            } else {
                this.login_info_true8();
                return true;
            }
        },
        //验证码
        eCodeData: function () {
            if ($.trim(this.eCode) == '') {
                this.login_info_error9();
                return false;
            } else {
                this.login_info_true9();
                return true;
            }
        },
        //获取验证码
        eGetCode: function () {
            var step = 59;
            var _res = "";
            var creditCode = $.trim($("#eCreditCode").val());
            var mobilePhone = $.trim($("#ePhone").val());
            var imgCode = $.trim($("#eImgCode").val());
            if (creditCode == "") {
                this.login_info_error6();
                return false;
            }
            if (mobilePhone == "") {
                this.login_info_error7();
                return false;
            }
            if (imgCode == '') {
                this.login_info_error8();
                return false;
            }
            var length = mobilePhone.length;
            var mobile = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;
            if (length == 11 && mobile.test(mobilePhone)) {
                $.ajax({
                    type: "POST",
                    url: '/beetl/reg/sendPhoneCode.do',
                    timeout: 60000,
                    data: {
                        "phone": mobilePhone,
                        tel_code: imgCode
                    },
                    dataType: "json",
                    beforeSend: function () {
                        _res = setInterval(function () {
                            $("#sendCode").attr("disabled", true); //设置disabled属性
                            $('#sendCode').html("重新发送(" + step + ")");
                            step -= 1;
                            if (step <= 0) {
                                $("#sendCode").removeAttr("disabled"); //移除disabled属性
                                $('#sendCode').html('获取验证码');
                                clearInterval(_res); //清除setInterval
                                step = 59;
                            }
                        }, 1000);
                    },
                    success: function (msg) {
                        if (msg.code != 0) {
                            layer.msg(msg.msg);
                            box.eChangeCode();
                            clearInterval(_res);
                            $("#sendCode").attr("disabled", false);
                            $('#sendCode').html("重新发送");
                        } else {
                            layer.msg("发送短信成功，请注意查收");
                        }
                    },
                    error: function (msg, error) {
                        layer.msg("发送短信失败");
                        box.eChangeCode();
                        clearInterval(_res);
                    }
                });
            } else {
                layer.msg('手机号码格式错误！');
                box.eChangeCode();
                return false;
            }
        },
        eOrdinaryValidata: function () { 
            if ($.trim(this.eCreditCode) == '') {
                this.login_info_error6();
                return false;
            } else if ($.trim(this.ePhone) == '') {
                this.login_info_error7();
                return false;
            } else if ($.trim(this.eImgCode) == '') {
                this.login_info_error8();
                return false;
            } else if ($.trim(this.eCode) == '') {
                this.login_info_error9();
                return false;
            } 
            // else if ($.trim(this.password) != $.trim(this.repassword)) {
            //     this.login_info_error5();
            //     return false;
            // } 
            else {
                return true;
            }
        },
        // 企业注册提交
        eSubmitReg: function () {
            var v = this.eOrdinaryValidata();
            if (v) {
                this.change();
            }
        },
        //企业密码
        companyPassword:function(){
            var reg = /^(\w){6,12}$/;
            var password = $.trim(this.companyPasswordData);
            if ($.trim(this.companyPasswordData) == '') {
                this.login_info_error10();
                return false;
            } else if (!reg.test(password)) {
                this.login_info_error10();
                $(".login_info10>s").html('密码必须由6-12位数字或者字母组成')
                return false;
            } else {
                this.login_info_true10();
                return true
            }
        },
        companyRepassword:function(){
            if ($.trim(this.companyPasswordData) != '' && $.trim(this.companyPasswordData) != $.trim(this.companyRepasswordData)) {
                this.login_info_error11();
                $(".login_info11>s").html('两次密码不一致')
                return false;
            } else {
                this.login_info_true11();
                return true
            }
        },

        lastSubmitCondition:function(){
            if ($.trim(this.companyPasswordData) == '') {
                this.login_info_error10();
                return false;
            } else if ($.trim(this.companyRepasswordData) == '') {
                this.login_info_error11();
                return false;
            } else if ($.trim(this.companyNameData) == '') {
                this.login_info_error12();
                return false;
            } 
            else {
                return true;
            }
        },
        lastSubmitReg:function(){
            var s=this.lastSubmitCondition();
            if(s){
                htajax.postForm("/ht-shiro/loginuser/register", {
                        phone: this.phone,
                        password: this.password,
                        repassword: this.repassword,
                        imgCode: this.imgCode,
                        code: this.code,
                    },
                    function (data) {
                        if (data.code == 10000) {

                            $(".loading_button").css("background", "none");
                            $(".loading_button").css("backgroundColor", "#dbdbdb");
                            layer.load();
                            layer.msg('您已成功注册，现为你跳转登录！');
                            setInterval(function () {
                                if (!box.getUrlData('returnUrl')) {
                                    location.href = "/beetl/login/toLogin.html";
                                } else {
                                    location.href = "/beetl/login/toLogin.html?returnUrl=" + box.returnurl;
                                }
                            }, 3000);

                        } else {
                            box.changeCode();
                            layer.msg(data.msg);
                        }
                    },
                    function (data) {
                        box.changeCode();
                        $(".submitInfo").fadeIn("500");
                        $("#overlay").fadeIn("300");
                        // layer.alert(data.msg);
                    });
            }
        },
    },
});