/**
 * Created by ACER on 2019/8/19.
 */

var box = new Vue({
    el: '#box',
    data() {
        return {
             //信息显示
             stats:'',
             type:'',
             code:'',
             serviceType:'',
             name:'',
             province:'',
             city:'',
             area:'',
             detailed_address:'',
             shop:'',
             linkman:'',
             phone:'',
             customer:'',
             qq:'',
             scope:'',
             pdfpath1:'',
             pdfpath2:'',
             pdfpath3:'',
             netryname: '',
             pdfpath4:'',
            isagency:'',
           
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.inquireService();
        },
        
        inquireService:function(){
            htajax.postForm("/ht-biz/service/judge",{},function(data){
                console.log(data);
                if(data.code == 10000){
                    box.stats = data.data.stats;//审核状态
                    box.type = data.data.netrytype;//入驻类型
                    box.code = data.data.socialcode;//社会统一信用代码
                    box.serviceType = data.data.servicetype;//服务商类型 1：全部 2：科技服务咨询 3：知识产权代理 4：会计事务所 5：其他
                    box.name = data.data.banme;//企业名称(机构入驻)真实名称（个人入驻）
                    box.province = data.data.pronice;//省
                    box.city = data.data.city;//市
                    box.area = data.data.area;//区
                    box.detailed_address = data.data.detailadrr;//详细地址
                    box.shop = data.data.shopname;//服务商铺名称
                    box.linkman = data.data.contacts;//联系人
                    box.phone = data.data.phone;//联系人电话
                    box.customer = data.data.customercont;//客服联系
                    box.qq = data.data.customerqq;//客服qq
                    box.scope = data.data.businesscope;//业务范围多个用逗号隔开

                    box.pdfpath1 = data.data.businesslicense;//社会统一信用代码
                    box.pdfpath2 = data.data.zconstacpath;//联系人身份证正面
                    box.pdfpath3 = data.data.fconstacpath;//联系人身份证反面
                    box.pdfpath4 = data.data.agencyimg;//
                    box.isagency = data.data.isagency
                    box.netryname = data.data.netryname;
                    var backstring = box.serviceType.split(';');
                    var arr = [];
                    var serviceType1,serviceType2,serviceType3,serviceType4;
                    for(var i=0;i<=4;i++){
                        if(backstring[i] == 2){
                            serviceType1 = '科技服务咨询';
                            arr.push(serviceType1);
                        }
                        if(backstring[i] == 3){
                            serviceType2 = '知识产权代理';
                            arr.push(serviceType2);
                        }
                        if(backstring[i] == 4){
                            serviceType3 = '会计事务所';
                            arr.push(serviceType3);
                        }
                        if(backstring[i] == 5){
                            serviceType4 = '其他';
                            arr.push(serviceType4);
                        }
                    }
                    // console.log(arr);
                    box.serviceType = arr.toString();
                }
            },function(data){
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