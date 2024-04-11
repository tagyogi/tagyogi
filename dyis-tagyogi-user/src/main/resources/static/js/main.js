$(document).ready(function () {

    //header nav layout
    $('nav').hover(function(){
        $('header').addClass('on');
    }, function() {
        $('header').removeClass('on');
    });
    
    //웹접근성 탭키 응대
    $("nav").focusin(function() {
        $('header').addClass('on');
    });
    $("nav > ul > li:last-child ul > li:last-child").focusout(function() {
        $('header').removeClass('on');
    })

    $(".menu-dept1 > li").focusin(function() {
        $(this).addClass('active');
    }).focusout(function() {
        $(this).removeClass('active');
    });
    
    //gnb 현재페이지 active
    var gnbTitle = $('h3').text();
    var gnbActive = $(".menu-dept1 > li span").filter(function() {
        return $(this).text() === gnbTitle;
    })
    gnbActive.addClass('on');
    
    //메가메뉴
    $('.site-map').click(function() {
        $("html, body").css('overflow', 'hidden');
        $(".generalMenu").addClass("on");
    });
    $(".site-map-close, .generalMenu_list a").click(function() {
        $("html, body").css('overflow', 'initial');
        $(".generalMenu").removeClass("on");
    });

    //side-bar 열기
    $('.side-bar').on('click',function(){
		$('html, body').addClass('active');
        $('.m-gnb-wrap').show().animate({
            "right": "0"
        }, 300);
        $('.overlay').css('display','block');
        
    });
    $('.side-close, .after-close').on('click',function(){
		$('html, body').removeClass('active');
		$('.m-gnb-wrap').find('.active').siblings("ul").toggle();
        $('.m-gnb-wrap').find('.active').removeClass('active');
        
        $('.m-gnb-wrap').hide().animate({
            "right": "-80%"
        }, 300);
        $('.overlay').css('display','none');
    });
    $(".menu-dept3").siblings('a').addClass('has');
    
    //.m-gnb, .footer-btn-up, .qna-wrap, lnb-wrap 
    $(document).on("click", ".init, .has", function(e) { 
		if($(this).hasClass('active')){
            $(this).removeClass('active');
            $(this).siblings("ul").toggle();
            $(this).siblings("li:not(.init)").toggle();
            $(this).closest('.lnb-dept1').removeClass('on');
        }else {
            $(this).addClass('active');
            $(this).siblings("ul").toggle();
            $(this).siblings("li:not(.init)").toggle();
            $(this).closest('.lnb-dept1').addClass('on');
        }
		
	});
	
    
    //footer
    $(".footer-btn-up ul li a").on("click", function(e) {
        e.preventDefault();
        $('.footer-btn-up button.init').text($(this).text());
        var link = $(this).attr('title');
        $('.move-link').attr('href', link);
        $(this).parents('ul').hide();
    });
    
    
    $(document).on("click", ".tabBasic a", function(e) {
        e.preventDefault();
        var $this = $(this),
            mobileParent = $this.parents('.tabBasic'),
            mobiletxt = $this.text(),
            mobiletabBtn = $this.parents("div[class *= 'tabBasicWrap']").find('.tab-txt'),
            tabgroup = '#' + $this.parents('.tabBasic').data('tabgroup'),
            tabfn =  $this.parents('.tabBasic').data('tabfunction'),
            others = $this.closest('li').siblings().children('a'),
            target = $this.attr('href');

        $(mobileParent).removeClass('active');
        $(mobiletabBtn).text(mobiletxt);

        others.removeClass('active');
        $this.addClass('active');
        $(tabgroup).children('div').hide();
        $(target).show();
        
        if(tabfn != null && tabfn != "")
        {
            tabClick(target);
        }
    });
    
    //quick
    $(document).scroll(function() {
        var scrollT = $(this).scrollTop(); //스크롤바의 상단위치
        if (scrollT > 310) {
            $("#quick-menu").addClass("on");
        } else if (scrollT < 310) {
            $("#quick-menu").removeClass("on");
        }
    });
    $(".quick-open").click(function() {
        $('#quick-menu').removeClass('active');
    });
    $(".quick-close").click(function() {
        $('#quick-menu').addClass('active');
    });

    if($(window).width() < 1650){
        $('#quick-menu').addClass('active');
    }
    
    //main-tab
    $('.main-tab-button').click(function(e) {
        $(this).siblings('.flex-box').children('a').toggleClass('hide');
        if($(window).width() < 768){
	        $('.news > .flex-box > a').click(function(e) {
	            $(this).parents('.flex-box').children('a').addClass('hide');
	        });
        }
    });
     $(window).resize(function() {
	    if($(window).width() < 768){
	        $('.news > .flex-box > a').addClass('hide');
	    }else {
	        $('.news > .flex-box > a').removeClass('hide');
		}
    });
    if($(window).width() < 768){
        $('.news > .flex-box > a').addClass('hide');
    }
    
    var nowZoom = 1;
    //줌인 버튼 클릭 시 
    $(".zoomIn").click(function() {
        if (nowZoom > 1.25) {
            alert("더 이상 확대할 수 없습니다.");
        }else {
            nowZoom += 0.05;
            document.body.style.transform = "scale("+nowZoom+")";
            $("body").css("transform-origin", "0 0");
            //zoomIn();
        }
    });

    //줌아웃 버튼 클릭 시 
    $(".zoomOut").click(function() {
        if (nowZoom < 1.05) {
            alert("더 이상 축소할 수 없습니다.");
        }else {
             nowZoom -= 0.05;
            //zoomOut();
            document.body.style.transform = "scale("+nowZoom+")";
            $("body").css("transform-origin", "0 0");
        }
       
    });
});

//탭mobile btn
function onpenInnerTab(self) {
    $(self).siblings('.tabBasic').toggleClass('active');
}

//scroll top
function goTop(){
    $('html').scrollTop(0);}

//main tap
function fnMaintab(self) {
    $(self).parents('.news').find('.main-tab').removeClass('active');
    $(self).parents('.news').find('.tab-content').removeClass('active');
    $(self).addClass('active');
    $($(self).attr('href')).addClass('active');
    if($(window).width() < 768){
        $('.news > .flex-box > a').addClass('hide');
    }

    $('.main-tab-button').text($(self).text());
}
