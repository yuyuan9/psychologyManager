/**
 * Created by ACER on 2019/8/12.
 */
// Vue.filter('hideMiddle', function (value) {
//     var strval = value.toString();
//     return value = `${strval.substring(0,3)}****${strval.substring(strval.length-4)}`
// });
var vm = new Vue({
    el:'#user',
    data:function() {
        return {
            userId:'',
            username:'',
            code:'',
            allkeyword:'',
            headImg:'',
            userType:'',
            cityOption:[],
            cityChild:[],
            targetId:'',
            myName:'',
            cityName:'全国',
            cityName1:'',
            province:'',
            parentId:'',
            childId:'',
            parentName: '',
            childName: '',
            tap:2,
            mycity:'',
        }
    },
    created :function() {
        this.initPage();
    },
    methods:{
        initPage:function(){
            this.getUserInfo();
            this.getCityCook();
            // this.cityChange();
        },
        // 获取用户的信息
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser", function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    vm.userId = data.data.userId;
                    vm.username = data.data.username;
                    vm.code = data.code;
                    vm.headImg = data.data.headImg;
                    vm.userType = data.data.userType;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
        //获取cook
        getCityCook: function () {
            var cookie_val = getCookie("citycookie");
            // console.log(cookie_val);
            if (cookie_val != "") {
                var values = cookie_val.split(",");
                this.cityName = values[0];
                this.targetId = values[1];
                this.cityName1 = values[2];   
            } else {
                this.cityName = '全国';
                this.targetId = '33';
                this.cityName1 = '';
            }
            this.cityChange();
        },
        //城市切换
        cityChange: function () {
            htajax.get('/ht-biz/sysregionset/firstregion',
                function (data) {
                    if (data.code == 10000) {
                        vm.cityOption = data.data;
                        vm.getCity();
                    }
                }, function (data) {
                    //错误回调
                    // console.log(data.msg)
                });
        },
        //获取选择的城市
        getCity:function(id,name){
            if (this.tap == 2){//判断是否初始化 2为初始化 1是为点击
                this.parentId = this.targetId;
                var timer = setTimeout(function () {
                    box.getBanner(vm.parentId, '');//首页
                    if (vm.cityName.indexOf('省') >= 0 || vm.cityName.indexOf('自治区') >= 0) {
                        box.getDeclare(vm.cityName, vm.cityName1);//首页
                        box.getPublicity(vm.cityName, vm.cityName1);//首页
                        box.getPolicy(vm.cityName, vm.cityName1);//首页
                        library.getHeadCity(vm.cityName, vm.cityName1);//文库
                        box1.getHeadCity(vm.cityName, vm.cityName1);//政策解读
                    }
                    if (vm.cityName.indexOf("北京") >= 0 || vm.cityName.indexOf("天津") >= 0 || vm.cityName.indexOf("上海") >= 0 || vm.cityName.indexOf("重庆") >= 0) {
                        box.getDeclare(vm.cityName, vm.cityName1);//首页
                        box.getPublicity(vm.cityName, vm.cityName1);//首页
                        box.getPolicy(vm.cityName, vm.cityName1);//首页
                        library.getHeadCity(vm.cityName, vm.cityName1);//文库
                        box1.getHeadCity(vm.cityName, vm.cityName1);//政策解读
                    }else {
                        library.getHeadCity(vm.cityName,vm.cityName1);//文库
                        box1.getHeadCity(vm.cityName,vm.cityName1);//政策解读
                    }
                    
                }, 500)
            }else{
                if (id == undefined) { id = '' }
                if (name == undefined) { name = "全国" }
                this.parentId = id;
                this.parentName = name;
                this.myName = name;
                city = this.parentName + "," + id + ',' + this.childName;
                setCookie("citycookie", city, 1); //cookie过期时间为1天。
                // console.log(city);
                var timer = setTimeout(function () {
                    box.getBanner(vm.parentId, '');//首页
                    box.getDeclare(name, '');//首页
                    box.getPublicity(name, '');//首页
                    box.getPolicy(name, '');//首页
                    library.getHeadCity(name, '');//文库
                    box1.getHeadCity(name,'');//政策解读
                }, 500)
            }
            htajax.get('/ht-biz/sysregionset/chileregion?id=' + id,
            function (data) {
                if (data.code == 10000) {
                    vm.cityChild=data.data;
                }
            }, function (data) {
                //错误回调
                // console.log(data.msg)
            });
        },
        //城市子级
        getChild:function(id,name){
            if (this.tap == 2) {//判断是否初始化 2为初始化 1是为点击
                this.childId = this.targetId;
                var timer = setTimeout(function () {
                    box.getBanner(vm.parentId, '');//首页
                    if (vm.cityName.indexOf('省') >= 0 || vm.cityName.indexOf('自治区') >= 0) {
                        box.getDeclare(vm.cityName, vm.cityName1);//首页
                        box.getPublicity(vm.cityName, vm.cityName1);//首页
                        box.getPolicy(vm.cityName, vm.cityName1);//首页
                        library.getHeadCity(vm.cityName, vm.cityName1);//文库
                        box1.getHeadCity(vm.cityName, vm.cityName1);//政策解读
                    }
                    if (vm.cityName.indexOf('市') >= 0) {
                        if (vm.cityName.indexOf("北京市") >= 0 || vm.cityName.indexOf("天津市") >= 0 || vm.cityName.indexOf("上海市") >= 0 || vm.cityName.indexOf("重庆市") >= 0) {
                            box.getDeclare(vm.cityName, vm.cityName1);//首页
                            box.getPublicity(vm.cityName, vm.cityName1);//首页
                            box.getPolicy(vm.cityName, vm.cityName1);//首页
                            library.getHeadCity(vm.cityName, vm.cityName1);//文库
                            box1.getHeadCity(vm.cityName, vm.cityName1);//政策解读
                        } else {
                            library.getHeadCity(vm.cityName, vm.cityName1);//文库
                            box1.getHeadCity(vm.cityName, vm.cityName1);//政策解读
                        }
                    }
                }, 500)
            } else {
                if (id == undefined) { id = '' }
                if (name == undefined) { name = '全国' }
                this.childId = id;
                this.childName = name;
                this.myName = name;
                city = this.parentName + "," + id + ',' + this.childName;
                setCookie("citycookie", city, 1); // cookie过期时间为1天。
                // console.log(city);
                var timer = setTimeout(function () {
                    box.getBanner('', vm.childId);//首页
                    box.getDeclare(vm.parentName,name);//首页
                    box.getPublicity(vm.parentName,name);//首页
                    box.getPolicy(vm.parentName,name);//首页
                    library.getHeadCity(vm.parentName,name);//文库
                    box1.getHeadCity(vm.parentName,name);//政策解读
                }, 500)
            }
        },
        showChild: function (id, name) {
            if (id == undefined) { id = '' };
            if (name == undefined) { name = "全国" };
            this.parentId = id;
            htajax.get('/ht-biz/sysregionset/chileregion?id=' + id,
                function (data) {
                    if (data.code == 10000) {
                        //console.log(data);
                        vm.cityChild = data.data;
                    }
                }, function (data) {
                    //错误回调
                    // console.log(data.msg)
                });
        },
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        quitRegister:function(){
            htajax.get("/ht-shiro/loginuser/loginOut",function(data){
                if(data.code == 10000){
                    location.reload();
                }
            },function(data){
                //layer.msg(data.msg);
            })
        },
    }
})
