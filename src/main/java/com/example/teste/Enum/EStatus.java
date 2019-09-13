package com.example.teste.Enum;

public enum EStatus {

	VERMELHO("Vermelho"),
	VERDE("Verde"),
	AMARELO("Amarelo");
	
	private String descricao;

	private EStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
