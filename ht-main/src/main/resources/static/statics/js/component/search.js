/**
 * Created by oll on 2017/9/7.
 * 
 * 
 * 
 * 
 */

var search = Vue.extend({
    template:  
    '<div v-show="!isSearch" v-cloak>'
    +'<div class="theme_tab fl clearfix" >'
    +'<h3 class="nav_tit clearfix" @click.stop.prevent="showTab()">'
    +'<p v-cloak v-show="dropDownList==0" class="nav_tit3"></p>'
    +'<p v-cloak v-show="dropDownList==1" class="nav_tit2"></p>'
    +'<p v-cloak v-show="dropDownList==2" class="nav_tit1"></p>'
    +'<p v-cloak v-show="dropDownList==3" class="nav_tit4">中小企查询</p>'
    +'<i></i>'
    +'</h3>'
    +'<ul v-cloak class="select clearfix" >'
    +'<s class="sjx"></s>'
    +'<li>'
    +'<a href="/ht-biz/inquire/company_details?theme=0">'
    +'<h3>高企查询</h3>'
    +'<p><i>免费提供全国36个省市有效期内的高企数据</i></p>'
    +'</a>'
    +'</li>'
    +'<li>'
    +'<a href="/ht-biz/inquire/policy_details?theme=1">'
    +'<h3>政策查询</h3>'
    +'<p><i>项目政策</i>|<i>区域政策</i>|<i>产业政策</i>·<i>全面评估规划</i></p>'
    +'</a>'
    +'</li>'
    +'<li>'
    +'<a href="/ht-biz/inquire/project_details?theme=2">'
    +'<h3>立项查询</h3>'
    +'<p><i>企业立项检索</i>|<i>政策应用规划</i>|<i>同行标杆参考</i></p>'
    +'</a>'
    +'</li>'
    + '<li>'
    + '<a href="/ht-biz/inquire/middle_details?theme=3">'
    + '<h3>中小企查询</h3>'
    + '<p><i>免费提供全国36个省市有效期内的高企数据</i></p>'
    + '</a>'
    + '</li>'
    +'</ul>'
    +'</div>'
    +'<div class="search_ipt fl clearfix">'
    +'<div v-cloak class="search_ipt_top fl clearfix" v-show="dropDownList==0">'
    +'<p class="fl"><i></i></p>'
        +'<input type="text"  class="fl keyWord0" :id="url0" name="keyWord "v-model="url0" @keyup.enter="showMsg2()" placeholder="请输入您想了解的企业名称，如“高企云”">'
    +'<s class="fl" @click="showMsg2()">立即查询</s>'
    +'</div>'
    +'<div v-cloak class="search_ipt_top fl clearfix" v-show="dropDownList==1">'
    +'<p class="fl"><i></i></p>'
    +'<input type="text" class="fl keyWord1" :id="url1" name="keyWord" v-model="url1" @keyup.enter="showMsg()" placeholder="请输入您想了解的政策关键词，如高新技术企业">'
    +'<s class="fl" @click="showMsg(index)">政策查询</s>'
    +'</div>'
    +'<div v-cloak class="search_ipt_top fl clearfix" v-show="dropDownList==2">'
    +'<p class="fl"><i></i></p>'
    +'<input type="text" class="fl keyWord2" :id="url2" name="keyWord" v-model="url2" @keyup.enter="showMsg1()" placeholder="请输入您想了解的项目关键词或企业名称，如高新技术企业或高企云">'
    +'<s class="fl" @click="showMsg1(index)">立项查询</s>'
    +'</div>'
    + '<div v-cloak class="search_ipt_top fl clearfix" v-show="dropDownList==3">'
    + '<p class="fl"><i></i></p>'
    + '<input type="text" class="fl keyWord3" :id="url3" name="keyWord" v-model="url3" @keyup.enter="showMsg3()" placeholder="请输入您想了解的企业名称，如“高企云”">'
    + '<s class="fl" @click="showMsg1(index)">中小企查询</s>'
    + '</div>'
    +'<div class="jumpto fl">'
    // +'<a href="https://www.hights.cn/beetl/sysbigdata/index.html">切换到旧版</a>'
    +'</div>'
    +'</div>'
    +'</div>',
    props: ['isSearch'],
    data: function () {
        return {
            dropDownList:'',
            dropDownShow:false,
            url0:'',
            url1:'',
            url2: '',
            url3: '',
            theme:'',
        }
    },
    created: function () {
        // this.changeCode();
        this.initPage();
    },
    mounted: function () {
        document.getElementById("box").addEventListener("click",function(){
            // this.dropDownShow=false;
            $(".select").hide();
        },true);
       
    },
    methods: {
        initPage:function(){
            this.getUrl();
            this.getTheme();
        },
        showTab:function(){
            $(".select").show();
        },
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)')
                .exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        getUrl:function(){
            var urlId1= this.getUrlData("keyword1");
            var urlId2 = this.getUrlData("keyword2");
            var urlId0 = this.getUrlData("keyword0");
            var urlId3 = this.getUrlData("keyword3");
            var theme = this.getUrlData("theme");
            this.url1 = urlId1;
            this.url2 = urlId2; 
            this.url0 = urlId0;
            this.url3 = urlId3;
            if(theme == 0){
                this.dropDownList = 0;
            }
            if(theme == 1){
                this.dropDownList = 1;
            }
            if(theme == 2){
                this.dropDownList = 2;
            }
            if (theme == 3) {
                this.dropDownList = 3;
            }
        },
        getTheme:function(){
            var theme= this.getUrlData("theme");
            if(theme == 0){
                this.theme = 0;
            }
            if(theme == 1){
                this.theme = 1;
            }
            if (theme == 2) {
                this.theme = 2;
            }
            if (theme == 3) {
                this.theme = 3;
            }
        },
        
        showMsg: function (data) {
            //this.$emit('getContent',{type:1,index:1});
            this.$emit('btn-click',this.url1);
        },
        showMsg1: function (data) {
            //this.$emit('getContent',{type:1,index:1});
            this.$emit('btn-click',this.url2);
        },
        showMsg2: function (data) {
            this.$emit('btn-click', this.url0);
        },
        showMsg3: function (data) {
            this.$emit('btn-click', this.url3);
        },
        getqizheng:function(){
            alert("敬请期待");
        },
        
        getpolicy:function(){
            window.open("/ht-biz/inquire/project_details?keyword2="+this.url2+"&theme="+this.theme);
        },
    }
});
Vue.component("search", search);
