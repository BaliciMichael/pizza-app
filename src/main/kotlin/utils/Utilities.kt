package utils

object Utilities {
    @JvmStatic
    fun validSize(size: Int):Boolean{
        if(size==10 || size==12 || size==14|| size==16 || size==18){

            return false
        }
        else{
            return true
        }
    }

}