package machine.states;

import machine.VendingMachine;
import machine.inventory.ItemType;

/**
 * Created by jacek.maszota on 27.10.2015.
 */
public class MachineOff extends State {

    private VendingMachine vendingMachine;

    public MachineOff(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertMoney(float coin) {
        System.out.println("Machine is OFF. You can't insert coin");
    }

    @Override
    public void coinReturn() {
        System.out.println("Machine is OFF. You can't return coin");
    }

    @Override
    public void getItem(ItemType item) {
        System.out.println("Machine is OFF. You can't get item");
    }

}
