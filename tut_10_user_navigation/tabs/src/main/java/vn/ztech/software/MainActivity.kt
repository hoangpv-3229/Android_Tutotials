package vn.ztech.software

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawer:DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = findViewById<View>(R.id.nav) as NavigationView
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this)
        }

        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewPager)


        val pagerAdapter = PagerAdapter(this, 3)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager){tab, pos->
            tab.text = "tab ${pos.toString()}"
        }.attach()


    }
    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        return when (item.getItemId()) {
            R.id.nav_camera -> {
                // Handle the camera import action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START)
                displayToast("chose_camera")
                true
            }
            R.id.nav_gallery -> {
                // Handle the gallery action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START)
                displayToast("chose_gallery")
                true
            }
            R.id.nav_slideshow -> {
                // Handle the slideshow action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START)
                displayToast("chose_slideshow")
                true
            }
            R.id.nav_manage -> {
                // Handle the tools action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START)
                displayToast("chose_tools")
                true
            }
            R.id.nav_share -> {
                // Handle the share action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START)
                displayToast("chose_share")
                true
            }
            R.id.nav_send -> {
                // Handle the send action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START)
                displayToast("chose_send")
                true
            }
            else -> false
        }
    }
    fun displayToast(message: String?) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
    inner class PagerAdapter(act: FragmentActivity, val num: Int): FragmentStateAdapter(act) {

        override fun getItemCount(): Int {
            return num
        }

        override fun createFragment(position: Int): Fragment {
            when(position){
                0 -> return Fragment1()
                1 -> return Fragment2()
                2 -> return Fragment3()
                else -> return Fragment1()
            }        }

    }
}