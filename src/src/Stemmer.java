import java.util.ArrayList;
import java.util.List;

public class Stemmer {

    public static List<StringBuilder> stemming(List<StringBuilder> lst_str) {
        List<StringBuilder> output = new ArrayList<>();
        for (StringBuilder str : lst_str){
            String string = str.toString();
            String str1 = stemStep1(string);
            str1 = step1b(str1);
            str1 = stemStep1c(str1);
            str1 = stemStep2(str1);
            str1 = stemStep3(str1);
            str1 = stemStep4(str1);
            str1 = stemStep5a(str1);
            str1 = stemStep5b(str1);
            output.add(new StringBuilder(str1));
        }
        return output;
    }

    private static String stemStep1(String input) {
        if (input.endsWith("sses")) {
            return input.substring(0, input.length() - 2);
        }
        // IES  -> I
        if (input.endsWith("ies")) {
            return input.substring(0, input.length() - 2);
        }
        // SS   -> SS
        if (input.endsWith("ss")) {
            return input;
        }
        // S    ->
        if (input.endsWith("s")) {
            return input.substring(0, input.length() - 1);
        }
        return  input;
    }

    public static String step1b (String input){
        if (input.endsWith("eed")) {
            String stem = input.substring(0, input.length() - 1);
            String letterTypes = getLetterTypes(stem);
            int m = getM(letterTypes);
            if (m > 0) return stem;
            return input;
        }
        // (*v*) ED  ->
        if (input.endsWith("ed")) {
            String stem = input.substring(0, input.length() - 2);
            String letterTypes = getLetterTypes(stem);
            if (letterTypes.contains("v")) {
                return step1b2(stem);
            }
            return input;
        }
        // (*v*) ING ->
        if (input.endsWith("ing")) {
            String stem = input.substring(0, input.length() - 3);
            String letterTypes = getLetterTypes(stem);
            if (letterTypes.contains("v")) {
                return step1b2(stem);
            }
            return input;
        }
        return input;
    }

    private static String step1b2(String input) {
        if (input.endsWith("at")) {
            return input + "e";
        }
        // BL -> BLE
        else if (input.endsWith("bl")) {
            return input + "e";
        }
        // IZ -> IZE
        else if (input.endsWith("iz")) {
            return input + "e";
        } else {
            // (*d and not (*L or *S or *Z))
            // -> single letter
            char lastDoubleConsonant = getLastDoubleConsonant(input);
            if (lastDoubleConsonant != 0 &&
                    lastDoubleConsonant != 'l'
                    && lastDoubleConsonant != 's'
                    && lastDoubleConsonant != 'z') {
                return input.substring(0, input.length() - 1);
            }
            // (m=1 and *o) -> E
            else {
                String letterTypes = getLetterTypes(input);
                int m = getM(letterTypes);
                if (m == 1 && isStarO(input)) {
                    return input + "e";
                }

            }
        }
        return input;
    }

    static String stemStep1c(String input) {
        if (input.endsWith("y")) {
            String stem = input.substring(0, input.length() - 1);
            String letterTypes = getLetterTypes(stem);
            if (letterTypes.contains("v")) return stem + "i";
        }
        return input;
    }

    static String stemStep2(String input) {
        String[] s1 = new String[]{
                "ational",
                "tional",
                "enci",
                "anci",
                "izer",
                "bli", // the published algorithm specifies abli instead of bli.
                "alli",
                "entli",
                "eli",
                "ousli",
                "ization",
                "ation",
                "ator",
                "alism",
                "iveness",
                "fulness",
                "ousness",
                "aliti",
                "iviti",
                "biliti",
                "logi", // the published algorithm doesn't contain this
        };
        String[] s2 = new String[]{
                "ate",
                "tion",
                "ence",
                "ance",
                "ize",
                "ble", // the published algorithm specifies able instead of ble
                "al",
                "ent",
                "e",
                "ous",
                "ize",
                "ate",
                "ate",
                "al",
                "ive",
                "ful",
                "ous",
                "al",
                "ive",
                "ble",
                "log" // the published algorithm doesn't contain this
        };
        // (m>0) ATIONAL ->  ATE
        // (m>0) TIONAL  ->  TION
        for (int i = 0; i < s1.length; i++) {
            if (input.endsWith(s1[i])) {
                String stem = input.substring(0, input.length() - s1[i].length());
                String letterTypes = getLetterTypes(stem);
                int m = getM(letterTypes);
                if (m > 0) return stem + s2[i];
                return input;
            }
        }
        return input;
    }

