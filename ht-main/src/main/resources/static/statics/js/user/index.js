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

Vue.filter('date1', function (value, formatString) {
    formatString = formatString || 'YYYY年MM月DD日';
    return moment(value).format(formatString);
});

Vue.filter('date2', function (value, formatString) {
    formatString = formatString || 'MM月DD日';
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
            userId:'',
            username:'',
            code:'',
            userHoney:'',
            userGrade:'',
            contentDatas:[],
            todayDate:'',
            tomorrowDate:'',
            endTime:'',
            content:'',
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            userInfoType:'',
            targetData:'',
            headImg:'',
        }
    },
    created() {
        this.initPage();
    },
    methods:{
        initPage:function(){
            this.returnurl = encodeURIComponent(window.location.href);
            var date = new Date();
            var tomorrow = new Date(date.setHours(0, 0, 0, 0));
            this.tomorrowDate = Date.parse(tomorrow) + 86400000;

            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
              month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
              strDate = "0" + strDate;
            }
            var currentdate = year + "年" + month + "月" + strDate + "日";
            this.todayDate = currentdate;
            this.getUserInfo();
            this.getHoney();
            this.getpolicy();
            this.getMessages();
            this.getUserInformation();
            this.getUrls();
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
                    // console.log("用户已退出");
                    // layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //获取链接的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        getUrls:function(){
            var urls= this.getUrlData("urls");
            if(urls == "honeys"){
                window.location.href = "/ht-biz/usercenter/user/honey";
            }
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
        getMessages:function(index){
            var current = index;
            if(this.targetData != ""){
                current = this.targetData;
            }
            if(current == '' || current == undefined){
                current = 1;
            }
            htajax.get("/ht-biz/usercenter/msgList?current=" + current + "&size=" + 5 , function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.content = data.data.records;
                    box.page.currentPage = data.data.current;
                    box.page.totalPage = data.data.pages;
                    box.page.totalResult = data.data.total;
                }else{
                    //layer.msg(data.msg);
                }
                box.getGrade();
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //分页
        clickPage:function(index){
            if (index != 0) {
                box.getMessages(index);
            }
        },
        getHoney:function(){
            htajax.get("/ht-biz/honeymanager/getHoneyTotal", function (data) {
                if (data.code == 10000) {
                    box.userHoney = data.data;
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

        timestampToTime:function(timestamp) {
            var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
            var Y = date.getFullYear() + '年';
            var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '月';
            var D = date.getDate() + '日';
            var h = date.getHours() + ':';
            var m = date.getMinutes() + ':';
            var s = date.getSeconds();
            return Y + M + D;
        },
        
        getpolicy:function(){
            htajax.postForm('/ht-biz/policydig/centerlist',{
                size:4,
                apply:1,//申报通知
                nature:1,
            }, function (data) {
                if (data.code == 10000) {
                    //var datas = data.data;
                    // for (var i = 0; i < datas.length; i++) {
                    //     if (datas[i].beginDate != '' && datas[i].beginDate != null) {
                    //       datas[i].beginDate = box.timestampToTime(datas[i].beginDate);
                    //     }
                    //     if (datas[i].endDate != '' && datas[i].endDate != null) {
                    //       datas[i].endDate = box.timestampToTime(datas[i].endDate);
                    //     }
                    //     if (datas[i].datetime != '' && datas[i].datetime != null) {
                    //       datas[i].datetime = box.timestampToTime(datas[i].datetime);
                    //     } 
                    //     if (datas[i].beginDate == 'NaN年NaN月NaN日') {
                    //       datas[i].beginDate = '';
                    //     }
                    //     if (datas[i].endDate == 'NaN年NaN月NaN日') {
                    //       datas[i].endDate = '';
                    //     }
                    //     datas[i].date=box.dateDiff(datas[i].endDate);
                    // }
                    box.contentDatas = data.data;
                }
            }, function (data) {
                //错误回调
            });
        },
        deleteShowInfo:function(event){
            var targetId = event.toElement.id;
            htajax.getForm("/ht-biz/usercenter/deleted", {
                id: targetId,
            },
            function (data) {
                if (data.code == 10000) {
                    //layer.msg(data.data);
                }
                box.getMessages();
            },
            function (data) {
                //layer.msg(data.msg);
            });
        },
        
        dateDiff:function(eDate){
            if(eDate){
                if(eDate!=null || eDate!=''){
                    eDate=eDate.replace('年','-');
                    eDate=eDate.replace('月','-');
                    eDate=eDate.replace('日','');
                    //console.log(eDate);
                    var date2 = new Date();
                    var date1 = new Date(Date.parse(eDate.replace(/-/g,   "/")));
                    //console.log("log:"+date1);
                    var iDays = parseInt((date1.getTime()- date2.getTime()) /1000/60/60/24); 
                    if(iDays>0){
                        return iDays;    
                    }else{
                        return '';
                    }
               }
           }else{
               return '';
           }
        },
        //拒绝原因
        reject: function(reason) {
            if (reason == null){
                reason = '';
            }
            layer.alert(reason,{title: '拒绝原因'})
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})


