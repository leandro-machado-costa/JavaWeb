package br.com.unisul.servlet;

import br.com.unisul.dao.Consulta;
import br.com.unisul.entidade.Cor;
import br.com.unisul.entidade.Estado;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ColorirMapaServlet extends HttpServlet {

    private int prioridade = 0;
    private List<Cor> coresEscolhidas = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            String cor1 = "#" + request.getParameter("cor1");
            String cor2 = "#" + request.getParameter("cor2");
            String cor3 = "#" + request.getParameter("cor3");
            String cor4 = "#" + request.getParameter("cor4");

            if (cor1.equals("") || cor2.equals("") || cor3.equals("") || cor4.equals("")) {
                response.sendRedirect(Consulta.getPropriedade("caminho.raiz") + "index.jsp?msg=validacao");
            } else {

                coresEscolhidas = new ArrayList<Cor>();
                coresEscolhidas.add(new Cor(cor1));
                coresEscolhidas.add(new Cor(cor2));
                coresEscolhidas.add(new Cor(cor3));
                coresEscolhidas.add(new Cor(cor4));

                Estado[] estados = carregarEstadoVizinhoBR();

                for (int i = 0; i < estados.length; i++) {
                    colorirMapa(estados[i]);
                }

                boolean resultado = gravar(estados);

                if (resultado) {
                    response.sendRedirect(Consulta.getPropriedade("caminho.raiz") + "index.jsp?msg=sucesso");
                } else {
                    response.sendRedirect(Consulta.getPropriedade("caminho.raiz") + "index.jsp?msg=erro");
                }

            }

        } finally {
            out.close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public void colorirMapa(Estado estado) {

        List<Cor> cores = carregarCores(prioridade);

        String cor = "";

        for (int i = 0; i < cores.size(); i++) {

            boolean useColor = true;

            for (int j = 0; j < estado.getVizinhos().length; j++) {

                if (estado.getVizinhos()[j].getCor().equals(cores.get(i).getNome())) {
                    useColor = false;
                    break;
                }

            }
            
            if (useColor) {
                cor = cores.get(i).getNome();
            }

        }

        estado.setCor(cor);

        if (cor.equals(coresEscolhidas.get(0).getNome())) {
            prioridade = 1;
        } else if (cor.equals(coresEscolhidas.get(1).getNome())) {
            prioridade = 2;
        } else if (cor.equals(coresEscolhidas.get(2).getNome())) {
            prioridade = 3;
        } else if (cor.equals(coresEscolhidas.get(3).getNome())) {
            prioridade = 0;
        }

    }

    public List<Cor> carregarCores(int prioridade) {

        List<Cor> cores = new ArrayList<Cor>();

        if (prioridade == 0) {
            cores.add(new Cor(coresEscolhidas.get(3).getNome()));
            cores.add(new Cor(coresEscolhidas.get(2).getNome()));
            cores.add(new Cor(coresEscolhidas.get(1).getNome()));
            cores.add(new Cor(coresEscolhidas.get(0).getNome()));
        }

        if (prioridade == 1) {
            cores.add(new Cor(coresEscolhidas.get(0).getNome()));
            cores.add(new Cor(coresEscolhidas.get(3).getNome()));
            cores.add(new Cor(coresEscolhidas.get(2).getNome()));
            cores.add(new Cor(coresEscolhidas.get(1).getNome()));
        }

        if (prioridade == 2) {
            cores.add(new Cor(coresEscolhidas.get(1).getNome()));
            cores.add(new Cor(coresEscolhidas.get(0).getNome()));
            cores.add(new Cor(coresEscolhidas.get(3).getNome()));
            cores.add(new Cor(coresEscolhidas.get(2).getNome()));
        }

        if (prioridade == 3) {
            cores.add(new Cor(coresEscolhidas.get(2).getNome()));
            cores.add(new Cor(coresEscolhidas.get(1).getNome()));
            cores.add(new Cor(coresEscolhidas.get(0).getNome()));
            cores.add(new Cor(coresEscolhidas.get(3).getNome()));
        }

        return cores;

    }

    public Estado[] carregarEstadoVizinhoBR() {

        Estado ac = new Estado("", "AC", new Estado[0]);
        Estado al = new Estado("", "AL", new Estado[0]);
        Estado am = new Estado("", "AM", new Estado[0]);
        Estado ap = new Estado("", "AP", new Estado[0]);
        Estado ba = new Estado("", "BA", new Estado[0]);
        Estado ce = new Estado("", "CE", new Estado[0]);
        Estado df = new Estado("", "DF", new Estado[0]);
        Estado es = new Estado("", "ES", new Estado[0]);
        Estado go = new Estado("", "GO", new Estado[0]);
        Estado ma = new Estado("", "MA", new Estado[0]);
        Estado mg = new Estado("", "MG", new Estado[0]);
        Estado ms = new Estado("", "MS", new Estado[0]);
        Estado mt = new Estado("", "MT", new Estado[0]);
        Estado pa = new Estado("", "PA", new Estado[0]);
        Estado pb = new Estado("", "PB", new Estado[0]);
        Estado pe = new Estado("", "PE", new Estado[0]);
        Estado pi = new Estado("", "PI", new Estado[0]);
        Estado pr = new Estado("", "PR", new Estado[0]);
        Estado rj = new Estado("", "RJ", new Estado[0]);
        Estado rn = new Estado("", "RN", new Estado[0]);
        Estado ro = new Estado("", "RO", new Estado[0]);
        Estado rr = new Estado("", "RR", new Estado[0]);
        Estado rs = new Estado("", "RS", new Estado[0]);
        Estado sc = new Estado("", "SC", new Estado[0]);
        Estado se = new Estado("", "SE", new Estado[0]);
        Estado sp = new Estado("", "SP", new Estado[0]);
        Estado to = new Estado("", "TO", new Estado[0]);

        ac.setVizinhos(new Estado[]{am, ro});
        al.setVizinhos(new Estado[]{ba, pe, se});
        am.setVizinhos(new Estado[]{ac, mt, pa, ro, rr});
        ap.setVizinhos(new Estado[]{pa});
        ba.setVizinhos(new Estado[]{al, es, go, mg, pe, pi, se, to});
        ce.setVizinhos(new Estado[]{pb, pe, pi, rn});
        df.setVizinhos(new Estado[]{go});
        es.setVizinhos(new Estado[]{ba, mg, rj});
        go.setVizinhos(new Estado[]{ba, df, mg, ms, mt, to});
        ma.setVizinhos(new Estado[]{ba, pa, pi, to});
        mg.setVizinhos(new Estado[]{ba, es, go, ms, rj, sp});
        ms.setVizinhos(new Estado[]{go, mg, mt, pr, sp,});
        mt.setVizinhos(new Estado[]{am, go, ms, pa, ro, to});
        pa.setVizinhos(new Estado[]{am, ap, ma, mt, rr, to});
        pb.setVizinhos(new Estado[]{ce, pe, rn});
        pe.setVizinhos(new Estado[]{al, ba, ce, pb, pi});
        pi.setVizinhos(new Estado[]{ba, ce, ma, pe});
        pr.setVizinhos(new Estado[]{ms, sc, sp});
        rj.setVizinhos(new Estado[]{es, mg, sp});
        rn.setVizinhos(new Estado[]{ce, pb});
        ro.setVizinhos(new Estado[]{ac, am, mt});
        rr.setVizinhos(new Estado[]{am, pa});
        rs.setVizinhos(new Estado[]{sc});
        sc.setVizinhos(new Estado[]{pr, rs});
        se.setVizinhos(new Estado[]{al, ba});
        sp.setVizinhos(new Estado[]{mg, ms, pr, rj});
        to.setVizinhos(new Estado[]{ba, go, ma, mt, pa});

        Estado[] estados = {ac, al, am, ap, ba, ce, df, es, go, ma, mg, ms, mt, pa, pb, pe, pi, pr, rj, rn, ro, rr, rs, sc, se, sp, to};

        return ordenarPorVizinhos(estados);

    }

    public Estado[] ordenarPorVizinhos(Estado[] estados) {

        for (int i = 0; i < (estados.length - 1); i++) {

            boolean bubbleSort = true;

            for (int j = 0; j < (estados.length - 1 - i); j++) {

                if (estados[j].getVizinhos().length < estados[j + 1].getVizinhos().length) {

                    Estado auxiliar = estados[j];
                    estados[j] = estados[j + 1];
                    estados[j + 1] = auxiliar;

                    bubbleSort = false;

                }

            }

            if (bubbleSort) {
                break;
            }

        }

        return estados;

    }

    public boolean gravar(Estado[] estados) {

        String caminhoArquivo = Consulta.getPropriedade("caminho.svg") + "mapa.svg";
        String texto = "";

        try {

            BufferedReader br = new BufferedReader(new FileReader(Consulta.getPropriedade("caminho.txt") + "mapa.txt"));

            while (br.ready()) {
                texto = texto + br.readLine();
            }

            br.close();

            File file = new File(caminhoArquivo);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < estados.length; i++) {
                texto = texto.replace("#" + estados[i].getSigla().toLowerCase(), estados[i].getCor());
            }
            
            bw.write(texto);
            bw.flush();
            bw.close();

            return true;

        } catch (Exception e) {

            System.out.println("Erro ao abrir o arquivo: " + caminhoArquivo);
            System.out.println("ERRO: " + e.getMessage());

            return false;

        }

    }
}
