package com.wzn.ablog.article.config;

import com.wzn.ablog.common.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.security.PublicKey;


@ConfigurationProperties(prefix = "rsakey")
@PropertySource("classpath:/application.yml")
@Data
public class RsaKeyConfig {
    private String publicKeyName;

    private PublicKey publicKey;

    @PostConstruct
    public void createRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(publicKeyName);
    }
}