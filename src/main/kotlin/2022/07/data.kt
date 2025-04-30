package `2022`.`07`

sealed class File {

    abstract val size: Long
    abstract val name: String

    data class Plain(
        override val size: Long,
        override val name: String,
    ) : File()

    data class Dir(
        override val name: String,
        val parent: Dir?,
        val files: MutableSet<File> = mutableSetOf(),
    ) : File() {
        override val size: Long
            get() = files.sumOf { it.size }

        override fun toString(): String {
            return name
        }

        // Necessary to implement hashCode and equals to resolve circular dependency
        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + size.hashCode()
            return result
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Dir

            if (name != other.name) return false
            return size == other.size
        }
    }
}
