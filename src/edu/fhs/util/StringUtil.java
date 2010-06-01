/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.util;

/**
 *
 * @author Adam
 */
public class StringUtil {



	public static String toFixedMessage(String label, String message) {
		char[] charMessage = message.toCharArray();
		char[] labelArray = label.toCharArray();
		int maxLength = 21;
		int used = 0;
		char[] fixedMessage = new char[maxLength];
		for (int x = 0; x < labelArray.length && used < maxLength; x++) {
			fixedMessage[used++] = labelArray[x];
		}
		for (int x = 0; x < charMessage.length && used < maxLength; x++) {
			fixedMessage[used++] = charMessage[x];
		}
		for (; used < fixedMessage.length; used++) {
			fixedMessage[used] = ' ';
		}
		String fixedLengthMessage = new String(fixedMessage);
		return fixedLengthMessage;
	}
}