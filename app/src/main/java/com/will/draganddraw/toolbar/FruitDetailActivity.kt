package com.will.draganddraw.toolbar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.will.draganddraw.databinding.ActivityFruitDetailBinding

class FruitDetailActivity: AppCompatActivity() {

    companion object {
        const val FRUIT_NAME = "fruit_name"
        const val FRUIT_IMAGE_ID = "fruit_image_id"

        fun startFruitDetailActivity(context: Context, name: String, id: Int) {
            val intent = Intent(context, FruitDetailActivity::class.java)
            intent.putExtra(FRUIT_NAME, name)
            intent.putExtra(FRUIT_IMAGE_ID, id)
            context.startActivity(intent)
        }
    }

    private lateinit var viewBinding: ActivityFruitDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFruitDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val fruitName = intent.getStringExtra(FRUIT_NAME) ?: ""
        val fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0)
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewBinding.collapsingToolbar.title = fruitName
        Glide.with(this).load(fruitImageId).into(viewBinding.fruitImageView)
        viewBinding.fruitContentText.text = generateFruitContent(fruitName)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun generateFruitContent(fruitName: String) = fruitName.repeat(500)
}