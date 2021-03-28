package ch.heig.dgyt.lecteursredacteurs;


import java.util.HashSet;
import java.util.Set;

public class Controleur {
    private Set<Lecteur> lecteurs = new HashSet<>();
    private Redacteur redacteur;
    private Object readLock = new Object();
    private Object writeLock = new Object();

    boolean read(Lecteur lecteur) {
        if(lecteur == null)
            return false;
        synchronized (readLock) {
            while (this.redacteur != null)
                try {
                    readLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            lecteurs.add(lecteur);
        }
        return true;
    }

    boolean write(Redacteur redacteur) {
        if (redacteur == null)
            return false;
        synchronized (writeLock) {
            while (this.redacteur != redacteur || !lecteurs.isEmpty()) {
                if (this.redacteur == null)
                    this.redacteur = redacteur;
                try {
                    writeLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    synchronized boolean isAccessing(Lecteur lecteur) {
        return lecteurs.contains(lecteur);
    }
    synchronized boolean isAccessing(Redacteur redacteur) {
        return redacteur != null && lecteurs.size() == 0 && this.redacteur == redacteur;
    }

    synchronized void close(Lecteur lecteur) {
        if(lecteurs.remove(lecteur) && lecteurs.size() == 0) {
            notifyLecteursRedacteurs();
        }
    }
    synchronized void close(Redacteur redacteur) {
        if(this.redacteur != null && this.redacteur == redacteur) {
            this.redacteur = null;
            notifyLecteursRedacteurs();
        }
    }

    private synchronized void notifyLecteursRedacteurs() {
        synchronized (writeLock) {
            writeLock.notifyAll();
        }
        sleep();
        synchronized (readLock) {
            readLock.notifyAll();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
