var package = new Vue({
    el: '#package',
    data: {
        keywords:'',
        ssss:'',
        cur:0,
        curTwo:0,
        choseCity:0,
        isRegion:1,
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

        //diqu
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

        regionData:[],
        pitchCity: '',
        pitchArea: '',
        pitchDataRegion: '',

        page: {
            currentPage: 1,
            totalPage: 1,
            totalResult: 0
        },
        sinpage:1,
        Polipage:1,
        repage:1,
        key:'',
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
            //this.getAreaData();
            this.getList();
            this.getPolicy();
            this.getRegion1();
            this.getregionData();
        },
        getRegion: function () {
            location.href = "/ht-biz/library/index?keyword="+package.keywords +'&curPolicy=' + 8;
        },
        lookMore:function(type){
            var gao1 = $(".point_one").height();
            var gao2 = $(".ziyuan").height();
            var gao3 = $(".select_con").height();
            if(type==1){ //单点资源包
                if(gao1 == 300){
                    $(".point_one").css({"height":"100%"});
                    $(".more>span").html("收起");
                }else{
                    $(".point_one").css({"height":"310px"});
                    $(".more>span").html("查看更多");
                }
            }
            if(type==2){
                if(gao2 == 270){
                    $(".ziyuan").css({"height":"100%"});
                    $(".more1>span").html("收起");
                }else{
                    $(".ziyuan").css({"height":"290px"});
                    $(".more1>span").html("查看更多");
                }
            }
            if(type==3){
                // this.getRegion()
                if(gao3 == 320){
                    $(".select_con").css({"height":"100%"});
                    $(".more2>span").html("收起");
                }else{
                    $(".select_con").css({"height":"330px"});
                    $(".more2>span").html("查看更多");
                }
            }
        },
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
                //this.getRegion();
            }else if(id == "country_list"){
                //this.getRegion();
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
        stopThings:function(){
            if(this.isCityActive ==1){
                this.isArea=true;
            }
        },
        // 2获取全国的省、市信息
        getregionData: function () {
            htajax.get('/statics/js/cityData.json',
                function (data) {
                    package.regionData = data;
                });
        },
        // 获取某个市的
        getCityData: function () {
            if (package.province != '省级') {
                for (var i = 0; i < package.regionData.length; i++) {
                    if (package.regionData[i].n == package.province) {
                        package.cityData = package.regionData[i].s;
                        package.isCity = true;
                    }
                }
            } else {
                package.cityData = [];
                package.isCity = false;
            }
        },
        IschoseCity: function (city) {
            package.city = city;
            package.isCity = false;
            if (package.isRegion == 2) {
                package.isCityActive = 1;
            }
            package.area = '区级';
            package.isAreaActive = 0;
            package.pitchArea = '';
            package.getAreaData();
        },

        getAreaData: function () {
            if (package.city != '市级') {
                for (var i = 0; i < package.cityData.length; i++) {
                    if (package.cityData[i].n == package.city) {
                        package.areaData = package.cityData[i].s;

                        if (package.areaData == undefined) {
                            package.showArea = false;
                        } else {
                            package.showArea = true;
                        }

                    }
                }
            } else {
                package.areaData = [];
            }
        },
        toggleBox:function(){
            this.showBox = false;  //通过控制showBox来控制box的显示与隐藏
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
            //package.getRegion();
        },
        selectAreaOne: function (area, areaActive) {
            if (package.area != area) {
                package.area = area;
                package.areaActive = areaActive;
            }
            $("#area_list").hide();
            //package.getRegion();
        },
        //单点政策资源包
        getList: function (pagesiez) {
            if(pagesiez){//分页
               var current = pagesiez;
            }else{
                var current = 1;
            }
            htajax.postForm("/ht-biz/resouce/listpage",{
                retype:1,
                size:50,
            }, function (data) {
                // console.log(data);
                if (data.code == 10000) {
                    package.items = data.data.records;
                    package.sinpage=package.sinpage +1;//下页数
                    // if(data.data.total<=pagesiez*4){
                    //     $(".more>span").text("收起");
                    // }
                }
            }, function (data) {
                //错误回调
            });
        },
        //行业政策资源包
        getPolicy:function(page){

            if(page){
               var  pagesize = page
            }else{
                var pagesize =1;
            }
            htajax.postForm("/ht-biz/resouce/listpage", {
                retype:2,
                size:50,
            },function (data) {
                // console.log(data);
                if (data.code == 10000) {
                    package.policy = data.data.records;
                    package.Polipage =package.Polipage+1;
                    // if(data.data.total<=2*pagesize){
                    //     layer.msg("已全部加载");
                    // }
                }
            }, function (data) {
                //错误回调
            });
        },

        getRegion1: function (index,type) {
            var  pagesiez = this.repage;
            var currentPage = index;
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
            if(currentPage == undefined){
                currentPage = 1;
            }
            var tabPolicyType = this.tabPolicyType;
            if(tabPolicyType == 0){
                tabPolicyType = '';
            }
            var url = "/beetl/rec/list?uploadType=3" + "&province=" + province0 + "&city=" + city0 + "&area=" + area0 + "&unit=" + tabPolicyType;

            var url1 = url + "&currentPage=" + currentPage +"&showCount=" + 8;
            // console.log(url1);

            htajax.postForm("/ht-biz/resouce/listpage", {
                retype:3,
                size:100,
                // province:province0,
                // city:city0,
                // area:area0,
                // department:this.tabPolicyType
            },function (data) {
                if (data.code == 10000) {
                    package.policyList = data.data.records;
                    package.repage = package.repage+1;
                    if(data.data.total<=4 *pagesiez){
                        layer.msg("已全部加载");
                    }
                }
            }, function (data) {
                //错误回调
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
            
            //this.getRegion();
        },
        // //分页
        // clickPage: function (index) {
        //     if (index != 0) {
        //         if (package.ssss == 0) {
        //             package.getRegion(index);
        //         } else {
        //             //box.getSubscribList(3,index);
        //         }
        //     }
        // },
    },
    created: function () {
        this.initPage();
    }
});


Vue.filter('moment', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});