var projectcx = new Vue({
    el: '#app_policydig',
    data: {
        //tab切换
    	index:1,
        city:'',
        province: '',
        area:'',
        nature: '1',
        keyword: '',
        //keyword:'',
        projectList:[],
        total:'',
        page1: {
        	current: 1,
        	pages: 0,
        	size:10
        },
        error: false,
        loading: false,
        finished: false,
        show: false,
    },
    created: function () {
        this.initPage();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
    	   this.city = this.getUrlData('city');
           this.province = this.getUrlData('province');
           this.area = this.getUrlData('area');
           this.keyword = this.getUrlData('keyword') || '';
           this.index =  this.getUrlData('nature');
           this.nature =  this.getUrlData('nature');
    	   if(this.index=='null' || this.index==null){
    		   this.index=1;
           }
        //    this.autoLocation();
        },
        queren: function (data) {
            this.loading = true
            this.province = data[0] != undefined && data[0].name ? data[0].name : '';
            this.city = data[1] != undefined && data[1].name ? data[1].name : '';
            this.area = data[2] != undefined && data[2].name ? data[2].name : '';
            // if(this.index==1){
            this.page1.current = 1;
            this.projectData(() => {
                projectcx.projectList = [];
            });
            this.recordData()
            this.show = false;
          },
          btn: function () {
            this.show = false;
          },
          showPopup() {
            this.show = true;
          },
        changetab:function(index){
        	this.index = index;
            this.nature = index;
            this.page1.current = 1;
            this.finished = false;
            this.projectData(() => {
                projectcx.projectList = [];
            });
        },
        //获取链接的参数
        getUrlData:function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        projectData:function(callback){
        	//alert(projectcx.page.current);
        	//alert(projectcx.page.size);
            //政策接口调用
        	 htajax.postFormDone("/ht-biz/app/policydig/list", {
                 province:this.province,
                 city: this.city,
                 area: this.area,
                 nature: this.nature,
                 current: this.page1.current,
                 size: this.page1.size,
                 keyword: this.keyword
             },
             function(data){
                //  layer.load(1, {
                //      shade: [0.5,'#fff'] //0.1透明度的白色背景
                //  });
             },
             function (data) {
                 if (data.code == 10000) {
                	 projectcx.total = data.data.total;
                     projectcx.page1.current = projectcx.page1.current + 1;
                     if (callback) {
                        callback();
                     }
                     projectcx.projectList.push(...data.data);

                     // 加载状态结束
                     projectcx.loading = false;
                     if (data.data.length < projectcx.page1.size) {
                         projectcx.finished = true;
                     };
                 }else if (data.code == 10119) {
                    location.href="/ht-biz/app/login/login.html";
                 } else {
                	//  layer.closeAll();
                 }
             },
             function (data) {
            	//  layer.closeAll();
             });
        },
    //     autoLocation:function(){//自动点位地址
    //         htajax.get("/ht-biz/app/index/getIpAddr", 
    //         function (data) {
    //             if(data.code == 10000){
    //                 projectcx.city=data.data.city;
    //                 projectcx.province=data.data.province;
    //                 projectcx.area = data.data.area;
    //                 projectcx.projectData();
    //             }
    //         }, function (data) {
    //             //错误回调
    //             projectcx.city='广东省';
    //             projectcx.province='广州市';
    //             projectcx.projectData();
    //         });
    //    },
       recordData:function(){//记忆功能接口调用
        htajax.getForm("/ht-biz/app/index/record", {
            province:projectcx.province,
            city: projectcx.city,
            area: projectcx.area,
            size:10,
        },
        function (data) {
            if (data.code == 10000) {} else {}
        },
        function (data) {});
        }, 
        todetails(id) {
            location.href="/ht-biz/app/policydig/details?id="+id+"&province="+this.province+"&city="+this.city+"&area="+this.area+"&index="+this.index+"&keyword="+this.keyword+"&nature="+this.nature;
        },
        digSearch() {
            var keyword=projectcx.keyword;
            if($.trim(keyword)==''){
                layer.msg('请输入要查询的内容'); 		
        	}else{
                projectcx.finished = false;
                this.page1.current = 1;
                this.projectData(() => {
                    projectcx.projectList = [];
                });
        	}
        }
    },
  
});


Vue.filter('date', function (value, formatString) {
	formatString = formatString || 'YYYY年MM月DD日';
	return moment(value).format(formatString);
});