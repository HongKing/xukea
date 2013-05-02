/**
 * 系统初始化
 * 
 * @author FishBoy
 * @version 1.0
 */

;(function($) {
  "use strict";
  
// ====== 多选框增加footer button
/*
var oldss = $.fn.multiSelect;
$.fn.multiSelect = function(){
    var footer_btns  = {
            selectableFooter: $("<div/>", {'class': "multi-select-footer"})
                    .append(
                        $("<button class='btn select_all'/>")
                        .append("<i class='icon-plus'></i> add all")
                        .click(function(){
                            var selector = $(this).parentsUntil('.ms-container').parent().prev('select');
                            selector.multiSelect("select_all");
                        })
                    ),
            selectionFooter:  $("<div/>", {'class': "multi-select-footer"})
                    .append(
                        $("<button class='btn select_all'/>")
                        .append("<i class='icon-minus'></i> remove all")
                        .click(function(){
                            var selector = $(this).parentsUntil('.ms-container').parent().prev('select');
                            selector.multiSelect("deselect_all");
                        })
                    )
        };
      arguments[0] = $.extend(arguments[0], footer_btns);
      
      oldss.apply(this, arguments);
};
  */
  
// ====== 页面加载完毕后执行
$(function(){
    var $window = $(window);
    
    // side bar
    setTimeout(function () {
      $('.sidenav').affix({
        offset: {
          top: function () { return $window.width() <= 980 ? 290 : 210 }
        , bottom: 270
        }
      })
    }, 100);
    
    // add-ons
    $('.add-on :checkbox').on('click', function () {
      var $this = $(this)
        , method = $this.attr('checked') ? 'addClass' : 'removeClass'
      $(this).parents('.add-on')[method]('active')
    })
    
    $('[data-toggle=tooltip]').tooltip();
    
    $("[data-toggle=popover]")
     .popover()
     .click(function(e) {
        e.preventDefault();
        return false;
    });
    
    $('.wizard').each(function(i,o){
        $(o).wizard();
    });
      
});
})(jQuery);