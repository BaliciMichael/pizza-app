import controllers.PizzaAPI
import models.Pizza
import persistence.JSONSerializer
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit
import java.util.*

fun main(args: Array<String>) {

    runMenu()
}
val scanner = Scanner(System.`in`)
private val pizzaApi = PizzaAPI(JSONSerializer(File("notes.yml")))
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
    val pizzaTitle = readNextLine("Enter a title for the note: ")
    val pizzaPrice = readNextDouble("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val pizzaSize = readNextInt("Enter a category for the note: ")
    val isAdded = pizzaApi.add(Pizza(pizzaTitle,pizzaPrice,true, pizzaSize))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listPizza(){
    println(pizzaApi.listPizzas())
}

fun updatePizza(){
    listPizza()
    if (pizzaApi.numberOfPizzas() > 0) {

        val indexToUpdate = readNextInt("Enter the index of the Pizza to update: ")
        if (pizzaApi.isValidIndex(indexToUpdate)) {
            val pizzaTitle = readNextLine("Enter a pizza name: ")
            val pizzaPrice = readNextDouble("Enter Price: ")
            val pizzaSize = readNextInt("Enter pizza size: ")



            if (pizzaApi.updatePizza(indexToUpdate, Pizza(pizzaTitle,pizzaPrice,true, pizzaSize))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deletePizza(){
    listPizza()
    if (pizzaApi.numberOfPizzas() > 0) {
        val indexToDelete = readNextInt("Enter the index of the Pizza you would like to delete: ")
        val noteToDelete = pizzaApi.deletePizza(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted Pizza: ${noteToDelete.PizzaTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}


fun exitApp(){
    println("Exiting...bye")
    exit(0)
}