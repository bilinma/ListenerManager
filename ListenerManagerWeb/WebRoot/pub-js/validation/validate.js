/*****************************************************************************
 * TITLE
 *	JavaScript Common Functions
 *
 * VERSION
 *	1.0.2
 *
 * CREATED
 *	About 2002; Horse
 *
 * HISTORY (since Nov, 2003)
 *	2003-11-21: ? isDate ???? yyyyMMdd ??????????????????????????
 *  	2003-11-24: ?? isFloat?trimTail?trimTailSpace ?? bug??? getByteLength ???????????????v 1.0.1?
 *  	2004-06-05: ??? prepare ???????????v 1.0.2?
 *****************************************************************************/


/*============================= Functions List ==============================
[null/empty/blank check]
	isNullOrEmpty, isBlank
[single character check]
	isDigitChar, isSmallAlphabetChar, isCapitalAlphabetChar, isAlphabetChar, isAlphanumericChar
[string check]
	isNumber, isFloat, isDecimal, isAlphabet, isAlphanumeric, isDate, isEmailSimp, isEmailComp
[data type conversion]
	padNumber, dateToString, stringToDate
[string trim]
	trimLeading, trimTail, trimLeadingAndTail, trimLeadingSpace, trimTailSpace, trimSpace, trimWhiteSpace
[miscellaneous]
	isConsistOf, replace, compareDate
  ===========================================================================*/

<!--

// Check a string is whether the "null" value or an empty string.
function isNullOrEmpty(str) {
	if (str == null || str == "") return true;
	return false;
}

// Check a string is whether the "null" value or empty string, or consists
// of only space characters.
// Attention: even it is useful and very convenient, any functions, especially the
// trimming functions, in this file must be careful on avoiding recursive
// invokation when use it.
function isBlank(str) {
	str = trimSpace(str);
	return isNullOrEmpty(str);
}




///////////////////////////////////////////////////////////////////////////////
//
// Following 5 functions are used for checking one character.
//
///////////////////////////////////////////////////////////////////////////////

// Check a character is whether a digit character.
function isDigitChar(c) {
	return (c >= '0' && c <= '9');
}

// Check a character is whether a small alphabet character.
function isSmallAlphabetChar(c) {
	return (c >= 'a' && c <= 'z');
}

// Check a character is whether a capital alphabet character.
function isCapitalAlphabetChar(c) {
	return (c >= 'A' && c <= 'Z');
}

// Check a character is whether an alphabet character.
function isAlphabetChar(c) {
	return ((c>='a' && c<='z') || (c>='A' && c<='Z'));
}

// Check a character is whether a digit or an alphabet.
function isAlphanumericChar(c) {
	return ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
}




///////////////////////////////////////////////////////////////////////////////
//
// Following 8 functions are used for checking string (one or more characters).
//
///////////////////////////////////////////////////////////////////////////////

// Check a string is whether a number.
function isNumber(str) {
	for (var i=0; i<str.length; i++)
		if (str.charCodeAt(i) < 0x0030 || str.charCodeAt(i) > 0x0039) return false;
	return true;
}

function isPositiveNumber(str) {
   if(isNumber(str)){
      return parseInt(str) > 0;
   }
   return false;
}

// Check the input whether can be converted to a float value.
// Comes from the JavaScript newsgroup.
function isFloat(value) {
	var fValue = parseFloat(value);
	return (!isNaN(fValue));
}

// Check a string is whether a decimal string. If the string is started with
// character "0" or ended with character ".", it is also right.
function isDecimal(str) {
	for (var i=0; i<str.length; i++)
		if ("1234567890+-.".indexOf(str.charAt(i)) == -1) return false;

	if (str == "+" || str == "-" || str == "." || str == "+." || str == "-.") return false;
	if (str.indexOf("+") > 0) return false;
	if (str.indexOf("-") > 0) return false;
	if (str.indexOf("+") != str.lastIndexOf("+")) return false;
	if (str.indexOf("-") != str.lastIndexOf("-")) return false;
	if (str.indexOf(".") != str.lastIndexOf(".")) return false;
	return true;
}

