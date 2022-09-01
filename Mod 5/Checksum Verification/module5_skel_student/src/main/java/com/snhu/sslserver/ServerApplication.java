package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}

@RestController
class ServerController{
	
	// Define a method to calculate the hash utilizing the SHA-256 Algorithm
	public static String calcHash(String dataString) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256"); // Creates a MessageDigest Object initialized with the SHA-256 cipher
		byte[] digest = messageDigest.digest(dataString.getBytes(Charset.forName("UTF-8"))); // Generates the hash value using the digest() method
		
		String hexValue = bytesToHex(digest); // Converts bytes to a hex string
		
		return hexValue; // Returns the value of the digest as a hex string
	}
	
	// Define a method for converting bytes to hex 
    private static String bytesToHex(byte[] digest) {
        StringBuilder builder = new StringBuilder();
        
        // Iterate through the byte array and convert the byte to a hex value
        for (byte hash : digest) {
            int intValue = 0xff & hash;
            if (intValue < 0x10) {
            	builder.append('0');
            }
            builder.append(Integer.toHexString(intValue)); // Append the value to a string builder
        }
        return builder.toString(); // Return a string value of the hex
	}

	@RequestMapping("/hash")
    public String myHash() throws NoSuchAlgorithmException{
    	String data = "Brandon Coulter"; // Variable to store the data to be hashed
    	String hashNum = calcHash(data); // Variable to store the checksum value
       
        return "<p>data:" + data + " : SHA-256 : CheckSum Value : " + hashNum; // Prints out the data and the checksum value
    }
}
