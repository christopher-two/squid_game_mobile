package com.shared.utils.enums

enum class KeysTensorflow(
    val first: String,
    val second: String,
    val third: String,
    val key: String,
    val model: String = "model.tflite"
) {
    KEY_1("0", "1", "2", key = "2012", model = "model.tflite"),
    KEY_2("3", "4", "5", key = "5343", model = "model_2.tflite"),
    KEY_3("6", "7", "8", key = "8676", model = "model_3.tflite"),
}