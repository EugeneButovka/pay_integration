package com.eugenebutovka.test.pay_integration.ui

import kotlinx.html.HTML
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.*
import kotlinx.html.dom.serialize


inline fun writePage(crossinline block: HTML.() -> Unit): String {
    return createHTMLDocument().html {
        lang = "en"
        visit(block)
    }.serialize()
}


fun FlowContent.customHeader() {
    nav(classes = "navbar navbar-expand-lg navbar-dark bg-dark") {
        div(classes = "container-fluid") {
            a(href = "/", classes = "navbar-brand") { +"Test Upgate Payment Integration" }
        }
    }
}


fun FlowContent.customFooter() {
    footer(classes = "footer mt-auto py-3 bg-light fixed-bottom") {
        p(classes = "text-center") { +"Copyright 2024 Eugene Butovka - All Rights Reserved" }
    }
}

