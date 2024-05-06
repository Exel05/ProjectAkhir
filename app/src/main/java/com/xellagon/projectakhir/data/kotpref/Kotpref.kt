package com.xellagon.projectakhir.data.kotpref

import com.chibatching.kotpref.KotprefModel

object Kotpref : KotprefModel() {
    var id by stringPref("")
    var username by stringPref("")
}