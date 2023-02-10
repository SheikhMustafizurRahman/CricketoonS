package com.example.cricketoons.model.fixtures

import androidx.room.Ignore

data class Fixture(
    val `data`: List<FixtureData>,
    val links: Links?,
    @Ignore
    val meta: Meta?
)