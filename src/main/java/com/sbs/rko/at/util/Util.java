package com.sbs.rko.at.util;

import java.math.BigInteger;

public class Util {
	//모든 숫자타입을 int로 바꿔주는 메서드
	public static int getAsInt(Object object) {
		if (object instanceof BigInteger) {
			return ((BigInteger) object).intValue();
		} else if (object instanceof Long) {
			return (int) object;
		} else if (object instanceof Integer) {
			return (int) object;
		} else if (object instanceof String) {
			return Integer.parseInt((String) object);
		}
		return -1;
	}

}