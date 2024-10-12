package com.library.online_library_spring_app;

import com.library.online_library_spring_app.service.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.rmi.ServerError;
import java.security.*;
import java.util.Base64;

@SpringBootApplication
public class OnlineLibrarySpringAppApplication
//		implements CommandLineRunner
{


	public static void main(String[] args) {
		SpringApplication.run(OnlineLibrarySpringAppApplication.class, args);
	}
//	@Override
//	public void run(String... args) throws Exception {
//		System.err.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//		UsersService.getAllUsers();
//	}

//	@Override
//	public void run(String... args) throws Exception {
//		{KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
//			keyGenerator.initialize(1024);
//			KeyPair kp = keyGenerator.genKeyPair();
//			PublicKey publicKey = kp.getPublic();
//			PrivateKey privateKey = kp.getPrivate();
//
//			String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
//			String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
//
//			System.out.println(convertToPublicKey(encodedPublicKey));
//
//			System.out.println();
//
//			System.out.println(convertToPrivateKey(encodedPrivateKey));
//
//		}
//
//		private static String convertToPrivateKey(String key) {
//			StringBuilder result = new StringBuilder();
//			result.append("-----BEGIN PRIVATE KEY-----\n");
//			result.append(key);
//			result.append("\n-----END PRIVATE KEY-----");
//			return result.toString();
//		}
//
//		private static String convertToPublicKey(String key) {
//			StringBuilder result = new StringBuilder();
//			result.append("-----BEGIN PUBLIC KEY-----\n");
//			result.append(key);
//			result.append("\n-----END PUBLIC KEY-----");
//			return result.toString();
//		}
//	}
//
//	private boolean convertToPublicKey(String encodedPublicKey) {
//	}
//}
}
