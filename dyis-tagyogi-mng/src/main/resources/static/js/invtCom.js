/****************************************************************
 * 
 * 파일명 : invtCom.js
 * 설  명 : 공통 자바스크립트
 * 
 *    수정일      수정자     Version        Function 명
 * ------------    ---------   -------------  ----------------------------
 * 2022.06.15          1.0             최초생성
 * 
 * 
 * **************************************************************/


//출자업무 공통 함수 시작 
var invtCom = new Object();

///////////////////////////////////////////////////////////////////////////////////////////////////////

//출자증권발행 설정 값 변동시 초기화
invtCom.fnChgInvtStock = function(obj,str){
	if($(obj).val() == "") {
		invtCom.calcByBillNum(str);
	}
}
//동적 출자증권발행정 설정
invtCom.fnSetinvtStock = function(cdType, tblId){
	
	$("#"+tblId).empty();
	
	var initHtml = "";
	
	initHtml += '<colgroup>';
	initHtml += '	<col style="width:100px;">';
	initHtml += '	<col style="width:100px;">';
	initHtml += '	<col style="width:100px;">';
	initHtml += '	<col style="width:100px;">';
	initHtml += '	<col style="width:100px;">';
	initHtml += '</colgroup>';
	initHtml += '<tbody>';
	initHtml += '	<tr>';
	initHtml += '		<th class="tac">권종</th>';
	initHtml += '		<th class="tac">액면가</th>';
	initHtml += '		<th class="tac">매수</th>';
	initHtml += '		<th class="tac">금액</th>';
	initHtml += '		<th class="tac">비고</th>';
	initHtml += '	</tr>';
	initHtml += '</tbody>';
	
	$("#" + tblId).append(initHtml);
	$.each(codeList, function(idx, item){
		if(item.cdType == cdType){
			var html = '<tr>';
				html += '<td class="tac">'+ item.cdNm; 
				html += 	'<input type="hidden" name="billType" id="billType'+item.cd+'" style="width:100%" value="'+item.cd+'">'; 
				html += '</td>'; //권종
				html += '<td class="tac">'; 
				
				html += 	'<input type="search" name="dealAmt'+item.cd+'" id="dealAmt'+item.cd+'" style="width:100%; text-align: right;" readonly>'; 
				html += 	'<input type="hidden" name="faceAmt'+item.cd+'" id="faceAmt'+item.cd+'">';
				html += '</td>'; //액면가
				html += '<td class="tac">'; 
				html += 	'<input type="search" name="billCnt'+item.cd+'" id="billCnt'+item.cd+'" style="width:100%; text-align: center;" maxlength="15" onSearch="invtCom.fnChgInvtStock(this,'+item.cd+');" onkeyup="com.fnOnlyNum(this);com.makeComma(this); invtCom.calcByBillNum('+item.cd+')">'; 
				html += '</td>'; //매수
				html += '<td class="tac">'; 
				html += 	'<input type="search" name="billAmt'+item.cd+'" id="billAmt'+item.cd+'" style="width:100%; text-align: right;" maxlength="5" readonly>'; 
				html += '</td>'; //금액
				html += '<td class="tac">'; 
				html += 	'<input type="search" name="remark'+item.cd+'" id="remark'+item.cd+'" style="width:100%; " maxlength="15" >'; 
				html += '</td>'; //비고
				html += '</tr>';
				
			$("#" + tblId + " > tbody:last").append(html);
		}
	});

	var footHtml = '<tfoot>';
		footHtml += 	'<tr>';
		footHtml += 	'<th class="alignC" scope="colgroup" colspan="2">합계</th>';
		footHtml += 	'<td class="alignC"><input type="search" name="sumBillCnt" id="sumBillCnt" style="width:100%; text-align: center;" title="" readonly></td>';
		footHtml += 	'<td class="alignC"><input type="search" name="sumBillAmt" id="sumBillAmt" style="width:100%; text-align: right;" title="" readonly></td>';
		footHtml += '<td colspan="2" ></td>';
		footHtml += '</tfoot>';
	$("#" + tblId + " > tbody").after(footHtml);
	
}

