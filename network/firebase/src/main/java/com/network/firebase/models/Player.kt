package com.network.firebase.models

data class Player(
    val id: String?,
    val sex: String = "Male",
    val gender: String = "Normal",
    val fullName: String = "Christopher Alejandro Maldonado Chavez",
    val height: Int = 180,
    val weight: Int = 100,
    val city: String = "uruapan york",
    val country: String = "Michoacan",
    val financialHistory: Int = 900000,
    val age: Int = 20,
    val civilStatus: String = "Single",
    val numPlayer: Int = 13,
    val image: String? = "https://scontent.fupn2-1.fna.fbcdn.net/v/t39.30808-6/481317602_2143244839428802_3669022559401477759_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=a5f93a&_nc_ohc=348eNUEtGyQQ7kNvgFdrFco&_nc_oc=Adlev6vcjrpqvHGE556e0SWds-5nM6I18sSD6XOAVOXMIEBU_NSgqDvxQkqMFEAUspMF3vdKOYrtBI0DDbW9wded&_nc_zt=23&_nc_ht=scontent.fupn2-1.fna&_nc_gid=Wx45IQwXIvA6h3CtMv-txw&oh=00_AYH5eu1hYeBisFxzkh1XrMiW-iraaGl3pKJQZmNBLcqNuQ&oe=67EFCFC4",
)