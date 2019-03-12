package persistencia;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Persistencia {
    private final String NomeBDContas = "\\dados\\BDContasBNG.txt";
    private final String NomeBDOperacoes = "\\dados\\BDOperBNG.txt";

    public Map<Integer,Conta> loadContas(){
    	Map<Integer,Conta> contas = new HashMap<>();

        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+NomeBDContas;
        //System.out.println(nameComplete);
        Path path2 = Paths.get(nameComplete);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.defaultCharset()))){
           sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
           int numero;
           String nomeCorr;
           double saldo;
           while (sc.hasNext()){
               numero = Integer.parseInt(sc.next());
               nomeCorr = sc.next();
               saldo = Double.parseDouble(sc.next());
               Conta conta = new Conta(numero,nomeCorr,saldo);
               contas.put(numero, conta);
           }
        }catch (IOException x){
            System.err.format("Erro de E/S: %s%n", x);
            return null;
        }
        return contas;
    }

    public void saveContas(Collection<Conta> contas) {
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+NomeBDContas;
        //System.out.println(nameComplete);
        Path path2 = Paths.get(nameComplete);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path2, Charset.defaultCharset())))
        {
            for(Conta c: contas)
                writer.format(Locale.ENGLISH,
                		      "%d;%s;%f;",
                		      c.getNumero(),c.getCorrentista(),
                              c.getSaldo());
        }
        catch (IOException x)
        {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public void saveOperacoes(Collection<Operacao> operacoes) {
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+NomeBDOperacoes;
        System.out.println(nameComplete);
        Path path2 = Paths.get(nameComplete);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path2, Charset.defaultCharset())))
        {
            for(Operacao op:operacoes)
                writer.format(Locale.ENGLISH,
                		      "%d;%d;%d;%d;%d;%d;%d;%f;%d;",
                              op.getDia(),op.getMes(),op.getAno(),
                              op.getHora(),op.getMinuto(),op.getSegundo(),
                              op.getNumeroConta(),
                              op.getValorOperacao(),op.getTipoOperacao()
                             );
        }
        catch (IOException x)
        {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public List<Operacao> loadOperacoes(){
        List<Operacao> operacoes = new LinkedList<Operacao>();

    	String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+NomeBDOperacoes;
        System.out.println(nameComplete);
        Path path2 = Paths.get(nameComplete);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.defaultCharset()))){
           sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
           int dia,mes,ano;
           int hora,minuto,segundo;
           int numero,tipo;
           double valor;

           while (sc.hasNext()){
               dia = Integer.parseInt(sc.next());
               mes = Integer.parseInt(sc.next());
               ano = Integer.parseInt(sc.next());
               hora = Integer.parseInt(sc.next());
               minuto = Integer.parseInt(sc.next());
               segundo = Integer.parseInt(sc.next());
               numero = Integer.parseInt(sc.next());
               valor = Double.parseDouble(sc.next());
               tipo = Integer.parseInt(sc.next());

               Operacao op = new Operacao(
            		   dia, mes, ano,
            		   hora, minuto, segundo,
	                   numero, valor, tipo);

               operacoes.add(op);
           }
        }catch (IOException x){
            System.err.format("Erro de E/S: %s%n", x);
            return null;
        }
        return operacoes;
    }
}