//증권매수설정
invtCom.calcByBillNum = function(billType) {
    if($("#investNum").val() == "") {
        alert("출자좌수를 입력해 주십시오.");
        $("#investNum").focus();
        
        invtCom.fnInitBill();
        
        return;
    }
    
    if($("#sepmerNum").val() == "") {
        alert("분할할좌수를 선택해 주십시오.");
        invtCom.fnInitBill();
        return;
    }
    
    
    var calDealAmt = Number(com.rmComma($("#dealAmt" + billType).val()));
    var calBillNum = Number(com.rmComma($("#billCnt" + billType).val()));
    
    if(calBillNum == 0) $("#billAmt" + billType).val("");
    else $("#billAmt" + billType).val(com.aprojomma(calDealAmt * calBillNum));
    
    invtCom.sumBillData();
}

//합계계산
invtCom.sumBillData = function() {
	var sumBillNum = 0;
	var sumBillAmt = 0;
    
    $.each(codeList, function(idx, item){
		if(item.cdType == 'IV03') {
			sumBillNum += Number(com.rmComma($("#billCnt"+item.cd).val())) * item.cd;
			sumBillAmt += Number(com.rmComma($("#billAmt"+item.cd).val()));
		}
	});
    
    $("#sumBillCnt").val(com.aprojomma(sumBillNum));
    $("#sumBillAmt").val(com.aprojomma(sumBillAmt));
	
	//출자등록시
	if($("#investNum").length){
		if(Number(com.rmComma($("#sumBillCnt").val())) > Number(com.rmComma($("#investNum").val()))) {
		    alert("증권발행정보합계가 출자좌수보다 큽니다. 증권정보를 확인하여 주십시오.");
		    invtCom.fnInitBill();
		    return false;
		}
	}
	
	//증권분할시
	if($("#sepmerNum").length){
		if(Number(com.rmComma($("#sumBillCnt").val())) > Number(com.rmComma($("#sepmerNum").val()))) {
		    alert("분할할 출자증권의 좌수(금액)가 분할선택좌수보다 큽니다.\n분할 정보를 확인하여 주십시오.");
		    invtCom.fnInitBill();
		    return false;
		}
	}
	
}

//권종별 액면가 계산 세팅
invtCom.fnCalfaceDeal = function(cdType, dealList){
	var resList = [];
	$.each(codeList, function(idx, item){
		if(item.cdType == cdType) resList.push(item);
	});
	
	if(resList == '' || resList == null || resList == undefined ) {
		alert('조회된 권종별정보가 없습니다');
		return;
	}
	
	//출자증권발행정보에 액면가세팅
	$.each(resList, function(idx, item){
		$("#faceAmt" + item.cd).val(com.aprojomma(Number(dealList.faceAmt) * item.cd));
		$("#dealAmt" + item.cd).val(com.aprojomma(Number(dealList.faceAmt) * item.cd));
	});
	
}

//출자시입금 입금계좌목록조회
invtCom.fnKocfcBankAcc = function(paramId, choiceType, value){
	param = {paramId:paramId, choiceType: choiceType, value: value, sUseYn: 'Y', asyncYn: false};
	obj = {url:"/invt/selectKocfcBankAccList.do", param:param, callBack:invtCom.fnKocfcBankAccCallBack, loadYn:"Y", msgYn:"N", asyncYn:false};
	com.fnAjaxDef(obj);
};

//출자시입금 입금계좌목록조회 콜백
invtCom.fnKocfcBankAccCallBack = function(resData){
	console.log('---입금계좌목록 콜백 도착---');
	console.log(resData);
	
	var paramId = resData.paramMap.paramId;
	var choiceType = resData.paramMap.choiceType;
	var value = resData.paramMap.value;
	
	//기존 옵션 삭제
	$("#"+paramId +" option").remove();
		
	//셀렉트 유형
	if(choiceType == 1) $("#"+paramId).append("<option value=''>선택</option>");
	if(choiceType == 2) $("#"+paramId).append("<option value=''>전체</option>");
	
	$.each(resData.data, function(idx, item){
		$("#"+paramId).append("<option value='"+item.bankCd+","+item.acctNo+"'>"+item.bankNm+"(" + item.bankType + ")</option>");
	});
	
	//변수값 선택
	if(value != undefined && value != ""){
		$("#"+paramId+ " > option[value="+value+"]").attr("selected", "selected");
	}
}

