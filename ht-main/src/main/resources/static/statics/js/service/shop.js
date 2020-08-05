/**
 * Created by ACER on 2019/9/11.
 */

var box = new Vue({
    el: '#box',
    data() {
        return {
            type:1,
            socialcode:'',
            netrytype:'',
            stats:'',
            customercont:'',
            contacts:'',
            banme:'',
            businesscope:'',
            pronice:'',
            city:'',
            area:'',
            detailadrr:'',
            value:0,//星星
            //文件路径
            originalfile1:'',
            originalfile2:'',
            originaldata:'',
            fileName1:'',
            fileName2:'',
            originalfile:'',
            fileName:'',
            isProtocol:true,
            //数据
            serid:'',
            web:'',
            email:'',
            intro:'',
            cases:'',
            honer:'',
            text:'',
            describeTeam:'',
            describeService:'',
            describeSafe:'',
            describeReplenish:'',
            other:'',
            isbond:'',

            nameIpt:'',
            detailed_address:'',
            shopIpt:'',
            star:0,
            grade:0,
            serviceimg:'',
            servicedetails:'',
            linkman:'',
            phone:'',
            customerIpt:'',
            qqIpt:'',
            scope:'',
            netryname: '',

            userGrade:'',
            userCode:'',
            value:'',
            pdfpath1: '',
            pdfpath2: '',
            pdfpath3: '',
            haveSafe:'',//保障金
            isagency:'',//认证服务机构
            agencyimg:'',
            fileName1:'',
            pdfpath1:'',
            originalfile1:'',
            shopimg:'',
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.inquireService();
            this.getGrade();
            this.getHoney();
            this.getSafe();
        },
        getSafe:function(){
            htajax.get("/ht-biz/guaran/list", function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.haveSafe = data.data.records;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },  
        inquireService:function(){
            htajax.postForm("/ht-biz/service/judge",{},function(data){
                // console.log(data);
                if(data.code == 10000){
                    box.isbond = data.data.isbond;
                    box.shopimg = data.data.shopimg;
                    box.serid = data.data.id;
                    box.isagency = data.data.isagency;
                    box.netrytype = data.data.netrytype;//1企业 2个人
                    box.socialcode = data.data.socialcode;
                    box.stats = data.data.stats;
                    box.phone = data.data.phone; 
                    box.banme = data.data.banme; 
                    box.customercont = data.data.customercont; 
                    box.businesscope = data.data.businesscope; 
                    box.contacts = data.data.contacts;
                    box.pronice = data.data.pronice;
                    box.city = data.data.city;
                    box.area = data.data.area; 
                    box.detailadrr = data.data.detailadrr; 
                    box.shopIpt = data.data.shopname;//店铺名称
                    var star = data.data.serverstars;
                    if(star == ''|| star == undefined || star == null){
                        box.value = 0;
                    }else{
                        box.value = data.data.serverstars;
                    }
                    box.grade = data.data.grade;
                    box.serviceimg = data.data.serviceimg;
                    box.servicedetails = data.data.detailedphone;
                    box.web = data.data.companyurl;
                    box.email = data.data.email; 
                    box.customerqq = data.data.customerqq; 
                    box.intro = data.data.introduction;
                    box.cases = data.data.servicecase;
                    box.originalfile = data.data.honor;
                    box.describeTeam = data.data.servicetame;
                    box.describeService = data.data.serviceflow;
                    box.describeSafe = data.data.serviceensure;
                    box.other = data.data.other;
                    box.originalfile2 = data.data.detailedphone;
                    box.originalfile2 = box.originalfile2.substr(0,box.originalfile2.length-1);
                    box.originaldata = box.originalfile2.split(',');
                    box.pdfpath1 = data.data.businesslicense;//营业执照
                    box.pdfpath2 = data.data.zconstacpath;//联系人身份证正面
                    box.pdfpath3 = data.data.fconstacpath;//联系人身份证反面
                    box.agencyimg = data.data.agencyimg
                    //console.log(box.originaldata);
                }
            },function(data){
                //layer.msg(data.msg);    
            })
        },
        getHoney:function(){
            htajax.get("/ht-biz/honeymanager/getHoneyTotal", function (data) {
                if (data.code == 10000) {
                    box.userHoney = data.data;
                }
                box.getGrade();
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getGrade:function(){
            htajax.get("/ht-biz/honeymanager/getUserLevel?type=" + 2, function (data) {
                if (data.code == 10000) {
                    if(data.data != null){
                        box.userGrade = data.data.sort;
                        box.userCode = data.data.code;
                        box.value = data.data.code;
                    }
                    
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //上传头像
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
                            // console.log(data);
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
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})
