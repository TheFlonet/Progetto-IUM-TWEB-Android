package com.ium.easyreps.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ium.easyreps.model.Course
import com.ium.easyreps.model.PrivateLesson
import com.ium.easyreps.model.Teacher
import com.ium.easyreps.view.CoursesList
import com.ium.easyreps.util.Day

class TabLessonsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
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
        val courses1 = arrayListOf(
            PrivateLesson(Course(1, "course 1"), Teacher(9, "pippo", "john"), Day.MON, 15),
            PrivateLesson(Course(5, "course 2"), Teacher(6, "pluto", "don"), Day.MON, 16),
            PrivateLesson(Course(8, "course 3"), Teacher(5, "paperino", "ciccio"), Day.MON, 17),
            PrivateLesson(Course(2, "course 4"), Teacher(1, "topolino", "prova"), Day.MON, 18)
        )
        val courses2 = arrayListOf(
            PrivateLesson(Course(1, "course 5"), Teacher(9, "pippo", "john"), Day.TUE, 15),
            PrivateLesson(Course(5, "course 6"), Teacher(6, "pluto", "don"), Day.TUE, 16),
            PrivateLesson(Course(8, "course 7"), Teacher(5, "paperino", "ciccio"), Day.TUE, 17),
            PrivateLesson(Course(2, "course 8"), Teacher(1, "topolino", "prova"), Day.TUE, 18)
        )
        val courses3 = arrayListOf(
            PrivateLesson(Course(1, "course 9"), Teacher(9, "pippo", "john"), Day.WEN, 15),
            PrivateLesson(Course(5, "course 10"), Teacher(6, "pluto", "don"), Day.WEN, 16),
            PrivateLesson(Course(8, "course 11"), Teacher(5, "paperino", "ciccio"), Day.WEN, 17),
            PrivateLesson(Course(2, "course 12"), Teacher(1, "topolino", "prova"), Day.WEN, 18)
        )
        val courses4 = arrayListOf(
            PrivateLesson(Course(1, "course 13"), Teacher(9, "pippo", "john"), Day.THU, 15),
            PrivateLesson(Course(5, "course 14"), Teacher(6, "pluto", "don"), Day.THU, 16),
            PrivateLesson(Course(8, "course 15"), Teacher(5, "paperino", "ciccio"), Day.THU, 17),
            PrivateLesson(Course(2, "course 16"), Teacher(1, "topolino", "prova"), Day.THU, 18)
        )
        val courses5 = arrayListOf(
            PrivateLesson(Course(1, "course 17"), Teacher(9, "pippo", "john"), Day.FRI, 15),
            PrivateLesson(Course(5, "course 18"), Teacher(6, "pluto", "don"), Day.FRI, 16),
            PrivateLesson(Course(8, "course 19"), Teacher(5, "paperino", "ciccio"), Day.FRI, 17),
            PrivateLesson(Course(2, "course 20"), Teacher(1, "topolino", "prova"), Day.FRI, 18)
        )

        fragments = arrayListOf(
            CoursesList(courses1),
            CoursesList(courses2),
            CoursesList(courses3),
            CoursesList(courses4),
            CoursesList(courses5)
        ) // TODO valutare che parametro passare (lista con i corsi del giorno, nome del giorno per fare la query, lista completa e norme del giorno per selezionare i corsi utili)
    }
}