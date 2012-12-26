
(function($) {
/* Tipsy */
	$(function(){ $('.tips').tipsy({gravity: 's',html: true}); });
	$(function(){ $('.tips-right').tipsy({gravity: 'w',html: true}); });
	$(function(){ $('.tips-left').tipsy({gravity: 'e',html: true}); });
	$(function(){ $('.tips-bottom').tipsy({gravity: 'n',html: true}); });


/* Ie7 z-index fix 
	$(function() {
	    var zIndexNumber = 1000;
	    $('div').each(function() {
	        $(this).css('zIndex', zIndexNumber);
	        zIndexNumber -= 10;
	    });
	});
*/


/* Uniform */
	$(function(){
		$(".uniform").uniform({
			fileDefaultText: '无',
			fileBtnText: '文件选择'
		});
	});

/*  Drag and Drop Select */
	$(function(){
		$(".multiselect").multiselect();
	});
	
/* Tips */
	$(document).ready(function(){
		$(".simple-tips .close").click(function(){
			$(".simple-tips").slideToggle();
			return false;
		});
		$(".albox .close").live('click', function(event){
			$(this).parents(".albox").slideToggle();
			return false;
		});
	});


})(jQuery);