//출자증권발행정보 초기화 
invtCom.fnInitBill = function() {
	$('input[id^=billCnt]').val('');
	$('input[id^=billAmt]').val('');
	$('input[id^=sumBillCnt]').val('');
	$('input[id^=sumBillAmt]').val('');
}

//액면가조회
invtCom.selectFaceDeal = function(val){
	var asyncYn = true;
	if(val == 'trans' || val == 'sepmer' || val == 'pay'){
		var target = val+"Dt"
		var callFn = invtCom.fnTranFaceDealCallBack;	
		asyncYn = false;
	}else if(val == 'dept'){
		var target = val+"Dt"
		var callFn = invtCom.fnDeptFaceDealCallBack;	
		asyncYn = false;
	}else{
		var target = "investDt";
		var callFn = invtCom.fnFaceDealCallBack;	
	}
	
	if($('#'+target).val().length != 10){
		$('#faceAmt').val('');
		$('#dealAmt').val('');
		return;
	}
	//console.log($("#investDt").val());
	//sys 좌당지분액 관리소스에서 호출 confInvt/selectConfInvtMaxAppDt   // 검증 후 invt/selectFaceDeal 제거
	obj = {url:"/confInvt/selectConfInvtMaxAppDt.do", param:{appDt: $("#"+target).val()}, callBack:callFn, msgYn:"N", sessYn:"Y", loadYn:"Y", asyncYn: asyncYn};
	com.fnAjaxDef(obj);
	
}

//액면가조회 콜백
invtCom.fnFaceDealCallBack = function(resData){
	console.log(resData);
	if(resData == '' || resData == null || resData == undefined ) {
		alert('좌당액면가 정보가 존재하지 않습니다.');
		return;
	}
	
	//$.each(resData.data, function(idx, item){
		
		var fAmt = resData.data.faceAmt;
		var dAmt = resData.data.dealAmt;
		
		$('#faceAmt').val(com.aprojomma(fAmt));
		$('#dealAmt').val(com.aprojomma(dAmt));
		
		//권종별 액면가 계산 세팅
		invtCom.fnCalfaceDeal("IV03",resData.data);
	//});
	
	invtCom.fnCalcByNum(); //좌수입력시 금액자동계산
}

//액면가조회 콜백-양도양수
invtCom.fnTranFaceDealCallBack = function(resData){
	console.log(resData);
	if(resData == '' || resData == null || resData == undefined ) {
		alert('좌당액면가 정보가 존재하지 않습니다.');
		return;
	}
	
	//$.each(resData.data, function(idx, item){
		
		var fAmt = resData.data.faceAmt;
		var dAmt = resData.data.dealAmt;
		
		$('#faceAmt').val(fAmt);
		$('#dealAmt').val(dAmt);
		
		//권종별 액면가 계산 세팅
		invtCom.fnCalfaceDeal("IV03",resData.data);
		
	//});
	
}

//액면가조회 콜백-예수금이력등록
invtCom.fnDeptFaceDealCallBack = function(resData){
	console.log(resData);
	if(resData == '' || resData == null || resData == undefined ) {
		alert('좌당액면가 정보가 존재하지 않습니다.');
		return;
	}
		
		var fAmt = resData.data.faceAmt;
		var dAmt = resData.data.dealAmt;
		
		$('#faceAmt').val(fAmt);
		$('#dealAmt').val(dAmt);
	
}

//좌수입력시 금액자동계산
invtCom.fnCalcByNum = function() {
	if($("#investDt").val() == "") {
		alert("출자일자를 입력(선택)해 주십시오.");
		$("#investNum").val("");
		$("#investDt").focus();
		return;
	}
	
	//fnInitBill();
	
	var dealAmt = Number(com.rmComma($("#dealAmt").val()));
	var investNum = Number(com.rmComma($("#investNum").val()));
	
	$("#investAmt").val(com.aprojomma(dealAmt * investNum));
}

