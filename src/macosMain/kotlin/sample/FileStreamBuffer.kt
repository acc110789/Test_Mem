package sample

import kotlinx.cinterop.*
import platform.Foundation.*
import platform.posix.uint8_tVar
import kotlin.native.concurrent.freeze

class FileStreamBuffer constructor(val size: Int) {

    private var nsData = NSMutableData()

    constructor(data: NSMutableData):this(data.length.toInt()){
        nsData = data
    }

    init {
        nsData.increaseLengthBy(size.convert())
    }


    fun opaque() {
        val arrayData = nativeHeap.allocArray<ByteVar>(size)
        val nsRange = NSMakeRange( 0 , size.convert())
        nsData.replaceBytesInRange(nsRange , arrayData)
        nativeHeap.free(arrayData)
    }

    fun getIOSBuffer(): CPointer<uint8_tVar> {
        return nsData.mutableBytes as CPointer<uint8_tVar>
    }

    fun get(index: Int): Byte {
        val bytePointer: CPointer<ByteVar> = nsData.mutableBytes as CPointer<ByteVar>
        return bytePointer[index]
    }

    operator fun set(index: Int, value: Byte) {
        val bytePointer: CPointer<ByteVar> = nsData.mutableBytes as CPointer<ByteVar>
        bytePointer[index] = value
    }

    private class ByteIteratorImpl(val collection: NSMutableData) : ByteIterator() {
        var index : Int = 0

        public override fun nextByte(): Byte {
            if (!hasNext()) throw NoSuchElementException("$index")
            val bytePointer: CPointer<ByteVar> = collection.mutableBytes as CPointer<ByteVar>
            return bytePointer[index++]
        }

        public override operator fun hasNext(): Boolean {
            return index < collection.length.convert<Int>()
        }
    }

    /** Creates an iterator over the elements of the array. */
    operator fun iterator(): ByteIterator {
        return ByteIteratorImpl(nsData)
    }

}