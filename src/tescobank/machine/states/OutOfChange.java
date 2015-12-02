package tescobank.machine.states;

import tescobank.machine.VendingMachine;
import tescobank.machine.inventory.ItemType;

/**
 * Created by jacek.maszota on 27.10.2015.
 */
public class OutOfChange extends State {

    protected VendingMachine vendingMachine;

    public OutOfChange(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertMoney(float coin) {
        System.out.println("No Change in machine.");
    }

    @Override
    public void coinReturn() {
        System.out.println("No change in machine.");
    }

    @Override
    public void getItem(ItemType item) {
        System.out.println("No Change in machine.");
    }
}
