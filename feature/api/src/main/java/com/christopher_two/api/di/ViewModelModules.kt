package com.christopher_two.api.di

import com.christopher_two.camera.CameraViewModel
import com.christopher_two.login.LoginViewModel
import com.christopher_two.start.StartViewModel
import com.home.viewmodel.HomeInterface
import com.home.viewmodel.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val viewModelModule: Module
    get() = module {
        viewModelOf(::StartViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::CameraViewModel)
        viewModelOf(::HomeViewModel).bind(HomeInterface::class)
    }