// Check a string whether consists of alphabets.
function isAlphabet(str) {
	for (var i=0; i<str.length; i++) {
		var c = str.charCodeAt(i);
		if (c < 0x0041 || (c > 0x005A && c < 0x0061) || c > 0x007A) return false;
	}
	return true;
}

// Check a string whether consists of digits and alphabets.
function isAlphanumeric(str) {
	for (var i=0; i<str.length; i++) {
		var c = str.charCodeAt(i);
		if (c < 0x0030 || (c > 0x0039 && c < 0x0041) || (c > 0x005A && c < 0x0061) || c > 0x007A) return false;
	}
	return true;
}

// Check a string separated by specified character is whether a valid date. Such date
// must contain three parts: year, month and day.
// If the separator is null or an empty string, or not supported, the date string is
// considered has the format yyyyMMdd.
function isDate(str, separator) {
	var strYear = "";
	var strMonth = "";
	var strDay = "";

	if (separator == undefined || isNullOrEmpty(separator)) {
		if (str.length != 8) return false;
		strYear = str.substring(0, 4);
		strMonth = str.substring(4, 6);
		strDay = str.substring(6);
	}
	else {
		var iSeparatorCount = 0;
	
		for (var i=0; i<str.length; i++) {
			var c = str.charAt(i);
			var cd = str.charCodeAt(i);
	
			// digit
			if (cd >= 0x0030 && cd <= 0x0039) {
				switch (iSeparatorCount) {
					case 0:
						strYear += c;
						break;
					case 1:
						strMonth += c;
						break;
					case 2:
						strDay += c;
						break;
					default:
						return false;
				}
			}
			// separator
			else if (c == separator) {
				iSeparatorCount++;
				if (iSeparatorCount > 2) return false;
			}
			// other
			else
				return false;
		}
	}
	
	if (strYear.length == 0 || strMonth.length == 0 || strDay.length == 0)
		return false;

	// convert to integer
	var iYear = parseInt(eval(strYear));
	var iMonth = parseInt(eval(strMonth));
	var iDay = parseInt(eval(strDay));

	if (iYear < 1000 || iYear > 9999) return false;
	if (iMonth < 1 || iMonth > 12) return false;
	if (iDay < 1) return false;

	// determine leap
	var iFebDays = 28;
	if ((iYear % 400 == 0) || ((iYear % 4 == 0) && (iYear % 100 != 0))) iFebDays = 29;

	switch (iMonth) {
		case 2:
			return (iDay <= iFebDays);
		case 4: case 6: case 9: case 11:
			return (iDay <= 30);
		default:
			return (iDay <= 31);
	}
}

// Simplified check a string is whether a E-mail address.
function isEmailSimp(str) {
	if (str.indexOf("@") == -1) return false;
	if (str.indexOf(".") == -1) return false;
	return true;
}

// Check a string is whether a E-mail address used more complex logic.
function isEmailComp(str) {
	if (str.indexOf("@") == -1 || str.indexOf(".") == -1) return false;
	if (str.indexOf("@") != str.lastIndexOf("@")) return false;

	var strDeny = '()<>@\\,;:"[] ';
	var strLeft = str.substring(0, str.indexOf("@"));
	for (var i=0; i<strLeft.length; i++) {
		if (strDeny.indexOf(strLeft.charAt(i)) > -1) return false;
	}

	var strRight = str.substring(str.indexOf("@") + 1);
	for (var i=0; i<strRight.length; i++)
		if ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-.".indexOf(strRight.charAt(i)) == -1) return false;

	if (strRight.indexOf(".") == 0 || strRight.indexOf("-") == 0) return false;
	if (strRight.indexOf("-.") > -1 || strRight.indexOf(".-") > -1) return false;
	if (strRight.lastIndexOf(".") == strRight.length - 1) return false;
	if (strRight.lastIndexOf("-") == strRight.length - 1) return false;

	return true;
}




///////////////////////////////////////////////////////////////////////////////
//
// Following 3 functions are used for formatting values or converting values or objects
// between different types.
//
///////////////////////////////////////////////////////////////////////////////

// Pad a number by zero to specified length. The zero characters always add to front.
function padNumber(num, len) {
	var strNum = new String(num);
	var iDiff = len - strNum.length;
	if (iDiff > 0){
		var str = "";
		for (var i=0; i<iDiff; i++) str = str.concat("0");
		return (str + strNum);
	}
	else return strNum;
}

