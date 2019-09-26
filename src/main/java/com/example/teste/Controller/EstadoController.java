package com.example.teste.Controller;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.teste.Entidade.Estado;
import com.example.teste.Enum.EStatus;
import com.example.teste.Repository.Repository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class EstadoController {

    @Autowired
    Repository repository;

    @GetMapping("listaTodos")
    public List<Estado> listaTodos() {
        return repository.findAll();
    }

    @GetMapping("porEstado/{id}")
    public Optional<Estado> listaEstados(@PathVariable(value = "id") Long id) {
        return repository.findById(id);
    }


    @PostMapping("teste")
    public void teste() {
        
        try {           
            Document doc = Jsoup.connect("http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx").get();
            String status;
            String estados;
            
            for (Element b : doc.select(".linhaImparCentralizada, .linhaParCentralizada")) {
                Estado estado = new Estado();
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
                
                Element caption = getCaptionVersion(doc);
                estado.setStatus(status);
                estado.setData(getdataUltimaAtualizacao(caption));
                estado.setEstaUf(estados);
                estado.setVersao(getVersao(caption).getVersao());
                repository.save(estado);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("status")
    public Estado salva(@RequestBody Estado estado){
        return repository.save(estado);
    }

    private Element getCaptionVersion(Element table) {
        Elements caption = table.getElementsByTag("caption");
        return caption.stream()
            .filter(tag -> tag.getElementsByTag("span").size() > 0)
            .map(tag -> tag.getElementsByTag("span").get(0)).findFirst().get();
    }

    private String getdataUltimaAtualizacao(Element caption) {
        String value = caption.text();

        if (value != null && !value.isEmpty()) {
            String[] values = value.split("-");
            String stringData = Arrays.asList(values).stream()
                .filter(text -> text.contains("Última Verificação"))
                .findFirst()
                .map(text -> {
                    String formatado = text.replaceAll("Última Verificação: ", "");
                    return formatado.trim();
                }).get();
                return stringData;
        }
        return null;
    }
    
    private Estado getVersao(Element caption) {
        Estado estado = new Estado();
        String value = caption.text();

        if (value != null && !value.isEmpty()) {
            String[] values = value.split("-");
            String versao = Arrays.asList(values).stream()
                .filter(text -> {
                    return text.contains("WebServices Vers");
                })
                .map(text -> {
                    String pattern = "[^0-9+.]";
                    return text.replaceAll(pattern, "");
                }).findFirst().orElse(null);
                estado.setVersao(versao);
        }
        return estado;
    }
}