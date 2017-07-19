package com.shibenli.basecom

/**
 * Created by shibenli on 2017/7/4.
 */

object BC{
    var TAG = "TAG"
        get private set

    var DEBUG :Boolean= true
        get private set

    @JvmOverloads fun init(tag :String = "TAG", debug: Boolean = false) {
        TAG = tag
        DEBUG = debug
    }

}