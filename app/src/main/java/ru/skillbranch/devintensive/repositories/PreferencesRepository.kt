package ru.skillbranch.devintensive.repositories

import android.preference.PreferenceManager
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.models.Profile
import java.lang.Exception

object PreferencesRepository {

    const val NICKNAME_KEY = "NICKNAME_KEY"
    const val RANK_KEY = "RANK_KEY"
    const val RATING_KEY = "RATING_KEY"
    const val RESPECT_KEY = "RESPECT_KEY"
    const val FIRSTNAME_KEY = "FIRSTNAME_KEY"
    const val LASTNAME_KEY = "LASTNAME_KEY"
    const val ABOUT_KEY = "ABOUT_KEY"
    const val REPOSITORY_KEY = "REPOSITORY_KEY"
    const val IS_NIGHT_MODE_KEY = "IS_NIGHT_MODE_KEY"

    val sharedPreferences by lazy {
        val ctx = App.applicationContext()
        PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun getNightThemeData(): Boolean = sharedPreferences.getBoolean(IS_NIGHT_MODE_KEY, false)

    fun saveIsNightTheme(nightTheme: Boolean) {
        putValue(IS_NIGHT_MODE_KEY to  nightTheme)
    }

    fun saveProfileData(profile: Profile) {
        with(profile){
            putValue(NICKNAME_KEY to nickName)
            putValue(RANK_KEY to rank)
            putValue(RATING_KEY to rating)
            putValue(RESPECT_KEY to respect)
            putValue(FIRSTNAME_KEY to firstName)
            putValue(LASTNAME_KEY to lastName)
            putValue(ABOUT_KEY to about)
            putValue(REPOSITORY_KEY to repository)
        }
    }

    fun getProfileData(): Profile = Profile(
        sharedPreferences.getString(NICKNAME_KEY, "John Doe")!!,
        sharedPreferences.getString(RANK_KEY, "Junior Android Developer")!!,
        sharedPreferences.getString(RATING_KEY, "0")!!,
        sharedPreferences.getString(RESPECT_KEY, "0")!!,
        sharedPreferences.getString(FIRSTNAME_KEY, "")!!,
        sharedPreferences.getString(LASTNAME_KEY, "")!!,
        sharedPreferences.getString(ABOUT_KEY, "")!!,
        sharedPreferences.getString(REPOSITORY_KEY, "")!!
    )

    private fun putValue(p: Pair<String, Any>){
        val (k, v) = p

        when(v){
            is String -> sharedPreferences.edit().putString(k, v).apply()
            is Int -> sharedPreferences.edit().putInt(k, v).apply()
            is Long -> sharedPreferences.edit().putLong(k, v).apply()
            is Boolean -> sharedPreferences.edit().putBoolean(k, v).apply()
            is Float -> sharedPreferences.edit().putFloat(k, v).apply()
            else -> throw Exception("Illegal argument!!!")
        }
    }
}