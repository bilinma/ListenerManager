/**
 * ǰ̨������
 * 
 * @class CommonUtils
 * @static
 */
CommonUtils = {
	/**
	 * ע�������ռ�
	 * 
	 * @example CommonUtils.regNamespace("cust");
	 *          CommonUtils.regNamespace("cust","order.custOrder");
	 * @return ���ƿռ����
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
	 * ����һ����function
	 * 
	 * @function
	 * @example var itemPorxy = { id : '', type : 'grp', callback :
	 *          <b>CommonUtils.emptyFunc</b>, data : null }
	 */
	emptyFunc : function() {
	},
	/**
	 * �ж�һ�������Ƿ���{}�����Ŀն���,���Լ���Object.prototype�ϵķ���������
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
	 * ����һ���������������
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
	 * @description json �ȽϷ���,�ݹ�ʵ��
	 * @function json����Ƚ�
	 * @requires jquery js���ļ�;Array.prototype.compareTo(array)����
	 * @author JiPing
	 * @param {object}
	 *            j1 �ɵĶ���
	 * @param {object}
	 *            j2 �µĶ���
	 * @param {string}
	 *            antePath �Ӹ���㵽���ڵ��·��
	 * @param {array}
	 *            diffResults �ȽϽ������,�ⲿ���õ�ʱ����Բ���,��Ҫ�Ǻ����ڲ��ݹ����ʱʹ��
	 * @param {object}
	 *            diffConfig arraykey:arrayItemId ��
	 *            busiOrder:"busiOrderInfo.boId"
	 * @return {array} diffResults ����:
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
							// �������ļ򵥱Ƚ�

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
												// ��j1[key]������n,Ҳ����j2[key][i]
												var nFound = false;
												$
														.each(
																j1[key],
																function(ii, nn) {
																	// ����ID����
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
																		return false;// ����eachѭ��
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
	 * @description ����ĸ��д
	 * @param {string}
	 *            str
	 * @return {string} ����str������ĸ��д
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
	 * @description ɾ��������ĳ��Ԫ��
	 * @param {array}
	 *            array �����������
	 * @param {int}
	 *            index ��ɾ����Ԫ���������е�����
	 * @return {array} ����������
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
	 * @description ���󿽱�,��Ҫ��������json����
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
	 * @description ����
	 * @param {string}
	 *            str Ҫ�������ַ���
	 * @param {int}
	 *            padLen ���볤��
	 * @param {String}
	 *            padChar ����ַ�
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
	 * <b>����jQuery��</b>,ͬ�����ؽű�<br>
	 * ͨ������jquery,ajax ͬ���첽����ʵ��<br>
	 * ����ǰ����Ϊͬ��,�������������Ϊ�첽<br>
	 * �ű��������ִ�лص�����.<br>
	 * ע����صĽű������window.onload����$(function(){ ... })�ǲ��ᱻִ�е�<br>
	 * jquery��load�������첽��,����ִ�лص�.<br>
	 * 
	 * @function
	 * @author ������
	 * @example $.getScriptSync("pub-js/third-lib/js-common-lib/jquery/jquery.easing.js");
	 * @param {string}
	 *            url url ������ű�����jsp/html/..ҳ������·��
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
 * @description ���ָ���
 * @param curDivId
 *            ����div��ID parentDivId ��div��Id
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
 * @description �Ƴ����е����ֲ�
 * @param {}
 *            curDivId
 */
function removeAllCovers() {
	var coverDiv = $('div[isCover="true"]');
	coverDiv.hide();
}
/**
 * @description ȥ�����ֲ�
 * @param curDivId
 *            ����div��ID
 */
function removeCover(curDivId) {
	var coverDivId = "divCover_" + curDivId;
	var coverDiv = $('#' + coverDivId);
	if (coverDiv.is("div")) {
		coverDiv.hide();
	}
}
/**
 * ˢ�����ֲ�
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
 * ���������Ƴ�Ԫ��
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

// firefox��dom������֧�� start add by hanl
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
		alert('��ȡDOMʧ��');
		return null;
	}
	dom.setProperty("SelectionLanguage", "XPath");
	return dom;
}
// firefox��dom������֧�� end add by hanl

function Map() {
	/** ��ż�������(�����õ�) */
	this.keys = new Array();
	/** ������� */
	this.data = new Object();

	/**
	 * ����һ����ֵ��
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
	 * ��ȡĳ����Ӧ��ֵ
	 * 
	 * @param {String}
	 *            key
	 * @return {Object} value
	 */
	this.get = function(key) {
		return this.data[key];
	};

	/**
	 * ɾ��һ����ֵ��
	 * 
	 * @param {String}
	 *            key
	 */
	this.remove = function(key) {
		this.keys.remove(key);
		this.data[key] = null;
	};

	/**
	 * ����Map,ִ�д�����
	 * 
	 * @param {Function}
	 *            �ص����� function(key,value,index){..}
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
	 * ��ȡ��ֵ����(����Java��entrySet())
	 * 
	 * @return ��ֵ����{key,value}������
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
	 * �ж�Map�Ƿ�Ϊ��
	 */
	this.isEmpty = function() {
		return this.keys.length == 0;
	};

	/**
	 * ��ȡ��ֵ������
	 */
	this.size = function() {
		return this.keys.length;
	};

	/**
	 * ��дtoString
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
 * ɾ���������˵Ŀո� ����ȫ�ǿո�
 */
String.prototype.trim = function() {
	return this.replace(/(^[\s|'��']*)|([\s|'��']*$)/g, '');
}
/**
 * @description �ж��Ƿ�������
 */
function isInteger(num) {
	var patrn = /^[0-9]*[1-9][0-9]*$/;

	if (!patrn.exec(num))
		return false
	else
		return true
}