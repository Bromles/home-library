package com.bromles.backend.test_utils

import com.bromles.backend.model.Book
import com.bromles.backend.model.Category
import com.bromles.backend.model.Tag
import java.time.LocalDate

object BookUtils {
    fun getBook(): Book {
        val book = Book(
            "name",
            "author",
            LocalDate.now(),
            Tag("name"),
            Category("name"),
            "img".toByteArray(),
            "file".toByteArray(),
            "filename"
        )
        book.createdUserId = "username"
        return book
    }
}