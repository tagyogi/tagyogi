var startAt = 0; ///////////// 일요일 표시 부분 / 0 : 일요일(일월화...) / 1 : 월요일(...금토일)
var showToday = 1; // 오늘 날자 표시 유무 - 0 : 감춤 / 1 : 보임
/////////////////////////////// 각 변수 선언 ///////////////////
var crossobj, crossMonthObj, crossYearObj, monthSelected, yearSelected, dateSelected, omonthSelected, oyearSelected, odateSelected, monthConstructed, yearConstructed, intervalID1, intervalID2, timeoutID1, timeoutID2, intervalID3, intervalID4, timeoutID3, timeoutID4, ctlToPlaceValue, ctlNow, dateFormat, nStartingMonth, nStartingYear, oFromDate, oPos;

var bPageLoaded = false;
var ie = document.all;
var dom = document.getElementById;
var bShow = false;
var ns4 = document.layers;

var today = new	Date(); /////////////// 날자 변수 선언
var dateNow = today.getDate(); //////////////// 로컬 컴퓨터의 일(day)을 구함
var monthNow = today.getMonth(); ///////////////// 로컬 컴퓨터의 월(month)을 구함
var yearNow = today.getFullYear(); ///////////////// 로컬 컴퓨터의 년(year)을 구함
var firstYearNow = today.getFullYear(); ///////////////// 로컬 컴퓨터의 년(year)을 구함

var	monthName  =	new	Array("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월");
var	monthName2 =	new	Array("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월");
var imgPath = "../images/calendar";
var isYearSelectBox = false;
var callback_func="";

function init(){
    var oPopup = document.createElement("div");		//window.createPopup();
    oPopup.setAttribute("id", "calendar");
    oPopup.setAttribute("style", "display:none;");

    var strCalendar;
    var cleft;
    var ctop;

    if(dom) {
        strCalendar  =  '';
        strCalendar += '  <table>';
        strCalendar += '   <tr>';
        strCalendar += '     <td>';
        strCalendar += '       <table>';
        strCalendar += '         <tr> ';
        strCalendar += '           <td> ';
        strCalendar += '             <span id="captionDiv"></span>';
        strCalendar += '           ........................................................</td>';
        strCalendar += '         </tr>';
        strCalendar += '         <tr> ';
        strCalendar += '           <td>';
        strCalendar += '             <span id="calContent"></span>';
        strCalendar += '           </td>';
        strCalendar += '         </tr>';
        strCalendar += '       </table>';
        strCalendar += '     </td>';
        strCalendar += '    </tr>';
        strCalendar += '  </table>';

        strCalendar += '<div id="selectMonth" style="position:absolute;visibility:hidden;"></div>';
        strCalendar += '<div id="selectYear"  style="position:absolute;visibility:hidden;"></div>';

        oPopup.innerHTML = strCalendar;

        var tt = document.getElementsByTagName("html");
        if(tt[0] != null){
            tt[0].appendChild(oPopup);
            calcInit();
        }
    }
}

function calcInit() {
    if(!ns4) {

        crossobj = $("#calendar");
        crossMonthObj = $("#selectMonth");
        crossYearObj = $("#selectYear");

        monthConstructed = false;
        yearConstructed = false;

        sHTML1= '<table>';
        sHTML1+='  <tr> ';
        sHTML1+='    <td class="calendar_img"><span class="cal_arrow prev" onclick="selectDecYear()">\<</span></td>';
        sHTML1+='    <td class="calendar_day"><span id="spanYear"></span></td>';
        sHTML1+='    <td class="calendar_img" ><span class="cal_arrow next" onclick="selectIncYear()">\></span></td>';
        sHTML1+='  </tr>';
        sHTML1+='</table>';
        sHTML1+='<table class="mt5">';
        sHTML1+='  <tr> ';
        sHTML1+='    <td class="calendar_img"><span class="cal_arrow prev" onclick="selectDecMonth()">\<<span/></td>'; //추가
        sHTML1+='   <td class="calendar_prev-month"><span id="spanMonth"></span><span id="month_1" onclick="moveMonth(1);" >1</span>.<span id="month_2" onclick="moveMonth(2);" >2</span>.<span id="month_3" onclick="moveMonth(3);" >3</span>.<span id="month_4" onclick="moveMonth(4);" >4</span>.<span id="month_5" onclick="moveMonth(5);" >5</span>.<span id="month_6" onclick="moveMonth(6);" >6</span>.<span id="month_7" onclick="moveMonth(7);" >7</span>.<span id="month_8" onclick="moveMonth(8);" >8</span>.<span id="month_9" onclick="moveMonth(9);" >9</span>.<span id="month_10" onclick="moveMonth(10);" >10</span>.<span id="month_11" onclick="moveMonth(11);" >11</span>.<span id="month_12" onclick="moveMonth(12);" >12</span></td>';//추가
        sHTML1+='    <td class="calendar_img" ><span class="cal_arrow next" onclick="selectIncMonth()">\></span></td>';//추가
        sHTML1+='  </tr>';
        sHTML1+='</table>';

        $("#captionDiv").html(sHTML1);

        bPageLoaded = true;
    }
}

