package models.converters;

import lib.VehicleDetails;
import models.entities.VehicleEntity;

public class VehicleEntityConverter {

    public static VehicleDetails toDto(VehicleEntity entity) {
        VehicleDetails dto = new VehicleDetails();

        dto.setId(entity.getId());
        dto.setBatteryPercent(entity.getBatteryPercent());
        dto.setKilometersDriven(entity.getKilometersDriven());
        dto.setOwnerId(entity.getOwnerId());
        dto = VehicleTemplateEntityConverter.toDto(dto, entity.getTemplate());

        return dto;
    }

    public static VehicleEntity toEntity(VehicleDetails dto){
        VehicleEntity entity = new VehicleEntity();

        entity.setId(dto.getId());
        entity.setBatteryPercent(dto.getBatteryPercent());
        entity.setKilometersDriven(dto.getKilometersDriven());
        entity.setOwnerId(dto.getOwnerId());
        entity.setTemplate(VehicleTemplateEntityConverter.toEntity(dto));

        return entity;
    }



}
