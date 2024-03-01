package com.eugenebutovka.test.pay_integration.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties(
    UiConfigProps::class,
    PaymentServiceConfigProps::class,
)
class ConfigurationPropertiesConfiguration

@ConfigurationProperties(prefix = "ui")
data class UiConfigProps(
    val refreshRate: Int
)

@ConfigurationProperties(prefix = "payment-service")
data class PaymentServiceConfigProps(
    val url: String,
    val timeout: Int,
    val apiKey: String,
    val serverUrl: String,
)

