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
function ozFirst(pubCnt){

	resultFunc = ""; //결과함수 초기화.

	OZUtil = start_OZUtil;

	//오즈서버
	OZUtil.setParameter("connection.servlet","http://" + ozIp + "/oz80/server");

	//마크애니
	if(pubCnt == 1) {
		OZUtil.setParameter("connection.2dbarcodeurl","http://" + ozIp + "/oz80/markany");
		OZUtil.setParameter("connection.extraparam","ShowCustomPrintUI=true,MaxCopies=1,SupportPrinter=3,DatFileName=MaPrintInfo_NoaXOZ.dat");
		OZUtil.setParameter("print.externalmoduleEx","OZPrintBarcodeEx_MarkAny.dll");
	}


	OZUtil.setParameter("viewer.printcommand","true"); //프린트 처리 결과 JSP 처리
	OZUtil.addEventListener("OZPrintCommand", OZPrintCommand_OZViewer,OZViewerID); //결과 처리 호출

}

//오즈 하단부 설정
function ozLast(isPrt, pubCnt){


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
		OZUtil.setParameter("export.applyformat",   "pdf");
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

//홈페이지용
function ozLoad(ozName, nameArr, valueArr, pubCnt, isPrt, rtnProc) {
	//상단부 설정
	ozFirst(pubCnt);

	//결과처리
	var commandUrl = "";
	if(rtnProc !=  undefined){
		OZUtil.setParameter("viewer.printcommand","true"); //프린트 처리 결과 JSP 처리
		if(rtnProc == "guar"){
			commandUrl = "https://"+serverUrl+"/guar/updateGuarPrintAfter.do";
			//commandUrl = "http://localhost:9099/guar/updateGuarPrintAfter.do";	//임시 테스트
		}else if(rtnProc == "lend"){
			commandUrl = "https://"+serverUrl+"/paper/confIssueAfter.do";
		}else if(rtnProc == "doAfter"){
			resultFunc = "doAfter"; //결과함수 초기화
		}else if(rtnProc == "conf"){ //민원처리 발급처리 업데이트

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
    	OZUtil.setParameter("connection.pcount", nameArr.length);
    	for(var i=0; i<nameArr.length; i++) {
    	    OZUtil.setParameter("connection.args"+(i+1), nameArr[i]+"=" + valueArr[i]);

    	    if(rtnProc !=  undefined){ //프린터 처리 결과 변수 설정
    	    	OZUtil.setParameter("ozcookieparam"+(i+1), commandUrl + "="+nameArr[i]+"=" + encodeURIComponent(valueArr[i]) ); //변수1

    	    }
    	}
    }

	ozLast(isPrt, pubCnt);

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//유형별 오즈 설정
//홈페이지용(보증)
function ozGuar(guarType, reportType, guarNo, pubDt) {
	var ozName = "";
	var rtnProc = "guar";

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

    if(reportType == 10){
    	ozName = "GR_이행보증청약서_사용자";
    	if(guarType == 31 ||guarType == 32 ||guarType == 33 ||guarType == 34){
    		ozName = "GR_신설보증청약서_사용자";
    	}

    	rtnProc = ""; //청약서는 결과 처리 없음
    }

	var nameArr = ["guar_no", "guar_pub_dt"];
    var valueArr = [guarNo, pubDt];
   //오즈파일명, 변수명, 변수값, 출력수, pdf저장여부, 결과처리여부
    ozLoad(ozName, nameArr, valueArr, 1, "pdf", rtnProc);

}




//홈페이지용(대출)
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


	    var nameArr = new Array("memb_no", "bas_dt", "pub_dt", "purpose", "st_yn","conf_cd", "conf_no","lend_no","inter_beg_dt","inter_end_dt");
	    var valueArr = new Array(data.membNo, data.basDt, data.pubDt, data.purpose, "Y", data.confCd, data.confNo, data.lendNo, data.interBegDt, data.interEndDt);

	    //오즈파일명, 변수명, 변수값, 출력수, pdf저장여부, 결과처리여부
	    ozLoad(ozName, nameArr, valueArr, 1, "pdf", rtnProc);
	}

}

//유형별 오즈 설정 끝
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



