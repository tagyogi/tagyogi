/****************************************************************
 *
 * 파일명 : guarCom.js
 * 설  명 : 보증 자바스크립트
 *
 *    수정일      수정자     Version        Function 명
 * ------------    ---------   -------------  ----------------------------
 * 2023.05.22          1.0             최초생성
 *
 *
 * **************************************************************/


//보증업무 공통 함수 시작
var guarCom = new Object();

///////////////////////////////////////////////////////////////////////////////////////////////////////

//동적 보증상품 한도 설정 화면 구성
guarCom.fnSetGuarLimit = function(cdType, tblId, type){

	$("#"+tblId).empty();

	var initHtml = "";

	initHtml += '<colgroup>';
	initHtml += '	<col style="width:80px;">';
	initHtml += '	<col style="width:40px;">';
	initHtml += '	<col style="width:75px;">';
	initHtml += '	<col style="width:75px;">';
	initHtml += '	<col style="width:75px;">';
	initHtml += '</colgroup>';

	initHtml += '<tbody>';
	initHtml += '	<tr>';
	initHtml += '		<th class="tac">상품</th>';
	initHtml += '		<th class="tac">배수</th>';
	initHtml += '		<th class="tac">한도액</th>';
	initHtml += '		<th class="tac">사용액</th>';
	initHtml += '		<th class="tac">사용가능액</th>';
	initHtml += '	</tr>';
	initHtml += '</tbody>';

	$("#" + tblId).append(initHtml);
	$.each(codeList, function(idx, item){
		if(item.cdType === cdType){
			var html = '<tr>';
				html += '<td class="tal"  ><input type="checkbox" name="guarType" id="guarType'+item.cd+'" value="'+item.cd+'" onClick="guarCom.fnGuarTypeSel(this)">&nbsp;'+ item.cdNm;
				html += '</td>'; //상품
				html += '<td class="tac">';
				html += 	'<input type="search" name="doubleNum" id="doubleNum'+item.cd+'" style="width:50px;text-align:right" onkeyup="com.fnOnlyNum(this);guarCom.fnLimiNumSet(this);" maxlength="2" readonly> 배';
				html += '</td>'; //배수
				html += '<td class="tac">';
				html += 	'<input type="search" name="limitAmt" id="limitAmt'+item.cd+'" style="width:100%;text-align:right" onkeyup="com.fnOnlyNum(this);com.makeComma(this);guarCom.fnLimiAmtSet(this)" onblur="guarCom.fnLimitUseChk(this)" maxlength="15" readonly>';
				html += '</td>'; //한도액
				html += '<td class="tac">';
				html += 	'<input type="search" name="useAmt" id="useAmt'+item.cd+'" style="width:100%;text-align:right;"  maxlength="15" readonly>';
				html += '</td>'; //한도액(사용액)
				html += '<td class="tac">';
				html += 	'<input type="search" name="remainAmt" id="remainAmt'+item.cd+'" style="width:100%;text-align:right;"  maxlength="15" readonly>';
				html += '</td>'; //사용가능액

				html += '</tr>';

				if(item.cd === "13")	html += guarCom.fnMakeSubTotal('이행 합계', 'Impl', 'input');	//이행합계 추가
				if(item.cd === "15")	html += guarCom.fnMakeSubTotal('지급 합계', 'Pay', 'input');	//지급합계 추가

			$("#" + tblId + " > tbody:last").append(html);
		}
	});

	var footHtml = '<tfoot>';
		footHtml += 	'<tr>';
		footHtml += 		'<th class="alignC" scope="colgroup">총 합계</th>';
		footHtml += 		'<td class="alignC"><input type="search" name="sumDoubleNum" id="sumDoubleNum" style="width:50px;text-align:right" title="" readonly>&nbsp;배</td>';
		footHtml += 		'<td class="alignC"><input type="search" name="sumLimitAmt" id="sumLimitAmt" style="width:100%;text-align:right" title="" readonly></td>';
		footHtml += 		'<td class="alignC"><input type="search" name="sumUseAmt" id="sumUseAmt" style="width:100%;text-align:right" title="" readonly></td>';
		footHtml += 		'<td class="alignC"><input type="search" name="sumRemainAmt" id="sumRemainAmt" style="width:100%;text-align:right" title="" readonly></td>';
		footHtml += 	'</tr>';
		//footHtml += 	'<tr>';
		//footHtml += 		'<th class="alignC" scope="colgroup">총 한도액</th>';
		//footHtml += 		'<td class="alignC"><input type="search" name="totDoubleNum" id="totDoubleNum" style="width:50px;text-align:right" title="" readonly>&nbsp;배</td>';
		//footHtml += 		'<td class="alignC"><input type="search" name="totLimitAmt" id="totLimitAmt" style="width:100%;text-align:right" title="" readonly></td>';
		//footHtml += 		'<td class="alignC"></td>';
		//footHtml += 		'<td class="alignC"></td>';
		//footHtml += 	'</tr>';
		footHtml += '</tfoot>';
	$("#" + tblId + " > tbody").after(footHtml);

}

//부분합계 영역 html 생성
guarCom.fnMakeSubTotal = function(subNm, subId, type, subSum){
	//var subSum = {
	//	doubleNum: 0,	//이행합계(배수)
	//	limitAmt: 0,	//이행합계(한도액)
	//	useAmt: 0,		//지급합계(배수)
	//	remainAmt: 0	//지급합계(한도액)
	//};

	var html = '';
	//기존
	//html += '<tr>';
	//html += '	<th class="alignC" scope="colgroup">'+subNm+'</th>';


	html += '<tr style="background-color: beige;">';
	html += '	<td class="alignC" style="font-weight: bold; font-size: 12px;">'+subNm+'</td>';	//색상

	if(type === "input"){
		html += '	<td class="alignC"><input type="search" name="sub'+subId+'_sumDoubleNum" id="sub'+subId+'_sumDoubleNum" style="width:50px;text-align:right" title="" readonly>&nbsp;배</td>';
		html += '	<td class="alignC"><input type="search" name="sub'+subId+'_sumLimitAmt" id="sub'+subId+'_sumLimitAmt" style="width:100%;text-align:right" title="" readonly></td>';
		html += '	<td class="alignC"><input type="search" name="sub'+subId+'_sumUseAmt" id="sub'+subId+'_sumUseAmt" style="width:100%;text-align:right" title="" readonly></td>';
		html += '	<td class="alignC"><input type="search" name="sub'+subId+'_sumRemainAmt" id="sub'+subId+'_sumRemainAmt" style="width:100%;text-align:right" title="" readonly></td>';
	}

	if(type === "text"){
		html += '	<td class="alignR">' + subSum.doubleNum + '배</td>';
		html += '	<td class="alignR">' + com.addComma(subSum.limitAmt) + '</td>';
		html += '	<td class="alignR">' + com.addComma(subSum.useAmt) + '</td>';
		html += '	<td class="alignR">' + com.addComma(subSum.remainAmt) + '</td>';
	}
	html += '</tr>';
	return html;
}


