package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.model.Alunos;

public class AlunosDAO extends SQLiteOpenHelper {
    public AlunosDAO(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE ALUNOS (ID INTEGER PRIMARY KEY, NOME TEXT NOT NULL, ENDERECO TEXT, TELEFONE TEXT, SITE TEXT, NOTA REAL, CAMINHO_FOTO TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE ALUNOS ADD COLUMN CAMINHO_FOTO TEXT";
                db.execSQL(sql);

        }
    }

    @NonNull
    private ContentValues pegaDadosAluno(Alunos aluno) {
        ContentValues dados = new ContentValues();
        dados.put("NOME", aluno.getNome());
        dados.put("ENDERECO", aluno.getEndereco());
        dados.put("TELEFONE", aluno.getTelefone());
        dados.put("SITE", aluno.getSite());
        dados.put("NOTA", aluno.getNota());
        dados.put("CAMINHO_FOTO", aluno.getCaminhoFoto());
        return dados;
    }

    public void insere(Alunos aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosAluno(aluno);

        db.insert("ALUNOS", null, dados);
    }

    public List<Alunos> buscaAlunos() {
        String sql = "SELECT * FROM ALUNOS;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Alunos> listaAlunos = new ArrayList<Alunos>();
        while (c.moveToNext()) {
            Alunos aluno = new Alunos();
            aluno.setId(c.getLong(c.getColumnIndex("ID")));
            aluno.setNome(c.getString(c.getColumnIndex("NOME")));
            aluno.setEndereco(c.getString(c.getColumnIndex("ENDERECO")));
            aluno.setTelefone(c.getString(c.getColumnIndex("TELEFONE")));
            aluno.setSite(c.getString(c.getColumnIndex("SITE")));
            aluno.setNota(c.getDouble(c.getColumnIndex("NOTA")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("CAMINHO_FOTO")));

            listaAlunos.add(aluno);
        }
        c.close();
        return listaAlunos;
    }

    public void deleta(Alunos aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String [] params = {aluno.getId().toString()};
        db.delete("ALUNOS", "ID = ?", params);
    }

    public void atualiza(Alunos aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosAluno(aluno);

        String [] params = {aluno.getId().toString()};
        db.update("ALUNOS", dados, "ID = ?", params);
    }
}
