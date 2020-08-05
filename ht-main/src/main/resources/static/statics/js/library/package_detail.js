/**
 * Created by oll on 2019/6/7.
 */

Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});
Vue.filter('date1', function (value, formatString) {
    formatString = formatString || 'YYYY年MM月DD日';
    return moment(value).format(formatString);
});
var box = new Vue({
    el: '#box',
    data: {
        url: '',
        logininformation: 2, //用户判断全局是否有用户登录
        createId: '',
        title: '',
        doDownloadtxt: '',
        //面包屑
        pathData: '',
        //hiddenkeywords:'',
        //文档data
        docDatas: '',
        //换一批
        changeList: [],
        //分页
        cur: 0,
        totalPage: 0,
        pages:'',
        //文档评分
        starScore: 0, //提交到后台的数据
        chooseSore: 0,
        //点击发布评论判断是否登录(控制弹出登录窗口)
        isLogin: true,
        //评论内容
        discussContent: '',
        //错误提醒
        errorMsg: '',
        keywords:'',
        //评论
        discussList: [],
        tempDisLst: [], //临时变量
        totalResult: 0,
        replyList: [],
        totalReply: 0,
        isShowTextarea: false,
        replyName: '',
        showDialog: true,
        bidDataWord: "",
        policyfmtWord: "", //右边数据查询初始值
        isHave: false,
        isDownload: false, //点击下载弹窗
        userInfoHoney: 0,
        isCollection: false, //收藏按钮,默认为false--》未收藏
        code: 0,
        trueName: "",
        beforeReplyId: "",
        pdfsize: "", //文档大小
        recName: "",
        indownloaded:false,
        richtext:false,
        userId:'',
        isLook:false,
        msg:'',
        delibraryList:[],

        requiredHoney:'',
        honey:'',
        key:'',
    },
    created: function () {
        this.initPage();
    },
    updated:function(){
        this.getCollection();
    },
    computed: {
        indexs: function () {
            var left = 1;
            var right = this.pages;
            var ar = [];
            if (this.pages >= 11) {
                if (this.cur > 5 && this.cur < this.pages - 4) {
                    left = this.cur - 5;
                    right = this.cur + 4
                } else {
                    if (this.cur <= 5) {
                        left = 1;
                        right = 10;
                    } else {
                        right = this.pages;
                        left = this.pages - 9
                    }
                }
            }
            while (left <= right) {
                ar.push(left);
                left++
            }
            if (ar[0] > 1) {
                ar[0] = 1;
                ar[1] = -1;
            }
            if (ar[ar.length - 1] < this.pages) {
                ar[ar.length - 1] = this.pages;
                ar[ar.length - 2] = 0;
            }
            return ar;
        }
    },
    methods: {
        initPage: function () {
            this.getDelibraryList();
            this.getUserInfo();
            this.getDiscuss();
            this.boxFixed();
            window.addEventListener("scroll", this.boxFixed);
        },
        skipTo: function () {
            //判断登录
            if (vm.code == 10000) {
                location.href = '/ht-biz/library/upload'
            } else {
                //需要先登录
                $(".loginBox").show();
            }
        },
        getRegion: function () {
            location.href = "/ht-biz/library/index?keyword="+box.keywords +'&curPolicy=' + 8;
        },
        // getRegion: function () {
        //     var province0 = this.province;
        //     var city0 = this.city;
        //     var area0 = this.area;
        //     if (this.isRegion == 0) {
        //         province0 = '';
        //         city0='';
        //         area0='';
        //     }else if(this.isRegion == 1){
        //         province0 = this.province;
        //         city0 = this.city;
        //         area0 = this.area;
        //     }
        //     var url = "/ht-biz/library/findlibraryall?keywords=" + this.keywords + "&province=" + province0 + "&city=" + city0 + "&area=" + area0 + "&libtype=" + this.tabProjectType + "&type=" + this.tabPolicyType;
        //     htajax.get(encodeURI(url), function (data) {
        //         if (data.msg.indexOf('success') >= 0) {
        //             //console.log(data)
        //             box.contentDatas = data.data;
        //         }
        //     }, function (data) {
        //         layer.msg(data.msg);
        //     });
        // },
        // 获取用户的信息
        
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser", function (data) {
                //"用户已退出"
                //console.log(data);
                if (data.code == 10000) {
                    // 用户的honey值
                    box.userId = data.data.userId;
                    box.username = data.data.username;
                    box.code = data.code;
                }else{
                    //console.log("用户已退出");
                }
                //box.code = data.code;
            }, function (data) {
                layer.msg(data.msg);
            });
        },
        
        //判断收藏
        getCollection:function(){
            var targetId = $(".getId").val();
            htajax.postForm("/ht-biz/myinfo/iscollection", {
                targetId: targetId,
                userId: vm.userId,
            },
            function (data) {
                if (data.data == "未收藏") {
                    box.isCollection = false;
                }else if(data.data == "已收藏"){
                    box.isCollection = true;
                }
            },
            function (data) {
                //
            });
        },
        //收藏事件
        doCollection: function () {
            if (vm.code == 10000) {  
                var targetId = $(".getId").val();
                htajax.postForm("/ht-biz/myinfo/SaveColletion", {
                        targetId: targetId,
                        clazzName: "com.ht.entity.biz.library.ResourcePage"
                    },
                    function (data) {
                        if (data.data == "收藏成功") {
                            box.isCollection = true;
                            layer.msg("收藏成功，可在个人云中心-我的文档里查看");
                        }else if(data.data == "取消成功"){
                            box.isCollection = false;
                            layer.msg("取消收藏成功");
                        }
                    },
                    function (data) {
                        //需要先登录
                        //layer.msg(data.msg);
                    });
            } else {
                // 先登录
                $(".loginBox").show();
            }
        },
        //固定栏
        boxFixed:function(){
            if (!!document.documentElement.scrollTop && document.documentElement.scrollTop > 200
               ) {            
                    //页面高度大于200执行操作
                    $(".resource_packs").css({
                        "position":"fixed",
                        "top":"0",
                    })
                    $(".pdf_tit").css({
                        "position":"fixed",
                        "top":"0",
                    })
                } else {
                    //页面高度小于200执行操作
                    $(".resource_packs").css({
                        "position":"relative",
                    })
                    $(".pdf_tit").css({
                        "position":"relative"
                    })
                }
        },
        getUserHoney:function(){
            var id = $(".getId").val();
            htajax.postForm('/ht-biz/resouce/getdownfilehoney',{
                id:id,
            },function (data) {
                if (data.code == 10000) {
                    box.honey = data.data[0];
                    box.honey = parseInt(box.honey);
                    if(data.data[1]==0){
                        box.requiredHoney="免费"
                    }else{
                        box.requiredHoney =data.data[1];
                    }
                } 
            },function(data){
                // console.log("错误");
            })
        },
        // 关闭弹框事件
        closePopup: function () {
            this.isDownload = false;
            var id = $("#id").val();
        },
        //点击下载
        doDownload: function () {
            //判断登录
            if (vm.code == 10000) {
                this.isDownload = true;
                    this.getUserHoney()
            } else {
                //需要先登录
                $(".loginBox").show();
            }
        },

        //使用honey值下载
        useHoneyDownload: function () {
           

            var id = $(".getId").val();
            if(box.requiredHoney == '免费'){
                box.requiredHoney = 0 ;
            }
            if (box.honey == box.requiredHoney) {
                location.href = "/ht-biz/resouce/downfile?id=" + id;
                this.isDownload = false;
            }else if (box.honey < box.requiredHoney) {
                //余额不足
                layer.msg("您的honey值不足，请前往个人云中心充值");
            } else {
                location.href = "/ht-biz/resouce/downfile?id=" + id;
                this.isDownload = false;
            }
        },

        //获取用户评论
        getDiscuss: function (pageNumber) {
            var that = this;
            var id = $(".getId").val();
            htajax.getForm('/ht-biz/evaluate/findEvaluatByTargetId', {
                targetId: id,
                showCount: 10,
                current: pageNumber,
            }, function (data) {
                if (data.code == 10000) {
                    that.discussList = data.data;
                    //that.headImg = data.data.headImg;
                    that.totalPage = data.reserveData.total;
                    that.pages = data.reserveData.pages;
                    that.cur = data.reserveData.current;
                    that.totalResult = data.reserveData.searchCount;
                    //console.log(that.headImg);
                }
                if (that.pages == 0) {
                    that.isHave = false;
                } else {
                    that.isHave = true;
                }
            }, function (data) {
                //错误回调
            });
        },
        //点击发布评论
        postMsg: function () {
            if (vm.code == 10000) {
                //已登录
                //发送数据
                if (this.discussContent.trim() == '') {
                    layer.msg('评论内容不能为空');
                    return;
                }
                var id = $(".getId").val();
                htajax.postForm('/ht-biz/evaluate/save', {
                    targetId: id,
                    className: 'com.ht.entity.biz.library.ResourcePage',
                    content: this.discussContent,
                    userId: this.userId
                }, function (data) {
                    if(data.code == 10000){
                        box.getDiscuss(1);
                        box.discussContent = '';
                    }
                    
                }, function (data) {

                });
            } else {
                //需要先登录
                $(".loginBox").show();
            }
        },
        //点击回复
        showTextarea: function (name, id) {
            if (vm.code == 10000) {
                $("#" + id).show();
                this.replyName = name;
                $("#r_" + id).show();
            } else {
                $(".loginBox").show();
            }
        },
        //点击取消
        cancelTextarea: function (id) {
            $("#" + id).hide();
            $("#r_" + id + " reply_word").hide();
            $("#r_" + id).hide();
        },
        //发布回复评论
        postMsg2: function (pid) {
            if (vm.code == 10000) {
                //已登录
                //发送数据
                var replyContent = $.trim($("." + pid).val());
                //var replyContent = $("#reply_word").val();
                //console.log(replyContent);
                if (replyContent.trim() == '') {
                    layer.msg('回复内容不能为空');
                    return;
                }
                var id = $(".getId").val();
                htajax.postForm('/ht-biz/evaluate/save', {
                    targetId: id,
                    className: 'com.ht.entity.biz.library.ResourcePage',
                    content: replyContent,
                    pid: pid
                }, function (data) {
                    if(data.code == 10000){
                        layer.msg("评论成功");
                        $(".reply_textarea").hide();
                        box.getDiscuss();
                    }
                }, function (data) {});
            } else {
                //需要先登录
                $(".loginBox").show();
            }
        },
        //关闭登录框
        closeLogin: function () {
            this.isLogin = true;

        },
        // 推荐
        getDelibraryList: function () {
            //var id = $(".getId").val();
            var tit = $("#title").text();
            // console.log(tit);
            htajax.get("/ht-biz/library/promotelibrary?size=" + 10 + '&title=' + tit, 
            function (data) {
                if (data.code == 10000) {
                    box.delibraryList = data.data;
                }
            }, function (data) {
                //错误回调
            });
        },
        // 页码点击事件
        btnClick: function (data) {
            if (data < 1) return;
            this.getDiscuss(data);
        },
        // 下一页
        nextPage: function (data) {
            if (this.cur >= this.totalPage) return;
            this.btnClick(this.cur + 1);
        },
        // 上一页
        prvePage: function (data) {
            if (this.cur <= 1) return;
            this.btnClick(this.cur - 1);
        },
        // 设置按钮禁用样式
        setButtonClass: function (isNextButton) {
            if (isNextButton) {
                return this.cur >= this.totalPage ? "page-button-disabled" : ""
            } else {
                return this.cur <= 1 ? "page-button-disabled" : ""
            }
        },

    }
});

Vue.filter('moment', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});