//보증상품 한도정보 상세
guarCom.fnSetGuarLimitView = function(cdType, tblId, guarList){
	$("#"+tblId).empty();

	var initHtml = "";

	initHtml += '<colgroup>';
	initHtml += '	<col style="width:110px;">';
	initHtml += '	<col style="width:60px;">';
	initHtml += '	<col style="width:120px;">';
	initHtml += '	<col style="width:110px;">';
	initHtml += '	<col style="width:130px;">';
	initHtml += '</colgroup>';
	initHtml += '<tbody>';
	initHtml += '	<tr>';
	initHtml += '		<th class="tac">상품</th>';
	initHtml += '		<th class="tac">배수</th>';
	initHtml += '		<th class="tac">한도액</th>';
	initHtml += '		<th class="tac">사용액</th>';
	initHtml += '		<th class="tac">사용가능액</th>';
	initHtml += '	</tr>';
	initHtml += '</tbody>';

	$("#" + tblId).append(initHtml);

	var totNum = 0; //총배수
	var totLimit = 0; //총한도
	var totUse = 0; //총사용
	var totRemain = 0; //총가능

	//이행합계(배수), 이행합계(한도액), 지급합계(배수), 지급합계(한도액)
	var subImplSum = { doubleNum: 0, limitAmt: 0, useAmt: 0, remainAmt: 0 };
	var subPaySum = { doubleNum: 0, limitAmt: 0, useAmt: 0, remainAmt: 0 };
	var implList = ['11', '12', '13'];	//이행 범위
	var payList = ['14', '15'];			//지급 범위

	$.each(codeList, function(idx, item){
		if(item.cdType === cdType){
			var getNum = 0;
			var getLimit = 0;
			var getUse = 0;
			var getRemain = 0;
			if(guarList !== undefined){
				var guarTypes = com.fnNvlChk(guarList.guarTypes, "").split(",");

				getNum 		= com.rmComma(guarList["doubleNum"+item.cd]);
				getLimit 	= com.rmComma(guarList["limitAmt"+item.cd]);
				getUse 		= com.rmComma(guarList["useAmt"+item.cd]);
				getRemain 	= com.rmComma(guarList["remainAmt"+item.cd]);

				if(implList.includes(item.cd)) {	//선택한 값만 배수에 더함

					if(guarTypes.indexOf(item.cd) > -1){
						subImplSum.doubleNum 	+= Number(com.rmComma(guarList["doubleNum"+item.cd]));
					}
					subImplSum.limitAmt 	+= Number(com.rmComma(guarList["limitAmt"+item.cd]));
					subImplSum.useAmt 		+= Number(com.rmComma(guarList["useAmt"+item.cd]));
					subImplSum.remainAmt 	+= Number(com.rmComma(guarList["remainAmt"+item.cd]));
				}

				if(payList.includes(item.cd)) {	//선택한 값만 배수에 더함
					if(guarTypes.indexOf(item.cd) > -1){
						subPaySum.doubleNum 	+= Number(com.rmComma(guarList["doubleNum"+item.cd]));
					}
					subPaySum.limitAmt 	+= Number(com.rmComma(guarList["limitAmt"+item.cd]));
					subPaySum.useAmt 		+= Number(com.rmComma(guarList["useAmt"+item.cd]));
					subPaySum.remainAmt 	+= Number(com.rmComma(guarList["remainAmt"+item.cd]));
				}
			}
			if(guarList !== undefined){
				if(guarTypes.indexOf(item.cd) > -1){	//선택한 값만 배수에 더함
					totNum 		+= Number(getNum);
				}
			}else{
				totNum 		+= Number(getNum);
			}

			totLimit 	+= Number(getLimit);
			totUse 		+= Number(getUse);
			totRemain 	+= Number(getRemain);

			var html = '<tr>';
				html += '	<td class="tal"  >'+ item.cdNm + '</td>'; //상품
				html += '	<td class="tar">' + Math.round(getNum) + '배</td>'; //배수
				html += '	<td class="tar">' + com.addComma(getLimit) + '</td>'; //한도
				html += '	<td class="tar">' + com.addComma(getUse) + '</td>'; //사용
				html += '	<td class="tar">' + com.addComma(getRemain) + '</td>'; //잔액
				html += '</tr>';

			if(item.cd === implList.at(-1))	html += guarCom.fnMakeSubTotal('이행 합계', 'Impl', 'text', subImplSum);	//이행합계 추가
			if(item.cd === payList.at(-1))	html += guarCom.fnMakeSubTotal('지급 합계', 'Pay', 'text', subPaySum);		//지급합계 추가

			$("#" + tblId + " > tbody:last").append(html);
		}
	});

	var footHtml = '<tfoot>';
		footHtml += 	'<tr>';
		footHtml += 	'	<th class="tac" scope="colgroup">합계</th>';
		footHtml += 	'	<td class="tar">' + totNum + '배</td>';
		footHtml += 	'	<td class="tar" id="totalLimitAmt">' + com.addComma(totLimit) + '</td>';
		footHtml += 	'	<td class="tar">' + com.addComma(totUse) + '</td>';
		footHtml += 	'	<td class="tar">' + com.addComma(totRemain) + '</td>';
		footHtml += 	'</tr>';
		footHtml += '</tfoot>';
	$("#" + tblId + " > tbody").after(footHtml);

}

//조합원 및 약정정보 콜백
guarCom.fnMembInfoSet = function(resData){
 	//console.log(resData.memb);

 	//화면처리
	com.fnSetKeyVal(resData.memb);
	com.fnSetKeyVal(resData.contr);
	com.fnSetKeyVal(resData.amtData);

	var data = resData.amtData;

	$("#ele_limitAmt").text((com.addComma(data.limitAmt)));
	$("#ele_useAmt").text((com.addComma(data.useAmt)));
	$("#ele_curAmt").text(com.addComma(((parseInt(data.limitAmt) - parseInt(data.useAmt)).toString())));
};


//보증신청정보(신청자) selectBox 생성
guarCom.fnSelCpSelectBox = function(persList, value){
	var tag = '<option value="">직접입력</option>';
	$.each(persList, function(idx, item){
		tag += '<option value="'+item.reqNm+'" data-tel="'+item.reqTel+'" data-seq="'+item.reqSeq+'" data-type="'+item.reqType+'" >'+ item.reqTypeNm + item.reqNm + '</option>';
	});
	$("#selCp").html(tag);

	//클릭 이벤트 추가
	$("#selCp").on("change", function() {
		//빈값(직접입력)이면 readonly 처리
		var showYn = true;
		if($(this).val() === "") showYn = false;
		$("#reqNm").prop("readonly", showYn);	//신청자명
		$("#reqTel").prop("readonly", showYn);	//신청자연락처

		//신청자명, 신청자연락처 값 추가
		$("#reqNm").val($(this).val());			//신청자명
		$("#reqTel").val($(this).find("option:selected").data("tel"));		//신청자연락처
		$("#reqType").val($(this).find("option:selected").data("type"));	//신청자타입(CEO, PERSON)
		$("#reqSeq").val($(this).find("option:selected").data("seq"));		//신청자순번
	});


	//변수값 선택
	if(value !== undefined && value !== ""){
		$("#selCp > option[value='"+value+"']").attr("selected", "selected");
	}

	var showYn = true;
	if($("#selCp").val() === "") showYn = false;
	$("#reqNm").prop("readonly", showYn);	//신청자명
	$("#reqTel").prop("readonly", showYn);	//신청자연락처
}



/////////////////////////////////////////////// 보증발급관리

var tmpGuarExpDt;	//마감일
//tmpGuarExpDt값 초기화
guarCom.fnInitTempGuarExpDt = function(){
	tmpGuarExpDt = com.fnDateGet($("#guarExpDt").val(), -1 * $("input[name=guarAddCd]:checked").val());
}

//보증수수료율 조회
guarCom.fnSelectGuarRate = function(calYn = "Y"){ //calYn 파라미터 없이오면 디폴트는 "Y"
	//조합원번호, 신용도, 공공/민간 select(조합원 검색, 피공제기관 검색 먼저 조회 해야 조회)
	//console.log($("#sMembNo").val() + " / " + $("#finalGrade").val() + " / " +  $("#orderCd option:selected").val() + " / " + $("#reqDt").val().replaceAll("-","").length)
	if($("#sMembNo").val() === "" || $("#finalGrade").val() === "" )	return;	//|| $("#orderCd option:selected").val() == "" : 민간/공공 검색조건 제거

	//신청일자
	if($("#reqDt").val().replaceAll("-","").length !== 8)	return;

	//, cdNm: $("#orderCd option:selected").val() : asis에 있는데 쿼리 검색조건에 없어서 일단 제외 --> 검색조건 제거로 변경(민간기관, 공공기관 )
	var selectParam = { appDt: $("#reqDt").val(), finalGrade: $("#finalGrade").val(), membNo: $("#sMembNo").val(), guarType: guarType, mortCnt: $("#mortCnt").val() };
	var callBackFun = guarCom.fnSelectRateCallBack;	//조회후 보증료 계산
	if(calYn === "N")	callBackFun = guarCom.fnSelectRateNoCalcCallBack;	//조회후 보증료 계산 호출만 없음

	obj = {url:"/guar/selectGuarTypeRate.do", param:selectParam, callBack:callBackFun, msgYn:"N", sessYn:"Y", loadYn:"Y"};
	com.fnAjaxDef(obj);
}

