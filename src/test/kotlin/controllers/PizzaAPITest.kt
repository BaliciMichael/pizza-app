package controllers

import models.Pizza
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import java.io.File
import kotlin.test.assertEquals

class PizzaAPITest {

    private var Capricossa: Pizza? = null
    private var Diavola: Pizza? = null
    private var Hawaian: Pizza? = null
    private var Margaritha: Pizza? = null
    private var Pepperoni: Pizza? = null
    private var populatedNotes: PizzaAPI? = PizzaAPI(JSONSerializer(File("pizzas.json")))
    private var emptyNotes: PizzaAPI? = PizzaAPI(JSONSerializer(File("pizzas.json")))

    @BeforeEach
    fun setup(){
        Capricossa = Pizza("Capricossa", 15.99, false, 12)
        Diavola = Pizza("Diavola", 18.40, false, 14)
        Hawaian = Pizza("Hawaian", 19.00, true, 14)
        Margaritha = Pizza("Margaritha", 10.50, false, 10)
        Pepperoni = Pizza("Pepperoni", 20.00, true, 16)

        //adding 5 Note to the notes api
        populatedNotes!!.add(Capricossa!!)
        populatedNotes!!.add(Diavola!!)
        populatedNotes!!.add(Hawaian!!)
        populatedNotes!!.add(Margaritha!!)
        populatedNotes!!.add(Pepperoni!!)
    }

    @AfterEach
    fun tearDown(){
        Capricossa = null
        Diavola = null
        Hawaian = null
        Margaritha = null
        Pepperoni = null
        populatedNotes = null
        emptyNotes = null
    }

    @Test
    fun `adding a Note to a populated list adds to ArrayList`(){
        val newNote = Pizza("Half and Half", 12.74, false, 12)
        assertTrue(populatedNotes!!.add(newNote))
    }

    @Test
    fun `adding a Note to an empty list adds to ArrayList`(){
        val newNote = Pizza("Half and Half", 12.74, false, 12)
        assertTrue(emptyNotes!!.add(newNote))
    }
}