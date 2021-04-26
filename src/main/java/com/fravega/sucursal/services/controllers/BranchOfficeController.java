package com.fravega.sucursal.services.controllers;

import com.fravega.sucursal.models.BranchOfficeModel;
import com.fravega.sucursal.services.BranchOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sucursal")
public class BranchOfficeController {
    private BranchOfficeService branchOfficeService;

    @Autowired
    public BranchOfficeController(BranchOfficeService branchOfficeService) {
        this.branchOfficeService = branchOfficeService;
    }

    @GetMapping("/{id}")
    public BranchOfficeModel findById(@PathVariable("id") int id) {
        return branchOfficeService.findById(id);
    }

    @PutMapping("")
    public BranchOfficeModel addBranchOffice(@RequestBody BranchOfficeModel branchOffice) {
        return branchOfficeService.addBranchOffice(branchOffice);
    }

    @GetMapping("/mas-cercana/")
    public BranchOfficeModel getNearestBranch(HttpServletRequest request) {
        return branchOfficeService.getNearestBranch(request.getParameter("latitud"), request.getParameter("longitud"));
    }
}
