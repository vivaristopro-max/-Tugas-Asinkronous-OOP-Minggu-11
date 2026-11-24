
package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Product;
import model.ProductRepository;

@WebServlet(name = "ProductServlet", urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {

    private final ProductRepository repository =
            new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            request.setAttribute("product", null);
            request.getRequestDispatcher("/form_product.jsp")
                   .forward(request, response);

        } else if ("edit".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = repository.getProductById(id);

                if (product == null) {
                    response.sendRedirect(
                        request.getContextPath() + "/product");
                    return;
                }

                request.setAttribute("product", product);
                request.getRequestDispatcher("/form_product.jsp")
                       .forward(request, response);

            } catch (NumberFormatException e) {
                response.sendRedirect(
                    request.getContextPath() + "/product");
            }

        } else {
            String keyword = request.getParameter("keyword");
            List<Product> products;

            if (keyword != null && !keyword.trim().isEmpty()) {
                products = repository.searchProducts(keyword.trim());
            } else {
                products = repository.getAllProducts();
            }

            request.setAttribute("products", products);
            request.setAttribute("keyword", keyword);

            request.getRequestDispatcher("/list_product.jsp")
                   .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("add".equals(action) || "edit".equals(action)) {
            saveProduct(request, response, action);

        } else if ("delete".equals(action)) {

            String message;

            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = repository.getProductById(id);

                if (product != null) {
                    repository.deleteProduct(id);
                    message = "Produk berhasil dihapus.";
                } else {
                    message = "Produk tidak ditemukan.";
                }

            } catch (NumberFormatException e) {
                message = "ID produk tidak valid.";
            }

            request.setAttribute("message", message);
            request.setAttribute(
                "products", repository.getAllProducts()
            );

            request.getRequestDispatcher("/list_product.jsp")
                   .forward(request, response);

        } else {
            response.sendRedirect(
                request.getContextPath() + "/product");
        }
    }

    // VALIDASI SERVER-SIDE
    private String validateProductInput(HttpServletRequest request) {

        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String priceText = request.getParameter("price");
        String stockText = request.getParameter("stock");

        if (name == null || name.trim().isEmpty()) {
            return "Nama produk wajib diisi.";
        }

        if (category == null || category.trim().isEmpty()) {
            return "Kategori produk wajib diisi.";
        }

        if (priceText == null || priceText.trim().isEmpty()) {
            return "Harga wajib diisi.";
        }

        try {
            double price = Double.parseDouble(priceText.trim());

            if (Double.isNaN(price) || Double.isInfinite(price)) {
                return "Harga harus berupa angka yang valid.";
            }

            if (price < 0) {
                return "Harga tidak boleh kurang dari 0.";
            }

        } catch (NumberFormatException e) {
            return "Harga harus berupa angka.";
        }

        if (stockText == null || stockText.trim().isEmpty()) {
            return "Stok wajib diisi.";
        }

        try {
            int stock = Integer.parseInt(stockText.trim());

            if (stock < 0) {
                return "Stok tidak boleh kurang dari 0.";
            }

        } catch (NumberFormatException e) {
            return "Stok harus berupa bilangan bulat.";
        }

        return null;
    }

    // PROSES TAMBAH DAN EDIT PRODUK
    private void saveProduct(HttpServletRequest request,
                             HttpServletResponse response,
                             String action)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String priceText = request.getParameter("price");
        String stockText = request.getParameter("stock");
        String description = request.getParameter("description");

        int id = 0;

        if ("edit".equals(action)) {
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
                response.sendRedirect(
                    request.getContextPath() + "/product");
                return;
            }
        }

        // Jalankan validasi sebelum penyimpanan data
        String error = validateProductInput(request);

        if (error != null) {

            double price = 0;
            int stock = 0;

            try {
                if (priceText != null && !priceText.trim().isEmpty()) {
                    price = Double.parseDouble(priceText.trim());

                    if (!Double.isFinite(price)) {
                        price = 0;
                    }
                }
            } catch (NumberFormatException e) {
                price = 0;
            }

            try {
                if (stockText != null && !stockText.trim().isEmpty()) {
                    stock = Integer.parseInt(stockText.trim());
                }
            } catch (NumberFormatException e) {
                stock = 0;
            }

            Product product = new Product(
                id,
                name == null ? "" : name,
                category == null ? "" : category,
                price,
                stock,
                description == null ? "" : description
            );

            request.setAttribute("product", product);
            request.setAttribute("error", error);

            request.getRequestDispatcher("/form_product.jsp")
                   .forward(request, response);
            return;
        }

        // Jika validasi berhasil, data diproses
        double price = Double.parseDouble(priceText.trim());
        int stock = Integer.parseInt(stockText.trim());

        Product product = new Product(
            id,
            name.trim(),
            category.trim(),
            price,
            stock,
            description
        );

        if ("add".equals(action)) {
            repository.addProduct(product);

            request.setAttribute(
                "message", "Produk berhasil ditambahkan."
            );

        } else {
            repository.updateProduct(product);

            request.setAttribute(
                "message", "Produk berhasil diperbarui."
            );
        }

        // Kirim daftar produk terbaru ke halaman JSP
        request.setAttribute(
            "products", repository.getAllProducts()
        );

        // Tampilkan halaman daftar produk menggunakan forward
        request.getRequestDispatcher("/list_product.jsp")
               .forward(request, response);
    }
}
