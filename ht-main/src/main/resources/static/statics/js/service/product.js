/**
 * Created by ACER on 2019/11/27.
 */
Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});

var box = new Vue({
    el: '#box',
    data() {
        return {
            Stauts3:false,
            Stauts2:false,
            Stauts4:false,
            bar:0,
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            boxname: '',
            boxid: '',
            honeyInfo1:false,
            online: 0,//上线
            toSubmit: 0,//待提交
            toAudit: 0,//待审核
            offline: 0,//下线
            fails: 0,//失败
            onlineList: [],//上线
            toSubmitList: [],//待提交
            toAuditList: [],//待审核
            offlineList: [],//下线
            failsList: [],//失败
            word:'',
            proid:'',
            Stauts5:false,
            removeid:'',
            saveframe: false,
            auditframe: false,
            shanframe:false,
            yijianname:'',
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getNumber();
            this.getProduct();
        },
        //获取条数
        getNumber:function(){
            htajax.get("/ht-biz/product/findstautnum", function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    var stauts = data.data;
                    for (var i = 0; i < stauts.length; i++) {//状态 1：待提交 2：待审核  3：下线  4：审核不通过 5：上线
                        if (stauts[i].staut == 1){
                            box.toSubmit = stauts[i].num;
                        }
                        if (stauts[i].staut == 2) {
                            box.toAudit = stauts[i].num;
                        }
                        if (stauts[i].staut == 3) {
                            box.offline = stauts[i].num;
                        }
                        if (stauts[i].staut == 4) {
                            box.fails = stauts[i].num;
                        }
                        if (stauts[i].staut == 5) {
                            box.online = stauts[i].num;
                        }
                    }
                }
            }, function (data) {
                //layer.msg(data.msg);
            });  
        },
        //
        getProduct:function(index){
            var current = index;
            if (this.bar == 1) { var type = 1; }
            if (this.bar == 2) { var type = 2; }
            if (this.bar == 3) { var type = 3; }
            if (this.bar == 4) { var type = 4; }
            if (this.bar == 0) { var type = 5; }//状态 1：待提交 2：待审核  3：下线  4：审核不通过 5：上线
            htajax.postForm("/ht-biz/product/findbystaut", {
                staut:type,
                current:current,
            }, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    if (box.bar == 1) { box.toSubmitList = data.data.records }
                    if (box.bar == 2) { box.toAuditList = data.data.records }
                    if (box.bar == 3) { box.offlineList = data.data.records }
                    if (box.bar == 4) { box.failsList = data.data.records }
                    if (box.bar == 0) { box.onlineList = data.data.records }
                    box.page.currentPage = data.data.current;
                    box.page.totalPage = data.data.pages;
                    box.page.totalResult = data.data.total;
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        //分页
        clickPage: function (index) {
            if (index != 0) {
                this.getProduct(index);
            }
        },
        //
        changeStaut:function(index,name,id){
            this.boxname = name;
            this.boxid = id;
            if (index == 3) { this.Stauts3 = true }//下线
            if (index == 2) { this.Stauts2 = true }//提交审核
        },
        getChange:function(type){
            htajax.postForm("/ht-biz/product/updatestaut", {
                staut: type,
                id: this.boxid,
            }, function (data) { 
                if(data.code == 10000){
                    if (type==2){
                        box.saveframe = true;
                        box.Stauts2 = false;
                        box.getProduct();
                        var timer = setTimeout(function () { box.saveframe = false; }, 1500);
                        box.getNumber();
                    }
                    if(type==3){
                        box.auditframe = true;
                        box.Stauts3 = false;
                        box.getProduct();
                        var timer = setTimeout(function () { box.auditframe = false; }, 1500);
                        box.getNumber();
                    }
                    
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
            
        },
        remove:function(id,name){
            this.removeid = id;
            this.boxname = name;
            this.Stauts5 = true;
        },
        shan:function(){
            var id = this.removeid
            htajax.postForm("/ht-biz/product/delect", {
                id: id,
            }, function (data) {
                if (data.code == 10000) {
                    // layer.msg("删除成功");
                    box.Stauts5 = false;
                    box.shanframe=true;
                    box.getProduct();
                    var timer = setTimeout(function () { box.shanframe = false; },1500);
                    box.getNumber();
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
            
        },
        opinion:function(name,word,id){
            box.yijianname = name;
            box.word = word;
            box.proid = id;
            box.Stauts4 = true;
        },
        productDetalis:function(){
            window.open('/ht-biz/service/index/product_details?id=' + this.proid);
        },
        proEdit:function(id){
            window.open('/ht-biz/service/index/product_add?id=' + id);
        },
        proEditBox:function(){
            window.open('/ht-biz/service/index/product_add?id=' + this.proid);
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})