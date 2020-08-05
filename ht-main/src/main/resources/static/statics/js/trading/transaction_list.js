/**
 * Created by ACER on 2019/8/19.
 */

var box = new Vue({
    el: '#box',
    data() {
        return {
            allkeyword: '',
            keyWords:'',
            words:'',
            choice: 0,
            choice_child: 0,

            //地区
            plevel: '',
            regions: 1,
            countrys: false,
            provinces: false,
            citys: false,
            areas: false,
            //地区选择
            isRegion: 0, //1-国家级  2-省级
            regionData: [], // 位置信息
            //复选框
            regions: 1,
            countrys: false,
            provinces: false,
            citys: false,
            areas: false,
            place1: '',
            place2: '',
            place3: '',
            place4: '',
            place5: '',
            declare: '',
            //位置
            province: '省级',
            isProvince: false,
            city: '市级',
            cityData: [],
            isCity: false,
            isCityActive: 0, //0-不active  1-active
            area: '区级',
            area1: '区级',
            areaData: [],
            areaData1: [],
            isArea: false,
            isArea1: false,
            isAreaActive: 0, //0-不active  1-active
            isAreaActive0: 0, //0-不active  1-active
            showArea: true,
            pitchCity: '',
            pitchArea: '',
            pitchDataRegion: '',
            areaShow: true,
            showBox: true,
            //选中条件
            pitchCity: '',
            pitchArea: '',
            pitchDataRegion: '',
            pitchIndustryField: '',
            pitchDataProType: '',
            pitchDataClaCation: '全部',
            pitchDataIssUnit: '',
            words: '',
            //分页
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            proResult:[],
            oneName: '',
            twoName: '',
            threeName: '',
            notfind: 0,
            havechiose:0,
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getregionData();
            this.getproductResult();
        },
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        //获取链接的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        stopThings: function () {
            if (this.isCityActive == 1) {
                this.isArea = true;
            }
        },
        toggleBox: function () {
            this.showBox = false;  //通过控制showBox来控制box1的显示与隐藏
        },
        // 2获取全国的省、市信息
        getregionData: function () {
            htajax.get('/statics/js/cityData.json',
                function (data) {
                    box.regionData = data;
                });
        },
        // 获取某个市的
        getCityData: function () {
            if (box.province != '省级') {
                for (var i = 0; i < box.regionData.length; i++) {
                    if (box.regionData[i].n == box.province) {
                        box.cityData = box.regionData[i].s;
                        box.isCity = true;
                    }
                }
            } else {
                box.cityData = [];
                box.isCity = false;
            }
        },
        choseCity: function (city) {
            box.city = city;
            box.isCity = false;
            if (box.isRegion == 2) {
                box.isCityActive = 1;
            }
            box.area = '区级';
            box.isAreaActive = 0;
            box.pitchArea = '';
            box.getAreaData();
        },

        getAreaData: function () {
            if (box.city != '市级') {
                for (var i = 0; i < box.cityData.length; i++) {
                    if (box.cityData[i].n == box.city) {
                        box.areaData = box.cityData[i].s;

                        if (box.areaData == undefined) {
                            box.showArea = false;
                        } else {
                            box.showArea = true;
                        }
                    }
                }
            } else {
                box.areaData = [];
            }
        },
        getPitchData: function (event) {
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
            box.getproductResult(1);
        },
        //接收地区
        getHeadCity: function (city) {
            if (city.indexOf('省') >= 0) {
                this.province = city;
                this.city = '市级';
                this.area = '区级';
                this.pitchDataRegion = city;
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
        getproductResult: function (index,c) {
            //搜索
            var urlkeyword = this.getUrlData("keyword");
            if(c == 1){
                var keyWords = this.keyWords;
            }else{
                var keyWords = urlkeyword;
                this.keyWords = urlkeyword;
            }
            this.words = keyWords;
            var cur = index;
            var plevel = this.plevel;
            var provice = this.province;
            var city = this.city;
            var area = this.area;
            
            if (provice == '省级') { provice = '' }
            if (city == '市级') { city = '' }
            if (area == '区级') { area = '' }
            if (cur == undefined) { cur = 1; }
            if (plevel == 5) { plevel = ''; }
            htajax.postFormDone("/ht-biz/product/findtypepro?t=" + new Date().getTime(), {
                flag: this.declare,
                productname:keyWords,
                plevel: plevel,
                provice: provice,
                city: city,
                area: area,
                size: 12,
                current: cur,
            }, function () {
                var index = layer.load(1, {
                    shade: [0.5, '#fff'] //0.1透明度的白色背景
                });
            }, function (data) {
                layer.closeAll();
                if (data.code == 10000) {
                    console.log(data);
                    if (data.reserveData.total == 0) { box.notfind = 1; }
                    if (data.reserveData.total != 0) { box.notfind = 0; }
                    box.proResult = data.data;
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
                box.getproductResult(index);
            }
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})