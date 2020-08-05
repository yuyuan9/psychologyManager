(function ($) {
    if (window.htajax)return;
    var htajax = {
        get: function (url, successCb, errorCb) {
            $.ajax({
                type: "GET",
                url: url,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                cache: false,
                success: function (data) {
                    successCb(data);
                },
                error: function (data) {
                    errorCb(data);
                }
            });
        },
        getForm: function (url, jsonData, successCb, errorCb) {
            $.ajax({
                type: "GET",
                url: url,
                data: jsonData,
                dataType: "json",
                cache: false,
                success: function (data) {
                    successCb(data);
                },
                error: function (data) {
                    errorCb(data);
                }
            });
        },
        post: function (url, jsonData, successCb, errorCb) {
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(jsonData),
                dataType: "json",
                cache:false,
                success: function (data) {
                    successCb(data);
                },
                error: function (data) {
                    errorCb(data);
                }
            });
        },
        postJson: function (url, jsonData, successCb, errorCb) {
            $.ajax({
                type: "POST",
                url: url,
                data: jsonData,
                dataType: "json",
                cache:false,
                success: function (data) {
                    successCb(data);
                },
                error: function (data) {
                    errorCb(data);
                }
            });
        },
        postForm: function (url, formData, successCb, errorCb) {
            $.ajax({
                type: "POST",
                url: url,
                data: formData,
                dataType: "json",
                cache:false,
                success: function (data) {
                    successCb(data);
                },
                error: function (data) {
                    errorCb(data);
                }
            });
        }
    };
    window.htajax = htajax;
})($);