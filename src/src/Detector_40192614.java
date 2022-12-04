import java.io.File;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Detector_40192614 {

    public static void main(String[] args) {

        String line;
        int i;
        byte isplag = 0;
        String[] fileArr;
        String s1;
        String s2;
        double plag = 0;

//        for (i = 1; i <= 6; ++i) {
//            final long startTime = System.currentTimeMillis();
//            System.out.println("Folder:okay0"+i);
//            line = "data/okay0" + i + "/1.txt,data/okay0" + i + "/2.txt";
//            fileArr = line.split(",");
//            s1 = readFile(fileArr[0]);
//            s2 = readFile(fileArr[1]);
//            if(isCodeFile(s1) && isCodeFile(s2)){
//                List<StringBuilder> str1 = preProcessCodeFiles(s1);
//                List<StringBuilder> str2 = preProcessCodeFiles(s2);
//                String[] str_array1  = Arrays.deepToString(str1.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").split(",");
//                String[] str_array2 = Arrays.deepToString(str2.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").split(",");
//                plag = detect_lcs(str_array1,str_array2,true);
//                if(plag>=55.00){
//                    isplag = 1;
//                }
//
//            }
//
//            else if (isCodeFile(s1) || isCodeFile(s2)) {
//                System.out.println("One is code file other is not. Not Plagiarised");
//                isplag = 0;
//            }
//            else{
//                List<String> str1 = preProcessTextFiles(s1);
//                List<String> str2 = preProcessTextFiles(s2);
//                plag = detectLCSInTextFiles(str1,str2,false);
//                System.out.println("Plagiarism:" + plag);
//                if(plag>5.0){
//                    isplag = 1;
//                }
//            }
//            System.out.println( "Plagiarism percentage is:" + plag +". Hence the plag score is:"+ isplag + " \n\n");
//
//            final long endTime = System.currentTimeMillis();
//            System.out.println("Total execution time: " + (endTime - startTime) + " \n\n");
//
//        }
//
        for(i = 8; i <= 9; ++i) {
            long startTime = 0L;
            System.out.println("Folder: plagiarism0"+i);
            line = "data/plagiarism0" + i + "/1.txt,data/plagiarism0" + i + "/2.txt";
            fileArr = line.split(",");
            s1 = readFile(fileArr[0]);
            s2 = readFile(fileArr[1]);
            if(isCodeFile(s1) && isCodeFile(s2)){
                System.out.println("here in code");
                startTime = System.currentTimeMillis();
                List<StringBuilder> str1 = preProcessCodeFiles(s1);
                List<StringBuilder> str2 = preProcessCodeFiles(s2);
                String[] str_array1  = Arrays.deepToString(str1.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                String[] str_array2 = Arrays.deepToString(str2.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                plag = detect_lcs(str_array1,str_array2,true);
                if(plag>=55.00){
                    isplag = 1;
                }
                
            }

            else if (isCodeFile(s1) || isCodeFile(s2)){
                System.out.println("One is code file other is not. Not Plagiarised");
                isplag = 0;
            }
            else{
                startTime = System.currentTimeMillis();
                List<String> str1 = preProcessTextFiles(s1);
                List<String> str2 = preProcessTextFiles(s2);
                plag = detectLCSInTextFiles(str1,str2,false);
                System.out.println("Detection done");
                System.out.println("Plagiarism:" + plag);
                if(plag>=5.0){
                    isplag = 1;
                }
            }
            System.out.println( "Plagiarism percentage is:" + plag +". Hence the plag score is:"+ isplag + " \n\n");
            final long endTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (endTime - startTime) + " \n\n");
        }

    }

    private static double detectLCSInTextFiles(List<String> str1, List<String> str2, boolean iscodeFile) {

        System.out.println("In detection");
        List<String> string1;
        List<String> string2;

        double match, max_match_in_one_sentence,total_match = 0.00;
        int word_size= 0;
        if(str1.size()>str2.size())
        {
            string1 = str2;
            string2 = str1;
        }
        else {
            string1 = str1;
            string2 = str2;
        }
        for (String sent1:string1){
            word_size+=sent1.length();
        }
        for(String sent1:string1){
            max_match_in_one_sentence = 0.00;
            for(String sent2:string2){
                match = detect_lcs(sent1.split(" "),sent2.split(" "),iscodeFile);
                if(max_match_in_one_sentence<match){
                    max_match_in_one_sentence= match;
                }
            }
            total_match+=max_match_in_one_sentence;
            //the breakout
            if((total_match/word_size*100) > 5.0){
                System.out.println("Breakout");
                return total_match/word_size*100;
            }
        }
        System.out.println("Exit detect");
        return total_match/word_size*100;
    }

    private static List<String> preProcessTextFiles(String s2) {
        System.out.println("in prepreprocess");

        String str = s2.replaceAll("[,;:'!?$%\\^*]","");
        List<String> sentences = new ArrayList<>();
        List<String> paras = Arrays.stream(str.split("\\n")).collect(Collectors.toList());
        List<String> ref_keywords = Arrays.asList("Wikipedia", "http","www", ".org","wiki","wikipedia","#","google",".com","https");
        int ref_count=0;
        Pattern matching_date = Pattern.compile("[0-9]{4}");
        for(String para: paras){
            ref_count=0;
            if(para.length()==0)
                continue;
            Matcher matcher = matching_date.matcher(para);
            while(matcher.find()){
                    ref_count++;
            }
            for(String word: ref_keywords){
                if(para.contains(word)){
                    ref_count++;
                }
            }
            if((100.00 * ref_count/para.length())<=0.90){
                //System.out.println("Not a reference");
                sentences.addAll(Arrays.stream(para.split("\\.")).collect(Collectors.toList()));
            }
        }
        System.out.println("Done preprocess");
        return sentences;
    }

    private static List<StringBuilder> preProcessCodeFiles(String string) {

        //Tokenizing

        List<StringBuilder> tokens = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(string, "  -/.,;:()'!?\"\t\n\r\f");
        while (st.hasMoreTokens()) {
            StringBuilder token = new StringBuilder(st.nextToken().toLowerCase()); //tolowercase
//            HashSet<String> stopWords = new HashSet<>(Arrays.asList("a", "able", "about", "across", "after", "all", "almost", "also", "am", "among", "an", "and", "any", "are", "as", "at", "be", "because", "been", "but", "by", "can", "cannot", "could", "dear", "did", "do", "does", "either", "else", "ever", "every", "for", "from", "get", "got", "had", "has", "have", "he", "her", "hers", "him", "his", "how", "however", "i", "if", "in", "into", "is", "it", "its", "just", "least", "let", "like", "likely", "may", "me", "might", "most", "must", "my", "neither", "no", "nor", "not", "of", "off", "often", "on", "only", "or", "other", "our", "own", "rather", "said", "say", "says", "she", "should", "since", "so", "some", "than", "that", "the", "their", "them", "then", "there", "these", "they", "this", "tis", "to", "too", "twas", "us", "wants", "was", "we", "were", "what", "when", "where", "which", "while",
//                    "who", "whom", "why", "will", "with", "would", "yet", "you", "your"));
//            if (!stopWords.contains(token.toString())) {
////                //System.out.println(token.getClass());//StopWords Removal
//               tokens.add(token);
//            }
           tokens.add(token);
        }
        //tokens = Stemmer.stemming(tokens);
        return tokens;
    }

    private static double detect_lcs(String[] str1, String[] str2,boolean isCode) {
        int [][] c = new int[str1.length + 1][str2.length + 1];

        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                if (str1[i - 1].equals(str2[j - 1])) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else {
                    c[i][j] = Math.max(c[i][j - 1], c[i - 1][j]);
                }
            }
        }
//
//        System.out.println("Printing the longest subsequence:");
//        int sequ_size = c[str1.length][str2.length];
//        int temp = sequ_size;
//        String[] lcs = new String[  sequ_size+  1];
//        lcs[sequ_size]  = "\0";
//
//        int i = str1.length, j = str2.length;
//        while (i > 0 && j > 0) {
//            if (str1[i - 1].equals(str2[j - 1])) {
//                lcs[sequ_size - 1] = str1[i - 1];
//                i--;
//                j--;
//                sequ_size--;
//            }
//
//            else if (c[i - 1][j] > c[i][j - 1])
//                i--;
//            else
//                j--;
//        }
//
//        System.out.print("\nLCS: ");
//        for (int k = 0; k <= temp; k++)
//            System.out.print(lcs[k]);
//        System.out.println("");

//        System.out.println("file1 length:"+str1.length);
//        System.out.println("file2 length:"+str2.length);
//        System.out.println("count: "+c[str1.length][str2.length]);
        if(isCode)
        return (1.00 * c[str1.length][str2.length])/Math.min(str1.length, str2.length)*100;
        else
            return c[str1.length][str2.length];
    }