    static String stemStep3(String input) {
        String[] s1 = new String[]{
                "icate",
                "ative",
                "alize",
                "iciti",
                "ical",
                "ful",
                "ness",
        };
        String[] s2 = new String[]{
                "ic",
                "",
                "al",
                "ic",
                "ic",
                "",
                "",
        };
        // (m>0) ICATE ->  IC
        // (m>0) ATIVE ->
        for (int i = 0; i < s1.length; i++) {
            if (input.endsWith(s1[i])) {
                String stem = input.substring(0, input.length() - s1[i].length());
                String letterTypes = getLetterTypes(stem);
                int m = getM(letterTypes);
                if (m > 0) return stem + s2[i];
                return input;
            }
        }
        return input;

    }

    static String stemStep4(String input) {
        String[] suffixes = new String[]{
                "al",
                "ance",
                "ence",
                "er",
                "ic",
                "able",
                "ible",
                "ant",
                "ement",
                "ment",
                "ent",
                "ion",
                "ou",
                "ism",
                "ate",
                "iti",
                "ous",
                "ive",
                "ize",
        };
        // (m>1) AL    ->
        // (m>1) ANCE  ->
        for(String suffix : suffixes) {
            if (input.endsWith(suffix)) {
                String stem = input.substring(0, input.length() - suffix.length());
                String letterTypes = getLetterTypes(stem);
                int m = getM(letterTypes);
                if (m > 1) {
                    if (suffix.equals("ion")) {
                        if (stem.charAt(stem.length() - 1) == 's' || stem.charAt(stem.length() - 1) == 't') {
                            return stem;
                        }
                    } else {
                        return stem;
                    }
                }
                return input;
            }
        }
        return input;
    }

    static String stemStep5a(String input) {
        if (input.endsWith("e")) {
            String stem = input.substring(0, input.length() - 1);
            String letterTypes = getLetterTypes(stem);
            int m = getM(letterTypes);
            // (m>1) E     ->
            if (m > 1) {
                return stem;
            }
            // (m=1 and not *o) E ->
            if (m == 1 && !isStarO(stem)) {
                return stem;
            }
        }
        return input;
    }

    static String stemStep5b(String input) {
        // (m > 1 and *d and *L) -> single letter
        String letterTypes = getLetterTypes(input);
        int m = getM(letterTypes);
        if (m > 1 && input.endsWith("ll")) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }


    private static boolean isStarO(String input) {
        if (input.length() < 3) return false;

        char lastLetter = input.charAt(input.length() - 1);
        if (lastLetter == 'w' || lastLetter == 'x' || lastLetter == 'y') return false;

        char secondToLastLetter = input.charAt(input.length() - 2);
        char thirdToLastLetter = input.charAt(input.length() - 3);
        char fourthToLastLetter = input.length() == 3 ? 0 : input.charAt(input.length() - 4);
        return getLetterType(secondToLastLetter, lastLetter) == 'c'
                && getLetterType(thirdToLastLetter, secondToLastLetter) == 'v'
                && getLetterType(fourthToLastLetter, thirdToLastLetter) == 'c';
    }

    private static char getLastDoubleConsonant(String input) {
        if (input.length() < 2) return 0;
        char lastLetter = input.charAt(input.length() - 1);
        char penultimateLetter = input.charAt(input.length() - 2);
        if (lastLetter == penultimateLetter && getLetterType((char) 0, lastLetter) == 'c') {
            return lastLetter;
        }
        return 0;
    }
    static int getM(String letterTypes) {
        if (letterTypes.length() < 2) return 0;
        if (letterTypes.charAt(0) == 'c') return (letterTypes.length() - 1) / 2;
        return letterTypes.length() / 2;
    }

    static String getLetterTypes(String input) {
        StringBuilder letterTypes = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char letter = input.charAt(i);
            char previousLetter = i == 0 ? 0 : input.charAt(i - 1);
            char letterType = getLetterType(previousLetter, letter);
            if (letterTypes.length() == 0 || letterTypes.charAt(letterTypes.length() - 1) != letterType) {
                letterTypes.append(letterType);
            }
        }
        return letterTypes.toString();
    }
    private static char getLetterType(char previousLetter, char letter) {
        switch (letter) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return 'v';
            case 'y':
                if (previousLetter == 0 || getLetterType((char) 0, previousLetter) == 'v') {
                    return 'c';
                }
                return 'v';
            default:
                return 'c';
        }
    }
}
