package delta

import java.util.zip.Inflater

class ZLib {
    companion object {

        fun decode(byteArray: ByteArray, decodingSize : Int): ByteArray {
            val decompresser = Inflater()
            decompresser.setInput(byteArray, 0, byteArray.size)
            val decompressedByteArray = ByteArray(decodingSize)
            decompresser.inflate(decompressedByteArray)
            decompresser.end()
            return decompressedByteArray
        }

    }
}