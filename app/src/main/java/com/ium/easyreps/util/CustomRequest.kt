package com.ium.easyreps.util

import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import java.nio.charset.Charset
import kotlin.collections.HashMap


class CustomRequest<T>(
    url: String,
    private val clazz: Class<T>,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : Request<T>(Method.GET, url, errorListener) {
    private val gson = Gson()

    override fun getHeaders(): MutableMap<String, String> {
        var headers = super.getHeaders()

        if (headers == null
            || headers.isEmpty()
        ) {
            headers = HashMap()
        }

        //ServerRequest.addSessionCookie(headers)

        return headers
    }

    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        val json = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        Log.d("PARSE", response.toString())

        if (response != null) {
            Log.d("PARSE", "entro if")
            //ServerRequest.checkSessionCookie(response.headers)
        }

        /*mHeaders?.putIfAbsent(
            "Cookie",
            response?.headers?.get("Set-Cookie")?.subSequence(11, 43) as String
        )*/

        return Response.success(
            gson.fromJson(json, clazz),
            HttpHeaderParser.parseCacheHeaders(response)
        )
    }
}