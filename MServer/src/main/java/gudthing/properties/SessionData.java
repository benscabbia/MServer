package gudthing.properties;

/**
 * Created by Ben on 27/05/2016.
 */
public class SessionData implements Comparable<SessionData> {
    private long currentSystemTime;
    private String information;
    private String type;
    private String targetMachineIP;

    public SessionData(String information, String type, String targetMachineIP) {
        this.information = information;
        this.type = type;
        this.targetMachineIP = targetMachineIP;
        this.currentSystemTime = System.currentTimeMillis();
    }

    public long getCurrentSystemTime() {
        return currentSystemTime;
    }

    public void setCurrentSystemTime(long currentSystemTime) {
        this.currentSystemTime = currentSystemTime;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTargetMachineIP() {
        return targetMachineIP;
    }

    public void setTargetMachineIP(String targetMachineIP) {
        this.targetMachineIP = targetMachineIP;
    }

    public String displayTime(){
        int seconds = (int) (currentSystemTime / 1000) % 60 ;
        int minutes = (int) ((currentSystemTime / (1000*60)) % 60);
        int hours   = (int) ((currentSystemTime / (1000*60*60)) % 24);
        return hours + ":" + minutes + ":" + seconds;
    }

//    @Override
//    public int compare(SessionData o1, SessionData o2) {
//        return (int)(o1.currentSystemTime - o2.currentSystemTime);
//    }

    @Override
    public int compareTo(SessionData o) {
        return (int)o.currentSystemTime;

    }
}
