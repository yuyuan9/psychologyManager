/**
 * Created by dsy on 2019/5/17
 */
Vue.filter('myAddressCity', function (value) {
    for (y in this.CityInfo) {
        if (this.CityInfo[y].value == value) {
            return value = this.CityInfo[y].label;
        }
    }
});
Vue.filter('myAddressErae', function (value) {
    for (y in this.CityInfo) {
        for (z in this.CityInfo[y].children) {
            if (this.CityInfo[y].children[z].value == value && value != undefined) {
                return value = this.CityInfo[y].children[z].label;
            }
        }
    }
});
Vue.filter('myAddressMinerae', function (value) {
    for (y in this.CityInfo) {
        for (z in this.CityInfo[y].children) {
            for (i in this.CityInfo[y].children[z].children) {
                if (this.CityInfo[y].children[z].children[i].value == value && value != undefined) {
                    return value = this.CityInfo[y].children[z].children[i].label;
                }
            }
        }
    }
})
var box = new Vue({
    el:'#box',
    data:{
        match:0,
        code:'',
        allkeyword:'',
        company:'50245',
        provider:'865',
        professor:'302',
        curColor:0,
        cur:0,
        curProduct:0,
        curAccordion:0,
        keyword1:'',
        keyword2:'',
        bannerList:[],
        declareList:[],
        publicityList:[],
        policyList:[],
        delibraryList1:[],
        delibraryList2:[],
        delibraryList3:[],
        // marqueeList:[],
        CityInfo: CityInfo, //地区数据
        form: {
            city: '', // 省
            erae: '', // 城市
            minerae: '', //区
            selectedOptions: [], //地区筛选数组
            selectedOptions2: [], //地区筛选数组
        },
        form1: {
            city: '', // 省
            erae: '', // 城市
            minerae: '', //区
            selectedOptions: [], //地区筛选数组
            selectedOptions2: [], //地区筛选数组
        },
        optionProps: {
            checkStrictly: true,
            value: "label",
            label: "label",
            children: "children"
        },
        productServ:'',
        guimo:'',
    },
    created:function(){
        this.initPage();
    },
    methods: {
        initPage:function(){
            this.getDeclare();
            this.getPublicity();
            this.getPolicy();
            this.getUserInfo();
            // this.getBanner();
            this.getDelibraryList1();
            this.getDelibraryList2();
            this.getDelibraryList3();
            this.getSelect();
            this.getGuimo();
        },
        // 获取用户的信息
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser", function (data) {
                box.code = data.code;
                // console.log(data.code);
            }, function (data) {
                layer.msg(data.msg);
            });
        },
        handleChange: function (value) {
            this.form.city = this.form.selectedOptions[0];
            this.form.erae = this.form.selectedOptions[1];
            this.form.minerae = this.form.selectedOptions[2];
        },
        
        handleChange1: function (value) {
            this.form1.city = this.form1.selectedOptions[0];
            this.form1.erae = this.form1.selectedOptions[1];
            this.form1.minerae = this.form1.selectedOptions[2];
        },
        //主营产品技术领域
        getSelect: function () {
            htajax.get("/ht-biz/catalog/getjson?type=" + 'techfield', function (data) {
                if (data.code == 10000) {
                    $.each(data.data, function (i, item) {
                        $("#productServ").append("<option value='" + item.value + "' " + ">" + item.name + "</option>");
                    });
                }
            }, function (data) {
                //错误回调
            });
        },
        //主营产品技术领域
        getGuimo: function () {
            htajax.get("/ht-biz/policymatch/pclist", function (data) {
                if (data.code == null) {
                    $.each(data.data, function (i, item) {
                        $("#guimo").append("<option value='" + item.value + "' " + ">" + item.text + "</option>");
                    });
                }
            }, function (data) {
                //错误回调
            });
        },
        //初步匹配
        preliminaryMatch:function(){
            var companyName = $.trim($("input[name='companyName1']").val());
            var province = this.form.city;
            var city = this.form.erae;
            var area = this.form.minerae;
            if (companyName == '') { layer.msg("请输入公司名称");return false; }
            if (province == '') { layer.msg("请选择注册地区"); return false;}
            if (city == '') { layer.msg("请输入市级"); return false; }
            if (area == '') { layer.msg("请输入区级"); return false;}
            window.open('/ht-biz/policymatch/preliminary_match?companyName=' + companyName + '&province=' + province + '&city=' + city + '&area=' + area);
        },
        //精准匹配
        preciseMatch: function () {
            var companyName = $.trim($("input[name='companyName2']").val());
            var province = this.form1.city;
            var city = this.form1.erae;
            var area = this.form1.minerae;
            var productServ = $.trim($("select[name='productServ']").val());
            var guimo = $.trim($("select[name='guimo']").val());
            if (productServ == '请选择') { productServ = '' }
            if (guimo == '请选择') { guimo =''}
            if (companyName == '') { layer.msg("请输入公司名称"); return false; }
            if (province == '') { layer.msg("请选择注册地区"); return false; }
            if (city == '') { layer.msg("请输入市级"); return false; }
            if (area == '') { layer.msg("请输入区级"); return false; }
            if (productServ == '') { layer.msg("请选择申报领域"); return false; }
            window.open('/ht-biz/policymatch/precise_match?companyName=' + companyName + '&province=' + province + '&city=' + city + '&area=' + area + '&productServ=' + productServ + '&guimo=' + guimo);
        },
        //全局搜索
        allSearch:function(){
            location.href ="/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        seachPolicy :function (){
            if(box.code == 10000){
                window.open("/ht-biz/inquire/policy_details?theme=1"+"&keyword1="+this.keyword1);
            }else{
                $(".loginBox").show();
            }
        },
        seachProject :function (){
            if(box.code == 10000){
                window.open("/ht-biz/inquire/project_details?theme=2"+"&keyword2="+this.keyword1);
            }else{
                $(".loginBox").show();
            }
        },
        checklogin:function(){
            if(box.code != 10000){
                $(".loginBox").show();
            }else{
                box.match = 1;
            }
        },
        //轮播图
        getBanner:function(id1,id2){
            if (id1 == '') { var bannerId = id2; }
            if (id2 == '') { var bannerId = id1; }
            if (id1 == '' && id2 == ''){var bannerId = '33';}
            htajax.postForm('/ht-biz/banner/getbannerbytype',{
                type:1,//首页
                proid:bannerId,
            },
             function (data) {
                if(data.code == 10000){
                    // console.log(data);
                    box.bannerList = data.data;
                }
            },function(data){
                //错误回调
                // console.log(data.msg)
            });
        },
        // 申报通知
        getDeclare: function(mycity1,mycity2){     
            if (mycity1 == undefined){
                var province = '';
            }else{
                var province = mycity1;
            }
            if (mycity2 == undefined) {
                var city = '';
            } else {
                var city = mycity2;
            }
            htajax.postForm('/ht-biz/policydig/list',{
                nature:1,
                province: province,
                city: city,
            },
             function (data) {
                if(data.code == 10000){
                    //console.log(data.data);
                    box.declareList = data.data;
                }
            },function(){
                //错误回调
                // console.log(data.msg)
            });
        },
        // 公示名录
        getPublicity: function (mycity1, mycity2){
            if (mycity1 == undefined) {
                var province = '';
            } else {
                var province = mycity1;
            }
            if (mycity2 == undefined) {
                var city = '';
            } else {
                var city = mycity2;
            }
            htajax.postForm('/ht-biz/policydig/list',{
                nature: 3,
                province:province,
                city: city,
            },
            function (data) {
                if(data.code == 10000){
                    //console.log(data.data);
                    box.publicityList = data.data;
                }
            },function(){
                //错误回调
                // console.log(data.msg)
            });
        },
        // 政策解读
        getPolicy: function (mycity1, mycity2){
            if (mycity1 == undefined) {
                var province = '';
            } else {
                var province = mycity1;
            }
            if (mycity2 == undefined) {
                var city = '';
            } else {
                var city = mycity2;
            }
            if (mycity1=="全国"){
                var province = '';
                var city = '';
            }
            htajax.get('/ht-biz/library/findlibraryall?libtype=4' + '&province=' + province+'&city=' +city,function(data){
                //console.log(data);
                if(data.code == 10000){
                    box.policyList = data.data;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //资源包推荐
        getDelibraryList1: function () {
            htajax.postForm("/ht-biz/resouce/listpage",{
                retype:1,
                size:5,
            }, function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    box.delibraryList1 = data.data.records;
                }
            }, function (data) {
                //错误回调
            });
        },
        getDelibraryList2: function () {
            htajax.postForm("/ht-biz/resouce/listpage",{
                retype:2,
                size:5,
            }, function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    box.delibraryList2 = data.data.records;
                }
            }, function (data) {
                //错误回调
            });
        },
        getDelibraryList3: function () {
            htajax.postForm("/ht-biz/resouce/listpage",{
                retype:3,
                size:5,
            }, function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    box.delibraryList3 = data.data.records;
                }
            }, function (data) {
                //错误回调
            });
        },

        tickling:function(){
            layer.open({
                type: 1,
                area: ['600px', '400px'], //宽高
                content: $('#ids1') //这里content是一个DOM，这个元素要放在body根节点下
            });
        },
        submitBtn:function(index ){
            layer.closeAll();
            var t=setTimeout("layer.msg('谢谢您的反馈!')",500);
        },
        handletips:function(){
            layer.msg("功能正在开发中，请耐心等待...");
        }
    },
});

Vue.filter('moment', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});
