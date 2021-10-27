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
// 实现 LoadTimeWeaverAware 接口是因为需要 SSH 正向代理需要在EntityManagerFactory加载前运行
public class SSHConfiguration implements LoadTimeWeaverAware {

    private final Session session;

    public SSHConfiguration(SSHProperties sshProperties) {
        Session session = null;
        try {
            // 可以自行为 JSch 添加日志，需要实现 com.jcraft.jsch.Logger 接口
            // JSch.setLogger(new JSchLogger())
            session = new JSch().getSession(sshProperties.getUsername(), sshProperties.getHost(), sshProperties.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(sshProperties.getPassword());
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
    // 配置销毁时，断开 SSH 链接
    public void disconnect() {
        if (session != null) {
            session.disconnect();
        }
    }

    @Override
    public void setLoadTimeWeaver(LoadTimeWeaver loadTimeWeaver) {

    }

}
