package my.application.shared;

import java.util.HashMap;

public class PlacedBetsUtils {
    private static final HashMap PLACED_BETS = new HashMap<String, Bet>();

    public static void addPlacedBet(Bet bet) {
        PLACED_BETS.put(bet.getId(), bet);
    }

    public static Boolean betBeenPlaced(Bet bet) {
        return PLACED_BETS.containsKey(bet.getId());
    }

    public static boolean betBeenPlaced(String id) {
        return PLACED_BETS.containsKey(id);
    }
}
