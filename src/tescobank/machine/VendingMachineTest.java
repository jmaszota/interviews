package tescobank.machine;

import tescobank.machine.coins.CoinType;
import tescobank.machine.inventory.Item;
import tescobank.machine.inventory.ItemType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link VendingMachine}
 */
public class VendingMachineTest {

    private Map<ItemType, Item> sampleInventory = new HashMap<ItemType, Item>();
    private Map<CoinType, Integer> machineCoins = new HashMap<CoinType, Integer>();

    @Test(expected = IllegalArgumentException.class)
    public void invalidVendingMachineConfiguration() {
        VendingMachine machine = new VendingMachine(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidVendingMachineConfiguration_Inventory() {
        VendingMachine machine = new VendingMachine(null, getMachineCoins());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidVendingMachineConfiguration_MachineCoins() {
        VendingMachine machine = new VendingMachine(getSampleInventory(), null);
    }

    @Test
    public void defaultStateIsOff() {
        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        assertFalse(machine.isOn());
    }

    @Test
    public void turnsOn() {
        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        machine.setOn();
        assertTrue(machine.isOn());
    }

    @Test
    public void turnsOff() {
        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        machine.setOff();
        assertFalse(machine.isOn());

        //check machine state
        assertEquals(machine.getMachineOff(), machine.getCurrentState());

        //check available change
        assertCoins(10, 10, 10, 10, machine.getMachineCoins());

        machine.insertMoney(1F);
        machine.coinReturn();
        machine.getA();

        //check available change
        assertCoins(10, 10, 10, 10, machine.getMachineCoins());
        //check inventory
        checkInventory(5, 5, 5, machine);
    }

    @Test
    public void insertCoin() {
        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        machine.setOn();
        machine.insertMoney(1.00f);

        //check user money
        assertEquals(1.00f, machine.getUserMoney(), 0);
        //check machine state
        assertEquals(machine.getHasCoins(), machine.getCurrentState());
        //check available change
        assertCoins(10, 10, 10, 11, machine.getMachineCoins());
        //check inventory
        checkInventory(5, 5, 5, machine);

    }

    @Test
    public void insertInvalidCoin() {

        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        machine.setOn();

        machine.insertMoney(2.00f);

        //check user money
        assertEquals(0.00f, machine.getUserMoney(), 0);
        //check machine state
        assertEquals(machine.getNotEnoughMoney(), machine.getCurrentState());
        //check available change
        assertCoins(10, 10, 10, 10, machine.getMachineCoins());
        //check inventory
        checkInventory(5, 5, 5, machine);
    }

    @Test
    public void getA() {

        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        machine.setOn();

        machine.insertMoney(1.00f);
        machine.insertMoney(1.00f);

        //check available change
        assertCoins(10, 10, 10, 12, machine.getMachineCoins());

        machine.getA();

        //check user money
        assertEquals(1.4f, machine.getUserMoney(), 0);
        //check machine state
        assertEquals(machine.getHasCoins(), machine.getCurrentState());
        //check inventory
        checkInventory(4, 5, 5, machine);
    }

    @Test
    public void getA_NotEnoughMoney() {

        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        machine.setOn();

        machine.insertMoney(0.5f);

        //check available change
        assertCoins(10, 10, 11, 10, machine.getMachineCoins());

        machine.getA();

        //check user money
        assertEquals(0.5f, machine.getUserMoney(), 0);
        //check machine state
        assertEquals(machine.getNotEnoughMoney(), machine.getCurrentState());
        //check inventory
        checkInventory(5, 5, 5, machine);
    }

    @Test
    public void getA_SoldOut() {
        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        machine.setOn();

        for (int i = 0; i < 12; i++) {
            machine.insertMoney(1.00f);
        }

        //check available change
        assertCoins(10, 10, 10, 22, machine.getMachineCoins());
        //check user money
        assertEquals(12.00f, machine.getUserMoney(), 0);

        for (int i = 0; i < 5; i++) {
            machine.getA();
        }

        //check user money
        assertEquals(9.00f, machine.getUserMoney(), 0);
        //check machine state
        assertEquals(machine.getHasCoins(), machine.getCurrentState());
        //check inventory
        checkInventory(0, 5, 5, machine);
    }

    @Test
    public void coinReturn() {
        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        machine.setOn();

        for (int i = 0; i < 12; i++) {
            machine.insertMoney(1.00f);
        }

        //check available change
        assertCoins(10, 10, 10, 22, machine.getMachineCoins());

        machine.coinReturn();

        //check user money
        assertEquals(0.00f, machine.getUserMoney(), 0);
        //check machine state
        assertEquals(machine.getNotEnoughMoney(), machine.getCurrentState());
        //check inventory
        checkInventory(5, 5, 5, machine);
    }


    @Test
    public void calculateChange() {
        //check change
        assertCoins(1, 1, 3, 10, CoinsHandler.calculateChange(11.80F, getMachineCoins()));
        //check change
        assertCoins(1, 0, 2, 10, CoinsHandler.calculateChange(11.10F, getMachineCoins()));
        //check change
        assertCoins(0, 1, 2, 10, CoinsHandler.calculateChange(11.20F, getMachineCoins()));
    }

    @Test
    public void coinReturn_11_80() {

        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        machine.setOn();

        machine.setUserMoney(11.80F);

        machine.setCurrentState(machine.getHasCoins());
        //check available change
        assertCoins(10, 10, 10, 10, machine.getMachineCoins());

        machine.coinReturn();

        //check user money
        assertEquals(0.00f, machine.getUserMoney(), 0);
        //check available change
        assertCoins(9, 9, 7, 0, machine.getMachineCoins());

    }

    @Test
    public void coinReturn_11_10() {

        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        machine.setOn();

        machine.setUserMoney(11.10F);
        machine.setCurrentState(machine.getHasCoins());
        //check available change
        assertCoins(10, 10, 10, 10, machine.getMachineCoins());

        machine.coinReturn();

        //check user money
        assertEquals(0.00f, machine.getUserMoney(), 0);
        //check available change
        assertCoins(9, 10, 8, 0, machine.getMachineCoins());

    }

    @Test
    public void coinReturn_11_20() {

        VendingMachine machine = new VendingMachine(getSampleInventory(), getMachineCoins());
        machine.setOn();

        machine.setUserMoney(11.20F);
        machine.setCurrentState(machine.getHasCoins());
        //check available change
        assertCoins(10, 10, 10, 10, machine.getMachineCoins());

        machine.coinReturn();

        //check user money
        assertEquals(0.00f, machine.getUserMoney(), 0);
        //check available change
        assertCoins(10, 9, 8, 0, machine.getMachineCoins());

    }


    @Test
    public void coinReturn_NotEnoughChange() {

        VendingMachine machine = new VendingMachine(getSampleInventory(), getEmptyMachineCoins());
        machine.setOn();

        machine.insertMoney(1F);

        //check status
        assertEquals(machine.getHasCoins(), machine.getCurrentState());

        //check available change
        assertCoins(0, 0, 0, 1, machine.getMachineCoins());

        machine.getA();

        //check change
        assertEquals(machine.getOutOfChange(), machine.getCurrentState());

    }

    private void assertCoins(int expected_01, int expected_02, int expected_05, int expected_1, Map<CoinType, Integer> coinsToCheck) {
        assertEquals(expected_01, coinsToCheck.get(CoinType._01).intValue());
        assertEquals(expected_02, coinsToCheck.get(CoinType._02).intValue());
        assertEquals(expected_05, coinsToCheck.get(CoinType._05).intValue());
        assertEquals(expected_1, coinsToCheck.get(CoinType._1).intValue());
    }

    private void checkInventory(int expectedA, int expectedB, int expectedC, VendingMachine machine) {
        assertEquals(expectedA, machine.getItems().get(ItemType.A).getCount());
        assertEquals(expectedB, machine.getItems().get(ItemType.B).getCount());
        assertEquals(expectedC, machine.getItems().get(ItemType.C).getCount());
    }

    public Map<ItemType, Item> getSampleInventory() {

        sampleInventory.put(ItemType.A, new Item(ItemType.A, 0.6F, 5));
        sampleInventory.put(ItemType.B, new Item(ItemType.B, 1F, 5));
        sampleInventory.put(ItemType.C, new Item(ItemType.C, 1.70F, 5));

        return sampleInventory;
    }

    public Map<CoinType, Integer> getMachineCoins() {

        machineCoins.put(CoinType._01, 10);
        machineCoins.put(CoinType._02, 10);
        machineCoins.put(CoinType._05, 10);
        machineCoins.put(CoinType._1, 10);


        return machineCoins;
    }

    public Map<CoinType, Integer> getEmptyMachineCoins() {

        machineCoins.put(CoinType._01, 0);
        machineCoins.put(CoinType._02, 0);
        machineCoins.put(CoinType._05, 0);
        machineCoins.put(CoinType._1, 0);


        return machineCoins;
    }
}