// Format date to string. The first parameter must be a Date object in Script.
// The format string's meaning is as same as it in Java. For detail, as listed
// below ( please focus on the case for "month" vs "minute" ):
//  y - "year",
//  M - "month",
//  d - "day",
//  H - "hour",
//  m - "minute",
//  s - "second".
// Pay attention to that this function must invoke the function "formatNumber".
function dateToString(dt, fmt){
	if (fmt == null || fmt == "") return dt.toString();
	
	//analyse format
	var iYear = 0;
	var iMonth = 0;
	var iDay = 0;
	var iHour = 0;
	var iMinute = 0;
	var iSecond = 0;
	
	var arrFmt = new Array();
	var arrItem = new Array();
	var idxArray = 0;
	var iStart = 0;
	
	for (var i=0; i<fmt.length; ) {
		var c = fmt.charAt(i);
		switch (fmt.charCodeAt(i)) {
			case 0x0048:
				iHour++;
				break;
			case 0x004d:
				iMonth++;
				break;
			case 0x0064:
				iDay++;
				break;
			case 0x006d:
				iMinute++;
				break;
			case 0x0073:
				iSecond++;
				break;
			case 0x0079:
				iYear++;
				break;
			default:
		}
		while (true) {
			var d = fmt.charAt(i);
			if (c != d) break;
			i++;
		}
		arrItem[idxArray] = c;
		arrFmt[idxArray++] = fmt.substring(iStart, i);
		iStart = i;
	}

	if (iYear > 1 || iMonth > 1 || iDay > 1 || iHour > 1 || iMinute > 1 || iSecond > 1){
		alert("Invalid format string");
		return dt.toString();
	}

	var str = "";
	
	for (var i=0; i<arrItem.length; i++){
		switch (arrItem[i]) {
			case "y":   //year
				str += padNumber(dt.getYear(), arrFmt[i].length);
				break;
			case "M":   //month
				str += padNumber(dt.getMonth() + 1, arrFmt[i].length);
				break;
			case "d":   //day
				str += padNumber(dt.getDate(), arrFmt[i].length);
				break;
			case "H":   //hour
				str += padNumber(dt.getHours(), arrFmt[i].length);
				break;
			case "m":   //minute
				str += padNumber(dt.getMinutes(), arrFmt[i].length);
				break;
			case "s":   //second
				str += padNumber(dt.getSeconds() + 1, arrFmt[i].length);
				break;
			default:
				str += arrFmt[i];
		}
	}
	
	return str;
}

// Convert a string separated by specified character to a Date object.
// Such date must contains only three parts: year, month and day.
// If the separator is null or empty string, or not supported, the date string is
// considered has the format yyyyMMdd.
// If the conversion failed, returns null.
function stringToDate(str, separator) {
	var strYear = "";
	var strMonth = "";
	var strDay = "";

	if (separator == undefined || isNullOrEmpty(separator)) {
		if (str.length != 8) return false;
		strYear = str.substring(0, 4);
		strMonth = str.substring(4, 6);
		strDay = str.substring(6);
	}
	else {
		var iSeparatorCount = 0;
	
		for (var i=0; i<str.length; i++) {
			var c = str.charAt(i);
			var cd = str.charCodeAt(i);
	
			// digit
			if (cd >= 0x0030 && cd <= 0x0039) {
				switch (iSeparatorCount) {
					case 0:
						strYear += c;
						break;
					case 1:
						strMonth += c;
						break;
					case 2:
						strDay += c;
						break;
					default:
						return null;
				}
			}
			// separator
			else if (c == separator) {
				iSeparatorCount++;
				if (iSeparatorCount > 2) return null;
			}
			// other
			else
				return null;
		}
	}
		
	if (strYear.length == 0 || strMonth.length == 0 || strDay.length == 0)
		return null;

	// convert to integer
	var iYear = parseInt(eval(strYear));
	var iMonth = parseInt(eval(strMonth));
	var iDay = parseInt(eval(strDay));

	if (iYear < 1000 || iYear > 9999) return null;
	if (iMonth < 1 || iMonth > 12) return null;
	if (iDay < 1) return null;

	// determine leap
	var iFebDays = 28;
	if ((iYear % 400 == 0) || ((iYear % 4 == 0) && (iYear % 100 != 0))) iFebDays = 29;

	switch (iMonth) {
		case 2:
			if (iDay > iFebDays) return null;
		case 4: case 6: case 9: case 11:
			if (iDay > 30) return null;
		default:
			if (iDay > 31) return null;
	}
	
	return (new Date(iYear, iMonth, iDay));
}




