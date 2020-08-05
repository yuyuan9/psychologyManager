var projectcx = new Vue({
    el: '#app_match_details',
    data: {
        //tab切换
        //keyword:'',
    	id:'',
        entity:[],
    },
    created: function () {
    	this.initPage();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
    	  
    	   this.entityData();
        },
        //获取链接的参数
        getUrlData:function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        back:function(){
        	history.back();
        },
        clickurl:function(url){
        	location.href=url;
        },
        entityData:function(){
        	//alert(projectcx.page.current);
        	//alert(projectcx.page.size);
        	//政策接口调用
        	 var id=this.getUrlData('id');
        	 htajax.get("/ht-biz/app/policymatch/detail?id="+id,
             function (data) {
                 if (data.code == 10000) {
                	 projectcx.entity=data.data;
                	 console.log(data.data);
                	 layer.closeAll();
                 } else {
                	 layer.closeAll();
                 }
             },
             function (data) {
            	 layer.closeAll();
             });
     
        },
        
       
        
    	
    },
  
});


Vue.filter('date', function (value, formatString) {
	formatString = formatString || 'YYYY年MM月DD日';
	return moment(value).format(formatString);
});