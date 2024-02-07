/**
 * 
 */
// ================ 아이콘 ================
feather.replace()


// ================ 스크롤 이벤트 ================
let lastScroll = document.documentElement.scrollTop || 0
document.addEventListener('scroll', function (e) {
  const scrollTop = document.documentElement.scrollTop
  const headerScroll = document.querySelector('header');
  const logoImgBlack = document.querySelector('.logo-img-black')
  const logoImgWhite = document.querySelector('.logo-img-white')
  const navMenu = document.querySelectorAll('.links li a')
  const navIcon = document.querySelectorAll('.sub-links li a')
  const toggleIcon = document.querySelector('.toggle-btn')

  if (scrollTop > lastScroll) {
    // down
    logoImgWhite.classList.remove('cs-dnone')
    logoImgWhite.classList.add('cs-dblock')
    logoImgBlack.classList.remove('cs-dblock')
    headerScroll.classList.remove('nav-fixed')
    toggleIcon.classList.add('cs-text-white')
    toggleIcon.classList.remove('cs-text-black')
    for (let i = 0; i < navMenu.length; i++) {
      navMenu[i].classList.remove('cs-text-black')
      navMenu[i].classList.add('cs-text-white')
    }
    for (let i = 0; i < navIcon.length; i++) {
      navIcon[i].classList.remove('cs-text-black')
      navIcon[i].classList.add('cs-text-white')
    }
  } else if (scrollTop === 0) {
    headerScroll.classList.remove('nav-fixed')
    logoImgWhite.classList.remove('cs-dnone')
    logoImgWhite.classList.add('cs-dblock')
    logoImgBlack.classList.remove('cs-dblock')
    toggleIcon.classList.remove('cs-text-black')
    toggleIcon.classList.add('cs-text-white')
    for (let i = 0; i < navMenu.length; i++) {
      navMenu[i].classList.remove('cs-text-black')
      navMenu[i].classList.add('cs-text-white')
    }
    for (let i = 0; i < navIcon.length; i++) {
      navIcon[i].classList.remove('cs-text-black')
      navIcon[i].classList.add('cs-text-white')
    }
  } else {
    //up
    headerScroll.classList.add('nav-fixed')
    logoImgWhite.classList.add('cs-dnone')
    logoImgBlack.classList.add('cs-dblock')
    toggleIcon.classList.add('cs-text-black')
    toggleIcon.classList.remove('cs-text-white')
    for (let i = 0; i < navMenu.length; i++) {
      navMenu[i].classList.add('cs-text-black')
      navMenu[i].classList.remove('cs-text-white')
    }
    for (let i = 0; i < navIcon.length; i++) {
      navIcon[i].classList.add('cs-text-black')
      navIcon[i].classList.remove('cs-text-white')
    }
  }
  lastScroll = scrollTop
})
// ================ click 이벤트 ================
let section01 = $('#sc1').offset()
let section02 = $('#sc2').offset()
let section03 = $('#sc3').offset()
let section04 = $('#sc4').offset()
let searchSection = $('#search-section').offset()
let bottom_Menu = $('.bottom-menu ul li span')
let bottom_Mobile_Menu = $('.bottom-mobile-menu ul li span')
let searchIcon = $('.sub-links .feather-search')
let upBtn = $('.up-btn')

// pc click 시 해당위치
bottom_Menu.eq(0).click(function () {
  $('body,html').animate({scrollTop: section01.top}, 500)
})
bottom_Menu.eq(1).click(function () {
  $('body,html').animate({scrollTop: section02.top}, 500)
})
bottom_Menu.eq(2).click(function () {
  $('body,html').animate({scrollTop: section03.top}, 500)
})
bottom_Menu.eq(3).click(function () {
  $('body,html').animate({scrollTop: section04.top}, 500)
})
searchIcon.click(function () {
  $('body,html').animate({scrollTop: searchSection.top}, 500)
})

