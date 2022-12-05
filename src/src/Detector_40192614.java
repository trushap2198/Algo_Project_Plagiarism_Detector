import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Detector_40192614 {

    public static void main(String[] args) throws FileNotFoundException {

        String line;
        int i;
        byte isplag = 0;
        String[] fileArr;
        String s1;
        String s2;
        double plag = 0;

        for (i = 1; i <= 7; ++i) {
            isplag = 0;
            final long startTime = System.currentTimeMillis();
            System.out.println("Folder:okay0"+i);
            line = "data/okay0" + i + "/1.txt,data/okay0" + i + "/2.txt";
            fileArr = line.split(",");
            s1 = readFile(fileArr[0]);
            s2 = readFile(fileArr[1]);
            boolean file1 = isCodeFile(fileArr[0]);
            boolean file2 = isCodeFile(fileArr[1]);
            if(file1 && file2){
                List<StringBuilder> str1 = preProcessCodeFiles(s1);
                List<StringBuilder> str2 = preProcessCodeFiles(s2);
                String[] str_array1  = Arrays.deepToString(str1.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                String[] str_array2 = Arrays.deepToString(str2.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                plag = detect_lcs(str_array1,str_array2,true);
                if(plag>=55.00){
                    isplag = 1;
                }

            }

            else if (file2 || file1) {
                System.out.println("One is code file other is not. Not Plagiarised");
                isplag = 0;
            }
            else{
                String str1 = preProcessTextFiles(s1);
                String str2 = preProcessTextFiles(s2);
                plag = detectLCSInTextFiles(str1,str2,false);
                System.out.println("Plagiarism:" + plag);
                if(plag>60.0){
                    isplag = 1;
                }
            }
            System.out.println( "Plagiarism percentage is:" + plag +". Hence the plag score is:"+ isplag + " \n\n");

            final long endTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (endTime - startTime) + " \n\n");

        }
//
        for(i = 1; i <= 9; ++i) {
            long startTime = 0L;
            isplag=0;
            System.out.println("Folder: plagiarism0"+i);
            line = "data/plagiarism0" + i + "/1.txt,data/plagiarism0" + i + "/2.txt";
            fileArr = line.split(",");
            s1 = readFile(fileArr[0]);
            s2 = readFile(fileArr[1]);
            boolean file1 = isCodeFile(fileArr[0]);
            boolean file2 = isCodeFile(fileArr[1]);
            if(file1 && file2){
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

            else if (file2 || file1){
                System.out.println("One is code file other is not. Not Plagiarised");
                isplag = 0;
            }
            else{
                startTime = System.currentTimeMillis();
                String str1 = preProcessTextFiles(s1);
                String str2 = preProcessTextFiles(s2);
                plag = detectLCSInTextFiles(str1,str2,false);
                System.out.println("Detection done");
                System.out.println("Plagiarism:" + plag);
                if(plag>=60.0){
                    isplag = 1;
                }
            }
            System.out.println( "Plagiarism percentage is:" + plag +". Hence the plag score is:"+ isplag + " \n\n");
            final long endTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (endTime - startTime) + " \n\n");
        }

    }

    private static double detectLCSInTextFiles(String str1, String str2, boolean iscodeFile) {

        HashSet<String> uniqueTerms = new HashSet<>();
        Map<String, Double> weights1 = new HashMap<>();
        Map<String, Double> weights2 = new HashMap<>();
        String[] words1 = str1.toLowerCase().split("\\s+");
        String[] words2 = str2.toLowerCase().split("\\s+");
        double total_cosine = 0.00;
        HashSet<String> stopWords = new HashSet<>(Arrays.asList("a", "able", "about", "across", "after", "all", "almost", "also", "am", "an",
                "and", "any", "are", "as", "at", "be", "because", "been", "but", "by", "can", "cannot", "could", "dear", "did", "do", "does", "either",
                "else", "for", "from", "get", "got", "had", "has", "have", "he", "her", "hers", "him", "his", "how", "i",
                "if", "in", "into", "is", "it", "its", "just", "least", "let", "like", "likely", "may", "me", "might", "most", "must", "my", "neither",
                "no", "nor", "not", "of", "off", "often", "one","on", "only", "or", "other", "own", "rather", "said", "say", "says", "she", "should",
                "since", "so", "some", "than", "that", "the", "their", "them", "then", "there", "these", "they", "this", "tis", "to", "too", "twas",
                "us", "wants", "was", "we", "were", "what", "when", "where", "which", "while",
                "who", "whom", "why", "will", "with", "would", "yet", "you", "your"));
        for(String word:words1){
            if(!stopWords.contains(word)) {
                if (!weights1.containsKey(word)) {
                    weights1.put(word, 0.0);
                }
                weights1.put(word, weights1.get(word) + 1.0);
            }    uniqueTerms.add(word);
        }
        for(String word:words2){
            if(!stopWords.contains(word)) {
                if (!weights2.containsKey(word)) {
                    weights2.put(word, 0.0);
                }
                weights2.put(word, weights2.get(word) + 1.0);
                uniqueTerms.add(word);
            }
        }
        double cosine_simialr = detect_lcs_cosine(weights1,weights2,uniqueTerms);
        System.out.println("Cosine similarity:" + (cosine_simialr));


//        for(String sent1: str1){
//            uniqueTerms.clear();
//            double max_similar = 0.0;
//            String[] words = sent1.split(" ");
//            for(String word: words){
//                word = word.trim();
//                if(!word.isEmpty()){
//                    if(!weights1.containsKey(word)){
//                        weights1.put(word,0.0);
//                    }
//                    weights1.put(word,weights1.get(word)+1.0);
//                    uniqueTerms.add(word);
//                }
//            }
//            for (String sent2: str2){
//                String[] words2 = sent2.split(" ");
//                for(String word: words2){
//                    word = word.trim();
//                    if(!word.isEmpty()){
//                        if(!weights2.containsKey(word)){
//                            weights2.put(word,0.0);
//                        }
//                        weights2.put(word,weights2.get(word)+1.0);
//                        //uniqueTerms.add(word);
//                    }
//                }
//
//                double current_cosine = detect_lcs_cosine(weights1,weights2,uniqueTerms);
//                if(max_similar<current_cosine){
//                    max_similar = current_cosine;
//                }
//            }
//            total_cosine+=max_similar/ uniqueTerms.size();
////            if(total_cosine/uniqueTerms.size()>10.0){
////                return 10.01;
////            }
//        }
//        //System.out.println("Size of unique corpus:" + uniqueTerms.size());
//        System.out.println("Total Cosine:" + total_cosine);
        return cosine_simialr*100;


    }

    private static double detect_lcs_cosine(Map<String, Double> weights1, Map<String, Double> weights2, HashSet<String> uniqueTerms) {
        double dot1=0.0,dot2 = 0.0,dot_final=0.0;
        for (String term:uniqueTerms){
            double count1 = weights1.getOrDefault(term,0.0);
            double count2 = weights2.getOrDefault(term,0.0);
            dot1 += (count1*count1);
            dot2+=(count2*count2);
            dot_final+= (count1*count2);

        }
        return (dot_final/ (Math.sqrt(dot1) * Math.sqrt(dot2)));
    }


    private static String preProcessTextFiles(String s2) {
        String str = s2.replaceAll("[^a-zA-Z ]", "");
        StringBuilder sentences = new StringBuilder();
        String[] paras = str.split("\\n");
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
                System.out.println("Not a reference");
                sentences.append(para);
            }
        }

        return sentences.toString();
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
        StringBuilder contents = new StringBuilder();
        try {
            BufferedReader fin = new BufferedReader(new FileReader(fileName));
            String line = "";
            while((line = fin.readLine())!= null){
                contents.append(line+"\n");
            }
        } catch (Exception var4) {
            System.err.println("Trouble reading from: " + fileName);
        }
        return contents.toString();
    }

    private static boolean isCodeFile(String text) throws FileNotFoundException {
        int count_processed = 0;
        Scanner fin = new Scanner(new FileReader(text));
        String line = "";
        while(fin.hasNextLine())
        {
            line = fin.nextLine().trim();
            if (line.startsWith("#include") || line.startsWith("int ")||line.startsWith("chr ")||line.startsWith("char ")||line.startsWith("/** ")||line.startsWith("/* ") ||
            line.startsWith("bool ") || line.startsWith("boolean ")|| line.startsWith("public static void") || line.startsWith("System.out.println"))
            {
                return true;
            }
            count_processed++;
            if (count_processed==200){
                return false;
            }
        }
        return false;
    }

}
