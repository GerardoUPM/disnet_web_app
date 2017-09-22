package edu.upm.midas.data.relational.entities.disnetdb;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by gerardo on 12/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className SystemService
 * @see
 */
@Entity
@Table(name = "system_service", schema = "disnetdb", catalog = "")
public class SystemService {
    private String serviceId;
    private boolean enabled;
    private String name;
    private String description;
    private List<LogQueryService> logQueryServicesByServiceId;
    private List<ProfileService> profileServicesByServiceId;
    private List<ServiceUrl> serviceUrlsByServiceId;

    @Id
    @Column(name = "service_id", nullable = false, length = 5)
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 55)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 250)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemService that = (SystemService) o;
        return Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(enabled, that.enabled) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, enabled, name, description);
    }

    @OneToMany(mappedBy = "systemServiceByServiceId")
    public List<LogQueryService> getLogQueryServicesByServiceId() {
        return logQueryServicesByServiceId;
    }

    public void setLogQueryServicesByServiceId(List<LogQueryService> logQueryServicesByServiceId) {
        this.logQueryServicesByServiceId = logQueryServicesByServiceId;
    }

    @OneToMany(mappedBy = "systemServiceByServiceId")
    public List<ProfileService> getProfileServicesByServiceId() {
        return profileServicesByServiceId;
    }

    public void setProfileServicesByServiceId(List<ProfileService> profileServicesByServiceId) {
        this.profileServicesByServiceId = profileServicesByServiceId;
    }

    @OneToMany(mappedBy = "systemServiceByServiceId")
    public List<ServiceUrl> getServiceUrlsByServiceId() {
        return serviceUrlsByServiceId;
    }

    public void setServiceUrlsByServiceId(List<ServiceUrl> serviceUrlsByServiceId) {
        this.serviceUrlsByServiceId = serviceUrlsByServiceId;
    }
}
