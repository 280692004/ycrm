/**
 * kyle
 * 处理四舍五入问题
 * @param 被处理数
 * @param 精度
 */
function round(number,fractionDigits){  
    with(Math){  
        return round(number*pow(10,fractionDigits))/pow(10,fractionDigits);  
    }  
}

/**
 * 用途: 去字符串的右边空格
 * 作者姓名:johnzhou
 * 制作日期:2015-06-17
 */ 
function trimRight(s){  
    if(s == null) return "";  
    var whitespace = new String(" \t\n\r");  
    var str = new String(s);  
    if (whitespace.indexOf(str.charAt(str.length-1)) != -1){  
        var i = str.length - 1;  
        while (i >= 0 && whitespace.indexOf(str.charAt(i)) != -1){  
           i--;  
        }  
        str = str.substring(0, i+1);  
    }  
    return str;  
}   

/**
 * 用途: 处理科学计数法
 * 作者姓名:kyle
 * 制作日期:2015-06-30
 */ 
function formatSalePrice(val){
	var num = new Number(val);
	return num;
}


/**
 * kyle
 * 处理new Date在IE8不兼容问题
 * @param dateStringInRange
 * @returns {Date}
 */
function parseDateForleIE8(dateStringInRange) {  
    var isoExp = /^\s*(\d{4})-(\d\d)-(\d\d)\s*$/, date = new Date(NaN), month, parts = isoExp.exec(dateStringInRange);  
    if(parts) {  
        month = +parts[2];  
        date.setFullYear(parts[1], month - 1, parts[3]);  
        if(month != date.getMonth() + 1) {  
            date.setTime(NaN);  
        }  
    }  
    return date;  
} 