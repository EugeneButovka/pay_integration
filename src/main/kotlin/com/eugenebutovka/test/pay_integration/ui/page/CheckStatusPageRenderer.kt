package com.eugenebutovka.test.pay_integration.ui.page

import com.eugenebutovka.test.pay_integration.config.UiConfigProps
import com.eugenebutovka.test.pay_integration.ui.customFooter
import com.eugenebutovka.test.pay_integration.ui.customHeader
import com.eugenebutovka.test.pay_integration.ui.writePage
import kotlinx.html.*
import org.springframework.stereotype.Component

@Component
class CheckStatusPageRenderer(
    val uiConfigProps: UiConfigProps
) {
    fun renderPage(message: String, shouldRefresh: Boolean): String {
        return writePage {
            head {
                title("Payment Status")
                link(href = "/css/bootstrap.min.css", rel = "stylesheet")
                script(src = "/js/jquery.min.js") {}
                script(src = "/js/bootstrap.min.js") {}
            }
            body {
                customHeader()
                script {
                    +"""
                    const refreshRate = ${uiConfigProps.refreshRate}
                    const isEnabled = $shouldRefresh
                    if (isEnabled) {
                        console.log("page will be refreshed in " + refreshRate + "ms")
                        setTimeout(() =>{location.reload(true)}, refreshRate);
                    }
                """.trimIndent()
                }
                div(classes = "d-flex align-items-center justify-content-center") {
                    style = "height: 50vh;"
                    h2 {
                        +"Payment status: $message"
                    }
                }
                customFooter()
            }
        }
    }
}
