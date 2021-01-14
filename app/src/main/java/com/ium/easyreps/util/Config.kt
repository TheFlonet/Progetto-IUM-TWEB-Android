package com.ium.easyreps.util

object Config {
    const val ip = "10.0.2.2"
    const val port = 8080
    const val servlet = "ServletMain"
    const val login = "login"
    const val logout = "logout"
    const val getReservations = "getprenotazioniutente"
    const val getActiveReservations = "stampaprenotazioniattive"
    const val cancel = "disdireprenotazione"
    const val book = "effettuareprenotazione"
}

//parametro action:
// login
// getPrenotazioneUtente unico array json con stato prenotazione
// stampaPrenotazioniAttive -> tutte le prenotazioni attive in quel momento
// per lista iniziale -> prendo docenti e docenze, creo combinazioni e sottraggo prenotazioni attive
// giorno passato come LUN, MAR ...
// per richieste di post metto gli id degli oggetti