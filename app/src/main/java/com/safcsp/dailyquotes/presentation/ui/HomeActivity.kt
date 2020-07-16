package com.safcsp.dailyquotes.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.safcsp.dailyquotes.R
import com.safcsp.dailyquotes.presentation.viewmodel.RandomQuotesViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val randomQuotesViewModel by viewModel<RandomQuotesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        randomQuotesViewModel.getRandomQuote.observe(this, Observer { quote ->
            if (quote != null) {
                Log.d(TAG, "onCreate: $quote")
                quote_content.text = "“ ${quote.content} ”"
                quote_author.text = quote.author
            }
        })

        randomQuotesViewModel.getError.observe(this, Observer {
            if (it != null) {
                Snackbar.make(
                    quote_author, getString(R.string.no_internet_msg),
                    Snackbar.LENGTH_SHORT
                ).show()
                Log.e(TAG, "onCreate: ${it.localizedMessage}")
            }
        })

        randomQuotesViewModel.getProgress.observe(this, Observer { isShown ->
            if (isShown) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun animate() {
        val animation: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.animation)
        quote_content.startAnimation(animation)
        quote_author.startAnimation(animation)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                randomQuotesViewModel.loadData()
                animate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}