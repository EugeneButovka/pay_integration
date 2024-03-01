package com.eugenebutovka.test.pay_integration.controller

import com.eugenebutovka.test.pay_integration.service.PaymentService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payment")
class PaymentController(
    val paymentService: PaymentService
) {
    private val logger = KotlinLogging.logger {}

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
        ApiResponse(
            responseCode = "302",
            description = "Redirect to payment platform or in case of error back to the main page"
        ),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    fun pay(httpServletResponse: HttpServletResponse) {
        logger.info { "payment callback" }

        val redirect = paymentService.startPayment()
        logger.info { "redirect $redirect" }
        if (redirect != null) httpServletResponse.sendRedirect(redirect)
        else httpServletResponse.sendRedirect("")
    }
}
