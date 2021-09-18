package com.poseidon.transaction.web.controller;

import java.util.Arrays;
import java.util.List;

public enum TransactionStatus {
    NEW, ACCEPTED, VERIFIED, CLOSED, REJECTED, INVOICED;

    public static List<String> populateStatus() {
        return Arrays.stream(TransactionStatus.values()).map(Enum::name).toList();
    }
}
