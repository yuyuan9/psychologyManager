/**
 * Created by oll on 2017/2/22.
 * 
 * <div class="header_top ac">
    <!-<a href="JavaScript:self.location=document.referrer;" class="left fl">-->
    <a href="javascript:history.go(-1)" class="left fl">
        <s class="back fl"></s>
    </a>
    <span>{{title}}</span>

    <a v-cloak v-show="code==0" href="/beetl/app/person_center/index.html" class="fr right"><s class="person_center"></s><s class="msg_count ac">2</s></a>
    <a v-cloak v-show="code==-1" href="/beetl/app/login.html" class="fr right"><s class="person_center"></s><s class="msg_count ac">2</s></a>
</div>
 */
var myuser = Vue.extend({
	template:'<div class="header_top vue_top ac">'
		     +'<a href=\"javascript:history.go(-1)\" class=\"left fl\">'
	         +'<s class=\"back fl\"></s>'
	         +'</a>'
	         +'<span>{{title}}</span>'
	         +'<a v-cloak v-show="code==0" href="/beetl/app/person_center/index.html" class="fr right"><s class="person_center"></s><s class="msg_count ac">{{msgcount}}</s></a>'
	         +'<a v-cloak v-show="code==-1" href="/beetl/app/vo_login.html" class="fr right"><s class="person_center"></s></a>'
		,
    props : ['title',"code","msgcount"]
    
});

Vue.component("user",myuser);

var useobj = new Vue({
    el: '#useobj',
    data: {
    	code: 0,
    	msgcount:0,
        userInfo:"",
    },
    created: function () {
      	 this.dopage();
    },
    methods: {
        dopage: function () {
        	 htajax.get("/beetl/login/getUser.do", function (data) {
                 if (data.code == 0) {
                     useobj.userInfo=data.data;
                 }
                 useobj.code = data.code;
                if(useobj.code==0){
                    useobj.getMsgCount();
                }
             }, function (data) {
            	 
             });
        },
        getMsgCount:function(){
            htajax.get("/beetl/app/getInform.do", function (data) {
                if (data.code == 0) {
                    useobj.msgcount = data.data.list.length;
                }
            }, function (data) {
                //����ص�
            });
        },

    }
});



