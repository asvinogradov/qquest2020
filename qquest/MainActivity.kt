package com.htccs.android.neneyolka.main

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.authentication.AuthenticationActivity
import com.htccs.android.neneyolka.base.authorized.BaseAuthorizedActivity
import com.htccs.android.neneyolka.main.router.Router
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseAuthorizedActivity() {

    companion object {

        private const val PARAM_FIRST_START = "PARAM_FIRST_START"

        fun start(context: Context) {
            Intent(context, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .run(context::startActivity)
        }

        fun startFirst(context: Context) {
            Intent(context, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .apply { putExtra(PARAM_FIRST_START, true) }
                .run(context::startActivity)
        }
    }

    override val mainViewModel: MainViewModel by viewModel()
    private val router: Router by inject()

    override fun initView() {
        setContentView(R.layout.main_activity)
        supportFragmentManager.addOnBackStackChangedListener(this)
        router.initialize(supportFragmentManager, R.id.fragment_container)
        setupBottomNavigationView()
        showViewOnStart()
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            openFragmentByMenuItem(menuItem)
            true
        }
        bottomNavigationView.selectedItemId = R.id.menu_levels
    }

    private fun openFragmentByMenuItem(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.menu_leader_board -> router.openLeaderBoardFragment()
            R.id.menu_inventory -> router.openInventoryFragment()
            R.id.menu_levels -> router.openLevelsFragment()
            R.id.menu_support -> router.openSupportFragment()
            else -> throw NoSuchElementException("Listener for menu item ${menuItem.itemId} not implemented")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                mainViewModel.signOut()
                AuthenticationActivity.start(this)
                finish()
                true
            }
            else -> false
        }
    }

    override val shouldCheckExit get() = !router.canGoBack

    override fun doOnBackPressed() = router.onBackPressed()

    private fun showViewOnStart() {
        if (intent.getBooleanExtra(PARAM_FIRST_START, false)) {
            router.openLevelFragment(0)
        }
    }
}