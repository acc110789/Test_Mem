package sample

import platform.darwin.NSObject
import platform.darwin.NSObjectMeta

class TestOneClass: NSObject() {

//    companion object : NSObjectMeta() {}

    private var value : Int = 0

    fun hello() {
        value ++
        println("value: $value")
    }
}