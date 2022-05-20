import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256 {


    public static String alg = "AES/CBC/PKCS5Padding";
    private final String kbKey = "C7CFD382D8D964472C602FB287FDD0E68D96447F7D0E62C77CFD382D02FB28DC";
    private final String kbIv = "4A614E645267556B4A614E645267556B";

    //private final String kbKey = "01234567890123456789012345678901";
    //private final String kbIv = kbKey.substring(0, 16);

    //암호화
    public String encrypt(String text) throws Exception {
        /*
        String key = hexToByteArray()
        String iv = "";

        System.out.println("before getbytes:"+ kbKey);
        System.out.println("after getbytes:"+ kbKey.getBytes(StandardCharsets.UTF_8));


        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
        */
        byte[] key = hexToByteArray(kbKey);
        byte[] iv = hexToByteArray(kbIv);

        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        //byte[] encrypted = cipher.doFinal(text.getBytes("EUC-KR"));
        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);


    }

    //복호화
    public String decrypt(String cipherText) throws Exception {


        try {
            byte[] key = hexToByteArray(kbKey);
            byte[] iv = hexToByteArray(kbIv);


            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);

            //return new String(decrypted, "EUC-KR");
            return new String(decrypted, "UTF-8");

        } catch (Exception e){
          System.out.println("dddd"+e);
            throw e;
        }

    }

    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }

        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }
    //바이트 데이터를 16진수로 변경
    public static String byteToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for(byte b : data) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }


    public  String fillWithZero(String strNVal, int nZeroCount){
        int nLenOfVal = strNVal.length();
        if(nLenOfVal >= nZeroCount ){
            return strNVal;
        } else {
            String freeSpace ="";
            int nSpaceLen = nZeroCount - nLenOfVal;

            for(int i=0 ; i < nSpaceLen; i++) {

                freeSpace += "0";

            }
            return freeSpace + strNVal;
        }


    }
    //테스트
    public  String test(){

        byte [] byteStr;

        //byteStr= hexToByteArray(key);
        byteStr= hexToByteArray(kbIv);
        String result = new String(byteStr);

        return result;
    }
}
