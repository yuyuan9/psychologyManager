/**
 * Created by ACER on 2019/10/24.
 */
var box = new Vue({
    el: '#box',
    data() {
        return {
            allkeyword: '',
            userId: '',
            userHoney: '',
            money: '',
            gold: '',
            isProtocol: '',
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getMoney();
        },
        //全局搜索
        allSearch: function () {
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        getMoney: function () {
            htajax.postForm("/ht-biz/myinfo/mycole", {}, function (data) {
                if (data.code == 10000) {
                    box.money = data.data;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        protocol: function () { 
            if (this.gold != 0) {
                $("#submitRegs").attr("disabled", false);
                $("#submitRegs").addClass("btn");
            } else {
                $("#submitRegs").attr("disabled", true);
                $("#submitRegs").removeClass("btn");
            }
        },
        close: function () {
            $(".submitInfo").hide();
        },
        prev:function(){
            location.href='/ht-biz/usercenter/user/honey';
        },
        conversion: function () {
            $("#submitRegs").attr({ "disabled": "disabled" });
            $("#submitRegs").removeClass("btn");
            var ss = this.gold; //获取输入的值
            if (ss <= this.money) {
                if (!isNaN(ss) && ss >= 0.1) {
                    htajax.postForm("/ht-biz/Substitute/gethoney?t=" + new Date().getTime(), {
                        applygold: this.gold,
                    }, function (data) {
                        if (data.code == 10000) {
                            // console.log(data);
                            $(".submitInfo").fadeIn(200);
                            setTimeout(function () {
                                location.reload();
                            }, 1500)
                            box.money = data.data;
                        } else {
                            //layer.msg(data.msg);
                        }
                    }, function (data) {
                        //layer.msg(data.msg);
                    });
                } else {
                    layer.msg("请输入纯合理数字！")
                    return;
                }
            } else {
                layer.msg("超出可兑换额度");
                $("#submitRegs").removeClass("btn");
            }
            
            
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})