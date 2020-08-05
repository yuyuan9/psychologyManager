var projectcx = new Vue({
    el: '#app_match_list',
    data: {
        //tab切换
    	total:0,
    	province: '',
        city:'',
        area:'',
        companyName:'',
        //keyword:'',
        matchList:[],
        error: false,
        loading: false,
        finished: false,
        current: 1,
        size: 15
    },
    created: function () {
    	this.initPage();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
    	   this.province=this.getUrlData('province');
    	   this.city=this.getUrlData('city');
    	   this.area=this.getUrlData('area');
    	   this.companyName=this.getUrlData('companyName');
    	//    this.matchData();
        },
        //获取链接的参数
        getUrlData:function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        tabclick:function(index){
        	this.index=index;
        },
        clickurl:function(url){
        	location.href=url;
        },
        matchData:function(){
        	//alert(projectcx.page.current);
        	//alert(projectcx.page.size);
        	//政策接口调用
        	 htajax.postFormDone("/ht-biz/app/policymatch/list", {
        		 province:this.province,
                 city: this.city,
                 area:this.area,
                 companyName:this.companyName,
                 matchType:1,
                 current: this.current,
                 size: this.size
             },
             function(data){
                //  layer.load(1, {
                //      shade: [0.5,'#fff'] //0.1透明度的白色背景
                //  });
             },
             function (data) {
                 if (data.code == 10000) {
                	 projectcx.total=data.data.total;
                    //  projectcx.matchList=data.data.records;

                     projectcx.current = projectcx.current + 1;
                     projectcx.matchList.push(...data.data.records);
                     // 加载状态结束
                     projectcx.loading = false;
                     if (data.data.records.length < projectcx.size) {
                         projectcx.finished = true;
                     }
            		 //projectcx.page1.pages=data.data.pages;
                	//  if(data.data.pages){
                	// 	 $("#noresult").show();
                	//  }
                	//  layer.closeAll();
                 } else if(data.code == 10119) {
                    location.href="/ht-biz/app/login/login.html";
                 } else {
                	 
                	//  layer.closeAll();
                 }
             },
             function (data) {
            	//  layer.closeAll();
             });
     
        },
        
       
        
    	
    },
  
});


Vue.filter('date', function (value, formatString) {
	formatString = formatString || 'YYYY年MM月DD日';
	return moment(value).format(formatString);
});