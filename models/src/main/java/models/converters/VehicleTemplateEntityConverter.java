package models.converters;

import lib.VehicleDetails;
import lib.VehicleTemplateDetails;
import models.entities.VehicleTemplateEntity;

public class VehicleTemplateEntityConverter {

    public static VehicleTemplateEntity toEntity(VehicleDetails dto){
        VehicleTemplateEntity entity = new VehicleTemplateEntity();

        entity.setId(dto.getTemplateId());
        entity.setEfficiency(dto.getEfficiency());
        entity.setBatteryCapacity(dto.getBatteryCapacity());
        entity.setManufacturer(dto.getManufacturer());
        entity.setModel(dto.getModel());

        return entity;
    }

    public static VehicleDetails toDto(VehicleDetails dto, VehicleTemplateEntity entity) {

        dto.setTemplateId(entity.getId());
        dto.setEfficiency(entity.getEfficiency());
        dto.setBatteryCapacity(entity.getBatteryCapacity());
        dto.setManufacturer(entity.getManufacturer());
        dto.setModel(entity.getModel());

        return dto;
    }

    public static VehicleTemplateDetails toDto(VehicleTemplateEntity entity) {

        VehicleTemplateDetails dto = new VehicleTemplateDetails();

        dto.setId(entity.getId());
        dto.setEfficiency(entity.getEfficiency());
        dto.setBatteryCapacity(entity.getBatteryCapacity());
        dto.setManufacturer(entity.getManufacturer());
        dto.setModel(entity.getModel());

        return dto;
    }
}
