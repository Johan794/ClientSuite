package com.seek.TalentSuite.application.constants;

import com.seek.TalentSuite.application.exception.custom.ApplicationError;
import com.seek.TalentSuite.application.exception.custom.ApplicationException;
import com.seek.TalentSuite.application.exception.custom.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.function.Supplier;

import static com.seek.TalentSuite.application.exception.util.ErrorManager.createDetail;
import static com.seek.TalentSuite.application.exception.util.ErrorManager.sendDetails;

public final class AppConstants {
    public static  final  int AGE_TO_RETIRE = 65;
    public static  final  String RETIRE_MESSAGE =  "%s has %s years left to retire";

    public static Supplier<ApplicationException> createApplicationException(String message, String detail, ErrorCode errorCode, HttpStatus status) {
        return () -> new ApplicationException(message, ApplicationError.builder()
                .details(List.of(sendDetails(createDetail(detail, errorCode))))
                .status(status)
                .build());
    }

}
