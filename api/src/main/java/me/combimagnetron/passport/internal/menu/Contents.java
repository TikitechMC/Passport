package me.combimagnetron.passport.internal.menu;

import me.combimagnetron.passport.internal.item.Item;
import me.combimagnetron.passport.util.Pair;
import me.combimagnetron.passport.util.Pos2D;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class Contents {
        private final List<Row> rows = new LinkedList<>();
        private final Consumer<Contents> updateConsumer;

        Contents(Consumer<Contents> updateConsumer) {
            this.updateConsumer = updateConsumer;
            for (int i = 0; i < 6; i++) {
                rows.add(i, Row.empty());
            }
            for (Row row : rows) {
                for (int i = 0; i < 9; i++) {
                    row.list.add(Item.empty());
                }
            }
        }

        public void set(Pos2D pos, Item item) {
            Row row = rows.get((int) pos.y());
            row.list.set((int) pos.x(), item);
            updateConsumer.accept(this);
        }

        public Item get(Pos2D pos) {
            Row row = rows.get((int) pos.y());
            return row.list.get((int) pos.x());
        }

        public Collection<Item> all() {
            final LinkedHashSet<Item> items = new LinkedHashSet<>();
            rows.forEach(row -> items.addAll(row.list));
            return items;
        }

        public Row row(int index) {
            return rows.get(index);
        }

        public List<Row> rows() {
            return rows;
        }

        public Stream<Pair<Integer, Item>> pairStream() {
            final LinkedList<Pair<Integer, Item>> list = new LinkedList<>();
            AtomicInteger slot = new AtomicInteger();
            rows.forEach(row -> list.addAll(row.list.stream().map(item -> Pair.of(slot.getAndIncrement(), item)).toList()));
            return list.stream();
        }

        public Column column(int index) {
            return Column.from(this, index);
        }

        public int sizeVertical() {
            return rows.size();
        }

        public record Row(LinkedList<Item> list) {

            static Row empty() {
                return new Row(new LinkedList<>());
            }

            static Row of(LinkedList<Item> list) {
                return new Row(list);
            }

        }

        public record Column(LinkedList<Item> list) {

            static Column of(LinkedList<Item> list) {
                return new Column(list);
            }

            static Column from(Contents contents, int index) {
                final LinkedList<Item> itemList = new LinkedList<>();
                contents.rows.forEach(row -> itemList.add(row.list.get(index)));
                return new Column(itemList);
            }

        }

    }