//요율 조회 콜백(요율 조회 후 계산 o)
var tempCommRate = "";
guarCom.fnSelectRateCallBack = function(resData){
	//console.log("요율 조회 후 계산 함 (Y)");
	//console.log(resData);
	if(com.fnIsEmpty(resData.data))	return;

	$("#orgRate").val(resData.data.orgRate);			//기본요율
	if(com.fnIsEmpty(resData.data.commRate)){
		$("#commRate").val(resData.data.orgRate);		//적용요율
	}else{
		$("#commRate").val(resData.data.commRate);		//적용요율
	}
	tempCommRate = $("#commRate").val();	//수기입력 체크시 원복할 값

	$("#exemLimitAmt").val(resData.data.exemLimitAmt);	//출자면제보증한도

	//보증료 계산
    guarCom.fnCalcCommAmt();
}

//요율 조회 콜백(요율 조회 후 계산 x)
guarCom.fnSelectRateNoCalcCallBack = function(resData){
	//console.log("요율 조회 후 계산 안함(N)");
	//console.log(resData);
	$("#orgRate").val(resData.data.orgRate);			//기본요율
	if(com.fnIsEmpty(resData.data.commRate)){
		$("#commRate").val(resData.data.orgRate);		//적용요율
	}else{
		$("#commRate").val(resData.data.commRate);		//적용요율
	}
	tempCommRate = $("#commRate").val();	//수기입력 체크시 원복할 값

	$("#exemLimitAmt").val(resData.data.exemLimitAmt);	//출자면제보증한도
}

//
guarCom.fnSetReleDt = function(){
	var releDt = $("#releDt").val();		//입찰마감일
    if(releDt.length !== 10) return;

	if(guarType === "11"){
	    // 입찰일로부터 30일계산
	    $("#guarBegDt").val(com.fnDateGet($("#releDt").val(), -1));
	    $("#guarExpDt").val(com.fnDateGet($("#guarBegDt").val(), 31));
	    tmpGuarExpDt = $("#guarExpDt").val();

	    //보증일수계산
		guarCom.fnSetGuarDays();
    }else if(guarType === "13"){
		$("#releBegDt").val(releDt);
		$("#guarBegDt").val(releDt);

		//하자담보기간일수 계산
		guarCom.fnSetReleDays();
	}
}

//보증일수계산
guarCom.fnSetGuarDays = function(){
	var guarBegDt = $("#guarBegDt").val();
    var guarExpDt = $("#guarExpDt").val();

	//기재변경 수정일때
	if(guarBegDt === undefined || guarExpDt === undefined)	return;

    // 기간이 모두 정확할때 계산
    if(guarBegDt.length !== 10 || guarExpDt.length !== 10) {
		$("#guarDays").val("");
		return;
	}

    $("#guarDays").val(com.fnDateTerm(guarBegDt, guarExpDt));

	//보증료 계산
	if(guarType === "14")	guarCom.fnSetContrInterAmt();	//선금급은 약정이자액 계산 먼저
    else					guarCom.fnCalcCommAmt();
}


//보증료 계산(등록화면)
guarCom.fnCalcCommAmt = function(){
    var guarBegDt = $("#guarBegDt").val();
	var guarExpDt = $("#guarExpDt").val();

	//console.log("유효성 체크 시작!!!");

    // 기간이 모두 정확할때 계산
    if(guarBegDt.length !== 10 || guarExpDt.length !== 10) {
        guarCom.fnCommInit();	//수수료 초기화
        return;
    }

    // 보증금액이 존재할때 수수료계산 시작
    if($("#guarAmt").val() === "0" || $("#guarAmt").val() === "") {
        guarCom.fnCommInit();
        //$("#guarAmt").focus();
        return;
    }

	//적용요율 없을때
    if($("#commRate").val() === "") {
		if($("#sMembNo").val() === "" || $("#finalGrade").val() === "" ){
			alert("조합원 정보가 존재하지 않습니다\n조합원 검색 후 진행하시기바랍니다.");
		}else{
			guarCom.fnSelectGuarRate();
		}
		return;
    }

    if($("#guarClassCd").val() === "60") {
        $("#commCalc").val(guarCom.fnGetMinCommAmt());
        return;
    }
	//console.log("유효성 체크 종료!!!");
	//0.변경 등록이면 변경등록 로직(menuCd 1030217,1030218 --> 변경등록, 변경등록 수정)
    if( (paramMap.menuCd === "1030217" || paramMap.menuCd === "1030218") && guarType !== "11" && guarType !== "13"){
		guarCom.fnCalcCommAmtMod();	//변경등록 로직으로
		return;
	}

	//console.log("1. 일반등록 로직");

    //1. 보증료 계산에 필요한 값
    var guarAmt  = guarCom.fnToNumber($("#guarAmt").val());	//보증금액
    var commRate = parseFloat(Number($("#commRate").val() / 100).toFixed(5));		//적용요율
    var guarDays = Number($("#guarDays").val());			//보증일수
    var commAmt  = guarAmt * commRate;
    if(guarType !== "11")	commAmt = commAmt * guarDays / 365;

	//2. 결과(화면)에 표시될 최종값 변수
	var pgCommAmtVal = com.addComma(guarCom.fnGetPayGateCommAmt(Math.floor(commAmt)));	//PG수수료
	var commCalcVal = "";	//보증료산식
	var commAmtVal = 0;		//보증료

	//3. 보증료 최저 계산
	if(commAmt < guarCom.fnGetMinCommAmt()) 	commAmtVal = guarCom.fnGetMinCommAmt();	//최저수수료
	else										commAmtVal = Math.floor(commAmt);

	//4. 보증료산식
	var repayAmt = $("#repayAmt").val();										//선금금액
	var contrInterRate = parseFloat(Number($("#contrInterRate").val() / 100).toFixed(5));	//약정이자율

	if(guarType === "11"){
		commCalcVal  = com.addComma(guarAmt) + " X " + commRate + " = " +com.addComma(Math.floor(commAmt));	//보증료산식
	}else if(guarType === "14"){	//이행선금급
		commCalcVal = "{ " + com.addComma(repayAmt) + " + ( " + com.addComma(repayAmt) + " X " + contrInterRate + " / 365 X" + guarDays + " ) } X " + commRate + " X " + guarDays + " / 365 = " + com.addComma(Math.floor(commAmt));
	}else{
		commCalcVal = com.addComma(guarAmt) + " X " + commRate + " / 365 X " + guarDays + " = " + com.addComma(Math.floor(commAmt));
	}

	//보증료 PG 포함인지 아닌지 구분(체크박스 이벤트와 동일하게 변경) -> 기존 : pg 수수료도 반올림, 변경 : 보증료 반올림 후 pg 수수료 더함
	if($("input[name=pgAddYn]").is(":checked")) 	commAmtVal = com.addComma( guarCom.fnToNumber(pgCommAmtVal) + guarCom.fnTrunc(guarCom.fnToNumber(commAmtVal), 100, "round") );
	else 											commAmtVal = com.addComma( guarCom.fnTrunc(commAmtVal, 100, "round") ); // 2014.03.07 : 10원단위에서 반올림하여 100원단위 징수

	//화면 표시
    $("#pgCommAmt").val(pgCommAmtVal);	//PG 수수료
    $("#commCalc").val(commCalcVal);	//보증료 산식
	$("#commAmt").val(commAmtVal);		//보증료

	//숨김값(신규 추가)
	$("#calcCommAmt").val(Math.floor(commAmt));		//계산 보증료
}

//PG 수수료 계산
guarCom.fnGetPayGateCommAmt = function(commAmt){
	//파라미터명(commAmt)은 고정(DB값)
	var cm13CodeList = codeList.filter(item => item.cdType === "CM13");
	var returnVal;
	var chkVal = true;

	$.each(cm13CodeList, function(idx, item) {
		if(guarCom.fnCommAmtEval(item.etcDesc, commAmt)) {
			returnVal = guarCom.fnCommAmtEval(item.remark, commAmt);
			if(chkVal) chkVal = false;
		}
	});

	if(chkVal)	returnVal = 5000;	//아무것도 해당 안되면
	return guarCom.fnTrunc(returnVal,10);
}

//공통코드 표현식 리턴 함수 생성자(eval() 대체 사용)
//guarCom.fnCommAmtEval("commAmt > 13300 && commAmt <= 98800", 100000) = false
//guarCom.fnCommAmtEval("commAmt > 98800 && commAmt <= 999850", 100000) = true
//guarCom.fnCommAmtEval("5000", 100000) = 5000
//guarCom.fnCommAmtEval("(commAmt * 1.2 / 100) + ((commAmt + (commAmt * 1.2 / 100)) * 1.2 / 100) - (commAmt * 1.2 / 100) / 15000", 15000) = 180
guarCom.fnCommAmtEval = function(expr, commAmt) {
  	return new Function('commAmt', 'return ' + expr)(commAmt);
}

