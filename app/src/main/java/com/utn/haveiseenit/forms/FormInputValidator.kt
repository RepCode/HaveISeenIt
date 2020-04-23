package com.utn.haveiseenit.forms

import android.widget.EditText
import java.util.regex.Pattern

abstract class FormInput(
    protected val required: Boolean,
    private val input: EditText,
    private val name: String,
    private val onFocusOut: ()->Unit
) {
    protected var isValid = false

    init {
        input.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (!hasFocus) {
                validate()
                onFocusOut()
            }
        }
    }

    fun markInputInvalid(error: String?){
        isValid = false
        input.error = error
    }

    fun isName(text: String): Boolean {
        return name == text
    }

    fun getInput(): EditText {
        return input
    }

    abstract fun getInputValue(): String

    abstract fun validate(): Unit
    fun isInputValid(): Boolean {
        validate()
        return isValid
    }

    fun isRequired(): Boolean {
        return required
    }
}

class EmailInput(
    input: EditText,
    name: String,
    private val missingMessage: String,
    private val patternMessage: String,
    required: Boolean,
    onFocusOut: ()->Unit
) : FormInput(required, input, name, onFocusOut) {

    override fun getInputValue(): String {
        return getInput().text.toString()
    }

    override fun validate() {
        if (required and getInput().text.isNullOrBlank()) {
            isValid = false
            getInput().error = missingMessage
            return
        } else {
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val hasError: Boolean = !pattern.matcher(getInput().text).matches()
            if (hasError) {
                isValid = false
                getInput().error = patternMessage
                return
            }
        }
        isValid = true
    }
}

class PasswordInput(
    input: EditText,
    name: String,
    private val missingMessage: String,
    private val patternMessage: String,
    private val validatePattern: Boolean,
    required: Boolean,
    onFocusOut: ()->Unit
) :
    FormInput(required, input, name, onFocusOut) {
    override fun getInputValue(): String {
        return getInput().text.toString()
    }

    override fun validate() {
        if (required and getInput().text.isNullOrBlank()) {
            isValid = false
            getInput().error = missingMessage
            return
        } else {
            if (validatePattern) {
                var expression = "^(?=.*\\d).{5,10}\$"
                val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
                val hasError: Boolean = !pattern.matcher(getInput().text).matches()
                if (hasError) {
                    isValid = false
                    getInput().error =
                        patternMessage
                    return
                }
            }
        }
        isValid = true
    }
}

class TextInput(
    input: EditText,
    name: String,
    missingMessage: String,
    private var text: String? = null,
    required: Boolean,
    onFocusOut: ()->Unit
) :
    FormInput(required, input, name, onFocusOut) {
    init {
        if (text == null) {
            text = missingMessage
        }
    }

    override fun getInputValue(): String {
        return getInput().text.toString()
    }

    override fun validate() {
        if (required and getInput().text.isNullOrBlank()) {
            isValid = false
            getInput().error = text
            return
        }
        isValid = true
    }
}