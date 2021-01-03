package com.ium.easyreps.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ium.easyreps.model.Course
import com.ium.easyreps.model.Reservation
import com.ium.easyreps.model.Teacher
import com.ium.easyreps.model.User
import com.ium.easyreps.view.HistoryList
import com.ium.easyreps.util.Day
import com.ium.easyreps.util.State

class TabHistoryAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private lateinit var fragments: ArrayList<Fragment>

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun initFragments() {
        // TODO rimuovere le liste quando i dati saranno presi da internet
        val active = arrayListOf(
            Reservation(
                1, Course(1, "corso1"), Teacher(1, "nome1", "cognome1"), User(
                    1, "utente1", "password1",
                    isLogged = true,
                    isAdmin = false
                ), State.ACTIVE, Day.MON, 15, 12.0
            )
        )
        val done = arrayListOf(
            Reservation(
                1, Course(1, "corso1"), Teacher(1, "nome1", "cognome1"), User(
                    1, "utente1", "password1",
                    isLogged = true,
                    isAdmin = false
                ), State.DONE, Day.MON, 15, 12.0
            )
        )
        val cancelled = arrayListOf(
            Reservation(
                1, Course(1, "corso1"), Teacher(1, "nome1", "cognome1"), User(
                    1, "utente1", "password1",
                    isLogged = true,
                    isAdmin = false
                ), State.CANCELLED, Day.MON, 15, 12.0
            )
        )

        fragments = arrayListOf(HistoryList(active), HistoryList(done), HistoryList(cancelled))
        // TODO valutare che parametro passare (lista con i corsi del giorno, nome del giorno per fare la query, lista completa e norme del giorno per selezionare i corsi utili)
    }
}
