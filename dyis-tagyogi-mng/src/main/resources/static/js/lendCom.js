/****************************************************************
 * 
 * 파일명 : lendCom.js
 * 설  명 : 융자 자바스크립트
 * 
 *    수정일      수정자     Version        Function 명
 * ------------    ---------   -------------  ----------------------------
 * 2023.05.22          1.0             최초생성
 * 
 * 
 * **************************************************************/


//융자업무 공통 함수 시작 
var lendCom = new Object();

///////////////////////////////////////////////////////////////////////////////////////////////////////


//동적 융자상품 한도 설정 화면 구성
lendCom.fnSetLendLimit = function(cdType, tblId){

	$("#"+tblId).empty();

	var initHtml = "";

	initHtml += '<colgroup>';
	initHtml += '	<col style="width:80px;">';
	initHtml += '	<col style="width:40px;">';
	initHtml += '	<col style="width:100px;">';
	initHtml += '	<col style="width:100px;">';
	initHtml += '</colgroup>';
	initHtml += '<tbody>';
	initHtml += '	<tr>';
	initHtml += '		<th class="tac">상품</th>';
	initHtml += '		<th class="tac">배수</th>';
	initHtml += '		<th class="tac">한도액</th>';
	initHtml += '		<th class="tac">사용액</th>';
	initHtml += '	</tr>';
	initHtml += '</tbody>';

	$("#" + tblId).append(initHtml);
	$.each(codeList, function(idx, item){
		if(item.cdType == cdType){
			var html = '<tr>';
				html += '<td class="tal"  ><input type="checkbox" name="lendTypes" id="lendTypes'+item.cd+'" value="'+item.cd+'" onClick="fnGuarTypeSel(this)">&nbsp;'+ item.cdNm;
				html += '</td>'; //상품
				html += '<td class="tac">';
				html += 	'<input type="text" name="doubleNum" id="doubleNum'+item.cd+'" style="width:70px;text-align:right" onkeyup="com.fnOnlyDecimal(this);fnSumLimit(this);" maxlength="4"> 배';
				html += '</td>'; //배수
				html += '<td class="tac">';
				html += 	'<input type="text" name="limitAmt" id="limitAmt'+item.cd+'" style="width:100%;text-align:right" onkeyup="com.fnOnlyNum(this);com.makeComma(this);fnLimiAmtSet(this)" tabIndex="3'+idx+'" maxlength="15" readonly>';
				html += '</td>'; //한도액
				html += '<td class="tac">';
				html += 	'<input type="text" name="lendUseAmt" id="lendUseAmt'+item.cd+'" style="width:100%;text-align:right;"  maxlength="15" readonly>';
				html += '</td>'; //한도액(사용액)
				html += '</tr>';

			$("#" + tblId + " > tbody:last").append(html);
		}
	});

	var footHtml = '<tfoot>';
		footHtml += 	'<tr>';
		footHtml += 	'<th class="alignC" scope="colgroup">합계</th>';
		footHtml += 	'<td class="alignC"><input type="text" name="sumDoubleNum" id="sumDoubleNum" style="width:70px;text-align:right" title="" readonly>배</td>';
		footHtml += 	'<td class="alignC"><input type="text" name="sumLimitAmt" id="sumLimitAmt" style="width:100%;text-align:right" title="" readonly></td>';
		footHtml += 	'<td class="alignC"></td>';
		footHtml += '</tfoot>';
	$("#" + tblId + " > tbody").after(footHtml);

}


