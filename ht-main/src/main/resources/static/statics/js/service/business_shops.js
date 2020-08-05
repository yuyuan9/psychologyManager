/**
 * Created by ACER on 2019/11/27.
 */

var box = new Vue({
    el: '#box',
    data() {
        return {
            page: {
                currentPage: 1,
                totalPage: 1,
                totalResult: 0
            },
            allkeyword:'',
        }
    },
    created() {
        this.initPage();
    },
    methods: {
        initPage: function () {
        },
        //分页
        clickPage: function (index) {
            if (index != 0) {
                

            }
        },
        //公共头部调用
        getDeclare: function () { },
        getPublicity: function () { },
        getPolicy: function () { },
        getBanner: function () { },
    }
})