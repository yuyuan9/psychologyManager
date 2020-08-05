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
    el:'#box',
    data() {
        return {
            allkeyword: '',
            cur:'',
            curInfo:0,
            deleteShow:'',
            deleteShow2:'',
            userId:'',
            username:'',
            code:'',
            progressBar:50,
            userHoney:'',
            userGrade:'',
            userInfo:'',
            userCode:'',
            userInfoName:'',
            userInfoPhone:'',
            userInfoEamil:'',
            userInfoType:'',
            companyname:'',
            companyid:'',
            compregcode:'',
            regdate:'',
            province:'',
            city:'',
            area:'',
            addr:'',
            weburl:'',
            mainproduct:'',
            techfield:'',
            remark:'',
            billpath:'',
            businesspath:'',
            newPhoneNumber:'',
            ccid:'',
            alterRemark:'',
            headImg:'',
        }
    },
    created() {
        this.initPage();
    },
    methods:{
        initPage:function(){
            this.getUserInfo();
            this.getHoney();
            
        },
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
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
                    // console.log(box.userType);
                }else{
                    window.location.href='/ht-biz/login/login';
                }
                box.getUserInformation();
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getHoney:function(){
            htajax.get("/ht-biz/honeymanager/getHoneyTotal", function (data) {
                if (data.code == 10000) {
                    box.userHoney = data.data;
                }else{
                    //layer.msg(data.msg);
                }
                box.getGrade();
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getGrade:function(){
            htajax.get("/ht-biz/honeymanager/getUserLevel?type=" + 1, function (data) {
                if (data.code == 10000) {
                    box.userGrade = data.data.sort;
                    box.userCode = data.data.code;
                }else{
                    //layer.msg(data.msg);
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        getUserInformation:function(){
            htajax.postForm("/ht-shiro/sysuser/myinfo",{}, function (data) {
                if (data.code == 10000) {
                    box.ccid= data.data.id;
                    box.userInfo = data.data;
                    box.userInfoName = data.data.username;
                    box.userInfoPhone = data.data.phone;
                    box.userInfoEamil = data.data.email;
                    box.userInfoType = data.data.userType;
                    box.companyname = data.data.companyname;
                    box.companyid = data.data.companyid;
                    box.compregcode = data.data.compregcode;
                    box.regdate = data.data.regdate;
                    box.province = data.data.province;
                    box.city = data.data.city;
                    box.area = data.data.area;
                    box.addr = data.data.addr;
                    box.weburl = data.data.weburl;
                    box.mainproduct = data.data.mainproduct;
                    box.techfield = data.data.techfield;
                    box.remark = data.data.remark;
                    box.billpath = data.data.billpath;
                    box.businesspath = data.data.businesspath;
                }else{
                    layer.msg(data.msg);
                }
            }, function (data) {
                layer.msg(data.msg);
            });
        },

        //修改手机号码
        changePhoneNumber:function(){
            layer.open({
                type: 1,
                area: ['600px', '400px'], //宽高
                content: $('#ids1') //这里content是一个DOM，这个元素要放在body根节点下
            });
        },
        changePassword:function(){
            layer.open({
                type: 1,
                area: ['420px', '240px'], //宽高
                content: $('#ids2') //这里content是一个DOM，这个元素要放在body根节点下
            });
        },
        //修改信息
        personInfo:function(){
            layer.open({
                type: 1,
                area: ['900px', '70%'], //宽高
                content: $('#ids3') //这里content是一个DOM，这个元素要放在body根节点下
            });
        },
        submitAlter:function(){
            var alterCompanyname = $.trim($("input[name='alterCompanyname']").val());
            var alterRegdate = $.trim($("input[name='alterRegdate']").val());
            var alterAddr = $.trim($("input[name='alterAddr']").val());
            var alterWeburl = $.trim($("input[name='alterWeburl']").val());
            var alterMainproduct = $.trim($("input[name='alterMainproduct']").val());
            var alterTechfield = $.trim($("input[name='alterTechfield']").val());
            var alterRemark = this.Remark;

            htajax.postForm("/ht-shiro/compuser/update", {"id" : box.ccid,"companyname" : alterCompanyname,"regdate":alterRegdate,"addr":alterAddr,"weburl":alterWeburl,"mainproduct":alterMainproduct,"techfield":alterTechfield,"Remark":alterRemark},
                function(data){
                    console.log(data);
                    if(data.code == 10000){
                        layer.msg("修改成功");
                        layer.closeAll();
                        location.reload();
                    }
                },function(data){
                    console.log(data)
                }
            )
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },

    }
})
