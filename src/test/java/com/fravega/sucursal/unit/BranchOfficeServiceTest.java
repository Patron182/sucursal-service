package com.fravega.sucursal.unit;


import com.fravega.sucursal.SucursalServiceApplicationTests;
import com.fravega.sucursal.builders.BranchOfficeEntityBuilder;
import com.fravega.sucursal.builders.BranchOfficeModelBuilder;
import com.fravega.sucursal.entities.BranchOfficeEntity;
import com.fravega.sucursal.exception.BranchOfficeErrorCode;
import com.fravega.sucursal.exception.BranchOfficeException;
import com.fravega.sucursal.models.BranchOfficeModel;
import com.fravega.sucursal.repository.BranchOfficeRepository;
import com.fravega.sucursal.services.BranchOfficeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

class BranchOfficeServiceTest extends SucursalServiceApplicationTests {

    @Autowired
    BranchOfficeService branchOfficeService;

    @MockBean
    BranchOfficeRepository branchOfficeRepository;

    @Test
    void findById() {
        BranchOfficeEntity branchOfficeEntity = BranchOfficeEntityBuilder.create().withId(1).build();
        Mockito.when(branchOfficeRepository.findById(1)).thenReturn(Optional.of(branchOfficeEntity));

        BranchOfficeModel branchOffice = branchOfficeService.findById(1);

        assertThat(branchOffice.getId()).isEqualTo(branchOfficeEntity.getId());
        assertThat(branchOffice.getDireccion()).isEqualTo(branchOfficeEntity.getDireccion());
        assertThat(branchOffice.getLatitud()).isEqualTo(branchOfficeEntity.getLatitud());
        assertThat(branchOffice.getLongitud()).isEqualTo(branchOfficeEntity.getLongitud());
    }

    @Test
    void findByIdError() {
        Mockito.when(branchOfficeRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> branchOfficeService.findById(999))
                .isInstanceOf(BranchOfficeException.class)
                .hasMessage(BranchOfficeErrorCode.BRANCH_OFFICE_NOT_FOUND.getMessage());
    }

    @Test
    void addBranchOffice() {
        BranchOfficeModel branchOfficeModel = BranchOfficeModelBuilder.create().build();
        BranchOfficeEntity branchOfficeEntity = BranchOfficeEntityBuilder.createFromModel(branchOfficeModel).build();

        Mockito.when(branchOfficeRepository.save(any())).thenReturn(branchOfficeEntity);

        BranchOfficeModel branchOffice = branchOfficeService.addBranchOffice(branchOfficeModel);

        assertThat(branchOffice.getId()).isEqualTo(branchOfficeEntity.getId());
        assertThat(branchOffice.getDireccion()).isEqualTo(branchOfficeEntity.getDireccion());
        assertThat(branchOffice.getLatitud()).isEqualTo(branchOfficeEntity.getLatitud());
        assertThat(branchOffice.getLongitud()).isEqualTo(branchOfficeEntity.getLongitud());
    }

    @Test
    void addBranchOfficeError() {
        BranchOfficeModel branchOfficeModel = BranchOfficeModelBuilder.create().withAddress("").build();

        assertThatThrownBy(() -> branchOfficeService.addBranchOffice(branchOfficeModel))
                .isInstanceOf(BranchOfficeException.class)
                .hasMessage(BranchOfficeErrorCode.BRANCH_OFFICE_REQUIRED_ADDRESS.getMessage());
    }

    @Test
    void getNearestBranch() {
        BranchOfficeModel branchOfficeModel = BranchOfficeModelBuilder.create().build();
        Mockito.when(branchOfficeRepository.findAll()).thenReturn(Arrays.asList(
                BranchOfficeEntityBuilder.create().build(),BranchOfficeEntityBuilder.create().build()));

        BranchOfficeModel branchOffice = branchOfficeService.getNearestBranch(branchOfficeModel.getLatitud().toString(),
                branchOfficeModel.getLongitud().toString());

        assertThat(branchOffice).isNotNull();
        assertThat(branchOffice.getDireccion()).isNotEmpty();
    }

    @Test
    void getNearestBranchNoBranches() {
        BranchOfficeModel branchOfficeModel = BranchOfficeModelBuilder.create().build();
        Mockito.when(branchOfficeRepository.findAll()).thenReturn(new ArrayList<>());

        assertThatThrownBy(() -> branchOfficeService.getNearestBranch(branchOfficeModel.getLatitud().toString(),
                branchOfficeModel.getLongitud().toString()))
                .isInstanceOf(BranchOfficeException.class)
                .hasMessage(BranchOfficeErrorCode.BRANCH_OFFICE_NOT_FOUND.getMessage());
    }

    @Test
    void getNearestBranchErrorRequired() {
        assertThatThrownBy(() -> branchOfficeService.getNearestBranch(null, null))
                .isInstanceOf(BranchOfficeException.class)
                .hasMessage(BranchOfficeErrorCode.GEOLOCATION_REQUIRED.getMessage());
    }

    @Test
    void getNearestBranchErrorBadRequest() {
        assertThatThrownBy(() -> branchOfficeService.getNearestBranch("123", "not a double"))
                .isInstanceOf(BranchOfficeException.class)
                .hasMessage(BranchOfficeErrorCode.GEOLOCATION_BAD_REQUEST.getMessage());
    }
}
