/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.capstone.sm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.capstone.sm.repository.Patient;


public class PictureFileManager {

	/**
	 * This static factory method creates and returns a 
	 * PatientFileManager object to the caller. Feel free to customize
	 * this method to take parameters, etc. if you want.
	 * 
	 * @return
	 * @throws IOException
	 */
	public static PictureFileManager get() throws IOException {
		return new PictureFileManager();
	}
	
	private Path targetDir_ = Paths.get("pictures");
	
	// The VideoFileManager.get() method should be used
	// to obtain an instance
	private PictureFileManager() throws IOException{
		if(!Files.exists(targetDir_)){
			Files.createDirectories(targetDir_);
		}
	}
	
	// Private helper method for resolving video file paths
	private Path getPicturePath(long id, String strClassName){
		
		return targetDir_.resolve(strClassName + "_picture" + id + ".jpg");
	}
	
	/**
	 * This method returns true if the specified Video has binary
	 * data stored on the file system.
	 * 
	 * @param v
	 * @return
	 */
	public boolean hasPictureData(long id, String strClassName){
		Path source = getPicturePath(id, strClassName);
		return Files.exists(source);
	}
	
	/**
	 * This method copies the binary data for the given video to
	 * the provided output stream. The caller is responsible for
	 * ensuring that the specified Video has binary data associated
	 * with it. If not, this method will throw a FileNotFoundException.
	 * 
	 * @param v 
	 * @param out
	 * @throws IOException 
	 */
	public void copyPictureData(long id, String strClassName, OutputStream out) throws IOException {
		Path source = getPicturePath(id, strClassName);
		if(!Files.exists(source)){
			throw new FileNotFoundException("Unable to find the referenced picture file for Picture: " + id);
		}
		Files.copy(source, out);
	}
	
	/**
	 * This method reads all of the data in the provided InputStream and stores
	 * it on the file system. The data is associated with the Picture object that
	 * is provided by the caller.
	 * 
	 * @param v
	 * @param pictureData
	 * @throws IOException
	 */
	public void savePictureData(long id, String strClassName, InputStream pictureData) throws IOException{
		assert(pictureData != null);
		
		Path target = getPicturePath(id, strClassName);
		Files.copy(pictureData, target, StandardCopyOption.REPLACE_EXISTING);
	}
	
}
