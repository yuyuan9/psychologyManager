/**
 * Created by ACER on 2019/8/8.
 * 精准匹配
 */

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
    el: '#box',
    data: function () {
        return {
            scale: 5,
            words: '',
            allkeyword: '',
            notfind: '',
            contentDatas: [],
            scaleType: '',
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            deriveInfo:false,
            targetData: '',
            userHoney: '',
            guimo:'',
        }
    },
    created: function () {
        this.initPage();

    },
    methods: {
        initPage: function () {
            this.getUserHoney();
            this.getPreliminary(1);
        },
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        //获取链接的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        //用户honey
        getUserHoney: function () {
            htajax.get("/ht-biz/honeymanager/getHoneyTotal", function (data) {
                if (data.code == 10000) {
                    box.userHoney = data.data;
                    if (box.userHoney < 0) {
                        box.userHoney = 0;
                    }

                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //点击切换
        checkScaleType: function () {
            switch (this.scale) {
                case 0:
                    this.scaleType = '不限';
                    break;
                case 1:
                    this.scaleType = '超大型';
                    break;
                case 2:
                    this.scaleType = '大型';
                    break;
                case 3:
                    this.scaleType = '中大型';
                    break;
                case 4:
                    this.scaleType = '中小型';
                    break;
                case 5:
                    this.scaleType = '小微型';
                    break;
                case 6:
                    this.scaleType = '初创';
                    break;
            }
            box.getPreliminary();
        },
        //从链接获取
        returnScaleType:function(){
            switch (this.guimo) {
                case '':
                    this.scale = 0;
                    break;
                case '超大型':
                    this.scale = 1;
                    break;
                case '大型':
                    this.scale = 2;
                    break;
                case '中大型':
                    this.scale = 3;
                    break;
                case '中小型':
                    this.scale = 4;
                    break;
                case '小微型':
                    this.scale = 5;
                    break;
                case '初创':
                    this.scale = 6;
                    break;
            }
        },
        //获取初步匹配
        getPreliminary: function (index) {
            if (this.targetData != "") {
                var currentPage = this.targetData;
            } else {
                var currentPage = index;
            }
            if (currentPage == undefined) { currentPage=1}
            var companyName = this.getUrlData("companyName");
            var province = this.getUrlData("province");
            var city = this.getUrlData("city");
            var area = this.getUrlData("area");
            var applyField = this.getUrlData("productServ");
            var scaleCode1 = this.scaleType;
            var scaleCode2 = this.getUrlData("guimo");
            if (scaleCode1 != "" && scaleCode1 != undefined) {
                this.guimo = scaleCode1;
            } else {
                this.guimo = scaleCode2;
            }
            if (this.guimo == "不限") { this.guimo=''};
            this.returnScaleType();
            htajax.postFormDone('/ht-biz/policymatch/list', {
                companyName: companyName,
                matchType: 2,
                province: province,
                city: city,
                area: area,
                applyField: applyField,
                scaleCode: this.guimo,
                current: currentPage,//分页
                size: 14,
            }, function () {
                var index = layer.load(1, {
                    shade: [0.5, '#fff'] //0.1透明度的白色背景
                });
            }, function (data) {
                layer.closeAll();
                if (data.code == 10000) {
                    //console.log(data);
                    if (data.data.total == 0) { box.notfind = 1; }
                    if (data.data.total != 0) { box.notfind = 0; }
                    box.contentDatas = data.data.records;
                    box.page.currentPage = data.data.current;
                    box.page.totalPage = data.data.pages;
                    box.page.totalResult = data.data.total;
                }
            }, function (data) {
                layer.closeAll();
                //错误回调
                // console.log(data.msg)
            });
        },
        //分页
        clickPage: function (index) {
            box.pageturn = index;
            if (index != 0) {
                box.getPreliminary(index);
            }
        },
        downloadInfo: function () {
            if (vm.code == 10000) {
                this.deriveInfo = true;
            } else {
                $(".loginBox").show();
            }
        },
        deriveToIf: function () {
            var companyName = this.getUrlData("companyName");
            var province = this.getUrlData("province");
            var city = this.getUrlData("city");
            var area = this.getUrlData("area");
            var applyField = this.getUrlData("productServ");
            var scaleCode1 = this.scaleType;
            var scaleCode2 = this.getUrlData("guimo");
            if (scaleCode1 != "" && scaleCode1 != undefined) {
                this.guimo = scaleCode1;
            } else {
                this.guimo = scaleCode2;
            }
            if (this.guimo == "不限") { this.guimo = '' };
            var needhoney = this.page.totalResult * 10;

            if (this.userHoney <= needhoney) {
                layer.msg("您的honey值不足，请前往个人云中心充值");
                return false;
            } else {
                location.href = '/ht-biz/policymatch/export?companyName=' + companyName + '&matchType=' + 2 + '&province=' + province + '&city=' + city + '&area=' + area + '&applyField=' + applyField + '&scaleCode=' + this.guimo + "&size=" + this.page.totalResult;
                box.deriveInfo = false;
            }

        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    },
});
