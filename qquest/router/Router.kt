package com.htccs.android.neneyolka.main.router

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItem
import com.htccs.android.neneyolka.inventories.TabsFragment
import com.htccs.android.neneyolka.main.leaderboard.LeaderBoardFragment
import com.htccs.android.neneyolka.main.leaderboard.action.ActionLeaderBoardFragment
import com.htccs.android.neneyolka.main.level.game.LevelGameFragment
import com.htccs.android.neneyolka.main.level.scanned.LevelScannedFragment
import com.htccs.android.neneyolka.main.levelsFragment.LevelsFragment
import com.htccs.android.neneyolka.main.router.BackStackFragment.Companion.BACK_STACK_CONTAINER
import com.htccs.android.neneyolka.main.support.SupportFragment

private const val INVENTORY_TAB = "inventory"
private const val LEVELS_TAB = "levels"
private const val LEADER_BOARD_TAB = "leader board"
private const val SUPPORT_TAB = "support"
private const val STACK_HOLDER_NOT_FOUND = "stackHolder not found"

class Router {

    private lateinit var fragmentManager: FragmentManager
    private var currentTab = LEVELS_TAB
    private val stackHolders = hashMapOf(
        INVENTORY_TAB to BackStackFragment.newInstance(),
        LEVELS_TAB to BackStackFragment.newInstance(),
        LEADER_BOARD_TAB to BackStackFragment.newInstance(),
        SUPPORT_TAB to BackStackFragment.newInstance()
    )
    private var containerId: Int = View.NO_ID

    val canGoBack: Boolean
        get() {
            getStackHolder(currentTab).let {
                return if (it.isAdded) {
                    it.childFragmentManager.backStackEntryCount > 1
                } else {
                    false
                }
            }
        }

    fun initialize(activityFragmentManager: FragmentManager, containerId: Int) {
        this.containerId = containerId
        fragmentManager = activityFragmentManager
    }

    fun onBackPressed() = tryPopChildBackStack(getStackHolder(currentTab))

    private fun tryPopChildBackStack(fragment: BackStackFragment) {
        if (fragment.childFragmentManager.backStackEntryCount > 1) {
            fragment.childFragmentManager.popBackStack()
        }
    }

    fun openSupportFragment() {
        openBottomNavigationFragment(
            SUPPORT_TAB,
            SupportFragment.TAG
        ) { SupportFragment.newInstance() }
    }

    fun openLeaderBoardFragment() {
        openBottomNavigationFragment(
            LEADER_BOARD_TAB,
            LeaderBoardFragment.TAG
        ) { LeaderBoardFragment.newInstance() }
    }

    fun openLevelsFragment() {
        openBottomNavigationFragment(
            LEVELS_TAB,
            LevelsFragment.TAG
        ) { LevelsFragment.newInstance() }
    }

    fun openActionLeaderBoardFragment(inventoryItem: InventoryItem) {
        openFragment(
            currentTab,
            ActionLeaderBoardFragment.TAG
        ) { ActionLeaderBoardFragment.newInstance(inventoryItem) }
    }

    fun openLevelFragment(levelId: Int) {
        openFragment(
            currentTab,
            LevelScannedFragment.TAG
        ) { LevelScannedFragment.newInstance(levelId) }
    }

    fun openLevelGameFragment(levelId: Int) {
        openFragment(
            currentTab,
            LevelGameFragment.TAG
        ) { LevelGameFragment.newInstance(levelId) }
    }

    fun backToBackStackFragment(stateName: String) {
        getStackHolder(currentTab).childFragmentManager.popBackStack(stateName, 0)
    }

    fun openInventoryFragment() {
        openBottomNavigationFragment(
            INVENTORY_TAB,
            TabsFragment.TAG
        )
        { TabsFragment.newInstance() }
    }

    private fun openBottomNavigationFragment(
        tabName: String,
        tag: String,
        createFragment: () -> Fragment
    ) {
        currentTab = tabName
        getStackHolder(tabName)
            .let {
                openTab(it, tabName)
                if (it.childFragmentManager.backStackEntryCount == 0) {
                    openFragment(tabName, tag, createFragment)
                }
            }
    }

    private fun openTab(tabFragment: Fragment, tabName: String) {
        fragmentManager
            .beginTransaction()
            .addAnimationToFragment()
            .replace(containerId, tabFragment, tabName)
            .addToBackStack(null)
            .commit()
        fragmentManager.executePendingTransactions()
    }

    private fun openFragment(
        tabName: String,
        tag: String,
        createFragment: () -> Fragment
    ) {
        val fragment = getFragment(tabName, tag, createFragment)
        getStackHolder(tabName)
            .childFragmentManager
            .beginTransaction()
            .addAnimationToFragment()
            .replace(BACK_STACK_CONTAINER, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    private fun getFragment(
        tabName: String,
        tag: String,
        createFragment: () -> Fragment
    ): Fragment {
        return getStackHolder(tabName)
            .childFragmentManager
            .findFragmentByTag(tag)
            ?: createFragment()
    }

    private fun getStackHolder(tabName: String): BackStackFragment {
        return stackHolders[tabName] ?: throw NoSuchElementException(STACK_HOLDER_NOT_FOUND)
    }

    private fun FragmentTransaction.addAnimationToFragment() =
        setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)

}