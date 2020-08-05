var company = new Vue({
    el: '#app-company',
    data: {
        //tab切换
    	refer:false,
        city:'',
        province: '',
        area: '',
        keyword:'',
        year:'',
        companyList:[],
        total:'',
        page: {
        	current: 1,
        	pages: 0,
        	size: 15
        },
        error: false,
        loading: false,
        finished: false,
        value1: 0,
        value2: 'a',
        option1: [
          { text: '认定年份', value: 0 },
          { text: '2008', value: 2008 },
          { text: '2009', value: 2009 },
          { text: '2010', value: 2010 },
          { text: '2011', value: 2011 },
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
        ],
        show: false,
        city: '地区选择'
    },
    created: function () {
    	this.initPage();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
    	   this.getKeyWord();
           
            //this.changeCode("regcodeimg");
        },
        queren: function (data) {
            this.province = data[0] != undefined && data[0].name ? data[0].name : '';
            this.city = data[1] != undefined && data[1].name ? data[1].name : '';
            this.area = data[2] != undefined && data[2].name ? data[2].name : '';
            company.page.current=1;
            company.page.size=15;
            if(company.companyList) {
                company.companyList = [];
            }
    		company.projectData();
    		this.show = false;
         },
         btn: function () {
             this.show = false;
         },
         showPopup() {
             this.show = true;
         },
        //获取链接的参数
        getUrlData:function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        //从链接获取keyword
        getKeyWord:function(){ // 注释部分为查询的定位传参
            var keyword = this.getUrlData("keyword");
            // var province= this.getUrlData("province");
            // var city= this.getUrlData("city");
            // var area= this.getUrlData("area");
            this.keyword = keyword;
            // this.province=province;
            // this.city=city;
            // this.projectData(1);
        },
        yearchange:function(value){
            this.year=value+"";
        	if(this.year!='0'){
        		company.page.current=1;
                company.page.size=15;
                if(company.companyList) {
                    company.companyList = [];
                }
                this.projectData();
        	}
        	
        },
        scrollSearch:function(index){
        	company.page.current=index+1;
        	company.page.size=15;
        	this.projectData();
        },
        projectData:function(status){
            if(status == 1) {
                this.page.current = 1;
                company.companyList = [];
                // company.finished = false;
            }
        	//政策接口调用
        	 htajax.postFormDone("/ht-biz/app/highcompany/list", {
        		 province:this.province,
                 city: this.city,
                 area: this.area,
                 keyword:this.keyword,
                 year:this.year,
                 current:this.page.current,
                 size:this.page.size,
             },
             function(data){
                 layer.load(1, {
                     shade: [0.5,'#fff'] //0.1透明度的白色背景
                 });
             },
             function (data) {
                 if (data.code == 10000) {
                     company.total=data.data.total;
                     company.page.current = company.page.current + 1;
                     company.companyList.push(...data.data.records);
                     // 加载状态结束
                     company.loading = false;
                     if (data.data.records.length < company.page.size) {
                         company.finished = true;
                     } else {
                        company.finished = false;
                     }
                	 if(data.data.pages==0){
                		 $(".hasno").show();
                	 }else{
                		 $(".hasno").hide();
                	 }
                	 layer.closeAll();
                 }else if(data.code == 10119) {
                    location.href="/ht-biz/app/login/login.html";
                 } else {
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