package ve.com.digitel.clientwsfacadepayment.objects;

import java.util.ArrayList;
import java.util.List;


public class Voucher {
	
	private ArrayList<String> linea;

	public ArrayList<String> getLinea() {
		return linea;
	}

	public void setLinea(ArrayList<String> linea) {
		this.linea = linea;
	}

	@Override
	public String toString() {
		return "Voucher [linea=" + linea.toString() + "]";
	}
	
	
}