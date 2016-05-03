package gudthing.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 03/05/2016.
 */
public class SingleClientInstructionWrapper {

    private List<SingleClientInstruction> instructionArray = new ArrayList<SingleClientInstruction>();

    public List<SingleClientInstruction> getInstructionArray() {
        return instructionArray;
    }

    public void setInstructionArray(List<SingleClientInstruction> instructionArray) {
        this.instructionArray = instructionArray;
    }

    public SingleClientInstructionWrapper() {
    }
}
