/**
 * Created by ACER on 2019/10/24.
 */
Vue.filter('hideMiddle', function (value) {
    var strval = value.toString();
    return value = `${strval.substring(0, 4)} **** **** ${strval.substring(strval.length - 4)}`
});

var box = new Vue({
    el: '#box',
    data() {
        return {
            allkeyword: '',
            userId: '',
            honeyInfo1: false,
            honeyInfo2: false,
            username: '',
            code: '',
            headImg: '',
            userGrade: '',
            userInfoType: '',
            cardList:[],
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getUserInfo();
            this.getUserInformation();
            this.getGrade();
            this.getBankCard();
        },
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        // 获取用户的信息
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser?t=" + new Date().getTime(), function (data) {
                if (data.code == 10000) {
                    box.userId = data.data.userId;
                    box.username = data.data.username;
                    box.code = data.code;
                    box.headImg = data.data.headImg;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getGrade: function () {
            htajax.get("/ht-biz/honeymanager/getUserLevel?type=" + 1 + "&t=" + new Date().getTime(), function (data) {
                if (data.code == 10000) {
                    box.userGrade = data.data.sort;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //获取银行卡
        getBankCard:function(){
            htajax.get("/ht-biz/myinfo/getmybankcar?t=" + new Date().getTime(), function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    box.cardList = data.data;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getUserInformation: function () {
            htajax.postForm("/ht-shiro/sysuser/myinfo?t="  + new Date().getTime(), {}, function (data) {
                if (data.code == 10000) {
                    box.userInfoType = data.data.userType;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getContent: function () {
            var cardName = $.trim($("input[name='cardName']").val());
            var phone = $.trim($("input[name='phone']").val());
            var idNumber = $.trim($("input[name='idNumber']").val());
            var bank = $.trim($("input[name='bank']").val());
            idNumber = idNumber.replace(/\s/g, '');

            if (cardName == "") {
                layer.msg("持卡人姓名");
                return false;
            }
            if (phone == '') {
                layer.msg("请填写手机号码");
                return false;
            }
            if (phone != '') {
                var search_str = /^[1][3,4,5,7,8][0-9]{9}$/;
                if (!search_str.test(phone)) {
                    layer.msg("请填写正确的手机号码");
                    return false;
                }
            }
            if (idNumber == "") {
                layer.msg("请填写银行卡号");
                return false;
            }
            if (bank == "") {
                layer.msg("请填写银行类型");
                return false;
            }
            if (idNumber.length < 16 || idNumber.length > 19) {
                layer.msg("银行卡号长度必须在16到19之间");
                return false;
            }
            var num = /^\d*$/; //全数字
            if (!num.exec(idNumber)) {
                layer.msg("银行卡号必须全为数字");
                return false;
            }
            htajax.postForm("/ht-biz/myinfo/insertbankcar", {
                acuountname: cardName,
                phone:phone,
                banktype: bank,
                carnumber: idNumber,
            }, function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    if (data.data == "添加成功") { layer.msg("添加成功")}
                    setTimeout(function () {
                        location.reload();
                    }, 1500)
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        deteleCard: function (event){
            var targetId = event.toElement.id;
            //console.log(targetId); 
            htajax.postForm("/ht-biz/myinfo/removebyId", {
                id: targetId,
            }, function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    if (data.data == "删除成功") { layer.msg("删除成功") }
                    setTimeout(function () {
                        location.reload();
                    }, 1500)
                }
            }, function (data) {
                //layer.msg(data.msg);
            });

        },
        popping: function () {
            this.honeyInfo1 = true;
        },
        cancel: function () {
            this.honeyInfo1 = false;
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})