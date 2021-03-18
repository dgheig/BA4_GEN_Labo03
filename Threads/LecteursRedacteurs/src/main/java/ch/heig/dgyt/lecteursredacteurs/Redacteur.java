package ch.heig.dgyt.lecteursredacteurs;

public class Redacteur {
    private Thread thread;
    private Controleur controleur;
    public Boolean writing = false;
    Redacteur(Controleur controleur) {
        Redacteur redacteur = this;
        this.controleur = controleur;
        thread = new Thread() {
            @Override
            public void run() {
                while (!controleur.write(redacteur));
                writing = true;
                while(controleur.isAccessing(redacteur));
                writing = false;
            }
        };
    }

    public synchronized void startWrite() {
        writing = false;
        thread.start();
        // We know that we either will have write access (=> writing == true) or the thread will change its state
        while(writing == false && thread.getState() == Thread.State.RUNNABLE);
    }

    public synchronized void stopWrite() {
        this.controleur.close(this);
        while(writing);
    }

    public synchronized boolean isWaiting() {
        while(writing == false && thread.getState() == Thread.State.RUNNABLE);
        return writing == false;
    }
}
