package payload;

public class VehicleUpdatePayload {

    Integer vehicleId;
    double metersTraveled;

    public double getSecondsDuration() {
        return secondsDuration;
    }

    public void setSecondsDuration(double secondsDuration) {
        this.secondsDuration = secondsDuration;
    }

    double secondsDuration;

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public double getMetersTraveled() {
        return metersTraveled;
    }

    public void setMetersTraveled(double metersTraveled) {
        this.metersTraveled = metersTraveled;
    }
}