function padZero(num) {
    return (num < 10)? '0' + num : num;
}

function constructDate(d,m,y) {
    sTmp = dateFormat;
    sTmp = sTmp.replace("dd","<e>");
    sTmp = sTmp.replace("d","<d>");
    sTmp = sTmp.replace("<e>",padZero(d));
    sTmp = sTmp.replace("<d>",d);
    sTmp = sTmp.replace("mmmm","<p>");
    sTmp = sTmp.replace("mmm","<o>");
    sTmp = sTmp.replace("mm","<n>");
    sTmp = sTmp.replace("m","<m>");
    sTmp = sTmp.replace("<m>",m+1);
    sTmp = sTmp.replace("<n>",padZero(m+1));
    sTmp = sTmp.replace("<o>",monthName[m]);
    sTmp = sTmp.replace("<p>",monthName2[m]);
    sTmp = sTmp.replace("yyyy",y);

    return sTmp.replace("yy",padZero(y%100));
}

function closeCalendar() {
    if(oFromDate == undefined || typeof(oFromDate.val()) == "undefined" || oFromDate.val() == "") {
        $("#calendar").hide();
        var ctlGetVal = constructDate(dateSelected,monthSelected,yearSelected);
        ctlToPlaceValue.val(ctlGetVal);
        ctlToPlaceValue.trigger('change');
    } else {
        if (oPos == 2) {
            if(oFromDate.val().length ==0){return;}

            if(chkData(oFromDate.val(), constructDate(dateSelected,monthSelected,yearSelected), "-")){
                alert("종료일이 시작일보다 작습니다.");
                ctlToPlaceValue.val(defaultDateT);
            } else {
                $("#calendar").hide();
                var ctlGetVal = constructDate(dateSelected,monthSelected,yearSelected);
                ctlToPlaceValue.val(ctlGetVal);
                ctlToPlaceValue.trigger('change');

            }
        } else if (oPos == 1){
            if(oFromDate.val().length ==0){return;}
            if(chkData(constructDate(dateSelected,monthSelected,yearSelected), oFromDate.val(), "-")){
                alert("시작일이 종료일보다 큽니다.");
                ctlToPlaceValue.val(defaultDateF);
            } else {
                $("#calendar").hide();
                var ctlGetVal = constructDate(dateSelected,monthSelected,yearSelected);
                ctlToPlaceValue.val(ctlGetVal);
                ctlToPlaceValue.trigger('change');
            }
        }
    }
    if(callback_func != undefined && callback_func != "") {
		callback_func();
	};
}

function moveMonth(str_month) {
    monthSelected = Number(str_month)-1;

    if (monthSelected>11) {
        monthSelected=0;
        yearSelected++;
    }
    constructCalendar();
}
function moveMonthR() { //추가
    monthSelected = 1;

    if (monthSelected<2) {
        monthSelected=11;
        yearSelected--;
    }
    constructCalendar();
}

function moveMonthF() { //추가
    monthSelected = 12;

    if (monthSelected>11) {
        monthSelected=0;
        yearSelected++;
    }
    constructCalendar();
}

function selectChgYear(yearChk) {
    yearSelected=parseInt(yearChk.value);
    yearConstructed=false;
    constructCalendar();
    popDownYear();
}

function selectDecYear() {
    yearSelected=parseInt(yearSelected-1);
    $("#yearBox").val(yearSelected);
    yearConstructed=false;
    constructCalendar();
    popDownYear();
}

