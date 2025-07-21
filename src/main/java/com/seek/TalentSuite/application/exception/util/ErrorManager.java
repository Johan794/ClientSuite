package com.seek.TalentSuite.application.exception.util;

import com.seek.TalentSuite.application.exception.custom.ErrorCode;
import com.seek.TalentSuite.application.exception.custom.ErrorDetail;

public final class ErrorManager {
    public static ErrorDetail createDetail(String message, ErrorCode errorCode){
        return ErrorDetail.builder().errorCode(errorCode).errorMessage(message).build();
    }

    public static ErrorDetail[] sendDetails(ErrorDetail ... details){
        return details;
    }
}
