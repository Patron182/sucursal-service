package com.fravega.sucursal.builders;

import com.fravega.sucursal.entities.BranchOfficeEntity;
import com.fravega.sucursal.models.BranchOfficeModel;

import java.util.Random;

public class BranchOfficeEntityBuilder {

    private final BranchOfficeEntity instance = new BranchOfficeEntity();

    public static BranchOfficeEntityBuilder create() {
        BranchOfficeEntityBuilder builder = new BranchOfficeEntityBuilder();
        builder.instance.setDireccion("Av. Rivadavia " + new Random().nextInt(9999));
        builder.instance.setLatitud(new Random().nextDouble());
        builder.instance.setLongitud(new Random().nextDouble());
        return builder;
    }

    public static BranchOfficeEntityBuilder createFromModel(BranchOfficeModel model) {
        BranchOfficeEntityBuilder builder = new BranchOfficeEntityBuilder();
        builder.instance.setId(model.getId());
        builder.instance.setDireccion(model.getDireccion());
        builder.instance.setLatitud(model.getLatitud());
        builder.instance.setLongitud(model.getLongitud());
        return builder;
    }

    public BranchOfficeEntityBuilder withId(int id) {
        instance.setId(id);
        return this;
    }

    public BranchOfficeEntity build() {
        return instance;
    }
}
