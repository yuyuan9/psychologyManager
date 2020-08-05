var projectcx = new Vue({
    el: '#app_fast_match',
    data: {
        //tab切换
    	index:1,
        city:'',
        applyField:'', // 申报领域
        scaleCode:'', // 企业规模传值
        scaleCode1:'', // 企业规模展示
        province: '',
        area:'',
        addr:'',
        companyName:'',
        //keyword:'',
        projectList:[],
        total:'',
        techfieldOption:[], // 接收申报领域数据
        scaleOption: [], // 接收企业规模数据
        show: false, // 地区开关
        showscale: false, // 企业规模开关
        showField: false, // 申报领域开关
        city: '地区选择',
    },
    created: function () {
    	this.initPage();
    },
    mounted: function () {},
    methods: {
    	initPage:function(){
        this.techfield();
        this.scale();
        },
        // 地区选择确认
        queren: function (data) {
            this.province = data[0] != undefined && data[0].name ? data[0].name : '';
            this.city = data[1] != undefined && data[1].name ? data[1].name : '';
            this.area = data[2] != undefined && data[2].name ? data[2].name : '';
            this.addr = this.province+this.city+this.area;
            this.show = false;
        },
        // 地区选择取消按钮
        btn: function () {
            this.show = false;
        },
        // 地区选择弹窗
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
        // 申报领域数据
        techfield:function(){
        	htajax.get('/ht-biz/catalog/getAppJson?type=techfield&grade=1',function(data){
        		projectcx.techfieldOption=data.data;
        	},function(data){
        		
        	});
        },
        // 申报领域弹窗
        showfield() {
            this.showField = true
        },
        // 申报领域确认按钮
        ConfirmField(r) {
            this.applyField = r.value;
            this.showField = false
        },
        // 申报领域取消按钮
        handleCancel() {
            this.showField = false
        },
        // 企业规模数据
        scale: function() {
          htajax.get('/ht-biz/policymatch/scalelist',function(data){
            projectcx.scaleOption=data.data;
        	},function(data){
        		
        	});
        },
        // 企业规模弹窗
        showScale() {
            this.showscale = true;
        },
        // 企业规模确认按钮
        onCancel() {
            this.showscale = false;
        },
        // 企业规模取消按钮
        onConfirm(row) {
            this.scaleCode = row.value
            this.scaleCode1 = row.text
            this.showscale = false;
        },
        techfieldclick:function(text){
        	
        },
        clickurl:function(url){
        	location.href=url;
        },
        // 验证
        match:function(){
        	if($.trim(this.companyName)==''){
        		layer.msg('请填写企业名称');
        	}else if(!/^[\u4e00-\u9fa5]+$/gi.test(this.companyName)) {
            layer.msg('企业名称请输入中文');
          }else if($.trim(this.addr)==''){
        		layer.msg('请填写注册地址');
        	}else if($.trim(this.applyField)==''){
        		layer.msg('请选择申报领域');
            }
            // else if($.trim(this.scaleCode)==''){
        	// 	layer.msg('请选择企业规模');
        	// }
        	else{
        		location.href="/ht-biz/app/policymatch/fastmatchlist?province="+this.province+"&city="+this.city+"&area="+this.area+"&companyName="+this.companyName+"&applyField="+this.applyField+"&scaleCode="+this.scaleCode;
        	}
        },
        
    	
    },
  
});


Vue.filter('date', function (value, formatString) {
	formatString = formatString || 'YYYY年MM月DD日';
	return moment(value).format(formatString);
});