//결제은행설정
invtCom.fnBankChange = function(bankStr) {
    if(bankStr == "") {
        $("#payBank").val("");
        $("#payAcct").val("");
    } else {
        $("#payBank").val(bankStr.split(",")[0]);
        $("#payAcct").val(bankStr.split(",")[1]);
    }
}

//권종자동설정
invtCom.fnAutoBill = function() {
	if($("#investNum").val() == "") {
        alert("출자좌수를 입력(선택)해 주십시오.");
        $("#investNum").focus();
        return;
    }
	
	invtCom.fnInitBill();
	
	var investNum = Number($("#investNum").val());
	var remainNum = investNum;
	var arrBill = [];
	
	//가져온 권종별로 내림차순 정렬 만들기
	$.each(codeList, function(idx, item){
		if(item.cdType == 'IV03') arrBill.push(item.cd);
		arrBill.sort((a,b)=> b - a);
	});
	
	//차례대로 권종별 계산해서 자동 세팅하기 
	$.each(arrBill, function(idx, item){
		if(remainNum / item >= 1) {
			var appNum = parseInt(remainNum / item);
			$("#billCnt"+item).val(appNum);
			invtCom.calcByBillNum(item);
			remainNum -= appNum * item;
		}
	});
	
	//권종별자동세팅
	//invtCom.fnSetAutoBill();
	
}

//출자담보 소유조합원, 배정조합원 값세팅
//소유조합원, 배정조합원 값 세팅
invtCom.fnAssSetKeyVal = function(obj, type = undefined){
	var keyList = Object.getOwnPropertyNames(obj);
	for( key in keyList ){
		var k = keyList[key].replace(" ", "");
		//구분자 없을때(기본)
		if(type == null || type == undefined || type ==""){
			$("#ele_"+k).text(obj[k]);	
			$("#"+k).val(obj[k]);
		}else {
			//구분자있을때
			$("#ele_"+type+"_"+k).text(obj[k]);	
			$("#"+type+"_"+k).val(obj[k]);	
		}
			
	}
}

/****************************************************************
 *담보배정관리  
****************************************************************/
//동적 줄자증권정보설정(담보배정등록)
invtCom.fnSetinvtbillAssTbl = function(cdType, tblId, mod){
	//우선 권종리스트 가져와서 array에 담아서 thead만 동적으로 생성
	//추후 tbody도 권종리스트확정여부에따라 동적으로 변경예정
	
	$("#"+tblId).find('thead').empty();
	
	var billArray = [];
	var initHtml = "";
	var theadHtml = "";
	var tbodyHtml = "";
	
	initHtml += '	<tr>'						 ;
	initHtml += '	<th>항목</th>'				 ;
	initHtml += '	</tr>'						 ;
	
	$("#" + tblId).find('thead').append(initHtml);
	
	$.each(codeList, function(idx, item){
		if(item.cdType == cdType) {
			billArray.push(item.cd);	
		}
	});
	
	//우선 thead만 동적으로 증권list 가져와서 확인
	//thead
	$.each(billArray, function(idx,item){
		theadHtml += '<th class="tac">'+item+'좌권</th>'
	});
	theadHtml += '<th class="tac">합계매수/좌수</th>';
	$("#" + tblId + " > thead tr:last").append(theadHtml);
	
};

