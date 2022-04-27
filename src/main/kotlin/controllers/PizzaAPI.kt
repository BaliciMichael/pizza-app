package controllers
import persistence.Serializer;
import models.Pizza;

class PizzaAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var pizzas = ArrayList<Pizza>()



    fun add(pizza: Pizza): Boolean {
        return pizzas.add(pizza)
    }
    fun listPizzas(): String {
        return if (pizzas.isEmpty()) {
            "No Pizzas exist"
        } else {
            var listOfPizzas = ""
            for (i in pizzas.indices) {
                listOfPizzas += "${i}: ${pizzas[i]} \n"
            }
            listOfPizzas
        }
    }
    fun numberOfPizzas(): Int {
        return pizzas.size
    }
    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
//delete method

    fun deletePizza(indexToDelete: Int): Pizza? {
        return if (isValidListIndex(indexToDelete, pizzas)) {
            pizzas.removeAt(indexToDelete)
        } else null
    }
    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, pizzas);
    }

    fun findPizza(index: Int): Pizza? {
        return if (isValidListIndex(index, pizzas)) {
            pizzas[index]
        } else null
    }

    fun updatePizza(indexToUpdate: Int, pizza: Pizza?): Boolean {

        val foundPizza = findPizza(indexToUpdate)


        if ((foundPizza!= null) && (pizza != null)) {
            foundPizza.PizzaTitle = pizza.PizzaTitle
            foundPizza.PizzaPrice = pizza.PizzaPrice
            foundPizza.PizzaSize = pizza.PizzaSize
            return true
        }


        return false
    }

}