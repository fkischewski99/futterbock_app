package view.admin.new_participant

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import model.EatingHabit
import model.Participant

class NewParicipantScreen(private var paricipant: Participant?): Screen {

    @Composable
    override fun Content() {
        if(paricipant == null){
            paricipant = Participant(ArrayList(), null, EatingHabit.OMNIVORE, "", "", null, null);
        }
        NewParicipant(paricipant!!, onSave = {});
    }
}