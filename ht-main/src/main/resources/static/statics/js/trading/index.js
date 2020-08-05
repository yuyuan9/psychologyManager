/**
 * Created by ACER on 2019/12/10.
 */

var box = new Vue({
    el: '#box',
    data:function() {
        return {
            allkeyword: '',
            scienceList: [],
            granularList: [],
            IPRList: [],
            otherList:[],
            granularListId: '',
            IPRListId: '',
            otherListId: '',
            searchWords:'',
            scienceListThree:[],
            granularListThree:[],
            IPRListThree:[],
            bannerList1:[],
            datamun:'',
            prop:[],
        }
    },
    created :function() {
        this.initPage();
    },
    methods: {
        initPage: function () {
            this.getTradingTree();
            this.getserbanner();
        },
        //全局搜索
        allSearch: function () {
            location.href = "/ht-biz/search/index/totalSearch?keyword=" + this.allkeyword;
        },
        getserbanner: function () {
            htajax.postForm('/ht-biz/banner/getbannerbytype', {
                type: 3,//服务商推荐
            },
            function (data) {
                if (data.code == 10000) {
                    box.bannerList1 = data.data;
                    box.datamun = data.data.length;
                }
            }, function (data) {
                //错误回调
                // console.log(data.msg)
            });
        },
        //搜索
        searchPro:function(){
            window.open("/currency/trading/transaction_list?keyword=" + this.searchWords); 
        },
        //获取树状数据
        getTradingTree:function(){
            htajax.postForm("/ht-biz/product/treelist?t=" + new Date().getTime(), {}, function (data) {
                if (data.code == 10000) {
                    // console.log(data);
                    box.scienceList = data.data[0];
                    box.scienceListId = data.data[0].id;
                    box.granularList = data.data[1];
                    box.granularListId = data.data[1].id;
                    box.IPRList = data.data[2];
                    box.IPRListId = data.data[2].id;
                    for(var i = 3;i<data.data.length;i++){
                        box.otherList.push(data.data[i]);
                    }
                    box.getThreeInfo1();
                    box.getThreeInfo2();
                    box.getThreeInfo3();

                }
            
            }, function (data) {
                //layer.msg(data.msg);
            })
            
        },
        //获取三级数据
        getThreeInfo1:function(){
            var id = this.scienceListId;
            htajax.postForm("/ht-biz/product/getprotybyid?t=" + new Date().getTime(), {
                id:id,
            }, function (data) {
                if (data.code == 10000) {
                    box.scienceListThree = data.data;
                }

            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        getThreeInfo2: function () {
            var id = this.granularListId;
            htajax.postForm("/ht-biz/product/getprotybyid?t=" + new Date().getTime(), {
                id: id,
            }, function (data) {
                if (data.code == 10000) {
                    box.granularListThree = data.data;
                }

            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        getThreeInfo3: function () {
            var id = this.IPRListId;
            htajax.postForm("/ht-biz/product/getprotybyid?t=" + new Date().getTime(), {
                id: id,
            }, function (data) {
                if (data.code == 10000) {
                    box.IPRListThree = data.data;
                }

            }, function (data) {
                //layer.msg(data.msg);
            })
        },
        submitBtn: function (index) {
            layer.closeAll();
            var t = setTimeout("layer.msg('谢谢您的反馈!')", 500);
        },
        handletips: function () {
            layer.msg("功能正在开发中，请耐心等待...");
        },
        tickling: function () {
            layer.open({
                type: 1,
                area: ['600px', '400px'], //宽高
                content: $('#ids1') //这里content是一个DOM，这个元素要放在body根节点下
            });
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})