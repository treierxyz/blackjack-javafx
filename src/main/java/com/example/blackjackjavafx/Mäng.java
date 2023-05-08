package com.example.blackjackjavafx;

import com.example.blackjackjavafx.kontrollerid.MängKontroller;

import java.util.ArrayList;
import java.util.List;


public class Mäng {
    private MängKontroller mängKontroller;


    public Mäng(MängKontroller mängKontroller){
        this.mängKontroller = mängKontroller;
    }

    public void alusta() {
        List<Mängija> lõpetanudList = new ArrayList<>();
        mängKontroller.setLõpetanudList(lõpetanudList);

        // testiks esimene
        Mängija mängija = mängKontroller.getMängijad().get(0);
        mängKontroller.setKelleKäik(mängija);

        mängija.getMängijaHbox().getParent().setOpacity(1.0); // Käigu alguses mustaks
        // mängija.getMängijaHbox().getParent().setOpacity(0.6); // Käigu lõpus tagasi halliks
    }
}
