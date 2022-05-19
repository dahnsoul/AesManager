import com.sun.xml.internal.ws.util.StringUtils;

import java.io.*;

public class AesManager {

    public static void main(String[] args) throws Exception {
        AES256 aes256 = new AES256();

        String recvDir  = "/Users/marco";
        String text = "단소리는 천재다.";
        //String cipherText = aes256.encrypt(text);

        String a = aes256.test();
        String filename = "고객정보_20220516.txt";
        System.out.println("ddd"+a);

        File dir = new File(recvDir);
        File recvFile = new File(recvDir,filename);

        File resultFile = new File(recvFile,"haha.txt");


        BufferedReader bfr = null;
        BufferedOutputStream bos = null;

        try {

            bfr= new BufferedReader(new FileReader(recvFile));
            //bos= new BufferedOutputStream(new FileOutputStream(resultFile));

            int line = 0;

            String cipherText ="";
            String plainText ="";

            char[] cbuf = new char[2000];


            while( bfr.read(cbuf)>0){
                cipherText = new String(cbuf);


                if(cipherText == null){
                    break;
                }
                //Header
                if(line==0) {
                    plainText = cipherText;
                    System.out.println("dahn1" + cipherText);

                } else {
                    String trail = "T";
                    if(cipherText.startsWith(trail,0)){

                        plainText =  cipherText;

                    }else {
                        plainText = aes256.encrypt(cipherText);

                    }

                    line++;

                    if(plainText.startsWith("H",0)){
                        continue;
                    }

                    if(plainText.startsWith("T",0)){
                        break;
                    }

                    try {

                        OutputStream output = new FileOutputStream("/Users/marco/dahn.txt");



                        byte[] lineStr = plainText.getBytes();

                        System.out.println("dahnsoul" + lineStr);

                        output.write(lineStr);

                    } catch (Exception e){
                        throw new Exception(e);
                    }
                }

            }
        } catch (Exception e){
            throw new Exception(e);
        }


    }

}
