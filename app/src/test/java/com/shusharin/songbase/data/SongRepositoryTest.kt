package com.shusharin.songbase.data

import com.shusharin.songbase.data.cache.external.ExternalCacheDataSource
import com.shusharin.songbase.data.cache.internal.InternalCacheDataSource
import com.shusharin.songbase.data.cache.internal.SongDb
import com.shusharin.songbase.data.cache.internal.SongListCacheMapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class SongRepositoryTest {

    @Test
    fun test_no_external_no_internal() = runBlocking {
        val testInternalDataSource = TestInternalCacheDataSource(returnSuccess = false)
        val testExternalDataSource = TestExternalCacheDataSource(returnSuccess = false)
        val repository = SongRepository.Base(
            testInternalDataSource,
            testExternalDataSource,
            SongListCacheMapper.Base(TestToBookMapper())
        )
        val actual = repository.findListSong()
        val expected = SongDataList.Success(emptyList())
        assertEquals(expected, actual)
    }

    @Test
    fun test_external_success_no_internal() = runBlocking {
        val testInternalDataSource = TestInternalCacheDataSource(returnSuccess = false)
        val testExternalDataSource = TestExternalCacheDataSource(returnSuccess = true)
        val repository = SongRepository.Base(
            testInternalDataSource,
            testExternalDataSource,
            SongListCacheMapper.Base(TestToBookMapper())
        )
        val actual = repository.findListSong()
        val expected = SongDataList.Success(listOf(
            SongData(
                _id = 1,
                track_id = 2,
                title = "Highway to hell",
                artist = "AC/DC",
                size = 100,
                bitrate = 320,
                duration = 10,
                local_path = "/",
                uri = "content/"), SongData(
                _id = 0,
                track_id = 0,
                title = "Highway to hell",
                artist = "AC/DC",
                size = 0,
                bitrate = 0,
                duration = 0,
                local_path = "/",
                uri = "content//")
        ))
        assertEquals(expected, actual)
    }

    private inner class TestInternalCacheDataSource(
        private val returnSuccess: Boolean,
    ) : InternalCacheDataSource {
        override suspend fun fetchListSong(): List<SongDb> {
            return if (returnSuccess) {
                listOf(
                    SongDb(
                        _id = 1,
                        track_id = 2,
                        title = "Highway to hell",
                        artist = "AC/DC",
                        size = 100,
                        bitrate = 320,
                        duration = 10,
                        local_path = "/",
                        uri = "content/"), SongDb(
                        _id = 0,
                        track_id = 0,
                        title = "Highway to hell",
                        artist = "AC/DC",
                        size = 0,
                        bitrate = 0,
                        duration = 0,
                        local_path = "/",
                        uri = "content//")
                )
            } else {
                emptyList()
            }
        }

        override suspend fun saveListSong(songs: List<SongData>) {
            //not used here
        }

        override suspend fun deleteSong(song: SongData) {
            //not used here
        }

        override suspend fun updateSong(song: SongData) {
            //not used here
        }
    }


    private inner class TestExternalCacheDataSource(
        private val returnSuccess: Boolean,
    ) : ExternalCacheDataSource {
        override fun find(): List<SongData> {
            return if (returnSuccess) {
                listOf(
                    SongData(
                        _id = 1,
                        track_id = 2,
                        title = "Highway to hell",
                        artist = "AC/DC",
                        size = 100,
                        bitrate = 320,
                        duration = 10,
                        local_path = "/",
                        uri = "content/"), SongData(
                        _id = 0,
                        track_id = 0,
                        title = "Highway to hell",
                        artist = "AC/DC",
                        size = 0,
                        bitrate = 0,
                        duration = 0,
                        local_path = "/",
                        uri = "content//")
                )
            } else {
                emptyList()
            }

        }
    }

    private class TestToBookMapper : ToSongMapper {

        override fun map(
            _id: Long,
            track_id: Long,
            title: String,
            artist: String,
            size: Int,
            bitrate: Int,
            duration: Int,
            local_path: String,
            uri: String,
        ): SongData = SongData(
            _id,
            track_id,
            title,
            artist,
            size,
            bitrate,
            duration,
            local_path,
            uri,
        )
    }
}