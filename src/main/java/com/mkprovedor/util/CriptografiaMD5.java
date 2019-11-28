package com.mkprovedor.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class CriptografiaMD5 {

	public static String senhaMD5(String senha) {
		PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder.encodePassword(senha, null);
	}

}