//동적 줄자증권정보설정(담보배정등록)
invtCom.fnSetinvtbillCnt = function(sheetid, type){
	
	var billTypeList = [];
	
	//권종리스트
	$.each(codeList, function(idx, item){
		if(item.cdType == 'IV03') {
			billTypeList.push(item.cd);
		}	
	});
	
	//권종별 동적으로 변수세팅 - 객체활용
	var billObj = {};
	var [billTtCnt, billTtNum, mortTtCnt, mortTtNum, assTtCnt, assTtNum, setTtCnt, setTtNum] = Array(8).fill(0);
	var billObj = { billTtCnt, billTtNum, mortTtCnt, mortTtNum, assTtCnt, assTtNum, setTtCnt, setTtNum };
	
	//초기화
	for (var billCd of billTypeList) { 
		var cBillCnt = "billCnt" + billCd; //권종별매수
		var cBillNum = "billNum" + billCd; //권종별좌수
		var cMortCnt = "mortCnt" + billCd; //권종별담보매수
		var cMortNum = "mortNum" + billCd; //권종별담보좌수
		var cAssCnt  = "assCnt"  + billCd; //권종별기배정매수
		var cAssNum  = "assNum"  + billCd; //권종별기배정좌수
		var cSetCnt  = "setCnt"  + billCd; //권종별설정매수
		var cSetNum  = "setNum"  + billCd; //권종별설정좌수
		
		billObj[cBillCnt] = 0;
		billObj[cBillNum] = 0;
		billObj[cMortCnt] = 0;
		billObj[cMortNum] = 0;
		billObj[cAssCnt] = 0;
		billObj[cAssNum] = 0;
		billObj[cSetCnt] = 0;
		billObj[cSetNum] = 0;
	}
	
	//조합원이 소유하고있는 권종 리스트
	var rows = sheetid.getDataRows(); 
	for(var i=0; i< rows.length; i++){
		var billType   = sheetInvtMortAss.getValue(rows[i], "billType"); //담보설정 혹은 배정이 되어있는애들 제외
		var billNum    = sheetInvtMortAss.getValue(rows[i], "billNum");
		var mortYn     = sheetInvtMortAss.getValue(rows[i], "mortYn");
		var assignYn   = sheetInvtMortAss.getValue(rows[i], "assignYn");
		var selected   = sheetInvtMortAss.getValue(rows[i], "selectYn");
		var setYn      = sheetInvtMortAss.getValue(rows[i], "setYn");

		//보유매수,좌수
		var objCnt = "billCnt"+billType
		var objNum = "billNum"+billType
		invtCom.fnSetBillTotal(billObj,objCnt,objNum,billNum);
		
		//담보매수,좌수
		if(mortYn == "Y"){
			var objCnt = "mortCnt"+billType
			var objNum = "mortNum"+billType
			invtCom.fnSetBillTotal(billObj,objCnt,objNum,billNum,'mort');
		}

		//기배정매수,좌수
		if(assignYn == "Y"){
			var objCnt = "assCnt"+billType
			var objNum = "assNum"+billType
			invtCom.fnSetBillTotal(billObj,objCnt,objNum,billNum,'ass');
		}
		
		//수정모드에서 기배정매수, 잔여매수, 설정매수 세팅
		if(type == "upt"){
			if(setYn = 1 && (selected == "Y" || selected == 1)) {
				
				var objCnt = "assCnt"+billType; //기배정매수
				var objNum = "assNum"+billType; //기배정좌수
				var setCnt = "setCnt"+billType; //설정매수
				var setNum = "setNum"+billType; //설정좌수
				
				billObj.setTtCnt += 1;					//합계매수
				billObj.setTtNum += Number(billNum);	//합계좌수
				billObj[objCnt] -= 1;					//기배정매주
				billObj[objNum] -= Number(billNum);		//기배정좌수
				billObj[setCnt] += 1;					//설정매수
				billObj[setNum] += Number(billNum);		//설정좌수
				
				if (billObj.hasOwnProperty('assTtCnt')) billObj['assTtCnt'] -= 1; //기배정
				if (billObj.hasOwnProperty('assTtNum')) billObj['assTtNum'] -= Number(billNum); //기배정
				
				$("#setCnt"+billType).val(billObj[setCnt]); //설정
				$("#setNum"+billType).val(billObj[setNum]); //설정
				$("#setTtCnt").val(billObj.setTtCnt);       //설정
				$("#setTtNum").val(billObj.setTtNum);       //설정
			}
		}
	}
	
	//결과값 세팅	
	invtCom.fnSetBillRes(billObj,billTypeList);
		
};

//출자증권 보유,담보,기배정매수 값계산
invtCom.fnSetBillTotal = function(obj,cnt,num,target,mod){
	
	//권종별 매수 및 좌수
	if (obj.hasOwnProperty(cnt)) obj[cnt] += 1; 
	if (obj.hasOwnProperty(num)) obj[num] += Number(target);
	
	var totalCnt, totalNum;
	switch (mod){
	    case "mort": //담보
			totalCnt = "mortTtCnt";
			totalNum = "mortTtNum";
			break;
		case "ass":  //배정
			totalCnt = "assTtCnt";
			totalNum = "assTtNum";
			break;
		default:	//보유
			totalCnt = "billTtCnt";
			totalNum = "billTtNum";
			break;
		}
	
	//항목별 총매수 및 총좌수
	if (obj.hasOwnProperty(totalCnt)) obj[totalCnt] += 1; 
	if (obj.hasOwnProperty(totalNum)) obj[totalNum] += Number(target);
	  
}

