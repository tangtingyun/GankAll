package com.step.data.model

data class BannerBean(
    val `data`: List<Data>,
    val status: Int
)

data class Data(
    val image: String,
    val title: String,
    val url: String
)