/**
 * Created by ACER on 2019/11/27.
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
            //订单
            area: "",
            city: "",
            companyName: "",
            createdate: "",
            createid: "",
            id: "",
            lastmodified: '',
            linkman: "",
            number: "",
            phone: "",
            productCounts: '',
            productId: "",
            productName: "",
            productPrice: '',
            productVo: '',
            province: "",
            refuse: '',
            regionid: "",
            remark: "",
            serviceCast: '',
            serviceDeleted: '',
            serviceId: "",
            serviceLinkman: '',
            servicePhone: '',
            status: '',
            productPrice: '',
            serviceCast: '',
            productCounts: '',
            customerqq:'',
            //产品
            producttypeonename: '',
            producttypetwoname: '',
            producttypethreename: '',
            plevelname: '',
            province1: '',
            city1: '',
            area1: '',
            subsidy: '',
            begintime: '',
            endtime: '',
            pricetype: '',
            price: '',
            price2: '',
            //服务商
            shopname: '',
            pronice2: '',
            city2: '',
            area2: '',
            customercont: '',
            servicePhone: '',
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getOrderStuts();
        },
        //获取链接的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        getOrderStuts: function () {
            var id = this.getUrlData("id");
            htajax.get("/ht-biz/myorder/edit?id=" + id, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.status = data.data.status;
                    box.productName = data.data.productName;
                    box.number = data.data.number;
                    box.createdate = data.data.createdate;
                    box.companyName = data.data.companyName;
                    box.linkman = data.data.linkman;
                    box.phone = data.data.phone;
                    box.province = data.data.province;
                    box.city = data.data.city;
                    box.area = data.data.area;
                    box.refuse = data.data.refuse;
                    box.productId = data.data.productId;
                    box.productPrice = data.data.productPrice;
                    box.serviceCast = data.data.serviceCast;
                    box.serviceId = data.data.serviceId;
                    box.productCounts = data.data.productCounts;
                    box.remark = data.data.remark;
                    box.getProductDetails();
                    box.getServiceDetails();
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getProductDetails: function () {
            var id = this.productId;
            htajax.postForm("/ht-biz/product/findbyid", {
                id: id,
            }, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.producttypeonename = data.data.producttypeonename;
                    box.producttypetwoname = data.data.producttypetwoname;
                    box.producttypethreename = data.data.producttypethreename;
                    box.plevelname = data.data.plevelname;
                    box.begintime = data.data.begintime;
                    box.endtime = data.data.endtime;
                    box.province1 = data.data.provice;
                    box.city1 = data.data.city;
                    box.area1 = data.data.area;
                    box.subsidy = data.data.subsidy;
                    box.pricetype = data.data.pricetype;
                    box.price = data.data.price;
                    box.price2 = data.data.price2;
                    // console.log(box.customerqq);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getServiceDetails: function () {
            var id = this.serviceId;
            htajax.get("/ht-biz/service/findByCreateid?serviceId="+id,  function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.customerqq = data.data.customerqq;
                    box.shopname = data.data.shopname;
                    box.pronice2 = data.data.pronice;
                    box.city2 = data.data.city;
                    box.area2 = data.data.area;
                    box.customercont = data.data.customercont;
                    box.servicePhone = data.data.phone;

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