//출자증권 보유,담보,기배정매수 계산값 tbl세팅
invtCom.fnSetBillRes = function(obj,list){
	
	//항목,좌수별세팅
	var keyList = Object.getOwnPropertyNames(obj);
	
	for( key in keyList ){
		var k = keyList[key].replace(" ", "");
		$("#"+k).text(obj[k]);
	}
	
	//잔여매수,좌수 세팅
	for(var key of list) {
	  var resCnt = Number($("#mortCnt"+key).text()) - Number($("#assCnt"+key).text());
	  var resNum = Number($("#mortNum"+key).text()) - Number($("#assNum"+key).text());
	  $("#resCnt"+key).text(resCnt);
	  $("#resNum"+key).text(resNum);
	}
	
	//잔여합계매수, 좌수 세팅
	var resTtCnt = Number($("#mortTtCnt").text()) - Number($("#assTtCnt").text());
	var resTtNum = Number($("#mortTtNum").text()) - Number($("#assTtNum").text());
	$("#resTtCnt").text(resTtCnt);
	$("#resTtNum").text(resTtNum);
}

//배정좌수입력
invtCom.fnSetAssignCnt = function(sheetid, mod){
	
	//전체해제
	var rows = sheetid.getDataRows(); 
	for(var i=0; i<rows.length; i++){
		sheetid.setValue(rows[i], "selectYn", "N");
	}
	
	/*************히든된 그리드에, 설정매수에 따른 증권 자동선택 시작 *************/
	//히든된 그리드에, 설정매수에 따른 증권 자동선택
	var setTtCnt = 0;
	var billTypeArr = [];
	
	$.each(codeList, function(idx, item){
		if(item.cdType == 'IV03') {
			billTypeArr.push(item.cd);
		}	
	});
	
	//임시
	/*
	billTypeArr 1 5 10 20 50
	for(var key of billTypeArr){
		
		//보유,담보,기배정 쿼리 리스트 resList
		[0] = {billType:0 , billCnt:10, billNum:10, mortCnt:10, mortNum:10, assCnt:10, assNum: 10}
		[1] = {billType:5 , billCnt:1,  billNum:5 , mortCnt:1,  mortNum:5,  assCnt:1,  assNum: 5 }
		for(var cnt=0, j=0; j< resList.length; j++){
			
			if(key = resList[j].billType){ //권종별타입이랑,쿼리리스트에서 권종별타입이 같다면 
				
			}
		}
	}
	
	*/
	
	//설정매수, 잔여매수초과 여부 확인후, 증권세팅
	for(var key of billTypeArr) {
		
		console.log(billTypeArr); 
		
		if($("#setCnt"+key).val() != ""){
			if(Number($("#setCnt"+key).val()) > Number($("#resCnt"+key).text())){
				alert("설정매수가 잔여매수를 초과하였습니다.");
				$("#setCnt"+key).val("");
				$("#setNum"+key).val("");
			}
			
			var calcSetNum = Number($("#setCnt"+key).val()) * Number(key);
			$("#setNum"+key).val(calcSetNum);
			
			//증권자동선택
			for(var cnt=0, j=0; j< rows.length; j++){
				
				var chkAvail = false;
				
				var billType   = sheetid.getValue(rows[j], "billType"); //담보설정혹은 배정이 되어있는애들 제외
				var billNum    = sheetid.getValue(rows[j], "billNum");
				var mortYn     = sheetid.getValue(rows[j], "mortYn");
				var assignYn   = sheetid.getValue(rows[j], "assignYn");
				var selected   = sheetid.getValue(rows[j], "selectYn");
				
				//등록인지 수정인지에따라 선택가능한 증권 구분
				if(mod == "upt"){//수정일때 배정이Y여도 이전에 배정등록했던 해당조합원에대한 배정Y일 수도 있기때문에 맨처음 초기화단계에서 해당배정ㄱ증권에서 select가능하게 푼 애들만 구분해주면됨
					if(billNum == key && mortYn == "Y" && (selected == "N" || selected == 0)) chkAvail = true;
				}else{//일반등록일땐(새로배정이기때문) 이미 배정되어있는애들은 다 disabled
					if(billNum == key && mortYn == "Y" && assignYn != "Y" && (selected == "N" || selected == 0)) chkAvail = true;
				}
				
				
				//증권목록중 배정가능한 목록 구분
				if(chkAvail) {
					
					//입력한 설정매수이상으로는 체크하지 않기
					if(cnt >= Number($("#setCnt"+key).val())) break;
					
					//해당증권 체크(배정할증권)
					sheetid.setValue(rows[j], "selectYn", "Y");
					setTtCnt += 1;
					cnt++;
				}
				
			}
			
		}else {
			$("#setNum"+key).val("");
		}
		
		
	}
	
	$("#setTtCnt").val(setTtCnt == 0 ? "" : setTtCnt);
	
	/*************히든된 그리드에, 설정매수에 따른 증권 자동선택 끝 *************/	
	
	invtCom.fnSetAssign(sheetid);
	
}

