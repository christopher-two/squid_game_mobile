package com.christopher_two.squid_game_mobile.di

import com.christopher_two.squid_game_mobile.MainActivity
import com.data.datastore.Datastore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule: Module
    get() = module {
        single {
            Datastore(get())
        }
    }