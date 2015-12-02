package tescobank.machine.coins;

/**
 * Created by jacek.maszota on 27.10.2015.
 */
public enum CoinType {

    _01(0.1F, "Ten Pence"), _02(0.2F, "Twenty Pence"), _05(0.5F, "Fifty Pence"), _1(1.0F, "One Pound");

    private float value;
    private String name;

    CoinType(float value, String name) {
        this.value = value;
        this.name = name;
    }

    public static CoinType getByValue(float value) {
        for (CoinType coinType : values()) {
            if (coinType.value == value) return coinType;
        }
        return null;
    }

    public float getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
