package com.tatademy.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class PasswordGeneratorService {
	private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	private static final String NUMBER = "0123456789";
	private static final String OTHER_CHAR = "!@#$%&*()_+-=[]|,./?><";
	private static final String ALLOWED_CHARS = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
	private static final SecureRandom random = new SecureRandom();

	private static final int MIN_LENGTH = 20;
	private static final int MIN_LOWER = 4;
	private static final int MIN_UPPER = 4;
	private static final int MIN_DIGIT = 4;
	private static final int MIN_SPECIAL = 4;

	public static String generateRandomPassword() {
		StringBuilder sb = new StringBuilder(MIN_LENGTH);

		for (int i = 0; i < MIN_LOWER; i++) {
			sb.append(CHAR_LOWER.charAt(random.nextInt(CHAR_LOWER.length())));
		}
		for (int i = 0; i < MIN_UPPER; i++) {
			sb.append(CHAR_UPPER.charAt(random.nextInt(CHAR_UPPER.length())));
		}
		for (int i = 0; i < MIN_DIGIT; i++) {
			sb.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
		}
		for (int i = 0; i < MIN_SPECIAL; i++) {
			sb.append(OTHER_CHAR.charAt(random.nextInt(OTHER_CHAR.length())));
		}
		for (int i = MIN_LOWER + MIN_UPPER + MIN_DIGIT + MIN_SPECIAL; i < MIN_LENGTH; i++) {
			sb.append(ALLOWED_CHARS.charAt(random.nextInt(ALLOWED_CHARS.length())));
		}

		return shuffleString(sb.toString());
	}

	private static String shuffleString(String input) {
		char[] charArray = input.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			int randomIndex = random.nextInt(charArray.length);
			char temp = charArray[i];
			charArray[i] = charArray[randomIndex];
			charArray[randomIndex] = temp;
		}
		return new String(charArray);
	}
}