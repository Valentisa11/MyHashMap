import java.util.ArrayList;
import java.util.List;


public class MyHashMap<K,V> {
    private static final int BUCKET_COUNT = 16;
    private Node<K, V>[] buckets;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        buckets = new Node[BUCKET_COUNT];
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) { //метод добавления/обновления элемента
        int index = getIndex(key);
        Node<K, V> newNode = new Node<>(key, value);

        if (buckets[index] == null) { // если бакет пуст, то просто добавляем новую ноду
            buckets[index] = newNode;
            return;
        }


        Node<K, V> current = buckets[index]; //иначе ищем существующий ключ или перемещаемся в конец списка
        Node<K, V> previous = null;
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value; // обновление значения
                return;
            }
            previous = current; // текущий сохраняется как предыдущий
            current = current.next; //переход к следующему

            previous.next = newNode; //добавление новой ноды в конец списка
        }
    }
        public V get (K key){
            int index = getIndex(key);
            Node<K, V> current = buckets[index];
            while (current != null) {
                if (current.key.equals(key)) {
                    return current.value;
                }
                current = current.next;
            }
            return null;
        }
        public String remove (K key){ // метод удаления элемента
            int index = getIndex(key);
            Node<K, V> current = buckets[index];
            Node<K, V> previous = null;

            while (current != null) {
                if (current.key.equals(key)) {
                    if (previous == null) {
                        buckets[index] = current.next;
                    } else {
                        previous.next = current.next;
                    }
                    return null;
                }
                previous = current;
                current = current.next;
            }
            return null;
        }

        private int getIndex (K key){ //рассчитываем индекс
            return Math.abs(key.hashCode() % BUCKET_COUNT);
        }

        public List<Entry<K, V>> entrySet () {
            List<Entry<K, V>> entries = new ArrayList<>();
            for (Node<K, V> bucket : buckets) {
                Node<K, V> current = bucket;
                while (current != null) {
                    entries.add(new Entry<>(current.key, current.value));
                    current = current.next;
                }
            }
            return entries;
        }
        public static class Entry<K, V> {
            K key;
            V value;

            Entry(K key, V value) {
                this.key = key;
                this.value = value;
            }

            @Override
            public String toString() {
                return "\n" + key + ": " + value;
            }
        }

        public static void main (String[]args){
            MyHashMap<String, String> map = new MyHashMap<>();

            map.put("9217_134156", "Пушкин Александр Сергеевич");
            map.put("1475_789987", "Лермонтов Михаил Юрьевич");
            map.put("7898_789654", "Толстой Лев Николаевич");

            System.out.println("_______________Список писателей_________________");
            System.out.println(map.entrySet());
            System.out.println();

            System.out.println("ФИО: " + map.get("9217_134156"));
            System.out.println("ФИО: " + map.get("1475_789987"));

            map.put("1475_789987", "Джек Лондон");
            System.out.println();
            System.out.println("Новые ФИО: " + map.get("1475_789987"));
            System.out.println();
            System.out.println("_______________Список писателей_________________");
            System.out.println(map.entrySet()); // выводим новый список после замены

            map.remove("7898_789654"); //удаление элемента
            System.out.println();
            System.out.println("После удаления: " + getValueOrDefault(map.get("7898_789654")));

        }

        private static String getValueOrDefault (String value){ //дополнительный метод для замены дефолтного  null на "удалено"
            return value != null ? value : "Удалено";
        }
    }
