import java.lang.reflect.*;
import java.util.Arrays;
// FIFO
public class Fila<X> implements Cloneable {
    private Object[] items;
    //private int size = 0;
    private int tamanhoInicial;
    private int ultimo = -1;
    private int primeiro = 0;
    

    public Fila(int tamanho) {
        this.items = new Object[tamanho];
    }   

    public Fila() {
        this.items = new Object[10];
    }

    private void redimensioneSe(float fator) {
        int novoTamanho = Math.round(this.items.length * fator);
        Object[] newArray = Arrays.copyOf(this.items, novoTamanho);

        this.items = newArray;
    }   



    public void guardeUmItem(X x)
    {
        if (this.ultimo + 1 == this.items.length) // cheia
            this.redimensioneSe(2.0F);

        this.ultimo++;

        if (x instanceof Cloneable)
            this.items[this.ultimo] = meuCloneDeX(x);
        else
            this.items[this.ultimo] = x;
    }

    public X recupereUmItem()
    {
        X ret = null;
        if (this.items[this.ultimo] instanceof Cloneable)
            ret = meuCloneDeX((X) this.items[this.primeiro]);
        else
            ret = (X) this.items[this.primeiro];

        return ret;
    }

    public void removaUmItem()
    {
        for (int i = 0; i <= this.ultimo - 1; i++) {
            this.items[i] = this.items[i + 1];
        }

        this.items[this.ultimo] = null;
        this.ultimo--; 

        if (this.items.length > this.tamanhoInicial &&
                this.ultimo + 1 <= Math.round(this.items.length * 0.25F))
            this.redimensioneSe(0.5F);
    }

    public void clear(){
        this.items = new Object[10];
        this.ultimo = -1;
    }

    public int tamanho() {
        return this.ultimo + 1;
    }

    public boolean isVazia() {
        return this.ultimo == -1;
    }
    
    public boolean isCheia() {
        return this.ultimo + 1 == this.items.length;
    }
    
    public String toString() {
        String temp = "Fila<";
        for (int i = 0; i < items.length; i++)
        {
            temp += items[i]+";";
        }
        temp += ">";
        return temp;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Fila<X> pil = (Fila<X>) obj;

        if (this.ultimo != pil.ultimo)
            return false;
        if (this.primeiro != pil.primeiro)
            return false;

        if (this.tamanhoInicial != pil.tamanhoInicial)
            return false;

        for (int i = 0; i < this.ultimo; i++)
            if (!this.items[i].equals(pil.items[i]))
                return false;

        return true;
    }

    public int hashCode() {
        int ret = 666;

        ret = ret * 11 + Integer.valueOf(this.ultimo).hashCode();
        ret = ret * 11 + Integer.valueOf(this.tamanhoInicial).hashCode();

        for (int i = 0; i < this.ultimo; i++)
            ret = ret * 11 + this.items[i].hashCode();

        if (ret < 0)
            ret = -ret;

        return ret;
    }

    public Fila(Fila<X> model) {

        this.items = new Object[model.items.length]; 

        for (int i = 0; i < model.items.length; i++){
            this.items[i] = model.items[i];
        }

        this.tamanhoInicial = model.tamanhoInicial;
        this.ultimo = model.ultimo;
    }

    private X meuCloneDeX(X x) {
        X ret = null;

        try {
            Class<?> classe = x.getClass();
            Class<?>[] tipoDosParms = null;
            Method metodo = classe.getMethod("clone", tipoDosParms);
            Object[] parms = null;
            ret = (X) metodo.invoke(x, parms);
        } catch (NoSuchMethodException erro) {
        } catch (IllegalAccessException erro) {
        } catch (InvocationTargetException erro) {
        }

        return ret;
    }

    @Override
    public Object clone() {
        Fila ret = null;
        try {
            ret = new Fila<X>(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
