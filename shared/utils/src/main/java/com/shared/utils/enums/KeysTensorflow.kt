package com.shared.utils.enums

enum class KeysTensorflow(
    val key: String,
    val model: String,
    val label: String
) {
    KEY_1(key = "2012", model = "model1.tflite", label = "labels1.txt"),
    KEY_2(key = "5343", model = "model2.tflite", label = "labels2.txt"),
    KEY_3(key = "8676", model = "model3.tflite", label = "labels3.txt"),
}