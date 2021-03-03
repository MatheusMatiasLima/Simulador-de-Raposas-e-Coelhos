import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Esta colecao de classe providencia algns dados estatisticos do estado de um campo. 
 * E flexivel: Ira criar e manter uma contagem para qualquer classe de objeto que for encontrado
 * no campo.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-23
 */
public class FieldStats
{
    // Contadores para cada tipo de entidade (Raposa, Coelho, etc) na simulacao.
    private HashMap counters;
    // Se os contadores estao atualizados.
    private boolean countsValid;

    /**
     * Construi um objeto field-statistics.
     */
    public FieldStats()
    {
        // Configura uma colecao de contadores para cada tipo que encontrar
        counters = new HashMap();
        countsValid = true;
    }

    /**
     * @return String que descreve quais animais estao no campo.
     */
    public String getPopulationDetails(Field field)
    {
        StringBuffer buffer = new StringBuffer();
        if(!countsValid) {
            generateCounts(field);
        }
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter info = (Counter) counters.get(keys.next());
            buffer.append(info.getName());
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }
    
    /**
     * Reseta todas as estatisticas para zero.
     */
    public void reset()
    {
        countsValid = false;
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter cnt = (Counter) counters.get(keys.next());
            cnt.reset();
        }
    }

    /**
     * Incrementa a contagem de uma classe de animal.
     */
    public void incrementCount(Class animalClass)
    {
        Counter cnt = (Counter) counters.get(animalClass);
        if(cnt == null) {
            // nos nao temos contagem para esta especia - cria uma
            cnt = new Counter(animalClass.getName());
            counters.put(animalClass, cnt);
        }
        cnt.increment();
    }

    /**
     * Indica que a contagem do animal esta completa.
     */
    public void countFinished()
    {
        countsValid = true;
    }

    /**
     * Determina se a simulacao continua valida.
     * I.e., se deve continuar.
     * @return true se tem mais de uma especie viva.
     */
    public boolean isViable(Field field)
    {
        // Quantas contagens sao diferentes de zero.
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(field);
        }
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter info = (Counter) counters.get(keys.next());
            if(info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }
    
    /**
     * Gera contagens do numero de raposas e coelhos e jacares.
     * Estes nao sao mantidos atualizados como raposas e coelhos e jacares
     * sao colocados no campo, mas apenas quando uma requesicao e feita
     * para a informacao.
     */
    private void generateCounts(Field field)
    {
        reset();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    incrementCount(animal.getClass());
                }
            }
        }
        countsValid = true;
    }
}
