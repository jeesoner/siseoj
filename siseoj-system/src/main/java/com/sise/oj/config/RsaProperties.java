package com.sise.oj.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
@Component
public class RsaProperties {

    @Value("${rsa.private_key}")
    private String privateKey;
}
