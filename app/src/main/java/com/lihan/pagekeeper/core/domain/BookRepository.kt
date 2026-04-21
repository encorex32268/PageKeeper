package com.lihan.pagekeeper.core.domain

import com.lihan.pagekeeper.core.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun upsert(book: Book)

    fun getBooks(): Flow<List<Book>>

    suspend fun deleteBook(id: Int)

    suspend fun deleteBooksByIds(ids: List<Int>)

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

}