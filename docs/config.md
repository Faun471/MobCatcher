# config.yml
On this page, we'll try to explain all there is to explain in the plugin's config.yml.

## Adding another mob-catcher
This is the default mob-catcher item:
```yaml
mob-catchers:
    default:
        material: STICK
        displayName: Mob Catching Stick!
        uses: 1
        whitelistedEntities: 
        - WOLF
```
Now, let's delve into detail as to what each of them does and the possible values you can change them to.

|    config value     | possible values |                                                             description                                                             |
|:-------------------:|:---------------:|:-----------------------------------------------------------------------------------------------------------------------------------:|
|       default       |     String      |           This is the name of the item and is what shows up when you tab-complete the `/mc give <player> <item>` command.           |
|      material       |    Material     |                    This is the item that will represent the item. It is advisable to not use a block as an item.                    |
|     displayName     |     String      |                                             This will serve as the item's display name.                                             |
|        uses         |     Integer     | This will determine how many times the item can be used before getting destroyed. You may put any negative value for infinite uses. |
| whitelistedEntities |   EntityType    |         This is the list of entities that can be put inside the item. You may put `"*"` to allow all mobs to get "caught".          |

### Possible values
`String` values are any string of text or numbers.

`Integer` values are any number from -2147483647 to 2147483647.

`Material` values are Material enum constants. You may refer to this [page](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html).

`EntityType` values are EntityType enum constants. You may refer to this [page](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html).

### Examples
A mob-catcher that has unlimited uses and can catch all mobs:
```yaml
mob-catchers:
    infinite:
        material: BLAZE_ROD
        displayName: &eThe Ultimate Catcher
        uses: -1
        whitelistedEntities: [ '*' ]
```
A mob-catcher that has three uses and can catch farm animals:
```yaml
mob-catchers:
    farm-animals-catcher:
        material: STICK
        displayName: &7Farm Animals Catcher
        uses: 3
        whitelistedEntities:
        - SHEEP
        -'CHICKEN
        - COW
        - PIG
```
And to seal the deal:
```yaml
mob-catchers:    
    default:
        material: STICK
        displayName: Mob Catching Stick!
        uses: 1
        whitelistedEntities:
          - WOLF
    infinite:
        material: BLAZE_ROD
        displayName: &eThe Ultimate Catcher
        uses: -1
        whitelistedEntities: [ '*' ]
    farm-animals-catcher:
        material: STICK
        displayName: &7Farm Animals Catcher
        uses: 3
        whitelistedEntities:
          - SHEEP
          - CHICKEN
          - COW
          - PIG
```