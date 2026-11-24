
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>

<%
    List<Product> products =
        (List<Product>) request.getAttribute("products");

    String keyword = (String) request.getAttribute("keyword");

    if (keyword == null) keyword = "";
%>

<!DOCTYPE html>
<html>
<head>
    <title>Daftar Produk</title>
    <style>
        body { font-family: Arial; background: #f4f6f9; padding: 30px; }
        .container { background: white; padding: 25px; border-radius: 10px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; border: 1px solid #ddd; text-align: left; }
        th { background: #2563eb; color: white; }
        a, button { padding: 8px 12px; text-decoration: none; cursor: pointer; }
        .add { background: #16a34a; color: white; }
        .edit { color: #2563eb; }
        .delete { color: red; border: none; background: none; }
    </style>
</head>
<body>

<div class="container">

    <h1>Daftar Produk</h1>

    <a href="<%= request.getContextPath() %>/home">Kembali ke Home</a>

    <h3>Cari Produk</h3>

    <form action="<%= request.getContextPath() %>/product" method="GET">
        <input type="text" name="keyword"
               placeholder="Nama atau kategori produk"
               value="<%= keyword %>">
        <button type="submit">Cari</button>
        <a href="<%= request.getContextPath() %>/product">Reset</a>
    </form>

    <br>

    <a class="add"
       href="<%= request.getContextPath() %>/product?action=add">
       + Tambah Produk
    </a>

    <table>
        <tr>
            <th>ID</th>
            <th>Nama</th>
            <th>Kategori</th>
            <th>Harga</th>
            <th>Stok</th>
            <th>Deskripsi</th>
            <th>Aksi</th>
        </tr>

        <% if (products != null) {
            for (Product p : products) { %>

        <tr>
            <td><%= p.getId() %></td>
            <td><%= p.getName() %></td>
            <td><%= p.getCategory() %></td>
            <td>Rp <%= String.format("%,.0f", p.getPrice()) %></td>
            <td><%= p.getStock() %></td>
            <td><%= p.getDescription() %></td>
            <td>
                <a class="edit"
                   href="<%= request.getContextPath() %>/product?action=edit&id=<%= p.getId() %>">
                   Edit
                </a>

                <form action="<%= request.getContextPath() %>/product"
                      method="POST"
                      style="display:inline;"
                      onsubmit="return confirm('Yakin ingin menghapus produk ini?');">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%= p.getId() %>">
                    <button class="delete" type="submit">Hapus</button>
                </form>
            </td>
        </tr>

        <% } } %>
    </table>

</div>

</body>
</html>
