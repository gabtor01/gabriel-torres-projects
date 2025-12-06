/**
 * Estructura que registra los colores que aparecen y cuántas veces lo 
 * hacen. Los nodos se organizan en un árbol ordenado por frecuencia y 
 * se balancea automáticamente después de cada eliminación de bloques.
 * */
public class ArbolColoresBalanceado{
    /**
     * Clase interna que representa un nodo del árbol de colores.
     * Cada nodo contiene un color, su frecuencia de aparición y 
     * referencias a los nodos hijo izquierdo y derecho.
     */
    public class NodoColor{
        /** Color representado en el nodo. */
        Color color;
        /** Número de veces que el color ha sido registrado. */
        int frecuencia;
        /** Referencia al subárbol izquierdo (frecuencias menores). */
        NodoColor izquierda;
        /** Referencia al subárbol derecho (frecuencias mayores). */
        NodoColor derecha;

        /**
         * Crea un nuevo nodo con el color y frecuencia especificados.
         * 
         * @param color color asociado al nodo
         * @param frecuencia cantidad de veces que el color aparece
         */
        public NodoColor(Color color, int frecuencia){
            this.color = color;
            this.frecuencia = frecuencia;
        }
    }
    /** Nodo raíz del árbol balanceado. */
    private NodoColor raiz;

    /**
     * Reduce la frecuencia de un color en el árbol. Si la frecuencia
     * llegara a ser menor que 0 (Como si el color se eliminara), se 
     * asigna 0.
     * 
     * @param color el color por actualizar
     */
    public void reducirFrecuencia(Color color, int cantidad) {
        NodoColor nodo = buscarColor(raiz, color);
        if (nodo != null){
            nodo.frecuencia -= cantidad;
            if (nodo.frecuencia < 0){
                nodo.frecuencia = 0; // Evitar frecuencias negativas
            }
        }
        // Modificar las frecuencias induce un desbalance por eso se autobalancea
        if (raiz != null){
            raiz = balancearArbol(raiz);
        }
    }

    /**
     * Agrega un color al árbol. Si ya existe, incrementa su frecuencia.
     * Luego reorganiza el árbol para mantenerlo balanceado.
     * 
     * @param color el color a agregar o actualizar
     */
    public void agregarColor(Color color){
        NodoColor nodo = buscarColor(raiz, color);
        // Si ya existe, solo actualizar frecuencia
        if (nodo != null){
            nodo.frecuencia++;
            raiz = balancearArbol(raiz);
            return;
        }
        // Si no existe, insertar como nodo nuevo
        raiz = agregarNodo(raiz, color);
        raiz = balancearArbol(raiz);
    }

    /**
     * Agrega un nodo en el árbol recursivamente.
     * 
     * @param nodo  nodo actual del árbol
     * @param color color a insertar
     * @return nodo actualizado
     */
    private NodoColor agregarNodo(NodoColor nodo, Color color){
        if (nodo == null){
            return new NodoColor(color, 1);
        }

        if (nodo.color.equals(color)){
            nodo.frecuencia++;
            return nodo;
        }
        // Insertar siempre a la izquierda
        nodo.izquierda = agregarNodo(nodo.izquierda, color);
        return nodo;
    }

    /**
     * Obtiene la frecuencia actual de un color en el árbol.
     * Si el color no existe, devuelve 1 (frecuencia inicial).
     * 
     * @param color color a consultar
     * @return frecuencia del color si existe, o 1 si no está en el árbol
     */
    public int getFrecuencia(Color color){
        // Buscar el NodoColor desde la raíz según el color
        NodoColor nodo = buscarColor(raiz, color);
        if (nodo == null){
            return 1;
        }
        return nodo.frecuencia;
    }

    /**
     * Busca recursivamente un color dentro del árbol a partir de la raíz.
     * 
     * @param nodo  nodo raíz del subárbol a inspeccionar
     * @param color color a buscar
     * @return nodo que contiene el color, o {@code null} si no se encuentra
     */
    private NodoColor buscarColor(NodoColor nodo, Color color){
        if (nodo == null){
            return null;
        }
        if (color.equals(nodo.color)){
            return nodo;
        }
        NodoColor nodoIzq = buscarColor(nodo.izquierda, color);
        if (nodoIzq != null){
            return nodoIzq;
        }
        return buscarColor(nodo.derecha, color);
    }

