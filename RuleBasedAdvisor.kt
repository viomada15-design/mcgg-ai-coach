package com.vio.mcggcoach.strategy

import com.vio.mcggcoach.model.GameState

data class Advice(val headline: String, val reason: String, val confidence: Double)

class RuleBasedAdvisor {
    fun advise(s: GameState): Advice {
        if ((s.gold ?: 0) >= 40 && (s.hp ?: 100) >= 45) {
            return Advice(
                "HOLD GOLD / AVOID PANIC REROLL",
                "Economy is healthy and HP is not yet critical. Preserve flexibility until a clear upgrade or level breakpoint appears.",
                0.72
            )
        }
        if ((s.hp ?: 100) <= 25) {
            return Advice(
                "STABILIZE NOW",
                "Low HP raises elimination risk. Spend only toward immediate board strength: key star upgrade, frontline, or synergy breakpoint.",
                0.78
            )
        }
        return Advice(
            "OBSERVE NEXT SHOP + OPPONENT",
            "Not enough high-confidence signals yet. Keep options open and scout before committing resources.",
            0.58
        )
    }
}
