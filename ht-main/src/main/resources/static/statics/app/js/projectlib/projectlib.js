var projectcx = new Vue({
    el: '#app-projectcx',
    data: {
        //tab切换
    	refer:false,
        city:'',
        province: '',
        keyword:'',
        projectList:[],
        total: 0,
        year: '',
        page: {
            current: 1,
        	pages: 0,
        	size: 15,
        },
        error: false,
        loading: false,
        finished: false,
        show: false,
        city: '地区选择',
        value1: 0,
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
            this.loading = true
            this.province = data[0] != undefined && data[0].name ? data[0].name : '';
            this.city = data[1] != undefined && data[1].name ? data[1].name : '';
            this.area = data[2] != undefined && data[2].name ? data[2].name : '';
            projectcx.page.current=1;
            projectcx.page.size=15;
            if(projectcx.projectList) {
                projectcx.projectList = [];
            }
    		projectcx.projectData();
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
        getKeyWord:function(){ // 首页定位传的参数
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
            this.year = year;;
        },
        yearchange:function(value){
            this.loading = true
        	this.year=value+"";
        	if(this.year!='0'){
        		projectcx.page.current=1;
                projectcx.page.size=15;
                if(projectcx.projectList) {

                    projectcx.projectList = [];
                }
        		projectcx.projectData();
        	}
        	
        },
        projectData:function(status){
        	//alert(projectcx.page.current);
        	//alert(projectcx.page.size);
            //政策接口调用
            if(status == 1) {
                this.page.current = 1;
                projectcx.projectList = [];
                // projectcx.finished = false;
            }
        	 htajax.postFormDone("/ht-biz/app/projectlib/list", {
        		 province: this.province,
                 city: this.city,
                 area: this.area,
                 keyword: this.keyword,
                 current: this.page.current,
                 size: this.page.size,
                 year:this.year,
             },
             function(data){
                //  layer.load(1, {
                //      shade: [0.5,'#fff'] //0.1透明度的白色背景
                //  });
             },
             function (data) {
                 if (data.code == 10000) {
                    projectcx.total = data.data.total;
                    projectcx.page.current = projectcx.page.current + 1;
                    projectcx.projectList.push(...data.data.records);
                    // 加载状态结束
                    projectcx.loading = false;
                    if (data.data.records.length < projectcx.page.size) {
                        projectcx.finished = true;
                    }else {
                         projectcx.finished = false;
                    }
            		//  projectcx.page.size=projectcx.page.current*10;
            		//  projectcx.page.pages=data.data.pages;
                	//  if(data.data.total==0 || projectcx.page.current==data.data.pages){
                	// 	 $("#noresult").show();
                	//  }
                	//  console.log(data.data.records);
                	//  layer.closeAll();
                 }else if(data.code == 10119) {
                    location.href="/ht-biz/app/login/login.html";
                  } else {
                	//  layer.closeAll();
                 }
             },
             function (data) {
            	//  layer.closeAll();
             });
     
        },
        projectbDetails(id) {
            location.href="/ht-biz/app/projectlib/details?id="+id+"&provincedes="+this.province+"&citydes="+this.city+"&areades="+this.area+"&year="+this.year+"&keyword="+this.keyword;
        }
    },
  
});

Vue.filter('date', function (value, formatString) {
	formatString = formatString || 'YYYY年MM月DD日';
	return moment(value).format(formatString);
});