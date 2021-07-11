package com.poseidon.reports.domain;

import java.util.Arrays;
import java.util.List;

public enum ExportList {
    EXCEL,
    WORD,
    PDF,
    HTML;

    public static List<String> asList() {
        return Arrays.stream(ExportList.values()).map(Enum::name).toList();
    }

    public static ExportList fromName(final String name) {
        ExportList exportList;
        try {
            exportList = ExportList.valueOf(name);
        } catch (IllegalArgumentException | NullPointerException ex) {
            exportList = ExportList.HTML;
        }
        return exportList;
    }

}