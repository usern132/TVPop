package com.silliconpowerinc.tvpop.data.sources

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.silliconpowerinc.tvpop.domain.models.TVShow
import kotlinx.serialization.json.Json


@Database(entities = [TVShow::class, TVShowRemoteKeys::class], version = 1)
@TypeConverters(TVShowTypeConverters::class)
abstract class TVShowsLocalSource : RoomDatabase() {
    abstract fun tvShowDao(): TVShowDao
    abstract fun remoteKeysDao(): TVShowRemoteKeysDao
}

@Dao
interface TVShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tvShows: List<TVShow>)

    @Query("SELECT * FROM tvshow ORDER BY popularity DESC")
    fun pagingSource(): PagingSource<Int, TVShow>

    @Query("DELETE FROM tvshow")
    suspend fun deleteAll()
}

class TVShowTypeConverters {

    @TypeConverter
    fun fromIntList(value: List<Int>): String = Json.encodeToString(value)

    @TypeConverter
    fun toIntList(value: String): List<Int> = Json.decodeFromString(value)

    @TypeConverter
    fun fromStringList(value: List<String>): String = Json.encodeToString(value)
    @TypeConverter
    fun toStringList(value: String): List<String> = Json.decodeFromString(value)
}

@Entity(tableName = "remote_keys")
data class TVShowRemoteKeys(
    @PrimaryKey val showId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Dao
interface TVShowRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<TVShowRemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE showId = :showId")
    suspend fun remoteKeys(showId: Int): TVShowRemoteKeys?

    @Query("SELECT lastUpdated FROM remote_keys ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getLastUpdated(): Long?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()
}