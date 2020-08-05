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
        back:function(){
        	 var keyword=this.getUrlData('keyword');
        	 var province=this.getUrlData('province');
        	 var city=this.getUrlData('city');
        	 var area=this.getUrlData('area');
        	 var year=this.getUrlData('year');
        	 location.href="/ht-biz/app/projectlib/list?keyword="+keyword+"&province="+province+"&city="+city+"&year="+year;
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
        	 htajax.get("/ht-biz/app/projectlib/detail?id="+id,
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
        toback:function(){
            var keyword=this.getUrlData('keyword');
            var province=this.getUrlData('provincedes');
            var city=this.getUrlData('citydes');
            var area=this.getUrlData('areades');
            var year=this.getUrlData('year');
            location.href="/ht-biz/app/projectlib/index?keyword="+keyword+"&provincedes="+province+"&citydes="+city+"&areades="+area+"&year="+year;
        },
       
    	
    },
  
});

Vue.filter('date', function (value, formatString) {
	formatString = formatString || 'YYYY年MM月DD日';
	return moment(value).format(formatString);
});