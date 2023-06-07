package com.varalakshmiakella.weatherappjpmc.locationSettings

import android.location.Location


interface LocationTracker {
        suspend fun getCurrentLocation(): Location?
}
