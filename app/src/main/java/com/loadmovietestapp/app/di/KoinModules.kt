package com.loadmovietestapp.app.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.loadmovietestapp.app.data.database.AppDatabase
import com.loadmovietestapp.app.data.database.DatabaseRepoImpl
import com.loadmovietestapp.app.data.database.IDatabaseRepo
import com.loadmovietestapp.app.data.network.ApiService
import com.loadmovietestapp.app.data.network.IRemoteRepo
import com.loadmovietestapp.app.data.network.RemoteRepoImpl
import com.loadmovietestapp.app.data.pref.CommonPreferencesHelperImpl
import com.loadmovietestapp.app.data.pref.IBasePreferencesHelper
import com.loadmovietestapp.app.data.pref.IPrefRepo
import com.loadmovietestapp.app.data.pref.PrefRepoImpl
import com.loadmovietestapp.app.data.providers.IMovieProvider
import com.loadmovietestapp.app.data.providers.MovieProviderImpl
import com.loadmovietestapp.app.flow.details.DetailsVM
import com.loadmovietestapp.app.flow.main.MainVM
import com.loadmovietestapp.app.utils.API_URL
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoriesModule = module {
    single { RemoteRepoImpl(get()) } bind IRemoteRepo::class
    single { PrefRepoImpl(get()) } bind IPrefRepo::class
    single { CommonPreferencesHelperImpl(get()) } bind IBasePreferencesHelper::class
    single { DatabaseRepoImpl(get()) } bind IDatabaseRepo::class
    single { provideDatabase(get()) }
}

val viewModelsModule = module {
    viewModel { MainVM(get()) }
    viewModel { DetailsVM(get()) }
}

val providers = module {
    single { MovieProviderImpl(get(), get(), get()) } bind IMovieProvider::class
}

val networkModule = module {
    factory { provideLoggingInterceptor() }
    factory { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideAPI(get()) }

}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {

    val interceptor = HttpLoggingInterceptor(PrettyLoggerRemote())
    interceptor.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    return interceptor
}

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    val okHttpClient = OkHttpClient().newBuilder().apply {
        addInterceptor(interceptor)
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        dispatcher(Dispatcher().apply {
            maxRequestsPerHost = 1
            maxRequests = 1
        })
    }
    return okHttpClient.build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val retrofit = Retrofit.Builder().apply {
        baseUrl(API_URL)
        client(okHttpClient)
        addConverterFactory(provideGson())
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }
    return retrofit.build()
}

fun provideAPI(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun provideGson(): GsonConverterFactory {

    val gson = GsonBuilder()
        .serializeNulls()
        .setPrettyPrinting()
//        .registerTypeAdapter(Collection::class.java, CollectionDeserializer())
        .create()

    return GsonConverterFactory.create(gson)
}

fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "test_app_database").build()
}