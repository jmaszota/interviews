package machine.states;

import machine.coins.CoinType;
import machine.inventory.ItemType;

/**
 * Created by jacek.maszota on 27.10.2015.
 */
public abstract class State {

    public abstract void insertMoney(float coin);

    public abstract void coinReturn();

    public abstract void getItem(ItemType item);

    public boolean isValidCoin(float coin) {
        if (CoinType.getByValue(coin) != null) return true;
        return false;
    }

}
