package main.java.cs601.project4;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/*
 * This class is to create salt and Hash for new User
 * to validate the login credentials for existing users.
 * 
 * Note: Salting and Hashing Code is from the following link discussed in the class
 * Link : https://www.baeldung.com/java-password-hashing
 */

public class SaltingandHashing {

	public SaltingandHashing() {
		// TODO Auto-generated constructor stub
	}

	public byte[] createSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}

	/*
	 * PBKDF2 algorithm is used for Hashing
	 */
	public byte[] createHash(String password, byte[] salt) throws Exception {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = f.generateSecret(spec).getEncoded();
		return hash;
	}

	/*
	 * method to validate the user credentials
	 */
	public boolean validateLogin(String userPwd, byte[] dbPwd, byte[] salt) throws Exception {

		byte[] tempPwd = createHash(userPwd, salt);
		System.out.println("temp password : " + tempPwd);

		return Arrays.equals(tempPwd, dbPwd);
	}
}
