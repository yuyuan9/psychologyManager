var vm = new Vue({
    el: '#app_usercenter',
    data: {
        //tab切换
        username:'',
    },
    created: function () {
    	this.initPage();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
    	   this.getUserInfo();
        },
        
        clickurl:function(url){
        	location.href=url;
        },
        getUserInfo: function () {
            htajax.get("/ht-shiro/sysuser/getUser", function (data) {
                //console.log(data);
                if (data.code == 10000) {
                    //vm.userId = data.data.userId;
                    vm.username = data.data.username;
                    //vm.code = data.code;
                    //vm.headImg = data.data.headImg;
                    //vm.userType = data.data.userType;
                }
            }, function (data) {
                //layer.msg(data.msg);
            });
        },
       
        
    	
    },
  
});


