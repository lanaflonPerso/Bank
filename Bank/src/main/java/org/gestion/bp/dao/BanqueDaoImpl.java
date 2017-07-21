package org.gestion.bp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.gestion.bp.entities.Client;
import org.gestion.bp.entities.Compte;
import org.gestion.bp.entities.Employe;
import org.gestion.bp.entities.Groupe;
import org.gestion.bp.entities.Operation;

public class BanqueDaoImpl implements IBanqueDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Client addClient(Client c) {
		em.persist(c);
		// TODO Auto-generated method stub
		return c;
	}

	@Override
	public Employe addEmploye(Employe e, Long codeSup) {
		if (codeSup != null) {
			Employe sup = em.find(Employe.class, codeSup);
			e.setEmployeSup(sup);
		}
		return e;
	}

	@Override
	public Groupe addGroupe(Groupe g) {
		em.persist(g);
		// TODO Auto-generated method stub
		return g;
	}

	@Override
	public void addEmployeToGroupe(Long codeEmp, Long codeGr) {
		// TODO Auto-generated method stub
		Employe e = em.find(Employe.class, codeEmp);
		Groupe g = em.find(Groupe.class, codeGr);
		e.getGroupes().add(g);
		g.getEmployes().add(e);
	}

	@Override
	public Compte addCompte(Compte cp, Long codeCli, Long codeEmp) {
		// TODO Auto-generated method stub
		Client cli = em.find(Client.class, codeCli);
		Employe emp = em.find(Employe.class, codeEmp);
		cp.setClient(cli);
		cp.setEmploye(emp);
		em.persist(cp);
		return cp;
	}

	@Override
	public Operation addOperation(Operation op, String codeCpte, Long codeEmp) {
		// TODO Auto-generated method stub
		Compte cp = consulterCompte(codeCpte);
		Employe emp = em.find(Employe.class, codeEmp);
		op.setCompte(cp);
		op.setEmploye(emp);
		em.persist(op);
		return op;
	}


	@Override
	public Compte consulterCompte(String codeCpte) {
		// TODO Auto-generated method stub
		Compte cp = em.find(Compte.class, codeCpte);
		if (cp == null) {
			throw new RuntimeException("Compte introuvable");
		}
		return cp;
	}

	@Override
	public List<Operation> consulterOperations(String codeCpte) {
		// TODO Auto-generated method stub
		Query req = em.createQuery("select o from Operation o where o.compte where o.compte.codeCompte=:x");
		req.setParameter("x", codeCpte);

		return req.getResultList();
	}

	@Override
	public Client consulterClient(Long codeCli) {
		// TODO Auto-generated method stub
		Client cl = em.find(Client.class, codeCli);
		if (cl == null) {
			throw new RuntimeException("Compte introuvable");
		}
		return cl;
	}

	@Override
	public List<Client> consulterClients(String mc) {
		// TODO Auto-generated method stub
		Query req = em.createQuery("select c from Client c where c.nomClient like :x");
		req.setParameter("x", "%" + mc + "%");

		return req.getResultList();
	}

	@Override
	public List<Compte> getComptesByClient(Long codeCli) {
		// TODO Auto-generated method stub
		Query req = em.createQuery("select c from compte c where c.client.codeClient = :x");
		req.setParameter("x", codeCli);

		return req.getResultList();

	}

	@Override
	public List<Compte> getComptesByEmploye(Long codeEmp) {
		Query req = em.createQuery("select c from compte c where c.employe.Employe = :x");
		req.setParameter("x", codeEmp);

		return req.getResultList();
	}

	@Override
	public List<Employe> getEmployes() {
		// TODO Auto-generated method stub
		Query req = em.createQuery("select e from Employe e");
		return req.getResultList();
	}

	@Override
	public List<Employe> getEmployesByGroupe(Long codeGr) {
		Query req = em.createQuery("select e from Employe e.groupes.codeGroupe:=x");
		req.setParameter("x", codeGr);
		return req.getResultList();
	
	}

	@Override
	public List<Groupe> getGroupes() {
		// TODO Auto-generated method stub
		Query req = em.createQuery("select g from Groupe g");
		return req.getResultList();
	}


}
