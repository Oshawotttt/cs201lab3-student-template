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

            private List<Integer> elems;
            private WeirdBST left;
            private WeirdBST right;
            private TraversalType type;

            private WeirdBST(List<Integer> elems, TraversalType type) {
                this.elems = elems;
                this.type = type;
            }

            private WeirdBST(String traversal, TraversalType type) {
                this(new ArrayList<>(), type);
                for (String s : traversal.split("-")) {
                    elems.add(Integer.parseInt(s.trim()));
                }
            }

            private boolean build(int head) {
                List<Integer> rest;

                if (type == TraversalType.INORDER) {
                    int i = elems.indexOf(head);
                    if (i == -1) return false;
                    return split(head, elems.subList(0, i), elems.subList(i + 1, elems.size()));

                } else if (type == TraversalType.PREORDER) {
                    if (elems.isEmpty() || elems.get(0) != head) return false;
                    rest = elems.subList(1, elems.size());

                } else {
                    if (elems.isEmpty() || elems.get(elems.size() - 1) != head) return false;
                    rest = elems.subList(0, elems.size() - 1);
                }

                int j = 0;
                while (j < rest.size() && rest.get(j) < head) j++;
                return split(head, rest.subList(0, j), rest.subList(j, rest.size()));
            }

            private boolean split(int head, List<Integer> leftElems, List<Integer> rightElems) {
                if (!leftElems.isEmpty() && Collections.max(leftElems) > head) return false;
                if (!rightElems.isEmpty() && Collections.min(rightElems) < head) return false;

                left = leftElems.isEmpty() ? null : new WeirdBST(leftElems, type);
                right = rightElems.isEmpty() ? null : new WeirdBST(rightElems, type);
                return true;
            }
        }

        private static Set<Integer> elemSet(WeirdBST node) {
            return node == null ? new HashSet<>() : new HashSet<>(node.elems);
        }

        private static boolean sameElems(WeirdBST a, WeirdBST b, WeirdBST c) {
            Set<Integer> setA = elemSet(a);
            return setA.equals(elemSet(b)) && setA.equals(elemSet(c));
        }

        private static boolean buildTree(WeirdBST inorder, WeirdBST preorder, WeirdBST postorder) {

            int head = preorder.elems.get(0);
            if (head != postorder.elems.get(postorder.elems.size() - 1)) return false;

            if (!inorder.build(head) || !preorder.build(head) || !postorder.build(head)) return false;

            if (!sameElems(inorder.left, preorder.left, postorder.left)) return false;
            if (!sameElems(inorder.right, preorder.right, postorder.right)) return false;

            if (inorder.left != null && !buildTree(inorder.left, preorder.left, postorder.left)) return false;
            if (inorder.right != null && !buildTree(inorder.right, preorder.right, postorder.right)) return false;

            return true;
        }

        public static String verify(String traversal1, String traversal2, String traversal3){

            String[] traversals = { traversal1, traversal2, traversal3 };
            TraversalType[] types = TraversalType.values();

            int size = traversal1.split("-").length;
            if (traversal2.split("-").length != size || traversal3.split("-").length != size) {
                return "Invalid traversals";
            }

            int[][] perms = {
                { 0, 1, 2 }, { 0, 2, 1 },
                { 1, 0, 2 }, { 1, 2, 0 },
                { 2, 0, 1 }, { 2, 1, 0 }
            };

            for (int[] p : perms) {
                TraversalType[] assigned = { types[p[0]], types[p[1]], types[p[2]] };

                String inorder = null, preorder = null, postorder = null;
                for (int i = 0; i < 3; i++) {
                    switch (assigned[i]) {
                        case INORDER:   inorder = traversals[i]; break;
                        case PREORDER:  preorder = traversals[i]; break;
                        case POSTORDER: postorder = traversals[i]; break;
                    }
                }

                if (buildTree(new WeirdBST(inorder, TraversalType.INORDER),
                              new WeirdBST(preorder, TraversalType.PREORDER),
                              new WeirdBST(postorder, TraversalType.POSTORDER))) {
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
