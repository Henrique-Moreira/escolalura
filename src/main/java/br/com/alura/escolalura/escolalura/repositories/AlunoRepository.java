package br.com.alura.escolalura.escolalura.repositories;

import br.com.alura.escolalura.escolalura.codecs.AlunoCodec;
import br.com.alura.escolalura.escolalura.models.Aluno;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlunoRepository {

    private MongoClient cliente;
    private MongoDatabase bancoDeDados;

    private void criarConexao() {
        Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
        AlunoCodec alunoCodec = new AlunoCodec(codec);

        CodecRegistry registro = CodecRegistries.fromRegistries(
                MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(alunoCodec));

        MongoClientOptions options = MongoClientOptions.builder().codecRegistry(registro).build();

        this.cliente = new MongoClient("localhost:27017", options);
        this.bancoDeDados = cliente.getDatabase("test");
    }

    public void salvar(Aluno aluno) {
        criarConexao();
        MongoCollection<Aluno> alunos = this.bancoDeDados.getCollection("alunos", Aluno.class);
        alunos.insertOne(aluno);
        this.cliente.close();
    }

    public List<Aluno> obterTodosAlunos() {
        criarConexao();
        MongoCollection<Aluno> alunos = this.bancoDeDados.getCollection("alunos", Aluno.class);

        MongoCursor<Aluno> resultado = alunos.find().iterator();

        List<Aluno> alunosEncontrados = new ArrayList<>();

        while(resultado.hasNext()) {
            Aluno aluno = resultado.next();
            alunosEncontrados.add(aluno);
        }
        this.cliente.close();

        return alunosEncontrados;
    }

}
