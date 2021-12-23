package fr.afpa.cda.metier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import fr.afpa.utils.Validate;

public class ConnexionManagerTest {
  private ConnexionManager connexionManager;

  @Before
  public void setUp() throws Exception {
    connexionManager = new ConnexionManager();
  }

  @Test
  public void testValidationMdp() {
    assertTrue(Validate.validationMdp("azertyuiop"));
    assertFalse(Validate.validationMdp("azerty"));
    assertFalse(Validate.validationMdp(" azerty "));

  }

}
