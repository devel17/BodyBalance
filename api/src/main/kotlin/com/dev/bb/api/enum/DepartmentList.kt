package com.dev.bb.api.enum

import com.fasterxml.jackson.annotation.JsonValue

enum class DepartmentList(@JsonValue val description: String, val id: String) {
    MAIN_OFFICE("Главный офис", "0"),
    MITINSKY("Сад на Митинской", "1"),
    LENINSKIY("Сад на Ленинском", "2"),
    KRASNOGORSK("Сад в Красногорске", "3"),
}