var box = new Vue({
    el: '#box',
    data() {
        return {
            province: '',
            city: '',
            area: '',
            isShow: -1,
            saveframe: false,
            auditframe: false,
            productList: [],
            productId: '',
            different: '',
            proname: '', //
            isscience: '',
            general: true, //普通的
            granular: false, //粒度化
            childList: [],
            childList2: [],
            //产品类型
            typeone: '',
            typetwo: '',
            typethree: '',
            other: '',
            //回显
            staut: '',
            producttypeone: '', //产品类型1
            producttypetwo: '', //产品类型2
            producttypethree: '', //产品类型3
            producttypeonename: '', //产品类型1
            producttypetwoname: '', //产品类型2
            producttypethreename: '', //产品类型3
            productname: '', //产品名称
            plevel: '', //产品级别  1：国家 2：省级 3：市级  4：区级
            provice: '', //省
            city: '', //市
            area: '', //区
            pricetype: '', //价格类型 1:一口价 2：区级价  3：面议
            price: '', //价格
            price1: '', //价格
            price2: '', //价格
            price3: '', //价格
            Date1: '',
            Date2: '',
            pricetwo: '',
            applytimetype: '', //申报时间类型 1：开始时间-结束时间 2：全年申报
            begintime: '', //开始申报时间
            endtime: '', //结束申报时间
            applyobject: '', //申报对象
            supportmode: '', //支持方式
            departmet: '', //主要部门
            subsidy: '', //项目补助金
            applybenefit: '', //申报好处
            productinfo: '', // 产品介绍
            servicecontent: '', //服务内容
            other: '', //其他
            finishtime:'',
            //预览
            Risscience:'',//true 科技政策 false普通类型
            Rstaut: '',//状态 1：待提交 2：待审核  3：下线  4：审核不通过 5：上线
            Rproductname: '',
            Rplevel: '',
            Rplevelname: '',
            Rprovice: '',
            Rcity: '',
            Rarea: '',
            Rpricetype: '',
            Rprice: '',
            Rapplytimetype: '',
            Rbegintime: '',
            Rendtime: '',
            Rapplyobject: '',
            Rsupportmode: '',
            Rdepartmet: '',
            Rsubsidy: '',
            Rapplybenefit: '',
            Rproductinfo: '',
            Rservicecontent: '',
            Rother: '',
            Rfinishtime: '',
            Rproducttypeone: '',
            Rproducttypetwo: '',
            Rproducttypethree: '',
            Rproducttypeonename: '',
            Rproducttypetwoname: '',
            Rproducttypethreename: '',
            Rfinishtime: '',
            Rservicecontent: '',
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getProductType();
            this.getProductChild();
            this.provincechange();
            this.getEditInfo();
        },
        //获取链接的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        //编辑回显
        getEditInfo: function () {
            var id = this.getUrlData("id");
            if (id != null) {
                htajax.postForm("/ht-biz/product/findbyid", {
                    id: id,
                }, function (data) {
                    if (data.code == 10000) {
                        console.log(data);
                        box.producttypeone = data.data.producttypeone;
                        var a = box.producttypeone;
                        $(".plx_r_t>li").find("#" +a)
                        box.isscience = data.data.isscience;
                        if (box.isscience) {
                            box.general = false;
                            box.granular = true; //不同的
                        } else {
                            box.general = true;
                            box.granular = false;
                        }
                        var province = data.data.provice;
                        var city = data.data.city;
                        var area = data.data.area;
                        var selectDataUrl = "/statics/js/cityData.json";
                        $.getJSON(selectDataUrl, function (json) {
                            box.areaData = json;
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
                        box.proname = data.data.producttypeonename;
                        box.producttypetwoname = data.data.producttypetwoname;
                        box.producttypethreename = data.data.producttypethreename;
                        box.typeone = data.data.producttypeone;
                        box.typetwo = data.data.producttypetwo;
                        box.typethree = data.data.producttypethree;

                        $("#tier1").prepend("<option value='" + data.data.producttypetwoname + "'>" + data.data.producttypetwoname + "</option>"); //为Select插入一个Option(第一个位置) 
                        $("#tier2").prepend("<option value='" + data.data.producttypethreename + "'>" + data.data.producttypethreename + "</option>"); //为Select插入一个Option(第一个位置) 
                        box.productname = data.data.productname;
                        box.plevel = data.data.plevel;
                        $("input[name='region'][value='" + data.data.plevel + "']").prop("checked", true);
                        box.pricetype = data.data.pricetype;
                        box.price = data.data.price;
                        box.pricetwo = data.data.pricetwo;
                        $("input[name='price'][value='" + data.data.pricetype + "']").prop("checked", true);
                        if (box.pricetype == 1) {
                            box.price1 = data.data.price;
                        }
                        if (box.pricetype == 2) {
                            box.price2 = data.data.price;
                            box.price3 = data.data.pricetwo;
                        }
                        box.finishtime = data.data.finishtime;
                        box.applytimetype = data.data.applytimetype;
                        $("input[name='declare'][value='" + data.data.applytimetype + "']").prop("checked", true);
                        if (box.applytimetype == 1) {
                            $("input[name='Date1']").val(data.data.begintime.substring(0, 10));
                            $("input[name='Date2']").val(data.data.endtime.substring(0, 10));
                        }
                        box.applyobject = data.data.applyobject;
                        box.supportmode = data.data.supportmode;
                        box.departmet = data.data.departmet;
                        box.subsidy = data.data.subsidy;
                        $('.haochu').summernote('code', data.data.applybenefit);
                        $('.jieshou').summernote('code', data.data.productinfo);
                        $('.neirong').summernote('code', data.data.servicecontent);
                        $('.other_test').summernote('code', data.data.other);
                    }
                }, function (data) {
                    //layer.msg(data.msg);
                })
            }else{
                $("#tier1").find("option").eq(0).prop("selected", true)
                $("#tier2").find("option").eq(0).prop("selected", true)
            }

        },
        //地区
        provincechange: function (province, city, area) {
            var selectDataUrl = "/statics/js/cityData.json";
            $.getJSON(selectDataUrl, function (json) {
                box.areaData = json;
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
        },
        //省份
        changePro: function () {
            var choseProvice = $("#province").val();
            $("#area").empty();
            var option = "<option value=''>--选择区--</option>";
            $("#area").append(option);
            for (var i = 0; i < box.areaData.length; i++) {
                $("#city").empty();
                var option = "<option value=''>--选择市--</option>";
                $("#city").append(option);
                if (box.areaData[i].n == choseProvice) {
                    for (var j = 0; j < box.areaData[i].s.length; j++) {
                        var op1 = "<option value='" + box.areaData[i].s[j].n + "'>" + box.areaData[i].s[j].n + "</option>";
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
            for (var i = 0; i < box.areaData.length; i++) {
                if (box.areaData[i].n == choseProvince) {
                    for (var j = 0; j < box.areaData[i].s.length; j++) {
                        if (box.areaData[i].s[j].n == choseCity) {
                            if (box.areaData[i].s[j].s != null && box.areaData[i].s[j].s != "" && box.areaData[i].s[j].s != undefined) {
                                //有区
                                $("#area").show().empty();
                                var option = "<option value=''>--选择区--</option>";
                                $("#area").append(option);
                                for (var k = 0; k < box.areaData[i].s[j].s.length; k++) {
                                    var op1 = "<option value='" + box.areaData[i].s[j].s[k].n + "'>" + box.areaData[i].s[j].s[k].n + "</option>";
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
        //查询产品
        getProductType: function () {
            htajax.postForm("/ht-biz/product/findfirsttype", {}, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.productList = data.data;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        selectchose:function(){
            var timer = setTimeout(function () {
                $("#tier1 option:first").prop("selected", 'selected');
                $("#tier2 option:first").prop("selected", 'selected');
            }, 300)
        },
        selectchose1: function () {
            var timer = setTimeout(function () {
                $("#tier2 option:first").prop("selected", 'selected');
            }, 300)
        },
        //获取id 查询下级
        getId: function (event, index) {
            var id = event.currentTarget.id;
            box.proname = this.productList[index].name;
            box.proid = this.productList[index].id;
            box.isscience = this.productList[index].isscience;
            if (this.productList[index].isscience) {
                this.general = false;
                this.granular = true; //不同的
            } else {
                this.general = true;
                this.granular = false;
            }
            box.getProductChild(box.proid);
        },
        //查询产品下一级
        getProductChild: function (id) {
            // console.log(id);
            this.typeone = id;
            htajax.postFormDone("/ht-biz/product/findchild", {
                id: id,
            }, function () {
                var index = layer.load(1, {
                    shade: [0.5, '#fff'] //0.1透明度的白色背景
                });
            }, function (data) {
                layer.closeAll();
                if (data.code == 10000) {
                    //console.log(data);
                    box.childList = data.data;
                    box.selectchose();
                }
            }, function (data) {
                layer.closeAll();
                //layer.msg(data.msg);
            });
        },
        gettier1: function (event) {
            var id = event.target.value;
            // console.log(id);
            this.typetwo = id;
            htajax.postFormDone("/ht-biz/product/findchild", {
                id: id,
            }, function () {
                var index = layer.load(1, {
                    shade: [0.5, '#fff'] //0.1透明度的白色背景
                });
            }, function (data) {
                layer.closeAll();
                if (data.code == 10000) {
                    //console.log(data);
                    box.childList2 = data.data;
                    box.selectchose1();
                }
            }, function (data) {
                layer.closeAll();
                //layer.msg(data.msg);
            });
        },
        gettier2: function (event) {
            var id = event.target.value;
            //console.log(id);
            this.typethree = id;
        },
        priceCheck:function(){
            var price = $('input:radio[name="price"]:checked').val();
            if (price != ''){
                $("input[name='price1']").removeAttr("disabled");
                $("input[name='price2']").removeAttr("disabled");
                $("input[name='price3']").removeAttr("disabled");
            }
            if (price == 1) {this.price2 = '';}
            if (price == 2) {this.price1 = '';}
            if (price == 3) {this.price1 = '';this.price2 = '';this.price3 = '';}
        },
        timeCheck:function(){
            var declare = $('input:radio[name="declare"]:checked').val();
            if (declare != '') {
                $("input[name='Date1']").removeAttr("disabled");
                $("input[name='Date2']").removeAttr("disabled");
            }
            if (declare == 2) {
                $("input[name='Date1']").val("");
                $("input[name='Date2']").val("");
            }
        },
        //检测信息
        //提交信息
        settled: function (setType) {
            var setType = setType;
            var name = $.trim($("input[name='proname']").val());
            var one = this.typeone;
            var two = this.typetwo;
            var three = this.typethree;
            var region = $('input:radio[name="region"]:checked').val();
            var finishtime = $.trim($("input[name='finishtime']").val());
            if (region == 1) {var plevel = 1;}
            if (region == 2) { var plevel = 2;}
            if (region == 3) { var plevel = 3;}
            if (region == 4) {var plevel = 4;}
            if (region == undefined) { var plevel = ''}
            var province = $('#province option:selected').val();
            var city = $('#city option:selected').val();
            var area = $.trim($("select[name='area']").val());
            var price = $('input:radio[name="price"]:checked').val();
            if (price == 1) {var setmoney1 = $.trim($("input[name='price1']").val());var setmoney2 = '';}
            //if (price == 2) {var setmoney1 = $.trim($("input[name='price2']").val());var setmoney2 = $.trim($("input[name='price3']").val());}
            if (price == 3) {var setmoney1 = '';var setmoney2 = '';}
            var declare = $('input:radio[name="declare"]:checked').val();
            if (declare == 1) {var begintime = $.trim($("input[name='Date1']").val());var endtime = $.trim($("input[name='Date2']").val());}
            if (declare == 2) {var begintime = null;var endtime = null;}
            var object = $.trim($("input[name='object']").val());
            var way = $.trim($("input[name='way']").val());
            var department = $.trim($("input[name='department']").val());
            var money = $.trim($("input[name='money']").val());
            var haochu = $('.haochu').summernote('code');
            var jieshou = $('.jieshou').summernote('code');
            var neirong = $('.neirong').summernote('code');
            var other = $('.other_test').summernote('code');
            if (setType == 2){
                if (one == '') {layer.msg("请选择产品类型");return false;}
                if ( two == '') {layer.msg("请选择二级产品类型");return false;}
                if (name == '') {layer.msg("请填写产品名称");return false;}
                if (this.isscience) {if (region == '' || region == undefined) {layer.msg("请选择产品级别");return false;}}
                if (province == '' || province == undefined) {layer.msg("产品所属省级");return false;}
                if (price == '' || price == undefined) {layer.msg("请选择产品价格");return false;}
                if (price == 1) {if (setmoney1 == '' || setmoney1 == undefined) {layer.msg("请填写产品价格");return false;}}
                if (price == 2) {
                    if (setmoney1 == '' || setmoney1 == undefined) {layer.msg("请填写产品价格");return false;}
                    if (setmoney2 == '' || setmoney2 == undefined) {layer.msg("请填写产品价格");return false;}
                }
                if (this.isscience) {
                    if (declare == '' || declare== undefined) {layer.msg("请选择申报时间");return false;}
                    if (declare == 1) {
                        if (begintime == '' || declare == undefined) {layer.msg("请选择开始申报时间");return false; }
                        if (endtime == '' || declare == undefined) {layer.msg("请选择结束申报时间");return false;}
                    }
                    if (object == '') {layer.msg("请填写申报对象");return false;}
                    if (way == '') {layer.msg("请填写支持方式");return false;}
                    if (department == '') {layer.msg("请填写主管部门");return false;}
                    if (haochu == '' || haochu == '<p><br></p>') {layer.msg("请填写申报好处");return false;}
                    if (jieshou == '' || jieshou == '<p><br></p>') {layer.msg("请填写产品介绍");return false;}
                }else{
                    if (finishtime == ''){ layer.msg("请填写完成时间");return false;}
                }
            }
            if (this.isscience){
                finishtime = '';
            }else{
                declare = '';
                begintime = '';
                endtime = '';
                object = '';
                way = '';
                department = '';
                money = '';
                haochu = '';
                jieshou = '';
            }
            var id = this.getUrlData("id");
            htajax.postForm("/ht-biz/product/insertPoroduct", {
                id:id,
                staut: setType,
                producttypeone: one, //产品类型1
                producttypetwo: two, //产品类型2
                producttypethree: three, //产品类型3
                productname: name, //产品名称
                plevel: plevel, //产品级别  1：国家 2：省级 3：市级  4：区级
                provice: province, //省
                city: city, //市
                area: area, //区
                pricetype: price, //价格类型 1:一口价 2：区级价  3：面议
                price: setmoney1, //价格
                pricetwo: setmoney2,
                applytimetype: declare, //申报时间类型 1：开始时间-结束时间 2：全年申报
                begintime: begintime, //开始申报时间
                endtime: endtime, //结束申报时间
                finishtime: finishtime,//完成时间
                applyobject: object, //申报对象
                supportmode: way, //支持方式
                departmet: department, //主要部门
                subsidy: money, //项目补助金
                applybenefit: haochu, //申报好处
                productinfo: jieshou, // 产品介绍
                servicecontent: neirong, //服务内容
                other: other, //其他
            }, function (data) {
                if (data.code == 10000) {
                    if (setType==1){
                        box.saveframe=true;
                    }
                    if (setType == 2) {
                        box.auditframe = true;
                        var timer = setTimeout(function () { window.location.href = "/ht-biz/service/index/product"; }, 1500)
                    }
                }
            }, function (data) {
                //layer.msg(data.msg);
            })

        },
        //预览
        lookmore: function () {
            layer.open({
                type: 1,
                title: '预览',
                area: ['1200px', '600px'], //宽高
                shadeClose: true,
                content: $('#proid') //这里content是一个DOM，这个元素要放在body根节点下
            });
            var name = $.trim($("input[name='proname']").val());
            var name1 = this.proname;
            var name2 =  $("#tier1 option:selected").text();
            var name3 =  $("#tier2 option:selected").text();
            var province = $('#province option:selected').val();
            var city = $('#city option:selected').val();
            var area = $.trim($("select[name='area']").val());
            var region = $('input:radio[name="region"]:checked').val();
            var finishtime = $.trim($("input[name='finishtime']").val());
            if (region == 1) {
                var plevel = '国家级';
            }
            if (region == 2) {
                var plevel = '省级';
            }
            if (region == 3) {
                var plevel = '市级';
            }
            if (region == 4) {
                var plevel = '区级';
            }
            if (region == undefined) {
                var plevel = ''
            }
            var price = $('input:radio[name="price"]:checked').val();
            if (price == 1) {
                var setmoney1 = $.trim($("input[name='price1']").val());
                var setmoney2 = '';
            }
            if (price == 2) {
                var setmoney1 = $.trim($("input[name='price2']").val());
                var setmoney2 = $.trim($("input[name='price3']").val());
            }
            if (price == 3) {
                var setmoney1 = '';
                var setmoney2 = '';
            }
            var declare = $('input:radio[name="declare"]:checked').val();
            if (declare == 1) {
                var begintime = $.trim($("input[name='Date1']").val());
                var endtime = $.trim($("input[name='Date2']").val());
            }
            if (declare == 2) {
                var begintime = null;
                var endtime = null;
            }
            var object = $.trim($("input[name='object']").val());
            var way = $.trim($("input[name='way']").val());
            var department = $.trim($("input[name='department']").val());
            var money = $.trim($("input[name='money']").val());
            var haochu = $('.haochu').summernote('code');
            var jieshou = $('.jieshou').summernote('code');
            var neirong = $('.neirong').summernote('code');
            var other = $('.other_test').summernote('code');
            // var other = this.other;
            this.Risscience = this.issciences;//true 科技政策 false普通类型
            this.Rstaut//状态 1：待提交 2：待审核  3：下线  4：审核不通过 5：上线
            this.Rproductname = name;
            this.Rplevelname = plevel;
            this.Rprovice = province;
            this.Rcity = city;
            this.Rarea = area;
            this.Rpricetype = price;
            if (this.Rpricetype == 1) { this.Rprice = setmoney1}
            if (this.Rpricetype == 2) { this.Rprice = setmoney1 + '至' + setmoney2 ;}
            this.Rapplytimetype = declare;
            this.Rbegintime = begintime;
            this.Rendtime = endtime;
            this.Rapplyobject = object;
            this.Rsupportmode = way;
            this.Rdepartmet = department;
            this.Rsubsidy = money;
            this.Rapplybenefit = haochu;
            this.Rproductinfo = jieshou;
            this.Rservicecontent = neirong;
            this.Rother = other;
            this.Rproducttypeonename = name1;
            this.Rproducttypetwoname = name2;
            this.Rproducttypethreename = name3;
            var timer = setTimeout(function () {
                $(".advantage_img>p").children("img").attr("style", "");
            }, 500);
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },

    }
})