//수수료 초기화
guarCom.fnCommInit = function(){
	 $("#commAmt, #curCommAmt").val("");		//보증료, 재출산보증료(변경등록)
}

//최저 수수료(기본 수수료)
guarCom.fnGetMinCommAmt = function(defaltYn = "N") {	//파라미터 Y로 보냈을때는 무조건 7000원 리턴(기재변경 최저수수료 조회시 사용)
	//payPreDt 없는거 방지( payPreDt(납입예정일자)는 updateGuarPayToDocNo 보증번호 발번후 들어가고 수정은 발번 이후 불가능해서 20171113 이전 3000은 작동 안하는걸로 보임 )
	var payPreDt = com.fnNvlChk($("#payPreDt").val(), "");

	//등록페이지는 기본 7000, 수정페이지는 날짜기준
    if(payPreDt === "" || payPreDt.replaceAll("-","") >= "20171113" || defaltYn === "Y") return 7000;
    else return 3000;
}

//보증금액설정
guarCom.fnSetGuarAmt = function(){

	if(guarType === "14"){
		var guarRate = guarCom.fnToNumber($("#guarRate").val());	// 선금금율
	    var contrAmt = guarCom.fnToNumber($("#contrAmt").val());	// 계약금액

	    if(guarRate === 0 || contrAmt === 0) return;

	    var repayAmt = guarRate * contrAmt / 100;
	    $("#repayAmt").val(com.addComma(Math.floor(repayAmt)));

	    // 약정이자액 계산
	    guarCom.fnSetContrInterAmt();
	}else{
		//if($("#contrAmt").val() === "" || $("#guarRate").val() === "") return;
	    var contrAmt = guarCom.fnToNumber($("#contrAmt").val());		// 계약금액(입찰금액)
	    var guarRate = guarCom.fnToNumber($("#guarRate").val());		// 보증금율
	    var guarAmt = Math.floor(contrAmt * guarRate / 100);		// 보증금액계산옵션

	    $("#guarAmt").val(com.addComma(guarAmt));	//보증금액

	    // 수수료계산호출
	    guarCom.fnCalcCommAmt();
	}
}

//amt원절삭(amt: 10,100등 10의 배수)
//ex) guarCom.fnTrunc(111,10) = 110
//ex) guarCom.fnTrunc(1111,100) = 1100
//ex) guarCom.fnTrunc(1171,100,"round") = 1200
//ex) guarCom.fnTrunc(1121,100,"ceil") = 1200
guarCom.fnTrunc = function(num, amt, type = "floor"){	//type 파라미터가 없으면 디폴트 type = "floor"
	if(!num || !amt)	return 0;
	if(type === "floor")			return Math.floor(num/amt) * amt;	//버림
	else if(type === "round")	return Math.round(num/amt) * amt;	//반올림
	else if(type === "ceil")		return Math.ceil(num/amt) * amt;	//올림
};

//콤마제거 후 숫자로
guarCom.fnToNumber = function(str) {
    if(com.fnIsEmpty(str)) str = 0;
    return Number(com.rmComma(str));
}

//PG수수료 포함 체크박스 클릭 이벤트
guarCom.fnPgAddYnChange = function(){
    var commAmt   = guarCom.fnToNumber($("#commAmt").val());
    var pgCommAmt = guarCom.fnToNumber($("#pgCommAmt").val());
    if(commAmt === 0 || pgCommAmt === 0) return;

    if($("input[name=pgAddYn]").is(":checked")) 	$("#commAmt").val(com.addComma(commAmt + pgCommAmt));
    else 											guarCom.fnCalcCommAmt();
}

//심사 승인자 조회
guarCom.fnSelectEmployList = function(){
	var selectParam = { sUserStat: "10", sExamAt: "Y" };
	var obj = {url:"/empl/selectEmplList.do", param:selectParam, callBack:guarCom.fnSelectEmployCallBack, msgYn:"N", sessYn:"Y", loadYn:"Y"};
	com.fnAjaxDef(obj);
}

//심사 승인자 조회 콜백
guarCom.fnSelectEmployCallBack = function(resData){
	//console.log(resData);
	var tag = '<option value="">선택</option>';
	$.each(resData.data, function(idx, item){
		tag += '<option value="'+item.userNm+'">'+ item.userNm + '</option>';
	});
	$("#examNm").html(tag);
}

//전자공고 조회 팝업
guarCom.fnG2bSearch = function(){
	if($("#publicNo").val() === "") {
        alert("공고번호를 입력해 주십시오.");
        $("#publicNo").focus();
        return;
    }
	var titl = "통합계약정보조회";
	var menuCd = "1070717";	//g2b 구전산				// com/serchG2bContr
	//menuCd = "1070735";	//mgwr 차세대(계약)		// com/serchMgwrContr
	if($("#guarType").val() === "11"){
		titl = "통합입찰정보조회";
		menuCd = "1070714";	//g2b 구전산				// com/serchG2bBid
		//menuCd = "1070734";	//mgwr 차세대(입찰)		// com/serchMgwrBid
	}
	com.dialpopupOpen(titl, "/index/pageCall.do?menuCd=" + menuCd, "800", "740");
}


//전자공고 조회 팝업 (등록시 누락 될 경우 처리)
guarCom.fnG2bSearchDirect = function(){

	param = {guarType:$("#guarType").val(), publicNo:$("#publicNo").val(), membNo:$("#membNo").val(), bizNo:$("#ele_bizNo").text()};
	//console.log(param);
	obj = {url:"/guarElect/serchG2b.do", param:param, msgYn:"N", sessYn:"Y", loadYn:"Y"};
	com.fnAjaxDef(obj);

}


// 계약일수설정
guarCom.fnSetContrDays = function(){
    var contrBegDt = $("#contrBegDt").val();
    var contrExpDt = $("#contrExpDt").val();
    if(contrBegDt.length !== 10 || contrExpDt.length !== 10) {
		$("#contrDays").val("");	//계약일수 초기화
		return;
	}

	//계약일수 계산
	$("#contrDays").val(com.fnDateTerm(contrBegDt,contrExpDt));
	if(guarType !== "13"){
		// 계약일자를 보증일자로
		if(guarType !== "14" ){	//선금보증은 보증 시작일 변경하면 안된다고함
	    	$("#guarBegDt").val($("#contrBegDt").val());
	    }

	    //기존로직(가산기간 제외)
	    //$("#guarExpDt").val($("#contrExpDt").val());
		//tmpGuarExpDt = $("#guarExpDt").val();

		//변경(2023-12-28 요청으로 수정)
		var guarAddVal = guarCom.fnToNumber($("input[name='guarAddCd']:checked").val());	//보증기간가산 값(0 or 60)
	    var guarExpDt = com.fnDateGet(contrExpDt, guarAddVal);
	    $("#guarExpDt").val(guarExpDt);
		tmpGuarExpDt = guarExpDt;

	    // 보증일수계산호출
	    guarCom.fnSetGuarDays();
    }
}

//보증금율 자동계산
guarCom.fnSetGuarRate = function(){
	//보증금율 = 보증금액 / 계약금액 * 100
    var contrAmt 	= guarCom.fnToNumber($("#contrAmt").val());	//계약금액
    var guarAmt 	= guarCom.fnToNumber($("#guarAmt").val());	//보증금액
	var guarRate 	= Math.round(guarAmt / contrAmt * 100);

    if(guarType === "14"){	//이행선금급
		var repayAmt 	= guarCom.fnToNumber($("#repayAmt").val());	//선금금액
		guarRate 		= Math.round(repayAmt / contrAmt * 1000);
		guarRate		= (guarRate / 10).toFixed(1);
	}

    $("#guarRate").val(guarRate);
}

//하자담보기간일수 계산
guarCom.fnSetReleDays = function(){
    var releBegDt = $("#releBegDt").val();	//하자담보기간 시작일
    var releEndDt = $("#releEndDt").val();	//하자담보기간 종료일

    // 하자담보종료일 >> 보증기간
    if(releBegDt.length === 10)		$("#guarBegDt").val($("#releBegDt").val());
    if(releEndDt.length === 10)		$("#guarExpDt").val($("#releEndDt").val());

    // 기간이 모두 정확할때 계산
    if(releBegDt.length !== 10 || releEndDt.length !== 10) return;

    $("#releDays").val(com.fnDateTerm(releBegDt, releEndDt));

    // 보증일수계산호출
    guarCom.fnSetGuarDays();
}

