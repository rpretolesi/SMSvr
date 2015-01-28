package com.capstone.sm;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.sm.client.PatientStatusSvcApi;
import com.capstone.sm.repository.PatientStatus;
import com.capstone.sm.repository.PatientStatusRepository;
import com.google.common.collect.Lists;

@Controller
public class PatientStatusSvc{
	
	public PatientStatusSvc() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private PatientStatusRepository patientstatuss;

	@RequestMapping(value=PatientStatusSvcApi.PATIENT_STATUS_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<PatientStatus> getPatientStatusList(){
		return Lists.newArrayList(patientstatuss.findAll());
	}
	
	@RequestMapping(value=PatientStatusSvcApi.PATIENT_STATUS_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody Collection<PatientStatus> addPatientStatus(@RequestBody Collection<PatientStatus> cpsm) {
		Collection<PatientStatus> cpsmTemp = new ArrayList<PatientStatus>();
		for (PatientStatus psm : cpsm)
		{
			cpsmTemp.add(patientstatuss.save(psm));
		}

		return cpsmTemp;
	}

	@RequestMapping(value=PatientStatusSvcApi.PATIENT_STATUS_DOCTOR_ID_SEARCH_PATH + "/{doctorid}", method=RequestMethod.GET)
	public @ResponseBody Collection<PatientStatus> getPatientStatusByDoctorId(@PathVariable long doctorid, HttpServletResponse response){
		ArrayList <PatientStatus> alpsm = new ArrayList<PatientStatus>();
		for (PatientStatus psm : patientstatuss.findAll()){
			long lDoctorID = psm.getDoctorid();
			if(doctorid == lDoctorID)
			{
				alpsm.add(psm);
			}
		}
		
		response.setStatus(200);			

		return alpsm;
	}

	@RequestMapping(value=PatientStatusSvcApi.PATIENT_STATUS_SVC_PATH + "/{id}", method=RequestMethod.GET)
	public @ResponseBody PatientStatus getPatientStatusById(@PathVariable long id, HttpServletResponse response) {
		PatientStatus psm = patientstatuss.findById(id);

		response.setStatus(200);			

		return psm;
	}

	@RequestMapping(value=PatientStatusSvcApi.PATIENT_STATUS_SVC_PATH + "/{patientid}" + "/{doctorid}", method=RequestMethod.PUT)
	public @ResponseBody Void setPatientStatusAsChecked(@PathVariable long patientid, @PathVariable long doctorid, HttpServletResponse response) {
		for (PatientStatus psm : patientstatuss.findAll())
		{
			long lPatientID = psm.getPatientid();
			long lDoctorID = psm.getDoctorid();
			if(patientid == lPatientID && doctorid == lDoctorID)
			{
				psm.setCheckedbydoctor(1);

				patientstatuss.save(psm);
			}
		}

		response.setStatus(200);			

		return null;

	}

}
