package elements;

public class Historico {
	private String descricao;
	private int idConta;
	private int idHistorico;
	private static int idContador = 0;
	
	public Historico(int idConta, float valor, String texto){
		
		descricao = texto + valor;
		this.idConta = idConta;
		incrementaIdContador();
		idHistorico = idContador;
		
	}
	
	private static void incrementaIdContador() {
		idContador++;
	}
	
	public int getIdHistorico() {
		return idHistorico;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public int getIdConta() {
		return idConta;
	}
}
