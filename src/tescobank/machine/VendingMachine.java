package tescobank.machine;

import tescobank.machine.coins.CoinType;
import tescobank.machine.inventory.Item;
import tescobank.machine.inventory.ItemType;
import tescobank.machine.states.*;

import java.util.Map;

/**
 * Encapsulates the state of a vending machine and the operations that can be performed on it
 */
public class VendingMachine {


    private Map<ItemType, Item> items;

    private State hasCoins;
    private State notEnoughMoney;
    private State machineOff;
    private State noItem;
    private State outOfChange;

    private State currentState;

    private Map<CoinType, Integer> machineCoins;
    private float userMoney;

    public VendingMachine(Map<ItemType, Item> inventory, Map<CoinType, Integer> machineCoins) {

        if (inventory == null) throw new IllegalArgumentException("inventory cannot be null");
        if (machineCoins == null) throw new IllegalArgumentException("machineCoins cannot be null");

        this.hasCoins = new HasCoins(this);
        this.notEnoughMoney = new NoEnoughMoney(this);
        this.machineOff = new MachineOff(this);
        this.noItem = new NoItem(this);
        this.outOfChange = new OutOfChange(this);

        this.setCurrentState(this.getMachineOff());

        this.setItems(inventory);
        this.setMachineCoins(machineCoins);
    }


    public boolean isOn() {
        return !this.getCurrentState().equals(this.getMachineOff());
    }


    public void setOn() {
        System.out.println("Set On");
        if (getUserMoney() > 0) {
            this.setCurrentState(this.getHasCoins());
        } else {
            this.setCurrentState(this.getNotEnoughMoney());
        }
    }


    public void setOff() {
        System.out.println("Set Off");
        this.setCurrentState(this.getMachineOff());
    }


    public void insertMoney(float coin) {
        getCurrentState().insertMoney(coin);
    }


    public void coinReturn() {
        getCurrentState().coinReturn();
    }


    public void getA() {
        getCurrentState().getItem(ItemType.A);
    }


    public void getB() {
        getCurrentState().getItem(ItemType.B);
    }


    public void getC() {
        getCurrentState().getItem(ItemType.C);
    }

    public void addUserCoin(float coin) {
        if (getMachineCoins().get(CoinType.getByValue(coin)) == null) {
            getMachineCoins().put(CoinType.getByValue(coin), 1);
        } else {
            getMachineCoins().put(CoinType.getByValue(coin), getMachineCoins().get(CoinType.getByValue(coin)) + 1);
        }

        userMoney += coin;
    }


    public float getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(float userMoney) {
        this.userMoney = CoinsHandler.roundFloat(userMoney, 2);
    }

    //getters & setters


    public Map<ItemType, Item> getItems() {
        return items;
    }

    public void setItems(Map<ItemType, Item> items) {
        this.items = items;
    }

    public State getHasCoins() {
        return hasCoins;
    }

    public void setHasCoins(State hasCoins) {
        this.hasCoins = hasCoins;
    }

    public State getNotEnoughMoney() {
        return notEnoughMoney;
    }

    public void setNotEnoughMoney(State notEnoughMoney) {
        this.notEnoughMoney = notEnoughMoney;
    }

    public State getMachineOff() {
        return machineOff;
    }

    public void setMachineOff(State machineOff) {
        this.machineOff = machineOff;
    }


    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public Map<CoinType, Integer> getMachineCoins() {
        return machineCoins;
    }

    public void setMachineCoins(Map<CoinType, Integer> machineCoins) {
        this.machineCoins = machineCoins;
    }

    public State getNoItem() {
        return noItem;
    }

    public void setNoItem(State noItem) {
        this.noItem = noItem;
    }

    public State getOutOfChange() {
        return outOfChange;
    }

    public void setOutOfChange(State outOfChange) {
        this.outOfChange = outOfChange;
    }
}
