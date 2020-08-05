/**
 * Created by ACER on 2019/8/19.
 */

var box = new Vue({
    el: '#box',
    data() {
        return {
            allkeyword:'',
            stats:'',
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.inquireService();
            // this.getType();
        },
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        enter:function(){
            if(vm.code == 10000){
                if(box.stats == 1){
                    //layer.msg("待审核");
                    location.href ="/ht-biz/service/index/loading";
                }
                if(box.stats == 2){
                    //layer.msg("正常");
                    location.href ="/ht-biz/service/index/loading";
                }
                if(box.stats == 4){
                    //layer.msg("拒绝");
                    location.href ="/ht-biz/service/index/service_input";
                }
                if(box.stats == ''){
                    //layer.msg("无");
                    location.href ="/ht-biz/service/index/service_input";
                }
            }else{
                $(".loginBox").show();
                // location.href ="/ht-biz/login/login";
            }
            
        },
        inquireService:function(){
            htajax.postForm("/ht-biz/bservice/judge",{},function(data){
                // console.log(data);
                if(data.code == 10000){
                    box.stats = data.data;
                    if(data.data == 2){
                        $("#enter").hide();
                    }
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