// mobile click 시 해당위치
bottom_Mobile_Menu.eq(0).click(function () {
  $('body,html').animate({scrollTop: section01.top}, 500)
})
bottom_Mobile_Menu.eq(1).click(function () {
  $('body,html').animate({scrollTop: section02.top}, 500)
})
bottom_Mobile_Menu.eq(2).click(function () {
  $('body,html').animate({scrollTop: section03.top}, 500)
})
bottom_Mobile_Menu.eq(3).click(function () {
  $('body,html').animate({scrollTop: section04.top}, 500)
})
upBtn.click(function () {
  $('body,html').animate({scrollTop: 0}, 100)
})

// scroll 진행 시 upBtn, bgColor event
$(window).scroll(function () {
  let scTop = $(window).scrollTop();
  let sectionTop = $('#sc1').offset().top;
  let SectionTextTitle = $('.section-main-text h1')
  let SectionTextSub = $('.text-sub p')
  let SectionBtn = $('.text-sub a')
  let sectionBubble = $('.section-bubble-inner')
  let searchSection = $('#search')
  // upBtn 나오고 없어지고
  if (scTop > sectionTop - 100) {
    upBtn.fadeIn();
  } else {
    upBtn.fadeOut();
  }

  // bgColor 섹션마다 바꾸기, textColor, bgAniColor change
  if (scTop < $('#sc2').offset().top - 100) {
    $('body').removeClass('bgColor')
    SectionTextTitle.removeClass('titleTextColor')
    SectionTextSub.removeClass('subTextColor')
    SectionBtn.removeClass('Btn')
    sectionBubble.removeClass('whiteBubble')
    searchSection.addClass('bg-blue')
    searchSection.removeClass('bg-whiteTone')
  } else if (scTop >= $('#search-section').offset().top - 300) {
    $('body').removeClass('bgColor')
    SectionTextTitle.removeClass('titleTextColor')
    SectionTextSub.removeClass('subTextColor')
    SectionBtn.removeClass('Btn')
    sectionBubble.removeClass('whiteBubble')
    searchSection.addClass('bg-blue')
    searchSection.removeClass('bg-whiteTone')
  } else if (scTop >= $('#sc4').offset().top - 100) {
    $('body').addClass('bgColor')
    SectionTextTitle.addClass('titleTextColor')
    SectionTextSub.addClass('subTextColor')
    SectionBtn.addClass('Btn')
    sectionBubble.addClass('whiteBubble')
    searchSection.removeClass('bg-blue')
    searchSection.addClass('bg-whiteTone')
  } else if (scTop >= $('#sc3').offset().top - 100) {
    $('body').removeClass('bgColor')
    SectionTextTitle.removeClass('titleTextColor')
    SectionTextSub.removeClass('subTextColor')
    SectionBtn.removeClass('Btn')
    sectionBubble.removeClass('whiteBubble')
    searchSection.addClass('bg-blue')
    searchSection.removeClass('bg-whiteTone')
  } else if (scTop >= $('#sc2').offset().top - 100) {
    $('body').addClass('bgColor')
    SectionTextTitle.addClass('titleTextColor')
    SectionTextSub.addClass('subTextColor')
    SectionBtn.addClass('Btn')
    sectionBubble.addClass('whiteBubble')
    searchSection.removeClass('bg-blue')
    searchSection.addClass('bg-whiteTone')
  }
})

// ================ 반응형 네비 이벤트 ================
const toggleBtn = document.querySelector('.toggle-btn')
const toggleIcon = document.querySelector('.toggle-btn i')
const dropdown = document.querySelector('.nav-dropdown-menu')
const mainText = document.querySelector('.main-text')
const bodyOvh = document.querySelector('body')
const bottomMenu = document.querySelector('.bottom-menu')
const toggleIconColor = document.querySelector('.toggle-btn')
const bottomMobileMenu = document.querySelector('.bottom-mobile-menu ul')

toggleBtn.onclick = function () {
  dropdown.classList.toggle('open')
  const isOpen = dropdown.classList.contains('open')
  mainText.classList.toggle('cs-dnone')
  bottomMenu.classList.toggle('cs-dnone')
  bottomMobileMenu.classList.toggle('cs-dnone')
  toggleIconColor.classList.toggle('cs-text-white')

  bodyOvh.classList.toggle('ovh')
  toggleIcon.classList = isOpen
    ? 'fa-solid fa-xmark' : 'fa-solid fa-bars'
}


