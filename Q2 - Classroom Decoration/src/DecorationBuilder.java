/**
 * The DecorationBuilder class is used to create different types of decorations. 
 * You can set up what kind of decoration you want, how much it costs, and other details like how big each tile is.
 */
class DecorationBuilder {
    private String type; // The type of decoration: Paint, Tile, Wallpaper.
    private double price; // How much the decoration costs.
    private double areaPerTile = 0; // The area covered by one tile, relevant only for tiles.

    /**
     * Specifies the type of decoration to create.
     * @param type The decoration type.
     * @return The builder itself for chaining.
     */
    public DecorationBuilder setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the price for the decoration.
     * @param price The cost of the decoration.
     * @return The builder itself for chaining.
     */
    public DecorationBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    /**
     * For tile decorations, sets how much area one tile will cover.
     * @param areaPerTile The area covered by a single tile.
     * @return The builder itself for chaining.
     */
    public DecorationBuilder setAreaPerTile(double areaPerTile) {
        this.areaPerTile = areaPerTile;
        return this;
    }

    /**
     * Builds and returns the decoration object based on the previously set parameters.
     * The type of object returned depends on the decoration type set earlier.
     * @return A new Decoration instance, such as Paint, Tile, or Wallpaper.
     */
    public Decoration build() {
        switch (type) {
            case "Paint":
                return new Paint(price);
            case "Tile":
                return new Tile(price, areaPerTile);
            case "Wallpaper":
                return new Wallpaper(price);
        }
        return null; // Returns null if the type doesn't match known types.
    }
}
