package ru.otus.webinar.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import ru.otus.webinar.App
import java.io.InputStream
import java.io.OutputStream
import kotlin.properties.ReadOnlyProperty

private const val USER_NAME = "user"
private const val PASSWORD = "password"

/**
 * Session manager
 */
interface SessionManager {
    suspend fun getSession(): Session
    suspend fun login(username: String, password: String): Session
    suspend fun logout(): Session.NONE

    class Impl(context: Context) : SessionManager {

        private val dataStore: DataStore<Session> = context.sessionStore

        override suspend fun getSession(): Session = dataStore.data.first()

        override suspend fun login(username: String, password: String): Session {
            if (username == USER_NAME && password == PASSWORD) {
                val session = Session.Active(username, System.currentTimeMillis())
                dataStore.updateData { session }
                return session
            }
            throw IllegalArgumentException("Invalid credentials")
        }

        override suspend fun logout(): Session.NONE {
            val session = Session.NONE
            dataStore.updateData { session }
            return session
        }

        companion object {
            private const val DEFAULT_FILE_NAME = "session.pb"

            /**
             * Session serializer
             */
            @OptIn(ExperimentalSerializationApi::class)
            internal val SessionSerializer = object : Serializer<Session> {
                override val defaultValue: Session = Session.NONE
                override suspend fun readFrom(input: InputStream): Session {
                    return Json.decodeFromStream(input)
                }
                override suspend fun writeTo(t: Session, output: OutputStream) {
                    Json.encodeToStream(t, output)
                }
            }

            private val Context.sessionStore: DataStore<Session> by dataStore(
                fileName = DEFAULT_FILE_NAME,
                serializer = SessionSerializer
            )
        }
    }
}

/**
 * Gets session manager from context
 */
private fun Context.getSessionManager(): SessionManager = (applicationContext as App).sessionManager

/**
 * Checks session state
 */
class SessionHelper(
    private val getContext: () -> Context,
    private val onAuthenticated: (Session.Active) -> Unit,
    private val onNonAuthenticated: () -> Unit
): DefaultLifecycleObserver {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(owner: LifecycleOwner) {
        sessionManager = getContext().getSessionManager()
    }

    override fun onStart(owner: LifecycleOwner) {
        owner.lifecycleScope.launch {
            when (val session = sessionManager.getSession()) {
                is Session.Active -> {
                    onAuthenticated(session)
                }
                else -> {
                    onNonAuthenticated()
                }
            }
        }
    }

    suspend fun logout() {
        sessionManager.logout()
        onNonAuthenticated()
    }
}

/**
 * Gets session helper from fragment
 */
private class SessionManagerContextDelegate: ReadOnlyProperty<Fragment, SessionManager> {
    override fun getValue(thisRef: Fragment, property: kotlin.reflect.KProperty<*>): SessionManager {
        return thisRef.requireContext().getSessionManager()
    }
}

/**
 * Gets session helper from fragment
 */
fun sessionManager(): ReadOnlyProperty<Fragment, SessionManager> = SessionManagerContextDelegate()

/**
 * Gets session helper from fragment
 */
private class SessionHelperFragmentDelegate(
    private val fragment: Fragment,
    onAuthenticated: (Session.Active) -> Unit,
    onNonAuthenticated: () -> Unit
): ReadOnlyProperty<Fragment, SessionHelper> {

    private var sessionHelper: SessionHelper = SessionHelper(
        getContext = { fragment.requireContext() },
        onAuthenticated = onAuthenticated,
        onNonAuthenticated = onNonAuthenticated
    )

    init {
        fragment.lifecycle.addObserver(sessionHelper)
    }

    override fun getValue(thisRef: Fragment, property: kotlin.reflect.KProperty<*>): SessionHelper {
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED).not()) {
            throw IllegalStateException("Should not attempt to get session manager before fragment is created.")
        }

        return sessionHelper
    }
}

/**
 * Gets session helper from fragment
 */
fun Fragment.sessionHelper(
    onAuthenticated: (Session.Active) -> Unit,
    onNonAuthenticated: () -> Unit
): ReadOnlyProperty<Fragment, SessionHelper> = SessionHelperFragmentDelegate(
    fragment = this,
    onAuthenticated = onAuthenticated,
    onNonAuthenticated = onNonAuthenticated
)

