package br.com.Spring_Bookstore.Spring_Bookstore.domain.enuns;

public enum Levels {
    USER(1),
    EMPLOYEE(2),
    SUPPORT(3);

    private int code;

    private Levels(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
