import java.util.List;

//10.3.1
public class Animal {
    //Se o animal está vivo ou não.
    private boolean alive;
    // O campo do animal. 
    private Field field;
    // A posição do animal no campo.
    private Location location;

    public Animal(Field field, Location location) {
        alive = true;
        this.field = field;
        setLocation(location);
    }


}
