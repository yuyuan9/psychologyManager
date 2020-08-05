/**
 * Created by ACER on 2019/11/27.
 */
Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD HH:mm:ss';
    return moment(value).format(formatString);
});

var box = new Vue({
    el: '#box',
    data() {
        return {
            Stauts3: false,
            Stauts2: false,
            Stauts4: false,
            bar: 0,
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            honeyInfo1:false,
            honeyInfo2: false,
            honeyInfo3:false,
            toSubmitList:[],
            proname:'',
            username:'',
            userType:'',
            total1: 0,
            total2: 0,
            total3: 0,
            total4: 0,
            //拒接原因
            refuse:'',
            fuse:'',
            productCounts:'', 
            productPrice: '', 
            serviceCast: '',
            saveframe: false,
            saveframe2: false,
            saveframe3:false,
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getUserInfo();
            this.getNumber();
        },
        // 获取用户的信息
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser", function (data) {
                // console.log(data);
                if (data.code == 10000) {
                    box.userType = data.data.userType;
                    box.getOrder();
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //获取条数
        getNumber: function () {
            htajax.get("/ht-biz/product/findstautnum", function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    var stauts = data.data;
                    for (var i = 0; i < stauts.length; i++) {//状态 1：待提交 2：待审核  3：下线  4：审核不通过 5：上线
                        if (stauts[i].staut == 1) {
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
        getOrder: function (index) {
            var current = index;
            var serviceId = '';
            var status = '';
            // console.log(this.userType);
            if (this.bar == 1) { status = 0}
            if (this.bar == 2) { status = 1 }
            if (this.bar == 3) { status = 2 }
            if (this.bar == 0) {  status = ''}
            if (this.userType == 'SERVICE_PROVIDER'){serviceId = 1;}
            htajax.postForm("/ht-biz/myorder/list",{
                serviceId: serviceId,
                status: status,
                current: current,
            }, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.toSubmitList = data.data.records;
                    box.page.currentPage = data.data.current;
                    box.page.totalPage = data.data.pages;
                    box.page.totalResult = data.data.total;
                    box.total2 = data.reserveData[0].count;
                    box.total3 = data.reserveData[1].count;
                    box.total4 = data.reserveData[2].count;
                    box.total1 = box.total2 + box.total3 + box.total4;
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
            
        },
        //分页
        clickPage: function (index) {
            if (index != 0) {
                this.getOrder(index);
            }
        },
        //拒绝
        getrefuse: function (id, createId, proname){
            this.honeyInfo1 = true;
            this.proname = proname;
            this.username = createId;
            this.fuse = id;
        },
        sureFuse:function(){
            var id = this.fuse;
            var refuse = this.refuse;
            htajax.postForm("/ht-biz/myorder/save", {
                id: id,
                status: 2,
                refuse: refuse,
            }, function (data) {
                if (data.code == 10000) {
                    box.honeyInfo1 = false;
                    box.saveframe2 = true;
                    var timer = setTimeout(function () { box.saveframe2 = false; }, 1500);
                    // layer.msg("拒绝成功");
                    box.getOrder();
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        //开始服务
        sureService: function (id, createId, proname, productCounts, productPrice, serviceCast) {
            this.fuse = id;
            this.honeyInfo2 = true;
            this.proname = proname;
            this.username = createId;
            this.productCounts = productCounts;
            this.productPrice = productPrice;
            this.serviceCast = serviceCast;
        },
        sureOrder: function () {
            var id = this.fuse;
            htajax.postForm("/ht-biz/myorder/save", {
                id: id,
                status: 1
            }, function (data) {
                if (data.code == 10000) {
                    // layer.msg("恭喜您，该订单接单成功");
                    box.honeyInfo2 = false;
                    box.saveframe = true;
                    var timer = setTimeout(function () { box.saveframe = false; }, 1500);
                    box.getOrder();
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        //删除
        deleteService: function (id, createId, proname, productCounts, productPrice, serviceCast) {
            this.fuse = id;
            this.honeyInfo3 = true;
            this.proname = proname;
            this.username = createId;
            this.productCounts = productCounts;
            this.productPrice = productPrice;
            this.serviceCast = serviceCast;
            
        },
        sureDetele:function(){
            var id = this.fuse;
            htajax.postForm("/ht-biz/myorder/save", {
                id: id,
                serviceDeleted: 1
            }, function (data) {
                if (data.code == 10000) {
                    // layer.msg("删除成功");
                    box.honeyInfo3 = false;
                    box.saveframe3 = true;
                    var timer = setTimeout(function () { box.saveframe3 = false; }, 1500);
                    box.getOrder();
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})