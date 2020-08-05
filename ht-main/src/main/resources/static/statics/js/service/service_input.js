/**
 * Created by ACER on 2019/8/19.
 */

var box = new Vue({
    el: '#box',
    data() {
        return {
            allkeyword:'',
            type:1,
            reason:'',
            province: '',
            city: '',
            area: '',
            usertown:'',
            province1: '',
            city1: '',
            area1: '',
            //文件路径
            pdfpath1:'',
            originalfile1:'',
            pdfpath2:'',
            originalfile2:'',
            pdfpath3:'',
            originalfile3:'',
            pdfpath4: '',
            originalfile4: '',
            fileName1:'',
            fileName2:'',
            fileName3: '',
            fileName4: '',
            isProtocol:true,
            code:'',
            serviceType1:'',
            serviceType2:'',
            serviceType3:'',
            serviceType4:'',
            serviceType5:'',
            nameIpt:'',
            detailed_address:'',
            shopIpt:'',
            linkman:'',
            phone:'',
            diqu:'',
            customerIpt:'',
            qqIpt:'',
            netryname: '',
            //回显
            bid:'',
            backtype:'',
            backcode:'',
            backtype:'',
            backcode:'',
            backserviceType:'',
            backname:'',
            backprovince:'',
            backarea:'',
            backdetailed_address:'',
            backshop:'',
            backlinkman:'',
            backphone:'',
            backcustomer:'',
            backqq:'',
            backscope:'',
            backpdfpath1:'',
            backpdfpath2:'',
            backpdfpath3: '',
            backpdfpath4: '',
            backnetryname: '',
            backnetryname: '',
            tcg: '',
            FormArr: [
                {
                    index: 0,
                    value: ["天津市","河东区"]
                }
            ],
            tecnologyProps: {
                value: 'n',
                label: 'n',
                children: 's'
            },
            //
            organization: 0,//1是 0否
        }
    },
    created() {
        this.initPage();
    },
    mounted() {
        setTimeout(function () {
            box.getcity();
        }, 1000)
    },
    methods: {
        initPage: function () {
            this.inquireService();
        },
        //
        organizationCheck:function(){
            // console.log(this.organization);
        },
        //城市
        getcity:function(){
            //省市区
            $(".pick-area4").pickArea({
                // "format":"", //格式
                "width":"400",//设置“省市县”文本边框的宽度
                "height":"50",//设置“省市县”文本边框的高度
                "borderColor":"#dcdfe6",//设置“省市县”文本边框的色值
                "arrowColor":"#dcdfe6",//设置下拉箭头颜色
                "listBdColor":"#dcdfe6",//设置下拉框父元素ul的border色值
                "color":"#666666",//设置“省市县”字体颜色
                "fontSize":"14px",//设置字体大小
                "hoverColor":"#1E9FFF",
                "paddingLeft":"30px",//设置“省”位置处的span相较于边框的距离
                "arrowRight":"30px",//设置下拉箭头距离边框右侧的距离
                "maxHeight":"247px",
                //"preSet":"",//数据初始化 ；这里的数据初始化有两种方式，第一种是用preSet属性设置，第二种是在a标签里使用name属性设置
                "getVal":function(){//这个方法是每次选中一个省、市或者县之后，执行的方法
                    // console.log($(".pick-area-hidden").val())
                    // console.log($(".pick-area-dom").val())
                    var thisdom = $("."+$(".pick-area-dom").val());//返回的是调用这个插件的元素pick-area，$(".pick-area-dom").val()的值是该元素的另一个class名，这个class名在dom结构中是唯一的，不会有重复，可以通过这个class名来识别这个pick-area
                    thisdom.next().val($(".pick-area-hidden").val());//$(".pick-area-hidden").val()是页面中隐藏域的值，存放着每次选中一个省、市或者县的时候，当前文本存放的省市县的最新值，每点击一次下拉框里的li，这个值就会立即更新
                }
            });
            //console.log('jq:'+$(".pick-area").attr('name'));
        },
        allSeach:function(){
            location.href ="/ht-biz/policydig/index?keyword="+box.allkeyword;
        },
        inquireService:function(){
            htajax.postForm("/ht-biz/bservice/judge",{},function(data){
                // console.log(data);
                if(data.code == 10000){
                    if(data.data == 1){
                        //layer.msg("待审核");
                        $(".service_info1").fadeIn(500);
                        return ;
                    }
                    if(data.data == 2){
                        //layer.msg("正常");
                        $(".service_info2").fadeIn(500);
                        return ;
                    }
                    if(data.data == 3){
                        //layer.msg("保存");
                        $(".service_info3").fadeIn(500);
                    }
                    if(data.data == 4){
                        //layer.msg("审核拒绝");
                        $(".service_info4").fadeIn(500);
                        box.reason = data.reserveData.reason;
                    }
                    box.bid = data.reserveData.bid;
                    box.backtype = data.reserveData.netrytype;
                    box.backcode = data.reserveData.socialcode;
                    box.backtype = data.reserveData.netrytype;
                    box.backcode = data.reserveData.socialcode;
                    box.backserviceType = data.reserveData.servicetype;
                    box.backname = data.reserveData.banme;
                    box.backprovince = data.reserveData.pronice;
                    box.backcity = data.reserveData.city;
                    box.backarea = data.reserveData.area;
                    
                    // console.log(box.usertown);
                    box.backdetailed_address = data.reserveData.detailadrr;
                    box.backshop = data.reserveData.shopname;
                    box.backlinkman = data.reserveData.contacts;
                    box.backphone = data.reserveData.phone;
                    box.backcustomer = data.reserveData.customercont;
                    box.backqq = data.reserveData.customerqq;
                    box.backscope = data.reserveData.businesscope;
                    box.backpdfpath1 = data.reserveData.businesslicense;
                    box.backpdfpath2 = data.reserveData.zconstacpath;
                    box.backpdfpath3 = data.reserveData.fconstacpath;
                    box.backpdfpath4 = data.reserveData.agencyimg;
                    box.backnetryname = data.reserveData.netryname;
                    
                }
            },function(data){
                //layer.msg(data.msg);    
            })
        },
        //回显信息
        backMessages:function(){
            $(".pick-show").remove();
            if (box.backprovince == null) { box.backprovince = "请选择省" }
            if (box.backcity == null) { box.backcity = "请选择市" }
            if (box.backarea == null) { box.backarea = "请选择区" }
            box.usertown = box.backprovince + '/' + box.backcity + '/' + box.backarea;
            $(".pick-area").attr('name', box.usertown);
            this.type = box.backtype;
            this.code = box.backcode;
            //this.serviceType = box.backserviceType;
            // if(box.backserviceType.indexOf('2')){
            // 	 this.serviceType2=true;
            // }
            // if(box.backserviceType.indexOf('3')){
           	//  	this.serviceType3=true;
            // } 
            // if(box.backserviceType.indexOf('4')){
            // 	this.serviceType4=true;
            // } 
            // if(box.backserviceType.indexOf('5')){
            // 	this.serviceType5=true;
            // } 
           
            this.nameIpt = box.backname;
            this.province = box.backprovince;
            this.city = box.backcity;
            this.area = box.backarea;
            this.detailed_address = box.backdetailed_address;
            this.shopIpt = box.backshop;
            this.linkman = box.backlinkman;
            this.phone = box.backphone;
            this.customerIpt = box.backcustomer;
            this.qqIpt = box.backqq;
            this.diqu = box.backscope;
            $("input[name='diqu']").val(box.backscope);
            this.pdfpath1 = box.backpdfpath1;
            this.originalfile1 = box.backpdfpath1;

            this.pdfpath2 = box.backpdfpath2;
            this.originalfile2 = box.backpdfpath2;

            this.pdfpath3 = box.backpdfpath3;
            this.originalfile3 = box.backpdfpath3;
            this.originalfile3 = box.backpdfpath3;
            this.originalfile4 = box.backpdfpath4;

            this.netryname = box.backnetryname
            $(".one_path").show();
            $(".two_path").show();
            $(".three_path").show();
            box.getcity();
        },
        //关闭
        closeicon0:function(){
            $(".service_info0").fadeOut(500);
        },
        closeicon1:function(){
            $(".service_info2").fadeOut(500);
        },
        closeicon2:function(){
            $(".service_info3").fadeOut(500);
        },
        closeicon3:function(){
            $(".service_info4").fadeOut(500);
        },
        closeicon4:function(){
            $(".service_info1").fadeOut(500);
        },

        clearIpt1:function(){
            $("#file1").attr('type','text');
            $("#file1").attr('type','file');
        },
        clearIpt2:function(){
            $("#file2").attr('type','text');
            $("#file2").attr('type','file');
        },
        clearIpt3:function(){
            $("#file3").attr('type','text');
            $("#file3").attr('type','file');
        },
        clearIpt4: function () {
            $("#file4").attr('type', 'text');
            $("#file4").attr('type', 'file');
        },
        //获取上传的文件属性
        tirggerFile: function (event,el) {
            // console.log(event);
            // console.log($(`#${el}`));
            var id=event.target.id;
            var files = $(`#${el}`).prop('files');
            var data = new FormData();
            data.append('file', files[0]);
            
            var checktype=false;
            var filetypes =["image/jpeg","image/gpg","image/bmp","image/jpg","image/png","image/tif","image/gif"];
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
	                },error:function(){
	                	layer.close(index);
	                	layer.msg('上传失败');
	                }
	            });
            
            }else{
            	 layer.msg("文件大于5M拒绝上传");
            }
        },
        checkFileSize:function(files){
        	 var fileSize = 0;
        	 //alert(files[0].type)
        	 //console.log(files[0]);
        	 checktype=false;
             if(files.length!=0){
                 fileSize = files[0].size;
             }
             if(fileSize<=5242880){
            	 return true;
             }else{
            	 return false;
             }
        },
        protocol: function () { //协议解除注册按钮
            if (this.isProtocol) {
                $("#submitReg").attr("disabled", true);
            } else {
                $("#submitReg").removeAttr("disabled");
            }
        },
        //提交信息
        settled:function(index){
            var index = index;
            var type = this.type;  //1:服务商 ,2:个人
            var code=$.trim($("input[name='code']").val());
            //var serviceType = $('input:radio[name="serviceType"]:checked').val();
            // var serviceType='';
            // if(type==1){
            // 	serviceType=((this.serviceType2==true)?"2;":'')+((this.serviceType3==true)?"3;":'')+((this.serviceType4==true)?"4;":'')+((this.serviceType5==true)?"5;":'')
            // }
            var name=$.trim($("input[name='nameIpt']").val());
            var province = $(".pick-province").text();
            var city = $('.pick-city').text();
            var area = $('.pick-county').text();
            var detailed_address=$.trim($("input[name='detailed_address']").val());
            var shop=$.trim($("input[name='shop']").val());
            var linkman=$.trim($("input[name='linkman']").val());
            var phone=$.trim($("input[name='phone']").val());
            var customer=$.trim($("input[name='customer']").val());
            var qq=$.trim($("input[name='qq']").val());
            var netryname=$.trim($("input[name='netryname']").val());
            var scope = $.trim($("input[name='diqu']").val());

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
            if (organization == 0) { organization = '0' }
            if(type==1){
                if (code == '') {layer.msg("请输入社会统一信用代码"); return false;} 
                // if (organization =='1') { layer.msg("是否认证成为相关服务机构"); return false; } 
                if (!(str.test(code))) {layer.msg("请输入正确的社会统一信息代码"); return false;} 
                if (code.length != 18) {layer.msg("请输入正确的社会统一信息代码"); return false;} 
                // if (!$('input[name="manager"]').is(":checked")) {layer.msg("请选择服务商类型");return false;}
                if (name == '') { layer.msg("请输入机构名称"); return false; }
                if (shop == '') { layer.msg("请输入服务商铺名称"); return false; }
	            
	            if (province == '请选择省'){ layer.msg("请选择省");return false;}
	            if (city == '请选择市'){layer.msg("请选择市");return false;}
	            if (area == '请选择区'){layer.msg("请选择区");return false;}
	            
	            if (linkman == ''){layer.msg("请输入联系人名称"); return false; }
                if (phone == "") {layer.msg("请输入手机号码");return false;	}
                if (!(mflag)) { layer.msg("请输入正确的手机号码"); return false; }
	            if (customer == ''){layer.msg("请输入客服联系人");return false;}
	            if (qq == ''){layer.msg("请输入客服QQ");return false;}
                if (scope == '') { layer.msg("请选择业务服务地区");return false;}
	            if (originalfile1 == ''){layer.msg("请上传企业营业执照"); return false;}
	            if (originalfile2 == ''){layer.msg("请上传联系人身份证正面");return false;}
	            if (originalfile3 == ''){layer.msg("请上传联系人身份证反面");return false;	}
                if (organization == 1){
                    if (originalfile4 == '') { layer.msg("请上传认定创新劵/服务卷服务机构相关材料证明"); return false;}
                }
            }else if(type==2){
                if (netryname == ''){layer.msg("请填写真实名称");return false;}
                if (shop == '') { layer.msg("请输入服务商铺名称"); return false; }
	            if (province == '请选择省'){ layer.msg("请选择省");return false;}
	            if (city == '请选择市'){layer.msg("请选择市");return false;}
	            if (area == '请选择区'){layer.msg("请选择区");return false;}
	            
	            if (linkman == ''){layer.msg("请输入联系人名称"); return false; }
                if (phone == "") {layer.msg("请输入手机号码");return false;	}
                if (!(mflag)) { layer.msg("请输入正确的手机号码"); return false; }
                if (customer == ''){layer.msg("请输入客服联系人");return false;}
	            if (qq == ''){layer.msg("请输入客服QQ");return false;}
                if (scope == '') { layer.msg("请选择业务服务地区");return false;}
	            if (originalfile2 == ''){layer.msg("请上传联系人身份证正面");return false;}
	            if (originalfile3 == ''){layer.msg("请上传联系人身份证反面");return false;	}
                name=netryname;
            }
            htajax.postForm("/ht-biz/bservice/savaService",{
                bid:this.bid,
                stats:1,//等待审核
                netrytype:type,
                isagency: organization,
                socialcode:code,
                // servicetype:serviceType,
                banme:name,
                pronice:province,
                city:city,
                area:area,
                detailadrr:detailed_address,
                shopname:shop,
                contacts:linkman,
                phone:phone,
                customercont:customer,
                customerqq:qq,
                businesscope:scope,
                businesslicense:originalfile1,
                zconstacpath:originalfile2,
                fconstacpath:originalfile3,
                netryname:netryname,
                agencyimg: originalfile4,

            },function(data){
                // console.log(data);
                if(data.code == 10000){
                    $(".service_info1").fadeIn('500',function(){
                        var timer=setTimeout(function(){
                            $(".service_info1").fadeOut('500');
                            window.location.href='/ht-biz/service/index/loading';
                        },2000);
                    });
                }
            },function(data){
                //layer.msg(data.msg);    
            })
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