//약정이자액 계산
guarCom.fnSetContrInterAmt = function(){
	var repayAmt 		= guarCom.fnToNumber($("#repayAmt").val());	//선금금액
    var guarDays 		= guarCom.fnToNumber($("#guarDays").val());	//보증일수

    if(repayAmt === 0 || guarDays === 0){
        guarCom.fnCommInit();
        return;
    }

    // 약정이자액 = 선금금액 X 약정이자율 / 365 * 보증일수
    var contrInterRate = parseFloat(Number(guarCom.fnToNumber($("#contrInterRate").val()) / 100).toFixed(5)) ;
    var contrInterAmt  = Math.floor(repayAmt * contrInterRate / 365 * Number(guarDays));

    $("#contrInterAmt").val(com.addComma(guarCom.fnTrunc(contrInterAmt, 10, "ceil")));

    // 보증금액
    var guarAmt = Math.floor(repayAmt + contrInterAmt);
    $("#guarAmt").val(com.addComma(guarCom.fnTrunc(guarAmt, 10, "ceil")));

    // 수수료계산호출
    guarCom.fnCalcCommAmt();
}

//공공기관지원사업 여부 클릭 이벤트
guarCom.fnKoccaChange = function(){
	if($("input[name=koccaYn]").is(":checked"))	$("#commRate").val("0.51");
    else 										guarCom.fnSelectGuarRate();	//보증수수료율 조회(순서 중요 : 조합원 정보 필요, 공공/민관 선택 필요)

	// 수수료계산호출
    guarCom.fnCalcCommAmt();
}


//보증료 계산(변경등록) - 진행시 등록페이지 로직 제거
guarCom.fnCalcCommAmtMod = function(){
	//console.log("2. 변경등록 로직");

    //보증료 계산에 필요한 값
    var guarAmt  = guarCom.fnToNumber($("#guarAmt").val());
    var commRate = parseFloat(Number($("#commRate").val() / 100).toFixed(5));
    var guarDays = Number($("#guarDays").val());
    var commAmt  = guarAmt * commRate;
    if(guarType !== "11")	commAmt = commAmt * guarDays / 365;

	//결과(화면)에 표시될 최종값 변수
	var pgCommAmtVal = com.addComma(guarCom.fnGetPayGateCommAmt(Math.floor(commAmt)));	//PG수수료
	//console.log("pgCommAmt : " + pgCommAmtVal);
	var commCalcVal = "";	//보증료산식
	var commAmtVal = 0;		//보증료

	//수수료
	if(commAmt < guarCom.fnGetMinCommAmt()) 	commAmt = guarCom.fnGetMinCommAmt();	//최저수수료
	else										commAmt = Math.floor(commAmt);

	//산식 반올림 처리
	commAmt = guarCom.fnTrunc(commAmt, 100, "round");

	//변경신청시 처리사항
    $("#curCommAmt").val(com.addComma(commAmt));	//재산출보증료

    if(guarType !== "32" && ($("#contrAmt").val() === "0" || $("#contrAmt").val() === "")){
        alert("계약금액을 확인해주십시오.");
        $("#contrAmt").focus();
        return;
    }

    var befGuarAmt 	= guarCom.fnToNumber($("#befGuarAmt").val());		//(기존)보증금액
    var befGuarDays = guarCom.fnToNumber($("#befGuarDays").val());	//(기존)보증일수
    var befCommAmt 	= guarCom.fnToNumber($("#befCommAmt").val());		//(기존)보증료
    //(기존)보증료가 최저수수료가 안될경우 최저수수료로 설정
    if(guarType === "12"){
    	if(befCommAmt < guarCom.fnGetMinCommAmt())	befCommAmt = guarCom.fnGetMinCommAmt();
	}
    var curCommAmt 	= guarCom.fnToNumber($("#curCommAmt").val());		//(재산출)보증료
    var guarAmt 	= guarCom.fnToNumber($("#guarAmt").val());		//(변경)보증금액

    guarCom.fnPayReturn("n");		//보증료납입정보

	var tmpCommAmt = 0;

	//console.log("befCommAmt : " + befCommAmt + " / commAmt(10의자리 반올림): " + guarCom.fnTrunc(commAmt, 100, "round") );
	//console.log(commAmt - befCommAmt);


    if(befCommAmt <= commAmt){
        //console.log("금액증액 or 같음");
        // 금액증액
        tmpCommAmt = commAmt - befCommAmt;
        if(tmpCommAmt > 0 && tmpCommAmt < 10) tmpCommAmt = 0;
        //else if(tmpCommAmt >= 10 && tmpCommAmt < getMinCommAmt()) tmpCommAmt = getMinCommAmt();		//2021.02.26  변경신청시 최소수수료 미만 계산된금액으로 변경

        if(guarType === "12") 		pgCommAmtVal = com.addComma(guarCom.fnGetPayGateCommAmt(commAmt));	//12
        else if(guarType === "14")	pgCommAmtVal = com.addComma(guarCom.fnGetPayGateCommAmt(tmpCommAmt));	//14
        else if(guarType === "15" || guarType === "21")	pgCommAmtVal = 0;	//15

		//console.log("guarType : " + guarType + " / pgCommAmtVal: " + pgCommAmtVal + " / tmpCommAmt:" + tmpCommAmt + " / commAmt: " + commAmt );

        commAmtVal = com.addComma(tmpCommAmt);
        commCalcVal = "( " + com.addComma(guarAmt) + " X "+  commRate  + " / 365 X "+ guarDays  + " = "+ com.addComma(commAmt) +" ) - "  + com.addComma(befCommAmt) + " = " +  com.addComma(Number(commAmt) - Number(befCommAmt));

		//보증료가 감소하면 환불 제거
        //$("#retCommAmt").val("");
    } else if(befCommAmt > commAmt) {
        //console.log("금액감액");
        // 금액감액
    	guarCom.fnPayReturn("y");

        tmpCommAmt = befCommAmt - commAmt;
        if(tmpCommAmt < guarCom.fnGetMinCommAmt() && tmpCommAmt > 0) tmpCommAmt = 0;

        //최소수수료
        if(tmpCommAmt === 0) commAmtVal = 0;
        else				commAmtVal = "-" + com.addComma(tmpCommAmt)

        pgCommAmtVal = 0;
        commCalcVal = com.addComma(befCommAmt) + "- { (" + com.addComma(guarAmt) + " X " + commRate + " / 365 X " + guarDays + ") = " + com.addComma(commAmt) + " } = -" + com.addComma(Number(befCommAmt) - Number(commAmt));

        //보증료가 감소하면 환불 추가
        //$("#retCommAmt").val(guarCom.fnTrunc(tmpCommAmt, 100, "round"));
    }

	//contrView, debtView, epsrView, payView, perfView에 page에 view 값이 있음 - 상세만 계산 안하게 처리되는중 -> 조건 필요없어서 제거
    //if(typeof($("#page").val()) === "undefined" ) guarCom.fnGuarClassCdChange();
    guarCom.fnGuarClassCdChange();
	if(guarType === "12"){
		if($("#guarReqCd").val() === "3") $("#commCalc").val("");	//재발급
	}

	//보증료 PG 포함인지 아닌지 구분(체크박스 이벤트와 동일하게 변경- 체크시 반올림 삭제)
	if($("input[name=pgAddYn]").is(":checked")) 	commAmtVal = com.addComma( guarCom.fnToNumber(pgCommAmtVal) + guarCom.fnToNumber(commAmtVal) );
	else 											commAmtVal = com.addComma( guarCom.fnToNumber(commAmtVal) ); // 2014.03.07 : 10원단위에서 반올림하여 100원단위 징수

	//기재변경일때는 보증료 산식이 필요없음.
	if(guarType === "12" || guarType === "13"){		//guarType 13은 여기에 없어도 됨
	    if($("#guarClassCd").val() === "60"){
			commCalcVal = "";
			pgCommAmtVal = 0;
			commAmtVal = 0;
	    }
	}
	//console.log("commAmtVal :" + commAmtVal);
	//console.log("retCommAmt : " + $("#retCommAmt").val());
	//if(pgCommAmtVal === 0)	pgCommAmtVal = "";
	//화면 표시
    $("#pgCommAmt").val(pgCommAmtVal);	//PG 수수료
    $("#commCalc").val(commCalcVal);	//보증료 산식
	$("#commAmt").val(commAmtVal);		//보증료

	//숨김값(신규 추가)
	$("#calcCommAmt").val(Math.floor(commAmt));		//계산 보증료
}



