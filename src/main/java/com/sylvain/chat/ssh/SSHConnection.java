package com.sylvain.chat.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;
@Component
public class SSHConnection {
    //private final static String S_PASS_PHRASE = "";
    @Value("${com.ssh.service-local}")
    private int serviceLocal;
    @Value("${com.ssh.service-remote}")
    private int serviceRemote;
    @Value("${com.ssh.port}")
    private int port;
    @Value("${com.ssh.username}")
    private String username;
    @Value("${com.ssh.password}")
    private String password;
    @Value("${com.ssh.host}")
    private String host;

    private final static String MYSQL_REMOTE_SERVER = "localhost";

    private Session session; //represents each ssh session

    public void closeSSH (){
        session.disconnect();
    }

    public void initSSHConnection () throws Throwable {
        JSch jsch = new JSch();
        //jsch.setKnownHosts(S_PATH_FILE_KNOWN_HOSTS);
        //jsch.addIdentity(S_PATH_FILE_PRIVATE_KEY);

        session = jsch.getSession(username, host, port);
        session.setPassword(password);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect(); //ssh connection established!

        //by security policy, you must connect through a forwarded port
        session.setPortForwardingL(serviceLocal, MYSQL_REMOTE_SERVER, serviceRemote);
    }
}
