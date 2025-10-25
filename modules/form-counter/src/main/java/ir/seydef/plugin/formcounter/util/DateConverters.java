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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Hossein Khoshniat
 */
@ProviderType
public interface DateConverters {

	public int getDay();

	public int getMonth();

	public int getYear();

	public void gregorianToPersian(int year, int month, int day);

	public void jalCal(int jY);

	public void jD2Jal(int jDN);

	public void jD2JG(int jD);

	public int jG2JD(int year, int month, int day, int j1G0);

	public String toString();

}