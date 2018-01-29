/**
 * 功能：JS基础类
 * 描述：基础命名空间定义，基础函数
 * 运行环境：IE 5.5+ only
 * 作者：Kunee Hwang
 * 创建：2008-8-20
 */
bss = {
    'version': '0.1',
    'navigator':navigator.userAgent.toLowerCase(),
	/**
	 * 获取HTML BODY对象
	 */
    getBody: function(){
        return document.body || document.documentElement
    },
	/**
	 * 空函数
	 */
    emptyFn: function(){
    },
    /**
     * 创建控件
     * @param {Object} tag
     */
    create: function(tag){
        return document.createElement(tag);
    },
    /**
     * 拷贝对象属性
     * @param {Object} o
     * @param {Object} c
     * @param {Object} override
     */
    apply: function(o, c, override){
        if (override == null) 
            override = true;
		else
			override = eval(override);
        if (o && c && typeof c == 'object') {
            for (var p in c) {
                if (override) 
                    o[p] = c[p];
                else 
                    if (o[p] == null) {
                        o[p] = c[p];
                    }
            }
        }
        return o;
    },
    /**
     * 查找对象
     * 方法1：类似于 docuemnt.getElementById;可以简化为 $('abc')
     * 方法2：在列表中查找
     * @param {Object} map
     * @param {Object} _key
     */
    findObj: function(map, _key){
        if (_key == null) {
            _key = map;
            return document.getElementById(_key);
        }
        for (var key in map) {
            key = key.toLowerCase();
            if (_key == key) 
                return map[_key];
        }
        return null;
    },
	/**
	 * 获取 XML DOM
	 * @param {Object} selectionLanguage
	 */
    getMsDom: function(selectionLanguage){
		{
			var dom = null;
			try {
				dom = new ActiveXObject('Microsoft.XMLDOM');
			} 
			catch (e) {
				dom = new ActiveXObject('Msxml2.DOMDocument.3.0');
			}
			if (dom == null) {
				alert('创建xml控件失败');
				return null;
			}
			dom.setProperty("SelectionLanguage", selectionLanguage ? selectionLanguage : "XPath");
			return dom;
		}
    },
	/**
	 * 继承
	 * 原型上增加supperclass做为父类，child实例方法。
	 * @param {Object} origin
	 * @param {Object} override
	 */
	extend:function(child,base){
		//child实例
		child.prototype = eval('new '+ base +'()');
		bss.apply(child.prototype,eval('new ' + child +'()'));
		child.prototype.constructor = base;
		child.prototype.supperclass = base;
	},
	/**
	 * 是否是IE7
	 */
	isIE7:function(){
		return this.navigator.indexOf("msie 7") > -1;
	}
};


$$$ = bss.findObj;

bss.util = {
	/**
	 * 获取当前页面路径的参数
	 * @param {Object} obj
	 */
	getPara:function(paraName){
		var urlPara = window.location.href;
		var reg = new RegExp("[&|?]"+paraName+"=([^&$]*)", "gi"); 
		var a = reg.test(urlPara); 
		return a ? RegExp.$1 : ""; 
	}
};



/**
 * 字符串
 * 此静态对象会绑定到 String对象，增加函数请注意处理 this对象
 */
bss.util.string = {
	/**
	 * 去除字符串首尾空格
	 * 
	 * var str = ' text ';
	 * str.trim();
	 * var str1 = ' text ';
	 * str1 = bss.util.String.trim(str1);
	 * 
	 * @param {Object} str
	 */
	trim: function(str){
		{
			var re = /^\s+|\s+$/g;
			
			if (str != null) {
				return str.replace(re, "");
			}
			else {
				//若不带参数，则将调用者认为是字符串对象
				return this.replace(re, "");
			}
		}
	},
	/**
	 * 格式化字符串
	 * var str = '{0}{1},你好';
	 * str = str.format('Kunee','先生');
	 */
	format:function(){
		{
			var args = Array.prototype.slice.call(arguments, 0);
			return this.replace(/\{(\d+)\}/g, function(m, i){
				return args[i];
			});
		}
	},
	/**
	 * 格式化字符串(静态方法)
	 * var str = '{0}{1},你好';
	 * str = bss.util.String.sFormat(str,'Kunee','先生');
	 */
	sFormat:function(str){
		{
			var args = Array.prototype.slice.call(arguments, 1);
			return str.replace(/\{(\d+)\}/g, function(m, i){
				return args[i];
			});
		}
	},
    /**
     * 字符串转换为JSON对象
     * @param {Object} str
     */
    decodeJson: function(str){
        if (str == null) 
            str = this;
        return eval("(" + str + ")");
    },
	/**
	 * 是否以某字符串开始
	 * @param {Object} str
	 */
	startsWith:function(str){
		 return this.indexOf(str) == 0;
	},
	/**
	 * 是否以某字符串结束
	 * @param {Object} str
	 */
	endsWith: function(str) {
    	var d = this.length - str.length;
    	return d >= 0 && this.lastIndexOf(str) == d;
	}
};
bss.apply(String.prototype,bss.util.string);


