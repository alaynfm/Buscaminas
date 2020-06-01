package codigo;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel { //clase para que no se puedan editar las celdas del JTable
	   public CustomTableModel() {
		   super();
	   }
	   public boolean isCellEditable(int row, int column) {
	      return false;
	   }
}
