/**
 * Created by oll on 2019/6/3.
 *
 *
 */

var mypagenation = Vue.extend({
    template: '<div class="block policy_pagenation">'
    + ' <div class="page-bar">'
    + '<ul class="page-bar-ul">'
    + '<li><a :class="setButtonClass(0)" v-on:click="prvePage(page.currentPage)">上一页</a></li>'
    + '<li v-for="index in indexs" v-bind:class="{ active: page.currentPage == index }">'
    + '<a href="javascript:;" v-on:click="btnClick(index)">{{ index < 1 ? "..." : index }}</a>'
    + '<li><a :class="setButtonClass(1)" v-on:click="nextPage(page.currentPage)">下一页</a></li>'
    + '</ul>'
    + '</div>'
    + '</div>',
    props: ['page'],
    computed: {
        indexs: function () {
            var left = 1;//开始页数
            var right = this.page.totalPage;//总页数
            var ar = [];
            if (this.page.totalPage >= 11) {
                if (this.page.currentPage > 5 && this.page.currentPage < this.page.totalPage - 4) {
                    left = this.page.currentPage - 5;
                    right = this.page.currentPage + 4
                } else {
                    if (this.page.currentPage <= 5) {
                        left = 1;
                        right = 10;
                    } else {
                        right = this.page.totalPage;
                        left = this.page.totalPage - 9
                    }
                }
            }
            while (left <= right) {
                ar.push(left);
                left++
            }
            if (ar[0] > 1) {
                ar[0] = 1;
                ar[1] = -1;
            }
            if (ar[ar.length - 1] < this.page.totalPage) {
                ar[ar.length - 1] = this.page.totalPage;
                ar[ar.length - 2] = 0;
            }
            return ar;
        }
    },
    methods: {
        //	分页
        // 页码点击事件
        btnClick: function (data) {
            this.$emit('btn-click',data);
            //window.scrollTo(0,350);  
            $(window).scrollTop(0);
        },
        // 下一页
        nextPage: function (data) {
            if (this.page.currentPage >= this.page.totalPage) return;
            this.btnClick(this.page.currentPage + 1);
        },
        // 上一页
        prvePage: function (data) {
            if (this.page.currentPage <= 1) return;
            this.btnClick(this.page.currentPage - 1);
        },
        // 设置按钮禁用样式
        setButtonClass: function (isNextButton) {
            if (isNextButton) {
                return this.page.currentPage >= this.page.totalPage ? "page-button-disabled" : ""
            }
            else {
                return this.page.currentPage <= 1 ? "page-button-disabled" : ""
            }
        }
    }
});
//window.pagenav = mypagenation;
Vue.component("mypagenation", mypagenation);







