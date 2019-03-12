import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import persistencia.Conta;
import persistencia.Operacao;
import persistencia.Persistencia;

public class CriaOperacoes{
    // Argumentos: mes inicial, mes final, ano
    public static void main( String[] args )
    {
        if (args.length != 3){
            System.out.println("<mes inicial>,<mes final>,<ano>");
            System.exit(0);
        }
        int mesInic = Integer.parseInt(args[0]);
        int mesFinal = Integer.parseInt(args[1]);
        int ano = Integer.parseInt(args[2]);

        Persistencia p = new Persistencia();
        Map<Integer, Conta> contas = p.loadContas();
        System.out.println("\nContas:");
        contas.keySet().stream()
            .forEach(k->System.out.println(contas.get(k)));

        System.out.println("\ngerando operacoes para o periodo: "+mesInic+"/"+ano+", "+mesFinal+"/"+ano);
        int qtdadeContas = contas.keySet().size();
        List<Operacao> lstOp = p.loadOperacoes();
        List<Integer> nroContas = new ArrayList<>(contas.keySet());
        Random r = new Random();
        for(int mes = mesInic; mes<=mesFinal; mes++){
            for(int dia=1;dia<31;dia++){
                for(int aux=0; aux<3; aux++){
                    int hora = r.nextInt(24);
                    int minuto = r.nextInt(60);
                    int segundo = 0;
                    int numeroConta = nroContas.get(r.nextInt(qtdadeContas));
                    double valor = r.nextInt(10000);
                    int tipoOperacao = r.nextInt(2);
                    Operacao op = new Operacao(dia,mes,ano,hora,minuto,segundo,numeroConta,valor,tipoOperacao);
                    lstOp.add(op);
                }
            }
        }
        p.saveOperacoes(lstOp);

        System.out.println("Conjunto de operacoes:");
        lstOp = p.loadOperacoes();
        lstOp.stream()
            .forEach(o->System.out.println(o));
    }
}
