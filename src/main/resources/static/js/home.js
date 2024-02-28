$(document).ready(function() {
    let homenavlist = $(".home_dl_nav_ul li");
    let dlment = $(".dl_ment span");
    let homedlimgAIcokcok = $(".home_dl_img_AIcokcok");
    let home_dl_img_hotPlace = $(".home_dl_img_hotPlace");
    let home_dl_img_AIPlaner = $(".home_dl_img_AIPlaner");
    let dl_refresh_button = $(".dl_refresh_button span");
    let clickEl = 0; // 초기 값은 0으로 설정

    // 클릭 이벤트 핸들러 등록
    homenavlist.click(function() {
        // 클릭된 요소에만 active 클래스 추가하고 나머지 요소에서는 제거
        homenavlist.removeClass("active");
        $(this).addClass("active");
    });
	
	
    function hideAllElements() {
        dlment.hide();
        homedlimgAIcokcok.hide();
        home_dl_img_hotPlace.hide();
        home_dl_img_AIPlaner.hide();
        dl_refresh_button.hide();
    }

    function showElementAtIndex(index) {
        hideAllElements();
        dlment.eq(index).show();
        switch (index) {
            case 0:
                homedlimgAIcokcok.show();
                break;
            case 1:
                home_dl_img_hotPlace.show();
                break;
            case 2:
                home_dl_img_AIPlaner.show();
                break;
        }
        dl_refresh_button.eq(index).show();
    }

    homenavlist.eq(0).click(function() {
        clickEl = 0;
        showElementAtIndex(0);
    });

    homenavlist.eq(1).click(function() {
        clickEl = 1;
        showElementAtIndex(1);
    });

    homenavlist.eq(2).click(function() {
        clickEl = 2;
        showElementAtIndex(2);
    });

    // 피드 추천 버튼 클릭 시 동작
    dl_refresh_button.eq(0).on('click', function() {
        location.reload(); // 페이지 새로고침
        hideAllElements(); // 모든 요소 숨기기
        clickEl = 0;
        showElementAtIndex(0); // 피드 추천 요소 표시
        $('html, body').animate({
            scrollTop: $('#myCarousel').offset().top // myCarousel 위치로 스크롤 이동
        }, 1000); // 스크롤 속도 (밀리초 단위)
    });

    // 코스 추천 버튼 클릭 시 동작
    dl_refresh_button.eq(1).on('click', function() {
        location.reload(); // 페이지 새로고침
        hideAllElements(); // 모든 요소 숨기기
        clickEl = 1;
        alert(clickEl);
        showElementAtIndex(1); // 코스 추천 요소 표시
        $('html, body').animate({
            scrollTop: $('#myCarousel').offset().top // myCarousel 위치로 스크롤 이동
        }, 1000); // 스크롤 속도 (밀리초 단위)
    });

    // 오늘의 핫플 추천 버튼 클릭 시 동작
    // 오늘의 핫플 추천 버튼 클릭 시 동작
	dl_refresh_button.eq(2).on('click', function() {
	    location.reload(); // 페이지 새로고침
	    hideAllElements(); // 모든 요소 숨기기
	    clickEl = 2;
	    alert(clickEl);
	    showElementAtIndex(2); // 핫플 추천 요소 표시
	    $('html, body').animate({
	        scrollTop: $('.hp_imgBox').offset().top // 스크롤을 hp_imgBox 위치로 이동
	    }, 1000); // 스크롤 속도 (밀리초 단위)
	});


    // 초기에 클릭한 버튼에 따라 요소를 표시
    showElementAtIndex(clickEl);
});
