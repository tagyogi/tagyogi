
/****************************************************************
 *
 * 파일명 : common.js
 * 설  명 : 공통 자바스크립트
 *
 *    수정일      수정자     Version        Function 명
 * ------------    ---------   -------------  ----------------------------
 * 2022.06.15   BSG       1.0             최초생성
 *
 *
 * **************************************************************/

//공통변수
var patnHan = RegExp(/[ㄱ-ㅎㅏ-ㅣ가-힣]/);
var patnEng = RegExp(/[a-zA-Z]/);
var patnNum = RegExp(/[0-9]/);
var patnHanNum = RegExp(/[ㄱ-ㅎ|가-힣|0-9]/);
var patnEngNum = RegExp(/[a-zA-Z|0-9]/);
var befMenuCd = "";

//세션끊김 방지
document.cookie = "safeCookie1=foo; SameSite=Lax";
document.cookie = "safeCookie2=foo";
document.cookie = "crossCookie=bar; SameSite=None; Secure";

$(function($){
	//$(".contentsArea").append('<div class="loadingImg" style="display:none;"><img src="/images/loading.gif" alt="loading"/></div>');
    //다이얼로그 팝업용
    $(".contentsArea").append('<div id="dialog" style="display:none"><iframe name="popFrm" id="popFrm" style="width: 100%; height: 100%; overflow: auto; border: none;" title="팝업"></iframe></div>');
});

/*
window.onpageshow = function(event) {
    if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
    	hideLoading();
    }
}
*/

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


//엔터키 이벤트 처리
window.onkeydown = function (obj){
	//console.log(obj);
	//console.log(obj.view);
	//console.log(obj.view);
	//window = obj.view;
	//console.log(window);

	if(event.keyCode == 13){
		if(typeof fnSearch == "function"){
			fnSearch();
		}
	}
};

///////////////////////////////////////////////////////////////////////////////////////
//datepicker 설정 시작
var datepicker_ymd_lmt = {
		dateFormat: 'yy-mm-dd',
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		//showMonthAfterYear: true,
		showAnim : "slideDown",
		changeMonth: true, //월변경셀렉트 박스
		//changeYear: true,  //년도변경셀렉트 박스
		yearSuffix: '년',
		showOn: "both",
		buttonImage: "../images/icon-cal2.png",
		buttonImageOnly: true,
		buttonText: "달력",
		regional: 'ko'
	}

var datepicker_ymd = {
	dateFormat: 'yy-mm-dd',
	prevText: '이전 달',
	nextText: '다음 달',
	monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	dayNames: ['일', '월', '화', '수', '목', '금', '토'],
	dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
	dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
	showMonthAfterYear: true,
	showAnim : "slideDown",
	changeMonth: true, //월변경셀렉트 박스
	changeYear: true,  //년도변경셀렉트 박스
	yearSuffix: '년',
	showOn: "both",
	buttonImage: "../images/icon-cal2.png",
	buttonImageOnly: true,
	buttonText: "달력",
	regional: 'ko',
	yearRange: 'c-50:c+10'
}

//datepicker 설정 종료

//공통 함수 시작
var com = new Object();


///////////////////////////////////////////////////////////////////////////////////////////////////////
//인증서!! 인증서!! 인증서 부분
//화면내에 signedCont  서명값 hidden 필요 //$("#signedCont").val(signData); //결과출력
com.fnCertSign = function(obj){
	if(obj.plan == "" || obj.plan == undefined){
		alert("서명값이 존재하지 않습니다.");
		return false;
	}

	idn = obj.idn;
	if(idn == "" || idn == undefined){
		idn = "";
	}

	sessYn = "Y";
	if(obj.loginYn == "Y"){
		sessYn = "N";
	}

	/******임시s - 운영데이터 입력을 위해 임시 통과******/
	/*if(signYn === "Y"){
		var resData = {certMap:{signData:""},resultMsg:"데이터입력위한 인증서 임시통과"}
		com.fnCertSingChkCallBack(resData);
		return;
	}*/
	/******임시e - 운영데이터 입력을 위해 임시 통과******/

	var signData = "";
	unisign.SignDataNonEnveloped( obj.plan, null, idn, function( resultObject )
	{
		//console.log(resultObject);
		signData = resultObject.signedData;
		subjectNm = resultObject.certAttrs.subjectName;
	    serialNumber = resultObject.certAttrs.serialNumber;
	    staDt = com.fnDateConv(resultObject.certAttrs.validateFrom);
	    endDt = com.fnDateConv(resultObject.certAttrs.validateTo);

		$("#signedCont").val(signData); //결과출력
		//document.frm.signedText.value = resultObject.signedData; //결과출력
		if( !resultObject || resultObject.resultCode !=0 )
		{
			alert( resultObject.resultMessage + "\n오류코드 : " + resultObject.resultCode );
			return false;
		}
		//GetRValueFromKey(resultObject.certAttrs.subjectName);

		//R값 생성
		unisign.GetRValueFromKey(resultObject.certAttrs.subjectName, "", function( resultObject ) {

			if( !resultObject || resultObject.resultCode != 0 ){
				alert( resultObject.resultMessage + "\n오류코드 : " + resultObject.resultCode );
				return false;
			}

			var param = {signData:signData, rVal:resultObject.RValue
						, idn:idn, subjectNm: subjectNm, serialNumber: serialNumber
						, staDt: staDt, endDt: endDt, certType: obj.certType

						};
			obj = {url:"/index/certSingChk.do", callBack:com.fnCertSingChkCallBack, param:param, msgYn:"N", sessYn:sessYn, loadYn:"Y" };
			com.fnAjaxDef(obj);

			//obj.callBack(resultObject.RValue);
			//document.getElementById('userR').value=resultObject.RValue;  // R 값
		})
	});
}

//전자서명 검증완료
com.fnCertSingChkCallBack = function(resData){
	if(resData.resultCode == 0){
		fnCertSingCallBack(resData.certMap); //화면호출
	}else{
		alert(resData.resultMsg);
		//alert("임시통과 운영시 제거!!");
		//fnCertSingCallBack(resData.certMap); //화면호출
	}
}


//변수 널처리
com.fnNvlChk = function(val, rep){
	if(val == undefined || val == "null" || val == null || val == ""){
		if(rep != undefined){
			return rep;
		}else{
			return "";
		}
	}else{
		return val;
	}
}


//일반 공통 함수 부분 시작

//숫자만 입력 onkeyup="com.fnOnlyNum(this);
com.fnOnlyNum = function(obj){
	var iKeyCode = event.keyCode;
	if(iKeyCode < 48 || iKeyCode > 57){
		event.returnValue=false;
	}

	//백스페이스, 탭, 방향키 replace 안타도록(입력 포커스 맨뒤로 이동 방지)
	if(iKeyCode == 8 || iKeyCode == 9 || (iKeyCode >= 37 && iKeyCode <= 40)) {
		return;
	}

	var str = obj.value;
	obj.value = obj.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-zA-Z|\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi, "");
}

//소수점 입력이벤트(숫자.)
//ex) maxlength="5" onkeyup="com.fnOnlyDecimal(this);"
com.fnOnlyDecimal = function(obj){
	var value = $(obj).val();
	$(obj).val( value.replace(/[^0-9.]/g, "") );
}


//상세화면 변수값 세팅
com.fnSetKeyVal = function(obj, area){
	//console.log(obj);
	var keyList = Object.getOwnPropertyNames(obj);
	for(var  key in keyList ){
		var k = keyList[key].replace(" ", "");

		if(area == undefined){
			//console.log(k + " / " + obj[k]);
			$("#ele_"+k).text(obj[k]);
			$("#div_"+k).text(obj[k]);
			$("#"+k).val(obj[k]);
		}else{
			$("#"+area+" #ele_"+k).text(obj[k]);
			$("#"+area+" #div_"+k).text(obj[k]);
			$("#"+area+" #"+k).val(obj[k]);
		}

	}
};

//로그아웃
com.fnLogout = function(){
	obj = {url:"/login/logout.do"};
	com.fnAjaxDef(obj);
};


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



//필수항목 체크
com.fnValChk = function(targs){
 	//alert(targs.length);
 	for(var i = 0; i < targs.length; i++ ){
		 if(targs[i] != ""){
			obj = $("#"+targs[i]);
			 //alert(obj.prop("tagName"));
			 if(obj.val() == ""){
				if(obj.prop("tagName") == "INPUT" || obj.prop("tagName") == "TEXTAREA"){
					 alert(obj.attr("title")+"을(를) 입력하십시오.");

			 	}else if(obj.prop("tagName") == "SELECT"){
					 alert(obj.attr("title")+"을(를) 선택하십시오.");
			 	}
			 	return false;
			 }
		 }
	 }
	 return true;
}

