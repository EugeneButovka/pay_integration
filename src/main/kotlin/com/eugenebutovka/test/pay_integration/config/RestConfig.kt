package com.eugenebutovka.test.pay_integration.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
@EnableRetry
class RestConfig(
    val paymentServiceConfigProps: PaymentServiceConfigProps
) {
    @Bean
    fun paymentRestTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder
            .rootUri(paymentServiceConfigProps.url)
            .setConnectTimeout(Duration.ofMillis(paymentServiceConfigProps.timeout.toLong()))
            .setReadTimeout(Duration.ofMillis(paymentServiceConfigProps.timeout.toLong()))
            .build()
    }
}
