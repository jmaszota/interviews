package tescobank.machine.states;

import tescobank.machine.CoinsHandler;
import tescobank.machine.VendingMachine;
import tescobank.machine.coins.CoinType;
import tescobank.machine.inventory.ItemType;

import java.util.Map;

/**
 * Created by jacek.maszota on 27.10.2015.
 */
public class HasCoins extends State {

    protected VendingMachine vendingMachine;

    public HasCoins(VendingMachine vendingMachine) {
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

        Map<CoinType, Integer> coins = CoinsHandler.calculateChange(this.vendingMachine.getUserMoney(), this.vendingMachine.getMachineCoins());

        if (coins == null) {
            this.vendingMachine.setCurrentState(this.vendingMachine.getOutOfChange());
            System.out.println("No change in machine");
            return;
        } else {
            CoinsHandler.returnChange(this.vendingMachine, coins);
            this.vendingMachine.setUserMoney(0);
            this.vendingMachine.setCurrentState(this.vendingMachine.getNotEnoughMoney());
            System.out.println(CoinsHandler.CoinsString("====Coins returned===", coins));
        }

    }

    @Override
    public void getItem(ItemType item) {

        if (this.vendingMachine.getItems().get(item) != null && this.vendingMachine.getItems().get(item).getCount() > 0) {
            if (this.vendingMachine.getUserMoney() >= this.vendingMachine.getItems().get(item).getPrice()) {

                float userMoneyAfterTransaction = CoinsHandler.roundFloat(this.vendingMachine.getUserMoney() - this.vendingMachine.getItems().get(item).getPrice(), 2);

                Map<CoinType, Integer> coins = CoinsHandler.calculateChange(userMoneyAfterTransaction, this.vendingMachine.getMachineCoins());

                if (coins == null) {
                    coinReturn();
                    this.vendingMachine.setCurrentState(this.vendingMachine.getOutOfChange());
                    System.out.println("Transaction cannot be completed. There is not enough change in machine");
                    return;
                }

                this.vendingMachine.getItems().get(item).setCount(this.vendingMachine.getItems().get(item).getCount() - 1);
                this.vendingMachine.setUserMoney(userMoneyAfterTransaction);
                System.out.println("Vending machine returns item: " + item);
                if (this.vendingMachine.getUserMoney() == 0) {
                    this.vendingMachine.setCurrentState(this.vendingMachine.getNotEnoughMoney());
                }
            } else {
                System.out.println("Not enough coins");
                this.vendingMachine.setCurrentState(this.vendingMachine.getNotEnoughMoney());
            }

        } else {
            System.out.println("No items " + item + " left");
            this.vendingMachine.setCurrentState(this.vendingMachine.getNoItem());
        }
    }
}
