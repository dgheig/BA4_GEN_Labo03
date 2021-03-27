package ch.heig.dgyt.lecteursredacteurs;

public class Redacteur {
    private Thread thread;
    private Controleur controleur;
    Redacteur(Controleur controleur) {
        Redacteur redacteur = this;
        this.controleur = controleur;
        thread = new Thread() {
            @Override
            public synchronized void run() {
                while (!controleur.write(redacteur)) {
                    try {
                        wait();
                        synchronized (System.out) {
                            System.out.println(redacteur + " done waiting");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while(controleur.isAccessing(redacteur));
            }
        };
        thread.setPriority(Thread.MAX_PRIORITY);
    }

    public synchronized void startWrite() {
        thread.start();
    }

    public synchronized void stopWrite() {
        this.controleur.close(this);
    }

    public synchronized boolean isWaiting() {
        return thread.getState() == Thread.State.WAITING;
    }
}
