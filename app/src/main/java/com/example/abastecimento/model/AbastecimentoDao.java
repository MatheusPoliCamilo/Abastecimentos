package com.example.abastecimento.model;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class AbastecimentoDao {

    private ArrayList<Abastecimento> bancoDeDados;

    public ArrayList<Abastecimento> obterLista() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults lista = realm.where(Abastecimento.class).findAll().sort("quilometragem", Sort.DESCENDING);
        bancoDeDados.clear();
        bancoDeDados.addAll(realm.copyFromRealm(lista));
        return bancoDeDados;
    }

    public void adicionarNaLista(Abastecimento c) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(c);
        realm.commitTransaction();
    }

    public int atualizaNaLista(Abastecimento c) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(c);
        realm.commitTransaction();

        for (int i = 0; i < bancoDeDados.size(); i++) {
            if (bancoDeDados.get(i).getId().equals(c.getId())) {
                bancoDeDados.set(i, c);
                return i;
            }
        }

        return -1;
    }

    public int excluiDaLista(Abastecimento c) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Abastecimento.class).equalTo("id", c.getId()).findFirst().deleteFromRealm();
        realm.commitTransaction();

        for (int i = 0; i < bancoDeDados.size(); i++) {
            if (bancoDeDados.get(i).getId().equals(c.getId())) {
                bancoDeDados.remove(i);
                return i;
            }
        }

        return -1;
    }

    public Abastecimento obterObjetoPeloId(String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Abastecimento c = realm.copyFromRealm(realm.where(Abastecimento.class).equalTo("id", id).findFirst());
        realm.commitTransaction();
        return c;
    }

    private static AbastecimentoDao INSTANCIA;

    public static AbastecimentoDao obterInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new AbastecimentoDao();
        }
        return INSTANCIA;
    }

    private AbastecimentoDao() {
        bancoDeDados = new ArrayList<Abastecimento>();
    }
}
