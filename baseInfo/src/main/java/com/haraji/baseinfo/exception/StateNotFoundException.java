package com.haraji.baseinfo.exception;

import com.haraji.common.exception.BaseException;

public class StateNotFoundException extends BaseException {

    public StateNotFoundException() {
        super("state.not.found", null, null);
    }

    public StateNotFoundException(int id) {
        super("state.not.found", null, String.valueOf(id));
    }
}
