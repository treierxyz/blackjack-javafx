package xyz.treier.blackjackjavafx;

import xyz.treier.blackjackjavafx.kontrollerid.LõppKontroller;
import xyz.treier.blackjackjavafx.kontrollerid.MängKontroller;

import java.util.*;


public class Mäng {
    private MängKontroller mängKontroller;
    private Queue<Mängija> mängijad;


    public Mäng(MängKontroller mängKontroller) throws MängijadOtsasErind {
        this.mängKontroller = mängKontroller;
        mängijad = new LinkedList<>();
        lisaOsalejad(this.mängKontroller.getMängijad(), new ArrayList<>());
    }

    public void alusta() {
        List<Mängija> lõpetanudList = new ArrayList<>();
        mängKontroller.setLõpetanudList(lõpetanudList);

        järgmineMängija();
    }

    public void järgmineMängija() {
        Mängija järgmine;

        if ((järgmine = mängijad.poll()) == null) {
            try {
                lisaOsalejad(mängKontroller.getMängijad(), mängKontroller.getLõpetanudList());
                järgmine = mängijad.poll();
            } catch (MängijadOtsasErind e) {
                LõppKontroller lõppKontroller = VaateVahetaja.vaheta(Vaade.LÕPP);
                lõppKontroller.setMängijadList(mängKontroller.getMängijad());
                lõppKontroller.lõpuEdetabel();

                System.out.println("Mängijad otsas");
                return;
            }
        }

        järgmine.setSeis(MängijaSeis.MÄNGIB);
        mängKontroller.setKelleKäik(järgmine);
    }

    public void lisaOsalejad(List<Mängija> mängijad, List<Mängija> lõpetanud) throws MängijadOtsasErind {
        if (lõpetanud.size() == mängijad.size()) {
            throw new MängijadOtsasErind();
        }

        for (Mängija m : mängijad) {
            if (lõpetanud.contains(m)) continue;
            this.mängijad.add(m);
        }
    }
}
