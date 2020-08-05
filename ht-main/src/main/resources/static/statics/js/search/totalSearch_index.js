Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});

var box = new Vue({
    el: '#totalSearch',
    data: {
        tab:0,
        targetData:'',
        isShow: false,
        page: {
            currentPage: 1,
            totalPage: 1,
            totalResult: 0
        },
        key:0,
        keyword:'',
        keyword2:'',
        resultDatas:[],
        notfind:0,
        havechiose: 0,
    },
    created: function () {
        this.initPage();

    },
    methods: {
        initPage: function () {
            this.getKeyWord();
        },
        //获取链接的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        //从链接获取keyword
        getKeyWord:function(){
            this.allSearch(1);
        },
        //全局搜索
        allSearch: function (index){
            if (this.targetData != "") {
                var currentPage = this.targetData;
            } else {
                var currentPage = index;
            }
            if (this.keyword == undefined) {var keyword = '';}
            var keyword1 = this.getUrlData("keyword");
            var keyword2 = $('input[name="keyword"]').val();
            if (keyword2 != "" && keyword2 != undefined) { 
                this.keyword = keyword2;
            }else{
                this.keyword = keyword1;
                
            }
            var signType = this.tab;
            if (signType == 0) { var type = '' }
            if (signType == 1) { var type = 'product' }
            if (signType == 2) { var type = 'library' }
            if (signType == 3) { var type = 'policydig' }
            htajax.postFormDone("/ht-biz/search/list",{
                keyword:this.keyword,
                sign: type,
                current: currentPage,//分页
            }, function () {
                var index = layer.load(1, {
                    shade: [0.5, '#fff'] //0.1透明度的白色背景
                });
            }, function (data) {
                layer.closeAll();
                if (data.code == 10000) {
                    if (data.data.total == 0) { box.notfind = 1; }
                    if (data.data.total != 0) { box.notfind = 0; }
                    //console.log(data);
                    box.resultDatas = data.data.records;
                    box.page.currentPage = data.data.current;
                    box.page.totalPage = data.data.pages;
                    box.page.totalResult = data.data.total;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
            this.targetData = '';
        },
        //分页
        clickPage: function (index) {
            if (index != 0) {
                box.allSearch(index);
            }
        },
        // 设置按钮禁用样式
        setButtonClass: function (isNextButton) {
            if (isNextButton) {
                return this.mun >= this.totalPage ? "page-button-disabled" : ""
            } else {
                return this.mun <= 1 ? "page-button-disabled" : ""
            }
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    },
});
