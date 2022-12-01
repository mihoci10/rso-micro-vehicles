package models.converters;

import lib.VehicleDetails;
import models.entities.VehicleTemplate;

public class VehicleTemplateConverter {

    public static VehicleTemplate toEntity(VehicleDetails dto){
        VehicleTemplate entity = new VehicleTemplate();

        entity.setId(dto.getTemplateId());
        entity.setEfficiency(dto.getEfficiency());
        entity.setBatteryCapacity(dto.getBatteryCapacity());
        entity.setManufacturer(dto.getManufacturer());
        entity.setModel(dto.getModel());

        return entity;
    }

    public static VehicleDetails toDto(VehicleDetails dto, VehicleTemplate entity) {

        dto.setTemplateId(entity.getId());
        dto.setEfficiency(entity.getEfficiency());
        dto.setBatteryCapacity(entity.getBatteryCapacity());
        dto.setManufacturer(entity.getManufacturer());
        dto.setModel(entity.getModel());

        return dto;
    }
}
