package com.ium.easyreps.util

object Config {
    const val ip = "http://10.0.2.2"
    const val port = 8080
    const val servlet = "ServletMain"
    const val login = "login"
    const val logout = "logout"
    const val checkSession = "checksession"
    const val getReservations = "getprenotazioniutente"
    const val getActiveReservations = "stampaprenotazioniattive" // fornisce TUTTE le prenotazioni attive
    const val getPrivateLessons = "getdocenze" //ottiene tutti i corsi (per visualizzare schermata inizio getdocenze - stampaprenotazioniattive)
    const val cancel = "disdireprenotazione"
    const val book = "effettuareprenotazione"
    lateinit var session: String
}