    /**
     * Devuelve el color con mayor frecuencia en el árbol.
     * 
     * @return color dominante.
     */
    public Color getColorDominante(){
        if (raiz == null){
            return null;
        }

        NodoColor actual = raiz;
        // Siempre a la derecha porque es autobalanceado
        while (actual.derecha != null){
            actual = actual.derecha;
        }
        return actual.color;
    }

    /**
     * Balancea el árbol ordenando los nodos por frecuencia y reconstruyéndolo.
     * 
     * @param nodo raíz del subárbol a balancear
     * @return nueva raíz del árbol balanceado
     */
    private NodoColor balancearArbol(NodoColor nodo){
        if (nodo == null){
            return null;
        }

        int numeroNodos = contarNodos(nodo);
        NodoColor[] lista = new NodoColor[numeroNodos];

        // llenar lista
        llenarLista(nodo, lista, new int[]{0});

        // ordenar por frecuencia
        insertionSort(lista);

        // romper enlances viejos
        for (int i = 0; i < lista.length; i++) {
            lista[i].izquierda = null;
            lista[i].derecha = null;
        }

        // Armar el arbol balanceado
        NodoColor raizArbolBalanceado = deListaAArbol(lista, 0, lista.length - 1);
        return raizArbolBalanceado;
    }

    /**
     * Cuenta la cantidad total de nodos en el subárbol cuya raíz es el nodo dado.
     * Este método recorre recursivamente el árbol sumando 1 por cada nodo visitado.
     * 
     * @param nodo raíz del subárbol del cual se desea contar los nodos.
     * @return número total de nodos en el subárbol. Devuelve 0 si el nodo es null.
     */
    private int contarNodos(NodoColor nodo){
    if (nodo == null){
        return 0;
    }
        return 1 + contarNodos(nodo.izquierda) + contarNodos(nodo.derecha);
    }

    /**
     * Llena un arreglo con los nodos del árbol realizando un recorrido in-order.
     *
     * @param nodo   nodo actual del recorrido.
     * @param lista  arreglo donde se almacenan los nodos del árbol.
     * @param indice arreglo de tamaño 1 que mantiene el índice actual de inserción.
     */
    private void llenarLista(NodoColor nodo, NodoColor[] lista, int[] indice){
        if (nodo == null){
            return;
        }
        llenarLista(nodo.izquierda, lista, indice);
        lista[indice[0]++] = nodo;
        llenarLista(nodo.derecha, lista, indice);
        }
    
    /**
     * Ordena un arreglo de nodos usando el algoritmo Insertion Sort
     * tomando como criterio la frecuencia del nodo.
     *
     * @param lista arreglo de nodos a ordenar.
     */
    private void insertionSort(NodoColor[] lista){
        int i, j;
        for (i = 0; i < lista.length; i++){
            j = i;
            while ((j > 0) && (lista[j].frecuencia < lista[j - 1].frecuencia)){
                NodoColor nodoColorTemp = lista[j];
                lista[j] = lista[j - 1];
                lista[j - 1] = nodoColorTemp;
                j--;
            }
        }
    }

    /**
     * Reconstruye un árbol balanceado a partir de una lista ordenada.
     * 
     * @param lista  arreglo ordenado de nodos
     * @param inicio índice de inicio del subarreglo
     * @param fin    índice final del subarreglo
     * @return nodo raíz del árbol reconstruido
     */
    private NodoColor deListaAArbol(NodoColor[] lista, int inicio, int fin){
        if (inicio > fin){
            return null;
        }
        int mitad = (inicio + fin) / 2;
        NodoColor raiz = lista[mitad]; // Cada subárbol agrega se puede ver como una raíz
        // Similar al algoritmo de búsqueda binaria porque la lista ya está ordenada
        raiz.izquierda = deListaAArbol(lista, inicio, mitad - 1); // Define la siguiente subraíz a la izquierda
        raiz.derecha = deListaAArbol(lista, mitad + 1, fin); // Define la siguiente subraíz a la derecha
        return raiz;
    }

}
