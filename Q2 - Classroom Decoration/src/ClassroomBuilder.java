/**
 * The ClassroomBuilder class is designed to simplify the creation of Classroom objects.
 * It allows for setting various parameters of a Classroom, such as its name, height, and dimensions,
 * before constructing a specific Classroom object (Circular or Rectangular) based on these parameters.
 */
class ClassroomBuilder {
    private String name;
    private double roomHeight;
    private Double roomWidth = null;
    private Double roomLength = null;
    private Double roomDiameter = null;
    /**
     * Sets the name of the classroom.
     * @param name The name for the classroom.
     * @return This ClassroomBuilder instance for method chaining.
     */
    public ClassroomBuilder setName(String name) {
        this.name = name;
        return this;
    }
    /**
     * Sets the height of the classroom.
     * @param roomHeight The height for the classroom.
     * @return This ClassroomBuilder instance for method chaining.
     */
    public ClassroomBuilder setRoomHeight(double roomHeight) {
        this.roomHeight = roomHeight;
        return this;
    }
    /**
     * Sets the width of the classroom. Used for RectangularClassroom.
     * @param roomWidth The width for the classroom.
     * @return This ClassroomBuilder instance for method chaining.
     */
    public ClassroomBuilder setRoomWidth(Double roomWidth) {
        this.roomWidth = roomWidth;
        return this;
    }
    /**
     * Sets the length of the classroom. Used for RectangularClassroom.
     * @param roomLength The length for the classroom.
     */
    public void setRoomLength(Double roomLength) {
        this.roomLength = roomLength;
    }
    /**
     * Sets the diameter of the classroom. Used for CircularClassroom.
     * @param roomDiameter The diameter for the classroom.
     */
    public void setRoomDiameter(Double roomDiameter) {
        this.roomDiameter = roomDiameter;
    }
    /**
     * Constructs and returns a specific Classroom object (either CircularClassroom or RectangularClassroom)
     * based on the parameters that have been set on this builder instance.
     * @return A new Classroom object, either Circular or Rectangular, based on the provided specifications.
     */
    public Classroom build() {
        if (roomDiameter != null) {
            return new CircularClassroom(name, roomHeight, roomDiameter);
        } else {
            return new RectangularClassroom(name, roomHeight, roomWidth, roomLength);
        }
    }
}