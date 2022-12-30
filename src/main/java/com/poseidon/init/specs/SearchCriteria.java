package com.poseidon.init.specs;

import java.io.Serializable;

public class SearchCriteria implements Serializable {
    public static final long serialVersionUID = 4328743;
    private String key;
    private transient Object value;
    private SearchOperation operation;

    public SearchCriteria() {
    }

    public SearchCriteria(final String key, final Object value, final SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(final SearchOperation operation) {
        this.operation = operation;
    }
}
