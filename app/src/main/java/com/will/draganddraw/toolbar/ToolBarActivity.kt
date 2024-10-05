package com.will.draganddraw.toolbar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.will.draganddraw.R
import com.will.draganddraw.databinding.ActivityToolBarBinding
import kotlin.concurrent.thread

class ToolBarActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityToolBarBinding
    private lateinit var fruitAdapter: FruitAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityToolBarBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        // 启用 toolbar
        setSupportActionBar(viewBinding.toolbar)
        // toolbar 与 draw layout 关联
        supportActionBar?.let {
            // 这里是让 toolbar 最左侧的图标显示出来，一般是一个向左的箭头图片，意思是返回上一级
            it.setDisplayHomeAsUpEnabled(true)
            // 修改图片为菜单形状
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        viewBinding.navigationView.setNavigationItemSelectedListener {menuItem->
            Toast.makeText(this, menuItem.title, Toast.LENGTH_SHORT).show()
            viewBinding.drawerLayout.closeDrawers()
            true
        }

        viewBinding.fab.setOnClickListener{
            Snackbar.make(it, "Data deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                    Toast.makeText(this, "Data restored", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        initData()
        fruitAdapter = FruitAdapter(this@ToolBarActivity, fruitList)
        viewBinding.recyclerView.apply {
            // 这个是瀑布流的适配器
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = fruitAdapter

        }
        // 设置下拉刷新进度条的颜色
        viewBinding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        viewBinding.swipeRefresh.setOnRefreshListener {
            refreshFruits(fruitAdapter)
        }
    }

    // Toolbar 中的 action 按钮只会显示图标，菜单中的 action 按钮只会显示文字
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            // home 菜单的 id 固定是  android.R.id.home ，打开抽屉菜单
            android.R.id.home -> viewBinding.drawerLayout.openDrawer(GravityCompat.START)
            R.id.backup -> Toast.makeText(this, "backup", Toast.LENGTH_SHORT).show()
            R.id.delete -> Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    val fruits = mutableListOf(
        Fruit("Apple", R.drawable.apple),
        Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange),
        Fruit("Pear", R.drawable.pear),
        Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pear),
        Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry),
        Fruit("Mango", R.drawable.mango),
    )
    val fruitList = ArrayList<Fruit>()
    private fun initData() {
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    private fun refreshFruits(adapter: FruitAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread{
                initData()
                adapter.notifyDataSetChanged()
                viewBinding.swipeRefresh.isRefreshing = false
            }
        }
    }
}