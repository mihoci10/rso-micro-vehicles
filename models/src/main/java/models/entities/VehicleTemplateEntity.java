package models.entities;

import javax.persistence.*;

@Entity
@Table(name = "vehicleTemplate")
@NamedQueries(value =
        {
                @NamedQuery(name = "VehicleTemplateEntity.getAll",
                        query = "SELECT u FROM VehicleTemplateEntity u")
        })
public class VehicleTemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "model", nullable = false)
    private String model;

    @Lob
    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "\"batteryCapacity\"", nullable = false)
    private Double batteryCapacity;

    @Column(name = "efficiency", nullable = false)
    private Double efficiency;

    public Double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Double efficiency) {
        this.efficiency = efficiency;
    }

    public Double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}