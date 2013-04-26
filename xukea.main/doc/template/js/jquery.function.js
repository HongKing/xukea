/**
 * 自定义全局通用函数
 * 
 * @author FishBoy
 * @version 1.0
 */

;(function($) {
	/***********************************     日志记录    *************************************/
    $.log = function(msg){
        window.console && console.log(msg)
    }
    window.log = $.log;
    
	/*********************************** 页面loading遮罩 *************************************/
	$.loading = {
		start : function(){
		    return;
		    /*
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
			*/
		},
		stop  : function(){
		    return;
		    /*
			$("#loading_overlay").hide();
			*/
		}
	}

	/*********************************** 消息提示 *************************************/
	var messager = {
		succes : function(obj, msg){
			messager._message(obj, "成功", msg, 'alert-success');
		},
		info : function(obj, msg){
			messager._message(obj, "通知", msg, 'alert-info');
		},
		warning : function(obj, msg){
			messager._message(obj, "警告", msg, '');
		},
		error : function(obj, msg){
			messager._message(obj, "错误", msg, 'alert-error');
		},
		_message : function(msg_box, title, msg, mtype){
    		// 消息内容处理
    		if(typeof msg === 'object'){
    		    msg = $.fillArgs.apply(this, msg);
    		}
    	    try{
    			msg = msg==undefined ? "未知错误" : msg;
                msg = msg.replace(/\r\n/g, '<br/>');
                msg = msg.replace(/\r/g, '<br/>');
                msg = msg.replace(/\n/g, '<br/>');
            }catch(e){}
            // 消息提示内容
            var content = $('<div class="alert " >').addClass(mtype)
                           .append('<strong>'+ title +'：</strong>') 
                           .append('<span>'+ msg +'</span>')
                           .append('<button type="button" class="close" data-dismiss="alert">&times;</button>');
            // show msg
            msg_box.empty();
    		msg_box.append( content );
    	}
	}
	// ================ 全局函数，于默认位置展现消息信息
	$.messager = {
		succes : function(){
		    var msg_box = $.messager._get_msg_box();
			messager['succes'].call(this, msg_box, arguments);
		},
		info : function(){
		    var msg_box = $.messager._get_msg_box();
			messager['info'].call(this, msg_box, arguments);
		},
		warning : function(){
		    var msg_box = $.messager._get_msg_box();
			messager['warning'].call(this, msg_box, arguments);
		},
		error : function(){
		    var msg_box = $.messager._get_msg_box();
			messager['error'].call(this, msg_box, arguments);
		},
		_get_msg_box : function(){
		    // 消息提示框对象
    		var msg_box = $("#messager_box");
			if(msg_box==null || msg_box==undefined || msg_box.length==0){
			    // 消息提示框对象不存在，则创建
			    $("div.container > div.row > div:not('.sidebar'):first").prepend('<div id="messager_box"></div>');
			    msg_box = $("#messager_box");
			}
			return msg_box;
		}
	}
	// ================ 对象函数，于指定位置展现消息信息
	$.fn.messager = function(){
	    var method = arguments[0];
        if ( messager[method] ) {
            //arguments[0] = this;
            //return messager[method].apply( this, arguments);
            return messager[method].call( this, this, Array.prototype.slice.call( arguments, 1 ));
        } else {
            $.error( 'Method ' +  method + ' does not exist on $(selector).messager of jQuery.function.js' );
        }
	}
	
	/*********************************** 弹出提示,弹出窗口 *************************************/
	$.alert = {
		succes : function(msg, calbak){
			calbak = $.isFunction( calbak ) ? calbak : function(){};
			var btns = [{
			    text : "确定",
			    class: "btn-primary",
				func : function(){
        					calbak.call(this, true);
        				}
    			}];
			$.alert._message(msg, 'alert-success', btns);
		},
		warning  : function(msg, calbak){
			calbak = $.isFunction( calbak ) ? calbak : function(){};
			var btns = [{
			    text : "确定",
			    class: "btn-primary",
				func : function(){
    					    calbak.call(this, true);
        				}
    			}];
			$.alert._message(msg, '', btns);
		},
		error : function(msg, calbak){
			calbak = $.isFunction( calbak ) ? calbak : function(){};
			var btns = [{
			    text : "确定",
			    class: "btn-primary",
				func : function(){
    					    calbak.call(this, true);
        				}
    			}];
			$.alert._message(msg, 'alert-error', btns);
		},
		confirm : function(msg, sucs_cal, cacl_cal){
			sucs_cal = $.isFunction( sucs_cal ) ? sucs_cal : function(){};
			cacl_cal = $.isFunction( cacl_cal ) ? cacl_cal : sucs_cal;
			var btns = [{
			    text : "确定",
			    class: "btn-primary",
				func : function(){
        					sucs_cal.call(this, true);
        				}
				},{
			    text : "取消",
			    class: "",
				func : function(){
        					cacl_cal.call(this, false);
        				}
			    }];
			$.alert._message(msg, 'alert-info', btns);
		},
		_message : function(msg, mtype, btns){
		    try{
    			msg = msg==undefined ? "未知错误" : msg;
                msg = msg.replace(/\r\n/g, '<br/>');
                msg = msg.replace(/\r/g, '<br/>');
                msg = msg.replace(/\n/g, '<br/>');
            }catch(e){}
            // msg window
            var msgWin = $('<div tabindex="-1" role="dialog" aria-hidden="true">').addClass("modal hide fade");
			// header
			$('<div class="modal-header">')
             .append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>')
             .append('<h3>系统提示</h3>')
             .appendTo(msgWin);
            // body
            var msgCotnt = $('<div class="modal-body" style="padding: 20px 20px 0;">');
            $('<div class="alert " >')
             .addClass(mtype)
             .append('<span>'+ msg +'</span>')
             .appendTo(msgCotnt);
            msgWin.append(msgCotnt);
            // btns
			var msgBtns = $('<div class="modal-footer">');
			$.each( btns, function( idx, props ) {
			     $('<button class="btn">').addClass(props.class).html(props.text).bind('click', function(){
			        props.func.call();
			        msgWin.modal('hide'); // hide this modal
			    }).appendTo(msgBtns);
			});
            msgWin.append(msgBtns);
			// show modal
			msgWin.modal('show').on('hidden', function () {
			    msgWin.remove(); // remove this modal
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
	
	/*********************************** 常用工具 *************************************/
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
		/*
		if(typeof args ==="object"){
		    return arguments.callee(args);
		    //return $.fillArgs(formated);
		}else if(typeof args ==="string"){
		*/
    	    for( var i=1; i<arguments.length; i++ ){
    	        var param = "\\{"+ (i-1) +"\\}";
    	        var val = !arguments[i] ? "" : arguments[i];
    	        formated = formated.replace(new RegExp(param, "gm"), val);
    	    }
	        return formated;
	    /*
		}else{
		    return "";
		}
		*/
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
