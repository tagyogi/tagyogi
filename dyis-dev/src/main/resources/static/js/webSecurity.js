/**
 * 우클릭 사용 방지
 */
$(document).on('contextmenu', function () {
    return false;
})

/**
 * 콘솔 사용 방지
 */
Object.getOwnPropertyNames(console).filter(function(property) {
    return typeof console[property] == 'function';
}).forEach(function (verb) {
    console[verb] = function() { return '콘솔은 사용하실 수 없습니다.'; };
});

/**
 * 단축키 사용 방지
 * F12, 개발자 도구 (Ctrl + Shift + J), 개발자 도구 검사(Ctrl + Shift + I or C), 사이트 저장 (Ctrl + S), 소스 보기 (Ctrl + U)
 */
window.onload = function() { function e(e) {
    return e.stopPropagation ? e.stopPropagation() : window.event && (window.event.cancelBubble=!0), e.preventDefault(), !1
} document.addEventListener("contextmenu",function(e) {
    e.preventDefault()},!1),document.addEventListener("keydown",function(t) {
    t.ctrlKey && t.shiftKey && 73 == t.keyCode && e(t),t.ctrlKey && t.shiftKey && 74 == t.keyCode && e(t), t.ctrlKey && t.shiftKey && 67 == t.keyCode &&
    e(t), 83 == t.keyCode && (navigator.platform.match("Mac") ? t.metaKey : t.ctrlKey) && e(t), t.ctrlKey && 85 == t.keyCode && e(t), 123 == event.keyCode && e(t)
} ,!1)};

/**
 * 개발자 도구 사용 방지
 * 경고 메시지 출력 후 이전 페이지 혹은 메인 페이지로 이동
 * debugger 상태로 만들어 시작 시간과 종료 시간을 비교하여 DevTools가 parse 상태 확인
 * IE 브라우저의 경우 적용이 제대로 안될 수 있음
 * 다른 스크립트 블럭과 충돌을 방지하기 위해 IIFE 함수 선언
 */
!function() {
    function detectDevTool(allow) {
        if (isNaN(+allow)) allow = 100;
        var start = +new Date();
        debugger; // parse script execution 상태
        var end = +new Date();
        if (isNaN(start) || isNaN(end) || end - start > allow) {
            if (document.referrer) { // 이전 페이지의 히스토리가 있을 경우 뒤로가기
                alert('개발자 도구는 사용하실 수 없습니다. 이전 페이지로 돌아갑니다.');
                history.back();
            } else { // 없을 경우 본회 링크로 이동
                alert('개발자 도구는 사용하실 수 없습니다. 메인 페이지로 돌아갑니다.');
                location.href = "https://www.kbiz.or.kr/index/index.do";
            }
            //document.write('내용 입력 후 활성화');
        }
    }
    if (window.attachEvent) {
        if (document.readyState === "complete" || document.readyState === "interactive") {
            detectDevTool();
            window.attachEvent('onresize', detectDevTool);
            window.attachEvent('onmousemove', detectDevTool);
            window.attachEvent('onfocus', detectDevTool);
            window.attachEvent('onblur', detectDevTool);
        } else {
            setTimeout(argument.callee, 0);
        }
    } else {
        window.addEventListener('load', detectDevTool);
        window.addEventListener('resize', detectDevTool);
        window.addEventListener('mousemove', detectDevTool);
        window.addEventListener('focus', detectDevTool);
        window.addEventListener('blur', detectDevTool);
    }
}();