package delta

class Packbits {
    companion object : Cipher{
        @Deprecated("This method is deprecated. Use the decode() instead.",
            ReplaceWith("decode(byteArray)", "delta.Packbits.Companion.decode")
        )
        fun decodePackBits(byteArray: ByteArray) : ByteArray{
            return decode(byteArray)
        }

        override fun encode(byteArray: ByteArray) : ByteArray {

            var duplicateCount = 0
            var queue = byteArrayOf()
            var encryption = byteArrayOf()

            byteArray.forEachIndexed { index, byte ->
                if(byte != byteArray[index + 1]){
                    if(duplicateCount > 0){
                        encryption += (-duplicateCount).toByte()
                        encryption += byte
                        duplicateCount = 0
                    }
                    queue += byte
                }else{
                    if(queue.size > 1){
                        encryption += (queue.size - 1).toByte()
                        encryption += queue
                        queue = byteArrayOf()
                    }
                    duplicateCount++

                }
            }

            return encryption
        }

        override fun decode(byteArray: ByteArray) : ByteArray {
            var decryption = byteArrayOf()
            var i = 0
            while(i < byteArray.size){
                when (val integer = byteArray[i].toInt()) {
                    -128 -> {
                        i = byteArray.size
                    }
                    in 0..<128 -> {
                        decryption += byteArray.sliceArray(i+ 1..<i + 1 + (integer + 1))
                        i += integer + 2
                    }
                    in -127..<0 -> {
                        for(j : Int in 0..<-integer + 1){
                            decryption += byteArray[i + 1]
                        }
                        i += 2
                    }
                }
            }
            return decryption
        }
    }
}