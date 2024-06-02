import java.util.Scanner;
import java.io.*;

import static Tools.Utility.*;

public class Main {
    public static void main(String[] args) {
        // Inizializzazione delle variabili
        int contacontatti = 0;

        final int nContratti = 3;
        boolean uscita = false;
        Scanner tastiera = new Scanner(System.in);
        Persona[] gestore = new Persona[nContratti];
        String[] opzioni = {"Gestore telefonico", "1 - Inserimento", "2 - Visualizza", "3 - Ricerca", "4 - Modifica", "5 - Cancella", "6 - Telefona", "7 - Copia", "8 - Ordina","9 - Salva numero contatti", "10 - Carica file", "11 - Esci"};
        // Menu principale
        do {

            switch (menu(opzioni, tastiera)) {
                case 1:
                    // Inserimento di un nuovo contatto
                    if (contacontatti < nContratti) {
                        Persona NuovoContatto = LeggiContatto(true, tastiera);
                        if (!Presente(gestore, NuovoContatto, contacontatti)) {
                            gestore[contacontatti] = NuovoContatto;
                            contacontatti++;
                        } else {
                            System.out.println("Il contatto è già presente nella collezione.");
                        }
                    } else {
                        System.out.println("Non ci sono più contratti da vendere");
                    }
                    break;
                case 2:
                    // Visualizzazione dei contatti
                    if (contacontatti > 0) {
                        Visualizza(gestore, contacontatti);
                    } else {
                        System.out.println("Non ci sono contatti da visualizzare");
                    }
                    break;
                case 3:
                    // Verifico presenza di un contatto
                    if (contacontatti > 0) {
                        Persona Supporto = LeggiContatto(false, tastiera);
                        if (Presente(gestore, Supporto, contacontatti)) {
                            System.out.println("e' presente");

                        } else {
                            System.out.println("Non e' presente");
                        }
                    } else {
                        System.out.println("Non ci sono contatti da visualizzare");
                    }
                    break;
                case 4:
                    // Modifica numero di telefono
                    if (contacontatti > 0) {
                        Persona Supporto = LeggiContatto(false, tastiera);
                        int posizione = posNumero(gestore, Supporto, contacontatti);
                        if (posizione != -1) {
                            System.out.println("Inserisci il numero di telefono");
                            gestore[posizione].numDiTelefono = tastiera.nextLine();

                        } else {
                            System.out.println("Non è presente");
                        }

                    } else {
                        System.out.println("Non ci sono contatti da visualizzare");
                    }
                    break;

                case 5:
                    // Cancellazione di un contatto
                    if (contacontatti > 0) {
                        Persona Supporto = LeggiContatto(false, tastiera);
                        int posizione = posNumero(gestore, Supporto, contacontatti);
                        if (posizione != -1) {
                            contacontatti = cancellaContatto(gestore, posizione, contacontatti);

                        } else {
                            System.out.println("Non è presente");
                        }

                    } else {
                        System.out.println("Non ci sono contatti da visualizzare");
                    }
                    break;
                case 6:
                    // Effettua la chiamata
                    if (contacontatti > 0) {
                        Persona Supporto = LeggiContatto(false, tastiera);
                        int posizione = posNumero(gestore, Supporto, contacontatti);
                        if (posizione != -1) {
                            System.out.println("Inserisci il numero di telefono");
                            gestore[posizione].numDiTelefono = tastiera.nextLine();
                        } else {
                            System.out.println("Non è presente");
                        }
                    }
                    break;
                case 7:
                    // Copia di un contatto
                    Persona[] copia = Copia(gestore);
                    for (int i = 0; i < copia.length; i++) {
                        Visualizza(copia, copia.length);
                    }
                    break;
                case 8:
                    if (contacontatti > 0) {
                        OrdinaSelezione(gestore, contacontatti);
                        Visualizza(gestore, contacontatti);
                    }
                    break;
                case 9:
                    try {
                        // scriviFile("Archivio.csv", gestore, contacontatti);
                        scriviNContatti("Archivio.csv", gestore, contacontatti);
                    } catch (IOException x) {
                        System.out.println("Errore: " + x.toString());
                    }
                    System.out.println("Scrittura fatta");
                    break;
                case 10:
                    try {
                        // contacontatti = leggiFile("Archivio.csv", gestore);
                        gestore = leggiNContatti("Archivio.csv");
                        contacontatti = gestore.length;
                    } catch (IOException x) {
                        System.out.println("Errore: " + x.toString());
                    }
                    System.out.println("Lettura fatta");
                    break;
                case 11:
                    uscita = true;
                    break;
                default:
                    System.out.println("Inserita opzione non valida. Riprova");
                    break;
            }
        } while (!uscita);
        System.out.println("Fine programma");
    }

    public static Persona LeggiContatto(boolean telSi, Scanner tastiera) {
        Persona contatto = new Persona();


        System.out.println("Inserici il nome");
        contatto.nome = tastiera.nextLine();
        System.out.println("Inserici il cognome");
        contatto.cognome = tastiera.nextLine();
        if (telSi) {
            System.out.println("Inserici il numero di telefono");
            contatto.numDiTelefono = tastiera.nextLine();
            String[] opzioni = {"TipoAbbonamento", "1-cellulare", "2-abitazione", "3-azienda"};
            int scelta;
            do {
                scelta = menu(opzioni, tastiera);
                switch (scelta) {
                    case 1 -> contatto.tipo = Tipologia.cellulare;
                    case 2 -> contatto.tipo = Tipologia.abitazione;
                    case 3 -> contatto.tipo = Tipologia.azienda;
                }

            } while (scelta > 3 || scelta < 1);
        }

        return contatto;
    }

    public static void Visualizza(Persona gestore[], int contacontatti) {
        for (int i = 0; i < contacontatti; i++) {
            System.out.println(gestore[i].anagrafica());
        }
    }

    public static boolean Presente(Persona[] gestore, Persona NuovoContatto, int contacontatti) {

        for (int i = 0; i < contacontatti; i++) {
            if ((gestore[i].nome.equals(NuovoContatto.nome)) && (gestore[i].cognome.equals(NuovoContatto.cognome))) {
                return true;
            }

        }
        return false;
    }

    public static int posNumero(Persona[] gestore, Persona NuovoContatto, int contacontatti) {
        for (int i = 0; i < contacontatti; i++) {
            if ((gestore[i].nome.equals(NuovoContatto.nome)) && (gestore[i].cognome.equals(NuovoContatto.cognome))) {
                return i;
            }
        }
        return -1;
    }

    public static int cancellaContatto(Persona[] gestore, int posizione, int contacontatti) {

        if (posizione != gestore.length) {
            for (int i = posizione; i < contacontatti - 1; i++)
                gestore[i] = gestore[i + 1];
        }
        return --contacontatti;
    }

    public static boolean Telefona(Persona x) {
        if (x.saldo >= 5) {
            x.saldo -= 5;
            return true;
        }
        return false;
    }

    public static Persona[] Copia(Persona[] gestore) {
        Persona[] copia = new Persona[gestore.length];
        for (int i = 0; i < gestore.length; i++) {
            for (int j = 0; j < gestore.length; j++) {
                copia[i] = gestore[i];
            }
        }
        return copia;
    }

    public static void OrdinaSelezione(Persona[] gestore, int contacontatti) {
        for (int i = 0; i < contacontatti - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < contacontatti; j++) {
                if (gestore[j].nome.compareTo(gestore[minIndex].nome) < 0 || (gestore[j].nome.compareTo(gestore[minIndex].nome) == 0 && (gestore[j].cognome.compareTo(gestore[minIndex].cognome)) < 0)) {
                    minIndex = j;
                }
            }
            Persona temp = gestore[minIndex];
            gestore[minIndex] = gestore[i];
            gestore[i] = temp;
        }
    }

    public static void scriviNContatti(String nomeFile, Persona[] persone, int nContatti) throws IOException {
        FileWriter scrittore = new FileWriter(nomeFile);
        Integer box = nContatti;
        scrittore.write(box.toString() + "\r\n");
        for (int i = 0; i < nContatti; i++) {
            scrittore.write(persone[i].toString() + "\r\n");
        }
        scrittore.flush();
        scrittore.close();
    }

    public static void scriviFile(String nomeFile, Persona[] persone, int nContatti) throws IOException {
        FileWriter scrittore = new FileWriter(nomeFile);
        for (int i = 0; i < nContatti; i++) {
            scrittore.write(persone[i].toString() + "\r\n");
        }
        scrittore.flush();
        scrittore.close();
    }

    public static Persona[] leggiNContatti(String nomeFile) throws IOException {
        FileReader lettore = new FileReader(nomeFile);
        Scanner input = new Scanner(lettore);
        String dato = input.nextLine();
        String[] dati;
        Persona[] nuovaRubrica = new Persona[Integer.parseInt(dato)];
        int contaLinee = 0;
        while (input.hasNextLine() && contaLinee < nuovaRubrica.length) {
            dati = input.nextLine().split(",");
            Persona p = new Persona();
            p.nome = dati[0];
            p.cognome = dati[1];
            p.numDiTelefono = dati[2];
            switch (dati[3]) {
                case "cellulare":
                    p.tipo = Tipologia.cellulare;
                    break;
                case "abitazione":
                    p.tipo = Tipologia.abitazione;
                    break;
                case "azienda":
                    p.tipo = Tipologia.azienda;
                    break;
            }
            nuovaRubrica[contaLinee] = p;
            contaLinee++;
        }
        return nuovaRubrica;
    }

    public static int leggiFile(String nomeFile, Persona[] persone) throws IOException {
        FileReader lettore = new FileReader(nomeFile);
        Scanner input = new Scanner(lettore);
        String[] dati;
        int contaLinee = 0;
        while (input.hasNextLine() && contaLinee < persone.length) {
            dati = input.nextLine().split(",");
            Persona p = new Persona();
            p.nome = dati[0];
            p.cognome = dati[1];
            p.numDiTelefono = dati[2];
            switch (dati[3]) {
                case "cellulare":
                    p.tipo = Tipologia.cellulare;
                    break;
                case "abitazione":
                    p.tipo = Tipologia.abitazione;
                    break;
                case "azienda":
                    p.tipo = Tipologia.azienda;
                    break;
            }
            persone[contaLinee] = p;
            contaLinee++;
        }
        return contaLinee;
    }
}