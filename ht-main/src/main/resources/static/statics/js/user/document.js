/**
 * Created by ACER on 2019/8/12.
 */
Vue.filter('hideMiddle', function (value) {
    var strval = value.toString();
    return value = `${strval.substring(0,3)}****${strval.substring(strval.length-4)}`
});
Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});

var box = new Vue({
    el:'#box',
    data() {
        return {
            allkeyword: '',
            bar:0,
            state:1,
            mobile:18216199930,
            userId:'',
            username:'',
            code:'',
            targetData:'',
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            CollectList:[],
            CollectTotal:'',
            downloadList:[],
            downloadTotal:'',
            uploadList:[],
            uploadTotal:'',
            uploadTotal1:'',
            uploadTotal2:'',
            uploadTotal3:'',
            deleteShow:0,
            collectId:'',
            userHoney:'',
            userGrade:'',
            userInfoType:'',
            headImg:'',
            //
            deleteRecord:0,
        }
    },
    created() {
        this.initPage();
    },
    methods:{
        initPage:function(){
            this.getUserInfo();
            this.getUserInformation();
            this.getUpload();
            this.getUpload1();
            this.getUpload2();
            this.getUpload3();
            this.getdownload();
            this.getCollect();
            this.getHoney();
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
                    box.code = data.code;
                    box.userId = data.data.userId;
                    box.username = data.data.username;
                    box.code = data.code;
                    box.headImg = data.data.headImg;
                }else{
                    // console.log("用户已退出");
                    //layer.msg(data.msg);
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
        getContent:function(){},
        getSubscribList:function(){},
        currentPage:function(){},
        //文档收藏
        getCollect:function(index){
            var current = index;
            if(this.targetData != ""){
                current = this.targetData;
            }
            if(current == 0){
                current = 1;
            }
            htajax.postForm("/ht-biz/myinfo/findcolletionbyUserId",{
                current: current,   
                type:0, 
            },function(data){
                if(data.code == 10000){
                    // console.log(data);
                    box.CollectList = data.data;
                    box.CollectTotal = data.reserveData.total;
                    box.page.currentPage = data.reserveData.current;
                    box.page.totalPage = data.reserveData.pages;
                    box.page.totalResult = data.reserveData.total;
                }else{
                    //
                }
                this.targetData = '';
            },function(data){
                //error
            })
           
        },
        //下载
        getdownload:function(index){
            var current = index;
            if(this.targetData != ""){
                current = this.targetData;
            }
            if(current == 0){
                current = 1;
            }
            htajax.postForm("/ht-biz/myinfo/findcolletionbyUserId",{
                current: current,     
                type:1, 
            },function(data){
                if(data.code == 10000){
                    box.downloadList = data.data;
                    box.downloadTotal = data.reserveData.total;
                    box.page.currentPage = data.reserveData.current;
                    box.page.totalPage = data.reserveData.pages;
                    box.page.totalResult = data.reserveData.total;
                }else{
                    //
                }
                this.targetData = '';
            },function(data){
                //error
            })
           
        },
        //上传
        getUpload:function(index){
            var current = index;
            if(this.targetData != ""){
                current = this.targetData;
            }
            if(current == 0){
                current = 1;
            }
            var state = this.state;
            htajax.postForm("/ht-biz/myinfo/myload",{
                current: current,         
                status:state,
            },function(data){
                if(data.code == 10000){
                    // console.log(data);
                    box.uploadList = data.data;
                    box.uploadTotal = data.reserveData.total;
                    box.page.currentPage = data.reserveData.current;
                    box.page.totalPage = data.reserveData.pages;
                    box.page.totalResult = data.reserveData.total;
                }else{
                    //
                }
                this.targetData = '';
            },function(data){
                //error
            })
        },
        getUpload1:function(index){
            var current = index;
            if(this.targetData != ""){
                current = this.targetData;
            }
            if(current == 0){
                current = 1;
            }
            htajax.postForm("/ht-biz/myinfo/myload",{
                current: current,         
                status:1,
            },function(data){
                if(data.code == 10000){
                    // console.log(data);
                    box.uploadList = data.data;
                    box.uploadTotal1 = data.reserveData.total;
                    box.page.currentPage = data.reserveData.current;
                    box.page.totalPage = data.reserveData.pages;
                    box.page.totalResult = data.reserveData.total;
                }else{
                    //
                }
                this.targetData = '';
            },function(data){
                //error
            })
        },
        getUpload2:function(index){
            var current = index;
            if(this.targetData != ""){
                current = this.targetData;
            }
            if(current == 0){
                current = 1;
            }
            htajax.postForm("/ht-biz/myinfo/myload?t=" + new Date().getTime(),{
                current: current,         
                status:0,
            },function(data){
                if(data.code == 10000){
                    // console.log(data);
                    box.uploadList = data.data;
                    box.uploadTotal2 = data.reserveData.total;
                    box.page.currentPage = data.reserveData.current;
                    box.page.totalPage = data.reserveData.pages;
                    box.page.totalResult = data.reserveData.total;
                }else{
                    //
                }
                this.targetData = '';
            },function(data){
                //error
            })
        },
        getUpload3:function(index){
            var current = index;
            if(this.targetData != ""){
                current = this.targetData;
            }
            if(current == 0){
                current = 1;
            }
            htajax.postForm("/ht-biz/myinfo/myload",{
                current: current,         
                status:-1,
            },function(data){
                if(data.code == 10000){
                    // console.log(data);
                    box.uploadList = data.data;
                    box.uploadTotal3 = data.reserveData.total;
                    box.page.currentPage = data.reserveData.current;
                    box.page.totalPage = data.reserveData.pages;
                    box.page.totalResult = data.reserveData.total;
                }else{
                    //
                }
                this.targetData = '';
            },function(data){
                //error
            })
        },

        //分页
        clickPage:function(index){
            if (index != 0) {
                if(this.bar == 0){
                    box.getCollect(index);
                }
                if(this.bar == 1){
                    box.getdownload(index);
                }
                if(this.bar == 2){
                    box.getUpload(index);
                }
            }
        },
        getHoney:function(){
            htajax.get("/ht-biz/honeymanager/getHoneyTotal", function (data) {
                if (data.code == 10000) {
                    // 用户的honey值
                    //console.log(data);
                    box.userHoney = data.data;
                    //console.log(box.userHoney);
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

        //收藏事件
        doCollection: function (event) {
            // console.log(event);
            // console.log(event.toElement.id);
            var targetId = event.toElement.id;
            htajax.postForm("/ht-biz/myinfo/SaveColletion", {
                    targetId: targetId,
                    clazzName: "com.fh.entity.system.Library"
                },
                function (data) {
                    if (data.code == 10000) {
                        //layer.msg(data.data);
                    }
                    box.getCollect();
                },
                function (data) {
                    //需要先登录
                    //layer.msg(data.msg);
                });
        },
        reject(reason) {
            layer.alert(reason,{title: '拒绝原因'})
        },
        //删除记录
        doDelete:function(event){
            var targetId = event.toElement.id;
            htajax.postForm("/ht-biz/library/deleteById", {
                id: targetId,
            },
            function (data) {
                if (data.code == 10000) {
                    //layer.msg(data.data);
                }
                box.getUpload();
            },
            function (data) {
                //需要先登录
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
