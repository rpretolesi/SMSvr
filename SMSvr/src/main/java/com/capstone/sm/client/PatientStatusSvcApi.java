package com.capstone.sm.client;

import java.util.Collection;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.capstone.sm.repository.CancerTherapy;
import com.capstone.sm.repository.PatientStatus;

public interface PatientStatusSvcApi 
{
	
	public static final String TOKEN_PATH = "/oauth/token";
	
	// The path where we expect the PatientSvc to live
	public static final String PATIENT_STATUS_SVC_PATH = "/patientstatus";
	
	// The path to search Patient Status by Doctor ID
	public static final String PATIENT_STATUS_DOCTOR_ID_SEARCH_PATH = PATIENT_STATUS_SVC_PATH + "/search/findByDoctorid";

	@GET(PATIENT_STATUS_SVC_PATH)
	public Collection<PatientStatus> getPatientStatusList();
	
	@POST(PATIENT_STATUS_SVC_PATH)
	public Collection<PatientStatus> addPatientStatus(@Body Collection<PatientStatus> cpsm);

	@GET(PATIENT_STATUS_DOCTOR_ID_SEARCH_PATH + "/{doctorid}")
	public Collection<PatientStatus> getPatientStatusByDoctorid(@Path("doctorid") long doctorid);

	@GET(PATIENT_STATUS_SVC_PATH + "/{id}")
	public PatientStatus getPatientStatusById(@Path("id") long id);
	
	@PUT(PATIENT_STATUS_SVC_PATH + "/{patientid}" + "/{doctorid}")
	public Void setPatientStatusAsChecked(@Path("patientid") long patientid, @Path("doctorid") long doctorid);

}
