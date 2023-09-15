package com.xhlm.dialog;

public class AccentedStringUtils {
    private static final String[] ACCENTED_STRINGS = {
            "aáàãảạăắằẵẳặâấầẫẩậ",
            "AÁÀÃẢẠĂẮẰẴẲẶÂẤẦẪẨẬ",
            "dđ",
            "DĐ",
            "eéèẽẻẹêếềễểệ",
            "EÉÈẼẺẸÊẾỀỄỂỆ",
            "iíìĩỉị",
            "IÍÌĨỈỊ",
            "oóòõỏọôốồỗổộơớờỡởợ",
            "OÓÒÕỎỌÔỐỒỖỔỘƠỚỜỠỞỢ",
            "uúùũủụưứừữửự",
            "UÚÙŨỦỤƯỨỪỮỬỰ",
            "yýỳỹỷỵ",
            "YÝỲỸỶỴ"
    };

    public static String getAccentedString(char base) {
        for (String accentedString : ACCENTED_STRINGS) {
            if (accentedString.charAt(0) == base) {
                return accentedString;
            }
        }
        return null;
    }
}
