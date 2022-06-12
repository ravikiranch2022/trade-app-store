package com.tradeapp.util;

import java.time.LocalDate;

public class DateUtil {

	public static boolean isOldMaturityDate(LocalDate maturityDate) {
		LocalDate currentDate = LocalDate.now();
		return maturityDate.compareTo(currentDate) < 0;
	}
}
