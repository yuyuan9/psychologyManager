//s首页计数插件
$.fn.countTo = function (options) {
    options = options || {};
    return $(this).each(function () {
        // set options for current element
        var settings = $.extend({}, $.fn.countTo.defaults, {
            from: $(this).data('from'),
            to: $(this).data('to'),
            speed: $(this).data('speed'),
            refreshInterval: $(this).data('refresh-interval'),
            decimals: $(this).data('decimals')
        }, options);

        // how many times to update the value, and how much to increment the value on each update
        var loops = Math.ceil(settings.speed / settings.refreshInterval),
            increment = (settings.to - settings.from) / loops;

        // references & variables that will change with each update
        var self = this,
            $self = $(this),
            loopCount = 0,
            value = settings.from,
            data = $self.data('countTo') || {};

        $self.data('countTo', data);

        // if an existing interval can be found, clear it first
        if (data.interval) {
            clearInterval(data.interval);
        }
        data.interval = setInterval(updateTimer, settings.refreshInterval);

        // initialize the element with the starting value
        render(value);

        function updateTimer() {
            value += increment;
            loopCount++;

            render(value);

            if (typeof (settings.onUpdate) == 'function') {
                settings.onUpdate.call(self, value);
            }

            if (loopCount >= loops) {
                // remove the interval
                $self.removeData('countTo');
                clearInterval(data.interval);
                value = settings.to;

                if (typeof (settings.onComplete) == 'function') {
                    settings.onComplete.call(self, value);
                }
            }
        }

        function render(value) {
            var formattedValue = settings.formatter.call(self, value, settings);
            $self.html(formattedValue);
        }
    });
};
$.fn.countTo.defaults = {
    from: 0, // the number the element should start at
    to: 0, // the number the element should end at
    speed: 1000, // how long it should take to count between the target numbers
    refreshInterval: 5, // how often the element should be updated
    decimals: 0, // the number of decimal places to show
    formatter: formatter, // handler for formatting the value before rendering
    onUpdate: null, // callback method for every time the element is updated
    onComplete: null // callback method for when the element finishes updating
};

function formatter(value, settings) {
    return value.toFixed(settings.decimals);
}
// custom formatting example
$('#count-number').data('countToOptions', {
    formatter: function (value, options) {
        return value.toFixed(options.decimals).replace(/\B(?=(?:\d{3})+(?!\d))/g, ',');
    }
});
// start all the timers
$('.timer').each(count);

function count(options) {
    var $this = $(this);
    options = $.extend({}, options || {}, $this.data('countToOptions') || {});
    $this.countTo(options);
}
//================================================
//首页轮播插件
var swiper = new Swiper('.swiper-container', {
    autoplayDisableOnInteraction: false,
    loop: true, //循环
    speed: 5000, //滚动速度
    spaceBetween: 50,
    centeredSlides: true,
    autoplay: {
        delay: 4000,
        disableOnInteraction: false,
    },
    pagination: {
        el: '.swiper-pagination',
        clickable: true,
    },
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },
});

// ===========================================
// jq 动画效果
$(function(){
    $(".icon_box").hide();
    $(".icon_position").mouseover(function(){
        $(".icon_box").show();
        $(".gd").hide();
    })
    $(".icon_position").mouseout(function(){
        $(".icon_box").hide();
        $(".gd").show();
    })

    $(window).scroll(function () {
        var $scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop; //兼容浏览器
        if ($scrollTop > 200) { //滚动高度可调
            $("#goTop").css("visibility", "visible");
        } else {
            $("#goTop").css("visibility", "hidden");
        }
    });

    $("#goTop").on("click", function () {
        $('html , body').stop().animate({scrollTop: 0},'slow');
    })
})

$(".QRCode").mouseover(function(e){
    $(".QR-bg").css({
        'display': 'block',
        'opacity': '1',
    })
    $(".QR-imgbg").css({
        'display':'block'
    })
})
$(".QRCode").mouseout(function(e){
    $(".QR-bg").css({
        'display': 'none',
        'opacity': '0',
    })
    $(".QR-imgbg").css({
        'display':'none'
    })
})

$(".xcx_h").mouseover(function () {
    $(".xcx").css({
        'visibility': 'visible'
    })
})
$(".xcx_h").mouseleave(function () {
    $(".xcx").css({
        'visibility': 'hidden'
    })
})