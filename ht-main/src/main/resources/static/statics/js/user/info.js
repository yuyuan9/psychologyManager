/**
 * Created by ACER on 2019/8/12.
 */
Vue.filter('hideMiddle', function (value) {
    var strval = value.toString();
    return value = `${strval.substring(0,3)}****${strval.substring(strval.length-4)}`
});
Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});
var box = new Vue({
    el: '#box',
    data() {
        return {
            allkeyword: '',
            value: 1, //星星
            cur: '',
            curInfo: 0,
            deleteShow: '',
            deleteShow2: '',
            userId: '',
            username: '',
            code: '',
            progressBar: 0,
            mun: 0,
            bar1: 0,
            bar2: 0,
            bar3: 0,
            bar4: 0,
            bar5: 0,
            userHoney: '',
            userGrade: '',
            userInfo: '',
            userCode: '',
            userInfoName: '',
            userInfoPhone: '',
            userInfoEamil: '',
            userInfoType: '',
            userType: '',
            peopleInfo: '',
            companyname: '',
            companyid: '',
            compregcode: '',
            regdate: '',
            usertown: '',
            province: '请选择省',
            city: '请选择市',
            area: '请选择区',
            areaData: '',
            addr: '',
            weburl: '',
            mainproduct: '',
            techfield: '',
            remark: '',
            billpath: '',
            businesspath: '',
            newPhoneNumber: '',

            alterRemark: '',
            headImg: '',

            // 上传
            fileName1: '',
            pdfpath1: '',
            originalfile1: '',
            //弹框
            reminder: false,
            beizhu: '',
            orderList: '',
            //订单信息回显
            companyName: '',
            province1: '',
            city1: '',
            area1: '',
            usertown1:'',
            name: '',
            phone: '',
            textId:'',
            saveframe1: '',
            saveframe2: '',
            saveframe3: '',
        }
    },
    created() {
        this.initPage();

    },
    mounted() {
        setTimeout(function () {
            box.getcity();
        }, 4000)
    },
    methods: {
        initPage: function () {
            this.getHoney();
            this.getUserInfo();
            this.getOrderList();
        },
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        //城市
        getcity: function () {
            //省市区
            $(".pick-area4").pickArea({
                // "format":"", //格式
                "width": "420", //设置“省市县”文本边框的宽度
                "height": "35", //设置“省市县”文本边框的高度
                "borderColor": "#dcdfe6", //设置“省市县”文本边框的色值
                "arrowColor": "#dcdfe6", //设置下拉箭头颜色
                "listBdColor": "#dcdfe6", //设置下拉框父元素ul的border色值
                "color": "#666666", //设置“省市县”字体颜色
                "fontSize": "14px", //设置字体大小
                "hoverColor": "#1E9FFF",
                "paddingLeft": "30px", //设置“省”位置处的span相较于边框的距离
                "arrowRight": "30px", //设置下拉箭头距离边框右侧的距离
                "maxHeight": "247px",
                //"preSet":"",//数据初始化 ；这里的数据初始化有两种方式，第一种是用preSet属性设置，第二种是在a标签里使用name属性设置
                "getVal": function () { //这个方法是每次选中一个省、市或者县之后，执行的方法
                    // console.log($(".pick-area-hidden").val())
                    // console.log($(".pick-area-dom").val())
                    var thisdom = $("." + $(".pick-area-dom").val()); //返回的是调用这个插件的元素pick-area，$(".pick-area-dom").val()的值是该元素的另一个class名，这个class名在dom结构中是唯一的，不会有重复，可以通过这个class名来识别这个pick-area
                    thisdom.next().val($(".pick-area-hidden").val()); //$(".pick-area-hidden").val()是页面中隐藏域的值，存放着每次选中一个省、市或者县的时候，当前文本存放的省市县的最新值，每点击一次下拉框里的li，这个值就会立即更新
                }
            });
            //console.log('jq:'+$(".pick-area").attr('name'));
        },
        //企业城市`
        getcity1: function () {
            //省市区
            $(".pick-area5").pickArea({
                // "format":"", //格式
                "width": "320", //设置“省市县”文本边框的宽度
                "height": "35", //设置“省市县”文本边框的高度
                "borderColor": "#dcdfe6", //设置“省市县”文本边框的色值
                "arrowColor": "#dcdfe6", //设置下拉箭头颜色
                "listBdColor": "#dcdfe6", //设置下拉框父元素ul的border色值
                "color": "#666666", //设置“省市县”字体颜色
                "fontSize": "14px", //设置字体大小
                "hoverColor": "#1E9FFF",
                "paddingLeft": "30px", //设置“省”位置处的span相较于边框的距离
                "arrowRight": "30px", //设置下拉箭头距离边框右侧的距离
                "maxHeight": "247px",
                //"preSet":"",//数据初始化 ；这里的数据初始化有两种方式，第一种是用preSet属性设置，第二种是在a标签里使用name属性设置
                "getVal": function () { //这个方法是每次选中一个省、市或者县之后，执行的方法
                    // console.log($(".pick-area-hidden").val())
                    // console.log($(".pick-area-dom").val())
                    var thisdom = $("." + $(".pick-area-dom").val()); //返回的是调用这个插件的元素pick-area，$(".pick-area-dom").val()的值是该元素的另一个class名，这个class名在dom结构中是唯一的，不会有重复，可以通过这个class名来识别这个pick-area
                    thisdom.next().val($(".pick-area-hidden").val()); //$(".pick-area-hidden").val()是页面中隐藏域的值，存放着每次选中一个省、市或者县的时候，当前文本存放的省市县的最新值，每点击一次下拉框里的li，这个值就会立即更新
                }
            });
            //console.log('jq:'+$(".pick-area").attr('name'));
        },
        // 获取用户的信息
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser", function (data) {
                if (data.code == 10000) {
                    box.userId = data.data.userId;
                    box.username = data.data.username;
                    box.code = data.code;
                    box.userType = data.data.userType;
                    box.headImg = data.data.headImg;

                } else {
                    window.location.href = '/ht-biz/login/login';
                }
                box.getUserInformation();
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getHoney: function () {
            htajax.get("/ht-biz/honeymanager/getHoneyTotal", function (data) {
                if (data.code == 10000) {
                    box.userHoney = data.data;
                }
                box.getGrade();
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getGrade: function () {
            htajax.get("/ht-biz/honeymanager/getUserLevel?type=" + 1, function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    if (data.data != null) {
                        box.userGrade = data.data.sort;
                        box.userCode = data.data.code;
                        box.value = data.data.code;
                    }

                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getUserInformation: function () {
            htajax.postForm("/ht-shiro/sysuser/myinfo", {}, function (data) {
                if (data.code == 10000) {
                    box.userInfo = data.data;
                    box.originalfile1 = data.data.headImg; //1
                    box.userInfoName = data.data.trueName; //2
                    box.userInfoPhone = data.data.phone;
                    box.userInfoEamil = data.data.email; //3
                    box.companyname = data.data.companyname;
                    box.companyid = data.data.companyid;
                    box.compregcode = data.data.compregcode;
                    box.regdate = data.data.regdate;
                    box.province = data.data.regprovince; //4
                    box.city = data.data.regcity; //4
                    box.area = data.data.regarec; //4
                    if (box.province == null || box.province == '') {
                        box.province = "请选择省"
                    }
                    if (box.city == null || box.city == '') {
                        box.city = "请选择市"
                    }
                    if (box.area == null || box.area == '') {
                        box.area = "请选择区"
                    }
                    box.usertown = box.province + '/' + box.city + '/' + box.area;
                    $(".pick-area4").attr('name', box.usertown);
                    //console.log(box.usertown);
                    box.addr = data.data.addr;
                    box.weburl = data.data.weburl;
                    box.mainproduct = data.data.mainproduct;
                    box.techfield = data.data.techfield;
                    box.peopleInfo = data.data.remark; //5
                    box.billpath = data.data.billpath;
                    box.businesspath = data.data.businesspath;
                    if (box.userInfoName == null) {
                        box.userInfoName = data.data.phone;
                    }
                    if (box.userType == "ADMIN") {
                        box.userType = "ADMIN";
                    }
                    if (box.userType == "COMPANY_USER") {
                        box.userType = "企业用户";
                    }
                    if (box.userType == "SERVICE_PROVIDER") {
                        box.userType = "服务商";
                    }
                    if (box.userType == "") {
                        box.userType = "普通用户";
                    }
                    if (box.userType == null) {
                        box.userType = "普通用户";
                    }
                    if (box.userType == "DEFAULT_USER") {
                        box.userType = "普通用户";
                    }
                    box.userInfoType = data.data.userType;
                    //资料完善度
                    (box.originalfile1 == null || box.originalfile1 == '') ? box.bar1 = 0: box.bar1 = 20;
                    (box.userInfoName == null || box.userInfoName == '') ? box.bar2 = 0: box.bar2 = 20;
                    (box.userInfoEamil == null || box.userInfoEamil == '') ? box.bar3 = 0: box.bar3 = 20;
                    (box.province == null && box.city == null && box.area == null) ? box.bar4 = 0: box.bar4 = 20;
                    (box.peopleInfo == null || box.peopleInfo == '') ? box.bar5 = 0: box.bar5 = 20;
                    box.progressBar = box.bar1 + box.bar2 + box.bar3 + box.bar4 + box.bar5;
                    //console.log(box.progressBar);
                } else {
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //修改信息
        personInfo: function () {
            layer.open({
                type: 1,
                area: ['750px', '600px'], //宽高
                content: $('#ids3') //这里content是一个DOM，这个元素要放在body根节点下
            });
        },
        submitAlter: function () {
            var alterName = $.trim($("input[name='alterName']").val());
            var alterUserInfoEamil = $.trim($("input[name='alterUserInfoEamil']").val());
            var province = $(".pick-area4>.pick-show>.pick-province").text();
            var city = $('.pick-area4>.pick-show>.pick-city').text();
            var area = $('.pick-area4>.pick-show>.pick-county').text();
            var peopleInfo = this.peopleInfo;
            var path = this.originalfile1;

            htajax.post("/ht-shiro/sysuser/editUser", {
                    "headImg": path,
                    "userId": box.userId,
                    "regprovince": province,
                    "regcity": city,
                    "regarec": area,
                    "trueName": alterName,
                    "email": alterUserInfoEamil,
                    "remark": peopleInfo,
                },
                function (data) {
                    if (data.code == 10000) {
                        layer.msg("修改成功");
                        layer.closeAll();
                        location.reload();
                    }
                },
                function (data) {
                    //console.log(data)
                }
            )

        },
        openfile1: function () {
            $(".file_ipt1").click();
        },
        checkFileSize: function (files) {
            var fileSize = 0;
            checktype = false;
            if (files.length != 0) {
                fileSize = files[0].size;
            }
            if (fileSize <= 5242880) {
                return true;
            } else {
                return false;
            }
        },
        clearIpt: function () {
            $("#file1").attr('type', 'text');
            $("#file1").attr('type', 'file');
        },
        //获取上传的文件属性
        tirggerFile1: function (event) {
            //var iptUrl = event.target.files[0];
            var files = $('#file1').prop('files');
            var data = new FormData();
            data.append('file', files[0]);

            var checktype = false;
            var filetypes = ["image/gpg", "image/bmp", "image/jpg", "image/png", "image/tif", "image/gif", "image/jpeg"];
            for (i = 0; i < filetypes.length; i++) {
                if (filetypes[i].toLowerCase() == files[0].type.toLowerCase()) {
                    checktype = true;
                }
            }
            if (checktype == false) {
                layer.msg("上传文件格式不正确");
            } else if (this.checkFileSize(files)) {
                var index;
                $.ajax({
                    type: 'POST',
                    url: "/uploadController/upload?t=" + new Date().getTime(),
                    data: data,
                    cache: false,
                    processData: false,
                    contentType: false,
                    beforeSend: function (XHR) {
                        index = layer.load(1, {
                            shade: [0.5, '#fff'] //0.1透明度的白色背景
                        });
                    },
                    success: function (data) {
                        layer.close(index);
                        if (data.code == 10000) {
                            box.fileName1 = data.data.fileName;
                            box.pdfpath1 = data.data.pdfpath;
                            box.originalfile1 = data.data.path;
                            //console.log(data);
                            //console.log(box.originalfile1);
                        } else {
                            layer.msg(data.msg);
                        }
                    },
                    error: function () {
                        layer.close(index);
                        layer.msg('上传失败');
                    }
                });

            } else {
                layer.msg("文件大于5M拒绝上传");
            }
        },

        submitPhone: function () {
            var userInfoPhone = $.trim($("input[name='userInfoPhone']").val());
            htajax.post("/ht-shiro/sysuser/editUser", {
                    "userId": box.userId,
                    "phone": userInfoPhone
                },
                function (data) {
                    if (data.code == 10000) {
                        layer.msg("修改成功");
                        layer.closeAll();
                        // location.reload();
                        box.getOrderList();
                    }
                },
                function (data) {
                    //console.log(data)
                }
            )
        },
        submitPassword: function () {
            var userInfoPassword = $.trim($("input[name='userInfoPassword']").val());
            htajax.post("/ht-shiro/sysuser/editUser", {
                    "userId": box.userId,
                    "password": userInfoPassword
                },
                function (data) {
                    if (data.code == 10000) {
                        layer.msg("修改成功");
                        layer.closeAll();
                        // location.reload();
                        box.getOrderList();
                    }
                },
                function (data) {
                    //console.log(data)
                }
            )
        },
        addCompanyInfo: function () {
            this.reminder = true;
            $(".pick-show").remove();
            box.getcity1();
        },
        //信息列表
        getOrderList: function () {
            htajax.get("/ht-biz/oucomp/list", function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    box.orderList = data.data.records;
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        //编辑信息
        newAddList: function (id) {
            // console.log(id);
            this.textId = id;
            $(".pick-show").remove();
            this.reminder = true;
            htajax.get("/ht-biz/oucomp/edit?id=" + id, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.companyName = data.data.companyName;
                    box.province1 = data.data.province;
                    box.city1 = data.data.city;
                    box.area1 = data.data.area;
                    box.usertown1 = box.province1 + '/' + box.city1 + '/' + box.area1;
                    $(".pick-area5").attr('name', box.usertown1);
                    box.name = data.data.linkman;
                    box.phone = data.data.phone;
                    box.beizhu = data.data.remark;
                    box.getcity1();
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        //新增信息
        submitRegs: function () {
            var id = this.textId;
            var companyName = $.trim($("input[name='companyName']").val());
            var province = $(".pick-area5>.pick-show>.pick-province").text();
            var city = $('.pick-area5>.pick-show>.pick-city').text();
            var area = $('.pick-area5>.pick-show>.pick-county').text();
            var name = $.trim($("input[name='name']").val());
            var phone = $.trim($("input[name='phone']").val());
            var beizhu = this.beizhu;
            var regBox = {
                regMobile: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/, //手机
            }
            var mflag = regBox.regMobile.test(phone);
            if (companyName == '') { layer.msg("请填写企业名字"); return false; }
            if (province == '' || province == '请选择省') { layer.msg("请填写所在省"); return false; }
            if (city == '' || city == '请选择市') { layer.msg("请填写所在市"); return false; }
            if (area == '' || area == '请选择区') { layer.msg("请填写所在区"); return false; }
            if (name == '') { layer.msg("请填写联系人"); return false; }
            if (phone == '') { layer.msg("请填写手机号码"); return false; }
            if (!(mflag)) {layer.msg("请输入正确的手机号码");return false; }
            htajax.postForm("/ht-biz/oucomp/save", {
                id:id,
                companyName: companyName,
                province: province,
                city: city,
                area: area,
                linkman: name,
                phone: phone,
                remark: beizhu,
            }, function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    if (id == "" ){
                        box.saveframe1 = true; var timer = setTimeout(function () { box.saveframe1 = false; }, 1500);
                    }else{
                        box.saveframe2 = true; var timer = setTimeout(function () { box.saveframe2 = false; }, 1500);
                    }
                    box.reminder = false;
                    var timer = setTimeout(function () { location.reload(); }, 2000);
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        //删除信息
        deleteOrder: function (id) {
            htajax.get("/ht-biz/oucomp/deleted?id=" + id, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    // layer.msg("删除成功");
                    box.saveframe3 = true;
                    var timer = setTimeout(function () { box.saveframe3 = false; }, 1500);
                    box.getOrderList();
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