// wizard/** * wizard插件说明： * 1. .wizard_steps中的li元素增加url（必须）和next_handle（可选）属性。 *    a. url为当前wizard的url，若为#开头的ID，则需要在.steps_page中有对应的元素，其他的则将通过ajax动态加载 *    b. next_handle为检测函数，当点击当前页面的“下一步”按钮时，将执行该函数的检测，若返回的是true，则展现下一页 * 2. 按钮的样式为定值：上一步（.btn_prev），下一步（.btn_next），完成（.btn_complete） * 3. 提交按钮btn_complete自行绑定事件 * 示例： * <pre> *  * <div class="wizard"> *     <div class="wizard_progressbar"></div> *     <div class="wizard_steps"> *         <ul> *             <li url="#id_page" next_handle="my_check" >1. step 1</li> *             <li url="ajax_url_1"  >2. step 2</li> *             <li url="ajax_url_2"  >3. submit</li> *         </ul> *     </div>    *     <div class="wizard_content" style="height: 290px; overflow-y: scroll;"> *        <form action="form_action_url" method="post"> *          <div class="steps_page"> * 	           <div id="id_page" class="step"> step_1 </div> *          </div> *        </form> *     </div> *     <div class="button_bar"> *         <div class="in"> *             <input type="button" class="button-blue  btn_prev"  value="上一步" /> *             <input type="button" class="button-green btn_next"  value="下一步" /> *             <input type="button" class="button-red   btn_complete" value="提 &nbsp; 交" /> *         </div> *     </div> * </div> *  * </pre> **/(function($) {	$.wizard = {		// wizard页面初始化		_init: function(){			$('.wizard_steps ul li:first').addClass('first');			$('.wizard_content .steps_page .step:first').show();			$('.wizard .button_bar .btn_prev').addClass('btn_disabled');			$('.wizard .button_bar .btn_complete').hide();						$('.wizard .button_bar .btn_prev').live('click', $.wizard.click_btn_prev);			$('.wizard .button_bar .btn_next').live('click', $.wizard.click_btn_next);						// 打开第一个页面			var flag = true;			$('.wizard_steps ul li').each(function(i, o){				if(!$(o).hasClass('disable') && flag){					$.wizard._change_step_page(i, null, o);					flag = false;				}			});		},		// 设置进度条		_set_progress: function(step_num){			var step_multiplyby = (100 / $('.wizard_steps > ul > li').size());			var prog_val = (step_num*step_multiplyby);			try{				$( ".wizard_progressbar").progressbar({ value: prog_val });			}catch(e){}		},		// 切换页面		_change_step_page: function(i, this_li, next_li){			$(this_li).removeClass('current');			$(next_li).addClass('current');						var url   = $(next_li).attr('url');			var parms = $(next_li).attr('data');			if(url.indexOf("#")==0){				// URL为ID				var id = url.substr(1);				var next_step = $('.wizard_content .steps_page').children('#'+id);			}else{				// URL为正常路径				var id = "step_" + i;				var next_step = $('.wizard_content .steps_page').children('#'+id);				if(next_step.length==0){					$.ajax({						url  : url,						data : $.parseJSON(parms),						success : function( data, status, xhr ){							$('.wizard_content .steps_page').append('<div id="'+ id +'" class="step">'+ data +'</div>');							// set tips							try{								$(function(){ $('.tips').tipsy({gravity: 's',html: true}); });								$(function(){ $('.tips-right').tipsy({gravity: 'w',html: true}); });								$(function(){ $('.tips-left').tipsy({gravity: 'e',html: true}); });								$(function(){ $('.tips-bottom').tipsy({gravity: 'n',html: true}); });							}catch(e){}							$('.wizard_content .steps_page').children('#'+id).fadeIn(1000);						}					});				}			}			$.wizard._set_progress(i+1);			try{				$("#messager_box").empty();//消息框置空			}catch(e){}			$('.wizard_content .steps_page').children().hide();			$('.wizard_content .steps_page').children('#'+id).fadeIn(1000);						return false;		},		// 按钮“上一步”点击事件		click_btn_prev: function(){			if($(this).hasClass('btn_disabled')) return;			var flag = false;			var prev_idx = null;			var prev_obj = null;			$('.wizard_steps ul li').each(function(i, o){				if($(o).hasClass('disable') || flag) return;								if($(o).hasClass('current')){					flag = true;					$.wizard._change_step_page(prev_idx, o, prev_obj);					//展现下一步按钮					$('.wizard .button_bar .btn_next').show();					$('.wizard .button_bar .btn_complete').hide();					//如果显示的是第一页，则上一步按钮不可用					var objs = $(o).prevAll('li:not(".disable")');					if(objs.size()==1){						$('.wizard .button_bar .btn_prev').addClass('btn_disabled');					}				}else{					prev_idx = i;					prev_obj = o;				}			});		},		// 按钮“下一步”点击事件		click_btn_next: function(){			if($(this).hasClass('btn_disabled')) return;						var flag = false;			var this_obj = null;			$('.wizard_steps ul li').each(function(i, o){				if($(o).hasClass('disable') || flag) return;								if($(o).hasClass('current')){					this_obj = o;				}else if(this_obj!=null){					flag = true;					var next_handle = $.trim( $(this_obj).attr('next_handle') );//获取当前页面的检测函数					if(next_handle.length>0){						try{							var temp = eval("("+next_handle+"())");							if(!temp) return; //如果检测不通过，则不进入下一个页面						}catch(e){}					}										$.wizard._change_step_page(i, this_obj, o);					$('.wizard .button_bar .btn_prev').removeClass('btn_disabled');//上一步按钮可用										// 是否展现提交按钮					var objs = $(o).nextAll('li:not(".disable")');					if(objs.size()==0){						$('.wizard .button_bar .btn_next').hide();						$('.wizard .button_bar .btn_complete').show();					}				}			});		},		// 设置第“idx”步不可用		disable: function(idx, flag){			$('.wizard_steps ul li').each(function(i, o){				if(idx==i){					flag ? $(o).addClass("disable") : $(o).removeClass("disable") ;				}			});			$('.wizard_content .steps_page').children('#step_'+idx).remove();		},		// 设置第“idx”步的传递参数		setParms: function(idx, parms){			$('.wizard_steps ul li').each(function(i, o){				if(idx==i){					$(o).attr("data", $.stringify(parms));				}			});			$('.wizard_content .steps_page').children('#step_'+idx).remove();		},		// 获取第“idx”步的传递参数		getParms: function(idx){			var parms="";			$('.wizard_steps ul li').each(function(i, o){				if(idx==i){					parms = $(o).attr("data");				}			});			return $.parseJSON(parms);		},		// 更改第“idx”步的URL		setUrl: function(idx, url, parms){			$('.wizard_steps ul li').each(function(i, o){				if(idx==i){					$(o).attr("url", url);				}			});			if(parms!=null){				$.wizard.setParms(idx, parms);			}			$('.wizard_content .steps_page').children('#step_'+idx).remove();		},		// 清空内容		clear: function(idx){			$('.wizard_content .steps_page').children('#step_'+idx).remove();		},		// 清空所有内容		clearAll: function(){			$('.wizard_steps ul li').each(function(i, o){				$.wizard.clear(i);			});		}	};		$(document).ready(function(){		$.wizard._init();	});})(jQuery);