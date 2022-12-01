package vn.ztech.software.tut_9_menu_pickers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class OrderActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        // Get the intent and its data.
        val intent: Intent = getIntent()
        val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)
        val textView: TextView = findViewById(R.id.order_textview)
        textView.text = message

        // Create the spinner.
        val spinner: Spinner = findViewById(R.id.label_spinner)
        if (spinner != null) {
            spinner.onItemSelectedListener = this
        }

        // Create an ArrayAdapter using the string array and default spinner
        // layout.
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.labels_array,
            android.R.layout.simple_spinner_item
        )

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.adapter = adapter
        }
    }

    /**
     * Checks which radio button was clicked and displays a toast message to
     * show the choice.
     *
     * @param view The radio button view.
     */
    fun onRadioButtonClicked(view: View) {
        // Is the button now checked?
        val checked = (view as RadioButton).isChecked
        when (view.getId()) {
            R.id.sameday -> if (checked) // Same day service
                displayToast(
                    getString(
                        R.string.same_day_messenger_service
                    )
                )
            R.id.nextday -> if (checked) // Next day delivery
                displayToast(
                    getString(
                        R.string.next_day_ground_delivery
                    )
                )
            R.id.pickup -> if (checked) // Pick up
                displayToast(
                    getString(
                        R.string.pick_up
                    )
                )
            else -> {}
        }
    }

    /**
     * Displays the actual message in a toast message.
     *
     * @param message Message to display.
     */
    fun displayToast(message: String?) {
        Toast.makeText(
            getApplicationContext(), message,
            Toast.LENGTH_SHORT
        ).show()
    }

    // Interface callback for when any spinner item is selected.
    override fun onItemSelected(
        adapterView: AdapterView<*>,
        view: View?, i: Int, l: Long
    ) {
        val spinnerLabel = adapterView.getItemAtPosition(i).toString()
        displayToast(spinnerLabel)
    }

    // Interface callback for when no spinner item is selected.
    override fun onNothingSelected(adapterView: AdapterView<*>?) {
        // Do nothing.
    }
}