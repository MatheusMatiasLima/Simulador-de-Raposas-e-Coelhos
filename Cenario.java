/* Classe abstrata para atores do tipo cenario, que influenciam o mundo,
*  possui assinatura que suas classes filhas usam para agir em cada etapa da simulacao
*/
import java.util.List;
import java.util.Random;
public abstract class Cenario implements Actor{
    protected Location location;
    private static final Random rand = new Random();
    abstract public void act(Field currentField, Field updatedField, List newActors);

    public void setLocation(int row, int col){
        this.location = new Location(row,col);
    }

    public void setLocation(Location location){
        this.location = location;
    }
}