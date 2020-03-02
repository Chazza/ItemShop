package it.xquickglare.itemshops.utils.configuration;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder {

    private Material material;

    private Message name;
    private Message lore;
    private int amount;
    private short data;
    
    private Map<Enchantment, Integer> enchantments;
    private List<ItemFlag> flags;
    
    public ItemBuilder() {
        amount = 1;
        data = 0;
    }

    /**
     * This method sets the item material
     * @param material The material     
     * @return The ItemBuilder object
     */
    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }
    
    /**
     * This method sets the item material
     * @param material The material     
     * @return The ItemBuilder object
     */
    public ItemBuilder material(String material) {
        this.material = Material.valueOf(material);
        return this;
    }

    /**
     * This method sets the item name
     * @param name The name
     * @return The ItemBuilder object
     */
    public ItemBuilder name(String name) {
        this.name = new Message(name);
        return this;
    }

    /**
     * This method sets the item name
     * @param name The name
     * @return The ItemBuilder object
     */
    public ItemBuilder name(Message name) {
        this.name = name;
        return this;
    }

    /**
     * This method sets the item lore
     * @param lore The lore
     * @return The ItemBuilder object
     */
    public ItemBuilder lore(String... lore) {
        this.lore = new Message(Arrays.asList(lore));
        return this;
    }

    /**
     * This method sets the item lore
     * @param lore The lore
     * @return The ItemBuilder object
     */
    public ItemBuilder lore(List<String> lore) {
        this.lore = new Message(lore);
        return this;
    }

    /**
     * This method sets the item lore
     * @param lore The lore
     * @return The ItemBuilder object
     */
    public ItemBuilder lore(Message lore) {
        this.lore = lore;
        return this;
    }

    /**
     * This method sets the item amount
     * @param amount The amount
     * @return The ItemBuilder object
     */
    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }
    
    /**
     * This method sets the item data
     * @param data The data
     * @return The ItemBuilder object
     */
    public ItemBuilder data(short data) {
        this.data = data;
        return this;
    }

    /**
     * This method sets the Enchantment(s) of this ItemBuilder
     * @param enchantments A list of String(s) with the Enchantment(s)
     * @return The ItemBuilder Object
     */
    public ItemBuilder enchantments(List<String> enchantments) {
        this.enchantments = new HashMap<>();
        for (String s : enchantments) {
            String[] split = s.split(";");
            this.enchantments.put(Enchantment.getByName(split[0]), Integer.parseInt(split[1]));
        }
        
        return this;
    }

    /**
     * This method sets the Enchantment(s) of this ItemBuilder
     * @param enchantments A Map with the Enchantment and the Level
     * @return The ItemBuilder Object
     */
    public ItemBuilder enchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    /**
     * This method sets the ItemFlag(s) of this ItemBuilder
     * @param flags A list of String(s) with all the ItemFlag(s)
     * @return The ItemBuilder Object
     */
    public ItemBuilder flags(List<String> flags) {
        this.flags = new ArrayList<>();
        flags.forEach(flagName -> this.flags.add(ItemFlag.valueOf(flagName)));
        return this;
    }

    /**
     * This method add a placeholder on the name and lore of the item
     * @param placeholder The placeholder
     * @param value The value of the placeholder
     * @return The ItemBuilder class
     */
    public ItemBuilder addPlaceholder(String placeholder, Object value) {
        name.addPlaceholder(placeholder, value);
        lore.addPlaceholder(placeholder, value);
        return this;
    }

    /**
     * This method use all the information gived to return the ItemStack object
     * @return The ItemStack object
     */
    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount, data);
        ItemMeta meta = item.getItemMeta();

        if(name != null)
            meta.setDisplayName(name.format().toString());

        if(lore != null)
            meta.setLore(lore.format().toStringList());
        
        if (enchantments != null && !enchantments.isEmpty())
            item.addUnsafeEnchantments(enchantments);
        
        if (flags != null && !flags.isEmpty())
            meta.addItemFlags(flags.toArray(new ItemFlag[0]));

        item.setItemMeta(meta);
        return item;
    }

    /**
     * This method create a new ItemBuilder object
     * @return The ItemBuilder object
     */
    public static ItemBuilder create() {
        return new ItemBuilder();
    }

    /**
     * This method gets all the informations from the config and created an ItemBuilder object
     * @param config The configuration instance
     * @param path The path where get the informations
     * @return The ItemBuilder object
     */
    public static ItemBuilder fromConfig(YAMLConfiguration config, String path) {
        return create()
                .name(config.getMessage(path + ".name"))
                .lore(config.getMessage(path + ".lore"))
                .material(config.getConfig().getString(path + ".material"))
                .amount(config.getConfig().getInt(path  + ".amount"))
                .data((short) config.getConfig().getInt(path + ".data"))
                .enchantments(config.getConfig().getStringList(path + ".enchantments"))
                .flags(config.getConfig().getStringList(path + ".flags"));
    }


}
