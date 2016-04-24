package gudthing.models;

import javax.persistence.*;

/**
 * Created by Ben on 24/04/2016.
 */

@Entity
@Table(name="clients")
public class ClientDb {
    public ClientDb() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="clientID")
    private int clientID;
    @Column(name="ipAddress")
    private String ipAddress;

    @Column(name="portNumber")
    private String portNumber;

    @Column(name="description")
    private String description;

    @Column(name="operatingSystem")
    private String operatingSystem;

    @Column(name="processor")
    private String processor;

    @Column(name="architecture")
    private String architecture;

    @Column(name="version")
    private String version;

    @Column(name="jvmVersion")
    private String jvmVersion;

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJvmVersion() {
        return jvmVersion;
    }

    public void setJvmVersion(String jvmVersion) {
        this.jvmVersion = jvmVersion;
    }

    @Override
    public String toString() {
        return "ClientDb{" +
                "clientID=" + clientID +
                ", ipAddress='" + ipAddress + '\'' +
                ", portNumber='" + portNumber + '\'' +
                ", description='" + description + '\'' +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", processor='" + processor + '\'' +
                ", architecture='" + architecture + '\'' +
                ", version='" + version + '\'' +
                ", jvmVersion='" + jvmVersion + '\'' +
                '}';
    }
}
