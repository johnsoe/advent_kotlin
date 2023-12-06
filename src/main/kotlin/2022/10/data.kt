package `2022`.`10`

sealed class Cmd {
    object Noop: Cmd()
    data class Add(val add: Int): Cmd()
}