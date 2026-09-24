package com.vio.mcggcoach.model

data class GameState(
    val phase: String = "unknown",
    val round: String? = null,
    val hp: Int? = null,
    val gold: Int? = null,
    val level: Int? = null,
    val boardSlotsUsed: Int? = null,
    val boardSlotsMax: Int? = null,
    val commander: String? = null,
    val commanderSkill: String? = null,
    val synergies: Map<String, Int> = emptyMap(),
    val shopHeroes: List<String> = emptyList(),
    val benchHeroes: List<String> = emptyList(),
    val notes: List<String> = emptyList()
)
