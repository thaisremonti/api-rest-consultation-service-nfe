package com.example.teste.Entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@SequenceGenerator(name = "id", sequenceName = "id", allocationSize = 1, initialValue = 2)
@Entity
@Table(name = "tb_estado")
public class Estado implements Serializable{

	private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue(generator = "id")
    private Long id;
    
    @Size(max = 6)
    @Column(name = "esta_uf")
    private String estaUf;

    @Column(name = "status")
    private String status;
    
    @Column(name = "data")
    private String data;
	
	@Column(name= "versao")
	private String versao;
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
	
    public String getEstaUf() {
        return estaUf;
    }

    public void setEstaUf(String estaUf) {
        this.estaUf = estaUf;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
    public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}
    
}

