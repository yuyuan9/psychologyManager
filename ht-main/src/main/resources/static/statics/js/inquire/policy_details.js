/**
 * Created by ACER on 2019/8/8.
 * 政策查
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
var box = new Vue({
    el: '#box',
    data: function () {
        return {
            url1:'',
            url2:'',
            dropDownList:0,
            dropDownShow:false,
            words:'',
            look_more1:'',

            memory:'',
            targetData:'',
            ssss: 0,
            //主管单位
            organizationList:[],
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
            keyword: '', //关键字搜索
            //地区选择
            isRegion: 0, //1-国家级  2-省级
            //isRegion0: 0, //1-国家级  2-省级
            regionData: [], // 位置信息
            //regionData0: [], // 位置信息
            //位置
            guojia:'国家',
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
            // 我的订阅传递的地址数据
            myregionlist1: [],
            myregionlist2: [],
            form: {
                city: '', // 省
                erae: '', // 城市
                minerae: '', //区
                selectedOptions: [], //地区筛选数组
                selectedOptions2: [], //地区筛选数组
            },
           
            //导出
            checkBox:4,
            pitchdiqu:'',
            year:'',
            projecname:'',
            chargedept:'',
            technology:'',
            regions:'',
            countrys:'',
            provinces:'',
            citys:'',
            areas:'',
            issueData:'开始年份',
            issueData1:'结束年份',
            isData:false,
            isData1:false,

            //推荐
            deliveryList:[],

            //导出
            derived:'',
            //搜索一次后 keyword为空
            outKeyword:'',
            //访问记录
            visitedData:'',
            userHoney:'',
            honeyInfo:false,
            nohoney:false,
            deriveInfo:false,
            reminder:false,
            deriveQuantity:'',
            needHoney:'',
            searchtotal:'',
            //翻页记录
            pageturn:'',
            notfind: 0,
            contentData1:[],
            memorys:false,
            returnCode:'',
            returnValue:'',
            gainCode:'',
            flag:'',
            
        }
    },
    created: function () {
        this.getUserHoney();
        this.visitData();
        var t;
        clearTimeout(t)
        t = setTimeout(function (){
            box.initPage();
        }, 200);
        //this.initPage();
    },
    methods: {
        initPage: function () {
            this.getCode();
            this.getregionData();
            this.getIsIndustryJson();
            this.getUserInfo();
            this.getDelibraryList();
            this.deriveToData();
            this.inquireHoney();
        },
        getIsIndustryJson:function(){
            htajax.get('/statics/js/tecnology.json',
                function (data) {
                    box.isIndustryJson = data.techfield.content;
                    //console.log(data.techfield.content);
            });
        },
        // 获取用户的信息
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser", function (data) {
                if (data.code == 10000) {
                    box.getUserHoney();
                    
                    
                }else{
                    $(".loginBox").show();
                }
                //box.code = data.code;
            }, function (data) {
                layer.msg(data.msg);
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
            //政策级别
            switch (this.checkBox) {
                case 0:
                    this.pitchdiqu = '国家';
                    break;
                case 1:
                    this.pitchdiqu = '省级';
                    break;
                case 2:
                    this.pitchdiqu = '市级';
                    break;
                case 3:
                    this.pitchdiqu = '区级';
                    break;
                case 4:
                    this.pitchdiqu = '不限';
                    break;
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
                    this.pitchDataIssUnit = '省工信厅';
                    break;
                case 9:
                    this.pitchDataIssUnit = '省人社厅';
                    break;
                case 10:
                    this.pitchDataIssUnit = '其他';
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
                    this.pitchDataIssUnit = '市工信局';
                    break;
                case 19:
                    this.pitchDataIssUnit = '市人民政府';
                    break;
                case 20:
                    this.pitchDataIssUnit = '其他';
                    break;
                case 21:
                    this.pitchDataIssUnit = '区科工信局';
                    break;
                case 22:
                    this.pitchDataIssUnit = '其他';
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
            }
            this.showMsg();

        },
        //用户honey
        getUserHoney:function(){
            htajax.get("/ht-biz/honeymanager/getHoneyTotal", function (data) {
                if (data.code == 10000) {
                    box.userHoney = data.data;
                    if(box.userHoney < 0){
                        box.userHoney = 0;
                    }
                    
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //访问记录
        visitData:function(){
            htajax.get('/ht-biz/poliprorecord/list?local=' + '政策库',
                function (data) {
                    //console.log(data);
                    if(data.code == 10000){
                        box.visitedData = data.data;
                        // console.log("今日查询记录:"+data.data);
                    }
            },function(){
                //
            });
        },
        //查询扣费
        inquireHoney:function(){
            htajax.get('/ht-biz/honeymanager/getHoneyByCode?code=' + 'poli_pro_counts',
                function (data) {
                    if (data.code == 10000) {
                        box.returnValue = Math.abs(data.data.returnValue);;
                        //console.log(box.returnValue);
                    }
                }, function () {
                    //
                });
        },
        //查询点击状态
        getCode:function(){
            htajax.get('/ht-biz/castbox/userbox?code=' + 'policylib_current',
            function (data) {
                if (data.code == 10000) {
                    box.flag = true;
                    box.getContent(0);
                } else{
                    box.flag = false;
                    box.getContent(0);
                }
            }, function () {
                //
            });
        },
        removeDis:function(){
            if (this.memorys) {
                $('#submitRegs').attr("disabled", false);
                $("#submitRegs").removeClass("dddd");
            }else{
                $('#submitRegs').attr("disabled", true);
                $("#submitRegs").addClass("dddd ");
            }
        },
        //保存用户点击记录
        goon:function(){
            if (box.userHoney < box.returnValue){
                box.reminder = false;
                //box.nohoney = true;
                layer.msg("您的honey值不足，请前往个人云中心充值");
                return false;
            }
            if (this.memorys) {
                htajax.postForm('/ht-biz/castbox/save', {
                    code: 'policylib_current',
                    module: '政策库',
                },
                function (data) {
                    if (data.code == 10000) {
                        // console.log(data);
                        box.gainCode = data.code;
                        box.getCode();
                        box.getContent();
                    }
                }, function () {
                    //
                });
                box.reminder = false;
                //box.notfind = 1;
            } else {
                box.reminder = false;
            }

            
        },
        clearWords:function(){
            this.issueData = '开始年份';
            this.issueData1 = '结束年份';
            this.province = '省级';
            this.city = '市级';
            this.area = '区级';
            this.regions = 1;
            this.checkBox = 4;
            this.pitchdiqu = '';
            this.countrys = '';
            this.provinces = '';
            this.citys = '';
            this.areas = '';
        },
        //接收组件的参数
        showMsg:function(index){
            this.pageturn = '';
            this.getUserHoney();
            this.url1 = index;
            if(box.visitedData == 20){
                this.honeyInfo = true;
            }else{
                this.getContent(1);
            }
        },
        //左边显示政策速递  type=1--查看更多
        getContent: function (type,index) {
            if(this.visitedData >= 20){
                if(this.userHoney <= 0){
                    layer.msg("您的honey值不足，请前往个人云中心充值");
                    return false;
                }
            }
            //console.log("剩余honey:"+box.userHoney);
            //jq this对象
            var url1 = this.url1;
            if(url1==''||url1==undefined||url1==null){
                var key=$(".keyWord1")[0].id;
                //console.log(key);
                var keyword = key;
            }else{
                var keyword = url1;
            }
            this.words = keyword;
            this.url1 = keyword;
            this.honeyInfo = false;
            var index = index;
            if(index == undefined||index == ''){index = 1;}
            var currentPage = this.pageturn
            if (currentPage == undefined || currentPage == '') { currentPage = 1; }
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
            if (this.isRegion == 1) {province0 = '国家';this.guojia = '国家';}
            if (province0 == '省级') {province0 = '';}
            if (city0 == '市级') {city0 = '';}
            if (area0 == '区级') {area0 = '';}

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
            //
            var region1 = this.regions;
            var country1 = this.countrys;
            var province1 = this.provinces;
            var city1 = this.citys;
            var area1 = this.areas;
            if (country1 == "国家"){country1 = '国家';province1 = '';city1 = '';area1 = '';}
            if (province1 == "省"){country1 = '';province1 = '省';city1 = '';area1 = '';}
            if (city1 == "市"){country1 = '';province1 = '';city1 = '市';area1 = '';}
            if (area1 == "区"){country1 = '';province1 = '';city1 = '';area1 = '区';}

            var isIndustry = this.isIndustry;
            var isProType = this.isProType;
            var isIssUnit = this.pitchDataIssUnit;
            var isclaCation = this.isclaCation;
            if (isIndustry == 1355) {isIndustry = '';}
            if (isProType == 0) {isProType = '';}
            if (isclaCation == 0) {isclaCation = '';}
            if (isIssUnit == 0) {isIssUnit = '';}
            
            if (isIndustry == 9) {isIndustry = '';}
            if (isProType == 9) {isProType = '';}
            if (isIssUnit == '全部') {isIssUnit = '';}

            //发布日期1
            var issueData = this.issueData;
            if(issueData=='开始年份'){issueData = '';}
             //发布日期2
            var issueData1 = this.issueData1;
            if(issueData1=='结束年份'){issueData1 = '';}
            //console.log(this.gainCode);
            
            if (this.flag){
                if (this.pageturn == 1 || this.pageturn == '') {
                    var page = '';
                } else {
                    var page = this.pageturn;
                }
            }else{
                var page = '';
            }
            htajax.postFormDone('/ht-biz/policylib/list?t=' + new Date().getTime(),{
                keyword:keyword,//关键字查询
                year1:issueData,
                year2:issueData1,
                // projecname:'',
                // chargedept:'',//主管部门
                // technology:'',//技术领域
                province:province0,//选择省级
                city:city0,//选择市级
                area:area0,//选择区级
                regions:region1,//复选框选择状态（0表示用户点击页面省市区复选框，1表示没有点击）
                countrys:country1,//复选框国家
                provinces:province1,//复选框省级
                citys:city1,//复选框市级
                areas:area1,//复选框区级
                current:currentPage,//分页
                // regions:this.regions,
                size:15,
                currcord: page,
            },function(){
                var index = layer.load(1, {
                    shade: [0.5,'#fff'] //0.1透明度的白色背景
                  });
            }, function (data) {
                layer.closeAll();
                if (box.pageturn != '') {//翻页记录
                    if (box.flag) {//是否点击弹框
                        if (data.code == 10000) {
                            // console.log(data);
                            if (data.data.total == 0) { box.notfind = 1; }
                            if (data.data.total != 0) { box.notfind = 0; }
                            if (data.data == "无结果") { box.notfind = 1; }
                            box.contentDatas = data.data.records;
                            box.page.currentPage = data.data.current;
                            box.page.totalPage = data.data.pages;
                            box.page.totalResult = data.data.total;
                            box.outKeyword = box.getUrlData("keyword1");
                            //box.honeyInfo = false;
                        } else {
                            if (data.msg == "honey值不足") { layer.msg("您的honey值不足，请前往个人云中心充值"); }
                            if (data.msg == "今日查询次数已用完") { layer.msg("今日查询次数已用完"); }
                            box.contentDatas = '';
                            box.page.currentPage = ''
                            box.page.totalPage = '';
                            box.page.totalResult = '';
                            box.notfind = 1;
                            //layer.msg(data.msg);
                        }
                    } else {
                        box.reminder = true;
                    }
                }else{
                    if (data.code == 10000) {
                        if (data.data.total == 0) { box.notfind = 1; }
                        if (data.data.total != 0) { box.notfind = 0; }
                        if (data.data == "无结果") { box.notfind = 1;  }
                        box.contentDatas = data.data.records;
                        box.page.currentPage = data.data.current;
                        box.page.totalPage = data.data.pages;
                        box.page.totalResult = data.data.total;
                        box.outKeyword = box.getUrlData("keyword1");
                        //box.honeyInfo = false;
                    } else {
                        if (data.msg == "honey值不足") { layer.msg("您的honey值不足，请前往个人云中心充值"); }
                        if (data.msg == "今日查询次数已用完") { layer.msg("今日查询次数已用完"); }
                        box.contentDatas = '';
                        box.page.currentPage = ''
                        box.page.totalPage = '';
                        box.page.totalResult = '';
                        box.notfind = 1;
                        //layer.msg(data.msg);
                    }
                }
                if (data.code == null) { if (keyword == '') { layer.msg("请先输入要查询的关键词") }}
            }, function (data) {
                //错误回调
                //layer.msg(data.msg);
                layer.closeAll();
            });
            // console.log("翻页记录"+box.pageturn);
            this.targetData = '';
            this.visitData();
            
        },
        clearPCA:function(){
            this.countrys = '';
            this.provinces = '';
            this.citys = '';
            this.areas = '';
        },
        
        //导出记录
        deriveToData: function(){
            htajax.get('/ht-biz/poliproexport/getcounts?local=' + '政策库',
                function (data) {
                    if(data.code == 10000){
                        // console.log('导出记录:'+data.data);
                        box.derived = data.data;
                    }
            },function(){
                //
            });
        },
        //导出提示信息
        /* 导出提示信息
         * 1、检查用户honey值
         * 2、检测用户当天导出记录条数
         * 3、弹出相关信息提醒
         * 4、导出记录
         */
        deriveInformation:function(){
        	box.getUserHoney();
            box.deriveToData();
            if (box.derived >= 100) { layer.msg("今日导出数量已满"); return false; }
        	var index = layer.load(1, {
                 shade: [0.5,'#fff'] //0.1透明度的白色背景
            });
        	var time = setTimeout(function(){
			     if(box.derived < 101){
			         box.deriveInfo = true;
			         //查询上来的记录数
			         box.searchtotal = box.page.totalResult;
                     var daochu = 100 - box.derived;
			         if(box.searchtotal <= daochu){
			         	box.deriveQuantity = box.searchtotal;
			         	box.needHoney = box.deriveQuantity*0.5;
			         }else{
			         	box.deriveQuantity = 100 - box.derived;
			         	box.needHoney = box.deriveQuantity*0.5;
			         }
			     }else{
			         layer.msg("今日导出数量已满")
			     }
			     layer.close(index);
        	},2000)
        },
        //导出判断
        deriveToIf:function(){
            if(box.needHoney > box.userHoney){
                layer.msg("余额不足请前往个人云中心充值");
                var time = setTimeout(function(){
                    box.deriveToData();
                    //box.deriveInfo = false;
                },1000)
                return false;
            }
            else if(box.userHoney == 0){
                layer.msg("余额不足请前往个人云中心充值");
                var time = setTimeout(function(){
                    box.deriveToData();
                    //box.deriveInfo = false;
                },1000)
                return false;
            }else if(box.deriveQuantity == 0){
                layer.msg("请选择导出项");
                var time = setTimeout(function(){
                    box.deriveToData();
                    //box.deriveInfo = false;
                },1000)
                return false;
            }
            else{
                this.deriveTo();
            }
        },
        //导出
        deriveTo: function(){
            var keyword = this.url1;
            var urlId1= this.getUrlData("keyword1");
            if(keyword==''||keyword==undefined){keyword = urlId1;}
            this.words = keyword;
            if(keyword == undefined){keyword = '';}
            //发布日期1
            var issueData = this.issueData;
            if(issueData=='开始年份'){issueData = '';}
            //发布日期2
            var issueData1 = this.issueData1;
            if(issueData1=='结束年份'){issueData1 = '';}

            var province0 = this.province;
            var city0 = this.city;
            var area0 = this.area;
            
            if (this.isRegion == 1) {province0 = '国家';}
            if (province0 == '省级') {province0 = '';}
            if (city0 == '市级') {city0 = '';}
            if (area0 == '区级') {area0 = '';}

            var region1 = this.regions;
            var country1 = this.countrys;
            var province1 = this.provinces;
            var city1 = this.citys;
            var area1 = this.areas;
            if (country1 == "国家"){country1 = '国家';province1 = '';city1 = '';area1 = '';}
            if (province1 == "省"){country1 = '';province1 = '省';city1 = '';area1 = '';}
            if (city1 == "市"){country1 = '';province1 = '';city1 = '市';area1 = '';}
            if (area1 == "区"){country1 = '';province1 = '';city1 = '';area1 = '区';}

            if(this.derived<100){
                location.href = '/ht-biz/policylib/export?keyword=' + keyword + '&year1=' + issueData + '&year2=' + issueData1 + '&projecname=' + this.projecname + '&chargedept=' + this.chargedept + '&technology=' + this.technology + '&province=' + province0 + '&city=' + city0 + '&area=' + area0 + '&regions=' + region1 + '&countrys=' + country1 + '&provinces=' + province1 + '&citys=' + city1 + '&areas=' + area1 + '&size=' + this.deriveQuantity;
                var time = setTimeout(function(){
                    box.deriveToData();
                    box.deriveInfo = false;
                },1000)
            }else{
                layer.msg("今日导出数量已满");
                var time = setTimeout(function(){
                    box.deriveToData();
                    box.deriveInfo = false;
                },1000)
            }
            
            
        },
        
        //分页
        clickPage: function (index) {
            box.pageturn = index;
            if (index != 0) {
                if (box.ssss == 0) {
                    box.getContent(3, index);
                } 
                
            }
        },
        browse_more1:function(){
            var height = $(".jishu").height();
            if(height <= 30){
                $(".jishu").animate({
                    "height":"70px"
                },500);
                $(".more_icon1").css({
                    "transform":"rotate(-90deg)",
                },200)
            }else{
                $(".jishu").animate({
                    "height":"30px"
                },500);
                $(".more_icon1").css({
                    "transform":"rotate(0deg)",
                },200)
            }
        },
        browse_more2:function(){
            var height = $(".issue_unit").height();
            if(height <= 30){
                $(".issue_unit").animate({
                    "height":"109px"
                },500);
                $(".more_icon2").css({
                    "transform":"rotate(-90deg)",
                },200)
            }else{
                $(".issue_unit").animate({
                    "height":"30px"
                },500);
                $(".more_icon2").css({
                    "transform":"rotate(0deg)",
                },200)
            }
            
        },
        // lookmore:function(event){
        //     if(event.currentTarget.parentElement.previousElementSibling.getAttribute("class") == "list_content_text list_info"){
        //         event.currentTarget.parentElement.previousElementSibling.classList.add("active2");
        //         event.srcElement.innerHTML = "收起";
                
        //     }else{
        //         event.currentTarget.parentElement.previousElementSibling.classList.remove("active2");
        //         event.srcElement.innerHTML = "查看详情";
        //     }
        // },
        //查看详情
        lookmore: function (id, name){
            var id = id;
            var keyword = name;
            // alert(111)
            layer.open({
                type: 1,
                title: '项目名称：' + keyword,
                area: ['750px', '600px'], //宽高
                content: $('#ids') //这里content是一个DOM，这个元素要放在body根节点下
            });
            htajax.get('/ht-biz/policylib/edit?id='+id,function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.contentData1 = data.data;
                }
            }, function (data) {
                //layer.closeAll();
            });
            
        },
         // 推荐
         getDelibraryList: function () {
            htajax.get("/ht-biz/library/findlibraryall?size=" + 10 + "&libtype=" + 4, 
            function (data) {
                if (data.code == 10000) {
                    box.deliveryList = data.data;
                }
            }, function (data) {
                //错误回调
            });
        },
        //鼠标隐藏提示
        enter: function () {
            $(".info_img").fadeIn(200);
        },
        leave: function () {
            $(".info_img").fadeOut(200);
        },
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    },
});
