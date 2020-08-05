/**
 * Created by ACER on 2019/12/5.
 */
Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD HH:mm:ss';
    return moment(value).format(formatString);
});
Vue.filter('date1', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});
var box = new Vue({
    el: '#box',
    data() {
        return {
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            safeList:[],
            days:'',
            status:'',
            banme:'',
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getSafeguardList(1);
            this.inquireService();
        },
        //购买记录列表
        getSafeguardList: function (index){
            var current = index;
            if (current == '' || current == undefined) {
                current = 1;
            }
            htajax.get("/ht-biz/guaran/list?size=" + 4 + "&current=" + current, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.safeList = data.data.records;
                    if (data.data.records.length > 0){
                        box.days = data.data.records[0].days;
                        box.status = data.data.records[0].status;
                    }
                    box.page.currentPage = data.data.current;
                    box.page.totalPage = data.data.pages;
                    box.page.totalResult = data.data.total;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        inquireService: function () {
            htajax.postForm("/ht-biz/service/judge", {}, function (data) {
                if (data.code == 10000) {
                    // box.banme = data.data.banme;//店铺名称
                    box.shopname = data.data.shopname;
                }
            }, function (data) {
                //layer.msg(data.msg);    
            })
        },
        //分页
        clickPage: function (index) {
            if (index != 0) {
                this.getSafeguardList(index);
            }
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})