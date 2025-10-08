/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.constants;

/**
 * @author S.Abolfazl Eftekhari
 */
public class FormCounterPersianTextConstants {

    public static final char ALEF = '\u0627';

    public static final char ALEF_WITH_HAMZA_ABOVE = '\u0623';

    public static final char ALEF_WITH_HAMZA_BELOW = '\u0625';

    public static final char ALEF_WITH_MADDA_ABOVE = '\u0622';

    public static final char ARABIC_KAF = '\u0643';

    public static final char ARABIC_NINE = '\u0669';

    public static final char ARABIC_YEH = '\u064A';

    public static final char ARABIC_ZERO = '\u0660';

    public static final String COMPARATIVE_REPLACEMENT =
            "$1\u200C$3$4$5";

    public static final String DIACRITICS_PATTERN = "[\\u064B-\\u0652]";

    public static final char HEH = '\u0647';

    public static final char PERSIAN_KAF = '\u06A9';

    public static final char PERSIAN_NINE = '\u06F9';

    public static final String PERSIAN_PLURAL_PATTERN =
            "(\\S)( ?)(ها)(ی?)(\\s|$)";

    public static final String PERSIAN_PREFIXES_PATTERN = "(می|بی|نمی) ";

    public static final char PERSIAN_YEH = '\u06CC';

    public static final char PERSIAN_ZERO = '\u06F0';

    public static final String PREFIX_REPLACEMENT =
            "$1\u200C";

    public static final String SPACES_AND_ZWNJ_PATTERN = "[ \\u200C]+";

    public static final char TEH_MARBUTA = '\u0629';

    public static final char WAW = '\u0648';

    public static final char WAW_WITH_HAMZA_ABOVE = '\u0624';

    public static final char YEH_WITH_HAMZA_ABOVE = '\u0626';

}
