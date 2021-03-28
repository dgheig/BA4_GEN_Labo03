package ch.heig.dgyt.lecteursredacteurs;

public class Redacteur {
    private Thread thread;
    private Controleur controleur;
    Redacteur(Controleur controleur) {
        Redacteur redacteur = this;
        this.controleur = controleur;
        thread = new Thread() {
            @Override
            public void run() {
                while (!controleur.write(redacteur));
                while(controleur.isAccessing(redacteur));
            }
        };
    }

    public synchronized void startWrite() {
        thread.start();
    }

    public synchronized void stopWrite() {
        synchronized (this.controleur) {
            this.controleur.close(this);
        }
    }

    public synchronized boolean isWaiting() {
        System.out.println(this + ": " + thread.getState());
        return thread.getState() != Thread.State.RUNNABLE;
    }
}
