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
                synchronized (writing) {
                    writing = false;
                }
                while(controleur.isAccessing(redacteur));
                synchronized (writing) {
                    writing = false;
                }
            }
        };
    }

    public synchronized void startWrite() {
        thread.start();
        System.out.println("setup: " + this);
        while(!writing && thread.getState() == Thread.State.RUNNABLE);
    }

    public synchronized void stopWrite() {
        this.controleur.close(this);
        while(writing);
    }

    public synchronized boolean isWaiting() {
        System.out.println(this + ": " + thread.getState());
        return thread.getState() != Thread.State.RUNNABLE;
    }
}
