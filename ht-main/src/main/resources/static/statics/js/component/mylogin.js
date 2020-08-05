/**
 * Created by oll on 2017/9/7.
 * 
 * 
 * 
 * 
 */

var mylogin = Vue.extend({

    template: '<div v-show="!islogin" class="loginBox" v-cloak>'
        +'<div class="contract_mask"></div>'
        +'<div class="loading_m_r fr" style="z-index: 2000">'
        +'<div class="table_top">登录账号'
        +'<a href="/ht-biz/login/register" target="_blank">注册</a>'
        +'<s class="close_login fr" @click="closelogin">×</s>'
        +'</div>'
        +'<div class="table_bottom">'
        +'<input type="text" name="phone" placeholder="手机号" @keyup.enter="loginsubmit" />'
        +'<input type="password" name="password" placeholder="登录密码" @keyup.enter="loginsubmit" />'
        +'<input class="fl code_l" name="code" type="text" placeholder="验证码" @keyup.enter="loginsubmit" />'
        +'<div class="code_r fl">'
        +'<img id="codeImg" alt="" @click="changeCode" />'
        +'</div>'
        +'<input class="loading_button button_active" id="submitReg" type="button" @click="loginsubmit" @keyup.enter="loginsubmit" value="立即登录" />'
        +'<div class="remember">'
        +'<p class="autoLogin"><input type="checkbox" id="a1" class="login_checked"> '
         +'<label id="a1">记住密码</label> <a class="wjmm" href="/ht-biz/login/forget_password">忘记密码</a></p>'
        +' <p class="autoLogin"><input type="checkbox" checked="checked" id="a2" class="login_checked"> '
        +'<label for="a2" id="a2">登录即视为同意'
        +'<a class="tiaokuan" href="http://g.hights.cn/beetl/help/rule_detail.html?type=104">《高企云服务条款》</a>'
        +'<label></label></label></p>'
        +'</div>'
        +'</div>'
        +'</div>'
        +'</div>',

    props: ['islogin'],
    created: function () {
        this.changeCode();
    },
    methods: {
        closelogin: function () {
            //this.$emit('close-login');
            $(".loginBox").hide();
        },
        loginsubmit: function () {
            var vm = this;
            if (this.validate_form()) {
                //ajax 验证提交
                layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
                var loginname = $.trim($("input[name='phone']").val());
                var password = $.trim($("input[name='password']").val());
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
                        layer.msg("登录成功");
                        window.location.reload();
                        $(".loginBox").hide();
                        vm.changeCode();
                    }else{
                        layer.closeAll("loading");
                        layer.msg(data.msg);
                        vm.changeCode();
                    }
                }, function (data) {
                    //错误回调
                    layer.msg("系统繁忙，请稍候重试！");
                    vm.changeCode();
                    layer.closeAll("loading");
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
                $("#codeImg").attr("src", "/ht-biz/sendcode/codeinfo?t=" + new Date().getTime());  
            })
        }
    }
});
Vue.component("mylogin", mylogin);
