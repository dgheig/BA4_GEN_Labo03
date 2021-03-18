package ch.heig.dgyt.lecteursredacteurs;


public class Lecteur {
    private Thread thread;
    private Controleur controleur;
    public Boolean reading = false;
    Lecteur(Controleur controleur) {
        Lecteur lecteur = this;
        this.controleur = controleur;
        thread = new Thread() {
            @Override
            public void run() {
                while (!controleur.read(lecteur)) ;
                reading = true;
                while(controleur.isAccessing(lecteur));
                reading = false;
            }
        };
    }

    public synchronized void startRead() {
        reading = false;
        thread.start();
        // We know that we either will have read access (=> reading == true) or the thread will change its state
        while(reading == false && thread.getState() == Thread.State.RUNNABLE);
    }

    public synchronized void stopRead() {
        this.controleur.close(this);
        while(reading);
    }

    public synchronized boolean isWaiting() {
        while(reading == false && thread.getState() == Thread.State.RUNNABLE);
        return reading == false;
    }
}
