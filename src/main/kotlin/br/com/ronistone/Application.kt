package br.com.ronistone

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("br.com.ronistone")
        .start()
}

