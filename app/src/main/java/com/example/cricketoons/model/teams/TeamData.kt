package com.example.cricketoons.model.teams

data class TeamData(
    var code: String?,
    var country_id: Int?,
    var id: Int?,
    var image_path: String?,
    var name: String?,
    var national_team: Boolean?,
    var resource: String?,
    var squad: List<Squad>?,
    var updated_at: String?
)