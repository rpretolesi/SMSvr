package com.capstone.sm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.sm.client.PatientSvcApi;
import com.capstone.sm.repository.Patient;
import com.capstone.sm.repository.PatientRepository;
import com.google.common.collect.Lists;

@Controller
public class PatientSvc{

	@Autowired
	private PatientRepository patients;
	
	private PictureFileManager pictureDataMgr = null;
	
	public PatientSvc() {
		// TODO Auto-generated constructor stub
		 try {
			 pictureDataMgr = PictureFileManager.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private String getDataUrl(long patientId){
        String url = getUrlBaseForLocalServer() + "/patient/" + patientId + "/picture";
        return url;
    }

 	private String getUrlBaseForLocalServer() {
	   HttpServletRequest request = 
	       ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	   String base = 
	      "https://"+request.getServerName() 
	      + ((request.getServerPort() != 80) ? ":"+request.getServerPort() : "");
	   return base;
	}
 	
	@RequestMapping(value=PatientSvcApi.PATIENT_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody Patient addPatient(@RequestBody Patient pm) {

		// Get the remote id
		patients.save(pm);
		// With the id i set the path where i store the pictures
		pm.setDataUrl(getDataUrl(pm.getId()));

		return patients.save(pm);
	}
	
	@RequestMapping(value=PatientSvcApi.PATIENT_SVC_PATH + "/{id}/picture", method=RequestMethod.POST)
	public @ResponseBody Void setPatienPictureData(
//	public @ResponseBody PictureStatus setPatienPictureData(
			@PathVariable("id") long id, @RequestParam("picture") MultipartFile pictureData, HttpServletResponse response) throws ServletException, IOException 
	{
		
		boolean bIsPatientIdPresent = false;
		for (Patient pm : patients.findAll())
		{
			
			if(pm.getId() == id)
			{
				bIsPatientIdPresent = true;
				
				pictureDataMgr.savePictureData(pm.getId(), "Patient", pictureData.getInputStream());
			}

		}
		
		if(bIsPatientIdPresent == true)
		{
			response.setStatus(200);
		}
		else
		{
			response.sendError(404, "Missing:" + id);
		}

//		PictureStatus ps = new PictureStatus(PictureState.READY);
//		return ps;
		
		return null;
		
	}
	
	@RequestMapping(value=PatientSvcApi.PATIENT_SVC_PATH + "/{id}/picture", method=RequestMethod.GET)
	public void getPatienPictureData(@PathVariable("id") long id, HttpServletResponse response) throws ServletException, IOException  
	{
		boolean bIsPatientIdPresent = false;
		for (Patient pm : patients.findAll())
		{
			if(pm.getId() == id)
			{
				bIsPatientIdPresent = true;

				response.setContentType("picture/jpg");
				response.addHeader("id", String.valueOf(pm.getId()));
				pictureDataMgr.copyPictureData(pm.getId(), "Patient", response.getOutputStream());
			}

		}
		
		
		if(bIsPatientIdPresent == true)
		{
			response.setStatus(200);
		}
		else
		{
			response.sendError(404, "Missing:" + id);
		}		

	}	
	
	@RequestMapping(value=PatientSvcApi.PATIENT_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Patient> getPatientList(){
		return Lists.newArrayList(patients.findAll());
	}
	
	@RequestMapping(value=PatientSvcApi.PATIENT_SVC_PATH, method=RequestMethod.PUT)
	public @ResponseBody Patient updatePatient(@RequestBody Patient pm) {
		Patient p = patients.findById(pm.getId());
		Patient pr = null;
		if (p != null)
		{
			// in case of need, we can update all values
			// now i update only the DoctorID
			p.setDoctorid(pm.getDoctorid());

			pr = patients.save(p);
			
		}
		
		return pr;
	}

	@RequestMapping(value=PatientSvcApi.PATIENT_SVC_PATH + "/{id}", method=RequestMethod.GET)
	public @ResponseBody Patient getPatientById(@PathVariable long id, HttpServletResponse response) {
		Patient pm = patients.findById(id);

		response.setStatus(200);			

		return pm;
	}
	
	@RequestMapping(value=PatientSvcApi.PATIENT_USER_NAME_SEARCH_PATH + "/{username}", method=RequestMethod.GET)
	public @ResponseBody Collection<Patient> getPatientByUsername(@PathVariable String username, HttpServletResponse response) {
		ArrayList <Patient> alpm = new ArrayList<Patient>();
		for (Patient pm : patients.findAll()){
			String strpmUserName = pm.getUsername().trim();
			String strpaUserName = username.trim();
			if(strpmUserName.equals(strpaUserName) == true)
			{
				alpm.add(pm);
			}
		}
		
		response.setStatus(200);			

		return alpm;
	}

	@RequestMapping(value=PatientSvcApi.PATIENT_DOCTOR_ID_SEARCH_PATH + "/{doctorid}", method=RequestMethod.GET)
	public @ResponseBody Collection<Patient> getPatientByDoctorid(@PathVariable long doctorid, HttpServletResponse response) {
		ArrayList <Patient> alpm = new ArrayList<Patient>();
		for (Patient pm : patients.findAll()){
			if(pm.getDoctorid() == doctorid)
			{
				alpm.add(pm);
			}
		}
		
		response.setStatus(200);			

		return alpm;
	}
}
