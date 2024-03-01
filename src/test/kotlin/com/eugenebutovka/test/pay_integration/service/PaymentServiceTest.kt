package com.eugenebutovka.test.pay_integration.service

import com.eugenebutovka.test.pay_integration.config.PaymentServiceConfigProps
import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.web.client.postForEntity

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

class PaymentServiceTest {
    private val paymentRestTemplate = mockk<RestTemplate>()
    private val paymentServiceConfigProps = mockk<PaymentServiceConfigProps>(relaxed = true)

    private val paymentService = PaymentService(paymentRestTemplate,paymentServiceConfigProps)

    private val mockOkPaymentResponse: PaymentResponse = PaymentResponse(
        "PAYMENT",
        PaymentResponseData(
            "111",
            "111",
            "CARD",
            "2022-04-15T10:54:03.633Z",
            PaymentResponseDataSession(
                "2022-04-15T10:54:03.633Z", "2022-05-15T10:54:03.633Z", "http://1.1.1.1"
            )
        )
    )

    @Test
    fun `startPayment returns redirect url successfully when external api respond ok`() {
        every { paymentRestTemplate.postForEntity<PaymentResponse?>(any<String>(), any()) }returns
                ResponseEntity(mockOkPaymentResponse, HttpStatus.OK)
        val expected = "http://1.1.1.1"

        val actual = paymentService.startPayment()
        assertEquals(expected, actual)
    }

    @Test
    fun `startPayment returns null when external api respond is empty`() {
        every { paymentRestTemplate.postForEntity<PaymentResponse?>(any<String>(), any()) } returns
                ResponseEntity(null, HttpStatus.BAD_REQUEST)
        val expected = null

        val actual = paymentService.startPayment()
        assertEquals(expected, actual)
    }

    @Test
    fun `startPayment throws when external api respond errors out`() {
        every { paymentRestTemplate.postForEntity<PaymentResponse?>(any<String>(), any()) } throws
                RestClientException("error")

        assertThrows<RestClientException> {  paymentService.startPayment() }
    }
}
