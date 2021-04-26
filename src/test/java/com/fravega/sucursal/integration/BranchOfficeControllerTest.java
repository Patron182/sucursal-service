package com.fravega.sucursal.integration;

import com.fravega.sucursal.SucursalServiceApplicationTests;
import com.fravega.sucursal.builders.BranchOfficeModelBuilder;
import com.fravega.sucursal.exception.BranchOfficeErrorResponse;
import com.fravega.sucursal.models.BranchOfficeModel;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BranchOfficeControllerTest extends SucursalServiceApplicationTests {
    @Test
    void findById() throws Exception {
        MvcResult resultRequest = mockMvc.perform(get("/sucursal/1"))
                .andExpect(status().isOk()).andReturn();

        BranchOfficeModel result = om.readValue(
                resultRequest.getResponse().getContentAsString(), BranchOfficeModel.class);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getDireccion()).isNotEmpty();
        assertThat(result.getLatitud()).isInstanceOf(Double.class);
        assertThat(result.getLongitud()).isInstanceOf(Double.class);
    }

    @Test
    void addBranchOffice() throws Exception {
        BranchOfficeModel branchOfficeModel = BranchOfficeModelBuilder.create().build();

        MvcResult resultRequest = mockMvc.perform(put("/sucursal/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(branchOfficeModel)))
                .andExpect(status().isOk()).andReturn();

        BranchOfficeModel result = om.readValue(
                resultRequest.getResponse().getContentAsString(), BranchOfficeModel.class);

        assertThat(result.getId()).isGreaterThan(0);
        assertThat(result.getDireccion()).isEqualTo(branchOfficeModel.getDireccion());
        assertThat(result.getLatitud()).isEqualTo(branchOfficeModel.getLatitud());
        assertThat(result.getLongitud()).isEqualTo(branchOfficeModel.getLongitud());
    }

    @Test
    void getNearestBranch() throws Exception {
        MvcResult resultRequest = mockMvc.perform(
                get("/sucursal/mas-cercana/?latitud=-34.60374276918381&longitud=-58.38157052807026"))
                .andExpect(status().isOk()).andReturn();

        BranchOfficeModel result = om.readValue(
                resultRequest.getResponse().getContentAsString(), BranchOfficeModel.class);

        assertThat(result.getId()).isGreaterThan(0);
        assertThat(result.getDireccion()).isNotEmpty();
    }

    @Test
    void badRequest() throws Exception {
        MvcResult resultRequest = mockMvc.perform(get("/error")).andExpect(status().isBadRequest()).andReturn();

        BranchOfficeErrorResponse result = om.readValue(
                resultRequest.getResponse().getContentAsString(), BranchOfficeErrorResponse.class);

        assertThat(result.getErrorCode()).isEqualTo(400);
        assertThat(result.getErrorMessage()).isEqualTo("Bad Request");
    }
}