//치환처리
com.fnConvertToHtml = function(val){

	val = val.replace(/</g,"&lt;");
	val = val.replace(/>/g,"&gt;");
	val = val.replace(/\"/g,"&quot;");
	val = val.replace(/\'/g,"&#39;;");
	val = val.replace(/\n/g,"<br/>");

	return val;
}


//일반 공통 함수 부분 종료
///////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////
//날짜 관련 함수 부분 시작
//date 날짜 추출
com.fnDateConv = function(dateStr){

	const date = new Date(dateStr);

	var yyyy = date.getFullYear()

	var mm = date.getMonth() + 1;
	if(mm < 10) mm = "0"+mm;

	var dd = date.getDate();
	if(dd < 10) dd = "0"+dd;

	var hh = date.getHours();
	if(hh < 10) hh = "0"+hh;

	var mi = date.getMinutes();
	if(mi < 10) mi = "0"+mi;

	var ss = date.getSeconds();
	if(ss < 10) ss = "0"+ss;

	return yyyy+""+mm+""+dd+""+hh+""+mi+""+ss;

};

//날짜 관련 함수 부분 시작
//특정날짜 이전, 이후 일자 가져오기
com.fnDateGet = function(dt, day){
	if(dt == "") return;

    var baseDt = new Date(dt);	// 기준일
    baseDt.setDate(baseDt.getDate() + day);	// 증감일

    var year = baseDt.getFullYear();
    var month = Number(baseDt.getMonth()+1);
    var day = Number(baseDt.getDate());

    if(month < 10) month = "0"+month;
    if(day < 10) day = "0"+day;

	return year+"-"+month+"-"+day;
};

//날짜 유효한지 체크
com.fnDateValiChk = function(obj){

	dt = obj.value.toString();
    if(dt == "") return;

    var isValid = false;

    var month_day = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    var dateToken = dt.split('-');
    var year = Number(dateToken[0]);
    var month = Number(dateToken[1]);
    var day = Number(dateToken[2]);
    // 날짜가 0이면 false
    if(day == 0){
		isValid = false;
	} else{
	    // 윤년일때
	    if(year % 4 == 0) {
	        if(month == 2) {
	            if(day <= month_day[month-1] + 1) {
	                isValid = true;
	            }
	        } else {
	            if(day <= month_day[month-1]) {
	                isValid = true;
	            }
	        }
	    } else {
	        if(day <= month_day[month-1]) {
	            isValid = true;
	        }
	    }
	}

	if(!isValid){
		alert("날짜값이 유효하지 않습니다.");
		obj.value = "";
	}

	return isValid;

};

//날짜 형식 자동 완성 (onkeyup)
com.fnDateMakeForm = function(obj, type){
	if(type == undefined) type = "-";

	str = obj.value.toString();

    if(str.indexOf("-") > -1) {
        str = str.replace(/\-/g,"");
    }

    if(str.length == 4) {
        obj.value = str.substring(0,4);
    } else if(str.length > 4 && str.length <= 6) {
        obj.value = str.substring(0,4) + type + str.substring(4,str.length);
    } else if(str.length > 6) {
        obj.value = str.substring(0,4) + type + str.substring(4,6) + type + str.substring(6,str.length);
    }
};

//날짜형식변환
com.fnDateFormat = function(val, type){

	if(val == null) return "";

	if(type == undefined) type = "-";

	var str = val.toString();

	result = str.substring(0,4) + type + str.substring(4,6) + type + str.substring(6,8);

	return result;

}

//날짜 시작일 종료일 비교
com.fnDateFromToChk = function(staDt, endDt, chk){
	var fromDt = $("#"+staDt).val();
	var toDt = $("#"+endDt).val();

	if(fromDt == "" || toDt == "") return;

	fromDt = fromDt.replace(/\-/g,"");
	toDt = toDt.replace(/\-/g,"");

	if(chk == "F" && fromDt > toDt){
		alert("시작일은 종료일보다 클 수 없습니다.");
		$("#"+staDt).val("");
	}else if(chk == "T" && fromDt > toDt){
		alert("종료일은 시작일보다 작을 수 없습니다.");
		$("#"+endDt).val("");
	}
};


//날자 일수 계산  (종료, 시작)
com.fnDateTerm = function(dt1, dt2) {
    var strYear1, strMonth1, strDay1;
    var strYear2, strMonth2, strDay2;

    dt1 = dt1.toString().replace(/-/gi,"");
    dt2 = dt2.toString().replace(/-/gi,"");

    strYear1  = dt1.substring(0, 4);
    strMonth1 = dt1.substring(4, 6);
    strDay1   = dt1.substring(6);

    strYear2  = dt2.substring(0, 4);
    strMonth2 = dt2.substring(4, 6);
    strDay2   = dt2.substring(6);

    date1 = new Date();
    date2 = new Date();
    diff  = new Date();

    var date1Temp = new Date(strMonth1 + "/" + strDay1 + "/" + strYear1 + " " + "10:00:00");
    var date2Temp = new Date(strMonth2 + "/" + strDay2 + "/" + strYear2 + " " + "10:00:00");

    date1.setTime(date1Temp.getTime());
    date2.setTime(date2Temp.getTime());

    diff.setTime(Math.abs(date1.getTime() - date2.getTime()));

    timediff = diff.getTime();

    weeks = Math.floor(timediff / (1000 * 60 * 60 * 24 * 7));
    timediff -= weeks * (1000 * 60 * 60 * 24 * 7);

    days = Math.floor(timediff / (1000 * 60 * 60 * 24));
    timediff -= days * (1000 * 60 * 60 * 24);

    hours = Math.floor(timediff / (1000 * 60 * 60));
    timediff -= hours * (1000 * 60 * 60);

    mins = Math.floor(timediff / (1000 * 60));
    timediff -= mins * (1000 * 60);

    secs = Math.floor(timediff / 1000);
    timediff -= secs * 1000;

    var diffCount = (parseInt(weeks) * 7) + parseInt(days);
    return (parseInt(diffCount) + 1);
}


//날짜 기간 검색 활성화 체크
com.fnDateCalVisible = function(divId){
	if(divId.is(":visible") == true) divId.hide();
	else divId.show();
}


//날짜 관련 함수 부분 종료
///////////////////////////////////////////////////////////////////////////////////////////////////////
//파일다운로드
com.fnFileDown = function(fileInfo){
	document.forms[0].action = "/file/fileDown.do?file="+fileInfo;
	document.forms[0].target = "_blank";
	document.forms[0].submit();
}

//파일다운로드(홈페이지 게시판, 서식자료 - 세션 필요없음 )
com.fnFileDownBrd = function(fileInfo, frm){
//   document.saveFile[0].reset();
   document.saveFile.action = "/file/fileDownBrd.do?file="+fileInfo;
   document.saveFile.target = "_blank";
   document.saveFile.submit();
}

//url : 호출 추소, param : json 변수, callBack : 콜백 함수, msgYn: 메시지 처리 여부, sessYn : 세션 체크 여부, obj.loadYn:로딩 여부
com.fnAjaxDef = function(obj){

	//세션 체크
	if(obj.sessYn == "Y"){
		if(!com.fnSessChk()){
			top.location.href = "/";
			return;
		}
	};

	if(obj.param == undefined) param = {};
	if(obj.asyncYn == undefined) obj.asyncYn = true;

	if(obj.loadYn != "N") $("#ele_loading").css("display","block"); //로딩바
	//console.log(obj.url);

	$.ajax({
   		url : obj.url
  			, type : "POST"
   			, dataType : "json"
  			, async : obj.asyncYn
  			, contentType : "application/json; charset=UTF-8"
  	    	, data : JSON.stringify(obj.param)
  			, success : function(resData){
				if(obj.loadYn != "N") $("#ele_loading").css("display","none"); //로딩바

	   			//결과메세지
	   			if(resData.resultMsg != undefined && (obj.msgYn == undefined || obj.msgYn =="Y"))
		   			alert(resData.resultMsg);


	   			//콜백 함수
   				if(typeof obj.callBack != undefined && typeof obj.callBack == "function"){
					obj.callBack(resData);
	   			}

   				if(resData.moveUrl != undefined ){
					location.href = resData.moveUrl;
				}
   			}
	   		, error : function(e){
	   			alert("오류가 발생하였습니다.(시스템 오류)");

	   			if(obj.loadYn != "N") $("#ele_loading").css("display","none"); //로딩바
	   		}
   	});
};

//ajax post 호출
//url : 호출 추소, formId : form ID, callBack : 콜백 함수, msgYn: 메시지 처리 여부, sessYn : 세셭 체크 여부
com.fnAjaxPost = function(obj){

	//세션 체크
	if(obj.sessYn == "Y"){
		if(!com.fnSessChk()){
			top.location.href = "/";
			return;
		}
	};

	if(obj.loadYn != "N") $("#ele_loading").css("display","block"); //로딩바

	//토큰추가
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	//var formData = new FormData($("#"+formId)[0]);
	var param = JSON.stringify($("#"+obj.formId).serializeObject());
	$.ajax({
   		url : obj.url
		, data : param
		, type : "post"
		, contentType : "application/json; charset=UTF-8"
		/*, beforeSend : function(xhr)
            {
				xhr.setRequestHeader(header, token);
            }*/
		, success : function(resData){
   			if(obj.loadYn != "N") $("#ele_loading").css("display","none"); //로딩바

   			//결과메세지
   			if(resData.resultMsg != undefined && (obj.msgYn == undefined || obj.msgYn =="Y"))
   				alert(resData.resultMsg);

   			//콜백 함수
   			if(typeof obj.callBack != undefined && typeof obj.callBack == "function")
   				obj.callBack(resData);

		}
   		, error : function(e){
   			alert("오류가 발생하였습니다.(시스템 오류)");
   			//console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

   			if(obj.loadYn != "N") $("#ele_loading").css("display","none"); //로딩바
   		}
   	});
};

//ajax multipart/form-data (첨부파일)
//url : 호출 추소, formId : form ID, callBack : 콜백 함수, msgYn: 메시지 처리 여부, sessYn : 세셭 체크 여부
//com.fnAjaxMulti = function(url, formId, fileId, callBack, msgYn, sessYn){
// * 화면에서 json 방식으로 변수 넘길 경우 formId 는  설정 하지말고 jparam 값으로 설정 하여 넘김
com.fnAjaxMulti = function(obj){

	//세션 체크
	if(obj.sessYn == "Y"){
		if(!com.fnSessChk()){
			top.location.href = "/";
			return;
		}
	};

	var formData = new FormData();
	//파일 추가
	if(obj.fileId != undefined){
		var fileIds = obj.fileId.split(",");
		for(var x = 0; x < fileIds.length; x++ ){
			$("input[id^='" + fileIds[x] + "']").each(function(idx){
				files = this.files;
				for(var i = 0; i < this.files.length; i++ ){
					console.log(this.files[i]);
					formData.append('file', this.files[i]);
				}
			});
		}
	}

	//변수 추가
	if(obj.formId != undefined){
		formData.append('param', new Blob([ JSON.stringify($("#" + obj.formId).serializeObject()) ], {type : "application/json"}));
	}

	if(obj.jParam != undefined){
		formData.append('param', new Blob([ JSON.stringify(obj.jParam) ], {type : "application/json"}));
	}

	//console.log(formData);
	if(obj.loadYn != "N") $("#ele_loading").css("display","block"); //로딩바

	$.ajax({
   		url : obj.url
		, data : formData
		, type : "post"
		, contentType : false
		, processData : false
		, enctype : "multipart/form-data"
		, success : function(resData){
   			if(obj.loadYn != "N") $("#ele_loading").css("display","none"); //로딩바

   			//결과메세지
   			if(resData.resultMsg != undefined && (obj.msgYn == undefined || obj.msgYn =="Y"))
   				alert(resData.resultMsg);

   			//콜백 함수
   			if(typeof obj.callBack != undefined && typeof obj.callBack == "function")
   				obj.callBack(resData);

		}
   		, error : function(e){
   			alert("오류가 발생하였습니다.(시스템 오류)");
   			//console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

   			if(obj.loadYn != "N") $("#ele_loading").css("display","none"); //로딩바
   		}
   	});
};


//HTML 가져오기
com.fnAjaxHtml = function(obj){

	//세션 체크
	if(obj.sessYn == "Y"){
		if(!com.fnSessChk()){
			top.location.href = "/";
			return;
		}
	};

	if(obj.param == undefined) param = {};
	if(obj.asyncYn == undefined) obj.asyncYn = true;

	if(obj.loadYn != "N") $("#ele_loading").css("display","block"); //로딩바
	//console.log(obj.url);

	$.ajax({
   		url : obj.url
  			, type : "POST"
   			, dataType : "html"
  			, async : obj.asyncYn
  			, contentType : "application/json; charset=UTF-8"
  	    	, data : JSON.stringify(obj.param)
  			, success : function(resData){
				if(obj.loadYn != "N") $("#ele_loading").css("display","none"); //로딩바

	   			//결과메세지
	   			if(resData.resultMsg != undefined && (obj.msgYn == undefined || obj.msgYn =="Y"))
		   			alert(resData.resultMsg);


	   			//콜백 함수
   				if(typeof obj.callBack != undefined && typeof obj.callBack == "function"){
					obj.callBack(resData);
	   			}

   				if(resData.moveUrl != undefined ){
					location.href = resData.moveUrl;
				}
   			}
	   		, error : function(e){
	   			alert("오류가 발생하였습니다.(시스템 오류)");

	   			if(obj.loadYn != "N") $("#ele_loading").css("display","none"); //로딩바
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

//dialog 영역
//일반 팝업창
com.popupOpen = function(href, popupNm, options, frm){
	if(options == undefined){
		options = "width=570,height=420, scrollbars=yes, resizable=yes";
	}

	if(frm == undefined){
		var popup = window.open(href, popupNm, options);
		popup.focus();
	}else{
		window.open("", popupNm, options);

		frm.action = href;
		frm.target = popupNm;
		frm.submit();
	}
}

//커스텀 팝업창
com.dialpopupOpen = function(titleNm, urlAddr, widthVal, heightVal) {
	$("#dialog").dialog({title: titleNm, resizable: true, width: widthVal, height: heightVal, modal: true, beforeClose: function(event, ui) {$("#popFrm").attr("src", "")} }).focus();
	$("#popFrm").attr("src", urlAddr);
}

com.dialpopupClose = function () {
	$("#dialog").dialog("close");
}

//fromTo 달력
com.fnDateFromTo = function(divId, fromId, toId, callBackFunc){

	var date_properti = {
			dateFormat: 'yy-mm-dd'
			, prevText: '이전 달'
			, nextText: '다음 달'
			, monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
			, monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
			, dayNames: ['일', '월', '화', '수', '목', '금', '토']
			, dayNamesShort: ['일', '월', '화', '수', '목', '금', '토']
			, dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
			, showMonthAfterYear: true
			, changeMonth: true //월변경셀렉트 박스
			, changeYear: true  //년도변경셀렉트 박스
			, yearSuffix: '년'
			, regional: 'ko'
			, yearRange: 'c-50:c+10'

	}

	//div 영역 만들기
	$("#"+divId).html("<div id='cal_"+fromId+"' style='float:left'></div><div id='cal_"+toId+"' style='float:right;'></div>");

	var datepicker = date_properti;
	//from설정
	datepicker.altField = $("#"+fromId);
	datepicker.onSelect = function(selectDate)
						{
							$("#cal_"+toId).datepicker('option', {  minDate: selectDate,
					            beforeShow : function () {
					            	$("#cal_"+toId).datepicker( "option", "minDate", selectDate );
					                setSdate = selectDate;
					        }});

							//콜백 함수 호출
        					if(callBackFunc != undefined && typeof callBackFunc == "function") callBackFunc();
						};

	$("#cal_"+fromId).datepicker(datepicker);
	//console.log(datepicker)

	//to설정
	var datepicker = date_properti;

	datepicker.altField = $("#"+toId);
	datepicker.onSelect = function(selectDate)
	{
		$("#cal_"+toId).datepicker('option', {
            minDate: selectDate,
            beforeShow : function () {
            	$("#cal_"+fromId).datepicker( "option", "maxDate", selectDate );
                setSdate = selectDate;
                //console.log(setSdate)
        }});

        //콜백 함수 호출
        if(callBackFunc != undefined && typeof callBackFunc == "function") callBackFunc();

		$("#"+divId).css("display", "none");
	};
	//console.log(datepicker)
	$("#cal_"+toId).datepicker(datepicker);

}


/****************************************************************
 * 화면 공통코드 input 타입 동적 생성
 * **************************************************************/

//fnCodeSelectBox("CRD001", targetDiv, paramId, choiceType, value );
//그룹코드값, select name 명, (미사용:0 선택:1, 전체:2, 직접입력:3), select 변수값)
com.fnCodeSelectBox = function(cdType, paramId, choiceType, value, ref1, ref2, ref3){

	//기존 옵션 삭제
	$("#"+paramId +" option").remove();

	//셀렉트 유형
	if(choiceType == 1) $("#"+paramId).append("<option value=''>선택</option>");
	if(choiceType == 2) $("#"+paramId).append("<option value=''>전체</option>");
	if(choiceType == 3) $("#"+paramId).append("<option value=''>직접입력</option>");

	$.each(codeList, function(idx, item){
		if(item.cdType == cdType){
			refChk = true;

			if(ref1 != undefined && ref1 != ""){
				if(ref1 != item.rfrnVal1) refChk = false;
			}

			if(ref2 != undefined && ref2 != ""){
				if(ref2 != item.rfrnVal2) refChk = false;
			}

			if(ref3 != undefined && ref3 != ""){
				if(ref3 != item.rfrnVal3) refChk = false;
			}

			if(refChk) $("#"+paramId).append("<option value='"+item.cd+"'>"+item.cdNm+"</option>");

		}
	});

	//변수값 선택
	if(value != undefined && value != ""){
		$("#"+paramId+ " > option[value="+value+"]").attr("selected", "selected");
	}
}

//fnCodeRadio("CRD001", divId, nameId,  value );
com.fnCodeRadio = function(cdType, divId, nameId, brYn, value, ref1, ref2, ref3){
	//기존 옵션 삭제
	$("#"+divId).empty();

	var setHtml = "";
	$.each(codeList, function(idx, item){

		if(item.cdType == cdType){
			//console.log(item);
			refChk = true;

			if(ref1 != undefined && ref1 != ""){
				if(ref1 != item.rfrnVal1) refChk = false;
			}

			if(ref2 != undefined && ref2 != ""){
				if(ref2 != item.rfrnVal2) refChk = false;
			}

			if(ref3 != undefined && ref3 != ""){
				if(ref3 != item.rfrnVal3) refChk = false;
			}
			//console.log("<input type='radio' name='"+nameId+"' id='"+nameId+"' value='"+item.cdType+"'/>&nbsp;"+item.cdNm);
			//console.log($("#"+divId).html());
			if(refChk){
				setHtml += "<input type='radio' name='"+nameId+"' value='"+item.cd+"'/>&nbsp;&nbsp;"+item.cdNm+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if(brYn) setHtml += "</br>";
			}

		}
	});
	$("#"+divId).html(setHtml);

	//변수값 선택
	if(value != undefined && value != ""){
		//$("#"+divId+ " > option[value="+value+"]").attr("checked", "checked");
		$("input[name='"+nameId+"']").each(function() {
		    if($(this).val() == value){
		    	$(this).prop('checked', true);
		    }
		});
	}
}

//fnCodeCheckBox("CRD001", divId, nameId,  value );
com.fnCodeCheckBox = function(cdType, divId, nameId, brYn, value, ref1, ref2, ref3){

	//기존 옵션 삭제
	//$("#"+divId).remove();

	var setHtml = "";
	$.each(codeList, function(idx, item){

		if(item.cdType == cdType){
			//console.log(item);
			refChk = true;

			if(ref1 != undefined && ref1 != ""){
				if(ref1 != item.rfrnVal1) refChk = false;
			}

			if(ref2 != undefined && ref2 != ""){
				if(ref2 != item.rfrnVal2) refChk = false;
			}

			if(ref3 != undefined && ref3 != ""){
				if(ref3 != item.rfrnVal3) refChk = false;
			}

			if(refChk) setHtml += "<input type='checkbox' name='"+nameId+"' value='"+item.cd+"' data-text='"+item.cdNm+"'/>&nbsp;"+item.cdNm+"&nbsp;&nbsp;";
			if(brYn) setHtml += "</br>";
		}
	});
	$("#"+divId).html(setHtml);

	//변수값 선택
	if(value != undefined && value != ""){
		//$("#"+divId+ " > option[value="+value+"]").attr("checked", "checked");
		$("input[name='"+nameId+"']").each(function() {
		    if("all" == value){
		    	$(this).prop('checked', true);
		    }else if($(this).val() == value){
				$(this).prop('checked', true);
			}
		});
	}
}


//input textBox에 사용될 코드 생성  (코드키, 검색코드)
com.fnInputText = function(cdType, val){
	var items = {};
	var text = "";

	$.each(codeList, function(idx, item){
		if( item.cdType == cdType ){
			if( item.cd == val ) {
				text = item.cdNm;
			}
		}
	});

	if( text == "" ) {
		text = "코드없음("+val+")";
	}

	return text;
}

//체크박스 value 체크 (쉼표 구분 string(cds) 데이터 체크)
com.checkBoxCheckValue = function(cds, inputNm){
	if(cds){
		var cdList = [];
		if(cds.indexOf(',') != -1)	cdList = [...cds.split(',')];
		else						cdList.push(cds);
		$.each(cdList, function(idx, item){
			$("input[name="+inputNm+"][value="+item+"]").prop("checked", true);
		});
	}
}

//ibsheet 동적 선택 박스
com.fnIbSelectBox = function(cdType, choiceType, value, ref1, ref2, ref3){

	var result = [];

	var enumNm = "";
	var enumKeys = "";

	//셀렉트 유형
	if(choiceType == 1){
		enumNm = "|선택";
		enumKeys = "|";
	}

	if(choiceType == 2)	enumNm = "|전체";

	$.each(codeList, function(idx, item){
		if(item.cdType == cdType){
			refChk = true;

			if(ref1 != undefined && ref1 != ""){
				if(ref1 != item.rfrnVal1) refChk = false;
			}

			if(ref2 != undefined && ref2 != ""){
				if(ref2 != item.rfrnVal2) refChk = false;
			}

			if(ref3 != undefined && ref3 != ""){
				if(ref3 != item.rfrnVal3) refChk = false;
			}

			if(refChk){
				enumNm += "|" + item.cdNm;
				enumKeys += "|" + item.cd;
			}

		}
	});

	result.enumNm = enumNm;
	result.enumKeys = enumKeys;

	return result;
}

//알림톡 신청자 selectbox 세팅(리스트가져오기) & 일반신청자목록 세팅
/*
* setParam 객체에 setTalk Y인 경우는 알림톡전용으로, 이외는 기본 신청자UI전용으로 세팅
*/
com.fnSetPersList = function(setParam = undefined){
	if (typeof setParam !== 'object' && typeof setParam === 'string') { //파라미터가 객체가 아닌경우 ==> 초기세팅(id만))
		$("#"+setParam).append("<option value=''>선택</option>");
	}else {
		//param = {paramId:paramId, choiceType: choiceType, value: value, sMembNo: $("#"+target).val(), sUseYn: 'Y', asyncYn: false};
		param = setParam;
		param.asyncYn = false;
		obj = {url:"/memb/selectTalkPersList.do", param:param, callBack:com.fnSetPerSelectBox, loadYn:"Y", msgYn:"N", asyncYn:false};
		com.fnAjaxDef(obj);
	}

}

//알림톡 신청자 selectbox ui세팅
com.fnSetPerSelectBox = function(resData){
	
	if (typeof resData !== 'object' && typeof resData === 'string') { //파라미터가 객체가 아닌경우 ==> 초기세팅(id만))
		$("#"+talkpers).append("<option value=''>직접입력</option>");
	}else {
		
		//결과리턴값 추출
		var persList = resData.data;
		var value = resData.paramMap.setVal;
		var talkYn = resData.paramMap.setTalk;
		
		//var setBox = resData.paramMap.setBox;  /*넘어온 신청자 selectbox id 값*/
		//var setNm  = resData.paramMap.setNm;   /*넘어온 신청자 인풋 id 값*/
		//var setTel = resData.paramMap.setTel;  /*넘어온 신청자연락처 인풋 id 값*/
		
		//타겟세팅 (신청자 selectbox, 신청자명, 연락처)
		var targetBox = talkYn === "Y" ? "talkpers" : "SelPers";
		var targetNm  = talkYn === "Y" ? "talkNm"   : "reqNm";
		var targetTel = talkYn === "Y" ? "talkTel"  : "reqTel";
		
		//초기화 
		$("#"+targetNm).val();
		$("#"+targetTel).val();
		
		//selectbox세팅
		var tag = '<option value="">직접입력</option>';
		$.each(persList, function(idx, item){
			tag += '<option value="'+item.reqNm+'" data-tel="'+item.reqTel+'" data-seq="'+item.reqSeq+'" data-type="'+item.reqType+'" >'+ item.reqTypeNm + item.reqNm + '</option>';
		});
		$("#"+targetBox).html(tag);
		
		//클릭 이벤트 추가
		$("#"+targetBox).on("change", function() {
			//빈값(직접입력)이면 readonly 처리
			var showYn = true;
			if($(this).val() === "") {
				showYn = false;
			}
			$("#"+targetNm).prop("readonly", showYn);	//신청자명
			//$("#talkTel").prop("readonly", showYn);	//신청자연락처
	
			//신청자명, 신청자연락처 값 추가
			$("#"+targetNm).val($(this).val());			//신청자명
			$("#"+targetTel).val($(this).find("option:selected").data("tel"));		//신청자연락처
		});
	
		//변수값 선택
		if(value !== undefined && value !== ""){
			$("#"+targetBox).find(" > option[value='"+value+"']").attr("selected", "selected");
		}
	}
}

// ##########################################################################################################################################
// 동적 화면 처리 부분 끝
// ##########################################################################################################################################


//동적 파일 설정
com.fnFileSetInit = function(){
	var fileDocInit = "";
	fileDocInit += '<colgroup>';
	fileDocInit += '	<col style="width:50px;">';
	fileDocInit += '	<col style="width:300px;">';
	fileDocInit += '	<col style="width:300px;">';
	fileDocInit += '</colgroup>';
	fileDocInit += '<tbody>';
	fileDocInit += '	<tr>';
	fileDocInit += '		<th class="tac">순번</th>';
	fileDocInit += '		<th class="tac">서식명</th>';
	fileDocInit += '		<th class="tac">첨부파일</th>';
	fileDocInit += '	</tr>';
	fileDocInit += '</tbody>';

	return fileDocInit;
};

//동적 파일 설정
com.fnFileSet = function(cdType, tblId){
	var fileIdx= 0;
	$("#"+tblId).empty();
	var initHtml = com.fnFileSetInit; //초기설정

	$("#" + tblId).append(initHtml);
	$.each(codeList, function(idx, item){
		if(item.cdType == cdType){
			var html = '<tr>';
				html += '<td class="alignC">' + Number(idx + 1) + '</td>';
				html += '<td class="alignC">' + item.cdNm + '<input type="hidden" name="docCd" value="'+item.cd+'" /><input type="hidden" name="docSeq" id="docSeq'+item.cd+'"/></td>';
				html += '<td><div  id="docFile'+item.cd+'"></div></td>';
				html += '</tr>';
			$("#" + tblId + " > tbody:last").append(html);
			com.fnIbsUploadOne($("#docFile"+item.cd), fileIdx); //파일첨부
			fileIdx++;
		}
	});


}

//동적 파일 설정(조회용)
com.fnFileViewSet = function(cdType, tblId){

	$("#"+tblId).empty();

	var initHtml = com.fnFileSetInit;

	$("#" + tblId).append(initHtml);
	$.each(codeList, function(idx, item){
		if(item.cdType == cdType){
			var html = '<tr>';
				html += '<td class="tac">' + item.cdNm + '</td>';
				html += '<td style="text-align:center"><div id="docSubmitYn'+item.cd+'"></div></td>'; //제출여부
				html += '<td><div id="docFile'+item.cd+'"></div></td>';
				html += '<td style="text-align:center"><div id="docSubmitDt'+item.cd+'"></div></td>';
				html += '<td><div id="docRemark'+item.cd+'"></div></td>';
				html += '</tr>';
			$("#" + tblId + " > tbody:last").append(html);
			com.fnIbsUploadOne($("#docFile"+item.cd)); //파일첨부
		}
	});
}

//조합계좌설정
com.fnSetBankAccList = function (resData, selId, choiceType){
	//console.log(resData);

	//기존 옵션 삭제
	$("#"+selId +" option").remove();

	//셀렉트 유형
	if(choiceType == 1) $("#"+selId).append("<option value=''>선택</option>");
	if(choiceType == 2) $("#"+selId).append("<option value=''>전체</option>");

	$.each(resData, function(idx, item){
		$("#"+selId).append("<option value='"+item.bankCd+","+item.acctNo+"'>"+item.bankNm+"(" + item.bankType + ")</option>");
	});

	//변수값 선택
	if(value != undefined && value != ""){
		$("#"+selId+ " > option[value="+value+"]").attr("selected", "selected");
	}
}

//결제은행설정
com.fnBankChange = function(bankStr) {
    if(bankStr == "") {
        $("#payBank").val("");
        $("#payAcct").val("");
    } else {
        $("#payBank").val(bankStr.split(",")[0]);
        $("#payAcct").val(bankStr.split(",")[1]);
    }
}

///////////////////////////////////////////////////////////////////////////////////////
//엑셀다운로드 시작 (exceljs.min.js) -- 사용안함
com.fnExcelToJs = function(sheet, cols, excelNm){

	$("#ele_loading").css({"display":""}); //로딩바
	$("#ele_exceDown").html("");
	$("#btn_excel").prop("disabled", true);

	var headers = cols;

	var rows = sheet.getDataRows();
	//var head = sheet.getHeaderRows();

	var body = "";
	body +="<table border='1' id='tbl_excelList'>";
	body +=" <thead>";
	body +=" <tr>";

	//헤더부
	for(var i = 0; i < headers.length; i++){
		body +=" <th style='width:" + Number(headers[i].Width) * 10 + "px'>" + headers[i].Header+ "</th>";
	}

	body +=" </tr>";
	body +=" </thead>";
	body +=" <tbody>";
	body +=" <tr>";

	//바디부
	for(var x = 0; x < rows.length; x++){
		for(var k = 0; k < headers.length; k++){
			console.log(headers[k]);
			var rowId = sheet.getRowById(rows[x].id);
			body +=" <td>" + sheet.getString(rowId, headers[k].Name) + "</td>";
		}
		body +=" </tr>";
	}
	body +=" </tbody>";

	var html = "";
		html += "<html xml:lang='en' lang='en' xmlns:x='urn:schemas-microsoft-com:office:excel' >";
		html += " 	<head>";
		html += " 	<meta http-equiv='content-type' content='application/vnd.ms-excel; charset=UTF-8'>";
		html += " 	<xml>";
		html += " 		<x:ExcelWorkbook>";
		html += " 			<x:ExcelWorksheets>";
		html += " 				<x:ExcelWorksheets>";
		html += " 					<x:name>" + excelNm + "</x:name>";
		html += " 					<x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions>";
		html += " 				</x:ExcelWorksheets>";
		html += " 			</x:ExcelWorksheets>";
		html += " 		</x:ExcelWorkbook>";
		html += " 	</xml>";
		html += "	</head>";
		html += "<body>";
		html += body;
		html += "</body>";
		html += "</html>";

	$("#ele_exceDown").html(html); //대상 생성

	const table = document.getElementById("tbl_excelList");

	// 엑셀 워크북을 생성합니다.
	const workbook = new ExcelJS.Workbook();
 	const worksheet = workbook.addWorksheet(excelNm);

	// 테이블 헤더를 추가합니다.
	const headerRow = worksheet.addRow(Array.from(table.querySelectorAll('thead th'), th => th.textContent));

	// 테이블 데이터를 추가합니다.
	Array.from(table.querySelectorAll('tbody tr')).forEach( (tr, trIdx) => {
		const dataRow = worksheet.addRow(Array.from(tr.querySelectorAll('td'), (td, tdIdx) => {
			//console.log(headers[tdIdx].Name);
			if(headers[tdIdx].Name.indexOf("Amt") > -1){
				//console.log(headers[k]);
				return parseInt(com.rmComma(td.textContent));
			}else{
				return td.textContent;	
			}
		}));
  	});
  	
  	//Array.from(table.querySelectorAll('tbody tr')).forEach(tr => {
    //	const dataRow = worksheet.addRow(Array.from(tr.querySelectorAll('td'), td => td.textContent));
  	//});

  	// 열의 너비를 설정합니다.
  	worksheet.columns.forEach((column, idx) => {
		//column.width = headers[idx].MinWidth;
		column.width = Number(headers[idx].MinWidth/5);
  	});

  	// 열의 텍스트 정렬과 색상을 설정합니다.
  	//const row = worksheet.getRow(1);
  	worksheet.getRow(1).font = { bold:true, color: { argb: '00000000' } }; //
  	worksheet.getRow(1).alignment = { horizontal: 'center' }; // Text alignment
  	worksheet.getRow(1).fill = { type:'pattern', pattern:'solid', fgColor: { argb: '00D5D5D5' } }; //

  	for(var x = 0; x < rows.length; x++){

		const row = worksheet.getRow(x+2);

		for(var k = 0; k < headers.length; k++){
			var alig = headers[k].Align;
			row.getCell(k+1).alignment = { horizontal: alig.toLowerCase() }; // Text alignment
		}
	}
	
	



    // Set the value of cell A1
    //const cellA1 = worksheet.getCell('A1');
    //console.log(cellA1);
    //cellA1.fill = { fgColor: { argb: '00000000' } };

    //console.log(cellA1);
    // Set the font color of cell A1 to blue
    //cellA1.cellfont = { color: { argb: 'FF0000FF' } };


	// 엑셀 파일로 저장합니다.
	workbook.xlsx.writeBuffer().then(function(buffer) {
		const blob = new Blob([buffer], { type: 'application/octet-stream' });
    	const fileName = excelNm + '.xlsx';

		if (navigator.msSaveBlob) {
	      // IE에서는 msSaveBlob 메서드를 사용하여 파일을 저장합니다.
	      navigator.msSaveBlob(blob, fileName);
	    } else {
	      // 다른 브라우저에서는 링크를 생성하여 클릭 이벤트를 발생시켜 파일을 다운로드합니다.
	      const link = document.createElement('a');
	      link.href = URL.createObjectURL(blob);
	      link.download = fileName;
	      link.click();
	    }
	});

	$("#ele_exceDown").html("");
	$("#ele_loading").css({"display":"none"}); //로딩바
	$("#btn_excel").prop("disabled", false);
}

///////////////////////////////////////////////////////////////////////////////////////
//엑셀다운로드 시작 (그리드 내용 추출 하여 엑셀 다운로드)
com.fnExcelToGrid = function(sheet, cols, excelNm){

	var getRows = sheet.getDataRows();
	var rows = [];
	var jRows = {};

	for(var i = 0; i < getRows.length; i++){
		var rowId = sheet.getRowById(getRows[i].id);
		for(var x = 0; x < cols.length; x++){
			jRows[cols[x].Name] = sheet.getString(rowId, cols[x].Name);
		}
		rows[i] = jRows;
	}

	var excelParam = {};
	excelParam.rows = rows;
	excelParam.gridCols = cols;
	excelParam.excelNm = excelNm;

	//console.log(excelParam);

	var xmlHttp = new XMLHttpRequest();       // XMLHttpRequest 객체를 생성함.
	xmlHttp.open("POST", "/com/excelDownToGrid.do", true);
	xmlHttp.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
	xmlHttp.responseType = "blob";

	xmlHttp.onload = function() { // onreadystatechange 이벤트 핸들러를 작성함.
		//$("#ele_loading").css({"display":"none"}); //로딩바
	    // 서버상에 문서가 존재하고 요청한 데이터의 처리가 완료되어 응답할 준비가 완료되었을 때
		if(this.status == 200 && this.readyState == this.DONE) {
	    	var disposition = com.fnConvertToHtml(xmlHttp.getResponseHeader('Content-Disposition'));

	    	if (disposition && disposition.indexOf('attachment') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches != null && matches[1])
                    filename = decodeURI( matches[1].replace(/['"]/g, '') );
            }

            var blob = this.response;
            if(window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveBlob(blob, filename);
            }
            else{
                var downloadLink = window.document.createElement('a');
                var contentTypeHeader = xmlHttp.getResponseHeader("Content-Type");
                downloadLink.href = window.URL.createObjectURL(new Blob([blob], { type: contentTypeHeader }));
                downloadLink.download = filename;
                document.body.appendChild(downloadLink);
                downloadLink.click();
                document.body.removeChild(downloadLink);

            }

            $("#ele_loading").css("display","none"); //로딩바
            // 요청한 데이터를 문자열로 반환함.
	    }
	};

	xmlHttp.send(JSON.stringify(excelParam));
	//xmlHttp.send(excelParam);

}

///////////////////////////////////////////////////////////////////////////////////////
//엑셀다운로드 시작 (쿼리 조회하여 엑셀 다운로드)
//param = $("#serchFrm").serializeObject();
//param.gridCols = gridOptList[0].Cols;
//param.excelMth = "selectSampleList";
//param.excelNm = /*[[${menuInfo.menuNm}]]*/;

com.fnExcel = function(param){

	$("#div_loading").css("display","block"); //로딩바

	var xmlHttp = new XMLHttpRequest();       // XMLHttpRequest 객체를 생성함.
	xmlHttp.open("POST", "/com/excelDown.do", true);
	xmlHttp.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
	xmlHttp.responseType = "blob";

	xmlHttp.onload = function() { // onreadystatechange 이벤트 핸들러를 작성함.
		//$("#div_loading").css({"display":"none"}); //로딩바
	    // 서버상에 문서가 존재하고 요청한 데이터의 처리가 완료되어 응답할 준비가 완료되었을 때
		if(this.status == 200 && this.readyState == this.DONE) {
	    	var disposition = com.fnConvertToHtml(xmlHttp.getResponseHeader('Content-Disposition'));

	    	if (disposition && disposition.indexOf('attachment') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches != null && matches[1])
                    filename = decodeURI( matches[1].replace(/['"]/g, '') );
            }

            var blob = this.response;
            if(window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveBlob(blob, filename);
            }
            else{
                var downloadLink = window.document.createElement('a');
                var contentTypeHeader = xmlHttp.getResponseHeader("Content-Type");
                downloadLink.href = window.URL.createObjectURL(new Blob([blob], { type: contentTypeHeader }));
                downloadLink.download = filename;
                document.body.appendChild(downloadLink);
                downloadLink.click();
                document.body.removeChild(downloadLink);

            }

            $("#div_loading").css("display","none"); //로딩바
            // 요청한 데이터를 문자열로 반환함.
	    }
	};

	xmlHttp.send(JSON.stringify(param));

}

//엑셀 다운로드(화면 html 내용 추출 하여 엑셀 다운로드)
com.fnExcelToHtml = function(tbodyId, excelNm){
	$("#ele_loading").show(); //로딩바
	$("#btn_excel").prop("disabled", true);

	//엑셀 header값 html화면의 th값으로 가져옴(hidden 포함)
	var headers = [];
	$("#"+tbodyId).closest('table').find('thead th').each(function(){
		var HeaderNm    = $(this).text();					//th명
		var MinWidthVal = Number($(this).data("width"));	//너비
		headers.push({Header: HeaderNm, MinWidth: MinWidthVal});
	});

	//내용 체크
	if($("#"+tbodyId).find('tr').eq(0).data('nodata') == 'Y'){	//리스트가 없으면
		alert("다운로드 가능한 정보가 없습니다.");

	}else{	//다운로드할 리스트가 있으면

		// 리스트값 그대로 가져옴(hidden 포함)
		var tbody = $("#"+tbodyId).html();

		//임시 html
		var html = "";
			html += "<html xml:lang='en' lang='en' xmlns:x='urn:schemas-microsoft-com:office:excel' >";
			html += " 	<head>";
			html += " 	<meta http-equiv='content-type' content='application/vnd.ms-excel; charset=UTF-8'>";
			html += " 	<xml>";
			html += " 		<x:ExcelWorkbook>";
			html += " 			<x:ExcelWorksheets>";
			html += " 				<x:ExcelWorksheets>";
			html += " 					<x:name>" + excelNm + "</x:name>";
			html += " 					<x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions>";
			html += " 				</x:ExcelWorksheets>";
			html += " 			</x:ExcelWorksheets>";
			html += " 		</x:ExcelWorkbook>";
			html += " 	</xml>";
			html += "	</head>";
			html += "	<body>";
			html +="		<table border='1' id='tbl_excelList'>";
			html +="			<thead>";
			html +="				<tr>";
			//헤더부
			for(var i = 0; i < headers.length; i++){
			html +="					<th>" + headers[i].Header+ "</th>";
			}
			html +="				</tr>";
			html +="			</thead>";
			html += 			tbody;
			html += "	</body>";
			html += "</html>";

		$("#ele_exceDown").html(html); //대상 생성

		const table = document.getElementById("tbl_excelList");

		// 엑셀 워크북을 생성합니다.
		const workbook = new ExcelJS.Workbook();
	 	const worksheet = workbook.addWorksheet(excelNm);

		// 테이블 헤더를 추가합니다.
		const headerRow = worksheet.addRow(Array.from(table.querySelectorAll('thead th'), th => th.textContent));

		// 테이블 데이터를 추가합니다.
	  	//Array.from(table.querySelectorAll('tbody tr')).forEach(tr => {
	    //	const dataRow = worksheet.addRow(Array.from(tr.querySelectorAll('td'), td => td.textContent));
	  	//});
	  	
	  	// 테이블 데이터를 추가합니다.
		Array.from(table.querySelectorAll('tbody tr')).forEach( (tr, trIdx) => {
			const dataRow = worksheet.addRow(Array.from(tr.querySelectorAll('td'), (td, tdIdx) => {
				let val = com.rmComma(td.textContent);
				let chk = RegExp(/^\d+$/); 
				if(chk.test(val)){
				    return parseInt(com.rmComma(td.textContent));
				}else{
				    return td.textContent;	
				}
				
			}));
	  	});
	  	
	   	// 열의 너비를 설정합니다.
	  	worksheet.columns.forEach((column, idx) => {
			//column.width = headers[idx].MinWidth;
			column.width = Number(headers[idx].MinWidth/5);
	  	});

	  	// 열의 텍스트 정렬과 색상을 설정합니다.
	  	//const row = worksheet.getRow(1);
	  	worksheet.getRow(1).font = { bold:true, color: { argb: '00000000' } }; //
	  	worksheet.getRow(1).alignment = { horizontal: 'center' }; // Text alignment
	  	worksheet.getRow(1).fill = { type:'pattern', pattern:'solid', fgColor: { argb: '00D5D5D5' } }; //

		// 열의 내용 정렬 설정합니다.
		$("#"+tbodyId).find('tr').each(function(index, item){
			const row = worksheet.getRow(index+2);
			$(this).find('td').each(function(index, item){
				if($(this).hasClass('alignR')) 		alig = 'right';
				else if($(this).hasClass('alignL')) alig = 'left';
				else								alig = 'center';
				row.getCell(index+1).alignment = { horizontal: alig }; // Text alignment
			});
		});

		// 엑셀 파일로 저장합니다.
		workbook.xlsx.writeBuffer().then(function(buffer) {
			const blob = new Blob([buffer], { type: 'application/octet-stream' });
	    	const fileName = excelNm + '.xlsx';

			if (navigator.msSaveBlob) {
		      // IE에서는 msSaveBlob 메서드를 사용하여 파일을 저장합니다.
		      navigator.msSaveBlob(blob, fileName);
		    } else {
		      // 다른 브라우저에서는 링크를 생성하여 클릭 이벤트를 발생시켜 파일을 다운로드합니다.
		      const link = document.createElement('a');
		      link.href = URL.createObjectURL(blob);
		      link.download = fileName;
		      link.click();
		    }
		});
	}

	$("#ele_exceDown").html("");
	$("#ele_loading").hide(); //로딩바
	$("#btn_excel").prop("disabled", false);
}

 /****************************************************************
 * 공통코드 처리 부분 종료
 * **************************************************************/

/****************************************************************
 * IBSheet 공통 부분 시작
 * ************************************************************* */

//업로드 초기 설정
var ibUploadInit = {
	viewType:"icon"
    , iconMode:"detail" //icon, list, detail
    //, theme :"Main"
    , userInputName : "myUpFil1e"
    , limitFileCount : 50 //개수 제한
    , limitFileSize : 1024 * 1024 * 50 //건당 사이즈 제한
    , limitFileExt : "txt,ppt,pptx,xls,xlsx,doc,docx,hwp,hwpx,zip,7z,gif,jpg,jpeg,png,bmp,TXT,PPT,PPTX,XLS,XLSX,DOC,DOCX,HWP,ZIP,7Z,GIF,JPG,JPEG,PNG,BMP,pdf,PDF,etc,ETC,hml,HML,HWPX"
    , limitFileExtMode : "allow" //해당 확장자만 허용
    , autoUpload:false
    , useDragDrop:true // 파일 드레그 드롭 기능 사용
    , contextMenuItems: { //마우스 우측 이벤트
        "download": {name: "다운로드 (D)", icon: "", accesskey: "d"},
        "sep1": "---------",
        "viewtype": {
            "name": "보기",
            "items": {
                "icon": {"name": "아이콘 (C)", accesskey: "c"},
                "list": {"name": "간단히 (L)", accesskey: "l"},
                "detail": {"name": "자세히 (D)", accesskey: "d"}
            }
        },
        "sep2": "---------",
        "add": {name: "추가 (A)", icon: "", accesskey: "a"},
        "delete": {name: "제거 (R)", icon: "delete", accesskey: "r"}
    }
    , onMessage: function(msgID, msgDesc) {
    	alert(msgDesc);
        //if (msgID == "INFO-041") {
        //   alert(msgDesc);
        //}
        return false;
    }
    , onUploadData: function(serverObject, serverText){ //파일업로드 처리 후 리턴

    	if(serverObject.resultCode == 0){
			if(typeof fnIbsFileUploadCallBack != undefined && typeof fnIbsFileUploadCallBack == "function"){
				fnIbsFileUploadCallBack(serverObject.atthNo);
			}
    	}else{
			alert(serverObject.msg);
		}

    	return serverObject; //반드시 리턴해줘야 함.
    }
    , onDblClick:function(uploadId){
		var id = this.id

    	$("#"+id).IBUpload("download");
    }
    , onContextMenu:function(key){
		//console.log(this.attr("id").split("_"));
		//console.log(this.attr("id"));
		var id = this.attr("id").replace("_fileview", "");

		switch (key) {
            case "icon":
            case "list":
            case "detail":
                $("#"+id).IBUpload("iconMode", key);
                break;
            default:
                if (key == "download") {
                    var fileList = $("#"+id).IBUpload('fileList');

                    for (i = fileList.length - 1; i >= 0; i--) {
						//console.log(fileList[i]);
						if(fileList[i].progress == "대기중"){
							alert("처리되지 않은 첨부파일 입니다. 업로드 후 다운로드 진행 바랍니다.");
							return;
						}
                        if ($("#"+id).IBUpload('selectedIndex', {
                                index: i
                            }) == true && gallery.contextmenu.data.files.filter(function(e) {
                                return e.name == fileList[i].name && e.url == fileList[i].url;
                            }).length > 0) {
                            alert("업로드의 기본 파일들은 단순 예제로 다운로드 되지 않습니다. '파일 추가'로 추가된 파일만 업로드/다운로드 가능합니다.");
                            return;
                        }
                    }
                }
                if (key == "upload") {
                    alert("제품에 대한 단순 예제이기에 서버가 구축되어 있지 않으므로 업로드가 진행되지 않습니다.");
                    return;
                }
                if (key == "delete") {
					com.fnIbsFileDel($("#"+id));
					return;
                }

            	$("#"+id).IBUpload(key);

        }
    }
}

//파일업로드 설정(단건)
com.fnIbsUploadOne = function(divId, fileIdx){

	ibUploadInit.limitFileCount = 1; //개수 제한
	ibUploadInit.iconMode = "list";

	//파일업로드
	divId.IBUpload(ibUploadInit);

}

//파일업로드 설정(다건)
com.fnIbsUpload = function(divId, cnt, exts){

	//건수 임의 설정
	if(cnt == undefined) cnt = 50;

	//확장자 임의 설정
	if(exts != undefined){
		ibUploadInit.limitFileExt = exts;
	}

	ibUploadInit.limitFileCount = cnt; //개수 제한

	//파일업로드
	divId.IBUpload(ibUploadInit);

}


//파일업로드(등록/조회)
com.fnIbsUploadView = function(divId, list){

	ibUploadInit.limitFileCount = 50; //개수 제한

	//파일업로드
	divId.IBUpload(ibUploadInit);

	if(list != undefined){
		var setList = new Array();
		$.each(list, function(i, item){
			//console.log(item);
			var setData = new Object();

			for(var elem in item){
				setData[elem] = item[elem];
			}
			setData.name = this["fileOrgNm"];
			setData.size = this["fileSize"];
			setData.date = this["regDtm"];
			setData.url = this["atthType"]+":"+this["atthNo"]+":"+this["atthSeq"];

			setList.push(setData);

		});

		var setObj = new Object();
		setObj.files = setList;
		divId.IBUpload(setObj);
	}
}

//파일업로드(등록/조회) - 단건
com.fnIbsUploadViewOne = function(list, mod){
	console.log('file list');
	console.log(list);

	$.each(list, function(idx, item){

		//변수선언
		var setData = new Object();
		setData.name = item.fileOrgNm;
		setData.size = item.fileSize;
		setData.date = item.submitDt;
		setData.url = item.atthType +":"+ item.atthNo +":"+ item.atthSeq;

		//리스트 객체에 파일정보 담기
		var setList = new Array();
		setList.push(setData);

		//객체 files 에 파일목록 담기
		var setObj = new Object();
		setObj.files = setList;

		//파일시퀀스 hidden 타입
		$("#docSeq"+item.docCd).val(item.atthSeq);

		//input(수정일때는 value값)
		if(mod == "upt") {
			$("#docSubmitYn"+item.docCd).val(item.submitYn);
			$("#docSubmitDt"+item.docCd).val(item.submitDt);
			$("#docRemark"+item.docCd).val(item.remark);
		} else {
			//파일 정보 설정
			$("#docSubmitYn"+item.docCd).text(item.submitNm);
			$("#docSubmitDt"+item.docCd).text(item.submitDt);
			$("#docRemark"+item.docCd).text(item.remark);
		}

		$("#docFile"+item.docCd).IBUpload(setObj);
	});
}

//파일업로드(등록/조회) - 단건 (아이비업로드 모바일 다운로드 안될시 사용할 샘플)
com.fnFileDownOne = function(list, mod){
	$.each(list, function(idx, item){
		//변수선언
		var setData = new Object();
		setData.name = item.fileOrgNm;
		setData.size = item.fileSize;
		setData.date = item.submitDt;
		setData.url = item.atthType +":"+ item.atthNo +":"+ item.atthSeq;

		//리스트 객체에 파일정보 담기
		var setList = new Array();
		setList.push(setData);

		var html = '<li><a herf="#;" onclick="javascript:fnDownloadFile(\''+ setData.url +'\');">'+setData.name+'</a></li>';
		$("#atthWrap > .file").append(html);

	});
	if($(".file > li").length > 0){
		$("#atthWrap").removeClass('hide');
	}
}


//파일업로드
com.fnIbsFileUpload = function(divId, atthType, atthNo){

	if(atthType == undefined || atthType == "" ){
		alert("파일유형값이 없습니다.");
		return;
	}
	if(atthNo == undefined || atthNo == "" ){
		atthNo = "";
	}

	$("#ele_loading").css("display","block"); //로딩바

	divId.IBUpload("extendParamUpload","atthType="+atthType+"&atthNo="+atthNo);
	divId.IBUpload("upload");
	//"/file/fileUpload.do"
	//업로드 완료 후 onUploadData 호출
}


//단건 첨부파일 업로드시, 첨부파일 서식코드 순서 순차별로 넘기기
//드래그앤드랍으로 첨부할때 첨부한 순서대로 html태그가 생성됨 (ex 두번째 먼저 업로드시 02, 01)
//이 순서를 기준으로, 물리적저장순서와 DB저장 순서를 맞춰야하기때문에 필수적으로 추가해야함
//ibsheet 동적파일 생성부분에 지정한 파일명 넣어주기
//form modFileCds input hidden으로 넣어두기
var modCds;
com.fnGetModCds = function(ids){
	//초기화
	modCds= "";
	$("#modFileCds").val('');

    //코드 순번으로 세팅
    $("input[id^='"+ids+"']").each(function() {
	    var modCd = $(this).attr("id");
	    modCd = modCd.replace(ids, "");
	    modCd = modCd.substring(0,2);

	    if(modCds == "") modCds = modCd;
	    else modCds = modCds + "," + modCd;
	});

	$("#modFileCds").val(modCds);
}


//파일다운로드
com.fnIbsFileDownAll = function(divId){
	divId.IBUpload("selectAll", true); // SET
	divId.IBUpload("download");
}

//파일추가 (id 변수값)
com.fnIbsFileAdd = function(divId){
	var atthType = $("#atthType").val();
	var atthNo = $("#atthNo").val();

	if(atthType == ""){
		alert("첨부파일유형이 정의되지 않았습니다.관리자에게 문의하시기 바랍니다.");
		return;
	}


	divId.IBUpload("add", {
		callback:function(){
			if(atthNo != ""){
				divId.IBUpload("extendParamUpload","atthType="+atthType+"&atthNo="+atthNo);
				divId.IBUpload("upload");
			}
		}
	});
}

//파일삭제 (id 변수값)
com.fnIbsFileDel = function(divId){
	var result = [];
	fileList = divId.IBUpload("fileList"); // GET
	console.log(fileList);
	var delChkYn = false;
	divId.find("> div li").each(function (i, e){
		if($(this).hasClass("ui-selected")){
			delChkYn = true;
			urls = fileList[i].url.split(":");

			//파일내용이 있을 경우 파일 삭제 처리
			if(urls[2] != undefined){
				var initParam = {atthType:urls[0], atthNo:urls[1], atthSeq:urls[2]}
				obj = {url:"/file/fileDelete.do", param:initParam, msgYn:"N", sessYn:"Y", loadYn:"Y"};
				com.fnAjaxDef(obj);
			}
		}
	});

	if(delChkYn){
		divId.IBUpload("delete");
	}else{
		alert("삭제할 파일을 선택 하십시오");
	}
}

/****************************************************************
 * IBSheet 공통 부분 종료
 * **************************************************************/


 /****************************************************************
 * Ckeditor 게시판 호출
 * **************************************************************/
//게시판 업로드 처리
class UploadAdapter {
    constructor(loader) {
        this.loader = loader;
    }

    upload() {
        return this.loader.file.then( file => new Promise(((resolve, reject) => {
            this._initRequest();
            this._initListeners( resolve, reject, file );
            this._sendRequest( file );
        })))
    }

    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();
        xhr.open('POST', '/file/editImgUpload.do', true);
        xhr.responseType = 'json';
    }

    _initListeners(resolve, reject, file) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = '파일을 업로드 할 수 없습니다.'

        xhr.addEventListener('error', () => {reject(genericErrorText)})
        xhr.addEventListener('abort', () => reject())
        xhr.addEventListener('load', () => {
            const response = xhr.response
            if(!response || response.error) {
                return reject( response && response.error ? response.error.message : genericErrorText );
            }

            resolve({
                default: response.url //업로드된 파일 주소
            })
            //alert(response.imgAtthNo);
            $("#imgAtthNo").val(response.imgAtthNo);
        })
    }

    _sendRequest(file) {
        const data = new FormData()
        data.append('upload',file)
        data.append('atthType', $("#atthType").val())
        if($("#imgAtthNo").val() != "") data.append('imgAtthNo', $("#imgAtthNo").val())
        this.xhr.send(data)
    }
}

function MyCustomUploadAdapterPlugin(editor) {
    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        return new UploadAdapter(loader)
    }
};

com.fnCkeditorSet = function(id){
	try{
		ClassicEditor.create( document.querySelector( id ), {
			licenseKey: '',
			extraPlugins: [MyCustomUploadAdapterPlugin],
		} )
		.then( editor => {
			window.editor = editor;
		} )
		.catch( error => {
			console.error( 'Oops, something went wrong!' );
			console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
			console.warn( 'Build id: hyjomnvlk7oy-w28feghlnb76' );
			console.error( error );
		} );

	}catch(e){
		console.log("ckeditor call exception : " + e);
	}
}



/****************************************************************
 * 주소검색 팝업 호출
 * **************************************************************/
com.fnAddrSerch = function(open){
	var url = "/index/popPageCall.do?menuPage=com/serchAddr"; // /com/serchAddr
	if(open == "popup"){
		window.open(url, "주소검색", "width=450,height=480, scrollbars=yes, resizable=yes");
	}else{
		com.dialpopupOpen("주소검색", url, "800", "600");
	}
}

/****************************************************************
 * 포맷관련
 * **************************************************************/
//공백, 널 체크
com.fnIsEmpty = function(value) {
    if (value == "" || value == null || value == undefined || (value != null && typeof value == "object" && !Object.keys(value).length)) {
        return true
    } else {
        return false
    }
};

//콤마
com.makeComma = function(obj){
	var name = obj.name;
  	var num = obj.value.replace(/,/g,'');
  	var $this = $("input[name="+name+"]");
	$this.val(num.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,"));
}

//콤마세팅
com.addComma = function(num){
	if(num == null) return "";
    num = num.toString();
    num = num.replace(/,/g, "");
    var num_str = num.toString();
    var result = '';
    var flag = '';

    if(num_str.indexOf('-') != -1){
      num_str = num_str.replace(/-/g, "");
      flag = '-';
    }

    if(num_str.indexOf('+') != -1){
      num_str = num_str.replace(/\+/g, "");
      flag = '+';
    }

    for(var i = 0; i<num_str.length; i++) {
        var tmp = num_str.length-(i+1);
        if(i%3==0 && i!=0) result = ',' + result;
        result = num_str.charAt(tmp) + result;
    }

    if(flag == '-') result = flag + result;

    return result;
}



//콤마 없애기
com.rmComma =  function(num) {
	console.log(num);
	return num.toString().replace(/,/gi,"");
}

//숫자처리
com.fnToNum = function(str) {
    if(str == "") str = "0";
    return Number(com.rmComma(str));
};


//사업자 번호 (keyup)
com.makeBizNo = function(obj) {
    var str = obj.value;
    if(str.indexOf("-") > -1) 	str = str.replace(/\-/g,"");

    if(str.length > 0 && str.length <= 3) 		obj.value = str;
    else if(str.length > 3 && str.length <= 5) 	obj.value = str.substring(0,3) + "-" + str.substring(3);
    else if(str.length > 5 && str.length <= 10) obj.value = str.substring(0,3) + "-" + str.substring(3,5) + "-" + str.substring(5);
}


//사업자 번호 (keyup)
com.makeBizNoVal = function(val) {
	if(val != "" && val != undefined){
		val = val.substring(0,3) + "-" + val.substring(3,5) + "-" + val.substring(5);

		return val;
	}else{
		return val;
	}

}

//법인(주민) 번호 (keyup)
com.makeJuminNo = function(obj) {
    var str = obj.value;
    if(str.indexOf("-") > -1) 	str = str.replace(/\-/g,"");

    if(str.length > 0 && str.length <= 6) 		obj.value = str;
    else if(str.length > 6 && str.length <= 13) obj.value = str.substring(0,6) + "-" + str.substring(6);
}

//전화 번호 (keyup)
com.makeTelNo = function(obj) {
    var str = obj.value;
    if(str.indexOf("-") > -1) {
        str = str.replace(/\-/g,"");
    }

    var firstNoIdx = 3;
    if(str.length >= 2 && str.substring(0, 2) == "02") firstNoIdx = 2;
    else if(str.length >= 4 && str.substring(0, 4) == "0505") firstNoIdx = 4;

    if(str.length > 0 && str.length <= firstNoIdx) {
        obj.value = str;
    } else if(str.length > firstNoIdx && str.length <= 7) {
        obj.value = str.substring(0,firstNoIdx) + "-" + str.substring(firstNoIdx);
    } else if(str.length > 7 && str.length <= 14) {
        obj.value = str.substring(0,firstNoIdx) + "-" + str.substring(firstNoIdx,str.length - 4) + "-" + str.substring(str.length - 4);
    }
}

/****************************************************************
 * 출자신청 관련
 * **************************************************************/
//액면가조회
com.selectFaceDeal = function(val){
	var asyncYn = true;
	if(val == 'trans' || val == 'sepmer' || val == 'pay'){
		var target = val+"Dt"
		var callFn = com.fnTranFaceDealCallBack;
		asyncYn = false;
	}else if(val == 'dept'){
		var target = val+"Dt"
		var callFn = com.fnDeptFaceDealCallBack;
		asyncYn = false;
	}else{
		var target = "investDt";
		var callFn = com.fnFaceDealCallBack;
	}

	if($('#'+target).val().length != 10){
		$('#faceAmt').val('');
		$('#dealAmt').val('');
		return;
	}
	//console.log($("#investDt").val());
	//sys 좌당지분액 관리소스에서 호출 confInvt/selectConfInvtMaxAppDt   // 검증 후 invt/selectFaceDeal 제거
	obj = {url:"/invtReq/selectConfInvtMaxAppDt.do", param:{appDt: $("#"+target).val()}, callBack:callFn, msgYn:"N", sessYn:"Y", loadYn:"Y", asyncYn: asyncYn};
	com.fnAjaxDef(obj);

}

//액면가조회 콜백
com.fnFaceDealCallBack = function(resData){
	console.log(resData);
	if(resData == '' || resData == null || resData == undefined ) {
		alert('좌당액면가 정보가 존재하지 않습니다.');
		return;
	}

	//$.each(resData.data, function(idx, item){

		var fAmt = resData.data.faceAmt;
		var dAmt = resData.data.dealAmt;

		$('#faceAmt').val(com.addComma(fAmt));
		$('#dealAmt').val(com.addComma(dAmt));

	//});

	com.fnCalcByNum(); //좌수입력시 금액자동계산
}

//좌수입력시 금액자동계산
com.fnCalcByNum = function() {
	if($("#investDt").val() == "") {
		alert("출자일자를 입력(선택)해 주십시오.");
		$("#investNum").val("");
		$("#investDt").focus();
		return;
	}

	//fnInitBill();

	var dealAmt = Number(com.rmComma($("#dealAmt").val()));
	var investNum = Number(com.rmComma($("#investNum").val()));

	$("#investAmt").val(com.addComma(dealAmt * investNum));
}
/****************************************************************
 * 홈페이지 관련
 * **************************************************************/

//오늘날짜(yyyy-mm-dd)
com.getToday = function(){
	var date = new Date();
	return date.getFullYear() + "-" + ("0"+(date.getMonth()+1)).slice(-2) + "-" + ("0"+date.getDate()).slice(-2);
}

//상세페이지호출
com.fnBrdDtlPage = function(brdNo, brdSeq, brdRef){
	var atthType = brdAtthType;

	//main -> 상세
	if(brdNo === 1){//공지사항
		atthType = 'BR01'
	}else if(brdNo === 7){//콘텐츠업계소식
		atthType = 'BR06'
	}else if(brdNo === 8){//조합원Talk
		atthType = 'BR07'
	}

	param = {brdNo:brdNo, brdSeq:brdSeq, brdRef:brdRef, atthType:atthType};
	fnPage("20404", param);
}


//페이지네이션 세팅
com.fnPageInit = function(obj){
//	console.log(obj);
	$('#pagination').empty();

	//이전 페이지 리스트 이동
	if(obj.startPage > 1 ){
		var prePage = obj.startPage - obj.pageCount;
		pageInfo  = "<li><a href='javascript:void(0);' class='pre-page' onClick='fnChPage(" + prePage + ");' title='이전페이지리스트이동'></a></li>";
	}else {
		pageInfo  = "<li><a href='javascript:void(0);' class='pre-page disabled' onClick='' title='이전페이지리스트없음'></a></li>";
	}

	//이전 페이지 이동(단건)
	if(obj.curPage > 1){
		pageInfo  += "<li><a href='javascript:void(0);' class='pre-num' onClick='fnChPage(" + (obj.curPage - 1) + ");' title='이전페이지이동'></a></li>";
	}else {
		pageInfo  += "<li><a href='javascript:void(0);' class='pre-num disabled' onClick=''title='이전페이지없음'></a></li>";
	}

	//페이지 뿌리기
	for(var i=obj.startPage; i<=obj.endPage; i++){
		if(i == obj.curPage){
			pageInfo += "<li><a href='javascript:void(0)' class='page-link on' onClick='fnChPage(" + i + ");' >" +  i + "</a><li>";
		}else{
			pageInfo += "<li><a href='javascript:void(0)' class='page-link' onClick='fnChPage(" + i + ");'>" +  i + "</a><li>";
		}
	}

	//다음 페이지 리스트 이동
	if(obj.curPage < obj.totalPage ){
		pageInfo  += "<li><a href='javascript:void(0);' class='next-num' onClick='fnChPage(" + (obj.curPage + 1) + ");' title='다음페이지리스트이동'></a></li>";
	}else {
		pageInfo  += "<li><a href='javascript:void(0);' class='next-num' disabled' onClick='' title='다음페이지리스트없음'></a></li>";
	}

	//다음 페이지 이동(단건)
	if(obj.endPage != obj.totalPage){
		pageInfo  += "<li><a href='javascript:void(0);' class='next-page' onClick='fnChPage(" + (obj.startPage + obj.pageCount ) + ");' title='다음페이지이동'></a></li>";
	}else {
		pageInfo  += "<li><a href='javascript:void(0);' class='next-page disabled' onClick='' title='다음페이지없음'></a></li>";
	}


	$("#pagination").append(pageInfo);
	$('.total-num').text(obj.totalCount);
}

//selectBrdList
//게시판 리스트 호출
com.fnBrdPage = function(){
	param = {};
	obj = {url:"/brd/selectBrdList.do", param:param, formId:"serch", callBack:com.fnBrdPagecallback, msgYn:"N", sessYn:"N", loadYn:"N"};
	com.fnAjaxPost(obj);
}
//검색
com.fnSearchBrd = function(){
	$("#curPage").val('');
	param = {};
	obj = {url:"/brd/selectBrdList.do", param:param, formId:"serch", callBack:com.fnBrdPagecallback, msgYn:"N", sessYn:"N", loadYn:"N"};
	com.fnAjaxPost(obj);
}

//selectBrdList return함수
//게시판 리스트 세팅

com.fnBrdPagecallback = function(resData){
	//console.log(resData);
	//console.log("게시판리스트세팅 brdNo" + resData.brdNo);

	//table 내용 추가
	var data = resData.tableInfo;
	var brdNo = resData.brdNo;

	$("#tableInfo").empty();

	//#tableInfo : 번호, 제목, 첨부, 작성자, 등록일, 조회수
	//공지사항, 콘텐츠업계소식, 조합원Talk, 경영공시
	if (brdNo === 1 || brdNo === 2 || brdNo === 7 || brdNo === 8) {

		if( String(data) === ''){
			var html = '<tr><td colspan="8">게시물이 존재하지 않습니다.</td><tr>';
			$('.total-num').text(0);
			$("#tableInfo").append(html);
		}


		$.each(data, function(index, item) {
			var html  = '<tr>';
				html += '<td>'+ item.rn2+ '</td>';
				if(item.notiYn === 'Y' || item.topYn === 'Y'){//공지태그추가
	            	html += '<td class="title-align ellipsis"><a href="javascript:void(0);" onClick="com.fnBrdDtlPage('+ item.brdNo+','+item.brdSeq +','+item.menuCd +'); return false;"><span class="notiTag">공지</span> '+ item.brdSubj +'</a></td>';
	            }else{
	            	html += '<td class="title-align ellipsis"><a href="javascript:void(0);" onClick="com.fnBrdDtlPage('+ item.brdNo+','+item.brdSeq +','+item.menuCd +'); return false;"> '+ item.brdSubj +'</a></td>';
				}
	            //html += '<td>'+ item.regNm+'</td>';
	            html += '<td>관리자</td>';
				html += '<td>'+ item.regDtm +'</td>';
				html += '<td>'+ item.viewCnt +'</td>';
				html += '</tr>';

			$("#tableInfo").append(html);
		});
	}

	//자주묻는질문 게시판
	if (brdNo === 5) {

		$("#brdList").empty();

		if( String(data) == ''){
			var html = '<div class="no-brd back-gray">게시물이 존재하지 않습니다.</div>';
			$('.total-num').text(0);
			$("#brdList").append(html);
		}

		$.each(data, function(index, item) {
			var html  = '<ul class="qna-wrap">';
				if(item.notiYn === 'Y' || item.topYn === 'Y'){//공지태그추가
					html += '<li class="qna-title init"><a href="#;" ><span class="notiTag">공지</span>'+ item.brdSubj+ '</a></li>';
				}else{
					html += '<li class="qna-title init"><a href="#;" >'+ item.brdSubj+ '</a></li>';
				}
				html += '<li class="qna-content brdCont' + item.brdSeq + '">'+ item.brdCont +'</li>';
				html += '</ul>';

			$("#brdList").append(html);
		});
	}

	//갤러리 게시판
	if (brdNo === 3) {

		$("#brdList").empty();
		$("#brdList").addClass('photo-wrap');

		if( String(data) === ''){
			var html = '<div class="no-brd back-gray">게시물이 존재하지 않습니다.</div>';
			$('.total-num').text(0);
			$("#brdList").append(html);
		}

		$.each(data, function(index, item) {

			var atthCnt = item.atthCnt;
			//console.log(atthCnt);
			var html  = '<article class="photo">';
				html += '<div class="img-wrap">';
			if(item.atthNo == "" || item.atthNo == undefined || atthCnt < 1){//썸네일확인
	            html += '<a href="javascript:void(0);" onClick="com.fnBrdDtlPage('+ item.brdNo+','+item.brdSeq +','+item.menuCd +'); return false;"><img src="" alt="등록된 썸네일 이미지 없음."></a>';
			}else{
				html += '<a href="javascript:void(0);" onClick="com.fnBrdDtlPage('+ item.brdNo+','+item.brdSeq +','+item.menuCd +'); return false;"><img src="/file/fileImgDown.do?atthType=BR03&atthNo='+item.atthNo+'&atthSeq='+item.minAtthSeq+'" alt="'+ item.fileOrgNm +'"></a>';
			}

			html += '</div>';
			if(item.notiYn === 'Y' || item.topYn === 'Y'){//공지태그추가
				html += '<a href="javascript:void(0);" onClick="com.fnBrdDtlPage('+ item.brdNo+','+item.brdSeq +','+item.menuCd +'); return false;"><p class="ko-bold ellipsis"><span class="notiTag">공지</span>'+ item.brdSubj +'</p></a>';
			}else{
				html += '<a href="javascript:void(0);" onClick="com.fnBrdDtlPage('+ item.brdNo+','+item.brdSeq +','+item.menuCd +'); return false;"><p class="ko-bold ellipsis">'+ item.brdSubj +'</p></a>';
			}
			html += '<span class="ko-medium">'+ item.regDtm +'</span>';
			html += '</article>';

			$("#brdList").append(html);

		});
	}

	//#brd-search : 통합검색
	if ($('#brd-search').length > 0) {
		$("#brd-search").empty();

		if( String(data) === ''){
			var html = '<tr><td colspan="8">게시물이 존재하지 않습니다.</td><tr>';
			$('.total-num').text(0);
			$("#brd-search").append(html);
		}

		$.each(data, function(index, item) {
			var html  = '<tr>';
				html += '<td>'+ item.rn2+ '</td>';
				html += '<td>'+ item.menuNm+ '</td>';
				if(item.notiYn === 'Y' || item.topYn === 'Y'){//공지태그추가
	            	html += '<td class="title-align ellipsis"><a href="javascript:void(0);" onClick="com.fnBrdDtlPage('+ item.brdNo+','+item.brdSeq +','+item.menuCd +'); return false;"><span class="notiTag">공지</span> '+ item.brdSubj +'</a></td>';
				}else{
	            	html += '<td class="title-align ellipsis"><a href="javascript:void(0);" onClick="com.fnBrdDtlPage('+ item.brdNo+','+item.brdSeq +','+item.menuCd +'); return false;"> '+ item.brdSubj +'</a></td>';
				}
				html += '<td>'+ item.regNm+'</td>';
				html += '<td>'+ item.regDtm +'</td>';
				html += '</tr>';

			$("#brd-search").append(html);
		});
	}

	//#qnaInfo : 질문과답변
	if ($('#qnaInfo').length > 0) {

		$("#qnaInfo").empty();

		if( String(data) === ''){
			var html = '<tr><td colspan="8">게시물이 존재하지 않습니다.</td><tr>';
			$('.total-num').text(0);
			$("#qnaInfo").append(html);
		}

		$.each(data, function(index, item) {
			var html  = '<tr>';
				html += '<td>'+ item.rn2+ '</td>';
				if(item.notiYn === 'Y' || item.topYn === 'Y'){//공지태그추가
	            	html += '<td class="title-align ellipsis"><a href="javascript:void(0);" onClick="chkpop('+ item.brdNo+','+item.brdSeq +','+item.menuCd +','+item.regPw +', \'' + item.secrtYn + '\'); return false;"><span class="notiTag">공지</span> '+ item.brdSubj +'</a></td>';
	            }else{
	            	html += '<td class="title-align ellipsis"><a href="javascript:void(0);" onClick="chkpop('+ item.brdNo+','+item.brdSeq +','+item.menuCd +','+item.regPw +', \'' + item.secrtYn + '\'); return false;"> '+ item.brdSubj +'</a></td>';
				}
				html += '<td class="ellipsis-t">'+ item.regNm + '</td>';
				html += '<td>'+ item.regDtm +'</td>';
				if(item.ansCont != null){//답변상태확인
					html += '<td>답변완료</td>';
				}else{
					html += '<td>답변준비중</td>';
				}
				html += '<td>'+ item.viewCnt +'</td>';
				html += '</tr>';

			$("#qnaInfo").append(html);
		});
	}

	//페이지네이션 세팅
	com.fnPageInit(resData.pageInfo);
}

//게시판 상세페이지 내용호출
com.fnBrdDtlView = function(){
	param = {};
	obj = {url:"/brd/selectBrdDtl.do", param:param, formId:"serch", callBack:com.fnBrdDtlViewCallback, msgYn:"N", sessYn:"N", loadYn:"N"};
	com.fnAjaxPost(obj);
}

//게시판 상세페이지 return함수
com.fnBrdDtlViewCallback = function(resData){
	//목록 버튼
	var menuCd =  resData.brdRef;
	$('#btn-CallList').attr('onClick',"fnPage('" + menuCd + "'); return false;");

	//메뉴경로 게시물제목
	$('#menuPath2').text(resData.data.brdSubj);

	//상세화면 변수값 세팅, 내용 추가
	com.fnSetKeyVal(resData.data);

	//공지태그추가
	if(resData.data.notiYn === 'Y' || resData.data.topYn === 'Y'){
		$('#ele_brdSubj').prepend('<span class="notiTag">공지</span>');
	}
	$('.brdCont').html(resData.data.brdCont);

	//이전글 다음글 세팅
	//#ele_prevSubj, #ele_nextSubj: pc / .page-right-icon, .page-left-icon: mobil
	if(resData.data.prevSeq !== '' && resData.data.prevSeq !== null){//이전글
		//비밀글 여부
		if(resData.data.prevPw !== '' && resData.data.prevPw !== null){
			$('#ele_prevSubj').attr('onClick',"chkpop('" + resData.data.brdNo + "','" + resData.data.prevSeq + "','" + menuCd + "','" + resData.data.prevPw + "'); return false;");
			$('.page-right-icon').addClass('on');
			$('.page-right-icon').attr('onClick',"chkpop('" + resData.data.brdNo + "','" + resData.data.prevSeq  + "','" + menuCd + "','" + resData.data.prevPw + "'); return false;");
		}else{
			$('#ele_prevSubj').attr('onClick',"com.fnBrdDtlPage('" + resData.data.brdNo + "','" + resData.data.prevSeq + "','" + menuCd + "'); return false;");
			$('.page-right-icon').addClass('on');
			$('.page-right-icon').attr('onClick',"com.fnBrdDtlPage('" + resData.data.brdNo + "','" + resData.data.prevSeq  + "','" + menuCd + "'); return false;");
		}
	}else{//이전글 없음
		$('#ele_prevSubj').attr('onClick',"alert($('#ele_prevSubj').text())");
		$('.page-right-icon').text($('#ele_prevSubj').text());
		$('.page-right-icon').attr('onClick',"alert($('#ele_prevSubj').text())");
	}

	if(resData.data.nextSeq !== '' && resData.data.nextSeq !== null){//다음글
		//비밀글 여부
		if(resData.data.nextPw !== '' && resData.data.nextPw !== null){
			$('#ele_nextSubj').attr('onClick',"chkpop('" + resData.data.brdNo + "','" + resData.data.nextSeq + "','" + menuCd + "','" + resData.data.nextPw + "'); return false;");
			$('.page-left-icon').addClass('on');
			$('.page-left-icon').attr('onClick',"chkpop('" + resData.data.brdNo + "','" + resData.data.nextSeq + "','" + menuCd + "','" + resData.data.nextPw + "'); return false;");
		}else{
			$('#ele_nextSubj').attr('onClick',"com.fnBrdDtlPage('" + resData.data.brdNo + "','" + resData.data.nextSeq + "','" + menuCd + "'); return false;");
			$('.page-left-icon').addClass('on');
			$('.page-left-icon').attr('onClick',"com.fnBrdDtlPage('" + resData.data.brdNo + "','" + resData.data.nextSeq + "','" + menuCd + "'); return false;");
		}
	}else{//다음글 없음
		$('#ele_nextSubj').attr('onClick',"alert($('#ele_nextSubj').text())");
		$('.page-left-icon').text($('#ele_nextSubj').text());
		$('.page-left-icon').attr('onClick',"alert($('#ele_nextSubj').text())");
	}

	//질문과답변 상세
	if(menuCd === '20202'){
		$("#brdCont").empty();
		$("#brdCont").addClass('qna-detail');

		var html  = '<span class="qna-title active">질문</span>';
			html += '<div class="txt-border-wrap">'+ resData.data.brdCont + '</div>';
			if(resData.answer != null){
				html += '<span class="qna-title answer active">답변</span>';
				html += '<div class="txt-border-wrap mb0">'+ resData.answer.brdCont +'</div>';
			}else{
				html += '<span class="qna-title answer init">답변</span>';
				html += '<div class="txt-border-wrap mb0">답변 준비 중입니다.</div>';

				//답변 등록 버튼
				html += '<div id="ansPop" class="alignR mt10 hide">'
				html += '<button class="sub-btn2 btn-color"><a onClick="fnAnsPop();">등록</a></button>'
				html += '</div>'

			}

		$("#brdCont").append(html);

		//관리자일 경우 답변 등록버튼 노출
		var membNm = $('#membNm').val();
		if(membNm.indexOf('관리자') >= 0 && membNm != null){
			$("#ansPop").removeClass("hide");
		}
	}

	//첨부파일
	com.fnIbsUploadView($("#docFile"), resData.fileList);

	//아이비다운로드 안될시 사용할 첨부파일 다운로드
	com.fnFileDownOne(resData.fileList);

}

