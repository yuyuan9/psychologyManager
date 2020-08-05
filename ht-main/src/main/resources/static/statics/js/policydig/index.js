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

var box1 = new Vue({
    el: '#policy',
    data: function () {
        return {
            memory:'',
            targetData:'',
            returnurl: '', //点击登录、注册获取该页面url
            loginData: { //判断是否登录
                code: -1
            },
            todayDate:'',
            subscription: 0,
            speechSpace: "", //设置地区
            isLogin: true,
            isEditInfo: false,
            // 查看更多
            isShow:false,
            //主管单位
            organizationList:[],
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
            //技术领域
            technologyField:[{
                    id:'1355',
                    'technology':'全部'
                },
                {
                    id:'1347',
                    'technology':'电子信息'
                },
                {
                    id:'1348',
                    'technology':'生物与新医药'
                },
                {
                    id:'1349',
                    'technology':'航空航天'
                },
                {
                    id:'1350',
                    'technology':'新材料'
                },
                {
                    id:'1351',
                    'technology':'高技术服务'
                },
                {
                    id:'1352',
                    'technology':'新能源与节能'
                },
                {
                    id:'1353',
                    'technology':'资源与环境'
                },
                {
                    id:'1354',
                    'technology':'先进制造与自动化'
                },
                    
            ],
            checkboxModel: [],
            checked: "",
            // 默认选中的状态
            fruitIds: [],
            fruidstring: '',
            fruitIds2: [],
            fruitIds3: [],
            fruitIds4: [],
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
            isIndustry: '',
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
            pitchDataRegion: '浙江省',
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
            checkedtest3: '',
            todayPushList: 0, //今日推送的数据
            idIndexclacation: '',
            areaShow:true,
            showBox:true,
            tecnology:'',
            regionone:'',
            dyid:'',
            dycreateid:'',
            dyvip:'',
            dycurrentPage:'',
            showList:5,

            bigData:'',
            setProject:'',
            words:'',
            notfind:0,
            conceal:0,
        }
    },
    created: function () {
        this.initPage();
        
    },
    mounted: function () {
        $('#regDate').datepicker({
            format: 'yyyy-mm-dd'
        }).on('changeDate', function (ev) {
            box1.pitchDataPubTime = $('#regDate').val();
            box1.getContent(0);
        });
        $('#regDate1').datepicker({
            format: 'yyyy-mm-dd'
        }).on('changeDate', function (ev) {
            box1.pitchDataPubTime1 = $('#regDate1').val();
            box1.getContent(0);
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
            this.getCompetent();

          
        },
        showListChange:function(){
            this.showList = 10;
        },
        //搜索
        searchBigData:function(){
            if(vm.code == 10000){
                window.open("/ht-biz/inquire/policy_details?keyword1="+this.bigData + "&theme=" + 1);
            }else{
                $(".loginBox").show();
            }
           
        },
        searchProject:function(){
            if(vm.code == 10000){
                window.open("/ht-biz/inquire/project_details?keyword2="+this.setProject + "&theme=" + 2);
            }else{
                $(".loginBox").show();
            }
            
        },
        getIsIndustryJson:function(){
            htajax.get('/statics/js/tecnology.json',
                function (data) {
                    box1.isIndustryJson = data.techfield.content;
                    //console.log(data.techfield.content);
            });
           
        },
        getCompetent:function(){
            var province0 = this.province;
            var city0 = this.city;
            var area0 = this.area;
            if (this.isRegion == 1) {
                province0 = '国家';
            }
            if (province0 == '国家') {
                province0 = '国家';
            }
            if (province0 == '省级') {
                province0 = '省';
            }
            if (city0 == '市级') {
                city0 = '市';
            }
            if (area0 == '区级') {
                area0 = '区';
            } 
            var region = province0 + ',' + city0 + ','  + area0;
            htajax.postForm('/ht-biz/unit/list',{
                //region:region
            },function(data){
                if(data.code == 10000){
                    //console.log(data);
                    box1.organizationList =data.data;
                }else{
                    layer.msg(data.msg);
                }
            },function(data){
                //erreo
                layer.msg(data.msg);
            })
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
            this.showBox = false;  //通过控制showBox来控制box1的显示与隐藏
        },
        handleChange: function (value) {
            this.form.city = this.form.selectedOptions[0];
            // alert(this.form.selectedOptions);
            this.form.erae = this.form.selectedOptions[1];
            this.form.minerae = this.form.selectedOptions[2];
        },
        callbackone: function () {
            if (box1.natureone != null) {
                var backstring = box1.natureone.split(',');
                var everyback;
                if (backstring.length == 6) {
                    this.clacationshow(7, box1.claCationList[0].clacation);
                    //console.log('hello');
                } else {
                    for (var i = 0; i < backstring.length; i++) {
                        everyback = backstring[i];

                        switch (everyback) {
                            case '1':
                                this.clacationshow(1, box1.claCationList[1].clacation);
                                break;
                            case '2':
                                this.clacationshow(2, box1.claCationList[2].clacation);
                                break;
                            case '3':
                                this.clacationshow(3, box1.claCationList[3].clacation);
                                break;
                            case '4':
                                this.clacationshow(4, box1.claCationList[4].clacation);
                                break;
                            case '5':
                                this.clacationshow(5, box1.claCationList[5].clacation);
                                break;
                            case '6':
                                this.clacationshow(6, box1.claCationList[6].clacation);
                                break;
                        }
                    }
                }
            };
        },
        callbacktwo: function () {
            if (box1.proTypeone != null) {
                //console.log(box1.proTypeone);
                var backnavtice = box1.proTypeone.split(',');
                var everynavtice;
                for (var i = 0; i < backnavtice.length; i++) {
                    everynavtice = backnavtice[i];
                    if (backnavtice.length == 8) {
                        this.ProTypeshow(9, box1.ProTypeList[0].ProType);
                    } else {
                        switch (everynavtice) {
                            case '1':
                                this.ProTypeshow(1, box1.ProTypeList[1].ProType);
                                break;
                            case '2':
                                this.ProTypeshow(2, box1.ProTypeList[2].ProType);
                                break;
                            case '3':
                                this.ProTypeshow(3, box1.ProTypeList[3].ProType);
                                break;
                            case '4':
                                this.ProTypeshow(4, box1.ProTypeList[4].ProType);
                                break;
                            case '5':
                                this.ProTypeshow(5, box1.ProTypeList[5].ProType);
                                break;
                            case '6':
                                this.ProTypeshow(6, box1.ProTypeList[6].ProType);
                                break;
                            case '7':
                                this.ProTypeshow(7, box1.ProTypeList[7].ProType);
                                break;
                            case '8':
                                this.ProTypeshow(8, box1.ProTypeList[8].ProType);
                                break;
                        }
                    }
                }

            };
        },
        callbackthree: function () {
            if (box1.tecnology != null) {
                //console.log(box1.tecnology);
                var backnavtice = box1.tecnology.split(',');
                var everynavtice;
                for (var i = 0; i < backnavtice.length; i++) {
                    everynavtice = backnavtice[i];
                    if (backnavtice.length == 9) {
                        this.ProTypeshow1(9, box1.technologyField[0].technology);
                    } else {
                        switch (everynavtice) {
                            case '1347':
                                this.ProTypeshow1(1, box1.technologyField[1].technology);
                                $("#1347").addClass("activec125").removeClass("technology_list");
                                break;
                            case '1348':
                                this.ProTypeshow1(2, box1.technologyField[2].technology);
                                $("#1348").addClass("activec125").removeClass("technology_list");
                                break;
                            case '1349':
                                this.ProTypeshow1(3, box1.technologyField[3].technology);
                                $("#1349").addClass("activec125").removeClass("technology_list");
                                break;
                            case '1350':
                                this.ProTypeshow1(4, box1.technologyField[4].technology);
                                $("#1350").addClass("activec125").removeClass("technology_list");
                                break;
                            case '1351':
                                this.ProTypeshow1(5, box1.technologyField[5].technology);
                                $("#1351").addClass("activec125").removeClass("technology_list");
                                break;
                            case '1352':
                                this.ProTypeshow1(6, box1.technologyField[6].technology);
                                $("#1352").addClass("activec125").removeClass("technology_list");
                                break;
                            case '1353':
                                this.ProTypeshow1(7, box1.technologyField[7].technology);
                                $("#1353").addClass("activec125").removeClass("technology_list");
                                break;
                            case '1354':
                                this.ProTypeshow1(8, box1.technologyField[8].technology);
                                $("#1354").addClass("activec125").removeClass("technology_list");
                                break;
                            case '1355':
                                this.ProTypeshow1(9, box1.technologyField[8].technology);
                                $("#1355").addClass("activec125").removeClass("technology_list");
                                break;
                        }
                    }
                }

            };
        },
        checkedOne: function (fruitId, clacation) {
            $('.edit_claCation').find("div").each(function () {
                // console.log(fruitId);
                box1.checkedtest = $(this).attr("data-data");
                if (fruitId == box1.checkedtest) {
                    var xxc = $(this).attr('class');
                    var allchecked = $(this).attr("id"); //性质分类的全部
                    if (allchecked == 7) {
                        var otherborther = $(this).nextAll(); // 其他兄弟元素
                        $(otherborther).addClass('claCation_list').siblings().removeClass('activec123'); // 将其他兄弟消除
                        $(this).attr('class', 'activec123');
                    } else {
                        if (xxc == 'claCation_list') {
                            var firstone = $('.edit_claCation').find('div')[0]; // 获取第一个元素 即 全部
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
                    box1.fruitIds2.push(clacation);
                }
            });
        },
        checkedOne2: function (Id, ProType) {
            $('.edit_ProType').find("div").each(function () {
                box1.checkedtest2 = $(this).attr("data-data");
                // console.log(Id);

                var allProType = $(this).attr("id");
                if (Id == box1.checkedtest2) {
                    var xxc = $(this).attr('class');
                    if (allProType == 9) {
                        var otherborther = $(this).nextAll(); // 其他兄弟节点元素
                        // console.log(otherborther)
                        $(otherborther).addClass('ProType_list').siblings().removeClass('activec124'); // 将其他兄弟消除
                        $(this).attr('class', 'activec124');
                    } else {
                        if (xxc == 'ProType_list') {
                            var firstone = $('.edit_ProType').find("div")[0]; // 获取第一个元素 即 全部
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
                    box1.fruitIds3.push(ProType);
                }
            });
        },
        checkedOne3: function (Id, ProType) {
            $('.edit_technology').find("div").each(function () {
                box1.checkedtest3 = $(this).attr("data-data");
                // console.log(Id);

                var allProType = $(this).attr("id");
                if (Id == box1.checkedtest3) {
                    var xxc = $(this).attr('class');
                    if (allProType == 1355) {
                        var otherborther = $(this).nextAll(); // 其他兄弟节点元素
                        // console.log(otherborther)
                        $(otherborther).addClass('technology_list').siblings().removeClass('activec125'); // 将其他兄弟消除
                        $(this).attr('class', 'activec125');
                    } else {
                        if (xxc == 'technology_list') {
                            var firstone = $('.edit_technology').find("div")[0]; // 获取第一个元素 即 全部
                            $(firstone).attr('class', 'technology_list');
                            $(this).attr('class', 'activec125');
                        } else {
                            $(this).attr('class', 'technology_list');
                        }
                    }
                }
            });
            this.fruitIds4.splice(0, this.fruitIds4.length);
            $('.edit_technology').find("div").each(function () {
                var xxc = $(this).attr('class');
                var ProType = $(this).attr('ProType');
                if (xxc == 'activec125') {
                    box1.fruitIds4.push(ProType);
                }
            });
        },

        checkShowButton: function (fruitId, check) {
            $('.edit_claCation').find("div").each(function () {
                box1.checkedtest = $(this).attr("data-data");
                if (fruitId == box1.checkedtest) {
                    if (check == true) {
                        $(this).attr('class', 'activec123');
                    } else {
                        $(this).attr('class', 'claCation_list');
                    }
                    if (check == false) {}
                }
                if(fruitId == 10){
                    var firstone = $('.edit_claCation').find("div")[0]; // 获取第一个元素 即 全部
                    $(firstone).attr('class', 'activec123');
                    var otherborther = $(this).nextAll(); // 其他兄弟元素
                     $(otherborther).addClass('claCation_list').siblings().removeClass('activec123'); // 将其他兄弟消除
                }
            });
        },

        backclacation: function () {
            if (box1.natureone != null) {
                var backstring = box1.natureone.split(',');
                //alert(backstring.length);
                var everyback;
                if (backstring.length == 6) {
                    box1.checkShowButton(10, true);
                } else {
                    for (var i = 0; i < backstring.length; i++) {
                        everyback = backstring[i];
                        //alert("erverboack:"+everyback);

                        switch (everyback) {
                            case '1':
                                box1.checkShowButton(1, true);
                                break;
                            case '2':
                                box1.checkShowButton(2, true);
                                break;
                            case '3':
                                box1.checkShowButton(3, true);
                                break;
                            case '4':
                                box1.checkShowButton(4, true);
                                break;
                            case '5':
                                box1.checkShowButton(5, true);
                                break;
                            case '6':
                                box1.checkShowButton(6, true);
                                break;
                        }
                    }

                }

            };
        },

        checkShowButton2: function (Id, check) {
            $('.edit_ProType').find("div").each(function () {
                box1.checkedtest = $(this).attr("data-data");
                if (Id == box1.checkedtest) {
                    if (check == true) {
                        $(this).attr('class', 'activec124');
                    } else {
                        $(this).attr('class', 'claCation_list');
                    }
                    if (check == false) {
                        //box1.checkedOne()
                    }
                } 
                if(Id == 15) {
                    var firstone = $('.edit_ProType').find("div")[0]; // 获取第一个元素 即 全部
                    $(firstone).attr('class', 'activec124');
                    var otherborther = $(this).nextAll(); // 其他兄弟节点元素
                        // console.log(otherborther)
                    $(otherborther).addClass('ProType_list').siblings().removeClass('activec124'); // 将其他兄弟消除
                }
            });

        },
        backProType: function () {
            if (box1.proTypeone != null) {
                var backnavtice = box1.proTypeone.split(',');
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

        checkShowButton3: function (Id, check) {
            $('.edit_technology').find("div").each(function () {
                box1.checkedtest = $(this).attr("data-data");
                if (Id == box1.checkedtest) {
                    if (check == true) {
                        $(this).attr('class', 'activec125');
                    } else {
                        $(this).attr('class', 'technology_list');
                    }
                    if (check == false) {
                        //box1.checkedOne()
                    }
                } 
                if(Id == 15) {
                    var firstone = $('.edit_technology').find("div")[0]; // 获取第一个元素 即 全部
                    $(firstone).attr('class', 'activec125');
                    var otherborther = $(this).nextAll(); // 其他兄弟节点元素
                        // console.log(otherborther)
                    $(otherborther).addClass('technology_list').siblings().removeClass('activec125'); // 将其他兄弟消除
                }
            });

        },
        backTechnology: function () {
            if (box1.proTypeone != null) {
                var backnavtice = box1.proTypeone.split(',');
                var everynavtice;
                if (backnavtice.length == 8) {
                    this.checkShowButton3(15, true);
                } else {
                    for (var i = 0; i < backnavtice.length; i++) {
                        everynavtice = backnavtice[i];

                        switch (everynavtice) {
                            case '1':
                                this.checkShowButton3(1, true);
                                break;
                            case '2':
                                this.checkShowButton3(2, true);
                                break;
                            case '3':
                                this.checkShowButton3(3, true);
                                break;
                            case '4':
                                this.checkShowButton3(4, true);
                                break;
                            case '5':
                                this.checkShowButton3(5, true);
                                break;
                            case '6':
                                this.checkShowButton3(6, true);
                                break;
                            case '7':
                                this.checkShowButton3(7, true);
                                break;
                            case '8':
                                this.checkShowButton3(8, true);
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
                var temp = []; //一个新的临时数组
                temp.push(ProType);
                for (var i = 0; i < temp.length; i++) {
                    if (this.fruitIds3.indexOf(temp[i]) == -1) {
                        this.fruitIds3.push(temp[i]);
                    }
                }
            }
        },
        ProTypeshow1: function (id, technology) {
            if (technology == '全部') {
                this.fruitIds4.splice(0, this.fruitIds4.length);
                this.fruitIds4.push('全部');
            } else {
                var temp = []; //一个新的临时数组
                temp.push(technology);
                for (var i = 0; i < temp.length; i++) {
                    if (this.fruitIds4.indexOf(temp[i]) == -1) {
                        this.fruitIds4.push(temp[i]);
                    }
                }
            }
        },
        clacationshow: function (id, clacation) {
            if (clacation == "全部") {
                this.fruitIds2.splice(0, this.fruitIds2.length);
                this.fruitIds2.push('全部');
            } else {
                var temp = [];
                temp.push(clacation);
                for (var i = 0; i < temp.length; i++) {
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
                    box1.regionData = data;
                });
        },
        // 获取某个市的
        getCityData: function () {
            if (box1.province != '省级') {
                for (var i = 0; i < box1.regionData.length; i++) {
                    if (box1.regionData[i].n == box1.province) {
                        box1.cityData = box1.regionData[i].s;
                        box1.isCity = true;
                    }
                }
            } else {
                box1.cityData = [];
                box1.isCity = false;
            }
        },
        choseCity: function (city) {
            box1.city = city;
            box1.isCity = false;
            if (box1.isRegion == 2) {
                box1.isCityActive = 1;
            }
            box1.area = '区级';
            box1.isAreaActive = 0;
            box1.pitchArea = '';
            box1.getAreaData();
        },

        getAreaData: function () {
            if (box1.city != '市级') {
                for (var i = 0; i < box1.cityData.length; i++) {
                    if (box1.cityData[i].n == box1.city) {
                        box1.areaData = box1.cityData[i].s;

                        if (box1.areaData == undefined) {
                            box1.showArea = false;
                        } else {
                            box1.showArea = true;
                        }

                    }
                }
            } else {
                box1.areaData = [];
            }
        },

        //选中条件
        getPitchData: function (event) {
            this.memory = 2;
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
                    this.subscription = 0;
                    break;
                case 1:
                    this.pitchDataClaCation = '申报通知';
                    this.subscription = 0;
                    break;
                case 2:
                    this.pitchDataClaCation = '政府文件';
                    this.subscription = 0;
                    break;
                case 3:
                    this.pitchDataClaCation = '公示名录';
                    this.subscription = 0;
                    break;
                case 4:
                    this.pitchDataClaCation = '政策解读';
                    this.subscription = 0;
                    break;
                case 5:
                    this.pitchDataClaCation = '新闻资讯';
                    this.subscription = 0;
                    break;
                case 6:
                    this.pitchDataClaCation = '其他';
                    this.subscription = 0;
                    break;
                case 7:
                    this.pitchDataClaCation = '我的订阅';
                    this.subscription = 1;
                    break;
            }
            //主管单位  其他 国家发改委 国家科技部 国家人社部 国家知识产权局 国家工信部 省人民政府 其他 省工信厅 省人社厅 知识产权局 省科技厅 省发改委 市人社局 市科技局 市发改委 市知识产权局 其他 15市工信局 市人民政府 其他 区科工信局 区发改局 区知识产权局 区人民政府 区人社局
            switch (this.isIssUnit) {
                case 1:
                    this.pitchDataIssUnit = '其他';
                    break;
                case 2:
                    this.pitchDataIssUnit = '国家发改委';
                    break;
                case 3:
                    this.pitchDataIssUnit = '国家科技部';
                    break;
                case 4:
                    this.pitchDataIssUnit = '国家人社部';
                    break;
                case 5:
                    this.pitchDataIssUnit = '国家知识产权局';
                    break;
                case 6:
                    this.pitchDataIssUnit = '国家工信部';
                    break;
                case 7:
                    this.pitchDataIssUnit = '省人民政府';
                    break;
                case 8:
                    this.pitchDataIssUnit = '其他';
                    break;
                case 9:
                    this.pitchDataIssUnit = '省工信厅';
                    break;
                case 10:
                    this.pitchDataIssUnit = '省人社厅';
                    break;
                case 11:
                    this.pitchDataIssUnit = '知识产权局';
                    break;
                case 12:
                    this.pitchDataIssUnit = '省科技厅';
                    break;
                case 13:
                    this.pitchDataIssUnit = '省发改委';
                    break;
                case 14:
                    this.pitchDataIssUnit = '市人社局';
                    break;
                case 15:
                    this.pitchDataIssUnit = '市科技局';
                    break;
                case 16:
                    this.pitchDataIssUnit = '市发改委';
                    break;
                case 17:
                    this.pitchDataIssUnit = '市知识产权局';
                    break;
                case 18:
                    this.pitchDataIssUnit = '其他';
                    break;
                case 19:
                    this.pitchDataIssUnit = '市工信局';
                    break;
                case 20:
                    this.pitchDataIssUnit = '市人民政府';
                    break;
                case 21:
                    this.pitchDataIssUnit = '其他';
                    break;
                case 22:
                    this.pitchDataIssUnit = '区科工信局';
                    break;
                case 23:
                    this.pitchDataIssUnit = '区发改局';
                    break;
                case 24:
                    this.pitchDataIssUnit = '区知识产权局';
                    break;
                case 25:
                    this.pitchDataIssUnit = '区人民政府';
                    break;
                case 26:
                    this.pitchDataIssUnit = '区人社局';
                    break;
                case 27:
                    this.pitchDataIssUnit = '全部';
                    break;
            }
            this.getContent(0);

        },
        timestampToTime:function(timestamp) {
            var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
            var Y = date.getFullYear() + '年';
            var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '月';
            var D = date.getDate() + '日';
            var h = date.getHours() + ':';
            var m = date.getMinutes() + ':';
            var s = date.getSeconds();
            return Y + M + D;
        },
        //接收地区
        getHeadCity: function (mycity1, mycity2) {
            //level 级别 1-4 级  default:1  p:2  c:3  a:4
            if (mycity1 != "" && mycity2 == "") {//省级不为空
                if (mycity1=="全国"){
                    box1.isRegion = 0;
                    box1.pitchDataRegion = '省级';
                    box1.province = '省级';
                    box1.city = '市级';
                    box1.area = '区级';
                }else{
                    box1.province = mycity1;
                    box1.city = '市级';
                    box1.area = '区级';
                    box1.pitchDataRegion = mycity1;
                    box1.isRegion = 2;
                    box1.pitchCity = '';
                    box1.pitchArea = '';
                }
            }
            else if (mycity1 != "" && mycity2 != "") {//省级市级不为空
                box1.province = mycity1;
                box1.city = mycity2;
                box1.area = '区级';
                box1.pitchDataRegion = mycity1;
                box1.isRegion = 2;
                box1.isCityActive = 1;
                box1.pitchCity = mycity2;
                box1.pitchArea = '';
            }
            box1.isclaCation = '';
            box1.targetData = '';
            box1.getPitchData();
            
        },
        //左边显示政策速递  type=1--查看更多
        getContent: function (type, index) {
            if(this.targetData != ""){
                var currentPage = this.targetData;
            }else{
                var currentPage = index;
            }
            if (currentPage == undefined) { currentPage=1}

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
            var isIssUnit = this.pitchDataIssUnit;
            var url = this.getUrlData("isclaCation");
            if (this.conceal == 1) {
                var isclaCation = this.isclaCation;
            }else{
                if (url != "" && url != null && url != undefined) {
                    this.isclaCation = url;
                    var isclaCation = url;
                }
            }

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
            if (isIssUnit == '全部') {
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
            htajax.postForm('/ht-biz/policydig/list?t=' + new Date().getTime(),{
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
                productType:isProType,//政策分类
                issueCompany:isIssUnit,//主管单位
                tecnology:isIndustry,//技术领域
                apply:this.apply,//申报通知
                // browsecount:1,//决定数据排序
                current:currentPage,//分页
                memory:this.memory,//记忆初始值为1 其他2
            }, function (data) {
                //console.log(data);
                layer.closeAll();
                if (data.code == 10000) {
                    if (data.reserveData.total == 0) { box1.notfind = 1; }
                    if (data.reserveData.total != 0) { box1.notfind = 0; }
                    box1.page.currentPage = data.reserveData.current;
                    box1.page.totalPage = data.reserveData.pages;
                    box1.page.totalResult = data.reserveData.total;
                    box1.bigData = box1.keyword;
                    box1.setProject = box1.keyword;
                    box1.words = box1.keyword;
                    var datas = data.data;
                    for (var i = 0; i < datas.length; i++) {
                        if (datas[i].beginDate != '' && datas[i].beginDate != null) {
                            datas[i].beginDate = box1.timestampToTime(datas[i].beginDate);
                        }
                        if (datas[i].endDate != '' && datas[i].endDate != null) {
                            datas[i].endDate = box1.timestampToTime(datas[i].endDate);
                        }
                        if (datas[i].datetime != '' && datas[i].datetime != null) {
                            datas[i].datetime = box1.timestampToTime(datas[i].datetime);
                        } 
                        if (datas[i].beginDate == 'NaN年NaN月NaN日') {
                            datas[i].beginDate = '';
                        }
                        if (datas[i].endDate == 'NaN年NaN月NaN日') {
                            datas[i].endDate = '';
                        }
                    }
                    box1.contentDatas = datas;
                    
                }
            }, function (data) {
                //错误回调
            });
            this.targetData = '';
            $(window).scrollTop(0);
            
        },

        //回显上次地区
        showRegion: function () {
            //console.log(url);
            layer.load(2,{
                offset: ['65%', "40%"],
                shade: false
            });
            htajax.postForm('/ht-biz/policydig/list',{
                keyword:this.keyword,//关键字查询
                nature:this.isclaCation,//性质分类
                // memory:1,//初始化值 点击之后为0或空
            }, function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    if (data.reserveData.total == 0) { box1.notfind = 1; }
                    if (data.reserveData.total != 0) { box1.notfind = 0; }
                    box1.bigData = box1.keyword;
                    box1.setProject = box1.keyword;
                    box1.words = box1.keyword;
                    var datas = data.data;
                    for (var i = 0; i < datas.length; i++) {
                        if (datas[i].beginDate != '' && datas[i].beginDate != null) {
                          datas[i].beginDate = box1.timestampToTime(datas[i].beginDate);
                        }
                        if (datas[i].endDate != '' && datas[i].endDate != null) {
                          datas[i].endDate = box1.timestampToTime(datas[i].endDate);
                        }
                        if (datas[i].datetime != '' && datas[i].datetime != null) {
                          datas[i].datetime = box1.timestampToTime(datas[i].datetime);
                        } 
                        if (datas[i].beginDate == 'NaN年NaN月NaN日') {
                          datas[i].beginDate = '';
                        }
                        if (datas[i].endDate == 'NaN年NaN月NaN日') {
                          datas[i].endDate = '';
                        }
                    }
                    box1.contentDatas = datas;
                    layer.closeAll();
                }
            }, function (data) {
                //错误回调
            });
        },
       
        //政策速递，推荐阅读
        getDeliveryList: function (current) {
            var current = current;
            if(current){
                var  current = current
             }else{
                 var current = 1;
             }
            htajax.getForm("/ht-biz/policydig/findlist",{
                browsecount:1,
                size:10,
            }, function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    box1.deliveryList = data.data.records;
                }
            }, function (data) {
                //错误回调
            });
        },
        //政策速递，推荐阅读 改
        // getDeliveryList: function () {
        //     var province0 = this.province;
        //     var city0 = this.city;
        //     var isclaCation = this.isclaCation;
        //     if(province0 == '省级'){province0 = ''; }
        //     if(city0 == '市级'){city0 = ''; }
        //     if(isclaCation == null){isclaCation = '';}
        //     htajax.getForm("/ht-biz/policydig/findlist",{
        //         province:province0,
        //         city:city0,
        //         nature:isclaCation,
        //         size:10
        //     }, function (data) {
        //         //console.log(data);
        //         if (data.code == 10000) {
        //             box1.deliveryList = data.data.records;
        //         }
        //     }, function (data) {
        //         //错误回调
        //     });
        // },

        // 点击编辑按钮触发的事件
        edit_show: function () {
            $('.edit_Mask').show();
            box1.backclacation();
            box1.backProType();
            
        },
       
        //分页
        clickPage: function (index) {
            if (index != 0) {
                if (box1.subscription == 0) {//我的订阅:subscription=1 
                    box1.getContent(3, index);
                } 
                else {
                    box1.getSubscribList(3,index);
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
                    "height":"84px"
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
            if(vm.code == 10000){
                htajax.get('/ht-biz/subscribe/getSubscribe', function (data) {
                    if (data.code == 10000) {
                        // if (data.reserveData.total == 0) { box1.notfind = 1; }
                        // if (data.reserveData.total != 0) { box1.notfind = 0; }
                        box1.dyid = data.data.id;//id
                        box1.dycreateid = data.data.createid;//createid
                        box1.dyvip = data.data.vip;//vip
                        box1.proTypeone = data.data.productType; //政策
                        box1.natureone = data.data.nature; // 性质
                        box1.tecnology = data.data.tecnology;//技术
                        box1.regionone = data.data.region;//地区
                        var somewhere = box1.regionone.split(',');
                        for(var i = 0;i < somewhere.length;i++){
                            box1.Subprovicnce += somewhere[i];
                        }
                        // box1.getpushlist();
                        // box1.getsubinfo();
                        box1.isclaCation = 7;
                        box1.callbackone();
                        box1.callbacktwo();
                        box1.callbackthree();
                        box1.getSubscribList(0);
                    }else{
                        layer.msg(data.msg);
                    }
                },function(data){
                    layer.msg(data.msg);
                })
            }else{
                $(".loginBox").show();
            }
            
        },
        //获取订阅列表 
        getSubscribList :function(type, index){
            var currentPage = 1;
            var targetData=$.trim($("input[name='targetData']").val());
            if(targetData != ""){
                currentPage = targetData;
            }else{
                currentPage = index;
            }
            if(currentPage == undefined){
                currentPage = 1;
            }
            if (type == 1) {
                this.page.showCount += 15;
            } else if (type == 0) {
                this.page.showCount = 15;
            } else if (type == 3) {
                pagesized = index;
            }
            htajax.get('/ht-biz/subscribe/getlist?region=' + box1.regionone + '&nature=' + box1.natureone + '&productType=' + box1.proTypeone + '&tecnology=' + '&vip=' + box1.dyvip + '&current=' + currentPage, function(data){
                if(data.code == 10000){
                    // console.log(data);
                    if (data.data.total == 0) { box1.notfind = 1; }
                    if (data.data.total != 0) { box1.notfind = 0; }
                    box1.page.currentPage = data.data.current;
                    box1.page.totalPage = data.data.pages;
                    box1.page.totalResult = data.data.total;
                    var datas = data.data.records;
                    for (var i = 0; i < datas.length; i++) {
                        if (datas[i].beginDate != '' && datas[i].beginDate != null) {
                            datas[i].beginDate = box1.timestampToTime(datas[i].beginDate);
                        }
                        if (datas[i].endDate != '' && datas[i].endDate != null) {
                            datas[i].endDate = box1.timestampToTime(datas[i].endDate);
                        }
                        if (datas[i].datetime != '' && datas[i].datetime != null) {
                            datas[i].datetime = box1.timestampToTime(datas[i].datetime);
                        } 
                        if (datas[i].beginDate == 'NaN年NaN月NaN日') {
                            datas[i].beginDate = '';
                        }
                        if (datas[i].endDate == 'NaN年NaN月NaN日') {
                            datas[i].endDate = '';
                        }
                    }
                }
                box1.contentDatas = datas;
            },function(data){
                // layer.msg(data.msg);
            })
            this.targetData = '';
        },

        edit_close: function () {
            $('.edit_Mask').hide();
            // box1.mySubscrib();
        },

        // 点击编辑按钮触发的事件
        edit_show: function () {
            $('.edit_Mask').show();
            box1.backclacation();
            box1.backProType();
        },
        // 提交修改订阅信息
        edit_submit: function () {
            //地区
            var region;
            var d1 = $("#region1")[0].innerHTML;
            var d2 = $("#region2")[0].innerHTML;
            var d3 = $("#region3")[0].innerHTML;
            (d1 == '')?region = '':region = d1;
            (d2 == '')?region = d1:region = d2;
            (d3 == '')?region = d2:region = d3;
            if(d3 == ''&&d2 == ''){
                region = d1;
            }
            
            var nature = "";
            var obj = $('.edit_claCation').find("div")[0];
            var allclaCation = $(obj).attr("class"); //性质分类的全部
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
            var objProType = $('.edit_ProType').find("div")[0];
            var allobjProType = $(objProType).attr("class"); //性质分类的全部
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


            var tecnology = "";
            var objtecnology = $('.edit_technology').find("div")[0];
            var allobjtecnology = $(objtecnology).attr("class"); //性质分类的全部
            if (allobjtecnology == 'activec125') {
                tecnology = '1347,1348,1349,1350,1351,1352,1353,1354,1355';
            } else {
                $("div[class='activec125']").each(function (j, item1) {
                    if (j == 0) {
                        tecnology = item1.id
                    } else {
                        tecnology = tecnology + "," + item1.id
                    }
                });
            }

            htajax.getForm('/ht-biz/subscribe/addSubscribe', {
                    id: box1.dyid,
                    createid: box1.dycreateid,
                    vip: box1.dyvip,
                    nature: nature,//性质分类
                    productType: productType,//政策分类
                    tecnology:tecnology,//技术领域
                    region:region,
                },
                function (data) {
                    //console.log(data);
                    if (data.code == 10000) {
                        box1.contentDatas = data.data.records;
                        box1.page.currentPage = data.data.current;
                        box1.page.totalPage = data.data.pages;
                        box1.page.totalResult = data.data.total;
                        $('.edit_Mask').hide();
                        box1.mySubscrib();
                        box1.getSubscribList();
                        //box.getpushlist();
                    }else{
                        layer.msg(data.msg);
                    }
                },
                function (data) {
                    //location.reload();
                })
        },
    },
});
