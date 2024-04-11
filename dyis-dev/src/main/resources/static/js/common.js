/****************************************************************
 * 
 * 파일명 : com.js
 * 설  명 : 공통 자바스크립트
 * 
 *    수정일      수정자     Version        Function 명
 * ------------    ---------   -------------  ----------------------------
 * 2022.06.15   BSG       1.0             최초생성
 * 
 * 
 * **************************************************************/

getBrowser = function(){ 
	var userAgent=navigator.userAgent.toLowerCase(); 
	
	if ( userAgent.indexOf('edge')>-1){
		return 'edge'; 
	} else if(userAgent.indexOf('whale')>-1){
		return 'whale';
	} else if(userAgent.indexOf('chrome')>-1){
		return 'chrome';
	} else if(userAgent.indexOf('firefox')>-1){
		return 'firefox';} else{return 'msie';
	} 
}


$(function($){
    try {
        $.datepicker.regional['ko'] = {
              closeText: '\ub2eb\uae30',
              prevText: '\uc774\uc804',
              nextText: '\ub2e4\uc74c',
              changeMonth: true, 
              changeYear: true,
              currentText: '\uc624\ub298',
              monthNames: ['1\uc6d4','2\uc6d4','3\uc6d4','4\uc6d4','5\uc6d4','6\uc6d4','7\uc6d4','8\uc6d4','9\uc6d4','10\uc6d4','11\uc6d4','12\uc6d4'],
              monthNamesShort: ['1\uc6d4','2\uc6d4','3\uc6d4','4\uc6d4','5\uc6d4','6\uc6d4','7\uc6d4','8\uc6d4','9\uc6d4','10\uc6d4','11\uc6d4','12\uc6d4'],
              dayNames: ['\uc77c','\uc6d4','\ud654','\uc218','\ubaa9','\uae08','\ud1a0'],
              dayNamesShort: ['\uc77c','\uc6d4','\ud654','\uc218','\ubaa9','\uae08','\ud1a0'],
              dayNamesMin: ['\uc77c','\uc6d4','\ud654','\uc218','\ubaa9','\uae08','\ud1a0'],
              weekHeader: 'Wk',
              dateFormat: 'yy.mm.dd',
              firstDay: 0,
              showMonthAfterYear: true,
              yearSuffix: '<span style="float:left;margin:4px 2px 0 0;">\ub144</span>',
              showOn: 'button',
              buttonImage: '/admin/images/icon_calendar.png', //이미지 url
              buttonImageOnly: true
        };
        $.datepicker.setDefaults($.datepicker.regional['ko']);
        
        $(".date").datepicker().css("margin-right", "2px").bind("focus", function() {
            $(this).select();
        });
        $(".ui-datepicker-trigger").css("cursor", "pointer");
    }catch(e){}
    
    //$(".contentsArea").append('<div class="loadingImg" style="display:none;"><img src="/images/loading.gif" /></div>');
    $(".contentsArea").append('<div id="dialog" style="display:none"><iframe name="popForm" id="popForm" style="width: 100%; height: 100%; overflow: auto; border: none;"></iframe></div>');
});

window.onpageshow = function(event) {
    if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
    	hideLoading();
    }
}

//object 설정
$.fn.serializeObject = function() {
  var result = {}
  var extend = function(i, element) {
    var node = result[element.name]
    if ("undefined" !== typeof node && node !== null) {
      if ($.isArray(node)) {
        node.push(element.value)
      } else {
        result[element.name] = [node, element.value]
      }
    } else {
      result[element.name] = element.value
    }
  }

  $.each(this.serializeArray(), extend)
  return result
}


//공통 함수 시작 
var com = new Object();

//로그아웃
com.fnLogout = function(){
	com.fnAjaxDef("/login/logout.do");
}
//ajax 기본 호출
//url : 호출 추소, param : json 변수, callBack : 콜백 함수, msgYn: 메시지 처리 여부, sessYn : 세션 체크 여부
com.fnAjaxDef = function(url, param, callBack, msgYn, sessYn){
	
	//세션 체크
	if(sessYn == "Y"){
		if(!com.fnSessChk()){
			top.location.href = "/";
			return;
		}
	};
	
	if(param == undefined) param = {};
	
	parent.$("#div_loading").css("display","block"); //로딩바
	
	$.ajax({
   		url : url
  			, type : "POST"
   			, dataType : "json"
  			, contentType : "application/json; charset=UTF-8"
  	    	, data : JSON.stringify(param) 
  			, success : function(resData){
	   			
	   			//결과메세지
	   			if(resData.resultMsg != undefined && (msgYn == undefined || msgYn =="Y"))
		   			alert(resData.resultMsg);
		   			
	   			//콜백 함수
	   			if(resData.resultCode == 0){
					if(callBack != undefined && typeof callBack == "function"){
		   				callBack(resData);
		   			}
	   			}
	   			parent.$("#div_loading").css("display","none"); //로딩바
   			}
	   		, error : function(e){
	   			alert("오류가 발생하였습니다.(시스템 오류)");
	   			//console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	   			
	   			parent.$("#div_loading").css("display","none"); //로딩바
	   		}
   	});
};

