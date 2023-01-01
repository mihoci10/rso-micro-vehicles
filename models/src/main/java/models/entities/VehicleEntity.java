package models.entities;

import javax.persistence.*;

@Entity
@Table(name = "vehicle")
@NamedQueries(value =
{
@NamedQuery(name = "VehicleEntity.getAll",
        query = "SELECT u FROM VehicleEntity u"),
@NamedQuery(name = "VehicleEntity.getOfUser",
        query = "SELECT u FROM VehicleEntity u WHERE u.ownerId = :ownerId")
})
public class VehicleEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"templateId\"", nullable = false)
    private VehicleTemplateEntity template;

    @Column(name = "\"ownerId\"", nullable = false)
    private Integer ownerId;

    @Column(name = "\"batteryPercent\"", nullable = false)
    private Double batteryPercent;

    @Column(name = "\"kilometersDriven\"", nullable = false)
    private Double kilometersDriven;

    public Double getKilometersDriven() {
        return kilometersDriven;
    }

    public void setKilometersDriven(Double kilometersDriven) {
        this.kilometersDriven = kilometersDriven;
    }

    public Double getBatteryPercent() {
        return batteryPercent;
    }

    public void setBatteryPercent(Double batteryPercent) {
        this.batteryPercent = batteryPercent;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public VehicleTemplateEntity getTemplate() {
        return template;
    }

    public void setTemplate(VehicleTemplateEntity template) {
        this.template = template;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
