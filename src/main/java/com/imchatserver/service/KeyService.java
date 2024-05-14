package com.imchatserver.service;

public interface KeyService {
    String generate(String jobno, String Ka);
    String getkey(String jobno);
}
