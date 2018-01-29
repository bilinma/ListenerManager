/**
 * @author 魏铁胜
 * @description 用jQuery实现的折叠面板插件
 */
$.fn.panel = function(opt){
	var cfg={
			title:'panel',
			html:'',
			collapsible:true,
			direct:'south',
			callback:function(){},
			panelWidth:25,
			drag:false,
			width:400,
			height:350
	};
	cfg=$.extend(cfg,opt);
	var mask,panel,body,head,close,title;
	var _self=$(this);
	panel=$('<div class="panel"/>');
	head=$('<div class="panel-head" />').append(close=$('<div class="panel-top-right" title="展开或折叠"></div>')).append(title=$('<div class="panel-top-left">'+cfg.title+'</div>'));
	body=$('<div class="panel-body" />').append(cfg.html).height(cfg.height);
	//south 折叠的内容在下方
	if(cfg.direct=='south'){
		panel.append(head).append(body).width(cfg.width).appendTo(_self);
		//初始隐藏内容
		//body.hide();
	}
	//north 折叠的内容在上方
	if(cfg.direct=='north'){
		panel.append(body).append(head).width(cfg.width).appendTo(_self);
		//初始隐藏内容
		//body.hide();		
	}
	if(cfg.collapsible){
		close.toggle(function(){
			close.removeClass().addClass('panel-top-right-left');
			body.fadeOut("slow");
		},function(){
			close.removeClass().addClass('panel-top-right');
			body.fadeIn("slow",function(){
				cfg.callback(_self);
			});
		});
	}
	else{
		close.remove();
	}
	return _self;
}
