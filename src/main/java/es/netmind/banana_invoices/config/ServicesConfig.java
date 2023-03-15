package es.netmind.banana_invoices.config;

import es.netmind.banana_invoices.persistence.IPropietarioRepo;
import es.netmind.banana_invoices.persistence.IReciboRepo;
import es.netmind.banana_invoices.services.IInventario;
import es.netmind.banana_invoices.services.InventarioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {

	@Autowired
    IPropietarioRepo iPropietarioRepo;
	@Autowired
    IReciboRepo iReciboRepo;
	
	@Bean
	public IInventario inventario() {
		InventarioImpl invent = new InventarioImpl();
		invent.setPropietariosRepo(iPropietarioRepo);
		invent.setRecibosRepo(iReciboRepo);
		return invent;
	}

}
