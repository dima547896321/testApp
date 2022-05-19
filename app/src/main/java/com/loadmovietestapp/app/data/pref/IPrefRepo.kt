package com.loadmovietestapp.app.data.pref

interface IPrefRepo {
    fun setApiKey(value: String)
    fun getApiKey(): String
}