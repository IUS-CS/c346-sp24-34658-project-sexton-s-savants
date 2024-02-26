package com.quark.client.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonHelper {
    val gson: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
}