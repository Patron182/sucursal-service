package com.fravega.sucursal.builders;

import com.fravega.sucursal.models.BranchOfficeModel;

import java.util.Random;

public class BranchOfficeModelBuilder {

    private final BranchOfficeModel instance = new BranchOfficeModel();

    public static BranchOfficeModelBuilder create() {
        BranchOfficeModelBuilder builder = new BranchOfficeModelBuilder();
        builder.instance.setDireccion("Av. Rivadavia " + new Random().nextInt(9999));
        builder.instance.setLatitud(new Random().nextDouble());
        builder.instance.setLongitud(new Random().nextDouble());
        return builder;
    }

    public BranchOfficeModelBuilder withAddress(String address) {
        instance.setDireccion(address);
        return this;
    }

    public BranchOfficeModel build() {
        return instance;
    }
}
