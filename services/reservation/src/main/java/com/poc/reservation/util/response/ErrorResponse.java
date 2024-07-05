package com.poc.reservation.util.response;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {}