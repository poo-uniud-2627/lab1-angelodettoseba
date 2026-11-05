// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 1: il Punteggio. Scadenza: domenica 25 ottobre 2026.
package pong;

import java.util.ArrayList;
import java.util.List;

/**
 * I conti di una partita: chi ha segnato, quanti punti ha ciascun lato, chi ha vinto.
 * La Partita gli dice chi ha fatto punto; lo Schermo chiede i punti alla Partita, che li chiede a lui.
 *
 * La regola per vincere: un lato vince quando ha almeno {@code puntiPerVincere} punti e almeno
 * {@code scartoMinimo} punti più dell'altro. Esempi, con 11 punti e scarto 2: 11 a 9 vince;
 * 11 a 10 si continua; 12 a 10 vince. Con scarto 1 vince chi arriva per primo a 11.
 *
 * IL PEZZO MANCANTE: i corpi dei metodi qui sotto (e i campi che vi servono).
 * Ogni metodo ha il suo contratto, nel commento sopra: cosa pretende, cosa promette.
 * I test ufficiali (cartella test) controllano i contratti.
 */
public class Punteggio {
    private int PunteggioSinistra = 0;
    private int PunteggioDestra = 0;
    private final int puntiPerVincere;
    private final int scartoMinimo;
    private final List<Lato> storia = new ArrayList<>();
    // scrivete qui i campi che vi servono

    /**
     * Una partita nuova, 0 a 0, con la sua regola per vincere.
     * Pretende: puntiPerVincere è almeno 1; scartoMinimo è almeno 1.
     * Promette: una partita 0 a 0, non finita, senza vincitore e con la storia vuota.
     */
    public Punteggio(int puntiPerVincere, int scartoMinimo) {
        if (puntiPerVincere < 1 || scartoMinimo < 1){
            throw new IllegalArgumentException("puntiPerVincere e scartoMinimo >= 1");
        }
        this.puntiPerVincere = puntiPerVincere;
        this.scartoMinimo = scartoMinimo;
        azzera();
    }

    /**
     * Il lato chi ha fatto un punto.
     * Pretende: chi non è null.
     * Promette: se la partita non era finita, il lato chi ha un punto in più, l'altro lato non
     *           cambia e il punto si aggiunge in fondo alla storia; se con questo punto il lato chi
     *           vince (regola in cima alla classe), la partita è finita e il vincitore è chi.
     *           Se la partita era già finita, non cambia niente.
     */
    public void segnaPunto(Lato chi) {
        if(chi == null){
            throw new IllegalArgumentException("Non può essere null");
        }
        if(isFinita()){
            return;
        }
        if (chi == Lato.SINISTRA) {
            PunteggioSinistra++;
        } else{
            PunteggioDestra++;
        }
        storia.add(chi);
    }

    /**
     * Pretende: chi non è null.
     * Promette: i punti del lato chi. Non cambia niente.
     */
    public int getPunti(Lato chi) {
        if(chi == null){
            throw new IllegalArgumentException("Non può esssere nullo");
        }
        if (chi == Lato.SINISTRA) {
            return PunteggioSinistra;
        } else{
            return PunteggioDestra;
        }
    }

    /**
     * Promette: vero se la partita è finita, cioè se un lato ha vinto. Non cambia niente.
     */
    public boolean isFinita() {
        return getVincitore() != null;
    }

    /**
     * Promette: il lato che ha vinto; null se la partita non è finita. Non cambia niente.
     */
    public Lato getVincitore() {
        int diffsinistra = PunteggioSinistra - PunteggioDestra; //differenza dei punti di sinistra da quelli di destra
        int diffdestra = PunteggioDestra - PunteggioSinistra; //differenza dei punti di destra da quelli di sinistra
        if(PunteggioSinistra >= puntiPerVincere && diffsinistra >= scartoMinimo){
            return Lato.SINISTRA;
        }
        if(PunteggioDestra >= puntiPerVincere && diffdestra >= scartoMinimo){
            return Lato.DESTRA;
        }
        return null;
    }

    /**
     * L'arbitro ci ripensa: toglie l'ultimo punto della storia (l'ultimo segnato e non già annullato).
     * Pretende: niente.
     * Promette: se nella storia c'è almeno un punto, l'ultimo punto viene tolto, dai conti e dalla
     *           storia, come se non fosse mai stato segnato. Se era il punto che aveva chiuso la
     *           partita, la partita torna in corso, senza vincitore. Se la storia è vuota, non cambia
     *           niente.
     */
    public void annullaUltimoPunto() {
        if(!storia.isEmpty()){
            Lato ultimo = storia.remove(storia.size() - 1);
            if(ultimo == Lato.SINISTRA){
                PunteggioSinistra--;
            } else{
                PunteggioDestra--;
            }
        }
    }

    /**
     * La storia dei punti, dal primo all'ultimo: per ogni punto segnato (e non annullato), il lato
     * che l'ha segnato.
     * Pretende: niente.
     * Promette: una lista con i punti in ordine. Chi riceve la lista non può cambiare il Punteggio
     *           attraverso di lei: aggiungere, togliere o cambiare elementi della lista ricevuta
     *           (se la lista lo permette) non cambia i punti, il vincitore né la storia del Punteggio.
     *           Non promette altro: la lista ricevuta può restare com'era o mostrare anche i punti
     *           segnati dopo; chi la usa non deve contare né sull'una né sull'altra cosa.
     */
    public List<Lato> getStoria() {
        return new ArrayList<>(storia);
    }

    /**
     * Una partita nuova con la stessa regola.
     * Pretende: niente.
     * Promette: 0 a 0, non finita, senza vincitore, storia vuota; la regola per vincere non cambia.
     */
    public void azzera() {
        PunteggioDestra = 0;
        PunteggioSinistra = 0;
        storia.clear();
    }

    /**
     * 
     * Lista della storia non nulla
     * Conteggio PuntiDestra e PuntiSinistra devo corrispondere al numero di occorrenze del lato presente nella storia
     * Nessun punto puo comparire nella sotria post fine gioco
     * 
     */
    public boolean repOK(){
       
        if(storia == null){
            return false;
        }

        int sx = 0;
        int dx = 0;

        for(Lato l : storia){
            if(l == null){
                return false;
            }
            if (l == Lato.SINISTRA){
                sx++;
            } else if (l == Lato.DESTRA){
                dx++;
            }

            boolean vince_sx = sx >= puntiPerVincere && sx-dx >= scartoMinimo;
            boolean vince_dx = dx >= puntiPerVincere && dx-sx >= scartoMinimo;

            boolean finita_postpunto = vince_dx || vince_sx ;

            //se sx + dx < storia -> punti aggiunti post vittoria
            if(finita_postpunto && (sx + dx) < storia.size()){
                return false;
            }
        }

        return sx == PunteggioSinistra && dx == PunteggioDestra;
    }
}
