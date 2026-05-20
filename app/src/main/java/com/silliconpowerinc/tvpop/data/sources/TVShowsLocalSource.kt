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

/**
 * The Room database for storing TV show information and remote pagination keys.
 */
@Database(entities = [TVShow::class, TVShowRemoteKeys::class], version = 1)
@TypeConverters(TVShowTypeConverters::class)
abstract class TVShowsLocalSource : RoomDatabase() {
    /** Returns the DAO for managing [TVShow] entities. */
    abstract fun tvShowDao(): TVShowDao
    /** Returns the DAO for managing [TVShowRemoteKeys] entities. */
    abstract fun remoteKeysDao(): TVShowRemoteKeysDao
}

/**
 * Data Access Object for the [TVShow] entity.
 */
@Dao
interface TVShowDao {
    /** Inserts a list of TV shows into the database, replacing them if they already exist. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tvShows: List<TVShow>)

    /** Provides a [PagingSource] for TV shows, ordered by popularity descending. */
    @Query("SELECT * FROM tvshow ORDER BY popularity DESC")
    fun pagingSource(): PagingSource<Int, TVShow>

    /** Deletes all TV shows from the database. */
    @Query("DELETE FROM tvshow")
    suspend fun deleteAll()
}

/**
 * Type converters for the Room database.
 */
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

/**
 * Entity representing remote pagination keys for a specific TV show.
 * Used by [TVShowsRemoteMediator] to manage paging state.
 *
 * @property showId The ID of the TV show as the primary key.
 * @property prevKey The previous page number.
 * @property nextKey The next page number.
 * @property language The language code used when these keys were fetched.
 * @property lastUpdated The timestamp of when these keys were last updated.
 */
@Entity
data class TVShowRemoteKeys(
    @PrimaryKey val showId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
    val language: String,
    val lastUpdated: Long = System.currentTimeMillis()
)

/**
 * Data Access Object for the [TVShowRemoteKeys] entity.
 */
@Dao
interface TVShowRemoteKeysDao {
    /** Inserts a list of remote keys, replacing them if they already exist. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<TVShowRemoteKeys>)

    /** Retrieves the remote keys for a specific TV show. */
    @Query("SELECT * FROM tvshowremotekeys WHERE showId = :showId")
    suspend fun remoteKeys(showId: Int): TVShowRemoteKeys?

    /** Retrieves the timestamp of the most recently updated remote key. */
    @Query("SELECT lastUpdated FROM tvshowremotekeys ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getLastUpdated(): Long?

    /** Retrieves the language associated with the cached remote keys. */
    @Query("SELECT language FROM tvshowremotekeys LIMIT 1")
    suspend fun getLanguage(): String?

    /** Deletes all remote keys from the database. */
    @Query("DELETE FROM tvshowremotekeys")
    suspend fun deleteAll()
}
