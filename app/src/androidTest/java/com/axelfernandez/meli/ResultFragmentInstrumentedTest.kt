package com.axelfernandez.meli

import android.os.Bundle
import androidx.constraintlayout.utils.widget.MockView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.axelfernandez.meli.ui.homeFragment.HomeFragment
import com.axelfernandez.meli.ui.resultFragment.ResultFragment
import org.junit.Test

class ResultFragmentInstrumentedTest {

    @Test
    fun testDetailFragmentLoading(){

        val args =  Bundle()
        args.putString("query", "fakeId")

        launchFragmentInContainer<ResultFragment>(
            fragmentArgs = args,
            themeResId = R.style.Theme_Meli)

        Espresso.onView(ViewMatchers.withId(R.id.searching_text))
            .check(ViewAssertions.matches(ViewMatchers.withText("Estamos buscando los mejores productos")))
    }

    @Test
    fun testDetailFragmentResult(){

        val args =  Bundle()
        args.putString("query", "fakeId")

        launchFragmentInContainer<ResultFragment>(
            fragmentArgs = args,
            themeResId = R.style.Theme_Meli)

        Espresso.onView(ViewMatchers.withId(R.id.searching_text))
            .check(ViewAssertions.matches(ViewMatchers.withText("Estamos buscando los mejores productos")))
    }
}