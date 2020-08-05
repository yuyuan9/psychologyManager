/**
 * Created by ACER on 2019/8/19.
 */

var box = new Vue({
    el: '#box',
    data() {
        return {
            type:1,
            netrytype:'',
            value: 0,//星星
            //文件路径
            originalfile1:'',
            originalfile2:[],
            originaldata:[],
            fileName1:'',
            fileName2:'',
            originalfile:'',
            fileName:'',
            isProtocol:true,
            //数据
            agencyimg:'',
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
            tcg: '',
            //预览信息
            shopimg:'',
            lookactive:'',
            lookarea:'',
            lookbanme: '',
            lookbusinesscope: '',
            lookcity: '',
            lookcontacts: '',
            lookcustomercont: '',
            lookcustomerqq: '',
            lookdetailadrr: '',
            lookdetailedphone: '',
            lookgrade: '',
            lookphone: '',
            lookpronice: '',
            lookregProvince: '',
            lookregarec: '',
            lookregcity: '',
            lookserviceimg: '',
            lookshopname: '',
            lookhonor: '',
            lookdescribeService: '',
            lookcases: '',
            lookdescribeTeam: '',
            lookdescribeSafe: '',
            lookother:'',
            //弹框
            saveframe: false,
            auditframe: false,
            haveSafe: '',//保障金
            isagency: '',//认证服务机构
            businesslicense:'',
            isbond:'',
        }
    },
    created() {
        this.initPage();
    },
    // mounted() {
    //     window.addEventListener('scroll', this.scrollToTop)
    // },

    methods: {
        initPage: function () {
            this.inquireService();
            this.getGrade();
        },
        // scrollToTop() {
        //     var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
        //     console.log(scrollTop)
        // },
        getSafe: function () {
            htajax.get("/ht-biz/guaran/list", function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    box.haveSafe = data.data.records;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        }, 
        getGrade: function () {
            htajax.get("/ht-biz/honeymanager/getUserLevel?type=" + 2, function (data) {
                if (data.code == 10000) {
                    if (data.data != null) {
                        box.userGrade = data.data.sort;
                        box.userCode = data.data.code;
                        box.value = data.data.code;//星级
                    }

                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        inquireService:function(){
            htajax.postForm("/ht-biz/service/judge",{},function(data){
                if(data.code == 10000){
                    // console.log(data);
                    var star = data.data.serverstars;
                    if (star == '' || star == undefined || star == null) {
                        box.value = 0;
                    } else {
                        box.value = data.data.serverstars;
                    }
                    box.netrytype = data.data.netrytype;
                    //console.log(box.netrytype);
                    if (box.netrytype==2){
                        $("#teams").hide();
                    }
                    box.isbond = data.data.isbond;
                    box.businesslicense = data.data.businesslicense;
                    box.agencyimg = data.data.agencyimg;
                    box.originalfile1 = data.data.shopimg;
                    box.originalfile2= data.data.detailedphone;
                    box.originalfile2 = box.originalfile2.substr(0,box.originalfile2.length-1);
                    box.originaldata = box.originalfile2.split(',');
                    box.web = data.data.companyurl;
                    box.email = data.data.email;
                    box.intro = data.data.introduction;
                    box.cases = data.data.servicecase;//al
                    box.originalfile = data.data.honor;//
                    box.describeTeam = data.data.servicetame;//td
                    box.describeService = data.data.serviceflow;//lc
                    box.describeSafe = data.data.serviceensure;//bz
                    box.other = data.data.other;
                    $('.summernote').summernote('code', box.originalfile);
                    $('.summernote-lc').summernote('code', box.describeService);
                    $('.summernote-al').summernote('code', box.cases);
                    $('.summernote-td').summernote('code', box.describeTeam);
                    $('.summernote-bz').summernote('code', box.describeSafe);
                    $('.summernote-other').summernote('code', box.other);
                    
                }
            },function(data){
                //layer.msg(data.msg);    
            })
        },
        openfile: function (e) {
            $(`.${e}`).click();
        },
        clearFile1:function(){
            $("#fileA").attr('type','text');
            $("#fileA").attr('type','file');
        },
        clearFile2:function(){
            $("#fileB").attr('type','text');
            $("#fileB").attr('type','file'); 
        },
        clearFile3:function(){},
        //获取上传的文件属性
        tirggerFile: function (event,el) {
            //var iptUrl = event.target.files[0];
            // console.log(event);
            // console.log($(`#${el}`));
            var id=event.target.id;
            var files = $(`#${el}`).prop('files');
            var data = new FormData();
            data.append('file', files[0]);
            
            var checktype=false;
            var filetypes =["image/gpg","image/bmp","image/jpg","image/png","image/tif","image/gif","image/jpeg"];
       	 	for(i=0;i<filetypes.length;i++){
	           	 if(filetypes[i].toLowerCase()==files[0].type.toLowerCase()){
	           		 checktype=true;
	           	 }
            }
            if(checktype==false){
            	layer.msg("上传文件格式不正确");
            }
            else if(this.checkFileSize(files)){
            	var index;
	            $.ajax({
	                type: 'POST',
	                url: "/uploadController/upload",
	                data: data,
	                cache: false,
	                processData: false,
	                contentType: false,
	                beforeSend:function(XHR){
	                	index = layer.load(1, {
	                		  shade: [0.5,'#fff'] //0.1透明度的白色背景
	                	});
	                },
	                success: function (data) {
	                	layer.close(index);
	                    if(data.code== 10000){
                            if(id == "fileA"){
                                box.fileName1 = data.data.fileName;
	                            box.originalfile1 = data.data.path;
                            }
	                        if(id == "fileB"){
                                box.fileName2 = data.data.fileName;
	                            box.originalfile2 = data.data.path;
                            }
                            if(id == 'file1'){
                                box.fileName = data.data.fileName;
                                box.originalfile = data.data.path;
                            }
	                        //console.log(data);
	                    }else{
	                        layer.msg(data.msg);
	                    }
	                },error:function(){
	                	layer.close(index);
	                	layer.msg('上传失败');
	                }
	            });
            
            }else{
            	 layer.msg("文件大于5M拒绝上传");
            }
            box.honer=box.originalfile+','+box.originalfile;
        },
        checkFileSize:function(files){
        	 var fileSize = 0;
        	 //alert(files[0].type)
        	 //console.log(files[0]);
        	 checktype=false;
             if(files.length!=0){
                 fileSize = files[0].size;
             }
            if (fileSize <= 5242880) {// 5242880=5m(文件大于5M拒绝上传)
            	 return true;
             }else{
            	 return false;
             }
             
        },
        //提交信息
        settled:function(ways){
            var ways = ways;
            var honor = $(".summernote").summernote("code");
            var describeService = $('.summernote-lc').summernote('code');
            var cases = $('.summernote-al').summernote('code');
            var describeTeam = $('.summernote-td').summernote('code');
            var describeSafe = $('.summernote-bz').summernote('code');
            var other = $('.summernote-other').summernote('code');
            var filesData = box.originalfile2;
            // if($("#superme").css("display") == "none"){
            //     var arr = '';
            //     for(var i in filesData){arr += filesData[i].ajax.data.path+',';}
            // }else{
            //     var arr = filesData;
            // }
            var email = $.trim($("input[name='email']").val());
            htajax.postForm("/ht-biz/service/savaService",{
                shopimg:this.originalfile1,//logo图
                //detailedphone: arr,//店铺详情介绍图
                companyurl:this.web,//网站
                email:email,//邮箱
                introduction: this.intro,//基本介绍
                servicecase:cases, 
                honor: honor,
                servicetame:describeTeam,
                serviceflow:describeService,
                serviceensure:describeSafe, 
                other:other, //其他
            },function(data){
                //console.log(data);
                if(data.code == 10000){
                    if (ways == 1) { box.saveframe = true;;}
                    if (ways == 2) { box.auditframe = true;  window.location.href = "/ht-biz/service/index/shop"}
                    
                }
            },function(data){
                layer.msg(data.msg);    
            })
        },
        //预览
        lookshit:function(){
            layer.open({
                type: 1,
                title: '预览',
                area: ['1200px', '600px'], //宽高
                shadeClose: true,
                content: $('#shopid') //这里content是一个DOM，这个元素要放在body根节点下
            });
            htajax.postForm("/ht-biz/service/getmyservice", {}, function (data) {
                if (data.code == 10000) {
                    //console.log(data);
                    box.shopimg = data.data.shopimg;
                    box.isagency = data.data.isagency;
                    box.lookactive = data.data.netrytype;
                    box.lookarea = data.data.area//: "铁东区"
                    box.lookbanme = data.data.banme//: "测试服务商gx"
                    box.lookbusinesscope = data.data.businesscope//: "包头市;乌海市;赤峰市;通辽市;鄂尔多斯市;"
                    box.lookcity = data.data.city//: "鞍山市"
                    box.lookcontacts = data.data.contacts//: "小明"
                    box.lookcustomercont = data.data.customercont//: "小王"
                    box.lookcustomerqq = data.data.customerqq//: "1213537017"
                    box.lookdetailadrr = data.data.detailadrr//: "nowhere"
                    box.lookdetailedphone = data.data.detailedphone//: ""
                    box.lookgrade = data.data.grade//: 0
                    box.lookphone = data.data.phone//: "18216199930"
                    box.lookpronice = data.data.pronice//: "辽宁省"
                    box.lookregProvince = data.data.regProvince//: "河北省"
                    box.lookregarec = data.data.regarec//: "路南区"
                    box.lookregcity = data.data.regcity//: "唐山市"
                    box.lookserviceimg = data.data.serviceimg//: ""
                    box.lookshopname = data.data.shopname//: "没有名称的店铺"
                    box.lookhonor = $(".summernote").summernote("code");
                    box.lookdescribeService = $('.summernote-lc').summernote('code');
                    box.lookcases = $('.summernote-al').summernote('code');
                    box.lookdescribeTeam = $('.summernote-td').summernote('code');
                    box.lookdescribeSafe = $('.summernote-bz').summernote('code');
                    box.lookother = $('.summernote-other').summernote('code');
                    var timer = setTimeout(function(){
                        $(".process_img1>p").children("img").attr("style", "");
                        $(".process_text>p").children("img").attr("style", "");
                        $(".process_img>p").children("img").attr("style", "");
                        $(".process_tt>p").children("img").attr("style", "");
                    },500)
                    
                }
            }, function (data) {
                layer.msg(data.msg);
            })
        },

        AddForm() {
            //console.log(this.FormArr) 
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
