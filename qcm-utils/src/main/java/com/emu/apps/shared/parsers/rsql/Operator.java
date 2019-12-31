package com.emu.apps.shared.parsers.rsql;

public enum Operator {
    EQUAL("=="),
    NOT_EQUAL("!="),
    CONTAINS("contains"),
    LESS_THAN("=lt="),
    LESS_THAN_OR_EQUAL("=le="),
    GREATER_THAN("=lt="),
    GREATER_THAN_OR_EQUAL("=ge="),
    IN("=in="), NOT_IN("=out=");

    private String op;

    Operator(String op) {
        this.op = op;
    }

    public String getOp() {
        return op;
    }
}
