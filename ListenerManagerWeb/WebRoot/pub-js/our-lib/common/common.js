/**
 * 前台工具类
 * 
 * @class CommonUtils
 * @static
 */
CommonUtils = {
	/**
	 * 注册命名空间
	 * 
	 * @example CommonUtils.regNamespace("cust");
	 *          CommonUtils.regNamespace("cust","order.custOrder");
	 * @return 名称空间对象
	 */
	regNamespace : function() {
		var args = arguments, o = null, nameSpaces;
		for ( var i = 0; i < args.length; i = i + 1) {
			nameSpaces = args[i].split(".");
			o = window;
			for ( var j = 0; j < nameSpaces.length; j = j + 1) {
				o[nameSpaces[j]] = o[nameSpaces[j]] || {};
				o = o[nameSpaces[j]];
			}
		}
		return o;
	},
	/**
	 * 生成一个空function
	 * 
	 * @function
	 * @example var itemPorxy = { id : '', type : 'grp', callback :
	 *          <b>CommonUtils.emptyFunc</b>, data : null }
	 */
	emptyFunc : function() {
	},
	/**
	 * 判断一个对象是否是{}这样的空对象,忽略加在Object.prototype上的方法、属性
	 * 
	 * @function
	 * @example
	 * @param {object}
	 *            obj
	 * @return {boolean}
	 */
	isEmptyObj : function(obj) {
		return this.objKeys(obj).length == this.objKeys( {}).length;
	},
	/**
	 * 返回一个对象的所有属性
	 * 
	 * @function
	 * @param {object}
	 *            obj
	 * @return {array}
	 */
	objKeys : function(obj) {
		var keys = [];
		for ( var property in obj)
			keys.push(property);
		return keys;
	},
	/**
	 * @description json 比较分析,递归实现
	 * @function json对象比较
	 * @requires jquery js库文件;Array.prototype.compareTo(array)方法
	 * @author JiPing
	 * @param {object}
	 *            j1 旧的对象
	 * @param {object}
	 *            j2 新的对象
	 * @param {string}
	 *            antePath 从根结点到父节点的路径
	 * @param {array}
	 *            diffResults 比较结果数组,外部调用的时候可以不填,主要是函数内部递归调用时使用
	 * @param {object}
	 *            diffConfig arraykey:arrayItemId 如
	 *            busiOrder:"busiOrderInfo.boId"
	 * @return {array} diffResults 形如:
	 *         [".pas.t[0].t3name[update]",".pas.t[1][new]",".pas.t[2].name[update]"]
	 * @example
	 * 
	 * <pre>
	 * var obj1 = {
	 * 	name : &quot;linzp&quot;,
	 * 	pas : {
	 * 		pasName : 'tt',
	 * 		paspas : 'pj',
	 * 		t : [ {
	 * 			tid : 1,
	 * 			t3name : 'rr',
	 * 			t3pas : 'rree'
	 * 		}, {
	 * 			tid : 2,
	 * 			name : 'sss1',
	 * 			pas : 'asa'
	 * 		} ]
	 * 	}
	 * };
	 * var obj2 = {
	 * 	name : &quot;linzp&quot;,
	 * 	pas : {
	 * 		pasName : 'tt',
	 * 		t : [ {
	 * 			tid : 1,
	 * 			t3name : 'rrff',
	 * 			t3pas : 'rree'
	 * 		}, {
	 * 			tid : 3,
	 * 			name : 'sss2',
	 * 			pas : 'asa'
	 * 		}, {
	 * 			tid : 2,
	 * 			name : 'sss',
	 * 			pas : 'bsb'
	 * 		} ]
	 * 	}
	 * };
	 * var arrIds = {
	 * 	t : 'tid'
	 * };
	 * console.debug(jsonDataHandler.diff(obj1, obj2, '', [], arrIds));
	 * //[&quot;.pas.t[0].t3name[update]&quot;, &quot;.pas.t[1][new]&quot;, &quot;.pas.t[2].name[update]&quot;, &quot;.pas.t[2].pas[update]&quot;, &quot;.pas.t[update]&quot;, &quot;.pas.paspas[delete]&quot;]
	 * </pre>
	 */
	jsonDiff : function(j1, j2, antePath, diffResults, diffArrIds) {
		if (typeof this.diffing === "undefined" || this.diffing === 0) {
			this.diffCyclicCheck = [];
			this.diffing = 0;
		}
		var diffRes = {};
		antePath = antePath || "";
		diffResults = diffResults || [];
		this.diffing += 1;
		if (typeof j1 === "undefined") {
			j1 = {};
		}
		if (typeof j2 === "undefined") {
			j2 = {};
		}
		if (typeof this.diffCyclicCheck === "undefined") {
			this.diffCyclicCheck = [];
		}
		var key, bDiff;
		for (key in j2)
			if (j2.hasOwnProperty(key)) {
				bDiff = false;
				if (typeof j1[key] === "undefined"
						|| typeof j1[key] != typeof j2[key]) {
					diffResults.push(antePath + "." + key + "[new]");
					bDiff = true;
				} else if (j1[key] !== j2[key]) {
					if (typeof j2[key] === "object") {
						if (this.diffCyclicCheck.indexOf(j2[key]) >= 0) {
							break;
						} else if ($.isArray(j2[key])) {
							// 数组对象的简单比较

							if (j1[key].length !== j2[key].length
									|| j1[key] !== j2[key]) {
								if (j2[key].compareTo(j1[key]) === false) {
									bDiff = true;
								}
							}
							var self = this;
							$
									.each(
											j2[key],
											function(i, n) {
												// 在j1[key]里面找n,也就是j2[key][i]
												var nFound = false;
												$
														.each(
																j1[key],
																function(ii, nn) {
																	// 根据ID来找
																	if (eval("nn."
																			+ diffArrIds[key]) == eval("n."
																			+ diffArrIds[key])) {
																		nFound = true;
																		self
																				.jsonDiff(
																						n,
																						nn,
																						antePath
																								+ "."
																								+ key
																								+ "["
																								+ i
																								+ "]",
																						diffResults,
																						diffArrIds);
																		return false;// 结束each循环
																	}
																})
												if (!nFound) {
													diffResults.push(antePath
															+ "." + key + "["
															+ i + "][new]");
												}
											});
						} else if (typeof j1[key] === "object") {
							this.jsonDiff(j1[key], j2[key], antePath + "."
									+ key, diffResults, diffArrIds);
						} else {
							bDiff = true;
						}
						this.diffCyclicCheck.push(j2[key]);
					} else if (j1[key] !== j2[key]) {
						bDiff = true;
					}
				}
				if (bDiff) {
					diffRes[key] = j2[key];
					diffResults.push(antePath + "." + key + "[update]");
				}
			}
		for (key in j1)
			if (j1.hasOwnProperty(key)) {
				bDiff = false;
				if (typeof j2[key] === "undefined") {
					diffRes[key] = j1[key];
					diffResults.push(antePath + "." + key + "[delete]");
				}
			}
		this.diffing -= 1;
		return diffResults;
	},
	/**
	 * @description 首字母大写
	 * @param {string}
	 *            str
	 * @return {string} 单词str的首字母大写
	 */
	upperCapital : function(str) {
		if (!str) {
			return null;
		}
		if (typeof (str) != "string") {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	},
	/**
	 * @description 删除数组中某个元素
	 * @param {array}
	 *            array 待处理的数组
	 * @param {int}
	 *            index 待删除的元素在数组中的索引
	 * @return {array} 处理后的数组
	 */
	removeArrItemAt : function(array, index) {
		delete array[index];
		for ( var i = index + 1; i < array.length; i++) {
			array[i - 1] = array[i];
		}
		array.length -= 1;
		return array;
	},
	/**
	 * @description 对象拷贝,主要用来拷贝json对象
	 * @param {}
	 *            srcObj
	 */
	objClone : function(srcObj) {
		var buf;
		if (srcObj instanceof Array) {
			buf = [];
			var i = srcObj.length;
			while (i--) {
				buf[i] = this.objClone(srcObj[i]);
			}
			return buf;
		} else if (srcObj instanceof Object) {
			buf = {};
			for ( var k in srcObj) {
				buf[k] = this.objClone(srcObj[k]);
			}
			return buf;
		} else {
			return srcObj;
		}
	},
	/**
	 * @description 左补齐
	 * @param {string}
	 *            str 要操作的字符串
	 * @param {int}
	 *            padLen 补齐长度
	 * @param {String}
	 *            padChar 填充字符
	 */
	lpad : function(str, padLen, padChar) {
		if (str == null || str == undefined) {
			return str;
		}
		if (str.toString().length < padLen) {
			for ( var i = 0; i < padLen - str.toString().length; i++) {
				str += padChar + str;
			}
		}
		return str;
	}
}

jQuery.extend( {
	/**
	 * <b>挂在jQuery下</b>,同步加载脚本<br>
	 * 通过设置jquery,ajax 同步异步开关实现<br>
	 * 加载前设置为同步,加载完后立即设为异步<br>
	 * 脚本加载完后不执行回调函数.<br>
	 * 注意加载的脚本如果有window.onload或者$(function(){ ... })是不会被执行的<br>
	 * jquery的load方法是异步的,可以执行回调.<br>
	 * 
	 * @function
	 * @author 翁舒嵘
	 * @example $.getScriptSync("pub-js/third-lib/js-common-lib/jquery/jquery.easing.js");
	 * @param {string}
	 *            url url 是这个脚本所在jsp/html/..页面的相对路径
	 */
	getScriptSync : function(url) {
		jQuery.ajaxSetup( {
			async : false
		});
		jQuery.getScript(url);
		jQuery.ajaxSetup( {
			async : true
		});

	}
});

Array.prototype.compareTo = function(compareAry) {
	if (this.length === compareAry.length) {
		var i;
		for (i = 0; i < compareAry.length; i += 1) {
			if ($.isArray(this[i]) === true) {
				if (this[i].compareTo(compareAry[i]) === false) {
					return false;
				}
				continue;
			} else if (this[i] !== compareAry[i]) {
				return false;
			}
		}
		return true;
	}
	return false;
};
/**
 * @description 摭罩父层
 * @param curDivId
 *            弹出div的ID parentDivId 父div的Id
 */
function coverParent(curDivId, parentDivId, inputHeight, color, opacity) {
	if (color == undefined) {
		color = 'black';
	}
	if (opacity == undefined) {
		opacity = 0.5;
	}
	var curDiv = $('#' + curDivId);
	var parentDiv;
	if (parentDivId != undefined) {
		parentDiv = $('#' + parentDivId);
	} else {
		parentDiv = curDiv.parent();
	}
	var coverDivId = "divCover_" + curDivId;

	var coverDiv = $('#' + coverDivId);
	if (!coverDiv.is("div")) {
		coverDiv = $('<div id="' + coverDivId + '"></div>').appendTo(
				parentDiv.parent());
		coverDiv.attr('isCover', 'true');
		coverDiv.attr('curDivId', curDivId);
		coverDiv.attr('parentDivId', parentDiv.attr('id'));
		coverDiv.attr('inputHeight', inputHeight);
		coverDiv.attr('color', color);
		coverDiv.attr('opacity', opacity);
	} else {
		coverDiv.show();
	}
	var position = parentDiv.position();
	var width = parentDiv.width();
	var height = parentDiv.height();
	coverDiv.css("left", position.left);
	coverDiv.css("top", position.top);
	coverDiv.css("width", width);
	coverDiv.css("height", height);
	coverDiv.css("position", "absolute");
	coverDiv.css("z-index", curDiv.css('z-index') - 1);
	coverDiv.css("background-color", color);
	coverDiv.css("opacity", opacity);
	curDiv.css("left", position.left + (width - curDiv.width()) * 0.5);
	curDiv.css("top", (inputHeight != undefined) ? (position.top + inputHeight)
			: (position.top + (height - curDiv.height()) * 0.5));
	curDiv.css("position", "absolute");

}
/**
 * @description 移除所有的摭罩层
 * @param {}
 *            curDivId
 */
function removeAllCovers() {
	var coverDiv = $('div[isCover="true"]');
	coverDiv.hide();
}
/**
 * @description 去掉摭罩层
 * @param curDivId
 *            弹出div的ID
 */
function removeCover(curDivId) {
	var coverDivId = "divCover_" + curDivId;
	var coverDiv = $('#' + coverDivId);
	if (coverDiv.is("div")) {
		coverDiv.hide();
	}
}
/**
 * 刷新摭罩层
 */
function refreshCover() {
	var coverDiv = $('div[isCover="true"]');
	$.each(coverDiv, function(i, tmpDiv) {
		tmpDiv = $(tmpDiv);
		if (tmpDiv.css('display') != 'none') {
			var curDivId = tmpDiv.attr('curDivId');
			var parentDivId = tmpDiv.attr('parentDivId');
			var inputHeight = tmpDiv.attr('inputHeight');
			var color = tmpDiv.attr('color');
			var opacity = tmpDiv.attr('opacity');
			coverParent(curDivId, parentDivId, inputHeight, color, opacity);
		}
	});
}
/**
 * 从数组中移除元素
 * 
 * @param array
 * @param attachId
 * @return
 */
function RemoveArray(array, attachId) {
	for ( var i = 0, n = 0; i < array.length; i++) {
		if (array[i] != attachId) {
			array[n++] = array[i]
		}
	}
	array.length -= 1;
}
Array.prototype.remove = function(obj) {
	return RemoveArray(this, obj);
};

// firefox对dom解析的支持 start add by hanl
try {
	XMLDocument.prototype.selectNodes = function(cXPathString, xNode) {
		if (!xNode) {
			xNode = this;
		}
		var oNSResolver = this.createNSResolver(this.documentElement)
		var aItems = this.evaluate(cXPathString, xNode, oNSResolver,
				XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null)
		var aResult = [];
		for ( var i = 0; i < aItems.snapshotLength; i++) {
			aResult[i] = aItems.snapshotItem(i);
		}
		return aResult;
	}

	XMLDocument.prototype.selectSingleNode = function(cXPathString, xNode) {
		if (!xNode) {
			xNode = this;
		}
		var xItems = this.selectNodes(cXPathString, xNode);
		if (xItems.length > 0) {
			return xItems[0];
		} else {
			return null;
		}
	}

	Element.prototype.selectSingleNode = function(cXPathString) {
		if (this.ownerDocument.selectSingleNode) {
			return this.ownerDocument.selectSingleNode(cXPathString, this);
		} else {
			throw "For XML Elements Only";
		}
	}

	Element.prototype.selectNodes = function(cXPathString) {
		if (this.ownerDocument.selectNodes) {
			return this.ownerDocument.selectNodes(cXPathString, this);
		} else {
			throw "For XML Elements Only";
		}
	}

	Element.prototype.__defineGetter__("text", function() {
		return this.textContent;
	})

	Element.prototype.__defineSetter__("text", function(sText) {
		this.textContent = sText;
	})
} catch (e) {

}

function f_createDom() {
	var dom = null;
	try {
		dom = new ActiveXObject('Msxml2.DOMDocument.3.0');
	} catch (x) {
	}
	try {
		dom = new ActiveXObject('Microsoft.XMLDOM');
	} catch (x) {
	}
	// firefox
	try {
		dom = document.implementation.createDocument("", "", null);
	} catch (x) {
	}
	if (dom == null) {
		alert('获取DOM失败');
		return null;
	}
	dom.setProperty("SelectionLanguage", "XPath");
	return dom;
}
// firefox对dom解析的支持 end add by hanl

function Map() {
	/** 存放键的数组(遍历用到) */
	this.keys = new Array();
	/** 存放数据 */
	this.data = new Object();

	/**
	 * 放入一个键值对
	 * 
	 * @param {String}
	 *            key
	 * @param {Object}
	 *            value
	 */
	this.put = function(key, value) {
		if (this.data[key] == null) {
			this.keys.push(key);
		}
		this.data[key] = value;
	};

	/**
	 * 获取某键对应的值
	 * 
	 * @param {String}
	 *            key
	 * @return {Object} value
	 */
	this.get = function(key) {
		return this.data[key];
	};

	/**
	 * 删除一个键值对
	 * 
	 * @param {String}
	 *            key
	 */
	this.remove = function(key) {
		this.keys.remove(key);
		this.data[key] = null;
	};

	/**
	 * 遍历Map,执行处理函数
	 * 
	 * @param {Function}
	 *            回调函数 function(key,value,index){..}
	 */
	this.each = function(fn) {
		if (typeof fn != 'function') {
			return;
		}
		var len = this.keys.length;
		for ( var i = 0; i < len; i++) {
			var k = this.keys[i];
			fn(k, this.data[k], i);
		}
	};

	/**
	 * 获取键值数组(类似Java的entrySet())
	 * 
	 * @return 键值对象{key,value}的数组
	 */
	this.entrys = function() {
		var len = this.keys.length;
		var entrys = new Array(len);
		for ( var i = 0; i < len; i++) {
			entrys[i] = {
				key : this.keys[i],
				value : this.data[i]
			};
		}
		return entrys;
	};

	/**
	 * 判断Map是否为空
	 */
	this.isEmpty = function() {
		return this.keys.length == 0;
	};

	/**
	 * 获取键值对数量
	 */
	this.size = function() {
		return this.keys.length;
	};

	/**
	 * 重写toString
	 */
	this.toString = function() {
		var s = "{";
		for ( var i = 0; i < this.keys.length; i++, s += ',') {
			var k = this.keys[i];
			s += k + "=" + this.data[k];
		}
		s += "}";
		return s;
	};
}
/**
 * 删除左右两端的空格 包括全角空格
 */
String.prototype.trim = function() {
	return this.replace(/(^[\s|'　']*)|([\s|'　']*$)/g, '');
}
/**
 * @description 判断是否是数字
 */
function isInteger(num) {
	var patrn = /^[0-9]*[1-9][0-9]*$/;

	if (!patrn.exec(num))
		return false
	else
		return true
}