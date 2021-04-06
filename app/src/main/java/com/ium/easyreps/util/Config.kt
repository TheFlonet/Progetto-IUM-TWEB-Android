package com.ium.easyreps.util

object Config {
    const val ip = "http://10.0.2.2"
    const val port = 8080
    const val servlet = "ServletMain"
    const val login = "login"
    const val logout = "logout"
    const val getReservations = "getprenotazioniutente"
    const val getActiveReservations = "getprenotazioniattive"
    const val getPrivateLessons = "getdocenze"
    const val setReservationDone = "prenotazioneeffettuata"
    const val cancel = "disdireprenotazione"
    const val book = "creaprenotazione"
    var session: String? = null
}