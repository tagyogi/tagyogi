//var ozIp = "www.kocfc.or.kr";
//var serverUrl = "mng.kocfc.or.kr";
var host = window.location.host;
var ozIp = "175.125.91.225:8088"; //webtob서버
var serverUrl = "175.125.91.225";
var port = "18080"; //리턴 처리에서만 사용

if(host.indexOf("kocfc.or.kr") > -1){
	//ozIp = "www.kocfc.or.kr";
	serverUrl = "mng.kocfc.or.kr";
	port = "18080";
}

// http://175.125.91.225:8088/oz80/server
var OZUtil;
var namespace = "kocfcExeViewer"; //exeViewer
var OZViewerID = "OZViewer";
var resultFunc = "";

OZViewerLoaded = function()
{
}

//프린터 출력 결과 처리
OZPrintCommand_OZViewer = function(msg, code, reportname, printername, printcopy, printpages, printrange, username, printerdrivername, printpagesrange) {
	//console.log("ozprintcommand");
	if(code==0) {
		//console.log("프린트 결과 메시지 : " + msg);
       	//console.log("보고서 이름 : " + reportname);
       	//console.log("프린터 이름 : " + printername);
       	//console.log("인쇄 매수 : " + printcopy);
       	//console.log("인쇄된 용지 매수 : " + printpages);
       	//console.log("인쇄 범위 : " + printrange);
       	//console.log("사용자 이름 : " + username);
       	//console.log("프린터 드라이버 이름 : " + printerdrivername);
       	//console.log("인쇄된 페이지 범위 : " + printpagesrange);
		alert(msg);
		OZUtil.CloseEXEViewer(OZViewerID); //뷰어종료
		if(resultFunc == "doAfter"){
			doAfter();
		}
    }
}

//오즈 상단부 설정
function ozFirst(isMark){

	resultFunc = ""; //결과함수 초기화

	OZUtil = start_OZUtil;

	//오즈서버
	OZUtil.setParameter("connection.servlet","http://" + ozIp + "/oz80/server");

	//마크애니
	if(isMark) {
		OZUtil.setParameter("connection.2dbarcodeurl","http://" + ozIp + "/oz80/markany");
		OZUtil.setParameter("connection.extraparam","ShowCustomPrintUI=true,MaxCopies=1,SupportPrinter=3,DatFileName=MaPrintInfo_NoaXOZ.dat");
		OZUtil.setParameter("print.externalmoduleEx","OZPrintBarcodeEx_MarkAny.dll");
	}

	OZUtil.setParameter("viewer.printcommand","true"); //프린트 처리 결과 JSP 처리
	OZUtil.addEventListener("OZPrintCommand", OZPrintCommand_OZViewer,OZViewerID); //결과 처리 호출

}

//오즈 하단부 설정
function ozLast(isOnlyFirst, isPrt, pubCnt){

	//보증서용(약관제외 1페이지만)
    if(isOnlyFirst) {
    	OZUtil.setParameter("print.pagerange","range");
    	OZUtil.setParameter("print.pages","1");
    }

    OZUtil.setParameter("print.lockopt","false");
	OZUtil.setParameter("print.once","true");
	OZUtil.setParameter("print.copies", pubCnt);

	//메뉴옵션
	//pdf 저장 처리
	if(isPrt == undefined) {
		OZUtil.setParameter("toolbar.savedm",   	"true");
		OZUtil.setParameter("toolbar.save",     	"true");
	} else if(isPrt == "pdf") {	//관리자에서 pdf 다운로드 추가 2019.02.14
		OZUtil.setParameter("toolbar.save",     	"true");
		OZUtil.setParameter("export.applyformat",   "");
	} else {
		OZUtil.setParameter("toolbar.savedm",       "false");
		OZUtil.setParameter("toolbar.save",         "false");
		
	}

	OZUtil.setParameter("viewer.usemenubar",	"false"); // 뷰어 메뉴바 숨기기
	//OZUtil.setParameter("toolbar.savedm",		"false");
	//OZUtil.setParameter("toolbar.save",		"false");

	OZUtil.setParameter("toolbar.showtree",		"false");
	OZUtil.setParameter("toolbar.addmemo",		"false");
	OZUtil.setParameter("toolbar.close",		"false");
	OZUtil.setParameter("toolbar.open",			"false");
	OZUtil.setParameter("toolbar.inputparameter","false");

	OZUtil.setParameter("toolbar.help","false");

	OZUtil.setParameter("information.debug","true");


	//console.log(OZUtil);
	var opt = {};
	opt['namespace'] = namespace;
	OZUtil.setOption(opt);
	OZUtil.createViewer(OZViewerID,OZViewerLoaded);
}

