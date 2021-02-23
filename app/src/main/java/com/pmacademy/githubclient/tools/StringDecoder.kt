package com.pmacademy.githubclient.tools

import android.util.Base64
import java.util.*

class StringDecoder {
    fun decodeText(encodeText: String, encodeType: String): String {
        return if (encodeType.toLowerCase(Locale.ROOT) == "base64") {
            String(Base64.decode(encodeText, Base64.DEFAULT))
        } else ""
    }
}