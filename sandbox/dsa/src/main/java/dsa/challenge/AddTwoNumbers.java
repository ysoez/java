package dsa.challenge;

import dsa.Node;

import java.util.function.BinaryOperator;

interface AddTwoNumbers extends BinaryOperator<Node<Integer>> {

    class Default implements AddTwoNumbers {
        static final Default INSTANCE = new Default();
        @Override
        public Node<Integer> apply(Node<Integer> node1, Node<Integer> node2) {
            var dummy = new Node<>(0);
            var current = dummy;
            int carry = 0;
            while (node1 != null || node2 != null || carry != 0) {
                int sum = carry;
                if (node1 != null) {
                    sum += node1.value;
                    node1 = node1.next;
                }
                if (node2 != null) {
                    sum += node2.value;
                    node2 = node2.next;
                }
                carry = sum / 10;
                current.next = new Node<>(sum % 10);
                current = current.next;
            }

            return dummy.next;
        }
    }

}
