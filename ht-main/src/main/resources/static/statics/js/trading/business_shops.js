/**
 * Created by ACER on 2019/11/27.
 */

var box = new Vue({
    el: '#box',
    data() {
        return {
            allSearch:'',
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            allkeyword:'',
            proList:[],
            proid:'',
            id:'',
            cur:'',
            tab:0,
            value:'',
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getGrade();
        },
        //获取链接的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        getGrade: function () {
            htajax.get("/ht-biz/honeymanager/getUserLevel?type=" + 2, function (data) {
                if (data.code == 10000) {
                    if (data.data != null) {
                        box.value = data.data.code;//星级
                    }

                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getService: function (cur){
            var proid = this.proid;
            var id = this.id;
            if(cur == undefined){cur = 1}
            htajax.postFormDone("/ht-biz/product/topageserpro?t=" + new Date().getTime(), {
                createid: proid,
                producttypeone: id,
                current:cur,
                size:12,
            }, function () {
                var index = layer.load(1, {
                    shade: [0.5, '#fff'] //0.1透明度的白色背景
                });
            }, function (data) {
                layer.closeAll();
                if (data.code == 10000) {
                    console.log(data);
                    box.proList = data.data;
                    box.page.currentPage = data.reserveData.current;
                    box.page.totalPage = data.reserveData.pages;
                    box.page.totalResult = data.reserveData.total;
                }
            }, function (data) {
                layer.closeAll();
                //layer.msg(data.msg);
            })
            
        },
        //分页
        clickPage: function (index) {
            if (index != 0) {
                box.cur=index;
                box.getService(index);
            }
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})