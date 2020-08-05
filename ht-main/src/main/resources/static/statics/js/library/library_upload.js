/**
 * Created by oll on 2019/7/29.
 */
Vue.filter('moment', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});
var box = new Vue({
    el: '#box',
    data: {
        keywords: '',
        isRegion: '',
        regionData: [],
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

        isProType: '', //地区
        isProDepartment: '', //部门
        pitchDataProType: '',
        policyList: [],
        countryData: [],
        country: "全国",
        countryActive: -1,

        provinceData: [],
        province: "省级",
        provinceActive: -1,

        cityData: [],
        city: "市级",
        cityActive: -1,

        areaData: [],
        area: "区级",
        areaActive: -1,
        //上传文档
        fileName: '',
        boxShow: false,
        username: '',
        id: '',
        fileName: '',
        pdfpath: '', //pdf路径
        originalfile: '', //源文件路径
        shopIpt: '',//店铺
        userType: '',
        osspath:'',
    },
    methods: {
        initPage: function () {
            this.getregionData();
            this.getUserInfo();
            this.inquireService();
        },
        inquireService: function () {
            htajax.postForm("/ht-biz/service/judge", {}, function (data) {
                // console.log(data);
                if (data.code == 10000) {
                    box.userType = data.data.userType;
                    box.shopIpt = data.data.shopname;//店铺名称
                }
            }, function (data) {
                //layer.msg(data.msg);    
            })
        },
        getRegion: function () {
            location.href = "/ht-biz/library/index?keyword=" + box.keywords + '&curPolicy=' + 8;
        },
        // 获取用户的信息
        getUserInfo: function () {
            htajax.get("/ht-biz/library/getuserinfo", function (data) {
                if (data.code == 10000) {
                    box.username = data.data;
                } else { }
            }, function (data) {
                layer.msg(data.msg);
            });
        },
        stopThings: function () {
            if (this.isCityActive == 1) {
                this.isArea = true;
            }
        },
        openfile: function () {
            $("input[name='file']").click();
        },
        clearIpt: function () {
            $("#file3").attr('type', 'text');
            $("#file3").attr('type', 'file');
        },
        //获取上传的文件属性
        tirggerFile: function (event) {
            debugger
            var iptUrl = event.target.files[0];
            box.fileName = iptUrl.name;
            if (box.fileName != '') {
                this.boxShow = true;
            } else {
                this.boxShow = false;
            }
            // if(box.fileName.indexOf(""))
            var extension = box.fileName.toLowerCase();
            var allowed = [".jpg", ".gif", ".png", ".jpeg", ".rar"];
            for (var i = 0; i < allowed.length; i++) {
                if (extension.indexOf(allowed[i]) >= 0) {
                    alert("不支持" + extension + "格式");
                    return false;
                }
            }
            var f = event.target.files
            var upsize = f[0].size;
            //大小 字节  
            //console.log("[大小 字节]" + upsize);
            //类型  
            //console.log("[类型]" + f[0].type);
            if (upsize > 10485760) {
                layer.msg("上传文件不能超过10M!");
                this.clearIpt();
                return false;
            }
            var files = $('#file').prop('files');
            var data = new FormData();
            data.append('file', files[0]);
            
            $.ajax({
                type: 'POST',
                url: "/uploadController/upload",
                data: data,
                cache: false,
                processData: false,
                contentType: false,
                beforeSend: function () {
                    var index = layer.load(1, {
                        shade: [0.5, '#fff'] //0.1透明度的白色背景
                    });
                },
                success: function (data) {
                    layer.closeAll();
                    if (data.code == 10000) {
                        box.pdfpath = data.data.pdfpath;
                        box.osspath = data.data.osspath;
                        box.originalfile = data.data.path;
                    } else {
                        layer.msg(data.msg);
                    }
                },
                error: function () {
                    layer.closeAll();
                }
            });
        },
        //提交
        submitForm: function () {
            var title = $.trim($("input[name='title']").val());
            var province = box.province;
            var city = box.city;
            var area = box.area;
            var keyword = $.trim($("input[name='keyword']").val());
            var policyList = $.trim($("select[name='policyList'] option:selected").val());
            var natureList = $.trim($("select[name='natureList'] option:selected").val());
            var technologyList = $.trim($("select[name='technologyList'] option:selected").val());
            var priceList = $.trim($("input[name='priceList']").val());

            if (box.originalfile == '') {
                layer.msg("请添加文档");
                return false;
            }
            if (title == '') {
                layer.msg("请填写文章标题");
                return false;
            }
            if (province == '') {
                layer.msg("请填写政策省");
                return false;
            }
            if (city == '') {
                layer.msg("请填写政策市");z
                return false;
            }
            if (box.originalfile == '') {
                box.originalfile = box.pdfpath;
            }
            if (priceList > 500) {
                layer.msg("最大可填写500honey");
                return false;
            }
            if (this.userType == "SERVICE_PROVIDER") {
                var name = this.shopIpt
            } else {
                var name = this.username;
            }
            htajax.postDone("/ht-biz/library/savalibrary", {
                uploadusername: name,
                pdfpath: this.pdfpath,
                originalfile: this.originalfile,
                title: title,
                province: province,
                city: city,
                area: area,
                keywords: keyword,
                type: policyList, //性质分类
                libtype: natureList, //项目分类 
                technicalfield: technologyList, //技术分类
                downloadhoney: priceList, //honey值
                osspath: this.osspath,//oss
            }, function () {
                var index = layer.load(1, {
                    shade: [0.5, '#fff'] //0.1透明度的白色背景
                });
            }, function (data) {
                layer.closeAll();
                if (data.code == 10000) {
                    layer.msg("上传成功");
                    setTimeout(function () {
                        location.reload();
                    }, 2000);

                } else {
                    layer.msg("上传失败");
                    setTimeout(function () {
                        location.reload();
                    }, 2000);
                }
            }, function (data) {
                layer.closeAll();
                //
            })


        },
        //鼠标隐藏提示
        enter: function () {
            $(".info_img").fadeIn(200);
        },
        leave: function () {
            $(".info_img").fadeOut(200);
        },
        // 获取全国的省、市信息
        getregionData: function () {
            htajax.get('/statics/js/cityData.json',
                function (data) {
                    box.regionData = data;
                    //console.log(box.regionData);
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
    },
    created: function () {
        this.initPage();
    },
});

