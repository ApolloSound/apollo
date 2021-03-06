package com.apollo.web.rest.errors;

public class NotYetImplementedException extends NotImplementedAlertException {

    private static final long serialVersionUID = 1L;

    public NotYetImplementedException() {
        super(ErrorConstants.NOT_IMPLEMENTED, "Not implemented!", "Apollo", ErrorConstants.NOT_YET_IMPLEMENTED);
    }
}
