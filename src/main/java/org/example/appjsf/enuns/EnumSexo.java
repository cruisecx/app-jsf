package org.example.appjsf.enuns;

public enum EnumSexo {
    MASCULINO("M", "MASCULINO"),
    FEMININO("F", "FEMININO");

    private String value;
    private String descricao;

    EnumSexo(String value, String descricao) {
        this.value = value;
        this.value = descricao;
    }

    public String getValue() {
        return value;
    }

    public String getDescricao() {
        return descricao;
    }
}
