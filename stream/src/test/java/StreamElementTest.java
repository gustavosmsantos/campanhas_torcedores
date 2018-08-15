import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StreamElementTest {

    @Test
    public void testObtencaoElementoSequenciaExemplo() {
        StreamElement.CharSequenceStream stream = new StreamElement.CharSequenceStream("aAbBABacafe");
        assertThat(StreamElement.getElement(stream), is('e'));
    }

    @Test
    public void testObtencaoElementoDuasSequenciasValidasRetornaPrimeira() {
        StreamElement.CharSequenceStream stream = new StreamElement.CharSequenceStream("aAbBABacafedu");
        assertThat(StreamElement.getElement(stream), is('e'));
    }

    @Test
    public void testObtencaoElementoSequenciaRepetidaLetraCaseOposto() {
        StreamElement.CharSequenceStream stream = new StreamElement.CharSequenceStream("aEAbBABacafedu");
        assertThat(StreamElement.getElement(stream), is('u'));
    }

    @Test
    public void testObtencaoElementoNenhumaSequenciaValida() {
        StreamElement.CharSequenceStream stream = new StreamElement.CharSequenceStream("AaEeIiOoUu");
        assertThat(StreamElement.getElement(stream), is('0'));
    }

}
