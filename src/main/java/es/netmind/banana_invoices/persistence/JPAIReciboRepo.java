package es.netmind.banana_invoices.persistence;

import es.netmind.banana_invoices.models.Propietario;
import es.netmind.banana_invoices.models.Recibo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.transaction.annotation.*;

import java.util.List;

public class JPAIReciboRepo implements IReciboRepo{
	@PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Recibo> findAllRecibos() {
    	Query query = em.createQuery("SELECT r FROM Recibo r");
        return (List<Recibo>) query.getResultList();
    }
    
    @Override
    @Transactional
    public Recibo saveRecibo(Recibo recibo) {
        em.persist(recibo);
        return recibo;
    }
}
