package `2015`.`13`

class Member (val name: String) {

    private val others = mutableMapOf<String, Int>()

    fun addLink(name: String, score: Int) {
        others[name] = score
    }

    fun getLink(name: String): Int {
        return others[name] ?: 0
    }

    // Bob, Frank , Mallory , Eric, George , Alice, Carol, David
    // 56,  82,     58,       66,    76,     178    118,  73
}