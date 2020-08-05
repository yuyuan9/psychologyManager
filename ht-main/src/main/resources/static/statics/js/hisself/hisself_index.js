var vc = new Vue({
    el: '#box',
    data: {
        allkeyword: '',
    },
    methods: {
        //全局搜索
        allSearch: function () {
            // console.log(box.allkeyword)
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        turn:function(index){
            //console.log(index);
            if(vm.code == 10000){
                if (index == 1) { 
                    location.href = "/ht-biz/hisself/index";
                }
                if (index == 2) {
                    location.href = "/ht-biz/hisself/index?p=江苏省&c=苏州市";
                 }
                if (index == 3) {
                    location.href = "/ht-biz/hisself/index?p=江苏省&c=淮安市";
                 }
                if (index == 4) { 
                    location.href = "/ht-biz/hisself/index?p=浙江省&c=金华市";
                }
                if (index == 5) { 
                    location.href = "/ht-biz/hisself/index?p=广东省&c=广州市";
                }
            }else{
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