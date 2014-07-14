Menu Framework
==============
A dynamic and flexible library to handle complex menu hierarchies in bukkit.

[JavaDoc](http://docs.njay.net/menu-framework)

Tutorial
========

Enable the Framework
-------------
```java
MenuFramework.enable(new MenuRegistry(instance, TestMenu.class)); //Args: JavaPlugin, Class... menusToRegister
```
or, if you want to use a custom player manager with the framework:
```java
MenuFramework.enable(new MenuRegistry(instance, TestMenu.class), instanceOfPlayerManager); //Args: JavaPlugin, Class... menusToRegister, extension of MenuPlayerManager
```

Creating a MenuItem
------------
A menu item is a method that represents one box of the inventory, when that box is clicked, the method is invoked (the method must take a MenuPlayer as a parameter)
```java
@MenuItem(slot = 5, item = @ItemStackAnnotation(material = Material.BEDROCK, name = "This is the item name"))
public void testItem1(MenuPlayer player) {
  player.getBukkit().sendMessage("This is a test message");
  player.setActiveMenu(new TestSubMenu(manager, null)); //This line changes the active menu to another menu, we'll cover that in more detail later
}
```
The **@MenuItem** annotation takes a slot (slot of the inventory) and an item which specifies the item that will reside in that slot

Creating a Menu class
-------------
```java
@MenuInventory(slots = 18, name = "Sub Menu", filler = @ItemStackAnnotation(name = "", material = Material.BED), onClose = TestMenu.class)
@IgnoreSlots(slots = {1, 2, 3}, items = {
  @ItemStackAnnotation(material = Material.BED, name = "Test"),
  @ItemStackAnnotation(material = Material.APPLE, name = "#LeaveItToApple", lore = {"I", "LIKE", "PANTS"}),
  @ItemStackAnnotation(material = Material.GOLDEN_CARROT, name = "OllyCode", amount = 11)})
public static class TestSubMenu extends Menu {
  //We will add the menu items here later
}
```
The **@MenuInventory** annotation takes slots (number of inventory slots), a filler item (item that the unused slots are filled with), and an (optional) onClose parameter which says what class to open when the player exits the inventory

The **@IgnoreSlots** (optional) annotation shows which slots are allowed to be manipulated by the player, it takes an array of slots (the slots which can be manipulated, and an **@ItemStackAnnotation** which matches each of the slots

Combining MenuItem with a Menu class
--------
```java
@MenuInventory(slots = 18, name = "Sub Menu", filler = @ItemStackAnnotation(name = "", material = Material.BED), onClose = TestMenu.class)
@IgnoreSlots(slots = {1, 2, 3}, items = {
  @ItemStackAnnotation(material = Material.BED, name = "Test"),
  @ItemStackAnnotation(material = Material.APPLE, name = "#LeaveItToApple", lore = {"I", "LIKE", "PANTS"}),
  @ItemStackAnnotation(material = Material.GOLDEN_CARROT, name = "OllyCode", amount = 11)})
public static class TestSubMenu extends Menu {
    @MenuItem(slot = 5, item = @ItemStackAnnotation(material = Material.BEDROCK, name = "This is the item name"))
    public void testItem1(MenuPlayer player) {
      player.getBukkit().sendMessage("This is a test message");
    }
}
```
Simply add the method with the **@MenuItem** annotation into the class as a local method. (must be public and take MenuPlayer as an argument)

Setting the active menu of the player
---------

```java
MenuPlayer player = MenuFramework.getPlayerManager().getPlayer((Player) sender);
player.setActiveMenu(new TestMenu(player.getMenuManager(), null));
```

Using this method will make use of any custom player manager that you add

----------------

*Feel free to contact me on bukkit [gcflames5](http://dev.bukkit.org/profiles/gcflames5/) if you are confused* 
