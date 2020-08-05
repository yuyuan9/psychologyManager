/**
 * Created by oll on 2017/9/7.
 */

var mylogin = Vue.extend({
    template: '<div v-show="!islogin" v-cloak>'
    + '<div class="contract_mask"></div>'
    + '<div class="loading_m_r fr" style="z-index: 2000">'
    + '<div class="table_top">欢迎登录高企云'
    + '<s class="close_login fr" @click="closelogin">×</s>'
    + '</div>'
    + '<div class="table_bottom">'
    + '<input type="text" name="phone" placeholder="手机号/邮箱" @keyup.enter="loginsubmit"/>'
    + '<input type="password" name="password" placeholder="登录密码" @keyup.enter="loginsubmit"/>'
    + '<input class="fl code_l" name="code" type="text" placeholder="验证码" @keyup.enter="loginsubmit"/>'
    + '<div class="code_r fl">'
    + '<img id="codeImg" alt="" @click="changeCode"/>'
    + '</div>'
    + '<input class="loading_button button_active" id="submitReg" type="button" @click="loginsubmit" @keyup.enter="loginsubmit" value="登  录"/>'
    + '<div class="remember">'
    + '<a href="/beetl/reg/toRegister.html" class="fl" target="_blank">立即注册</a>'
    + '<a href="/beetl/mycenter/forget_password.html" class="fr" target="_blank">忘记密码</a>'
    + '</div>'
    + '</div>'
    + '</div>'
    + '</div>',
    props: ['islogin'],
    created: function () {
        this.changeCode();
    },
    methods: {
        closelogin: function () {
            this.$emit('close-login');
        },
        loginsubmit: function () {
            if (this.validate_form()) {
                //ajax 验证提交
                var loginname = $.trim($("input[name='phone']").val());
                var password = $.trim($("input[name='password']").val());
                var codevalue = $.trim($('input[name="code"]').val());
                //var rememberMe = $("input[type='checkbox']").is(':checked');
                $.ajax({
                    type: "POST",
                    url: '/login_login',
                    timeout: 60000,
                    data: {"PHONE": loginname, "PASSWORD": password, "CODE": codevalue, "rememberMe": 'false'},
                    cache: false,
                    beforeSend: function () {
                    },
                    success: function (msg) {
                    	 if (msg.indexOf("success") !=-1) {
                            $(".loading_button").css("background", "none");
                            $(".loading_button").css("backgroundColor", "#dbdbdb");
//                            layer.load();
                            location.reload();
                        } else {
                            layer.msg(msg);
                        }
                    },
                    error: function (error) {
                        layer.msg("系统繁忙，请稍候重试！");
                    }
                });


            }
        },

        validate_form: function () {
            var phone = $('input[name="phone"]');
            var pwd = $('input[name="password"]');
            var code = $.trim($("input[name='code']").val());
            if (!this.phonemsg(phone, '登录账号错误')) {
                return false;
            }

            if (!this.isNotNull(pwd, '密码不能为空')) {
                return false;
            }
            if (code == "") {
                layer.msg("验证码不能为空！");
                return false;
            }
            return true;
        },
        phonemsg: function (obj, msg) {
            var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/;
            if (reg.test($.trim(obj.val()))) {
                return true;
            } else {
                if (msg != null) {
                    layer.msg(msg);
                } else {
                    layer.msg('手机号码错误');
                }

                obj.focus();
                return false;
            }

        },
        isNotNull: function (obj, msg) {
            if ($.trim(obj.val()) == '' ||
                $.trim(obj.val()) == null ||
                $.trim(obj.val()) == 'undefined') {
                layer.msg(msg);
                obj.focus();
                return false;
            }
            return true;
        },
        changeCode: function () {
            Vue.nextTick(function () {
                $("#codeImg").attr("src", "/code.do?t=" + new Date().getTime());

            })
        }
    }
});
Vue.component("mylogin", mylogin);
