package com.eugenebutovka.test.pay_integration.controller

import com.eugenebutovka.test.pay_integration.ui.page.CheckStatusPageRenderer
import com.eugenebutovka.test.pay_integration.ui.page.MainPageRenderer
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/")
class UiController(
    val mainPageRenderer: MainPageRenderer,
    val checkStatusPageRenderer: CheckStatusPageRenderer
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping(
        value = ["", "/"],
        produces = [MediaType.TEXT_HTML_VALUE]
    )
    @Operation(
        summary = "Renders main page HTML"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Page rendered"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    fun getMainPage(): String {
        return mainPageRenderer.renderPage()
    }

    @GetMapping(
        value = ["/paymentStatus", "/paymentStatus/{statusParam}"],
        produces = [MediaType.TEXT_HTML_VALUE]
    )
    @Operation(
        summary = "Renders payment status page HTML"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Page rendered"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    fun getCheckStatusPage(@PathVariable(value = "statusParam") statusParam: String?): String {
        logger.info { "payment status $statusParam" }
        val message = when (statusParam) {
            "success" -> "покупка успешно оплачена"
            "failure" -> "покупка отклонена"
            else -> "покупка еще не оплачена"
        }
        return checkStatusPageRenderer.renderPage(message, false)
    }
}
