package com.lihan.pagekeeper.core.data

import com.lihan.pagekeeper.core.data.local.BookDao
import com.lihan.pagekeeper.core.data.mapper.toDomain
import com.lihan.pagekeeper.core.data.mapper.toEntity
import com.lihan.pagekeeper.core.domain.BookRepository
import com.lihan.pagekeeper.core.domain.FileManager
import com.lihan.pagekeeper.core.domain.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PageKeeperBookRepository(
    private val bookDao: BookDao
) : BookRepository {

    override suspend fun upsert(book: Book) {
        bookDao.upsert(bookEntity = book.toEntity())
    }

    override fun getBooks(): Flow<List<Book>> {
        return bookDao.getBookList().map { bookEntities ->
            bookEntities.map { bookEntity ->
                bookEntity.toDomain()
            }
        }
    }

    override suspend fun deleteBook(id: Int) {
        bookDao.deleteBook(id)
    }

    override suspend fun deleteBooksByIds(ids: List<Int>) {
        bookDao.deleteBooksByIds(ids)
    }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        bookDao.updateFavoriteStatus(id, isFavorite)
    }
}