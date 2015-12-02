package tescobank.machine.states;

import tescobank.machine.VendingMachine;
import tescobank.machine.inventory.ItemType;

/**
 * Created by jacek.maszota on 27.10.2015.
 */
public class NoEnoughMoney extends State {

    private VendingMachine vendingMachine;

    public NoEnoughMoney(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertMoney(float coin) {
        if (isValidCoin(coin)) {
            this.vendingMachine.addUserCoin(coin);
            this.vendingMachine.setCurrentState(this.vendingMachine.getHasCoins());
        } else {
            System.out.println("Invalid coin. Valid: 0.10, 0.20, 0.50, 1.00");
        }
    }

    @Override
    public void coinReturn() {
        System.out.println("Nothing to return.");
    }

    @Override
    public void getItem(ItemType item) {
        System.out.println("No coins.");
    }

}
