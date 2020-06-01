package codigo;

public class LinkedListJ<T> {

	private Node<T> first;
	private T data;
	private int count;

	public LinkedListJ() {
		first = null;
		data = null;
		count = 0;
	}

	public void removeFirst() {

		if (!isEmpty()) {
			first = first.next;
		}
	}

	public void anadirNuevo(String datos) {

		// si es vacio
		if (isEmpty()) {
			Node nuevo = new Node(datos);
			first = nuevo;
			nuevo.next = null;
			count++;

		}

		else {
			Node act = first;
			Node ant = null;
			boolean salir = false;

			String[] linea2 = datos.split("\\s+--->\\s+");
			while (act != null && !salir) {
				String[] linea = ((String) act.data).split("\\s+--->\\s+");
				if (Integer.parseInt(linea[2]) < Integer.parseInt(linea2[2])) {
					// Añadimos antes
					Node<String> nuevo = new Node(datos);
					count++;

					// primera posicion
					if (ant== null) {
						nuevo.next = act;
						first = (Node<T>) nuevo;
						salir=true;

						// en el medio
					} else {

						ant.next = nuevo;
						nuevo.next = act;
						salir = true;

					}

				} else {
					ant = act;
					act = act.next;
				}

			} // while

			if (!salir) {

				//ultima posicion
				Node<String> nuevo = new Node(datos);
				ant.next = nuevo;
				nuevo.next = null;
				count++;

			}
		}

	}

	private boolean isEmpty() {
		return first == null;
	}

	public Node<T> getFirst() {
		return first;
	}

	public static void main(String[] args) {

		LinkedListJ<String> j = new LinkedListJ<String>();
		j.anadirNuevo("f ---> Alain ---> " + 99 + " ---> " + 10 + "");
		j.anadirNuevo("f ---> Alain ---> " + 98 + " ---> " + 10 + "");
		j.anadirNuevo("f ---> Alain ---> " + 97 + " ---> " + 10 + "");
	}
}
