/**
 * @jQuery插件：Jevent
 * @author:zhanzhihu
 * @date:  2008-14-40
 * jQuery插件：自定义jQuery事件机制
 * 事件的机制：分为捕获阶段、目标阶段、冒泡阶段
 * 捕获阶段：事件定义时设置，默认为关闭，由根结点出发，至目标对象，处理事件
 * 冒泡阶段：事件定义时设置，默认为打开，由目标触发，至根结点为止，处理事件
 */
if (typeof jQueryJEvent == "undefined") { var jQueryJEvent = new Object();}

/**
 * 所有事件类的父类
 * @param {string} name    
 * @param {boolean} capture 
 * @param {boolean} bubble  
 * @param {Object} data   
 * @param {jQuery Object} target  
 */
jQueryJEvent.Event = 
function(name,capture,bubble,data,target){
	this.name    = name;
	this.capture = capture;
	this.bubble  = bubble;
	this.data    = data;
	this.target  = target;
};


jQueryJEvent.poolUnit = function(name,handleArray){
	this.name = name;
	if(handleArray instanceof Array){
		this.handleArray = handleArray;
	}else{
		this.handleArray = [handleArray];
	}
	
};
jQueryJEvent.handleUnit = function(jQueryObj,capture,bubble,func){
	this.jQueryObj = jQueryObj;
	this.capture   = capture;
	this.bubble    = bubble;
	this.func      = func;
};
jQueryJEvent.jEventController = function(){
	if(jQueryJEvent.jEventController.caller != jQueryJEvent.getJEventControllerInstance){
		throw new Error("非法调用jEventController的构造函数");return;
	}
	this.eventPool = [];
};
jQueryJEvent.jEventController.prototype = {
	handleJEvent: function(jEvent){	
		var parentArray1 = null;
		for(var i=0;i<this.eventPool.length;i+=1){
			if(this.eventPool[i].name == jEvent.name){
				if (jEvent.capture) {
						var handleArray1 = this.getJEventListener(jEvent,jEvent.name);
						for(var j=0;j<handleArray1.length;j+=1){
							handleArray1[j].func.call(handleArray1[j].jQueryObj,jEvent.data,"capture");
						}
				}
			}
		}			
	},
	addjEventListener:function(jQueryObj,name,capture,bubble,func){
		for(var i=0;i<this.eventPool.length;i+=1){
			if(this.eventPool[i].name == name){
				this.eventPool[i].handleArray.push(new jQueryJEvent.handleUnit(jQueryObj,capture,bubble,func));
				return;							
			}
		}
		this.eventPool.push(new jQueryJEvent.poolUnit(name,new jQueryJEvent.handleUnit(jQueryObj,capture,bubble,func)));
	},
	removeEventListener:function(jQueryObj,name,func){
		for(var i=0;i<this.eventPool.length;i+=1){
			if(this.eventPool[i].name == name){
				for(var j=0;j<this.eventPool[i].handleArray.length;j+=1){
					if(this.eventPool[i].handleArray[j].jQueryObj[0] == jQueryObj[0] && this.eventPool[i].handleArray[j].func == func){
						this.eventPool[i].handleArray.splice(j,1);
						return;
					}
				}
			}
		}		
	},
	hasJEventListener:function(jQueryObj,name){
		for(var i=0;i<this.eventPool.length;i+=1){
			if(this.eventPool[i].name == name){
				for(var j=0;j<this.eventPool[i].handleArray.length;j+=1){
					if(this.eventPool[i].handleArray[j].jQueryObj[0] == jQueryObj[0]){
						return true;
					}
				}
			}
		}
		return false;
	},
	getJEventListener:function(jQueryObj,name){
		var handleArrayRet = [];
		for(var i=0;i<this.eventPool.length;i+=1){
			if(this.eventPool[i].name == name){		
				for(var j=0;j<this.eventPool[i].handleArray.length;j+=1){
					handleArrayRet.push(this.eventPool[i].handleArray[j]);
				}
			}
		}
		return handleArrayRet;
	}
};
jQueryJEvent.singleJEventController = null;
jQueryJEvent.getJEventControllerInstance = function(){
	if(jQueryJEvent.singleJEventController == null){
		jQueryJEvent.singleJEventController = new jQueryJEvent.jEventController();
	}
	return jQueryJEvent.singleJEventController;
};

$.fn.extend({
	dispatchJEvent: function(name/*string*/,data,capture,bubble){		
		capture = capture || true;//发出事件时默认开启捕获
		bubble  = bubble  || true;//发出事件时默认开启冒泡
		var controller = jQueryJEvent.getJEventControllerInstance();
		var jEvent = jQuery.createJEvent(name,capture,bubble,data,this);
		controller.handleJEvent(jEvent);
	},
	addJEventListener:function(name,func,capture,bubble){		
		capture = capture || false;//添加监听时默认不开启捕获
		bubble  = bubble  || true; //添加监听时默认开启冒泡
		var controller = jQueryJEvent.getJEventControllerInstance();
		controller.addjEventListener(this,name,capture,bubble,func);	
	},
	removeJEventListener:function(name,func){
		var controller = jQueryJEvent.getJEventControllerInstance();
		controller.removeEventListener(this,name,func);
	},
	hasJEventListener:function(name){
		var controller = jQueryJEvent.getJEventControllerInstance();	
		return	controller.hasJEventListener(this,name);
	},
	getJEventListener:function(name){
		var controller = jQueryJEvent.getJEventControllerInstance();	
		return controller.getJEventListener(this,name);
	}
});
$.extend({
	createJEvent:function(name,capture,bubble,data,target){
		return new jQueryJEvent.Event(name,capture,bubble,data,target);
	},
	getParentsArray:function(jQueryObj){
		var parentsArray = [];
		if(jQueryObj[0] == jQuery(document)[0]){return parentsArray;}
		var tempParent = null;		
		for(tempParent = jQueryObj.parent();tempParent[0]!=jQuery(document)[0];tempParent=tempParent.parent()){			
			parentsArray.push(tempParent);
		}
		parentsArray.push(jQuery(document));
		return parentsArray;
	},
	checkInArray:function(ele,array,isJQuery){
		isJQuery = isJQuery || true;
		for(var i=0;i<array.length;i+=1){
			if(isJQuery){
				if(array[i][0] == ele[0]){
					return true;
				}
			}else{
				if(array[i] == ele){
					return true;
				}
			}
		}
		return false;
	}
});
