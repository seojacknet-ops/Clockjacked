package com.clockjacked.app.util

import androidx.compose.ui.graphics.Color
import java.time.ZoneId
import java.time.ZonedDateTime

/** Get a subtle time-of-day background tint for a clock card (7% opacity) */
fun getTimeTint(timezoneId: String): Color {
    val hour = ZonedDateTime.now(ZoneId.of(timezoneId)).hour
    val baseColor = when (hour) {
        in 22..23, in 0..4 -> Color(0xFF1A237E)  // Deep Indigo — night
        in 5..6            -> Color(0xFFE91E63)  // Warm Rose — dawn
        in 7..11           -> Color(0xFFFFC107)  // Soft Amber — morning
        in 12..13          -> Color(0xFFFFD54F)  // Bright Gold — midday
        in 14..16          -> Color(0xFFFF9800)  // Warm Orange — afternoon
        in 17..18          -> Color(0xFFFF5722)  // Deep Coral — sunset
        in 19..21          -> Color(0xFF7B1FA2)  // Cool Purple — evening
        else               -> Color.Transparent
    }
    return baseColor.copy(alpha = 0.07f)
}
