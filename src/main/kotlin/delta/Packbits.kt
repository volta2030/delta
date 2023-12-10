package delta

class Packbits {
    companion object {
        fun encode(byteArray: ByteArray) : ByteArray {

            var duplicateCount = 0
            var queue = byteArrayOf()
            var encryption = byteArrayOf()

            byteArray.forEachIndexed { index, byte ->

                if(index == byteArray.size - 1){
                    println("끝트머리!!!")
                }

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

       fun decode(byteArray: ByteArray) : ByteArray {
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