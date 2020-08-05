/**
 * Created by oll on 2017/6/29.
 */
var box = new Vue({
    el: '#box',
    data: {
    	returnurl: '',  //点击注册获取该页面url
        pdfData:"",
        pdfurl:"",
        id:"",
        loginInfo: {
            code: -1
        },
    },
    methods: {
        dopage: function () {
        	
            
        },
        
        getUrlData: function getUrlParam(name) {
            var match = RegExp('[?&]' + name + '=([^&]*)')
                .exec(window.location.href);
            return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
        },
    




    },
    mounted: function () {
        this.dopage();
    }
});

Vue.filter('date', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
    return moment(value).format(formatString);
});