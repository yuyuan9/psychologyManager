var apppolicycx = new Vue({
    el: '#app-policycx',
    data: {
        //tab切换
    	refer:false,
        province: '',
        city:'',
        area:'',
        year:'',
        keyword:'',
        policyList:[],
        total:'',
        
        page: {
        	current: 1,
        	pages: 0,
        	size: 15
        },
        value1: 0,
        value2: 'a',
        option1: [
          { text: '政策年份', value: 0 },
          { text: '2008', value: 2008 },
          { text: '2009', value: 2009 },
          { text: '2010', value: 2010 },
          { text: '2012', value: 2012 },
          { text: '2013', value: 2013 },
          { text: '2014', value: 2014 },
          { text: '2015', value: 2015 },
          { text: '2016', value: 2016 },
          { text: '2017', value: 2017 },
          { text: '2018', value: 2018 },
          { text: '2019', value: 2019 },
          { text: '2020', value: 2020 },
          { text: '2021', value: 2021 },
          { text: '2022', value: 2022 }
        ],
        show: false,
        city: '地区选择',
        error: false,
        loading: false,
        finished: false,
    },
    created: function () {
    	this.initPage();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
    	   this.getKeyWord();
        },
        queren: function (data) {
          this.province = data[0] != undefined && data[0].name ? data[0].name : '';
          this.city = data[1] != undefined && data[1].name ? data[1].name : '';
          this.area = data[2] != undefined && data[2].name ? data[2].name : '';
        	apppolicycx.page.size=15;
          apppolicycx.page.current=1;
          if(apppolicycx.policyList) {
            apppolicycx.policyList = [];
        }
        apppolicycx.policyData();
          this.show = false;
    		  // apppolicycx.policyData(1);
            
        },
        btn: function () {
              this.show = false;
        },
        showPopup() {
              this.show = true;
        },
        yearchange:function(value){
        	this.year=value+"";
        	if(this.year!='0'){
        		apppolicycx.page.current=1;
            apppolicycx.page.size=15;
        		apppolicycx.policyData(1);
        	}
        },
        //获取链接的参数
        getUrlData:function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        //从链接获取keyword
        getKeyWord:function(){
          var keyword=this.getUrlData('keyword');
          var provincedes=this.getUrlData('provincedes');
          var citydes=this.getUrlData('citydes');
          var areades=this.getUrlData('areades');
          var year=this.getUrlData('year');
          this.keyword = keyword;
          this.province = provincedes;
          this.city = citydes || '地区选择';
          this.area = areades;
          this.value1 = year - 0;
          this.year = year;
        },
        policyData:function(index){
          if(index == 1) {
            this.page.current = 1;
            apppolicycx.policyList = [];
            // apppolicycx.finished = false;
        }
        	//政策接口调用
        	 htajax.postFormDone("/ht-biz/app/policylib/list", {
        		     province:this.province,
                 city: this.city,
                 area:this.area,
                 year: this.year,
                 keyword:this.keyword,
                 current:this.page.current,
                 size:this.page.size,
             },
             function(data){
                //  layer.load(1, {
                //      shade: [0.5,'#fff'] //0.1透明度的白色背景
                //  });
             },
             function (data) {
                 if (data.code == 10000) {
                	 
                	 apppolicycx.total=data.data.total;

                   apppolicycx.page.current =apppolicycx.page.current + 1;
                   apppolicycx.policyList.push(...data.data.records);
                   // 加载状态结束
                   apppolicycx.loading = false;
                   if (data.data.records.length < apppolicycx.page.size) {
                       apppolicycx.finished = true;
                   } else {
                      apppolicycx.finished = false;
                   }
                	//  if(apppolicycx.page.current==data.data.pages){
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
        // 跳转至详情页
        policylibDetails(id) {
          location.href="/ht-biz/app/policylib/details?id="+id+"&provincedes="+this.province+"&citydes="+this.city+"&areades="+this.area+"&year="+this.year+"&keyword="+this.keyword;
        }
    },
  
});

Vue.filter('date', function (value, formatString) {
	formatString = formatString || 'YYYY年MM月DD日';
	return moment(value).format(formatString);
});