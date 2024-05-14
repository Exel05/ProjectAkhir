package com.xellagon.projectakhir.data.kotpref

import com.chibatching.kotpref.KotprefModel

object Kotpref : KotprefModel() {
    var id by nullableStringPref()
    var username by nullableStringPref()

    var isDarkMode by booleanPref(true)
}