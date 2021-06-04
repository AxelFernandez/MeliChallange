package com.axelfernandez.meli

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.axelfernandez.meli.repository.DetailRepository
import com.axelfernandez.meli.ui.itemDetail.ItemDetailViewModel
import com.axelfernandez.meli.utils.Status
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import java.lang.RuntimeException

class ItemDetailViewModelTest {
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
    fun getDetailSuccess() = runBlockingTest {
        val detailRepository = mock<DetailRepository>(){
            onBlocking { getItemDetail("123") } doReturn TestUtilities.getFakeItem()
        }
        val viewModel = ItemDetailViewModel(detailRepository,testDispatcher)
        viewModel.getDetail("123")
        assertEquals(Status.SUCCESS,viewModel.item.value?.status)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun getDetailError() = runBlockingTest {
        val detailRepository = mock<DetailRepository>(){
            onBlocking { getItemDetail("123") } doThrow RuntimeException()
        }
        val viewModel = ItemDetailViewModel(detailRepository,testDispatcher)
        viewModel.getDetail("123")
        assertEquals(Status.ERROR,viewModel.item.value?.status)

    }


    @ExperimentalCoroutinesApi
    @Test
    fun getDescriptionSuccess() = runBlockingTest {
        val detailRepository = mock<DetailRepository>(){
            onBlocking { getItemDetail("123") } doReturn TestUtilities.getFakeItem()
        }
        val viewModel = ItemDetailViewModel(detailRepository,testDispatcher)

        viewModel.getDescription("123")

        assertEquals(Status.SUCCESS,viewModel.description.value?.status)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun getDescriptionError() = runBlockingTest {
        val detailRepository = mock<DetailRepository>(){
            onBlocking { getItemDescription("123") } doThrow RuntimeException()
        }
        val viewModel = ItemDetailViewModel(detailRepository,testDispatcher)

        viewModel.getDescription("123")

        assertEquals(Status.ERROR,viewModel.description.value?.status)

    }


}