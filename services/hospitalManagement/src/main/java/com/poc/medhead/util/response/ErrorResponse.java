package com.poc.medhead.util.response;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {

}
