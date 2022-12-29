package com.msf.tvshows.repository.impl

import com.msf.tvshows.model.detail.DetailResponse
import com.msf.tvshows.model.list.ShowResponse
import com.msf.tvshows.network.ResultWrapper
import com.msf.tvshows.network.TvShowService
import com.msf.tvshows.repository.ShowRepository
import com.msf.tvshows.util.StubFactory
import com.msf.tvshows.util.TestCoroutineRule
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.matchers.types.shouldBeSameInstanceAs
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
internal class ShowRepositoryImplTest {
    private val showService: TvShowService = mockk(relaxed = true)
    private lateinit var showRepository: ShowRepository

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    private val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        showRepository = ShowRepositoryImpl(showService)
    }

    @Test
    fun `GIVEN service return a detail WHEN callDetail is called THEN should return ResultWrapper Success with data`() = testCoroutineRule.runBlockingTest {
        coEvery { showService.callDetail(anyLong()) } returns StubFactory.detailResponse

        val resultWrapper = showRepository.getShowDetail(anyLong())

        resultWrapper shouldNotBe null
        resultWrapper.shouldBeInstanceOf<ResultWrapper.Success<DetailResponse>>()
        (resultWrapper as ResultWrapper.Success<DetailResponse>).value shouldNotBe null
        resultWrapper.value.id shouldBe 1
        resultWrapper.value.languages.size shouldBe 2
        resultWrapper.value.popularity shouldBe 0.0
    }

    @Test
    fun `GIVEN service will throw an httpexception WHEN callDetail is called THEN should return ResultWrapper Generic Error`() = testCoroutineRule.runBlockingTest {
        val httpException = mockk<HttpException>(relaxed = true)
        coEvery { showService.callDetail(anyLong()) } throws httpException

        val resultWrapper = showRepository.getShowDetail(anyLong())

        resultWrapper shouldNotBe null
        resultWrapper.shouldBeInstanceOf<ResultWrapper.GenericError>()
        (resultWrapper as ResultWrapper.GenericError) shouldNotBe null
        resultWrapper.code shouldBe httpException.code()
        resultWrapper.error shouldBe httpException.message
    }

    @Test
    fun `GIVEN service will throw an exception WHEN callDetail is called THEN should return ResultWrapper Generic Error with code 999`() = testCoroutineRule.runBlockingTest {
        val exception = mockk<Exception>(relaxed = true)
        coEvery { showService.callDetail(anyLong()) } throws exception

        val resultWrapper = showRepository.getShowDetail(anyLong())

        resultWrapper shouldNotBe null
        resultWrapper.shouldBeInstanceOf<ResultWrapper.GenericError>()
        (resultWrapper as ResultWrapper.GenericError) shouldNotBe null
        resultWrapper.code shouldBe 999
        resultWrapper.error shouldBe "Something weird happens"
    }

    @Test
    fun `GIVEN service return a ShowResponse WHEN callShowsFiltered is called THEN should return ResultWrapper Success with data`() = testCoroutineRule.runBlockingTest {
        coEvery { showService.callShowsFiltered(anyString(), anyInt()) } returns StubFactory.showResponse

        val resultWrapper = showRepository.fetchShowList(anyString(), anyInt())

        resultWrapper shouldNotBe null
        resultWrapper.shouldBeInstanceOf<ResultWrapper.Success<ShowResponse>>()
        (resultWrapper as ResultWrapper.Success<ShowResponse>).value shouldNotBe null
        resultWrapper.value.totalResults shouldBe 1
        resultWrapper.value.results.size shouldBe 1
        resultWrapper.value.page shouldBe 1
        resultWrapper.value.totalPages shouldBe 1
        resultWrapper.value.shouldBeSameInstanceAs(StubFactory.showResponse)
    }

    @Test
    fun `GIVEN service will throw an httpexception WHEN fetchShowList is called THEN should return ResultWrapper Generic Error`() = testCoroutineRule.runBlockingTest {
        val httpException = mockk<HttpException>(relaxed = true)
        coEvery { showService.callShowsFiltered(anyString(), anyInt()) } throws httpException

        val resultWrapper = showRepository.fetchShowList(anyString(), anyInt())

        resultWrapper shouldNotBe null
        resultWrapper.shouldBeInstanceOf<ResultWrapper.GenericError>()
        (resultWrapper as ResultWrapper.GenericError) shouldNotBe null
        resultWrapper.code shouldBe httpException.code()
        resultWrapper.error shouldBe httpException.message
    }

    @Test
    fun `GIVEN service will throw an exception WHEN fetchShowList is called THEN should return ResultWrapper Generic Error with code 999`() = testCoroutineRule.runBlockingTest {
        val exception = mockk<Exception>(relaxed = true)
        coEvery { showService.callShowsFiltered(anyString(), anyInt()) } throws exception

        val resultWrapper = showRepository.fetchShowList(anyString(), anyInt())

        resultWrapper shouldNotBe null
        resultWrapper.shouldBeInstanceOf<ResultWrapper.GenericError>()
        (resultWrapper as ResultWrapper.GenericError) shouldNotBe null
        resultWrapper.code shouldBe 999
        resultWrapper.error shouldBe "Something weird happens"
    }

    @Test
    fun `GIVEN service return a ShowResponse AND not pass parameter page WHEN callShowsFiltered is called THEN should return ResultWrapper Success with data`() = testCoroutineRule.runBlockingTest {
        coEvery { showService.callShowsFiltered(anyString(), 1) } returns StubFactory.showResponse

        val resultWrapper = showRepository.fetchShowList(anyString())

        resultWrapper shouldNotBe null
        resultWrapper.shouldBeInstanceOf<ResultWrapper.Success<ShowResponse>>()
        (resultWrapper as ResultWrapper.Success<ShowResponse>).value shouldNotBe null
        resultWrapper.value.totalResults shouldBe 1
        resultWrapper.value.results.size shouldBe 1
        resultWrapper.value.page shouldBe 1
        resultWrapper.value.totalPages shouldBe 1
        resultWrapper.value.shouldBeSameInstanceAs(StubFactory.showResponse)
    }

    @Test
    fun `GIVEN service will throw an httpexception AND not pass parameter page WHEN fetchShowList is called THEN should return ResultWrapper Generic Error`() = testCoroutineRule.runBlockingTest {
        val httpException = mockk<HttpException>(relaxed = true)
        coEvery { showService.callShowsFiltered(anyString(), 1) } throws httpException

        val resultWrapper = showRepository.fetchShowList(anyString())

        resultWrapper shouldNotBe null
        resultWrapper.shouldBeInstanceOf<ResultWrapper.GenericError>()
        (resultWrapper as ResultWrapper.GenericError) shouldNotBe null
        resultWrapper.code shouldBe httpException.code()
        resultWrapper.error shouldBe httpException.message
    }

    @Test
    fun `GIVEN service will throw an exception AND not pass parameter page WHEN fetchShowList is called THEN should return ResultWrapper Generic Error with code 999`() =
        testCoroutineRule.runBlockingTest {
            val exception = mockk<Exception>(relaxed = true)
            coEvery { showService.callShowsFiltered(anyString(), 1) } throws exception

            val resultWrapper = showRepository.fetchShowList(anyString())

            resultWrapper shouldNotBe null
            resultWrapper.shouldBeInstanceOf<ResultWrapper.GenericError>()
            (resultWrapper as ResultWrapper.GenericError) shouldNotBe null
            resultWrapper.code shouldBe 999
            resultWrapper.error shouldBe "Something weird happens"
        }
}