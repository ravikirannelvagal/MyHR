package com.MyHR.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.MyHR.Model.JobApplication;

@Repository
@Transactional
public class JobApplicationDAO {

	@PersistenceContext
	private EntityManager em;
	
	public long create(JobApplication application){
		em.persist(application);
		em.flush();
		return application.getApplicationId();
	}
	
	public void update(JobApplication application){
		em.merge(application);
	}
	
	public JobApplication getApplicationById(long id){
		return em.find(JobApplication.class,id);
	}
	
	public List<JobApplication> getAllApplications(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<JobApplication> cq = cb.createQuery(JobApplication.class);
		Root<JobApplication> applRoot = cq.from(JobApplication.class);
		CriteriaQuery<JobApplication> allApplns = cq.select(applRoot);
		TypedQuery<JobApplication> typedQuery = em.createQuery(allApplns);
		return typedQuery.getResultList();
	}
	
	public void delete(long id){
		JobApplication application= getApplicationById(id);
		if(application!=null){
			em.remove(application);
		}
	}
	
	public void deleteAll(){
		List<JobApplication> allApplications = this.getAllApplications();
		for(JobApplication j:allApplications){
			this.delete(j.getApplicationId());
		}
	}
	
	public JobApplication isJobAlreadyApplied(JobApplication application){
		JobApplication retAppl = null;
		try{
			Query q = em.createNamedQuery("findIsJobAlreadyApplied");
			q.setParameter("jobOfferId", application.getJobOfferId());
			q.setParameter("candidateEmail", application.getCandidateEmail());
			retAppl=(JobApplication) q.getSingleResult();
		}catch(NoResultException e){
			// no such case
		}
		return retAppl;
	}
	
	@SuppressWarnings("unchecked")
	public List<JobApplication> getAllApplicationsByJobId(long jobOfferId){
		List<JobApplication>allJobApplns=null;
		try{
			Query q= em.createNamedQuery("findAllApplicationsByJobId");
			q.setParameter("jobOfferId", jobOfferId);
			allJobApplns=q.getResultList();
		}catch(NoResultException e){
			// no such case
		}
		
		return allJobApplns;
	}
}
