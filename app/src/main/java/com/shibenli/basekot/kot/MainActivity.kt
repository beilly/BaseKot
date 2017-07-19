package com.shibenli.basekot.kot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.shibenli.basekot.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView.text = "hello bugfix"
        titleBar.setMiddleTitleText(getString(R.string.app_name))
    }
}
