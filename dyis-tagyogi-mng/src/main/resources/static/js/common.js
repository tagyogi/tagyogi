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
//세션끊김 방지
document.cookie = "safeCookie1=foo; SameSite=Lax";
document.cookie = "safeCookie2=foo";
document.cookie = "crossCookie=bar; SameSite=None; Secure";

let prevEventType = ""; //조합원 조회 이벤트 저장 변수
$(function($){
	//다이얼로그 팝업용
    $(".contentsArea").append('<div id="dialog" style="display:none"><iframe name="popFrm" id="popFrm" style="width: 100%; height: 100%; overflow: auto; border: none;"></iframe></div>');


	//조합원번호, 조합원명 같이 초기화
    $("input[type='search'][id^=s][id$=MembNo], input[type='search'][id^=s][id$=MembNm]").on("search", function(event) {

      /* console.log('------------------------');
	   console.log("prevEventType:: " + prevEventType);
	   console.log("curEventType:: " + event.type);*/

       var val = event.target.value;
       //serchingYn = true;

       //x표시 클릭시 value 값 없음 => 초기화
       if(val == "" || val == undefined){
		   $(this).siblings('input[type=search]').val('');
			serchingYn = false;
			prevEventType = "clear"; //search구분을 위해
			return;
	   }else {
	       if(event.type == "search" && (prevEventType == "change" || prevEventType == "keyup")){//이전이벤트가 keyup, change일때
				prevEventType = event.type;
				return; //패스
		   }
	       //현재 일어난 이벤트를 이전이벤트 타입 변수에 저장
		   prevEventType = event.type;
	   }



	});

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
		
			var target = event.target;

			if (target.tagName === 'INPUT') {
				var inputId = target.id;
				
				if (inputId.length >= 2) {
					var firstLetter = inputId[0];
        			var secondLetter = inputId[1];

        			if (firstLetter === 's' && secondLetter === secondLetter.toUpperCase()) {
						fnSearch();
			        }
				}

			}

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
		changeYear: true,  //년도변경셀렉트 박스
		yearSuffix: '년',
		showOn: "both",
		buttonImage: "../images/icon_calendar.png",
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
	buttonImage: "../images/icon_calendar.png",
	buttonImageOnly: true,
	buttonText: "달력",
	regional: 'ko',
	yearRange: 'c-50:c+10'
}

//datepicker 설정 종료


//공통변수
var patnHan = RegExp(/[ㄱ-ㅎㅏ-ㅣ가-힣]/);
var patnEng = RegExp(/[a-zA-Z]/);
var patnNum = RegExp(/[0-9]/);
var patnHanNum = RegExp(/[ㄱ-ㅎ|가-힣|0-9]/);
var patnEngNum = RegExp(/[a-zA-Z|0-9]/);

//공통 함수 시작
var com = new Object();


///////////////////////////////////////////////////////////////////////////////////////////////////////
// 업무별 공통 조회 부분
///////////////////////////////////////////////////////////////////////////////////////////////////////
//카카오톡 메신저 전송처리
// msgSeq 전송메세지순번,  param  치환 변수값, refNo  참조번호, telNo  특정대상번호 , membNo :조합원번호(대상자추출)
com.fnKakao = function(obj){
	param = {msgSeq:obj.msgSeq, param:obj.param, membNo:obj.membNo, refNo:obj.refNo, telNo:obj.telNo };
	obj = {url:"/kakao/sendKakao.do", param:param, callBack:com.fnKakaoCallBack, msgYn:"N", loadYn:"N"};
	com.fnAjaxDef(obj); 
}
com.fnKakaoCallBack = function(resData){
	if(resData.resultCode == "0"){
		alert("전송 되었습니다.");
	}else{
		alert(resData.resultMsg);
	}
}



//조합원 로그인
com.fnLoginHome = function(userEncVal, membNo){

	const host = window.location.host;
	let hostUrl = host.split(":")[0];

	if(hostUrl === "localhost") hostUrl = "http://"+hostUrl + ":9099/login/adminLoginProc.do";
    else  hostUrl = "https://www.kocfc.or.kr/login/adminLoginProc.do";

	document.forms[0].paramMap.value = userEncVal+":"+membNo;
	document.forms[0].action = hostUrl;
	document.forms[0].target = "_blank";
	document.forms[0].submit();

}

//상세, 수정화면 tab에 조합원명 명시
com.fnSetTabMembTag = function(obj){
	
	//넘어온값이 없는경우는 디폴트
	var targetNm = "";
	if(obj === undefined || obj.target === undefined || obj.target === "" ){
		targetNm = $("#ele_membNm").text() == "" ? $("#membNm").val():"";
	}else{
		targetNm = obj.target;
	}
	
	//맴버이름이 없는경우
	if(targetNm == "") return;
	else {
		
		//header에 class없으면 추가
		if(!$(top.document).find('.container').hasClass('hasNm')) $(top.document).find('.container').addClass('hasNm');
		
		//이름추가
		
		if($(top.document).find(".move-tabs .tab.active").find('em.tabMembTag').length > 0) return;
		else {
			var tag = '<em class="tabMembTag">' + targetNm + '</em>';
    		$(top.document).find(".move-tabs .tab.active").append(tag);
		}
		
	}
}

//조합원 조회
var serchingYn = false; //중복 조회 방지
com.fnMembInfo = function(){

	var sMembNo = $("#sMembNo").val();
	var sMembNm = $("#sMembNm").val();

	if(sMembNo == "" && sMembNm == ""){
		com.dialpopupOpen("조합원검색", "/index/pageCall.do?menuCd=1070707", "800", "500"); ///com/serchMemb
	}else{
		param = {sMembNo:sMembNo, sMembNm:sMembNm };
		obj = {url:"/com/selectMembList.do", param:param, callBack:com.fnMembInfoCallBack, msgYn:"N", loadYn:"N"};
		com.fnAjaxDef(obj);
	}


};

//조합원조회 커스텀 (담보배정관리)
//설명 :  소유조합원, 배정조합원 등 조합원검색이 두개 이상있을시 사용
//부모html form 에 sMembNo, sMembNm 공통으로 쓰되, 파라미터로 값세팅
//**************************************************************/
com.fnMembInfoMulti = function(event = undefined, objStr, setId){
	/*console.log('-------멀티조회----------------');
    console.log("prevEventType:: " + prevEventType);
    console.log("curEventType:: " + event.type);*/

	if(prevEventType === "search" && objStr !== "etc"){ //search는 x표시(clear)외엔 다 스킵 + 좌수증명원
		prevEventType = event.type;
		return;
	}

	//넘어온 string이 있으면, 그 값을 기준으로 넘어갈 파라미터(sMembNo, sMembNm 에 세팅)
	if(objStr != undefined){

		//초기, 세팅
		$("#sMembNo").val("");
		$("#sMembNm").val("");

		var strMemb = "";

		//실제값 가져올 객체 찾기
		switch (objStr){
		    case "own": //소유조합원
				strMemb = "sOwnMemb";
				break;
			case "ass": //배정조합원
				strMemb = "sAssMemb";
				break;
			case "trans": //양도조합원
				strMemb = "sTransMemb";
				break;
			case "take": //양수조합원
				strMemb = "sTakeMemb";
				break;
			case "etc": //기타
				strMemb = setId;
				break;
			defualt:
				break;
			}

		//찾은 객체의 value 추출
		var targetNo = $("#"+strMemb+"No").val();
		var targetNm = $("#"+strMemb+"Nm").val();

	//	alert(targetNo);

		if(event.type != "click" && prevEventType == "clear" && targetNo == "" && targetNm == ""){//clear로 지우고, 공백일땐 팝업조회 및 조회 방지 (돋보기클릭빼고)
			prevEventType = event.type;
			return; //패스
	    }

		//타입변수지정
		sMembType = strMemb;//조합원검색구분
		vMembType = objStr;//조합원결과상세구분

		//추출한값 검색조건에 추가
		$("#sMembNo").val(targetNo);
		$("#sMembNm").val(targetNm);

	}

	var sMembNo = $("#sMembNo").val();
	var sMembNm = $("#sMembNm").val();

	prevEventType = event.type;

	if(sMembNo == "" && sMembNm == ""){
		com.dialpopupOpen("조합원검색", "/index/pageCall.do?menuCd=1070707", "800", "500"); ///com/serchMemb
	}else{
		if(!serchingYn){
			serchingYn = true; //중복 조회 방지
			param = {sMembNo:sMembNo, sMembNm:sMembNm };
			obj = {url:"/com/selectMembList.do", param:param, callBack:com.fnMembInfoCallBack, msgYn:"N", loadYn:"N", asyncYn: false};
			com.fnAjaxDef(obj);
		}
	}




};

//조합원 조회 콜백
com.fnMembInfoCallBack = function(resData){

	if(resData.data.length == 0) {
		alert("조합원 정보가 존재하지 않습니다.");
		return;
	}
	if(resData.data.length > 1){ //여러건 일 경우
		com.dialpopupOpen("조합원검색", "/index/pageCall.do?menuCd=1070707", "800", "500"); ///com/serchMemb

	}else{ //단건인 경우
		$("#sMembNo").val(resData.data[0].membNo);
		$("#sMembNm").val(resData.data[0].membNm);

		if(typeof fnMembInfoSet != "undefined"){
			var param = {membNo : resData.data[0].membNo}
			obj = {url:"/com/selectMemb.do", param:param, callBack:com.fnSerchMembCallBack, msgYn:"N", sessYn:"Y", loadYn:"Y"};
			com.fnAjaxDef(obj);
		}
	}
};

//조합원 조회 콜백
com.fnSerchMembCallBack = function(resData){

	fnMembInfoSet(resData.data);
	serchingYn = false; //중복 조회 방지
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


//좌당지분액조회
com.fnGetDealAmt = function(appDt){

	//console.log($("#investDt").val());
	//sys 좌당지분액 관리소스에서 호출 confInvt/selectConfInvtMaxAppDt   // 검증 후 invt/selectFaceDeal 제거
	obj = {url:"/confInvt/selectConfInvtMaxAppDt.do", param:{appDt: appDt}, callBack:fnGetDealAmtCallBack, msgYn:"N", sessYn:"Y", loadYn:"Y"};
	com.fnAjaxDef(obj);
}



//전자서명검증처리
com.fnCertSignVeri = function(signData){

	param = {signData:signData};
	obj = {url:"/com/certSignDataVeri.do", param:param, callBack:com.fnCertSignVeriCallBack, msgYn:"N", loadYn:"N", sessYn:"Y"};
	com.fnAjaxDef(obj);
}
var signOrgData;
com.fnCertSignVeriCallBack = function(resData){
	signOrgData = resData.signOrgDataUtf;
	com.dialpopupOpen("전자서명정보", "/index/pageCall.do?menuPage=/com/comCertSign", "800", "500");
}


///////////////////////////////////////////////////////////////////////////////////////////////////////

com.fnNoProc = function(){
	alert("작업중입니다.");
}

//일반 공통 함수 부분 시작
//숫자만 입력 onkeyup="com.fnOnlyNum(this);
com.fnOnlyNum = function(obj){
	var iKeyCode = event.keyCode;
	if(iKeyCode < 48 || iKeyCode > 57){
		event.returnValue=false;
	}

	//백스페이스, 탭, 방향키 replace 안타도록(입력 포커스 맨뒤로 이동 방지)
	if(iKeyCode == 8 || iKeyCode == 9 || (iKeyCode >= 37 && iKeyCode <= 40)){
		return;
	}

	var str = obj.value;
	obj.value = obj.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-zA-Z|\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi, "");

	//obj.select();
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

	try{
		var k;
		var keyList = Object.getOwnPropertyNames(obj);
		for( key in keyList ){
			k = keyList[key].replace(" ", "");

			if(area == undefined){
				$("#ele_"+k).text(obj[k]);
				$("#div_"+k).text(obj[k]);
				$("#bef_"+k).text(obj[k]);
				$("#"+k).val(obj[k]);
			}else{
				$("#"+area+" #ele_"+k).text(obj[k]);
				$("#"+area+" #div_"+k).text(obj[k]);
				$("#"+area+" #bef_"+k).text(obj[k]);
				$("#"+area+" #"+k).val(obj[k]);
			}

		}
	} catch (err){
		console.log("com.fnSetKeyVal err : " + err  + " [ " +k+ "]");
	}
	
	
	//조합원정보 세팅일경우 조기경보 표시
	
	if(obj.membCretop !== undefined && obj.membCretop !== null){
		//console.log(obj.membCretop);	
		obj = {memb : obj, targId:"#ele_cretop"};
		com.fnMembSetCretop(obj);
	};
	
};




//필수항목 체크
com.fnValChk = function(targs){
 	//alert(targs.length);
 	for(i = 0; i < targs.length; i++ ){
		 //alert(targs[i] );
		 if(targs[i] != ""){
			obj = $("#"+targs[i]);
			 //alert(obj.prop("tagName"));
			 if(obj.val() == ""){
				if(obj.prop("tagName") == "INPUT"){
					 alert(obj.attr("title")+"을(를) 입력하십시오.");
					 obj.focus();
			 	}else if(obj.prop("tagName") == "SELECT"){
					 alert(obj.attr("title")+"을(를) 선택하십시오.");
					 obj.focus();
			 	}else{
					 alert(obj.attr("title")+"을(를) 입력하십시오.");
					 obj.focus();
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
//기준월 마지막요일 가져오기
com.fnDateGetLastDay = function(today, addMm){
	if(addMm === undefined) addMm = 0;
	if(today === "") return "";

	yy = Number(today.substring(0,4));
	mm = Number(today.substring(5,7));
	mm = mm + addMm;

	if(mm === 2){
		if(mm === 2 && yyyy % 4 === 0) {
			day = "29";
		}else{
			day = "28";
		}
	}else{
		if(mm % 2 === 0){
			day = "31";
		}else{
			day = "30";
		}
	}

	if(mm < 10) mm = "0"+mm;

	return yy+"-"+mm+"-"+day;

};


//당해 시작일 1/1일 추출
com.fnDateGetSta = function(day){
	if(day == "") return "";

	return day.substring(0,4)+"-01-01";
};

//당해 시작일 12/31일 추출
com.fnDateGetEnd = function(day){
	if(day == "") return "";

	return day.substring(0,4)+"-12-31";
};

//특정날짜 이전, 이후 일자 가져오기
com.fnDateGet = function(dt, day){
	//alert(typeof dt);
	if(dt == "") return "";
	if(typeof dt == "string" && dt.length == 8) dt = com.fnDateFormat(dt);

	var baseDt = new Date(dt);	// 기준일
    baseDt.setDate(baseDt.getDate() + day);	// 증감일

    var year = baseDt.getFullYear();
    var month = Number(baseDt.getMonth()+1);
    var day = Number(baseDt.getDate());

    if(month < 10) month = "0"+month;
    if(day < 10) day = "0"+day;

	return year+"-"+month+"-"+day;
};

//특정날짜 이전, 이후 일자 가져오기
com.fnDateMonGet = function(dt, mon){
	//alert(typeof dt);
	if(dt == "") return "";
	if(typeof dt == "string" && dt.length == 8) dt = com.fnDateFormat(dt);

	var baseDt = new Date(dt);	// 기준일
    baseDt.setMonth(baseDt.getMonth() + mon);	// 증감일

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

	dt = dt.replace(/\-/g,"");

    var isValid = false;
    var month_day = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    var year = Number(dt.substring(0,4));
    var month = Number(dt.substring(4,6));
    var day = Number(dt.substring(6,8));

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

//시간형식변환
com.fnTimeFormat = function(val, type){

	if(val == null) return "";

	if(type == undefined) type = ":";

	var str = val.toString();

	result = str.substring(0,2) + type + str.substring(2,4) + type + str.substring(4,6);

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

	if(obj.loadYn != "N") parent.$("#ele_loading").css("display","block"); //로딩바
	//console.log(obj.url);

	$.ajax({
   		url : obj.url
  			, type : "POST"
   			, dataType : "json"
  			, async : obj.asyncYn
  			, contentType : "application/json; charset=UTF-8"
  	    	, data : JSON.stringify(obj.param)
  			, success : function(resData){
				if(obj.loadYn != "N") parent.$("#ele_loading").css("display","none"); //로딩바

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

	   			if(obj.loadYn != "N") parent.$("#ele_loading").css("display","none"); //로딩바
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

	if(obj.loadYn != "N") parent.$("#ele_loading").css("display","block"); //로딩바

	//var formData = new FormData($("#"+formId)[0]);
	var param = JSON.stringify($("#"+obj.formId).serializeObject());
	$.ajax({
   		url : obj.url
		, data : param
		, type : "post"
		, contentType : "application/json; charset=UTF-8"
		, success : function(resData){
   			if(obj.loadYn != "N") parent.$("#ele_loading").css("display","none"); //로딩바

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

   			if(obj.loadYn != "N") parent.$("#ele_loading").css("display","none"); //로딩바
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
		var fileIds = obj.fileId.split(","); //myUpload,myUploadEqn,myUploadXxx
		for(x = 0; x < fileIds.length; x++ ){
			$("input[id^='" + fileIds[x] + "']").each(function(idx){
				files = this.files;
				for(i = 0; i < this.files.length; i++ ){
					//console.log(this.files[i]);
					if(x == 0){
						formData.append('file', this.files[i]);
					}else{
						formData.append('file' + x, this.files[i]);
					}

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
	if(obj.loadYn != "N") parent.$("#ele_loading").css("display","block"); //로딩바

	$.ajax({
   		url : obj.url
		, data : formData
		, type : "post"
		, contentType : false
		, processData : false
		, enctype : "multipart/form-data"
		, success : function(resData){
   			if(obj.loadYn != "N") parent.$("#ele_loading").css("display","none"); //로딩바

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

   			if(obj.loadYn != "N") parent.$("#ele_loading").css("display","none"); //로딩바
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
	parent.$("#dialog").dialog("close");
}

//fromTo 달려
com.fnDateFromTo = function(divId, fromId, toId, callBackFunc){
//console.log(divId + "콜백 : " + callBackFunc)
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
			, regional: 'ko'
			, yearRange: 'c-50:c+10'

	}

	//div 영역 만들기
	$("#"+divId).html("<div id='cal_"+fromId+"' style='float:left'></div><div id='cal_"+toId+"' style='float:right;margin-left:10px'></div>");

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
				if(ref1 != item.refVal1) refChk = false;
			}

			if(ref2 != undefined && ref2 != ""){
				if(ref2 != item.refVal2) refChk = false;
			}

			if(ref3 != undefined && ref3 != ""){
				if(ref3 != item.refVal3) refChk = false;
			}

			if(refChk) $("#"+paramId).append("<option value='"+item.cd+"'>"+item.cdNm+"(" + item.cd + ")</option>");

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
				if(ref1 != item.refVal1) refChk = false;
			}

			if(ref2 != undefined && ref2 != ""){
				if(ref2 != item.refVal2) refChk = false;
			}

			if(ref3 != undefined && ref3 != ""){
				if(ref3 != item.refVal3) refChk = false;
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
				if(ref1 != item.refVal1) refChk = false;
			}

			if(ref2 != undefined && ref2 != ""){
				if(ref2 != item.refVal2) refChk = false;
			}

			if(ref3 != undefined && ref3 != ""){
				if(ref3 != item.refVal3) refChk = false;
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
				if(ref1 != item.refVal1) refChk = false;
			}

			if(ref2 != undefined && ref2 != ""){
				if(ref2 != item.refVal2) refChk = false;
			}

			if(ref3 != undefined && ref3 != ""){
				if(ref3 != item.refVal3) refChk = false;
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

// ##########################################################################################################################################
// 동적 화면 처리 부분 끝
// ##########################################################################################################################################


//동적 파일 설정
com.fnFileSetInit = function(){
	var fileDocInit = "";
	fileDocInit += '<colgroup>';
	fileDocInit += '	<col style="width:110px;">';
	fileDocInit += '	<col style="width:100px;">';
	fileDocInit += '	<col style="width:150px;">';
	fileDocInit += '	<col style="width:140px;">';
	fileDocInit += '	<col style="">';
	fileDocInit += '</colgroup>';
	fileDocInit += '<tbody>';
	fileDocInit += '	<tr>';
	fileDocInit += '		<th class="tac">서식명</th>';
	fileDocInit += '		<th class="tac">제출여부</th>';
	fileDocInit += '		<th class="tac">첨부파일명</th>';
	fileDocInit += '		<th class="tac">제출일자</th>';
	fileDocInit += '		<th class="tac">비고</th>';
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
				html += '<td class="tac">' + item.cdNm + '<input type="hidden" name="docCd" value="'+item.cd+'" /><input type="hidden" name="docSeq" id="docSeq'+item.cd+'"/></td>';
				html += '</td>';
				html += '<td>'; //제출여부
					html += '<select name="docSubmitYn" style="width:80px" id="docSubmitYn'+item.cd+'">'; //제출여부
						html += '<option value="Y">제출</option>';
						html += '<option value="N">미제출</option>';
						html += '<option value="I">해당없음</option>';
					html += '</select>';
				html += '</td>';
				html += '<td><div  id="docFile'+item.cd+'"></div></td>';
				html += '<td><input type="text" name="docSubmitDt" id="docSubmitDt'+item.cd+'" onkeyup="com.fnOnlyNum(this);com.fnDateMakeForm(this)" onblur="com.fnDateValiChk(this)" style="width:90px" maxlength="10"/>';
				html += '<img src="../images/icon_calendar.png" class="calendar-box" onclick="calendar(this, \'docSubmitDt'+item.cd+'\', \'yyyy-mm-dd\')" alt="달력"/>';
				html += '</td>';
				html += '<td><input type="text" name="docRemark" id="docRemark'+item.cd+'"  style="width:150px" maxlength="100"/></td>';
				html += '</tr>';
			$("#" + tblId + " > tbody:last").append(html);

			//$("#docSubmitDt"+item.cd).datepicker(datepicker_ymd);
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

//조합계좌설정 (계좌목록 main.html 내에서 참조해옴 )
com.fnSetBankAccList = function ( selId, choiceType, value){
	//console.log(resData);

	//기존 옵션 삭제
	$("#"+selId +" option").remove();

	//셀렉트 유형
	if(choiceType == 1) $("#"+selId).append("<option value=''>선택</option>");
	if(choiceType == 2) $("#"+selId).append("<option value=''>전체</option>");
	
	let bankList = parent.bankAccList;
	if(bankList === undefined){
		bankList = parent.parent.bankAccList;
	}
	$.each(bankList, function(idx, item){
		$("#"+selId).append("<option value='"+item.bankCd+","+item.acctNo+"'>"+item.bankNm+"(" + item.bankType + ")</option>");
	});

	//변수값 선택
	if(value != undefined && value != ""){
		$("#"+selId+ " > option[value='"+value+"']").attr("selected", "selected");
	}
}

//조합원(회원)계좌목록
com.fnSetMembAccList = function(accParam = undefined){
	if (typeof accParam !== 'object' && typeof accParam === 'string') { //파라미터가 객체가 아닌경우 ==> 초기세팅(id만))
		$("#"+accParam).append("<option value=''>선택</option>");
	}else {
		//param = {paramId:paramId, choiceType: choiceType, value: value, sMembNo: $("#"+target).val(), sUseYn: 'Y', asyncYn: false};
		param = accParam;
		param.asyncYn = false;
		console.log(param);
		obj = {url:"/membAcc/selectMembAccMngList.do", param:param, callBack:com.fnSetMembAccListCallBack, loadYn:"Y", msgYn:"N", asyncYn:false};
		com.fnAjaxDef(obj);
	}

}

//조합원(회원)계좌 설정
com.fnMembAccChange = function(bankStr, other = undefined) { //other없을때 기본 undefined 설정

    //입금계좌외, 출금계좌등 기타 계좌 매개변수 있을때
    if(other != undefined){
		if(bankStr == "") {
	        $("#"+other+"Bank").val("");
	        $("#"+other+"Acct").val("");
	        $("#"+other+"Nm").val("");
	    } else {
			$("#"+other+"Bank").val(bankStr.split(",")[0]);
	        $("#"+other+"Acct").val(bankStr.split(",")[1]);
	        $("#"+other+"Nm").val(bankStr.split(",")[2]);
	    }
	    return;
	}

	//기본
    if(bankStr == "") {
        $("#payBank").val("");
        $("#payAcct").val("");
    } else {
        $("#payBank").val(bankStr.split(",")[0]);
        $("#payAcct").val(bankStr.split(",")[1]);
        $("#payNm").val(bankStr.split(",")[2]);
    }
}

//조합원(회원)계좌목록 콜백
com.fnSetMembAccListCallBack = function(resData){
	//console.log(resData);
	var paramId = resData.paramMap.paramId;
	var choiceType = resData.paramMap.choiceType;
	var value = resData.paramMap.value;

	if(choiceType === undefined) choiceType = 1;
	//기존 옵션 삭제
	$("#"+paramId +" option").remove();

	//셀렉트 유형
	if(choiceType == 1) $("#"+paramId).append("<option value=''>선택</option>");
	if(choiceType == 2) $("#"+paramId).append("<option value=''>전체</option>");
	
	$.each(resData.data, function(idx, item){
		$("#"+paramId).append("<option value='"+item.bankCd+","+item.acctNo+","+item.acctNm+"'>"+item.bankNm+","+item.acctNo+","+item.acctNm+"</option>");
	});

	//변수값 선택
	if(value != undefined && value != ""){
		$("#"+paramId+ " > option[value="+value+"]").attr("selected", "selected");
	}

}

//결제은행설정
com.fnBankChange = function(bankStr, other = undefined) { //other없을때 기본 undefined 설정

    //입금계좌외, 출금계좌등 기타 계좌 매개변수 있을때
    if(other != undefined){
		if(bankStr == "") {
	        $("#"+other+"Bank").val("");
	        $("#"+other+"Acct").val("");
	    } else {
			$("#"+other+"Bank").val(bankStr.split(",")[0]);
	        $("#"+other+"Acct").val(bankStr.split(",")[1]);
	    }
	    return;
	}

	//기본
    if(bankStr == "") {
        $("#payBank").val("");
        $("#payAcct").val("");
    } else {
        $("#payBank").val(bankStr.split(",")[0]);
        $("#payAcct").val(bankStr.split(",")[1]);
    }
}

//ibsheet 조합계좌목록 selectbox 동적생성
com.fnBankIbSelectBox = function(choiceType, value, ref1, ref2, ref3){
	var result = [];
	var enumNm = "";
	var enumKeys = "";

	//셀렉트 유형
	if(choiceType == 1){
		enumNm = "|선택";
		enumKeys = "|";
	}

	if(choiceType == 2)	enumNm = "|전체";

	$.each(parent.bankAccList, function(idx, item){
		refChk = true;


		if(ref1 != undefined && ref1 != ""){
			if(ref1 != item.refVal1) refChk = false;
		}

		if(ref2 != undefined && ref2 != ""){
			if(ref2 != item.refVal2) refChk = false;
		}

		if(ref3 != undefined && ref3 != ""){
			if(ref3 != item.refVal3) refChk = false;
		}

		if(refChk){
			enumNm   += "|" + item.bankNm + "("+item.bankType+")";
			enumKeys += "|" + item.bankCd + ","+item.acctNo;
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
		obj = {url:"/com/selectTalkPersList.do", param:param, callBack:com.fnSetPerSelectBox, loadYn:"Y", msgYn:"N", asyncYn:false};
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

//알림톡 발송
com.fnSendTalkMsg = function(param = undefined){
	//param = param;
	
	var msgYn = "Y";
	if(param.msgYn !== undefined) msgYn = param.msgYn
	
	obj = {url:"/kakao/sendKakao.do", param:param, msgYn:msgYn, sessYn:"Y", loadYn:"N", asyncYn: false};
	com.fnAjaxDef(obj);
}

///////////////////////////////////////////////////////////////////////////////////////
//엑셀다운로드 시작 (exceljs.min.js)
//Cannot read properties of null (reading 'querySelectorAll') 에러 발생시 태그 누락
var excelMultiHeader = false; // 멀티헤더여부 판단 변수
com.fnExcelToJs = function(sheet, cols, excelNm){

	parent.$("#ele_loading").css({"display":""}); //로딩바
	parent.$("#ele_exceDown").html("");
	$("#btn_excel").prop("disabled", true);

	var headersOrigin = cols;
	var headers = [];
	var data = [];

	//엑셀 다운로드 여부 필터링 (엑셀다운로드 표시된 컬럼만 headers에 세팅해서 넘기기)
	for(i = 0; i < headersOrigin.length; i++){
		if(headersOrigin[i].excelYn == '1'){
			headers.push(headersOrigin[i]);
		}
	}

	/********************멀티헤더 구분필요**************** */
	//멀티헤더 가공할 데이터 만들기
	var rowCt = headersOrigin[0].Header.length;
	var headerRowArr;
	if(rowCt > 1) excelMultiHeader = true;
	if(excelMultiHeader) {
		for(i = 0; i < headers.length; i++){
			data.push(headersOrigin[i].Header);
		}
		//멀티헤더 가공 return값 받기
		headerRowArr = com.fnExcelSetHeadArr(data);
	}

	/************************************************* */

	var rows = sheet.getDataRows();
	if(sheet.getFormulaRowPosition() > 0){
		rows.push(sheet.getRowById("FormulaRow"));
	}

	//var head = sheet.getHeaderRows();
	var body = "";
	body +="<table border='1' id='tbl_excelList'>";
	body +=" <thead>";
	body +=" <tr>";

	//헤더 단행일때
	if(!excelMultiHeader) {
		//헤더부
		for(i = 0; i < headers.length; i++){
			body +=" <th style='width:" + Number(headers[i].Width) * 10 + "px'>" + headers[i].Header+ "</th>";
		}
	}

	body +=" </tr>";
	body +=" </thead>";
	body +=" <tbody>";
	body +=" <tr>";

	//바디부
	for(x = 0; x < rows.length; x++){
		for(k = 0; k < headers.length; k++){
			//console.log(headers[k]);
			var rowId = sheet.getRowById(rows[x].id);
			body +=" <td>" + sheet.getString(rowId, headers[k].Name) + "</td>";
		}
		body +=" </tr>";
	}
	body +=" </tbody>";

	var html = "";
		html += "<html xmlns:x='urn:schemas-microsoft-com:office:excel' xml:lang='ko'>";
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

	
	let table = "";
    if(parent.$("#ele_exceDown").length === 0){
		top.$("#ele_exceDown").html(html); //대상 생성
		table = top.document.getElementById("tbl_excelList");
	}else{
		parent.$("#ele_exceDown").html(html); //대상 생성
		table = parent.document.getElementById("tbl_excelList");
    }
	
	
	// 엑셀 워크북을 생성합니다.
	const workbook = new ExcelJS.Workbook();
 	const worksheet = workbook.addWorksheet(excelNm);

	// 테이블 헤더를 추가합니다.
	/************************************** */
	//멀티헤더 머지처리
	if(excelMultiHeader) {
		var rowSize = headerRowArr.length;
		var colSize = headerRowArr[0].length;
		headerRowArr.forEach((item, idx) => {
			worksheet.addRow(headerRowArr[idx]);
	    });

		for (let row = 1; row <= rowSize; row++) {
		  for (let col = 1; col <= colSize; col++) {
		    com.fnMergeCells(worksheet, row, col);
		  }
		}

		//console.log(mergedCells);
		//머지된 헤더 style처리
		for (let row = 1; row <= rowSize; row++) {
			for (let col = 1; col <= colSize; col++) {
			  	worksheet.getRow(row).getCell(col).font = { bold:true, color: { argb: '00000000' } }; //
			  	worksheet.getRow(row).getCell(col).alignment = { horizontal: 'center', vertical: 'middle' }; // Text alignment
			  	worksheet.getRow(row).getCell(col).fill = { type:'pattern', pattern:'solid', fgColor: { argb: '00D5D5D5' } }; //
			  }
		}
	/***************************************** */
	}else { //멀티헤더아닐때
		// 테이블 헤더를 추가합니다.
		console.log(table);
		const headerRow = worksheet.addRow(Array.from(table.querySelectorAll('thead th'), th => th.textContent));
	}

	// 테이블 데이터를 추가합니다.
	//console.log("##########");console.log(headers);
	//console.log("##########");
	
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
  	
  	/*
  	Array.from(table.querySelectorAll('tbody tr')).forEach( tr => {
		
    	const dataRow = worksheet.addRow(Array.from(tr.querySelectorAll('td'), (td, tdIdx) => {
			if(cols[tdIdx].Name.indexOf("Amt") > -1){
				//console.log(headers[k]);
				worksheet.getRow(trIdx).getCell(tdIdx).numFmt  =  '# ?/?' ;
			}
			td.textContent;
		}));
  	});
*/
  	// 열의 너비를 설정합니다.
  	worksheet.columns.forEach((column, idx) => {
		//column.width = headers[idx].MinWidth;
		column.width = Number(headers[idx].MinWidth/5);
  	});

  	// 열의 텍스트 정렬과 색상을 설정합니다.
  	//const row = worksheet.getRow(1);
  	if(!excelMultiHeader) { //머지아닌 header style
	  	worksheet.getRow(1).font = { bold:true, color: { argb: '00000000' } }; //
	  	worksheet.getRow(1).alignment = { horizontal: 'center' }; // Text alignment
	  	worksheet.getRow(1).fill = { type:'pattern', pattern:'solid', fgColor: { argb: '00D5D5D5' } }; //
	}


	//스타일

  	for(x = 0; x < rows.length; x++){

		if(!excelMultiHeader) var row = worksheet.getRow(x+2);
		else{
			var multiRow = headerRowArr.length;
		 	var row = worksheet.getRow(x+multiRow+1); //멀티헤더의 경우 멀티헤더갯수 이후부터 카운팅
		}

		for(k = 0; k < headers.length; k++){
			var alig = headers[k].Align;
			row.getCell(k+1).alignment = { horizontal: alig.toLowerCase(),vertical: 'middle' }; // Text alignment
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

	parent.$("#ele_exceDown").html("");
	parent.$("#ele_loading").css({"display":"none"}); //로딩바
	$("#btn_excel").prop("disabled", false);
}

// 동적으로 헤더셀 머지하기
com.fnMergeCells = function(worksheet, rowStart, colStart) {

	var rowEnd = rowStart; //1
	var colEnd = colStart;

    //row 머지할 대상 찾기
	while (
			rowEnd + 1 <= worksheet.rowCount &&
			worksheet.getCell(rowEnd + 1, colStart).value === worksheet.getCell(rowStart, colStart).value
		) {
		rowEnd++;
	}

	//col 머지할 대상 찾기
	while (
			colEnd + 1 <= worksheet.columns.length &&
    		worksheet.getCell(rowStart, colEnd + 1).value === worksheet.getCell(rowStart, colStart).value
		) {
		colEnd++;
	}


	//머지시작
	if (!com.fnChkCellsMerged(rowStart, rowEnd, colStart, colEnd, worksheet)) {
		if (rowEnd > rowStart || colEnd > colStart) {

	    var mergeStart = worksheet.getCell(rowStart, colStart);
	    var mergeEnd = worksheet.getCell(rowEnd, colEnd);

		var setFrom = com.fnConvertToAlphabet(colStart);
		var setTo   = com.fnConvertToAlphabet(colEnd);

		var from = `${setFrom}${rowStart}`; //A,B,C...AA, AB, AC,,
		var to = `${setTo}${rowEnd}`;  // 1,2,3,4....

	   //var from = `${String.fromCharCode(65 + colStart - 1)}${rowStart}`; //A,B,C...AA, AB, AC,,
	   //var to = `${String.fromCharCode(65 + colEnd - 1)}${rowEnd}`;  // 1,2,3,4....

		console.log("from ::" +from);
		console.log("to ::" +to);

	    //worksheet.unMergeCells();
		worksheet.mergeCells(from, to);

		com.fnMarkCellsAsMerged(rowStart, rowEnd, colStart, colEnd, worksheet);

	  }

  }
};

//이미머지됐는지 구분
const mergedCells = new Set();
com.fnChkCellsMerged = function(rowStart, rowEnd, colStart, colEnd, worksheet) {

	for (var row = rowStart; row <= rowEnd; row++) {
		for (var col = colStart; col <= colEnd; col++) {

		var setCol = com.fnConvertToAlphabet(col);
		var cellAddress = `${setCol}${row}`;
		//const cellAddress = `${String.fromCharCode(65 + col - 1)}${row}`;

		if (mergedCells.has(cellAddress)) {
		return true;
		}
    }
  }
  return false;
};

//엑셀 컬럼 갯수가 Z를 넘어갔을경우 대비
com.fnConvertToAlphabet = function(number) {
  if (typeof number !== "number" || isNaN(number)) {
    throw new Error("유효하지않은 숫자값 입니다.");
  }

  if (number >= 1 && number <= 26) {
    return String.fromCharCode(64 + number);
  }

  const div = Math.floor((number - 1) / 26);
  const mod = (number - 1) % 26;

  return com.fnConvertToAlphabet(div) + com.fnConvertToAlphabet(mod + 1);
}

//머지됐는지 체크할 대상 mergedCells 에 추가
com.fnMarkCellsAsMerged = function(rowStart, rowEnd, colStart, colEnd, worksheet) {
	for (let row = rowStart; row <= rowEnd; row++) {
		for (let col = colStart; col <= colEnd; col++) {

		var setCol = com.fnConvertToAlphabet(col);
		var cellAddress = `${setCol}${row}`;

      	//const cellAddress = `${String.fromCharCode(65 + col - 1)}${row}`;
		//const cellAddress = `${worksheet.getCell(row, col)._address.model}`;
		mergedCells.add(cellAddress);

    }
  }
};


/************************ 배열재배치 - excel.js ******************************/
//mergeCell를 위한 배열 세팅
com.fnExcelSetHeadArr = function(data){

	var data = data;
    // 동적으로 받아온 카운트 숫자
    var rowCount = data.length; // 행 개수
    var colCount = data[0].length; // 열 개수

	var headerRow = {};
    var headerRow = Array.from({ length: colCount }, () => new Array(rowCount).fill(''));

    for (var j = 0; j < colCount; j++) { //0
        for (var k = 0; k < rowCount; k++) { //0~6
            headerRow[j][k] = data[k][j];
        }
    }
    return headerRow;
}

///////////////////////////////////////////////////////////////////////////////////////
//엑셀다운로드 시작 (그리드 내용 추출 하여 엑셀 다운로드)
com.fnExcelToGrid = function(sheet, cols, excelNm){

	var getRows = sheet.getDataRows();
	var rows = [];
	var jRows = {};

	for(i = 0; i < getRows.length; i++){
		var rowId = sheet.getRowById(getRows[i].id);
		for(x = 0; x < cols.length; x++){
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
		//parent.$("#ele_loading").css({"display":"none"}); //로딩바
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

            parent.$("#ele_loading").css("display","none"); //로딩바
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

	parent.$("#div_loading").css("display","block"); //로딩바

	var xmlHttp = new XMLHttpRequest();       // XMLHttpRequest 객체를 생성함.
	xmlHttp.open("POST", "/com/excelDown.do", true);
	xmlHttp.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
	xmlHttp.responseType = "blob";

	xmlHttp.onload = function() { // onreadystatechange 이벤트 핸들러를 작성함.
		//parent.$("#div_loading").css({"display":"none"}); //로딩바
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

            parent.$("#div_loading").css("display","none"); //로딩바
            // 요청한 데이터를 문자열로 반환함.
	    }
	};

	xmlHttp.send(JSON.stringify(param));

}

 /****************************************************************
 * 공통코드 처리 부분 종료
 * **************************************************************/

/****************************************************************
 * IBSheet 공통 부분 시작
 * **************************************************************/
//초기설정 조회
com.fnIbsInit = function(workGird, cfg){

	if(typeof gridColList == "undefined") {
		alert("헤더정보가 존재 하지 않습니다.");
		return;
	};

	if (cfg == undefined){
		cfg = {
				FitWidth:true //우측 빈칸 더미 활용
				, InfoRowConfig :{
					Space:"Bottom" //건수표시 하단에 표기
				}
				, Size: "Small" //행 높이
				, SectionCanResize : 1 //
				, HeaderMerge :5 //열우선 사방병합
				, SearchMode:0 //검색로딩
				, IgnoreFocused : true //포커스 가져오지 않음
				, AutoRowHeight: 1 //행높이 자동사용 (line 들어간 그리드만 별도 추가 설정) - 스크립트오류 발생
		}
	};

	var workGirds = workGird.split(":");

	//alert(workGirds);
	for(i = 0; i < workGirds.length; i++){
		var gridNo = workGirds[i];

		var colList = []; //컬럼 정보

		//	alert(workGirds[i]);
		gridColList.forEach((item, idx) => { //컬럼 개수만큼 순환
		//	console.log(item);
			if(gridNo == item.gridNo){
				var header = item.Header;
				if(header != null){
					if(header.indexOf('{') != -1){
						item.Header = JSON.parse(header);
					}else{
						headers = header.split(",");
						item.Header = headers;
					}
				}

				colList[colList.length] = item;
			}

		});

		gridOpt = new Object();
		//gridOpt.Def = {Row:{Height:27}}; //화면내 별도 선언
		gridOpt.Def = {}; //화면내 별도 선언
		gridOpt.Cfg = cfg;
		gridOpt.Cols = colList;

		gridOptList[i] = gridOpt;

	}

	//console.log(gridOptList);
	/*
	//그리드필드 리스트 분류
	if(typeof gridColList != "undefined" ){
		//그리드별 리스트 분류
		var gridNo = "" //그리드관리번호
		var gridIdx = 0; //그리드 배열 순번
		var colList = []; //컬럼 정보
		gridColList.forEach((item, idx) => { //컬럼 개수만큼 순환
			//console.log(item.Header + " idx : " + idx);
			if(gridNo == "" || item.gridNo != gridNo){

				if(idx != 0){ //하나 끝났을경우
					gridOpt = new Object();
					gridOpt.Def = {}; //화면내 별도 선언
					gridOpt.Cfg = cfg;
					gridOpt.Cols = colList;

					gridOptList[gridIdx] = gridOpt;
					gridIdx++;
				}

				gridNo = item.gridNo;
				colList = [];
			}

			var header = item.Header;
			if(header != null){
				if(header.indexOf('{') != -1){
					item.Header = JSON.parse(header);
				}else{
					headers = header.split(",");
					item.Header = headers;
				}
			}

			colList[colList.length] = item;

		});

		//마지막 건 설정
		gridOpt = new Object();
		gridOpt.Def = {}; //화면내 별도 선언
		gridOpt.Cfg = cfg;
		gridOpt.Cols = colList;

		gridOptList[gridIdx] = gridOpt;
	}
	*/
	/*
	for(i = 0; i < colsList.length; i++){
		gridOpt = new Object();
		gridOpt.Def = {};
		gridOpt.Cfg = cfg;

		//헤더배열설정
		for(x = 0; x < colsList[i].length; x++){
			var header = colsList[i][x].Header;
			if(header != null){
				if(header.indexOf('{') != -1){
					colsList[i][x].Header = JSON.parse(header);
				}else{
					headers = header.split(",");
					colsList[i][x].Header = headers;
				}
			}else{
				colsList[i][x].Header = header;
			}

		}


		gridOpt.Cols = colsList[i];

		gridOptList[i] = gridOpt
	};
	*/

};

//현황 조회
com.fnIbsSearch = function(url, param, sheetId, callback){

	if(!com.fnSessChk()){
		top.location.href = "/";
		return;
	}

	parent.$("#ele_loading").css("display","block"); //로딩바

	//화면 해상도에 따른 높이 조절
	$("div[id^='sheetDiv']").each(function(){
		//$(this).css("height", window.innerHeight - 220);
	});

	if (param == undefined) param = {};
	if (sheetId == undefined) sheetId = sheet;
	if (callback == undefined) callback = com.fnIbsSearchCallBack;

	var opt = {
	  url: url,
	  param: param,
	  method: "POST",
	  reqHeader: {"Content-Type":"application/json"},
	  callback: callback

	};
	sheetId.doSearch(opt);
};

//현황 조회콜백(별도 콜백 함수가 없을경우 처리)
com.fnIbsSearchCallBack = function(resData){
	var resData = JSON.parse(resData.data);
	if(resData.resultCode == 9){
		alert(resData.resultMsg);
	}
	parent.$("#ele_loading").css("display","none"); //로딩바

};

//행추가
com.fnIbsRowAdd = function(focs, initData, sheetId, editYn){
	if (initData == undefined) initData = {};
	if (sheetId == undefined) sheetId = sheet;
	if (editYn == undefined) editYn = "Y";

	if(focs == "first"){ //맨 위에
		sheetId.addRow( {"next":sheetId.getFirstRow(), "init":initData } )
	}else if(focs == "cur"){ //현재지점 위에
		sheetId.addRow( {"next":sheetId.getFocusedRow(), "init":initData } )
	}else if(focs == "pre"){ //아래부분
		var rows = sheetId.getDataRows();
		if(rows.length == 0){
			sheetId.addRow( {"next":sheetId.getFirstRow(), "init":initData } )
		}else{
			if(sheetId.getFocusedRow() === null){
				sheetId.addRow( {"next":sheetId.getNextSiblingRow(0), "init":initData } )
			}else{
				sheetId.addRow( {"next":sheetId.getNextSiblingRow(sheetId.getFocusedRow()), "init":initData } )
			}
		}
	}else if(focs == "tree"){ //tree 형식
		sheetId.addRow( {"parent":sheetId.getFocusedRow(), "init":initData } )
		//sheetId.addRow( {"next":sheetId.getNextSiblingRow(sheetId.getFocusedRow()), "init":initData } )
	}

	rowData = sheetId.getFocusedRow(); //생성된 행 정보 가져오기

	//편집 제한 열 편집 활성화 처리
	if(editYn == "Y"){
		var getCols = sheetId.getCols("Visible");
		for(colsi = 0; colsi < getCols.length; colsi++){
			if(sheetId.getAttribute(rowData, getCols[colsi], "CanEdit") == 0){
				sheetId.setAttribute(rowData, getCols[colsi], "CanEdit", 1);
			}
		}
	}
};

//행삭제(추가된 행에 대해서만 처리)
com.fnIbsRowDel = function(sheetId){
	if (sheetId == undefined) sheetId = sheet;

	rowData = sheetId.getFocusedRow();
	if(rowData.id == ""){
		alert("선택된 행이 없습니다."); //선택된 행이 없습니다.
		return;
	}

	if(rowData.Added){
		sheetId.removeRow(rowData);
	}else{
		alert("추가된 행만 삭제 가능합니다."); //추가된 행만 삭제 가능합니다.
	}
};

//변경 내용 저장
com.fnIbsSave = function(url, sheetId){

	//세션체크
	if(!com.fnSessChk()){
		top.location.href = "/";
		return;
	}

	if (sheetId == undefined) sheetId = sheet;
	//var saveData = sheetId.getSaveJson();
	//console.log(saveData);
	//if(saveData.data.length < 1){
	//	alert("변경 내역이 없습니다.");
		//return;
	//}

	var procYn = true;
	if(typeof confYn === 'undefined'){
		if(!confirm("저장 처리 하시겠습니까?")){
			procYn = false;
		}
	}
	if(procYn){
		//자동 유효성 체크 처리 위함
		sheetId.doSave({
			url : url
			//, param : saveData
			, saveMode : 2 //수정데이터
			, queryMode : 0 //json 방식 전달
			, reqHeader : {"Content-Type":"application/json"}//request 추가 내용  (ex) {key1:value1, key2:value2})
			, request:1
			//, quest:1 //저장시 confirm 메세지 사용 여부
			, sync :0 // 0 비동기, 1 동기
			, validRequired : 1 //필수 항목 메세지
		});
	}
};

//행 상테 변경(삭제)
com.fnIbsStatusChg = function(sheeId, status){
	rowData = sheeId.getFocusedRow(); //생성된 행 정보 가져오기

	if(rowData == null || rowData.id == ""){
		alert("선택된 행이 없습니다."); //선택된 행이 없습니다.
		return;
	}

	if(rowData.Added == 1){ //추가 인경우는 삭제
		sheeId.removeRow(rowData);
	}else {
		if(status == "D"){
			sheeId.deleteRow({row:sheeId.getFocusedRow(), del:sheeId.getFocusedRow().delChk});
		}
	}
};

//열 중복값 체크(삽입하기 전)
com.fnIbsRowDup = function(sheeId, cellId, val){
	var rows = sheeId.getDataRows();

	for(var i=0; i<rows.length; i++){
		if(rows[i][cellId] == val){
			alert("중복된 데이터 입니다.");
			return false;
		}
	}
	return true;
};

//행 추가, 수정 구분해서 데이터 세팅(status와 컬럼값들/수정시 로우삭제후 add방식)
/*
* 파라미터로 넘긴 변수명이 전부 undefined 가아니고 공백 아닌경우만 등록후 수정으로 판단 (로우삭제후 추가 및 staus 변경함)
* 그외 조건들은 단순 등록화면에서 추가 및 추가된(저장전) 행 정보 변경으로 판단
*/
com.fnIbsAddChgRow = function(obj){
	//var param = {sheetId: sheetSure, mapRow: selSureRow, rnVal: obj, ifVal: "sureNm:sureSeq"}
	//sheetId : 시트아이디 , mapRow : 그리드행, rnVal : 팝업파라미터, ifVal: 필수조건변수명들
	
	//수정화면에서 수정(기등록된 정보수정) - AND조건
	if (fncheckCmpareVals(obj.mapRow, obj.ifVal)) { 
	  
	  //삭제처리
		oriRowData = obj.sheetId.getFocusedRow(); //원본행저장
		obj.rnVal.sureNo = oriRowData.sureNo;   
		obj.sheetId.removeRow({row:obj.mapRow});
		
		//추가
		com.fnIbsRowAdd("pre", obj.rnVal, obj.sheetId);
		rowData = obj.sheetId.getFocusedRow(); //생성된 행 정보 가져오기
		
		// 상태 변경 Changed
		rowData.Changed = 1;
		rowData.Added = 0;
	  
	} 
	//입력화면에서 행추가 혹은 수정화면에서 행추가된 정보수정
	else {									 
		if(obj.mapRow.sureNm !== undefined){ //최초등록저장전 수정인경우는 보증인명있음
			obj.sheetId.removeRow({row:obj.mapRow});
		}
		
		com.fnIbsRowAdd("pre", obj.rnVal, obj.sheetId);
	  
	}
	
};


//조건문 체크
function fncheckCmpareVals(obj, valString) {
	if (!valString) {
		return false;
	}
	const vals = valString.split(':');
	
	for(const val of vals) {
		const trimVal = val.trim(); // 앞뒤 공백 제거
		if (obj[trimVal] === undefined || obj[trimVal] === "") {
			return false; // 하나라도 조건을 만족하면 false를 반환
		}
	}
  return true;
}

//업로드 초기 설정
var ibUploadInit = {
	viewType:"icon"
    , iconMode:"detail" //icon, list, detail
    //, theme :"Main"
    , userInputName : "myUpFil1e"
    , limitFileCount : 50 //개수 제한
    , limitFileSize : 1024 * 1024 * 500 //건당 사이즈 제한
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
com.fnIbsUpload = function(divId){

	ibUploadInit.limitFileCount = 50; //개수 제한
	ibUploadInit.iconMode = "detail";

	//파일업로드
	divId.IBUpload(ibUploadInit);

}


//파일업로드(등록/조회)
com.fnIbsUploadView = function(divId, list){

	ibUploadInit.limitFileCount = 50; //개수 제한
	ibUploadInit.iconMode = "detail";

	//파일업로드
	divId.IBUpload(ibUploadInit);

	if(list != undefined){
		var setList = new Array();
		$.each(list, function(i, item){
			//console.log(item);
			var setData = new Object();

			for(elem in item){
				setData[elem] = item[elem];
			}
			setData.name = this["fileOrgNm"];
			setData.size = this["fileSize"];
			setData.date = this["regDtm"];
			setData.url = this["atthType"]+":"+this["atthNo"]+":"+this["atthSeq"];

			setList.push(setData);

		});

		var setObj = new Object();

		//세팅전 빈값으로 세팅으로 초기화
		/*setObj.files = null;
		divId.IBUpload("reset");
		divId.IBUpload(setObj);*/

		setObj.files = setList;
		divId.IBUpload(setObj);
	}
}

//파일업로드(등록/조회) - 단건
com.fnIbsUploadViewOne = function(list, mod){
	//console.log('file list');
	//console.log(list);

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

//(공통)상세 페이지 파일저장
//pFileId: 첨부파일영역 아이디(양식있는 첨부파일은 docFile으로 id넣어야함 - com.fnFileSet 참고),  pRefNo: 데이터 pk,
//pCodeNm는 codeList에서 docCd찾는방식, pDocCd는 지정값일때(둘중 하나만 넣어야함)
com.fnDtlFileUpload = function(pFileId, pAtthType, pAtthNo, pRefNo, pCodeNm, pDocCd){
	//첨부파일 유효성검사
	if(com.fnIsEmpty(pAtthType)){
		alert("파일 유형값이 없습니다. 관리자에게 문의해주세요.");
		return;
	}
	if(com.fnIsEmpty(pAtthNo)){
		alert("파일 아이디가 없습니다. 관리자에게 문의해주세요.");
		return;
	}

	//참조번호 파라미터(다건첨부 기본 파라미터)
	var fileParam = {atthType: pAtthType, atthNo: pAtthNo, refNo: pRefNo};

	//codeNm이 있으면 docCd 추가 및 첨부파일 순서 정리
	if(!com.fnIsEmpty(pCodeNm)){	//양식이 있음
		var docCdList = [];
		$.each(codeList, function(idx, item){
			if(item.cdType === pCodeNm)	docCdList.push(item.cd);
		});
		fileParam.docCd = docCdList;	//docCd(DB 저장에 필요)
		fileParam.modFileCds = com.fnGetModCds(pFileId, "Y");	//첨부파일 순서 정리
	}

	if(!com.fnIsEmpty(pDocCd)){		//다건 첨부파일인경우만 사용중이라 modFileCds는 따로 해주지 않았음
		fileParam.docCd = pDocCd;	//docCd(DB 저장에 필요)
	}

	if(confirm("첨부파일을 저장 처리 하시겠습니까?")){
		obj = {url:"/file/dtlFileUpload.do", jParam: fileParam, fileId: pFileId, callBack: com.fnDtlFileUploadCallBack, msgYn:"Y", sessYn:"Y", loadYn:"Y"};
		com.fnAjaxMulti(obj);
	}
}

//(공통)상세 페이지 파일저장 콜백
com.fnDtlFileUploadCallBack = function(resData){
	//페이지 리로드
	let reLoadFrm = window.frames.name;
	top.document[reLoadFrm].location.reload();
}


//파일업로드
com.fnIbsFileUpload = function(divId, atthType, atthNo, docCd){

	if(atthType == undefined || atthType == "" ){
		alert("파일유형값이 없습니다.");
		return;
	}
	if(atthNo == undefined || atthNo == "" ){
		atthNo = "";
	}
	if(docCd == undefined || docCd == "" ){
		docCd = "";
	}

	parent.$("#ele_loading").css("display","block"); //로딩바

	divId.IBUpload("extendParamUpload","atthType="+atthType+"&atthNo="+atthNo + "&docCd="+docCd);
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
com.fnGetModCds = function(ids, needReturn = "N"){
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
	if(needReturn === "Y")	return modCds;	//com.fnDtlFileUpload에서 사용
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
	var url = "/index/pageCall.do?menuCd=1070706"; // /com/serchAddr
	if(open == "popup"){
		window.open(url, "주소검색", "width=570,height=420, scrollbars=yes, resizable=yes");
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

//콤마 (name 경우 동일 name 이 같이 변경됨 id로 변경 )
com.makeComma = function(obj){
	var id = obj.id;
  	var num = obj.value.replace(/,/g,'');
  	var $this = $("input[id="+id+"]");
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

	if(num == ""){
		return 0;
	}else{
		return num.toString().replace(/,/gi,"");
	}

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

//전화 번호 (그리드용 - 조합원수정)
com.makeTelNoReturn = function(str) {
	var returnVal = "";
    if(str.indexOf("-") > -1) {
        str = str.replace(/\-/g,"");
    }
    //숫자만 허용
    str = str.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-zA-Z|\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi, "");

    var firstNoIdx = 3;
    if(str.length >= 2 && str.substring(0, 2) == "02") firstNoIdx = 2;
    else if(str.length >= 4 && str.substring(0, 4) == "0505") firstNoIdx = 4;

    if(str.length >= 0 && str.length <= firstNoIdx) {
        returnVal = str;
    } else if(str.length > firstNoIdx && str.length <= 7) {
       	returnVal = str.substring(0,firstNoIdx) + "-" + str.substring(firstNoIdx);
    } else if(str.length > 7 && str.length <= 14) {
        returnVal = str.substring(0,firstNoIdx) + "-" + str.substring(firstNoIdx,str.length - 4) + "-" + str.substring(str.length - 4);
    }

    return returnVal;

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

//금액 한글화
com.fnConvNumberToHan = function(num){
 
  num = num.toString().replace(/,/gi,"");
  var result = '';
  var digits = ['영','일','이','삼','사','오','육','칠','팔','구'];
  var units = ['', '십', '백', '천', '만', '십만', '백만', '천만', '억', '십억', '백억', '천억', '조', '십조', '백조', '천조'];
  
  var numStr = num.toString(); // 문자열로 변환
  var numLen = numStr.length; // 문자열의 길이
  
  for(var i=0; i<numLen; i++) {
    var digit = parseInt(numStr.charAt(i)); // i번째 자릿수 숫자
    var unit = units[numLen-i-1]; // i번째 자릿수 단위
    
    // 일의 자리인 경우에는 숫자를 그대로 한글로 변환
    if(i === numLen-1 && digit === 1 && numLen !== 1) {
      result += '일';
    } else if(digit !== 0) { // 일의 자리가 아니거나 숫자가 0이 아닐 경우
      result += digits[digit] + unit;
    } else if(i === numLen-5) { // 십만 단위에서는 '만'을 붙이지 않습니다.
      result += '만';
    }
  }
  
  return result;
}



/**
 * 조합원 조기경보 상태 표시
 * 
 */
//게시판 상세페이지 내용호출
com.fnMembSetCretop = function(obj){
	let creColor = "";
	let membCretop = obj.memb.membCretop;
	if(membCretop === "1") creColor = "#319A02"; //관심
	if(membCretop === "2") creColor = "#CCCCCC"; //관찰1
	if(membCretop === "3") creColor = "#A9A3A7"; //관찰2
	if(membCretop === "4") creColor = "#F8A66E"; //관찰3
	if(membCretop === "5") creColor = "#901821"; //부도
	if(membCretop === "6") creColor = "#C4212A"; //휴업(폐업)
	if(membCretop === "7") creColor = "#C4212A"; //휴업
	
	if(membCretop === "8") creColor = "#319A02"; //유보 (WATCH)
	if(membCretop === "9") creColor = "#CCCCCC"; //관찰 (WATCH)
	if(membCretop === "10") creColor = "#A9A3A7"; //주의 (WATCH)
	if(membCretop === "11") creColor = "#F8A66E"; //경보 (WATCH)
	if(membCretop === "12") creColor = "#901821"; //위험 (WATCH)
	if(membCretop === "13") creColor = "#C4212A"; //회수의문 (WATCH)
	if(membCretop === "14") creColor = "#D77A3F"; //휴폐업 (WATCH)
	if(membCretop === "15") creColor = "#D77A3F"; //부도 (WATCH)
	if(membCretop === "16") creColor = "#319A02"; //산출유보 (WATCH)
	
	if(membCretop !== "16"){
		$(obj.targId).css("background-color", creColor);
		$(obj.targId).css("padding", "1px 10px 2px 10px");
		$(obj.targId).text(obj.memb.membCretopNm);
	}
}


/**
 *  검색 조건 초기화
 * 
 */

com.fnSerchInit = function(frm){
	$(frm)[0].reset();
	var selArry = $(frm + " select");
	$.each(selArry, function (idx, item){
		$(item).val("");
	});
}
 