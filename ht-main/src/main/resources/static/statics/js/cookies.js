function setCookie(city, value, expiredays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + expiredays);
    document.cookie = city + "=" + escape(value) +
        ((expiredays == null) ? "" : ";path=/;expires=" + exdate.toGMTString())
}

function getCookie(userName) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(userName + "=");
        if (c_start != -1) {
            c_start = c_start + userName.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) {
                c_end = document.cookie.length;
            }
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
    
}

function getCookie2(name) {
    var strcookie = document.cookie;//获取cookie字符串
    var arrcookie = strcookie.split("; ");//分割
    //遍历匹配
    for (var i = 0; i < arrcookie.length; i++) {
        var arr = arrcookie[i].split("=");
        if (arr[0] == name) {
            return arr[1];
        }
    }
    return "";
}