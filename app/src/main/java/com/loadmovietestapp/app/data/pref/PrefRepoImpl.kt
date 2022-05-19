package com.loadmovietestapp.app.data.pref

class PrefRepoImpl(private val prefHelper: IBasePreferencesHelper) : IPrefRepo {
    companion object {
        const val API_KEY = "API_KEY"
    }

    override fun setApiKey(value: String) {
        prefHelper.setStringToPrefs(API_KEY, value)
    }

    override fun getApiKey(): String {
//        return prefHelper.getStringFromPrefs(API_KEY, "")!!
        return "9278e7bc"
    }
}