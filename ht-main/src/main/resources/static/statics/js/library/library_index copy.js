var library = new Vue({
    el: '#library',
    data: {
        keywords:'',
        cur:0,
        curAccordion:0,
        curProject:'',
        curPolicy:8,
        curTechnology:'',
        targetData:'',
        isRegion:'',
        items: [],
        recName:'',
        recCover:'',
        title:'',
        imgUrl1:'',
        policy:[],
        region:[],
        tabProjectType:'',
        tabProjectActive:-1,
        tabPolicyType:'',
        tabPolicyActive:-1,
        //分页
        total:'',
        mun:1,
        totalPage:'',
        current:'',
        
        //位置
        province: '省级',
        isProvince: false,
        //isProvince0: false,
        city: '市级',
        cityData: [],
        //cityData0: [],
        isCity: false,
        //isCity0: false,
        isCityActive: 0, //0-不active  1-active
        //isCityActive0: 0, //0-不active  1-active
        area: '区级',
        area1: '区级',
        areaData: [],
        //areaData0: [],
        areaData1: [],
        isArea: false,
        //isArea0:false,
        isArea1: false,
        isAreaActive: 0, //0-不active  1-active
        isAreaActive0: 0, //0-不active  1-active
        showArea: true,

        isProType: '',//地区
        isProDepartment:'',//部门
        pitchDataProType:'',
        policyList:[],

        haveData: 0, //是否有地区列表，0有，1无
        remList: [], //科研立项推荐列表

        countryData:[],
        country:"全国",
        countryActive:-1,

        provinceData: [],
        province: "省级",
        provinceActive: -1,

        cityData: [],
        city: "市级",
        cityActive: -1,

        areaData: [],
        area: "区级",
        areaActive: -1,

        contentDatas:[],
        lists:[],
        //选中条件
        delibraryList1:[],
        delibraryList2:[],
        delibraryList3:[],

        pitchDataRegion:'',
        pitchCity:'',
        pitchArea:'',
        pitchProject:'',
        pitchPolicy:"全部",
        pitchTechnology:'',

        regionData: [], // 位置信息
        bigData:'',
        setProject:'',
        key:'',
    },
    computed: {
        indexs: function () {
            var left = 1;
            var right = this.totalPage;
            var ar = [];
            if (this.totalPage >= 8) {
                if (this.mun > 5 && this.mun < this.totalPage - 4) {
                    left = this.mun - 5;
                    right = this.mun + 4
                } else {
                    if (this.mun <= 5) {
                        left = 1;
                        right = 10;
                    } else {
                        right = this.totalPage;
                        left = this.totalPage - 9
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
            if (ar[ar.length - 1] < this.totalPage) {
                ar[ar.length - 1] = this.totalPage;
                ar[ar.length - 2] = 0;
            }
            return ar;
        },
    },
    
    methods: {
        initPage: function () {
            if (this.getUrlData('tabPolicyActive') == 4) {
                this.tabPolicyActive = 4;
                this.tabPolicyType = 4;
            }
            var urlId= this.getUrlData("keyword");
            if(urlId == undefined || urlId == null){
                urlId = '';
            }
            if(urlId.length != 0 && urlId != '#path'){
                this.keywords=urlId;
            }else{
                this.keywords='';
            }
            //this.getAreaData();
            //this.getRegion();
            this.btnClick(0);
            this.pageHandler();
            this.getDelibraryList1();
            this.getDelibraryList2();
            this.getDelibraryList3();
            this.getregionData();
            this.getSearch();
        },
        skipTo:function(){
            //判断登录
            if (vm.code == 10000) {
                location.href = '/ht-biz/library/upload'
            } else {
                //需要先登录
                $(".loginBox").show();
            }
        },
        //搜索
        searchBigData: function () {
            if (vm.code == 10000) {
                window.open("/ht-biz/inquire/policy_details?keyword1=" + this.bigData + "&theme=" + 1);
            } else {
                //需要先登录
                $(".loginBox").show();
            }

        },
        searchProject: function () {
            if (vm.code == 10000) {
                window.open("/ht-biz/inquire/project_details?keyword2=" + this.setProject + "&theme=" + 2);
            } else {
                //需要先登录
                $(".loginBox").show();
            }

        },
        pageHandler: function (page) {
            this.page = page;
        },
        stopThings:function(){
            if(this.isCityActive ==1){
                this.isArea=true;
            }
        },
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)')
                .exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        
        // 2获取全国的省、市信息
        getregionData: function () {
            htajax.get('/statics/js/cityData.json',
                function (data) {
                    library.regionData = data;
                });
        },
        // 获取某个市的
        getCityData: function () {
            if (library.province != '省级') {
                for (var i = 0; i < library.regionData.length; i++) {
                    if (library.regionData[i].n == library.province) {
                        library.cityData = library.regionData[i].s;
                        library.isCity = true;
                    }
                }
            } else {
                library.cityData = [];
                library.isCity = false;
            }
        },
        choseCity: function (city) {
            library.city = city;
            library.isCity = false;
            if (library.isRegion == 2) {
                library.isCityActive = 1;
            }
            library.area = '区级';
            library.isAreaActive = 0;
            library.pitchArea = '';
            library.getAreaData();
        },

        getAreaData: function () {
            if (library.city != '市级') {
                for (var i = 0; i < library.cityData.length; i++) {
                    if (library.cityData[i].n == library.city) {
                        library.areaData = library.cityData[i].s;

                        if (library.areaData == undefined) {
                            library.showArea = false;
                        } else {
                            library.showArea = true;
                        }

                    }
                }
            } else {
                library.areaData = [];
            }
        },

        getPitchData:function(){
            //地区
            if (this.isRegion == 1) {
                this.pitchDataRegion = '国家';
            } else if (this.isRegion == 2) {
                this.pitchDataRegion = this.province;
                if (this.isCityActive == 1) {
                    this.pitchCity = this.city;
                    if (this.showArea == true && this.isAreaActive == 1) {
                        this.pitchArea = this.area;
                    }
                }
            }
            // 政策类型 
            switch(this.curPolicy){
                case 8:
                    this.pitchPolicy = '全部';
                    break;
                case 7:
                    this.pitchPolicy = '资源包';
                    break;
                case 4:
                    this.pitchPolicy = '政策解读';
                    break;
                case 2:
                    this.pitchPolicy = '模板范文';
                    break;
                case 1:
                    this.pitchPolicy = '培训资料';
                    break;
                case 6:
                    this.pitchPolicy = '申报通知';
                    break;
                case '0':
                    this.pitchPolicy = '公示名录';
                    break;
                case 5:
                    this.pitchPolicy = '政府文件';
                    break;
                case 3:
                    this.pitchPolicy = '其他';
                    break;
            }
            // 项目分类：
            switch(this.curProject){
                // case 0:
                //     this.pitchProject = '全部';
                //     break;
                case 1:
                    this.pitchProject = '高新技术企业';
                    break;
                case 2:
                    this.pitchProject = '研发机构';
                    break;
                case 3:
                    this.pitchProject = '科研立项';
                    break;
                case 4:
                    this.pitchProject = '知识产权';
                    break;
                case 5:
                    this.pitchProject = '科技成果';
                    break;
                case 6:
                    this.pitchProject = '科技财金';
                    break;
                case 7:
                    this.pitchProject = '人才团队';
                    break;
                case 8:
                    this.pitchProject = '全部';
                    break;
                case 9:
                    this.pitchProject = '其他';
                    break;
            }

            // 技术领域 全部 电子信息 生物与新医药 航天航空 新材料 高科技服务 新能源与节能 资源与环境 其他
            switch(this.curTechnology){
                case 0:
                    this.pitchTechnology = '全部';
                    break;
                case 1:
                    this.pitchTechnology = '电子信息';
                    break;
                case 2:
                    this.pitchTechnology = '生物与新医药';
                    break;
                case 3:
                    this.pitchTechnology = '航天航空';
                    break;
                case 4:
                    this.pitchTechnology = '新材料';
                    break;
                case 5:
                    this.pitchTechnology = '高科技服务';
                    break;
                case 6:
                    this.pitchTechnology = '新能源与节能';
                    break;
                case 7:
                    this.pitchTechnology = '资源与环境';
                    break;
                case 8:
                    this.pitchTechnology = '其他';
                    break;
            }
            this.getRegion()
        },
        //接收地区
        getHeadCity: function (city) {
            // console.log(city);
            if (city.indexOf('省') >= 0) {
                this.province = city;
                this.city = '市级';
                this.area = '区级';
                this.pitchDataRegion=city;
                this.pitchCity = '';
                this.pitchArea = '';
            }
            if (city.indexOf('市') >= 0) {
                if (city.indexOf("北京市") >= 0) {
                    this.province = city;
                    this.city = '市级';
                }
                else if (city.indexOf("天津市") >= 0) {
                    this.province = city;
                    this.city = '市级';
                }
                else if (city.indexOf("上海市") >= 0) {
                    this.province = city;
                    this.city = '市级';
                }
                else if (city.indexOf("重庆市") >= 0) {
                    this.province = city;
                    this.city = '市级';
                } else {
                    this.city = city;
                    this.province = '省级';
                }
                this.area = '区级';
                this.pitchDataRegion = '';
                this.pitchCity = city;
                this.pitchArea = '';
            }
            if (city.indexOf('区') >= 0) {
                this.area = city;
                this.province = '省级';
                this.area = '区级';
                this.pitchDataRegion = '';
                this.pitchCity = '';
                this.pitchArea = city;
            }
            if (city.indexOf('全国') >= 0) {
                this.isRegion = 1;
                this.province = '省级';
                this.city = '市级';
                this.area = '区级';
            }
            this.isclaCation = '';
            this.targetData = '';
            this.getPitchData();
        },

        getSearch:function(){
            var that = this;
            if(that.key == ''){
                var url= that.getUrlData("curPolicy");
                that.curPolicy = url;
            }
            this.getRegion();
        },
        getRegion: function (index,type) {
            var that = this;
            var province0 = that.province;
            var city0 = that.city;
            var area0 = that.area;
            //var curProject = that.curProject;
            var pitchProject = that.pitchProject;
            var curPolicy = that.curPolicy;
            var curTechnology = that.curTechnology;
            if (that.isRegion == 1) {
                province0 = '国家';
            }
            if (province0 == '省级') {
                province0 = '';
            }
            if (city0 == '市级') {
                city0 = '';
            }
            if (area0 == '区级') {
                area0 = '';
            }
            if(pitchProject == ""){
                pitchProject = "";
            }
            if(pitchProject == "全部"){
                pitchProject = "";
            }
            if(curPolicy == null){
                curPolicy = "";
            }
            if(curPolicy == 8){
                curPolicy = "";
            }
            if(curPolicy == 4){
                that.pitchPolicy = "政策解读";
            }
            if(curTechnology == ""){
                curTechnology = "";
            }
            if(index == undefined){
                index = 1;
            }
            if(index == ''){
                index = 1;
            }
            if(that.targetData != ""){
                var currentPage = that.targetData;
            }else{
                var currentPage = index;
            }
            if(currentPage == undefined || currentPage == ''){
                currentPage = 1;
            }
            layer.load(2,{
                offset: ['65%', "40%"],
                shade: false
            });
            var url = "/ht-biz/library/findlibraryall?current=" + currentPage + "&keywords="+ that.keywords  + "&province=" + province0 + "&city=" + city0 + "&area=" + area0 + "&type=" + pitchProject + "&libtype=" + curPolicy + "&size=" + "15" + "&status=" + "1";
            // console.log(url);
            htajax.get(encodeURI(url + "&t="+new Date().getTime()), function (data) {
                if (data.code = 10000) {
                    // console.log(data);
                    layer.closeAll();
                    that.contentDatas = data.data;
                    if(that.contentDatas == null){
                        that.total = 0;
                    }else{
                        that.mun = data.reserveData.current;
                        that.totalPage = data.reserveData.pages;
                        that.total = data.reserveData.total;
                    }
                    

                    that.remList = data.data;
                    that.bigData = that.keywords;
                    that.setProject = that.keywords;
                    
                }
            }, function (data) {
                layer.msg(data.msg);
            });
        },
        //分页
        btnClick: function (index) {
            if (index < 1) return;
            this.getRegion(index);
            this.targetData = '';
            $("html,body").animate({scrollTop:0},10);
        },
        // 下一页
        nextPage: function (data) {
            this.targetData = '';
            if (this.mun >= this.totalPage) return;
            this.btnClick(this.mun + 1);
            $("html,body").animate({scrollTop:0},10);
        },
        // 上一页
        prvePage: function (data) {
            this.targetData = '';
            if (this.mun <= 1) return;
            this.btnClick(this.mun - 1);
            $("html,body").animate({scrollTop:0},10);
        },
        // 设置按钮禁用样式
        setButtonClass: function (isNextButton) {
            if (isNextButton) {
                return this.mun >= this.totalPage ? "page-button-disabled" : ""
            } else {
                return this.mun <= 1 ? "page-button-disabled" : ""
            }
        },
       
         //资源包推荐
         getDelibraryList1: function () {
            htajax.postForm("/ht-biz/resouce/listpage",{
                retype:1,
                size:4,
            }, function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    library.delibraryList1 = data.data.records;
                }
            }, function (data) {
                //错误回调
            });
        },
        getDelibraryList2: function () {
            htajax.postForm("/ht-biz/resouce/listpage",{
                retype:2,
                size:4,
            }, function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    library.delibraryList2 = data.data.records;
                }
            }, function (data) {
                //错误回调
            });
        },
        getDelibraryList3: function () {
            htajax.postForm("/ht-biz/resouce/listpage",{
                retype:3,
                size:4,
            }, function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    library.delibraryList3 = data.data.records;
                }
            }, function (data) {
                //错误回调
            });
        },
        
    },
    created: function () {
        this.initPage();
    },
});


Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});
Vue.filter('date1', function (value, formatString) {
    formatString = formatString || 'YYYY年MM月DD日';
    return moment(value).format(formatString);
});