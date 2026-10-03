package ourlibrary.Back;

import java.sql.SQLOutput;
import ourlibrary.Back.*;
import ourlibrary.Front.*;
import static java.lang.Math.max;

public class BooksTree {

    Book root;

    public void insert(Book book) {
        this.root = insertHelper(this.root, book);
    }

    public Book getRoot() {
        return root;
    }

    public void setRoot(Book root) {
        this.root = root;
    }

    //<editor-fold defaultstate="collapsed" desc="insert">
    public Book insertHelper(Book root, Book book) {

        // If the tree is empty, return a new node
        if (root == null) {
            return book;
        }

        // Otherwise, recur down the tree
        if (book.getISBN() < root.getISBN()) {
            root.left = insertHelper(root.left, book);
        } else {
            root.right = insertHelper(root.right, book);
        }

        root.height = 1 + max(height(root.left),
                height(root.right));
        //the difference between the heights of the
        int balance = getBalance(root);
        //right rotation case
        if (balance > 1 && book.getISBN() < root.left.getISBN()) {
            return rightRotate(root);
        }
        if (balance < -1 && book.getISBN() > root.right.getISBN()) {
            return leftRotate(root);
        }

        // Left Right Case
        if (balance > 1 && book.getISBN() > root.left.getISBN()) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Left Case
        if (balance < -1 && book.getISBN() < root.right.getISBN()) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        // Return the (unchanged) node pointer
        return root;
    }
//</editor-fold>

    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="height">
    public int height(Book node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }
//</editor-fold>

    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    // Right rotate subtree rooted with node
    //<editor-fold defaultstate="collapsed" desc="rightRotate">
    public Book rightRotate(Book node) {
        Book leftChild = node.left;
        Book temp = leftChild.right;

        // Perform rotation
        leftChild.right = node;
        node.left = temp;

        // Update heights
        node.height = max(height(node.left), height(node.right)) + 1;
        leftChild.height = max(height(leftChild.left), height(leftChild.right)) + 1;

        // Return new root
        return leftChild;
    }
//</editor-fold>

    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    // Left rotate subtree rooted with node
    //<editor-fold defaultstate="collapsed" desc="leftRotate">
    public Book leftRotate(Book node) {
        Book rightChild = node.right;
        Book temp = rightChild.left;

        // Perform rotation
        rightChild.left = node;
        node.right = temp;

        // Update heights
        node.height = max(height(node.left), height(node.right)) + 1;
        rightChild.height = max(height(rightChild.left), height(rightChild.right)) + 1;

        // Return new root
        return rightChild;
    }
//</editor-fold>

    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    // Get balance factor of node
    //<editor-fold defaultstate="collapsed" desc="getBalance">
    public int getBalance(Book node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }
//</editor-fold>

    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="inOrder">
    public void inOrder(Book node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.getISBN() + " ");
            inOrder(node.right);
        }
    }

    public Book search(int key) {
        return searchHealper(this.root, key);
    }

//</editor-fold>
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="search">
    public Book searchHealper(Book root, int key) {

        // root is null -> return false
        if (root == null) {
            return root;
        }

        // if root has key -> return true
        if (root.getISBN() == key) {
            return root;
        }

        if (key > root.getISBN()) {
            return searchHealper(root.right, key);
        }

        return searchHealper(root.left, key);
    }
//</editor-fold>

    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="minElement">
    public Book minElement(Book root) { //returns successsor

        if (root.left != null) {
            return minElement(root.left);
        }
        return root;
    }

    public void deleteBook(int ISBN) {
        this.root = deleteBookHelper(this.root, ISBN);
    }

//</editor-fold>
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="deleteBook">
    public Book deleteBookHelper(Book root, int ISBN) {
        if (root == null) {
            return null;
        }

        if (ISBN < root.getISBN()) {
            root.left = deleteBookHelper(root.left, ISBN);
        } else if (ISBN > root.getISBN()) {
            root.right = deleteBookHelper(root.right, ISBN);
        } else {
            // Node to be deleted found
            // Case 1: leaf node
            if (root.left == null && root.right == null) {
                return null;
            } // Case 2: one child
            else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } // Case 3: two children – replace with inorder successor
            else {
                Book successor = minElement(root.right);
                root.copyBookData(successor);
                root.right = deleteBookHelper(root.right, successor.getISBN());
            }
        }

        // --- Rebalance current node (common for all paths) ---
        root.height = 1 + max(height(root.left), height(root.right));
        int balance = getBalance(root);

        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }
        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }
        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    public void display() {
        this.inOrder(this.root);
    }

    public String AvailablesAllBooksHelper(Book root) {

        if (root == null) {
            return "";
        }

        return AvailablesAllBooksHelper(root.left)
                + root.getISBN()
                + " -> "
                + root.getName()
                + " -> "
                + root.getAvailables()
                + "\n"
                + AvailablesAllBooksHelper(root.right);
    }

}
