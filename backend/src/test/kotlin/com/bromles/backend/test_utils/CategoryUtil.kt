package com.bromles.backend.test_utils

import com.bromles.backend.model.Book
import com.bromles.backend.model.Category
import com.bromles.backend.model.Tag
import java.time.LocalDate

object CategoryUtil {
    fun getCategory(): Category {
        val category = Category(
            "name"
        )
        return category
    }
}