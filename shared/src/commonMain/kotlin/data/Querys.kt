package data

import kotlinx.datetime.LocalDate
import model.EatingHabit
import model.Event
import model.Meal
import model.MealType
import model.Participant
import view.event.new_meal_screen.ExampleObjects

class Querys {
    fun getCurrentEvents(): MutableList<Event>{
        var meal = Meal(
            LocalDate.parse("2024-12-01"),
            MealType.MITTAG,
            ExampleObjects.getAllRecepies()
        )
        var meal2 = Meal(
            LocalDate.parse("2024-12-01"),
            MealType.MITTAG,
            ExampleObjects.getAllRecepies()
        )
        var meals = mutableListOf(meal, meal2);

        var participant = Participant(
            listOf("Kiwi"),
            null,
            EatingHabit.VEGETARISCH,
            "Ronja",
            "Wehmeyer",
            LocalDate.parse("2024-12-01"),
            LocalDate.parse("2024-12-04"),
        );
        var participant2 = Participant(
            listOf(""),
            null,
            EatingHabit.OMNIVORE,
            "Fredö",
            "K",
            LocalDate.parse("2024-12-01"),
            LocalDate.parse("2024-12-04"),
        );
        val participants = mutableListOf(participant, participant2)
        return mutableListOf( Event(
            "id",
            LocalDate.parse("2024-12-01"),
            LocalDate.parse("2024-12-04"),
            "Stamm",
            "Group",
            "kitchen",
            meals,
            "Lager 1",
            participants
        ))
    }

    fun getPastEvents(): List<Event> {
        return listOf( Event(
            "id",
            LocalDate.parse("2023-12-08"),
            LocalDate.parse("2023-12-10"),
            "stammeslager",
            "ab",
            "yoyo",
            ArrayList(),
            "ab",
            ArrayList()
        ));
    }
}