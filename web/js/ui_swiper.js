// document.writeln("");
// document.writeln("      <!-- Swiper -->");
// document.writeln("      <div class=\'swiper-container\'>");
// document.writeln("        <div class=\'swiper-wrapper\'>");
// document.writeln("          <div class=\'swiper-slide blue-slide\'>");
// document.writeln("            <p  swiper-animate-effect=\'fadeInUp\' swiper-animate-duration=\'0.5s\' swiper-animate-delay=\'0.3s\'>内容</p>");
// document.writeln("            <p  swiper-animate-effect=\'fadeInUp\' swiper-animate-duration=\'0.5s\' swiper-animate-delay=\'0.3s\'>内容</p>");
// document.writeln("            <p  swiper-animate-effect=\'fadeInUp\' swiper-animate-duration=\'0.5s\' swiper-animate-delay=\'0.3s\'>内容</p>");
// document.writeln("            <p  swiper-animate-effect=\'fadeInUp\' swiper-animate-duration=\'0.5s\' swiper-animate-delay=\'0.3s\'>内容</p>");
// document.writeln("            <p  swiper-animate-effect=\'fadeInUp\' swiper-animate-duration=\'0.5s\' swiper-animate-delay=\'0.3s\'>内容</p>");
// document.writeln("            <p  swiper-animate-effect=\'fadeInUp\' swiper-animate-duration=\'0.5s\' swiper-animate-delay=\'0.3s\'>内容</p>");
// document.writeln("          </div>");
// document.writeln("          <div class=\'swiper-slide red-slide\'>");
// document.writeln("            <p  swiper-animate-effect=\'bounceInLeft\' swiper-animate-duration=\'0.5s\' swiper-animate-delay=\'0.3s\'>内容</p>");
// document.writeln("          </div>");
// document.writeln("          <div class=\'swiper-slide orange-slide\'>");
// document.writeln("            <p class=\'ani\' swiper-animate-effect=\'shake\' swiper-animate-duration=\'1.5s\' swiper-animate-delay=\'0s\'>内容</p>");
// document.writeln("          </div>");
// document.writeln("        </div>");
// document.writeln("      </div>");
// document.writeln("");
// document.writeln("      <button id=\'lastPage\'>上一页</button>");
// document.writeln("      <button id=\'nextPage\'>下一页</button>");
// document.writeln("      <button id=\'destory\'>销毁</button>");
// document.writeln("      <button id=\'addPage\'>添加1个page</button>");
// document.writeln("      <button id=\'addPage2\'>添加2个page</button>");
// document.writeln("      <!-- Swiper JS -->");
// document.writeln("      <script src=\'js/swiper.min.js\'></script>");
// document.writeln("      <script src=\'js/swiper.animate.min.js\'></script>");
// document.writeln("");
// document.writeln("      <!-- Initialize Swiper -->");
// document.writeln("      <script>");
// document.writeln("        var mySwiper = new Swiper(\'.swiper-container\',{");
// document.writeln("          // on 用来注册事件");
// document.writeln("          on:{");
// document.writeln("            init: function(){ // swiper 初始化运行");
// document.writeln("              swiperAnimateCache(this); //隐藏动画元素");
// document.writeln("              this.emit(\'slideChangeTransitionEnd\');//在初始化时触发一次slideChangeTransitionEnd事件");
// document.writeln("            },");
// document.writeln("            slideChangeTransitionEnd: function(){");
// document.writeln("              swiperAnimate(this); //每个slide切换结束时运行当前slide动画");
// document.writeln("              this.slides.eq(this.activeIndex).find(\'.ani\').removeClass(\'ani\');//动画只展示一次");
// document.writeln("            },");
// document.writeln("            // 窗口大小更改");
// document.writeln("            resize: function(){");
// document.writeln("              this.params.width = window.innerWidth;");
// document.writeln("              this.update();");
// document.writeln("            },");
// document.writeln("            // 页面切换事件");
// document.writeln("            slideChange: function () {");
// document.writeln("              console.log(this.activeIndex);");
// document.writeln("            },");
// document.writeln("            pagination: {");
// document.writeln("              el: \'.swiper-pagination\',");
// document.writeln("            },");
// document.writeln("          },");
// document.writeln("");
// document.writeln("          speed:200,// 页面切换速度");
// document.writeln("          autoHeight: true, //高度随内容变化，考虑到内部列表高度，可能会用上");
// document.writeln("          watchOverflow: true,//因为仅有1个slide，swiper无效");
// document.writeln("          touchMoveStopPropagation : true, // 阻止touchmove冒泡事件");
// document.writeln("          resistanceRatio:0, // 到边缘时无法再拖动");
// document.writeln("          // simulateTouch : false,//禁止鼠标模拟");
// document.writeln("          // allowTouchMove: false,// 禁止鼠标、手机滑动，只能代码动态转变");
// document.writeln("");
// document.writeln("          // centeredSlides : true, // 左右不再紧贴容器边缘， 配合slidesPerView");
// document.writeln("          // slidesPerView : 3, // 一次性最多显示几个page");
// document.writeln("          // spaceBetween : 20, // page之间的间距");
// document.writeln("          // direction: \'vertical\',");
// document.writeln("          // loop: true,");
// document.writeln("        });");
// document.writeln("");
// document.writeln("      </script>");
// document.writeln("");


<!-- Swiper js-->
$(document).ready(function () {
  $('#lastPage').click(function () {
    // mySwiper.slidePrev();
    mySwiper.slideTo(0);
  });

  $('#nextPage').click(function () {
    // mySwiper.slideNext();
    mySwiper.slideTo(2);
  });

  $('#destory').click(function () {
    mySwiper.destroy(true, true);
  });

  $('#addPage').click(function () {
    mySwiper.appendSlide(
      '<div class="swiper-slide blue-slide">\n' +
      '        <p class="ani" swiper-animate-effect="fadeInUp" swiper-animate-duration="0.5s" swiper-animate-delay="0.3s">内容</p>\n' +
      '      </div>'
    );
  });

  $('#addPage2').click(function () {
    mySwiper.appendSlide(
      [
        '<div class="swiper-slide red-slide">\n' +
        '<p class="ani" swiper-animate-effect="bounceInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.3s">内容</p>\n' +
        '</div>',
        '<div class="swiper-slide orange-slide">\n' +
        '<p class="ani" swiper-animate-effect="shake" swiper-animate-duration="1.5s" swiper-animate-delay="0s">内容</p>\n' +
        '</div>'
      ]
    );
  });

});
