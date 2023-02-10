package com.example.cricketoons.model.fixtures

import androidx.room.Ignore
import com.example.cricketoons.model.fixtures.Link

data class Meta(
    val current_page: Int?,
    val from: Int?,
    val last_page: Int?,
    @Ignore
    val links: List<Link>?,
    val path: String?,
    val per_page: Int?,
    val to: Int?,
    val total: Int?
)