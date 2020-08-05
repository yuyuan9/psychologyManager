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
//固定栏
$(function () {
    $(window).scroll(function () {
        var $scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop; //兼容浏览器
        //console.log($scrollTop);
        if ($scrollTop > 288) { //滚动高度可调
            $(".btn_discuss1").css({
                "position": "relative",
                "margin-left": "-450px",
            });
        } else {
            $(".btn_discuss1").css({
                "position": "fixed",
                "left": " 50%",
                "bottom": "0",
                "margin-left": "-600px",
            });
        }
    });

})

//分享
$('#share_pay').click(function () {
    $('.share24').fadeIn(300);
});

$('.shareclose24').click(function () {
    $('.share24').fadeOut(300);
});

$('.payshow').hide(300);
$(".freeshow").hide(300);
$('#r_box_login2').hide();
$('.close-reveal-modal').click(function () {
    location.reload();
    setInterval(function () {
        $('.login-top').hide();
    }, 3000);
});

$('#login_btn1').click(function () {
    login_submit();
});

$('.login_input').keyup(function (event) {
    if (event.keyCode == 13) {
        login_submit();
    }
});

$("#contract_mask").css("width", $(document).width());

function limitTextarea(self, nowleng) {
    $(self).on('input propertychange', function (event) {
        var _val = $(self).val();
        _val = _val < 300 ? _val : _val.substr(0, 300);
        $(self).val(_val);
        $(nowleng).text(_val.length)
    });
    $(self).blur(function () {
        $(self).off('input propertychange');
    });
}

$(function () {
    function autocenter() {
        var bodyW = parseInt(document.documentElement.clientWidth);
        var bodyH = parseInt(document.documentElement.clientHeight);
        var elW = $("#weixin").width();
        var elH = $("#weixin").height();

        $("#weixin").css("left", (bodyW - elW) / 2);
        $("#weixin").css("top", (bodyH - elH) / 2);
    }
    window.onresize = function () {
        autocenter();
    };
});



$("#contract_mask").css("width", $(document).width());

function limitTextarea(self, nowleng) {
    $(self).on('input propertychange', function (event) {
        var _val = $(self).val();
        _val = _val < 300 ? _val : _val.substr(0, 300);
        $(self).val(_val);
        $(nowleng).text(_val.length)
    });
    $(self).blur(function () {
        $(self).off('input propertychange');
    });
}

$(function () {
    $("#socialShare").socialShare({
        content: $("p").text().trim(),
        url: window.location.href,
        titile: $("h1").text().trim()
    });
    $("#socialShare1").socialShare({
        content: $("p").text().trim(),
        url: window.location.href,
        titile: $("h1").text().trim()
    });
    $(".msb_main").trigger('click');
});

$("#shareQQ").on("click", function () {
    $(this).socialShare("tQQ");
})


$(function () {
    function autocenter() {
        var bodyW = parseInt(document.documentElement.clientWidth);
        var bodyH = parseInt(document.documentElement.clientHeight);
        var elW = $("#weixin").width();
        var elH = $("#weixin").height();

        $("#weixin").css("left", (bodyW - elW) / 2);
        $("#weixin").css("top", (bodyH - elH) / 2);
    }
    window.onresize = function () {
        autocenter();
    };
});

$(document).on("click", ".msb_main", function () {
    if ($(this).hasClass("disabled")) return;
    var e = 500; //动画时间
    var t = 250; //延迟时间
    var r = $(this).parent().find(".msb_network_button").length; //分享组件的个数
    var i = 60;
    var s = e + (r - 1) * t;
    var o = 1;
    var a = $(this).outerWidth();
    var f = $(this).outerHeight();
    var c = $(this).parent().find(".msb_network_button:eq(0)").outerWidth();
    var h = $(this).parent().find(".msb_network_button:eq(0)").outerHeight();
    var p = (a - c) / 2; //起始位置
    var d = (f - h) / 2; //起始位置
    var v = 0 / 180 * Math.PI;
    if (!$(this).hasClass("active")) {
        $(this).addClass("disabled").delay(s).queue(function (e) {
            $(this).removeClass("disabled").addClass("active");
            e()
        });
        $(this).parent().find(".msb_network_button").each(function () {
            var n = p + (p + i * o) * Math.cos(v) * 0.8; //结束位置
            var r = d + (d + i * o) * Math.sin(v); //结束位置
            $(this).css({
                display: "block",
                left: p + "px",
                top: d + "px"
            }).stop().delay(t * o).animate({
                left: n + "px",
                top: r + "px"
            }, e);
            o++
        })
    } else {
        o = r;
        $(this).addClass("disabled").delay(s).queue(function (e) {
            $(this).removeClass("disabled").removeClass("active");
            e()
        });
        $(this).parent().find(".msb_network_button").each(function () {
            $(this).stop().delay(t * o).animate({
                left: p,
                top: d
            }, e);
            o--
        })
    }
});
//懒加载方法
function test(name) {
    if (name) {
        this.layerIndex = layer.load(0, {
            offset: ['50%', "42%"],
            shade: false
        })
    } else {
        layer.close(this.layerIndex); //完成加载后关闭loading
    }
}

