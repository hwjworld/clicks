/*
 * $Logfile: /cms40/main/src/com/founder/enp/util/StringUtils.java $
 * $Revision: 1 $
 * $Date: 07-07-24 15:36 $
 * $Author: Zhang_lijie $
 * $History: StringUtils.java $
 *
 * *****************  Version 1  *****************
 * User: Zhang_lijie  Date: 07-07-24   Time: 15:36
 * Created in $/cms40/main/src/com/founder/enp/util
 * java主程序
 *
 * *****************  Version 4  *****************
 * User: Liu_wenmin   Date: 07-05-25   Time: 11:43
 * Updated in $/chinadaily/enp/src/com/founder/enp/util
 *
 * *****************  Version 2  *****************
 * User: Liu_dong     Date: 06-08-02   Time: 11:04
 * Updated in $/chinadaily/enp/src/com/founder/enp/util
 * 增加split2List方法
 *
 * *****************  Version 1  *****************
 * User: Liu_dong     Date: 06-07-20   Time: 23:06
 * Created in $/chinadaily/enp/src/com/founder/enp/util
 *
 * Copyright (c) 2006,北大方正电子有限公司数字媒体开发部
 * All rights reserved.
 */
package util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 字符串操作的工具类
 *
 * <p>在Jakarta Commons Lang包中已经包含了StringUtils工具类，
 * 这里是对它的补充。</p>
 *
 * @since 1.0
 *
 * @author Liudong
 * @version 2.0
 * Date:2003-11-2
 */
public class StringUtils
{

	static String[] parsePatterns = {
			"yyyy-MM-dd HH:mm:ss.SSS",
			"yyyy-MM-dd HH:mm:ss",
			"yyyy-MM-dd HH:mm",
			"yyyy-MM-dd HH",
			"yyyy-MM-dd",
			"MM-dd HH:mm",
			"MM-dd",
			"yyyyMMdd HH:mm:ss",
			"yyyyMMdd HH:mm",
			"yyyyMMdd HH",
			"yyyyMMdd",
			"MMdd HH:mm",
			"MMdd",
			"yyyy/MM/dd HH:mm:ss",
			"yyyy/MM/dd HH:mm",
			"yyyy/MM/dd HH",
			"yyyy/MM/dd",
			"MM/dd HH:mm",
			"MM/dd",
			"yyyy年MM月dd日 HH时mm分ss秒",
			"yyyy年MM月dd日 HH:mm:ss",
			"yyyy年MM月dd日 HH:mm",
			"yyyy年MM月dd日 HH",
			"MM月dd日 HH:mm:ss",
			"MM月dd日 HH:mm",
			"MM月dd日"};
	
