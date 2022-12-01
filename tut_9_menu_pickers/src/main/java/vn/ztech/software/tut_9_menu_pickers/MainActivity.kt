package vn.ztech.software.tut_9_menu_pickers

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    // Tag for the intent extra.
    companion object {val EXTRA_MESSAGE = "com.example.android.droidcafeinput.extra.MESSAGE"}

    // The order message, displayed in the Toast and sent to the new Activity.
    private var mOrderMessage: String? = null

    /**
     * Creates the content view, the toolbar, and the floating action button.
     *
     * This method is provided in the Basic Activity template.
     *
     * @param savedInstanceState Saved instance state bundle.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@MainActivity,
                OrderActivity::class.java
            )
            intent.putExtra(EXTRA_MESSAGE, mOrderMessage)
            startActivity(intent)
        })

        val tvContextMenu = findViewById<TextView>(R.id.tvContextMenu)
        registerForContextMenu(tvContextMenu)

        val btCalendar = findViewById<Button>(R.id.btCalendar)
        btCalendar.setOnClickListener {
            openCalendar()
        }
    }

    private fun openCalendar() {
        val dialogFragment = BlankFragment()
        dialogFragment.show(supportFragmentManager, "xxx")
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        // This comment suppresses the Android Studio warning about simplifying
        // the return statements.
        when(id){
            R.id.action_order -> {
                val intent = Intent(this@MainActivity, OrderActivity::class.java)
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, mOrderMessage)
                startActivity(intent)
                return true
            }
            R.id.action_favorit -> {
                Toast.makeText(this, "Favorite", Toast.LENGTH_LONG).show()
                return true

            }
            R.id.action_info -> {
                Toast.makeText(this, "Info", Toast.LENGTH_LONG).show()
                return true

            }
            R.id.action_contact -> {
                Toast.makeText(this, "Contact", Toast.LENGTH_LONG).show()
                return true

            }
        }
        return super.onContextItemSelected(item)
    }
    /**
     * Inflates the menu, and adds items to the action bar if it is present.
     *
     * @param menu Menu to inflate.
     * @return Returns true if the menu inflated.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Handles app bar item clicks.
     *
     * @param item Item clicked.
     * @return True if one of the defined items was clicked.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        // This comment suppresses the Android Studio warning about simplifying
        // the return statements.
        when(id){
            R.id.action_order -> {
                val intent = Intent(this@MainActivity, OrderActivity::class.java)
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, mOrderMessage)
                startActivity(intent)
                return true
            }
            R.id.action_favorit -> {
                Toast.makeText(this, "Favorite", Toast.LENGTH_LONG).show()
                return true

            }
            R.id.action_info -> {
                Toast.makeText(this, "Info", Toast.LENGTH_LONG).show()
                return true

            }
            R.id.action_contact -> {
                Toast.makeText(this, "Contact", Toast.LENGTH_LONG).show()
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Displays a Toast with the message.
     *
     * @param message Message to display.
     */
    fun displayToast(message: String?) {
        Toast.makeText(
            applicationContext, message,
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Shows a message that the donut image was clicked.
     */
    fun showDonutOrder(view: View?) {
        mOrderMessage = getString(R.string.donut_order_message)
        displayToast(mOrderMessage)
    }

    /**
     * Shows a message that the ice cream sandwich image was clicked.
     */
    fun showIceCreamOrder(view: View?) {
        mOrderMessage = getString(R.string.ice_cream_order_message)
        displayToast(mOrderMessage)
    }

    /**
     * Shows a message that the froyo image was clicked.
     */
    fun showFroyoOrder(view: View?) {
        mOrderMessage = getString(R.string.froyo_order_message)
        displayToast(mOrderMessage)
    }
    fun showPickedDate(y: Int, m: Int, d: Int){
        Toast.makeText(this, "$y ${m+1} $d", Toast.LENGTH_LONG).show()
    }
}