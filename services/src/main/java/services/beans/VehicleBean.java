package services.beans;

import lib.VehicleDetails;
import lib.VehicleTemplateDetails;
import models.converters.VehicleEntityConverter;
import models.converters.VehicleTemplateEntityConverter;
import models.entities.VehicleEntity;
import models.entities.VehicleTemplateEntity;
import payload.VehiclePayload;
import payload.VehicleTemplatePayload;
import payload.VehicleUpdatePayload;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Model
@RequestScoped
public class VehicleBean {

    private Logger log = Logger.getLogger(VehicleBean.class.getName());

    @Inject
    private EntityManager em;

    public List<VehicleDetails> getAllVehicles(){
        List<VehicleEntity> resultList = null;
        try{
            TypedQuery<VehicleEntity> query = em.createNamedQuery("VehicleEntity.getAll", VehicleEntity.class);

            resultList = query.getResultList();

        }catch (Exception e){
            System.out.println(e);
        }

        return resultList.stream().map(VehicleEntityConverter::toDto).collect(Collectors.toList());
    }

    public VehicleDetails getVehicle(Integer vehicleId) {

        VehicleEntity vehicleEntity = em.find(VehicleEntity.class, vehicleId);

        return VehicleEntityConverter.toDto(vehicleEntity);
    }

    public List<VehicleDetails> getUserVehicles(Integer userId) {
        List<VehicleEntity> resultList = null;
        try{
            TypedQuery<VehicleEntity> query = em.createNamedQuery("VehicleEntity.getOfUser", VehicleEntity.class)
                    .setParameter("ownerId", userId);

            resultList = query.getResultList();

        }catch (Exception e){
            System.out.println(e);
        }

        return resultList.stream().map(VehicleEntityConverter::toDto).collect(Collectors.toList());
    }

    public List<VehicleTemplateDetails> getAllTemplates(){
        List<VehicleTemplateEntity> resultList = null;
        try{
            TypedQuery<VehicleTemplateEntity> query = em.createNamedQuery("VehicleTemplateEntity.getAll", VehicleTemplateEntity.class);

            resultList = query.getResultList();

        }catch (Exception e){
            System.out.println(e);
        }

        return resultList.stream().map(VehicleTemplateEntityConverter::toDto).collect(Collectors.toList());
    }

    public void addVehicle(VehiclePayload vehiclePayload){

        try {
            beginTx();

            VehicleEntity vehicleEntity = new VehicleEntity();
            VehicleTemplateEntity templateEntity = em.find(VehicleTemplateEntity.class, vehiclePayload.getTemplateId());

            vehicleEntity.setTemplate(templateEntity);
            vehicleEntity.setOwnerId(vehiclePayload.getOwnerId());
            vehicleEntity.setKilometersDriven(vehiclePayload.getKilometersDriven());
            vehicleEntity.setBatteryPercent(vehiclePayload.getBatteryPercent());

            em.persist(vehicleEntity);

            commitTx();

        }catch (Exception e) {
            rollbackTx();

            throw e;
        }

    }

    public void addVehicleTemplate(VehicleTemplatePayload vehicleTemplatePayload){

        try {
            beginTx();

            VehicleTemplateEntity vehicleTemplateEntity = new VehicleTemplateEntity();

            vehicleTemplateEntity.setModel(vehicleTemplatePayload.getModel());
            vehicleTemplateEntity.setManufacturer(vehicleTemplatePayload.getManufacturer());
            vehicleTemplateEntity.setEfficiency(vehicleTemplatePayload.getEfficiency());
            vehicleTemplateEntity.setBatteryCapacity(vehicleTemplatePayload.getBatteryCapacity());

            em.persist(vehicleTemplateEntity);

            commitTx();

        }catch (Exception e) {
            rollbackTx();

            throw e;
        }

    }

    public Double updateVehicle(VehicleUpdatePayload vehicleUpdatePayload) {

        try{
            beginTx();

            VehicleEntity vehicleEntity = em.find(VehicleEntity.class, vehicleUpdatePayload.getVehicleId());

            double currKWH = vehicleEntity.getBatteryPercent() * vehicleEntity.getTemplate().getBatteryCapacity();
            double usedKWH = 0;
            if (vehicleUpdatePayload.getSecondsDuration() > 0)
                usedKWH = ((vehicleUpdatePayload.getMetersTraveled() / vehicleUpdatePayload.getSecondsDuration())
                    * vehicleEntity.getTemplate().getEfficiency());

            vehicleEntity.setKilometersDriven(vehicleEntity.getKilometersDriven() + (vehicleUpdatePayload.getMetersTraveled()/1000));
            vehicleEntity.setBatteryPercent((currKWH - usedKWH) / vehicleEntity.getTemplate().getBatteryCapacity());

            commitTx();
            return usedKWH;
        }catch (Exception e){
            rollbackTx();

            throw e;
        }
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
