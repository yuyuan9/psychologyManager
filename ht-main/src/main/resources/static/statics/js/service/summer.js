$(function () {
    $('.summernote').summernote({
        width: 690,
        height: 200,
        placeholder:'请输入荣誉资质（图片+文字）（限1000字）',
        tabsize: 2,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                sendFile(files);
            }
        }
    });
});
$(function () {
    $('.summernote-lc').summernote({
        width: 820,
        height: 240,
        placeholder: '请描述服务项目的大体流程，可图文结合描述（限1000字）',
        tabsize: 2,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                sendFile1(files);
            }
        }
    });
});
$(function () {
    $('.summernote-al').summernote({
        width: 820,
        height: 240,
        placeholder: '请描述服务项目的大体流程，可图文结合描述（限1000字）',
        tabsize: 2,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                sendFile2(files);
            }
        }
    });
});
$(function () {
    $('.summernote-td').summernote({
        width: 820,
        height: 240,
        placeholder: '请描述服务项目的大体流程，可图文结合描述（限1000字）',
        tabsize: 2,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                sendFile3(files);
            }
        }
    });
});
$(function () {
    $('.summernote-bz').summernote({
        width: 820,
        height: 240,
        placeholder: '请描述服务项目的大体流程，可图文结合描述（限1000字）',
        tabsize: 2,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                sendFile4(files);
            }
        }
    });
});


$(function () {
    $('.haochu').summernote({
        width: 820,
        height: 165,
        placeholder:'请输入申报项目改项目企业能够获得的益处，可图文结合描述（限1000字）',
        tabsize: 2,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                sendFile5(files);
            }
        }
    });
});

$(function () {
    $('.jieshou').summernote({
        width: 820,
        height: 165,
        placeholder:'包括单不限于以下内容，可图文结合描述1.申报条件2.申报流程3.附件资料：申报通知、申报指南、管理办法、申报模版等4.材料清单：罗列申报该项目需要准备的材料（限1000字）',
        tabsize: 2,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                sendFile6(files);
            }
        }
    });
});

$(function () {
    $('.neirong').summernote({
        width: 820,
        height: 165,
        placeholder:'请输入该项目申报服务主要包含的内容，如政策辅导，资料撰写、专家评审、现成一对一服务等（限1000字）',
        tabsize: 2,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                sendFile7(files);
            }
        }
    });
});
$(function () {
    $('.summernote-other').summernote({
        width: 820,
        height: 240,
        placeholder: '可输入需要补充的信息（限1000字）',
        tabsize: 2,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                sendFile8(files);
            }
        }
    });
});
$(function () {
    $('.other_test').summernote({
        width: 820,
        height: 165,
        placeholder: '请输入需要对产品或者服务补充的内容（限300字）',
        tabsize: 2,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                sendFile9(files);
            }
        }
    });
});

/**
 * 编辑器新增的ajax上传图片函数
 */
function sendFile(files, editor, $editable) {
    var size = files[0].size;
    if ((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    if (files) {
        //读取图片数据
        var f = files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                var width = image.width;
                if (width<=800){
                    uploadimg(files,1)
                }else{
                    // layer.msg("图片宽度必须等于800");
                    // return false;
                    uploadimg(files, 1)
                }
            };
            image.src = data;
        };
        reader.readAsDataURL(f);
    } else {
        layer.msg("文件上传失败");
        return false;
    }
   
}

