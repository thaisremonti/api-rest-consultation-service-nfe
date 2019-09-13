package com.example.teste;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.teste.DAO.EstadoDAO;
import com.example.teste.Entidade.Estado;
import com.example.teste.Enum.EStatus;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HtmlParser {

    static String url = "http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx\\";

    public static void main(String[] args) throws IOException, SQLException {
        try {
            URL url = new URL("http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("src/main/resources/templates/file.html")));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                bw.write(inputLine);
                bw.newLine();
            }

            in.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Document doc = Jsoup.connect("http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx").get();
        String status;
        String estados;
        Estado estado = new Estado();

        for (Element b : doc.select(".linhaImparCentralizada")) {
            String aux = b.html();
            aux = aux.replace("<td>", "");
            String[] aux2 = aux.split("<\\/td>");
            estados = aux2[0];

            if (aux2[1].contains("bola_vermelho_P")) {
                status = EStatus.VERMELHO.getDescricao();
            } else if (aux2[1].contains("bola_verde_P")) {
                status = EStatus.VERDE.getDescricao();
            } else {
                status = EStatus.AMARELO.getDescricao();
            }


            estado.setStatus(status);
            estado.setData(LocalDate.now());
            estado.setEstaUf(estados);
            EstadoDAO.getInstance().persist(estado);
        }
    }
}