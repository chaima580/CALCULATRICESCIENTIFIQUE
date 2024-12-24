package com.example.scientificcalculator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Button
import android.widget.TextView
class MainActivity : AppCompatActivity() {
    private lateinit var tvDisplay: TextView
    private lateinit var expression: SpannableStringBuilder
    private var newNum = true
    private var dot = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvDisplay = findViewById(R.id.tvDisplay)
        expression = SpannableStringBuilder("")
        val buttons = findViewById<GridLayout>(R.id.gridLayout)
        for (i in 0 until buttons.childCount) {
            val button = buttons.getChildAt(i) as Button
            button.setOnClickListener { handleClick(button) }
        }
    }
    private fun handleClick(button: Button) {
        val buttonText = button.text.toString()
        when (buttonText) {
            in "0".."9" -> appendNumber(buttonText)
            "." -> appendDecimal()
            in "+-*/^" -> appendOperator(buttonText)
            "(" -> appendLeftParenthesis()
            ")" -> appendRightParenthesis()
            "=" -> calculateExpression()
            "DEL" -> deleteLastCharacter()
            "AC" -> clearDisplay()
            else -> {
            // Handle other buttons like "EXP", "ANS" (logic needed)
                // Add your logic for these buttons here
            }
        }
        newNum = true
    }

    private fun appendNumber(number: String) {
        expression.append(number)
        tvDisplay.text = expression
        dot = false
    }

    private fun appendDecimal() {
        if (!dot) {
            expression.append(".")
            tvDisplay.text = expression
            dot = true
        }
    }

    private fun appendOperator(operator: String) {
        if (newNum || expression.endsWith(")")) {
            expression.append(operator)
            tvDisplay.text = expression
        }
    }

    private fun appendLeftParenthesis() {
        expression.append("(")
        tvDisplay.text = expression
        newNum = true
    }

    private fun appendRightParenthesis() {
        expression.append(")")
        tvDisplay.text = expression
        newNum = false
    }

    private fun calculateExpression() {
        val exp = ExpressionBuilder(expression.toString()).build()
        try {
            val result = exp.evaluate()
            expression = SpannableStringBuilder(result.toString())
            tvDisplay.text = expression
        } catch (e: ExpressionParsingException) {
            // Handle parsing errors (e.g., invalid expression)
            tvDisplay.text = "Error"
        }
    }

    private fun deleteLastCharacter() {
        if (expression.isNotEmpty()) {
            expression.delete(expression.length - 1, expression.length)
            tvDisplay.text = expression
            dot = expression.endsWith(".")
        }
    }

    private fun clearDisplay() {
        expression.clear()
        tvDisplay.text = ""
        newNum = true
        dot = false
    }
}