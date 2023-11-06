package delta

interface Cipher {
    fun encode(byteArray: ByteArray) : ByteArray
    fun decode(byteArray: ByteArray) : ByteArray
}