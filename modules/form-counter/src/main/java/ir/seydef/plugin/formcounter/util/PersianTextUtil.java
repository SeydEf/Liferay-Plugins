package ir.seydef.plugin.formcounter.util;

import com.liferay.portal.kernel.util.Validator;

import java.text.Normalizer;

/**
 * @author S.Abolfazl Eftekhari
 */
public class PersianTextUtil {

    public static String normalize(String text) {
        if (Validator.isNull(text)) {
            return "";
        }

        String normalized = text.trim().toLowerCase();

        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFKC);

        normalized = normalized
            .replace('ي', 'ی')
            .replace('ك', 'ک')
            .replace('٠', '۰')
            .replace('١', '۱')
            .replace('٢', '۲')
            .replace('٣', '۳')
            .replace('٤', '۴')
            .replace('٥', '۵')
            .replace('٦', '۶')
            .replace('٧', '۷')
            .replace('٨', '۸')
            .replace('٩', '۹');

        normalized = normalized.replaceAll("\\s+", " ");

        return normalized;
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
