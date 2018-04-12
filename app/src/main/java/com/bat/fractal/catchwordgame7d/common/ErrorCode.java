package com.bat.fractal.catchwordgame7d.common;

import android.util.SparseIntArray;

import com.bat.fractal.catchwordgame7d.R;

/**
 * Created by TungPhan on 12/11/17.
 */
public enum ErrorCode {

    SUCCESS(0),
    NETWORK(10),
    PERMISSION(11),
    INTERNET_CONNECTION(12),
    REQUEST_FAILED(2001),
    REPONSE_FAILED(2002);

    private static SparseIntArray codeToDescriptionMap = createErrorMessageMap();
    private final int errorCode;

    private static SparseIntArray createErrorMessageMap()
    {
        SparseIntArray messageSIA = new SparseIntArray();
        messageSIA.put(ErrorCode.SUCCESS.errorCode, R.string.done);
        messageSIA.put(ErrorCode.NETWORK.errorCode, R.string.error_lost_internet);
        messageSIA.put(ErrorCode.PERMISSION.errorCode, R.string.turn_on_permissions);
        messageSIA.put(ErrorCode.INTERNET_CONNECTION.errorCode, R.string.error_lost_internet);
        messageSIA.put(ErrorCode.REPONSE_FAILED.errorCode, R.string.response_fail);
        messageSIA.put(ErrorCode.REQUEST_FAILED.errorCode, R.string.request_failed);
        return messageSIA;
    }

    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getMessage() {
        int message = codeToDescriptionMap.get(errorCode);
        if (message == 0) {
            message = R.string.temp_string;
        }
        return message;
    }

    public int getValue(){
        return errorCode;
    }

}
