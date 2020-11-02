/*
function heroSlider() {
  const sections = gsap.utils.toArray(".an-hero-slider__item");
  const discoverBtn = document.querySelectorAll("[data-discover-btn]");
  const dataSliderLinesList = document.querySelector("[data-slider-lines-list]");

  function addLines() {
    const linesHTML = []
    for(let i = 0; i < sections.length; i++) {
      linesHTML.push(`<div class="an-hero-slider__lines-item an-hero-slider__lines-item-${(i + 1)} ${ i === 0 && "an-hero-slider__lines-item--active"}" data-slide-line></div>`)
    }
    dataSliderLinesList.innerHTML = linesHTML.join("");
  }

  function debounce(fn, time) {
    let timeout;

    return function(...args) {
      callback = () => fn.apply(this, arguments);

      clearTimeout(timeout);
      timeout = setTimeout(callback, time);
    }
  }

  function getSiblings(elem) {
      var siblings = [];
      var sibling = elem.parentNode.firstChild;
      var skipMe = elem;
      for ( ; sibling; sibling = sibling.nextSibling )
        if ( sibling.nodeType == 1 && sibling != elem )
            siblings.push( sibling );
      return siblings;
  }

  function checkLeftCollision() {
    const slideSections = document.querySelectorAll("[data-slider-item-section]")
    const slideLine = document.querySelectorAll("[data-slide-line]")

    slideSections.forEach((slide, i) => {
      const slideRect = slide.getBoundingClientRect()
      if(slideLine.length) {
        if(Math.abs(slideRect.left).toFixed(0) < 300) {
        const slideLineSiblings = getSiblings(slideLine[i])

          slideLine[i].classList.add('an-hero-slider__lines-item--active')

          slideLineSiblings.forEach((siblingLine) => {
            siblingLine.classList.remove('an-hero-slider__lines-item--active')
          })
        }
      }
    })
  }

  addLines();

  discoverBtn.forEach((btn) => {
    btn.addEventListener("click", (e) => {
      e.preventDefault()
      st.scrollTrigger.scroll(
        (document.querySelector(".an-hero-slider__item").offsetWidth * (sections.length + 0.55))
      )
    })
  })

  // GSAP
  gsap.registerPlugin(ScrollTrigger);
  gsap.registerPlugin(CSSPlugin);

  const st = gsap.to(sections, {
    xPercent: -100 * (sections.length - 1),
    ease: "none",
    scrollTrigger: {
      trigger: ".an-hero-slider",
      pin: true,
      scrub: 1,
      start: 'top top',
      snap: 1 / (sections.length - 1),
      // base vertical scrolling on how wide the container is so it feels more natural.
      end: "+=" + (document.querySelector(".an-hero-slider__item").offsetWidth * sections.length),
      onUpdate: debounce(checkLeftCollision, 500),
      onLeave: () => document.getElementById('banner').style.left = '-100%',
      onEnterBack: () => document.getElementById('banner').style.left = '0'
    }
  });
}

if (document.querySelectorAll(".an-hero-slider__item").length > 1) {
  heroSlider();
  document.getElementById('banner').classList.add('more-elem');
} else {
  const discoverBtn = document.querySelectorAll("[data-discover-btn]");
  discoverBtn.forEach((btn) => {
    btn.addEventListener("click", (e) => {
      e.preventDefault();
      window.scrollTo({
        top: document.querySelector(".an-hero-slider__item").offsetWidth - 300,
        behavior: 'smooth',
      });
    });
  });
}
*/
