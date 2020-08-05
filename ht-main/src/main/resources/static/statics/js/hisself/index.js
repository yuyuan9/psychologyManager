var order = new Vue({
    el: '#estimate',
    data: {
        items: [],
        //tab切换
        isActive: 0,
        province: '',
        city: '',
        area: '',
        detailadrr:'',
        //暂存数据
        id: '',
        companyName: '',
        orgcode: '',
        regDate: '',
        contact: '',
        phone: '',
        email: '',

        productServ: '',
        productCogn: '',
        thirdfruit: '',
        onefruit: '',
        workerTotal: '',
        juniorTechTotal: '',
        developmentTotal: '',
        sciencePsersons: '',
        cog2008: true,
        compop: true,
        assetFirstValue: '',
        assetSecondValue: '',
        assetThirdValue: '',
        saleFirstValue: '',
        saleSecondValue: '',
        saleThirdValue: '',
        saleyfrate: '',
        prodrate: '',
        x1self: '',
        x1eg: '',
        x2self: '',
        x2eg: '',
        x3self: '',
        x3eg: '',
        x4self: '',
        x4eg: '',
        x5self: '',
        x5eg: '',
        x6self: '',
        x6eg: '',
        y1self: '',
        y1eg: '',
        y2self: '',
        y2eg: '',
        y3self: '',
        y3eg: '',
        other: '',
        compsys: '',
        cooper: '',
        platform: '',
        personnel: '',

        thisyear:'',
        lastyear1: '',
        lastyear2:'',
    },
    created: function () {
        this.initPage();
        setTimeout(function () { order.getUrlCity();},500)
    },
    mounted: function () {
        this.changeChose();
    },
    methods: {
        initPage: function () {
            this.getSelect();
            this.provincechange('', '', '');
            this.getUserInfo();
            this.backSave();
            this.getDateYear();
            
        },
        getDateYear:function(){
            var myDate = new Date();
            var myYear = myDate.getFullYear();  // 获取当前年份
            this.thisyear = myYear;
            this.lastyear1 = myYear - 1;
            this.lastyear2 = myYear - 2;
        },
        fmtDate: function (obj) {
            var date = new Date(obj);
            var y = 1900 + date.getYear();
            var m = "0" + (date.getMonth() + 1);
            var d = "0" + date.getDate();
            return y + "-" + m.substring(m.length - 2, m.length) + "-" + d.substring(d.length - 2, d.length);
        },
        //获取链接的城市
        getUrlCity:function(){
            var p= this.getUrlData("p");
            var c= this.getUrlData("c");
            if(p != '' && p != undefined && p != null){
                var province = p;
                var city = c;
                var area = '--选择区--';
                var selectDataUrl = "/statics/js/cityData.json";
                $.getJSON(selectDataUrl, function (json) {
                    order.areaData = json;
                    //var op = "<option value='" + obj.n + "'>" + text + "</option>";
                    var initOp1 = '  <option value="">--选择省--</option>';
                    if (province == null || province == "" || province == undefined) {
                        for (var i = 0; i < json.length; i++) {
                            var op = "<option value='" + json[i].n + "'>" + json[i].n + "</option>";
                            $("#province").append(op);
                        }
                    } else {
                        $("#province").empty();
                        var value1 = "<option value='" + province + "'>" + province + "</option>";
                        $("#province").append(value1);

                        for (var i = 0; i < json.length; i++) {
                            if (json[i].n != province) {
                                var op = "<option value='" + json[i].n + "'>" + json[i].n + "</option>";
                                $("#province").append(op);
                            }
                        }
                        $("#city").empty();
                        var value2 = "<option value='" + city + "'>" + city + "</option>";
                        $("#city").append(value2);
                        for (var i = 0; i < json.length; i++) {
                            if (json[i].n == province) {
                                for (var j = 0; j < json[i].s.length; j++) {
                                    if (json[i].s[j].n != city) {
                                        var op1 = "<option value='" + json[i].s[j].n + "'>" + json[i].s[j].n + "</option>";
                                        $("#city").append(op1);
                                    }
                                }
                                break;
                            }
                        }
                        $("#area").empty();
                        if (area == '' || area == null) {
                            $("#area").hide();
                            $("#area").val(" ");
                        } else {
                            $("#area").show();
                            var value3 = "<option value='" + area + "'>" + area + "</option>";
                            $("#area").append(value3);
                            for (var i = 0; i < json.length; i++) {
                                if (json[i].n == province) {
                                    for (var j = 0; j < json[i].s.length; j++) {
                                        if (json[i].s[j].n == city) {
                                            for (var k = 0; k < json[i].s[j].s.length; k++) {
                                                var op1 = "<option value='" + json[i].s[j].s[k].n + "'>" + json[i].s[j].s[k].n + "</option>";
                                                $("#area").append(op1);
                                            }

                                        }
                                    }
                                    return false;
                                }
                            }
                        }

                    }
                });
            }
            
        },
        // 获取用户的信息
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser", function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    // 用户已登录
                } else {
                    layer.msg("未登录");
                    var timer = setTimeout(function () {
                        window.location.href = '/ht-biz/login/login';
                    }, 2000);
                }

            }, function (data) {
                layer.msg(data.msg);
            });

        },
        //调用城市，选择添加

        provincechange: function (province, city, area) {
            var selectDataUrl = "/statics/js/cityData.json";
            $.getJSON(selectDataUrl, function (json) {
                order.areaData = json;
                //var op = "<option value='" + obj.n + "'>" + text + "</option>";
                var initOp1 = '  <option value="">--选择省--</option>';
                if (province == null || province == "" || province == undefined) {
                    for (var i = 0; i < json.length; i++) {
                        var op = "<option value='" + json[i].n + "'>" + json[i].n + "</option>";
                        $("#province").append(op);
                    }
                    //for (var i = 0; i < json[0].s.length; i++) {
                    //    var op1 = "<option value='" + json[0].s.n + "'>" + json[0].s.n + "</option>";
                    //    $("#province").append(op1)
                    //}
                } else {
                    $("#province").empty();
                    var value1 = "<option value='" + province + "'>" + province + "</option>";
                    $("#province").append(value1);

                    for (var i = 0; i < json.length; i++) {
                        if (json[i].n != province) {
                            var op = "<option value='" + json[i].n + "'>" + json[i].n + "</option>";
                            $("#province").append(op);
                        }
                    }

                    $("#city").empty();
                    var value2 = "<option value='" + city + "'>" + city + "</option>";
                    $("#city").append(value2);
                    for (var i = 0; i < json.length; i++) {
                        if (json[i].n == province) {
                            for (var j = 0; j < json[i].s.length; j++) {
                                if (json[i].s[j].n != city) {
                                    var op1 = "<option value='" + json[i].s[j].n + "'>" + json[i].s[j].n + "</option>";
                                    $("#city").append(op1);
                                }
                            }
                            break;
                        }
                    }

                    $("#area").empty();
                    if (area == '' || area == null) {
                        $("#area").hide();
                        $("#area").val(" ");
                    } else {
                        $("#area").show();
                        var value3 = "<option value='" + area + "'>" + area + "</option>";
                        $("#area").append(value3);
                        for (var i = 0; i < json.length; i++) {
                            if (json[i].n == province) {
                                for (var j = 0; j < json[i].s.length; j++) {
                                    if (json[i].s[j].n == city) {
                                        for (var k = 0; k < json[i].s[j].s.length; k++) {
                                            var op1 = "<option value='" + json[i].s[j].s[k].n + "'>" + json[i].s[j].s[k].n + "</option>";
                                            $("#area").append(op1);
                                        }

                                    }
                                }
                                return false;
                            }
                        }
                    }

                }
            });
        },

        //检查时间格式
        // checkDate: function(){
        //     var DATE_FORMAT = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
        //     var regDate = $("#regDate").val();
        //     if(DATE_FORMAT.test(regDate)){
        //         //layer.msg("您输入的日期格式正确");
        //     } else {
        //         layer.msg("抱歉，您输入的日期格式有误，正确格式应为'2000-01-01'");
        //         return false;
        //     }
        // },
        //省份
        changePro: function () {
            var choseProvice = $("#province").val();
            $("#area").empty();
            var option = "<option value=''>--选择区--</option>";
            $("#area").append(option);
            for (var i = 0; i < order.areaData.length; i++) {
                $("#city").empty();
                var option = "<option value=''>--选择市--</option>";
                $("#city").append(option);
                if (order.areaData[i].n == choseProvice) {
                    for (var j = 0; j < order.areaData[i].s.length; j++) {
                        var op1 = "<option value='" + order.areaData[i].s[j].n + "'>" + order.areaData[i].s[j].n + "</option>";
                        $("#city").append(op1);
                    }
                    return false;
                }
            }
        },
        //市
        changeCity: function () {
            var choseProvince = $("#province").val();
            var choseCity = $("#city").val();
            order.changeChose();
            $("#site").html(choseCity);
            for (var i = 0; i < order.areaData.length; i++) {
                if (order.areaData[i].n == choseProvince) {
                    for (var j = 0; j < order.areaData[i].s.length; j++) {
                        if (order.areaData[i].s[j].n == choseCity) {
                            if (order.areaData[i].s[j].s != null && order.areaData[i].s[j].s != "" && order.areaData[i].s[j].s != undefined) {
                                //有区
                                $("#area").show().empty();
                                var option = "<option value=''>--选择区--</option>";
                                $("#area").append(option);
                                for (var k = 0; k < order.areaData[i].s[j].s.length; k++) {
                                    var op1 = "<option value='" + order.areaData[i].s[j].s[k].n + "'>" + order.areaData[i].s[j].s[k].n + "</option>";
                                    $("#area").append(op1);
                                }

                            } else {
                                //没有区
                                $("#area").hide();
                                $("#area").val(" ");
                            }
                        }
                    }
                }
            }
        },

        //变化选项
        changeChose: function () {
            var choseProvince = $("#province").val();
            var choseCity = $("#city").val();
            //console.log(choseCity);
            if (choseCity.indexOf("广州市") >= 0) {
                $(".munber_gd").css({"display": "inline"});
                $(".munber").hide();
                $("#dazhuan").show();
                $(".munber3").show();
                $(".munber1").show();
                $(".yanfa1").hide();
                $(".yanfa2").hide();
                $(".yanfa3").hide();
                $(".yanfa4").hide();
                $(".yanfa5").show();
                $(".high1").hide();
                $(".high2").show();
                $(".high3").hide();

            } else if (choseCity.indexOf("金华市") >= 0) { //判断城市
                $(".munber_gd").hide();
                $(".munber").show();
                $(".munber3").show();
                $(".munber1").show();
                $(".yanfa1").hide();
                $(".yanfa2").hide();
                $(".yanfa3").show();
                $(".yanfa4").hide();
                $(".yanfa5").hide();
                $(".high1").show();
                $(".high2").hide();
                $(".high3").hide();
            } else if (choseCity.indexOf("淮安市") >= 0) {
                $(".munber_gd").hide();
                $(".munber").show();
                $(".munber3").show();
                $("#oneFruitDisplay").hide();
                $("#dazhuan").hide();
                $(".munber1").show();
                $(".yanfa1").hide();
                $(".yanfa2").hide();
                $(".yanfa3").hide();
                $(".yanfa4").hide();
                $(".yanfa5").show();
                $(".high1").show();
                $(".high2").hide();
                $(".high3").hide();
            } else if (choseCity.indexOf("苏州市") >= 0) {
                $(".munber_gd").hide();
                $(".munber").hide();
                $(".munber3").show();
                $(".munber1").show();
                $(".yanfa1").hide();
                $(".yanfa2").hide();
                $(".change").text("2");
                $(".yanfa3").hide();
                $(".yanfa4").hide();
                $(".yanfa5").show();
                $(".high1").show();
                $(".high2").hide();
                $(".high3").hide();
            } else {
                $(".munber_gd").hide();
                $(".munber").hide();
                // $(".munber3").hide();
                $(".munber1").show();
                $(".yanfa2").show();
                $(".yanfa1").hide();
                $(".yanfa3").hide();
                $(".yanfa4").hide();
                $(".yanfa5").hide();
                $(".high1").hide();
                $(".high2").hide();
                $(".high3").show();
            }
        },

        
        //获取url的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)')
                .exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        //继续评估
        backSave: function () {
            var ids = this.getUrlData("id");
            if (ids != '' && ids != null) {
                htajax.get("/ht-biz/hisself/findById?id=" + ids, function (data) {
                    if (data.code = 10000) {
                        //console.log(data);
                        var province = data.data.province;
                        var city = data.data.city;
                        var area = data.data.area;
                        var selectDataUrl = "/statics/js/cityData.json";
                        $.getJSON(selectDataUrl, function (json) {
                            order.areaData = json;
                            //var op = "<option value='" + obj.n + "'>" + text + "</option>";
                            var initOp1 = '  <option value="">--选择省--</option>';
                            if (province == null || province == "" || province == undefined) {
                                for (var i = 0; i < json.length; i++) {
                                    var op = "<option value='" + json[i].n + "'>" + json[i].n + "</option>";
                                    $("#province").append(op);
                                }
                            } else {
                                $("#province").empty();
                                var value1 = "<option value='" + province + "'>" + province + "</option>";
                                $("#province").append(value1);

                                for (var i = 0; i < json.length; i++) {
                                    if (json[i].n != province) {
                                        var op = "<option value='" + json[i].n + "'>" + json[i].n + "</option>";
                                        $("#province").append(op);
                                    }
                                }

                                $("#city").empty();
                                var value2 = "<option value='" + city + "'>" + city + "</option>";
                                $("#city").append(value2);
                                for (var i = 0; i < json.length; i++) {
                                    if (json[i].n == province) {
                                        for (var j = 0; j < json[i].s.length; j++) {
                                            if (json[i].s[j].n != city) {
                                                var op1 = "<option value='" + json[i].s[j].n + "'>" + json[i].s[j].n + "</option>";
                                                $("#city").append(op1);
                                            }
                                        }
                                        break;
                                    }
                                }

                                $("#area").empty();
                                if (area == '' || area == null) {
                                    $("#area").hide();
                                    $("#area").val(" ");
                                } else {
                                    $("#area").show();
                                    var value3 = "<option value='" + area + "'>" + area + "</option>";
                                    $("#area").append(value3);
                                    for (var i = 0; i < json.length; i++) {
                                        if (json[i].n == province) {
                                            for (var j = 0; j < json[i].s.length; j++) {
                                                if (json[i].s[j].n == city) {
                                                    for (var k = 0; k < json[i].s[j].s.length; k++) {
                                                        var op1 = "<option value='" + json[i].s[j].s[k].n + "'>" + json[i].s[j].s[k].n + "</option>";
                                                        $("#area").append(op1);
                                                    }

                                                }
                                            }
                                            return false;
                                        }
                                    }
                                }

                            }
                        });
                        order.companyName = data.data.companyName;
                        //$.trim($("input[name='companyName']").val()) = data.data.companyName;
                        order.orgcode = data.data.orgcode;
                        $("input[name='regDate']").val(data.data.regDate.substring(0, 10));
                        
                        order.contact = data.data.contact;
                        order.phone = data.data.phone;
                        order.email = data.data.email;
                        order.productServ = data.data.productServ;
                        order.productCogn = data.data.productCogn;
                        order.thirdfruit = data.data.thirdfruit;
                        order.onefruit = data.data.onefruit;
                        order.workerTotal = data.data.workerTotal;
                        order.juniorTechTotal = data.data.juniorTechTotal;
                        order.developmentTotal = data.data.developmentTotal;
                        order.sciencePsersons = data.data.sciencePsersons;
                        order.cog2008 = data.data.cog2008;
                        order.compop = data.data.compop;
                        order.assetFirstValue = data.data.assetFirstValue;
                        order.assetSecondValue = data.data.assetSecondValue;
                        order.assetThirdValue = data.data.assetThirdValue;
                        order.saleFirstValue = data.data.saleFirstValue;
                        order.saleSecondValue = data.data.saleSecondValue;
                        order.saleThirdValue = data.data.saleThirdValue;
                        
                        if(city == "广州市"){
                            $("input[name='saleyfrate2'][value='" + data.data.saleyfrate + "']").prop("checked", true);
                        }
                        else if(city == "金华市"){
                            $("input[name='saleyfrate1'][value='" + data.data.saleyfrate + "']").prop("checked", true);
                        }
                        else if(city == "淮安市"){
                            $("input[name='saleyfrate2'][value='" + data.data.saleyfrate + "']").prop("checked", true);
                        }
                        else if(city == "苏州市"){
                            $("input[name='saleyfrate2'][value='" + data.data.saleyfrate + "']").prop("checked", true);
                        }
                        else{
                            $("input[name='saleyfrate3'][value='" + data.data.saleyfrate + "']").prop("checked", true);
                        }
                        order.changeChose();
                        $("input[name='jnyfrate'][value='" + data.data.jnyfrate + "']").prop("checked", true);

                        if(city == "广州市"){
                            $("input[name='prodrate2'][value='" + data.data.prodrate + "']").prop("checked", true);
                        }
                        else if(city == "金华市"){
                            $("input[name='prodrate1'][value='" + data.data.prodrate + "']").prop("checked", true);
                        }
                        else if(city == "淮安市"){
                            $("input[name='prodrate1'][value='" + data.data.prodrate + "']").prop("checked", true);
                        }
                        else if(city == "苏州市"){
                            $("input[name='prodrate1'][value='" + data.data.prodrate + "']").prop("checked", true);
                        }
                        else{
                            $("input[name='prodrate3'][value='" + data.data.prodrate + "']").prop("checked", true);
                        }

                        //3  18
                        var x1self = data.data.x1self ? data.data.x1self : 0;
                        order.x1self = x1self;
                        var x1eg = data.data.x1eg ? data.data.x1eg : 0;
                        order.x1eg = x1eg;

                        var x2self = data.data.x2self ? data.data.x2self : 0;
                        order.x2self = x2self;
                        var x2eg = data.data.x2eg ? data.data.x2eg : 0;
                        order.x2eg = x2eg;

                        var x3self = data.data.x3self ? data.data.x3self : 0;
                        var x3eg = data.data.x3eg ? data.data.x3eg : 0;
                        order.x3self = x3self;
                        order.x3eg = x3eg;

                        var x4self = data.data.x4self ? data.data.x4self : 0;
                        var x4eg = data.data.x4eg ? data.data.x4eg : 0;
                        order.x4self = x4self;
                        order.x4eg = x4eg;

                        var x5self = data.data.x5self ? data.data.x5self : 0;
                        var x5eg = data.data.x5eg ? data.data.x5eg : 0;
                        order.x5self = x5self;
                        order.x5eg = x5eg;

                        var x6self = data.data.x6self ? data.data.x6self : 0;
                        var x6eg = data.data.x6eg ? data.data.x6eg : 0;
                        order.x6self = x6self;
                        order.x6eg = x6eg;

                        var totalX = x1self + x1eg + x2self + x2eg + x3self + x3eg + x4self + x4eg + x5self + x5eg + x6self + x6eg;
                        $("#xtotal_span").text(totalX);


                        var y1self = data.data.y1self ? data.data.y1self : 0;
                        var y1eg = data.data.y1eg ? data.data.y1eg : 0;
                        order.y1self = y1self;
                        order.y1eg = y1eg;

                        var y2self = data.data.y2self ? data.data.y2self : 0;
                        var y2eg = data.data.y2eg ? data.data.y2eg : 0;
                        order.y2self = y2self;
                        order.y2eg = y2eg;

                        var y3self = data.data.y3self ? data.data.y3self : 0;
                        var y3eg = data.data.y3eg ? data.data.y3eg : 0;
                        order.y3self = y3self;
                        order.y3eg = y3eg;

                        var totalY = y1self + y1eg + y2self + y2eg + y3self + y3eg;
                        $("#ytotal_span").text(totalY);

                        if (data.data.other == 1) {
                            $("input[name='other'][value=true]").prop("checked", true);
                        } else if (data.data.other == 0) {
                            $("input[name='other'][value=false]").prop("checked", true);
                        }

                        //4
                        $("input[name='compsys'][value='" + data.data.compsys + "']").prop("checked", true);
                        $("input[name='cooper'][value='" + data.data.cooper + "']").prop("checked", true);
                        $("input[name='platform'][value='" + data.data.platform + "']").prop("checked", true);
                        $("input[name='personnel'][value='" + data.data.personnel + "']").prop("checked", true);
                    }
                }, function (data) {
                    //
                })
            }
        },
        // 暂存
        zcClick: function (tabid) {
            
            var ii = layer.load();
            setTimeout(function () {
                layer.close(ii);
            }, 1000);
            //var dats = $("#inputForm").serialize();
            var id=$.trim($("input[name='id']").val());
            var companyName = $.trim($("input[name='companyName']").val());
            var orgcode = $.trim($("input[name='orgcode']").val());
            var regDate = $.trim($("input[name='regDate']").val());
            var cog2008 = $.trim($("input[name='cog2008']").val());
            var contact = $.trim($("input[name='contact']").val());
            var phone = $.trim($("input[name='phone']").val());
            var email = $.trim($("input[name='email']").val());
            var province = $('#province option:selected').val();
            var city = $('#city option:selected').val();
            var area = $.trim($("select[name='area']").val());
            var productServ = $.trim($("select[name='productServ']").val());
            var productCogn = $.trim($("select[name='productCogn']").val());
            var thirdfruit = $.trim($("input[name='thirdfruit']").val());
            var onefruit = $.trim($("input[name='onefruit']").val());
            var workerTotal = $.trim($("input[name='workerTotal']").val());
            var juniorTechTotal = $.trim($("input[name='juniorTechTotal']").val());
            var developmentTotal = $.trim($("input[name='developmentTotal']").val());
            var sciencePsersons = $.trim($("input[name='sciencePsersons']").val());
            var compop = $.trim($("input[name='compop']").val());

            var assetFirstValue = $('input[name="assetFirstValue"]').val();
            var assetSecondValue = $('input[name="assetSecondValue"]').val();
            var assetThirdValue = $('input[name="assetThirdValue"]').val();
            var saleFirstValue = $('input[name="saleFirstValue"]').val();
            var saleSecondValue = $('input[name="saleSecondValue"]').val();
            var saleThirdValue = $('input[name="saleThirdValue"]').val();
            if(city == "广州市"){
                var saleyfrate = $('input:radio[name="saleyfrate2"]:checked').val();
            }
            else if(city == "金华市"){
                var saleyfrate = $('input:radio[name="saleyfrate1"]:checked').val();
            }
            else if(city == "淮安市"){
                var saleyfrate = $('input:radio[name="saleyfrate2"]:checked').val();
            }
            else if(city == "苏州市"){
                var saleyfrate = $('input:radio[name="saleyfrate2"]:checked').val();
            }
            else{
                var saleyfrate = $('input:radio[name="saleyfrate3"]:checked').val();
            }
            
            var jnyfrate = $('input:radio[name="jnyfrate"]:checked').val();


            if(city == "广州市"){
                var prodrate = $('input:radio[name="prodrate2"]:checked').val();
            }
            else if(city == "金华市"){
                var prodrate = $('input:radio[name="prodrate1"]:checked').val();
            }
            else if(city == "淮安市"){
                var prodrate = $('input:radio[name="prodrate1"]:checked').val();
            }
            else if(city == "苏州市"){
                var prodrate = $('input:radio[name="prodrate1"]:checked').val();
            }
            else{
                var prodrate = $('input:radio[name="prodrate3"]:checked').val();
            }

            if (saleyfrate == undefined) {
                saleyfrate = '';
            }
            if (jnyfrate == undefined) {
                jnyfrate = '';
            }
            if (prodrate == undefined) {
                prodrate = '';
            }
            var x1self = $('input[name="x1self"]').val();
            var x1eg = $('input[name="x1eg"]').val();
            var x2self = $('input[name="x2self"]').val();
            var x2eg = $('input[name="x2eg"]').val();
            var x3self = $('input[name="x3self"]').val();
            var x3eg = $('input[name="x3eg"]').val();
            var x4self = $('input[name="x4self"]').val();
            var x4eg = $('input[name="x4eg"]').val();
            var x5self = $('input[name="x5self"]').val();
            var x5eg = $('input[name="x5eg"]').val();
            var x6self = $('input[name="x6self"]').val();
            var x6eg = $('input[name="x6eg"]').val();
            var y1self = $('input[name="y1self"]').val();
            var y1eg = $('input[name="y1eg"]').val();
            var y2self = $('input[name="y2self"]').val();
            var y2eg = $('input[name="y2eg"]').val();
            var y3self = $('input[name="y3self"]').val();
            var y3eg = $('input[name="y3eg"]').val();
            var other = $('input:radio[name="other"]:checked').val();
            var compsys = $('input:radio[name="compsys"]:checked').val();
            var cooper = $('input:radio[name="cooper"]:checked').val();
            var platform = $('input:radio[name="platform"]:checked').val();
            var personnel = $('input:radio[name="personnel"]:checked').val();
            if (other == undefined) {
                other = '';
            }
            if (compsys == undefined) {
                compsys = '';
            }
            if (cooper == undefined) {
                cooper = '';
            }
            if (platform == undefined) {
                platform = '';
            }
            if (personnel == undefined) {
                personnel = '';
            }
            if (orgcode != '') {
                //if(order.checkValidator(tabid)){}
                htajax.postForm('/ht-biz/hisself/saveTemporary', {
                        'id': id,
                        'companyName': companyName,
                        'orgcode': orgcode,
                        'regDate': regDate,
                        'contact': contact,
                        'phone': phone,
                        'email': email,
                        'province': province,
                        'city': city,
                        'area': area,
                        'productServ': productServ,
                        'productCogn': productCogn,
                        'thirdfruit': thirdfruit,
                        'onefruit': onefruit,
                        'workerTotal': workerTotal,
                        'juniorTechTotal': juniorTechTotal,
                        'developmentTotal': developmentTotal,
                        'sciencePsersons': sciencePsersons,
                        'cog2008': cog2008,
                        'compop': compop,
                        'assetFirstValue': assetFirstValue,
                        'assetSecondValue': assetSecondValue,
                        'assetThirdValue': assetThirdValue,
                        'saleFirstValue': saleFirstValue,
                        'saleSecondValue': saleSecondValue,
                        'saleThirdValue': saleThirdValue,
                        'saleyfrate': saleyfrate,
                        'jnyfrate':jnyfrate,
                        'prodrate': prodrate,
                        'x1self': x1self,
                        'x1eg': x1eg,
                        'x2self': x2self,
                        'x2eg': x2eg,
                        'x3self': x3self,
                        'x3eg': x3eg,
                        'x4self': x4self,
                        'x4eg': x4eg,
                        'x5self': x5self,
                        'x5eg': x5eg,
                        'x6self': x6self,
                        'x6eg': x6eg,
                        'y1self': y1self,
                        'y1eg': y1eg,
                        'y2self': y2self,
                        'y2eg': y2eg,
                        'y3self': y3self,
                        'y3eg': y3eg,
                        'other': other,
                        'compsys': compsys,
                        'cooper': cooper,
                        'platform': platform,
                        'personnel': personnel
                        
                    },
                    function (data) {
                        if (data.code == 10000) {
                            $("input[name='id']").val(data.data.id);
                            layer.msg('暂存成功');
                        } else {
                            layer.msg('暂存失败');
                        }
                    },
                    function (data) {
                        layer.msg('暂存失败');
                        //.log(data);
                        //错误回调
                    })

            } else {
                layer.msg('社会统一信用代码/组织机构代码不能为空');
            }

        },
        //信用代码记录
        initData: function () {
            var orgcode = $("input[name='orgcode']").val();
            if(orgcode != ''){
                var initUrl = "/ht-biz/hisself/findByOrgcode?orgcode=" + orgcode;
                htajax.get(initUrl, function (data) {
                        if (data.code == 10000) {
                            //console.log(data);
                            if (data.data == null || data.data == '') {
                                // $('#inputForm')[0].reset();
                                // $("input[name='orgcode']").val(orgcode);
                                // $("input[name='saleyfrate']").prop("checked", false);
                                // $("input[name='jnyfrate']").prop("checked", false);
                                // $("input[name='prodrate']").prop("checked", false);
                                // $("input[name='compsys']").prop("checked", false);
                                // $("input[name='cooper']").prop("checked", false);
                                // $("input[name='platform']").prop("checked", false);
                                // $("input[name='personnel']").prop("checked", false);
                                // $("#area").val('');
                                // order.cog2008 = true;
                                // order.compop = -1;
                                // document.getElementById("productServ").options[0].selected = true;
                                // document.getElementById("situation1").options[0].selected = true;
                                // $('#xtotal_span').text('0');
                                // $('#ytotal_span').text('0');
                                return false;
                            }else{
                                var province = data.data.province;
                                var city = data.data.city;
                                var area = data.data.area;
                                var selectDataUrl = "/statics/js/cityData.json";
                                $.getJSON(selectDataUrl, function (json) {
                                    order.areaData = json;
                                    //var op = "<option value='" + obj.n + "'>" + text + "</option>";
                                    var initOp1 = '  <option value="">--选择省--</option>';
                                    if (province == null || province == "" || province == undefined) {
                                        for (var i = 0; i < json.length; i++) {
                                            var op = "<option value='" + json[i].n + "'>" + json[i].n + "</option>";
                                            $("#province").append(op);
                                        }
                                    } else {
                                        $("#province").empty();
                                        var value1 = "<option value='" + province + "'>" + province + "</option>";
                                        $("#province").append(value1);

                                        for (var i = 0; i < json.length; i++) {
                                            if (json[i].n != province) {
                                                var op = "<option value='" + json[i].n + "'>" + json[i].n + "</option>";
                                                $("#province").append(op);
                                            }
                                        }

                                        $("#city").empty();
                                        var value2 = "<option value='" + city + "'>" + city + "</option>";
                                        $("#city").append(value2);
                                        for (var i = 0; i < json.length; i++) {
                                            if (json[i].n == province) {
                                                for (var j = 0; j < json[i].s.length; j++) {
                                                    if (json[i].s[j].n != city) {
                                                        var op1 = "<option value='" + json[i].s[j].n + "'>" + json[i].s[j].n + "</option>";
                                                        $("#city").append(op1);
                                                    }
                                                }
                                                break;
                                            }
                                        }

                                        $("#area").empty();
                                        if (area == '' || area == null) {
                                            $("#area").hide();
                                            $("#area").val(" ");
                                        } else {
                                            $("#area").show();
                                            var value3 = "<option value='" + area + "'>" + area + "</option>";
                                            $("#area").append(value3);
                                            for (var i = 0; i < json.length; i++) {
                                                if (json[i].n == province) {
                                                    for (var j = 0; j < json[i].s.length; j++) {
                                                        if (json[i].s[j].n == city) {
                                                            for (var k = 0; k < json[i].s[j].s.length; k++) {
                                                                var op1 = "<option value='" + json[i].s[j].s[k].n + "'>" + json[i].s[j].s[k].n + "</option>";
                                                                $("#area").append(op1);
                                                            }

                                                        }
                                                    }
                                                    return false;
                                                }
                                            }
                                        }

                                    }
                                });
                                order.companyName = data.data.companyName;
                                //$.trim($("input[name='companyName']").val()) = data.data.companyName;
                                order.orgcode = data.data.orgcode;
                                $("input[name='regDate']").val(data.data.regDate.substring(0, 10));
                                
                                order.contact = data.data.contact;
                                order.phone = data.data.phone;
                                order.email = data.data.email;
                                order.productServ = data.data.productServ;
                               // $('#productServ').children("option[value='" + data.data.productServ + '&3' + "']").prop("selected", true);

                                $("select[name='regDate']").val(data.data.regDate.substring(0, 10));
                                order.productCogn = data.data.productCogn;
                                order.thirdfruit = data.data.thirdfruit;
                                order.onefruit = data.data.onefruit;
                                order.workerTotal = data.data.workerTotal;
                                order.juniorTechTotal = data.data.juniorTechTotal;
                                order.developmentTotal = data.data.developmentTotal;
                                order.sciencePsersons = data.data.sciencePsersons;
                                order.cog2008 = data.data.cog2008;
                                order.compop = data.data.compop;
                                order.assetFirstValue = data.data.assetFirstValue;
                                order.assetSecondValue = data.data.assetSecondValue;
                                order.assetThirdValue = data.data.assetThirdValue;
                                order.saleFirstValue = data.data.saleFirstValue;
                                order.saleSecondValue = data.data.saleSecondValue;
                                order.saleThirdValue = data.data.saleThirdValue;
                                if(city == "广州市"){
                                    $("input[name='saleyfrate2'][value='" + data.data.saleyfrate + "']").prop("checked", true);
                                }
                                else if(city == "金华市"){
                                    $("input[name='saleyfrate1'][value='" + data.data.saleyfrate + "']").prop("checked", true);
                                }
                                else if(city == "淮安市"){
                                    $("input[name='saleyfrate2'][value='" + data.data.saleyfrate + "']").prop("checked", true);
                                }
                                else if(city == "苏州市"){
                                    $("input[name='saleyfrate2'][value='" + data.data.saleyfrate + "']").prop("checked", true);
                                }
                                else{
                                    $("input[name='saleyfrate3'][value='" + data.data.saleyfrate + "']").prop("checked", true);
                                }
                                order.changeChose();
                                
                                $("input[name='jnyfrate'][value='" + data.data.jnyfrate + "']").prop("checked", true);

                                if(city == "广州市"){
                                    $("input[name='prodrate2'][value='" + data.data.prodrate + "']").prop("checked", true);
                                }
                                else if(city == "金华市"){
                                    $("input[name='prodrate1'][value='" + data.data.prodrate + "']").prop("checked", true);
                                }
                                else if(city == "淮安市"){
                                    $("input[name='prodrate1'][value='" + data.data.prodrate + "']").prop("checked", true);
                                }
                                else if(city == "苏州市"){
                                    $("input[name='prodrate1'][value='" + data.data.prodrate + "']").prop("checked", true);
                                }
                                else{
                                    $("input[name='prodrate3'][value='" + data.data.prodrate + "']").prop("checked", true);
                                }
                                
                                //3  18
                                var x1self = data.data.x1self ? data.data.x1self : 0;
                                order.x1self = x1self;
                                var x1eg = data.data.x1eg ? data.data.x1eg : 0;
                                order.x1eg = x1eg;

                                var x2self = data.data.x2self ? data.data.x2self : 0;
                                order.x2self = x2self;
                                var x2eg = data.data.x2eg ? data.data.x2eg : 0;
                                order.x2eg = x2eg;

                                var x3self = data.data.x3self ? data.data.x3self : 0;
                                var x3eg = data.data.x3eg ? data.data.x3eg : 0;
                                order.x3self = x3self;
                                order.x3eg = x3eg;

                                var x4self = data.data.x4self ? data.data.x4self : 0;
                                var x4eg = data.data.x4eg ? data.data.x4eg : 0;
                                order.x4self = x4self;
                                order.x4eg = x4eg;

                                var x5self = data.data.x5self ? data.data.x5self : 0;
                                var x5eg = data.data.x5eg ? data.data.x5eg : 0;
                                order.x5self = x5self;
                                order.x5eg = x5eg;

                                var x6self = data.data.x6self ? data.data.x6self : 0;
                                var x6eg = data.data.x6eg ? data.data.x6eg : 0;
                                order.x6self = x6self;
                                order.x6eg = x6eg;

                                var totalX = x1self + x1eg + x2self + x2eg + x3self + x3eg + x4self + x4eg + x5self + x5eg + x6self + x6eg;
                                $("#xtotal_span").text(totalX);


                                var y1self = data.data.y1self ? data.data.y1self : 0;
                                var y1eg = data.data.y1eg ? data.data.y1eg : 0;
                                order.y1self = y1self;
                                order.y1eg = y1eg;

                                var y2self = data.data.y2self ? data.data.y2self : 0;
                                var y2eg = data.data.y2eg ? data.data.y2eg : 0;
                                order.y2self = y2self;
                                order.y2eg = y2eg;

                                var y3self = data.data.y3self ? data.data.y3self : 0;
                                var y3eg = data.data.y3eg ? data.data.y3eg : 0;
                                order.y3self = y3self;
                                order.y3eg = y3eg;

                                var totalY = y1self + y1eg + y2self + y2eg + y3self + y3eg;
                                $("#ytotal_span").text(totalY);

                                if (data.data.other == 1) {
                                    $("input[name='other'][value=true]").prop("checked", true);
                                } else if (data.data.other == 0) {
                                    $("input[name='other'][value=false]").prop("checked", true);
                                }

                                //4
                                $("input[name='compsys'][value='" + data.data.compsys + "']").prop("checked", true);
                                $("input[name='cooper'][value='" + data.data.cooper + "']").prop("checked", true);
                                $("input[name='platform'][value='" + data.data.platform + "']").prop("checked", true);
                                $("input[name='personnel'][value='" + data.data.personnel + "']").prop("checked", true);
                            }
                        }
                    },
                    function () {
                        //错误回调
                    });
                order.changeChose();
            }
            
        },

        nextSetp: function (id) {
            if (id == 'a_finance') {
                if (order.validator()) {
                    $('html , body').animate({
                        scrollTop: 0
                    }, 'slow');
                    this.isActive = 1;
                    return;
                }
            }
            if (id == 'a_knowledge') {
                if (order.validator() && order.checkKnowledge()) {
                    $('html , body').animate({
                        scrollTop: 0
                    }, 'slow');
                    this.isActive = 2;
                    return;
                }
            }
            if (id == 'a_knowledge1') {
                if (order.validator()) {
                    $('html , body').animate({
                        scrollTop: 0
                    }, 'slow');
                    this.isActive = 1;
                    return;
                }
            }
            if (id == 'a_manage') {
                if (order.validator() && order.checkKnowledge()) {
                    $('html , body').animate({
                        scrollTop: 0
                    }, 'slow');
                    this.isActive = 3;
                    return;
                }
            }
            if (id == 'a_manage1') {
                if (order.validator() && order.checkKnowledge()) {
                    $('html , body').animate({
                        scrollTop: 0
                    }, 'slow');
                    this.isActive = 2;
                    return;
                }
            }
            if (id == "end") {
                if (order.validator() && order.checkKnowledge()) {
                    $('html , body').animate({
                        scrollTop: 0
                    }, 'slow');
                    this.isActive = 3;
                    return;
                }
            }
        },
        //主营产品技术领域
        getSelect: function () { 
            htajax.get("/ht-biz/catalog/getjson?type=" + 'techfield', function (data) {
                if (data.code == 10000) {
                    $.each(data.data, function (i, item) {
                        $("#productServ").append("<option value='" + item.value + "&" + item.grade + "' "  + ">&nbsp;&nbsp;&nbsp;&nbsp;" + item.name + "</option>");
                        $.each(item.list, function (i, item) {
                            $("#productServ").append("<option value='" + item.value + "&" + item.grade + "' " + ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + item.name + "</option>");
                            $.each(item.list, function (i, item) {
                                $("#productServ").append("<option value='" + item.value + "&" + item.grade + "' " + ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + item.name + "</option>");
                            });
                        });
                    });
                }
            }, function (data) {
                //错误回调
            });
        },
        calcX: function () {
            var x1self = parseInt($.trim($('input[name="x1self"]').val())) ? parseInt($.trim($('input[name="x1self"]').val())) : 0;
            var x1eg = parseInt($.trim($('input[name="x1eg"]').val())) ? parseInt($.trim($('input[name="x1eg"]').val())) : 0;
            var x2self = parseInt($.trim($('input[name="x2self"]').val())) ? parseInt($.trim($('input[name="x2self"]').val())) : 0;
            var x2eg = parseInt($.trim($('input[name="x2eg"]').val())) ? parseInt($.trim($('input[name="x2eg"]').val())) : 0;
            var x3self = parseInt($.trim($('input[name="x3self"]').val())) ? parseInt($.trim($('input[name="x3self"]').val())) : 0;
            var x3eg = parseInt($.trim($('input[name="x3eg"]').val())) ? parseInt($.trim($('input[name="x3eg"]').val())) : 0;
            var x4self = parseInt($.trim($('input[name="x4self"]').val())) ? parseInt($.trim($('input[name="x4self"]').val())) : 0;
            var x4eg = parseInt($.trim($('input[name="x4eg"]').val())) ? parseInt($.trim($('input[name="x4eg"]').val())) : 0;
            var x5self = parseInt($.trim($('input[name="x5self"]').val())) ? parseInt($.trim($('input[name="x5self"]').val())) : 0;
            var x5eg = parseInt($.trim($('input[name="x5eg"]').val())) ? parseInt($.trim($('input[name="x5eg"]').val())) : 0;
            var x6self = parseInt($.trim($('input[name="x6self"]').val())) ? parseInt($.trim($('input[name="x6self"]').val())) : 0;
            var x6eg = parseInt($.trim($('input[name="x6eg"]').val())) ? parseInt($.trim($('input[name="x6eg"]').val())) : 0;
            var x1 = x1self + x1eg;
            $("input[name='x1']").val(x1);
            var x2 = x2self + x2eg;
            $("input[name='x2']").val(x2);
            var x3 = x3self + x3eg;
            $("input[name='x3']").val(x3);
            var x4 = x4self + x4eg;
            $("input[name='x4']").val(x4);
            var x5 = x5self + x5eg;
            $("input[name='x5']").val(x5);
            var x6 = x6self + x6eg;
            $("input[name='x6']").val(x6);
            var totalX = x1self + x1eg + x2self + x2eg + x3self + x3eg + x4self + x4eg + x5self + x5eg + x6self + x6eg;
            $("#xtotal_span").text(totalX);
        },
        calcY: function () {
            var y1self = parseInt($.trim($('input[name="y1self"]').val())) ? parseInt($.trim($('input[name="y1self"]').val())) : 0;
            var y1eg = parseInt($.trim($('input[name="y1eg"]').val())) ? parseInt($.trim($('input[name="y1eg"]').val())) : 0;
            var y2self = parseInt($.trim($('input[name="y2self"]').val())) ? parseInt($.trim($('input[name="y2self"]').val())) : 0;
            var y2eg = parseInt($.trim($('input[name="y2eg"]').val())) ? parseInt($.trim($('input[name="y2eg"]').val())) : 0;
            var y3self = parseInt($.trim($('input[name="y3self"]').val())) ? parseInt($.trim($('input[name="y3self"]').val())) : 0;
            var y3eg = parseInt($.trim($('input[name="y3eg"]').val())) ? parseInt($.trim($('input[name="y3eg"]').val())) : 0;
            var y1 = y1self + y1eg;
            $("input[name='y1']").val(y1);
            var y2 = y2self + y2eg;
            $("input[name='y2']").val(y2);
            var y3 = y3self + y3eg;
            $("input[name='y3']").val(y3);
            var totalY = y1self + y1eg + y2self + y2eg + y3self + y3eg;
            $("#ytotal_span").text(totalY);
        },
        //提交
        inputFormSubmit: function () {
            var ii = layer.load();
            setTimeout(function () {
                layer.close(ii);
            }, 1000);

            if (this.checkResearch()) {
                if (!order.validator()) {
                    return false;
                }
                if (!order.checkKnowledge()) {
                    return false;
                }
                this.submitDefault();
                // var dats = $("#inputForm").serialize();
                var id=$.trim($("input[name='id']").val());
                var companyName = $.trim($("input[name='companyName']").val());
                var orgcode = $.trim($("input[name='orgcode']").val());
                var regDate = $.trim($("input[name='regDate']").val());
                var cog2008 = $.trim($("input[name='cog2008']").val());
                var contact = $.trim($("input[name='contact']").val());
                var phone = $.trim($("input[name='phone']").val());
                var email = $.trim($("input[name='email']").val());
                var province = $('#province option:selected').val();
                var city = $('#city option:selected').val();
                var area = $.trim($("select[name='area']").val());
                var productServ = $.trim($("select[name='productServ']").val());
                //productServ=productServ.substring(0,productServ.length-2);

                var productCogn = $.trim($("select[name='productCogn']").val());
                var thirdfruit = $.trim($("input[name='thirdfruit']").val());
                var onefruit = $.trim($("input[name='onefruit']").val());
                var workerTotal = $.trim($("input[name='workerTotal']").val());
                var juniorTechTotal = $.trim($("input[name='juniorTechTotal']").val());
                var developmentTotal = $.trim($("input[name='developmentTotal']").val());
                var sciencePsersons = $.trim($("input[name='sciencePsersons']").val());
                var compop = $.trim($("input[name='compop']").val());
    
                var assetFirstValue = $('input[name="assetFirstValue"]').val();
                var assetSecondValue = $('input[name="assetSecondValue"]').val();
                var assetThirdValue = $('input[name="assetThirdValue"]').val();
                var saleFirstValue = $('input[name="saleFirstValue"]').val();
                var saleSecondValue = $('input[name="saleSecondValue"]').val();
                var saleThirdValue = $('input[name="saleThirdValue"]').val();
                if(city == "广州市"){
                    var saleyfrate = $('input:radio[name="saleyfrate2"]:checked').val();
                }
                else if(city == "金华市"){
                    var saleyfrate = $('input:radio[name="saleyfrate1"]:checked').val();
                }
                else if(city == "淮安市"){
                    var saleyfrate = $('input:radio[name="saleyfrate2"]:checked').val();
                }
                else if(city == "苏州市"){
                    var saleyfrate = $('input:radio[name="saleyfrate2"]:checked').val();
                }
                else{
                    var saleyfrate = $('input:radio[name="saleyfrate3"]:checked').val();
                }
                
                var jnyfrate = $('input:radio[name="jnyfrate"]:checked').val();


                if(city == "广州市"){
                    var prodrate = $('input:radio[name="prodrate2"]:checked').val();
                }
                else if(city == "金华市"){
                    var prodrate = $('input:radio[name="prodrate1"]:checked').val();
                }
                else if(city == "淮安市"){
                    var prodrate = $('input:radio[name="prodrate1"]:checked').val();
                }
                else if(city == "苏州市"){
                    var prodrate = $('input:radio[name="prodrate1"]:checked').val();
                }
                else{
                    var prodrate = $('input:radio[name="prodrate3"]:checked').val();
                }



                if (saleyfrate == undefined) {
                    saleyfrate = '';
                }
                if (jnyfrate == undefined) {
                    jnyfrate = '';
                }
                if (prodrate == undefined) {
                    prodrate = '';
                }
                var x1self = $('input[name="x1self"]').val();
                var x1eg = $('input[name="x1eg"]').val();
                var x2self = $('input[name="x2self"]').val();
                var x2eg = $('input[name="x2eg"]').val();
                var x3self = $('input[name="x3self"]').val();
                var x3eg = $('input[name="x3eg"]').val();
                var x4self = $('input[name="x4self"]').val();
                var x4eg = $('input[name="x4eg"]').val();
                var x5self = $('input[name="x5self"]').val();
                var x5eg = $('input[name="x5eg"]').val();
                var x6self = $('input[name="x6self"]').val();
                var x6eg = $('input[name="x6eg"]').val();
                var y1self = $('input[name="y1self"]').val();
                var y1eg = $('input[name="y1eg"]').val();
                var y2self = $('input[name="y2self"]').val();
                var y2eg = $('input[name="y2eg"]').val();
                var y3self = $('input[name="y3self"]').val();
                var y3eg = $('input[name="y3eg"]').val();
                var other = $('input:radio[name="other"]:checked').val();
                var compsys = $('input:radio[name="compsys"]:checked').val();
                var cooper = $('input:radio[name="cooper"]:checked').val();
                var platform = $('input:radio[name="platform"]:checked').val();
                var personnel = $('input:radio[name="personnel"]:checked').val();
                if (other == undefined) {
                    other = '';
                }
                if (compsys == undefined) {
                    compsys = '';
                }
                if (cooper == undefined) {
                    cooper = '';
                }
                if (platform == undefined) {
                    platform = '';
                }
                if (personnel == undefined) {
                    personnel = '';
                }

                htajax.postForm("/ht-biz/hisself/submit", {
                    'id': id,
                        'companyName': companyName,
                        'orgcode': orgcode,
                        'regDate': regDate,
                        'contact': contact,
                        'phone': phone,
                        'email': email,
                        'province': province,
                        'city': city,
                        'area': area,
                        'productServ': productServ,
                        'productCogn': productCogn,
                        'thirdfruit': thirdfruit,
                        'onefruit': onefruit,
                        'workerTotal': workerTotal,
                        'juniorTechTotal': juniorTechTotal,
                        'developmentTotal': developmentTotal,
                        'sciencePsersons': sciencePsersons,
                        'cog2008': cog2008,
                        'compop': compop,

                        'saleFirstYear': this.lastyear2,//2017
                        'saleSecondYear': this.lastyear1,//2018
                        'saleThirdYear': this.thisyear,//2019
                        'assetFirstYear': this.lastyear2,//2017
                        'assetSecondYear': this.lastyear1,//2018
                        'assetThirdYear': this.thisyear,//2019

                        'assetFirstValue': assetFirstValue,
                        'assetSecondValue': assetSecondValue,
                        'assetThirdValue': assetThirdValue,
                        'saleFirstValue': saleFirstValue,
                        'saleSecondValue': saleSecondValue,
                        'saleThirdValue': saleThirdValue,
                        'saleyfrate': saleyfrate,
                        'jnyfrate':jnyfrate,
                        'prodrate': prodrate,
                        'x1self': x1self,
                        'x1eg': x1eg,
                        'x2self': x2self,
                        'x2eg': x2eg,
                        'x3self': x3self,
                        'x3eg': x3eg,
                        'x4self': x4self,
                        'x4eg': x4eg,
                        'x5self': x5self,
                        'x5eg': x5eg,
                        'x6self': x6self,
                        'x6eg': x6eg,
                        'y1self': y1self,
                        'y1eg': y1eg,
                        'y2self': y2self,
                        'y2eg': y2eg,
                        'y3self': y3self,
                        'y3eg': y3eg,
                        'other': other,
                        'compsys': compsys,
                        'cooper': cooper,
                        'platform': platform,
                        'personnel': personnel
                },
                // $("#inputForm").serialize(),
                 function (data) {
                    if (data.code == 10000) {
                        //console.log(data);
                        var province = data.data.province;
                        var city = data.data.city;
                        var area = data.data.area;
                        //城市通过url传递到 result
                        location.href = "/ht-biz/hisself/result?id=" + data.data.id + "&province=" + province + "&city=" + city + "&area=" + area;
                    }
                }, function (data) {
                    //错误回调;
                    layer.msg(data);
                });
            }
        },
        submitDefault: function () { // y 类计算
            var x = 6;
            y = 3;
            for (i = 1; i <= x; i++) {
                this.init("x" + i);
                this.init("x" + i + "self");
                this.init("x" + i + "eg");
                this.initspan("x" + i, "x" + i + "_span");
            }
            for (i = 1; i <= 3; i++) {
                this.init("y" + i);
                this.init("y" + i + "self");
                this.init("y" + i + "eg");
                this.initspan("y" + i, "y" + i + "_span");
            }
            this.init("saleFirstValue");
            this.init("saleSecondValue");
            this.init("saleThirdValue");
            this.init("assetFirstValue");
            this.init("assetSecondValue");
            this.init("assetThirdValue");
            this.init("profitFirstValue");
            this.init("profitSecondValue");
            this.init("profitThirdValue");
            this.init("workerTotal");
            this.init("developmentTotal");
        },
        init: function (v) {
            var vv = ($.trim($("input[name='" + v + "']").val()) == '') ? 0 : parseFloat($.trim($("input[name='" + v + "']").val()));
            $("input[name='" + v + "']").val(vv);
        },
        initspan: function (v, span) {
            var vv = ($.trim($("input[name='" + v + "']").val()) == '') ? 0 : parseFloat($.trim($("input[name='" + v + "']").val()));
            $("#" + span).text(vv);
        },
        //校验基本信息
        validator: function () {
            var companyName = $.trim($("input[name='companyName']").val());
            var orgcode = $.trim($("input[name='orgcode']").val());
            var regDate = $.trim($("input[name='regDate']").val());
            var cog2008 = $.trim($("input[name='cog2008']").val());
            var contact = $.trim($("input[name='contact']").val());
            var phone = $.trim($("input[name='phone']").val());
            var email = $.trim($("input[name='email']").val());
            var province = $.trim($("select[name='province']").val());
            var city = $.trim($("select[name='city']").val());
            var area = $.trim($("select[name='area']").val());
            var productServ = $.trim($("select[name='productServ']").val());
            var productCogn = $.trim($("select[name='productCogn']").val());
            var thirdfruit = $.trim($("input[name='thirdfruit']").val());
            var onefruit = $.trim($("input[name='onefruit']").val());
            var workerTotal = $.trim($("input[name='workerTotal']").val());
            var juniorTechTotal = $.trim($("input[name='juniorTechTotal']").val());
            var developmentTotal = $.trim($("input[name='developmentTotal']").val());
            var sciencePsersons = $.trim($("input[name='sciencePsersons']").val());
            var compop = $.trim($("input[name='compop']").val());

            var DATE_FORMAT = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
            var regDate = $("#regDate").val();

            if (province == '' || province == '请选择') {
                layer.msg("请选择省");
                return false;
            }
            if (city == '' || city == '请选择') {
                layer.msg("请选择市");
                return false;
            }
            if (area == '请选择') {
                layer.msg("请选择区");
                return false;
            }

            if (orgcode == "") {
                layer.msg("请填写社会统一信用代码/组织机构代码");
                return false;
            } else if (orgcode != "") {
                var orgcode_tr = /^[\dA-Z]{18}$/
                var orgcode_val = $("input[name='orgcode']").val();
                if (!orgcode_tr.test(orgcode_val)) {
                    layer.msg("请填写正确的社会统一信用代码/组织机构代码")
                    return false
                }
            }
            if (companyName == '') {
                layer.msg("请填写企业名称");
                return false;
            }
            if (regDate == '') {
                layer.msg("请填写注册时间");
                return false;
            }

            if (!(DATE_FORMAT.test(regDate))) {
                layer.msg("抱歉，您输入的日期格式有误，正确格式应为'2000-01-01'");
                return false;
            }


            if (contact == '') {
                layer.msg("请填写联系人");
                return false;
            }
            if (phone == '') {
                layer.msg("请填写电话");
                return false;
            }
            if (phone != '') {
                // var search_str = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
                var search_str = /^[1][3,4,5,7,8][0-9]{9}$/;
                var phone_val = $("input[name='phone']").val();
                if (!search_str.test(phone_val)) {
                    layer.msg("请填写正确的手机号码");
                    return false;
                }
            }
            if (email == '') {
                layer.msg("请填写邮箱");
                return false;
            }
            if (email != '') {

                var search_str = /^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/;
                var email_val = $("input[name='email']").val();
                if (!search_str.test(email_val)) {
                    layer.msg("请填写正确的邮箱账号");
                    return false;
                }
            }
            if (productServ == '' || productServ == '请选择') {
                layer.msg("请选择主营产品技术领域");
                return false;
            }
            if (productCogn == '' || productCogn == '请选择') {
                layer.msg("请选择产品认定情况");
                return false;
            }
            if (thirdfruit == "") {
                layer.msg("请填写近三年年平均科技成果转化数量");
                return false;
            }
            if (thirdfruit == '0') {
                layer.msg("近三年年平均科技成果转化数量不能为0");
                return false;
            }
            if (thirdfruit.indexOf(".") >= 0) {
                layer.msg("近三年年平均科技成果转化数量不能包含小数，请重新输入！");
                return false;
            }
            if ($(".munber_gd").css("display") == "inline") {
                if (onefruit == "") {
                    layer.msg("请填写近两年科技成果转化数量");
                    return false;
                }
                if (onefruit == '0') {
                    layer.msg("近两年科技成果转化数量不能为0");
                    return false;
                }
                if (onefruit.indexOf(".") >= 0) {
                    layer.msg("近两年科技成果转化数量不能包含小数，请重新输入！");
                    return false;
                }
            }
            if ($(".munber").css("display") == "inline") {
                if (onefruit == "") {
                    layer.msg("请填写近一年科技成果转化数量");
                    return false;
                }
                if (onefruit == '0') {
                    layer.msg("近一年科技成果转化数量不能为0");
                    return false;
                }
                if (onefruit.indexOf(".") >= 0) {
                    layer.msg("近一年科技成果转化数量不能包含小数，请重新输入！");
                    return false;
                }
            }
            if (workerTotal == '') {
                layer.msg("请填写职工总数");
                return false;
            }
            if (workerTotal == "0") {
                layer.msg("职工总数不能为 0，请重新输入！");
                return false;
            }
            if (workerTotal.indexOf(".") >= 0) {
                layer.msg("职工总数不能包含小数，请重新输入！");
                return false;
            }
            if (developmentTotal == '') {
                layer.msg("请填写科技人员数");
                return false;
            }
            if (developmentTotal == '0') {
                layer.msg("科技人员数不能为0");
                return false;
            }
            if (developmentTotal.indexOf(".") >= 0) {
                layer.msg("科技人员数不能包含小数，请重新输入！");
                return false;
            }
            if (parseInt(workerTotal) < parseInt(developmentTotal)) {
                layer.msg("科技人员数不能大于职工总人数！");
                return false;
            }
            if ($(".munber").css("display") == "inline") {
                if (juniorTechTotal == '') {
                    layer.msg("请填写大专以上学历科技人员数");
                    return false;
                }
                if (juniorTechTotal == '0') {
                    layer.msg("大专以上学历科技人员数不能为0");
                    return false;
                }
                if (juniorTechTotal.indexOf(".") >= 0) {
                    layer.msg("大专以上学历科技人员数不能包含小数，请重新输入！");
                    return false;
                }
                if (sciencePsersons == '') {
                    layer.msg("请填写其中从事研究开发人员数");
                    return false;
                }
                if (sciencePsersons == '0') {
                    layer.msg("从事研究开发人员数不能为 0");
                    return false;
                }
                if (sciencePsersons.indexOf(".") >= 0) {
                    layer.msg("从事研究开发人员数不能包含小数，请重新输入！");
                    return false;
                }
                if (parseInt(developmentTotal) < parseInt(juniorTechTotal)) {
                    layer.msg("大专以上学历科技人员数不能大于科技人员数！");
                    return false;
                }
                if (parseInt(juniorTechTotal) < parseInt(sciencePsersons)) {
                    layer.msg("从事研究开发人员数不能大于大专以上学历科技人员数！");
                    return false;
                }
            }
            if ($(".munber3").css("display") == "block") {
                if (cog2008 == -1) {
                    layer.msg("请选择2008年后是否认定为高企");
                    return false;
                }
            }
            if (compop == -1) {
                layer.msg("请选择是否合规运营");
                return false;
            }
            return true;
        },

        //补充
        checkValidator: function (id) {
            if (id == 'all') {
                return order.validator() && order.checkKnowledge() && order.checkResearch();

            } else if (id == 'basic') {
                var c = order.validator();
                return order.validator();
            } else if (id == 'finance') {
                return order.checkKnowledge();

            } else if (id == 'knowledge') {
                return order.checkResearch();
            } else if (id == 'manage') {
                return order.checkResearch();
            } else {

                return true;
            }

        },
        //校验财务信息
        checkKnowledge: function () {
            var province = $.trim($("select[name='province']").val());
            var city = $.trim($("select[name='city']").val());
            var area = $.trim($("select[name='area']").val());
            var assetFirstValue = $('input[name="assetFirstValue"]').val();
            var assetSecondValue = $('input[name="assetSecondValue"]').val();
            var assetThirdValue = $('input[name="assetThirdValue"]').val();
            var saleFirstValue = $('input[name="saleFirstValue"]').val();
            var saleSecondValue = $('input[name="saleSecondValue"]').val();
            var saleThirdValue = $('input[name="saleThirdValue"]').val();
            var saleyfrate1 = $('input:radio[name="saleyfrate1"]:checked').val();
            var saleyfrate2 = $('input:radio[name="saleyfrate2"]:checked').val();
            var saleyfrate3 = $('input:radio[name="saleyfrate3"]:checked').val();
            var jnyfrate = $('input:radio[name="jnyfrate"]:checked').val();
            var prodrate1 = $('input:radio[name="prodrate1"]:checked').val();
            var prodrate2 = $('input:radio[name="prodrate2"]:checked').val();
            var prodrate3 = $('input:radio[name="prodrate3"]:checked').val();

            if (assetFirstValue == '') {
                layer.msg("请填写近三年净资产");
                return false;
            }
            if (assetSecondValue == '') {
                layer.msg("请填写近两年净资产");
                return false;
            }
            if (assetThirdValue == '') {
                layer.msg("请填写近一年净资产");
                return false;
            }
            if (saleFirstValue == '') {
                layer.msg("请填写近三年销售收入");
                return false;
            }
            if (saleSecondValue == '') {
                layer.msg("请填写近两年销售收入");
                return false;
            }
            if (saleThirdValue == '') {
                layer.msg("请填写近一年销售收入");
                return false;
            }

            if(city == "广州市"){
                if (saleyfrate2 == '' || saleyfrate2 == null) {
                    layer.msg("请选择年研发费用占同期销售收入总额比例");
                    return false;
                }
            }
            else if(city == "金华市"){
                if (saleyfrate1 == '' || saleyfrate1 == null) {
                    layer.msg("请选择年研发费用占同期销售收入总额比例");
                    return false;
                }
            }
            else if(city == "淮安市"){
                if (saleyfrate2 == '' || saleyfrate2 == null) {
                    layer.msg("请选择年研发费用占同期销售收入总额比例");
                    return false;
                }
            }
            else if(city == "苏州市"){
                if (saleyfrate2 == '' || saleyfrate2 == null) {
                    layer.msg("请选择年研发费用占同期销售收入总额比例");
                    return false;
                }
            }
            else{
                if (saleyfrate3 == '' || saleyfrate3 == null) {
                    layer.msg("请选择年研发费用占同期销售收入总额比例");
                    return false;
                }
            }


            if ($(".munber1").css("display") == "block") {
                if (jnyfrate == '' || jnyfrate == null) {
                    layer.msg("境内研发投入比例占全部研发费用比例");
                    return false;
                }
            }


            if(city == "广州市"){
                if (prodrate2 == '' || prodrate2 == null) {
                    layer.msg("申报前一年高新技术产品（服务）收入占当年总收入的比例");
                    return false;
                }
            }
            else if(city == "金华市"){
                if (prodrate1 == '' || prodrate1 == null) {
                    layer.msg("申报前一年高新技术产品（服务）收入占当年总收入的比例");
                    return false;
                }
            }
            else if(city == "淮安市"){
                if (prodrate1 == '' || prodrate1 == null) {
                    layer.msg("申报前一年高新技术产品（服务）收入占当年总收入的比例");
                    return false;
                }
            }
            else if(city == "苏州市"){
                if (prodrate1 == '' || prodrate1 == null) {
                    layer.msg("申报前一年高新技术产品（服务）收入占当年总收入的比例");
                    return false;
                }
            }
            else{
                if (prodrate3 == '' || prodrate3 == null) {
                    layer.msg("申报前一年高新技术产品（服务）收入占当年总收入的比例");
                    return false;
                }
            }
            
            return true;

        },

        //校验研发管理
        checkResearch: function () {
            var firstProblem = $('input:radio[name="compsys"]:checked').val();
            var secondProblem = $('input:radio[name="cooper"]:checked').val();
            var thirdProblem = $('input:radio[name="platform"]:checked').val();
            var fourProblem = $('input:radio[name="personnel"]:checked').val();
            if (firstProblem == '' || firstProblem == null) {
                layer.msg("请选择带*号的选项");
                return false;
            }
            if (secondProblem == '' || secondProblem == null) {
                layer.msg("请选择带*号的选项");
                return false;
            }
            if (thirdProblem == '' || thirdProblem == null) {
                layer.msg("请选择带*号的选项");
                return false;
            }
            if (fourProblem == '' || fourProblem == null) {
                layer.msg("请选择带*号的选项");
                return false;
            }
            return true;

        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },

    },
    
});