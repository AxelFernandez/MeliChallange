package com.axelfernandez.meli

import android.os.Bundle
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.axelfernandez.meli.ui.homeFragment.HomeFragment
import com.axelfernandez.meli.ui.itemDetail.ItemDetailFragment


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 *  Espresso Tests
 */
class HomeFragmentInstrumentedTest {

    @Test
    fun testHomeFragment(){
        launchFragmentInContainer<HomeFragment>(themeResId = R.style.Theme_Meli)
        onView(withId(R.id.title)).check(matches(withText("Aqui encontrarás los mejores productos")))
    }

    @Test
    fun testBlankFieldShowError(){
        launchFragmentInContainer<HomeFragment>(themeResId = R.style.Theme_Meli)
        onView(withId(R.id.button_search)).perform(click())
        onView(withId(R.id.text_search)).check(matches(hasErrorText("Debes Buscar algo antes, animate!")))
    }



}