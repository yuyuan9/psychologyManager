$(".look").click(function(){
    $(this).siblings(".info").fadeIn(200);
})
$(".close").click(function(){
    $(this).parents(".info").fadeOut(200);
})

$(".xq").click(function(){
    $(".line_icon1").hide();
    $(".line_icon2").show();
    $(".line_icon3").hide();
    $(".item1").hide();
    $(".item2").show();
    $(".item3").hide();
    $(".img_head").attr('src','/statics/other/img/innovate_c.png');
    $(".sc").css({
        'background-image':'url(/statics/other/img/point.png)',
        'background-position':'center',
        'background-repeat':'no-repeat',
        'color':'#0246d2',
    })
    $(".zc").css({
        'background-image':'url(/statics/other/img/point.png)',
        'background-position':'center',
        'background-repeat':'no-repeat',
        'color':'#0246d2',
    })
})
$(".zc").click(function(){
    $(".line_icon1").hide();
    $(".line_icon2").hide();
    $(".line_icon3").show();
    $(".item1").hide();
    $(".item2").hide();
    $(".item3").show();
    $(".img_head").attr('src','/statics/other/img/innovate.png');
    $(".sc").css({
        'background-image':'url(/statics/other/img/point.png)',
        'background-position':'center',
        'background-repeat':'no-repeat',
        'color':'#0246d2',
    })
    $(".zc").css({
        'background-image':'url(/statics/other/img/point_c.png)',
        'background-position':'center',
        'background-repeat':'no-repeat',
        'color':'#fff',
    })
})
$(".sc").click(function(){
    $(".line_icon1").show();
    $(".line_icon2").hide();
    $(".line_icon3").hide();
    $(".item1").show();
    $(".item2").hide();
    $(".item3").hide();
    $(".img_head").attr('src','/statics/other/img/innovate.png');
    $(".sc").css({
        'background-image':'url(/statics/other/img/point_c.png)',
        'background-position':'center',
        'background-repeat':'no-repeat',
        'color':'#fff',
    })
    $(".zc").css({
        'background-image':'url(/statics/other/img/point.png)',
        'background-position':'center',
        'background-repeat':'no-repeat',
        'color':'#0246d2',
    })
})



window.onload = function () {
    document.getElementById("father").addEventListener("click",function(){
        $(".info").fadeOut(200);
    },true);
}

$(".item2_head_btn1").click(function(){
    if ($(".details1").css("visibility") == "visible") {
          $(".details1").css({
            'visibility':'hidden',
        })
    }else{
        $(".details1").css({
            'visibility':'visible',
        })
    }
    
})
$(".item2_head_btn2").click(function(){
    if ($(".details2").css("visibility") == "visible") {
          $(".details2").css({
            'visibility':'hidden',
        })
    }else{
        $(".details2").css({
            'visibility':'visible',
        })
    }
})
$(".item2_head_btn3").click(function(){
    if ($(".details3").css("visibility") == "visible") {
          $(".details3").css({
            'visibility':'hidden',
        })
    }else{
        $(".details3").css({
            'visibility':'visible',
        })
    }
})




$(".captain1>.kk ").click(function(){
    if($(".options").css("visibility") == "visible"){
        $(this).siblings(".options").css({
            'visibility':'hidden'
        })
        $(this).parent(".captain1").css({'background-image':'none'})
    }else{
        $(this).siblings(".options").css({
            'visibility':'visible'
        })
        $(this).parent(".captain1").css({'background-image':'url(/statics/other/img/zhixian.png)','background-position':'center','background-repeat':'repeat-y'})
    }
})

$(".captain2>.kk").click(function(){
    if($(this).siblings(".options_btn").css("visibility") == "hidden"){
        $(this).siblings(".options_btn").css({
            'visibility':'visible'
        })
        $(this).parent(".captain2").css({'background-image':'url(/statics/other/img/zhixian.png)','background-position':'center','background-repeat':'repeat-y'})
    }else{
        $(this).siblings(".options_btn").css({
            'visibility':'hidden'
        })
        $(this).parent(".captain2").css({'background-image':'none'})
    }
    

})

$(".captain3>.kk").click(function(){
    if($(this).siblings(".options").css("visibility") == "hidden"){
        $(this).siblings(".options").css({
        'visibility':'visible'
    })
    $(this).parent(".captain3").css({'background-image':'url(/statics/other/img/zhixian.png)','background-position':'center','background-repeat':'repeat-y'})
    }else{
        $(this).siblings(".options").css({
        'visibility':'hidden'
    })
    $(this).parent(".captain3").css({'background-image':'none'})
    }
})

$(".captain4>.kk ").click(function(){
    if($(this).siblings(".options").css("visibility") == "hidden"){
        $(this).siblings(".options").css({
        'visibility':'visible'
        })
        $(this).parent(".captain4").css({'background-image':'url(/statics/other/img/zhixian.png)','background-position':'center','background-repeat':'repeat-y'})
    }else{
        $(this).siblings(".options").css({
            'visibility':'hidden'
        })
        $(this).parent(".captain4").css({'background-image':'none'})    
    }
    
})


$(".aff1").click(function(){
    $(".pop-up2").css({"display":"block"})
})
$(".aff2").click(function(){
    $(".pop-up3").css({"display":"block"})
})
$(".aff3").click(function(){
    $(".pop-up14").css({"display":"block"})
})
$(".aff4").click(function(){
    $(".pop-up15").css({"display":"block"})
})
$(".aff5").click(function(){
    $(".pop-up5").css({"display":"block"})
})
$(".aff6").click(function(){
    $(".pop-up7").css({"display":"block"})
})
$(".aff7").click(function(){
    $(".pop-up21").css({"display":"block"})
})
$(".aff8").click(function(){
    $(".pop-up22").css({"display":"block"})
})
$(".aff9").click(function(){
    $(".pop-up1").css({"display":"block"})
})
$(".aff10").click(function(){
    $(".pop-up24").css({"display":"block"})
})
$(".aff11").click(function(){
    $(".pop-up10").css({"display":"block"})
})
$(".aff12").click(function(){
    $(".pop-up23").css({"display":"block"})
})
$(".aff13").click(function(){
    $(".pop-up16").css({"display":"block"})
})
$(".aff14").click(function(){
    $(".pop-up4").css({"display":"block"})
})
$(".aff15").click(function(){
    $(".pop-up27").css({"display":"block"})
})
$(".aff16").click(function(){
    $(".pop-up19").css({"display":"block"})
})
$(".aff17").click(function(){
    $(".pop-up13").css({"display":"block"})
})
$(".aff18").click(function(){
    $(".pop-up28").css({"display":"block"})
})
$(".aff19").click(function(){
    $(".pop-up11").css({"display":"block"})
})
$(".aff20").click(function(){
    $(".pop-up31").css({"display":"block"})
})
$(".aff21").click(function(){
    $(".pop-up9").css({"display":"block"})
})
$(".aff22").click(function(){
    $(".pop-up17").css({"display":"block"})
})
$(".aff23").click(function(){
    $(".pop-up28").css({"display":"block"})
})
$(".aff24").click(function(){
    $(".pop-up6").css({"display":"block"})
})
$(".aff25").click(function(){
    $(".pop-up26").css({"display":"block"})
})
$(".aff26").click(function(){
    $(".pop-up20").css({"display":"block"})
})
$(".aff27").click(function(){
    //$(".pop-up2").css({"display":"block"})
})
$(".aff28").click(function(){
    $(".pop-up8").css({"display":"block"})
})
$(".aff29").click(function(){
    $(".pop-up12").css({"display":"block"})
})