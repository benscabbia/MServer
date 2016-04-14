package gudthing.models;

/**
 * Created by Ben on 14/04/2016.
 */
public class Message {
    private int id;
    private String instruction;
    private InstructionType instructionType;
    private InstructionStatus instructionStatus;

    public Message(int id, String instruction, InstructionType instructionType) {
        this.id = id;
        this.instruction = instruction;
        this.instructionType = instructionType;
    }

    public int getId() {
        return id;
    }

    public String getInstruction() {
        return instruction;
    }

    public InstructionType getInstructionType() {
        return instructionType;
    }

    public InstructionStatus getInstructionStatus() {
        return instructionStatus;
    }

    public void setInstructionStatus(InstructionStatus instructionStatus) {
        this.instructionStatus = instructionStatus;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", instruction='" + instruction + '\'' +
                ", instructionType=" + instructionType +
                ", instructionStatus=" + instructionStatus +
                '}';
    }

    //required default constructor
    public Message(){

    }
}
