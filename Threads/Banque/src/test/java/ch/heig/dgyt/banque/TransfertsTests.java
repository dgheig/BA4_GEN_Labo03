package ch.heig.dgyt.banque;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


public class TransfertsTests {

    private Banque banque;

    @BeforeEach
    public void setUp() throws Exception {
        banque = new Banque(10);
        System.out.println("Bank is null: " + banque==null);
    }

    @Test
    public void checkConsistency() throws InterruptedException {
        //Banque bank = new Banque(10);
        for (int i = 0; i < 1000; i++){
            Transferts trans = new Transferts(1000, banque);
            trans.start();
        }
        assertTrue(banque.consistent());
    }

}
