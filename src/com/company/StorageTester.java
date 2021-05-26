package com.company;

import webscraper.Member;
import java.util.*;

public class StorageTester {
    public static void main(String[] args) {
        Storage storage = new Storage("https://it.wikipedia.org/wiki/Imperatori_romani");
        System.out.println(storage.getDynasties());
        try {
            System.out.println("oraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            ArrayList<Member> m = storage.getTree("Gens Iulia");
            System.out.println(m.size());
        }finally {
            storage.close();
        }
    }
}
