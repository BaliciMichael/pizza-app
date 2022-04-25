import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit
import java.util.*

fun main(args: Array<String>) {

    runMenu()
}
val scanner = Scanner(System.`in`)

fun mainMenu() : Int {
    print("""
          ----------------------------------
          |    Welcome to the Pizza App    |
          ----------------------------------
          | PIZZA MENU                     |
          |   1) Add a new Pizza           |
          |   2) List all Pizzas           |
          |   3) Update a Pizza            |
          |   4) Delete a Pizza            |
          ----------------------------------
          |   0) Exit                      |
          ----------------------------------
          ==>> """)
    return scanner.nextInt()
}
fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addPizza()
            2  -> listPizza()
            3  -> updatePizza()
            4  -> deletePizza()
            0  -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)
}
fun addPizza(){
    println("You chose Add Pizza")
}

fun listPizza(){
    println("You chose List Pizza")
}

fun updatePizza(){
    println("You chose Update this Pizza")
}

fun deletePizza(){
    println("You chose to Delete this Pizza")
}


fun exitApp(){
    println("Exiting...bye")
    exit(0)
}