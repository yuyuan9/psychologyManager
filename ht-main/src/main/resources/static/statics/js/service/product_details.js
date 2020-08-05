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
            staut:'',
            isscience:'',
            productname: '',
            plevel: '',
            plevelname: '',
            provice: '',
            city: '',
            area: '',
            pricetype: '',
            price: '',
            pricetwo:'',
            applytimetype: '',
            begintime: '',
            endtime: '',
            applyobject: '',
            supportmode: '',
            departmet: '',
            subsidy: '',
            applybenefit: '',
            productinfo:'',
            servicecontent: '',
            other: '',
            finishtime: '',
            producttypeone: '',
            producttypetwo: '',
            producttypethree: '',
            producttypeonename: '',
            producttypetwoname: '',
            producttypethreename: '',
            finishtime:'',
            servicecontent:'',
            auditframe:false,
            Stauts3:false,
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getDetalis();
        },
        //获取链接的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        //获取信息
        getDetalis: function () {
            var id = this.getUrlData("id");
            htajax.postForm("/ht-biz/product/findbyid", {
                id:id,
            }, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.isscience = data.data.isscience;//true 科技政策 false普通类型
                    box.staut = data.data.staut//状态 1：待提交 2：待审核  3：下线  4：审核不通过 5：上线
                    box.productname = data.data.productname;
                    box.plevel = data.data.plevel;
                    box.plevelname = data.data.plevelname;
                    box.provice = data.data.provice;
                    box.city = data.data.city;
                    box.area = data.data.area;
                    box.pricetype = data.data.pricetype;
                    box.price = data.data.price;
                    box.pricetwo = data.data.pricetwo;
                    box.applytimetype = data.data.applytimetype;
                    box.begintime = data.data.begintime;
                    box.endtime = data.data.endtime;
                    box.applyobject = data.data.applyobject;
                    box.supportmode = data.data.supportmode;
                    box.departmet = data.data.departmet;
                    box.subsidy = data.data.subsidy;
                    box.applybenefit = data.data.applybenefit;
                    box.productinfo = data.data.productinfo;
                    box.servicecontent = data.data.servicecontent;
                    box.other = data.data.other;
                    box.finishtime = data.data.finishtime;
                    box.producttypeone = data.data.producttypeone;
                    box.producttypetwo = data.data.producttypetwo;
                    box.producttypethree = data.data.producttypethree;
                    box.producttypeonename = data.data.producttypeonename;
                    box.producttypetwoname = data.data.producttypetwoname;
                    box.producttypethreename = data.data.producttypethreename;
                    box.finishtime = data.data.finishtime;
                    box.servicecontent = data.data.servicecontent;
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        //下线
        getChange: function () {
            this.Stauts3 =true;
        },
        getUnderLine:function(){
            var id = this.getUrlData("id");
            htajax.postForm("/ht-biz/product/updatestaut", {
                staut: 3,
                id: id,
            }, function (data) {
                if (data.code == 10000) {
                    box.Stauts3 = false; 
                    box.auditframe = true;
                    var timer = setTimeout(function () {
                         box.auditframe = false; 
                         window.location.href = "/ht-biz/service/index/product";
                     }, 1500);
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