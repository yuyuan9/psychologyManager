var projectcx = new Vue({
    el: '#app-details',
    data: {
    	
    	province: '',
    	city:'',
        area:'',
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
        back:function(){
            var index=this.getUrlData("index");
            var province=this.getUrlData('province');
            var city=this.getUrlData('city');
            var area=this.getUrlData('area');
            var keyword= this.getUrlData('keyword') || '';
            console.log(keyword)
            var nature=this.getUrlData('nature');
        	if(index==null || index==''){
        		index=1;
        	}
        	if(index==0){
        		location.href="/ht-biz/app/index/index";
        	}else{
        		location.href="/ht-biz/app/policydig/index?index="+index+"&province="+province+"&city="+city+"&area="+area+"&keyword="+keyword+"&nature="+nature;
        	}
        },
        //获取链接的参数
        getUrlData:function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
      
        entityData:function(){
        	//alert(projectcx.page.current);
        	//alert(projectcx.page.size);
        	//政策接口调用
        	 var id=this.getUrlData('id');
        	 htajax.get("/ht-biz/app/policydig/detail?id="+id,
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