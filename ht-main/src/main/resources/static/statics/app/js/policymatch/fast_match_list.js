var projectcx = new Vue({
    el: '#app_match_list',
    data: {
        //tab切换
    	 value1: 0,
        scaleOption: [],
        show: false,
    	total:0,
    	province: '',
        city:'',
        area:'',
        companyName:'',
        applyField:'',
        scaleCode:'',
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
        this.scale();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
    	   this.province=this.getUrlData('province');
    	   this.city=this.getUrlData('city');
    	   this.area=this.getUrlData('area');
    	   this.companyName=this.getUrlData('companyName');
    	   this.applyField=this.getUrlData('applyField');
    	   this.scaleCode=this.getUrlData('scaleCode');
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
        	//政策接口调用
        	 htajax.postFormDone("/ht-biz/app/policymatch/amatchlist", {
        		 province:this.province,
                 city: this.city,
                 area:this.area,
                 companyName:this.companyName,
                 applyField:this.applyField,
                 scaleCode:this.scaleCode,
                 matchType:2,
                 current: this.current,
                 size: this.size
             },
             function(data){
                 layer.load(1, {
                     shade: [0.5,'#fff'] //0.1透明度的白色背景
                 });
             },
             function (data) {
                 if (data.code == 10000) {
                	 projectcx.total=data.data.total;
                	//  projectcx.matchList=data.data.records;
            		 //projectcx.page1.current=projectcx.page1.current+1;
            		 //projectcx.page1.size=projectcx.page1.current*10;
                     //projectcx.page1.pages=data.data.pages;
                     projectcx.current = projectcx.current + 1;
                     projectcx.matchList.push(...data.data.records);
                     // 加载状态结束
                     projectcx.loading = false;
                     if (data.data.records.length < projectcx.size) {
                         projectcx.finished = true;
                     }
                	 if(data.data.pages){
                		 $("#noresult").show();
                	 }
                	 layer.closeAll();
                 } else {
                	 
                	 layer.closeAll();
                 }
             },
             function (data) {
            	 layer.closeAll();
             });
     
        },
        queren: function (data) {
            this.city = data[2].name;
            this.show = false;
      
        },
        btn: function () {
            this.show = false;
        },
        showPopup() {
            this.show = true;
        },
        scale: function() {
            htajax.get('/ht-biz/policymatch/scalelist',function(data){
              projectcx.scaleOption=data.data;
            },function(data){
                
            });
          },
          matchchange(value) {
              this.scaleCode = value
              if(this.scaleCode != '0') {
                this.current = 1
                this.size = 15
                this.matchData()
              }
          }
       
        
    	
    },
  
});


Vue.filter('date', function (value, formatString) {
	formatString = formatString || 'YYYY年MM月DD日';
	return moment(value).format(formatString);
});