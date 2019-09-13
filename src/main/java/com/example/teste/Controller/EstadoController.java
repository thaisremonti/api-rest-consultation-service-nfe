package com.example.teste.Controller;


import java.util.List;

import com.example.teste.Entidade.Estado;
import com.example.teste.Enum.EStatus;
import com.example.teste.Repository.Repository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value="/api")
public class EstadoController {

    @Autowired
    Repository repository;

    @GetMapping("status")
    public List<Estado> lista(){
        return repository.findAll();
    }

    //@GetMapping("porEstado/{id}")
    //public Estado listaEstados(@PathVariable(value="estaUf") Long id){
        //return repository.findById(id)
    //}



    @PostMapping("teste")
    public void teste() {
        
        try {           
            Document doc = Jsoup.connect("http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx").get();
            String status;
            String estados;
            
            for (Element b : doc.select(".linhaImparCentralizada")) {
                Estado estado = new Estado();
                String aux = b.html();
                aux = aux.replace("<td>", "");
                String[] aux2 = aux.split("<\\/td>");
                estados = aux2[0];
                String data = doc.select(".fonte10").get(0).text().replace("- �ltima Verifica��o: ", "").replace(" - WebServices Vers�es 3.10 ou anteriores", "");
                
                if (aux2[1].contains("bola_vermelho_P")) {
                    status = EStatus.VERMELHO.getDescricao();
                } else if (aux2[1].contains("bola_verde_P")) {
                    status = EStatus.VERDE.getDescricao();
                } else {
                    status = EStatus.AMARELO.getDescricao();
                }
                
                estado.setStatus(status);
                estado.setData(data);
                estado.setEstaUf(estados);
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

}