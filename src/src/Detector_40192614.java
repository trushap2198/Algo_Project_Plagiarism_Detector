

import java.io.File;
import java.io.RandomAccessFile;
import java.util.*;

public class Detector_40192614 {

    public static void main(String[] args) {
        String txt = "";
        String pat = "";

        String line;
        int i;
        String[] fileArr;
        String s1;
        String s2;
        for (i = 1; i <= 6; ++i) {
            System.out.println("file new"+i);
            line = "data/okay0" + i + "/1.txt,data/okay0" + i + "/2.txt";
            fileArr = line.split(",");
            s1 = readFile(fileArr[0]);
            s2 = readFile(fileArr[1]);
            List<StringBuilder> str1 = preProcess(s1);

            List<StringBuilder> str2 = preProcess(s2);
            String[] str_array1  = Arrays.deepToString(str1.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").split(",");
            String[] str_array2 = Arrays.deepToString(str2.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").split(",");
            System.out.println(detect_lcs(str_array1,str_array2));
        }

        for(i = 1; i <= 7; ++i) {
            System.out.println("file new in pal"+i);
            line = "data/plagiarism0" + i + "/1.txt,data/plagiarism0" + i + "/2.txt";
            fileArr = line.split(",");
            s1 = readFile(fileArr[0]);
            s2 = readFile(fileArr[1]);
            List<StringBuilder> str1 = preProcess(s1);

            List<StringBuilder> str2 = preProcess(s2);
            String[] str_array1  = Arrays.deepToString(str1.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").split(",");
            String[] str_array2 = Arrays.deepToString(str2.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").split(",");
            System.out.println(detect_lcs(str_array1,str_array2));
        }

    }

    private static List<StringBuilder> preProcess(String string) {

        //Tokenizing
        List<StringBuilder> tokens = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(string, " -/.,;:()'!?\"\t\n\r\f");
        while (st.hasMoreTokens()) {
            StringBuilder token = new StringBuilder(st.nextToken().toLowerCase()); //tolowercase
            HashSet<String> stopWords = new HashSet<>(Arrays.asList("a", "able", "about", "across", "after", "all", "almost", "also", "am", "among", "an", "and", "any", "are", "as", "at", "be", "because", "been", "but", "by", "can", "cannot", "could", "dear", "did", "do", "does", "either", "else", "ever", "every", "for", "from", "get", "got", "had", "has", "have", "he", "her", "hers", "him", "his", "how", "however", "i", "if", "in", "into", "is", "it", "its", "just", "least", "let", "like", "likely", "may", "me", "might", "most", "must", "my", "neither", "no", "nor", "not", "of", "off", "often", "on", "only", "or", "other", "our", "own", "rather", "said", "say", "says", "she", "should", "since", "so", "some", "than", "that", "the", "their", "them", "then", "there", "these", "they", "this", "tis", "to", "too", "twas", "us", "wants", "was", "we", "were", "what", "when", "where", "which", "while",
                    "who", "whom", "why", "will", "with", "would", "yet", "you", "your"));
//            if (!stopWords.contains(token.toString())) {
////                //System.out.println(token.getClass());//StopWords Removal
//               tokens.add(token);
//            }
            tokens.add(token);
        }
        //tokens = Stemmer.stemming(tokens);
        return tokens;
    }

    private static double detect_lcs(String[] str1, String[] str2) {
        int [][] c = new int[str1.length + 1][str2.length + 1];

        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                //System.out.println(str1[i-1] + ": "  +str2[j-1]);
                if (str1[i - 1].equals(str2[j - 1])) {
                    //System.out.println("here??");
                    c[i][j] = c[i - 1][j - 1] + 1;

                } else {
                    //System.out.println("no here");
                    c[i][j] = Math.max(c[i][j - 1], c[i - 1][j]);
                }
            }
        }

        //System.out.println(str1.length + ": " + str2.length);

        System.out.println("file1 length:"+str1.length);
        System.out.println("file2 length:"+str2.length);
        System.out.println("count"+c[str1.length][str2.length]);
        return (1.00 * c[str1.length][str2.length])/Math.min(str1.length, str2.length)*100;
    }




    private static String readFile(String fileName) {
        String contents = "";

        try {
            RandomAccessFile fin = new RandomAccessFile(new File(fileName), "r");

            for (int b = fin.read(); b != -1; b = fin.read()) {
                contents = contents + (char) b;
            }
        } catch (Exception var4) {
            System.err.println("Trouble reading from: " + fileName);
        }

        return contents;
    }
}
