/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private static final List<Product> products = new ArrayList<>();
    private static int nextId = 3;

    // Data awal produk
    static {
        products.add(new Product(
            1, "Pensil 2B", "Alat Tulis",
            3000, 20, "Pensil untuk menulis dan menggambar"
        ));

        products.add(new Product(
            2, "Buku Tulis", "Buku",
            5000, 15, "Buku tulis 38 lembar"
        ));
    }

    // Menampilkan semua produk
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    // Mencari produk berdasarkan ID
    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    // Menambahkan produk
    public void addProduct(Product product) {
        product.setName(product.getName().trim());
        products.add(new Product(
            nextId++,
            product.getName(),
            product.getCategory(),
            product.getPrice(),
            product.getStock(),
            product.getDescription()
        ));
    }

    // Mengedit produk
    public boolean updateProduct(Product updatedProduct) {
        Product product = getProductById(updatedProduct.getId());

        if (product == null) {
            return false;
        }

        product.setName(updatedProduct.getName().trim());
        product.setCategory(updatedProduct.getCategory());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());
        product.setDescription(updatedProduct.getDescription());

        return true;
    }

    // Menghapus produk
    public boolean deleteProduct(int id) {
        return products.removeIf(product -> product.getId() == id);
    }

    // Mencari produk berdasarkan nama atau kategori
    public List<Product> searchProducts(String keyword) {
        List<Product> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getName().toLowerCase()
                    .contains(keyword.toLowerCase())
                || product.getCategory().toLowerCase()
                    .contains(keyword.toLowerCase())) {
                result.add(product);
            }
        }

        return result;
    }
}
