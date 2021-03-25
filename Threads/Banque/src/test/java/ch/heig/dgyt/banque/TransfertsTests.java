package ch.heig.dgyt.banque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransfertsTests {

    private Banque banque;

    @BeforeEach
    public void setUp() throws Exception {
        banque = new Banque(1000);
    }

    @Test
    public void checkConsistency() throws InterruptedException {

    }
}
