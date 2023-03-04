package com.bromles.backend.test_utils

import com.bromles.backend.model.Tag

object TagUtils {
    fun getTag(): Tag {
        val tag = Tag(
            "name",
            listOf()
        )
        tag.createdUserId = "username"
        return tag
    }
}