package com.pmacademy.githubclient.data.model

enum class ReactionType(val textReaction: String) {
    LIKE("+1"),
    DISLIKE("-1"),
    LAUGH("laugh"),
    CONFUSED("confused"),
    HEART("heart"),
    HOORAY("hooray"),
    ROCKET("rocket"),
    EYES("eyes")
}
