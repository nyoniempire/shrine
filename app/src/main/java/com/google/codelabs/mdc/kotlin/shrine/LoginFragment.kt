package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Fragment representing the login screen for Shrine.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        // Snippet from "Navigate to the next Fragment" section goes here.

        val view = inflater.inflate(R.layout.shr_login_fragment, container, false)
        val passwordTextInput: TextInputLayout = view.findViewById(R.id.password_text_input)
        val passwordEditText: TextInputEditText = view.findViewById(R.id.password_edit_text)
        var nextButton: MaterialButton = view.findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            if(isPasswordValid(passwordEditText.text!!)) passwordTextInput.error = getString(R.string.shr_error_password)
            else {
                passwordTextInput.error = null
                (activity as NavigationHost).navigateTo(ProductGridFragment(),false)
            }
        }

        passwordEditText.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(isPasswordValid(passwordEditText.text!!)) passwordTextInput.error = null

                return false
            }
        })
        return view
    }

    // "isPasswordValid" from "Navigate to the next Fragment" section method goes here

    private fun isPasswordValid(text: Editable?):Boolean{
        return text!=null && text.length >= 8
    }
}