//    private static double lcsLength(String s1, String s2) {
//        // if either string is blank, return 0
//        if (s1.length() == 0 || s2.length() == 0) {
//            return 0;
//        }
//
//        int[][] lcsArr = new int[2][s2.length() + 1];    // instantiate lcsArr with 2 rows
//        char[] charArr1 = s1.toCharArray();     // s1 converted to char array
//        char[] charArr2 = s2.toCharArray();     // s2 converted to char array
//
//        for (int i = 1; i <= charArr1.length; i++) {
//            for (int j = 1; j <= charArr2.length; j++) {
//                // if two chars are the same:
//                if (charArr1[i - 1] == charArr2[j - 1]) {
//                    if (i % 2 == 0) // row is even (row 1 is previous row)
//                        lcsArr[0][j] = lcsArr[1][j - 1] + 1;
//                    else  // row is odd (row 0 is previous row)
//                        lcsArr[1][j] = lcsArr[0][j - 1] + 1;
//                } else {
//                    if (i % 2 == 0) // row is even (row 1 is previous row)
//                        lcsArr[0][j] = Integer.max(lcsArr[1][j], lcsArr[0][j - 1]);
//                    else  // row is odd (row 0 is previous row)
//                        lcsArr[1][j] = Integer.max(lcsArr[0][j], lcsArr[1][j - 1]);
//                }
//            }
//        }
//        // return last position in arr
//        System.out.println("S1 LENGTH: "+s1.length() + "\n" + "S2 Length:" + s2.length());
//        return lcsArr[1][s2.length()] * 100.00 / Math.min(s1.length(),s2.length()) ;
//    }




    private static String readFile(String fileName) {
        String contents = "";
        try {
            RandomAccessFile fin = new RandomAccessFile(new File(fileName), "r");

            for (int b = fin.read(); b != -1; b = fin.read()) {
                //System.out.println("Okay");
                contents = contents + (char) b;
            }
        } catch (Exception var4) {
            System.err.println("Trouble reading from: " + fileName);
        }
        //System.out.println("CON"+ contents);
        return contents;
    }

    private static boolean isCodeFile(String text) {
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("#include", "elif", "class", "void", "bool", "h>", "int", "char", "chr", "return", "char", "cout", "<<", ">>", "==", "!=", ">=", "<=", "{", "}", "=", "++", "--", "<", ">", "++", "--", "switch", "case", "()", "system", "out", "println", "print", "boolean", "static", "void"));
        double count = 0.00;
        for (String str : text.split(" ")) {
            if (keywords.contains(str)) {
                count++;
            }
            if (count / text.length() >= 0.01) {
                return true;
            }
        }
        //System.out.println("This file has "+ count + " codewords out of " + text.length() + " words");
        if (count / text.length() >= 0.01) {
            return true;
        } else
            return false;
    }

}
