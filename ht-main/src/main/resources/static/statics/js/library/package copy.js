var package = new Vue({
    el: '#package',
    data: {
        keywords:'',
        cur:0,
        curTwo:0,
        choseCity:0,
        isRegion:'',
        items: [],
        recName:'',
        recCover:'',
        title:'',
        imgUrl1:'',
        policy:[],
        imgUrl2:'',
        region:[],
        imgUrl3:'',
        tabPolicyType:'',
        tabPolicyActive:-1,
        province:'',
        city:'',
        //area:'',
        isProType: '',//地区
        isProDepartment:'',//部门
        pitchDataProType:'',
        policyList:[],

        page: 1,  //显示的是哪一页
        pageSize: 10, //每一页显示的数据条数
        total: 150, //记录总数
        maxPage:5 , //最大页数

        haveData: 0, //是否有地区列表，0有，1无

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
    },
    created:function(){
        //created  表示页面加载完毕，立即执行
        this.pageHandler(1);
    },
    methods: {
        initPage: function () {
            if (this.getUrlData('tabPolicyActive') == 4) {
                this.tabPolicyActive = 4;
                this.tabPolicyType = 4;
            }
            this.getAreaData();
            this.getList();
            this.getPolicy();
            this.getRegion();
        },
        getRegion:function(){},
        pageHandler: function (page) {
            //here you can do custom state update
            this.page = page;
        },
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)')
                .exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        doChose: function (id) {
            var country = this.country;
            if(id == "country_all"){
                this.getRegion();
            }else if(id == "country_list"){
                this.getRegion();
                return;
            } else if (id == "province_list" && package.provinceData == ""){
                this.country ="";
                return;
            } else if (id == "city_list" && package.cityData == "") {
                return;
            } else if (id == "area_list" && package.areaData == "") {
                return;
            }
            $("#" + id).toggle();
        },
        selectCountry: function(country, countryActive){
            if (package.country != country) {
                package.countryActive = -1;
                package.country = country;
                package.countryeActive = countryActive;

                package.initProCityArea(2);
                for (var i = 0; i < package.provinceData.length; i++) {
                    if (package.provinceData[i].n == province) {
                        package.cityData = package.provinceData[i].s;
                        break;
                    }
                }
            }
        },
        selectProvince: function (province, provinceActive) {
            if (package.province != province) {
                package.countryActive = -1;
                package.province = province;
                package.provinceActive = provinceActive;

                package.initProCityArea(2);
                for (var i = 0; i < package.provinceData.length; i++) {
                    if (package.provinceData[i].n == province) {
                        package.cityData = package.provinceData[i].s;
                        break;
                    }
                }
            }
            $("#province_list").hide();
        },
        selectCity: function (city, cityActive) {
            if (package.city != city) {
                package.city = city;
                package.cityActive = cityActive;

                package.initProCityArea(1);
                for (var i = 0; i < package.cityData.length; i++) {
                    if (package.cityData[i].n == city) {
                        package.areaData = package.cityData[i].s;
                        if (package.cityData[i].s == null || package.cityData[i].s == "" || package.cityData[i].s == undefined) {
                            $("#area_div").hide();
                        } else {
                            $("#area_div").show();
                        }
                        break;
                    }
                }
            }
            $("#city_list").hide();
        },
        initProCityArea: function (step) {
            if(step==4){
                package.country = "全国";
                package.countryActive = -1;
                package.province = "省级";
                package.provinceActive = -1;
                package.cityData = [];
                package.city = "市级";
                package.cityActive = -1;
                package.areaData = [];
                package.area = "区级";
                package.areaActive = -1;
            }
            if (step == 3) {
                package.province = "省级";
                package.provinceActive = -1;
                package.cityData = [];
                package.city = "市级";
                package.cityActive = -1;
                package.areaData = [];
                package.area = "区级";
                package.areaActive = -1;
            } else if (step == 2) {
                package.city = "市级";
                package.cityActive = -1;
                package.areaData = [];
                package.area = "区级";
                package.areaActive = -1;
            } else if (step == 1) {
                package.area = "区级";
                package.areaActive = -1;
            }
            package.getRegion();
        },
        selectAreaOne: function (area, areaActive) {
            if (package.area != area) {
                package.area = area;
                package.areaActive = areaActive;
            }
            $("#area_list").hide();
            package.getRegion();
        },
        //单点政策资源包
        getList: function () {
            htajax.postForm("/ht-biz/resouce/listpage",{
                retype:0,
            }, function (data) {
                // console.log(data);
                if (data.code == 10000) {
                    package.items = data.data.records;
                    package.recName = data.data.recName;
                    package.imgUrl1 = data.data.recCover;
                    //console.log(data.data.recCover);
                }
            }, function (data) {
                //错误回调
            });
        },
        //行业政策资源包
        getPolicy:function(){
            htajax.postForm("/ht-biz/resouce/listpage", {
                retype:1,
            },function (data) {
                // console.log(data);
                if (data.code == 10000) {
                    package.policy = data.data.records;
                    package.recName = data.data.recName;
                    package.imgUrl2 = data.data.recCover;
                }
            }, function (data) {
                //错误回调
            });
        },
        getAreaData: function () {
            var selectDataUrl = "/statics/js/cityData.json";
            $.getJSON(selectDataUrl, function (json) {
                package.provinceData = json;
            })
        },

        getRegion: function () {
            var province0 = this.province;
            var city0 = this.city;
            var area0 = this.area;
            if (this.isRegion == 0) {
                province0 ='';
                city0='';
                area0='';
            }else if(this.isRegion == 1){
                province0 = '全国';
                city0='';
                area0='';
            }else if(this.isRegion == 2){
                province0 = this.province;
                city0 = this.city;
                area0 = this.area;
            }
            
            htajax.postForm("/ht-biz/resouce/listpage", {
                retype:2,
                province:province0,
                city:city0,
                area:area0,
                department:this.tabPolicyType,
            },function (data) {
                // console.log(data)
                if (data.code == 10000) {
                    //console.log(data.reserveData.currentPage);
                    package.policyList = data.data.records;
                    package.page = data.data.current;//显示的是哪一页
                    package.pageSize = data.data.size;//每一页显示的数据条数
                    package.total = data.data.total; //记录总数
                    package.maxPage = data.data.pages;//最大页数
                    if (data.data == null || data.data == "" || data.data == undefined) {
                        package.haveData = 1;
                    } else {
                        package.haveData = 0;
                    }
                }
            }, function (data) {
                layer.msg("shibai");
            });

        },
        //筛选地区
        selectArea: function (type, index) {
            //地区类型
            package.tabPolicyType = type;
            package.tabPolicyActive = index;
            var province = this.province;
            var city = this.city;
            var area = this.area;
            if (package.isRegion == 1) {
                province = '国家';
            }
            if (province == '省级') {
                province = '';
            }
            if (city == '市级') {
                city = '';
            }
            if (area == '区级') {
                area = '';
            }
            
            this.getRegion();
        },
        
    },
    created: function () {
        this.initPage();
    }
});


Vue.filter('moment', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});