package com.sylvain.cvmanagement.ssh;

import javax.annotation.PreDestroy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.instrument.classloading.LoadTimeWeaver;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableConfigurationProperties(SSHProperties.class)
@ConditionalOnProperty(prefix = "ssh", value = "enabled", havingValue = "true", matchIfMissing = false)
@Slf4j
// use LoadTimeWeaverAware because SSH proxy needs to execute before loading EntityManagerFactory
public class SSHConfiguration implements LoadTimeWeaverAware {

    private final Session session;

    public SSHConfiguration(SSHProperties sshProperties) {
        Session session = null;
        try {
            // by implementing 'com.jcraft.jsch.Logger' interface you can add log to JSch
            // JSch.setLogger(new JSchLogger())
            JSch jsch = new JSch();
            jsch.addIdentity(sshProperties.getPrivatekey());
            session = jsch.getSession(sshProperties.getUsername(), sshProperties.getHost(), sshProperties.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            SSHProperties.Forward forward = sshProperties.getForward();
            if (forward != null) {
                session.setPortForwardingL(forward.getFromHost(), forward.getFromPort(), forward.getToHost(), forward.getToPort());
                log.info("{}:{} -> {}:{}", forward.getFromHost(), forward.getFromPort(), forward.getToHost(), forward.getToPort());
            }
        } catch (JSchException e) {
            log.error("ssh " + sshProperties.getHost() + " failed.", e);
        }
        this.session = session;
    }

    @PreDestroy
    // disconnect SSH
    public void disconnect() {
        if (session != null) {
            session.disconnect();
        }
    }

    @Override
    public void setLoadTimeWeaver(LoadTimeWeaver loadTimeWeaver) {

    }

}