// 청구,환불 change
//type : 빈값일 시 입력 영역 text 변경
//type : dtl 시 상세 영역 text 변경
guarCom.fnPayReturn = function(yn, type = ""){	//파라미터 type 없을시 디폴트값 : ""
	try {
	    if(yn === "y") {
			//보증료 미수처리 입력 영역 th 이름 변경
	        $("#txt_commTxt"+type).text("환불보증료");
	        $("#txt_realCommTxt"+type).text("환불금액");
	        $("#txt_payTxt"+type).text("환불상태");
	        $("#txt_commDtTxt"+type).text("환불마감일자");
	        $("#txt_payDtTxt"+type).text("환불일자");
	        $("#txt_payNmTxt"+type).text("환불예금주");
	        $("#txt_payBankTxt"+type).text("환불은행");
	        $("#txt_payAcctTxt"+type).text("환불계좌");
	        $("#txt_payWayTxt"+type).text("환불방법");

			//계좌 선택 변경
	        $("#selBank").hide();		//입금계좌(콘텐츠조합 계좌)
	        $("#retSelBank").show();	//환불계좌(은행전체)
	        $("#payBank").val($("#retSelBank option:selected").val());
	        $("#payAcct").val("");
	        $("#payAcct").attr("readonly", false);
	    } else {
			//보증료 미수처리 입력 영역 th 이름 변경
	        $("#txt_commTxt"+type).text("보증료");
	        $("#txt_realCommTxt"+type).text("납입금액");
	        $("#txt_payTxt"+type).text("입금상태");
	        $("#txt_commDtTxt"+type).text("청구일자");
	        $("#txt_payDtTxt"+type).text("입금일자");
	        $("#txt_payNmTxt"+type).text("입금자");
	        $("#txt_payBankTxt"+type).text("입금은행");
	        $("#txt_payAcctTxt"+type).text("입금계좌");
	        $("#txt_payWayTxt"+type).text("입금방법");

			//계좌 선택 변경
	        $("#selBank").show();		//입금계좌(콘텐츠조합 계좌)
	        $("#retSelBank").hide();	//환불계좌(은행전체)
	        $("#payBank").val($("#selBank option:selected").val().split(",")[0]);	//bankCd
	        $("#payAcct").val($("#selBank option:selected").val().split(",")[1]);	//계좌번호
	        $("#payAcct").attr("readonly", true);
	    }
	}
	catch(e){console.log("보증료 미수처리 th 변경 에러")}
}


//심사 처리상태 selectBox
guarCom.fnProcStatSelectBox = function(value = "22"){	//value 디폴드 22(심사승인)
	//기존 옵션 삭제
	$("#procStat option").remove();

	var showTypeObj = ["10","11","21","22"];	//보여줄 심사 타입
	$.each(codeList, function(idx, item){
		if(item.cdType === "GR05" && showTypeObj.includes(item.cd)){
			$("#procStat").append("<option value='"+item.cd+"'>"+item.cdNm+"(" + item.cd + ")</option>");
		}
	});

	//변수값 선택
	$("#procStat > option[value="+value+"]").attr("selected", "selected");
}

//발급구분변경(전자공고 영역 변경)
guarCom.fnGuarIssueChg = function(value, area = ""){
	var g2bNone = "";
	var g2b = ""
	if(area != "")	{
		g2bNone = "#"+area +" ";
		g2b = "#"+area +" ";
	}
	g2bNone += "#ele_g2bNone";
	g2b += "#ele_g2b";

	if(value === "G"){
		$(g2bNone).hide();
		$(g2b).show();
	}else{
		$(g2bNone).show();
		$(g2b).hide();
	}
}


//추가보증변경분 자동 설정
//14,21, ""
guarCom.fnGuarClassCdChange = function(){
	//console.log("fnGuarClassCdChange 시작")
	var classCd = "";

    var befGuarDays = guarCom.fnToNumber($("#befGuarDays").val());
    var guarDays = guarCom.fnToNumber($("#guarDays").val());
    //console.log("기간 변화 체크 시작 befGuarDays: "+befGuarDays + " / guarDays: " + guarDays);
    if(befGuarDays < guarDays){	//기간연장
    	//console.log("기간연장")
        $("#chgDtUp").prop("checked", true);
        $("#chgDtDn").prop("checked", false);
        classCd += 2;
    }else if(befGuarDays > guarDays){	//기간단축
     	//console.log("기간단축")
     	$("#chgDtDn").prop("checked", true);
        $("#chgDtUp").prop("checked", false);
        classCd += 3;
    }else{	//변경 없을시
    	//console.log("기간변경없음")
    	$("#chgDtUp").prop("checked", false);
        $("#chgDtDn").prop("checked", false);
	}
    if($("#chgGuarDays").val() === "NaN") $("#chgGuarDays").val("");

    var befGuarAmt = guarCom.fnToNumber($("#befGuarAmt").val());
    var guarAmt = guarCom.fnToNumber($("#guarAmt").val());
    //console.log("보증료 변화 체크 시작 befGuarAmt: "+befGuarAmt + " / guarAmt: " + guarAmt);

    if(befGuarAmt < guarAmt){
		//console.log("금액증액");
        //guarCom.fnDetectChg("chgAmtUp","chgAmtDn");
		$("#chgAmtUp").prop("checked", true);
        $("#chgAmtDn").prop("checked", false);
        classCd += 4;
    }else if(befGuarAmt > guarAmt){
		//console.log("금액감액");
        //guarCom.fnDetectChg("chgAmtDn","chgAmtUp");
		$("#chgAmtDn").prop("checked", true);
        $("#chgAmtUp").prop("checked", false);
        classCd += 5;
    }else{
		//console.log("금액변화없음");
		$("#chgAmtUp").prop("checked", false);
		$("#chgAmtDn").prop("checked", false);
	}

    $("#chgGuarAmt").val(com.addComma(guarAmt - befGuarAmt));

    if(classCd.length < 2) 	classCd += 0;
    $("#guarClassCd").val(classCd);
    guarCom.fnChgDispChange();
}

//변경구분 선택 이벤트
guarCom.fnDetectChg = function(check, unCheck){
    if($("#"+check).is(":checked")){
        $("#"+check).prop("checked", true);
        $("#"+unCheck).prop("checked", false);
    }

    //보증문구값 생성 및 변경
    guarCom.fnMakeGuarClassCont();
}


//추가보증 변경분 자동 설정
guarCom.fnChgDispChange = function(){
	//console.log("fnChgDispChange시작 : 추가 보증 정보 표시")
	var guarClassCd = $("#guarClassCd").val();

    //기간연장:20 | 기간단축:30
    var chgDispYn = $("[name=chgDispYn]:checked").val(); 	//변경분표시여부(변경분 표시, 전체표시)
    var tmpChgGuarBegDt = "";	//변경보증시작일
    var tmpChgGuarExpDt = "";	//변경보증종료일
    
    var befGuarBegDt = $("#befGuarBegDt").val().replace(/\-/g,"");;
	var guarBegDt = $("#guarBegDt").val().replace(/\-/g,"");;
			
	//console.log("guarClassCd : " + guarClassCd + " / chgDispYn : " + chgDispYn);
    if(guarClassCd.indexOf("2") > -1 || guarClassCd.indexOf("3") > -1) { //기간연장 이거나 단축일때
    	if(chgDispYn === "Y") { //변경분
        	tmpChgGuarBegDt = com.fnDateGet($("#befGuarExpDt").val(),1);
        }else{
			//시작일자가 변경 된경우
			if(befGuarBegDt > guarBegDt)	tmpChgGuarBegDt = $("#guarBegDt").val(); //일자다 당겨진 경우
        	else							tmpChgGuarBegDt = $("#befGuarBegDt").val();
		}
        
        tmpChgGuarExpDt = $("#guarExpDt").val();
        
    } 

    $("#chgGuarBegDt").val(tmpChgGuarBegDt);
    $("#chgGuarExpDt").val(tmpChgGuarExpDt);
    $("#chgGuarDays").val(com.fnDateTerm($("#chgGuarBegDt").val(), $("#chgGuarExpDt").val()));

    if($("#chgGuarDays").val() === "NaN") $("#chgGuarDays").val("");

    //보증문구값 생성 및 변경
    guarCom.fnMakeGuarClassCont();
}

