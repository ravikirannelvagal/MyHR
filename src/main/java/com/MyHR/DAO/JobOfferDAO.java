package com.MyHR.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.h2.message.DbException;
import org.springframework.stereotype.Repository;
import com.MyHR.Model.JobOffer;

@Repository
@Transactional
public class JobOfferDAO {

	@PersistenceContext
	private EntityManager em;
	
	public long create(JobOffer offer){
		em.persist(offer);
		em.flush();
		return offer.getJobOfferId();
	}
	
	public void update(JobOffer offer){
		em.merge(offer);
	}
	
	public JobOffer getJobOfferById(long id){
		return em.find(JobOffer.class,id);
	}
	
	public List<JobOffer> getAllJobOffers(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<JobOffer> cq = cb.createQuery(JobOffer.class);
		Root<JobOffer> jobOffersRoot = cq.from(JobOffer.class);
		CriteriaQuery<JobOffer> allJobOffers = cq.select(jobOffersRoot);
		TypedQuery<JobOffer> typedQuery = em.createQuery(allJobOffers);
		List<JobOffer> jo =typedQuery.getResultList(); 
		return jo;
	}
	
	public void delete(long id){
		JobOffer jobOffer= getJobOfferById(id);
		if(jobOffer!=null){
			em.remove(jobOffer);
		}
	}
	
	public void deleteAll(){
		List<JobOffer> allOffers = this.getAllJobOffers();
		for(JobOffer j:allOffers){
			this.delete(j.getJobOfferId());
		}
	}
	
	public boolean getJobOfferByTitle(String jobTitle){
		TypedQuery<JobOffer> getJobByTitleQuery = em.createQuery
				("SELECT J FROM JobOffer AS J WHERE J.jobTitle = :jobTitle",JobOffer.class);
		try{
			getJobByTitleQuery.setParameter("jobTitle", jobTitle).getSingleResult();
			return true;
		}catch(NoResultException e){
			return false;
		}catch(DbException e){
			return false;
		}
		
	}
}
