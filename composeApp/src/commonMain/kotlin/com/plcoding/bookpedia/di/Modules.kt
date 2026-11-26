package com.plcoding.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver

import com.plcoding.bookpedia.core.data.networking.HttpClientFactory
import com.plcoding.bookpedia.core.data.networking.TokenProviderImpl

import com.plcoding.bookpedia.features.books.data.database.DatabaseFactory
import com.plcoding.bookpedia.features.books.data.database.FavoriteBookDatabase

import com.plcoding.bookpedia.features.books.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.features.books.data.network.RemoteBookDataSourceImpl

import com.plcoding.bookpedia.features.books.data.repository.BookRepositoryImpl
import com.plcoding.bookpedia.features.books.domain.repositories.BookRepository

import com.plcoding.bookpedia.features.books.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.features.books.presentation.book_detail.BookDetailViewModel

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single {
        TokenProviderImpl()
    }
    single {
        HttpClientFactory.createHttpClient(
            get(),
            get<TokenProviderImpl>()
        )
    }
    singleOf(::RemoteBookDataSourceImpl)
        .bind<RemoteBookDataSource>()
    singleOf(::BookRepositoryImpl)
        .bind<BookRepository>()
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single {
        get<FavoriteBookDatabase>().favoriteBookDao
    }
    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
}
