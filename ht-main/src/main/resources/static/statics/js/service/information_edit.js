/**
 * Created by ACER on 2019/8/19.
 */

var box = new Vue({
    el: '#box',
    data() {
        return {
            allkeyword: '',
            type: 1,
            province: '',
            city: '',
            area: '',
            province1: '',
            city1: '',
            area1: '',
            //文件路径
            pdfpath1: '',
            originalfile1: '',
            pdfpath2: '',
            originalfile2: '',
            pdfpath3: '',
            originalfile3: '',
            pdfpath4: '',
            originalfile4: '',
            fileName1: '',
            fileName2: '',
            fileName3: '',
            fileName4: '',
            isProtocol: true,
            code: '',
            serviceType1: false,
            serviceType2: false,
            serviceType3: false,
            serviceType4: false,
            serviceType5: false,
            nameIpt: '',
            detailed_address: '',
            shopIpt: '',
            linkman: '',
            phone: '',
            customerIpt: '',
            qqIpt: '',
            scope: '',
            addr: '',
            netryname: '',
            tcg: '',
            FormArr: [

            ],
            tecnologyProps: {
                value: 'n',
                label: 'n',
                children: 's'
            },
            //
            organization: 1,//0是 1否
        }
    },
    mounted() {
        setTimeout(function () {
            box.getcity();
        }, 2000)
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.inquireService();
        },
        //城市
        getcity: function () {
            //省市区
            $(".pick-area4").pickArea({
                // "format":"", //格式
                "width": "400",//设置“省市县”文本边框的宽度
                "height": "50",//设置“省市县”文本边框的高度
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
            //console.log('jq:' + $(".pick-area").attr('name'));
        },
        inquireService: function () {
            htajax.postForm("/ht-biz/service/judge", {}, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.type = data.data.netrytype;
                    box.code = data.data.socialcode;
                    box.nameIpt = data.data.banme;
                    box.province = data.data.pronice;
                    box.city = data.data.city;
                    box.area = data.data.area;
                    if (box.province == null) { box.province = "请选择省" }
                    if (box.city == null) { box.province = "请选择市" }
                    if (box.area == null) { box.province = "请选择区" }
                    box.usertown = box.province + '/' + box.city + '/' + box.area;
                    $(".pick-area").attr('name', box.usertown);
                    box.detailed_address = data.data.detailadrr;
                    box.shopIpt = data.data.shopname;
                    box.linkman = data.data.contacts;
                    box.phone = data.data.phone;
                    box.customerIpt = data.data.customercont;
                    box.qqIpt = data.data.customerqq;
                    box.addr = data.data.businesscope;
                    box.originalfile1 = data.data.businesslicense;
                    box.originalfile2 = data.data.zconstacpath;
                    box.originalfile3 = data.data.fconstacpath;
                    box.originalfile4 = data.data.agencyimg;
                    box.netryname = data.data.netryname;
                    box.serviceType = data.data.servicetype;
                    box.organization = data.data.isagency;
                    // console.log(box.organization);
                    var backstring = box.serviceType.split(';');
                    var arr = [];
                    for (var i = 0; i <= 4; i++) {
                        if (backstring[i] == 2) {
                            $("#typeB").prop("checked", true);
                            this.serviceType2 = true;
                        }
                        if (backstring[i] == 3) {
                            $("#typeC").prop("checked", true);
                            this.serviceType3 = true;
                        }
                        if (backstring[i] == 4) {
                            $("#typeD").prop("checked", true);
                            this.serviceType4 = true;
                        }
                        if (backstring[i] == 5) {
                            $("#typeE").prop("checked", true);
                            this.serviceType5 = true;
                        }
                    }

                }
            }, function (data) {
                //layer.msg(data.msg);    
            })
        },
        //
        organizationCheck: function () {
            // console.log(this.organization);
        },
        openfile: function (e) {
            $(`.${e}`).click();
        },
        clearIpt1: function () {
            $("#file1").attr('type', 'text');
            $("#file1").attr('type', 'file');
        },
        clearIpt2: function () {
            $("#file2").attr('type', 'text');
            $("#file2").attr('type', 'file');
        },
        clearIpt3: function () {
            $("#file3").attr('type', 'text');
            $("#file3").attr('type', 'file');
        },
        //获取上传的文件属性
        tirggerFile: function (event, el) {
            // console.log(event);
            // console.log($(`#${el}`));
            var id = event.target.id;
            var files = $(`#${el}`).prop('files');
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
            }
            else if (this.checkFileSize(files)) {
                var index;
                $.ajax({
                    type: 'POST',
                    url: "/uploadController/upload",
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
                            if(data.code== 10000){
                                if(id == "file1"){
                                    box.fileName1 = data.data.fileName;
                                    box.originalfile1 = data.data.path;
                                }
                                if(id == "file2"){
                                    box.fileName2 = data.data.fileName;
                                    box.originalfile2 = data.data.path;
                                }
                                if(id == 'file3'){
                                    box.fileName3 = data.data.fileName;
                                    box.originalfile3 = data.data.path;
                                }
                                if (id == 'file4') {
                                    box.fileName4 = data.data.fileName;
                                    box.originalfile4 = data.data.path;
                                }
                            }else{
                                layer.msg(data.msg);
                            }
                        } else {
                            layer.msg(data.msg);
                        }
                    }, error: function () {
                        layer.close(index);
                        layer.msg('上传失败');
                    }
                });

            } else {
                layer.msg("文件大于5M拒绝上传");
            }
        },
        checkFileSize: function (files) {
            var fileSize = 0;
            //alert(files[0].type)
            //console.log(files[0]);
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
        //提交信息
        settled: function (index) {
            var index = index;
            var type = this.type;  //1:服务商 ,2:个人
            var code = $.trim($("input[name='code']").val());
            //var serviceType = $('input:radio[name="serviceType"]:checked').val();
            var serviceType = '';
            if (type == 1) {
                serviceType = ((this.serviceType2 == true) ? "2;" : '') + ((this.serviceType3 == true) ? "3;" : '') + ((this.serviceType4 == true) ? "4;" : '') + ((this.serviceType5 == true) ? "5;" : '')
            }
            var name = $.trim($("input[name='name']").val());
            var province = $(".pick-province").text();
            var city = $('.pick-city').text();
            var area = $('.pick-county').text();
            var detailed_address = $.trim($("input[name='detailed_address']").val());
            var shop = $.trim($("input[name='shop']").val());
            var linkman = $.trim($("input[name='linkman']").val());
            var phone = $.trim($("input[name='phone']").val());
            var customer = $.trim($("input[name='customer']").val());
            var qq = $.trim($("input[name='qq']").val());
            var netryname = $.trim($("input[name='netryname']").val());

            var scope = $.trim($("input[name='addr']").val());
            var originalfile1 = box.originalfile1;
            var originalfile2 = box.originalfile2;
            var originalfile3 = box.originalfile3;
            var originalfile4 = box.originalfile4;
            //检验
            var regBox = {
                regMobile: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|17[0-9])\d{8}$/, //手机
            }
            var mflag = regBox.regMobile.test(phone);
            var str = /^[A-Za-z0-9]{18}/;
            var organization = this.organization; 
            if (organization == 0) { organization='0'}
            if (type == 1) {
                if (code == '') { layer.msg("请输入社会统一信用代码"); return false; }
                // if (organization == '') { layer.msg("是否认证成为相关服务机构"); return false; } 
                if (!(str.test(code))) { layer.msg("请输入正确的社会统一信息代码"); return false; }
                if (code.length != 18) { layer.msg("请输入正确的社会统一信息代码"); return false; }
                // if (!$('input[name="manager"]').is(":checked")) { layer.msg("请选择服务商类型"); return false; }

                if (name == '') { layer.msg("请输入机构名称"); return false; }
                if (province == '') { layer.msg("请选择省"); return false; }
                if (city == '') { layer.msg("请选择市"); return false; }
                //if (area == ''){layer.msg("请选择区");return false;}
                if (shop == '') { layer.msg("请输入服务商铺名称"); return false; }
                if (linkman == '') { layer.msg("请输入联系人名称"); return false; }
                if (!(mflag)) { layer.msg("请输入正确的手机号码"); return false; }
                if (phone == "") { layer.msg("请输入手机号码"); return false; }
                //if (customer == ''){layer.msg("请输入客服联系人");return false;}
                if (qq == '') { layer.msg("请输入客服QQ"); return false; }
                //if (diqu == ''){layer.msg("请选择业务范围");return false;}
                if (originalfile1 == '') { layer.msg("请上传企业营业执照"); return false; }
                if (originalfile2 == '') { layer.msg("请上传联系人身份证正面"); return false; }
                if (originalfile3 == '') { layer.msg("请上传联系人身份证反面"); return false; }
                if (organization == 1) {
                    if (originalfile4 == '') { layer.msg("请上传认定创新劵/服务卷服务机构相关材料证明"); return false; }
                }
            } else if (type == 2) {
                if (netryname == '') { layer.msg("请填写真实名称"); return false; }
                if (province == '') { layer.msg("请选择省"); return false; }
                if (city == '') { layer.msg("请选择市"); return false; }
                //if (area == ''){layer.msg("请选择区");return false;}
                if (shop == '') { layer.msg("请输入服务商铺名称"); return false; }
                if (linkman == '') { layer.msg("请输入联系人名称"); return false; }
                if (phone == "") { layer.msg("请输入手机号码"); return false; }
                if (qq == '') { layer.msg("请输入客服QQ"); return false; }
                //if (diqu == ''){layer.msg("请选择业务范围");return false;}
                if (originalfile2 == '') { layer.msg("请上传联系人身份证正面"); return false; }
                if (originalfile3 == '') { layer.msg("请上传联系人身份证反面"); return false; }
            }
            htajax.postForm("/ht-biz/bservice/savaService", {
                stats: 1,//等待审核
                netrytype: type,
                socialcode: code,
                isagency: this.organization,
                servicetype: serviceType,
                banme: name,
                pronice: province,
                city: city,
                area: area,
                detailadrr: detailed_address,
                shopname: shop,
                contacts: linkman,
                phone: phone,
                customercont: customer,
                customerqq: qq,
                businesscope: scope,
                businesslicense: originalfile1,
                zconstacpath: originalfile2,
                fconstacpath: originalfile3,
                netryname: netryname,
                agencyimg: originalfile4,

            }, function (data) {
                // console.log(data);
                if (data.code == 10000) {
                    $(".service_info1").fadeIn('500', function () {
                        var timer = setTimeout(function () {
                            $(".service_info1").fadeOut('500');
                            window.location.href = '/ht-biz/service/index/information';
                        }, 2000);
                    });
                }
            }, function (data) {
                layer.msg(data.msg);
            })
        },
        closeicon: function () {
            $(".service_info1").fadeOut(500);
        },
        AddForm() {
            // console.log(this.FormArr)
            this.FormArr.push({
                index: this.FormArr.length,
                value: []
            })
        },
        Delete(index) {
            this.FormArr.splice(index, 1)
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})