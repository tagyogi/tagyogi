/****************************************************************
 * 
 * 파일명 : commonGrid.js
 * 설  명 : 공통 자바스크립트
 * 
 *    수정일      수정자     Version        Function 명
 * ------------    ---------   -------------  ----------------------------
 * 2022.02   BSG       1.0             최초생성
 * 
 * grid 공통 기능
 * 
 * **************************************************************/
var lastSelection; //그리드 현재 선택 행 

//그리드 공통 함수 시작 
var comGrid = new Object();

//그리드 데이터 세팅
comGrid.dataSet = function(grid, result){
	if($("#"+grid.attr("id")+"_NoData").length > 0) grid.clearGridData(); //검색 결과가 없습니다 표시 삭제
	
	grid.clearGridData();
	grid[0].addJSONData(result.rows);
	grid[0].grid.endReq();
	
	if(result.rows.length == 0){
		var gridId = grid.attr("id");
		var colModels = grid.jqGrid("getGridParam", "colModel");
		
		$("#gbox_"+gridId+" > div > .ui-jqgrid-bdiv > div > table").append("<tr><td style='text-align:center' colspan='"+colModels.length+"' id='"+gridId+"_NoData'>검색 결과가 없습니다.</td></tr>");
		
	}
};

// 그리드 행 추가(입력)
comGrid.rowAdd = function(grid, setData){
	var rowCnt = grid.getGridParam("reccount");
	if($("#"+grid.attr("id")+"_NoData").length > 0) grid.clearGridData(); //검색 결과가 없습니다 표시 삭제
	
	grid.jqGrid("saveRow",lastSelection); //에디터 비활성화
	
	var rowId = grid.getGridParam("selrow"); //선택셀
	var rowIdx = grid.jqGrid('getInd',rowId); // counting from 1
	
	if(rowIdx == undefined){
		grid.jqGrid("addRowData", rowCnt+1, setData, "first");
	}else{
		grid.jqGrid("addRowData", rowCnt+1, setData, "after", rowIdx);
	}
	
	grid.jqGrid("setSelection", rowCnt+1); 
};

//그리드 행 추가 삭제
comGrid.rowAddDel = function(grid, rowId){
	if(rowId == null){
		alert("삭제할 행을 선택 하십시오.");
		return;
	}
	var rowData = grid.jqGrid("getRowData", rowId);
	if(rowData.status == "C"){
		grid.delRowData(rowId);
	}else{
		alert("추가된 행만 삭제 가능하니다.")
	}
};


//그리드 행 데이터 추출 
comGrid.rowData = function(grid, targ){
		
	grid.jqGrid("saveRow",lastSelection); //에디터 비활성화
	
	var rowDatas = [];
	var allRowData = grid.getRowData();
	for(i = 0; i < allRowData.length; i++){
		
		if(targ == "all"){
			rowDatas[rowDatas.length] = allRowData[i];
		}else if(targ == "chg"){
			if(allRowData[i].status == "C" || allRowData[i].status == "U" ){
				rowDatas[rowDatas.length] = allRowData[i];
	    	}
	    }
    }
	return rowDatas;
} 

//그리드 중복 체크
comGrid.rowDataOver= function(grid, overChks){
	
	var allRowData = grid.getRowData(); //그리드 데이터 담기
	//console.log("00 : " + allRowData);
	for(i = 0; i < overChks.length; i++){ //중복대상
		//console.log("00 : " + overChks[i]);
		var rowChks = []; //중복 체크 데이터
		for(x = 0; x < allRowData.length; x++){ //전체 데이터 체크
			rowChks[x] = eval("allRowData[x]."+overChks[i]);
	    }
		var setArr = new Set(rowChks); //중복 필터
		if(rowChks.length != setArr.size){
			var colModels = grid.jqGrid("getGridParam", "colModel");
			var colModel = colModels.find(function(data){ return data.name === overChks[i]})
			
			alert("중복된 값이 존재합니다. 중복값 [" + colModel.label + "]");
	 		return false;
		}
    }
	return true;
}


//그리드 항목 필터 (입력 불가)
comGrid.rowDataFilter= function(grid, filterId, filterVal){
	
	var allRowData = grid.getRowData(); //그리드 데이터 담기
	for(x = 0; x < allRowData.length; x++){ //전체 데이터 체크
		for(i = 0; i < filterId.length; i++){ //필터 항목
			for(y = 0; y < filterVal.length; y++){ //필터 값
				
				if(eval("allRowData[x]."+filterId[i]) == filterVal[y]){
					alert("입력불가 값이 존재 합니다. 입력불가 [" + filterVal[y] + "]");
			 		return false;
				}
			}
			
		}
	}
	return true;
	
}


//그리드 행 입력 활성화 (gridId, rowid, 활성화대상)
//활성화대상 (키값 인 경우 신규 일대 editable 를 true 로 설정 한다)
comGrid.rowEdit = function(grid, iRow, editables){
	//에디터 설정
	rowData = grid.jqGrid("getRowData", iRow); 
  
	//유형에 따라 키값 수정 가능 여부
	if(rowData.status == "C"){
		if(editables != undefined){
			for(i = 0; i < editables.length; i++){
				grid.setColProp(editables[i],{editable:true});	 
		    }
		}
	}else{
		grid.jqGrid('setCell', iRow, "status","U"); 
	} 
	
	//화면 에디터 활성화 
	grid.jqGrid("saveRow",lastSelection); //이전 에디터 비활성화  
	grid.jqGrid("editRow", iRow, {keys: true} ); //선택 행 에디터 활성화 
	
	lastSelection = iRow;
    
}


//그리드 기본 설정
var styleUI 	= "Bootstrap" 
var autowidth 	= true
var height 		= 300 
var rowNum 		= -1
var page 		= 1
var scroll 		= 1
var viewrecords = true
var cmTemplate 	= { sortable: false }
var datatype 	= "local"
var editurl 	= "clientArray"
/* ************************************************************** 그리드 생성/  */

comGrid.createDef= function(grid, colModel){
	
	grid.jqGrid({
		colModel: colModel
		, styleUI : styleUI
		, autowidth : autowidth
        , height: height
	    , rowNum: rowNum
	    , page: page
	    , scroll: scroll
	    , viewrecords: viewrecords
	    , cmTemplate: cmTemplate
	    , datatype:datatype
	    , editurl: editurl
	    , loadError:function(xhr, status, error){
	    	fnGridCallError(JSON.stringify(xhr.responseText));
		}
	});
}



//그리드 호출오류처리
comGrid.callErr= function(val){
	//세션종료 체크
	if(val.indexOf("로그인") > -1){
		alert("세션이 종료 되었습니다.");
		
		parent.document.forms[0].action = "/husr/loginPage.do";
		parent.document.forms[0].submit();
		
	}else{
		alert("오류가 발생하였습니다.")
	}
}




