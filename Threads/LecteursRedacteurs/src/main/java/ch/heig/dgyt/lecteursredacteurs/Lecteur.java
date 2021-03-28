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
                synchronized (reading) {
                    reading = true;
                }
                while(controleur.isAccessing(lecteur));
                synchronized (reading) {
                    reading = false;
                }
            }
        };
    }

    public synchronized void startRead() {
        thread.start();
        System.out.println("setup: " + this);
        while(!reading && thread.getState() == Thread.State.RUNNABLE);
    }

    public synchronized void stopRead() {
        this.controleur.close(this);
        while(reading);
    }

    public synchronized boolean isWaiting() {
        System.out.println(this + ": " + thread.getState());
        return thread.getState() != Thread.State.RUNNABLE;
    }
}
