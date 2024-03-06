package cn.perhome.snapha.utils;

import com.github.promeg.pinyinhelper.Pinyin;

public class SpellUtils {
    public static String spell(String str) {
        String spell = Pinyin.toPinyin(str, "");
        return spell.toLowerCase();
    }

    public static String abbr(String str) {
        String spell = Pinyin.toPinyin(str, "#").toLowerCase();
        String[] arrStr = spell.split("#");
        StringBuffer ret = new StringBuffer();
        for (String s : arrStr) {
            if (s.length() > 0) {
                ret.append(s.charAt(0));
            }
        }
        return ret.toString();
    }
}
