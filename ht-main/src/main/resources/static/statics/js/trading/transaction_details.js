/**
 * Created by ACER on 2019/12/10.
 */

var box = new Vue({
    el: '#box',
    data() {
        return {
            allkeyword: '',
            reminder: false,
            userOrderInfo: false,
            orderList: [],
            productname:'',
            priceOrder:'',
            number:'',
            needMoney:'',
            //下单信息
            companyName: '',
            province1: '',
            city1: '',
            area1: '',
            usertown1: '',
            name: '',
            phone: '',
            beizhu: '',
            id: '',
            auditframe: false,
            auditframe1: false,
            mun:'',
            pricetype:'',
            value:'',
        }
    },
    created() {
        this.initPage();
    },
    mounted() {
        setTimeout(function () { box.getcity1(); }, 1000)
    },
    methods: {
        initPage: function () {
            this.getGrade();
        },
        //全局搜索
        allSearch: function () {
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        //获取链接的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        //企业城市`
        getcity1: function () {
            //省市区
            $(".pick-area5").pickArea({
                // "format":"", //格式
                "width": "320",//设置“省市县”文本边框的宽度
                "height": "35",//设置“省市县”文本边框的高度
                "borderColor": "#dcdfe6",//设置“省市县”文本边框的色值
                "arrowColor": "#dcdfe6",//设置下拉箭头颜色
                "listBdColor": "#dcdfe6",//设置下拉框父元素ul的border色值
                "color": "#666666",//设置“省市县”字体颜色
                "fontSize": "14px",//设置字体大小
                "hoverColor": "#1E9FFF",
                "paddingLeft": "30px",//设置“省”位置处的span相较于边框的距离
                "arrowRight": "30px",//设置下拉箭头距离边框右侧的距离
                "maxHeight": "247px",
                //"preSet":"",//数据初始化 ；这里的数据初始化有两种方式，第一种是用preSet属性设置，第二种是在a标签里使用name属性设置
                "getVal": function () {//这个方法是每次选中一个省、市或者县之后，执行的方法
                    // console.log($(".pick-area-hidden").val())
                    // console.log($(".pick-area-dom").val())
                    var thisdom = $("." + $(".pick-area-dom").val());//返回的是调用这个插件的元素pick-area，$(".pick-area-dom").val()的值是该元素的另一个class名，这个class名在dom结构中是唯一的，不会有重复，可以通过这个class名来识别这个pick-area
                    thisdom.next().val($(".pick-area-hidden").val());//$(".pick-area-hidden").val()是页面中隐藏域的值，存放着每次选中一个省、市或者县的时候，当前文本存放的省市县的最新值，每点击一次下拉框里的li，这个值就会立即更新
                }
            });
            //console.log('jq:'+$(".pick-area").attr('name'));
        },
        //加入购物车
        addCart: function (id, createid){
            if (vm.code == 10000) {
                var urlId = this.getUrlData("createid");
                htajax.postForm("/ht-biz/shopcart/save", {
                    productId: id,
                    serviceId: urlId,
                }, function (data) {
                    if (data.code == 10000) {
                        // console.log(data);
                        box.auditframe = true;
                        var timer = setTimeout(function () { box.auditframe = false; }, 1500);
                    }
                }, function (data) {
                    //layer.msg(data.msg);
                })
            }else{
                $(".loginBox").show();
            }
                
        },
       //下单
        getorders: function (productname, pricetype, price1, price2) {
            if (vm.code == 10000) {
                this.reminder = true;
                this.productname = productname;
                this.pricetype = pricetype;
                if (pricetype == 1) {
                    this.priceOrder = price1;
                }
                if (pricetype == 2) {
                    this.priceOrder = price1;
                }
                if (pricetype == 3) {
                    this.priceOrder = '面议';
                }
                var id = this.getUrlData("id");//产品id
                this.productId = id;
            }else{
                $(".loginBox").show();
            }
        },
        //下单信息列表
        userOrder: function () {
            this.userOrderInfo = true;
            var id = vm.userId;
            htajax.get("/ht-biz/oucomp/list?id=" + id, function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    box.orderList = data.data.records;
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        //计算价格
        compute: function () {
            if (this.pricetype == 3){
                this.needMoney = '面议';
            }else{
                var number = $.trim($("input[name='number']").val());
                this.needMoney = number * this.priceOrder;
            }
            
        },
        getGrade: function () {
            htajax.get("/ht-biz/honeymanager/getUserLevel?type=" + 2, function (data) {
                if (data.code == 10000) {
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
        //下单
        Overbooking: function () {
            var companyName = $.trim($("input[name='companyName']").val());
            var province = $(".pick-area5>.pick-show>.pick-province").text();
            var city = $('.pick-area5>.pick-show>.pick-city').text();
            var area = $('.pick-area5>.pick-show>.pick-county').text();
            var name = $.trim($("input[name='name']").val());
            var phone = this.phone;
            var beizhu = this.beizhu;
            var productId = this.productId;
            var productname = this.productname;
            var priceOrder = this.priceOrder;
            var num = this.number;
            var regBox = {
                regMobile: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/, //手机
            }
            var mflag = regBox.regMobile.test(phone);
            if (num == '') { layer.msg("请填写数量"); return false; }
            if (companyName == '') { layer.msg("请填写企业名字"); return false; }
            if (province == '' || province == '请选择省') { layer.msg("请填写所在省"); return false; }
            if (city == '' || city =='请选择市') { layer.msg("请填写所在市"); return false; }
            if (area == '' || area == '请选择区') { layer.msg("请填写所在区"); return false; }
            if (name == '') { layer.msg("请填写联系人"); return false; }
            if (phone == '') { layer.msg("请填写手机号码"); return false; }
            if (!(mflag)) { layer.msg("请输入正确的手机号码"); return false; }
            if (priceOrder == '面议') { priceOrder=0;}
            htajax.postForm("/ht-biz/myorder/save?t=" + new Date().getTime(), {
                productId: productId,//产品id
                productName: productname,
                productPrice: priceOrder,    //产品单价(产品信息可能会变化，但用户下过的订单信息不能改变)
                serviceId: '',//服务商id
                status: 0,//status=0待服务status=1交易完成status=2拒接服务
                companyName: companyName,
                province: province,
                city: city,
                area: area,
                linkman: name,
                phone: phone,
                remark: beizhu,
                productCounts: num,//数量
            }, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    // layer.msg("下单成功");
                    htajax.postForm("/ht-biz/oucomp/ordersave", {
                        companyName: companyName,
                        province: province,
                        city: city,
                        area: area,
                        linkman: name,
                        phone: phone,
                        remark: beizhu,
                    }, function (data) {
                        if (data.code == 10000) {
                            // console.log("新增成功");
                        }
                    }, function (data) {
                        //layer.msg(data.msg);
                    })
                    box.reminder = false;
                    box.auditframe1 = true;
                    var timer = setTimeout(function () { box.auditframe1 = false; }, 1500);
                }
            }, function (data) {
                //layer.msg(data.msg);
            })

        },
        //删除
        deleteProduct: function () {
            var targetId = event.toElement.id;
            htajax.get("/ht-biz/shopcart/deleted?productIds=" + targetId, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    layer.msg("删除成功");
                    box.getCartList();
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //删除信息
        deleteOrder: function (id) {
            htajax.get("/ht-biz/oucomp/deleted?id=" + id, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    layer.msg("删除成功");
                    box.userOrder();
                }
            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        //选择信息
        choiceDefault: function (companyName, province, city, area, phone, linkman, remark) {
            $(".pick-show").remove();
            box.companyName = companyName;
            box.province1 = province;
            box.city1 = city;
            box.area1 = area;
            box.usertown1 = box.province1 + '/' + box.city1 + '/' + box.area1;
            $(".pick-area5").attr('name', box.usertown1);
            box.name = linkman;
            box.phone = phone;
            box.beizhu = remark;
            box.getcity1();
        },
        //设为默认
        setDefault: function (id) {
            htajax.get("/ht-biz/oucomp/defaulted?id=" + id, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    layer.msg("设为默认成功");
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