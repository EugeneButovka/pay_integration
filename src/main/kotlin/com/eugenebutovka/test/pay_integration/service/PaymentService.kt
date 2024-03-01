package com.eugenebutovka.test.pay_integration.service

import com.eugenebutovka.test.pay_integration.config.PaymentServiceConfigProps
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

@Service
class PaymentService(
    val paymentRestTemplate: RestTemplate,
    val paymentServiceConfigProps: PaymentServiceConfigProps
) {
    private val logger = KotlinLogging.logger {}

    @Retryable(value = [RestClientException::class], maxAttempts = 3, backoff = Backoff(random = true))
    fun startPayment(): String? {
        return runBlocking(Dispatchers.IO) {
            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                accept = listOf(MediaType.APPLICATION_JSON)
                add("X-Api-Key", paymentServiceConfigProps.apiKey)
            }
            val reqBody = getSampleRequestBody(paymentServiceConfigProps.serverUrl)
            val request = HttpEntity(reqBody,headers)

            val res = paymentRestTemplate.postForEntity<PaymentResponse?>("/",request)
            logger.info { "payment result: " + res.body }
            logger.info { "payment result code: " + res.statusCode.toString() }
            logger.info { "payment result redirect url: " + res.body?.data?.session?.redirect_url }

            res.body?.data?.session?.redirect_url
        }
    }
}

private fun getSampleRequestBody(callbackUrl: String) = object {
    val payment_method = "CARD"
    val merchant_payment_id = "P_001"
    val merchant_customer_id = "U_001"
    val amount = "9.99"
    val currency_code = "USD"
    val email = "john_doe@upgate.com"
    val language = "en-us"
    val country_code = "US"
    val forced_3d = true
    val success_url = callbackUrl + "/paymentStatus/success"
    val failure_url = callbackUrl + "/paymentStatus/failure"
    val shop_name = "my shop"
    val shop_url = callbackUrl
    val products = arrayOf(
        object {
            val merchant_product_id = "R_001"
            val product_type = "SALE"
            val product_price = "9.99"
            val product_name = "Test product name"
            val product_description = "Test product description"
        }
    )
}

data class PaymentResponse(
    val type: String,
    val data: PaymentResponseData
)

data class PaymentResponseData(
    val payment_id: String,
    val payment_type: String,
    val payment_method: String,
    val created_at: String,
    val session: PaymentResponseDataSession
)

data class PaymentResponseDataSession(
    val created_at:String,
    val expires_at:String,
    val redirect_url:String,
)
