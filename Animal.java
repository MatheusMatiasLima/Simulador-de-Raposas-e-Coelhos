import java.util.List;

//10.3.1
public class Animal {
    //Se o animal está vivo ou não.
    private boolean alive;
    // O campo do animal. 
    protected Field field;
    // A posição do animal no campo.
    protected Location location;

    public Animal(Field field, Location location) {
        alive = true;
        this.field = field;
        setLocation(location);
    }

    
    //Retorna a localização do animal
    protected Location getLocation()
    {
        return location;
    }

    //Coloquar o animal no novo local no campo determinado.
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear();
        }
        location = newLocation;
        field.place(this, newLocation);
    }


    //Verifique se o animal está vivo ou não.
    // @return true se o animal ainda estiver vivo.
    protected boolean isAlive()
    {
        return alive;
    }

    //Indica que o animal não está mais vivo e ele é removido do campo. 
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear();
            location = null;
            field = null;
        }
    }


}
