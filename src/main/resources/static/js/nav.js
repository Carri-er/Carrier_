feather.replace()

const toggleBtn = document.querySelector('.toggle-btn')
const toggleIcon = document.querySelector('.toggle-btn i')
const dropdown = document.querySelector('.nav-dropdown-menu')

toggleBtn.onclick = function () {
  dropdown.classList.toggle('open')
  const isOpen = dropdown.classList.contains('open')

  toggleIcon.classList = isOpen
    ? 'fa-solid fa-xmark' : 'fa-solid fa-bars'
}

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
