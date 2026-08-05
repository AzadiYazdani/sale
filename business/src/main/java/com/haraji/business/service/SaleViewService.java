package com.haraji.business.service;

import com.haraji.business.model.SaleView;
import jakarta.validation.constraints.Min;

import java.util.List;

public interface SaleViewService {

    List<SaleView> getAllSalesByCityAndBusinessType(int id_city, int id_business_type);

    SaleView getById(@Min(1) Long id);


}
