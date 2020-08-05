/**
 * Created by dsy on 2019/5/17
 */
var box = new Vue({
    el: '#box',
    data: {
        isChoose: 1,
        eIsChoose: 1,
        isActive: 0,
        phone: '',
        imgCode: '',
        code: '',
        password: '',
        repassword: '',
        isProtocol: true,
        eIsProtocol: false,
        eCreditCode: '',
        ePhone: '',
        eImgCode: '',
        eCode: '',
        companyPasswordData: '',
        companyRepasswordData: '',
        companyNameData: '',
    },
    created: function () {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.changeCode();
            this.eChangeCode();
        },
        close: function () {
            $(".submitInfo").hide();
            $("#overlay").hide();
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
        imgCodeData: function () {
            if ($.trim(this.imgCode) == '') {
                layer.msg("请输入图形验证码");
                return false;
            }
        },
        codeData: function () {
            if ($.trim(this.code) == '') {
                layer.msg("请输入短信验证码");
                return false;
            }
        },
        changeCode: function () {
            $("#codeImg").attr("src", "/ht-biz/sendcode/codeinfo?t=" + new Date().getTime());
        },
        // 个人手机验证码
        getcode: function () {
            var imgcode = $("#imgCode").val();
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
                        verificacode: imgCode
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
                    success: function (data) {
                        // console.log(data);
                        if (data.code == 10000) {
                            layer.msg("验证码发送成功");
                            clearInterval(_res);
                            $("#sendCode").attr("disabled", false);
                            $('#sendCode').html("重新发送");
                        } else {
                            layer.msg(data.msg);
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
                layer.msg("请输入密码");
                return false;
            } else if (!reg.test(password)) {
                layer.msg("密码必须由6-12位数字或者字母组成");
                return false;
            } else {
                return true;
            }
        },
        reValpassword: function () {
            if ($.trim(this.password) != '' && $.trim(this.password) != $.trim(this.repassword)) {
                layer.msg("两次密码不一致");
                return false;
            }
        },
        ordinaryValidata: function () { //普通注册验证
            var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;
            var regBox = {
                regMobile: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/, //手机
            }
            var mflag = regBox.regMobile.test($.trim(this.phone));
            var regpsd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/;
            
            if (!(mflag)) {
                layer.msg("请输入正确的手机号码");
                return false;
            }
            if ($.trim(this.phone) == '') {
                layer.msg("请输入正确的手机号码");
                return false;
            }
            if ($.trim(this.imgCode) == '') {
                layer.msg("请输入图形验证码");
                return false;
            }
            if ($.trim(this.code) == '') {
                layer.msg("请输入短信验证码");
                return false;
            }
            if ($.trim(this.password) == '') {
                layer.msg("请输入密码");
                return false;
            }
            if (!regpsd.test($.trim(this.password))||$.trim(this.password).length<6||$.trim(this.password).length>12){
                layer.msg("请输入6-12位数字或字母组合密码");
                return false;
            }
            if ($.trim(this.repassword) == '') {
                layer.msg("请输入确认密码");
                return false;
            }
            if ($.trim(this.password) != $.trim(this.repassword)) {
                layer.msg("两次密码不一致");
                return false;
            }
        },
        // 个人注册提交
        submitReg: function () {
            if (this.ordinaryValidata()) {
                htajax.postFormDone("/ht-shiro/loginuser/register", {
                        username: this.phone,
                        password: this.password,
                        code: this.code,
                    },
                    function (data) {
                        layer.load(1, {
                            shade: [0.5, '#fff'] //0.1透明度的白色背景
                        });
                    },
                    function (data) {
                        if (data.code == 10000) {
                            $(".loading_button").css("background", "none");
                            $(".loading_button").css("backgroundColor", "#dbdbdb");
                            layer.closed(index);
                            layer.msg('您已成功注册，现为你跳转登录！');
                            setInterval(function () {
                                location.href = "/ht-biz/login/login";
                            }, 1000);
                        } else {
                            box.changeCode();
                            layer.msg(data.msg);
                        }
                    },
                    function (data) {
                        box.changeCode();
                        $(".submitInfo").fadeIn("500");
                        $("#overlay").fadeIn("300");
                        layer.msg("服务器繁忙");
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
        //切换图形验证码
        eChangeCode: function () {
            $("#eCodeImg").attr("src", "/ht-biz/sendcode/codeinfo?t=" + new Date().getTime());
        },
        //获取验证码
        eGetCode: function () {
            var step = 59;
            var _res = "";
            var creditCode = $.trim($("#eCreditCode").val());
            var mobilePhone = $.trim($("#ePhone").val());
            var imgCode = $.trim($("#eImgCode").val());
            var regBox = {
                regMobile: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/, //手机
            }
            var mflag = regBox.regMobile.test($.trim(this.ePhone));
            var str = /^[A-Za-z0-9]{18}/;
            var creditCode = $.trim(this.eCreditCode);
            if ($.trim(this.companyNameData) == '') {
                layer.msg("请输入您的公司名称");
                return false;
            }
            if (!(str.test(creditCode))) {
                layer.msg("请输入18位正确的信用代码位数");
                return false;
            }
            if (creditCode == "") {
                layer.msg("请输入18位正确的信用代码位数");
                return false;
            }
            if (!(mflag)) {
                layer.msg("请输入正确的手机号码");
                return false;
            }
            if (mobilePhone == "") {
                layer.msg("请输入手机号码");
                return false;
            }
            if (imgCode == '') {
                layer.msg("请输入图形验证码");
                return false;
            }
            $.ajax({
                type: "GET",
                url: '/ht-shiro/sendcode/sendPhoneCode',
                timeout: 60000,
                data: {
                    phone: mobilePhone,
                    verificacode: imgCode
                },
                dataType: "json",
                beforeSend: function () {
                    _res = setInterval(function () {
                        $("#sendCode2").attr("disabled", true); //设置disabled属性
                        $('#sendCode2').html("重新发送(" + step + ")");
                        step -= 1;
                        if (step <= 0) {
                            $("#sendCode2").removeAttr("disabled"); //移除disabled属性
                            $('#sendCode2').html('获取验证码');
                            clearInterval(_res); //清除setInterval
                            step = 59;
                        }
                    }, 1000);
                },
                success: function (data) {
                    // console.log(data);
                    if (data.code == 10000) {
                        layer.msg("验证码发送成功");
                        clearInterval(_res);
                        $("#sendCode2").attr("disabled", false);
                        $('#sendCode2').html("重新发送");
                    } else {
                        layer.msg(data.msg);
                        box.eChangeCode();
                    }
                },
                error: function (msg, error) {
                    layer.msg(msg);
                    box.eChangeCode();
                    clearInterval(_res);
                }
            });
        },
        eOrdinaryValidata: function () {
            var str = /^[A-Za-z0-9]{18}/;
            var creditCode = $.trim(this.eCreditCode);
            var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;
            var regBox = {
                regMobile: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/, //手机
            }
            var mflag = regBox.regMobile.test($.trim(this.ePhone));
            if (!(str.test(creditCode))) {
                layer.msg("请输入18位正确的信用代码位数");
                return false;
            }
            if (!(mflag)) {
                layer.msg("请输入正确的手机号码");
                return false;
            }

            if ($.trim(this.eCreditCode) == '') {
                layer.msg("请输入18位正确的信用代码位数");
                return false;
            } else if ($.trim(this.ePhone) == '') {
                layer.msg("请输入正确的手机号码");
                return false;
            } else if ($.trim(this.eImgCode) == '') {
                layer.msg("请输入图形验证码");
                return false;
            } else if ($.trim(this.eCode) == '') {
                layer.msg("请输入短信验证码");
                return false;
            } else {
                return true;
            }
        },
        //企业密码
        companyPassword: function () {
            var reg = /^(\w){6,12}$/;
            var password = $.trim(this.companyPasswordData);
            if ($.trim(this.companyPasswordData) == '') {
                layer.msg("请设置6-12位数字或字母组合");
                return false;
            } else if (!reg.test(password)) {
                layer.msg("密码必须由6-12位数字或者字母组成");
                return false;
            } else {
                return true
            }
        },
        companyRepassword: function () {
            if ($.trim(this.companyPasswordData) != '' && $.trim(this.companyPasswordData) != $.trim(this.companyRepasswordData)) {
                layer.msg("两次密码不一致");
                return false;
            } else {
                return true
            }
        },

        lastSubmitCondition: function () {

            var reg = /^(\w){6,12}$/;
            var password = $.trim(this.companyPasswordData);
            if ($.trim(this.companyPasswordData) == '') {
                layer.msg("请设置6-12位数字或字母组合");
                return false;
            } else if (!reg.test(password)) {
                layer.msg("密码必须由6-12位数字或者字母组成");
                return false;
            } else if ($.trim(this.companyPasswordData) != '' && $.trim(this.companyPasswordData) != $.trim(this.companyRepasswordData)) {
                layer.msg("两次密码不一致");
                return false;
            } else if ($.trim(this.companyPasswordData) == '') {
                layer.msg("请设置6-12位数字或字母组合");
                return false;
            } else if (!reg.test(password)) {
                layer.msg("密码必须由6-12位数字或者字母组成");
                return false;
            } else if ($.trim(this.companyPasswordData) != '' && $.trim(this.companyPasswordData) != $.trim(this.companyRepasswordData)) {
                layer.msg("两次密码不一致");
                return false;
            } else if ($.trim(this.companyPasswordData) == '') {
                layer.msg("请设置6-12位数字或字母组合");
                return false;
            } else if ($.trim(this.companyRepasswordData) == '') {
                layer.msg("请再次确认密码");
                return false;
            } else {
                return true;
            }
        },
        lastSubmitReg: function () {
            var creditCode = $.trim($("#eCreditCode").val());
            var mobilePhone = $.trim($("#ePhone").val());
            var imgCode = $.trim($("#eImgCode").val());
            var regBox = {
                regMobile: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/, //手机
            }
            var mflag = regBox.regMobile.test($.trim(this.ePhone));
            var str = /^[A-Za-z0-9]{18}/;
            if ($.trim(this.companyNameData) == '') {
                layer.msg("请输入您的公司名称");
                return false;
            }
            if (!(str.test(creditCode))) {
                layer.msg("请输入18位正确的信用代码位数");
                return false;
            }
            if (creditCode.length!= 18) {
                layer.msg("请输入18位正确的信用代码位数");
                return false;
            }
            if (creditCode == "") {
                layer.msg("请输入18位正确的信用代码位数");
                return false;
            }
            if (!(mflag)) {
                layer.msg("请输入正确的手机号码");
                return false;
            }
            if (mobilePhone == "") {
                layer.msg("请输入手机号码");
                return false;
            }
            if (imgCode == '') {
                layer.msg("请输入图形验证码");
                return false;
            }
            var s = this.lastSubmitCondition();

            if (s) {

                htajax.postFormDone("/ht-shiro/loginuser/qregister", {
                        companyname: this.companyNameData,
                        compregcode: creditCode,
                        username: this.ePhone,
                        code: this.eCode,
                        password: this.companyPasswordData,
                    },
                    function (data) {
                        var index = layer.load(1, {
                            shade: [0.5, '#fff'] //0.1透明度的白色背景
                        });
                    },
                    function (data) {
                        if (data.code == 10000) {
                            $(".loading_button").css("background", "none");
                            $(".loading_button").css("backgroundColor", "#dbdbdb");
                            //layer.load();
                            layer.msg('您已成功注册，现为你跳转登录！');
                            setInterval(function () {
                                location.href = "/ht-biz/login/login";
                            }, 1000);

                        } else {
                            box.eChangeCode();
                            layer.msg(data.msg);
                        }
                    },
                    function (data) {
                        layer.msg(data.msg);
                        box.eChangeCode();
                        $(".submitInfo").fadeIn("500");
                        $("#overlay").fadeIn("300");
                        // layer.alert(data.msg);
                    });
            }
        },
    },
});