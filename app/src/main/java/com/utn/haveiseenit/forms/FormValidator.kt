package com.utn.haveiseenit.forms

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import com.utn.haveiseenit.R

class Form(private val c: Context) {
    private val inputs = mutableListOf<FormInput>()
    lateinit var onSubmit: ()->Unit

    fun addEmailInput(input: EditText, name: String, required: Boolean = true, isLast: Boolean = false, onFocusOut: ()->Unit = {}): Form {
        inputs.add(
            EmailInput(
                input,
                name,
                c.getString(R.string.email_missing_message),
                c.getString(R.string.email_error_message),
                required,
                onFocusOut
            )
        )
        if(isLast){
            setSubmitOnKeyboardCheck(input)
        }
        return this
    }

    fun addPasswordInput(
        input: EditText,
        name: String,
        required: Boolean = true,
        validatePattern: Boolean = true,
        isLast: Boolean = false,
        onFocusOut: ()->Unit = {}
    ): Form {
        inputs.add(
            PasswordInput(
                input,
                name,
                c.getString(R.string.password_missing_message),
                c.getString(R.string.password_error_message),
                validatePattern,
                required,
                onFocusOut
            )
        )
        if(isLast){
            setSubmitOnKeyboardCheck(input)
        }
        return this
    }

    fun addTextInput(
        input: EditText,
        name: String,
        required: Boolean = true,
        errorMessage: String?,
        isLast: Boolean = false,
        onFocusOut: ()->Unit = {}
    ):Form {
        inputs.add(
            TextInput(
                input,
                name,
                c.getString(R.string.text_input_missing_default_message),
                errorMessage,
                required,
                onFocusOut
            )
        )
        if(isLast){
            setSubmitOnKeyboardCheck(input)
        }
        return this
    }

    fun addSubmitButton(button: Button, onSubmit: ()->Unit):Form{
        this.onSubmit = onSubmit
        button.setOnClickListener{
            onSubmit()
        }
        return this
    }

    private fun setSubmitOnKeyboardCheck(input: EditText){
        input.setOnEditorActionListener { _, actionId: Int, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onSubmit()
                true
            }
            false
        }
    }

    fun isValid(): Boolean {
        var state = true
        for (input in inputs) {
            if (input.isRequired() and !input.isInputValid()) {
                state = false
            }
        }
        return state;
    }

    fun getValue(inputName: String): String {
        return inputs.first { it.isName(inputName) }.getInputValue()
    }

    fun markInputInvalid(inputName: String, error: String? = null){
        inputs.first { it.isName(inputName) }.markInputInvalid(error)
    }
}