import controllers.PizzaAPI
import models.Pizza
import persistence.JSONSerializer
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit
import java.util.*
import utils.Utilities

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
          |   5) Availability              |
          |   6) Search Pizza By Name      |
          |   9) Save                      |
          |   10) Load                     |
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
            5 -> specifyAvailability()
            6 -> searchPizzaByTitle()
            9 -> save()
            10 -> load()
            0  -> exitApp()
            else -> System.out.println("Invalid option entered: ${option}")
        }
    } while (true)
}
fun addPizza(){
    val pizzaTitle = readNextLine("Enter a pizza name: ")
    val pizzaPrice = readNextDouble("Enter Price: ")
    var pizzaSize = readNextInt("Enter pizza size(In inches) 10',12',14',16',18' : ")
    while (Utilities.validSize(pizzaSize)){
        println("Invalid number ")
        pizzaSize = readNextInt("Enter pizza size(In inches) 10',12',14',16',18' : ")
    }
    val isAdded = pizzaApi.add(Pizza(pizzaTitle,pizzaPrice,true, pizzaSize))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listPizza(){
    //println(pizzaApi.listPizzas())
    if (pizzaApi.numberOfPizzas() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------------------------
                  > |   1) View ALL Pizzas                           |
                  > |   2) View Pizzas with AVAILABLE toppings       |
                  > |   3) View Pizzas without AVAILABLE toppings    |
                  > --------------------------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllPizzas();
            2 -> listAvailablePizzas();
            3 -> listNonAvailablePizzas();
            else -> println("Invalid number entered: " + option);
        }
    } else {
        println("Option Invalid - No Pizzas stored");
    }
}
fun listAllPizzas(){
    print(pizzaApi.listPizzas())
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
            println("There are no pizzas for this index number")
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
fun listAvailablePizzas() {
    println(pizzaApi.listAvailablePizzas())
}
fun listNonAvailablePizzas(){
    print(pizzaApi.listNonAvailablePizzas())
}

fun specifyAvailability() {
    listAvailablePizzas()
    if (pizzaApi.pizzasWithAvailableToppings() > 0) {

        val indexToArchive = readNextInt("Enter the index of the pizza to show toppings are not available: ")

        if (pizzaApi.availability(indexToArchive)) {
            println("Action Successful!")
        } else {
            println("Action NOT Successful")
        }
    }
}
//extra methods
fun searchPizzaByTitle(){
    val sTitle = readNextLine("Enter the Pizza name: ")
    val sResult = pizzaApi.searchPizzaByTitle(sTitle)
    if(sResult.isEmpty()){
        println("No Pizza like that exists")
    }
    else{
        println(sResult)
    }
}


fun save() {
    try {
        pizzaApi.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        pizzaApi.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}


fun exitApp(){
    println("Exiting...bye")
    exit(0)
}