function selectIncYear() {
    yearSelected=parseInt(yearSelected+1);
    if(yearSelected > firstYearNow) firstYearNow = yearSelected;
    yearConstructed=false;
    constructCalendar();
    popDownYear();
}
function selectDecMonth() { //추가
    monthSelected=parseInt(monthSelected-1);
    monthConstructed=false;
    if(monthSelected<0){//추가
        moveMonthR();
    }
    constructCalendar();
    popDownMonth();
}
function selectIncMonth() {//추가
    monthSelected=parseInt(monthSelected+1);
    monthConstructed=false;
    if(monthSelected>11){//추가
        moveMonthF();
    }
    constructCalendar();
    popDownMonth();
}

function popDownYear() {
    clearInterval(intervalID1);
    clearTimeout(timeoutID1);
    clearInterval(intervalID2);
    clearTimeout(timeoutID2);
    crossYearObj.hide();
}
function popDownMonth() {//추가
    clearInterval(intervalID3);
    clearTimeout(timeoutID3);
    clearInterval(intervalID4);
    clearTimeout(timeoutID4);
    crossMonthObj.hide();
}
function getDataDay(rYear, rMonth, rDay, num, flag){
    var vn_Date;
    var re_day;
    if(flag =='M'){
        vn_Date=new Date(Number(rYear), Number(rMonth), Number(rDay)-num);
    }else{
        vn_Date=new Date(Number(rYear), Number(rMonth), Number(rDay)+num);
    }
    re_day = ""+vn_Date.getDate();

    if(re_day.length  > 1){

    }else{
        re_day = "0"+re_day;
    }

    return re_day;
}

var calTimer;
var setTimer;
function yearSetAct() {
    clearTimeout(calTimer);
    calendarDiv2IsOver = true;
    setTimer = setTimeout(function() {
        calendarDiv2IsOver = true;
    }, 100);
}

