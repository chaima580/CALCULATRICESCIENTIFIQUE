import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var currentExpression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.tvDisplay)

        // Reference all buttons using their IDs
        val buttonShift: Button = findViewById(R.id.btnSHIFT)
        val buttonOn: Button = findViewById(R.id.btnON)
        val buttonAlpha: Button = findViewById(R.id.btnALPHA)
        val buttonReplay: Button = findViewById(R.id.btnREPLAY)
        val buttonMode: Button = findViewById(R.id.btnMODE)
        // ... other buttons ...
        val buttonEqual: Button = findViewById(R.id.btnEqual)
        val buttonClear: Button = findViewById(R.id.btnAC) // Assuming "AC" is for clear
        val buttonDelete: Button = findViewById(R.id.btnDEL)

        // Single OnClickListener for most buttons
        val listener = View.OnClickListener { view ->
            val button = view as Button
            val buttonText = button.text.toString()
            when (buttonText) {
                "=" -> calculateResult()
                "C" -> clearExpression() // Assuming "C" is for clear
                "DEL" -> deleteLastCharacter()
                else -> appendToExpression(buttonText)
            }
        }

        // Set OnClickListener for all buttons except clear and delete
        buttonShift.setOnClickListener(listener)
        buttonOn.setOnClickListener(listener)
        buttonAlpha.setOnClickListener(listener)
        buttonReplay.setOnClickListener(listener)
        buttonMode.setOnClickListener(listener)
        // ... other buttons ...
        buttonEqual.setOnClickListener(listener)

        // Separate listeners for clear and delete buttons (optional)
        buttonClear.setOnClickListener {
            clearExpression()
        }

        buttonDelete.setOnClickListener {
            deleteLastCharacter()
        }
    }

    private fun appendToExpression(text: String) {
        currentExpression += text
        resultTextView.text = currentExpression
    }

    private fun calculateResult() {
        try {
            // Use a library like MathParser.org for expression evaluation
            val expression = Expression(currentExpression) // Replace with your chosen library
            val result = expression.evaluate()
            resultTextView.text = result.toString()
        } catch (e: Exception) {
            resultTextView.text = "Error"
        }
    }

    private fun clearExpression() {
        currentExpression = ""
        resultTextView.text = "0"
    }

    private fun deleteLastCharacter() {
        if (currentExpression.isNotEmpty()) {
            currentExpression = currentExpression.substring(0, currentExpression.length - 1)
            resultTextView.text = currentExpression
        }
    }
}