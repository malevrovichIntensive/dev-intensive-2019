package ru.skillbranch.devintensive.ui.profile

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.ac_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    companion object{
        const val EDIT_MODE = "EDIT_MODE"
    }

    lateinit var viewModel: ProfileViewModel
    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_profile)
        initViews(savedInstanceState)
        initViewModel()
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickName" to tv_nick_name,
            "rank" to tv_rank,
            "rating" to tv_rating,
            "respect" to tv_respect,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository
        )

        isEditMode = savedInstanceState?.getBoolean(EDIT_MODE) ?: false
        showMode(isEditMode)

        btn_edit.setOnClickListener {
            if(isEditMode) saveProfileInfo()
            isEditMode = isEditMode.not()
            showMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener{
            Log.d("Activity: ", "Clicked")
            val isNightTheme = !(viewModel.getIsNightTheme().value ?: false)
            viewModel.saveIsNightTheme(isNightTheme)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getIsNightTheme().observe(this, Observer { changeTheme(it) })
    }

    private fun changeTheme(isNightTheme: Boolean) {
        if(isNightTheme) {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        } else{
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
        }
    }

    private fun updateUI(it: Profile) {
        for ((k, v) in it.toMap()) {
            viewFields[k]!!.text = v
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(EDIT_MODE, isEditMode)
    }

    private fun showMode(editMode: Boolean) {
        val info = viewFields.filter {
            setOf(
                "firstName",
                "lastName",
                "about",
                "repository"
            ).contains(it.key)
        }
        for ((_, v) in info) {
            v as EditText
            v.isFocusable = editMode
            v.isFocusableInTouchMode = editMode
            v.isEnabled = editMode
            v.background.alpha = if (editMode) 255 else 0
        }

        with(btn_edit) {
            background.colorFilter = if (editMode) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                null
            }

            val resId = if (editMode) {
                R.drawable.ic_save_black_24dp
            } else {
                R.drawable.ic_edit_black_24dp
            }
            setImageResource(resId)
        }
    }

    private fun saveProfileInfo() {
        viewModel.saveProfileData(
            Profile(
                firstName = et_first_name.text.toString(),
                lastName = et_last_name.text.toString(),
                about = et_about.text.toString(),
                repository = et_repository.text.toString()
            )
        )
    }
}