//관리자용
function ozLoad(ozName, nameArr, valueArr, pubCnt, isMark, isPrt, rtnProc, isOnlyFirst) {
//		ozLoad("GR_영수증_(공제계약자_보관용)", nameArr, valArr, 1, false, false, "doAfter");
	//상단부 설정
	ozFirst(isMark)

	//결과처리
	var commandUrl = "";
	if(rtnProc !=  undefined && rtnProc != ""){
		resultFunc = "doAfter"; //결과함수 초기화

		if(rtnProc == "guar"){
			commandUrl = "http://"+serverUrl+":"+port+"/guar/updateGuarPrintAfter.do";
			//commandUrl = "http://localhost:8080/guar/updateGuarPrintAfter.do";	//임시 테스트
		}else if(rtnProc == "investBill"){ //출자증권
			//commandUrl = "http://localhost:8080/invt/updateInvtBillIssue.do"; //테스트
			commandUrl = "http://"+serverUrl+":"+port+"/updateInvtBillIssue.do";
		}/*else if(rtnProc == "investKeep"){ //출자증권보관증 - 민원처리발급 공통
			commandUrl = "http://"+serverUrl+":"+port+"/invt/invtKeepIssueAfter.do";
		}*/else if(rtnProc == "conf"){ //민원처리 발급처리 업데이트

			//commandUrl = "http://localhost:8080/comConf/updateConfProcStat.do";
			commandUrl = "http://"+serverUrl+":"+port+"/comConf/updateConfProcStat.do";
		}
		//alert(commandUrl);
		OZUtil.setParameter("viewer.ozcommandurl", commandUrl); //프린트 처리 결과 JSP 처리
	}

	//오즈정보
	var odiName = ozName.indexOf("_20") > -1 ? ozName.substring(0, ozName.length - 5) : ozName;
	odiName = ozName.indexOf("_2019_1") > -1 ? ozName.substring(0, ozName.length - 7) : odiName; //약관수정 건 추가

	OZUtil.setParameter("connection.reportname","/" + ozName + ".ozr");
	OZUtil.setParameter("odi.odinames", odiName);

	//변수
	if(nameArr.length > 0) {
    	OZUtil.setParameter("odi." + odiName + ".pcount", nameArr.length);
    	for(var i=0; i<nameArr.length; i++) {
    	    OZUtil.setParameter("odi." + odiName + ".args"+(i+1), nameArr[i]+"=" + valueArr[i]);

    	    if(rtnProc !=  undefined && rtnProc != ""){ //프린터 처리 결과 변수 설정

    	    	OZUtil.setParameter("ozcookieparam"+(i+1), commandUrl + "="+nameArr[i]+"=" + encodeURIComponent(valueArr[i]) ); //변수1
    	    	console.log(valueArr[i]);
    	    }
    	}
    }

	ozLast(isOnlyFirst, isPrt, pubCnt);

}


//관리자용(odi 여러건
function ozLoad2(ozName, nameArr, valueArr, nameArr2, valueArr2, pubCnt, isOnlyFirst) {


	ozFirst(false);

	//오즈정보
	var odiName = ozName.indexOf("_20") > -1 ? ozName.substring(0, ozName.length - 5) : ozName;
	odiName = ozName.indexOf("_2019_1") > -1 ? ozName.substring(0, ozName.length - 7) : odiName; //약관수정 건 추가

	OZUtil.setParameter("connection.reportname","/" + ozName + ".ozr");
	OZUtil.setParameter("odi.odinames", odiName);

	//ODI 파라메터
	if(nameArr.length > 0) {
		OZUtil.setParameter("odi." + odiName + ".pcount", nameArr.length);

		for(var i=0; i<nameArr.length; i++) {
			OZUtil.setParameter("odi." + odiName + ".args" + (i+1), nameArr[i] + "=" + valueArr[i]);
		}
	}


	//변수
	if(nameArr2.length > 0) {
		OZUtil.setParameter("connection.pcount", nameArr2.length);
		for(var i=0; i<nameArr2.length; i++) {
		    OZUtil.setParameter("connection.args"+(i+1), nameArr2[i]+"=" + valueArr2[i]);

		}
	}

	OZUtil.setParameter("viewer.printcommand","true"); //프린트 처리 결과 JSP 처리
	OZUtil.addEventListener("OZPrintCommand", OZPrintCommand_OZViewer,OZViewerID); //결과 처리 호출

	ozLast(isOnlyFirst, false, pubCnt);

}


