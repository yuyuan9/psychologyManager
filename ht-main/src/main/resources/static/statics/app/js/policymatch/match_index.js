var projectcx = new Vue({
    el: '#app_match',
    data: {
        //tab切换
    	index:1,
        city:'',
        province: '',
        area:'',
        companyname:'',
        addr:'',
        //keyword:'',
        projectList:[],
        total:'',
        value1: 0,
        show: false,
        city: '地区选择'
    },
    created: function () {
    	this.initPage();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
    	   this.index=this.getUrlData('index');
    	   if(this.index=='null' || this.index==null){
    		   this.index=1;
    	   }
    	  
        },
        queren: function (data) {
            this.province = data[0] != undefined && data[0].name ? data[0].name : '';
            this.city = data[1] != undefined && data[1].name ? data[1].name : '';
            this.area = data[2] != undefined && data[2].name ? data[2].name : '';
            this.addr=this.province+this.city+this.area;
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
        tabclick:function(index){
        	this.index=index;
        },
        clickurl:function(url){
        	location.href=url;
        },
        fastmathLogin:function(url){//精准匹配需要登录
        	
        	
        },
        match:function(){
        	if($.trim(this.companyname)==''){
        		layer.msg('请填写企业名称');
        	} else if(!/^[\u4e00-\u9fa5]+$/gi.test(this.companyname)) {
            layer.msg('企业名称请输入中文');
          }else if($.trim(this.addr)==''){
        		layer.msg('请填写注册地址');
        	}else{
        		location.href="/ht-biz/app/policymatch/matchlist?province="+this.province+"&city="+this.city+"&area="+this.area+"&companyName="+this.companyname;
        	}
        },
        
       
        
    	
    },
  
});


Vue.filter('date', function (value, formatString) {
	formatString = formatString || 'YYYY年MM月DD日';
	return moment(value).format(formatString);
});