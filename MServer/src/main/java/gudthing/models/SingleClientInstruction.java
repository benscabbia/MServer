package gudthing.models;

/**
 * Created by Ben on 03/05/2016.
 */
public class SingleClientInstruction {
    Message message;
    InstructionType instructionType;

    public SingleClientInstruction(Message message, InstructionType instructionType) {
        this.message = message;
        this.instructionType = instructionType;
    }

    public SingleClientInstruction() {
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public InstructionType getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(InstructionType instructionType) {
        this.instructionType = instructionType;
    }
}
