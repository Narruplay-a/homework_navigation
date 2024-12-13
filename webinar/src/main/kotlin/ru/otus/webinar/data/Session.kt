package ru.otus.webinar.data

import kotlinx.serialization.Serializable

/**
 * Session state
 */
@Serializable
sealed class Session {
    /**
     * Not authenticated
     */
    @Serializable
    data object NONE : Session()

    /**
     * Active session - user logged in
     */
    @Serializable
    data class Active(val user: String, val loginTime: Long) : Session()
}