//보증 문구 값 변경
guarCom.fnMakeGuarClassCont = function(){
	if( com.fnIsEmpty($("#initReqDt").val()) || com.fnIsEmpty($("#initDocNo").val()) ) {
		return;
	}

    var initReqDt = $("#initReqDt").val().replaceAll('-', '');	//최초등록일
    var initReqYear = initReqDt.substring(0,4);		//연
    var initReqMonth = initReqDt.substring(4,6);	//월
    var initReqDay = initReqDt.substring(6,8);		//일
    var initDocNo = $("#initDocNo").val();			//보증서번호
    var chgCnt = Number($("#chgCnt").val());		//변경차수
    if(com.fnIsEmpty($("#atthNo").val()))	chgCnt += 1;	//첨부파일번호가 있으면 수정페이지라서 수정페이지면 그대로 사용

    var guarClassNm = "";	//문구
    $("#ele_chgDescType").find("input[type=checkbox]:checked").each(function(){
		if(guarClassNm !== "")	guarClassNm += ", ";
		guarClassNm += $(this).data('text');
	});
	guarClassNm = (guarClassNm == "") ? "변경" : guarClassNm;		//빈값이면 변경 처리

	var guarClassCont  = `${initReqYear}년 ${initReqMonth}월 ${initReqDay}일 증권번호 ${initDocNo}호 `;
	    guarClassCont += `발급분에 대한 ${guarClassNm}에 따른 ${chgCnt}차 추가보증임`;

	$("#guarClassCont").val(guarClassCont);	//comGuarReqGuar.html
}

//추가보증정보(변경분 표시여부 체크박스) 클릭 이벤트
guarCom.fnChgDispYn = function(value){
	$("input[name=chgDispYn]").prop("checked", false);
	$("input[name=chgDispYn][value="+value+"]").prop("checked", true);

	guarCom.fnChgDispChange();	//추가보증 변경분 자동 설정
}


//전자보증 상세 팝업 호출
guarCom.fnG2bHisView = function(sheet){
	rowData = sheet.getFocusedRow(); //생성된 행 정보 가져오기
	if(rowData === null){
		alert("대상을 선택 하십시오.");
		return;
	}

	com.dialpopupOpen("전자보증이력 상세", "/index/pageCall.do?menuCd=1070719&guarNo="+rowData.guarNo+"&histSeq="+rowData.histSeq, "800", "500");	// com/g2bHistDtl
};



//그리드 필드 색상 변경
guarCom.fnIbsStyleSet = function(sheet, row){

	//신청구분
	var guarIssueCd = row.guarIssueCd;
	var guarIssueColor = "";
	if(guarIssueCd === "I") guarIssueColor = "#1E90FF";	//인터넷
	if(guarIssueCd === "G") guarIssueColor = "#FF4500";	//G2B
	if(guarIssueCd === "V") guarIssueColor = "#228B22";	//창구

	//변경차수
	var chgCnt = row.chgCnt;
	var chgCntColor = "#1E90FF";
	if(chgCnt === "최초") chgCntColor = "#000000";

	//보증서번호에 색깔 취소:빨 해제:회색 미전송:체크
	var guarStatCd = row.guarStat;
	var guarStatColor = "#000000";	//기본 (검정)
	if(guarStatCd === '10')	guarStatColor = "#000000";	//처리중(검정)
	//else if(guarStatCd === '20')	guarStatColor = "#FF4500";	//보증사고
	else if(guarStatCd === '30')	guarStatColor = "#BDBDBD";	//해제 (회색) //
	else if(guarStatCd === '31')	guarStatColor = "#FF0000";	//취소 (빨강)
	//else if(guarStatCd === '21')	guarStatColor = "#000000";	//이관
	else if(guarStatCd === '90')	guarStatColor = "#228B22";	//보증


	sheet.setCellStyle(sheet.getRowById(row.id), "guarIssueCd", {"TextColor" : guarIssueColor}, 1);	//신청구분(G2B, 인터넷, 창구)
	sheet.setCellStyle(sheet.getRowById(row.id), "chgCnt", {"TextColor" : chgCntColor}, 1);	//변경차수
	sheet.setCellStyle(sheet.getRowById(row.id), "docNo", {"TextColor" : guarStatColor}, 1);	//보증서번호
	sheet.setCellStyle(sheet.getRowById(row.id), "defDocNo", {"TextColor" : guarStatColor}, 1);	//보증서번호

	//잔여보증료
	if(row.remainCommAmt !== undefined){
		var remainCommAmt = row.remainCommAmt;
		if(remainCommAmt > 0) setColor = "#FF0000";
		else				  setColor = "#000000";
		sheet.setCellStyle(sheet.getRowById(row.id), "remainCommAmt", {"TextColor" : setColor}, 1);	//잔여보증료
	}
};

//table영역 숨김/보이기 처리
guarCom.fnHideArea = function(btnObj, tableId){
	if($("#"+tableId).hasClass('hide')){
		$("#"+tableId).removeClass('hide');
		$(btnObj).text('숨김');
	}else{
		$("#"+tableId).addClass('hide');
		$(btnObj).text('보이기');
	}
};

//보증상태값 보증처리(procStat : 90)
guarCom.fnUpdateProcStat = function(pGuarNo){
	if(!confirm("보증번호: " + pGuarNo + " 을 보증처리 하시겠습니까?")) return;

	var pParam = { guarNo: pGuarNo };
	var obj = {url:"/guar/updateGuarProcStatIssue.do", param:pParam, callBack:guarCom.fnUpdateProcStatCallBack, msgYn:"Y", sessYn:"Y", loadYn:"Y"};
	com.fnAjaxDef(obj);
};

//보증상태값 보증처리 콜백
guarCom.fnUpdateProcStatCallBack = function(resData){	//페이지 리로드
	//let reLoadFrm = window.frames.name;
	//top.document[reLoadFrm].location.reload();
	fnSearch();
}

//관리자 수정팝업
guarCom.fnUptPop = function(){
	var rowData = sheet.getFocusedRow(); //생성된 행 정보 가져오기
	if(rowData === null){
		alert("대상을 선택 하십시오.");
		return;
	}
	var guarNo = rowData.guarNo;
	var guarType = rowData.guarType;
	var guarReqCd = rowData.guarReqCd;

	com.dialpopupOpen("보증정보수정", "/index/pageCall.do?menuCd=1070731&guarNo="+guarNo+"&refVal="+guarType+"&guarReqCd="+guarReqCd, "1600", "800");	// guar/guarUptPop
}


//보증약정 한도거래유형에따른 스크립트
//한도구분 선택
guarCom.fnLimitOptYnSel = function(type){

	//상품정보 화면 컨트롤
	$("input[name='guarType']").each(function(idx) {
		var cd = $(this).attr("id").replace("guarType", ""); //상품id
		if(type === "Y"){
			$("#doubleNum"+cd).addClass("readonly").attr("readonly", false);
			$("#limitAmt"+cd).addClass("readonly").attr("readonly", false);
		}else if(type === "T" || type === "D"){
			$("#doubleNum"+cd).addClass("readonly").attr("readonly", false);
			$("#limitAmt"+cd).addClass("readonly").attr("readonly", false);
		}else{
			$("#doubleNum"+cd).addClass("readonly").attr("readonly", true);
			$("#limitAmt"+cd).addClass("readonly").attr("readonly", true);
		}

		//초기화
		//$("#guarType"+cd).prop("checked", false);
		//$("#doubleNum"+cd).val(0);
		//$("#limitAmt"+cd).val(0);

		//기본배수율 설정
		//guarCom.fnConfSet(confGuar);
	});
}

