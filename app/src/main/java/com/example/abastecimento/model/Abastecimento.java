package com.example.abastecimento.model;

import java.util.UUID;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Abastecimento extends RealmObject {

    @PrimaryKey
    private String id;
    private int quilometragem;
    private String posto;
    private double litros;
    private String caminhoFotografia;

    public Abastecimento() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public Double getLitros() {
        return litros;
    }

    public void setLitros(Double litros) {
        this.litros = litros;
    }

    public String getCaminhoFotografia() {
        return caminhoFotografia;
    }

    public void setCaminhoFotografia(String caminhoFotografia) {
        this.caminhoFotografia = caminhoFotografia;
    }
}
