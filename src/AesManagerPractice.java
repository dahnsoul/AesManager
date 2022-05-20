import java.io.*;

public class AesManagerPractice {

    public static void main(String[] args) throws Exception {

        AES256 aes256 = new AES256();
        String glDecryptLine = "";

        //실행 파라미터 1 : 암호화
        if (args[0].equals("1")) {

            //File file = new File("/Users/marco/고객정보_20220516.txt");
            File file = new File("/Users/marco/카드정보_20220516.txt");

            if (file.exists()) {

                BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(file), "euc-kr"));

                //File outPutFile = new File("/Users/marco/고객정보_20220516_암호화.txt");
                File outPutFile = new File("/Users/marco/카드정보_20220516_암호화.txt");

                if (!outPutFile.exists()) {
                    outPutFile.createNewFile();
                }

                System.out.println("암호화 프로세스 시작" + args[0]);


                String sLine = null;

                FileWriter fw = new FileWriter(outPutFile);
                BufferedWriter writer = new BufferedWriter(fw);

                int line = 0;

                while ((sLine = inFile.readLine()) != null) {

                    if (line == 0) {
                        writer.write(sLine);
                        System.out.println("Header Line:" + sLine);

                    } else {
                        String tail = "T" + aes256.fillWithZero((line -1)+"",10);

                        if (sLine.startsWith(tail,0)) {
                            System.out.println("Tail Line:" + sLine);
                            writer.write(sLine);
                        } else {
                            System.out.println("Data Line:" + sLine);
                            String encLine = aes256.encrypt(sLine);
                            System.out.println("Enc Data Size:"+encLine.length());
                            writer.write(encLine);
                        }
                    }

                    writer.newLine();

                    line++;

                    if (sLine.startsWith("H", 0)) {
                        continue;
                    }

                    if (sLine.startsWith("D", 0)) {
                        continue;
                    }

                    if (sLine.startsWith("T", 0)) {
                        break;
                    }

                }
                writer.close();

            } else {
                throw new Exception();
            }


        //복호화
        } else {

            //File file = new File("/Users/marco/고객정보_20220516_암호화.txt");
            File file = new File("/Users/marco/카드정보_20220516_암호화.txt");

            if (file.exists()) {

                BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(file), "euc-kr"));

                //File outPutFile = new File("/Users/marco/고객정보_20220516_복호화.txt");
                File outPutFile = new File("/Users/marco/카드정보_20220516_복호화.txt");

                if (!outPutFile.exists()) {
                    outPutFile.createNewFile();
                }

                System.out.println("복호화 프로세스 시작" + "파라미터:"+args[0]);


                String sLine = null;

                FileWriter fw = new FileWriter(outPutFile);
                BufferedWriter writer = new BufferedWriter(fw);

                int line = 0;

                while ((sLine = inFile.readLine()) != null) {

                    if (line == 0) {
                        writer.write(sLine);
                        writer.newLine();

                        System.out.println("Header Line:" + sLine);

                    } else {
                        System.out.println("LINE" +line);
                        System.out.println("LINE" + (line -1));
                        String tail = "T" + aes256.fillWithZero((line -1)+"",9);

                        if (sLine.startsWith(tail,0)) {
                            System.out.println("Tail Line:" + sLine);
                            writer.write(sLine);
                            writer.newLine();
                        } else {
                            String decryptLine = aes256.decrypt(sLine);
                            glDecryptLine = decryptLine;

                            if (decryptLine.startsWith("D", 0)) {
                                System.out.println("Data Line:" + decryptLine);
                                writer.write(decryptLine);
                                writer.newLine();
                                continue;
                            }
                        }
                    }




                    line++;



                    if (sLine.startsWith("H", 0)) {
                        continue;
                    }

                    if (glDecryptLine.startsWith("D", 0)) {
                        continue;
                    }

                    if (sLine.startsWith("T", 0)) {
                        break;
                    }

                }
                writer.close();

            } else {
                throw new Exception();
            }

        }

    }

}