//융자상품 한도정보 상세
lendCom.fnSetLendLimitView = function(cdType, tblId, lendLimit){
	
	$("#"+tblId).empty();

	var initHtml = "";

	initHtml += '<colgroup>';
	initHtml += '	<col style="width:105px;">';
	initHtml += '	<col style="width:80px;">';
	initHtml += '	<col style="width:120px;">';
	initHtml += '	<col style="width:120px;">';
	initHtml += '	<col style="width:120px;">';
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

	$.each(codeList, function(idx, item){
		if(item.cdType == cdType){
			var getNum = 0;
			var getLimit = 0;
			var getUse = 0;
			if(lendLimit != undefined){
				getNum = lendLimit["doubleNum"+item.cd];
				getLimit = lendLimit["limitAmt"+item.cd];
				getUse = lendLimit["useAmt"+item.cd];
				
			}
			var getRemain = Number(getLimit) - Number(getUse);

			totNum 		+= Number(getNum);
			totLimit 	+= Number(getLimit);
			totUse 		+= Number(getUse);
			totRemain 	+= Number(getRemain);
			
			var html = '<tr>';
				html += '<td class="tal"  >'+ item.cdNm + '</td>'; //상품
				html += '<td class="tar">' + getNum + '배</td>'; //배수
				html += '<td class="tar">' + com.addComma(getLimit) + '</td>'; //한도
				html += '<td class="tar">' + com.addComma(getUse) + '</td>'; //사용
				html += '<td class="tar">' + com.addComma(getRemain) + '<input type="hidden" name="remainAmt" id="remainAmt'+item.cd+'" value="'+getRemain+'" readonly></td>'; //잔액
				html += '</tr>';

			$("#" + tblId + " > tbody:last").append(html);
		}
	});

	var footHtml = '<tfoot>';
		footHtml += 	'<tr>';
		footHtml += 	'<th class="tac" scope="colgroup">합계</th>';
		footHtml += 	'<td class="tar">' + totNum + '배</td>';
		footHtml += 	'<td class="tar" id="totalLimitSum">' + com.addComma(totLimit) + '</td>';
		footHtml += 	'<td class="tar">' + com.addComma(totUse) + '</td>';
		footHtml += 	'<td class="tar">' + com.addComma(totRemain) + '</td>';
		footHtml += '</tfoot>';
	$("#" + tblId + " > tbody").after(footHtml);

}



//융자 대출이자계산 (대출일자, 상환예정일, 이자율, 이자시트, 상환이자 시트)
lendCom.fnInterCalc = function(sheetId, sheetRepay){
	
	let interWay = $("#interWay").val(); //이자산정방식
	let lendDt = $("#lendDt").val();
	let repayPreDt = $("#repayPreDt").val();
	let lendAmt = com.rmComma($("#lendAmt").val());
	let interPer = $("#interPer").val();
	
	if(sheetId === undefined ) sheetId = sheetInter;
	
	if(lendDt == null || lendDt == ""){
		 alert("대출일자를 입력하십시오.");
		 return;
	}
	
	if(repayPreDt == null || repayPreDt == ""){
		 alert("대출상환일자를 입력하십시오.");
		 return;
	}
	
	if(lendAmt == null || lendAmt == ""){
		 alert("대출금액을 입력하십시오.");
		 return;
	}
	
	if(interPer == null || interPer == ""){
		 alert("대출이자율을 입력하십시오.");
		 return;
	} 	
	
	sheetId.removeAll();//마스터 값이 없을 경우
	
	var interBegDt = lendDt;
	var interBegDay = lendDt.substring(8,10);
	var interEndDay = repayPreDt.substring(8,10);
	
	var getRows = sheetId.getDataRows(); //rows 추출
	
	for(x = 0; x <= 11; x++){
		
		//상환여부에 따른 잔액 
		if(sheetRepay !== undefined){
			var getRepayRows = sheetRepay.getDataRows(); //상환정보
			getRepayRows.forEach((item, idx) => {
				//console.log(item);
				var repayDt = com.fnDateGet(item.repayDt, 0);
				
				if(com.fnDateFormat(interBegDt, "") >= com.fnDateFormat(repayDt, "") ){
					lendAmt = item.remainAmt;
				}
				
		    });
		}
		
		var row = getRows[x];
		
		interEndDt = com.fnDateGet(com.fnDateMonGet(interBegDt, 1), -1); //30일 더하고 일자는 종료일 기준 설정 
		if(x == 11){
			interEndDt = com.fnDateGet(repayPreDt, 0); //마지막달은 상환 예정일 전날까지 
		}
		
		interDays = com.fnDateTerm(interEndDt, interBegDt);
		
		if(interWay === "10"){ //월별
			interAmt = Math.floor((lendAmt * interPer / 100 /12)/10)*10;
		}else{ //일별
			interAmt = Math.floor((lendAmt * interPer / 100 / 365 * interDays)/10)*10;
		}
		
		if(row == null){
			initData = { interSeq :x+1 , interBegDt: interBegDt , interEndDt:interEndDt,  interDays:interDays, interAmt : interAmt, interRate: interPer};
			com.fnIbsRowAdd("pre", initData, sheetId);
		}else{
			
			if(row.payStat == "10"){
				
				sheetId.setValue( row, "interBegDt", new Date(interBegDt).getTime());
				sheetId.setValue( row, "interEndDt", new Date(interEndDt).getTime());
				sheetId.setValue( row, "interDays", interDays);
				sheetId.setValue( row, "interAmt", interAmt);
				sheetId.setValue( row, "interRate", interPer);
				sheetId.setValue( row, "interDefaultAmt", 0);
			}
		}
		
		//interBegDt = interEndDt.substring(0, 7)+"-"+interBegDay;	
		interBegDt = com.fnDateGet(interEndDt, 1);
				
	}
	
};




