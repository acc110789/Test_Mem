package sample

import kotlinx.cinterop.*


class FileStreamBuffer2 constructor(val size: Int) {

    private val arrayData = ByteArray(size)

    fun get(index: Int): Byte {
        return arrayData[index]
    }

    fun opaque() {
        (0 until size).forEach {
            arrayData[it] = 100
        }
    }

    fun set(index: Int, value: Byte) {
        arrayData[index] = value
    }

}