package com.msf.tvshows.usecase

import com.msf.tvshows.core.CoroutinesContextProvider
import com.msf.tvshows.core.RequestWrapperImpl
import com.msf.tvshows.network.ResultWrapper
import com.msf.tvshows.repository.ShowRepository
import com.msf.tvshows.util.StubFactory
import com.msf.tvshows.util.TestCoroutineRule
import io.kotlintest.matchers.types.shouldBeSameInstanceAs
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong

@OptIn(ExperimentalCoroutinesApi::class)
internal class DetailUseCaseTest {
    private val repository: ShowRepository = mockk(relaxed = true)
    private val requestWrapper = RequestWrapperImpl()
    private val contextProvider: CoroutinesContextProvider = mockk(relaxed = true)

    private lateinit var useCase: DetailUseCase

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    private val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = DetailUseCase(
            repository = repository,
            contextProvider = contextProvider,
            requestWrapper = requestWrapper
        )
    }

    @Test
    fun `GIVEN repository is ok WHEN useCase runs THEN details is called`() {
        coEvery { repository.getShowDetail(anyLong()) } returns ResultWrapper.Success(StubFactory.detailResponse)
        runBlocking {
            useCase.run(anyLong())

            coVerify(exactly = 1) {
                repository.getShowDetail(anyLong())
            }
        }
    }

    @Test
    fun `GIVEN repository will response success WHEN useCase runs THEN should return success true`() {
        coEvery { repository.getShowDetail(anyLong()) } returns ResultWrapper.Success(StubFactory.detailResponse)
        runBlocking {
            val result = useCase.run(anyLong())

            coVerify(exactly = 1) {
                repository.getShowDetail(anyLong())
            }

            result.isFailure shouldBe false
            result.isSuccess shouldBe true
            result.success(StubFactory.detailResponse).success shouldBeSameInstanceAs StubFactory.detailResponse
        }
    }

    @Test
    fun `GIVEN repository will response generic error WHEN useCase runs THEN should return success true`() {
        coEvery { repository.getShowDetail(anyLong()) } returns ResultWrapper.GenericError(404, "error")
        runBlocking {
            val result = useCase.run(anyLong())

            coVerify(exactly = 1) {
                repository.getShowDetail(anyLong())
            }

            result.isFailure shouldBe true
            result.isSuccess shouldBe false
        }
    }

    @Test
    fun `GIVEN repository will response network error WHEN useCase runs THEN should return success true`() {
        coEvery { repository.getShowDetail(anyLong()) } returns ResultWrapper.NetworkError
        runBlocking {
            val result = useCase.run(anyLong())

            coVerify(exactly = 1) {
                repository.getShowDetail(anyLong())
            }

            result.isFailure shouldBe true
            result.isSuccess shouldBe false
        }
    }
}
