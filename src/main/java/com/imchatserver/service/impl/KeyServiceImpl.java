package com.imchatserver.service.impl;

import com.imchatserver.service.KeyService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.codec.digest.DigestUtils;

@Service
public class KeyServiceImpl implements KeyService {
    BigInteger G = new BigInteger("81339"), P = new BigInteger("1000000001339");
    int eb;
    ConcurrentHashMap<String, String> keyset = new ConcurrentHashMap<>();
    @Override
    public String generate(String jobno, String Ka) {
        eb = (int) Math.floor(Math.random() * 900 + 100);
        BigInteger Kb = G.pow(eb).mod(P);
        if (keyset.containsKey(jobno)) keyset.remove(jobno);
        keyset.put(jobno, DigestUtils.md5Hex(String.valueOf(new BigInteger(Ka).pow(eb).mod(P))));
        System.out.println(keyset.get(jobno));
        return String.valueOf(Kb);
    }

    @Override
    public String getkey(String jobno) {
        return keyset.get(jobno);
    }
}
