package com.will.draganddraw.toolbar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.will.draganddraw.R
import com.will.draganddraw.databinding.ActivityToolBarBinding

class ToolBarActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityToolBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityToolBarBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        // 启用 toolbar
        setSupportActionBar(viewBinding.toolbar)
    }

    // Toolbar 中的 action 按钮只会显示图标，菜单中的 action 按钮只会显示文字
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.backup -> Toast.makeText(this, "backup", Toast.LENGTH_SHORT).show()
            R.id.delete -> Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}