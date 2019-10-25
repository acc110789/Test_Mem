package sample

import kotlinx.cinterop.*


class FileStreamBuffer3 constructor(val size: Int) {

    private val arrayData = nativeHeap.allocArray<ByteVar>(size)

    fun free() {
        nativeHeap.free(arrayData)
    }

    fun get(index: Int): Byte {
        return arrayData[index]
    }

    fun set(index: Int, value: Byte) {
        arrayData[index] = value
    }

}