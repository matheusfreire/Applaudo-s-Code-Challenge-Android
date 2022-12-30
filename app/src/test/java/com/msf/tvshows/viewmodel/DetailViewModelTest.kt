package com.msf.tvshows.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.msf.tvshows.model.detail.DetailResponse
import com.msf.tvshows.network.ResultWrapper
import com.msf.tvshows.usecase.DetailUseCase
import com.msf.tvshows.util.StubFactory
import com.msf.tvshows.util.TestCoroutineRule
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong

@OptIn(ExperimentalCoroutinesApi::class)
internal class DetailViewModelTest {
    private val useCase: DetailUseCase = mockk(relaxed = true)
    private lateinit var viewModel: DetailViewModel

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    private val testCoroutineRule = TestCoroutineRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(useCase)
    }

    @Test
    fun `GIVEN viewmodel is initialized THEN uiState should return state loading`() {
        runTest {
            viewModel.uiState.first() shouldNotBe null
            viewModel.uiState.first().shouldBeInstanceOf<UiState.Loading>()
        }
    }

    @Test
    fun `GIVEN id is valid WHEN getDetail is called THEN should livedata be update to LOADED`() {
        runTest {
            every { useCase.invoke(any(), any(), any(), any()) } answers {
                lastArg<(ResultWrapper.Success<DetailResponse>) -> ResultWrapper.Success<DetailResponse>>().invoke(ResultWrapper.Success(StubFactory.detailResponse))
            }

            testDispatcher.pauseDispatcher()
            viewModel.getDetail(anyLong())

            viewModel.uiState.first().shouldBeInstanceOf<UiState.Loading>()
            testDispatcher.resumeDispatcher()

            viewModel.uiState.first().shouldBeInstanceOf<UiState.Loaded<DetailResponse>>()
            val loadedValue = viewModel.uiState.first() as UiState.Loaded<DetailResponse>
            loadedValue.value shouldNotBe null
            loadedValue.value.id shouldBe StubFactory.detailResponse.id
        }
    }

    @Test
    fun `GIVEN anyLong for parameter WHEN getDetail is called AND return an error THEN should flow be update to ERROR`() {
        runTest {
            every { useCase.invoke(any(), any(), any(), any()) } answers {
                lastArg<(ResultWrapper.GenericError) -> ResultWrapper.GenericError>().invoke(ResultWrapper.GenericError(400, "error"))
            }

            viewModel.getDetail(anyLong())

            viewModel.uiState.first().shouldBeInstanceOf<UiState.Error>()
            val errorValue = viewModel.uiState.first() as UiState.Error
            errorValue shouldNotBe null
            errorValue.message shouldBe "error"
        }
    }

    @Test
    fun `GIVEN anyLong for parameter WHEN getDetail is called AND return an error without message THEN should flow be update to ERROR with empty message`() {
        runTest {
            every { useCase.invoke(any(), any(), any(), any()) } answers {
                lastArg<(ResultWrapper.GenericError) -> ResultWrapper.GenericError>().invoke(ResultWrapper.GenericError(400))
            }

            viewModel.getDetail(anyLong())

            viewModel.uiState.first().shouldBeInstanceOf<UiState.Error>()
            val errorValue = viewModel.uiState.first() as UiState.Error
            errorValue shouldNotBe null
            errorValue.message.isEmpty() shouldBe true
            errorValue.message shouldBe ""
        }
    }

    @Test
    fun `GIVEN anyLong for parameter WHEN getDetail is called AND return an NetworkError THEN should flow be update to EMPTY`() {
        runTest {
            every { useCase.invoke(any(), any(), any(), any()) } answers {
                lastArg<(ResultWrapper.NetworkError) -> ResultWrapper.NetworkError>().invoke(ResultWrapper.NetworkError)
            }

            viewModel.getDetail(anyLong())

            viewModel.uiState.first().shouldBeInstanceOf<UiState.Empty>()
        }
    }
}