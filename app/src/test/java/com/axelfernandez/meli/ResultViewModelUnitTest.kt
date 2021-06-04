package com.axelfernandez.meli

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.axelfernandez.meli.api.ApiHelper
import com.axelfernandez.meli.models.Item
import com.axelfernandez.meli.models.ItemResponse
import com.axelfernandez.meli.models.Page
import com.axelfernandez.meli.repository.ResultRepository
import com.axelfernandez.meli.ui.resultFragment.ResultViewModel
import com.axelfernandez.meli.utils.Resource
import com.axelfernandez.meli.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import java.lang.Exception
import java.lang.RuntimeException
import java.net.ConnectException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ResultViewModelUnitTest {



    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testResultViewModelEmpty() = runBlockingTest {
        val itemResponse = TestUtilities.getFakeItemResponse().copy("query",TestUtilities.getFakePage(), emptyList())

        val resultRepository = mock<ResultRepository>() {

            onBlocking { searchItems("query") }.doReturn(itemResponse)
        }

        val viewmodel = ResultViewModel(SavedStateHandle(HashMap()), resultRepository, testDispatcher)
        viewmodel.searchItem("query")

        assertEquals(Status.EMPTY, viewmodel.items.value?.status)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testResultViewModelError()= runBlockingTest {
        val resultRepository = mock<ResultRepository>() {

            onBlocking { searchItems("query") }.thenThrow(RuntimeException())
        }

        val viewmodel = ResultViewModel(SavedStateHandle(HashMap()), resultRepository, testDispatcher)
        viewmodel.searchItem("query")

        assertEquals(Status.ERROR, viewmodel.items.value?.status)

    }


    @ExperimentalCoroutinesApi
    @Test
    fun testResultViewModelSuccess()= runBlockingTest {
        val resultRepository = mock<ResultRepository>() {

            onBlocking { searchItems("query") }.doReturn(TestUtilities.getFakeItemResponse())
        }

        val viewmodel = ResultViewModel(SavedStateHandle(HashMap()), resultRepository, testDispatcher)
        viewmodel.searchItem("query")

        assertEquals(Status.SUCCESS, viewmodel.items.value?.status)

    }


    @Test
    fun testSavedStateQuery(){
        val resultRepository = mock<ResultRepository>()
        val viewmodel = ResultViewModel(SavedStateHandle(HashMap()), resultRepository, testDispatcher)


        viewmodel.querySaved = "queryToBeSaved"

        assertEquals(viewmodel.querySaved,"queryToBeSaved")
    }

    @Test
    fun testSavedStateTitleStatus(){
        val resultRepository = mock<ResultRepository>()
        val viewmodel = ResultViewModel(SavedStateHandle(HashMap()), resultRepository, testDispatcher)

        viewmodel.titleStatus = true

        assertTrue(viewmodel.titleStatus)
    }

}