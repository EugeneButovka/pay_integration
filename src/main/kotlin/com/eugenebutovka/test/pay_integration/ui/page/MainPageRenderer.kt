package com.eugenebutovka.test.pay_integration.ui.page

import com.eugenebutovka.test.pay_integration.ui.customFooter
import com.eugenebutovka.test.pay_integration.ui.customHeader
import com.eugenebutovka.test.pay_integration.ui.writePage
import kotlinx.html.*
import org.springframework.stereotype.Component

@Component
class MainPageRenderer {
    fun renderPage(): String {
        return writePage {
            head {
                title("Payment")
                link(href = "/css/bootstrap.min.css", rel = "stylesheet")
                script(src = "/js/jquery.min.js") {}
                script(src = "/js/bootstrap.min.js") {}
            }
            body {
                customHeader()

                div(
                    classes = "d-flex align-items-center justify-content-center"
                ) {
                    style = "height: 50vh;"
                    form(
                        action = "/payment",
                        encType = FormEncType.applicationXWwwFormUrlEncoded,
                        method = FormMethod.post,
                        classes = "d-flex"
                    ) {
                        button(classes = "btn btn-primary btn-lg btn-block", type = ButtonType.submit) { +"Оплатить" }
                    }
                }
                customFooter()
            }
        }
    }
}
