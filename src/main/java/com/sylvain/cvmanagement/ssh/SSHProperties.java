package com.sylvain.cvmanagement.ssh;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString(exclude="password")
@ConfigurationProperties(prefix="ssh")
public class SSHProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private Forward forward;

    @Getter
    @Setter
    @ToString
    public static class Forward {

        private String fromHost;
        private Integer fromPort;
        private String toHost;
        private Integer toPort;

    }
}
