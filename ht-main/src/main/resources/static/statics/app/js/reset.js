var i = window.devicePixelRatio;
var iScale = 1/i;

document.write('<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale='+iScale+', maximum-scale='+iScale+', minimum-scale='+iScale+'" />');



var winW = document.documentElement.clientWidth


var htmlFont = winW/10;
document.getElementsByTagName('html')[0].style.fontSize = htmlFont+'px';

