/**
 * Created by ACER on 2019/8/12.
 */
Vue.filter('hideMiddle', function (value) {
    let strval = value.toString();
    return value = `${strval.substring(0,3)}****${strval.substring(strval.length-4)}`
});
Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD hh:mm:ss';
    return moment(value).format(formatString);
});

var box = new Vue({
    el:'#box',
    data() {
        return {
            allkeyword:'',
            cur:'',
            curInfo:0,
            deleteShow:'',
            deleteShow2:'',
            mobile:18216199930,
            userId:'',
            username:'',
            code:'',
            userHoney:'',
            userGrade:'',
            shareData:'',

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
            this.getHoney();
            this.shareRecord();
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
                    // console.log(box.userId);
                }else{
                    // layer.msg(data.msg);
                }
            }, function (data) {
                layer.msg(data.msg);
            });
        },
        getUserInformation:function(){
            htajax.postForm("/ht-shiro/sysuser/myinfo",{}, function (data) {
                if (data.code == 10000) {
                    box.userInfoType = data.data.userType;
                }else{
                    layer.msg(data.msg);
                }
            }, function (data) {
                layer.msg(data.msg);
            });
        },
        getHoney:function(){
            htajax.get("/ht-biz/honeymanager/getHoneyTotal", function (data) {
                if (data.code == 10000) {
                    box.userHoney = data.data;
                }else{
                    layer.msg(data.msg);
                }
                box.getGrade();
            }, function (data) {
                layer.msg(data.msg);
            });
        },
        getGrade:function(){
            htajax.get("/ht-biz/honeymanager/getUserLevel?type=" + 1, function (data) {
                if (data.code == 10000) {
                    box.userGrade = data.data.sort;
                }else{
                    layer.msg(data.msg);
                }
            }, function (data) {
                layer.msg(data.msg);
            });
        },
        copyLink:function(){
            var Url2 = $("#copyId").val();
            var oInput = document.createElement("input");
            oInput.value = Url2;
            document.body.appendChild(oInput);
            oInput.select(); // 选择对象
            document.execCommand("Copy"); // 执行浏览器复制命令
            oInput.className = "oInput";
            oInput.style.display = "none";
            layer.msg("复制成功");
        },
        shareRecord:function(){
            htajax.postForm("/ht-biz/share/getmyshare",{} ,function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.shareData = data.data.records;
                }else{
                    layer.msg(data.msg);
                }
            }, function (data) {
                layer.msg(data.msg);
            });
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})
