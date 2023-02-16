package com.example.cricketoons.model.apiSpecificTeamwithSquad

data class TeamDataFromAPI(
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