bss.ajax = {
    getXmlHttpRequest: function(){
	    var xhr = window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();
        return xhr;
    },
	/**
	 * 远程获取
	 * @param {Object} url
	 * @param {Object} data
	 * @param {Object} callback
	 * @param {Object} domData
	 */
	post:function(url,data,callback,domData){
		var parameter = {
			'url':url,
			'urlpara':data,
			'p_method':'POST',
			'async':callback!=null,
			'dompara':domData
		};
		return this.load(parameter,callback);
	},
	/**
	 * 远程获取
	 * @param {Object} url
	 * @param {Object} data
	 * @param {Object} callback
	 */
	get:function(url,data,callback){
		var parameter = {
			'url':url,
			'urlpara':data,
			'p_method':'GET',
			'async':callback!=null
		};
		return this.load(parameter,callback);
	},
	/**
	 * 远程加载
	 * @param {Object} paramters 参数
	 * @param {Object} callback 回调函数
	 */
    load: function(paramters, callback){
		//修正配置
        paramters = this.getDefaultSetting(paramters);
        callback = callback || bss.emptyFn;
        if(paramters.url.indexOf('?')>-1){
        	if(!paramters.url.endsWith('&')){
        		paramters.url = paramters.url + '&';
        	}
        	paramters.url = paramters.url + this.getUrlParamter(paramters['urlpara']);
        }else
	        paramters.url = paramters.url + '?' + this.getUrlParamter(paramters['urlpara']);

        var xhr = this.getXmlHttpRequest();
        if (paramters.async) {
			//异步模式，完成以后执行回调函数
			xhr.onreadystatechange = function(){
				if(xhr.readyState == 4){
					if(xhr.status == 200){
						eval(callback).call(this,bss.ajax.getResult(xhr,paramters.dataType));
					}
				}
			}
        }
		xhr.open(paramters.p_method,paramters.url,paramters.async);
		xhr.setRequestHeader("Content-Type",paramters.contentType);
		xhr.setRequestHeader('charset',paramters.charset);
        xhr.send(paramters.data);
		if(paramters.async){
			//异步模式，返回XMLHTTP句柄
			return xhr;
		}else{
			//同步模式，直接返回值
			return this.getResult(xhr,paramters.dataType);
		}
    },
	/**
	 * 获取返回值
	 * @param {Object} xhr
	 * @param {Object} dataType
	 */
	getResult:function(xhr,dataType){
		var dom = bss.getMsDom();
		dom.load(xhr.responseXML);
		if(dom==null || dom.xml == '')
			return xhr.responseText;
		return dom;
	},
	/**
	 * 放弃请求
	 * @param {Object} xhr
	 */
    abort: function(xhr){
        if (xhr)
            xhr.abort();
    },
	/**
	 * URL参数
	 * @param {Object} obj
	 */
    getUrlParamter: function(obj){
        var str = '';
		if (obj != null) {
			for (var key in obj) {
				str += key + '=' + encodeURIComponent(encodeURIComponent(obj[key])) + '&';
			}
		}
        str += 'reconnectKeyCode=' + Math.random();
        return str;
    },
	/**
	 * 默认配置
	 * @param {Object} paramters
	 */
    getDefaultSetting: function(paramters){
        paramters['url'] = paramters['url'];
        paramters['p_method'] = paramters['p_method'] ? paramters['p_method'] : 'GET';
        paramters['async'] = paramters['async'] ? eval(paramters['async']) : false;
        paramters['charset'] = paramters['charset'] ? paramters['charset'] : 'utf-8';
        paramters['data'] = paramters['dompara'] ? paramters['dompara'] : '';
        paramters['contentType'] = paramters['contentType'] ? paramters['contentType'] : "application/x-www-form-urlencoded";
        paramters['dataType'] = paramters['dataType'] ? paramters['dataType'] : 'text';
        return paramters;
    }
};