/**
 * Created by dsy on 2017/3/20.
 */
Vue.filter('moment', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});
var box2 = new Vue({
    el: '#box2',
    data: {
        // 导航
        isEstimateNav: 1,
        //线上/线下
        isOnline: true,
        isSure: false,
        list: {},
        szpdfdown: 0,
        areas:'',
        citys: '',
        provinces: '',

        userId:'',
        userName:'',
        code:'',
        honey:'',
        requiredHoney:'',

        // page:{
        //      currentPage:1,
        //      totalPage:10,
        //      totalResult:48,
        //      preCount:0,
        //      showCount: 5
        // },
        //项目初步匹配
        objectLists: [],
        yourHoney: 0,
        //相关文档阅读
        readDataLists: [],
        moreList:[],
        deriveInfo:false,        
        deriveInfo1:false,
        resultPDF:false,
    },
    created: function () {
        this.initPage();
        this.showResult();
        this.getUserInfo();
    },
    methods: {
        initPage: function () {
            this.getResult();
            //企业匹配
            this.getObject();
            
            this.doPlay();
            //相关文档阅读
            this.getMoreList();
        },
        //获取链接的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        getResult:function(){
            this.provinces = this.getUrlData("province");
            this.citys = this.getUrlData("city");
            this.areas = this.getUrlData("area");
            layer.load(2, {shade: false}); //0代表加载的风格，支持0-2
            var url = '/ht-biz/hisself/id?id=' + this.getUrlData('id');
            htajax.get(url, function (data) {
                    if (data.code == 10000) {
                        layer.closeAll("loading");
                        var datas = data.data;
                        if (datas.productServ != '' && datas.productServ != null) {
                            datas.productServ = datas.productServ.substring(0, datas.productServ.length - 2);
                        }
                        box2.list = datas;
                    }
                },
                function () {
                    //错误回调
                });
        },
        //除金华市 淮安市 苏州市都没有 现状对比和项目匹配
        showResult: function () {
            var thiscity = decodeURI(window.location.href);
            //console.log(thiscity);
            if (thiscity.indexOf("金华市") >= 0) {
                $(".xzdb").show();
                $(".xmpp").show();
            } else if (thiscity.indexOf("淮安市") >= 0) {
                $(".xzdb").show();
                $(".xmpp").show();
            } else if (thiscity.indexOf("苏州市") >= 0) {
                $(".xzdb").show();
                $(".xmpp").show();
            } else if (thiscity.indexOf("广州市") >= 0) {
                $(".xzdb").show();
                $(".xmpp").show();
            } else {
                $(".xzdb").hide();
                $(".xmpp").hide();
                $("#table1").hide();
                $("#table2").hide();
            }
        },
        doPlay:function(){
            var thiscity= this.getUrlData("city");
            if(thiscity == '广州市' || thiscity == '金华市' || thiscity == '淮安市' || thiscity == '苏州市'){
                $(".gz_play").hide();
                $(".other_play").show();
            }else{
                $(".gz_play").hide();
                $(".other_play").hide();
                $(".jhsbjy").hide();
            }
        },
        // 获取用户的信息
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser", function (data) {
                if (data.code == 10000) {
                    box2.userId = data.data.userId;
                    box2.username = data.data.username;
                    box2.code = data.code;
                    box2.getHoneyTotal();
                }else{
                    // console.log("用户已退出");
                }
            }, function (data) {
                layer.msg(data.msg);
            });
        },
        getHoneyTotal:function(){
             htajax.get("/ht-biz/honeymanager/getHoneyTotal", function (data) {
                if (data.code == 10000) {
                    box2.yourHoney = data.data;
                    if(box2.yourHoney  < 0){
                        box2.yourHoney = 0;
                    }
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        // 返回上一层
        goback: function () {
            window.location.replace(document.referrer);
        },
        
        //政策匹配
        getObject: function () {
            var thiscity = decodeURI(window.location.href);
            var that = this;
            if (thiscity.indexOf("金华市") >= 0) {
                htajax.get("/statics/js/hisself/jhzcpp.json", function (data) {
                    that.objectLists = data;
                }, function (data) {
                    //错误回调
                });
            } 
            else if (thiscity.indexOf("淮安市") >= 0) {
                htajax.get("/statics/js/hisself/hazcpp.json", function (data) {

                    that.objectLists = data;

                }, function (data) {
                    //错误回调
                });
            } 
            else if(thiscity.indexOf("广州市") >= 0){
                $(".project_match>table").hide();
                $(".project_match>.mask_wrap").hide();
                $(".project_match>.download").hide();
                $(".project_match>.ac").text("请点击导出项目匹配下载");
            }
            else{
                $(".project_match>table").hide();
                $(".project_match>.mask_wrap").hide();
                $(".project_match>.download").hide();
                $(".project_match>.ac").text("暂无符合条件的项目初步匹配表");
            }

        },
        //分页
        // clickPage:function(index){
        //     box2.page.currentPage = index;
        //     box2.getObject();
        // },

        //导出项目匹配判断
    exportResult: function () {
        this.deriveInfo1 = true;
        box2.getHoneyTotal();
        
    },
    //导出项目匹配
    XMPPDerive:function(){
        var province = this.getUrlData("province");
        var city = this.getUrlData("city");
        var area = this.getUrlData("area");
        if (box2.yourHoney >= 100) {
            htajax.get("/statics/js/mate.json", function (data) {
                
                var citydata = data;
                var alldata = data[3].data;
                for (var i = 0; i < citydata.length; i++) {
                    //console.log(data[i].name);
                    for (var j = 0; j < alldata.length; j++) {
                        //console.log(data[3].data[j].name);
                        //判断除广州之外的其他市
                        if (data[i].name == city && data[i].name != '广州市') {
                            //console.log(data[i].url);
                            location.href = data[i].url;
                        }
                        //判断广州市里面的区
                        else if (data[i].name == '广州市' && data[3].data[j].name == area) {
                            //console.log(data[3].data[j].url);
                            location.href = data[3].data[j].url;
                        }
                        //只为广州市的选项
                        else if (data[i].name == '广州市' && data[3].data[j].name == '') {
                            //console.log(data[i].url);
                            location.href = data[i].url;
                        }

                    }
                }
            }, function (data) {
                //错误回调
            });
            this.deriveInfo1 = false
        } else {
            layer.msg('您的honey值不足，请前往个人云中心充值');
        }
        
        
    },

    //导出现状对比判断
    exportXZDBResult: function () {
        box2.getHoneyTotal();
        this.deriveInfo = true;
    },
    //导出现状对比
    XZDBDerive:function(){
        var id = this.getUrlData("id");
        if (box2.yourHoney >= 50) {
            if (id != '') {
                location.href = "/ht-biz/hisself/exportexcel?testingId=" + id;
                // var timer = setTimeout(function () { this.deriveInfo = false; }, 1500)
                this.deriveInfo = false;
            }
        } else {
            layer.msg('您的honey值不足，请前往个人云中心充值');
        }
        
    },

    
    //下载pdf判断
    PDFResult:function(){
        this.resultPDF = true;
        box2.getHoneyTotal();
    },
    downloadPDF: function () {
        var id = this.getUrlData("id");
        if (box2.yourHoney >= 50) {
            if (id != '') {
                location.href = "/ht-biz/hisself/exportpdf?testingid=" + id;
                // var timer = setTimeout(function () { this.resultPDF = false; }, 1500)
                this.resultPDF = false;
            }
        } else {
            layer.msg('您的honey值不足，请前往个人云中心充值');
        }
        
    },
    //推荐阅读
    getMoreList: function () {
        var province0 = this.getUrlData("province");
        var city0 = this.getUrlData("city");
        var nature = this.nature;
        if(province0 == undefined){province0 = ''; }
        if(city0 == undefined){city0 = ''; }
        if(nature == undefined || nature == null){nature = '';}
        htajax.postForm("/ht-biz/policydig/list",{
            province:province0,
            city:city0,
            size:5
        }, function (data) {
            if (data.code == 10000) {
                box2.moreList = data.data;
            }
        }, function (data) {
            //错误回调
        });
    },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
}

});