///////////////////////////////////////////////////////////////////////////////
//
// Following 7 functions are used for trimming string.
//
///////////////////////////////////////////////////////////////////////////////

// Remove characters from the leading of the string. If the string is null or
// empty, no operation did on it.
function trimLeading(str, c) {
	if (str == null || str == "") return str;
	
	var i = 0;
	while (str.charAt(i) == c) i += c.length;
	return str.substring(i);
}

// Remove characters from the tail of the string. If the string is null
// or empty, no operation did on it.
function trimTail(str, c) {
	if (str == null || str == "") return str;

	var i = str.length;
	while (str.charAt(i - 1) == c) i -= c.length;
	return str.substring(0, i);
}

// Remove characters from both leading and tail of the string. If the string
// is null or empty, no operation did on it.
function trimLeadingAndTail(str, c) {
	if (str == null || str == "") return str;

	str = trimLeft(str, c);
	return trimRight(str, c);
}

// Remove the space character (ASCII 0x20) from the leading of the string.
function trimLeadingSpace(str) {
	if (str == null || str == "") return str;
	return trimLeading(str, ' ');
}

// Remove the space character (ASCII 0x20) from the tail of the string.
function trimTailSpace(str) {
	if (str == null || str == "") return str;
	return trimTail(str, ' ');
}

// Remove the space character (ASCII 0x20) from both leading and tail of the string.
function trimSpace(str) {
	if (str == null || str == "") return str;
	str = trimLeading(str, ' ');
	return trimTail(str, ' ');
}

// All white space characters, including horizontal tabs, line feed, carriage return,
// and space (ASCII 0x20), are considered to be removed from leading and tail of the
// string.
function trimWhiteSpace(str) {
	if (str == null || str == "") return str;

	var i = 0;
	while (str.charCodeAt(i) == 0x20 || str.charCodeAt(i) == 0x9
			|| str.charCodeAt(i) == 0xA || str.charCodeAt(i) == 0xD)
		i += 1;
	str = str.substring(i);

	i = str.length;
	while (str.charCodeAt(i - 1) == 0x20 || str.charCodeAt(i - 1) == 0x9
			|| str.charCodeAt(i - 1) == 0xA || str.charCodeAt(i - 1) == 0xD)
		i -= 1;
	return str.substring(0, i);
}




///////////////////////////////////////////////////////////////////////////////
//
// More useful 3 functions.
//
///////////////////////////////////////////////////////////////////////////////

// Determine a string (the first parameter) whether consists of characters in
// another string (the second parameter). It is nessary that the second
// parameter is not null or empty and this function has not checked this.
function isConsistOf(str, chars) {
	for (var i=0; i<str.length; i++)
		if (chars.indexOf(str.charAt(i)) == -1) return false;

	return true;
}

// Replace specified string in a string by another specified string.
function replace(str, find, replace) {
	if (str == null || str == "") return str;
	
	var pos = 0;
	var temp = "";
	
	while (true) {
		pos = str.indexOf(find);
		if (pos != -1) {
			temp = temp + str.substring(0, pos) + replace;
			str = str.substring(pos + find.length);
		}
		else {
			temp += str;
			break;
		}
	}
	
	return temp;
}

// Compare two date objects. Returns 0 if the first Date is equal to the
// second; return a value less than 0 if the first Date is before the second;
// and a value greater than 0 if the first is after the second Date.
function compareDate(dt1, dt2) {
	return (dt1.getTime() - dt2.getTime());
}

//-->



//  输入:  正则表达式,和需要校验的字符串
//  返回:  校验成功返回 true, 错误返回false
function valication(regex,checkdata) {
    var result = new RegExp(regField).test(dateField);
	return result;
}


