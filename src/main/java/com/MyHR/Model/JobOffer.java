package com.MyHR.Model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="myhr_jobOffers",uniqueConstraints={@UniqueConstraint(columnNames={"jobTitle"})})
public class JobOffer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long jobOfferId;
	private String jobTitle;
	private Timestamp startDate;
	@Transient
	private String startDateString;
	private long noOfApplications;
	
	public long getJobOfferId() {
		return jobOfferId;
	}
	public void setJobOfferId(long jobOfferId) {
		this.jobOfferId = jobOfferId;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getStartDateString() {
		return startDateString;
	}
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}
	public long getNoOfApplications() {
		return noOfApplications;
	}
	public void setNoOfApplications(long noOfApplications) {
		this.noOfApplications = noOfApplications;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (jobOfferId ^ (jobOfferId >>> 32));
		result = prime * result
				+ ((jobTitle == null) ? 0 : jobTitle.hashCode());
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
		JobOffer other = (JobOffer) obj;
		if (jobOfferId != other.jobOfferId)
			return false;
		if (jobTitle == null) {
			if (other.jobTitle != null)
				return false;
		} else if (!jobTitle.equals(other.jobTitle))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JobOffer:[Job Offer Id:"+ jobOfferId +", Job Title:" + jobTitle + ", Start Date:" +startDate 
				+ ", No of Applications:"+ noOfApplications +"]";
	}
	
}