//관리자용(여러건)
function ozLoadMulti(ozNameArr, nameArr, valueArr, pubCnt, isMark, rtnProc, isDirPrt) {

	//console.log(nameArr);
	//console.log(valueArr);
	ozFirst(isMark);

	//결과처리
	var commandUrl = "";
	if(rtnProc !=  undefined && rtnProc != ""){
		if(rtnProc == "investBill"){ //출자증권
			commandUrl = "http://"+serverUrl+":"+port+"/invt/updateInvtBillIssue.do";
			//commandUrl = "http://localhost:8080/invt/updateInvtBillIssue.do";
		}
		OZUtil.setParameter("viewer.ozcommandurl", commandUrl); //프린트 처리 결과 JSP 처리
	}


	var procNm = ""; //리턴 처리 변수명 설정
	var procVal = ""; //리턴 처리 변수값 설정

	for(var k=0; k < ozNameArr.length; k++) {
		if(k == 0) {
			//오즈서버
			OZUtil.setParameter("connection.servlet","http://" + ozIp + "/oz80/server");

			if(isMark) {
				OZUtil.setParameter("connection.2dbarcodeurl","http://" + ozIp + "/oz80/markany");
				OZUtil.setParameter("connection.extraparam","ShowCustomPrintUI=true,MaxCopies=1,SupportPrinter=3,DatFileName=MaPrintInfo_NoaXOZ.dat");
				OZUtil.setParameter("print.externalmoduleEx","OZPrintBarcodeEx_MarkAny.dll");
			}

			OZUtil.setParameter("connection.reportname","/" + ozNameArr[k] + ".ozr");

			var inNameArr = nameArr[k];
  	    	var inValueArr = valueArr[k];

			//변수
			if(inNameArr.length > 0) {
		    	OZUtil.setParameter("connection.pcount", inNameArr.length);
		    	for(var i=0; i<inNameArr.length; i++) {
		    	    OZUtil.setParameter("connection.args"+(i+1), inNameArr[i]+"=" + inValueArr[i]);

		    	    if(rtnProc !=  undefined && rtnProc != ""){ //프린터 처리 결과 변수 설정

			    	    if(i == 0){
		    	    		procNm  += inNameArr[i];
		    	    		procVal += inValueArr[i];
		    	    	}else{
		    	    		procNm  += ","+inNameArr[i];
		    	    		procVal += ","+inValueArr[i];
		    	    	}
		    	    }
		    	}
		    }
		}else{
			//처리 변수 구분
			procVal += ":";

			//오즈서버
			OZUtil.setParameter("child" + k + ".connection.servlet","http://" + ozIp + "/oz80/server");

			if(isMark) {
				OZUtil.setParameter("child" + k + ".connection.2dbarcodeurl","http://" + ozIp + "/oz80/markany");
				OZUtil.setParameter("child" + k + ".connection.extraparam","ShowCustomPrintUI=true,MaxCopies=1,SupportPrinter=3,DatFileName=MaPrintInfo_NoaXOZ.dat");
				OZUtil.setParameter("child" + k + ".print.externalmoduleEx","OZPrintBarcodeEx_MarkAny.dll");
			}

			OZUtil.setParameter("child" + k + ".connection.reportname","/" + ozNameArr[k] + ".ozr");

			var inNameArr = nameArr[k];
  	    	var inValueArr = valueArr[k];

			//변수
			if(inNameArr.length > 0) {
				OZUtil.setParameter("child" + k + ".connection.pcount", inNameArr.length);
		    	for(var i=0; i<inNameArr.length; i++) {
		    	    OZUtil.setParameter("child" + k + ".connection.args"+(i+1), inNameArr[i]+"=" + inValueArr[i]);
		    	    if(rtnProc !=  undefined && rtnProc != ""){ //프린터 처리 결과 변수 설정
		    	    	if(i == 0) procVal += inValueArr[i];
		    	    	else procVal += ","+inValueArr[i];

		    	    	//OZUtil.setParameter("ozcookieparam"+(i+1), commandUrl + "="+inNameArr[i]+"=" + inValueArr[i]); //변수1
		    	    }
		    	}
		    }
		}
	}

	//리턴 처리 변수 설정
	if(rtnProc !=  undefined && rtnProc != ""){ //프린터 처리 결과 변수 설정
		var procNms = procNm.split(","); //변수 개수
		//console.log(procNms);
		for(var i=0; i < procNms.length; i++) { //변수 개수 만큼 회전
			procVals = procVal.split(":"); //페이지별 변수값 배열 추출
			for(var x=0; x < procVals.length; x++) {
				getVals = procVals[x].split(",");
				if(x == 0) setVals = getVals[i];
				else setVals += "_"+getVals[i];
			}
			//console.log(setVals);
			OZUtil.setParameter("ozcookieparam"+(i+1), commandUrl + "="+nameArr[i]+"=" + encodeURIComponent(setVals)); //변수1
		}
	}



	OZUtil.setParameter("global.concatpage", "true");
	OZUtil.setParameter("viewer.childcount", ozNameArr.length -1);

	OZUtil.setParameter("viewer.printcommand","true"); //프린트 처리 결과 JSP 처리
	OZUtil.addEventListener("OZPrintCommand", OZPrintCommand_OZViewer,OZViewerID); //결과 처리 호출

	ozLast(false, false, pubCnt);

}



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//유형별 오즈 설정

