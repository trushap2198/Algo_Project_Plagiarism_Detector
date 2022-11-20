import java.io.*;

public class Plagiarism_detector_LCS {
    private static long startTime;
    private static int counterForTime;

    public static double endTime = 0;

    public static void main(String[] args) throws IOException {
//        if(args.length!=1){
//            System.out.println("Not correct number of args");
//            System.exit(0);
//        }
//        else{
            String f1 = readFile("data/okay01/1.txt");
            String f2 = readFile("data/okay01/2.txt");
            System.out.println("f1:" + f1);
            System.out.println("f2:" + f2);


        System.out.println("Is it plagiarised:" + detect_lcs(f1.split(" "),f2.split(" ")));
        //}
    }

    private static double detect_lcs(String[] str1, String[] str2) {
        int [][] c = new int[str1.length + 1][str2.length + 1];

        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                System.out.println(str1[i-1] + ": "  +str2[j-1]);
                if (str1[i - 1].equals(str2[j - 1])) {
                    System.out.println("here??");
                    c[i][j] = c[i - 1][j - 1] + 1;

                } else {
                    System.out.println("no here");
                    c[i][j] = Math.max(c[i][j - 1], c[i - 1][j]);
                }
            }
        }

        System.out.println(str1.length + ": " + str2.length);
        return (c[str1.length][str2.length]/(Math.max(str1.length, str2.length * 1.00))) * 100;
    }


    private static String readFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        StringBuilder file_data = new StringBuilder();
        while ((line = br.readLine())!=null){
            file_data.append(line);
        }
        return file_data.toString();
    }
}