//대출정보조회
lendCom.fnLendSet = function(rowData){
	//이자정보 조회
	param = {lendNo:rowData.lendNo};
	obj = {url:"/lend/selectLend.do", param:param, callBack:lendCom.fnLendSetCallBack, msgYn:"N", loadYn:"Y", sessYn:"Y"};
	com.fnAjaxDef(obj);
}

//대출정보조회 콜백
lendCom.fnLendSetCallBack = function(resData){
	var lendType = resData.lend.lendType;
	com.fnSetKeyVal(resData.lend, 'areaLend'); //대출정보
	
	$("#lendNo").val(resData.lend.lendNo);
	//한도금액
	$("#limitAmt").val(lendContr["limitAmt"+lendType]);
	//지급대상금액
	if(lendType == "12" || lendType == "15"){
		$("#payAmt11").text(resData.lend.payAmt);
	}else{
		$("#payAmt"+lendType).text(resData.lend.payAmt);
	}
	
	//출금은행
	$("#selBank").val(resData.lend.outBank+","+resData.lend.outAcct);
	
	if(lendType == "11"){
		$("#ele_concl").css("display", "");
		sheetConcl.loadSearchData(resData.conclList); //계약체결명세현황
		
	}else if(lendType == "13"){
		$("#ele_note").css("display", "");
		$(".ele_lendType13").show();
		sheetNote.loadSearchData(resData.noteList); //어음담보명세현황
	}else if(lendType == "14"){
		$("#ele_bond").css("display", "");
		$(".ele_lendType14").show();
		sheetBond.loadSearchData(resData.bondList); //매출채권명세현황
	}else if(lendType == "15"){
		$("#ele_claim").css("display", "");
		$("#ele_bond").css("display", "");
		sheetBond.loadSearchData(resData.bondList); //매출채권명세현황
		sheetClaim.loadSearchData(resData.claimList); //매출채권명세현황
    }
    
    if(typeof sheetInter != "undefined"){
		sheetInter.loadSearchData(resData.interList); //이자현황	
	}
	
	//첨부파일 (다건)
    com.fnIbsUploadView($("#myUploadLend"), resData.fileList); 
    $("#lendAtthNo").val(resData.lend.atthNo); //약정수정에서 사용
}
 	

//연체이자산정
lendCom.fnInterDelayCalc = function(evtParam){
	//연체 이자 계산
	if(evtParam.col === "interAmt" || evtParam.col === "interRate" || evtParam.col === "interBegDt" || evtParam.col === "interEndDt"){
		//let rowId = evtParam.row.id;
		let interAmt = evtParam.row.interAmt;
		let interRate = evtParam.row.interRate;
		let interBegDt = com.fnDateGet(evtParam.row.interBegDt, 0);
		let interEndDt = com.fnDateGet(evtParam.row.interEndDt, 0);
		let delayDays = com.fnDateTerm(interBegDt, interEndDt);
		
		let delayAmt = Math.floor(interAmt * interRate / 100 / 365 * delayDays / 10)  * 10;
		sheetDelay.setValue(sheetDelay.getRowById(evtParam.row.id), "delayDays", delayDays);
		sheetDelay.setValue(sheetDelay.getRowById(evtParam.row.id), "delayAmt", delayAmt);
	//	sheetDelay.setValue(sheetDelay.getRowById(evtParam.row.id), "payAmt", delayAmt); // OnKeyUp 이벤트 사용으로 납부금액 자동셋팅 제외 (2024-02-19) cjh
		 		
	}
}





