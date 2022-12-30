package com.msf.tvshows.network

import androidx.paging.PagingSource
import com.msf.tvshows.model.list.Show
import com.msf.tvshows.repository.ShowRepository
import com.msf.tvshows.util.StubFactory
import com.msf.tvshows.viewmodel.FilterType
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ListDataSourceTest {

    private val repository: ShowRepository = mockk(relaxed = true)

    private val listDataSource = ListDataSource(repository)

    @Test
    fun `GIVEN FilterType is different of TOP_RATED WHEN setFilterType is called THEN filter will be changed`() {
        listDataSource.setFilterType(FilterType.AIRING_TODAY)

        listDataSource.lastFilter shouldBe FilterType.AIRING_TODAY
        listDataSource.actualPage shouldBe 1
    }

    @Test
    fun `GIVEN FilterType is same of TOP_RATED WHEN setFilterType is called THEN filter wont be changed`() {
        listDataSource.setFilterType(FilterType.TOP_RATED)

        listDataSource.lastFilter shouldBe FilterType.TOP_RATED
    }

    @Test
    fun `GIVEN repository return a success WHEN load is called THEN should returns a page with result`() = runTest {
        coEvery { repository.fetchShowList(FilterType.TOP_RATED.filterName, 1) } returns ResultWrapper.Success(StubFactory.showResponse)
        val pagingSource = listDataSource
        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        ) shouldBe
            PagingSource.LoadResult.Page(
                data = StubFactory.showResponse.shows,
                prevKey = null,
                nextKey = null
            )
    }

    @Test
    fun `GIVEN repository return a exception WHEN load is called THEN should returns a page with result`() = runTest {
        coEvery { repository.fetchShowList(FilterType.TOP_RATED.filterName, 1) } returns ResultWrapper.GenericError(404, "error")
        val pagingSource = listDataSource
        val load = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        load.shouldBeInstanceOf<PagingSource.LoadResult.Error<Int, Show>>()
    }

    @Test
    fun `GIVEN repository return a network WHEN load is called THEN should returns a page with result`() = runTest {
        coEvery { repository.fetchShowList(FilterType.TOP_RATED.filterName, 1) } returns ResultWrapper.NetworkError
        val pagingSource = listDataSource
        val load = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        load.shouldBeInstanceOf<PagingSource.LoadResult.Error<Int, Show>>()
    }
}