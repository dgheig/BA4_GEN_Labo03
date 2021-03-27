package ch.heig.dgyt.banque;
import java.util.Objects;
import java.util.Random;

public class Transferts extends Thread {

    private Banque banque;
    private int nbTransfertToDo;
    private Random rand = new Random(System.currentTimeMillis());

    Transferts(int nbTransfertToDo, Banque banque) {
        Objects.requireNonNull(banque, "TRANSFERTS CONSTRUCTOR: Bank cannot be null");
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
        //super.run();
        for (int i = 0; i < nbTransfertToDo; i++){
            int fromAccount = rand.nextInt(banque.getNbComptes());
            int toAccount = rand.nextInt(banque.getNbComptes());
            int amount = rand.nextInt(10);
            banque.transfert(fromAccount, toAccount, amount);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
