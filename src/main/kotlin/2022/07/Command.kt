package `2022`.`07`

sealed class Command {
    object Ls : Command()
    data class Cd(val directory: String) : Command()
}