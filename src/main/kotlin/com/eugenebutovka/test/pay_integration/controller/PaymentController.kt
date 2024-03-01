package com.eugenebutovka.test.pay_integration.controller

import com.eugenebutovka.test.pay_integration.service.PaymentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestClientException

@RestController
@RequestMapping("/payment")
class PaymentController(
    val paymentService: PaymentService
) {
    private val logger = KotlinLogging.logger {}

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
        value = [""],
        headers = ["Accept=*/*"],
        consumes = [MediaType.ALL_VALUE],
        produces = [MediaType.ALL_VALUE]
    )
    @Operation(
        summary = "Starts a payment with Upgate",
        description = "Starts a payment with Upgate API https://sandbox.upgate.com/v1/sale"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Payment initiated"),
        ApiResponse(responseCode = "400", description = "Bad payment request"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    fun pay(httpServletResponse: HttpServletResponse) {
        logger.info { "payment callback" }

        val redirect = try {
            paymentService.startPayment()
        } catch (e: RestClientException) {
            null
        }
        if (redirect != null) httpServletResponse.sendRedirect(redirect)
    }
}
