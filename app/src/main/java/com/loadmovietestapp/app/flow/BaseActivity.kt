package com.loadmovietestapp.app.flow

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<TViewModel : BaseVM> : AppCompatActivity() {
    abstract val mViewModel: TViewModel
}