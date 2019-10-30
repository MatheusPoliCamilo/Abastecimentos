package com.example.abastecimento.model;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class GerenciadorMigracoesRealm implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        for(long versao = oldVersion; versao < newVersion; versao++) {
            if(oldVersion == 0 && newVersion == 1){
                RealmSchema schema = realm.getSchema();
                RealmObjectSchema abastecimentoSchema = schema.get("Abastecimento");
                abastecimentoSchema.addField("caminhoFotografia", String.class);
            }
        }
    }
}
