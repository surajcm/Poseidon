package com.poseidon.reports.domain;

import java.util.Arrays;
import java.util.List;

public enum InvoiceStatus {
    NEW,
    ACCEPTED,
    VERIFIED,
    CLOSED,
    REJECTED;

    public static List<String> asList() {
        return Arrays.stream(InvoiceStatus.values()).map(Enum::name).toList();
    }
}