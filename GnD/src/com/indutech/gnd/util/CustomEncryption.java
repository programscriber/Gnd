package com.indutech.gnd.util;

public class CustomEncryption {
	static int x = 10;

	public static String encrypt(String val) {
		String enc = "";
		for (int i = 0; i < val.length(); i++) {
			char c = (char) (((int) val.charAt(i) + x));
			enc = enc + c;
		}
		return enc;
	}
	
	//to re
	public static String decrypt(String val) {
		int maskSize = 0;
		String dec = "";
		maskSize = val.length() / 4;
		for (int i = 0; i < val.length(); i++) {
			char c = (char) (((int) val.charAt(i) - x));
			dec = dec + c;
		}
		return dec;
	}
	
 // print first and last four digit of card number
	public static String decryptMask(String val) {
		int maskSize = 0;
		String dec = "";
		maskSize = val.length() / 4;
		for (int i = 0; i < val.length(); i++) {
			char c = (char) (((int) val.charAt(i) - x));
			dec = dec + c;
		}
		String finalval = dec.substring(0, maskSize)
				+ new String(new char[maskSize * 2]).replace("\0", "x")
				+ dec.substring(maskSize * 3);
		
		return finalval;
	}
	
	// print last four digit of card number
		public static String decryptLast4digit(String val) {
			int maskSize = 0;
			String dec = "";
			maskSize = val.length() / 4;
			for (int i = 0; i < val.length(); i++) {
				char c = (char) (((int) val.charAt(i) - x));
				dec = dec + c;
			}
			String finalval = dec.substring(dec.length()-4, dec.length());
			
			return finalval;
		}
	
	// print only first six  digit of card number
	public static String decryptBin(String val) {
		int maskSize = 0;
		String dec = "";
		maskSize = val.length() / 4;
		for (int i = 0; i < val.length(); i++) {
			char c = (char) (((int) val.charAt(i) - x));
			dec = dec + c;
		}
		String finalval = dec.substring(0, 6);
		
		return finalval;
	}
	
	
	// print first and last six digit of card number
		public static String decryptMaskFirst4Last6(String val) {
			int maskSize = 0;
			String dec = "";
			//maskSize = val.length() / 4;
			for (int i = 0; i < val.length(); i++) {
				char c = (char) (((int) val.charAt(i) - x));
				dec = dec + c;
			}
			String finalval = dec.substring(0, 4)
					+ new String(new char[6]).replace("\0", "x")
					+ dec.substring(val.length()-6,val.length());
			
			return finalval;
		}
		
		public static String decryptMaskFirst4Last4(String val) {
			int maskSize = 0;
			String dec = "";
			//maskSize = val.length() / 4;
			for (int i = 0; i < val.length(); i++) {
				char c = (char) (((int) val.charAt(i) - x));
				dec = dec + c;
			}
			String finalval = dec.substring(0, 4)
					+ new String(new char[8]).replace("\0", "x")
					+ dec.substring(val.length()-4,val.length());
			
			return finalval;
		}
}
