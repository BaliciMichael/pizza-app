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




}