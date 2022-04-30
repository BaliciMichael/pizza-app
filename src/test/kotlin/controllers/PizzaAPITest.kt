package controllers

import models.Pizza
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
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
    fun `adding a Pizza to a populated list adds to ArrayList`(){
        val newNote = Pizza("Half and Half", 12.74, false, 12)
        assertEquals(5, populatedNotes!!.numberOfPizzas())
        assertTrue(populatedNotes!!.add(newNote))
        assertEquals(6, populatedNotes!!.numberOfPizzas())
        assertEquals(newNote, populatedNotes!!.findPizza(populatedNotes!!.numberOfPizzas() - 1))
    }

    @Test
    fun `adding a Pizza to an empty list adds to ArrayList`(){
        val newNote = Pizza("Half and Half", 12.74, false, 12)
        assertEquals(0, emptyNotes!!.numberOfPizzas())
        assertTrue(emptyNotes!!.add(newNote))
        assertEquals(1, emptyNotes!!.numberOfPizzas())
        assertEquals(newNote, emptyNotes!!.findPizza(emptyNotes!!.numberOfPizzas() - 1))
    }

    @Test
    fun `listPizzas returns No Pizzas Stored message when ArrayList is empty`() {
        assertEquals(0, emptyNotes!!.numberOfPizzas())
        assertTrue(emptyNotes!!.listPizzas().lowercase().contains("no pizzas"))
    }

    @Test
    fun `listPizzas returns Pizzas when ArrayList has Pizzas stored`() {
        assertEquals(5, populatedNotes!!.numberOfPizzas())
        val notesString = populatedNotes!!.listPizzas()
        assertTrue(notesString.contains("Capricossa"))
        assertTrue(notesString.contains("Diavola"))
        assertTrue(notesString.contains("Hawaian"))
        assertTrue(notesString.contains("Margaritha"))
        assertTrue(notesString.contains("Pepperoni"))
    }
    @Nested
    inner class ArchiveNotes {
        @Test
        fun `testing that changing topping availability for a pizza that does not exist returns false`(){
            assertFalse(populatedNotes!!.availability(6))
            assertFalse(populatedNotes!!.availability(-1))
            assertFalse(emptyNotes!!.availability(0))
        }

        @Test
        fun `changing an already changed pizza returns false`(){
            assertTrue(populatedNotes!!.findPizza(2)!!.ToppingsAvailable)
            assertFalse(populatedNotes!!.availability(2))
        }

        @Test
        fun `changing an available pizza changes and returns true`() {
            assertTrue(populatedNotes!!.findPizza(1)!!.ToppingsAvailable)
            assertTrue(populatedNotes!!.availability(1))
            assertFalse(populatedNotes!!.findPizza(1)!!.ToppingsAvailable)
        }
    }


    @Test
    fun `saving and loading an empty collection in JSON doesn't crash app`() {
        // Saving an empty notes.json file.
        val storingPizzas = PizzaAPI(JSONSerializer(File("pizzas.json")))
        storingPizzas.store()

        //Loading the empty notes.json file into a new object
        val loadedPizzas = PizzaAPI(JSONSerializer(File("pizzas.json")))
        loadedPizzas.load()

        //Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
        Assertions.assertEquals(0, storingPizzas.numberOfPizzas())
        Assertions.assertEquals(0, loadedPizzas.numberOfPizzas())
        assertEquals(storingPizzas.numberOfPizzas(), loadedPizzas.numberOfPizzas())
    }

    @Test
    fun `saving and loading an loaded collection in JSON doesn't loose data`() {
        // Storing 3 notes to the notes.json file.
        val storingPizzas = PizzaAPI(JSONSerializer(File("pizzas.json")))
        storingPizzas.add(Capricossa!!)
        storingPizzas.store()

        //Loading notes.json into a different collection
        val loadedPizzas = PizzaAPI(JSONSerializer(File("pizzas.json")))
        loadedPizzas.load()

        //Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
        Assertions.assertEquals(1, storingPizzas.numberOfPizzas())
        Assertions.assertEquals(1, loadedPizzas.numberOfPizzas())
        assertEquals(storingPizzas.numberOfPizzas(), loadedPizzas.numberOfPizzas())
        assertEquals(storingPizzas.findPizza(0), loadedPizzas.findPizza(0))

    }
}