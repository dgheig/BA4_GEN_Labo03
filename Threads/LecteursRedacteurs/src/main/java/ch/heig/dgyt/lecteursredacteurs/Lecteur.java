package ch.heig.dgyt.lecteursredacteurs;

public class Lecteur {
    private Thread thread;
    private Controleur controleur;
    Lecteur(Controleur controleur) {
        Lecteur lecteur = this;
        this.controleur = controleur;
        thread = new Thread() {
            @Override
            public synchronized void run() {
                while (!controleur.read(lecteur)) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while(controleur.isAccessing(lecteur));
            }
        };
        thread.setPriority(Thread.MIN_PRIORITY);
    }

    public synchronized void startRead() {
        thread.start();
    }

    public synchronized void stopRead() {
        this.controleur.close(this);
    }

    public synchronized boolean isWaiting() {
        return thread.getState() == Thread.State.WAITING;
    }
}
