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
$(function () {
    var lianjie = window.location.href;
    //console.log(lianjie);
    if (lianjie.indexOf("index") >= 0) {
        $(".page-index").addClass("active");
    } else if (lianjie.indexOf("honey") >= 0) {
        $(".page-honey").addClass("active");
    } else if (lianjie.indexOf("document") >= 0) {
        $(".page-doc").addClass("active");
    } else if (lianjie.indexOf("assess") >= 0) {
        $(".page-assess").addClass("active");
    } else if (lianjie.indexOf("share") >= 0) {
        $(".page-share").addClass("active");
    } else if (lianjie.indexOf("infoUser") >= 0) {
        $(".page-info").addClass("active");
    } else if (lianjie.indexOf("infoCompany") >= 0) {
        $(".page-infoCompany").addClass("active");
    } else if (lianjie.indexOf("infoService") >= 0) {
        $(".page-infoService").addClass("active");
    } else if (lianjie.indexOf("add_card") >= 0) {
        $(".page-myCard").addClass("active");
    } else if (lianjie.indexOf("myorder") >= 0) {
        $(".page-order").addClass("active");
    } else if (lianjie.indexOf("cart") >= 0) {
        $(".page-cart").addClass("active");
    }
})
$('.title_list>li').mouseover(function(e){
    $('.title_list>li').removeClass('active');
    $(this).addClass('active');
    $(this).children().stop().siblings(".drop_down").animate({
        'top':'30px',
        'opacity':'1',
    },200);
});
$('.title_list>li').mouseout(function(e){
    $(this).children().stop().siblings(".drop_down").animate({
        'top':'-100px',
        'opacity':'0',
    },200);
})