$(function () {
    test(true);
    // isdownloaded();
    var id0 = "[[${libary.id}]]";
    var downloadhoney = "[[${libary.downloadhoney}]]";
    var pdfDoc = null,
        pageNum = 1,
        pageRendering = false,
        pageNumPending = null,
        scale = 1,
        scaleChange = false,
        canvas = document.getElementById('the-canvas'),
        ctx = canvas.getContext('2d');
    canvas.height = 600;
    //下载接口 url
    var url = '';
    url = "/ht-biz/library/seekExperts?id=" + id0;

    function renderPage(num) {
        pageRendering = true;
        // Using promise to fetch the page
        pdfDoc.getPage(num).then(function (page) {
            var viewport = page.getViewport(1);
            if (!scaleChange) {
                var desiredWidth = "900";
                scale = desiredWidth / viewport.width;
            }
            viewport = page.getViewport(scale);
            canvas.height = viewport.height;
            canvas.width = viewport.width;
            // Render PDF page into canvas context
            var renderContext = {
                canvasContext: ctx,
                viewport: viewport
            };
            var renderTask = page.render(renderContext);
            // Wait for rendering to finish
            renderTask.promise.then(function () {
                pageRendering = false;
                if (pageNumPending !== null) {
                    // New page rendering is pending
                    renderPage(pageNumPending);
                    pageNumPending = null;
                }
            });

        });
        // Update page counters
        $("#page_num_down_one").html(num);
        $(".page_last").html(pdfDoc.numPages - num);
        //document.getElementById('page_num').innerHTML = num;
        //document.getElementById('page_num_down').textContent = num;
    }
    /**
     * If another page rendering in progress, waits until the rendering is
     * finised. Otherwise, executes rendering immediately.
     */
    function queueRenderPage(num) {
        if (pageRendering) {
            pageNumPending = num;
        } else {
            renderPage(num);
        }
    }
    /**
     * Displays previous page.
     */
    function onPrevPage() {
        if (pageNum <= 1) {
            $('.payshow').hide(300);
            return;
        }
        $("html,body").animate({
            "scrollTop": "150"
        }, 300);
        $("#look").hide();
        $(".contract_mask").hide();
        $('.payshow').hide(300);
        $('.freeshow').hide(300);
        pageNum--;
        queueRenderPage(pageNum);
    }
    document.getElementById('queueRenderPage').addEventListener('click', onPrevPage);
    // document.getElementById('pdf_prev').addEventListener('click', onPrevPage);

    function onNextPage() {
        if (pageNum >= pdfDoc.numPages) {
            return;
        }
        //只能看1半，提示下载
        $("html,body").animate({
            "scrollTop": "150"
        }, 300);
        var id = "[[${libary.id}]]";
        if (downloadhoney != 'null' && downloadhoney > 0) {
            if (pageNum >= Math.floor(pdfDoc.numPages / 2)) {
                if (vm.code == 10000) {

                    htajax.postForm('/ht-biz/library/getdownfilehoney', {
                        ids: id,
                    }, function (data) {
                        if (data.code == 10000) {
                            if (data.data[1] != 0) {
                                //layer.msg('请下载查看后续内容');
                                $("#look").show();
                                $(".contract_mask").show();
                                $(".close_login").click(function () {
                                    $("#look").hide();
                                    $(".contract_mask").hide();
                                })
                                $('.payshow').show(300);
                                return;
                            } else {
                                pageNum++;
                                queueRenderPage(pageNum);
                                return;
                            }
                        } else {
                            return;
                        }
                    }, function (data) {
                        // console.log("错误");
                    })
                } else {
                    //layer.msg("登录查看更多");
                    $(".loginBox").show();
                    return;
                }

            } else {
                pageNum++;
                queueRenderPage(pageNum);
                return;
            }
        } else {
            pageNum++;
            queueRenderPage(pageNum);
            return;
        }

    }
    document.getElementById('next_down_one').addEventListener('click', onNextPage);
    // document.getElementById('pdf_next').addEventListener('click', onNextPage);
    /**
     * Asynchronously downloads PDF.
     */
    PDFJS.getDocument(url).then(function (pdfDoc_) {
        pdfDoc = pdfDoc_;
        $("#page_count_down_one").html(pdfDoc.numPages);
        // console.log(num);
        // $(".page_last").html(pdfDoc.numPages-num);
        // document.getElementById('page_count').textContent = pdfDoc.numPages;
        // document.getElementById('page_count_down').textContent = pdfDoc.numPages;
        // Initial/first page rendering
        renderPage(pageNum);
        test(false);
    });
    //放大
    document.getElementById('big').addEventListener('click', function () {
        scale += 0.1;
        queueRenderPage(pageNum);
        var sizeNum = (Math.round(scale * 10000) / 100) + '%';
        //document.getElementById('pdf_size').innerHTML = sizeNum;
        scaleChange = true;
    });
    //初始化放大120%
    //document.getElementById('pdf_size').innerHTML = '130%';
    //缩小
    document.getElementById('small').addEventListener('click', function () {
        if (scale > 0.1) {
            scale -= 0.1;
            queueRenderPage(pageNum);
            var sizeNum = (Math.round(scale * 10000) / 100) + '%';
            //document.getElementById('pdf_size').innerHTML = sizeNum;
        } else {
            scale = 0;
            var sizeNum = (Math.round(scale * 10000) / 100) + '%';
            //document.getElementById('pdf_size').innerHTML = sizeNum;
        }
        scaleChange = true;

    });

});