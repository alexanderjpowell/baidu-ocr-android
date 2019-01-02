package demo.ocr.baidu.com.testapp;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class TextParser {

    //private List<String> progressives = new ArrayList<String>();
    private String text;

    public TextParser() {
    }

    public TextParser(String text) {
        //this.text = text;
        //parseText(this.text);
    }

    private static Boolean canConvertToDouble(String value) {
        try {
            value = value.replaceAll(",", "");
            value = value.replaceAll("\\.", "");
            Double.valueOf(value);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static List<String> parseText(List<String> words) {
        List<String> ret = new ArrayList<String>();
        for (String word : words) {
            word = word.trim();
            if ((word.startsWith("$") || word.startsWith("S") || word.startsWith("串")) && (word.length() >=3) && word.charAt(word.length() - 3) == '.' && canConvertToDouble(word.substring(1)))
                ret.add(word.substring(1));
            else if ((word.startsWith("$") || word.startsWith("S") || word.startsWith("串")) && canConvertToDouble(word.substring(1)))
                ret.add(word.substring(1));
            else if ((word.length() >= 3) && (word.charAt(word.length() - 3) == '.') && canConvertToDouble(word))
                ret.add(word);
            else if (count(word, '$') == 2) {
                String[] parts = word.split("\\$");
                for (String part : parts) {
                    if (canConvertToDouble(part))
                        ret.add(part);
                }
            } else if (count(word, '串') == 2) {
                String[] parts = word.split("串");
                for (String part : parts) {
                    if (canConvertToDouble(part))
                        ret.add(part);
                }
            }
        }
        for (int i = 0; i < ret.size(); i++) {
            ret.set(i, formatDollarString(ret.get(i)));
        }
        return ret;
    }

    public static void main(String[] args) {

        String test = "{\"log_id\": 1973890621467273506, \"direction\": 0, \"words_result_num\": 9, \"words_result\": [{\"words\": \" LIBERTY\"}, {\"words\": \" LINK+\"}, {\"words\": \"串5,422.79\"}, {\"words\": \" GRAND JACKPOT\"}, {\"words\": \"串644.80\"}, {\"words\": \" MAJOR JACKPOT\"}, {\"words\": \"串50.00串10.00\"}, {\"words\": \" MINOR BONUS\"}, {\"words\": \" MINI BONUS\"}]}";
        List<String> sample = new ArrayList<String>();
        sample.add("串5,422.79");
        sample.add("串644.80");
        sample.add("串50.00串10.00");
        List<String> progressives = parseText(sample);
        for (String progressive : progressives) {
            System.out.println(formatDollarString(progressive));
        }
    }

    private static String formatDollarString(String value) {
        value = value.replaceAll(",", "");
        value = value.replaceAll("\\.", "");
        value = value.replaceAll("\\$", "");
        return value.substring(0, value.length() - 2) + "." + value.substring(value.length()-2, value.length());
    }

    private static int count(String word, char c) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == c)
                count++;
        }
        return count;
    }
}
