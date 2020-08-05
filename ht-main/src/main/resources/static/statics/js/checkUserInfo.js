/**
 * Created by oll on 2017/2/22.
 */
var user = new Vue({
    el: '#user',
    data: {
    	returnurl: '',  //点击登录、注册获取该页面url
    	
        userInfo:{},
        code: 2,
        loginname:'',

        orderMessageNum:0,//订单状态改变,0不显示

        isSearchShow: false,  //显示搜索框

        msgList:[], //个人消息提醒
        msgCount:0,  //消息数目

        showMsgList:true,  //消息数目为0时不显示

        honey:0,

        speechSpace:"",  //地区
        aa:""
    },
    created: function () {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.doPage();
            //this.getCity();
            //this.getOrderChangeNum();
            
            this.returnurl = encodeURIComponent(window.location.href);
            
        },


        //获得订单状态改变的数量
        // getOrderChangeNum:function(){
        //     htajax.get("/beetl/neworder/notRordermun", function (data) {
        //         if (data.code == 0) {
        //             user.orderMessageNum = data.data.number;
        //         }
        //     }, function (data) {

        //     });
        // },
        // deOrderChangeNum:function(){
        //     htajax.get("/beetl/neworder/deletenotRordermun", function (data) {
        //         if (data.code == 0&&data.msg=='') {
        //             user.orderMessageNum = 0;
        //         }
        //     }, function (data) {

        //     });
        // },

        // searchShow: function () {
        //     if(user.isSearchShow==false){
        //         user.isSearchShow = true;
        //     }else{
        //         var searchWord=$("#searchWord").val();
        //         window.open("/beetl/solr/allSearch_result.html?searchWord="+searchWord);
        //     }
        // },
        // EnterSearch:function(){
        //     if(event.keyCode == 13) {
        //         this.searchShow();
        //     }
        // },
        // doSearch:function(){
        //     var searchWord=$("#searchWord").val();
        //     window.open("/beetl/solr/allSearch_result.html?searchWord="+searchWord);
        // },
        doPage: function () {
            htajax.get("/beetl/login/getUser.do?t="+new Date().getTime(), function (data) {
                if (data.code == 0) {
                	//if(data.data.displayName!=null && $.trim(data.data.displayName)!=''){
                	//	user.loginname=data.data.displayName;
                	//}else if(data.data.trueName!=null && $.trim(data.data.trueName)!=''){
                	//	user.loginname=data.data.trueName;
                	//}else if(data.data.nikeName!=null && $.trim(data.data.nikeName)!=''){
                	//	user.loginname=data.data.nikeName;
                	//}else if(data.data.username!=null && $.trim(data.data.username)!=''){
                	//	user.loginname=data.data.username;
                	//}
                    if(data.data.nikeName!=null && $.trim(data.data.nikeName)!=''){
                        user.loginname=data.data.nikeName;
                    }else if(data.data.username!=null && $.trim(data.data.username)!=''){
                        user.loginname=data.data.username;
                    }
                    if(data.data.compRegCode){
                        user.loginname = data.data.companyName;
                    }

                	user.userInfo = data.data;
                }
                user.code = data.code;
                if(user.code==0){
                    user.getMsgCall();
                    user.getHoney();
                    
                }

            }, function (data) {
                  layer.msg(data.msg);
            });
        },

        // getHoney:function(){
        //     htajax.get("/pointrdp/getHoneyTotal?t="+new Date().getTime(), function (data) {
        //         if (data.code == 0) {
        //             user.honey = data.data;

        //         }
        //     }, function (data) {
        //         layer.msg(data.msg);
        //     });
        // },
        del: function() { // 退出系统
        	htajax.get("/beetl/login/logout.do?t="+new Date().getTime(), function (data) {
                if (data.code == 0) {
                    location.reload();
                }
                user.code = data.code;
            }, function (data) {
                  layer.msg(data.msg);
            });
        },

        // doLogin:function(url){
        //     if ( user.code == 0) {
        //         //已登录]
        //         if (url == '/beetl/declaretool/tool.html') {
        //             if(user.userInfo.isExpert == 1){
        //                 layer.msg("只有服务商才能使用该功能");
        //             }else if (user.userInfo.isServicer == 1) {
        //                 location.href = url;
        //             } else {
        //                 layer.confirm('该功能需注册成为服务商才能使用，是否要注册成为服务商？', {
        //                     btn: ['确定', '取消'], //按钮
        //                     shade: false //不显示遮罩
        //                 }, function (index) {
        //                     location.href = '/beetl/spage/server_register.html';

        //                 }, function (index) {
        //                     layer.close(index);
        //                 });
        //             }
        //         } else {
        //             location.href = url;
        //         }
        //     } else {
        //         //需要先登录
        //         window.location.href = "/beetl/login/toLogin.html";
        //     }
        // },

        // getMsgCall:function(){
        //     htajax.get("/beetl/mymsg/navlist.do?showCount=1000", function (data) {
        //         if (data.code == 0) {
        //            user.msgList=data.data;
        //             user.msgCount = data.reserveData.totalResult;
        //             if(user.msgCount==0){
        //                 user.showMsgList=false;
        //             }else{
        //                 user.showMsgList=true;
        //             }
        //         }
        //     }, function (data) {
        //         layer.msg(data.msg);
        //     });
        // },

        // getCity:function(){
        //     htajax.get("/bl/city/selectUserRegion.do", function (data) {
        //         if (data.code == 0) {
        //             user.speechSpace=data.data;
        //         }
        //     }, function (data) {
        //         layer.msg(data.msg);
        //     });
        // },

        // //社区搜索
        // comSearch:function(){
        //     var keyword=$("#comKeyWord").val();
        //     window.open("/bl/c/search_result.html?keyword="+keyword);
        // },
    }
});
Vue.filter('time', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD HH:mm';
    return moment(value).format(formatString);
});