var __notice=function(a){var l=function(e){function h(b){if(!b)return alert("UI load error."),!1;var a=document.createElement("div");document.body.insertBefore(a,document.body.firstChild);a.innerHTML=b;return!0}var f=function(){var b=window.XMLHttpRequest?new window.XMLHttpRequest:new ActiveXObject("MSXML2.XMLHTTP.3.0");b.open("GET",a.ESVS.SRCPath+"unisignweb/rsrc/layout/notice.html?version="+a.ver,!1);b.send(null);return b.responseText},c=function(){var b=window.XMLHttpRequest?new window.XMLHttpRequest:new ActiveXObject("MSXML2.XMLHTTP.3.0");b.open("GET",a.ESVS.SRCPath+"unisignweb/rsrc/lang/"+a.ESVS.Language+"/notice_"+a.ESVS.Language+".js?version="+a.ver,!1);b.send(null);return b.responseText},d=a.ESVS.TabIndex;return function(){var b=a.CustomEval(f),g=a.CustomEval(c,!0);h(b());b=document.getElementById("us-notice-lbl-title");b.appendChild(document.createTextNode(g.IDS_NOTICE));b.setAttribute("tabindex",d,0);if(b=e.args.msg){var k=document.getElementById("us-notice-lbl");k.innerHTML=b;k.setAttribute("tabindex",d+1,0)}b=document.getElementById("us-notice-confirm-btn");b.setAttribute("value",g.IDS_CONFIRM,0);b.setAttribute("title",g.IDS_CONFIRM+g.IDS_BUTTON,0);b.setAttribute("tabindex",d+2,0);b.onclick=function(){e.onConfirm()};b=document.getElementById("us-notice-cls-img-btn");b.setAttribute("title",g.IDS_NOTICE_CLOSE,0);b.setAttribute("tabindex",d+3,0);b.onclick=function(){e.onCancel()};document.getElementById("us-notice-cls-btn-img").setAttribute("src",a.ESVS.SRCPath+"unisignweb/rsrc/img/x-btn.png",0);return document.getElementById("us-div-notice")}()};return function(e){var h=a.uiLayerLevel,f=a.uiUtil().getOverlay(h),c=l({type:e.type,args:e.args,onConfirm:e.onConfirm,onCancel:e.onCancel});c.style.zIndex=h+1;a.ESVS.TargetObj.insertBefore(f,a.ESVS.TargetObj.firstChild);var d=window.onresize;return{show:function(){a.ActiveUI=this;draggable(c,document.getElementById("us-div-notice-title"));f.style.display="block";a.uiUtil().offsetResize(c);window.onresize=function(){a.uiUtil().offsetResize(c);d&&d()};a.uiLayerLevel+=10;a.ESVS.TabIndex+=30;setTimeout(function(){var b=c.getElementsByTagName("p");if(0<b.length)for(var a=0;a<b.length;a++)"us-notice-lbl-title"==b[a].id&&b[a].focus()},10)},hide:function(){f.style.display="none";c.style.display="none"},dispose:function(){window.onresize=function(){d&&d()};c.parentNode.parentNode.removeChild(c.parentNode);f.parentNode.removeChild(f);a.uiLayerLevel-=10;a.ESVS.TabIndex-=30}}}};