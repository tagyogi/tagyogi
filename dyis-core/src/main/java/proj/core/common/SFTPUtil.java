package proj.core.common;

import com.jcraft.jsch.*;

import java.io.*;

/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>SFTPUtil.java (SFTP Util)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	: 
 * modify-date  :
 * description  :
 */
public class SFTPUtil {
    //private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static Session session = null;
    private static Channel channel = null;
    private static ChannelSftp channelSftp = null;

    // SFTP 서버연결
    public static void init(String url, String user, String password) {

        //JSch 객체 생성
        JSch jsch = new JSch();
        try {
            //세션객체 생성 ( user , host, port )
            session = jsch.getSession(user, url);

            //password 설정
            session.setPassword(password);

            //세션관련 설정정보 설정
            java.util.Properties config = new java.util.Properties();

            //호스트 정보 검사하지 않는다.
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            //접속
            session.connect();

            //sftp 채널 접속
            channel = session.openChannel("sftp");
            channel.connect();

        } catch (JSchException e) {
            e.toString();
        }

        channelSftp = (ChannelSftp) channel;
    }

    // 단일 파일 업로드
    public static void upload(String dir, File file) {
        FileInputStream in = null;

        try { //파일을 가져와서 inputStream에 넣고 저장경로를 찾아 put
            in = new FileInputStream(file);
            channelSftp.cd(dir);
            channelSftp.put(in, file.getName());
        } catch (SftpException se) {
            se.toString();
        } catch (FileNotFoundException fe) {
            fe.toString();
        } finally {
            try {
                in.close();
            } catch (IOException ioe) {
                ioe.toString();
            }
        }
    }

    // 단일 파일 다운로드
    public static void download(String url, String user, String password, String dir, String fileNm, String saveDir, String saveFileNm) {
        init(url, user, password);

        InputStream in = null;
        FileOutputStream out = null;
        try {
            channelSftp.cd(dir);
            in = channelSftp.get(fileNm);
        } catch (SftpException e) {
            e.toString();
        }
        try {
            out = new FileOutputStream(new File(saveDir + File.separator + saveFileNm));
            int i;
            if (in != null) {
                while ((i = in.read()) != -1) {
                    out.write(i);
                }
            }
        } catch (IOException e) {
            e.toString();
        } finally {
            try {
                out.close();
                if (in != null) in.close();
            } catch (IOException e) {
                e.toString();
            }
        }

        disconnect();
    }

    // 파일서버와 세션 종료
    public static void disconnect() {
        channelSftp.quit();
        session.disconnect();
    }

}