//관리자용(보증)
function ozGuar(guarType, guarNo, pubDt, membNo, pubCnt) {
	var ozName = "";
	if(guarType == 11) ozName = "GR_이행입찰보증증권";
    if(guarType == 12) ozName = "GR_이행계약보증증권";
    if(guarType == 13) ozName = "GR_이행하자보증증권";
    if(guarType == 14) ozName = "GR_이행선금급보증증권";
    if(guarType == 15) ozName = "GR_이행지급보증증권";
    if(guarType == 21) ozName = "GR_채무보증증권";
    if(guarType == 31) ozName = "GR_성능이행보증증권";
    if(guarType == 32) ozName = "GR_인허가보증증권";
    if(guarType == 33) ozName = "GR_상품판매대금보증증권";
    if(guarType == 34) ozName = "GR_지급보증의지급보증증권";

    var pubDt = pubDt.replace(/-/gi, "");
    if(pubDt >= "20190301") {
    	ozName += "_2019";

    	if(guarType == 15 || guarType == 33){	//이행지급보증증권, 상품판매대금보증증권 약관변경
    		if(pubDt >= "20190930") {
        		ozName += "_1";
        	}
    	}
    }else if(pubDt >= "20171124") {
    	if(!(guarType == 31 || guarType == 32 || guarType == 33 || guarType == 34)){
    		ozName += "_2017";
    	}
    }
	if(guarType == 11)  ozName = "GR_이행입찰보증증권_2023";
	
	var nameArr = ["guar_no", "guar_pub_dt", "memb_no"];
    var valueArr = [guarNo, pubDt, membNo];

    if(pubCnt == undefined) {
    	pubCnt = 1;
    }


    //오즈파일명, 변수명, 변수값, 출력수, 마크애니, pdf저장여부, 결과처리여부
    ozLoad(ozName, nameArr, valueArr, pubCnt, true, "pdf", "guar");
}


//관리자용(보증청약서)
function ozGuarSubs(reportType, guarType, guarNo) {
	ozName = "";

	if(reportType == 10){
		if(guarType.substring(0,1) == 3){
			ozName = "GR_신설보증청약서_사용자";
		}else{
			ozName = "GR_이행보증청약서_사용자";
		}
	}else{
		if(guarType.substring(0,1) == 3){
			ozName = "GR_신설보증청약서";
		}else{
			ozName = "GR_이행보증청약서";
		}
	}

	if(guarType == 21) ozName = "GR_채무보증청약서";

	var nameArr = new Array("guar_no");
    var valueArr = new Array(guarNo);
	ozLoad(ozName, nameArr, valueArr, 1, false);

}



//관리자용(대출)
function ozLend(data) {

	//console.log(data);
	//$(this).find("LEND_DT").text()

	if(data.memb_no == ""){
		alert("조회된 정보가 없습니다.");
		return;
	}else{
		var ozName = "";
		var rtnProc = "lend";

		if(data.confCd == "LP") ozName = "LD_대출상환증명서";
	    if(data.confCd == "LR") ozName = "LD_대출잔액증명서";
	    if(data.confCd == "LI") ozName = "LD_대출이자납입증명서";


	    var nameArr = new Array("memb_no", "bas_dt", "pub_dt", "purpose", "st_yn","conf_cd", "conf_no","lend_no");
	    var valueArr = new Array(data.membNo, data.basDt, data.pubDt, data.purpose, "Y", data.confCd, data.confNo, data.lendNo);

	        //오즈파일명,    변수명,    변수값, 출력수, pdf저장여부, 결과처리여부
	    ozLoad(ozName, nameArr, valueArr, 1, "pdf", isPrt, rtnProc);
	}         

}

//유형별 오즈 설정  끝
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


