package atoms.rebuiltfactions.dev.utils;

import atoms.RebuiltFactions;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.MetaItemStack;
import com.earth2me.essentials.User;
import com.earth2me.essentials.textreader.IText;
import com.earth2me.essentials.textreader.KeywordReplacer;
import com.earth2me.essentials.textreader.SimpleTextInput;
import net.ess3.api.IEssentials;
import com.earth2me.essentials.I18n;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class KitUtils {

    private static int maxYears = 100000;

    static final IEssentials ess = null;

    static int dateDiff(int type, Calendar fromDate, Calendar toDate, boolean future) {
        int year = 1;
        int fromYear = fromDate.get(year);
        int toYear = toDate.get(year);

        if (Math.abs(fromYear - toYear) > 100000)
            toDate.set(year, fromYear + (future ? 100000 : -100000));
        int diff = 0;
        long savedDate = fromDate.getTimeInMillis();
        while ((future && !fromDate.after(toDate)) || (!future && !fromDate.before(toDate))) {
            savedDate = fromDate.getTimeInMillis();
            fromDate.add(type, future ? 1 : -1);
            diff++;
        }
        diff--;
        fromDate.setTimeInMillis(savedDate);
        return diff;
    }

    public static String fromDateDiff(long date) {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(date);
        Calendar now = new GregorianCalendar();
        return formatDateDiff(now, c);
    }

    public static String formatDateDiff(Calendar fromDate, Calendar toDate) {
        boolean future = false;
        if (toDate.equals(fromDate))
            return I18n.tl("now", new Object[0]);
        if (toDate.after(fromDate))
            future = true;
        StringBuilder sb = new StringBuilder();
        int[] types = {1, 2, 5, 11, 12, 13};
        String[] names = {
                I18n.tl("year", new Object[0]), I18n.tl("years", new Object[0]), I18n.tl("month", new Object[0]), I18n.tl("months", new Object[0]), I18n.tl("day", new Object[0]), I18n.tl("days", new Object[0]), I18n.tl("hour", new Object[0]), I18n.tl("hours", new Object[0]), I18n.tl("minute", new Object[0]), I18n.tl("minutes", new Object[0]),
                I18n.tl("second", new Object[0]), I18n.tl("seconds", new Object[0])};
        int accuracy = 0;
        for (int i = 0; i < types.length &&
                accuracy <= 2; i++) {
            int diff = dateDiff(types[i], fromDate, toDate, future);
            if (diff > 0) {
                accuracy++;
                sb.append(" ").append(diff).append(" ").append(names[i * 2 + ((diff > 1) ? 1 : 0)]);
            }
        }
        if (sb.length() == 0)
            return I18n.tl("now", new Object[0]);
        return sb.toString().trim();
    }

    public static List<String> listKits(User user) {
        File file = new File("plugins/Essentials/kits.yml");
        YamlConfiguration config = new YamlConfiguration();
        if (!file.exists()) {
            (RebuiltFactions.getPlugin(RebuiltFactions.class)).getLogger().log(Level.SEVERE, "Could not find kits.yml in the essentials folder.");
            return null;
        }
        try {
            config.load(file);
            ConfigurationSection kits = config.getConfigurationSection("kits");
            List<String> list = new ArrayList<>();
            try {
                for (String kitItem : kits.getKeys(false)) {
                    String name = I18n.capitalCase(kitItem);
                    list.add(name);
                }
            } catch (NullPointerException nfe) {
                return null;

            }
            return list;
        } catch (NullPointerException | IOException | InvalidConfigurationException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public List<String> getItemStrings(String name) {
        File file = new File("plugins/Essentials/kits.yml");
        YamlConfiguration config = new YamlConfiguration();
        if (!file.exists()) {
            (RebuiltFactions.getPlugin(RebuiltFactions.class)).getLogger().log(Level.SEVERE, "Could not find kits.yml in the essentials folder.");
            return null;
        }
        try {
            config.load(file);
            ConfigurationSection kits = config.getConfigurationSection("kits");
            List<String> tools = kits.getStringList(name + ".items");
            return tools;
        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<ItemStack> getKitItems(final User user, final List<String> items, Essentials ess) throws Exception {
        ArrayList<ItemStack> kitItems = new ArrayList<>();
        try {
            IText input = new SimpleTextInput(items);
            IText output = new KeywordReplacer(input, user.getSource(), ess, true, true);

            final boolean allowUnsafe = ess.getSettings().allowUnsafeEnchantments();
            for (String kitItem : output.getLines()) {
                if (kitItem.startsWith(ess.getSettings().getCurrencySymbol())) {
                    continue;
                }

                final String[] parts = kitItem.split(" +");
                final ItemStack parseStack = ess.getItemDb().get(parts[0], parts.length > 1 ? Integer.parseInt(parts[1]) : 1);

                if (parseStack.getType() == Material.AIR) {
                    continue;
                }

                final MetaItemStack metaStack = new MetaItemStack(parseStack);

                if (parts.length > 2) {
                    metaStack.parseStringMeta(null, allowUnsafe, parts, 2, ess);
                }
                kitItems.add(metaStack.getItemStack());
            }
        } catch (Exception e) {
            return new ArrayList<ItemStack>();
        }
        return kitItems;
    }

    public static Map<TimeUnit,Long> computeDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        List<TimeUnit> units = new ArrayList<TimeUnit>();
        units.add(TimeUnit.DAYS);
        units.add(TimeUnit.HOURS);
        units.add(TimeUnit.MINUTES);

        Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
        long milliesRest = diffInMillies;
        for ( TimeUnit unit : units ) {
            long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;
            result.put(unit,diff);
        }
        return result;
    }
}
