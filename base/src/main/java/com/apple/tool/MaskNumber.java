package com.apple.tool;

import com.apple.encrypt.DesUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HUANG on 14/8/13. 用于隐藏用户电话号码以及身份证号等敏感信息
 */
public class MaskNumber {
	/**
	 * Mask iD card number.
	 *
	 * @param s
	 *            the ID card number
	 * @return the the ID card number after masking
	 */
	public static String maskIDCardNumber(String s) {
		try {
			String decyIDCardNumber= DesUtil.decrypt(s);
			String str = "";
			for (int i = 0; i < 4; i++) {
				str += decyIDCardNumber.substring(i, i + 1);
			}
			for (int i = 4; i < decyIDCardNumber.length(); i++) {
				str += "*";
			}
			for (int i = decyIDCardNumber.length() - 4; i < decyIDCardNumber.length(); i++) {
				str += decyIDCardNumber.substring(i, i + 1);
			}
			return str;
		}catch (Exception e){
			e.printStackTrace();
			return "";
		}


	}

	/**
	 * Mask bank card number.
	 *
	 * @param s
	 *            the bank card number
	 * @return the bank card number after masking
	 */
	public static String maskBankCardNumber(String s) {
		try {
			String decyBankCardNumber=DesUtil.decrypt(s);
			int l = decyBankCardNumber.length();
			int i = l - 4;// 截取最后四个字符；
			return "尾号" + decyBankCardNumber.substring(i, l);
		}catch (Exception e ){
			e.printStackTrace();
		}
		return  "";
	}


	public static String maskBankCardNumber2(String s) {
        String decyBankCardNumber=DesUtil.decrypt(s);

		int l = decyBankCardNumber.length();

		int i = l - 4;// 截取最后四个字符；

		return "****" + decyBankCardNumber.substring(i, l);
	}

	/**
	 * Mask phone number.
	 *
	 * @param s
	 *            the phone number
	 * @return the phone number after masking
	 */
	public static String maskPhoneNumber(String s) {
		try {
			if(s.contains("*")){
				return s;
			}
			String decyPhoneNumber=DesUtil.decrypt(s);
			Pattern p = Pattern.compile("^([0-9]{3})([0-9]{4})([0-9]{4})$");
			Matcher m = p.matcher(decyPhoneNumber);
			String str = "";
			while (m.find()) {
				str = m.group(1) + "****" + m.group(3);// 替换中间四位
			}
			return str;
		}catch (Exception e){
			e.printStackTrace();
			return "";
		}

	}

	public static String subStringPhoneNumber(String s) {
        String decyPhoneNumber=DesUtil.decrypt(s);
		return decyPhoneNumber.substring(decyPhoneNumber.length() - 4, decyPhoneNumber.length());
	}

	public static String maskName(String s) {
		try {
			String decyName=DesUtil.decrypt(s);
			if (decyName.length() > 0) {
				String newS = decyName.substring(0, 1);
				for (int i = 1; i < decyName.length(); i++) {
					newS += "*";
				}
				return newS;
			} else {
				return decyName;
			}
		}catch (Exception e){
			e.printStackTrace();
			return "";
		}

	}
	public static String maskNameCrad(String s) {
        String decyCrad=DesUtil.decrypt(s);
		if (decyCrad.length() > 0) {
			String newS = decyCrad.substring(0, 2);
			for (int i = 1; i < decyCrad.length()-3; i++) {
				newS += "*";
			}
			String newS1 = decyCrad.substring(decyCrad.length()-2,decyCrad.length());
			newS+=newS1;
			return newS;
		} else {
			return decyCrad;
		}
	}

    public static void main(String[] args) {
        System.out.println(maskBankCardNumber("62302****1660897"));

    }
}
