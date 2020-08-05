/**
 * Created by ACER on 2019/8/12.
 */
Vue.filter('hideMiddle', function (value) {
    var strval = value.toString();
    return value = `${strval.substring(0,3)}****${strval.substring(strval.length-4)}`
});
Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD HH:MM:SS';
    return moment(value).format(formatString);
});

var box = new Vue({
    el:'#box',
    data() {
        return {
            allkeyword: '',
            honeyTab:0,
            deleteShow:'',
            deleteShow2:'',
            userId:'',
            username:'',
            code:'',
            targetData:'',
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            userHoney:'',
            money:'',
            userGrade:'',
            userBill:'',
            userBill1:'',

            userInfoType:'',
            headImg:'',
        }
    },
    created() {
        this.initPage();
    },
    methods:{
        initPage:function(){
            this.getUserInfo();
            this.getUserInformation();
            this.getHoney();
            this.getWater();
            // this.getMoneyWater();
            this.getMoney();
        },
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        // 获取用户的信息
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser", function (data) {
                if (data.code == 10000) {
                    box.userId = data.data.userId;
                    box.username = data.data.username;
                    box.code = data.code;
                    box.headImg = data.data.headImg;
                }else{
                    // layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getUserInformation:function(){
            htajax.postForm("/ht-shiro/sysuser/myinfo",{}, function (data) {
                if (data.code == 10000) {
                    box.userInfoType = data.data.userType;
                }else{
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        currentPage:function(){},
        getHoney:function(){
            htajax.get("/ht-biz/honeymanager/getHoneyTotal", function (data) {
                if (data.code == 10000) {
                    box.userHoney = data.data;
                    if(box.userHoney  < 0){
                        box.userHoney = 0;
                    }
                }else{
                    //layer.msg(data.msg);
                }
                box.getGrade();
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getGrade:function(){
            htajax.get("/ht-biz/honeymanager/getUserLevel?type=" + 1, function (data) {
                if (data.code == 10000) {
                    box.userGrade = data.data.sort;
                }else{
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        infoMoney:function(){
            layer.msg("敬请期待...")
        },
        getMoney:function(){
            htajax.postForm("/ht-biz/myinfo/mycole",{}, function (data) {
                if (data.code == 10000) {
                    box.money = data.data;
                }else{
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });

            
        },
        getWater:function(index){
            var current = index;
            if(this.targetData != ""){
                current = this.targetData;
            }
            if(current == 0){
                current = 1;
            }
            if(current == undefined){
                current = 1;
            }
            htajax.get("/ht-biz/honeymanager/getUserWater?size=" + 12 + "&current=" + current, function (data) {
                if (data.code == 10000) {
                    box.userBill = data.data.records;
                    box.page.currentPage = data.data.current;
                    box.page.totalPage = data.data.pages;
                    box.page.totalResult = data.data.total;
                }else{
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getMoneyWater:function(index){
            var current = index;
            if(this.targetData != ""){
                current = this.targetData;
            }
            if(current == 0){
                current = 1;
            }
            if(current == undefined){
                current = 1;
            }
            htajax.get("/ht-biz/honeymanager/getGoldcoin?size=" + 12 + "&current=" + current, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.userBill1 = data.data.records;
                    box.page.currentPage = data.data.current;
                    box.page.totalPage = data.data.pages;
                    box.page.totalResult = data.data.total;
                }else{
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //分页
        clickPage:function(index){
            if (index != 0) {
                if(this.honeyTab == 0){
                    box.getWater(index);
                }
                if(this.honeyTab == 1){
                    box.getMoneyWater(index);
                }
               
            }
        },
        //鼠标隐藏提示
        enter: function () {
            $(".info_img").fadeIn(200);
        },
        leave: function () {
            $(".info_img").fadeOut(200);
        },
        enter1: function () {
            $(".info_img1").fadeIn(200);
        },
        leave1: function () {
            $(".info_img1").fadeOut(200);
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})
