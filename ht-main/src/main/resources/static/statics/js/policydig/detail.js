/**
 * Created by ACER on 2019/6/3.
 */
Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});

var vbox = new Vue({
    el: '#vbox',
    data: {
        keyword:'',
        policyData: {},
        relatePolicy: [],   //相关政策解读
        moreList: [],  //更多政策速递
        moreListBtn:[],//底部
        recLibrary: [],   //高企云文库推荐
        bidDataWord:"",  //政策大数据
        policyfmtWord:"",  //政策立项
        userId:'',
        username:'',
        code:'',
        showList:5,
        //收藏
        haveCollection:true,

        bigData:'',
        setProject:'',
        province:'',
        city:'',
        area:'',
        title:'',
        nature:'',
        isCollection: false, //收藏按钮,默认为false--》未收藏
    },
    created: function () {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getPolicyData();
            
            this.getUserInfo();
            this.getCollection();
           
        },
        getContent:function(){
            location.href = "/ht-biz/policydig/index?keyword=" + vbox.keyword;
        },
        // 获取用户的信息
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser", function (data) {
                if (data.code == 10000) {
                    // 用户的honey值
                    vbox.userId = data.data.userId;
                    vbox.username = data.data.username;
                    vbox.code = data.code;
                }else{
                    //console.log("用户已退出");
                }

            }, function (data) {
                layer.msg(data.msg);
            });
        },
        //搜索
        searchBigData:function(){
            if(vm.code == 10000){
                window.open("/ht-biz/inquire/policy_details?keyword1="+this.bigData + "&theme=" + 1);
            }else{
                window.open("/ht-biz/login/login");
            }
           
        },
        searchProject:function(){
            if(vm.code == 10000){
                window.open("/ht-biz/inquire/project_details?keyword2="+this.setProject + "&theme=" + 2);
            }else{
                window.open("/ht-biz/login/login");
            }
            
        },
        getPolicyData: function () {
            htajax.get("/ht-biz/policydig/findById?id="+this.getUrlData("id"), function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    vbox.policyData = data.data;
                    vbox.province = data.data.province;
                    vbox.city = data.data.city;
                    vbox.area = data.data.area;
                    vbox.title = data.data.title;
                    vbox.nature = data.data.nature;
                    var region="";
                    if(vbox.policyData.area!=null&&vbox.policyData.area!=""&&vbox.policyData.area!=undefined){
                        region=vbox.policyData.area;
                    }else if(vbox.policyData.city!=null&&vbox.policyData.city!=""&&vbox.policyData.city!=undefined){
                        region=vbox.policyData.city;
                    }else if(vbox.policyData.province!=null&&vbox.policyData.province!=""&&vbox.policyData.province!=undefined){
                        region=vbox.policyData.province;
                    }
                    //vbox.getRelatePolicy(region);
                    //vbox.getRecLibrary(region);
                    vbox.getMoreList();
                    vbox.getMoreListBtn();
                    
                }
            }, function (data) {
                //错误回调
            });
        },
        //推荐阅读
        getMoreList: function () {
            var province0 = this.province;
            var city0 = this.city;
            var nature = this.nature;
            if(province0 == undefined){province0 = ''; }
            if(city0 == undefined){city0 = ''; }
            if(nature == undefined || nature == null){nature = '';}
            htajax.getForm("/ht-biz/policydig/findlist",{
                province:province0,
                city:city0,
                nature:nature,
                size:10
            }, function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    // console.log(data);
                    vbox.moreList = data.data.records;
                }
            }, function (data) {
                //错误回调
            });
        },



        //相关政策解读
        getMoreListBtn: function () {
            var tit = this.title;
            // console.log(tit);
            htajax.get("/ht-biz/library/promotelibrary?size=" + 10 + '&title=' + tit, 
            function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    vbox.moreListBtn = data.data;
                }
            }, function (data) {
                //错误回调
            });
        },
       
        //系统搜索
        sysSearch:function(type){
            if(type==0){
                window.open("/ht-biz/policydig/list?keyword="+vbox.bidDataWord);
            }else if(type==1){
                window.open("/ht-biz/policydig/list?keyword="+vbox.policyfmtWord);
            }
        },

        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)')
                .exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },

        //判断收藏
        getCollection: function () {
            // var targetId = $(".getId").val();
            var targetId = this.getUrlData('id');
            htajax.postForm("/ht-biz/myinfo/iscollection", {
                targetId: targetId,
            },
                function (data) {
                    // console.log(data);
                    if (data.data == "未收藏") {
                        vbox.haveCollection = true;
                    } else if (data.data == "已收藏") {
                        vbox.haveCollection = false;
                    }
                },
                function (data) {
                    //
                });
        },
        //收藏事件
        doCollection: function () {
            if (vm.code == 10000) {
                // var targetId = $(".getId").val();
                var targetId = this.getUrlData('id');
                htajax.postForm("/ht-biz/myinfo/SaveColletion", {
                    targetId: targetId,
                    clazzName: "com.ht.entity.policydig.PolicyDig"
                },
                    function (data) {
                        if (data.data == "收藏成功") {
                            // console.log(data);
                            // vbox.haveCollection = true;
                            vbox.haveCollection = false;
                            layer.msg("收藏成功，可在个人云中心-我的文档里查看");
                        } else if (data.data == "取消成功") {
                            // console.log(data);
                            vbox.haveCollection = true;
                            layer.msg("取消收藏成功");
                        }
                    },
                    function (data) {
                        //layer.msg(data.msg);
                    });
            } else {
                // 先登录
                $(".loginBox").show();
            }
        },
        //收藏事件
        // doCollection: function () {
        //     if (this.code == 10000) {  
        //         var targetId = this.getUrlData('id');
        //         htajax.postForm("/ht-biz/myinfo/SaveColletion", {
        //                 targetId: targetId,
        //                 clazzName: "com.ht.entity.policydig.PolicyDig"
        //             },
        //             function (data) {
        //                 if (data.code == 10000) {
        //                     vbox.haveCollection = false;
        //                     // vbox.loseCollection = false;
        //                     layer.msg("收藏成功，可在个人云中心-我的文档里查看");
                            
        //                 }
        //             },
        //             function (data) {
        //                 //需要先登录
        //                 layer.msg(data.msg);
        //             });
        //     } else {
        //         // 先登录
        //         $(".loginBox").show();
        //     }
        // },
        policyConsult: function () {
            layer.msg("敬请期待");
        },
        projectDeclare: function () {
            layer.msg("敬请期待");
        }
    }
});

