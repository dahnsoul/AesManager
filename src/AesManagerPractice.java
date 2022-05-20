import java.io.*;

public class AesManagerPractice {

    public static void main(String[] args) throws Exception {

        AES256 aes256 = new AES256();

        //실행 파라미터 1 : 암호화
        if (args[0].equals("1")) {

            File file = new File("/Users/marco/고객정보_20220516.txt");

            if (file.exists()) {


                //BufferedReader inFile = new BufferedReader(new FileReader(file));
                BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(file), "euc-kr"));

                File outPutFile = new File("/Users/marco/고객정보_20220516_암호화.txt");

                if (!outPutFile.exists()) {
                    outPutFile.createNewFile();
                }

                System.out.println("agr" + args[0]);


                String sLine = null;

                FileWriter fw = new FileWriter(outPutFile);
                BufferedWriter writer = new BufferedWriter(fw);
                //BufferedWriter writer = new BufferedWriter(new BufferedWriter(new OutputStreamWriter((new FileOutputStream(outPutFile)), "UTF-8")));
                int line = 0;

                while ((sLine = inFile.readLine()) != null) {

                    if (line == 0) {
                        writer.write(sLine);
                        System.out.println("Header Line:" + sLine);

                    } else {
                        if (sLine.startsWith("T", 0)) {
                            System.out.println("Tail Line:" + sLine);
                            writer.write(sLine);
                        } else {
                            System.out.println("Data Line:" + sLine);
                            String encLine = aes256.encrypt(sLine);


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

            File file = new File("/Users/marco/고객정보_20220516_암호화.txt");

            if (file.exists()) {
                //BufferedReader inFile = new BufferedReader(new FileReader(file));
                BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(file), "euc-kr"));

                File outPutFile = new File("/Users/marco/고객정보_20220516_복호화.txt");

                if (!outPutFile.exists()) {
                    outPutFile.createNewFile();
                }

                System.out.println("agr" + args[0]);


                String sLine = null;

                FileWriter fw = new FileWriter(outPutFile);
                BufferedWriter writer = new BufferedWriter(fw);
                //BufferedWriter writer = new BufferedWriter(new BufferedWriter(new OutputStreamWriter((new FileOutputStream(outPutFile)), "UTF-8")));
                int line = 0;

                while ((sLine = inFile.readLine()) != null) {

                    if (line == 0) {
                        writer.write(sLine);
                        System.out.println("Header Line:" + sLine);

                    } else {
                        if (sLine.startsWith("T", 0)) {
                            System.out.println("Tail Line:" + sLine);
                            writer.write(sLine);
                        } else {
                            System.out.println("Data Line:" + sLine);
                            String encLine = aes256.decrypt(sLine);
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

        }

    }

}
