/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.util;

import java.net.InetAddress;
import java.security.SecureRandom;

import org.apache.log4j.Logger;

/**
 * <pre>
 * UUID生成器。
 * <pre>
 * @author meihu2007@sina.com
 * 2015年6月4日
 */
public class UUIDGenerator {

	private static final Logger logger = Logger.getLogger(UUIDGenerator.class);

	private static SecureRandom seederStatic;
	private static byte addr[];
	private static String midValueStatic = null;
	private String midValue;
	private SecureRandom seeder;
	private static long prevMillis = 0L;
	private static byte addrBytes[] = null;

	static {
		seederStatic = null;
		addr = null;
		try {
			addr = InetAddress.getLocalHost().getAddress();
			addrBytes = InetAddress.getLocalHost().getAddress();
			StringBuffer buffer = new StringBuffer(8);
			buffer.append(toHex(toInt(addr), 8));
			midValueStatic = buffer.toString();
			seederStatic = new SecureRandom();
			seederStatic.nextInt();
		} catch (Exception ex) {
			logger.error("", ex);
		}
	}

	/**
	 * 构建器。
	 */
	public UUIDGenerator() {
		midValue = null;
		seeder = null;
		StringBuffer buffer = new StringBuffer(16);
		buffer.append(midValueStatic);
		buffer.append(toHex(System.identityHashCode(this), 8));
		midValue = buffer.toString();
		seeder = new SecureRandom();
		seeder.nextInt();
	}

	/**
	 * 创建UUID。
	 * 
	 * @param obj
	 *            Object
	 * 
	 * @return String
	 */
	public static String generate(Object obj) {
		StringBuffer uid = new StringBuffer(32);
		long currentTimeMillis = System.currentTimeMillis();
		uid.append(toHex((int) (currentTimeMillis & -1L), 8));
		uid.append(midValueStatic);
		uid.append(toHex(System.identityHashCode(obj), 8));
		uid.append(toHex(getRandom(), 8));
		return uid.toString();
	}

	/**
	 * 创建UUID。
	 * 
	 * @return String
	 */
	public String generate() {
		StringBuffer uid = new StringBuffer(32);
		long currentTimeMillis = System.currentTimeMillis();
		uid.append(toHex((int) (currentTimeMillis & -1L), 8));
		uid.append(midValue);
		uid.append(toHex(seeder.nextInt(), 8));
		return uid.toString();
	}

	private static String toHex(int value, int length) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		StringBuffer buffer = new StringBuffer(length);
		int shift = length - 1 << 2;
		for (int i = -1; ++i < length;) {
			buffer.append(hexDigits[value >> shift & 0xf]);
			value <<= 4;
		}
		return buffer.toString();
	}

	private static int toInt(byte bytes[]) {
		int value = 0;
		for (int i = -1; ++i < bytes.length;) {
			value <<= 8;
			value |= bytes[i];
		}

		return value;
	}

	private static synchronized int getRandom() {
		return seederStatic.nextInt();
	}

	private static synchronized long getSystemTimeMillis() {
		long millis = System.currentTimeMillis();
		if (millis > prevMillis) {
			prevMillis = millis;
		} else {
			prevMillis++;
		}
		return prevMillis;
	}

	/**
	 * 创建Long类型的UUID。
	 * 
	 * @return Long
	 */
	public static Long getUniqueLong() {
		long l = getSystemTimeMillis();
		l *= 1000L;
		long b1 = addrBytes[3] & 0xff;
		l += b1;
		return Long.valueOf(l);
	}

}