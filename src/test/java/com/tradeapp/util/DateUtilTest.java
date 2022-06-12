package com.tradeapp.util;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class DateUtilTest {

	@Test
	public void testIsOldMaturityDate_futureDate() {
		LocalDate date = LocalDate.of(2090, 1, 10);
		Assertions.assertFalse(DateUtil.isOldMaturityDate(date));
	}

	@Test
	public void testIsOldMaturityDate_previousDate() {
		LocalDate date = LocalDate.of(2020, 1, 10);
		Assertions.assertTrue(DateUtil.isOldMaturityDate(date));
	}

}
