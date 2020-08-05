/**
 * Created by oll on 2019/9/12.
 */


var box = new Vue({
    el: '#box',
    data: {
        password: "",
        repassword: "",
        ecode:"",
        imgCode:"",
        sendPhone:"",

    },
    created: function () {
    },
    methods: {
        changeCode: function () {
            $("#codeImg").attr("src", "/ht-biz/sendcode/codeinfo?t=" + new Date().getTime());
        },
        esubmitReg: function () {
            var v = this.ordinaryValidata();
            if (v) {
                var mobilePhone =box.sendPhone;

                htajax.postForm("/ht-shiro/sysuser/retrievepassword",
                    {
                        phone: mobilePhone,
                        code: box.ecode,
                        password: box.password,
                    },
                    function (data) {
                        //console.log(data);
                        if (data.code == 10000) {
                            
                        	layer.alert('密码修改成功', {
                        		  skin: 'layui-layer-rim' //样式类名
                        		  ,closeBtn: 0
                        		}, function(index){
                                    layer.load(1, {shade: [0.5,'#fff'] }); //0代表加载的风格，支持0-2
                        		    location.href="/ht-biz/login/login";
                        		    layer.close(index);
                        		});
                           
                        } else if(data.code==1) {
                            layer.alert("找回密码失败，请重试");
                        }else if(data.code==2){
                            layer.msg("验证码错误");
                        }else if(data.code==3){
                            layer.alert("该号码未注册过，请先注册");
                        }
                    }, function (data) {
                        layer.alert(data.msg);
                    });
            }

        },

        getcode: function () {
            var step = 59;
            var _res = "";
            var mobilePhone = box.sendPhone;
            var imgCode=$.trim($("#imgCode").val());
            if (mobilePhone == "") {
                layer.msg('手机号码不能为空');
                return false;
            }
            if(imgCode == ""){
                layer.msg('图形验证不能为空');
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
                            box.changeCode();
                            clearInterval(_res);
                        }
                    },
                    error: function (msg, error) {
                        layer.msg(msg.msg);
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


        ordinaryValidata: function () {//普通注册验证
            if($.trim(this.sendPhone) == ''){
                layer.msg("请输入手机号");
            }else if ($.trim(this.password) == '') {
                layer.msg("请输入密码");
                return false;
            } else if ($.trim(this.password) != $.trim(this.repassword)) {
                layer.msg("确认密码输入不正确");
                return false;
            }else if ($.trim(this.imgCode) != $.trim(this.imgCode)) {
                layer.msg("图形验证码不能为空");
                return false;
            }else {
                return true;
            }
        },


    },


});
