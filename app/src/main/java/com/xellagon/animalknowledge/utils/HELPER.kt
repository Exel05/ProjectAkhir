package com.xellagon.animalknowledge.utils

import android.util.Patterns

fun String.emailChecked() = !Patterns.EMAIL_ADDRESS.matcher(this).matches()