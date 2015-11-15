package machine;

import machine.coins.CoinType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jacek.maszota on 28.10.2015.
 */
public class CoinsHandler {

    private static int calculateCoinsNeeded(float money, CoinType coinType) {
        return (int) (money / coinType.getValue());
    }

    private static float addToChange(Map<CoinType, Integer> changeCoins, Map<CoinType, Integer> machineCoins, float tempUserMoney, int coinsNeededCount, CoinType coinType) {
        if (machineCoins.get(coinType) > coinsNeededCount) {
            changeCoins.put(coinType, coinsNeededCount);
            tempUserMoney -= (coinsNeededCount * coinType.getValue());
            machineCoins.put(coinType, machineCoins.get(coinType) - coinsNeededCount);
        } else {
            changeCoins.put(coinType, machineCoins.get(coinType));
            tempUserMoney -= (coinType.getValue() * machineCoins.get(coinType));
            machineCoins.put(coinType, 0);
        }
        return roundFloat(tempUserMoney, 2);
    }

    public static Map<CoinType, Integer> calculateChange(float userMoney, Map<CoinType, Integer> machineCoins) {

        Map<CoinType, Integer> returnCoins = getEmptyCoinsHolder();

        float tempUserMoney = userMoney;

        Map<CoinType, Integer> tempMachineCoins = getEmptyCoinsHolder();

        for (Map.Entry<CoinType, Integer> entry : machineCoins.entrySet()) {
            tempMachineCoins.put(entry.getKey(), entry.getValue());
        }

        int _1_count = calculateCoinsNeeded(tempUserMoney, CoinType._1);

        tempUserMoney = addToChange(returnCoins, tempMachineCoins, tempUserMoney, _1_count, CoinType._1);

        if (tempUserMoney == 0) {
            return returnCoins;
        }

        int _05_count = calculateCoinsNeeded(tempUserMoney, CoinType._05);

        tempUserMoney = addToChange(returnCoins, tempMachineCoins, tempUserMoney, _05_count, CoinType._05);

        if (tempUserMoney == 0) {
            return returnCoins;
        }

        int _02_count = calculateCoinsNeeded(tempUserMoney, CoinType._02);

        tempUserMoney = addToChange(returnCoins, tempMachineCoins, tempUserMoney, _02_count, CoinType._02);

        if (tempUserMoney == 0) {
            return returnCoins;
        }

        int _01_count = calculateCoinsNeeded(tempUserMoney, CoinType._01);

        tempUserMoney = addToChange(returnCoins, tempMachineCoins, tempUserMoney, _01_count, CoinType._01);

        if (tempUserMoney == 0) {
            return returnCoins;
        }

        return null;
    }

    private static Map<CoinType, Integer> getEmptyCoinsHolder() {
        Map<CoinType, Integer> returnCoins = new HashMap<CoinType, Integer>();
        returnCoins.put(CoinType._01, 0);
        returnCoins.put(CoinType._02, 0);
        returnCoins.put(CoinType._05, 0);
        returnCoins.put(CoinType._1, 0);
        return returnCoins;
    }

    public static void returnChange(VendingMachine vendingMachine, Map<CoinType, Integer> coins) {

        for (Map.Entry<CoinType, Integer> entry : coins.entrySet()) {
            vendingMachine.getMachineCoins().put(entry.getKey(), vendingMachine.getMachineCoins().get(entry.getKey()) - entry.getValue());
        }
    }

    public static String CoinsString(String header, Map<CoinType, Integer> coins) {
        StringBuilder output = new StringBuilder();
        output.append(header);
        output.append("\n");
        output.append(coinsToString(coins));
        return output.toString();
    }


    private static StringBuilder coinsToString(Map<CoinType, Integer> coinsToDisplay) {

        StringBuilder output = new StringBuilder();

        for (Map.Entry<CoinType, Integer> coins : coinsToDisplay.entrySet()) {
            output.append(coins.getKey().getName());
            output.append(" = ");
            output.append(coins.getValue());
            output.append("\n");
        }

        return output;
    }

    public static float roundFloat(float floatToRound, int digits) {
        return BigDecimal.valueOf(floatToRound).setScale(digits, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
