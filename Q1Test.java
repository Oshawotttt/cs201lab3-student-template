import java.util.*;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
        private enum TraversalType {
            INORDER,
            PREORDER,
            POSTORDER
        }

        private static class WeirdBST {

            private TraversalType type;
            private WeirdNode head;

            public WeirdNode getHead() {
                return head;
            }

            public WeirdBST(String traversal, TraversalType type) {
                head = new WeirdNode(traversal, type);
                this.type = type;
            }

        }

        private static class WeirdNode {

            private int elem;
            private List<Integer> tmpList;

            private WeirdNode right;
            private WeirdNode left;

            private TraversalType type;

            private WeirdNode(String traversal, TraversalType type) {
                tmpList = new ArrayList<>();
                for (String s : traversal.split("-")) {
                    tmpList.add(Integer.parseInt(s.trim()));
                }
                this.type = type;
            }

            private int getElem() {
                return elem;
            }

            private void setElem(int elem) {
                this.elem = elem;
            }

            private List<Integer> getTmpList() {
                return tmpList;
            }

            private void setTmpList(List<Integer> tmpList) {
                this.tmpList = tmpList;
            }

            private WeirdNode getRight() {
                return right;
            }

            private void setRight(WeirdNode right) {
                this.right = right;
            }

            private WeirdNode getLeft() {
                return left;
            }

            private void setLeft(WeirdNode left) {
                this.left = left;
            }

            private WeirdNode(List<Integer> tmpList, TraversalType type) {
                this.tmpList = new ArrayList<>(tmpList);
                this.type = type;
            }

            private boolean build(int head) {
                List<Integer> rest;

                if (type == TraversalType.INORDER) {
                    int i = tmpList.indexOf(head);
                    if (i == -1) return false;
                    return split(head, tmpList.subList(0, i), tmpList.subList(i + 1, tmpList.size()));
                } else if (type == TraversalType.PREORDER) {
                    // head first, then left subtree, then right subtree
                    if (tmpList.isEmpty() || tmpList.get(0) != head) return false;
                    rest = tmpList.subList(1, tmpList.size());
                } else {
                    // left subtree, then right subtree, then head last
                    if (tmpList.isEmpty() || tmpList.get(tmpList.size() - 1) != head) return false;
                    rest = tmpList.subList(0, tmpList.size() - 1);
                }

                // left subtree is the leading run of elems < head
                int j = 0;
                while (j < rest.size() && rest.get(j) < head) j++;
                return split(head, rest.subList(0, j), rest.subList(j, rest.size()));
            }

            private boolean split(int head, List<Integer> leftElems, List<Integer> rightElems) {
                // left elems must all be < head, right elems all > head
                if (!leftElems.isEmpty() && Collections.max(leftElems) > head) return false;
                if (!rightElems.isEmpty() && Collections.min(rightElems) < head) return false;

                left = leftElems.isEmpty() ? null : new WeirdNode(leftElems, type);
                right = rightElems.isEmpty() ? null : new WeirdNode(rightElems, type);
                elem = head;
                return true;
            }
        }

        private static Set<Integer> elemSet(WeirdNode node) {
            return node == null ? new HashSet<>() : new HashSet<>(node.getTmpList());
        }

        private static boolean sameElems(WeirdNode a, WeirdNode b, WeirdNode c) {
            Set<Integer> setA = elemSet(a);
            return setA.equals(elemSet(b)) && setA.equals(elemSet(c));
        }

        private static boolean buildTree(WeirdNode inorderNode, WeirdNode preorderNode, WeirdNode postorderNode) {

            List<Integer> postList = postorderNode.getTmpList();
            int preorderHead = preorderNode.getTmpList().get(0);
            int postorderHead = postList.get(postList.size() - 1);
            if (preorderHead != postorderHead) return false;

            if (!inorderNode.build(preorderHead) || !preorderNode.build(preorderHead) || !postorderNode.build(preorderHead)) return false;

            if (!sameElems(inorderNode.getLeft(), preorderNode.getLeft(), postorderNode.getLeft())) return false;
            if (!sameElems(inorderNode.getRight(), preorderNode.getRight(), postorderNode.getRight())) return false;

            // sameElems passed, so if one child is null all three are
            if (inorderNode.getLeft() != null
                    && !buildTree(inorderNode.getLeft(), preorderNode.getLeft(), postorderNode.getLeft())) return false;
            if (inorderNode.getRight() != null
                    && !buildTree(inorderNode.getRight(), preorderNode.getRight(), postorderNode.getRight())) return false;

            return true;
        }

        public static String verify(String traversal1, String traversal2, String traversal3){

            String[] traversals = { traversal1, traversal2, traversal3 };
            TraversalType[] types = TraversalType.values();

            int size = traversal1.split("-").length;
            if (traversal2.split("-").length != size || traversal3.split("-").length != size) {
                return "Invalid traversals";
            }

            // all 6 ways to assign the 3 types to the 3 traversals
            int[][] perms = {
                { 0, 1, 2 }, { 0, 2, 1 },
                { 1, 0, 2 }, { 1, 2, 0 },
                { 2, 0, 1 }, { 2, 1, 0 }
            };

            for (int[] p : perms) {
                // traversals[i] is assumed to be of type types[p[i]]
                TraversalType[] assigned = { types[p[0]], types[p[1]], types[p[2]] };

                String inorder = null, preorder = null, postorder = null;
                for (int i = 0; i < 3; i++) {
                    switch (assigned[i]) {
                        case INORDER:   inorder = traversals[i]; break;
                        case PREORDER:  preorder = traversals[i]; break;
                        case POSTORDER: postorder = traversals[i]; break;
                    }
                }

                WeirdBST inorderBST = new WeirdBST(inorder, TraversalType.INORDER);
                WeirdBST preorderBST = new WeirdBST(preorder, TraversalType.PREORDER);
                WeirdBST postorderBST = new WeirdBST(postorder, TraversalType.POSTORDER);




                // boolean valid = false;

                // valid = ;

                if (buildTree(inorderBST.getHead(), preorderBST.getHead(), postorderBST.getHead())) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 3; i++) {
                        if (i > 0) sb.append(", ");
                        String name = assigned[i].name();
                        sb.append("Traversal ").append(i + 1).append(" - ")
                          .append(name.charAt(0)).append(name.substring(1).toLowerCase());
                    }
                    return sb.toString();
                }
            }

            return "Invalid traversals";
        }
}