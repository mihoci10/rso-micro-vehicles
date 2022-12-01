package services.beans;

import lib.VehicleDetails;
import models.converters.VehicleEntityConverter;
import models.entities.VehicleEntity;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
}
