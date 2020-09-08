package pw.cdmi.utils;

import java.util.Random;

/****************************************************
 * 随机数工具。可以随机的生成各种长度的字符串
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 28, 2014
 ***************************************************/
public class RandomUtils {
	
	private static Random randGen = new Random();
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
			+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	private static char[] numbers = ("0123456789").toCharArray();

	/**
	 * 随机产生一定字符长度的字符串，字符串由数字和大小写拼音组成
	 * @param length 要生成的字符串长度
	 * @return 一个字符串
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(61)];
		}
		return new String(randBuffer);
	}
	
	/**
	 * 随机产生一定字符长度的字符串，字符串由数字音组成
	 * @param length 要生成的字符串长度
	 * @return 一个字符串
	 */
	public static final String randomNumber(int length) {
		if (length < 1) {
			return null;
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbers[randGen.nextInt(9)];
		}
		return new String(randBuffer);
	}
}
