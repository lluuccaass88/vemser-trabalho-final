package br.com.logisticadbc;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class main {
    public static void main(String[] args) {
        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder(16384, 8, 1, 32, 64);
        CharSequence senha = "123";
        senha = sCryptPasswordEncoder.encode(senha);

        System.out.println(senha);
    }
}

