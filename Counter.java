import java.awt.Color;

/**
 * Proporciona um contador para cada participante da simulacao.
 * Isto inclui uma string como identificador e a contagem de como
 * varios participantes de cada tipo atualmente existe na simulacao.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-23
 */
public class Counter
{
    // Um nome para o participante da simulacao
    private String name;
    //Quantos deste tipo existem na simulacao.
    private int count;

    /**
     * Providencia um nome para cada um dos tipos da simulacao.
     * @param name  Um nome, e.g. "Raposa".
     */
    public Counter(String name)
    {
        this.name = name;
        count = 0;
    }
    
    /**
     * @return Uma descricao curta do tipo.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return A quantidade atual do tipo
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Incrementa o contador atual em um.
     */
    public void increment()
    {
        count++;
    }
    
    /**
     * Reseta o contador para zero.
     */
    public void reset()
    {
        count = 0;
    }
}