//url : 호출 추소, param : json 변수, callBack : 콜백 함수, msgYn: 메시지 처리 여부, sessYn : 세션 체크 여부
com.fnAjaxDefTest = function(param){
	console.log(param);
	
};


//ajax post 호출
//url : 호출 추소, formId : form ID, callBack : 콜백 함수, msgYn: 메시지 처리 여부, sessYn : 세셭 체크 여부
com.fnAjaxPost = function(url, formId, callBack, msgYn, sessYn){
	
	//세션 체크
	if(sessYn == "Y"){
		if(!com.fnSessChk()){
			top.location.href = "/";
			return;
		}
	};
	
	parent.$("#div_loading").css("display","block"); //로딩바
	
	//var formData = new FormData($("#"+formId)[0]);
	var param = JSON.stringify($("#"+formId).serializeObject());
	$.ajax({
   		url : url
		, data : param
		, type : "post"
		, contentType : "application/json; charset=UTF-8"
		, success : function(resData){
   			parent.$("#div_loading").css("display","none"); //로딩바
   			
   			//결과메세지
   			if(resData.resultMsg != undefined && (msgYn == undefined || msgYn =="Y"))
   				alert(resData.resultMsg);
   				
   			//콜백 함수
   			if(callBack != undefined && typeof callBack == "function")
   				callBack(resData);
   			
		}
   		, error : function(e){ 
   			alert("오류가 발생하였습니다.(시스템 오류)");
   			//console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
   			
   			parent.$("#div_loading").css("display","none"); //로딩바
   		}
   	});
};


//form 기본 호출
com.fnAction = function(url, frm, target){
 	if(target == undefined)
 		target = "_self";
 	
 	if(frm != undefined){
 		frm.action = url;
 		frm.target = target;
 		frm.submit();
 	}else{
 		document.forms[0].action = url;
 		document.forms[0].target = target;
 		document.forms[0].submit();
 	}
}


//필수항목 체크
com.fnValChk = function(targs){
 	//alert(targs.length);
 	for(i = 0; i < targs.length; i++ ){
		 obj = $("#"+targs[i]);
		 //alert(obj.prop("tagName"));
		 if(obj.val() == ""){
			if(obj.prop("tagName") == "INPUT"){
				 alert(obj.attr("title")+"을(를) 입력하십시오.");
				 
		 	}else if(obj.prop("tagName") == "SELECT"){
				 alert(obj.attr("title")+"을(를) 선택하십시오.");
		 	}
		 	return false;	 
		 }
	 }
	 return true;
}


/** 세션 유효 체크 */
com.fnSessChk = function(){	
	var sessChk = true;
	$.ajax({
   		url : "/index/sessChk.do"
  			, dataType : "json"
  			, contentType : "application/json; charset=UTF-8"
  	    	, type : "POST"
  	    	, async : false
   			, success : function(resData){
	   			if(resData.resultCode == 1){
	   				alert("세션이 만료되었습니다.");
	   				sessChk = false;
	   			}else{
	   				sessChk = true;
	   			}
   			}
	   		, error : function(e){
	   			alert("오류가 발생하였습니다.");
	   			return false;
	   		}
   	});
  
  	return sessChk; 	
};






//dialog 영역
//일반 팝업창
com.popupOpen = function(popupNm, href){
	var option = "width=570,height=420, scrollbars=yes, resizable=yes";
	var popup = window.open(href, popupNm, option);
	popup.focus();
	
	return false;
}

//커스텀 팝업창
com.dialpopupOpen = function(titleNm, urlAddr, widthVal, heightVal) {
	
	$("#dialog").dialog({title: titleNm, resizable: true, width: widthVal, height: heightVal, modal: true, beforeClose: function(event, ui) {$("#popForm").attr("src", "")} }).focus();
	
	$("#popForm").attr("src", urlAddr);
}

com.dialpopupClose = function () {
	parent.$("#dialog").dialog("close");
}

