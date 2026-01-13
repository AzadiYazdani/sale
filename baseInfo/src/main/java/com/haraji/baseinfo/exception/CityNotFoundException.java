package com.haraji.baseinfo.exception;

import com.haraji.common.exception.BaseException;

public class CityNotFoundException extends BaseException {

    public CityNotFoundException() {
        super("city.not.found", null, null);
    }

    public CityNotFoundException(int id) {
        super("state.cities.not.found", null, String.valueOf(id));
    }


}