function sendFile1(files, editor, $editable) {
    var size = files[0].size;
    if ((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    if (files) {
        //读取图片数据
        var f = files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                var width = image.width;
                if (width = 800) {
                    uploadimg(files,2)
                } else {
                    // layer.msg("图片宽度必须等于800");
                    // return false;
                    uploadimg(files, 2)
                }
            };
            image.src = data;
        };
        reader.readAsDataURL(f);
    } else {
        layer.msg("文件上传失败");
        return false;
    }

}
function sendFile2(files, editor, $editable) {
    var size = files[0].size;
    if ((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    if (files) {
        //读取图片数据
        var f = files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                var width = image.width;
                if (width = 800) {
                    uploadimg(files,3)
                } else {
                    // layer.msg("图片宽度必须等于800");
                    // return false;
                    uploadimg(files, 3)
                }
            };
            image.src = data;
        };
        reader.readAsDataURL(f);
    } else {
        layer.msg("文件上传失败");
        return false;
    }

}
function sendFile3(files, editor, $editable) {
    var size = files[0].size;
    if ((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    if (files) {
        //读取图片数据
        var f = files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                var width = image.width;
                if (width = 800) {
                    uploadimg(files,4)
                } else {
                    // layer.msg("图片宽度必须等于800");
                    // return false;
                    uploadimg(files, 4)
                }
            };
            image.src = data;
        };
        reader.readAsDataURL(f);
    } else {
        layer.msg("文件上传失败");
        return false;
    }

}
function sendFile4(files, editor, $editable) {
    var size = files[0].size;
    if ((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    if (files) {
        //读取图片数据
        var f = files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                var width = image.width;
                if (width = 800) {
                    uploadimg(files,5)
                } else {
                    // layer.msg("图片宽度必须等于800");
                    // return false;
                    uploadimg(files, 5)
                }
            };
            image.src = data;
        };
        reader.readAsDataURL(f);
    } else {
        layer.msg("文件上传失败");
        return false;
    }

}
function sendFile5(files, editor, $editable) {
    var size = files[0].size;
    if ((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    if (files) {
        //读取图片数据
        var f = files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                var width = image.width;
                if (width = 800) {
                    uploadimg(files,6)
                } else {
                    // layer.msg("图片宽度必须等于800");
                    // return false;
                    uploadimg(files, 6)
                }
            };
            image.src = data;
        };
        reader.readAsDataURL(f);
    } else {
        layer.msg("文件上传失败");
        return false;
    }

}
function sendFile6(files, editor, $editable) {
    var size = files[0].size;
    if ((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    if (files) {
        //读取图片数据
        var f = files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                var width = image.width;
                if (width = 800) {
                    uploadimg(files,7)
                } else {
                    // layer.msg("图片宽度必须等于800");
                    // return false;
                    uploadimg(files, 7)
                }
            };
            image.src = data;
        };
        reader.readAsDataURL(f);
    } else {
        layer.msg("文件上传失败");
        return false;
    }

}
function sendFile7(files, editor, $editable) {
    var size = files[0].size;
    if ((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    if (files) {
        //读取图片数据
        var f = files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                var width = image.width;
                if (width = 800) {
                    uploadimg(files,8)
                } else {
                    // layer.msg("图片宽度必须等于800");
                    // return false;
                    uploadimg(files, 8)
                }
            };
            image.src = data;
        };
        reader.readAsDataURL(f);
    } else {
        layer.msg("文件上传失败");
        return false;
    }

}
function sendFile8(files, editor, $editable) {
    var size = files[0].size;
    if ((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    if (files) {
        //读取图片数据
        var f = files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                var width = image.width;
                if (width = 800) {
                    uploadimg(files, 9)
                } else {
                    // layer.msg("图片宽度必须等于800");
                    // return false;
                    uploadimg(files, 9)
                }
            };
            image.src = data;
        };
        reader.readAsDataURL(f);
    } else {
        layer.msg("文件上传失败");
        return false;
    }

}
function sendFile9(files, editor, $editable) {
    var size = files[0].size;
    if ((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    if (files) {
        //读取图片数据
        var f = files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload = function () {
                var width = image.width;
                if (width = 800) {
                    uploadimg(files, 10)
                } else {
                    // layer.msg("图片宽度必须等于800");
                    // return false;
                    uploadimg(files, 10)
                }
            };
            image.src = data;
        };
        reader.readAsDataURL(f);
    } else {
        layer.msg("文件上传失败");
        return false;
    }

}
function uploadimg(files,index){
    var formData = new FormData();
    formData.append("file", files[0]);
    $.ajax({
        data: formData,
        type: "POST",
        url: "/uploadController/upload", // 图片上传出来的url，返回的是图片上传后的路径，http格式
        cache: false,
        contentType: false,
        processData: false,
        dataType: "json",
        success: function (data) { //data是返回的hash,key之类的值，key是定义的文件名
            // console.log(data);
            if(index==1){
                $('.summernote').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
            }
            if(index==2){
                $('.summernote-lc').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
            }
            if (index == 3) {
                $('.summernote-al').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
            }
            if (index == 4) {
                $('.summernote-td').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
            }
            if (index == 5) {
                $('.summernote-bz').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
            }
            if (index == 6) {
                $('.haochu').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
            }
            if (index == 7) {
                $('.jieshou').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
            }
            if (index == 8) {
                $('.neirong').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
            }
            if (index == 9) {
                $('.summernote-other').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
            }
            if (index == 10) {
                $('.other_test').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
            }
            $('#addModal').modal('handleUpdate');
        },
        error: function () {
            alert("上传失败");
        }
    });
}

// function sendFile1(files, editor, $editable) {
//     var size = files[0].size;
//     if ((size / 1024 / 1024) > 2) {
//         alert("图片大小不能超过2M...");
//         return false;
//     }
//     var formData = new FormData();
//     formData.append("file", files[0]);
//     $.ajax({
//         data: formData,
//         type: "POST",
//         url: "/uploadController/upload", // 图片上传出来的url，返回的是图片上传后的路径，http格式
//         cache: false,
//         contentType: false,
//         processData: false,
//         dataType: "json",
//         success: function (data) { //data是返回的hash,key之类的值，key是定义的文件名
//             console.log(data);
//             console.log(data.data.path);
//             $('.summernote-lc').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
//             $('#addModal').modal('handleUpdate');
//         },
//         error: function () {
//             alert("上传失败");
//         }
//     });
// }
// function sendFile2(files, editor, $editable) {
//     var size = files[0].size;
//     if ((size / 1024 / 1024) > 2) {
//         alert("图片大小不能超过2M...");
//         return false;
//     }
//     var formData = new FormData();
//     formData.append("file", files[0]);
//     $.ajax({
//         data: formData,
//         type: "POST",
//         url: "/uploadController/upload", // 图片上传出来的url，返回的是图片上传后的路径，http格式
//         cache: false,
//         contentType: false,
//         processData: false,
//         dataType: "json",
//         success: function (data) { //data是返回的hash,key之类的值，key是定义的文件名
//             console.log(data);
//             console.log(data.data.path);
//             $('.summernote-al').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
//             $('#addModal').modal('handleUpdate');
//         },
//         error: function () {
//             alert("上传失败");
//         }
//     });
// }
// function sendFile3(files, editor, $editable) {
//     var size = files[0].size;
//     if ((size / 1024 / 1024) > 2) {
//         alert("图片大小不能超过2M...");
//         return false;
//     }
//     var formData = new FormData();
//     formData.append("file", files[0]);
//     $.ajax({
//         data: formData,
//         type: "POST",
//         url: "/uploadController/upload", // 图片上传出来的url，返回的是图片上传后的路径，http格式
//         cache: false,
//         contentType: false,
//         processData: false,
//         dataType: "json",
//         success: function (data) { //data是返回的hash,key之类的值，key是定义的文件名
//             console.log(data);
//             console.log(data.data.path);
//             $('.summernote-td').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
//             $('#addModal').modal('handleUpdate');
//         },
//         error: function () {
//             alert("上传失败");
//         }
//     });
// }
// function sendFile4(files, editor, $editable) {
//     var size = files[0].size;
//     if ((size / 1024 / 1024) > 2) {
//         alert("图片大小不能超过2M...");
//         return false;
//     }
//     var formData = new FormData();
//     formData.append("file", files[0]);
//     $.ajax({
//         data: formData,
//         type: "POST",
//         url: "/uploadController/upload", // 图片上传出来的url，返回的是图片上传后的路径，http格式
//         cache: false,
//         contentType: false,
//         processData: false,
//         dataType: "json",
//         success: function (data) { //data是返回的hash,key之类的值，key是定义的文件名
//             console.log(data);
//             console.log(data.data.path);
//             $('.summernote-bz').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
//             $('#addModal').modal('handleUpdate');
//         },
//         error: function () {
//             alert("上传失败");
//         }
//     });
// }
// function sendFile5(files, editor, $editable) {
//     var size = files[0].size;
//     if ((size / 1024 / 1024) > 2) {
//         alert("图片大小不能超过2M...");
//         return false;
//     }
//     var formData = new FormData();
//     formData.append("file", files[0]);
//     $.ajax({
//         data: formData,
//         type: "POST",
//         url: "/uploadController/upload", // 图片上传出来的url，返回的是图片上传后的路径，http格式
//         cache: false,
//         contentType: false,
//         processData: false,
//         dataType: "json",
//         success: function (data) { //data是返回的hash,key之类的值，key是定义的文件名
//             console.log(data);
//             console.log(data.data.path);
//             $('.haochu').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
//             $('#addModal').modal('handleUpdate');
//         },
//         error: function () {
//             alert("上传失败");
//         }
//     });
// }
// function sendFile6(files, editor, $editable) {
//     var size = files[0].size;
//     if ((size / 1024 / 1024) > 2) {
//         alert("图片大小不能超过2M...");
//         return false;
//     }
//     var formData = new FormData();
//     formData.append("file", files[0]);
//     $.ajax({
//         data: formData,
//         type: "POST",
//         url: "/uploadController/upload", // 图片上传出来的url，返回的是图片上传后的路径，http格式
//         cache: false,
//         contentType: false,
//         processData: false,
//         dataType: "json",
//         success: function (data) { //data是返回的hash,key之类的值，key是定义的文件名
//             console.log(data);
//             console.log(data.data.path);
//             $('.jieshou').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
//             $('#addModal').modal('handleUpdate');
//         },
//         error: function () {
//             alert("上传失败");
//         }
//     });
// }
// function sendFile7(files, editor, $editable) {
//     var size = files[0].size;
//     if ((size / 1024 / 1024) > 2) {
//         alert("图片大小不能超过2M...");
//         return false;
//     }
//     var formData = new FormData();
//     formData.append("file", files[0]);
//     $.ajax({
//         data: formData,
//         type: "POST",
//         url: "/uploadController/upload", // 图片上传出来的url，返回的是图片上传后的路径，http格式
//         cache: false,
//         contentType: false,
//         processData: false,
//         dataType: "json",
//         success: function (data) { //data是返回的hash,key之类的值，key是定义的文件名
//             console.log(data);
//             console.log(data.data.path);
//             $('.neirong').summernote('insertImage', "http://47.92.70.12:9090/" + data.data.path);
//             $('#addModal').modal('handleUpdate');
//         },
//         error: function () {
//             alert("上传失败");
//         }
//     });
// }