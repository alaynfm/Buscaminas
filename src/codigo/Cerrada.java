package codigo;

public class Cerrada implements Estado {


	@Override
	public void clickIzq(int fila, int columna) {
		
		(Tablero.getTablero().getCasilla(fila, columna)).cambiarEstado(new Abierto());
		//Paso 1 para haGanado()
		
	}
	@Override
	public void clickDer(int fila, int columna) {
		(Tablero.getTablero().getCasilla(fila, columna)).cambiarEstado(new Bandera());
		
	}
	@Override
	public int getEstado() {
		// TODO Auto-generated method stub
		return 2;
	}
}