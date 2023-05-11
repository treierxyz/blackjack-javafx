# blackjack-javafx
Blackjack kaardimäng JavaFX teegiga

## Autorite nimed
Romek Hanson, Oliver Jõgar

## Projekti põhjalik kirjeldus, kus on kirjas programmi eesmärk ja selgitus programmi üldisest tööst, vajadusel lühike kasutusjuhis
Programmi eesmärk on pakkuda kasutajale Blackjack kaardimängu elamust graafilise kasutajaliidesega. Mängida saab kuni 8 mängijaga korraga. Mängu peamenüüs on ka lühike reeglite ülevaade.
## Iga klassi kohta eraldi selle eesmärk ja olulisemad meetodid
- Main - programmi käivituspunkt
- VaateVahetaja - Võimaldab vahetada mugavalt FXML stseenide vahel, tähtsaim meetod on vaheta()
- Vaade - enum, sisaldab kõiki programmi vaateid ja neile vastavate FXML failide asukohti
- Kaart - mängukaardi klass, mis hoiab endas kaardi masti ja väärtust. Tähtsaimad meetodid on getVaarusArv() ja kaartLabel()
- Mast - enum, hoiab kaartide maste ja nende sümboleid
- Väärtus - enum, hoiab kõiki kaartide väärtusi
- Mängija - hoiab mängijale omast infot, nagu näiteks nende seis, krediit ja praegune käsi. Tähtsaimad meetodid on lisaKrediit() ja getKäsi()
- Käsi - sisaldab kaarte. Tähtsaim meetod on summa()
- MängijaSeis - enum, hoiab kõiki mängija seise ja seisuga vastavaid läbipaistvuse väärtusi, mida kasutatakse mängus mängijate korra näitamiseks
- MängijaSeisProperty - kasutatakse mängija seisu vaadeldavaks tegemiseks
- Kaardipakk - hoiab mängus kasutatud kaarte. Tähtsaim meetod on suvaline()
- Mäng - sisaldab osa mängu loogikast.
- AbiKontroller - kontroller abi stseeni jaoks. Tähtsaim meetod on initialize()
- LõppKontoller - kontroller lõpp stseeni jaoks. Tähtsaimad meetodid on lõpuTabel() ja jätka()
- MängijateNimedKontroller - kontroller mängijate nimede sisestamise stseeni jaoks. Tähtsaimad meetodid on genereeriTekstiväljad() ja edasi()
- MängKontroller - kontroller mängu peamise stseeni jaoks, sisaldab suurema osa mängu loogikast. Tähtsaimad meetodid on edetabel(), küsiPanuseid(), jagaKaardid(), mängijadInit(), standNupp(), hitNupp() ja doubleNupp()
- PeamenüüKontroller - kontroller peamenüü jaoks. Tähtsaim meetod on mängijaid()
## Projekti tegemise protsessi kirjeldus (erinevad etapid ja rühmaliikmete osalemine neis)
Alguses arutasime millist GUI raamistikku kasutada ning otsustasime valida JavaFX. Tahtsime proovida liidest disainida FXMLis, mille jaoks on mugav programm nimega Scene Builder. Niimoodi saime vähendada klasside ja koodi kogust mida oleks olnud vaja liidese kujunduse valmistamiseks.
Seejärel taaskasutasime eelmisest projektist, mis oli tekstipõhine Blackjack kaardimäng, klasse ja mõnda meetodit, kuna see tundus loogiline.
Mõned meetodid said asendatud või täiendatud selleks, et nad oleksid kasutatavad graafilise kasutajaliidesega. Iga FXML stseeni jaoks oli vajalik valmistada kontrolleri klass, et oleks võimalik kasutaja sisendit töödelda.
Proovisime ka kasutada rohkem enumme, mis oli edukas otsus.
## Iga rühmaliikme panus (sh tehtud klassid/meetodid) ja ajakulu (orienteeruvalt)
Meetodeid on liiga palju, et iga liikme panused ükshaaval välja tuua tekstis. Projekti hallati Git versioonihaldus süsteemiga, mis on projekti tihendkaustaga kaasas. Sealt on võimalik näha täpselt kes millele panustas. Romek Hansoni panused on tehtud kasutaja "romekh" ja Oliver Jõgari panused kasutaja "treierxyz" kaudu.
## Tegemise mured (nt millistest teadmistest/oskustest tundsite projekti tegemisel puudust)
Kõige rohkem muret tekitas alguses FXML ja selle ühendamine koodiga. Saime teada, et lihtsaim viis dünaamilisi elemente lisada liidesesse on neid ikkagi koodi kaudu genereerida. Seetõttu on programmi staatilised graafilised elemendid tehtud FXMLis ning dünaamilised Java koodis.
Projekt muutus ka lõpus veidi keerulisemaks kui lootsime ning tekkis raskusi leida kus millist meetodit või muutujat kasutatakse. Arvatavasti on mõned kasutamata meetodid veel projektis alles selle tõttu.
## Hinnang oma töö lõpptulemusele (millega saite hästi hakkama ja mis vajab arendamist)
Oleme lõpptulemusega üsnagi rahul. Saime hästi hakkama FXMLis kasutajaliidese disainimise ja selle ühendamisega Java koodi. Enumide kasutamine oli ka hästi läinud. Klassid ja meetodid on aga veidi segased ja oleks võimalik koodi lihtsustada kui oleks rohkem aega. Nagu ka eelmises mängu iteratsioonis, jäi implementeerimata Split ja Surrender funktsioonid. 
Oleks ka võimalik lisada rohkem funktsionaalsust, nagu näiteks kindlustus ja tasaraha. 
## Selgitus ja/või näited, kuidas programmi osi eraldi ja programmi tervikuna testisite ehk kuidas veendusite, et programm töötab korrektselt.
Proovisime rakendust kasutada nagu tavakasutaja ning probleemide tekkimisel said need parandatud. Erilisi testimismeetodeid me ei kasutanud.