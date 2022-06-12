package com.tradeapp.util;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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
