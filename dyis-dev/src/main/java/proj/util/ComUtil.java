/**
 * @Class Name  : ComUtil.java
 * @Description : Com 관련 정보
 * @Modification Information
 *
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *   2018.12.27   BSG       최초생성
 *
 * @author 공통 서비스 개발팀 BSG
 * @since 2018.12.27
 * @version 1.0
 * @see
 *
 */

package proj.util;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ComUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComUtil.class);
	
	//MAC 주소 조회
	public static String getMacAddr(HttpServletRequest request) throws SocketException {
	    //HashMap<String, Object> map = new HashMap<String, Object>();
	    
	    String result = "";
    	InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
	    	byte[] mac = network.getHardwareAddress();
	    
	    	StringBuilder sb = new StringBuilder();
	    	for( int i  = 0; i < mac.length; i++) {
	    		sb.append(String.format("%02X%s",  mac[i], (i < mac.length - 1)? "-" :""));
	    	}
	    	result = sb.toString();
	    	
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
    	
    	
	    return result;
	}
	
	//단방햔 암호화
	public static String setShaOneWayEnc(String sParam) {
		String getEnc = "";
		StringBuffer sb = new StringBuffer();
    	try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			md.update(sParam.getBytes());
			byte[] msgStr = md.digest();
			
			for(int x = 0; x <msgStr.length; x++){
				byte tmpStrByte = msgStr[x];
				String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);
				
				sb.append(tmpEncTxt);
				
			}
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
    	getEnc = sb.toString();
		
	    return getEnc;
	}
	
	//단방햔 암호화(salt 추가)
	public static String setShaOneWayEncAddSalt(String sParam, String sSalt) {
		String getEnc = "";
		StringBuffer sb = new StringBuffer();
    	try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			//salt 추가
			String setEncParam = sSalt + sParam;
			
			md.update(setEncParam.getBytes());
			byte[] msgStr = md.digest();
			
			for(int x = 0; x <msgStr.length; x++){
				byte tmpStrByte = msgStr[x];
				String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);
				
				sb.append(tmpEncTxt);
				
			}
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
    	getEnc = sb.toString();
		
	    return getEnc;
	}
	
	
	//암호복호화 
	public static String getDecrypt(String sEncParam, String sSalt) {
		String result = "";
		
		try {
            final int keySize = 256;
            final int ivSize = 128;

            // 텍스트를 BASE64 형식으로 디코드 한다.
            byte[] ctBytes = Base64.decodeBase64(sEncParam.getBytes("UTF-8"));

            // 솔트를 구한다. (생략된 8비트는 Salted__ 시작되는 문자열이다.)
            byte[] saltBytes = Arrays.copyOfRange(ctBytes, 8, 16);
            
            // 암호화된 테스트를 구한다.( 솔트값 이후가 암호화된 텍스트 값이다.)
            byte[] ciphertextBytes = Arrays.copyOfRange(ctBytes, 16, ctBytes.length);
            
            // 비밀번호와 솔트에서 키와 IV값을 가져온다.
            byte[] key = new byte[keySize / 8];
            byte[] iv = new byte[ivSize / 8];
            EvpKDF(sSalt.getBytes("UTF-8"), keySize, ivSize, saltBytes, key, iv);

            // 복호화
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            
            byte[] recoveredPlaintextBytes = cipher.doFinal(ciphertextBytes);
            
            return new String(recoveredPlaintextBytes);
        } catch (Exception e) {
        	LOGGER.error("ComUtil Exception : " + e);
            e.printStackTrace();
        }
		
	    return result;
	}
	
	private static byte[] EvpKDF(byte[] password, int keySize, int ivSize, byte[] salt, byte[] resultKey, byte[] resultIv) throws NoSuchAlgorithmException {
        return EvpKDF(password, keySize, ivSize, salt, 1, "MD5", resultKey, resultIv);
    }

    private static byte[] EvpKDF(byte[] password, int keySize, int ivSize, byte[] salt, int iterations, String hashAlgorithm, byte[] resultKey, byte[] resultIv) throws NoSuchAlgorithmException {
        keySize = keySize / 32;
        ivSize = ivSize / 32;
        int targetKeySize = keySize + ivSize;
        byte[] derivedBytes = new byte[targetKeySize * 4];
        int numberOfDerivedWords = 0;
        byte[] block = null;
        MessageDigest hasher = MessageDigest.getInstance(hashAlgorithm);
        while (numberOfDerivedWords < targetKeySize) {
            if (block != null) {
                hasher.update(block);
            }
            hasher.update(password);
            // Salting
            block = hasher.digest(salt);
            hasher.reset();
            // Iterations : 키 스트레칭(key stretching)
            for (int i = 1; i < iterations; i++) {
                block = hasher.digest(block);
                hasher.reset();
            }
            System.arraycopy(block, 0, derivedBytes, numberOfDerivedWords * 4, Math.min(block.length, (targetKeySize - numberOfDerivedWords) * 4));
            numberOfDerivedWords += block.length / 4;
        }
        System.arraycopy(derivedBytes, 0, resultKey, 0, keySize * 4);
        System.arraycopy(derivedBytes, keySize * 4, resultIv, 0, ivSize * 4);
        return derivedBytes; // key + iv
    }
    
    //폴더 생성
    public static void folderCreate(String[] folders) {
    	
    	for(int i = 0; i < folders.length; i++) {
    		//LOGGER.debug("folderCreate : " + folders[i]);
    		File Folder = new File(folders[i]);
			
			if(!Folder.exists()) Folder.mkdir(); //폴더 생성 
			
    	}
    };
}
