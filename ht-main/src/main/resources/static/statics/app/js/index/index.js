var appindex = new Vue({
    el: '#app-index',
    data: {
        //tab切换
    	refer:1,
        city:'',
        province: '',
        area: '',
        codeImg: '',
        policyDigList:[],
        policySearchKey:'',
        projectSearchKey:'',
        companySearchKey:'',
        areaList: {},
        show:false,
    },
    created: function () {
        this.initPage();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
    	   //if(this.refer )
    	   this.refer=this.getUrlData('refer');
    	   if(this.refer==null || this.refer=='null'){
    		   this.refer=1;
    	   }
           this.autoLocation();
            //this.changeCode("regcodeimg");
        },
        tabclick:function(index){
        	this.refer=index;
        },
        clickurl:function(url){
        	location.href=url;
        },
        //获取链接的参数
        getUrlData:function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        autoLocation:function(){//自动点位地址
        	 htajax.get("/ht-biz/app/index/getIpAddr", 
             function (data) {
                 if(data.code == 10000){
                	 appindex.city=data.data.city;
                	 appindex.province=data.data.province;
                	 appindex.area = data.data.area;
                     appindex.policydigData();
                 }
             }, function (data) {
                 //错误回调
            	 appindex.city='广东省';
            	 appindex.province='广州市';
                 appindex.policydigData();
             });
        },
        policydigclick:function(){
        	location.href="/ht-biz/app/policydig/index?city="+this.city+"&province="+this.province;
        },
        policydigData:function(){//政策速递接口调用
        	 htajax.getForm("/ht-biz/app/policydig/newlist", {
        		 province:appindex.province,
                 city: appindex.city,
                 area: appindex.area,
                 size:10,
             },
            //  function(data){
            //      layer.load(1, {
            //          shade: [0.5,'#fff'] //0.1透明度的白色背景
            //      });
            //  },
             function (data) {
                 if (data.code == 10000) {
                	 appindex.policyDigList=data.data.records
                	 layer.closeAll();
                 } else {
                	 layer.closeAll();
                 }
             },
             function (data) {
            	 layer.closeAll();
             });
     
        }, 
        recordData:function(){//记忆功能接口调用
            htajax.getForm("/ht-biz/app/index/record", {
                province:appindex.province,
                city: appindex.city,
                area: appindex.area,
                size:10,
            },
            function (data) {
                if (data.code == 10000) {} else {}
            },
            function (data) {});
        }, 
        policySearch:function(){//政策接口调用
        	var keyword=appindex.policySearchKey;
        	var province=appindex.province;
        	var city=appindex.city;
        	if($.trim(keyword)==''){
                layer.msg('请输入要查询的内容');        		
        	}else{
        		location.href="/ht-biz/app/policylib/index?keyword="+keyword+"&province="+province+"&city="+city;
        	}
        },
        projectSearch:function(){//政策接口调用
        	var keyword=appindex.projectSearchKey;
        	var province=appindex.province;
        	var city=appindex.city;
        	if($.trim(keyword)==''){
                layer.msg('请输入要查询的内容');        		
        	}else{
        		location.href="/ht-biz/app/projectlib/index?keyword="+keyword+"&province="+province+"&city="+city;
        	}
        },
        companySearch:function(){//政策接口调用
        	var keyword=appindex.companySearchKey;
        	var province=appindex.province;
        	var city=appindex.city;
        	if($.trim(keyword)==''){
                layer.msg('请输入要查询的内容');        		
        	}else{
        		location.href="/ht-biz/app/highcompany/index?keyword="+keyword+"&province="+province+"&city="+city;
        	}
        },
        to_policydig() {
            location.href="/ht-biz/app/policydig/index?province="+appindex.province+"&city="+appindex.city+"&area="+appindex.area+"&nature="+1;
        },
        queren: function (data) {
            this.province = data[0] != undefined && data[0].name ? data[0].name : '';
            this.city = data[1] != undefined && data[1].name ? data[1].name : '';
            this.area = data[2] != undefined && data[2].name ? data[2].name : '';
            appindex.policydigData();
            appindex.recordData();
    		this.show = false;
         },
         btn: function () {
             this.show = false;
         },
         showPopup() {
             this.show = true;
         },
    	
    },
  
});

Vue.filter('date', function (value, formatString) {
	formatString = formatString || 'YYYY年MM月DD日';
	return moment(value).format(formatString);
});