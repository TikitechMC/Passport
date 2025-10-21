package me.combimagnetron.passport.util.data;

public interface Tree<T> {

    Node<T> root();

    Trace<T> trace(Identifier identifier);

    Branch<T> branch(Identifier identifier);

    Navigator<T> navigator();

    int branches();

    int elements();

    interface Node<T> {

        Identifier identifier();

        T value();

    }

    interface Navigator<T> {

        Branch<T> next();

        Branch<T> previous();

        Branch<T> first();

        Branch<T> last();

    }

    interface Branch<T> {

        Identifier identifier();

        Node<T> parent();

        Iterable<Node<T>> children();

    }

    record Trace<T>(Node<T> node, Iterable<Node<T>> path) {

        public static <T> Trace<T> of(Node<T> node, Iterable<Node<T>> path) {
            return new Trace<>(node, path);
        }

    }

}