function constructCalendar() {
    var aNumDays = Array (31,0,31,30,31,30,31,31,30,31,30,31);
    var dateMessage;
    var startDate =	new Date (yearSelected,monthSelected,1);
    var endDate;
    var intWeekCount = 1;

    if(monthSelected==1) {
        endDate	= new Date (yearSelected,monthSelected+1,1);
        endDate	= new Date (endDate	- (24*60*60*1000));
        numDaysInMonth = endDate.getDate();
    } else {
        numDaysInMonth = aNumDays[monthSelected];
    }

    datePointer = 0;
    dayPointer = startDate.getDay() - startAt;

    if(dayPointer<0) {
        dayPointer = 6;
    }

    sHTML ='<table class="days_wrap">';
    sHTML +='  <tr>';
    sHTML +='    <td >일</td>';
    sHTML +='    <td >월</td>';
    sHTML +='    <td >화</td>';
    sHTML +='    <td >수</td>';
    sHTML +='    <td >목</td>';
    sHTML +='    <td >금</td>';
    sHTML +='    <td >토</td>';

    sHTML +="  </tr>";
    sHTML +="</table>";

    sHTML +='<table class="days">';
    sHTML +='  <tr >';

    for(var i=1; i<=dayPointer;i++)	{  // 빈 날짜
        sHTML +='<td>&nbsp;</td>';
    }
    var trChk = 0;
    for(var datePointer=1; datePointer<=numDaysInMonth; datePointer++) {
        dayPointer++;
        if(trChk == 1){
            sHTML += "<tr>";
            trChk = 0;
        }
        sHTML += '<td>';
        //sHTML += '  <table>';
        //sHTML += '    <tr>';

		//dateNow = 21;	//테스트
        var divClass = "calendar_day";	//평일
		if(dayPointer % 7 == 1) 		divClass = "calendar_sunday";	//일요일
		else if(dayPointer % 7 == 0) 	divClass = "calendar_saturday";	//토요일

		if((datePointer==dateNow)&&(monthSelected==monthNow)&&(yearSelected==yearNow)){	//오늘
			divClass += " today"
		}
		sHTML += '<div class="'+divClass+'" >';

		/*if ((datePointer==dateNow)&&(monthSelected==monthNow)&&(yearSelected==yearNow)){	//오늘
            sHTML += ' <div class="calendar_today" >';
        }else if (dayPointer % 7 == 1 ){
            sHTML += ' <div class="calendar_sunday" >';
        }else if (dayPointer % 7 == 0 ){
            sHTML += ' <div class="calendar_saturday" >';
        }else{
            sHTML += ' <div class="calendar_day" >';
        }*/
        sHTML += ' <a class="link02" onclick="dateSelected='+datePointer+';closeCalendar();">' + datePointer + '</a>';
        sHTML += '      </div>';

       // sHTML += '    </tr>';
        //sHTML += '  </table>';
        sHTML += '</td>';
        if (dayPointer % 7 == 0) {
            sHTML += "</tr>";
            trChk = 1;
            intWeekCount++;
        }

    }
    var num_count = 1;
    if((dayPointer%7) != 0){
        for ( var j=(dayPointer%7) ; j<7 ; j++ ){
            sHTML +='<td>&nbsp;</td>';
        }
    }
    if(trChk == 0) sHTML += '</tr>';

    sHTML +='</table>';
    sHTML +='</tr>';
    $("#calContent").html(sHTML);

    if(isYearSelectBox) {
        var yearTag = '<select id="yearBox" onmouseover="yearSetAct();" onfocus="yearSetAct();" onclick="yearSetAct();" onchange="selectChgYear(this);" onmouseout="setTimer = null;" onblur="setTimer = null;">';
        for(var i=firstYearNow; i>=1970; i--) {
            yearTag += '<option value="' + i + '">' + i + '</option>';
        }
        $("#spanYear").html("&nbsp;" +yearTag+"\ub144");
    } else {
        $("#spanYear").html(yearSelected+"\ub144");
    }

    for(i=1;i<=12;i++){
        if((monthSelected+1) == i) $('#month_'+i).addClass('cur');
        else $('#month_'+i).removeClass('cur');
    }

    var popHeight;
    if (intWeekCount == 6)
        popHeight = 200;
    else
        popHeight = 210;

    if( navigator.appName.indexOf("Microsoft") > -1 ){
        if( navigator.appVersion.indexOf("MSIE 9") > -1){       // 익스플로러이면 버전 8인지 확인
            $("#calendar").attr("style", "position:absolute;left:"+cleft+"px;top:"+ctop+"px;width:208px;height:auto;z-index:6;dispaly:;");
        } else {
            $("#calendar").attr("style", "position:absolute;left:"+cleft+"px;top:"+ctop+"px;width:208px;height:auto;z-index:6;dispaly:;");
        }
    } else {
        $("#calendar").attr("style", "position:absolute;left:"+cleft+"px;top:"+ctop+"px;width:208px;height:auto;z-index:6;dispaly:;");
    }

    calendarDiv2 =  document.getElementById("calendar");
    calendarDiv2.onmouseover = function() {
        calendarDiv2IsOver = true;
    };
    calendarDiv2.onmouseout = function() {
        calendarDiv2IsOver = false;
        calTimer = setTimeout(function () {
            if(!calendarDiv2IsOver && !setTimer) {
                if(calendarDiv2 != null)
                    calendarDiv2.style.display = "none";
            }
        },100);
    };

    if(isYearSelectBox) {
        $("#yearBox").val(yearSelected);
    }
}

