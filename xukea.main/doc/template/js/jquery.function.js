/**
 * 自定义全局通用函数
 * 
 * @author FishBoy
 * @version 1.0
 */
;(function($) {
    window.log = function(msg){
        window.console && console.log(msg)
    }
	/*********************************** 页面loading遮罩 *************************************/
	$.loading = {
		start : function(){
			if($("#loading_overlay").attr("id")!=undefined){
				$("#loading_overlay").show();
				return;
			}
			var base_url = "";
			$("script").each(function () { 
				if(this.src.toString().match(/\/js\/jquery\.function[^\/]*?\.js(\?.*)?$/)) { 
					base_url = this.src.toString().replace(/\/js\/jquery\.function[^\/]*?\.js(\?.*)?$/, ""); 
					return false; 
				}
			});
			var loading_html = '<div id="loading_overlay">'
				             + '  <div class="loading_message">'
				             + '    <img src="'+ base_url +'/img/loading.gif" alt="loading" />'
				             + '  </div>'
				             + '</div>';
			$(loading_html).appendTo("body");
		},
		stop  : function(){
			$("#loading_overlay").hide();
		}
	}

	/*********************************** 消息提示 *************************************/
	$.messager = {
		succes : function(msg){
			$.messager.message("成功", msg, 'succesbox');
		},
		info : function(msg){
			$.messager.message("通知", msg, 'informationbox');
		},
		warn : function(msg){
			$.messager.message("警告", msg, 'warningbox');
		},
		error : function(msg){
			$.messager.message("错误", msg, 'errorbox');
		},
		message : function(title, msg, mtype){
			msg = msg==undefined ? "未知错误" : msg;
            msg = msg.replace(/\r\n/g, '<br/>');
            msg = msg.replace(/\r/g, '<br/>');
            msg = msg.replace(/\n/g, '<br/>');
            
			var content = '<div class="albox '+ mtype +'" ><b>'+ title +'：</b>'+ msg +'<a href="#" class="close tips" title="关闭">close</a></div>';

            $("#messager_box").empty();
			$('#messager_box').append( content );
		}
	}
	
	/*********************************** 弹出提示,弹出窗口 *************************************/
	$.xukea = {
		alert : function(msg, calbak){
			$.xukea.warning(msg, calbak);
		},
		warning  : function(msg, calbak){
			calbak = $.isFunction( calbak ) ? calbak : function(){};
			var btns = {
				"确定": function(){
					$(this).dialog("close");
					calbak.call(this, true);
				}
			};
			$.xukea._message(msg, 'warningbox', btns);
		},
		error : function(msg, calbak){
			calbak = $.isFunction( calbak ) ? calbak : function(){};
			var btns = {
				"确定": function(){
					$(this).dialog("close");
					calbak.call(this, true);
				}
			};
			$.xukea._message(msg, 'errorbox', btns);
		},
		confirm : function(msg, sucs_cal, cacl_cal){
			sucs_cal = $.isFunction( sucs_cal ) ? sucs_cal : function(){};
			cacl_cal = $.isFunction( cacl_cal ) ? cacl_cal : sucs_cal;
			var btns = {
				"确定": function(){
					$(this).dialog("close");
					sucs_cal.call(this, true);
				},"取消": function(){
					$(this).dialog("close");
					sucs_cal.call(this, false);
				}
			};
			$.xukea._message(msg, 'informationbox', btns);
		},
		_message : function(msg, mtype, btns){
            msg = msg.replace(/\r\n/g, '<br/>');
            msg = msg.replace(/\r/g, '<br/>');
            msg = msg.replace(/\n/g, '<br/>');

			var content = '<div title="系统提示">';
			content += '<div class="albox '+ mtype +'">';
			content += msg;
			content += '</div></div>';

            $( content ).dialog({
    			height: 200,
    			width: 400,
    			modal: true,
    			buttons: btns,
    			close: function(){
    				$(content).empty();
    			}
    		});
		}
	}
	
	/*********************************** AJAX SEND *************************************/
	$.sendGet = function(url, data, callback, errCallback, type){
		return $.send(url, data, callback, errCallback, type, 'GET');
	}

	$.sendPost = function(url, data, callback, errCallback, type){
		return $.send(url, data, callback, errCallback, type, 'POST');
	}

	$.sendPut = function(url, data, callback, errCallback, type){
		data._method='PUT';
		return $.send(url, data, callback, errCallback, type, 'POST');
	}

	$.sendDelete = function(url, data, callback, errCallback, type){
		data._method='DELETE';
		return $.send(url, data, callback, errCallback, type, 'POST');
	}
	
	$.send = function(url, data, callback, errCallback, dtype, stype){
		$.loading.start();
		
		if ( $.isFunction( data ) ) {
			if( !$.isFunction( callback ) ){
				dtype = callback;
				errCallback = data;//没有定义错误处理方法时，采用成功的处理方式
			}else{
				dtype = errCallback;
				errCallback = callback;
			}
			callback = data;
			data = {};
		}else if( !$.isFunction( errCallback ) ) {
			dtype = errCallback;
			errCallback = callback;//没有定义错误处理方法时，采用成功的处理方式
		}
		
		if(stype==null){
			stype = "GET";
		}

		//访问成功后回调
		var cmpCallback = function( res, status ) {
			$.loading.stop();
		}
		
		return jQuery.ajax({
			type: stype,
			url : url,
			data: data,
			success : function( data, status, xhr ){
			    var type = "success";
			    try{
			        type = data.xukea_type;
			    }catch(e){}
			    if(type=="error"){
			        errCallback.call(this, data, status, xhr);
			    }else{
    			    callback.call(this, data, status, xhr);
    			}
			},
			error   : errCallback,
			complete: cmpCallback,
			dataType: dtype
		});
	}
	
	/**
	 * 将对象转为string
	 * implement JSON.stringify serialization
	 */ 
	$.stringify = $.stringify || function (obj) {
	    var t = typeof (obj);
	    if (t != "object" || obj === null) {
	        // simple data type
	        if (t == "string") obj = '"'+obj+'"';
	        return String(obj);
	    }
	    else {
	        // recurse array or object
	        var n, v, json = [], arr = (obj && obj.constructor == Array);
	        for (n in obj) {
	            v = obj[n]; t = typeof(v);
	            if (t == "string") v = '"'+v+'"';
	            else if (t == "object" && v !== null) v = $.stringify(v);
	            json.push((arr ? "" : '"' + n + '":') + String(v));
	        }
	        return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
	    }
	};
	
	/**
	 * 字符替换，主要用于语言包中文字的替换<br>
	 * 用法：
	 * var msg = "hello {0} {1}";
	 * msg = $.fillArgs(msg, "张三", "先生"); //hello 张三 先生
	 * 
	 */
	$.fillArgs = $.fillArgs || function(){
		var formated = arguments[0];
	    for( var i=1; i<arguments.length; i++ ){
	        var param = "\{"+ (i-1) +"\}";  
	        formated = formated.replace(param, arguments[i]);
	    }
	    return formated;
	};
})(jQuery);

;(function($){
	ajaxUnLogin = function(data){
		try{
			var data = $.isPlainObject(data) ? data : $.parseJSON(data);
	        var code = data.xukea_status;
	        if(code == 403.1){
	        	top.logout(); //未登录，则跳转至登录界面
	        	return;
		    }
	    }catch(e){ }
	    
	    $.loading.stop();
	};
	
	var old_ajax = $.ajax;
	$.ajax = function(s){
		var old_complete = s.complete;
		s.complete = function(nativeStatusText, status, responses, headers) {
			ajaxUnLogin(nativeStatusText.responseText);
		    if($.isFunction(old_complete)){
		    	old_complete(nativeStatusText, status, responses, headers);
		    }
		}
		var old_error = s.error;
		s.error = function(nativeStatusText, status, responses, headers){
			ajaxUnLogin(nativeStatusText.responseText);
			if($.isFunction(old_error)){
				old_error(nativeStatusText, status, responses, headers);
		    }
		}
		
		var old_success = s.success;
		s.success = function(responses, status, nativeStatusText, headers){
			ajaxUnLogin(responses);
			if($.isFunction(old_success)){
				old_success(responses, status, nativeStatusText, headers);
		    }
		}
		
		old_ajax(s);
	}
})(jQuery);
