package gudthing.models.spark;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 09/05/2016.
 */
public class SingleClientSparkInstructionWrapper {
    private List<SingleClientSparkInstruction> sparkInstructionArray = new ArrayList<SingleClientSparkInstruction>();

    public List<SingleClientSparkInstruction> getSparkInstructionArray() {
        return sparkInstructionArray;
    }

    public void setSparkInstructionArray(List<SingleClientSparkInstruction> sparkInstructionArray) {
        this.sparkInstructionArray = sparkInstructionArray;
    }

    public SingleClientSparkInstructionWrapper() {
    }
}