//보증상품 선택
guarCom.fnGuarTypeSel = function(obj){
	var cd = obj.id.replace("guarType", ""); //상품id
	var limitOptYn  = $("input[name='limitOptYn']:checked").val();//약정한도유형
	var chkYn  = $("#"+obj.id).is(":checked"); //상품선택여부

	var contrType = $("#contrType").val();
	var contrNum = $("#contrNum").val();
	var dealAmt  = Number(com.rmComma($("#dealAmt").val())); //좌당금액

	if(limitOptYn !== "Y" && $("#contrNum").val() === ""){
		alert("약정좌수를 입력 하십시오.");
		if(chkYn) $("#"+obj.id).prop("checked", false);
		$("#contrNum").focus();
		return
	}

	if(limitOptYn !== "Y" && $("#gradeNo").val() === ""){
		alert("신용도를 조회 하십시오.");
		if(chkYn) $("#"+obj.id).prop("checked", false);
		$("#finalGrade").focus();
		return
	}

	if(chkYn){
		$("#limitAmt"+cd).val(com.addComma(contrNum * dealAmt * $("#doubleNum"+cd).val()));
	}else{
		$("#limitAmt"+cd).val(0);
	}

	if(!guarCom.fnSumLimit()){
		$("#guarType"+cd).prop("checked", false);
	};
}


//한도배수 입력
guarCom.fnLimiNumSet = function(obj){
	var cd = obj.id.replace("doubleNum", ""); //상품id
	var chkYn  = $("#guarType"+cd).is(":checked"); //상품선택여부
	var limitOptYn  = $("input[name='limitOptYn']:checked").val();//약정한도유형
	
	var dealAmt  = Number(com.rmComma($("#dealAmt").val())); //좌당금액
	var contrNum = Number($("#contrNum").val()); //약정좌수

	//한도배수 총 합
	var sumImplRate = $("#sumImplRate").val();
	var sumPayRate = $("#sumPayRate").val();

	//체크된 합계
	var totImplNum = 0;	//이행 합계
	var totPayNum = 0;	//지급 합계

	//체크 되어있으면 배수에 대한 한도금액 산정
	if(chkYn){
		$("#limitAmt"+cd).val(com.addComma(contrNum * dealAmt * $("#doubleNum"+cd).val()));
	}

	//배수 합산
	/*
	$("input[name='guarType']:checked").each(function(idx) {
		var cd = $(this).val(); //상품id
		//이행합계
		if(cd === '11' || cd === '12' || cd === '13'){
			totImplNum += Number($('input[name="doubleNum"]')[idx].value);
		}

		//지급합계
		if(cd === '14' || cd === '15'){
			totPayNum += Number($('input[name="doubleNum"]')[idx].value);
		}
	});


	if(limitOptYn !== "Y" && limitOptYn !== "T"){	//미출자, 통합추가(2023-11-27 요청)
		//이행합계 한도배수 체크
		if(sumImplRate < totImplNum){
			alert("총 이행합계 한도배수를 초과 할 수 없습니다 (총 배수 " +sumImplRate + ")");
			obj.value = "";
		}else{
			//지급합계 한도배수 체크
			if(sumPayRate < totPayNum){
				alert("총 지급합계 한도배수를 초과 할 수 없습니다 (총 배수 " +sumImplRate + ")");
				obj.value = "";
			}
		}
	}
	*/
	//합산
	if(!guarCom.fnSumLimit()){
		//$("#guarType"+cd).prop("checked", false);
		$("#limitAmt"+cd).val(0);
		obj.value = 0;
	};
}


//한도금액 입력
guarCom.fnLimiAmtSet = function(obj){
	var limitAmt = obj.value;
	var id = obj.id;
	var cd = id.replace("limitAmt", ""); //한도액

	if(limitAmt ===""){
		$("#guarType"+cd).prop("checked", false);
	}else{
		$("#guarType"+cd).prop("checked", true);
	}

	if(!guarCom.fnSumLimit()){
		$("#guarType"+cd).prop("checked", false);
		obj.value = 0;
	};
}


//합산
guarCom.fnSumLimit = function(){
	var limitOptYn  = $("input[name='limitOptYn']:checked").val();//약정한도유형

	var sumNum = 0;		//총합계(배수)
	var sumAmt = 0;		//총합계(한도액)

	var subImplNum = 0;	//이행합계(배수)
	var subImplAmt = 0;	//이행합계(한도액)

	var subPayNum = 0;	//지급합계(배수)
	var subPayAmt = 0;	//지급합계(한도액)

	var contrNum = Number($("#contrNum").val()); //약정좌수
	var dealAmt  = Number(com.rmComma($("#dealAmt").val())); //좌당금액
	var finalGrade = $("#finalGrade").val(); //등급
	var smallAmt = Number(com.rmComma($("#smallAmt").val())); //소액금액
	var sumRate = $("#sumRate").val(); //총한도배수
	var totLimitAmt = Number(com.rmComma($("#totLimitAmt").val())); //총한도금액
	var implLimitAmt = contrNum * dealAmt * $("#subImpl_sumDoubleNum").val(); //총한도금액(이행)
	var payLimitAmt = contrNum * dealAmt * $("#subPay_sumDoubleNum").val(); //총한도금액(지급)


	//console.log("dealAmt : " + $("#dealAmt").val());
	//console.log("subImpl_sumDoubleNum : " + $("#subImpl_sumDoubleNum").val());
	//console.log(sumLimitAmt +"/"+implLimitAmt+"/"+payLimitAmt);

	$("input[name='guarType']").each(function(idx) {
		var cd = $(this).attr("id").replace("guarType", ""); //상품id
		var chkYn = $(this).is(":checked"); //상품체크여부
		var doubleNum = Number($('input[name="doubleNum"]')[idx].value); //상품배수
		var limitAmt  = Number(com.rmComma($('input[name="limitAmt"]')[idx].value)); //상품한도액
		var useAmt  = Number(com.rmComma($('input[name="useAmt"]')[idx].value)); //사용가능금액

		if(cd == "11" || cd == "12" || cd == "13"){	//이행
			subImplNum += doubleNum;	//이행합계(배수)
			subImplAmt += limitAmt;		//이행합계(한도액)
		}
		if(cd == "14" || cd == "15"){	//지급
			subPayNum += doubleNum;	//지급합계(배수)
			subPayAmt += limitAmt;	//지급합계(한도액)
		}

		sumNum += doubleNum;
		sumAmt += limitAmt;
	});

	//console.log(limitOptYn + " /" + subImplAmt + " / " + subPayAmt + " / "  + implLimitAmt + " / " + payLimitAmt + " / " +  totLimitAmt);
	if(limitOptYn === "T"){ //통합
		if(subImplAmt + subPayAmt > totLimitAmt){
			alert("총 한도금액을 초과 할 수 없습니다. 한도(" + com.addComma(totLimitAmt) +")");
			return false;
		}
	}else if(limitOptYn === "D"){ //부분통합
		if(subImplAmt > implLimitAmt){
			alert("총 이행 한도금액을 초과 할 수 없습니다. 한도(" + com.addComma(implLimitAmt) +")");
			return false;
		}

		if(subPayAmt > payLimitAmt){
			alert("총 지급 한도금액을 초과 할 수 없습니다. 한도(" + com.addComma(payLimitAmt) +")");
			return false;
		}
	}

	$("#subImpl_sumLimitAmt").val(com.addComma(subImplAmt));	//이행합계(한도액)
	$("#subPay_sumLimitAmt").val(com.addComma(subPayAmt));		//지급합계(한도액)

	$("#sumLimitAmt").val(com.addComma(sumAmt));	//총합계(한도액)
	$("#sumDoubleNum").val(sumNum);					//총합계(배수)

	//$("#subImpl_sumDoubleNum").val(subImplNum);		//이행합계(배수)
	//$("#subPay_sumDoubleNum").val(subPayNum);		//지급합계(배수)

	return true;
}


//상품 배수 설정(초기화)
guarCom.fnConfSet = function(confGuar){
	if(confGuar.bidRate === undefined) return;

	//상품배수율
	$("#doubleNum11").val(confGuar.bidRate);
	$("#doubleNum12").val(confGuar.contrRate);
	$("#doubleNum13").val(confGuar.defectRate);
	$("#doubleNum14").val(confGuar.payRate);
	$("#doubleNum15").val(confGuar.perfRate);
}

//상품 한도사용액 체크
guarCom.fnLimitUseChk = function(obj){
	var id = obj.id;
	var cd = id.replace("limitAmt", ""); //한도액

    var limitAmt = Number(com.rmComma($("#limitAmt"+cd).val()));
	var useAmt = Number(com.rmComma($("#useAmt"+cd).val()));

	if(useAmt !== null){
		if(useAmt > limitAmt){
			alert("한도액이 사용액보다 작을 수 없습니다.");
			$("#limitAmt"+cd).val(0);
			return;
		}
	}
}
