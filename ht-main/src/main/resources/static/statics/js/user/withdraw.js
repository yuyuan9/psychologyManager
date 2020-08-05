/**
 * Created by ACER on 2019/10/30.
 */
Vue.filter('hideMiddle', function (value) {
    var strval = value.toString();
    return value = `${strval.substring(0, 4)} **** ${strval.substring(strval.length - 4)}`
});

var box = new Vue({
    el: '#box',
    data() {
        return {
            cur: 0,
            curCard: -1,
            mun1: false,
            mun2: false,
            mun3: false,
            mun4: false,
            allkeyword: '',
            userHoney: '',
            money: '',
            gold: '',
            honeyInfo1:false,
            getSomeMoney:0,
            exMoney:0,
            myMoney:0,
            cardList:[],
            name:'',
            banktype: '',
            carnumber: '',
            phone: '',
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getMoney();
            this.getBankCard();
        },
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        getMoney: function () {
            htajax.postForm("/ht-biz/myinfo/mycole", {}, function (data) {
                if (data.code == 10000) {
                    box.money = data.data;
                    box.detectionMoney();
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //检测是否可以兑换
        detectionMoney:function(){
            var mun = box.money;
            if (mun > 2000) {this.mun1=true; }
            if (mun > 3000) { this.mun1 = true; this.mun2 = true;}
            if (mun > 4000) { this.mun1 = true; this.mun2 = true; this.mun3 = true; }
            if (mun > 5000) { this.mun1 = true; this.mun2 = true; this.mun3 = true; this.mun4 = true;}
        },
        //屏蔽按钮
        warning:function(index){
            switch (index) {
                case 1:
                    if (!this.mun1) {  this.cur = 0; this.exMoney = 0; this.myMoney = 0;};
                    break;
                case 2:
                    if (!this.mun2) {  this.cur = 0; this.exMoney = 0; this.myMoney = 0;};
                    break;
                case 3:
                    if (!this.mun3) {  this.cur = 0; this.exMoney = 0; this.myMoney = 0;};
                    break;
                case 4:
                    if (!this.mun4) {  this.cur = 0; this.exMoney = 0; this.myMoney =0;};
                    break;
            }
        },
        //计算
        countMoney:function(mun){
            this.getSomeMoney = mun;
            this.exMoney = mun * 0.1;
            this.myMoney = this.exMoney - (this.exMoney * 0.1) ;
        },
        close: function () {
            $(".submitInfo").hide();
        },
        prev: function () {
            location.href = '/ht-biz/usercenter/user/honey';
        },
        
        popping: function () {
            this.honeyInfo1 = true;
        },
        cancel: function () {
            this.honeyInfo1 = false;
        },
        //获取银行卡
        getBankCard: function () {
            htajax.get("/ht-biz/myinfo/getmybankcar", function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    box.cardList = data.data;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //添加银行卡
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
                phone: phone,
                banktype: bank,
                carnumber: idNumber,
            }, function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    if (data.data == "添加成功") { layer.msg("添加成功") }
                    setTimeout(function () {
                        location.reload();
                    }, 1500)
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getInfo: function (event){
            var index = this.curCard;
            htajax.get("/ht-biz/myinfo/getmybankcar", function (data) {
                if (data.code == 10000) {
                    //console.log(data.data[index]);
                    box.name = data.data[index].acuountname;
                    box.banktype = data.data[index].banktype;
                    box.carnumber = data.data[index].carnumber;
                    box.phone = data.data[index].phone;
                    if (box.carnumber != ''){
                        $('#submitRegs').attr("disabled", false);
                        $(".coin_comfirm").addClass("bgbtn");
                    }
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //兑换
        conversion: function () {
            $("#submitRegs").attr({ "disabled": "disabled" });
            $("#submitRegs").removeClass("bgbtn ");
            var applygold = this.getSomeMoney;
            var banktype = this.banktype;
            var name = this.name;
            var carnumber = this.carnumber;
            var phone = this.phone;
            if (applygold >= 2000){
                htajax.postForm("/ht-biz/Substitute/cash", {
                    applygold: applygold,//兑换金币额度
                    banktype: banktype,//银行类型
                    applicants: name,//姓名
                    cardnumber:carnumber,//银行卡号
                    phone: phone,//手机号码
                }, function (data) {
                    if (data.code == 10000) {
                        // console.log(data);
                        $(".submitInfo").fadeIn(200);
                        setTimeout(function () {
                            location.reload();
                        }, 2000)
                        //box.money = data.data;
                    } else {
                        //layer.msg(data.msg);
                    }
                }, function (data) {
                    //layer.msg(data.msg);
                });
            }else{
                layer.msg("请选择提现金额")
            }
            
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})