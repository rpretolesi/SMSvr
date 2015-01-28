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

import com.capstone.sm.client.CancerTherapySvcApi;
import com.capstone.sm.repository.CancerTherapy;
import com.capstone.sm.repository.CancerTherapyRepository;
import com.google.common.collect.Lists;

@Controller
public class CancerTherapySvc{
	
	public CancerTherapySvc() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private CancerTherapyRepository cancertherapies;
	
	@RequestMapping(value=CancerTherapySvcApi.CANCER_THERAPY_STATUS_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<CancerTherapy> getCancerTherapyList(){
		return Lists.newArrayList(cancertherapies.findAll());
	}

	@RequestMapping(value=CancerTherapySvcApi.CANCER_THERAPY_STATUS_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody CancerTherapy addCancerTherapy(@RequestBody CancerTherapy ctm) {
		return cancertherapies.save(ctm);
	}
	
	@RequestMapping(value=CancerTherapySvcApi.CANCER_THERAPY_STATUS_SVC_PATH, method=RequestMethod.PUT)
	public @ResponseBody CancerTherapy updateCancerTherapy(@RequestBody CancerTherapy ctm) {
		CancerTherapy ct = cancertherapies.findById(ctm.getId());
		CancerTherapy ctr = null;
		if (ctm != null)
		{
			ct.setDatetimeset(ctm.getDatetimeset());
			ct.setDatetimeexec(ctm.getDatetimeexec());

			ctr = cancertherapies.save(ct);
			
		}
		
		return ctr;
	}
		
	@RequestMapping(value=CancerTherapySvcApi.CANCER_THERAPY_STATUS_SVC_PATH + "/{id}", method=RequestMethod.DELETE)
	public @ResponseBody Void deleteCancerTherapy(@PathVariable long id, HttpServletResponse response) {

				response.setStatus(200);			

				cancertherapies.delete(id);

				return null;
	}

	@RequestMapping(value=CancerTherapySvcApi.CANCER_THERAPY_STATUS_SVC_PATH + "/{id}", method=RequestMethod.GET)
	public @ResponseBody CancerTherapy getCancerTherapyById(@PathVariable long id, HttpServletResponse response) {
		CancerTherapy ctm = cancertherapies.findById(id);

		response.setStatus(200);			
		
		return ctm;
	}
	
	@RequestMapping(value=CancerTherapySvcApi.CANCER_THERAPY_PATIENT_ID_SEARCH_PATH + "/{patientid}", method=RequestMethod.GET)
	public @ResponseBody Collection<CancerTherapy> getCancerTherapyByPatientid(@PathVariable long patientid, HttpServletResponse response) {
		ArrayList <CancerTherapy> alctm = new ArrayList<CancerTherapy>();
		for (CancerTherapy ctm : cancertherapies.findAll()){
			long lPatientID = ctm.getPatientid();
			if(patientid == lPatientID)
			{
				alctm.add(ctm);
			}
		}
		
		response.setStatus(200);			

		return alctm;
	}
	
	@RequestMapping(value=CancerTherapySvcApi.CANCER_THERAPY_DOCTOR_ID_SEARCH_PATH + "/{doctorid}", method=RequestMethod.GET)
	public @ResponseBody Collection<CancerTherapy> getCancerTherapyByDoctorid(@PathVariable long doctorid, HttpServletResponse response) {
		ArrayList <CancerTherapy> alctm = new ArrayList<CancerTherapy>();
		for (CancerTherapy ctm : cancertherapies.findAll()){
			long lDoctorID = ctm.getDoctorid();
			if(doctorid == lDoctorID)
			{
				alctm.add(ctm);
			}
		}
		
		response.setStatus(200);			

		return alctm;
	}
	
	@RequestMapping(value=CancerTherapySvcApi.CANCER_THERAPY_PATIENT_ID_DOCTOR_ID_AND_NAME_SEARCH_PATH + "/{patientid}" + "/{doctorid}" + "/{name}", method=RequestMethod.GET)
	public @ResponseBody CancerTherapy getCancerTherapyByPatientidAndDoctoridAndName(@PathVariable long patientid, @PathVariable long doctorid, @PathVariable String name, HttpServletResponse response) {
		CancerTherapy ctm = new CancerTherapy();
		for (CancerTherapy ctmTemp : cancertherapies.findAll()){
			long lPatientID = ctmTemp.getPatientid();
			long lDoctorID = ctmTemp.getDoctorid();
			String strName = ctmTemp.getName().trim();
			if(patientid == lPatientID && doctorid == lDoctorID && name.trim().equals(strName) == true)
			{
				ctm = ctmTemp;
			}
		}
		
		response.setStatus(200);			

		return ctm;
	}

	@RequestMapping(value=CancerTherapySvcApi.CANCER_THERAPY_PATIENT_ID_DOCTOR_ID_SEARCH_PATH + "/{patientid}" + "/{doctorid}", method=RequestMethod.GET)
	public @ResponseBody Collection<CancerTherapy> getCancerTherapyByPatientidAndDoctorid(@PathVariable long patientid, @PathVariable long doctorid, HttpServletResponse response) {
		ArrayList <CancerTherapy> alctm = new ArrayList<CancerTherapy>();
		for (CancerTherapy ctmTemp : cancertherapies.findAll()){
			long lPatientID = ctmTemp.getPatientid();
			long lDoctorID = ctmTemp.getDoctorid();
			if(patientid == lPatientID && doctorid == lDoctorID)
			{
				alctm.add(ctmTemp);
			}
		}
		
		response.setStatus(200);			

		return alctm;
	}	

	@RequestMapping(value=CancerTherapySvcApi.CANCER_THERAPY_PATIENT_ID_AND_NAME_SEARCH_PATH + "/{patientid}" + "/{name}", method=RequestMethod.GET)
	public @ResponseBody Collection<CancerTherapy> getCancerTherapyByPatientidAndName(@PathVariable long patientid, @PathVariable String name, HttpServletResponse response) {
		ArrayList <CancerTherapy> alctm = new ArrayList<CancerTherapy>();
		for (CancerTherapy ctmTemp : cancertherapies.findAll()){
			long lPatientID = ctmTemp.getPatientid();
			String strName = ctmTemp.getName().trim();
			if(patientid == lPatientID && name.trim() == strName)
			{
				alctm.add(ctmTemp);
			}
		}
		
		response.setStatus(200);			

		return alctm;
	}	
	
}
