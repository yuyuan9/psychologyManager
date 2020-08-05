var vc = new Vue({
    el:'#vc',
    data: {
        theme:'',
        keyword0: '',
        keyword1:'',
        keyword2:'',
        keyword3:'',
        allkeyword: '',
    },
    created:function() {
        this.initPage();
        // console.log(this.getUrlData("theme"));
    },
    methods: {
        initPage:function(){
            this.getTheme();
        },
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        //获取链接的参数
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
        getTheme:function(){
            var theme= this.getUrlData("theme");
            //console.log(theme);
            if (theme == 0) {
                this.theme = 0;
            }
            if(theme == 1){
                this.theme = 1;
            }
            if(theme == 2){
                this.theme = 2;
            }
            if (theme == 3) {
                this.theme = 3;
            }
        },

        getWords1: function (index) {
            if (vm.code == 10000) {
                //工程技术中心
                if (index == 1) { window.open("/ht-biz/inquire/policy_details?keyword1=工程技术中心&theme=1"); }
                //高新技术产品
                if (index == 2) { window.open("/ht-biz/inquire/policy_details?keyword1=高新技术产品&theme=1"); }
                //科技型中小企业
                if (index == 3) { window.open("/ht-biz/inquire/policy_details?keyword1=科技型中小企业&theme=1"); }
                //创新型企业
                if (index == 4) { window.open("/ht-biz/inquire/policy_details?keyword1=创新型企业&theme=1"); }
                //产业发展专项
                if (index == 5) { window.open("/ht-biz/inquire/policy_details?keyword1=产业发展专项&theme=1"); }
            } else {
                $(".loginBox").show();
            }

        },

        getWords2: function (index) {
            if (vm.code == 10000) {
                //工程技术中心
                if (index == 1) { window.open("/ht-biz/inquire/project_details?keyword2=工程技术中心&theme=2"); }
                //高新技术产品
                if (index == 2) { window.open("/ht-biz/inquire/project_details?keyword2=高新技术产品&theme=2"); }
                //科技型中小企业
                if (index == 3) { window.open("/ht-biz/inquire/project_details?keyword2=科技型中小企业&theme=2"); }
                //创新型企业
                if (index == 4) { window.open("/ht-biz/inquire/project_details?keyword2=创新型企业&theme=2"); }
                //产业发展专项
                if (index == 5) { window.open("/ht-biz/inquire/project_details?keyword2=产业发展专项&theme=2"); }
            } else {
                $(".loginBox").show();
            }

        },

        getContent: function (index) {
            if (vm.code == 10000) {
                //全国高新技术企业查询系统
                if (index == 0) { window.open("/ht-biz/inquire/company_details?keyword0=" + this.keyword0 + "&theme=" + 0); }
                //立项查
                if (index == 1) { window.open("/ht-biz/inquire/project_details?keyword2=" + this.keyword2 + "&theme=" + 2); }
                //政策查
                if (index == 2) { window.open("/ht-biz/inquire/policy_details?keyword1=" + this.keyword1 + "&theme=" + 1); }
                //中小企业查询
                if (index == 3) { window.open("/ht-biz/inquire/middle_details?keyword3=" + this.keyword3 + "&theme=" + 3); }
            } else {
                $(".loginBox").show();
            }

        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
        
    },

})