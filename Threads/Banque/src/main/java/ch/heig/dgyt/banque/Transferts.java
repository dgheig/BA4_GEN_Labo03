package ch.heig.dgyt.banque;
import java.util.Objects;

public class Transferts extends Thread {

    private Banque banque;
    private int nbTransfertToDo;

    Transferts(int nbTransfertToDo, Banque banque) {
        Objects.requireNonNull(banque);
        if(nbTransfertToDo < 1)
            throw new RuntimeException("Transfert number must be strictly positive");
        this.banque = banque;
        this.nbTransfertToDo = nbTransfertToDo;
    }

    Transferts(int nbTransfertToDo, int nbAccount) {
        this(nbTransfertToDo, new Banque(nbAccount));
    }

    Transferts(int nbTransfertToDo) {
        this(nbTransfertToDo, 5);
    }

    @Override
    public void run() {
        super.run();
    }
}