function f_calendar(ctl, ctl2, format, fromDate, pos) {
    if(pos == 1){
        defaultDateF = $('#'+ctl2).val();
        defaultDateT = $('#'+fromDate).val();
    }else if(pos == 2){
        defaultDateT = $('#'+ctl2).val();
        defaultDateF = $('#'+fromDate).val();
    }

    var leftpos = 0;
    var toppos = 0;

    if(bPageLoaded) {
        ctlToPlaceValue = $('#'+ctl2);
        dateFormat=format;
        formatChar = " ";
        aFormat = dateFormat.split(formatChar);

        if(aFormat.length<3) {
            formatChar = "/";
            aFormat = dateFormat.split(formatChar);

            if(aFormat.length<3) {
                formatChar = ".";
                aFormat = dateFormat.split(formatChar);

                if(aFormat.length<3) {
                    formatChar = "-";
                    aFormat = dateFormat.split(formatChar);

                    if (aFormat.length<3) {
                        formatChar="";
                    }
                }
            }
        }
        tokensChanged = '0';

        if(formatChar != "") {

            aData = $('#'+ctl2).val().split(formatChar);

            for(var i=0;i<3;i++) {
                if ((aFormat[i]=="d") || (aFormat[i]=="dd")) {
                    dateSelected = parseInt(aData[i], 10);
                    tokensChanged++;
                } else
                    if((aFormat[i]=="m") || (aFormat[i]=="mm")) {
                        monthSelected = parseInt(aData[i], 10) - 1;
                        tokensChanged++;
                    } else
                        if(aFormat[i]=="yyyy") {
                            yearSelected = parseInt(aData[i], 10);
                            tokensChanged++;
                        }else
                            if(aFormat[i]=="mmm") {

                                for(var j=0; j<12;  j++) {
                                    if (aData[i]==monthName[j]) {
                                        monthSelected=j;
                                        tokensChanged++;
                                    }
                                }
                            } else
                                if(aFormat[i]=="mmmm") {
                                    for(j=0; j<12;  j++) {
                                        if (aData[i]==monthName2[j]) {
                                            monthSelected=j;
                                            tokensChanged ++;
                                        }
                                    }
                                }
            }
        }

        if((tokensChanged!=3) || isNaN(dateSelected) || isNaN(monthSelected) || isNaN(yearSelected)) {
            dateSelected = dateNow;
            monthSelected = monthNow;
            yearSelected = yearNow;
        }
        odateSelected=dateSelected;
        omonthSelected=monthSelected;
        oyearSelected=yearSelected;

        ctop = document.body.clientTop + GetObjectTop(ctl) - document.body.scrollTop+25;
        cleft = document.body.clientLeft + GetObjectLeft(ctl) -  document.body.scrollLeft- 205;

        //달력이 하단에 있으면 잘림 방지
        if(ctop > com.fnToNum(document.body.clientHeight * 2 / 3)) ctop = com.fnToNum(ctop - 235);
		
		 //달력좌우 잘림 방지
        // 달력 DIV의 높이와 너비를 가져옴
        var calendar = document.getElementById('calendar');
        var calendarWidth = calendar.offsetWidth;
        
		 // 달력이 화면 우측을 벗어날 경우 조정합니다.
        /*var availableSpaceRight = window.innerWidth - cleft;
        if (availableSpaceRight < calendarWidth) {
            cleft = GetObjectLeft(ctl) - (calendarWidth - availableSpaceRight);
        }*/
        
        //달력이 화면 좌측을 벗어날 경우
        if (cleft < 0) {
            cleft = 20;
        } 
		
        constructCalendar (1, monthSelected, yearSelected);
        bShow = true;
        ctlNow = ctl;
        if(pos != 0) oFromDate = $('#'+fromDate);
        else oFromDate = undefined;
        oPos = pos;

    }
}

function calendar(ctl, ctl2, format, fromDate, pos, callback) {
	if(fromDate == undefined || fromDate == "") fromDate = "";
	if(pos == undefined || pos == "") pos = 0;
	if(callback != undefined || callback != "") callback_func = callback; //콜백세팅
    f_calendar(ctl, ctl2, format, fromDate, pos);
}

/* 달력팝업 위치 정보*/
function getposOffset(what, offsettype){
    var totaloffset=(offsettype=="left")? what.offsetLeft : what.offsetTop;
    var parentEl=what.offsetParent;
    while (parentEl!=null){
        totaloffset=(offsettype=="left")? totaloffset+parentEl.offsetLeft : totaloffset+parentEl.offsetTop;
        parentEl=parentEl.offsetParent;
    }
    return totaloffset;
}
function GetObjectTop(obj){
    return getposOffset(obj,'top');
}

function GetObjectLeft(obj){
    return getposOffset(obj,'left');
}

init();
//calcInit();

/**
 * 시작일과 종료일을 비교한다.
 * 죵료일이 작으면  true;
 */
function chkData(startData, endData, gubun){
    var retValue = true;
    v0=startData.split(gubun);
    v1=endData.split(gubun);

    v0=new Date(Number(v0[0]),Number(v0[1]),Number(v0[2]));
    v1=new Date(Number(v1[0]),Number(v1[1]),Number(v1[2]));

    if (v0 > v1) {
        retValue = true;
    } else {
        retValue = false;
    }
    return retValue;
}