	/**
	 * 字符串替换函数
	 *
	 * @param line 要进行操作的字符串
	 * @param oldString 要替换掉的字符串
	 * @param newString 准备替换的字符串
	 * @return 完成替换操作的字符串
	 */
	public static final String replace(
		String line,
		String oldString,
		String newString)
	{
		if (line == null)
		{
			return null;
		}
		if (oldString == null || newString == null)
		{
			return line;
		}

		if (oldString.equals(""))
		{
			return line;
		}

		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0)
		{
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0)
			{
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	* Replace all occurences of a with b.
	*
	* @param s original string.
	* @param a string to change.
	* @param b replacement string.
	* @return string with replacements.
	*/
	static public String replace2(String s, String a, String b)
	{
		// If both parts of the replacement are just 1 char
		// use the String method.
		if (a.length() == 1 && b.length() == 1)
		{
			return s.replace(a.charAt(0), b.charAt(0));
		}

		// Otherwise do it the long way.

		int idx;
		int span = a.length();
		int lidx = 0;
		StringBuffer sb = new StringBuffer();

		while ((idx = s.indexOf(a, lidx)) >= 0)
		{
			// Copy where we started to this point.
			sb.append(s.substring(lidx, idx));
			sb.append(b);
			lidx = idx + span;
		}

		if (lidx == 0) // Optimization - most strings won't need replacement.
			return s;

		sb.append(s.substring(lidx));

		return sb.toString();
	}

	/**
	 * Replaces all instances of oldString with newString in line with the
	 * added feature that matches of newString in oldString ignore case.
	 * The count paramater is set to the number of replaces performed.
	 *
	 * @param line the String to search to perform replacements on
	 * @param oldString the String that should be replaced by newString
	 * @param newString the String that will replace all instances of oldString
	 * @param count a value that will be updated with the number of replaces
	 *      performed.
	 *
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replaceIgnoreCase(
		String line,
		String oldString,
		String newString,
		int[] count)
	{
		if (line == null)
		{
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0)
		{
			int counter = 0;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0)
			{
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line.
	 * The count Integer is updated with number of replaces.
	 *
	 * @param line the String to search to perform replacements on
	 * @param oldString the String that should be replaced by newString
	 * @param newString the String that will replace all instances of oldString
	 *
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replace(
		String line,
		String oldString,
		String newString,
		int[] count)
	{
		if (line == null)
		{
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0)
		{
			int counter = 0;
			counter++;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0)
			{
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * 字符串替换函数,忽略大小写
	 *
	 * @param line 要进行操作的字符串
	 * @param oldString 要替换掉的字符串
	 * @param newString 准备替换的字符串
	 *
	 * @return 完成替换操作的字符串
	 */
	public static final String replaceIgnoreCase(
		String line,
		String oldString,
		String newString)
	{
		if (line == null)
		{
			return null;
		}
		if (oldString == null || newString == null)
		{
			return line;
		}

		if (oldString.equals(""))
		{
			return line;
		}

		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0)
		{
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0)
			{
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 判断两个char数组是否完全相等
	 *
	 * @param ch1 第一个char数组
	 * @param ch2 第二个char数组
	 *
	 * @return 是否完全相等
	 */
	public static final boolean charArryEquals(char[] ch1, char[] ch2)
	{
		if (ch1 == null && ch2 == null)
		{
			return true;
		}
		else if (ch1 == null || ch2 == null)
		{
			return false;
		}
		else
		{
			int len1 = ch1.length;
			int len2 = ch2.length;
			if (len1 == len2)
			{
				char c1, c2;
				for (int i = 0; i < len1; i++)
				{
					c1 = ch1[i];
					c2 = ch2[i];
					if (c1 != c2)
					{
						return false;
					}
				}
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	// XML special character
	private static final String QUOT_ENCODE = "&quot;"; // "
	private static final String AMP_ENCODE = "&amp;"; // &
	private static final String LT_ENCODE = "&lt;"; // <
	private static final String GT_ENCODE = "&gt;"; // >

	/**
	 * 去除字符串中的XML标志（如："<>&），使结果可以作为XML中内容
	 *
	 * @param string 要操作的字符串
	 *
	 * @return 去除XML标志的字符串
	 */
	public static final String escapeForXML(String string)
	{
		if (string == null)
		{
			return null;
		}
		char ch;
		char[] input = string.toCharArray();
		int len = input.length;
		if (len == 0)
		{
			return string;
		}
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (int i = 0; i < len; i++)
		{
			ch = input[i];
			switch (ch)
			{
				case '<' :
					out.append(LT_ENCODE);
					break;
				case '>' :
					out.append(GT_ENCODE);
					break;
				case '"' :
					out.append(QUOT_ENCODE);
					break;
				case '&' :
					out.append(AMP_ENCODE);
					break;
				default :
					out.append(ch);
			}
		}
		return out.toString();
	}

	/**
	 * This method takes a string which may contain HTML tags (ie, &lt;b&gt;,
	 * &lt;table&gt;, etc) and converts the '&lt' and '&gt;' characters to
	 * their HTML escape sequences.
	 *
	 * @param in the text to be converted.
	 *
	 * @return the input string with the characters '&lt;' and '&gt;' replaced
	 *  with their HTML escape sequences.
	 */
	public static final String escapeHTMLTags(String in)
	{
		if (in == null)
		{
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++)
		{
			ch = input[i];
			if (ch > '>')
			{
				continue;
			}
			else if (ch == '<')
			{
				if (i > last)
				{
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			}
			else if (ch == '>')
			{
				if (i > last)
				{
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0)
		{
			return in;
		}
		if (i > last)
		{
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	private static final char[] zeroArray =
		"00000000000000000000".toCharArray();

	/**
	 * 将字符串从前面补0,如果输入字符串的长度比指定的长度长则返回原字符串
	 *
	 * @param string 要操作的字符串
	 * @param length 完成补0操作的后的长度，最长不能超过20个字符
	 *
	 * @return 完成补0操作的后的字符串
	 */
	public static final String zeroPadString(String string, int length)
	{
		if (string == null || string.length() > length)
		{
			return string;
		}
		StringBuffer buf = new StringBuffer(length);
		buf.append(zeroArray, 0, length - string.length()).append(string);
		return buf.toString();
	}

	/**
	 * 返回字符串的前几个字符
	 *
	 * @param string 源字符串
	 * @param len 取出字符的个数
	 *
	 * @return 指定的部分字符串
	 */
	public static final String getFirstString(String string, int len)
	{
		if (string == null)
		{
			return null;
		}

		if (string.equals("") || len <= 0)
		{
			return "";
		}

		if (string.length() <= len)
		{
			return string;
		}
		else
		{
			return string.substring(0, len);
		}
	}

	/**
	 * 测试字符串的实际长度 （中文算两个字符）
	 *
	 * @param string 要进行计算的源字符串
	 *
	 * @return 字符串的实际长度
	 */
	public static final int getChineseStringLenght(String string)
	{
		int fLen = 0; //字符串的伪长度
		int tLen = 0; //字符串的真实长度
		int i = 0;

		if (string == null || string.equals(""))
		{
			tLen = 0;
		}
		else
		{
			fLen = string.length();
			for (i = 0; i < fLen; i++)
			{
				if ((int) string.charAt(i) > 255)
				{
					tLen += 2;
				}
				else
				{
					tLen++;
				}
			}
		}
		return tLen;
	}

	/**
	 * 取得字符串的前几个字符 （中文算两个字符）
	 *
	 * @param string 要操作的源字符串
	 * @param len 要取出的字符串的长度
	 *
	 * @return 最终取出的子字符串
	 */
	public static final String getFirstChineseString(String string, int len)
	{
		int fLen = 0; //字符串的伪长度
		int tLen = 0; //字符串的真实长度
		//int i = 0;

		if (getChineseStringLenght(string) < len)
		{
			return string;
		}
		else
		{
			while (tLen < len)
			{
				if ((int) string.charAt(fLen) > 255)
					tLen += 2;
				else
					tLen++;
				if (tLen <= len)
					fLen++;
			}
			return getFirstString(string, fLen);
		}
	}

	/**
	 * 从字符串的右边删除指定的字符串
	 *
	 * <p>
	 * <li>如果 flag 的长度大于 src 的长度返回空字符串("")
	 * <li>如果 src 的右侧有多个 flag 出现，则全部删除
	 * <li>如果 src为 null，则返回 null
	 * <li>如果 flag为 null或空，则不作处理直接返回src
	 * <li>如果 src 为空字符串("")，则返回空字符串("")
	 * </p>
	 *
	 * @param string 源字符串
	 * @param flag 要删除的字符串
	 *
	 * @return 已经删除了指定字符的字符串
	 */
	public static final String rtrim(String string, String flag)
	{
		int lenSrc = 0;
		int lenFlag = 0;
		String tmpStr = null;
		boolean hasNext = true;
		if (string == null)
		{
			return null;
		}
		if (flag == null || flag.equals(""))
		{
			return string;
		}
		if (string.equals(""))
		{
			return "";
		}
		lenSrc = string.length();
		lenFlag = flag.length();
		if (lenFlag > lenSrc)
		{
			return "";
		}
		while (lenSrc >= lenFlag && hasNext)
		{
			tmpStr = string.substring(lenSrc - lenFlag);
			if (tmpStr.equals(flag))
			{
				hasNext = true;
				string = string.substring(0, lenSrc - lenFlag);
				lenSrc = string.length();
			}
			else
			{
				hasNext = false;
			}
		}
		return string;
	}

	/**
	 * 从字符串的左边删除指定的字符串
	 *
	 * <p>
	 * <li>如果 flag 的长度大于 src 的长度返回空字符串("")
	 * <li>如果 src 的左侧有多个 flag 出现，则全部删除
	 * <li>如果 src 为 null，则返回 null
	 * <li>如果 flag 为 null或空，则返回原字符串src
	 * <li>如果 src 为空字符串("")，则返回空字符串("")
	 * </p>
	 *
	 * @param src 源字符串
	 * @param flag 要删除的字符串
	 *
	 * @return 已经删除了指定字符的字符串
	 */
	public static final String ltrim(String src, String flag)
	{
		int lenSrc = 0;
		int lenFlag = 0;
		String tmpStr = null;
		boolean hasNext = true;
		if (src == null)
		{
			return null;
		}
		if (flag == null || flag.equals(""))
		{
			return src;
		}
		if (src.equals(""))
		{
			return "";
		}
		lenSrc = src.length();
		lenFlag = flag.length();
		if (lenFlag > lenSrc)
		{
			return "";
		}
		while (lenSrc >= lenFlag && hasNext)
		{
			tmpStr = src.substring(0, lenFlag);
			if (tmpStr.equals(flag))
			{
				hasNext = true;
				src = src.substring(lenFlag);
				lenSrc = src.length();
			}
			else
			{
				hasNext = false;
			}
		}
		return src;
	}

	/**
	 * 按照指定的标志分割字符串到一个数组中
	 *
	 * @param string 要操作的源字符串
	 * @param flag 分割标志字符串
	 *
	 * @return 分割操作完成后的字符数组
	 */
	public static final String[] split(String string, String flag)
	{
		if (string == null)
		{
			return null;
		}
		if (string.equals(""))
		{
            //如果传入的为空字符串则返回一个长度为0的数组
			return new String[0];
		}
		string = rtrim(string, flag);

		int size = 1;
		int i = 0;
		int len = string.length();
		while (i < len && i != -1)
		{
			i = string.indexOf(flag, i);
			if (i != -1)
			{
				size++;
				i++;
			}
		}

		String[] resultArray = new String[size];
		StringTokenizer tokenizer = new StringTokenizer(string, flag);
		i = 0;
		while (tokenizer.hasMoreTokens())
		{
			resultArray[i] = tokenizer.nextToken();
			i++;
		}
		return resultArray;
	}

	/**
	* Split a line into an array based on white space.
	*
	* @param s  String to split
	*
	* @return array of strings.
	*/
	static public String[] split(String s)
	{
		return split(s, " \t\n\r");
	}

	/**
	* Split a line into an array based on the delimiter.
	*
	* @param s  String to split
	* @param delim  list of delimiters
	* @return array of strings.
	*/
	static public String[] split2(String s, String delim)
	{
		StringTokenizer st = new StringTokenizer(s, delim);
		int count = st.countTokens();

		String r[] = new String[count];

		for (int i = 0; st.hasMoreTokens(); i++)
			r[i] = st.nextToken();

		return r;
	}

    /**
     * 分割字符串，将结果放入List集合中，List容器中仍为String对象
     * @param s 要拆分的字符串
     * @param delim 分割符号
     * @return
     */
    public static List split2List(String s, String delim)
    {
        StringTokenizer st = new StringTokenizer(s, delim);
        int count = st.countTokens();

        List ret = new ArrayList(count);

        for(int i = 0; st.hasMoreTokens(); i++)
            ret.add(st.nextToken());

        return ret;
    }

	/**
	 * 按指定编码方式对字符串进行编码
	 *
	 * @param str 要操作的字符串
	 * @param enc 编码方式
	 *
	 * @return String 编码后的字符串
	 */
	public static final String unescapeString(String str, String enc)
	{
		if (str == null || str.equals(""))
		{
			return str;
		}

		int length = str.length();
		int bytelen = 0;
		byte[] bytes = new byte[length];
		int tempB1, tempB2;
		char tempChar;

		for (int i = 0; i < length; i++)
		{
			tempChar = str.charAt(i);
			if (tempChar == '+')
			{
				//加号必须变为空格
				bytes[bytelen++] = ' ';
				continue;
			}
			if (tempChar == '%')
			{
				if (i + 1 == length)	break;

				tempChar = str.charAt(++i);
				if (tempChar != 'u')
				{
					//国标码
					tempB1 = Character.digit(tempChar, 16);
					tempChar = str.charAt(++i);
					tempB2 = Character.digit(tempChar, 16);
					bytes[bytelen++] = (byte) (tempB1 * 16 + tempB2);
				}
				else
				{
					//UNICODE码
					tempChar = str.charAt(++i);
					tempB1 = Character.digit(tempChar, 16);

					tempChar = str.charAt(++i);
					tempB1 = tempB1 * 16 + Character.digit(tempChar, 16);

					tempChar = str.charAt(++i);
					tempB1 = tempB1 * 16 + Character.digit(tempChar, 16);

					tempChar = str.charAt(++i);
					tempB1 = tempB1 * 16 + Character.digit(tempChar, 16);

					tempChar = (char) tempB1;
					byte[] tempbytes = null;

					try
					{
						tempbytes = ("" + tempChar).getBytes(enc);
					}
					catch (java.io.UnsupportedEncodingException f)
					{
						tempbytes = ("" + tempChar).getBytes();
					}

					bytes[bytelen++] = tempbytes[0];
					bytes[bytelen++] = tempbytes[1];
				}
			}
			else
			{
				bytes[bytelen++] = (byte) tempChar;
			}
		}

		if (bytelen < length)
		{
			byte[] trimValue = new byte[bytelen];
			System.arraycopy(bytes, 0, trimValue, 0, bytelen);
			bytes = trimValue;
		}

		try
		{
			return new String(bytes, enc);
		}
		catch (java.io.UnsupportedEncodingException ex)
		{
			return new String(bytes);
		}
	}

	/**
	 * 使用GBK对字符串进行编码
	 *
	 * @param str 要操作的字符串
	 *
	 * @return String 编码后的字符串
	 */
	public static final String unescapeString(String str)
	{
		return unescapeString(str, "GBK");
	}

	/**
	 * 将byte数组转换成16进制的字符串
	 *
	 * @param ba byte数组
	 *
	 * @return String 16进制的字符串
	 */
	public static final String toHex(byte[] ba)
	{
		if (ba == null)
		{
			return null;
		}

		int length = ba.length;
		if (length <= 0)
		{
			return "";
		}

		StringBuffer buf = new StringBuffer(length * 2);
		int i;

		for (i = 0; i < length; i++)
		{
			if (((int) ba[i] & 0XFF) < 0X10)
			{
				buf.append("0");
			}
			buf.append(Long.toString((int) ba[i] & 0XFF, 16));
		}
		return buf.toString();
	}

	// Hex conversion.
	private static char hex[] =
		{
			'0',
			'1',
			'2',
			'3',
			'4',
			'5',
			'6',
			'7',
			'8',
			'9',
			'A',
			'B',
			'C',
			'D',
			'E',
			'F' };

	/**
	* Convert a byte buffer to a hex string.
	*
	* @param buf Byte array.
	* @return Hexadecimal representation.
	*/
	public static String toHexString(byte[] buf)
	{
		StringBuffer sb = new StringBuffer();
		int buflen = buf.length;
		for (int i = 0; i < buflen; i++)
		{
			sb.append(hex[(buf[i] >>> 4) & 0xf]).append(hex[buf[i] & 0xf]);
		}
		return sb.toString();
	}

	/**
	 * 将16进制的字符串转换成byte数组
	 *
	 * @param hex 16进制字符串
	 *
	 * @return byte[] byte数组
	 */
	public static final byte[] toByteArray(String hex)
	{
		if (hex == null)
		{
			return null;
		}
		int len = hex.length();
		if (len == 0 || (len % 2) > 0)
		{
			return null;
		}
		byte[] result = new byte[len / 2];

		String tmp;
		int i = 0, j = 0;
		while (i < len)
		{
			tmp = hex.substring(i, i + 2);
			result[j] = (byte) Long.parseLong(tmp, 16);
			i += 2;
			j++;
		}
		return result;
	}

	/**
	 * 取字符串左数n个字符
	 *
	 * @param str 字符串
	 * @param len 要取字符个数
	 *
	 * @return String 返回的字符串
	 */
	public static final String left(String str, int len)
	{
		if (str == null || len <= 0)
		{
			return null;
		}
		if (str.equals("") || str.length() <= len)
		{
			return str;
		}
		else
		{
			return str.substring(0, len);
		}
	}

	/**
	 * 取字符串右数n个字符
	 *
	 * @param str 字符串
	 * @param len 要取字符个数
	 *
	 * @return String 返回子串
	 */
	public static final String right(String str, int len)
	{
		if (str == null || len <= 0)
		{
			return null;
		}
		if (str.equals("") || str.length() <= len)
		{
			return str;
		}
		else
		{
			return str.substring(len - 1, str.length());
		}
	}

	/**
	 * 取字符串中间的字符
	 *
	 * @param str 字符串
	 * @param start 起始位置
	 * @param len 字符个数
	 *
	 * @return String 返回字符
	 */
	public static final String mid(String str, int start, int len)
	{
		if (str == null || start <= 0 || len <= 0)
		{
			return null;
		}
		if (str.equals("") || str.length() <= start)
		{
			return "";
		}
		else if (str.length() < (start + len))
		{
			return str.substring(start - 1, str.length());
		}
		else
		{
			return str.substring(start - 1, start + len - 1);
		}
	}

	/**
	 * 判断两个字符串是否相等(如果都是null也认为是相等的)
	 *
	 * @param s1 第一个字符串
	 * @param s2 第二个字符串
	 *
	 * @return 两个字符串相等返回true,否则返回false
	 */
	public static final boolean isEquals(String s1, String s2)
	{
		if (s1 == null && s2 == null)
		{
			return true;
		}
		if ((s1 == null && s2 != null) || (s1 != null && s2 == null))
		{
			return false;
		}
		if (!s1.equals(s2))
		{
			return false;
		}

		return true;
	}

	/**
	 * 判断两个字符串数组是否相等
	 *
	 * @param s1 第一个字符串数组
	 * @param s2 第二个字符串数组
	 *
	 * @return 是否相等
	 */
	public static boolean isEquals(String[] s1, String[] s2)
	{
		if (s1 == null && s2 == null)
		{
			return true;
		}
		if ((s1 == null && s2 != null) || (s1 != null && s2 == null))
		{
			return false;
		}
		int len1 = s1.length;
		int len2 = s2.length;
		if (len1 != len2)
		{
			return false;
		}
		for (int i = 0; i < len1; i++)
		{
			if (!s1[i].equals(s2[i]))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Capitalizes the first character of the given string.
	 * @param s the String to capitalize
	 * @return the capitalized result
	 */
	public static String capitalize(String s)
	{
		if (s.length() == 0)
			return s;
		char c = Character.toUpperCase(s.charAt(0));
		return c + s.substring(1, s.length());
	}

	/**
	 * Parses a comma-separated list into an array of Strings
	 * Values can contain whitespace, but whitespace at the beginning and
	 * end of each value is trimmed.
	 * @return array of Strings
	 * @param csvList a string of comma seperated values
	 */
	public static String[] parseCommaDelimitedList(String csvList)
	{
		String[] result = parseList(csvList, ",");
		for (int i = 0; i < result.length; i++)
		{
			result[i] = result[i].trim();
		}
		return result;
	}

	/**
	 * Parses a whitepsace-separated list into an array of Strings
	 * @return array of Strings
	 * @param wsvList a string of white space seperated values
	 */
	public static String[] parseWhitespaceDelimitedList(String wsvList)
	{
		return parseList(wsvList, "\t ");
	}

	/**
	 * Parses a list according to the specified delimiter into an
	 * array of Strings.
	 * @see java.util.StringTokenizer
	 * @param list a string of token seperated values
	 * @param delim the delimiter character(s).  Each character in the string is a
	 * single delimeter.
	 * @return an array of strings
	 */
	public static String[] parseList(String list, String delim)
	{
		List result = new ArrayList();
		StringTokenizer tokenizer = new StringTokenizer(list, delim);
		while (tokenizer.hasMoreTokens())
		{
			result.add(tokenizer.nextToken());
		}
		return (String[]) result.toArray(new String[0]);
	}

	/**
	 * <p>Removes specified chars from a string.</p>
	 *
	 * @param aString the string that will be examined to remove chars
	 * @param unWantedCharArray the char array containing the chars
	 * that should be removed from a string
	 * @return the string after removing the specified chars
	 */
	public static String removeCharsFromString(
		String aString,
		char[] unWantedCharArray)
	{

		Character character = null;

		// Store unwanted chars in a hashset
		Set unWantedCharSet = new HashSet();

		for (int i = 0; i < unWantedCharArray.length; i++)
		{
			character = new Character(unWantedCharArray[i]);
			unWantedCharSet.add(character);
		}

		// Create result String buffer
		StringBuffer result = new StringBuffer(aString.length());

		// For each character in aString, append it to the result string buffer
		// if it is not in unWantedCharSet
		for (int i = 0; i < aString.length(); i++)
		{
			character = new Character(aString.charAt(i));
			if (!unWantedCharSet.contains(character))
			{
				result.append(aString.charAt(i));
			}
		}

		// Return result
		return result.toString();
	}

	/**
	 * <p> Converts a string to a Set. Breaks the string to characters and store
	 * each character in a Set.
	 *
	 * @param string the string to convert
	 * @return a <code>Set</code> containing all characters in the text string parameter
	 */
	public static Set convertToSet(String string)
	{

		// Result hashset
		Set resultSet = new HashSet();

		for (int i = 0; i < string.length(); i++)
		{
			resultSet.add(new Character(string.charAt(i)));
		}

		// Return result
		return resultSet;
	}

	/**
	 * <p> Converts a char array to a Set. Puts all characters in the array to a Set.
	 *
	 * @param charArray an array of <CODE>char</CODE>s
	 * @return a <code>set</code> containing all characters in the char array
	 */
	public static Set convertToSet(char[] charArray)
	{

		// Result hashset
		Set resultSet = new HashSet();

		for (int i = 0; i < charArray.length; i++)
		{
			resultSet.add(new Character(charArray[i]));
		}

		// Return result
		return resultSet;
	}

    /**
    * Dump out the current call stack to stdout.
    */
    public static void dumpStack()
    {
        java.io.CharArrayWriter ca = new java.io.CharArrayWriter();
        java.io.PrintWriter pwca = new java.io.PrintWriter(ca);

        (new Throwable()).printStackTrace(pwca);

        System.out.println(ca.toString() + "\n");
    }

    /**
    * Format a number to the given width, right justfied, blank filled.
    *
    * @param n Number to format.
    * @param width Width of spaces + number.
    * @return formatted number.
    */
    public static String format(int n, int width)
    {
        StringBuffer num = new StringBuffer(Integer.toString(n));

        int spaces = width - num.length();
        for (int i = 0; i < spaces; i++)
            num.insert(0, ' ');

        return num.toString();
    }

    /**
    * Format a number to the given width, right justfied, blank filled.
    *
    * @param n Number to format.
    * @param width Width of spaces + number.
    * @return formatted number.
    */
    public static String format(long n, int width)
    {
        StringBuffer num = new StringBuffer(Long.toString(n));

        int spaces = width - num.length();
        for (int i = 0; i < spaces; i++)
            num.insert(0, ' ');

        return num.toString();
    }

    /**得到一个长度小于n的随机字符串
     * add by guoqx
     * @param n
     * @return
     */
    public static String randomStr(Random r,int n){
        int i=0;
        String str="";
        String s="";
        int nn = r.nextInt(n);
        if(nn==0)
        	nn=1;
        while(i<nn)
        {
                switch(r.nextInt(76))
                {
                        case(0):s="0";break;
                        case(1):s="1";break;
                        case(2):s="2";break;
                        case(3):s="3";break;
                        case(4):s="4";break;
                        case(5):s="5";break;
                        case(6):s="6";break;
                        case(7):s="7";break;
                        case(8):s="8";break;
                        case(9):s="9";break;
                        case(10):s="a";break;
                        case(11):s="b";break;
                        case(12):s="c";break;
                        case(13):s="d";break;
                        case(14):s="e";break;
                        case(15):s="f";break;
                        case(16):s="g";break;
                        case(17):s="h";break;
                        case(18):s="i";break;
                        case(19):s="j";break;
                        case(20):s="k";break;
                        case(21):s="m";break;
                        case(23):s="n";break;
                        case(24):s="o";break;
                        case(25):s="p";break;
                        case(26):s="q";break;
                        case(27):s="r";break;
                        case(28):s="s";break;
                        case(29):s="t";break;
                        case(30):s="u";break;
                        case(31):s="v";break;
                        case(32):s="w";break;
                        case(33):s="l";break;
                        case(34):s="x";break;
                        case(35):s="y";break;
                        case(36):s="z";break;
                        case(37):s="A";break;
                        case(38):s="B";break;
                        case(39):s="C";break;
                        case(40):s="D";break;
                        case(41):s="E";break;
                        case(42):s="F";break;
                        case(43):s="G";break;
                        case(44):s="H";break;
                        case(45):s="I";break;
                        case(46):s="L";break;
                        case(47):s="J";break;
                        case(48):s="K";break;
                        case(49):s="M";break;
                        case(50):s="N";break;
                        case(51):s="O";break;
                        case(52):s="P";break;
                        case(53):s="Q";break;
                        case(54):s="R";break;
                        case(55):s="S";break;
                        case(56):s="T";break;
                        case(57):s="U";break;
                        case(58):s="V";break;
                        case(59):s="W";break;
                        case(60):s="X";break;
                        case(61):s="Y";break;
                        case(62):s="Z";break;
                        case(63):s="!";break;
                        case(64):s="@";break;
                        case(65):s="#";break;
                        case(66):s="$";break;
                        case(67):s="%";break;
                        case(68):s="^";break;
                        case(69):s="&";break;
                        case(70):s="*";break;
                        case(71):s="(";break;
                        case(72):s=")";break;
                        case(73):s="-";break;
                        case(74):s="+";break;
                        case(75):s=" ";break;
                }
                i++;
                str=s+str;
        }
        return str;
    }




    public static Date str2Date(String dateStr){
      Date dResult = null;
      try{
        dResult = new Date(Str2Calendar(dateStr,Locale.CHINESE).getTime().getTime());
      }catch(Exception ex){
      }
      return dResult;
    }

    //把日期字符串转换为Calendar对象
    public static Calendar Str2Calendar(String sDateStr,Locale alocale){
      Calendar cal = null;

      cal = Calendar.getInstance(alocale);

      int[] datePart = splitDateStr(sDateStr);
      cal.set(datePart[0],datePart[1]-1,datePart[2],datePart[3],datePart[4],datePart[5]);
      return cal;
    }

    //把时间字符串内的各个部分拆出来放到int[]中,
    //顺序为 年月日时分秒
    private static int[] splitDateStr(String strDate){
      StringTokenizer st = null;

      //把类似这样的日期中:2005-11-02 13:23:05.0 的 . 后面的微秒数去掉
      if(strDate.indexOf(".")>0)
        strDate =strDate.substring(0,strDate.indexOf("."));

      int[] lResult = {0,1,0,0,0,0};
      int iPos = 0;
      String sdate = "", stime = "";
      if(strDate.indexOf(" ")>0){
        sdate = strDate.substring(0,strDate.indexOf(" "));
        if(strDate.indexOf(":")<0)
          stime = sdate;
        else stime = strDate.substring(strDate.indexOf(" ")+1);
      }else{
        if(strDate.indexOf(":")>=0)
          stime = strDate;
        else sdate = strDate;
      }
      String currPart = "";
      st = new StringTokenizer(sdate,"-");
      while(st.hasMoreTokens()){
        currPart = st.nextToken();
        if(iPos==0)
          lResult[iPos] = getInt(currPart);
        else if(iPos==1)
          lResult[iPos] = getInt(currPart);
        else if(iPos==2)
          lResult[iPos] = getInt(currPart);
        iPos++;
      }
      iPos = 3;
      if(stime.trim().length()>0){
        st = new StringTokenizer(stime, ":");
        while (st.hasMoreTokens()) {
          currPart = st.nextToken();
          if(iPos==3)
            lResult[iPos] = getInt(currPart);
          else if(iPos==4)
            lResult[iPos] = getInt(currPart);
          else if(iPos==5)
            lResult[iPos] = getInt(currPart);
          iPos++;
        }
      }
      return lResult;
    }

    public static void main(String[] args) {
    	StringBuilder sb = new StringBuilder("233ab");
    	deleteLastChat(sb);
    	System.out.print(sb);
	}
    

	/**
	 * 是否为空，“”也算空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str)
	{
		if(str==null||str.equals(""))
		{
			return true;
		}
		return false;
	}
	/**
	 * 加入是否Trim的选择
	 * @param str
	 * @param isTrim
	 * @return
	 */
	public static boolean isNull(String str,boolean isTrim)
	{
		if(!isTrim)
			return isNull(str);
		else
		{
			if(str==null||str.trim().equals(""))
			{
				return true;
			}
			return false;
		}
	}

	public static void deleteLastChat(StringBuilder str){
		str.deleteCharAt(str.length()-1);
	}

	/**
	 * 获取字符串中的int值,
	 * 如果在转换过程中出现任何错误则返回缺省值
	 *
	 * @param value 输入的字符串
	 * @param defaultValue 缺省值
	 * @return 对应的int值
	 */
	public static int getInt(String value)
	{
		int result = 0;
		try
		{
			result = Integer.parseInt(value);
		}
		catch (Exception ex)
		{
		}
		return result;
	}
}
