/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package ir.seydef.plugin.formcounter.util;


import com.liferay.portal.kernel.util.Validator;
import ir.seydef.plugin.formcounter.constants.FormCounterPersianTextConstants;

/**
 * @author S.Abolfazl Eftekhari
 */
public class PersianTextUtil {

    public static String normalize(String text) {
        if ((text == null) || text.isEmpty()) {
            return text;
        }

        text = text.toLowerCase();

        text = _normalizeSpaces(text);

        text = _convertPersianNumbers(text);

        text = _standardizeCharacters(text);

        text = _removeDiacritics(text);

        return text.trim();
    }

    private static String _convertPersianNumbers(String text) {
        StringBuilder result = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if ((ch >= FormCounterPersianTextConstants.PERSIAN_ZERO) &&
                    (ch <= FormCounterPersianTextConstants.PERSIAN_NINE)) {

                result.append(
                        (char) ('0' + (ch - FormCounterPersianTextConstants.PERSIAN_ZERO)));
            } else if ((ch >= FormCounterPersianTextConstants.ARABIC_ZERO) &&
                    (ch <= FormCounterPersianTextConstants.ARABIC_NINE)) {

                result.append(
                        (char) ('0' + (ch - FormCounterPersianTextConstants.ARABIC_ZERO)));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    private static String _normalizeSpaces(String text) {
        text = text.replaceAll(
                FormCounterPersianTextConstants.SPACES_AND_ZWNJ_PATTERN, " ");

        text = text.replaceAll(
                FormCounterPersianTextConstants.PERSIAN_PREFIXES_PATTERN,
                FormCounterPersianTextConstants.PREFIX_REPLACEMENT);

        text = text.replaceAll(
                FormCounterPersianTextConstants.PERSIAN_PLURAL_PATTERN,
                FormCounterPersianTextConstants.COMPARATIVE_REPLACEMENT);

        return text;
    }

    private static String _removeDiacritics(String text) {
        return text.replaceAll(FormCounterPersianTextConstants.DIACRITICS_PATTERN, "");
    }

    private static String _standardizeCharacters(String text) {
        text = text.replace(
                FormCounterPersianTextConstants.ARABIC_KAF,
                FormCounterPersianTextConstants.PERSIAN_KAF);

        text = text.replace(
                FormCounterPersianTextConstants.ARABIC_YEH,
                FormCounterPersianTextConstants.PERSIAN_YEH);

        text = text.replace(
                FormCounterPersianTextConstants.ALEF_WITH_HAMZA_ABOVE,
                FormCounterPersianTextConstants.ALEF);
        text = text.replace(
                FormCounterPersianTextConstants.ALEF_WITH_HAMZA_BELOW,
                FormCounterPersianTextConstants.ALEF);
        text = text.replace(
                FormCounterPersianTextConstants.ALEF_WITH_MADDA_ABOVE,
                FormCounterPersianTextConstants.ALEF);

        text = text.replace(
                FormCounterPersianTextConstants.TEH_MARBUTA, FormCounterPersianTextConstants.HEH);

        text = text.replace(
                FormCounterPersianTextConstants.WAW_WITH_HAMZA_ABOVE,
                FormCounterPersianTextConstants.WAW);

        text = text.replace(
                FormCounterPersianTextConstants.YEH_WITH_HAMZA_ABOVE,
                FormCounterPersianTextConstants.PERSIAN_YEH);

        return text;
    }

    public static boolean contains(String text, String searchTerm) {
        if (Validator.isNull(searchTerm)) {
            return true;
        }

        if (Validator.isNull(text)) {
            return false;
        }

        return normalize(text).contains(normalize(searchTerm));
    }

}