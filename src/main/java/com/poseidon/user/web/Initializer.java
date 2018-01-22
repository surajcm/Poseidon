package com.poseidon.user.web;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Suraj Muraleedharan
 * on 1/9/17.
 */
@Component
public class Initializer {

    @PostConstruct
    public void init() {
        System.setProperty("java.awt.headless","false");
        DatabaseManagerSwing.main(
                new String[]{"--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", ""});
    }

}
