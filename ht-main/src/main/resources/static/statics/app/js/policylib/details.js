var projectcx = new Vue({
    el: '#app-details',
    data: {
    	keyword:'',
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
        toback:function(){
        	 var keyword=this.getUrlData('keyword');
        	 var province=this.getUrlData('provincedes');
        	 var city=this.getUrlData('citydes');
        	 var area=this.getUrlData('areades');
        	 var year=this.getUrlData('year');
             location.href="/ht-biz/app/policylib/index?keyword="+keyword+"&provincedes="+province+"&citydes="+city+"&areades="+area+"&year="+year;
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
        	 htajax.get("/ht-biz/app/policylib/detail?id="+id,
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