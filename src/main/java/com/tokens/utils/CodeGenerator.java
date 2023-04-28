package com.tokens.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CodeGenerator {

	private static final String HASH_ALGORITHM = "SHA-256";
	private static final int RANDOM_BYTES = 16;

	public static String generateHashCode(String masterKey) {
		SecureRandom random = new SecureRandom();
		byte[] randomBytes = new byte[RANDOM_BYTES];
		random.nextBytes(randomBytes);
		long timestamp = System.currentTimeMillis() + 5 * 60 * 1000;
		String input = masterKey + new String(randomBytes)+Long.toString(timestamp);
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			byte[] hashBytes = digest.digest(input.getBytes());
			return bytesToHexString(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}
