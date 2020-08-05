/**
 * Created by oll on 2019/8/12.
 */

var box = new Vue({
    el: '#box',
    data: {
        allkeyword: '',
        chosePic: 0, //选择图片充值
        payMoney: 0, //支付金钱
        payid: "",
        userInfo: "",
        honey: "",
        honeyData: [], //honey充值数据
        amount: 0, //微信支付金额
        headImg:'',
    },
    created: function () {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getHoneyData();
            //this.getUserInfo();
        },
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        //获取honey充值数据
        getHoneyData: function () {
            htajax.get("/beetl/payment/rechargelist", function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    box.honeyData = data.data;
                    box.payMoney = box.honeyData[0].newmoney;
                    box.payid = box.honeyData[0].id;
                    box.honey = box.honeyData[0].honey;
                }
            }, function (data) {
                layer.msg(data.msg);
            });
        },

        doChose: function (index, money, id) {
            box.chosePic = index;
            box.payMoney = money;
            box.payid = id;
            box.honey = box.honeyData[index].honey;
        },

        index: function (id, paymentCash, payType) {
            if(payType == 0){
                var paymentType = 'alipay';
            }
            if (payType == 1){
                var paymentType = 'Wechat';
            }
            htajax.get("/beetl/payment/payment?id=" + id + "&paymentCash=" + paymentCash + "&paymentType=" + paymentType, function (data) {
                    // console.log(data);
                    if (data.code == 10000) {
                        // console.log(data);
                        box.entityPd = data.data.pd;
                        box.entityPo = data.data.po;
                        if (payType == 0) {
                            location.href = "/beetl/payment/jumping/" + box.entityPd.paymentOrderId;
                        } else {
                            box.wechatWin();
                        }
                    }
                },
                function () {
                    // 错误回调
                })
        },
        //支付宝
        jumpAlipay: function (id) {
            this.index(id, 4, 0);
        },
        //微信支付弹窗
        wechatPay: function () {
            this.index(box.payid, 4, 1);

            //$(".wechatPay").fadeIn();
        },
        ajaxstatus: function (id) {
            if (id != "") {
                var id = id;
                var url = "/order/queryOrderWeiXin/" + id;
                $.ajax({
                    url: url,
                    type: "POST",
                    dataType: "text",
                    success: function (data) {
                        if (data == "success") {
                            box.weiXinPaySuccess(id);
                        }
                    },
                    error: function (data) {}
                });
            }
        },

        //微信支付成功之后
        weiXinPaySuccess: function (id) {
            htajax.get("/order/getWeixinSuccess?id=" + id, function (data) {
            	if (data.code == 10000) {
                        //window.location.href = "/beetl/myorder/payment_success.html?orderId=" + data.data.businessId + "&paymentNo=" + data.data.paymentNo; //页面跳转
                        //location.href = "/beetl/myintegral/index";
                    	location.href="/ht-biz/usercenter/user/honey";
                    }
                },
                function () {
                    // 错误回调
                })
        },


        
        wechatWin: function () {

            htajax.get("/order/weixinJumping/" + box.entityPd.paymentOrderId, function (data) {
                    if (data.code == 10000) {
                        box.wechatPayData = data.data;
                        box.amount = box.wechatPayData.pageData.amount;

                        $("#payCode").attr("src", "/order/getQRCode?code_url=" + box.wechatPayData.code_url + "&t=" + new Date().getTime());
                        $(".wechatPay").fadeIn();

                        //setTimeout(box.ajaxstatus(box.wechatPayData.pageData.id), 2000);
                        setInterval("box.ajaxstatus(box.wechatPayData.pageData.id)", 2000);
                        //$(".wechatPay").fadeIn();
                    }
                },
                function () {
                    // 错误回调
                })
        },


        closeWechatPay: function () {
            $(".wechatPay").fadeOut();
        },

        // getUserInfo: function () {
        //     htajax.get("/beetl/login/getUser.do", function (data) {
        //         if (data.code == 0) {
        //             box.userInfo = data.data;
        //         }
        //     }, function (data) {
        //         layer.msg(data.msg);
        //     });
        // },


        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)')
                .exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    },

});