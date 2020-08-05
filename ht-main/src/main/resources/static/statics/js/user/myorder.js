/**
 * Created by ACER on 2019/8/12.
 */
Vue.filter('hideMiddle', function (value) {
    let strval = value.toString();
    return value = `${strval.substring(0, 3)}****${strval.substring(strval.length - 4)}`
});
Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});
Vue.filter('date1', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD HH:mm:ss';
    return moment(value).format(formatString);
});

var box = new Vue({
    el: '#box',
    data() {
        return {
            allkeyword: '',
            userId: '',
            username: '',
            code: '',
            userHoney: '',
            userGrade: '',
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            userInfoType: '',
            headImg: '',
            honeyInfo1: false,
            honeyInfo2: false,
            honeyInfo3: false,
            orderList:[],
            refuse:'',
            offid:'',
            offproductName:'',
            auditframe: false,
            auditframe1: false,
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getUserInfo();
            this.getHoney();
            this.getUserInformation();
            this.getOrder(1);
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
                } else {
                    // console.log("用户已退出");
                    // layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getUserInformation: function () {
            htajax.postForm("/ht-shiro/sysuser/myinfo", {}, function (data) {
                if (data.code == 10000) {
                    box.userInfoType = data.data.userType;
                } else {
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //分页
        clickPage: function (index) {
            if (index != 0) {
                box.getOrder(index);
            }
        },
        getHoney: function () {
            htajax.get("/ht-biz/honeymanager/getHoneyTotal", function (data) {
                if (data.code == 10000) {
                    box.userHoney = data.data;
                } else {
                    //layer.msg(data.msg);
                }
                box.getGrade();
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getGrade: function () {
            htajax.get("/ht-biz/honeymanager/getUserLevel?type=" + 1, function (data) {
                if (data.code == 10000) {
                    box.userGrade = data.data.sort;
                } else {
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getOrder:function(index){
            var current = index;
            var serviceId = '';
            if (current == 0) {current = 1;}
            htajax.postForm("/ht-biz/myorder/list",{
                serviceId: serviceId,
                size:3,
                current: current,
            }, function (data) {
                if (data.code == 10000) {
                    //  console.log(data);
                    box.orderList = data.data.records;
                    box.page.currentPage = data.data.current;
                    box.page.totalPage = data.data.pages;
                    box.page.totalResult = data.data.total;
                } else {
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //删除
        deleteInfo:function(id){
            htajax.get("/ht-biz/myorder/deleted?id=" + id, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    // layer.msg("删除成功");
                    box.auditframe = true;
                    var timer = setTimeout(function () { box.auditframe = false; }, 1500);
                    box.getOrder(1);
                } 
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //原因
        reason: function (refuse){
            this.honeyInfo1 = true;
            this.refuse = refuse;
        },
        //取消
        callOff:function(id,name){
            
            this.offid = id;
            this.offproductName = name;
            this.honeyInfo2 = true;
        },
        //删除
        callOff1: function (id, name) {

            this.offid = id;
            this.offproductName = name;
            this.honeyInfo3 = true;
        },
        //取消 删除订单
        getCancel:function(index){
            if (index == 1) { var serviceDeleted = 1; }//取消
            if (index == 2) { var serviceDeleted = '';}//删除
            var id = this.offid;
            htajax.postForm("/ht-biz/myorder/save",{
                id:id,
                userDeleted:1,
                serviceDeleted: serviceDeleted,
            }, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    if(index==1){
                        box.honeyInfo2 = false;
                        box.auditframe1 = true;
                        var timer = setTimeout(function () { box.auditframe1 = false; location.reload(); }, 1500);
                    }
                    if(index == 2){
                        box.honeyInfo3 = false;
                        box.auditframe = true;
                        var timer = setTimeout(function () { box.auditframe = false; location.reload(); }, 1500);
                    }
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


