package com.company;

import webscraper.Member;
import java.util.*;

public class StorageTester {
    public static void main(String[] args) {
        Storage storage = new Storage("https://it.wikipedia.org/wiki/Imperatori_romani");
        System.out.println(storage.getDynasties());
        try {
            ArrayList<Member> m = storage.getTree("Dinastia giulioclaudia");
            System.out.println(m);
        }finally {
            storage.close();
        }
    }
}
