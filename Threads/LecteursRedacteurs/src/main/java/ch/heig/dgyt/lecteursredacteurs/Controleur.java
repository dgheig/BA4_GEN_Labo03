package ch.heig.dgyt.lecteursredacteurs;


import java.util.HashSet;
import java.util.Set;

public class Controleur {
    private Set<Lecteur> lecteurs = new HashSet<>();
    private Redacteur redacteur;

    synchronized boolean read(Lecteur lecteur) {
        if(lecteur == null || redacteur != null)
            return false;
        lecteurs.add(lecteur);
        return true;
    }

    synchronized boolean write(Redacteur redacteur) {
        if(this.redacteur != null || redacteur == null)
            return false;
        this.redacteur = redacteur;
        return lecteurs.isEmpty();
    }

    synchronized boolean isAccessing(Lecteur lecteur) {
        return lecteurs.contains(lecteur);
    }
    synchronized boolean isAccessing(Redacteur redacteur) {
        return redacteur != null && lecteurs.size() == 0 && this.redacteur == redacteur;
    }

    synchronized void close(Lecteur lecteur) {
        if(lecteurs.remove(lecteur) && lecteurs.size() == 0) {
            this.redacteur = null;
            this.notifyAll();
        }
    }
    synchronized void close(Redacteur redacteur) {
        if(this.redacteur != null && this.redacteur == redacteur) {
            this.redacteur = null;
            this.notifyAll();
        }
    }


}
