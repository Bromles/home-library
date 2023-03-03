package com.bromles.backend.listener

import com.bromles.backend.test_utils.BookUtils.getBook
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class BaseEntityListenerTest {

    private val baseEntityListener = BaseEntityListener()

    @Test
    fun onCreate() {
        val book = getBook()
        baseEntityListener.onCreate(book)
        assertNotNull(book.createdDate)
        assertNotNull(book.updatedDate)
    }

    @Test
    fun onUpdate() {
        val book = getBook()
        baseEntityListener.onUpdate(book)
        assertNotNull(book.updatedDate)
    }
}