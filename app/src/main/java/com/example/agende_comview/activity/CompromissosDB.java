package com.example.agende_comview.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CompromissosDB {
    private final SQLiteDatabase mDatabase;

    public CompromissosDB(Context contexto) {
        mDatabase = new CompromissosDBHelper(contexto).getWritableDatabase();
    }

    public String listaCompromissos(String clausulaWhere, String[] argsWhere) {
        Cursor cursor = queryCompromissos(clausulaWhere, argsWhere);
        StringBuilder stringBuilder = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String data = cursor.getString(cursor.getColumnIndexOrThrow(CompromissosDBSchema.CompromissosTbl.Cols.DATA));
                String hora = cursor.getString(cursor.getColumnIndexOrThrow(CompromissosDBSchema.CompromissosTbl.Cols.HORA));
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow(CompromissosDBSchema.CompromissosTbl.Cols.DESCRICAO));

                if (hora != null && !descricao.isEmpty()) {
                    stringBuilder.append(data).append(" - ").append(hora).append(" - ").append(descricao).append("\n");
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        return stringBuilder.toString();
    }

    private static ContentValues getValoresConteudo(Compromisso c) {
        ContentValues valores = new ContentValues();
        valores.put(CompromissosDBSchema.CompromissosTbl.Cols.DATA, c.getData());
        valores.put(CompromissosDBSchema.CompromissosTbl.Cols.HORA, c.getHora());
        valores.put(CompromissosDBSchema.CompromissosTbl.Cols.DESCRICAO, c.getDescricao());
        return valores;
    }

    public void addCompromisso(Compromisso r) {
        ContentValues valores = getValoresConteudo(r);
        mDatabase.insert(CompromissosDBSchema.CompromissosTbl.NOME, null, valores);
    }

    public Cursor queryCompromissos(String clausulaWhere, String[] argsWhere) {
        return mDatabase.query(CompromissosDBSchema.CompromissosTbl.NOME,
                null,
                clausulaWhere,
                argsWhere,
                null,
                null,
                null
        );
    }
}
