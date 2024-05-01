package com.xellagon.projectakhir.utils

import android.util.Patterns
import java.util.regex.Pattern

fun String.emailChecked() = !Patterns.EMAIL_ADDRESS.matcher(this).matches()