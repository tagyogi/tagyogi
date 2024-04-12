package proj.core.crypto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>CryptoUtil.java (암호화 Util)</p>
 *
 * @author 	    : BSG
 * @date 		: 2023.01.03
 *
 * modifier 	:
 * modify-date  :
 * description  : 암/복호화 처리
 */
public class CryptoUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtil.class);
   
	private static final String ALGORITHM = "AES";
	private static final String HASHALGORITHM = "SHA-256";
    private static final String defaultSecretKey = "dyis";
    private Key secretKeySpec;   
    
    public CryptoUtil()
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        this(null);
    }
 
    public CryptoUtil(String secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {
        this.secretKeySpec = generateKey(secretKey);
    }

    public String encrypt(String plainText) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        return asHexString(encrypted);
    }

    public String decrypt(String encryptedString) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] original = cipher.doFinal(toByteArray(encryptedString));
        return new String(original);
    }

    private Key generateKey(String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (secretKey == null || "".equals(secretKey)) {
            secretKey = defaultSecretKey;
        }
        byte[] key = (secretKey).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        
        //key = Arrays.copyOf(key, 16); // use only the first 128 bit
        
        byte[] cutKey = new byte[16];
        System.arraycopy(key, 0, cutKey, 0, 16);
        
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128); // 192 and 256 bits may not be available
        return new SecretKeySpec(cutKey, ALGORITHM);
    }

    private final String asHexString(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    private final byte[] toByteArray(String hexString) {
        int arrLength = hexString.length() >> 1;
        byte buf[] = new byte[arrLength];
        for (int ii = 0; ii < arrLength; ii++) {
            int index = ii << 1;
            String l_digit = hexString.substring(index, index + 2);
            buf[ii] = (byte) Integer.parseInt(l_digit, 16);
        }
        return buf;
    }
    
    
    //단방향 암호화
   	public static String setHashEnc(String msg) {
   		String result = "";
   		
   		try {
   			MessageDigest md = MessageDigest.getInstance(HASHALGORITHM);
	        md.update(msg.getBytes());
	        byte byteData[] = md.digest();
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        result = sb.toString();
	        
   		}catch (Exception e) {
   			LOGGER.error("setHashEnc Exception : " + e.toString());
   		}
   		
   		return result; 
   	}
   	
    //암호화
	public static String setEncrypt(String msg, String key) {
		
		String result = "";
		
		try {
			CryptoUtil aes = new CryptoUtil(key);
			 
			if(!"".equals(msg) && msg != null) {
				result = aes.encrypt(msg);
			}
				
		}catch (Exception e) {
			LOGGER.error("setEncrypt Exception : " + e.toString());
		}
		
		return result; 
	}

	//복호화
	public static String setDecrypt(String msg, String key)  {
		
		
		String result = "";
		try {
			CryptoUtil aes = new CryptoUtil(key);
			
			if(!"".equals(msg) && msg != null) {
				result = aes.decrypt(msg);
			}
				
		}catch (Exception e) {
			LOGGER.error("setDecrypt Exception : " + e.toString());
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
}
