package br.com.acc.cronometro.classesBasicas;




public class Demanda {
	
	String cd_demanda;
	
	String tempo;
	
	String data;
	
	Localidade localidade;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getCd_demanda() {
		return cd_demanda;
	}
	public void setCd_demanda(String demanda) {
		cd_demanda = demanda;
	}
	
	public String getTempo() {
		return tempo;
	}
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	
	public Localidade getLocalidade() {
		return localidade;
	}
	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}
	
	
}
