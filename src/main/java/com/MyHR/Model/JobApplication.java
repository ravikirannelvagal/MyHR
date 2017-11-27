package com.MyHR.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="myhr_application",uniqueConstraints={@UniqueConstraint(columnNames={"jobOfferId", "candidateEmail"})})
@NamedQueries({
	@NamedQuery(name="findIsJobAlreadyApplied",
				query="SELECT ja "
						+ "FROM JobApplication ja, "
						+ "JobOffer jo "
						+ "WHERE jo.jobOfferId = :jobOfferId AND "
						+ "ja.candidateEmail = :candidateEmail"),
	@NamedQuery(name="findAllApplicationsByJobId",
				query="SELECT ja "
						+ "FROM JobApplication ja "
						+ "WHERE "
						+ "ja.offer IN "
						+ "(SELECT jo "
						+ "FROM JobOffer jo "
						+ "WHERE jo.jobOfferId=:jobOfferId)")
})
public class JobApplication{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long applicationId;
	@ManyToOne
	@JoinColumn(name="jobOfferId")
	private JobOffer offer;
	@Transient
	private long jobOfferId;
	private String candidateEmail;
	private String resumeText;
	private String applnStatus;
	
	public JobApplication(){
		
	}
	public long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}
	public JobOffer getOfferId() {
		return offer;
	}
	public void setOfferId(JobOffer offer) {
		this.offer = offer;
	}
	public String getCandidateEmail() {
		return candidateEmail;
	}
	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}
	public String getResumeText() {
		return resumeText;
	}
	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}
	public String getApplnStatus() {
		return applnStatus;
	}
	public void setApplnStatus(String applnStatus) {
		this.applnStatus = applnStatus;
	}
	public JobOffer getOffer() {
		return offer;
	}
	public void setOffer(JobOffer offer) {
		this.offer = offer;
	}
	public long getJobOfferId() {
		return jobOfferId;
	}
	public void setJobOfferId(long jobOfferId) {
		this.jobOfferId = jobOfferId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (applicationId ^ (applicationId >>> 32));
		result = prime * result
				+ ((applnStatus == null) ? 0 : applnStatus.hashCode());
		result = prime * result
				+ ((candidateEmail == null) ? 0 : candidateEmail.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobApplication other = (JobApplication) obj;
		if (applicationId != other.applicationId)
			return false;
		if (applnStatus == null) {
			if (other.applnStatus != null)
				return false;
		} else if (!applnStatus.equals(other.applnStatus))
			return false;
		if (candidateEmail == null) {
			if (other.candidateEmail != null)
				return false;
		} else if (!candidateEmail.equals(other.candidateEmail))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JobApplication [Application Id:" + applicationId + ", Job Offer Id:"
				+ offer.getJobOfferId() + ", Job Title:" + offer.getJobTitle() + ", Candidate Email:"
				+ candidateEmail + ", Resume Text:" + resumeText
				+ ", Application Status:" + applnStatus + "]";
	}
	
	
}