//담보배정설정정보 세팅 (총 담보배정좌수와 금액)
invtCom.fnSetAssign = function(sheetid){
	var assignNum = 0;
	var assignAmt = 0;
	
	//그리드에서 선택된 증권가져오기
	var rows = sheetid.getDataRows(); 
	for(var i=0; i<rows.length; i++){
		
		var selected   = sheetid.getValue(rows[i], "selectYn");
		var billNum    = Number(sheetid.getValue(rows[i], "billNum"));
		var billAmt    = Number(sheetid.getValue(rows[i], "billAmt"));
		
		if(selected == "Y" || selected == 1) {
			assignNum += billNum;
			assignAmt += billAmt;
		}
	}
	
	$("#assignNum").val(com.aprojomma(assignNum == 0 ? "" : assignNum));
	$("#assignAmt").val(com.aprojomma(assignAmt == 0 ? "" : assignAmt));
	
	$("#setTtNum").val($("#assignNum").val());
	
}

//출자증권정보에서 담보배정설정한 매수와, 좌수 가져오기 (담보배정수정화면)
/*invtCom.fnSetOriAssignCnt = function(sheetid){
	var rows = sheetid.getDataRows(); 
	
	var billTypeArr = [];
	$.each(codeList, function(idx, item){
		if(item.cdType == 'IV03') {
			billTypeArr.push(item.cd);
		}	
	});
	
	//권종별 동적으로 변수세팅 - 객체활용
	var billObj = {};
	
	for (var billCd of billTypeArr) { 
		var cSetCnt = "setCnt" + billCd; //권종별설정매수
		var cSetNum = "setNum" + billCd; //권종별설정좌수
		var cAssCnt  = "assCnt"  + billCd; //권종별기배정매수
		var cAssNum  = "assNum"  + billCd; //권종별기배정좌수
		
		billObj[cSetCnt] = 0;
		billObj[cSetNum] = 0;
		billObj[cAssCnt] = 0;
		billObj[cAssNum] = 0;
	}
	
	for(var bill of billTypeArr) {
		
		//배정시 선택했던 권종판단해서 매수, 좌수 추출하기
		for(var cnt=0, j=0; j< rows.length; j++){
			
			var billType   = sheetid.getValue(rows[j], "billType"); //담보설정혹은 배정이 되어있는애들 제외
			var billNum    = sheetid.getValue(rows[j], "billNum");
			var mortYn     = sheetid.getValue(rows[j], "mortYn");
			var selected   = sheetid.getValue(rows[j], "selectYn");
			
			//증권목록중 배정가능한 목록 구분
			if(billNum == bill && (selected == "Y" || selected == 1)) {
				var assCnt = "assCnt"+bill;
				var assNum = "assNum"+bill;
				billObj[assCnt] -= 1;
				billObj[assNum] -= 1;
								 
			}
		}
		
		
		
	}
	
	
	
	
}*/






