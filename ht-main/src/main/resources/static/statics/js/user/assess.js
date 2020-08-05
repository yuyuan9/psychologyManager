/**
 * Created by ACER on 2019/8/26.
 */
Vue.filter('hideMiddle', function (value) {
    var strval = value.toString();
    return value = `${strval.substring(strval.length-9)}`
});
Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD hh:mm:ss';
    return moment(value).format(formatString);
});

var box = new Vue({
    el:'#box',
    data() {
        return {
            allkeyword: '',
            cur:'',
            curInfo:0,
            deleteShow:'',
            deleteShow2:'',
            mobile:'',
            userId:'',
            username:'',
            code:'',
            userHoney:'',
            userGrade:'',
            shareData:'',

            userInfoType:'',
            assessData:[],
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            targetData:'',
            headImg:'',
        }
    },
    created() {
        this.initPage();
    },
    methods:{
        initPage:function(){
            this.getGrade();
            this.getUserInfo();
            this.getAssess();
            this.getUserInformation();
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
                    // 用户的honey值
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
        getGrade:function(){
            htajax.get("/ht-biz/honeymanager/getUserLevel?type=" + 1 + "&t=" + new Date().getTime(), function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.userGrade = data.data.sort;
                    // console.log(box.userGrade);
                }else{
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getAssess:function(index){
            var current = index;
            if(this.targetData != ""){
                current = this.targetData;
            }
            if(current == '' || current == undefined){
                current = 1;
            }
            htajax.get("/ht-biz/hisself/findByCreateId?current=" + current + "&size=" + 4 + "&t=" + new Date().getTime() , function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.assessData = data.data.records;
                    
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
                box.getAssess(index);
            }
        },
        getUserInformation:function(){
            htajax.postForm("/ht-shiro/sysuser/myinfo?t=" + new Date().getTime(),{}, function (data) {
                if (data.code == 10000) {
                    box.userInfoType = data.data.userType;
                }else{
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})
