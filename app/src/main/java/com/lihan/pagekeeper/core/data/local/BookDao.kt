package com.lihan.pagekeeper.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Upsert
    suspend fun upsert(bookEntity: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getBookList(): Flow<List<BookEntity>>

    @Query("DELETE FROM BookEntity WHERE id=:id")
    suspend fun deleteBook(id: Int)

    @Query("DELETE FROM BookEntity WHERE id IN (:ids)")
    suspend fun deleteBooksByIds(ids: List<Int>)

    @Query("UPDATE BookEntity SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("""
        SELECT * FROM BookEntity
        WHERE title LIKE '%' || :text || '%' 
        OR author LIKE '%' || :text || '%'
    """)
    fun searchBooks(text: String): Flow<List<BookEntity>>


}