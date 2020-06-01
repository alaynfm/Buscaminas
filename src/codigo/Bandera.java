package codigo;

public class Bandera implements Estado {



	@Override
	public void clickIzq(int fila, int columna) {
		
	}

	@Override
	public void clickDer(int fila, int columna) {
		(Tablero.getTablero().getCasilla(fila, columna)).cambiarEstado(new Cerrada());
		
	}

	@Override
	public int getEstado() {
		// TODO Auto-generated method stub
		return 1;
	}
}