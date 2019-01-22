package ve.com.digitel.clientwsfacadepayment.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"codigo","descripcion","afiliacion","authid","authname","facture","lote","marca","referencia", "rifbanco", "seqn", "tarjeta","terminal", "voucher", "vtid", "ult"})
public class Payment {	
	
	private String codigo;
	
    private String descripcion;
    
    private String afiliacion;
    
    private String authid;
    
    private String authname;
    
    private String facture;
    
    private String lote;
   
    private String marca;
    
    private String referencia;
    
    private String rifbanco;
    
    private String seqn;
    
    private String tarjeta;
    
    private String terminal;
    
    private Voucher voucher;
    
    private String vtid;
    
    private String ult;

	public Payment() {
		super();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAfiliacion() {
		return afiliacion;
	}

	public void setAfiliacion(String afiliacion) {
		this.afiliacion = afiliacion;
	}

	public String getAuthid() {
		return authid;
	}

	public void setAuthid(String authid) {
		this.authid = authid;
	}

	public String getAuthname() {
		return authname;
	}

	public void setAuthname(String authname) {
		this.authname = authname;
	}

	public String getFacture() {
		return facture;
	}

	public void setFacture(String facture) {
		this.facture = facture;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getRifbanco() {
		return rifbanco;
	}

	public void setRifbanco(String rifbanco) {
		this.rifbanco = rifbanco;
	}

	public String getSeqn() {
		return seqn;
	}

	public void setSeqn(String seqn) {
		this.seqn = seqn;
	}
	

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public String getVtid() {
		return vtid;
	}

	public void setVtid(String vtid) {
		this.vtid = vtid;
	}
	
	public String getUlt() {
		return ult;
	}

	public void setUlt(String ult) {
		this.ult = ult;
	}

	@Override
	public String toString() {
		return "Payment [codigo=" + codigo + ", descripcion=" + descripcion
				+ ", afiliacion=" + afiliacion + ", authid=" + authid
				+ ", authname=" + authname + ", facture=" + facture + ", lote="
				+ lote + ", marca=" + marca + ", referencia=" + referencia
				+ ", rifbanco=" + rifbanco + ", seqn=" + seqn + ", tarjeta="
				+ tarjeta + ", terminal=" + terminal + ", voucher=" + voucher
				+ ", vtid=" + vtid + ", ult=" + ult + "]";
	}
	
	
	
	
}
