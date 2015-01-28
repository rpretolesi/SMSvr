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

import com.capstone.sm.client.DoctorSvcApi;
import com.capstone.sm.client.PatientSvcApi;
import com.capstone.sm.repository.Doctor;
import com.capstone.sm.repository.DoctorRepository;
import com.capstone.sm.repository.Patient;
import com.google.common.collect.Lists;

@Controller
public class DoctorSvc{
	
	@Autowired
	private DoctorRepository doctors;

	private PictureFileManager pictureDataMgr = null;

	public DoctorSvc() {
		// TODO Auto-generated constructor stub
		 try {
			 pictureDataMgr = PictureFileManager.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private String getDataUrl(long patientId){
        String url = getUrlBaseForLocalServer() + "/doctor/" + patientId + "/picture";
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

	@RequestMapping(value=DoctorSvcApi.DOCTOR_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody Doctor addDoctor(@RequestBody Doctor dm) {
		// Get the remote id
		doctors.save(dm);
		// With the id i set the path where i store the pictures
		dm.setDataUrl(getDataUrl(dm.getId()));
		
		return doctors.save(dm);
	}
	
	@RequestMapping(value=DoctorSvcApi.DOCTOR_SVC_PATH + "/{id}/picture", method=RequestMethod.POST)
	public @ResponseBody Void setDoctorPictureData(
//	public @ResponseBody PictureStatus setPatienPictureData(
			@PathVariable("id") long id, @RequestParam("picture") MultipartFile pictureData, HttpServletResponse response) throws ServletException, IOException 
	{
		
		boolean bIsDoctorIdPresent = false;
		for (Doctor dm : doctors.findAll())
		{
			
			if(dm.getId() == id)
			{
				bIsDoctorIdPresent = true;
				
				pictureDataMgr.savePictureData(dm.getId(), "Doctor", pictureData.getInputStream());
			}

		}
		
		if(bIsDoctorIdPresent == true)
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
	
	@RequestMapping(value=DoctorSvcApi.DOCTOR_SVC_PATH + "/{id}/picture", method=RequestMethod.GET)
	public void getDoctorPictureData(@PathVariable("id") long id, HttpServletResponse response) throws ServletException, IOException  
	{
		boolean bIsDoctorIdPresent = false;
		for (Doctor dm : doctors.findAll())
		{
			if(dm.getId() == id)
			{
				bIsDoctorIdPresent = true;

				response.setContentType("picture/jpg");
				response.addHeader("id", String.valueOf(dm.getId()));
				pictureDataMgr.copyPictureData(dm.getId(), "Doctor", response.getOutputStream());
			}

		}
		
		
		if(bIsDoctorIdPresent == true)
		{
			response.setStatus(200);
		}
		else
		{
			response.sendError(404, "Missing:" + id);
		}		

	}	
		
	@RequestMapping(value=DoctorSvcApi.DOCTOR_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Doctor> getDoctorList(){
		return Lists.newArrayList(doctors.findAll());
	}

	@RequestMapping(value=DoctorSvcApi.DOCTOR_SVC_PATH + "/{id}", method=RequestMethod.GET)
	public @ResponseBody Doctor getDoctorById(@PathVariable long id, HttpServletResponse response) {
		Doctor dm = doctors.findById(id);

		response.setStatus(200);			
		
		return dm;
	}
	
	@RequestMapping(value=DoctorSvcApi.DOCTOR_USER_NAME_SEARCH_PATH + "/{username}", method=RequestMethod.GET)
	public @ResponseBody Collection<Doctor> getDoctorByUsername(@PathVariable String username, HttpServletResponse response) {
		ArrayList <Doctor> aldm = new ArrayList<Doctor>();
		for (Doctor pm : doctors.findAll()){
			String strpmUserName = pm.getUsername().trim();
			String strpaUserName = username.trim();
			if(strpmUserName.equals(strpaUserName) == true)
			{
				aldm.add(pm);
			}
		}
		
		response.setStatus(200);			

		return aldm;
	}	
}
