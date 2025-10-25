/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package ir.seydef.plugin.formcounter.util;

import com.liferay.portal.kernel.util.GetterUtil;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Hossein Khoshniat
 */
@Component(immediate = true, service = DateConverters.class)
public class DateConverterImpl implements DateConverters {

	public String getDateTimeFromGregorianToPersian(Date date) {
		DateConverterImpl dateConverterImpl = new DateConverterImpl();

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

		String strDateMiladi = dateFormatter.format(date);
		String strTime = timeFormatter.format(date);

		String[] strings = strDateMiladi.split("/");

		dateConverterImpl.gregorianToPersian(
			GetterUtil.getInteger(strings[2]),
			GetterUtil.getInteger(strings[1]),
			GetterUtil.getInteger(strings[0]));

		return dateConverterImpl.getYear() + "/" +
			dateConverterImpl.getMonth() + "/" + dateConverterImpl.getDay() +
				" " + strTime;
	}

	/**
	 * Get manipulated day
	 *
	 * @return Day as int
	 */
	public int getDay() {
		return _day;
	}

	/**
	 * Get manipulated month
	 *
	 * @return Month as int
	 */
	@Override
	public int getMonth() {
		return _month;
	}

	/**
	 * Get manipulated year
	 *
	 * @return Year as int
	 */
	@Override
	public int getYear() {
		return _year;
	}

	/**
	 * Converts Gregorian date to Persian(Jalali) date
	 *
	 * @param year  int
	 * @param month int
	 * @param day   int
	 */
	@Override
	public void gregorianToPersian(int year, int month, int day) {
		int jd = jG2JD(year, month, day, 0);

		jD2Jal(jd);

		_year = _jYear;

		_month = _jMonth;

		_day = _jDay;
	}

	/**
	 * This procedure determines if the Jalali (Persian) year is _leap (366-day
	 * <p>
	 * long) or is the common year (365 days), and finds the day in March
	 * <p>
	 * (Gregorian calendar) of the first day of the Jalali year (_jYear)
	 *
	 * @param jY Jalali calendar year (-61 to 3177)
	 */
	@Override
	public void jalCal(int jY) {
		_march = 0;
		_leap = 0;

		int[] breaks = {
			-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210, 1635, 2060,
			2097, 2192, 2262, 2324, 2394, 2456, 3178
		};

		_gYear = jY + 621;

		int leapJ = -14;

		int jp = breaks[0];

		int jump;

		for (int j = 1; j <= 19; j++) {
			int jm = breaks[j];

			jump = jm - jp;

			if (jY < jm) {
				int n = jY - jp;

				leapJ = leapJ + (n / 33 * 8) + (((n % 33) + 3) / 4);

				if (((jump % 33) == 4) && ((jump - n) == 4)) {
					leapJ = leapJ + 1;
				}

				int leapG = (_gYear / 4) - (((_gYear / 100) + 1) * 3 / 4) - 150;

				_march = 20 + leapJ - leapG;

				if ((jump - n) < 6) {
					n = n - jump + ((jump + 4) / 33 * 33);
				}

				_leap = (((n + 1) % 33) - 1) % 4;

				if (_leap == -1) {
					_leap = 4;
				}

				break;
			}

			leapJ = leapJ + (jump / 33 * 8) + ((jump % 33) / 4);
			jp = jm;
		}
	}

	/**
	 * Converts the Julian Day number to a date in the Jalali calendar
	 *
	 * @param jDN the Julian Day number
	 */
	@Override
	public void jD2Jal(int jDN) {
		jD2JG(jDN);

		_jYear = _gYear - 621;

		jalCal(_jYear);

		int jDN1F = jG2JD(_gYear, 3, _march, 0);

		int k = jDN - jDN1F;

		if (k >= 0) {
			if (k <= 185) {
				_jMonth = 1 + (k / 31);

				_jDay = (k % 31) + 1;

				return;
			}

			k = k - 186;
		}
		else {
			_jYear = _jYear - 1;

			k = k + 179;

			if (_leap == 1) {
				k = k + 1;
			}
		}

		_jMonth = 7 + (k / 30);

		_jDay = (k % 30) + 1;
	}

	/**
	 * Calculates Gregorian and Julian calendar dates from the Julian Day number
	 * <p>
	 * (JD) for the period since JD=-34839655 (i.e. the year -100100 of both the
	 * <p>
	 * calendars) to some millions (10**6) years ahead of the present. The
	 * <p>
	 * algorithm is based on D.A. Hatcher, Q.Jl.R.Astron.Soc. 25(1984), 53-55
	 * <p>
	 * slightly modified by me (K.M. Borkowski, Post.Astron. 25(1987), 275-279).
	 *
	 * @param jD Julian day number as int
	 */
	@Override
	public void jD2JG(int jD) {
		int j = (4 * jD) + 139361631;

		j = j + (((4 * jD) + 183187720) / 146097 * 3 / 4 * 4) - 3908;

		int i = ((j % 1461) / 4 * 5) + 308;

		_gDay = ((i % 153) / 5) + 1;

		_gMonth = ((i / 153) % 12) + 1;

		_gYear = (j / 1461) - 100100 + ((8 - _gMonth) / 6);
	}

	/**
	 * Calculates the Julian Day number (_jG2JD) from Gregorian or Julian
	 * <p>
	 * calendar dates. This integer number corresponds to the noon of the date
	 * <p>
	 * (i.e. 12 hours of Universal Time). The procedure was tested to be good
	 * <p>
	 * since 1 March, -100100 (of both the calendars) up to a few millions
	 * <p>
	 * (10**6) years into the future. The algorithm is based on D.A. Hatcher,
	 * <p>
	 * Q.Jl.R.Astron.Soc. 25(1984), 53-55 slightly modified by me (K.M.
	 * <p>
	 * Borkowski, Post.Astron. 25(1987), 275-279).
	 *
	 * @param year  int
	 * @param month int
	 * @param day   int
	 * @param j1G0  to be set to 1 for Julian and to 0 for Gregorian calendar
	 * @return Julian Day number
	 */
	@Override
	public int jG2JD(int year, int month, int day, int j1G0) {
		int jd =
			((1461 * (year + 4800 + ((month - 14) / 12))) / 4) +
				((367 * (month - 2 - (12 * ((month - 14) / 12)))) / 12) -
					((3 * ((year + 4900 + ((month - 14) / 12)) / 100)) / 4) +
						day - 32075;

		if (j1G0 == 0) {
			jd = jd - ((year + 100100 + ((month - 8) / 6)) / 100 * 3 / 4) + 752;
		}

		return jd;
	}

	/**
	 * Modified toString() method that represents date string
	 *
	 * @return Date as String
	 */
	@Override
	public String toString() {
		return String.format("%04d-%02d-%02d", getYear(), getMonth(), getDay());
	}

	private int _day;
	private int _gDay;
	private int _gMonth;
	private int _gYear;
	private int _jDay;
	private int _jMonth;
	private int _jYear;
	private int _leap;
	private int _march;
	private int _month;
	private int _year;

}