var __ssn=function(a){var k=function(f){function h(a){if(!a)return alert("UI load error."),!1;var b=document.createElement("div");document.body.insertBefore(b,document.body.firstChild);b.innerHTML=a;return!0}var g=function(){var b=window.XMLHttpRequest?new window.XMLHttpRequest:new ActiveXObject("MSXML2.XMLHTTP.3.0");b.open("GET",a.ESVS.SRCPath+"unisignweb/rsrc/layout/ssn.html?version="+a.ver,!1);b.send(null);return b.responseText},d=function(){var b=window.XMLHttpRequest?new window.XMLHttpRequest:new ActiveXObject("MSXML2.XMLHTTP.3.0");b.open("GET",a.ESVS.SRCPath+"unisignweb/rsrc/lang/"+a.ESVS.Language+"/ssn_"+a.ESVS.Language+".js?version="+a.ver,!1);b.send(null);return b.responseText},e=a.ESVS.TabIndex;return function(){var b=a.CustomEval(g),c=a.CustomEval(d,!0);h(b());b=document.getElementById("us-ssn-lbl-title");b.appendChild(document.createTextNode(c.IDS_SSN));b.setAttribute("tabindex",e,0);document.getElementById("us-ssn-cls-btn-img").setAttribute("src",a.ESVS.SRCPath+"unisignweb/rsrc/img/x-btn.png",0);document.getElementById("us-ssn-notice-text").innerHTML=c.IDS_SSN_NOTICE+" ("+c.IDS_SSN_WARNING+")";document.getElementById("us-ssn-input-text").appendChild(document.createTextNode(c.IDS_SSN_NAME));b=document.getElementById("us-ssn-input-textbox");b.setAttribute("tabindex",e+1,0);b.onkeydown=function(a){if(a=a?a:event)a=a||window.event,13==(a.which||a.keyCode)&&document.getElementById("us-ssn-confirm-btn").click()};b=document.getElementById("us-ssn-confirm-btn");b.setAttribute("value",c.IDS_CONFIRM,0);b.setAttribute("title",c.IDS_CONFIRM+c.IDS_BUTTON,0);b.setAttribute("tabindex",e+2,0);b.onclick=function(){if(c){var b=document.getElementById("us-ssn-input-textbox"),d=b.value;d&&4&a.ESVS.Mode&&("touchen"==a.ESVS.SecureKeyboardType&&a.bsUtil().isTouchEnKeyUsable()?d=a.bsUtil().GetEncryptPwd("us-keyboard-secure-frm","us-ssn-input-textbox"):"ahnlab"==a.ESVS.SecureKeyboardType&&a.bsUtil().isAhnlabProtectorUsable()&&(d=a.bsUtil().GetAhnlabEncInputInfo("us-ssn-input-textbox")));d?(f.onConfirm(d),b.value=""):(a.uiUtil().msgBox(c.IDS_MSGBOX_ERROR_PLEASE_INPUT_SSN),b.focus())}};b=document.getElementById("us-ssn-cancel-btn");b.setAttribute("value",c.IDS_CANCEL,0);b.setAttribute("title",c.IDS_CANCEL+c.IDS_BUTTON,0);b.setAttribute("tabindex",e+3,0);b.onclick=function(){f.onCancel()};b=document.getElementById("us-ssn-cls-btn");b.setAttribute("title",c.IDS_SSN_CLOSE,0);b.setAttribute("tabindex",e+4,0);b.onclick=function(){f.onCancel()};return document.getElementById("us-div-ssn")}()};return function(f){var h=a.uiLayerLevel,g=a.uiUtil().getOverlay(h),d=k({type:f.type,args:f.args,onConfirm:f.onConfirm,onCancel:f.onCancel});d.style.zIndex=h+1;a.ESVS.TargetObj.insertBefore(g,a.ESVS.TargetObj.firstChild);var e=window.onresize;return{show:function(){a.bsUtil().AhnlabClearText("us-ssn-input-textbox");a.ActiveUI=this;draggable(d,document.getElementById("us-div-ssn-title"));g.style.display="block";a.uiUtil().offsetResize(d);window.onresize=function(){a.uiUtil().offsetResize(d);e&&e()};a.uiLayerLevel+=10;a.ESVS.TabIndex+=30;a.bsUtil().ahnlabInit();setTimeout(function(){var b=d.getElementsByTagName("p");if(0<b.length)for(var c=0;c<b.length;c++)"us-ssn-lbl-title"==b[c].id&&b[c].focus();a.bsUtil().SetSecurityStatus("us-ssn-input-textbox");a.bsUtil().SetReScan()},10)},hide:function(){g.style.display="none";d.style.display="none"},dispose:function(){window.onresize=function(){e&&e()};d.parentNode.parentNode.removeChild(d.parentNode);g.parentNode&&g.parentNode.removeChild(g);a.uiLayerLevel-=10;a.ESVS.TabIndex-=30}}}};