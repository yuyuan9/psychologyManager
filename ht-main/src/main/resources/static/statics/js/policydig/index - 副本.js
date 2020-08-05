/**
 * Created by ACER on 2019/6/3.
 */

Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});
Vue.filter('date1', function (value, formatString) {
    formatString = formatString || 'YYYY年MM月DD日';
    return moment(value).format(formatString);
});
Vue.filter('date2', function (value, formatString) {
    formatString = formatString || 'MM月DD日';
    return moment(value).format(formatString);
});
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
    el: '#policy',
    data: function () {
        return {
            memory:'',
            targetData:'',
            returnurl: '', //点击登录、注册获取该页面url
            loginData: { //判断是否登录
                code: -1
            },
            ssss: 0,
            speechSpace: "", //设置地区
            isLogin: true,
            isEditInfo: false,
            // 查看更多
            isShow:false,
            // 性质类型列表
            claCationList: [{
                    id: '7',
                    'clacation': '全部'
                },
                {
                    id: '1',
                    'clacation': '申报通知'
                }, {
                    id: '2',
                    'clacation': '政府文件'
                }, {
                    id: '3',
                    'clacation': '公示名录'
                }, {
                    id: '4',
                    'clacation': '政策解读'
                }, {
                    id: '5',
                    'clacation': '新闻资讯'
                }, {
                    id: '6',
                    'clacation': '其他'
                },

            ],
            // 政策类型列表
            ProTypeList: [{
                    id: '9',
                    'ProType': '全部'
                },
                {
                    id: '1',
                    'ProType': '高新技术企业'
                }, {
                    id: '2',
                    'ProType': '研发机构'
                }, {
                    id: '3',
                    'ProType': '科研立项'
                }, {
                    id: '4',
                    'ProType': '科技成果'
                }, {
                    id: '5',
                    'ProType': '知识产权'
                }, {
                    id: '6',
                    'ProType': '科技财金'
                },
                {
                    id: '7',
                    'ProType': '人才团队'
                }, {
                    id: '8',
                    'ProType': '其他'
                },

            ],
            checkboxModel: [],
            checked: "",
            // 默认选中的状态
            fruitIds: [],
            fruidstring: '',
            fruitIds2: [],
            fruitIds3: [],
            fruitIds_Type: [],
            Sub_string: '',
            Sub_string2: '',
            // 修改订阅的列表
            SubscribeList: [],
            // 初始化按钮，默认为不选
            isCheckedAll: false,
            acitveRegion: false,
            acitveclaCation: false,
            acitveProType: false,
            acitveIssUnit: false,
            keyword: '', //关键字搜索
            //地区选择
            isRegion: 0, //1-国家级  2-省级
            //isRegion0: 0, //1-国家级  2-省级
            regionData: [], // 位置信息
            //regionData0: [], // 位置信息
            //复选框
            regions:1,
            countrys:false,
            provinces:false,
            citys:false,
            areas:false,
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
            //showArea0: true,
            //行业领域
            isIndustry: 0,
            isIndustryJson:[],
            //政策类型
            isProType: 0,
            //性质分类
            isclaCation: 0, // 默认选择导航栏
            apply: -1, //apply为0申报结束，apply为1申报中
            isApplyActive: -1, //0-申报结束active  1-申报中active
            //发布单位
            isIssUnit: 0,
            //发布日期
            isPubTime: '',
            isPubTime1: '',
            //选中条件
            pitchCity: '',
            //pitchCity0: '',
            pitchArea: '',
            //pitchArea0: '',
            pitchDataRegion: '',
            //pitchDataRegion0: '',
            pitchIndustryField:'',
            pitchDataProType: '',
            pitchDataClaCation: '全部',
            pitchDataIssUnit: '',
            pitchDataPubTime: '选择起始时间',
            pitchDataPubTime1: '选择截止时间',
            //左边显示政策速递
            contentDatas: [],
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            tomorrowDate: '',
            comList: [], //高企云社区
            deliveryList: [], //政策速递
            isLogin: true, //是否登录
            code: -1,
            loginInfo: {
                userInfo: "",
                code: 2,
                loginname: "",
                msgCount: 0, //消息
                honey: 0,
                headImg: "",
                showCount: '',
                szpdfdown: 0,
                itemCount: 0, //相关帖子
                titleRank: "",
                CollectionCount: '',
            },
            // 我的订阅传递的地址数据
            myregionlist1: [],
            myregionlist2: [],
            CityInfo: CityInfo, //地区数据
            form: {
                city: '', // 省
                erae: '', // 城市
                minerae: '', //区
                selectedOptions: [], //地区筛选数组
                selectedOptions2: [], //地区筛选数组
            },
            mysubList: [],
            Subprovicnce: '',
            Subcity: '',
            Subarea: '',
            collectCount: 0, //文档收藏
            downloadCount: 0,
            // 中关村选择与否
            collectRegion: '',
            // 登录信息
            loginpassword: '',
            loginphone: '',
            logincode: '',
            // 已订阅的回显问题
            checkedtest: '',
            checkedtest2: '',
            todayPushList: 0, //今日推送的数据
            idIndexclacation: '',
            areaShow:true,
            showBox:true,
        }
    },
    created: function () {
        this.initPage();
        
    },
    mounted: function () {
        $('#regDate').datepicker({
            format: 'yyyy-mm-dd'
        }).on('changeDate', function (ev) {
            box.pitchDataPubTime = $('#regDate').val();
            box.getContent(0);
        });
        $('#regDate1').datepicker({
            format: 'yyyy-mm-dd'
        }).on('changeDate', function (ev) {
            box.pitchDataPubTime1 = $('#regDate1').val();
            box.getContent(0);
        });
        var this1 = this;
        this1.showRegion();
    },
    methods: {
        initPage: function () {
            this.returnurl = encodeURIComponent(window.location.href);
            var date = new Date();
            var tomorrow = new Date(date.setHours(0, 0, 0, 0));
            this.tomorrowDate = Date.parse(tomorrow) + 86400000;
            this.getregionData();
            this.getDeliveryList();
            this.getIsIndustryJson();
            //搜索
            var urlId= this.getUrlData("keyword");
            if(urlId == undefined || urlId == null){
                urlId = '';
            }
            if(urlId.length != 0 && urlId != '#path'){
                this.keyword=urlId;
            }else{
                this.keyword='';
            }
        },
        getIsIndustryJson:function(){
            htajax.get('/statics/js/policydig/tecnology.json',
                function (data) {
                    box.isIndustryJson = data.techfield.content;
                    //console.log(data.techfield.content);
            });
           
        },
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)')
                .exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        stopThings:function(){
            if(this.isCityActive ==1){
                this.isArea=true;
            }
        },
        toggleBox:function(){
            this.showBox = false;  //通过控制showBox来控制box的显示与隐藏
        },
        handleChange: function (value) {
            this.form.city = this.form.selectedOptions[0];
            // alert(this.form.selectedOptions);
            this.form.erae = this.form.selectedOptions[1];
            this.form.minerae = this.form.selectedOptions[2];
        },
        callbackone: function () {
            if (box.natureone != null) {
                var backstring = box.natureone.split(',');
                var everyback;
                if (backstring.length == 6) {
                    this.clacationshow(7, box.claCationList[0].clacation);
                    // console.log('hello');
                } else {
                    for (var i = 0; i < backstring.length; i++) {
                        everyback = backstring[i];

                        switch (everyback) {
                            case '1':
                                this.clacationshow(1, box.claCationList[1].clacation);
                                break;
                            case '2':
                                this.clacationshow(2, box.claCationList[2].clacation);
                                break;
                            case '3':
                                this.clacationshow(3, box.claCationList[3].clacation);
                                break;
                            case '4':
                                this.clacationshow(4, box.claCationList[4].clacation);
                                break;
                            case '5':
                                this.clacationshow(5, box.claCationList[5].clacation);
                                break;
                            case '6':
                                this.clacationshow(6, box.claCationList[6].clacation);
                                break;
                        }
                    }
                }
            };
        },
        callbacktwo: function () {
            if (box.proTypeone != null) {
                var backnavtice = box.proTypeone.split(',');
                var everynavtice;
                for (var i = 0; i < backnavtice.length; i++) {
                    everynavtice = backnavtice[i];
                    if (backnavtice.length == 8) {
                        this.ProTypeshow(9, box.ProTypeList[0].ProType);
                    } else {
                        switch (everynavtice) {
                            case '1':
                                this.ProTypeshow(1, box.ProTypeList[1].ProType);
                                break;
                            case '2':
                                this.ProTypeshow(2, box.ProTypeList[2].ProType);
                                break;
                            case '3':
                                this.ProTypeshow(3, box.ProTypeList[3].ProType);
                                break;
                            case '4':
                                this.ProTypeshow(4, box.ProTypeList[4].ProType);
                                break;
                            case '5':
                                this.ProTypeshow(5, box.ProTypeList[5].ProType);
                                break;
                            case '6':
                                this.ProTypeshow(6, box.ProTypeList[6].ProType);
                                break;
                            case '7':
                                this.ProTypeshow(7, box.ProTypeList[7].ProType);
                                break;
                            case '8':
                                this.ProTypeshow(8, box.ProTypeList[8].ProType);
                                break;
                        }
                    }
                }

            };
        },
        checkedOne: function (fruitId, clacation) {
            $('.edit_claCation').find("div").each(function () {
                // console.log(fruitId);
                box.checkedtest = $(this).attr("data-data");
                if (fruitId == box.checkedtest) {
                    var xxc = $(this).attr('class');
                    let allchecked = $(this).attr("id"); //性质分类的全部
                    if (allchecked == 7) {
                        var otherborther = $(this).nextAll(); // 其他兄弟元素
                        $(otherborther).addClass('claCation_list').siblings().removeClass('activec123'); // 将其他兄弟消除
                        $(this).attr('class', 'activec123');
                    } else {
                        if (xxc == 'claCation_list') {
                            let firstone = $('.edit_claCation').find('div')[0]; // 获取第一个元素 即 全部
                            $(firstone).attr('class', 'claCation_list');
                            $(this).attr('class', 'activec123'); //选择
                        } else {
                            $(this).attr('class', 'claCation_list'); //取消
                        }
                    }
                }
            });
            this.fruitIds2.splice(0, this.fruitIds2.length);;
            $('.edit_claCation').find("div").each(function () {
                var xxc = $(this).attr('class');
                var clacation = $(this).attr('clacation');
                if (xxc == 'activec123') {
                    box.fruitIds2.push(clacation);
                }
            });
        },
        checkedOne2: function (Id, ProType) {
            $('.edit_ProType').find("div").each(function () {
                box.checkedtest2 = $(this).attr("data-data");
                // console.log(Id);

                let allProType = $(this).attr("id");
                if (Id == box.checkedtest2) {
                    var xxc = $(this).attr('class');
                    if (allProType == 9) {
                        let otherborther = $(this).nextAll(); // 其他兄弟节点元素
                        // console.log(otherborther)
                        $(otherborther).addClass('ProType_list').siblings().removeClass('activec124'); // 将其他兄弟消除
                        $(this).attr('class', 'activec124');
                    } else {
                        if (xxc == 'ProType_list') {
                            let firstone = $('.edit_ProType').find("div")[0]; // 获取第一个元素 即 全部
                            $(firstone).attr('class', 'ProType_list');
                            $(this).attr('class', 'activec124');
                        } else {
                            $(this).attr('class', 'ProType_list');
                        }
                    }
                }
            });
            this.fruitIds3.splice(0, this.fruitIds3.length);
            $('.edit_ProType').find("div").each(function () {
                var xxc = $(this).attr('class');
                var ProType = $(this).attr('ProType');
                if (xxc == 'activec124') {
                    box.fruitIds3.push(ProType);
                }
            });
        },

        checkShowButton: function (fruitId, check) {
            $('.edit_claCation').find("div").each(function () {
                box.checkedtest = $(this).attr("data-data");
                if (fruitId == box.checkedtest) {
                    if (check == true) {
                        $(this).attr('class', 'activec123');
                    } else {
                        $(this).attr('class', 'claCation_list');
                    }
                    if (check == false) {}
                }
                if(fruitId == 10){
                    let firstone = $('.edit_claCation').find("div")[0]; // 获取第一个元素 即 全部
                    $(firstone).attr('class', 'activec123');
                    let otherborther = $(this).nextAll(); // 其他兄弟元素
                     $(otherborther).addClass('claCation_list').siblings().removeClass('activec123'); // 将其他兄弟消除
                }
            });
        },

        backclacation: function () {
            if (box.natureone != null) {
                var backstring = box.natureone.split(',');
                //alert(backstring.length);
                var everyback;
                if (backstring.length == 6) {
                    box.checkShowButton(10, true);
                } else {
                    for (var i = 0; i < backstring.length; i++) {
                        everyback = backstring[i];
                        //alert("erverboack:"+everyback);

                        switch (everyback) {
                            case '1':
                                box.checkShowButton(1, true);
                                break;
                            case '2':
                                box.checkShowButton(2, true);
                                break;
                            case '3':
                                box.checkShowButton(3, true);
                                break;
                            case '4':
                                box.checkShowButton(4, true);
                                break;
                            case '5':
                                box.checkShowButton(5, true);
                                break;
                            case '6':
                                box.checkShowButton(6, true);
                                break;
                        }
                    }

                }

            };
        },
        checkShowButton2: function (Id, check) {
            $('.edit_ProType').find("div").each(function () {
                box.checkedtest = $(this).attr("data-data");
                if (Id == box.checkedtest) {
                    if (check == true) {
                        $(this).attr('class', 'activec124');
                    } else {
                        $(this).attr('class', 'claCation_list');
                    }
                    if (check == false) {
                        //box.checkedOne()
                    }
                } 
                if(Id == 15) {
                    let firstone = $('.edit_ProType').find("div")[0]; // 获取第一个元素 即 全部
                    $(firstone).attr('class', 'activec124');
                    let otherborther = $(this).nextAll(); // 其他兄弟节点元素
                        // console.log(otherborther)
                    $(otherborther).addClass('ProType_list').siblings().removeClass('activec124'); // 将其他兄弟消除
                }
            });

        },
        backProType: function () {
            if (box.proTypeone != null) {
                var backnavtice = box.proTypeone.split(',');
                var everynavtice;
                if (backnavtice.length == 8) {
                    this.checkShowButton2(15, true);
                } else {
                    for (var i = 0; i < backnavtice.length; i++) {
                        everynavtice = backnavtice[i];

                        switch (everynavtice) {
                            case '1':
                                this.checkShowButton2(1, true);
                                break;
                            case '2':
                                this.checkShowButton2(2, true);
                                break;
                            case '3':
                                this.checkShowButton2(3, true);
                                break;
                            case '4':
                                this.checkShowButton2(4, true);
                                break;
                            case '5':
                                this.checkShowButton2(5, true);
                                break;
                            case '6':
                                this.checkShowButton2(6, true);
                                break;
                            case '7':
                                this.checkShowButton2(7, true);
                                break;
                            case '8':
                                this.checkShowButton2(8, true);
                                break;
                        }
                    }
                }

            };
        },
        ProTypeshow: function (id, ProType) {
            if (ProType == '全部') {
                this.fruitIds3.splice(0, this.fruitIds3.length);
                this.fruitIds3.push('全部');
            } else {
                let temp = []; //一个新的临时数组
                temp.push(ProType);
                for (var i = 0; i < temp.length; i++) {
                    if (this.fruitIds3.indexOf(temp[i]) == -1) {
                        this.fruitIds3.push(temp[i]);
                    }
                }
            }
        },
        clacationshow: function (id, clacation) {
            if (clacation == "全部") {
                this.fruitIds2.splice(0, this.fruitIds2.length);
                this.fruitIds2.push('全部');
            } else {
                let temp = [];
                temp.push(clacation);
                for (let i = 0; i < temp.length; i++) {
                    if (this.fruitIds2.indexOf(temp[i]) == -1) {
                        this.fruitIds2.push(temp[i]);
                    }
                }
            }

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

        //选中条件
        getPitchData: function (event) {
            this.memory = 2;
            //地区
            if (this.isRegion == 1) {
                this.pitchDataRegion = '国家级';
            } else if (this.isRegion == 2) {
                this.pitchDataRegion = this.province;
                if (this.isCityActive == 1) {
                    this.pitchCity = this.city;
                    if (this.showArea == true && this.isAreaActive == 1) {
                        this.pitchArea = this.area;
                    }
                }
            }
            
             //技术领域
             if(this.isIndustry == 1347){
                this.pitchIndustryField = '电子信息';
             }else if(this.isIndustry == 1348){
                this.pitchIndustryField = '生物与新医药';
             }else if(this.isIndustry == 1349){
                this.pitchIndustryField = '航空航天';
             }else if(this.isIndustry == 1350){
                this.pitchIndustryField = '新材料';
             }else if(this.isIndustry == 1351){
                this.pitchIndustryField = '高技术服务';
             }else if(this.isIndustry == 1352){
                this.pitchIndustryField = '新能源与节能';
             }else if(this.isIndustry == 1353){
                this.pitchIndustryField = '资源与环境';
             }else if(this.isIndustry == 1354){
                this.pitchIndustryField = '先进制造与自动化';
             }else if(this.isIndustry == 1355){
                this.pitchIndustryField = '全部';
             }

            //政策分类
            switch (this.isProType) {
                case 1:
                    this.pitchDataProType = '高新技术企业';
                    break;
                case 2:
                    this.pitchDataProType = '研发机构';
                    break;
                case 3:
                    this.pitchDataProType = '科研立项';
                    break;
                case 4:
                    this.pitchDataProType = '科技成果';
                    break;
                case 5:
                    this.pitchDataProType = '知识产权';
                    break;
                case 6:
                    this.pitchDataProType = '科技财金';
                    break;
                case 7:
                    this.pitchDataProType = '人才团队';
                    break;
                case 8:
                    this.pitchDataProType = '其他';
                    break;
                case 9:
                    this.pitchDataProType = '全部';
                    break;
            }
            //性质分类 1:申报通知（指南）|2:政府文件（管理办法）|3:公示名录|4:政策解读|5:新闻资讯|6:其他
            switch (this.isclaCation) {
                case 0:
                    this.pitchDataClaCation = '全部';
                    this.ssss = 0;
                    break;
                case 1:
                    this.pitchDataClaCation = '申报通知';
                    this.ssss = 0;
                    break;
                case 2:
                    this.pitchDataClaCation = '政府文件';
                    this.ssss = 0;
                    break;
                case 3:
                    this.pitchDataClaCation = '公示名录';
                    this.ssss = 0;
                    break;
                case 4:
                    this.pitchDataClaCation = '政策解读';
                    this.ssss = 0;
                    break;
                case 5:
                    this.pitchDataClaCation = '新闻资讯';
                    this.ssss = 0;
                    break;
                case 6:
                    this.pitchDataClaCation = '其他';
                    this.ssss = 0;
                    break;
                case 7:
                    this.pitchDataClaCation = '我的订阅';
                    this.ssss = 1;
                    break;
            }
            //主管单位 1:人民政府|2:科技局|3:知识产权局|4:人社局|5:经信委|6:工信委|7:发改委|8:工商局|9:中小企业|10:其他|11:商务委|12:经济科技促进局|13:高新技术企业协会|14:中关村
            switch (this.isIssUnit) {
                case 1:
                    this.pitchDataIssUnit = '人民政府';
                    break;
                case 2:
                    this.pitchDataIssUnit = '科技局';
                    break;
                case 3:
                    this.pitchDataIssUnit = '知识产权局';
                    break;
                case 4:
                    this.pitchDataIssUnit = '人社局';
                    break;
                case 5:
                    this.pitchDataIssUnit = '经信委';
                    break;
                case 6:
                    this.pitchDataIssUnit = '工信委';
                    break;
                case 7:
                    this.pitchDataIssUnit = '发改委';
                    break;
                case 8:
                    this.pitchDataIssUnit = '工商局';
                    break;
                case 9:
                    this.pitchDataIssUnit = '中小企业';
                    break;
                case 10:
                    this.pitchDataIssUnit = '其他';
                    break;
                case 11:
                    this.pitchDataIssUnit = '商务委';
                    break;
                case 12:
                    this.pitchDataIssUnit = '经济科技促进局';
                    break;
                case 13:
                    this.pitchDataIssUnit = '高新技术企业协会';
                    break;
                case 14:
                    this.pitchDataIssUnit = '中关村';
                    break;
                case 15:
                    this.pitchDataIssUnit = '全部';
                    break;
            }
            this.getContent(0);

        },
        //左边显示政策速递  type=1--查看更多
        getContent: function (type, index) { 
            if(this.targetData != ""){
                var currentPage = this.targetData;
            }else{
                var currentPage = index;
            }
            var pagesized = 1;
            if (type == 1) {
                this.page.showCount += 15;
            } else if (type == 0) {
                this.page.showCount = 15;
            } else if (type == 3) {
                pagesized = index;
            }
            var province0 = this.province;
            var city0 = this.city;
            var area0 = this.area;
            
            if (this.isRegion == 1) {
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

            //复选框条件 北京市天津市上海市重庆市
            
            if (province0.indexOf("北京市") >=0){
                this.areaShow = false;
            }
            else if (province0.indexOf("天津市") >= 0){
                this.areaShow = false;
            }
            else if (province0.indexOf("上海市") >= 0){
                this.areaShow = false;
            }
            else if (province0.indexOf("重庆市") >= 0){
                this.areaShow = false;
            }else{
                this.areaShow = true;
            }

            var country1 = this.countrys;
            var province1 = this.provinces;
            var city1 = this.citys;
            var area1 = this.areas;
            if(!country1 && !province1 && !city1 && !area1){
                this.regions = 1;
            }else{
                this.regions = 0;
            }
            if (country1){
                country1 = "国家";
            }else{
                country1 = "";
            }
            if (province1){
                province1 = "省";
            }else{
                province1 = "";
            }
            if (city1){
                city1 = "市";
            }else{
                city1 = "";
            }
            if (area1){
                area1 = "区";
            }else{
                area1 = "";
            }

            var isIndustry = this.isIndustry;
            var isProType = this.isProType;
            var isIssUnit = this.isIssUnit;
            var isclaCation = this.isclaCation;
            if (isIndustry == 1355) {
                isIndustry = '';
            }
            if (isProType == 0) {
                isProType = '';
            }
            if (isclaCation == 0) {
                isclaCation = '';
            }
            if (isIssUnit == 0) {
                isIssUnit = '';
            }
            
            if (isIndustry == 9) {
                isIndustry = '';
            }
            if (isProType == 9) {
                isProType = '';
            }
            if (isIssUnit == 15) {
                isIssUnit = '';
            }

            //发布日期
            if (this.pitchDataPubTime == "选择起始时间") {
                this.isPubTime = '';
            } else {
                this.isPubTime = this.pitchDataPubTime;
            }
            if (this.pitchDataPubTime1 == "选择截止时间") {
                this.isPubTime1 = '';
            } else {
                this.isPubTime1 = this.pitchDataPubTime1;
            }
            if(this.keyword == undefined){
                this.keyword = '';
            }
            layer.load(2,{
                offset: ['65%', "40%"],
                shade: false
            });
            htajax.postForm('/ht-biz/policydig/list',{
                keyword:this.keyword,//关键字查询
                province:province0,//选择省级
                city:city0,//选择市级
                area:area0,//选择区级
                regions:this.regions,//复选框选择状态（0表示用户点击页面省市区复选框，1表示没有点击）
                countrys:country1,//复选框国家
                provinces:province1,//复选框省级
                citys:city1,//复选框市级
                areas:area1,//复选框区级
                datetime1:this.isPubTime,//开始时间
                datetime2:this.isPubTime1,//结束时间
                nature:isclaCation,//性质分类
                productType:isProType,//政策类型
                issueCompany:isIssUnit,//发布单位
                tecnology:isIndustry,//技术领域
                apply:this.apply,//申报通知
                browsecount:1,//决定数据排序
                current:15,//currentPage,//分页
                memory:1,//this.memory,//记忆初始值为1 其他2
            }, function (data) {
                // console.log(data);
                layer.closeAll();
                if (data.code == 10000) {
                    box.contentDatas = data.data.records;
                    box.page.currentPage = data.data.current;
                    box.page.totalPage = data.data.pages;
                    box.page.totalResult = data.data.total;
                }
            }, function (data) {
                //错误回调
            });
            this.targetData = '';
        },

        //回显上次地区
        showRegion: function () {
            layer.load(2,{
                offset: ['65%', "40%"],
                shade: false
            });
            htajax.postForm('/ht-biz/policydig/list',{
                keyword:this.keyword,//关键字查询
                memory:1,//初始化值 点击之后为0或空
            }, function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    layer.closeAll();
                    box.memory = data.data.pd.memory;
                    var tips = data.data.pd.province;
                    if(tips == undefined){
                        box.isRegion = " 国家级";
                        box.province = "省级";
                    }
                    else if(tips == ''){
                        box.isRegion = " 国家级";
                        box.province = "省级";
                    }
                    else if(tips.indexOf("国家")>=0){
                        box.isRegion = data.data.pd.province;
                        box.province = "省级"
                    }
                    else if(tips.indexOf("省")>=0){
                        box.isRegion = 2;
                        box.isCityActive = 1;
                        box.isAreaActive = 1;
                        box.province = data.data.pd.province;
                    }else if(tips.indexOf("市")>=0){
                        box.isRegion = 2;
                        box.isCityActive = 1;
                        box.province = data.data.pd.province;
                    }

                    box.city = data.data.pd.city;
                    box.area = data.data.pd.area;

                    box.contentDatas = data.data.records;
                    box.page.currentPage = data.data.current;
                    box.page.totalPage = data.data.pages;
                    box.page.totalResult = data.data.total;

                    box.pitchDataRegion = data.data.pd.province;
                    box.pitchCity = data.data.pd.city;
                    box.pitchArea = data.data.pd.area;

                    if (box.province == undefined || box.province == "" ) {
                        box.province = '省级';
                    }
                    if (box.city == undefined || box.city == "") {
                        box.city = '市级';
                    }
                    if (box.area == undefined || box.area == "") {
                        box.area = '区级';
                    }

                    if (box.pitchDataRegion == undefined || box.pitchDataRegion == "") {
                        box.pitchDataRegion = '';
                    }
                    if (box.pitchCity == undefined || box.pitchCity == "") {
                        box.pitchCity = '';
                    }
                    if (box.pitchArea == undefined || box.pitchArea == "") {
                        box.pitchArea = '';
                    }
                }
            }, function (data) {
                //错误回调
            });
        },
        //政策速递，推荐阅读
        getDeliveryList: function () {
            htajax.postForm("/ht-biz/policydig/list",{
                browsecount:5,
            }, function (data) {
                if (data.code == 10000) {
                    box.deliveryList = data.data.records;
                }
            }, function (data) {
                //错误回调
            });
        },
        
        uploadfile: function (hiddenname) { //上传文件
            layer.open({
                type: 2,
                area: ['700px', '450px'],
                fix: false,
                maxmin: true,
                content: '/upload_file/view?callback=' + hiddenname + '&path=true&multiple=false&callclick=true&showspan=false&t=' + new Date().getTime()
            });
        },

        // 点击编辑按钮触发的事件
        edit_show: function () {
            $('.edit_Mask').show();
            box.backclacation();
            box.backProType();
        },
       
        //分页
        clickPage: function (index) {
            if (index != 0) {
                // box.page.currentPage = index;
                if (box.ssss == 0) {
                    box.getContent(3, index);
                } else {
                    //  box.page.currentPage = index;
                    box.getsubinfo(3, index);
                }
            }
        },
        browse_more1:function(){
            var height = $(".jishu").height();
            if(height <= 19){
                $(".jishu").animate({
                    "height":"55px"
                },500);
                $(".more_icon1").css({
                    "transform":"rotate(-90deg)",
                },200)
            }else{
                $(".jishu").animate({
                    "height":"19px"
                },500);
                $(".more_icon1").css({
                    "transform":"rotate(0deg)",
                },200)
            }
        },
        browse_more2:function(){
            var height = $(".issue_unit").height();
            if(height <= 19){
                $(".issue_unit").animate({
                    "height":"55px"
                },500);
                $(".more_icon2").css({
                    "transform":"rotate(-90deg)",
                },200)
            }else{
                $(".issue_unit").animate({
                    "height":"19px"
                },500);
                $(".more_icon2").css({
                    "transform":"rotate(0deg)",
                },200)
            }
            
        },
        
        // 查看是否订阅
        mySubscrib: function () {
            if (box.loginInfo.code != 0) {
                $('.login-top').show();
            } else {
                htajax.get('/websubscribe/findByWebSubscribeCreateId', function (data) {
                    if (data.code == 0) {
                        box.proTypeone = data.data.productType; //政策
                        box.natureone = data.data.nature; // 性质
                        box.Subprovicnce = data.data.province;
                        box.Subcity = data.data.city;
                        box.Subarea = data.data.area;
                        box.getpushlist();
                        box.getsubinfo();
                        box.isclaCation = 7;
                        box.callbackone();
                        box.callbacktwo();
                    }
                })
            }
        },
        edit_close: function () {
            $('.edit_Mask').hide();
            // box.mySubscrib();
        },
        doEdit: function () {
            var nickname = $("input[name='nickname']").val();
            var headImg = $("#headImg").val();
            if (nickname == "" || nickname == null) {
                layer.msg("请填写昵称");
                return;
            }
            if (headImg != null && headImg != "") {
                box.uploadHeadImg();
            }
            htajax.postForm('/beetl/mycenter/editnikeName.do', {
                    nikeName: nickname
                },
                function (data) {
                    if (data.code == 0) {
                        layer.msg("信息修改成功");
                        box.isEditInfo = false;
                        box.checkLogin();
                        user.doPage();
                    }
                },
                function (data) {
                    layer.msg(data.msg);
                    // 错误回调
                });
        },
        // 点击编辑按钮触发的事件
        edit_show: function () {
            $('.edit_Mask').show();
            box.backclacation();
            box.backProType();
        },
        // 提交修改订阅信息
        edit_submit: function () {
            var nature = "";
            let obj = $('.edit_claCation').find("div")[0];
            let allclaCation = $(obj).attr("class"); //性质分类的全部
            // var xxc = $(obj).attr('class');
            if (allclaCation == 'activec123') {
                nature = '1,2,3,4,5,6';
                // claCation_list
            } else {
                $("div[class='activec123']").each(function (j, item3) {
                    if (j == 0) {
                        nature = item3.id
                    } else {
                        nature = nature + "," + item3.id
                    }
                });
            }

            var productType = "";
            let objProType = $('.edit_ProType').find("div")[0];
            let allobjProType = $(objProType).attr("class"); //性质分类的全部
            if (allobjProType == 'activec124') {
                productType = '1,2,3,4,5,6,7,8';
            } else {
                $("div[class='activec124']").each(function (j, item) {
                    if (j == 0) {
                        productType = item.id
                    } else {
                        productType = productType + "," + item.id
                    }
                });
            }


            var provincename = $("#provincename").text().trim();
            var cityname = $("#cityname").text().trim();
            var areaname = $("#areaname").text().trim();
            htajax.postForm('/websubscribe/savaAndeidtBySuscribe', {
                    nature: nature,
                    productType: productType,
                    province: provincename,
                    city: cityname,
                    area: areaname
                },
                function (data) {
                    if (data.code == 0) {
                        // box.SubscribeList = data.data;
                        box.contentDatas = data.data;
                        box.page.currentPage = data.reserveData.currentPage;
                        box.page.totalPage = data.reserveData.totalPage;
                        box.page.totalResult = data.reserveData.totalResult;
                        $('.edit_Mask').hide();
                        box.mySubscrib();
                        box.getpushlist();
                    }
                },
                function (data) {
                    location.reload();
                })
        },
    },
});
