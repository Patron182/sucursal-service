package com.fravega.sucursal.services;

import com.fravega.sucursal.entities.BranchOfficeEntity;
import com.fravega.sucursal.exception.BranchOfficeErrorCode;
import com.fravega.sucursal.exception.BranchOfficeException;
import com.fravega.sucursal.models.BranchOfficeModel;
import com.fravega.sucursal.repository.BranchOfficeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class BranchOfficeService {
    private BranchOfficeRepository branchOfficeRepository;
    private static final Logger log = LogManager.getLogger(BranchOfficeService.class);

    @Autowired
    BranchOfficeService(BranchOfficeRepository branchOfficeRepository) {
        this.branchOfficeRepository = branchOfficeRepository;
    }

    public BranchOfficeModel findById(int id) {
        log.info("Buscar sucursal con id {}", id);
        validateBranchId(id);

        Optional<BranchOfficeEntity> branchOfficeEntity = branchOfficeRepository.findById(id);

        if (branchOfficeEntity.isEmpty()) {
            log.error(BranchOfficeErrorCode.BRANCH_OFFICE_NOT_FOUND.getMessage());
            throw new BranchOfficeException(BranchOfficeErrorCode.BRANCH_OFFICE_NOT_FOUND);
        }

        return toModel(branchOfficeEntity.get());
    }

    public BranchOfficeModel addBranchOffice(BranchOfficeModel branchOffice) {
        validateBranchOffice(branchOffice);
        log.info("Crear sucursal {}", branchOffice.toString());

        BranchOfficeEntity branchOfficeEntity = branchOfficeRepository.save(toEntity(branchOffice));

        if (branchOfficeEntity == null) {
            log.error(BranchOfficeErrorCode.BRANCH_OFFICE_CREATE_ERROR.getMessage());
            throw new BranchOfficeException(BranchOfficeErrorCode.BRANCH_OFFICE_CREATE_ERROR);
        }

        return toModel(branchOfficeEntity);
    }

    public BranchOfficeModel getNearestBranch(String latitude, String longitude) {
        log.info("Buscar sucursal mas cercana a {}, {}", latitude, longitude);
        validateGeolocation(latitude, longitude);

        List<BranchOfficeEntity> branchOfficeEntityList = branchOfficeRepository.findAll();

        if (branchOfficeEntityList == null || branchOfficeEntityList.isEmpty()) {
            log.error(BranchOfficeErrorCode.BRANCH_OFFICE_NOT_FOUND.getMessage());
            throw new BranchOfficeException(BranchOfficeErrorCode.BRANCH_OFFICE_NOT_FOUND);
        }

        int branchId = 0;
        double distance = 0;
        for (int i = 0; i < branchOfficeEntityList.size(); i++){
            BranchOfficeEntity branchOffice = branchOfficeEntityList.get(i);
            double newDistance = distance(Double.parseDouble(latitude), branchOffice.getLatitud(),
                    Double.parseDouble(longitude), branchOffice.getLongitud(), 0, 0);
            if (i == 0 || newDistance < distance) {
                branchId = branchOffice.getId();
                distance = newDistance;
            }
        }

        int finalBranchId = branchId;
        Optional<BranchOfficeEntity> nearestBranch = branchOfficeEntityList.stream()
                .filter(branch -> branch.getId() == finalBranchId)
                .findFirst();

        if (nearestBranch.isEmpty()) {
            throw new BranchOfficeException(BranchOfficeErrorCode.BRANCH_OFFICE_NOT_FOUND);
        }

        log.info("La sucursal mas cercana es {}, esta a {} metros", nearestBranch.get().getDireccion(),
                new DecimalFormat("#.#").format(distance));
        return toModel(nearestBranch.get());
    }

    private void validateBranchId(int id) {
        if (id < 1) {
            log.error(BranchOfficeErrorCode.BRANCH_OFFICE_INVALID_ID.getMessage());
            throw new BranchOfficeException(BranchOfficeErrorCode.BRANCH_OFFICE_INVALID_ID);
        }
    }

    private void validateBranchOffice(BranchOfficeModel branchOffice) {
        if (branchOffice == null) {
            log.error(BranchOfficeErrorCode.BRANCH_OFFICE_INVALID_DATA.getMessage());
            throw new BranchOfficeException(BranchOfficeErrorCode.BRANCH_OFFICE_INVALID_DATA);
        }

        if (branchOffice.getDireccion() == null || branchOffice.getDireccion().isEmpty()) {
            log.error(BranchOfficeErrorCode.BRANCH_OFFICE_REQUIRED_ADDRESS.getMessage());
            throw new BranchOfficeException(BranchOfficeErrorCode.BRANCH_OFFICE_REQUIRED_ADDRESS);
        }

        if (branchOffice.getLatitud() == null) {
            log.error(BranchOfficeErrorCode.BRANCH_OFFICE_REQUIRED_LATITUDE.getMessage());
            throw new BranchOfficeException(BranchOfficeErrorCode.BRANCH_OFFICE_REQUIRED_LATITUDE);
        }

        if (branchOffice.getLongitud() == null) {
            log.error(BranchOfficeErrorCode.BRANCH_OFFICE_REQUIRED_LONGITUDE.getMessage());
            throw new BranchOfficeException(BranchOfficeErrorCode.BRANCH_OFFICE_REQUIRED_LONGITUDE);
        }
    }

    private void validateGeolocation(String latitude, String longitude) {
        if (latitude == null || latitude.isEmpty() || longitude == null || longitude.isEmpty()) {
            log.error(BranchOfficeErrorCode.GEOLOCATION_REQUIRED.getMessage());
            throw new BranchOfficeException(BranchOfficeErrorCode.GEOLOCATION_REQUIRED);
        }

        try {
            Double.parseDouble(latitude);
            Double.parseDouble(longitude);
        } catch (Exception e){
            log.error(BranchOfficeErrorCode.GEOLOCATION_BAD_REQUEST.getMessage());
            throw new BranchOfficeException(BranchOfficeErrorCode.GEOLOCATION_BAD_REQUEST);
        }
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    private BranchOfficeModel toModel(BranchOfficeEntity entity) {
        BranchOfficeModel model = new BranchOfficeModel();
        model.setId(entity.getId());
        model.setDireccion(entity.getDireccion());
        model.setLatitud(entity.getLatitud());
        model.setLongitud(entity.getLongitud());
        return model;
    }

    private BranchOfficeEntity toEntity(BranchOfficeModel model) {
        BranchOfficeEntity entity = new BranchOfficeEntity();
        entity.setId(model.getId());
        entity.setDireccion(model.getDireccion());
        entity.setLatitud(model.getLatitud());
        entity.setLongitud(model.getLongitud());
        return entity;
    }
}
