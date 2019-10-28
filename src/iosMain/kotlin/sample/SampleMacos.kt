package sample

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.cValuesOf
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import platform.darwin.*
import platform.posix.uint8_tVar
import kotlin.native.concurrent.freeze
import kotlin.native.concurrent.isFrozen
import kotlin.native.internal.GC

fun hello(): String = "Hello, Kotlin/Native!"

val fileBufferList = mutableListOf<FileStreamBuffer>()
//val fileBufferList2 = mutableListOf<FileStreamBuffer2>()
//val fileBufferList3 = mutableListOf<FileStreamBuffer3>()

fun main() {

    println("enter SampleMacos main")

    val mainQueue = dispatch_get_main_queue()

    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 5000 * NSEC_PER_MSEC.toLong()), mainQueue) {

        println("start gen object")

        repeat(800) {
            //1M
            val size = 4 * 1024 * 1024
            val buffer = FileStreamBuffer(size)
            buffer.opaque()
            fileBufferList.add(buffer)
        }

        println("gen object end")

        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 4000 * NSEC_PER_MSEC.toLong()), mainQueue) {
            println("release fileBufferList")
//            fileBufferList2.clear()
            fileBufferList.clear()

//            fileBufferList3.forEach { it.free() }
//            fileBufferList3.clear()
//            invokeGC()
        }
    }

//    test()
}

fun test() {
    val obj = TestOneClass()
    obj.hello()
    obj.freeze()
    println("after freeze")
    println("is frozen: ${obj.isFrozen}")
    obj.hello()
}

private fun invokeGC() {
    val mainQueue = dispatch_get_main_queue()

    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 1000 * NSEC_PER_MSEC.toLong()), mainQueue) {
        println("GC^^")
        GC.collect()
    }

    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 5000 * NSEC_PER_MSEC.toLong()), mainQueue) {
        println("GC again ^^")
        GC.collect()
    }
}