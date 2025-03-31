package com.network.firebase.di

import com.network.firebase.firestore.Firestore
import com.network.firebase.firestore.FirestoreImpl
import com.network.firebase.realtime.RealtimeDatabase
import com.network.firebase.realtime.RealtimeDatabaseImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule: Module
    get() = module {
        singleOf(::FirestoreImpl).bind(Firestore::class)
        singleOf(::RealtimeDatabaseImpl).bind(RealtimeDatabase::class)
    }