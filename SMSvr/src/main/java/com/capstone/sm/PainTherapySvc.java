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

import retrofit.http.GET;
import retrofit.http.Path;

import com.capstone.sm.client.CancerTherapySvcApi;
import com.capstone.sm.client.PainTherapySvcApi;
import com.capstone.sm.repository.CancerTherapy;
import com.capstone.sm.repository.PainTherapy;
import com.capstone.sm.repository.PainTherapyRepository;
import com.google.common.collect.Lists;

@Controller
public class PainTherapySvc{
	
	public PainTherapySvc() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private PainTherapyRepository paintherapies;
		
	@RequestMapping(value=PainTherapySvcApi.PAIN_THERAPY_STATUS_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<PainTherapy> getPainTherapyList(){
		return Lists.newArrayList(paintherapies.findAll());
	}

	@RequestMapping(value=PainTherapySvcApi.PAIN_THERAPY_STATUS_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody PainTherapy addPainTherapy(@RequestBody PainTherapy ctm) {
		return paintherapies.save(ctm);
	}	
	
	@RequestMapping(value=PainTherapySvcApi.PAIN_THERAPY_STATUS_SVC_PATH, method=RequestMethod.PUT)
	public @ResponseBody PainTherapy updatePainTherapy(@RequestBody PainTherapy ptm) {
		PainTherapy pt = paintherapies.findById(ptm.getId());
		PainTherapy ptr = null;
		if (ptm != null)
		{
			
			// Here i can update the value that i need,
			// actually i don't need to update no values

			ptr = paintherapies.save(pt);
			
		}
		
		return ptr;
	}

	@RequestMapping(value=PainTherapySvcApi.PAIN_THERAPY_STATUS_SVC_PATH + "/{id}", method=RequestMethod.DELETE)
	public @ResponseBody Void deletePainTherapy(@PathVariable long id, HttpServletResponse response) {

				response.setStatus(200);			

				paintherapies.delete(id);

				return null;
	}
	
	@RequestMapping(value=PainTherapySvcApi.PAIN_THERAPY_STATUS_SVC_PATH + "/{id}", method=RequestMethod.GET)
	public @ResponseBody PainTherapy getPainTherapyById(@PathVariable long id, HttpServletResponse response) {
		PainTherapy ptm = paintherapies.findById(id);

		response.setStatus(200);			
		
		return ptm;
	}
	
	@RequestMapping(value=PainTherapySvcApi.PAIN_THERAPY_PATIENT_ID_SEARCH_PATH + "/{patientid}", method=RequestMethod.GET)
	public @ResponseBody Collection<PainTherapy> getPainTherapyByPatientid(@PathVariable long patientid, HttpServletResponse response) {
		ArrayList <PainTherapy> alptm = new ArrayList<PainTherapy>();
		for (PainTherapy ptm : paintherapies.findAll()){
			long lPatientID = ptm.getPatientid();
			if(patientid == lPatientID)
			{
				alptm.add(ptm);
			}
		}
		
		response.setStatus(200);			

		return alptm;
	}
	
	@RequestMapping(value=PainTherapySvcApi.PAIN_THERAPY_PATIENT_ID_DOCTOR_ID_AND_NAME_SEARCH_PATH + "/{patientid}" + "/{doctorid}" + "/{name}", method=RequestMethod.GET)
	public @ResponseBody PainTherapy getPainTherapyByPatientidAndDoctoridAndName(@PathVariable long patientid, @PathVariable long doctorid, @PathVariable String name, HttpServletResponse response) {
		PainTherapy ptm = new PainTherapy();
		for (PainTherapy ptmTemp : paintherapies.findAll()){
			long lPatientID = ptmTemp.getPatientid();
			long lDoctorID = ptmTemp.getDoctorid();
			String strName = ptmTemp.getName().trim();
			if(patientid == lPatientID && doctorid == lDoctorID && name.trim().equals(strName) == true)
			{
				ptm = ptmTemp;
			}
		}
		
		response.setStatus(200);			

		return ptm;
	}

	@RequestMapping(value=PainTherapySvcApi.PAIN_THERAPY_PATIENT_ID_DOCTOR_ID_SEARCH_PATH + "/{patientid}" + "/{doctorid}", method=RequestMethod.GET)
	public @ResponseBody Collection<PainTherapy> getPainTherapyByPatientidAndDoctorid(@PathVariable long patientid, @PathVariable long doctorid, HttpServletResponse response) {
		ArrayList <PainTherapy> alptm = new ArrayList<PainTherapy>();
		for (PainTherapy ptmTemp : paintherapies.findAll()){
			long lPatientID = ptmTemp.getPatientid();
			long lDoctorID = ptmTemp.getDoctorid();
			if(patientid == lPatientID && ptmTemp.getDoctorid() == lDoctorID)
			{
				alptm.add(ptmTemp);
			}
		}
		
		response.setStatus(200);			

		return alptm;
	}	

	@RequestMapping(value=PainTherapySvcApi.PAIN_THERAPY_PATIENT_ID_AND_NAME_SEARCH_PATH + "/{patientid}" + "/{name}", method=RequestMethod.GET)
	public @ResponseBody Collection<PainTherapy> getPainTherapyByPatientidAndName(@PathVariable long patientid, @PathVariable String name, HttpServletResponse response) {
		ArrayList <PainTherapy> alptm = new ArrayList<PainTherapy>();
		for (PainTherapy ptmTemp : paintherapies.findAll()){
			long lPatientID = ptmTemp.getPatientid();
			String strName = ptmTemp.getName().trim();
			if(patientid == lPatientID && name.trim() == strName)
			{
				alptm.add(ptmTemp);
			}
		}
		
		response.setStatus(200);			

		return alptm;
	}	

}
